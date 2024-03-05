package io.hyleo.obsb.phases;

import io.hyleo.obsb.api.LootDigest;
import io.hyleo.obsb.api.Phase;
import io.hyleo.obsb.api.Weight;
import io.hyleo.obsb.util.EntityUtil;
import io.hyleo.obsb.util.ItemBuilder;
import io.hyleo.obsb.util.Util;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.experimental.UtilityClass;
import lombok.val;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionType;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;

@UtilityClass
@FieldDefaults(makeFinal = true, level = AccessLevel.PUBLIC)
public class BoggedCloudforest {
    @NotNull
    public static final String NAME = "Bogged_Cloudforest";

    public static final int BLOCKS = 1250;

    @NotNull
    public static final String DISPLAY_NAME = "Bogged Cloudforest";

    @NotNull
    public static final TextColor TEXT_COLOR = Objects.requireNonNull(TextColor.fromHexString("#B87333"));

    @NotNull
    public static final Component BOSS_NAME = Component.text("Alchemist");

    @NotNull
    public static final BossBar.Color BAR_COLOR = BossBar.Color.GREEN;

    @NotNull
    public static final Component CHEST_DISPLAY_NAME = Component.text("Clouded Chest").color(NamedTextColor.DARK_GRAY);
    @NotNull
    public static final Component WITCH_CHEST_DISPLAY_NAME = Component.text("Alchemist Chest").color(NamedTextColor.DARK_GRAY);

    @NotNull
    public static final BiFunction<@NotNull BlockBreakEvent, @NotNull Player, @NotNull Collection<@NotNull LivingEntity>> BOSS = (e, t) -> {
        val spawnLocation = e.getBlock().getLocation().add(0, 2, 0);
        val boss = (Witch) e.getBlock().getWorld().spawnEntity(spawnLocation, EntityType.WITCH);

        val maxHealth = boss.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        assert maxHealth != null;

        maxHealth.setBaseValue(78);
        boss.setHealth(78);

        return List.of(boss);
    };

    @NotNull
    public static final LootDigest LOOT = LootDigest.builder("BOGGED_CLOUD_FOREST", Weight.of(3), Weight.of(6), Weight.of(9))
            .item(() -> new ItemStack(Material.JUNGLE_LOG), 1, 7.5, true, Weight.of(2, 1), Weight.of(4, 2), Weight.of(6, 1))
            .item(() -> new ItemStack(Material.JUNGLE_SAPLING), 1, 2.5, true, Weight.of(1, 4), Weight.of(2, 2))
            .item(() -> new ItemStack(Material.MANGROVE_LOG), 1, 7.5, true, Weight.of(2, 1), Weight.of(4, 2), Weight.of(6, 1))
            .item(() -> new ItemStack(Material.MANGROVE_PROPAGULE), 1, 2.5, true, Weight.of(1, 4), Weight.of(2, 2))

            .item(() -> new ItemStack(Material.SLIME_BALL), 1, 3.0, true, Weight.of(1, 1), Weight.of(2, 2), Weight.of(4, 1))
            .item(() -> new ItemStack(Material.ROTTEN_FLESH), 1, 3.0, true, Weight.of(1, 1), Weight.of(2, 2), Weight.of(4, 1))
            .item(() -> new ItemStack(Material.STRING), 1, 3.0, true, Weight.of(1, 1), Weight.of(2, 2), Weight.of(4, 1))
            .item(() -> new ItemStack(Material.GUNPOWDER), 2, 2.0, true, Weight.of(1, 1), Weight.of(2, 2), Weight.of(4, 1))
            .item(() -> new ItemStack(Material.FEATHER), 1, 1.5, Weight.of(1, 1), Weight.of(2, 2), Weight.of(4, 1))
            .item(() -> new ItemStack(Material.LEATHER), 1, 0.5, Weight.of(1, 1), Weight.of(2, 2), Weight.of(4, 1))

            .item(() -> new ItemStack(Material.COCOA), 2, 2.5, Weight.of(1, 1), Weight.of(2, 2), Weight.of(4, 1))
            .item(() -> new ItemStack(Material.SUGAR_CANE), 2, 2.5, Weight.of(2, 1), Weight.of(4, 2), Weight.of(6, 1))
            .item(() -> new ItemStack(Material.MELON_SLICE), 2, 1.5, Weight.of(1, 1), Weight.of(2, 2), Weight.of(4, 1))
            .item(() -> new ItemStack(Material.MELON_SEEDS), 2, 1.5, Weight.of(1, 2), Weight.of(2, 1))
            .item(() -> new ItemStack(Material.PORKCHOP), 2, 1.5, Weight.of(1, 1), Weight.of(2, 2), Weight.of(4, 1))
            .item(() -> new ItemStack(Material.CHICKEN), 2, 1.5, Weight.of(1, 1), Weight.of(2, 2), Weight.of(4, 1))
            .item(() -> new ItemStack(Material.BEEF), 2, 0.5, Weight.of(1, 1), Weight.of(2, 2), Weight.of(4, 1))

            .item(() -> new ItemStack(Material.COAL), 2, 2.75, Weight.of(1, 4), Weight.of(2, 2), Weight.of(4, 1))
            .item(() -> new ItemStack(Material.IRON_INGOT), 3, 2.5, Weight.of(1, 4), Weight.of(2, 1))
            .item(() -> new ItemStack(Material.GOLD_INGOT), 4, 2.25, Weight.of(1, 4), Weight.of(2, 1))

            .item(() -> new ItemStack(Material.FERN), 1, 2.5, true)
            .item(() -> new ItemStack(Material.LARGE_FERN), 1, 1.5, true)
            .item(() -> new ItemStack(Material.RED_MUSHROOM), 1, 1.5, true)
            .item(() -> new ItemStack(Material.BROWN_MUSHROOM), 1, 1.5, true)
            .item(() -> new ItemStack(Material.CAVE_VINES), 1, 1.5, true)
            .item(() -> new ItemStack(Material.LILY_PAD), 1, 1.5, true)
            .item(() -> new ItemStack(Material.MOSS_CARPET), 1, 1.5, true)
            .item(() -> new ItemStack(Material.DANDELION), 1, 1.5, true)
            .item(() -> new ItemStack(Material.POPPY), 1, 1.5, true)
            .item(() -> new ItemStack(Material.BLUE_ORCHID), 1, 1.5, true)

            .item(() -> new ItemStack(Material.EXPERIENCE_BOTTLE), 3, 2.5, Weight.of(4, 4), Weight.of(8, 2), Weight.of(12, 1))
            .item(() -> new ItemStack(Material.BOOK), 2, 2.5, Weight.of(1, 1), Weight.of(2, 2), Weight.of(4, 2), Weight.of(8, 1))
            .item(new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.DURABILITY, Weight.of(1, 2), Weight.of(2, 1)).asStackSupplier(), 4, 0.5)
            .item(new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, Weight.of(1, 2), Weight.of(2, 1)).asStackSupplier(), 4, 0.5)
            .item(new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.THORNS, Weight.of(1, 2), Weight.of(2, 1)).asStackSupplier(), 4, 0.5)
            .item(new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.DAMAGE_ALL, Weight.of(1, 2), Weight.of(2, 1)).asStackSupplier(), 4, 0.5)
            .item(new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.DIG_SPEED, Weight.of(1, 2), Weight.of(2, 1)).asStackSupplier(), 4, 0.5)

            .item(() -> new ItemStack(Material.MUSIC_DISC_MELLOHI), 4, 0.125, true)

            .build();

    @NotNull
    public static final LootDigest WITCH_LOOT = LootDigest.builder("BOGGED_CLOUD_FOREST_WITCH", Weight.of(3), Weight.of(6))
            .item(() -> new ItemStack(Material.NETHER_WART), 2, 5.0, Weight.of(1, 4), Weight.of(2, 2), Weight.of(4, 1))
            .item(() -> new ItemStack(Material.BLAZE_POWDER), 3, 5.0, Weight.of(1, 4), Weight.of(2, 2), Weight.of(4, 1))
            .item(() -> new ItemStack(Material.FERMENTED_SPIDER_EYE), 3, 1.0)
            .item(() -> new ItemStack(Material.GOLDEN_CARROT), 3, 1.0)
            .item(() -> new ItemStack(Material.GLISTERING_MELON_SLICE), 3, 1.0)

            .item(Util.potion(PotionType.WATER, false, false), 2, 7.5)
            .item(Util.potion(PotionType.AWKWARD, false, false), 3, 2.5)
            .item(Util.potion(PotionType.INSTANT_HEAL, false, false), 4, 0.5)
            .item(Util.splashPotion(PotionType.INSTANT_DAMAGE, false, false), 4, 0.5)
            .item(Util.potion(PotionType.SPEED, false, false), 4, 0.5)
            .item(Util.splashPotion(PotionType.POISON, false, false), 4, 0.5)
            .item(Util.splashPotion(PotionType.WEAKNESS, false, false), 4, 0.5)

            .build();

    @NotNull
    public static final LootDigest REWARDS = LootDigest.builder("CAMBRIAN_ESTUARY_REWARDS", Weight.of(0))
            .item(() -> new ItemStack(Material.BREWING_STAND))

            .chain(LOOT, Weight.of(5))
            .chain(WITCH_LOOT, Weight.of(5))

            .build();

    @NotNull
    public static final Phase PHASE = Phase.builder().name(NAME).displayName(DISPLAY_NAME).textColor(TEXT_COLOR).color(BAR_COLOR).blocks(BLOCKS)
            .boss(BOSS)
            .bossName(BOSS_NAME)
            .rewards(REWARDS)
            .result((e, c) -> Util.spawnChest(CHEST_DISPLAY_NAME, LOOT, c).apply(e, c), Weight.of(4, 0.2))
            .result((e, c) -> Util.spawnChest(WITCH_CHEST_DISPLAY_NAME, WITCH_LOOT, c).apply(e, c), Weight.of(4, 0.1))

            .result(Util.defaultData(Material.GRASS_BLOCK), Weight.of(1, 6.0))
            .result(Util.defaultData(Material.PODZOL), Weight.of(1, 4.0))
            .result(Util.defaultData(Material.MOSSY_COBBLESTONE), Weight.of(1, 2.0))
            .result(Util.defaultData(Material.MUD), Weight.of(1, 6.0))
            .result(Util.defaultData(Material.CLAY), Weight.of(1, 4.0))
            .result(Util.defaultData(Material.MYCELIUM), Weight.of(1, 1.0))
            .result(Util.defaultData(Material.SOUL_SAND), Weight.of(1, 0.4))
            .result(Util.defaultData(Material.JUNGLE_LOG), Weight.of(1, 1.2))
            .result(Util.defaultData(Material.MANGROVE_LOG), Weight.of(1, 1.2))
            .result(Util.defaultData(Material.MANGROVE_ROOTS), Weight.of(1, 0.4))
            .result(Util.waterlogged(Material.MANGROVE_ROOTS), Weight.of(1, 0.4))
            .result(Util.defaultData(Material.MUDDY_MANGROVE_ROOTS), Weight.of(1, 0.4))
            .result(Util.defaultData(Material.COAL_ORE), Weight.of(1, 0.8))
            .result(Util.defaultData(Material.IRON_ORE), Weight.of(1, 0.7))
            .result(Util.defaultData(Material.GOLD_ORE), Weight.of(1, 0.6))
            .result(Util.defaultData(Material.MELON), Weight.of(1, 0.4))
            .result(Util.defaultData(Material.PEARLESCENT_FROGLIGHT), Weight.of(1, 0.2))
            .result(Util.defaultData(Material.OCHRE_FROGLIGHT), Weight.of(1, 0.2))

            .result(EntityUtil.spawnEntity(EntityType.PIG), Weight.of(1, 0.05))
            .result(EntityUtil.spawnEntity(EntityType.MUSHROOM_COW), Weight.of(4, 0.015))
            .result(EntityUtil.spawnEntity(EntityType.TADPOLE), Weight.of(1, 0.03))
            .result(EntityUtil.spawnFrog(Frog.Variant.WARM), Weight.of(1, 0.03))
            .result(EntityUtil.spawnFrog(Frog.Variant.TEMPERATE), Weight.of(1, 0.03))
            .result(EntityUtil.spawnEntity(EntityType.OCELOT), Weight.of(3, 0.05))
            .result(EntityUtil.spawnEntity(EntityType.PARROT), Weight.of(2, 0.04))
            .result(EntityUtil.spawnEntity(EntityType.PANDA), Weight.of(3, 0.04))
            .result(EntityUtil.spawnBigSlime(), Weight.of(1, 0.2))
            .result(EntityUtil.spawnDrowned(), Weight.of(1, 0.17))
            .result(EntityUtil.spawnBabyDrowned(), Weight.of(2, 0.03))
            .result(EntityUtil.spawnEntity(EntityType.SPIDER), Weight.of(1, 0.2))
            .result(EntityUtil.spawnCreeper(), Weight.of(2, 0.12))
            .result(EntityUtil.spawnEntity(EntityType.WITCH), Weight.of(3, 0.12))
            .build();
}
