package io.hyleo.obsb.phases;

import io.hyleo.obsb.OneblockSkyblock;
import io.hyleo.obsb.api.LootDigest;
import io.hyleo.obsb.api.Phase;
import io.hyleo.obsb.api.Weight;
import io.hyleo.obsb.listeners.ProgressListener;
import io.hyleo.obsb.util.EntityUtil;
import io.hyleo.obsb.util.ItemBuilder;
import io.hyleo.obsb.util.Util;
import lombok.NoArgsConstructor;
import lombok.val;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class GroundZero {
    @NotNull
    public static final String NAME = "Ground_Zero";

    public static final int BLOCKS = 750;
    @NotNull
    public static final String DISPLAY_NAME = "Ground Zero";
    @NotNull
    public static final TextColor TEXT_COLOR = Objects.requireNonNull(TextColor.fromHexString("#DA70D6"));

    @NotNull
    public static final BossBar.Color BAR_COLOR = BossBar.Color.PURPLE;
    @NotNull
    public static final Component CHEST_DISPLAY_NAME = Component.text("Cipher chest").color(NamedTextColor.DARK_GRAY);

    @NotNull
    public static final Component BOSS_NAME = Component.text("The Warden");

    @NotNull
    public static final BiFunction<@NotNull BlockBreakEvent, @NotNull Player, @NotNull Collection<@NotNull LivingEntity>> BOSS = (e, t) -> {
        val spawnLocation = e.getBlock().getLocation().add(0.5, 2.0, 0.5);
        val boss = (Warden) e.getBlock().getWorld().spawnEntity(spawnLocation, EntityType.WARDEN);

        val maxHealth = boss.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        val attackDamage = boss.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE);

        assert maxHealth != null;
        assert attackDamage != null;

        maxHealth.setBaseValue(1200);
        boss.setHealth(600);
        attackDamage.setBaseValue(15);

        boss.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 1));

        return List.of(boss);
    };

    @NotNull
    public static final BiConsumer<EntityDeathEvent, Player> BOSS_DEATH = (e, t) -> {
        val boss = e.getEntity();

        spawnFireworks(ProgressListener.bosses.get(boss).getOneblock());
    };

    @NotNull
    public static final Location[] fireworkLocations = new Location[]{
            new Location(null, 1.5, 1, 1.5),
            new Location(null, 1.5, 1, -0.5),
            new Location(null, -0.5, 1, -0.5),
            new Location(null, -0.5, 1, 1.5),
    };

    public static void spawnFireworks(Location location) {
        new BukkitRunnable() {
            Random random = new Random();
            int fireworks = 0;
            int index = 0;

            @Override
            public void run() {
                if (fireworks >= 16) {
                    this.cancel();
                }

                Location offsets = fireworkLocations[index].clone();
                offsets.setWorld(location.getWorld());
                Firework firework = (Firework) location.getWorld().spawnEntity(location.clone().add(offsets), EntityType.FIREWORK);
                FireworkMeta meta = firework.getFireworkMeta();
                meta.setPower(2);
                meta.addEffect(FireworkEffect.builder().with(FireworkEffect.Type.BALL_LARGE).withColor(Color.fromRGB(random.nextInt(256), random.nextInt(256), random.nextInt(256))).withFade(Color.WHITE).build());
                firework.setFireworkMeta(meta);

                index = index >= 4 ? 0 : index++;
                fireworks++;
            }
        }.runTaskTimer(OneblockSkyblock.getInstance(), 0, 20);
    }

    @NotNull
    public static final LootDigest LOOT = LootDigest.builder("THE_END", Weight.of(6), Weight.of(9), Weight.of(12))
            .item(() -> new ItemStack(Material.ENDER_PEARL), 3, 2.5, Weight.of(1, 1), Weight.of(2, 2), Weight.of(4, 1))
            .item(() -> new ItemStack(Material.SHULKER_SHELL), 4, 2.5, Weight.of(1, 1), Weight.of(2, 1))
            .item(() -> new ItemStack(Material.PHANTOM_MEMBRANE), 4, 2.5, Weight.of(1, 1), Weight.of(2, 1))
            .item(() -> new ItemStack(Material.DRAGON_BREATH), 5, 2.5, Weight.of(1, 1))

            .item(() -> new ItemStack(Material.BEETROOT), 1, 3.0, Weight.of(1, 1), Weight.of(2, 2), Weight.of(4, 1))
            .item(() -> new ItemStack(Material.BEETROOT_SEEDS), 1, 1.5, Weight.of(1, 1), Weight.of(2, 2))
            .item(() -> new ItemStack(Material.CHORUS_FRUIT), 3, 2.5, Weight.of(1, 1), Weight.of(2, 2), Weight.of(4, 1))
            .item(() -> new ItemStack(Material.GOLDEN_APPLE), 4, 1.5, Weight.of(1, 1), Weight.of(2, 1))
            .item(() -> new ItemStack(Material.ENCHANTED_GOLDEN_APPLE), 6, 1.0, Weight.of(1, 1))

            .item(() -> new ItemStack(Material.IRON_INGOT), 3, 3.5, Weight.of(2, 1), Weight.of(4, 2), Weight.of(8, 1))
            .item(() -> new ItemStack(Material.GOLD_INGOT), 4, 3.0, Weight.of(2, 1), Weight.of(4, 2), Weight.of(8, 1))
            .item(() -> new ItemStack(Material.DIAMOND), 5, 2.5, Weight.of(1, 2), Weight.of(2, 2), Weight.of(4, 1))
            .item(() -> new ItemStack(Material.EMERALD), 5, 1.75, Weight.of(1, 2), Weight.of(2, 2), Weight.of(4, 1))
            .item(() -> new ItemStack(Material.NETHERITE_SCRAP), 6, 0.625, Weight.of(1, 2), Weight.of(2, 2), Weight.of(4, 1))
            .item(() -> new ItemStack(Material.NETHERITE_INGOT), 7, 0.46875, Weight.of(1, 4), Weight.of(2, 1))

            .item(() -> new ItemStack(Material.SCULK_VEIN), 1, 5.0, true)
            .item(() -> new ItemStack(Material.CHORUS_FLOWER), 1, 5.0, true)
            .item(() -> new ItemStack(Material.END_ROD), 2, 2.5)
            .item(() -> new ItemStack(Material.DRAGON_HEAD), 3, 1.25)

            .item(new ItemBuilder(Material.IRON_PICKAXE)
                    .addQuantity(0, 1.0).addQuantity(1, 2.0).addQuantity(2, 1.0)
                    .addWeightedEnchant(Enchantment.DIG_SPEED, Weight.of(1, 4.0), Weight.of(2, 2.0), Weight.of(3, 1.0))
                    .addWeightedEnchant(Enchantment.DURABILITY, Weight.of(1, 2.0), Weight.of(2, 1.0))
                    .asStackSupplier(), 6, 0.1)
            .item(new ItemBuilder(Material.IRON_SHOVEL)
                    .addQuantity(0, 1.0).addQuantity(1, 2.0).addQuantity(2, 1.0)
                    .addWeightedEnchant(Enchantment.DIG_SPEED, Weight.of(1, 4.0), Weight.of(2, 2.0), Weight.of(3, 1.0))
                    .addWeightedEnchant(Enchantment.DURABILITY, Weight.of(1, 2.0), Weight.of(2, 1.0))
                    .asStackSupplier(), 6, 0.1)
            .item(new ItemBuilder(Material.IRON_SWORD)
                    .addQuantity(0, 1.0).addQuantity(1, 2.0).addQuantity(2, 1.0)
                    .addWeightedEnchant(Enchantment.DAMAGE_ALL, Weight.of(1, 4.0), Weight.of(2, 2.0), Weight.of(3, 1.0))
                    .addWeightedEnchant(Enchantment.LOOT_BONUS_MOBS, Weight.of(1, 2.0), Weight.of(2, 1.0))
                    .asStackSupplier(), 6, 0.1)
            .item(new ItemBuilder(Material.IRON_AXE)
                    .addQuantity(0, 1.0).addQuantity(1, 2.0).addQuantity(2, 1.0)
                    .addWeightedEnchant(Enchantment.DAMAGE_ALL, Weight.of(1, 4.0), Weight.of(2, 2.0), Weight.of(3, 1.0))
                    .addWeightedEnchant(Enchantment.LOOT_BONUS_MOBS, Weight.of(1, 2.0), Weight.of(2, 1.0))
                    .asStackSupplier(), 6, 0.1)

            .item(new ItemBuilder(Material.DIAMOND_PICKAXE).addEnchant(Enchantment.VANISHING_CURSE, 1, false)
                    .addQuantity(0, 1.0).addQuantity(1, 1.0)
                    .addWeightedEnchant(Enchantment.DIG_SPEED, Weight.of(1, 2.0), Weight.of(2, 1.0))
                    .asStackSupplier(), 7, 0.05)
            .item(new ItemBuilder(Material.DIAMOND_SHOVEL).addEnchant(Enchantment.VANISHING_CURSE, 1, false)
                    .addQuantity(0, 1.0).addQuantity(1, 1.0)
                    .addWeightedEnchant(Enchantment.DIG_SPEED, Weight.of(1, 2.0), Weight.of(2, 1.0))
                    .asStackSupplier(), 7, 0.05)
            .item(new ItemBuilder(Material.DIAMOND_SWORD).addEnchant(Enchantment.VANISHING_CURSE, 1, false)
                    .addQuantity(0, 1.0).addQuantity(1, 1.0)
                    .addWeightedEnchant(Enchantment.DAMAGE_ALL, Weight.of(1, 2.0), Weight.of(2, 1.0))
                    .asStackSupplier(), 7, 0.05)
            .item(new ItemBuilder(Material.DIAMOND_AXE).addEnchant(Enchantment.VANISHING_CURSE, 1, false)
                    .addQuantity(0, 1.0).addQuantity(1, 1.0)
                    .addWeightedEnchant(Enchantment.DAMAGE_ALL, Weight.of(1, 2.0), Weight.of(2, 1.0))
                    .asStackSupplier(), 7, 0.05)

            .item(new ItemBuilder(Material.IRON_HELMET)
                    .addQuantity(0, 1.0).addQuantity(1, 2.0).addQuantity(2, 1.0)
                    .addWeightedEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, Weight.of(1, 4.0), Weight.of(2, 2.0), Weight.of(1, 1.0))
                    .addWeightedEnchant(Enchantment.THORNS, Weight.of(1, 2.0), Weight.of(2, 1.0))
                    .asStackSupplier(), 6, 0.1)
            .item(new ItemBuilder(Material.IRON_CHESTPLATE)
                    .addQuantity(0, 1.0).addQuantity(1, 2.0).addQuantity(2, 1.0)
                    .addWeightedEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, Weight.of(1, 4.0), Weight.of(2, 2.0), Weight.of(1, 1.0))
                    .addWeightedEnchant(Enchantment.THORNS, Weight.of(1, 2.0), Weight.of(2, 1.0))
                    .asStackSupplier(), 6, 0.1)
            .item(new ItemBuilder(Material.IRON_LEGGINGS)
                    .addQuantity(0, 1.0).addQuantity(1, 2.0).addQuantity(2, 1.0)
                    .addWeightedEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, Weight.of(1, 4.0), Weight.of(2, 2.0), Weight.of(1, 1.0))
                    .addWeightedEnchant(Enchantment.THORNS, Weight.of(1, 2.0), Weight.of(2, 1.0))
                    .asStackSupplier(), 6, 0.1)
            .item(new ItemBuilder(Material.IRON_BOOTS)
                    .addQuantity(0, 1.0).addQuantity(1, 2.0).addQuantity(2, 1.0)
                    .addWeightedEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, Weight.of(1, 4.0), Weight.of(2, 2.0), Weight.of(1, 1.0))
                    .addWeightedEnchant(Enchantment.THORNS, Weight.of(1, 2.0), Weight.of(2, 1.0))
                    .asStackSupplier(), 6, 0.1)

            .item(new ItemBuilder(Material.DIAMOND_HELMET)
                    .addQuantity(0, 1.0).addQuantity(1, 1.0)
                    .addWeightedEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, Weight.of(1, 2.0), Weight.of(2, 1.0))
                    .asStackSupplier(), 7, 0.05)
            .item(new ItemBuilder(Material.DIAMOND_CHESTPLATE)
                    .addQuantity(0, 1.0).addQuantity(1, 1.0)
                    .addWeightedEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, Weight.of(1, 2.0), Weight.of(2, 1.0))
                    .asStackSupplier(), 7, 0.05)
            .item(new ItemBuilder(Material.DIAMOND_LEGGINGS)
                    .addQuantity(0, 1.0).addQuantity(1, 1.0)
                    .addWeightedEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, Weight.of(1, 2.0), Weight.of(2, 1.0))
                    .asStackSupplier(), 7, 0.05)
            .item(new ItemBuilder(Material.DIAMOND_BOOTS)
                    .addQuantity(0, 1.0).addQuantity(1, 1.0)
                    .addWeightedEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, Weight.of(1, 2.0), Weight.of(2, 1.0))
                    .asStackSupplier(), 7, 0.05)

            .item(new ItemBuilder(Material.BOW)
                    .addQuantity(0, 2.0).addQuantity(1, 2.0).addQuantity(2, 2.0).addQuantity(3, 1.0)
                    .addWeightedEnchant(Enchantment.ARROW_DAMAGE, Weight.of(1, 8.0), Weight.of(2, 4.0), Weight.of(3, 2.0), Weight.of(4, 1.0))
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

            .item(() -> new ItemStack(Material.SADDLE), 3, 0.5)
            .item(() -> new ItemStack(Material.IRON_HORSE_ARMOR), 4, 0.5)
            .item(() -> new ItemStack(Material.DIAMOND_HORSE_ARMOR), 5, 0.25)

            .item(() -> new ItemStack(Material.EXPERIENCE_BOTTLE), 3, 1.25, Weight.of(4, 1), Weight.of(8, 2), Weight.of(12, 2), Weight.of(16, 1))
            .item(() -> new ItemStack(Material.BOOK), 2, 1.25, Weight.of(1, 1), Weight.of(2, 2), Weight.of(4, 2), Weight.of(8, 1))
            .item(new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.MENDING, Weight.of(1, 1)).asStackSupplier(), 4, 0.075)
            .item(new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.DURABILITY, Weight.of(1, 4), Weight.of(2, 2), Weight.of(3, 1)).asStackSupplier(), 4, 0.075)
            .item(new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.PROTECTION_EXPLOSIONS, Weight.of(1, 8), Weight.of(2, 4), Weight.of(3, 2), Weight.of(4, 1)).asStackSupplier(), 4, 0.075)
            .item(new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.PROTECTION_FALL, Weight.of(1, 8), Weight.of(2, 4), Weight.of(3, 2), Weight.of(4, 1)).asStackSupplier(), 4, 0.075)
            .item(new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.PROTECTION_FIRE, Weight.of(1, 8), Weight.of(2, 4), Weight.of(3, 2), Weight.of(4, 1)).asStackSupplier(), 4, 0.075)
            .item(new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.PROTECTION_PROJECTILE, Weight.of(1, 8), Weight.of(2, 4), Weight.of(3, 2), Weight.of(4, 1)).asStackSupplier(), 4, 0.075)
            .item(new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, Weight.of(1, 8), Weight.of(2, 4), Weight.of(3, 2), Weight.of(4, 1)).asStackSupplier(), 4, 0.075)
            .item(new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.SWIFT_SNEAK, Weight.of(1, 4), Weight.of(2, 2), Weight.of(3, 1)).asStackSupplier(), 4, 0.075)
            .item(new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.THORNS, Weight.of(1, 4), Weight.of(2, 2), Weight.of(3, 1)).asStackSupplier(), 4, 0.075)
            .item(new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.DAMAGE_ARTHROPODS, Weight.of(1, 8), Weight.of(2, 4), Weight.of(3, 2), Weight.of(4, 1)).asStackSupplier(), 4, 0.075)
            .item(new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.FIRE_ASPECT, Weight.of(1, 2), Weight.of(2, 1)).asStackSupplier(), 4, 0.075)
            .item(new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.LOOT_BONUS_MOBS, Weight.of(1, 4), Weight.of(2, 2), Weight.of(3, 1)).asStackSupplier(), 4, 0.075)
            .item(new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.KNOCKBACK, Weight.of(1, 2), Weight.of(2, 1)).asStackSupplier(), 4, 0.075)
            .item(new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.DAMAGE_ALL, Weight.of(1, 16), Weight.of(2, 8), Weight.of(3, 4), Weight.of(4, 2), Weight.of(5, 1)).asStackSupplier(), 4, 0.075)
            .item(new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.DAMAGE_UNDEAD, Weight.of(1, 4), Weight.of(2, 2), Weight.of(3, 1)).asStackSupplier(), 4, 0.075)
            .item(new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.SWEEPING_EDGE, Weight.of(1, 4), Weight.of(2, 2), Weight.of(3, 1)).asStackSupplier(), 4, 0.075)
            .item(new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.ARROW_FIRE, Weight.of(1, 1)).asStackSupplier(), 4, 0.075)
            .item(new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.ARROW_INFINITE, Weight.of(1, 1)).asStackSupplier(), 4, 0.075)
            .item(new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.MULTISHOT, Weight.of(1, 1)).asStackSupplier(), 4, 0.075)
            .item(new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.PIERCING, Weight.of(1, 8), Weight.of(2, 4), Weight.of(3, 2), Weight.of(4, 1)).asStackSupplier(), 4, 0.075)
            .item(new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.ARROW_DAMAGE, Weight.of(1, 16), Weight.of(2, 8), Weight.of(3, 4), Weight.of(4, 2), Weight.of(5, 1)).asStackSupplier(), 4, 0.075)
            .item(new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.ARROW_KNOCKBACK, Weight.of(1, 2), Weight.of(2, 1)).asStackSupplier(), 4, 0.075)
            .item(new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.QUICK_CHARGE, Weight.of(1, 4), Weight.of(2, 2), Weight.of(3, 1)).asStackSupplier(), 4, 0.075)
            .item(new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.DIG_SPEED, Weight.of(1, 16), Weight.of(2, 8), Weight.of(3, 4), Weight.of(4, 2), Weight.of(5, 1)).asStackSupplier(), 4, 0.075)
            .item(new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.LOOT_BONUS_BLOCKS, Weight.of(1, 4), Weight.of(2, 2), Weight.of(3, 1)).asStackSupplier(), 4, 0.075)
            .item(new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.SILK_TOUCH, Weight.of(1, 1)).asStackSupplier(), 4, 0.075)

            .item(() -> new ItemStack(Material.MUSIC_DISC_WARD), 7, 0.075, true)
            .item(() -> new ItemStack(Material.MUSIC_DISC_11), 7, 0.075, true)

            .build();

    @NotNull
    public static final LootDigest REWARDS = LootDigest.builder("THE_END_REWARDS", Weight.of(0))
            .item(() -> new ItemStack(Material.DRAGON_EGG))

            .chain(LOOT, Weight.of(8))
            .build();

    @NotNull
    public static final Phase PHASE = Phase.builder().name(NAME).displayName(DISPLAY_NAME).textColor(TEXT_COLOR).color(BAR_COLOR).blocks(BLOCKS)
            .boss(BOSS)
            .bossName(BOSS_NAME)
            .bossDeath(BOSS_DEATH)
            .rewards(REWARDS)
            .result((e, c) -> Util.spawnChest(CHEST_DISPLAY_NAME, LOOT, c).apply(e, c), Weight.of(4, 0.25))
            .result(Util.defaultData(Material.END_STONE), Weight.of(1, 10.0))
            .result(Util.defaultData(Material.OBSIDIAN), Weight.of(1, 1.5))
            .result(Util.defaultData(Material.CRYING_OBSIDIAN), Weight.of(2, 0.5))
            .result(Util.defaultData(Material.END_STONE_BRICKS), Weight.of(1, 1.0))
            .result(Util.defaultData(Material.PURPUR_BLOCK), Weight.of(1, 1.5))
            .result(Util.defaultData(Material.PURPUR_PILLAR), Weight.of(1, 0.5))
            .result(Util.defaultData(Material.MAGENTA_STAINED_GLASS), Weight.of(1, 0.25))
            .result(Util.defaultData(Material.PURPLE_STAINED_GLASS), Weight.of(1, 0.125))
            .result(Util.defaultData(Material.CYAN_STAINED_GLASS), Weight.of(1, 0.125))
            .result(Util.defaultData(Material.BLUE_STAINED_GLASS), Weight.of(1, 0.125))
            .result(Util.defaultData(Material.SCULK), Weight.of(1, 2.5))
            .result(Util.defaultData(Material.SCULK_SENSOR), Weight.of(2, 0.625))
            .result(Util.defaultData(Material.SCULK_CATALYST), Weight.of(3, 0.125))
            .result(Util.defaultData(Material.SCULK_SHRIEKER), Weight.of(3, 0.0625))
            .result(Util.defaultData(Material.BLUE_ICE), Weight.of(2, 2.5))
            .result(Util.defaultData(Material.ANCIENT_DEBRIS), Weight.of(5, 0.125))
            .result(Util.defaultData(Material.BONE_BLOCK), Weight.of(3, 0.5))
            .result(Util.defaultData(Material.STONE_BRICKS), Weight.of(1, 0.5))
            .result(Util.defaultData(Material.CRACKED_STONE_BRICKS), Weight.of(1, 0.25))
            .result(Util.defaultData(Material.MOSSY_STONE_BRICKS), Weight.of(1, 0.25))
            .result(Util.defaultData(Material.INFESTED_STONE_BRICKS), Weight.of(1, 0.25))
            .result(Util.defaultData(Material.INFESTED_CRACKED_STONE_BRICKS), Weight.of(1, 0.125))
            .result(Util.defaultData(Material.INFESTED_MOSSY_STONE_BRICKS), Weight.of(1, 0.125))
            .result(Util.defaultData(Material.BOOKSHELF), Weight.of(2, 0.25))

            .result(EntityUtil.spawnEntity(EntityType.ENDERMAN), Weight.of(1, 0.625))
            .result(EntityUtil.spawnEndermiteMob(), Weight.of(1, 0.25))
            .result(EntityUtil.spawnShulker(), Weight.of(2, 0.25))
            .result(EntityUtil.spawnPhantom(), Weight.of(3, 0.25))

            .result(EntityUtil.playRandomWardenSound(), Weight.of(2, 5.0))
            .result(EntityUtil.wardenPursuit(), Weight.of(3, 0.35))

            .build();
}
