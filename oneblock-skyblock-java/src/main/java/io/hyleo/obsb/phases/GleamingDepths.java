package io.hyleo.obsb.phases;

import io.hyleo.obsb.api.LootDigest;
import io.hyleo.obsb.api.Phase;
import io.hyleo.obsb.api.Weight;
import io.hyleo.obsb.util.EntityUtil;
import io.hyleo.obsb.util.ItemBuilder;
import io.hyleo.obsb.util.Util;
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
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Silverfish;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.function.BiFunction;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class GleamingDepths {

    @NotNull
    public static final String NAME = "Gleaming_Depths";

    public static final int BLOCKS = 1500;
    @NotNull
    public static final String DISPLAY_NAME = "Gleaming Depths";

    @NotNull
    public static final TextColor TEXT_COLOR = Objects.requireNonNull(TextColor.fromHexString("#770737"));

    @NotNull
    public static final BossBar.Color BAR_COLOR = BossBar.Color.YELLOW;
    @NotNull
    public static final Component CHEST_DISPLAY_NAME = Component.text("Gleaming Chest").color(NamedTextColor.DARK_GRAY);

    @NotNull
    public static final Component BOSS_NAME = Component.text("Silverfish Swarm");

    @NotNull
    public static final BiFunction<@NotNull BlockBreakEvent, @NotNull Player, @NotNull Collection<@NotNull LivingEntity>> BOSS = (e, t) -> {
        val entities = new ArrayList<@NotNull LivingEntity>();

        for (int i = 0; i < 12; i++) {
            val spawnLocation = e.getBlock().getLocation().add(0, 2, 0);
            val boss = (Silverfish) e.getBlock().getWorld().spawnEntity(spawnLocation, EntityType.SILVERFISH);
            val maxHealth = boss.getAttribute(Attribute.GENERIC_MAX_HEALTH);
            val movementSpeed = boss.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED);

            assert maxHealth != null;
            assert movementSpeed != null;

            maxHealth.setBaseValue(16);
            boss.setHealth(16);

            movementSpeed.setBaseValue(0.375);

            entities.add(boss);
        }

        return entities;
    };

    @NotNull
    public static final LootDigest LOOT = LootDigest.builder("GLEAMING_DEPTHS", Weight.of(6), Weight.of(9), Weight.of(12))
            .item(() -> new ItemStack(Material.TNT), 1, 7.5, true, Weight.of(1, 1), Weight.of(2, 2), Weight.of(4, 1))

            .item(() -> new ItemStack(Material.ROTTEN_FLESH), 1, 3.0, true, Weight.of(1, 1), Weight.of(2, 2), Weight.of(4, 1))
            .item(() -> new ItemStack(Material.BONE), 1, 3.0, true, Weight.of(1, 1), Weight.of(2, 2), Weight.of(4, 1))
            .item(() -> new ItemStack(Material.STRING), 1, 3.0, true, Weight.of(1, 1), Weight.of(2, 2), Weight.of(4, 1))
            .item(() -> new ItemStack(Material.SPIDER_EYE), 1, 3.0, true, Weight.of(1, 1), Weight.of(2, 2), Weight.of(4, 1))
            .item(() -> new ItemStack(Material.GUNPOWDER), 2, 3.0, true, Weight.of(1, 1), Weight.of(2, 2), Weight.of(4, 1))

            .item(() -> new ItemStack(Material.WHEAT), 2, 4.0, Weight.of(2, 1), Weight.of(4, 2), Weight.of(8, 1))
            .item(() -> new ItemStack(Material.BREAD), 2, 2.0, Weight.of(1, 1), Weight.of(2, 2), Weight.of(4, 1))
            .item(() -> new ItemStack(Material.GLOW_BERRIES), 2, 3.0, Weight.of(1, 1), Weight.of(2, 2), Weight.of(4, 1))
            .item(() -> new ItemStack(Material.GOLDEN_APPLE), 4, 1.5, Weight.of(1, 4), Weight.of(2, 1))

            .item(() -> new ItemStack(Material.COAL), 2, 2.0, Weight.of(1, 1), Weight.of(2, 2), Weight.of(4, 1))
            .item(() -> new ItemStack(Material.IRON_INGOT), 3, 1.75, Weight.of(1, 1), Weight.of(2, 2), Weight.of(4, 1))
            .item(() -> new ItemStack(Material.COPPER_INGOT), 2, 1.5, Weight.of(1, 1), Weight.of(2, 2), Weight.of(4, 1))
            .item(() -> new ItemStack(Material.GOLD_INGOT), 4, 1.25, Weight.of(1, 1), Weight.of(2, 1))
            .item(() -> new ItemStack(Material.REDSTONE), 4, 1.25, Weight.of(4, 1), Weight.of(8, 2), Weight.of(12, 1))
            .item(() -> new ItemStack(Material.LAPIS_LAZULI), 4, 1.25, Weight.of(4, 1), Weight.of(8, 2), Weight.of(12, 2), Weight.of(16, 1))
            .item(() -> new ItemStack(Material.DIAMOND), 5, 1.0, Weight.of(1, 4), Weight.of(2, 1))
            .item(() -> new ItemStack(Material.AMETHYST_SHARD), 3, 1.5, Weight.of(1, 4), Weight.of(2, 2), Weight.of(4, 1))
            .item(() -> new ItemStack(Material.NAME_TAG), 3, 1.0)

            .item(() -> new ItemStack(Material.TORCH), 1, 5.0, Weight.of(2, 1), Weight.of(4, 2), Weight.of(8, 1))
            .item(() -> new ItemStack(Material.GLOW_LICHEN), 1, 2.5, true)
            .item(() -> new ItemStack(Material.POINTED_DRIPSTONE), 1, 2.5, true)
            .item(() -> new ItemStack(Material.RAIL), 1, 4.0, true, Weight.of(1, 4), Weight.of(2, 2), Weight.of(4, 1))
            .item(() -> new ItemStack(Material.POWERED_RAIL), 2, 2.0, true, Weight.of(1, 2), Weight.of(2, 1))
            .item(() -> new ItemStack(Material.ACTIVATOR_RAIL), 2, 1.0, true, Weight.of(1, 2), Weight.of(2, 1))
            .item(() -> new ItemStack(Material.DETECTOR_RAIL), 2, 1.0, true, Weight.of(1, 2), Weight.of(2, 1))

            .item(() -> new ItemStack(Material.IRON_PICKAXE), 4, 1.0)
            .item(() -> new ItemStack(Material.IRON_AXE), 4, 1.0)
            .item(() -> new ItemStack(Material.DIAMOND_PICKAXE), 6, 0.25)
            .item(() -> new ItemStack(Material.DIAMOND_AXE), 6, 0.25)
            .item(() -> new ItemStack(Material.IRON_SWORD), 4, 1.0)
            .item(() -> new ItemStack(Material.DIAMOND_SWORD), 6, 0.15)
            .item(() -> new ItemStack(Material.BOW), 2, 2.0)
            .item(() -> new ItemStack(Material.ARROW), 1, 10.0, Weight.of(1, 1), Weight.of(2, 2), Weight.of(4, 2), Weight.of(8, 1))

            .item(() -> new ItemStack(Material.IRON_HELMET), 4, 0.5)
            .item(() -> new ItemStack(Material.IRON_CHESTPLATE), 4, 0.5)
            .item(() -> new ItemStack(Material.IRON_LEGGINGS), 4, 0.5)
            .item(() -> new ItemStack(Material.IRON_BOOTS), 4, 0.5)
            .item(() -> new ItemStack(Material.DIAMOND_HELMET), 6, 0.2)
            .item(() -> new ItemStack(Material.DIAMOND_CHESTPLATE), 6, 0.2)
            .item(() -> new ItemStack(Material.DIAMOND_LEGGINGS), 6, 0.2)
            .item(() -> new ItemStack(Material.DIAMOND_BOOTS), 6, 0.2)

            .item(() -> new ItemStack(Material.EXPERIENCE_BOTTLE), 3, 2.5, Weight.of(4, 4), Weight.of(8, 2), Weight.of(12, 2), Weight.of(16, 1))
            .item(() -> new ItemStack(Material.BOOK), 2, 2.5, Weight.of(1, 1), Weight.of(2, 2), Weight.of(4, 2), Weight.of(8, 1))
            .item(new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.DURABILITY, Weight.of(1, 2), Weight.of(2, 1)).asStackSupplier(), 4, 0.25)
            .item(new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.PROTECTION_EXPLOSIONS, Weight.of(1, 2), Weight.of(2, 1)).asStackSupplier(), 4, 0.25)
            .item(new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, Weight.of(1, 4), Weight.of(2, 2), Weight.of(3, 1)).asStackSupplier(), 4, 0.25)
            .item(new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.DAMAGE_ARTHROPODS, Weight.of(1, 2), Weight.of(2, 1)).asStackSupplier(), 4, 0.25)
            .item(new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.LOOT_BONUS_MOBS, Weight.of(1, 2), Weight.of(2, 1)).asStackSupplier(), 4, 0.25)
            .item(new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.DAMAGE_ALL, Weight.of(1, 4), Weight.of(2, 2), Weight.of(3, 1)).asStackSupplier(), 4, 0.25)
            .item(new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.DAMAGE_UNDEAD, Weight.of(1, 2), Weight.of(2, 1)).asStackSupplier(), 4, 0.25)
            .item(new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.DIG_SPEED, Weight.of(1, 4), Weight.of(2, 2), Weight.of(3, 1)).asStackSupplier(), 4, 0.25)
            .item(new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.LOOT_BONUS_BLOCKS, Weight.of(1, 2), Weight.of(2, 1)).asStackSupplier(), 4, 0.25)

            .item(() -> new ItemStack(Material.MUSIC_DISC_STAL), 6, 0.075, true)

            .build();

    @NotNull
    public static final LootDigest REWARDS = LootDigest.builder("GLEAMING_DEPTHS_REWARDS", Weight.of(0))
            .item(() -> new ItemStack(Material.ENCHANTING_TABLE))

            .chain(LOOT, Weight.of(10))

            .build();

    @NotNull
    public static final Phase PHASE = Phase.builder().name(NAME).displayName(DISPLAY_NAME).textColor(TEXT_COLOR).color(BAR_COLOR).blocks(BLOCKS)
            .boss(BOSS)
            .bossName(BOSS_NAME)
            .rewards(REWARDS)
            .result((e, c) -> Util.spawnChest(CHEST_DISPLAY_NAME, LOOT, c).apply(e, c), Weight.of(4, 0.35))

            .result(Util.defaultData(Material.STONE), Weight.of(1, 10.0))
            .result(Util.defaultData(Material.DEEPSLATE), Weight.of(1, 10.0))
            .result(Util.defaultData(Material.TUFF), Weight.of(2, (5d / 3)))
            .result(Util.defaultData(Material.GRANITE), Weight.of(1, (5d / 3)))
            .result(Util.defaultData(Material.ANDESITE), Weight.of(1, (5d / 3)))
            .result(Util.defaultData(Material.DIORITE), Weight.of(1, (5d / 3)))
            .result(Util.defaultData(Material.COAL_ORE), Weight.of(1, 0.5))
            .result(Util.defaultData(Material.DEEPSLATE_COAL_ORE), Weight.of(2, 0.5))
            .result(Util.defaultData(Material.IRON_ORE), Weight.of(2, (5d / 12)))
            .result(Util.defaultData(Material.DEEPSLATE_IRON_ORE), Weight.of(3, (5d / 12)))
            .result(Util.defaultData(Material.COPPER_ORE), Weight.of(1, (1d / 3)))
            .result(Util.defaultData(Material.DEEPSLATE_COPPER_ORE), Weight.of(2, (1d / 3)))
            .result(Util.defaultData(Material.GOLD_ORE), Weight.of(3, 0.25))
            .result(Util.defaultData(Material.DEEPSLATE_GOLD_ORE), Weight.of(4, 0.25))
            .result(Util.defaultData(Material.REDSTONE_ORE), Weight.of(3, 0.25))
            .result(Util.defaultData(Material.DEEPSLATE_REDSTONE_ORE), Weight.of(4, 0.25))
            .result(Util.defaultData(Material.LAPIS_ORE), Weight.of(3, 0.25))
            .result(Util.defaultData(Material.DEEPSLATE_LAPIS_ORE), Weight.of(4, 0.25))
            .result(Util.defaultData(Material.DIAMOND_ORE), Weight.of(4, (1d / 6)))
            .result(Util.defaultData(Material.DEEPSLATE_DIAMOND_ORE), Weight.of(5, (1d / 6)))
            .result(Util.defaultData(Material.AMETHYST_BLOCK), Weight.of(2, (1d / 3)))
            .result(Util.defaultData(Material.CALCITE), Weight.of(2, (1d / 3)))
            .result(Util.defaultData(Material.DRIPSTONE_BLOCK), Weight.of(2, (1d / 3)))
            .result(Util.defaultData(Material.OBSIDIAN), Weight.of(3, (2d / 3)))
            .result(Util.defaultData(Material.TNT), Weight.of(3, (2d / 3)))

            .result(EntityUtil.spawnEntity(EntityType.BAT), Weight.of(1, (1d / 3)))
            .result(EntityUtil.spawnEntity(EntityType.GLOW_SQUID), Weight.of(3, (1d / 30)))
            .result(EntityUtil.spawnZombie(), Weight.of(2, (1d / 12)))
            .result(EntityUtil.spawnUpgradedZombie1(), Weight.of(2, (1d / 12)))
            .result(EntityUtil.spawnUpgradedZombie2(), Weight.of(2, (1d / 12)))
            .result(EntityUtil.spawnSkeleton(), Weight.of(2, (1d / 12)))
            .result(EntityUtil.spawnUpgradedSkeleton1(), Weight.of(2, (1d / 12)))
            .result(EntityUtil.spawnUpgradedSkeleton2(), Weight.of(2, (1d / 12)))
            .result(EntityUtil.spawnEntity(EntityType.CAVE_SPIDER), Weight.of(3, (1d / 6)))
            .result(EntityUtil.spawnCreeper(), Weight.of(3, (1d / 6)))
            .result(EntityUtil.spawnSpiderJockey(), Weight.of(4, (1d / 12)))
            .result(EntityUtil.spawnChickenJockey(), Weight.of(4, (1d / 12)))
            .build();
}
