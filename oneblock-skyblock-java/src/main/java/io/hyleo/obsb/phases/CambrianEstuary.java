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
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Guardian;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;


@UtilityClass
@FieldDefaults(makeFinal = true, level = AccessLevel.PUBLIC)
public class CambrianEstuary {
    @NotNull
    public static final  String NAME = "Cambrian_Estuary";

    public static final int BLOCKS = 1200;
    @NotNull
    public static final  String DISPLAY_NAME = "Cambrian Estuary";

    @NotNull
    public static final  TextColor TEXT_COLOR = Objects.requireNonNull(TextColor.fromHexString("#088F8F"));
    @NotNull
    BossBar.Color BAR_COLOR = BossBar.Color.BLUE;

    @NotNull
    public static final Component CHEST_DISPLAY_NAME = Component.text("Estuary Chest").color(NamedTextColor.DARK_GRAY);

    @NotNull
    public static final Component BURIED_CHEST_DISPLAY_NAME = Component.text("Buried Chest").color(NamedTextColor.DARK_GRAY);

    @NotNull
    public static final  Component BOSS_NAME = Component.text("Counter-Guardian General");
    @NotNull
    public static final  BiFunction<BlockBreakEvent, Player, Collection<LivingEntity>> BOSS = (e, t) -> {
        val spawnLocation = e.getBlock().getLocation().add(0, 2, 0);
        val boss = (Guardian) e.getBlock().getWorld().spawnEntity(spawnLocation, EntityType.ELDER_GUARDIAN);

        val maxHealth = boss.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        val attackDamage = boss.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE);

        assert maxHealth != null;
        assert attackDamage != null;

        maxHealth.setBaseValue(120);
        attackDamage.setBaseValue(12);
        boss.setHealth(120);

        return List.of(boss);
    };

    @NotNull
    public static final LootDigest LOOT = LootDigest.builder("CAMBRIAN_ESTUARY", Weight.of(3), Weight.of(6), Weight.of(9))
            .item(() -> new ItemStack(Material.TUBE_CORAL), 1, 2.0, true)
            .item(() -> new ItemStack(Material.BRAIN_CORAL), 1, 2.0, true)
            .item(() -> new ItemStack(Material.BUBBLE_CORAL), 1, 2.0, true)
            .item(() -> new ItemStack(Material.FIRE_CORAL), 1, 2.0, true)
            .item(() -> new ItemStack(Material.HORN_CORAL), 1, 2.0, true)
            .item(() -> new ItemStack(Material.TUBE_CORAL_FAN), 1, 2.0, true)
            .item(() -> new ItemStack(Material.BRAIN_CORAL_FAN), 1, 2.0, true)
            .item(() -> new ItemStack(Material.BUBBLE_CORAL_FAN), 1, 2.0, true)
            .item(() -> new ItemStack(Material.FIRE_CORAL_FAN), 1, 2.0, true)
            .item(() -> new ItemStack(Material.HORN_CORAL_FAN), 1, 2.0, true)
            .item(() -> new ItemStack(Material.SEAGRASS), 1, 4.0, true)
            .item(() -> new ItemStack(Material.KELP), 1, 4.0, true)

            .item(() -> new ItemStack(Material.ROTTEN_FLESH), 1, 5.0, true, Weight.of(1, 1), Weight.of(2, 2), Weight.of(4, 1))
            .item(() -> new ItemStack(Material.PRISMARINE_SHARD), 2, 3.0, Weight.of(1, 1), Weight.of(2, 2), Weight.of(4, 2), Weight.of(8, 1))
            .item(() -> new ItemStack(Material.PRISMARINE_CRYSTALS), 3, 1.0, true, Weight.of(1, 4), Weight.of(2, 2), Weight.of(4, 1))
            .item(() -> new ItemStack(Material.SCUTE), 3, 2.0, true, Weight.of(1, 4), Weight.of(2, 1))
            .item(() -> new ItemStack(Material.NAUTILUS_SHELL), 4, 2.0, true, Weight.of(1, 4), Weight.of(2, 1))

            .item(() -> new ItemStack(Material.COD_BUCKET), 2, 1.0)
            .item(() -> new ItemStack(Material.SALMON_BUCKET), 2, 1.0)
            .item(() -> new ItemStack(Material.TROPICAL_FISH_BUCKET), 2, 1.5)
            .item(() -> new ItemStack(Material.PUFFERFISH_BUCKET), 2, 0.5)

            .item(() -> new ItemStack(Material.TRIDENT), 3, 1.0)
            .item(() -> new ItemStack(Material.MUSIC_DISC_STRAD), 3, 0.075, true)

            .build();
    @NotNull
    public static final   LootDigest BURIED_LOOT = LootDigest.builder("BURIED_CAMBRIAN_ESTUARY", Weight.of(6), Weight.of(9), Weight.of(12))
            .item(() -> new ItemStack(Material.COAL), 2, 10.0, Weight.of(1, 4), Weight.of(2, 2), Weight.of(4, 1))
            .item(() -> new ItemStack(Material.IRON_NUGGET), 3, 8.0, Weight.of(4, 1), Weight.of(8, 2), Weight.of(12, 2), Weight.of(16, 1))
            .item(() -> new ItemStack(Material.IRON_INGOT), 3, 6.0, Weight.of(1, 4), Weight.of(2, 2), Weight.of(4, 1))
            .item(() -> new ItemStack(Material.GOLD_NUGGET), 3, 4.0, Weight.of(4, 1), Weight.of(8, 2), Weight.of(12, 2), Weight.of(16, 1))
            .item(() -> new ItemStack(Material.GOLD_INGOT), 4, 2.0, Weight.of(1, 4), Weight.of(2, 2), Weight.of(4, 1))
            .item(() -> new ItemStack(Material.PRISMARINE_CRYSTALS), 3, 2.0, Weight.of(2, 4), Weight.of(4, 2), Weight.of(8, 1))

            .item(() -> new ItemStack(Material.COOKED_COD), 2, 2.5, Weight.of(1, 1), Weight.of(2, 2), Weight.of(4, 1))
            .item(() -> new ItemStack(Material.COOKED_SALMON), 2, 2.5, Weight.of(1, 1), Weight.of(2, 2), Weight.of(4, 1))
            .item(() -> new ItemStack(Material.GOLDEN_APPLE), 5, 1.0)

            .item(() -> new ItemStack(Material.FISHING_ROD), 5, 0.75)
            .item(() -> new ItemStack(Material.GOLDEN_HELMET), 5, 0.75)
            .item(() -> new ItemStack(Material.GOLDEN_CHESTPLATE), 5, 0.75)
            .item(() -> new ItemStack(Material.GOLDEN_LEGGINGS), 5, 0.75)
            .item(() -> new ItemStack(Material.GOLDEN_BOOTS), 5, 0.75)

            .item(() -> new ItemStack(Material.EXPERIENCE_BOTTLE), 3, 1.5, Weight.of(4, 4), Weight.of(8, 2), Weight.of(12, 1))
            .item(() -> new ItemStack(Material.BOOK), 2, 1.5, Weight.of(1, 1), Weight.of(2, 2), Weight.of(4, 2), Weight.of(8, 1))
            .item(new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.DURABILITY, Weight.of(1, 2)).asStackSupplier(), 4, 0.15)
            .item(new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.WATER_WORKER, Weight.of(1, 1)).asStackSupplier(), 4, 0.15)
            .item(new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.DEPTH_STRIDER, Weight.of(1, 2), Weight.of(2, 1)).asStackSupplier(), 4, 0.15)
            .item(new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.CHANNELING, Weight.of(1, 1)).asStackSupplier(), 4, 0.15)
            .item(new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.IMPALING, Weight.of(1, 4), Weight.of(2, 2), Weight.of(3, 1)).asStackSupplier(), 4, 0.15)
            .item(new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.LOYALTY, Weight.of(1, 2), Weight.of(2, 1)).asStackSupplier(), 4, 0.15)
            .item(new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.RIPTIDE, Weight.of(1, 2), Weight.of(2, 1)).asStackSupplier(), 4, 0.15)
            .item(new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.LUCK, Weight.of(1, 2), Weight.of(2, 1)).asStackSupplier(), 4, 0.15)
            .item(new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.LURE, Weight.of(1, 2), Weight.of(2, 1)).asStackSupplier(), 4, 0.15)
            .item(new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.DIG_SPEED, Weight.of(1, 2), Weight.of(2, 1)).asStackSupplier(), 4, 0.15)

            .build();
    @NotNull
    public static final   LootDigest REWARDS = LootDigest.builder("CAMBRIAN_ESTUARY_REWARDS", Weight.of(0))
            .item(() -> new ItemStack(Material.HEART_OF_THE_SEA))

            .chain(LOOT, Weight.of(3))
            .chain(BURIED_LOOT, Weight.of(5))

            .build();

    @NotNull
    public static final Phase PHASE = Phase.builder().name(NAME).displayName(DISPLAY_NAME).textColor(TEXT_COLOR).blocks(BLOCKS)
            .boss(BOSS)
            .bossName(BOSS_NAME)
            .color(BAR_COLOR)
            .rewards(REWARDS)
            .result((e, c) -> Util.spawnChest(CHEST_DISPLAY_NAME, LOOT, c).apply(e, c), Weight.of(5, 0.25))
            .result((e, c) -> Util.spawnChest(BURIED_CHEST_DISPLAY_NAME, BURIED_LOOT, c).apply(e, c), Weight.of(5, 0.2))
            .result(Util.defaultData(Material.SAND), Weight.of(1, 12.0))
            .result(Util.defaultData(Material.SANDSTONE), Weight.of(1, 6.0))
            .result(Util.defaultData(Material.GRAVEL), Weight.of(1, 2.0))
            .result(Util.defaultData(Material.CLAY), Weight.of(1, 2.0))
            .result(Util.defaultData(Material.WET_SPONGE), Weight.of(3, 0.4))
            .result(Util.defaultData(Material.TUBE_CORAL_BLOCK), Weight.of(1, 0.6))
            .result(Util.defaultData(Material.BRAIN_CORAL_BLOCK), Weight.of(1, 0.6))
            .result(Util.defaultData(Material.BUBBLE_CORAL_BLOCK), Weight.of(1, 0.6))
            .result(Util.defaultData(Material.FIRE_CORAL_BLOCK), Weight.of(1, 0.6))
            .result(Util.defaultData(Material.HORN_CORAL_BLOCK), Weight.of(1, 0.6))
            .result(Util.defaultData(Material.PRISMARINE), Weight.of(2, 1.2))
            .result(Util.defaultData(Material.PRISMARINE_BRICKS), Weight.of(2, 1.2))
            .result(Util.defaultData(Material.DARK_PRISMARINE), Weight.of(3, 0.4))
            .result(Util.defaultData(Material.SEA_LANTERN), Weight.of(3, 0.4))
            .result(Util.defaultData(Material.MAGMA_BLOCK), Weight.of(2, 0.6))
            .result(Util.waterlogged(Material.SPRUCE_STAIRS), Weight.of(1, 0.2))
            .result(Util.waterlogged(Material.OAK_STAIRS), Weight.of(1, 0.2))
            .result(Util.waterlogged(Material.DARK_OAK_STAIRS), Weight.of(1, 0.2))
            .result(Util.waterlogged(Material.SPRUCE_FENCE), Weight.of(1, 0.2))
            .result(Util.waterlogged(Material.OAK_FENCE), Weight.of(1, 0.2))
            .result(Util.waterlogged(Material.DARK_OAK_FENCE), Weight.of(1, 0.2))
            .result(Util.waterlogged(Material.SPRUCE_SLAB), Weight.of(1, 0.2))
            .result(Util.waterlogged(Material.OAK_SLAB), Weight.of(1, 0.2))
            .result(Util.waterlogged(Material.DARK_OAK_SLAB), Weight.of(1, 0.2))

            .result(EntityUtil.spawnEntity(EntityType.COD), Weight.of(2, 0.08))
            .result(EntityUtil.spawnEntity(EntityType.SALMON), Weight.of(2, 0.08))
            .result(EntityUtil.spawnEntity(EntityType.PUFFERFISH), Weight.of(3, 0.06))
            .result(EntityUtil.spawnEntity(EntityType.TROPICAL_FISH), Weight.of(2, 0.1))
            .result(EntityUtil.spawnEntity(EntityType.SQUID), Weight.of(2, 0.08))
            .result(EntityUtil.spawnEntity(EntityType.TURTLE), Weight.of(3, 0.06))
            .result(EntityUtil.spawnEntity(EntityType.DOLPHIN), Weight.of(3, 0.06))
            .result(EntityUtil.spawnDrowned(), Weight.of(2, 0.34))
            .result(EntityUtil.spawnBabyDrowned(), Weight.of(2, 0.06))
            .result(EntityUtil.spawnEntity(EntityType.GUARDIAN), Weight.of(2, 0.2))
            .build();
}
