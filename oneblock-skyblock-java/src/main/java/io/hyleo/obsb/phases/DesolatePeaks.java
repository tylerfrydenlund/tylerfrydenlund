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
import org.bukkit.entity.*;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DesolatePeaks {
    @NotNull
    public static final String NAME = "Desolate_Peaks";

    public static final int BLOCKS = 1450;

    @NotNull
    public static final String DISPLAY_NAME = "Desolate Peaks";

    @NotNull
    public static final TextColor TEXT_COLOR = Objects.requireNonNull(TextColor.fromHexString("#FFFDD0"));

    @NotNull
    public static final Component BOSS_NAME = Component.text("Vagrant Raid Party");

    @NotNull
    public static final BossBar.Color BAR_COLOR = BossBar.Color.WHITE;

    @NotNull
    public static final Component CHEST_DISPLAY_NAME = Component.text("Peaks Chest").color(NamedTextColor.DARK_GRAY);

    @NotNull
    public static final BiFunction<@NotNull BlockBreakEvent, @NotNull Player, @NotNull Collection<@NotNull LivingEntity>> BOSS = (e, t) -> {
        val spawnLocation = e.getBlock().getLocation().add(0, 2, 0);
        val ravager = (Ravager) e.getBlock().getWorld().spawnEntity(spawnLocation, EntityType.RAVAGER);

        val maxHealth = ravager.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        val attackDamage = ravager.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE);
        val movementSpeed = ravager.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED);

        assert maxHealth != null;
        assert attackDamage != null;
        assert movementSpeed != null;

        maxHealth.setBaseValue(75);
        ravager.setHealth(75);

        attackDamage.setBaseValue(18);
        movementSpeed.setBaseValue(0.5);

        ravager.setCanJoinRaid(false);

        val pillager = (Pillager) e.getBlock().getWorld().spawnEntity(e.getBlock().getLocation().add(0, 2, 0), EntityType.PILLAGER);

        pillager.setCanJoinRaid(false);
        ravager.addPassenger(pillager);

        val vindicator1 = (Vindicator) e.getBlock().getWorld().spawnEntity(e.getBlock().getLocation().add(1.5, 2, 1.5), EntityType.VINDICATOR);
        val vindicator2 = (Vindicator) e.getBlock().getWorld().spawnEntity(e.getBlock().getLocation().add(-1.5, 2, -1.5), EntityType.VINDICATOR);

        vindicator1.setCanJoinRaid(false);
        vindicator2.setCanJoinRaid(false);

        return List.of(ravager, pillager, vindicator1, vindicator2);
    };

    @NotNull
    public static final LootDigest LOOT = LootDigest.builder("DESOLATE_PEAKS", Weight.of(6), Weight.of(9), Weight.of(12))
            .item(() -> new ItemStack(Material.ROTTEN_FLESH), 1, 3.0, true, Weight.of(1, 1), Weight.of(2, 2), Weight.of(4, 1))
            .item(() -> new ItemStack(Material.BONE), 1, 3.0, true, Weight.of(1, 1), Weight.of(2, 2), Weight.of(4, 1))

            .item(() -> new ItemStack(Material.BEETROOT), 2, 2.5, Weight.of(1, 1), Weight.of(2, 2), Weight.of(4, 1))
            .item(() -> new ItemStack(Material.BEETROOT_SEEDS), 2, 1.0, Weight.of(1, 2), Weight.of(2, 1))
            .item(() -> new ItemStack(Material.BEETROOT_SOUP), 3, 1.0, Weight.of(1, 1))
            .item(() -> new ItemStack(Material.POTATO), 2, 2.5, Weight.of(1, 1), Weight.of(2, 2), Weight.of(4, 1))
            .item(() -> new ItemStack(Material.RABBIT), 2, 2.0, Weight.of(1, 1), Weight.of(2, 2), Weight.of(4, 1))
            .item(() -> new ItemStack(Material.COOKED_RABBIT), 3, 1.5, Weight.of(1, 1), Weight.of(2, 2), Weight.of(4, 1))
            .item(() -> new ItemStack(Material.RABBIT_HIDE), 2, 2.0, Weight.of(2, 1), Weight.of(4, 2), Weight.of(8, 1))
            .item(() -> new ItemStack(Material.RABBIT_FOOT), 4, 0.75, Weight.of(1, 1))
            .item(() -> new ItemStack(Material.RABBIT_STEW), 3, 1.0, Weight.of(1, 1))

            .item(() -> new ItemStack(Material.COAL), 2, 1.75, Weight.of(1, 1), Weight.of(2, 2), Weight.of(4, 1))
            .item(() -> new ItemStack(Material.IRON_INGOT), 3, 1.375, Weight.of(1, 1), Weight.of(2, 2), Weight.of(4, 1))
            .item(() -> new ItemStack(Material.DIAMOND), 5, 1.0, Weight.of(1, 4), Weight.of(2, 1), Weight.of(4, 1))
            .item(() -> new ItemStack(Material.EMERALD), 5, 1.0, Weight.of(1, 4), Weight.of(2, 2))
            .item(() -> new ItemStack(Material.NAME_TAG), 3, 0.5)

            .item(() -> new ItemStack(Material.TORCH), 1, 2.0, Weight.of(2, 1), Weight.of(4, 2), Weight.of(8, 1))
            .item(() -> new ItemStack(Material.DEAD_BUSH), 1, 4.0, true)
            .item(() -> new ItemStack(Material.WITHER_ROSE), 3, 1.0)
            .item(() -> new ItemStack(Material.TRIPWIRE_HOOK), 1, 1.5, true)
            .item(() -> new ItemStack(Material.BELL), 3, 1.0, true)

            .item(() -> new ItemStack(Material.IRON_PICKAXE), 4, 0.5)
            .item(() -> new ItemStack(Material.IRON_AXE), 4, 0.5)
            .item(() -> new ItemStack(Material.DIAMOND_PICKAXE), 6, 0.125)
            .item(() -> new ItemStack(Material.DIAMOND_AXE), 6, 0.125)
            .item(() -> new ItemStack(Material.IRON_SWORD), 4, 0.5)
            .item(() -> new ItemStack(Material.DIAMOND_SWORD), 6, 0.125)

            .item(new ItemBuilder(Material.BOW)
                    .addQuantity(0, 2.0).addQuantity(1, 1.0)
                    .addWeightedEnchant(Enchantment.ARROW_DAMAGE, Weight.of(1, 2.0), Weight.of(2, 1.0))
                    .addWeightedEnchant(Enchantment.ARROW_KNOCKBACK, Weight.of(1, 1.0))
                    .asStackSupplier(), 2, 0.5)
            .item(new ItemBuilder(Material.CROSSBOW)
                    .addQuantity(0, 2.0).addQuantity(1, 1.0)
                    .addWeightedEnchant(Enchantment.PIERCING, Weight.of(1, 2.0), Weight.of(2, 1.0))
                    .addWeightedEnchant(Enchantment.QUICK_CHARGE, Weight.of(1, 1.0))
                    .asStackSupplier(), 3, 0.5)
            .item(() -> new ItemStack(Material.ARROW), 1, 2.5, Weight.of(1, 1), Weight.of(2, 2), Weight.of(4, 2), Weight.of(8, 1))

            .item(() -> new ItemStack(Material.IRON_HELMET), 4, 0.2)
            .item(() -> new ItemStack(Material.IRON_CHESTPLATE), 4, 0.2)
            .item(() -> new ItemStack(Material.IRON_LEGGINGS), 4, 0.2)
            .item(() -> new ItemStack(Material.IRON_BOOTS), 4, 0.2)
            .item(() -> new ItemStack(Material.DIAMOND_HELMET), 6, 0.075)
            .item(() -> new ItemStack(Material.DIAMOND_CHESTPLATE), 6, 0.075)
            .item(() -> new ItemStack(Material.DIAMOND_LEGGINGS), 6, 0.075)
            .item(() -> new ItemStack(Material.DIAMOND_BOOTS), 6, 0.075)

            .item(() -> new ItemStack(Material.EXPERIENCE_BOTTLE), 3, 1.0, Weight.of(4, 4), Weight.of(8, 2), Weight.of(12, 2), Weight.of(16, 1))
            .item(() -> new ItemStack(Material.BOOK), 2, 0.5, Weight.of(1, 1), Weight.of(2, 2), Weight.of(4, 2), Weight.of(8, 1))
            .item(new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.DURABILITY, Weight.of(1, 2), Weight.of(2, 1)).asStackSupplier(), 4, 0.085)
            .item(new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.PROTECTION_FALL, Weight.of(1, 2), Weight.of(2, 1)).asStackSupplier(), 4, 0.085)
            .item(new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.PROTECTION_PROJECTILE, Weight.of(1, 2), Weight.of(2, 1)).asStackSupplier(), 4, 0.085)
            .item(new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, Weight.of(1, 4), Weight.of(2, 2), Weight.of(3, 1)).asStackSupplier(), 4, 0.085)
            .item(new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.KNOCKBACK, Weight.of(1, 2), Weight.of(2, 1)).asStackSupplier(), 4, 0.085)
            .item(new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.DAMAGE_ALL, Weight.of(1, 4), Weight.of(2, 2), Weight.of(3, 1)).asStackSupplier(), 4, 0.085)
            .item(new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.MULTISHOT, Weight.of(1, 1)).asStackSupplier(), 4, 0.085)
            .item(new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.PIERCING, Weight.of(1, 2), Weight.of(2, 1)).asStackSupplier(), 4, 0.085)
            .item(new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.ARROW_DAMAGE, Weight.of(1, 4), Weight.of(2, 2), Weight.of(3, 1)).asStackSupplier(), 4, 0.085)
            .item(new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.ARROW_KNOCKBACK, Weight.of(1, 2), Weight.of(2, 1)).asStackSupplier(), 4, 0.085)
            .item(new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.QUICK_CHARGE, Weight.of(1, 2), Weight.of(2, 1)).asStackSupplier(), 4, 0.085)
            .item(new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.DIG_SPEED, Weight.of(1, 4), Weight.of(2, 2), Weight.of(3, 1)).asStackSupplier(), 4, 0.085)
            .item(() -> new ItemStack(Material.MUSIC_DISC_FAR), 6, 0.03, true)

            .build();

    @NotNull
    public static LootDigest REWARDS = LootDigest.builder("DESOLATE_PEAKS_REWARDS", Weight.of(0))
            .item(() -> new ItemStack(Material.TOTEM_OF_UNDYING))

            .chain(LOOT, Weight.of(10))

            .build();

    @NotNull
    public static final Phase PHASE = Phase.builder().name(NAME).displayName(DISPLAY_NAME).textColor(TEXT_COLOR).color(BAR_COLOR).blocks(BLOCKS)
            .boss(BOSS)
            .bossName(BOSS_NAME)
            .rewards(REWARDS)
            .result((e, c) -> Util.spawnChest(CHEST_DISPLAY_NAME, LOOT, c).apply(e, c), Weight.of(4, 0.35))

            .result(Util.defaultData(Material.STONE), Weight.of(1, 8.0))
            .result(Util.defaultData(Material.DEEPSLATE), Weight.of(1, 2.0))
            .result(Util.defaultData(Material.INFESTED_STONE), Weight.of(2, 0.4))
            .result(Util.defaultData(Material.INFESTED_DEEPSLATE), Weight.of(1, 0.4))
            .result(Util.defaultData(Material.COAL_ORE), Weight.of(1, 0.72))
            .result(Util.defaultData(Material.DEEPSLATE_COAL_ORE), Weight.of(2, 0.36))
            .result(Util.defaultData(Material.IRON_ORE), Weight.of(2, 0.6))
            .result(Util.defaultData(Material.DEEPSLATE_IRON_ORE), Weight.of(3, 0.3))
            .result(Util.defaultData(Material.DIAMOND_ORE), Weight.of(4, 0.2))
            .result(Util.defaultData(Material.DEEPSLATE_DIAMOND_ORE), Weight.of(5, 0.1))
            .result(Util.defaultData(Material.EMERALD_ORE), Weight.of(4, 0.2))
            .result(Util.defaultData(Material.DEEPSLATE_EMERALD_ORE), Weight.of(5, 0.1))
            .result(Util.defaultData(Material.DIRT), Weight.of(1, 2.0))
            .result(Util.defaultData(Material.GRAVEL), Weight.of(1, 2.0))
            .result(Util.defaultData(Material.DIRT_PATH), Weight.of(1, 0.8))
            .result(Util.defaultData(Material.STONE_BRICKS), Weight.of(2, 0.8))
            .result(Util.defaultData(Material.CRACKED_STONE_BRICKS), Weight.of(3, 0.8))
            .result(Util.defaultData(Material.CHISELED_STONE_BRICKS), Weight.of(4, 0.8))
            .result(Util.defaultData(Material.INFESTED_STONE_BRICKS), Weight.of(3, 0.2))
            .result(Util.defaultData(Material.INFESTED_CRACKED_STONE_BRICKS), Weight.of(4, 0.2))
            .result(Util.defaultData(Material.INFESTED_CHISELED_STONE_BRICKS), Weight.of(3, 0.2))
            .result(Util.defaultData(Material.ICE), Weight.of(4, 2.0))
            .result(Util.defaultData(Material.PACKED_ICE), Weight.of(4, 1.4))
            .result(Util.defaultData(Material.BLUE_ICE), Weight.of(5, 0.8))
            .result(Util.defaultData(Material.SNOW_BLOCK), Weight.of(2, 8.0))

            .result(EntityUtil.spawnEntity(EntityType.GOAT), Weight.of(2, 0.06))
            .result(EntityUtil.spawnEntity(EntityType.RABBIT), Weight.of(1, 0.12))
            .result(EntityUtil.spawnEntity(EntityType.WOLF), Weight.of(2, 0.06))
            .result(EntityUtil.spawnEntity(EntityType.POLAR_BEAR), Weight.of(3, 0.04))
            .result(EntityUtil.spawnHusk(), Weight.of(2, 0.08))
            .result(EntityUtil.spawnUpgradedHusk1(), Weight.of(3, 0.08))
            .result(EntityUtil.spawnStray(), Weight.of(2, 0.08))
            .result(EntityUtil.spawnUpgradedStray1(), Weight.of(3, 0.08))
            .result(EntityUtil.spawnEntity(EntityType.VILLAGER), Weight.of(3, 0.08))
            .result(EntityUtil.spawnEntity(EntityType.IRON_GOLEM), Weight.of(4, 0.04))
            .result(EntityUtil.spawnEntity(EntityType.SNOWMAN), Weight.of(4, 0.02))
            .result(EntityUtil.spawnSnowman(), Weight.of(4, 0.02))
            .result(EntityUtil.spawnEntity(EntityType.PILLAGER), Weight.of(3, 0.08))
            .result(EntityUtil.spawnUpgradedPillager(), Weight.of(4, 0.08))
            .result(EntityUtil.spawnEntity(EntityType.VINDICATOR), Weight.of(3, 0.08))
            .result(EntityUtil.spawnUpgradedVindicator(), Weight.of(4, 0.08))
            .result(EntityUtil.spawnEntity(EntityType.EVOKER), Weight.of(4, 0.04))
            .build();
}
