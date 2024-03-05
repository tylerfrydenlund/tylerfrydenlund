package io.hyleo.obsb.util;

import io.hyleo.obsb.api.PhaseEnchantment;
import io.hyleo.obsb.api.Weight;
import lombok.val;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;

import java.util.*;
import java.util.function.Supplier;

public class ItemBuilder {
    Material material;
    int amount = 1;
    Component displayName;
    List<Component> lore = new ArrayList<>();
    int damage = 0;

    Map<Enchantment, Integer> enchantments = new HashMap<>();
    Map<Enchantment, Integer> unsafeEnchantments = new HashMap<>();
    Map<Enchantment, Map<Integer, Weight>> weightedEnchantments = new HashMap<>();
    Map<Integer, Weight> quantities = new HashMap<>();

    boolean ignoreConflictingEnchants;

    public ItemBuilder(ItemStack stack) {
        this(stack, true);
    }


    public ItemBuilder(ItemStack stack, boolean copyLore) {
        val meta = stack.getItemMeta();

        this.material = stack.getType();
        this.amount = stack.getAmount();
        this.displayName = meta.displayName();
        this.lore = copyLore ? meta.lore() : new ArrayList<>();

        if(meta instanceof Damageable damageable) {
            this.damage = damageable.getDamage();
        }

        if(meta instanceof EnchantmentStorageMeta storageMeta) {
            for (Map.Entry<Enchantment, Integer> entry : storageMeta.getStoredEnchants().entrySet()) {
                val enchantment = entry.getKey();
                val level = entry.getValue();

                (!isVanillaEnchant(enchantment) ? unsafeEnchantments : enchantments).put(enchantment, level);
            }
        } else {
            for (Map.Entry<Enchantment, Integer> entry : stack.getEnchantments().entrySet()) {
                val enchantment = entry.getKey();
                val level = entry.getValue();

                (!isVanillaEnchant(enchantment) ? unsafeEnchantments : enchantments).put(enchantment, level);
            }
        }
    }

    public ItemBuilder(Material material) {
        this.material = material;
    }

    public ItemBuilder(Material material, int amount) {
        this.material = material;
        this.amount = amount;
    }

    public ItemBuilder setDisplayName(String displayName) {
        this.displayName = Component.text(displayName);

        return this;
    }

    public ItemBuilder setDisplayName(Component displayName) {
        this.displayName = displayName;

        return this;
    }

    public ItemBuilder setDamage(int damage) {
        this.damage = damage;

        return this;
    }

    public ItemBuilder addEnchant(Enchantment enchantment, int level, boolean ignoreLevelRestriction) {
        (!isVanillaEnchant(enchantment) ? unsafeEnchantments : enchantments).put(enchantment, level);
        return this;
    }

    public static boolean isVanillaEnchant(Enchantment enchantment) {
        return enchantment.getKey().namespace().equalsIgnoreCase("minecraft");
    }

    public ItemBuilder setIgnoringConflictingEnchants(boolean ignore) {
        ignoreConflictingEnchants = ignore;

        return this;
    }

    public ItemBuilder addQuantity(Integer quantity, Double weight) {
        quantities.put(quantity, new Weight(quantity, weight));

        return this;
    }

    public ItemBuilder addWeightedEnchant(Enchantment enchantment, Weight... weights) {
        List.of(weights).forEach(w -> weightedEnchantments.computeIfAbsent(enchantment, k -> new HashMap<>()).put(w.quality(), w));

        return this;
    }

    public ItemStack asStack() {

        val stack = new ItemStack(material, amount);
        val meta = stack.getItemMeta();

        val enchants = new HashMap<>(enchantments);
        val unsafeEnchants = new HashMap<>(unsafeEnchantments);

        val weightedEnchants = new HashMap<>(weightedEnchantments);
        val random = new Random();
        val quantity = Util.selectRandom(random, quantities, 0);

        for(int i = 0; i < quantity; i++) {
            if(weightedEnchants.isEmpty()) {
                break;
            }

            val index = random.nextInt(weightedEnchants.size());
            val enchant = new ArrayList<>(weightedEnchants.keySet()).get(index);
            val level = Util.selectRandom(random, weightedEnchants.get(enchant), 1);
            val iterator = (!isVanillaEnchant(enchant) ? unsafeEnchants : enchants).keySet().iterator();

            boolean conflicts = false;

            while(!conflicts && iterator.hasNext()) {
                conflicts = enchant.conflictsWith(iterator.next());
            }

            if(!conflicts || ignoreConflictingEnchants) {
                (enchant instanceof PhaseEnchantment ? unsafeEnchants : enchants).put(enchant, level);
            }

            weightedEnchants.remove(enchant);
        }

        if(displayName != null) {
            meta.displayName(displayName);
        }

        for(Map.Entry<Enchantment, Integer> entry : enchants.entrySet()) {
            val enchant = entry.getKey();
            val level = entry.getValue();

            if(meta instanceof EnchantmentStorageMeta storageMeta) {
                storageMeta.addStoredEnchant(enchant, level, true);
            } else {
                meta.addEnchant(enchant, level, true);
            }
        }

        val lore = new ArrayList<Component>();

        for(Map.Entry<Enchantment, Integer> entry : unsafeEnchants.entrySet()) {
            val enchant = entry.getKey();
            val level = entry.getValue();

            if(meta instanceof EnchantmentStorageMeta storageMeta) {
                storageMeta.addStoredEnchant(enchant, level, true);
            }

            if(!isVanillaEnchant(enchant)) {
                lore.add(enchant.displayName(level).decorations(Map.of(TextDecoration.ITALIC, TextDecoration.State.FALSE)));
            }
        }

        if(this.lore != null) {
            for(Component loreLine : this.lore) {
                lore.add(loreLine);
            }
        }

        if(!lore.isEmpty()) {
            meta.lore(lore);
        }

        if(meta instanceof Damageable damageable) {
            damageable.setDamage(damage);
        }

        stack.setItemMeta(meta);

        for(Map.Entry<Enchantment, Integer> entry : unsafeEnchants.entrySet()) {
            val enchant = entry.getKey();
            val level = entry.getValue();

            if(!(meta instanceof EnchantmentStorageMeta storageMeta)) {
                stack.addUnsafeEnchantment(enchant, level);
            }
        }

        return stack;
    }

    public Supplier<ItemStack> asStackSupplier() { return this::asStack; }

    public static Map<Enchantment, Integer> allEnchants(ItemStack itemStack){
        val meta = itemStack.getItemMeta();
        val enchantments = meta.getEnchants();

        val storedEnchantments = meta instanceof EnchantmentStorageMeta enchMeta ? enchMeta.getStoredEnchants() : new HashMap<Enchantment, Integer>();

        val all = new HashMap<Enchantment, Integer>();
        all.putAll(enchantments);
        all.putAll(storedEnchantments);

        return all;
    }

}
