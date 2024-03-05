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
import org.bukkit.entity.Skeleton;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class YawningCavern {
    @NotNull
    public static final String NAME = "Yawning_Cavern";

    public static final int BLOCKS = 1000;

    @NotNull
    public static final String DISPLAY_NAME = "Yawning Cavern";

    @NotNull
    public static final TextColor TEXT_COLOR = Objects.requireNonNull(TextColor.fromHexString("#7393B3"));

    @NotNull
    public static final BossBar.Color BAR_COLOR = BossBar.Color.YELLOW;

    @NotNull
    public static final Component CHEST_DISPLAY_NAME = Component.text("Yawing Chest").color(NamedTextColor.DARK_GRAY);
    @NotNull
    public static final Component LUSH_CHEST_DISPLAY_NAME = Component.text("Lush Chest").color(NamedTextColor.DARK_GRAY);

    @NotNull
    public static final Component BOSS_NAME = Component.text("Remnant");

    @NotNull
    public static final BiFunction<@NotNull BlockBreakEvent, @NotNull Player, @NotNull Collection<@NotNull LivingEntity>> BOSS = (e, t) -> {
        val spawnLocation = e.getBlock().getLocation().add(0, 2, 0);
        val boss = (Skeleton) e.getBlock().getWorld().spawnEntity(spawnLocation, EntityType.SKELETON);
        val maxHealth = boss.getAttribute(Attribute.GENERIC_MAX_HEALTH);

        assert maxHealth != null;
        maxHealth.setBaseValue(50);

        boss.setShouldBurnInDay(false);
        boss.setHealth(50);
        boss.getEquipment().clear();
        boss.getEquipment().setHelmet(new ItemStack(Material.LEATHER_HELMET));
        boss.getEquipment().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));

        boss.getEquipment().setItemInMainHand(new ItemBuilder(Material.BOW).addEnchant(Enchantment.ARROW_KNOCKBACK, 1, false).asStack());

        return List.of(boss);
    };

    @NotNull
    public static final LootDigest LOOT = LootDigest.builder("YAWNING_CAVERN", Weight.of(3), Weight.of(6), Weight.of(9))
            .item(() -> new ItemStack(Material.TORCH), 1, 3.0, Weight.of(2, 1), Weight.of(4, 2), Weight.of(8, 1))
            .item(() -> new ItemStack(Material.COAL), 2, 1.25, Weight.of(1, 4), Weight.of(2, 2), Weight.of(4, 1))
            .item(() -> new ItemStack(Material.COPPER_INGOT), 2, 0.9, Weight.of(1, 4), Weight.of(2, 1))
            .item(() -> new ItemStack(Material.IRON_INGOT), 3, 1.0, Weight.of(1, 4), Weight.of(2, 1))
            .item(() -> new ItemStack(Material.AMETHYST_SHARD), 3, 0.75, Weight.of(1, 4), Weight.of(2, 1))

            .item(() -> new ItemStack(Material.BONE), 1, 3.0, true, Weight.of(1, 1), Weight.of(2, 2), Weight.of(4, 1))
            .item(() -> new ItemStack(Material.ROTTEN_FLESH), 1, 3.0, true, Weight.of(1, 1), Weight.of(2, 2), Weight.of(4, 1))
            .item(() -> new ItemStack(Material.STRING), 1, 3.0, true, Weight.of(1, 1), Weight.of(2, 2), Weight.of(4, 1))

            .item(() -> new ItemStack(Material.WHEAT), 2, 4.0, Weight.of(2, 1), Weight.of(4, 2), Weight.of(8, 1))
            .item(() -> new ItemStack(Material.BREAD), 2, 2.0, Weight.of(1, 1), Weight.of(2, 2), Weight.of(4, 1))

            .item(() -> new ItemStack(Material.NAME_TAG), 3, 0.5)

            .item(() -> new ItemStack(Material.IRON_PICKAXE), 4, 0.125)
            .item(() -> new ItemStack(Material.IRON_SHOVEL), 4, 0.125)
            .item(() -> new ItemStack(Material.IRON_SWORD), 4, 0.125)

            .item(() -> new ItemStack(Material.MUSIC_DISC_BLOCKS), 4, 0.025, true)

            .build();

    @NotNull
    public static final LootDigest LUSH_LOOT = LootDigest.builder("YAWNING_CAVERN_LUSH", Weight.of(3), Weight.of(6))
            .item(() -> new ItemStack(Material.AZALEA), 1, 0.75, true)
            .item(() -> new ItemStack(Material.FLOWERING_AZALEA), 1, 0.5, true)
            .item(() -> new ItemStack(Material.GLOW_BERRIES), 2, 3.0, true, Weight.of(1, 1), Weight.of(2, 2), Weight.of(4, 1))
            .item(() -> new ItemStack(Material.SPORE_BLOSSOM), 1, 0.5, true)
            .item(() -> new ItemStack(Material.SMALL_DRIPLEAF), 1, 0.75, true)
            .item(() -> new ItemStack(Material.BIG_DRIPLEAF), 1, 0.5, true)
            .item(() -> new ItemStack(Material.GLOW_LICHEN), 1, 2.0, true)

            .build();

    @NotNull
    public static final LootDigest REWARDS = LootDigest.builder("YAWNING_CAVERN_REWARDS", Weight.of(0), Weight.of(1))
            .item(() -> new ItemStack(Material.LAVA_BUCKET))

            .chain(LOOT, Weight.of(6))

            .build();

    @NotNull
    public static final Phase PHASE = Phase.builder().name(NAME).displayName(DISPLAY_NAME).textColor(TEXT_COLOR).color(BAR_COLOR).blocks(BLOCKS)
            .boss(BOSS)
            .bossName(BOSS_NAME)
            .rewards(REWARDS)
            .result((e, c) -> Util.spawnChest(CHEST_DISPLAY_NAME, LOOT, c).apply(e, c), Weight.of(4, 0.45))
            .result((e, c) -> Util.spawnChest(LUSH_CHEST_DISPLAY_NAME, LUSH_LOOT, c).apply(e, c), Weight.of(3, 0.1))
            .result(Util.defaultData(Material.STONE), Weight.of(1, 16.0))
            .result(Util.defaultData(Material.DIRT), Weight.of(1, (5d / 3)))
            .result(Util.defaultData(Material.GRANITE), Weight.of(1, 1.0))
            .result(Util.defaultData(Material.DIORITE), Weight.of(1, 1.0))
            .result(Util.defaultData(Material.ANDESITE), Weight.of(1, 1.0))
            .result(Util.defaultData(Material.GRAVEL), Weight.of(1, (4d / 3)))
            .result(Util.defaultData(Material.COAL_ORE), Weight.of(2, 1.0))
            .result(Util.defaultData(Material.IRON_ORE), Weight.of(3, (5d / 6)))
            .result(Util.defaultData(Material.COPPER_ORE), Weight.of(2, (2d / 3)))
            .result(Util.defaultData(Material.CALCITE), Weight.of(3, 0.5))
            .result(Util.defaultData(Material.AMETHYST_BLOCK), Weight.of(3, 0.5))
            .result(Util.defaultData(Material.ROOTED_DIRT), Weight.of(1, 1.0))
            .result(Util.defaultData(Material.MOSS_BLOCK), Weight.of(1, 1.0))
            .result(Util.leaves(Material.AZALEA_LEAVES), Weight.of(1, (2d / 3)))
            .result(Util.leaves(Material.FLOWERING_AZALEA_LEAVES), Weight.of(1, (2d / 3)))
            .result(Util.defaultData(Material.CLAY), Weight.of(1, (2d / 3)))

            .result(EntityUtil.spawnEntity(EntityType.BAT), Weight.of(1, 0.5))
            .result(EntityUtil.spawnZombie(), Weight.of(2, 0.25))
            .result(EntityUtil.spawnSkeleton(), Weight.of(2, 0.25))
            .result(EntityUtil.spawnEntity(EntityType.AXOLOTL), Weight.of(4, (1d / 30)))

            .build();
}
