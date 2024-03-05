package io.hyleo.obsb.phases;

import io.hyleo.obsb.api.LootDigest;
import io.hyleo.obsb.api.Phase;
import io.hyleo.obsb.api.Weight;
import io.hyleo.obsb.util.EntityUtil;
import io.hyleo.obsb.util.ItemBuilder;
import io.hyleo.obsb.util.Util;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.val;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.BiFunction;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class KaleidoscopeLibrary {

    @NotNull
    public static final String NAME = "Kaleidoscope_Library";

    public static final int BLOCKS = 1400;

    @NotNull
    public static final String DISPLAY_NAME = "Kaleidoscope Library";

    @NotNull
    public static final TextColor TEXT_COLOR = Objects.requireNonNull(TextColor.fromHexString("#CF9FFF"));

    @NotNull
    public static final BossBar.Color BAR_COLOR = BossBar.Color.PURPLE;

    @NotNull
    public static final Component CHEST_DISPLAY_NAME = Component.text("Library Chest").color(NamedTextColor.DARK_GRAY);

    @NotNull
    public static final Component SCHOLAR_CHEST_DISPLAY_NAME = Component.text("Scholar's Chest").color(NamedTextColor.DARK_GRAY);

    @NotNull
    public static final Component BOSS_NAME = Component.text("Treacherous Sages");

    @NotNull
    public static final BiFunction<@NotNull BlockBreakEvent, @NotNull Player, @NotNull Collection<@NotNull LivingEntity>> BOSS = (e, t) -> {
        val entities = new ArrayList<    @NotNull LivingEntity>();

        val block = e.getBlock();
        val team = Bukkit.getScoreboardManager().getNewScoreboard().registerNewTeam(UUID.randomUUID().toString().substring(0, 16));

        val offset1 = new Vector(1, 2, 1);
        val spiderJockey1 = spawnSpiderJockey(block, "Sir Nostradamus the Acolyte", offset1, null);

        val offset2 = new Vector(1, 2, -1);
        val spiderJockeyEffect2 = new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1);
        val spiderJockey2 = spawnSpiderJockey(block, "Lady Seighildan the Swift", offset2, spiderJockeyEffect2);

        val offset3 = new Vector(-1, 2, 1);
        val spiderJockeyEffect3 = new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, 1);
        val spiderJockey3 = spawnSpiderJockey(block, "Lady Domerthocles the Vicious", offset3, spiderJockeyEffect3);

        val offset4 = new Vector(-1, 2, -1);
        val spiderJockeyEffect4 = new PotionEffect(PotionEffectType.REGENERATION, Integer.MAX_VALUE, 1);
        val spiderJockey4 = spawnSpiderJockey(block, "Sir Tsoptihscus the Apothecary", offset4, spiderJockeyEffect4);

        entities.addAll(spiderJockey1);
        entities.addAll(spiderJockey2);
        entities.addAll(spiderJockey3);
        entities.addAll(spiderJockey4);

        team.addEntities(entities.toArray(new LivingEntity[0]));

        return entities;
    };


    @NotNull
    public static List<@NotNull LivingEntity> spawnSpiderJockey(@NonNull Block block, @NonNull String name, @NonNull Vector offset, @Nullable PotionEffect effect) {

        val world = block.getWorld();
        val spawnLocation = block.getLocation().add(offset);

        val spider = (Spider) world.spawnEntity(spawnLocation, EntityType.SPIDER);
        val skeleton = (Skeleton) world.spawnEntity(spawnLocation, EntityType.SKELETON);

        final List<LivingEntity> entities = List.of(spider, skeleton);

        for (LivingEntity entity : entities) {
            val maxHealth = entity.getAttribute(Attribute.GENERIC_MAX_HEALTH);
            assert maxHealth != null;

            maxHealth.setBaseValue(40);
            entity.setHealth(40);

            if (effect != null) {
                entity.addPotionEffect(effect);
            }
        }

        skeleton.customName(Component.text(name));
        skeleton.setShouldBurnInDay(false);

        spider.addPassenger(skeleton);

        return entities;
    }

    @NotNull
    public static final LootDigest LOOT = LootDigest.builder("KALEIDOSCOPE_LIBRARY", Weight.of(6), Weight.of(9), Weight.of(12))
            .item(() -> new ItemStack(Material.WARPED_STEM), 1, 7.5, true, Weight.of(2, 1), Weight.of(4, 2), Weight.of(6, 1))
            .item(() -> new ItemStack(Material.WARPED_FUNGUS), 1, 5.0, true, Weight.of(1, 4), Weight.of(2, 2))

            .item(() -> new ItemStack(Material.BONE), 1, 3.0, true, Weight.of(1, 1), Weight.of(2, 2), Weight.of(4, 1))
            .item(() -> new ItemStack(Material.STRING), 1, 3.0, true, Weight.of(1, 1), Weight.of(2, 2), Weight.of(4, 1))
            .item(() -> new ItemStack(Material.SPIDER_EYE), 1, 3.0, true, Weight.of(1, 1), Weight.of(2, 2), Weight.of(4, 1))

            .item(() -> new ItemStack(Material.SUGAR_CANE), 2, 7.5, Weight.of(2, 1), Weight.of(4, 2), Weight.of(6, 1))
            .item(() -> new ItemStack(Material.APPLE), 2, 10.0, Weight.of(2, 1), Weight.of(4, 2), Weight.of(6, 1))
            .item(() -> new ItemStack(Material.GOLDEN_APPLE), 3, 1.5, Weight.of(1, 1), Weight.of(2, 2))
            .item(() -> new ItemStack(Material.ENCHANTED_GOLDEN_APPLE), 6, 0.5, Weight.of(1, 1))
            .item(() -> new ItemStack(Material.GOLDEN_CARROT), 3, 1.5, Weight.of(1, 1), Weight.of(2, 2))

            .item(() -> new ItemStack(Material.WARPED_ROOTS), 1, 2.5, true)
            .item(() -> new ItemStack(Material.TWISTING_VINES), 1, 2.5, true)
            .item(() -> new ItemStack(Material.NETHER_SPROUTS), 1, 2.5, true)
            .item(() -> new ItemStack(Material.DANDELION), 1, 1.0, true)
            .item(() -> new ItemStack(Material.POPPY), 1, 1.0, true)
            .item(() -> new ItemStack(Material.BLUE_ORCHID), 1, 1.0, true)
            .item(() -> new ItemStack(Material.ALLIUM), 1, 1.0, true)
            .item(() -> new ItemStack(Material.AZURE_BLUET), 1, 1.0, true)
            .item(() -> new ItemStack(Material.RED_TULIP), 1, 1.0, true)
            .item(() -> new ItemStack(Material.ORANGE_TULIP), 1, 1.0, true)
            .item(() -> new ItemStack(Material.PINK_TULIP), 1, 1.0, true)
            .item(() -> new ItemStack(Material.WHITE_TULIP), 1, 1.0, true)
            .item(() -> new ItemStack(Material.OXEYE_DAISY), 1, 1.0, true)
            .item(() -> new ItemStack(Material.CORNFLOWER), 1, 1.0, true)
            .item(() -> new ItemStack(Material.LILY_OF_THE_VALLEY), 1, 1.0, true)
            .item(() -> new ItemStack(Material.SUNFLOWER), 1, 1.0, true)
            .item(() -> new ItemStack(Material.LILAC), 1, 1.0, true)
            .item(() -> new ItemStack(Material.ROSE_BUSH), 1, 1.0, true)
            .item(() -> new ItemStack(Material.PEONY), 1, 1.0, true)
            .item(() -> new ItemStack(Material.WITHER_ROSE), 3, 1.0, true)
            .item(() -> new ItemStack(Material.COBWEB), 1, 25.0, true)
            .item(() -> new ItemStack(Material.CANDLE), 1, 5.0, true)
            .item(() -> new ItemStack(Material.LANTERN), 1, 5.0, true)
            .item(() -> new ItemStack(Material.LECTERN), 1, 2.0, true)

            .item(() -> new ItemStack(Material.MUSIC_DISC_WAIT), 6, 0.2)

            .build();

    @NotNull
    public static final LootDigest SCHOLAR_LOOT = LootDigest.builder("KALEIDOSCOPE_LIBRARY_SCHOLAR", Weight.of(6), Weight.of(8), Weight.of(10))
            .item(() -> new ItemStack(Material.REDSTONE), 3, 7.5, Weight.of(1, 4), Weight.of(2, 2), Weight.of(4, 1))
            .item(() -> new ItemStack(Material.LAPIS_LAZULI), 3, 7.5, Weight.of(1, 4), Weight.of(2, 2), Weight.of(4, 1))
            .item(() -> new ItemStack(Material.DIAMOND), 4, 1.0, Weight.of(1, 4), Weight.of(2, 1))

            .item(new ItemBuilder(Material.LEATHER_HELMET).setIgnoringConflictingEnchants(true).addEnchant(Enchantment.VANISHING_CURSE, 1, false)
                    .addQuantity(4, 1.0).addQuantity(5, 2.0).addQuantity(6, 2.0).addQuantity(7, 1.0)
                    .addWeightedEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, Weight.of(3, 4.0), Weight.of(4, 2.0), Weight.of(5, 1.0))
                    .addWeightedEnchant(Enchantment.PROTECTION_PROJECTILE, Weight.of(3, 4.0), Weight.of(4, 2.0), Weight.of(5, 1.0))
                    .addWeightedEnchant(Enchantment.PROTECTION_EXPLOSIONS, Weight.of(3, 4.0), Weight.of(4, 2.0), Weight.of(5, 1.0))
                    .addWeightedEnchant(Enchantment.THORNS, Weight.of(2, 2.0), Weight.of(3, 1.0))
                    .addWeightedEnchant(Enchantment.DURABILITY, Weight.of(3, 4.0), Weight.of(4, 2.0), Weight.of(5, 1.0))
                    .addWeightedEnchant(Enchantment.MENDING, Weight.of(1, 1.0))
                    .addWeightedEnchant(Enchantment.OXYGEN, Weight.of(2, 2.0), Weight.of(3, 1.0))
                    .asStackSupplier(), 6, 0.25)
            .item(new ItemBuilder(Material.LEATHER_CHESTPLATE).setIgnoringConflictingEnchants(true).addEnchant(Enchantment.VANISHING_CURSE, 1, false)
                    .addQuantity(4, 1.0).addQuantity(5, 2.0).addQuantity(6, 1.0)
                    .addWeightedEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, Weight.of(3, 4.0), Weight.of(4, 2.0), Weight.of(5, 1.0))
                    .addWeightedEnchant(Enchantment.PROTECTION_PROJECTILE, Weight.of(3, 4.0), Weight.of(4, 2.0), Weight.of(5, 1.0))
                    .addWeightedEnchant(Enchantment.PROTECTION_EXPLOSIONS, Weight.of(3, 4.0), Weight.of(4, 2.0), Weight.of(5, 1.0))
                    .addWeightedEnchant(Enchantment.THORNS, Weight.of(2, 2.0), Weight.of(3, 1.0))
                    .addWeightedEnchant(Enchantment.DURABILITY, Weight.of(3, 4.0), Weight.of(4, 2.0), Weight.of(5, 1.0))
                    .addWeightedEnchant(Enchantment.MENDING, Weight.of(1, 1.0))
                    .asStackSupplier(), 6, 0.25)
            .item(new ItemBuilder(Material.LEATHER_LEGGINGS).setIgnoringConflictingEnchants(true).addEnchant(Enchantment.VANISHING_CURSE, 1, false)
                    .addQuantity(4, 1.0).addQuantity(5, 2.0).addQuantity(6, 1.0)
                    .addWeightedEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, Weight.of(3, 4.0), Weight.of(4, 2.0), Weight.of(5, 1.0))
                    .addWeightedEnchant(Enchantment.PROTECTION_PROJECTILE, Weight.of(3, 4.0), Weight.of(4, 2.0), Weight.of(5, 1.0))
                    .addWeightedEnchant(Enchantment.PROTECTION_EXPLOSIONS, Weight.of(3, 4.0), Weight.of(4, 2.0), Weight.of(5, 1.0))
                    .addWeightedEnchant(Enchantment.THORNS, Weight.of(2, 2.0), Weight.of(3, 1.0))
                    .addWeightedEnchant(Enchantment.DURABILITY, Weight.of(3, 4.0), Weight.of(4, 2.0), Weight.of(5, 1.0))
                    .addWeightedEnchant(Enchantment.MENDING, Weight.of(1, 1.0))
                    .asStackSupplier(), 6, 0.25)
            .item(new ItemBuilder(Material.LEATHER_BOOTS).setIgnoringConflictingEnchants(true).addEnchant(Enchantment.VANISHING_CURSE, 1, false)
                    .addQuantity(4, 1.0).addQuantity(5, 2.0).addQuantity(6, 2.0).addQuantity(7, 1.0)
                    .addWeightedEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, Weight.of(3, 4.0), Weight.of(4, 2.0), Weight.of(5, 1.0))
                    .addWeightedEnchant(Enchantment.PROTECTION_PROJECTILE, Weight.of(3, 4.0), Weight.of(4, 2.0), Weight.of(5, 1.0))
                    .addWeightedEnchant(Enchantment.PROTECTION_EXPLOSIONS, Weight.of(3, 4.0), Weight.of(4, 2.0), Weight.of(5, 1.0))
                    .addWeightedEnchant(Enchantment.THORNS, Weight.of(2, 2.0), Weight.of(3, 1.0))
                    .addWeightedEnchant(Enchantment.DURABILITY, Weight.of(3, 4.0), Weight.of(4, 2.0), Weight.of(5, 1.0))
                    .addWeightedEnchant(Enchantment.MENDING, Weight.of(1, 1.0))
                    .addWeightedEnchant(Enchantment.PROTECTION_FALL, Weight.of(3, 4.0), Weight.of(4, 2.0), Weight.of(5, 1.0))
                    .asStackSupplier(), 6, 0.25)

            .item(() -> new ItemStack(Material.DIAMOND_HELMET), 6, 0.25)
            .item(() -> new ItemStack(Material.DIAMOND_CHESTPLATE), 6, 0.25)
            .item(() -> new ItemStack(Material.DIAMOND_LEGGINGS), 6, 0.25)
            .item(() -> new ItemStack(Material.DIAMOND_BOOTS), 6, 0.25)

            .item(new ItemBuilder(Material.WOODEN_AXE).setIgnoringConflictingEnchants(true).addEnchant(Enchantment.VANISHING_CURSE, 1, false)
                    .addQuantity(3, 1.0).addQuantity(4, 2.0)
                    .addWeightedEnchant(Enchantment.DIG_SPEED, Weight.of(4, 4.0), Weight.of(5, 2.0), Weight.of(6, 1.0))
                    .addWeightedEnchant(Enchantment.LOOT_BONUS_BLOCKS, Weight.of(2, 2.0), Weight.of(3, 1.0))
                    .addWeightedEnchant(Enchantment.DURABILITY, Weight.of(3, 4.0), Weight.of(4, 2.0), Weight.of(5, 1.0))
                    .addWeightedEnchant(Enchantment.MENDING, Weight.of(1, 1.0))
                    .asStackSupplier(), 6, 0.25)
            .item(new ItemBuilder(Material.WOODEN_HOE).setIgnoringConflictingEnchants(true).addEnchant(Enchantment.VANISHING_CURSE, 1, false)
                    .addQuantity(3, 1.0).addQuantity(4, 2.0)
                    .addWeightedEnchant(Enchantment.DIG_SPEED, Weight.of(4, 4.0), Weight.of(5, 2.0), Weight.of(6, 1.0))
                    .addWeightedEnchant(Enchantment.LOOT_BONUS_BLOCKS, Weight.of(4, 4.0), Weight.of(5, 2.0), Weight.of(6, 1.0))
                    .addWeightedEnchant(Enchantment.DURABILITY, Weight.of(3, 4.0), Weight.of(4, 2.0), Weight.of(5, 1.0))
                    .addWeightedEnchant(Enchantment.MENDING, Weight.of(1, 1.0))
                    .asStackSupplier(), 6, 0.25)
            .item(new ItemBuilder(Material.WOODEN_PICKAXE).setIgnoringConflictingEnchants(true).addEnchant(Enchantment.VANISHING_CURSE, 1, false)
                    .addQuantity(3, 1.0).addQuantity(4, 2.0)
                    .addWeightedEnchant(Enchantment.DIG_SPEED, Weight.of(4, 4.0), Weight.of(5, 2.0), Weight.of(6, 1.0))
                    .addWeightedEnchant(Enchantment.LOOT_BONUS_BLOCKS, Weight.of(2, 2.0), Weight.of(3, 1.0))
                    .addWeightedEnchant(Enchantment.DURABILITY, Weight.of(3, 4.0), Weight.of(4, 2.0), Weight.of(5, 1.0))
                    .addWeightedEnchant(Enchantment.MENDING, Weight.of(1, 1.0))
                    .asStackSupplier(), 6, 0.25)
            .item(new ItemBuilder(Material.WOODEN_SHOVEL).setIgnoringConflictingEnchants(true).addEnchant(Enchantment.VANISHING_CURSE, 1, false)
                    .addQuantity(3, 1.0).addQuantity(4, 2.0)
                    .addWeightedEnchant(Enchantment.DIG_SPEED, Weight.of(4, 4.0), Weight.of(5, 2.0), Weight.of(6, 1.0))
                    .addWeightedEnchant(Enchantment.LOOT_BONUS_BLOCKS, Weight.of(2, 2.0), Weight.of(3, 1.0))
                    .addWeightedEnchant(Enchantment.DURABILITY, Weight.of(3, 4.0), Weight.of(4, 2.0), Weight.of(5, 1.0))
                    .addWeightedEnchant(Enchantment.MENDING, Weight.of(1, 1.0))
                    .asStackSupplier(), 6, 0.25)
            .item(new ItemBuilder(Material.WOODEN_SWORD).setIgnoringConflictingEnchants(true).addEnchant(Enchantment.VANISHING_CURSE, 1, false)
                    .addQuantity(3, 1.0).addQuantity(4, 2.0).addQuantity(5, 2.0).addQuantity(6, 1.0)
                    .addWeightedEnchant(Enchantment.DAMAGE_ALL, Weight.of(4, 4.0), Weight.of(5, 2.0), Weight.of(6, 1.0))
                    .addWeightedEnchant(Enchantment.DAMAGE_UNDEAD, Weight.of(2, 4.0), Weight.of(3, 2.0), Weight.of(4, 1.0))
                    .addWeightedEnchant(Enchantment.DAMAGE_ARTHROPODS, Weight.of(4, 4.0), Weight.of(5, 2.0), Weight.of(6, 1.0))
                    .addWeightedEnchant(Enchantment.LOOT_BONUS_MOBS, Weight.of(2, 2.0), Weight.of(3, 1.0))
                    .addWeightedEnchant(Enchantment.DURABILITY, Weight.of(3, 4.0), Weight.of(4, 2.0), Weight.of(5, 1.0))
                    .addWeightedEnchant(Enchantment.MENDING, Weight.of(1, 1.0))
                    .asStackSupplier(), 6, 0.25)
            .item(new ItemBuilder(Material.WOODEN_AXE).setIgnoringConflictingEnchants(true).addEnchant(Enchantment.VANISHING_CURSE, 1, false)
                    .addQuantity(3, 1.0).addQuantity(4, 2.0).addQuantity(5, 2.0).addQuantity(6, 1.0)
                    .addWeightedEnchant(Enchantment.DAMAGE_ALL, Weight.of(4, 4.0), Weight.of(5, 2.0), Weight.of(6, 1.0))
                    .addWeightedEnchant(Enchantment.DAMAGE_UNDEAD, Weight.of(2, 4.0), Weight.of(3, 2.0), Weight.of(4, 1.0))
                    .addWeightedEnchant(Enchantment.DAMAGE_ARTHROPODS, Weight.of(4, 4.0), Weight.of(5, 2.0), Weight.of(6, 1.0))
                    .addWeightedEnchant(Enchantment.LOOT_BONUS_MOBS, Weight.of(2, 2.0), Weight.of(3, 1.0))
                    .addWeightedEnchant(Enchantment.DURABILITY, Weight.of(3, 4.0), Weight.of(4, 2.0), Weight.of(5, 1.0))
                    .addWeightedEnchant(Enchantment.MENDING, Weight.of(1, 1.0))
                    .asStackSupplier(), 6, 0.25)

            .item(new ItemBuilder(Material.DIAMOND_AXE).asStackSupplier(), 6, 0.25)
            .item(new ItemBuilder(Material.DIAMOND_PICKAXE).asStackSupplier(), 6, 0.25)
            .item(new ItemBuilder(Material.DIAMOND_SHOVEL).asStackSupplier(), 6, 0.25)
            .item(new ItemBuilder(Material.DIAMOND_SWORD).asStackSupplier(), 6, 0.25)

            .item(() -> new ItemStack(Material.EXPERIENCE_BOTTLE), 3, 10.0, Weight.of(4, 1), Weight.of(8, 2), Weight.of(12, 2), Weight.of(16, 1))
            .item(() -> new ItemStack(Material.BOOK), 2, 5.0, Weight.of(1, 1), Weight.of(2, 2), Weight.of(4, 2), Weight.of(8, 1))
            .item(new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.MENDING, Weight.of(1, 1)).asStackSupplier(), 4, 0.5)
            .item(new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.DURABILITY, Weight.of(1, 2), Weight.of(2, 1)).asStackSupplier(), 4, 0.5)
            .item(new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.VANISHING_CURSE, Weight.of(1, 1)).asStackSupplier(), 4, 0.5)
            .item(new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.BINDING_CURSE, Weight.of(1, 1)).asStackSupplier(), 4, 0.5)
            .item(new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.FROST_WALKER, Weight.of(1, 2), Weight.of(2, 1)).asStackSupplier(), 4, 0.5)
            .item(new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, Weight.of(1, 4), Weight.of(2, 2), Weight.of(3, 1)).asStackSupplier(), 4, 0.5)
            .item(new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.OXYGEN, Weight.of(1, 2), Weight.of(2, 1)).asStackSupplier(), 4, 0.5)
            .item(new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.ARROW_INFINITE, Weight.of(1, 1)).asStackSupplier(), 4, 0.5)
            .item(new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.FIRE_ASPECT, Weight.of(1, 2), Weight.of(2, 1)).asStackSupplier(), 4, 0.5)
            .item(new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.DAMAGE_ALL, Weight.of(1, 8), Weight.of(2, 4), Weight.of(3, 2), Weight.of(4, 1)).asStackSupplier(), 4, 0.5)
            .item(new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.DIG_SPEED, Weight.of(1, 8), Weight.of(2, 4), Weight.of(3, 2), Weight.of(4, 1)).asStackSupplier(), 4, 0.5)
            .item(new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.SILK_TOUCH, Weight.of(1, 1)).asStackSupplier(), 4, 0.5)

            .item(() -> new ItemStack(Material.NETHER_WART), 2, 10.0, Weight.of(1, 4), Weight.of(2, 2), Weight.of(4, 2), Weight.of(8, 1))
            .item(() -> new ItemStack(Material.BLAZE_POWDER), 3, 10.0, Weight.of(1, 4), Weight.of(2, 2), Weight.of(4, 2), Weight.of(8, 1))
            .item(() -> new ItemStack(Material.REDSTONE), 2, 2.0, Weight.of(1, 4), Weight.of(2, 2), Weight.of(4, 2), Weight.of(8, 1))
            .item(() -> new ItemStack(Material.GUNPOWDER), 2, 2.0, Weight.of(1, 4), Weight.of(2, 2), Weight.of(4, 2), Weight.of(8, 1))
            .item(() -> new ItemStack(Material.FERMENTED_SPIDER_EYE), 3, 2.0)
            .item(() -> new ItemStack(Material.SUGAR), 2, 2.0)
            .item(() -> new ItemStack(Material.RABBIT_FOOT), 4, 2.0)
            .item(() -> new ItemStack(Material.GLISTERING_MELON_SLICE), 3, 2.0)
            .item(() -> new ItemStack(Material.SPIDER_EYE), 1, 2.0)
            .item(() -> new ItemStack(Material.GOLDEN_CARROT), 3, 2.0)

            .item(Util.potion(PotionType.WATER, false, false), 2, 25.0)
            .item(Util.potion(PotionType.AWKWARD, false, false), 3, 25.0)
            .item(Util.potion(PotionType.INSTANT_HEAL, false, false), 4, 0.5)
            .item(Util.splashPotion(PotionType.INSTANT_DAMAGE, false, false), 4, 0.5)
            .item(Util.potion(PotionType.SPEED, true, false), 5, 0.5)
            .item(Util.potion(PotionType.SPEED, false, true), 5, 0.5)
            .item(Util.splashPotion(PotionType.POISON, true, false), 5, 0.5)
            .item(Util.splashPotion(PotionType.POISON, false, true), 5, 0.5)
            .item(Util.splashPotion(PotionType.WEAKNESS, true, false), 5, 0.5)
            .item(Util.potion(PotionType.JUMP, false, false), 4, 0.5)
            .item(Util.splashPotion(PotionType.SLOWNESS, false, false), 4, 0.5)
            .item(Util.potion(PotionType.NIGHT_VISION, false, false), 4, 0.5)
            .item(Util.potion(PotionType.INVISIBILITY, false, false), 4, 0.5)
            .item(Util.potion(PotionType.STRENGTH, false, false), 4, 0.5)

            .build();

    @NotNull
    public static final LootDigest REWARDS = LootDigest.builder("KALEIDOSCOPE_LIBRARY_REWARDS", Weight.of(0))
            .chain(LOOT, Weight.of(3))
            .chain(SCHOLAR_LOOT, Weight.of(7))

            .build();

    @NotNull
    public static final Phase PHASE = Phase.builder().name(NAME).displayName(DISPLAY_NAME).textColor(TEXT_COLOR).color(BAR_COLOR).blocks(BLOCKS)
            .boss(BOSS)
            .bossName(BOSS_NAME)
            .rewards(REWARDS)
            .result((e, c) -> Util.spawnChest(CHEST_DISPLAY_NAME, LOOT, c).apply(e, c), Weight.of(4, 0.15))
            .result((e, c) -> Util.spawnChest(SCHOLAR_CHEST_DISPLAY_NAME, SCHOLAR_LOOT, c).apply(e, c), Weight.of(4, 0.15))

            .result(Util.defaultData(Material.WHITE_GLAZED_TERRACOTTA), Weight.of(1, 0.75))
            .result(Util.defaultData(Material.ORANGE_GLAZED_TERRACOTTA), Weight.of(1, 0.75))
            .result(Util.defaultData(Material.MAGENTA_GLAZED_TERRACOTTA), Weight.of(1, 0.75))
            .result(Util.defaultData(Material.LIGHT_BLUE_GLAZED_TERRACOTTA), Weight.of(1, 0.75))
            .result(Util.defaultData(Material.YELLOW_GLAZED_TERRACOTTA), Weight.of(1, 0.75))
            .result(Util.defaultData(Material.LIME_GLAZED_TERRACOTTA), Weight.of(1, 0.75))
            .result(Util.defaultData(Material.PINK_GLAZED_TERRACOTTA), Weight.of(1, 0.75))
            .result(Util.defaultData(Material.GRAY_GLAZED_TERRACOTTA), Weight.of(1, 0.75))
            .result(Util.defaultData(Material.LIGHT_GRAY_GLAZED_TERRACOTTA), Weight.of(1, 0.75))
            .result(Util.defaultData(Material.CYAN_GLAZED_TERRACOTTA), Weight.of(1, 0.75))
            .result(Util.defaultData(Material.PURPLE_GLAZED_TERRACOTTA), Weight.of(1, 0.75))
            .result(Util.defaultData(Material.BLUE_GLAZED_TERRACOTTA), Weight.of(1, 0.75))
            .result(Util.defaultData(Material.BROWN_GLAZED_TERRACOTTA), Weight.of(1, 0.75))
            .result(Util.defaultData(Material.GREEN_GLAZED_TERRACOTTA), Weight.of(1, 0.75))
            .result(Util.defaultData(Material.RED_GLAZED_TERRACOTTA), Weight.of(1, 0.75))
            .result(Util.defaultData(Material.BLACK_GLAZED_TERRACOTTA), Weight.of(1, 0.75))
            .result(Util.defaultData(Material.WHITE_STAINED_GLASS), Weight.of(1, 0.225))
            .result(Util.defaultData(Material.ORANGE_STAINED_GLASS), Weight.of(1, 0.225))
            .result(Util.defaultData(Material.MAGENTA_STAINED_GLASS), Weight.of(1, 0.225))
            .result(Util.defaultData(Material.LIGHT_BLUE_STAINED_GLASS), Weight.of(1, 0.225))
            .result(Util.defaultData(Material.YELLOW_STAINED_GLASS), Weight.of(1, 0.225))
            .result(Util.defaultData(Material.LIME_STAINED_GLASS), Weight.of(1, 0.225))
            .result(Util.defaultData(Material.PINK_STAINED_GLASS), Weight.of(1, 0.225))
            .result(Util.defaultData(Material.GRAY_STAINED_GLASS), Weight.of(1, 0.225))
            .result(Util.defaultData(Material.LIGHT_GRAY_STAINED_GLASS), Weight.of(1, 0.225))
            .result(Util.defaultData(Material.CYAN_STAINED_GLASS), Weight.of(1, 0.225))
            .result(Util.defaultData(Material.PURPLE_STAINED_GLASS), Weight.of(1, 0.225))
            .result(Util.defaultData(Material.BLUE_STAINED_GLASS), Weight.of(1, 0.225))
            .result(Util.defaultData(Material.BROWN_STAINED_GLASS), Weight.of(1, 0.225))
            .result(Util.defaultData(Material.GREEN_STAINED_GLASS), Weight.of(1, 0.225))
            .result(Util.defaultData(Material.RED_STAINED_GLASS), Weight.of(1, 0.225))
            .result(Util.defaultData(Material.BLACK_STAINED_GLASS), Weight.of(1, 0.225))
            .result(Util.defaultData(Material.STRIPPED_OAK_WOOD), Weight.of(1, 1.2))
            .result(Util.defaultData(Material.STRIPPED_SPRUCE_WOOD), Weight.of(1, 1.2))
            .result(Util.defaultData(Material.STRIPPED_BIRCH_WOOD), Weight.of(1, 1.2))
            .result(Util.defaultData(Material.STRIPPED_DARK_OAK_WOOD), Weight.of(1, 1.2))
            .result(Util.defaultData(Material.OAK_PLANKS), Weight.of(1, 1.2))
            .result(Util.defaultData(Material.SPRUCE_PLANKS), Weight.of(1, 1.2))
            .result(Util.defaultData(Material.BIRCH_PLANKS), Weight.of(1, 1.2))
            .result(Util.defaultData(Material.DARK_OAK_PLANKS), Weight.of(1, 1.2))
            .result(Util.defaultData(Material.BOOKSHELF), Weight.of(2, 1.5))
            .result(Util.defaultData(Material.WARPED_NYLIUM), Weight.of(1, 3.0))
            .result(Util.defaultData(Material.WARPED_STEM), Weight.of(1, 1.5))
            .result(Util.defaultData(Material.WARPED_WART_BLOCK), Weight.of(1, 1.5))
            .result(Util.defaultData(Material.SHROOMLIGHT), Weight.of(2, 0.75))
            .result(Util.defaultData(Material.CAULDRON), Weight.of(1, 0.0375))
            .result(Util.cauldron(), Weight.of(1, 0.1125))
            .result(Util.defaultData(Material.DEEPSLATE_REDSTONE_ORE), Weight.of(3, 0.2625))
            .result(Util.defaultData(Material.DEEPSLATE_LAPIS_ORE), Weight.of(3, 0.225))
            .result(Util.defaultData(Material.LAPIS_BLOCK), Weight.of(4, 0.075))
            .result(Util.defaultData(Material.DEEPSLATE_DIAMOND_ORE), Weight.of(4, 0.1875))

            .result(EntityUtil.spawnEntity(EntityType.ALLAY), Weight.of(3, 0.075))
            .result(EntityUtil.spawnEntity(EntityType.CAT), Weight.of(4, 0.0375))
            .result(EntityUtil.spawnEntity(EntityType.SPIDER), Weight.of(1, 0.225))
            .result(EntityUtil.spawnSkeleton(), Weight.of(3, 0.1125))
            .result(EntityUtil.spawnUpgradedSkeleton1(), Weight.of(4, 0.1125))
            .result(EntityUtil.spawnAscendedSkeleton(), Weight.of(5, 0.1125))
            .result(EntityUtil.spawnEntity(EntityType.SKELETON_HORSE), Weight.of(2, 0.075))
            .result(EntityUtil.spawnEntity(EntityType.WITHER_SKELETON), Weight.of(1, 0.075))
            .result(EntityUtil.spawnSilverfishMob(), Weight.of(1, 0.075))
            .result(EntityUtil.spawnEndermiteMob(), Weight.of(1, 0.075))
            .result(EntityUtil.spawnEntity(EntityType.WITCH), Weight.of(3, 0.0375))
            .result(EntityUtil.spawnAscendedWitch(), Weight.of(3, 0.0375))
            .result(EntityUtil.spawnEntity(EntityType.EVOKER), Weight.of(2, 0.075))
            .build();
}
