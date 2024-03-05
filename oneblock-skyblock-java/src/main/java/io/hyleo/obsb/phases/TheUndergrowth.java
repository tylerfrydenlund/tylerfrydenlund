package io.hyleo.obsb.phases;

import io.hyleo.obsb.api.LootDigest;
import io.hyleo.obsb.api.Phase;
import io.hyleo.obsb.api.Weight;
import io.hyleo.obsb.util.EntityUtil;
import io.hyleo.obsb.util.ItemBuilder;
import io.hyleo.obsb.util.Util;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.val;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Evoker;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TheUndergrowth {
    @NotNull
    public static final String NAME = "Undergrowth";

    public static final int BLOCKS = 950;

    @NotNull
    public static final String DISPLAY_NAME = "Undergrowth";

    @NotNull
    public static final TextColor TEXT_COLOR = Objects.requireNonNull(TextColor.fromHexString("#8A9A5B"));

    @NotNull
    public static final BossBar.Color BAR_COLOR = BossBar.Color.GREEN;

    @NotNull
    public static final Component CHEST_DISPLAY_NAME = Component.text("Undergrowth Chest").color(NamedTextColor.DARK_GRAY);

    @NotNull
    public static final Component MANSION_CHEST_DISPLAY_NAME = Component.text("Mansion Chest").color(NamedTextColor.DARK_GRAY);

    @NotNull
    public static final Component BOSS_NAME = Component.text("Evoker");

    @NotNull
    public static final BiFunction<@NotNull BlockBreakEvent, @NotNull Player, @NotNull Collection<@NotNull LivingEntity>> BOSS = (e, t) -> {
        val spawnLocation = e.getBlock().getLocation().add(0, 2, 0);
        val boss = (Evoker) e.getBlock().getWorld().spawnEntity(spawnLocation, EntityType.EVOKER);
        val maxHealth = boss.getAttribute(Attribute.GENERIC_MAX_HEALTH);

        assert maxHealth != null;
        maxHealth.setBaseValue(48);

        boss.setHealth(48);
        boss.setCanJoinRaid(false);
        return List.of(boss);
    };

    @NotNull
    public static final LootDigest LOOT = LootDigest.builder("UNDERGROWTH", Weight.of(3), Weight.of(6), Weight.of(9))
            .item(() -> new ItemStack(Material.BIRCH_LOG), 1, 7.5, true, Weight.of(2, 1), Weight.of(4, 2), Weight.of(6, 1))
            .item(() -> new ItemStack(Material.DARK_OAK_LOG), 1, 5.0, true, Weight.of(2, 1), Weight.of(4, 2), Weight.of(6, 1))
            .item(() -> new ItemStack(Material.SPRUCE_LOG), 1, 7.5, true, Weight.of(2, 1), Weight.of(4, 2), Weight.of(6, 1))
            .item(() -> new ItemStack(Material.BIRCH_SAPLING), 1, 2.5, true, Weight.of(1, 4), Weight.of(2, 2))
            .item(() -> new ItemStack(Material.DARK_OAK_SAPLING), 1, 2.0, true, Weight.of(1, 4), Weight.of(2, 2))
            .item(() -> new ItemStack(Material.SPRUCE_SAPLING), 1, 2.5, true, Weight.of(1, 4), Weight.of(2, 2))

            .item(() -> new ItemStack(Material.BONE), 1, 4.0, true, Weight.of(1, 1), Weight.of(2, 2), Weight.of(4, 1))
            .item(() -> new ItemStack(Material.STRING), 1, 4.0, true, Weight.of(1, 1), Weight.of(2, 2), Weight.of(4, 1))
            .item(() -> new ItemStack(Material.GUNPOWDER), 2, 2.0, true, Weight.of(1, 1), Weight.of(2, 2), Weight.of(4, 1))

            .item(() -> new ItemStack(Material.FEATHER), 1, 2.0, true, Weight.of(1, 1), Weight.of(2, 2), Weight.of(4, 1))
            .item(() -> new ItemStack(Material.RABBIT_HIDE), 1, 2.0, true, Weight.of(2, 1), Weight.of(4, 2), Weight.of(8, 1))
            .item(() -> new ItemStack(Material.CHICKEN), 2, 2.0, true, Weight.of(1, 1), Weight.of(2, 2), Weight.of(4, 1))
            .item(() -> new ItemStack(Material.RABBIT), 2, 2.0, true, Weight.of(1, 1), Weight.of(2, 2), Weight.of(4, 1))

            .item(() -> new ItemStack(Material.PUMPKIN_SEEDS), 2, 2.5, true, Weight.of(1, 1), Weight.of(2, 2))
            .item(() -> new ItemStack(Material.SWEET_BERRIES), 2, 5.0, Weight.of(1, 1), Weight.of(2, 2), Weight.of(4, 2), Weight.of(8, 1))
            .item(() -> new ItemStack(Material.HONEYCOMB), 2, 1.5, Weight.of(3, 4), Weight.of(6, 1))
            .item(() -> new ItemStack(Material.HONEY_BOTTLE), 2, 1.5)

            .item(() -> new ItemStack(Material.FERN), 1, 2.5, true)
            .item(() -> new ItemStack(Material.LARGE_FERN), 1, 1.5, true)
            .item(() -> new ItemStack(Material.RED_MUSHROOM), 1, 2.0, true)
            .item(() -> new ItemStack(Material.BROWN_MUSHROOM), 1, 2.0, true)
            .item(() -> new ItemStack(Material.DANDELION), 1, 1.5, true)
            .item(() -> new ItemStack(Material.POPPY), 1, 1.5, true)
            .item(() -> new ItemStack(Material.ALLIUM), 1, 1.0, true)
            .item(() -> new ItemStack(Material.LILY_OF_THE_VALLEY), 1, 1.0, true)
            .item(() -> new ItemStack(Material.LILAC), 1, 1.0, true)
            .item(() -> new ItemStack(Material.ROSE_BUSH), 1, 1.0, true)
            .item(() -> new ItemStack(Material.PEONY), 1, 1.0, true)

            .build();

    @NotNull
    public static final LootDigest MANSION_LOOT = LootDigest.builder("UNDERGROWTH_MANSION", Weight.of(3), Weight.of(6), Weight.of(9))
            .item(() -> new ItemStack(Material.DARK_OAK_LOG), 1, 5.0, true, Weight.of(2, 1), Weight.of(4, 2), Weight.of(6, 1))

            .item(() -> new ItemStack(Material.BONE), 1, 3.0, true, Weight.of(1, 1), Weight.of(2, 2), Weight.of(4, 1))
            .item(() -> new ItemStack(Material.STRING), 1, 3.0, true, Weight.of(1, 1), Weight.of(2, 2), Weight.of(4, 1))
            .item(() -> new ItemStack(Material.GUNPOWDER), 1, 1.5, true, Weight.of(1, 1), Weight.of(2, 2), Weight.of(4, 1))

            .item(() -> new ItemStack(Material.FEATHER), 1, 4.0, true, Weight.of(1, 1), Weight.of(2, 2), Weight.of(4, 1))
            .item(() -> new ItemStack(Material.RABBIT_HIDE), 1, 2.0, true, Weight.of(2, 1), Weight.of(4, 2), Weight.of(8, 1))

            .item(() -> new ItemStack(Material.WHEAT), 2, 4.0, Weight.of(2, 1), Weight.of(4, 2), Weight.of(8, 1))
            .item(() -> new ItemStack(Material.BREAD), 2, 2.0, Weight.of(1, 1), Weight.of(2, 2), Weight.of(4, 1))
            .item(() -> new ItemStack(Material.PUMPKIN_SEEDS), 2, 1.5, Weight.of(1, 2), Weight.of(2, 1))
            .item(() -> new ItemStack(Material.COOKED_RABBIT), 3, 1.0, Weight.of(1, 1), Weight.of(2, 2), Weight.of(4, 1))
            .item(() -> new ItemStack(Material.COOKED_CHICKEN), 3, 1.0, Weight.of(1, 1), Weight.of(2, 2), Weight.of(4, 1))
            .item(() -> new ItemStack(Material.MUSHROOM_STEW), 2, 2.0, Weight.of(1, 1))

            .item(() -> new ItemStack(Material.COAL), 2, 1.75, Weight.of(1, 4), Weight.of(2, 2), Weight.of(4, 1))
            .item(() -> new ItemStack(Material.IRON_INGOT), 3, 1.5, Weight.of(1, 4), Weight.of(2, 1))
            .item(() -> new ItemStack(Material.NAME_TAG), 3, 0.75)

            .item(() -> new ItemStack(Material.CHAINMAIL_HELMET), 3, 0.5)
            .item(() -> new ItemStack(Material.CHAINMAIL_CHESTPLATE), 3, 0.5)
            .item(() -> new ItemStack(Material.CHAINMAIL_LEGGINGS), 3, 0.5)
            .item(() -> new ItemStack(Material.CHAINMAIL_BOOTS), 3, 0.5)
            .item(() -> new ItemStack(Material.IRON_HOE), 3, 0.75)

            .item(() -> new ItemStack(Material.BOOK), 2, 1.5, Weight.of(1, 1), Weight.of(2, 2), Weight.of(4, 2), Weight.of(8, 1))
            .item(new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.DURABILITY, Weight.of(1, 1)).asStackSupplier(), 4, 0.5)
            .item(new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, Weight.of(1, 1)).asStackSupplier(), 4, 0.5)
            .item(new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.THORNS, Weight.of(1, 1)).asStackSupplier(), 4, 0.5)
            .item(new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.DAMAGE_ALL, Weight.of(1, 1)).asStackSupplier(), 4, 0.5)
            .item(new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.DIG_SPEED, Weight.of(1, 1)).asStackSupplier(), 4, 0.5)

            .item(() -> new ItemStack(Material.MUSIC_DISC_13), 3, 0.1, true)
            .item(() -> new ItemStack(Material.MUSIC_DISC_CAT), 3, 0.1, true)

            .build();

    @NotNull
    public static final LootDigest REWARDS = LootDigest.builder("UNDERGROWTH_REWARDS", Weight.of(0))
            .item(() -> new ItemStack(Material.ANVIL))

            .chain(LOOT, Weight.of(2))
            .chain(MANSION_LOOT, Weight.of(6))

            .build();

    @NotNull
    public static final Phase PHASE = Phase.builder().name(NAME).displayName(DISPLAY_NAME).textColor(TEXT_COLOR).color(BAR_COLOR).blocks(BLOCKS)
            .boss(BOSS)
            .bossName(BOSS_NAME)
            .rewards(REWARDS)
            .result((e, c) -> Util.spawnChest(CHEST_DISPLAY_NAME, LOOT, c).apply(e, c), Weight.of(4, 0.25))
            .result((e, c) -> Util.spawnChest(MANSION_CHEST_DISPLAY_NAME, MANSION_LOOT, c).apply(e, c), Weight.of(4, 0.15))

            .result(Util.defaultData(Material.PODZOL), Weight.of(1, 6.25))
            .result(Util.defaultData(Material.COARSE_DIRT), Weight.of(1, 3.75))
            .result(Util.defaultData(Material.MOSSY_COBBLESTONE), Weight.of(1, 3.75))
            .result(Util.defaultData(Material.GRASS_BLOCK), Weight.of(1, 6.25))
            .result(Util.defaultData(Material.BIRCH_LOG), Weight.of(1, 1.25))
            .result(Util.defaultData(Material.SPRUCE_LOG), Weight.of(1, 1.25))
            .result(Util.defaultData(Material.DARK_OAK_LOG), Weight.of(1, 1.0))
            .result(Util.defaultData(Material.BEE_NEST), Weight.of(3, 0.125))
            .result(Util.defaultData(Material.PUMPKIN), Weight.of(3, 0.75))
            .result(Util.defaultData(Material.HONEYCOMB_BLOCK), Weight.of(2, 0.75))
            .result(Util.defaultData(Material.COAL_ORE), Weight.of(2, 0.875))
            .result(Util.defaultData(Material.IRON_ORE), Weight.of(3, 0.75))
            .result(Util.defaultData(Material.RED_MUSHROOM_BLOCK), Weight.of(2, 0.75))
            .result(Util.defaultData(Material.BROWN_MUSHROOM_BLOCK), Weight.of(2, 0.75))
            .result(Util.defaultData(Material.MUSHROOM_STEM), Weight.of(2, 0.75))

            .result(EntityUtil.spawnEntity(EntityType.BEE), Weight.of(3, 0.0625))
            .result(EntityUtil.spawnEntity(EntityType.WOLF), Weight.of(3, 0.0625))
            .result(EntityUtil.spawnEntity(EntityType.FOX), Weight.of(3, 0.0625))
            .result(EntityUtil.spawnEntity(EntityType.CHICKEN), Weight.of(2, 0.1))
            .result(EntityUtil.spawnEntity(EntityType.RABBIT), Weight.of(2, 0.1))
            .result(EntityUtil.spawnEntity(EntityType.SPIDER), Weight.of(2, 0.25))
            .result(EntityUtil.spawnBabyZombie(), Weight.of(3, 0.075))
            .result(EntityUtil.spawnCreeper(), Weight.of(3, 0.075))
            .result(EntityUtil.spawnEntity(EntityType.VINDICATOR), Weight.of(3, 0.125))
            .build();
}
