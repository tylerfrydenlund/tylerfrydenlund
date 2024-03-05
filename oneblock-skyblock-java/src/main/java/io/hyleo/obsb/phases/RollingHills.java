package io.hyleo.obsb.phases;

import io.hyleo.obsb.api.LootDigest;
import io.hyleo.obsb.api.Phase;
import io.hyleo.obsb.api.Weight;
import io.hyleo.obsb.util.EntityUtil;
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
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RollingHills {
    @NotNull
    public static final String NAME = "Rolling_Hills";

    public static final int BLOCKS = 350;

    @NotNull
    public static final String DISPLAY_NAME = "Rolling Hills";

    @NotNull
    public static final TextColor TEXT_COLOR = Objects.requireNonNull(TextColor.fromHexString("#7CFC00"));

    @NotNull
    public static final BossBar.Color BAR_COLOR = BossBar.Color.GREEN;

    @NotNull
    public static final Component CHEST_DISPLAY_NAME = Component.text("Hills Chest").color(NamedTextColor.DARK_GRAY);

    @NotNull
    public static final Component BOSS_NAME = Component.text("Infected");

    @NotNull
    public static final BiFunction<@NotNull BlockBreakEvent, @NotNull Player, @NotNull Collection<@NotNull LivingEntity>> BOSS = (e, t) -> {
        val spawnLocation = e.getBlock().getLocation().add(0, 2, 0);
        val boss = (Zombie) e.getBlock().getWorld().spawnEntity(spawnLocation, EntityType.ZOMBIE);
        val maxHealth = boss.getAttribute(Attribute.GENERIC_MAX_HEALTH);

        assert maxHealth != null;
        maxHealth.setBaseValue(40);

        boss.setAdult();
        boss.setShouldBurnInDay(false);
        boss.setHealth(40);
        boss.getEquipment().clear();
        boss.getEquipment().setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE));
        boss.getEquipment().setItemInMainHand(new ItemStack(Material.WOODEN_SWORD));

        return List.of(boss);
    };

    @NotNull
    public static final LootDigest LOOT = LootDigest.builder("ROLLING_HILLS", Weight.of(3), Weight.of(6), Weight.of(9))
            .item(() -> new ItemStack(Material.OAK_LOG), 1, 10.0, Weight.of(2, 1), Weight.of(4, 2), Weight.of(6, 1))
            .item(() -> new ItemStack(Material.OAK_SAPLING), 1, 2.5, true, Weight.of(1, 4), Weight.of(2, 2))
            .item(() -> new ItemStack(Material.BIRCH_LOG), 1, 7.5, Weight.of(2, 1), Weight.of(4, 2), Weight.of(6, 1))
            .item(() -> new ItemStack(Material.WHITE_WOOL), 1, 1.5, true, Weight.of(1, 1), Weight.of(2, 2), Weight.of(4, 1))

            .item(() -> new ItemStack(Material.LEATHER), 1, 1.0, Weight.of(1, 1), Weight.of(2, 2), Weight.of(4, 1))
            .item(() -> new ItemStack(Material.RABBIT_HIDE), 1, 2.0, Weight.of(2, 1), Weight.of(4, 2), Weight.of(8, 1))
            .item(() -> new ItemStack(Material.APPLE), 1, 2.0, Weight.of(1, 1), Weight.of(2, 2), Weight.of(4, 1))
            .item(() -> new ItemStack(Material.WHEAT_SEEDS), 1, 3.0, true, Weight.of(2, 1), Weight.of(4, 2), Weight.of(8, 1))
            .item(() -> new ItemStack(Material.POTATO), 1, 2.0, Weight.of(1, 1), Weight.of(2, 2), Weight.of(4, 1))
            .item(() -> new ItemStack(Material.CARROT), 1, 2.0, Weight.of(1, 1), Weight.of(2, 2), Weight.of(4, 1))
            .item(() -> new ItemStack(Material.RABBIT), 2, 2.0, Weight.of(1, 1), Weight.of(2, 2), Weight.of(4, 1))
            .item(() -> new ItemStack(Material.BEEF), 2, 1.0, Weight.of(1, 1), Weight.of(2, 2), Weight.of(4, 1))
            .item(() -> new ItemStack(Material.MUTTON), 2, 1.5, Weight.of(1, 1), Weight.of(2, 2), Weight.of(4, 1))

            .item(() -> new ItemStack(Material.TORCH), 1, 3.0, Weight.of(2, 1), Weight.of(4, 2), Weight.of(8, 1))

            .item(() -> new ItemStack(Material.DANDELION), 1, 3.0, true)
            .item(() -> new ItemStack(Material.POPPY), 1, 3.0, true)
            .item(() -> new ItemStack(Material.AZURE_BLUET), 1, 2.0, true)
            .item(() -> new ItemStack(Material.PINK_TULIP), 1, 0.5, true)
            .item(() -> new ItemStack(Material.ORANGE_TULIP), 1, 0.5, true)
            .item(() -> new ItemStack(Material.RED_TULIP), 1, 0.5, true)
            .item(() -> new ItemStack(Material.WHITE_TULIP), 1, 0.5, true)
            .item(() -> new ItemStack(Material.OXEYE_DAISY), 1, 2.0, true)
            .item(() -> new ItemStack(Material.CORNFLOWER), 1, 2.0, true)
            .item(() -> new ItemStack(Material.SUNFLOWER), 1, 2.0, true)

            .item(() -> new ItemStack(Material.LEATHER_HELMET), 3, 1.0)
            .item(() -> new ItemStack(Material.LEATHER_CHESTPLATE), 3, 1.0)
            .item(() -> new ItemStack(Material.LEATHER_LEGGINGS), 3, 1.0)
            .item(() -> new ItemStack(Material.LEATHER_BOOTS), 3, 1.0)
            .item(() -> new ItemStack(Material.WOODEN_SWORD), 3, 1.0)
            .item(() -> new ItemStack(Material.STONE_SWORD), 4, 0.5)
            .item(() -> new ItemStack(Material.WOODEN_SHOVEL), 3, 1.0)
            .item(() -> new ItemStack(Material.STONE_SHOVEL), 4, 0.5)

            .item(() -> new ItemStack(Material.MUSIC_DISC_CHIRP), 4, 0.2, true)

            .build();

    @NotNull
    public static final LootDigest REWARDS = LootDigest.builder("ROLLING_HILLS_REWARDS", Weight.of(0))
            .item(() -> new ItemStack(Material.WATER_BUCKET))
            .item(() -> new ItemStack(Material.OAK_SAPLING, 2))
            .item(() -> new ItemStack(Material.WHEAT_SEEDS, 4))

            .chain(LOOT, Weight.of(6))

            .build();

    @NotNull
    public static final Phase PHASE = Phase.builder().name(NAME).displayName(DISPLAY_NAME).textColor(TEXT_COLOR).color(BAR_COLOR).blocks(BLOCKS)
            .boss(BOSS)
            .bossName(BOSS_NAME)
            .rewards(REWARDS)
            .result((e, c) -> Util.spawnChest(CHEST_DISPLAY_NAME, LOOT, c).apply(e, c), Weight.of(4, 0.375))

            .result(Util.defaultData(Material.GRASS_BLOCK), Weight.of(1, 15.0))
            .result(Util.defaultData(Material.OAK_LOG), Weight.of(1, 2.25))
            .result(Util.defaultData(Material.BIRCH_LOG), Weight.of(1, 1.5))
            .result(Util.defaultData(Material.GRAVEL), Weight.of(1, 1.5))
            .result(Util.defaultData(Material.SAND), Weight.of(1, 1.5))
            .result(Util.defaultData(Material.CLAY), Weight.of(1, 1.5))

            .result(EntityUtil.spawnEntity(EntityType.COW), Weight.of(2, 0.075))
            .result(EntityUtil.spawnEntity(EntityType.SHEEP), Weight.of(2, 0.075))
            .result(EntityUtil.spawnEntity(EntityType.RABBIT), Weight.of(2, 0.075))
            .result(EntityUtil.spawnSlime(), Weight.of(2, 0.075))

            .build();


}
