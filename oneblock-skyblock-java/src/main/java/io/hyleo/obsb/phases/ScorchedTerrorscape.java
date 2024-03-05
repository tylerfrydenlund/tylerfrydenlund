package io.hyleo.obsb.phases;

import io.hyleo.obsb.OneblockSkyblock;
import io.hyleo.obsb.api.LootDigest;
import io.hyleo.obsb.api.Phase;
import io.hyleo.obsb.api.Weight;
import io.hyleo.obsb.listeners.bossfights.WitherFightListener;
import io.hyleo.obsb.util.EntityUtil;
import io.hyleo.obsb.util.ItemBuilder;
import io.hyleo.obsb.util.Util;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.val;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wither;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionType;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ScorchedTerrorscape {

    @NotNull
    public static final String NAME = "Scorched_Terrorscape";

    public static final int BLOCKS = 1750;

    @NotNull
    public static final String DISPLAY_NAME = "Scorched Terrorscape";

    @NotNull
    public static final TextColor TEXT_COLOR = Objects.requireNonNull(TextColor.fromHexString("#A42A04"));

    @NotNull
    public static final BossBar.Color BAR_COLOR = BossBar.Color.RED;

    @NotNull
    public static final Component CHEST_DISPLAY_NAME = Component.text("Scorched Chest").color(NamedTextColor.DARK_GRAY);

    @NotNull
    public static final Component BASTION_CHEST_DISPLAY_NAME = Component.text("Bastion Chest").color(NamedTextColor.DARK_GRAY);

    @NotNull
    public static final Location[] witherSpawnArrayLocations = new Location[]{
            new Location(null, 0, 1, 0),
            new Location(null, 0, 2, 0),
            new Location(null, 1, 2, 0),
            new Location(null, -1, 2, 0),
            new Location(null, -1, 3, 0),
            new Location(null, 1, 3, 0),
            new Location(null, 0, 3, 0)
    };

    @NotNull
    public static final Component BOSS_NAME = Component.text("The Wither");

    @NotNull
    public static final BiFunction<@NotNull BlockBreakEvent, @NotNull Player, @NotNull Collection<@NotNull LivingEntity>> BOSS = (e, t) -> {
        val boss = spawnWither(t);
        return List.of(boss);
    };

    @NotNull
    public static Wither spawnWither(@NonNull Player player) {
        val oneBlock = OneblockSkyblock.getEmpire(player).getOneblock();
        val spawnLocation = new Location(oneBlock.getWorld(), oneBlock.getX(), 1, oneBlock.getZ());

        val wither = (Wither) oneBlock.getWorld().spawnEntity(spawnLocation, EntityType.WITHER);
        val bossBar = wither.getBossBar();

        if (bossBar != null) {
            bossBar.setVisible(false);
        }

        wither.setInvisible(true);
        wither.setAI(false);

        new BukkitRunnable() {
            final List<Location> newLocations = new ArrayList<>();
            int index = 0;

            @Override
            public void run() {
                if (index >= witherSpawnArrayLocations.length) {
                    for (Location location : newLocations) {
                        oneBlock.getWorld().getBlockAt(location).setType(Material.AIR);
                    }

                    oneBlock.getWorld().createExplosion(newLocations.get(0), 7.0F, false, false, wither);
                    wither.teleport(oneBlock.clone().add(0, 1, 0));
                    wither.setInvisible(false);
                    wither.setAI(true);

                    WitherFightListener.removeBlockLocations(newLocations);

                    this.cancel();
                } else {
                    val offsets = witherSpawnArrayLocations[index];
                    val location = oneBlock.clone().add(offsets.getX(), offsets.getY(), offsets.getZ());
                    oneBlock.getWorld().getBlockAt(location).setType(index < 4 ? Material.SOUL_SAND : Material.WITHER_SKELETON_SKULL);
                    oneBlock.getWorld().playSound(Sound.sound(Key.key("entity.wither.ambient"), Sound.Source.HOSTILE, 1.0F, 1.0F), oneBlock.getX(), oneBlock.getY(), oneBlock.getZ());
                    newLocations.add(location);
                    WitherFightListener.addBlockLocation(location);

                    index++;
                }
            }
        }.runTaskTimer(OneblockSkyblock.getInstance(), 60, 60);

        return wither;
    }

    @NotNull
    public static final LootDigest LOOT = LootDigest.builder("SCORCHED_TERRORSCAPE", Weight.of(3), Weight.of(6), Weight.of(9))
            .item(() -> new ItemStack(Material.CRIMSON_STEM), 2, 7.5, true, Weight.of(2, 1), Weight.of(4, 2), Weight.of(6, 1))
            .item(() -> new ItemStack(Material.CRIMSON_FUNGUS), 2, 5.0, true, Weight.of(1, 4), Weight.of(2, 2))

            .item(() -> new ItemStack(Material.STRING), 1, 5.0, true, Weight.of(1, 1), Weight.of(2, 2), Weight.of(4, 1))
            .item(() -> new ItemStack(Material.ROTTEN_FLESH), 1, 5.0, true, Weight.of(1, 1), Weight.of(2, 2), Weight.of(4, 1))
            .item(() -> new ItemStack(Material.BONE), 1, 5.0, true, Weight.of(1, 1), Weight.of(2, 2), Weight.of(4, 1))
            .item(() -> new ItemStack(Material.COAL), 1, 2.5, Weight.of(1, 1), Weight.of(2, 2), Weight.of(4, 1))
            .item(() -> new ItemStack(Material.BLAZE_ROD), 3, 2.5, Weight.of(1, 1), Weight.of(2, 2), Weight.of(4, 1))
            .item(() -> new ItemStack(Material.MAGMA_CREAM), 3, 2.5, Weight.of(1, 1), Weight.of(2, 2), Weight.of(4, 1))
            .item(() -> new ItemStack(Material.GHAST_TEAR), 3, 1.5, Weight.of(1, 1))

            .item(() -> new ItemStack(Material.LEATHER), 2, 5.0, Weight.of(2, 1), Weight.of(4, 2), Weight.of(8, 1))
            .item(() -> new ItemStack(Material.PORKCHOP), 2, 5.0, Weight.of(1, 1), Weight.of(2, 2), Weight.of(4, 1))
            .item(() -> new ItemStack(Material.COOKED_PORKCHOP), 3, 2.5, Weight.of(1, 1), Weight.of(2, 2), Weight.of(4, 1))

            .item(() -> new ItemStack(Material.QUARTZ), 2, 5.0, Weight.of(1, 1), Weight.of(2, 2), Weight.of(4, 1))
            .item(() -> new ItemStack(Material.GLOWSTONE_DUST), 2, 5.0, Weight.of(2, 1), Weight.of(4, 2), Weight.of(8, 1))
            .item(() -> new ItemStack(Material.NETHER_WART), 2, 2.5, Weight.of(2, 1), Weight.of(4, 2), Weight.of(8, 1))

            .item(() -> new ItemStack(Material.CRIMSON_ROOTS), 1, 2.5, true)
            .item(() -> new ItemStack(Material.WEEPING_VINES), 1, 2.5, true)
            .item(() -> new ItemStack(Material.CHAIN), 1, 5.0, true)
            .item(() -> new ItemStack(Material.LANTERN), 1, 2.5, true)
            .item(() -> new ItemStack(Material.SOUL_LANTERN), 2, 1.5, true)

            .item(() -> new ItemStack(Material.WARPED_FUNGUS_ON_A_STICK), 2, 1.0)
            .item(() -> new ItemStack(Material.LAVA_BUCKET), 2, 10.0)

            .build();

    @NotNull
    public static final LootDigest BASTION_LOOT = LootDigest.builder("SCORCHED_TERRORSCAPE_BASTION", Weight.of(6), Weight.of(8), Weight.of(10))
            .item(() -> new ItemStack(Material.LODESTONE), 3, 1.25)
            .item(() -> new ItemStack(Material.IRON_BLOCK), 4, 1.0, Weight.of(1, 4), Weight.of(2, 2))
            .item(() -> new ItemStack(Material.GOLD_BLOCK), 5, 1.0, Weight.of(1, 4), Weight.of(2, 2))
            .item(() -> new ItemStack(Material.BELL), 3, 1.25)
            .item(() -> new ItemStack(Material.OBSIDIAN), 3, 1.25, Weight.of(1, 1), Weight.of(2, 2), Weight.of(4, 2), Weight.of(8, 1))
            .item(() -> new ItemStack(Material.CRYING_OBSIDIAN), 4, 1.0, Weight.of(1, 4), Weight.of(2, 1))

            .item(() -> new ItemStack(Material.GOLDEN_APPLE), 3, 1.25, Weight.of(1, 2), Weight.of(2, 1))
            .item(() -> new ItemStack(Material.ENCHANTED_GOLDEN_APPLE), 6, 0.75, Weight.of(1, 1))
            .item(() -> new ItemStack(Material.GOLDEN_CARROT), 3, 1.25, Weight.of(1, 2), Weight.of(2, 1))
            .item(() -> new ItemStack(Material.GLISTERING_MELON_SLICE), 3, 1.25, Weight.of(1, 2), Weight.of(2, 1))

            .item(() -> new ItemStack(Material.IRON_INGOT), 3, 3.75, Weight.of(1, 1), Weight.of(2, 2), Weight.of(4, 1))
            .item(() -> new ItemStack(Material.IRON_NUGGET), 2, 2.5, Weight.of(4, 1), Weight.of(8, 2), Weight.of(12, 2), Weight.of(16, 1))
            .item(() -> new ItemStack(Material.GOLD_INGOT), 4, 3.75, Weight.of(1, 1), Weight.of(2, 2), Weight.of(4, 1))
            .item(() -> new ItemStack(Material.GOLD_NUGGET), 3, 2.5, Weight.of(4, 1), Weight.of(8, 2), Weight.of(12, 2), Weight.of(16, 1))
            .item(() -> new ItemStack(Material.DIAMOND), 5, 2.5, Weight.of(1, 2), Weight.of(2, 1))
            .item(() -> new ItemStack(Material.NETHERITE_SCRAP), 6, 0.625, Weight.of(1, 4), Weight.of(2, 1))
            .item(() -> new ItemStack(Material.NETHERITE_INGOT), 7, 0.3125, Weight.of(1, 4), Weight.of(2, 1))

            .item(new ItemBuilder(Material.GOLDEN_AXE)
                    .addQuantity(2, 1.0).addQuantity(3, 2.0)
                    .addWeightedEnchant(Enchantment.DIG_SPEED, Weight.of(2, 4.0), Weight.of(3, 2.0), Weight.of(4, 1.0))
                    .addWeightedEnchant(Enchantment.LOOT_BONUS_BLOCKS, Weight.of(1, 2.0), Weight.of(2, 1.0))
                    .addWeightedEnchant(Enchantment.MENDING, Weight.of(1, 1.0))
                    .asStackSupplier(), 6, 0.1)
            .item(new ItemBuilder(Material.GOLDEN_HOE)
                    .addQuantity(2, 1.0).addQuantity(3, 2.0)
                    .addWeightedEnchant(Enchantment.DIG_SPEED, Weight.of(2, 4.0), Weight.of(3, 2.0), Weight.of(4, 1.0))
                    .addWeightedEnchant(Enchantment.LOOT_BONUS_BLOCKS, Weight.of(2, 2.0), Weight.of(3, 1.0))
                    .addWeightedEnchant(Enchantment.MENDING, Weight.of(1, 1.0))
                    .asStackSupplier(), 6, 0.1)
            .item(new ItemBuilder(Material.GOLDEN_PICKAXE)
                    .addQuantity(2, 1.0).addQuantity(3, 2.0)
                    .addWeightedEnchant(Enchantment.DIG_SPEED, Weight.of(2, 4.0), Weight.of(3, 2.0), Weight.of(4, 1.0))
                    .addWeightedEnchant(Enchantment.LOOT_BONUS_BLOCKS, Weight.of(1, 2.0), Weight.of(2, 1.0))
                    .addWeightedEnchant(Enchantment.MENDING, Weight.of(1, 1.0))
                    .asStackSupplier(), 6, 0.1)
            .item(new ItemBuilder(Material.GOLDEN_SHOVEL)
                    .addQuantity(2, 1.0).addQuantity(3, 2.0)
                    .addWeightedEnchant(Enchantment.DIG_SPEED, Weight.of(2, 4.0), Weight.of(3, 2.0), Weight.of(4, 1.0))
                    .addWeightedEnchant(Enchantment.LOOT_BONUS_BLOCKS, Weight.of(2, 2.0), Weight.of(3, 1.0))
                    .addWeightedEnchant(Enchantment.MENDING, Weight.of(1, 1.0))
                    .asStackSupplier(), 6, 0.1)
            .item(new ItemBuilder(Material.GOLDEN_SWORD)
                    .addQuantity(3, 1.0).addQuantity(4, 2.0).addQuantity(5, 1.0)
                    .addWeightedEnchant(Enchantment.DAMAGE_ALL, Weight.of(2, 2.0), Weight.of(3, 1.0))
                    .addWeightedEnchant(Enchantment.DAMAGE_UNDEAD, Weight.of(1, 2.0), Weight.of(2, 1.0))
                    .addWeightedEnchant(Enchantment.DAMAGE_ARTHROPODS, Weight.of(1, 2.0), Weight.of(2, 1.0))
                    .addWeightedEnchant(Enchantment.LOOT_BONUS_MOBS, Weight.of(1, 2.0), Weight.of(2, 1.0))
                    .addWeightedEnchant(Enchantment.MENDING, Weight.of(1, 1.0))
                    .asStackSupplier(), 6, 0.1)
            .item(new ItemBuilder(Material.GOLDEN_AXE)
                    .addQuantity(3, 1.0).addQuantity(4, 2.0).addQuantity(5, 1.0)
                    .addWeightedEnchant(Enchantment.DAMAGE_ALL, Weight.of(2, 2.0), Weight.of(3, 1.0))
                    .addWeightedEnchant(Enchantment.DAMAGE_UNDEAD, Weight.of(1, 2.0), Weight.of(2, 1.0))
                    .addWeightedEnchant(Enchantment.DAMAGE_ARTHROPODS, Weight.of(1, 2.0), Weight.of(2, 1.0))
                    .addWeightedEnchant(Enchantment.LOOT_BONUS_MOBS, Weight.of(1, 2.0), Weight.of(2, 1.0))
                    .addWeightedEnchant(Enchantment.MENDING, Weight.of(1, 1.0))
                    .asStackSupplier(), 6, 0.1)

            .item(new ItemBuilder(Material.DIAMOND_AXE).asStackSupplier(), 6, 0.1)
            .item(new ItemBuilder(Material.DIAMOND_HOE).asStackSupplier(), 6, 0.1)
            .item(new ItemBuilder(Material.DIAMOND_PICKAXE).asStackSupplier(), 6, 0.1)
            .item(new ItemBuilder(Material.DIAMOND_SHOVEL).asStackSupplier(), 6, 0.1)
            .item(new ItemBuilder(Material.DIAMOND_SWORD).asStackSupplier(), 6, 0.1)

            .item(new ItemBuilder(Material.CROSSBOW)
                    .addQuantity(0, 2.0).addQuantity(1, 2.0).addQuantity(2, 1.0)
                    .addWeightedEnchant(Enchantment.PIERCING, Weight.of(1, 4.0), Weight.of(2, 2.0), Weight.of(3, 1.0))
                    .addWeightedEnchant(Enchantment.QUICK_CHARGE, Weight.of(1, 2.0), Weight.of(2, 1.0))
                    .asStackSupplier(), 3, 1.5)
            .item(() -> new ItemStack(Material.ARROW), 1, 2.5, Weight.of(1, 1), Weight.of(2, 2), Weight.of(4, 2), Weight.of(8, 1))
            .item(() -> new ItemStack(Material.SPECTRAL_ARROW), 3, 1.25, Weight.of(1, 2), Weight.of(2, 1))

            .item(new ItemBuilder(Material.GOLDEN_HELMET)
                    .addQuantity(2, 1.0).addQuantity(3, 2.0).addQuantity(4, 1.0)
                    .addWeightedEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, Weight.of(2, 4.0), Weight.of(3, 2.0), Weight.of(4, 1.0))
                    .addWeightedEnchant(Enchantment.PROTECTION_FIRE, Weight.of(2, 4.0), Weight.of(3, 2.0))
                    .addWeightedEnchant(Enchantment.THORNS, Weight.of(1, 2.0), Weight.of(2, 1.0))
                    .addWeightedEnchant(Enchantment.MENDING, Weight.of(1, 1.0))
                    .asStackSupplier(), 6, 0.1)
            .item(new ItemBuilder(Material.GOLDEN_CHESTPLATE)
                    .addQuantity(2, 1.0).addQuantity(3, 2.0).addQuantity(4, 1.0)
                    .addWeightedEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, Weight.of(2, 4.0), Weight.of(3, 2.0), Weight.of(4, 1.0))
                    .addWeightedEnchant(Enchantment.PROTECTION_FIRE, Weight.of(2, 4.0), Weight.of(3, 2.0))
                    .addWeightedEnchant(Enchantment.THORNS, Weight.of(1, 2.0), Weight.of(2, 1.0))
                    .addWeightedEnchant(Enchantment.MENDING, Weight.of(1, 1.0))
                    .asStackSupplier(), 6, 0.1)
            .item(new ItemBuilder(Material.GOLDEN_LEGGINGS)
                    .addQuantity(2, 1.0).addQuantity(3, 2.0).addQuantity(4, 1.0)
                    .addWeightedEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, Weight.of(2, 4.0), Weight.of(3, 2.0), Weight.of(4, 1.0))
                    .addWeightedEnchant(Enchantment.PROTECTION_FIRE, Weight.of(2, 4.0), Weight.of(3, 2.0))
                    .addWeightedEnchant(Enchantment.THORNS, Weight.of(1, 2.0), Weight.of(2, 1.0))
                    .addWeightedEnchant(Enchantment.MENDING, Weight.of(1, 1.0))
                    .asStackSupplier(), 6, 0.1)
            .item(new ItemBuilder(Material.GOLDEN_BOOTS)
                    .addQuantity(2, 1.0).addQuantity(3, 2.0).addQuantity(4, 2.0).addQuantity(5, 1.0)
                    .addWeightedEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, Weight.of(2, 4.0), Weight.of(3, 2.0), Weight.of(4, 1.0))
                    .addWeightedEnchant(Enchantment.PROTECTION_FIRE, Weight.of(2, 4.0), Weight.of(3, 2.0))
                    .addWeightedEnchant(Enchantment.THORNS, Weight.of(1, 2.0), Weight.of(2, 1.0))
                    .addWeightedEnchant(Enchantment.MENDING, Weight.of(1, 1.0))
                    .addWeightedEnchant(Enchantment.PROTECTION_FALL, Weight.of(2, 4.0), Weight.of(3, 2.0))
                    .asStackSupplier(), 6, 0.1)

            .item(() -> new ItemStack(Material.DIAMOND_HELMET), 6, 0.1)
            .item(() -> new ItemStack(Material.DIAMOND_CHESTPLATE), 6, 0.1)
            .item(() -> new ItemStack(Material.DIAMOND_LEGGINGS), 6, 0.1)
            .item(() -> new ItemStack(Material.DIAMOND_BOOTS), 6, 0.1)

            .item(() -> new ItemStack(Material.SADDLE), 3, 0.25)
            .item(() -> new ItemStack(Material.IRON_HORSE_ARMOR), 3, 0.25)
            .item(() -> new ItemStack(Material.GOLDEN_HORSE_ARMOR), 4, 0.1875)
            .item(() -> new ItemStack(Material.DIAMOND_HORSE_ARMOR), 5, 0.125)

            .item(() -> new ItemStack(Material.EXPERIENCE_BOTTLE), 3, 1.5, Weight.of(4, 1), Weight.of(8, 2), Weight.of(12, 2), Weight.of(16, 1))
            .item(() -> new ItemStack(Material.BOOK), 2, 1.5, Weight.of(1, 1), Weight.of(2, 2), Weight.of(4, 2), Weight.of(8, 1))
            .item(new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.DURABILITY, Weight.of(1, 2), Weight.of(2, 1)).asStackSupplier(), 4, 0.5)
            .item(new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.PROTECTION_EXPLOSIONS, Weight.of(1, 4), Weight.of(2, 2), Weight.of(3, 1)).asStackSupplier(), 4, 0.5)
            .item(new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.PROTECTION_FIRE, Weight.of(1, 2), Weight.of(2, 1)).asStackSupplier(), 4, 0.5)
            .item(new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, Weight.of(1, 4), Weight.of(2, 2), Weight.of(3, 1)).asStackSupplier(), 4, 0.5)
            .item(new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.SOUL_SPEED, Weight.of(1, 2), Weight.of(2, 1)).asStackSupplier(), 4, 0.5)
            .item(new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.FIRE_ASPECT, Weight.of(1, 2), Weight.of(2, 1)).asStackSupplier(), 4, 0.5)
            .item(new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.LOOT_BONUS_MOBS, Weight.of(1, 4), Weight.of(2, 2), Weight.of(3, 1)).asStackSupplier(), 4, 0.5)
            .item(new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.DAMAGE_ALL, Weight.of(1, 8), Weight.of(2, 4), Weight.of(3, 2), Weight.of(4, 1)).asStackSupplier(), 4, 0.5)
            .item(new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.ARROW_FIRE, Weight.of(1, 1)).asStackSupplier(), 4, 0.5)
            .item(new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.DIG_SPEED, Weight.of(1, 8), Weight.of(2, 4), Weight.of(3, 2), Weight.of(4, 1)).asStackSupplier(), 4, 0.5)

            .item(Util.potion(PotionType.FIRE_RESISTANCE, false, false), 4, 0.25)
            .item(Util.potion(PotionType.STRENGTH, true, false), 5, 0.25)
            .item(Util.potion(PotionType.STRENGTH, false, true), 5, 0.25)
            .item(Util.splashPotion(PotionType.WEAKNESS, true, false), 5, 0.25)
            .item(Util.potion(PotionType.REGEN, false, false), 4, 0.25)

            .item(() -> new ItemStack(Material.MUSIC_DISC_PIGSTEP), 6, 0.05, true)

            .build();

    @NotNull
    public static final LootDigest REWARDS = LootDigest.builder("SCORCHED_TERRORSCAPE_REWARDS", Weight.of(0))
            .chain(LOOT, Weight.of(3))
            .chain(BASTION_LOOT, Weight.of(7))

            .build();

    @NotNull
    public static final Phase PHASE = Phase.builder().name(NAME).displayName(DISPLAY_NAME).textColor(TEXT_COLOR).color(BAR_COLOR).blocks(BLOCKS)
            .boss(BOSS)
            .bossName(BOSS_NAME)
            .rewards(REWARDS)
            .result((e, c) -> Util.spawnChest(CHEST_DISPLAY_NAME, LOOT, c).apply(e, c), Weight.of(4, 0.2))
            .result((e, c) -> Util.spawnChest(BASTION_CHEST_DISPLAY_NAME, BASTION_LOOT, c).apply(e, c), Weight.of(4, 0.2))

            .result(Util.defaultData(Material.NETHERRACK), Weight.of(1, 7.5))
            .result(Util.flamingBlock(Material.NETHERRACK, false), Weight.of(1, 1.25))
            .result(Util.defaultData(Material.GRAVEL), Weight.of(1, 1.0))
            .result(Util.defaultData(Material.GLOWSTONE), Weight.of(2, 1.0))
            .result(Util.defaultData(Material.NETHER_BRICKS), Weight.of(1, 2.5))
            .result(Util.defaultData(Material.MAGMA_BLOCK), Weight.of(2, 1.0))
            .result(Util.defaultData(Material.SOUL_SAND), Weight.of(1, 2.5))
            .result(Util.defaultData(Material.SOUL_SOIL), Weight.of(1, 2.5))
            .result(Util.flamingBlock(Material.SOUL_SOIL, true), Weight.of(1, 0.5))
            .result(Util.defaultData(Material.BONE_BLOCK), Weight.of(3, 0.5))
            .result(Util.defaultData(Material.CRIMSON_NYLIUM), Weight.of(1, 2.5))
            .result(Util.defaultData(Material.CRIMSON_STEM), Weight.of(1, 1.25))
            .result(Util.defaultData(Material.NETHER_WART_BLOCK), Weight.of(1, 1.25))
            .result(Util.defaultData(Material.SHROOMLIGHT), Weight.of(2, 0.5))
            .result(Util.defaultData(Material.BASALT), Weight.of(1, 2.5))
            .result(Util.defaultData(Material.POLISHED_BASALT), Weight.of(1, 1.25))
            .result(Util.defaultData(Material.BLACKSTONE), Weight.of(1, 1.25))
            .result(Util.defaultData(Material.GILDED_BLACKSTONE), Weight.of(2, 0.25))
            .result(Util.defaultData(Material.POLISHED_BLACKSTONE_BRICKS), Weight.of(1, 0.375))
            .result(Util.defaultData(Material.CRACKED_POLISHED_BLACKSTONE_BRICKS), Weight.of(1, 0.375))
            .result(Util.defaultData(Material.CHISELED_POLISHED_BLACKSTONE), Weight.of(1, 0.375))
            .result(Util.defaultData(Material.QUARTZ_BLOCK), Weight.of(2, 0.25))
            .result(Util.defaultData(Material.SMOOTH_QUARTZ), Weight.of(2, 0.25))
            .result(Util.defaultData(Material.NETHER_QUARTZ_ORE), Weight.of(2, 1.0))
            .result(Util.defaultData(Material.NETHER_GOLD_ORE), Weight.of(2, 1.0))
            .result(Util.defaultData(Material.ANCIENT_DEBRIS), Weight.of(5, 0.0625))
            .result(Util.defaultData(Material.GOLD_BLOCK), Weight.of(3, 0.25))
            .result(Util.defaultData(Material.OBSIDIAN), Weight.of(2, 1.25))
            .result(Util.defaultData(Material.CRYING_OBSIDIAN), Weight.of(3, 0.25))

            .result(EntityUtil.spawnPiglin(), Weight.of(1, 0.25))
            .result(EntityUtil.spawnPiglinBrute(), Weight.of(2, 0.125))
            .result(EntityUtil.spawnEntity(EntityType.ZOMBIFIED_PIGLIN), Weight.of(1, 0.125))
            .result(EntityUtil.spawnHoglin(), Weight.of(2, 0.0625))
            .result(EntityUtil.spawnEntity(EntityType.ZOGLIN), Weight.of(3, 0.0625))
            .result(EntityUtil.spawnGhast(), Weight.of(3, 0.0625))
            .result(EntityUtil.spawnEntity(EntityType.MAGMA_CUBE), Weight.of(2, 0.25))
            .result(EntityUtil.spawnEntity(EntityType.WITHER_SKELETON), Weight.of(1, 0.125))
            .result(EntityUtil.spawnEntity(EntityType.BLAZE), Weight.of(2, 0.125))
            .result(EntityUtil.spawnEntity(EntityType.STRIDER), Weight.of(2, 0.0625))
            .build();
}
