package io.hyleo.obsb.api;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import io.hyleo.obsb.OneblockSkyblock;
import io.hyleo.obsb.util.Util;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.loot.LootContext;
import org.bukkit.loot.LootTable;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.Map.Entry;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Builder(builderMethodName = "hidden")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Accessors(fluent = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NonNull
@Getter
public class LootDigest implements LootTable {

    public static LootDigestBuilder builder(String name, Weight... rolls) {
        return hidden().name(name).rolls(List.of(rolls));
    }

    @NotNull
    String name;

    @Nullable
    List<@NotNull Weight> rolls;

    @Nullable
    Map<@NotNull Supplier<@NotNull ItemStack>, @NotNull ItemWeight> items;

    @Nullable
    Map<@NotNull Supplier<@NotNull ItemStack>, @NotNull ItemWeight> guaranteedItems;

    @Nullable
    Multimap<@NotNull LootDigest, @NotNull Weight> chains;

    public int randomRolls(@NonNull Random random, @NonNull LootContext context) {
        if (Objects.isNull(rolls)) {
            return 1;
        }
        return Util.selectRandom(random, Util.adjustWeights(rolls.stream().collect(Collectors.toMap(Weight::quality, w -> w)), context), 1);
    }

    @NotNull
    public Collection<@NotNull ItemStack> guaranteedItems(@NonNull Random random, @NonNull LootContext context, boolean isChain) {

        if (Objects.isNull(guaranteedItems)) {
            return List.of();
        }

        Map<Supplier<ItemStack>, Integer> amounts = guaranteedItems.entrySet().stream()
                .filter(w -> !(isChain && w.getValue().preventChaining()))
                .collect(Collectors.toMap(Entry::getKey,
                        e -> Util.selectRandom(random, Util.adjustWeights(guaranteedItems.get(e.getKey()).amounts(), context), -1)));

        Map<ItemStack, Supplier<ItemStack>> items = guaranteedItems.keySet().stream()
                .filter(w -> !(isChain && guaranteedItems.get(w).preventChaining()))
                .collect(Collectors.toMap(Supplier::get, s -> s));

        items.forEach((key, value) -> key.setAmount((amounts.get(value) == -1) ? key.getAmount() : amounts.get(value)));

        return items.keySet();
    }

    @NotNull
    public ItemStack randomItem(@NonNull Random random, @NonNull LootContext context, boolean isChain) {

        if (Objects.isNull(items)) {
            return new ItemStack(Material.AIR);
        }

        Supplier<ItemStack> defaultSupplier = () -> new ItemStack(Material.AIR);

        val supplier = Util.selectRandom(random, Util.adjustWeights(items.entrySet()
                .stream().filter(w -> !(isChain && w.getValue().preventChaining()))
                .collect(Collectors.toMap(Entry::getKey, Entry::getValue)
                ), context), defaultSupplier);

        val item = supplier.get();
        val amount = supplier == defaultSupplier ? 1 : Util.selectRandom(random, Util.adjustWeights(items.get(supplier).amounts(), context), item.getAmount());

        item.setAmount(amount);

        return item; // Ensure result is non null
    }

    @NotNull
    public Collection<@NotNull ItemStack> populateLoot(@NonNull Random random, @NonNull LootContext context, boolean isChain) {
        val items = new ArrayList<>(guaranteedItems(random, context, isChain));

        val rolls = randomRolls(random, context);
        IntStream.range(0, rolls).forEach(i -> items.add(randomItem(random, context, isChain)));

        if (isChain) {
            chains.keys().forEach(c -> items.addAll(c.populateLoot(random, context, true)));
        }
        items.removeIf(Objects::isNull);

        return items;
    }

    @Override
    @NotNull
    public Collection<ItemStack> populateLoot(@Nullable Random random, @NonNull LootContext context) {
        random = Objects.isNull(random) ? new Random() : random;

        val items = new ArrayList<>(populateLoot(random, context, false));

        items.addAll(populateLootFromChains(random, context));

        items.removeIf(Objects::isNull);

        return items;
    }

    @NotNull
    public Collection<ItemStack> populateLootFromChains(@NonNull Random random, @NonNull LootContext context) {
        List<ItemStack> items = new ArrayList<>();

        if (chains == null) {
            return items;
        }

        for (val chain : chains.keys()) {
            val r = chains.get(chain);

            if (r.isEmpty()) {
                items.addAll(chain.populateLoot(random, context, true));
                continue;
            }

            val weights = Util.adjustWeights(r.stream().collect(Collectors.toMap(Weight::quality, w -> w)), context);
            val rolls = Util.selectRandom(random, weights, 1);

            items.addAll(chain.guaranteedItems(random, context, true));
            IntStream.range(0, rolls).forEach(i -> items.add(chain.randomItem(random, context, true)));
        }

        return items;
    }

    @Override
    public void fillInventory(@NonNull Inventory inventory, @Nullable Random random, @NonNull LootContext context) {
        val items = populateLoot(random, context);

        if (inventory.firstEmpty() == -1) {
            return;
        }

        for (val item : items) {
            val slot = randomSlot(inventory);

            if (slot == -1) {
                return;
            }

            inventory.setItem(slot, item);
        }

        // Rerun if generated items is empty and loot table is not entirely empty
        if (items.isEmpty() && !guaranteedItems().isEmpty() && !items().isEmpty()) {
            fillInventory(inventory, random, context);
        }

    }

    public int randomSlot(@NonNull Inventory inventory) {
        if (inventory.firstEmpty() == -1) {
            return -1;
        }

        val slot = new Random().nextInt(inventory.getSize());

        return inventory.getItem(slot) == null ? slot : randomSlot(inventory);
    }

    @Override
    @NotNull
    public NamespacedKey getKey() {
        return new NamespacedKey(JavaPlugin.getProvidingPlugin(OneblockSkyblock.class), name);
    }


    public static class LootDigestBuilder {

        @NotNull
        public final LootDigestBuilder chain(@NonNull LootDigest lootDigest, @NonNull Weight... rolls) {
            chains = chains == null ? ArrayListMultimap.create() : chains;
            for (val r : rolls) {
                chains.put(lootDigest, r);
            }
            return this;
        }

        @NotNull
        public final LootDigestBuilder item(@NonNull Supplier<@NotNull ItemStack> item, @NonNull Weight... amounts) {
            return item(item, false, amounts);
        }

        @NotNull
        public final LootDigestBuilder item(@NotNull Supplier<@NotNull ItemStack> item, boolean preventChaining, @NonNull Weight... amounts) {
            guaranteedItems = guaranteedItems == null ? new HashMap<>() : guaranteedItems;
            guaranteedItems.put(item, ItemWeight.of(Integer.MAX_VALUE, Double.MAX_VALUE, preventChaining, collect(amounts)));
            return this;
        }

        @NotNull
        public final LootDigestBuilder item(@NonNull Supplier<@Nullable ItemStack> item, int quality, double weight, @NonNull Weight... amounts) {
            return item(item, quality, weight, false, amounts);
        }

        @NotNull
        public final LootDigestBuilder item(@NonNull Supplier<@Nullable ItemStack> item, int quality, double weight, boolean preventChaining, @NonNull Weight... amounts) {
            items = items == null ? new HashMap<>() : items;
            items.put(item, ItemWeight.of(quality, weight, preventChaining, collect(amounts)));
            return this;
        }

        @NotNull
        final Map<@NotNull Integer, @NotNull Weight> collect(@NonNull Weight... amounts) {
            return Util.remap(Arrays.stream(amounts).collect(Collectors.toMap(Weight::quality, Weight::weight)));
        }


    }

}
