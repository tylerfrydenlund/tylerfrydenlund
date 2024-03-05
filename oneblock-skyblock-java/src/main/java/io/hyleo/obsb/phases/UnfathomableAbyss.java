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
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Warden;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionType;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UnfathomableAbyss {
    @NotNull
    public static final String NAME = "Unfathomable_Abyss";

    public static final int BLOCKS = 1900;

    @NotNull
    public static final String DISPLAY_NAME = "Unfathomable Abyss";

    @NotNull
    public static final TextColor TEXT_COLOR = Objects.requireNonNull(TextColor.fromHexString("#708090"));

    @NotNull
    public static final Component BOSS_NAME = Component.text("Warden");

    @NotNull
    public static final BossBar.Color BAR_COLOR = BossBar.Color.BLUE;

    @NotNull
    public static final Component CHEST_DISPLAY_NAME = Component.text("Unfathomable Chest").color(NamedTextColor.DARK_GRAY);

    @NotNull
    public static final BiFunction<@NotNull BlockBreakEvent, @NotNull Player, @NotNull Collection<@NotNull LivingEntity>> BOSS = (e, t) -> {
        val spawnLocation = e.getBlock().getLocation().add(0, 2, 0);
        val boss = (Warden) e.getBlock().getWorld().spawnEntity(spawnLocation, EntityType.WARDEN);

        val maxHealth = boss.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        val attackDamage = boss.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE);

        assert maxHealth != null;
        assert attackDamage != null;

        maxHealth.setBaseValue(500);
        boss.setHealth(500);
        attackDamage.setBaseValue(7.5);

        return List.of(boss);
    };

    @NotNull
    public static final LootDigest LOOT = LootDigest.builder("UNFATHOMABLE_ABYSS", Weight.of(10), Weight.of(12), Weight.of(14))
            .item(() -> new ItemStack(Material.SCULK), 1, 5.0, true, Weight.of(2, 1), Weight.of(4, 2), Weight.of(6, 1))
            .item(() -> new ItemStack(Material.SCULK_SENSOR), 2, 2.5, true, Weight.of(1, 2), Weight.of(2, 1))
            .item(() -> new ItemStack(Material.SCULK_CATALYST), 2, 2.5, true, Weight.of(1, 2), Weight.of(2, 1))
            .item(() -> new ItemStack(Material.ECHO_SHARD), 3, 1.25, Weight.of(1, 1), Weight.of(2, 2), Weight.of(4, 1))

            .item(() -> new ItemStack(Material.ROTTEN_FLESH), 1, 2.5, true, Weight.of(1, 1), Weight.of(2, 2), Weight.of(4, 1))
            .item(() -> new ItemStack(Material.BONE), 1, 2.5, true, Weight.of(1, 1), Weight.of(2, 2), Weight.of(4, 1))
            .item(() -> new ItemStack(Material.STRING), 1, 2.5, true, Weight.of(1, 1), Weight.of(2, 2), Weight.of(4, 1))
            .item(() -> new ItemStack(Material.SPIDER_EYE), 1, 2.5, true, Weight.of(1, 1), Weight.of(2, 2), Weight.of(4, 1))
            .item(() -> new ItemStack(Material.GUNPOWDER), 2, 2.5, true, Weight.of(1, 1), Weight.of(2, 2), Weight.of(4, 1))
            .item(() -> new ItemStack(Material.ENDER_PEARL), 3, 2.0, Weight.of(1, 1), Weight.of(2, 1))

            .item(() -> new ItemStack(Material.GLOW_BERRIES), 2, 3.0, Weight.of(1, 1), Weight.of(2, 2), Weight.of(4, 1))
            .item(() -> new ItemStack(Material.BAKED_POTATO), 2, 3.0, Weight.of(1, 1), Weight.of(2, 2), Weight.of(4, 1))
            .item(() -> new ItemStack(Material.SUSPICIOUS_STEW), 2, 2.5)
            .item(() -> new ItemStack(Material.GOLDEN_APPLE), 3, 1.5, Weight.of(1, 1), Weight.of(2, 1))
            .item(() -> new ItemStack(Material.ENCHANTED_GOLDEN_APPLE), 6, 1.0, Weight.of(1, 1))
            .item(() -> new ItemStack(Material.GOLDEN_CARROT), 3, 1.5, Weight.of(1, 1), Weight.of(2, 1))

            .item(() -> new ItemStack(Material.COAL), 2, 2.5, Weight.of(4, 1), Weight.of(8, 2), Weight.of(12, 1))
            .item(() -> new ItemStack(Material.IRON_INGOT), 3, 2.5, Weight.of(2, 1), Weight.of(4, 2), Weight.of(8, 1))
            .item(() -> new ItemStack(Material.REDSTONE), 4, 2.25, Weight.of(4, 1), Weight.of(8, 2), Weight.of(12, 1))
            .item(() -> new ItemStack(Material.LAPIS_LAZULI), 3, 2.25, Weight.of(4, 1), Weight.of(8, 2), Weight.of(12, 2), Weight.of(16, 1))
            .item(() -> new ItemStack(Material.DIAMOND), 5, 2.0, Weight.of(1, 2), Weight.of(2, 2), Weight.of(4, 1))
            .item(() -> new ItemStack(Material.NETHERITE_SCRAP), 6, 0.625, Weight.of(1, 2), Weight.of(2, 2), Weight.of(4, 1))
            .item(() -> new ItemStack(Material.NETHERITE_INGOT), 7, 0.25, Weight.of(1, 4), Weight.of(2, 1))

            .item(() -> new ItemStack(Material.SCULK_VEIN), 1, 5.0, true)
            .item(() -> new ItemStack(Material.COBWEB), 1, 25.0)
            .item(() -> new ItemStack(Material.CANDLE), 1, 1.0)
            .item(() -> new ItemStack(Material.WHITE_CANDLE), 1, 1.0)
            .item(() -> new ItemStack(Material.SOUL_TORCH), 1, 1.0)
            .item(() -> new ItemStack(Material.SOUL_LANTERN), 1, 0.5)
            .item(() -> new ItemStack(Material.REDSTONE_TORCH), 2, 1.0)
            .item(() -> new ItemStack(Material.REPEATER), 3, 1.0)
            .item(() -> new ItemStack(Material.COMPARATOR), 3, 1.0)
            .item(() -> new ItemStack(Material.LECTERN), 1, 1.0)
            .item(() -> new ItemStack(Material.SKELETON_SKULL), 4, 0.5)

            .item(new ItemBuilder(Material.IRON_HELMET)
                    .addQuantity(0, 1.0).addQuantity(1, 2.0).addQuantity(2, 1.0)
                    .addWeightedEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, Weight.of(1, 2.0), Weight.of(2, 1.0))
                    .addWeightedEnchant(Enchantment.THORNS, Weight.of(1, 1.0))
                    .asStackSupplier(), 6, 0.125)
            .item(new ItemBuilder(Material.IRON_CHESTPLATE)
                    .addQuantity(0, 1.0).addQuantity(1, 2.0).addQuantity(2, 1.0)
                    .addWeightedEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, Weight.of(1, 2.0), Weight.of(2, 1.0))
                    .addWeightedEnchant(Enchantment.THORNS, Weight.of(1, 1.0))
                    .asStackSupplier(), 6, 0.125)
            .item(new ItemBuilder(Material.IRON_LEGGINGS)
                    .addQuantity(0, 1.0).addQuantity(1, 2.0).addQuantity(2, 1.0)
                    .addWeightedEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, Weight.of(1, 2.0), Weight.of(2, 1.0))
                    .addWeightedEnchant(Enchantment.THORNS, Weight.of(1, 1.0))
                    .asStackSupplier(), 6, 0.125)
            .item(new ItemBuilder(Material.IRON_BOOTS)
                    .addQuantity(0, 1.0).addQuantity(1, 2.0).addQuantity(2, 1.0)
                    .addWeightedEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, Weight.of(1, 2.0), Weight.of(2, 1.0))
                    .addWeightedEnchant(Enchantment.THORNS, Weight.of(1, 1.0))
                    .asStackSupplier(), 6, 0.125)

            .item(() -> new ItemStack(Material.DIAMOND_HELMET), 6, 0.125)
            .item(() -> new ItemStack(Material.DIAMOND_CHESTPLATE), 6, 0.125)
            .item(() -> new ItemStack(Material.DIAMOND_LEGGINGS), 6, 0.125)
            .item(() -> new ItemStack(Material.DIAMOND_BOOTS), 6, 0.125)

            .item(new ItemBuilder(Material.IRON_AXE)
                    .addQuantity(0, 1.0).addQuantity(1, 2.0).addQuantity(2, 1.0)
                    .addWeightedEnchant(Enchantment.DIG_SPEED, Weight.of(1, 2.0), Weight.of(2, 1.0))
                    .addWeightedEnchant(Enchantment.DURABILITY, Weight.of(1, 1.0))
                    .asStackSupplier(), 6, 0.125)
            .item(new ItemBuilder(Material.IRON_HOE)
                    .addQuantity(0, 1.0).addQuantity(1, 1.0)
                    .addWeightedEnchant(Enchantment.LOOT_BONUS_BLOCKS, Weight.of(2, 2.0), Weight.of(3, 1.0))
                    .asStackSupplier(), 6, 0.125)
            .item(new ItemBuilder(Material.IRON_PICKAXE)
                    .addQuantity(0, 1.0).addQuantity(1, 2.0).addQuantity(2, 1.0)
                    .addWeightedEnchant(Enchantment.DIG_SPEED, Weight.of(1, 2.0), Weight.of(2, 1.0))
                    .addWeightedEnchant(Enchantment.DURABILITY, Weight.of(1, 1.0))
                    .asStackSupplier(), 6, 0.125)
            .item(new ItemBuilder(Material.IRON_SHOVEL)
                    .addQuantity(0, 1.0).addQuantity(1, 2.0).addQuantity(2, 1.0)
                    .addWeightedEnchant(Enchantment.DIG_SPEED, Weight.of(1, 2.0), Weight.of(2, 1.0))
                    .addWeightedEnchant(Enchantment.DURABILITY, Weight.of(1, 1.0))
                    .asStackSupplier(), 6, 0.125)
            .item(new ItemBuilder(Material.IRON_SWORD)
                    .addQuantity(0, 1.0).addQuantity(1, 2.0).addQuantity(2, 1.0)
                    .addWeightedEnchant(Enchantment.DAMAGE_ALL, Weight.of(1, 2.0), Weight.of(2, 1.0))
                    .addWeightedEnchant(Enchantment.LOOT_BONUS_MOBS, Weight.of(1, 1.0))
                    .asStackSupplier(), 6, 0.125)
            .item(new ItemBuilder(Material.IRON_AXE)
                    .addQuantity(0, 1.0).addQuantity(1, 2.0).addQuantity(2, 1.0)
                    .addWeightedEnchant(Enchantment.DAMAGE_ALL, Weight.of(1, 2.0), Weight.of(2, 1.0))
                    .addWeightedEnchant(Enchantment.LOOT_BONUS_MOBS, Weight.of(1, 1.0))
                    .asStackSupplier(), 6, 0.125)

            .item(new ItemBuilder(Material.DIAMOND_AXE).asStackSupplier(), 6, 0.125)
            .item(new ItemBuilder(Material.DIAMOND_HOE).asStackSupplier(), 6, 0.125)
            .item(new ItemBuilder(Material.DIAMOND_PICKAXE).asStackSupplier(), 6, 0.125)
            .item(new ItemBuilder(Material.DIAMOND_SHOVEL).asStackSupplier(), 6, 0.125)
            .item(new ItemBuilder(Material.DIAMOND_SWORD).asStackSupplier(), 6, 0.125)

            .item(new ItemBuilder(Material.BOW)
                    .addQuantity(0, 2.0).addQuantity(1, 2.0).addQuantity(2, 2.0).addQuantity(3, 1.0)
                    .addWeightedEnchant(Enchantment.ARROW_DAMAGE, Weight.of(1, 4.0), Weight.of(2, 2.0), Weight.of(3, 1.0))
                    .addWeightedEnchant(Enchantment.ARROW_KNOCKBACK, Weight.of(1, 2.0), Weight.of(2, 1.0))
                    .addWeightedEnchant(Enchantment.ARROW_FIRE, Weight.of(1, 1.0))
                    .addWeightedEnchant(Enchantment.ARROW_INFINITE, Weight.of(1, 1.0))
                    .asStackSupplier(), 2, 0.5)
            .item(new ItemBuilder(Material.CROSSBOW)
                    .addQuantity(0, 2.0).addQuantity(1, 2.0).addQuantity(2, 1.0)
                    .addWeightedEnchant(Enchantment.PIERCING, Weight.of(1, 8.0), Weight.of(2, 4.0), Weight.of(3, 2.0), Weight.of(4, 1.0))
                    .addWeightedEnchant(Enchantment.QUICK_CHARGE, Weight.of(1, 4.0), Weight.of(2, 2.0), Weight.of(3, 1.0))
                    .addWeightedEnchant(Enchantment.MULTISHOT, Weight.of(1, 1.0))
                    .asStackSupplier(), 3, 0.5)
            .item(() -> new ItemStack(Material.ARROW), 1, 2.5, Weight.of(2, 1), Weight.of(4, 2), Weight.of(8, 2), Weight.of(16, 1))


            .item(() -> new ItemStack(Material.LEAD), 2, 1.25)
            .item(() -> new ItemStack(Material.SADDLE), 3, 0.5)
            .item(() -> new ItemStack(Material.DIAMOND_HORSE_ARMOR), 5, 0.125)

            .item(() -> new ItemStack(Material.EXPERIENCE_BOTTLE), 3, 1.25, Weight.of(4, 1), Weight.of(8, 2), Weight.of(12, 2), Weight.of(16, 1))
            .item(() -> new ItemStack(Material.BOOK), 2, 1.25, Weight.of(1, 1), Weight.of(2, 2), Weight.of(4, 2), Weight.of(8, 1))
            .item(new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.MENDING, Weight.of(1, 1)).asStackSupplier(), 4, 0.065)
            .item(new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.DURABILITY, Weight.of(1, 2), Weight.of(2, 1)).asStackSupplier(), 4, 0.065)
            .item(new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.PROTECTION_EXPLOSIONS, Weight.of(1, 4), Weight.of(2, 2), Weight.of(3, 1)).asStackSupplier(), 4, 0.065)
            .item(new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.PROTECTION_FALL, Weight.of(1, 4), Weight.of(2, 2), Weight.of(3, 1)).asStackSupplier(), 4, 0.065)
            .item(new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.PROTECTION_FIRE, Weight.of(1, 4), Weight.of(2, 2), Weight.of(3, 1)).asStackSupplier(), 4, 0.065)
            .item(new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.PROTECTION_PROJECTILE, Weight.of(1, 4), Weight.of(2, 2), Weight.of(3, 1)).asStackSupplier(), 4, 0.065)
            .item(new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, Weight.of(1, 4), Weight.of(2, 2), Weight.of(3, 1)).asStackSupplier(), 4, 0.065)
            .item(new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.SWIFT_SNEAK, Weight.of(1, 2), Weight.of(2, 1)).asStackSupplier(), 4, 0.065)
            .item(new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.THORNS, Weight.of(1, 4), Weight.of(2, 2), Weight.of(3, 1)).asStackSupplier(), 4, 0.065)
            .item(new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.DAMAGE_ARTHROPODS, Weight.of(1, 4), Weight.of(2, 2), Weight.of(3, 1)).asStackSupplier(), 4, 0.065)
            .item(new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.FIRE_ASPECT, Weight.of(1, 2), Weight.of(2, 1)).asStackSupplier(), 4, 0.065)
            .item(new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.LOOT_BONUS_MOBS, Weight.of(1, 4), Weight.of(2, 2), Weight.of(3, 1)).asStackSupplier(), 4, 0.065)
            .item(new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.KNOCKBACK, Weight.of(1, 2), Weight.of(2, 1)).asStackSupplier(), 4, 0.065)
            .item(new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.DAMAGE_ALL, Weight.of(1, 8), Weight.of(2, 4), Weight.of(3, 2), Weight.of(4, 1)).asStackSupplier(), 4, 0.065)
            .item(new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.DAMAGE_UNDEAD, Weight.of(1, 4), Weight.of(2, 2), Weight.of(3, 1)).asStackSupplier(), 4, 0.065)
            .item(new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.SWEEPING_EDGE, Weight.of(1, 2), Weight.of(2, 1)).asStackSupplier(), 4, 0.065)
            .item(new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.ARROW_FIRE, Weight.of(1, 1)).asStackSupplier(), 4, 0.065)
            .item(new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.ARROW_INFINITE, Weight.of(1, 1)).asStackSupplier(), 4, 0.065)
            .item(new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.MULTISHOT, Weight.of(1, 1)).asStackSupplier(), 4, 0.065)
            .item(new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.PIERCING, Weight.of(1, 4), Weight.of(2, 2), Weight.of(3, 1)).asStackSupplier(), 4, 0.065)
            .item(new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.ARROW_DAMAGE, Weight.of(1, 8), Weight.of(2, 4), Weight.of(3, 2), Weight.of(4, 1)).asStackSupplier(), 4, 0.065)
            .item(new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.ARROW_KNOCKBACK, Weight.of(1, 2), Weight.of(2, 1)).asStackSupplier(), 4, 0.065)
            .item(new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.QUICK_CHARGE, Weight.of(1, 4), Weight.of(2, 2), Weight.of(3, 1)).asStackSupplier(), 4, 0.065)
            .item(new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.DIG_SPEED, Weight.of(1, 8), Weight.of(2, 4), Weight.of(3, 2), Weight.of(4, 1)).asStackSupplier(), 4, 0.065)
            .item(new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.LOOT_BONUS_BLOCKS, Weight.of(1, 4), Weight.of(2, 2), Weight.of(3, 1)).asStackSupplier(), 4, 0.065)
            .item(new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.SILK_TOUCH, Weight.of(1, 1)).asStackSupplier(), 4, 0.065)

            .item(Util.potion(PotionType.REGEN, false, false), 4, 0.25)
            .item(Util.potion(PotionType.REGEN, true, false), 5, 0.1875)
            .item(Util.potion(PotionType.REGEN, false, true), 5, 0.1875)
            .item(Util.splashPotion(PotionType.REGEN, false, false), 5, 0.1875)
            .item(Util.splashPotion(PotionType.REGEN, true, false), 6, 0.125)
            .item(Util.splashPotion(PotionType.REGEN, false, true), 6, 0.125)

            .item(() -> new ItemStack(Material.DISC_FRAGMENT_5), 7, 1.75, true)
            .item(() -> new ItemStack(Material.MUSIC_DISC_OTHERSIDE), 7, 0.0625, true)

            .build();

    @NotNull
    public static final LootDigest REWARDS = LootDigest.builder("UNFATHOMABLE_ABYSS_REWARDS", Weight.of(0))
            .chain(LOOT, Weight.of(12))

            .build();

    @NotNull
    public static final Phase PHASE = Phase.builder().name(NAME).displayName(DISPLAY_NAME).textColor(TEXT_COLOR).color(BAR_COLOR).blocks(BLOCKS)
            .boss(BOSS)
            .bossName(BOSS_NAME)
            .rewards(REWARDS)
            .result((e, c) -> Util.spawnChest(CHEST_DISPLAY_NAME, LOOT, c).apply(e, c), Weight.of(4, 0.375))

            .result(Util.defaultData(Material.DEEPSLATE), Weight.of(1, 11.25))
            .result(Util.defaultData(Material.BLACKSTONE), Weight.of(2, 1.875))
            .result(Util.defaultData(Material.TUFF), Weight.of(1, 1.875))
            .result(Util.defaultData(Material.PACKED_ICE), Weight.of(1, 3.75))
            .result(Util.defaultData(Material.BLUE_ICE), Weight.of(2, 1.875))
            .result(Util.defaultData(Material.COAL_BLOCK), Weight.of(2, 0.28125))
            .result(Util.defaultData(Material.RAW_IRON_BLOCK), Weight.of(3, 0.28125))
            .result(Util.defaultData(Material.DEEPSLATE_REDSTONE_ORE), Weight.of(4, 0.375))
            .result(Util.defaultData(Material.DEEPSLATE_LAPIS_ORE), Weight.of(4, 0.375))
            .result(Util.defaultData(Material.DEEPSLATE_DIAMOND_ORE), Weight.of(5, 0.28125))
            .result(Util.defaultData(Material.SCULK), Weight.of(2, 7.5))
            .result(Util.defaultData(Material.SCULK_SENSOR), Weight.of(2, 1.875))
            .result(Util.defaultData(Material.SCULK_CATALYST), Weight.of(3, 0.75))
            .result(Util.defaultData(Material.SCULK_SHRIEKER), Weight.of(3, 0.375))
            .result(Util.defaultData(Material.COBBLED_DEEPSLATE), Weight.of(1, 0.375))
            .result(Util.defaultData(Material.POLISHED_DEEPSLATE), Weight.of(1, 0.1875))
            .result(Util.defaultData(Material.DEEPSLATE_BRICKS), Weight.of(1, 0.375))
            .result(Util.defaultData(Material.CRACKED_DEEPSLATE_BRICKS), Weight.of(1, 0.1875))
            .result(Util.defaultData(Material.DEEPSLATE_TILES), Weight.of(1, 0.375))
            .result(Util.defaultData(Material.CRACKED_DEEPSLATE_TILES), Weight.of(1, 0.1875))
            .result(Util.defaultData(Material.CHISELED_DEEPSLATE), Weight.of(1, 0.28125))
            .result(Util.defaultData(Material.SOUL_SAND), Weight.of(1, 0.28125))
            .result(Util.defaultData(Material.POLISHED_BASALT), Weight.of(1, 0.28125))
            .result(Util.defaultData(Material.SMOOTH_BASALT), Weight.of(1, 0.28125))
            .result(Util.defaultData(Material.DARK_OAK_LOG), Weight.of(1, 0.375))
            .result(Util.defaultData(Material.DARK_OAK_PLANKS), Weight.of(1, 0.375))
            .result(Util.defaultData(Material.BOOKSHELF), Weight.of(2, 0.375))
            .result(Util.defaultData(Material.REDSTONE_BLOCK), Weight.of(4, 0.375))
            .result(Util.defaultData(Material.REDSTONE_LAMP), Weight.of(3, 0.075))
            .result(Util.defaultData(Material.TARGET), Weight.of(2, 0.075))
            .result(Util.defaultData(Material.NOTE_BLOCK), Weight.of(2, 0.075))
            .result(Util.defaultData(Material.GRAY_WOOL), Weight.of(1, 0.15))
            .result(Util.defaultData(Material.BLUE_WOOL), Weight.of(1, 0.075))
            .result(Util.defaultData(Material.CYAN_WOOL), Weight.of(1, 0.075))
            .result(Util.defaultData(Material.LIGHT_BLUE_WOOL), Weight.of(1, 0.075))

            .result(EntityUtil.spawnEntity(EntityType.BAT), Weight.of(1, 0.1875))
            .result(EntityUtil.spawnZombie(), Weight.of(1, 0.06375))
            .result(EntityUtil.spawnBabyZombie(), Weight.of(2, 0.01125))
            .result(EntityUtil.spawnUpgradedZombie2(), Weight.of(3, 0.05625))
            .result(EntityUtil.spawnUpgradedZombie3(), Weight.of(4, 0.0375))
            .result(EntityUtil.spawnSkeleton(), Weight.of(1, 0.075))
            .result(EntityUtil.spawnUpgradedSkeleton2(), Weight.of(3, 0.05625))
            .result(EntityUtil.spawnUpgradedSkeleton3(), Weight.of(4, 0.0375))
            .result(EntityUtil.spawnHusk(), Weight.of(1, 0.06375))
            .result(EntityUtil.spawnBabyHusk(), Weight.of(2, 0.01125))
            .result(EntityUtil.spawnUpgradedHusk1(), Weight.of(3, 0.05625))
            .result(EntityUtil.spawnUpgradedHusk2(), Weight.of(4, 0.0375))
            .result(EntityUtil.spawnStray(), Weight.of(1, 0.075))
            .result(EntityUtil.spawnUpgradedStray1(), Weight.of(3, 0.05625))
            .result(EntityUtil.spawnUpgradedStray2(), Weight.of(4, 0.0375))
            .result(EntityUtil.spawnEntity(EntityType.CAVE_SPIDER), Weight.of(2, 0.075))
            .result(EntityUtil.spawnUpgradedCaveSpider(), Weight.of(3, 0.05625))
            .result(EntityUtil.spawnCaveSpiderJockey(), Weight.of(3, 0.05625))
            .result(EntityUtil.spawnChargedCreeper(), Weight.of(4, 0.0375))
            .result(EntityUtil.spawnSilverfishMob(), Weight.of(2, 0.05625))
            .result(EntityUtil.spawnEntity(EntityType.ENDERMAN), Weight.of(3, 0.05625))

            .result(EntityUtil.playRandomWardenSound(), Weight.of(2, 0.75))
            .result(EntityUtil.wardenBlip(), Weight.of(3, 0.06))

            .build();
}
