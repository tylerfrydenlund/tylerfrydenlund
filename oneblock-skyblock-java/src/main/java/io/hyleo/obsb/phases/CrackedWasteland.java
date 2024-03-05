package io.hyleo.obsb.phases;

import io.hyleo.obsb.OneblockSkyblock;
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
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.function.BiFunction;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CrackedWasteland {
    @NotNull
    public static final String NAME = "Cracked_Wasteland";
    public static final int BLOCKS = 1350;
    @NotNull
    public static final String DISPLAY_NAME = "Cracked Wasteland";
    @NotNull
    public static final TextColor TEXT_COLOR = Objects.requireNonNull(TextColor.fromHexString("#E35335"));

    @NotNull
    public static final BossBar.Color BAR_COLOR = BossBar.Color.YELLOW;
    @NotNull
    public static final Component CHEST_DISPLAY_NAME = Component.text("Wasteland Chest").color(NamedTextColor.DARK_GRAY);

    @NotNull
    public static final Random random = new Random();

    @NotNull
    public static final Component BOSS_NAME = Component.text("Wandering Rogue");
    @NotNull
    public static final BiFunction<@NotNull BlockBreakEvent, @NotNull Player, @NotNull Collection<@NotNull LivingEntity>> BOSS = (e, t) -> {
        val spawnLocation = e.getBlock().getLocation().add(0, 2, 0);
        val boss = (Pillager) e.getBlock().getWorld().spawnEntity(spawnLocation, EntityType.PILLAGER);

        val maxHealth = boss.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        val movementSpeed = boss.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED);

        assert maxHealth != null;
        assert movementSpeed != null;

        maxHealth.setBaseValue(48);
        boss.setHealth(48);
        movementSpeed.setBaseValue(1.0);

        boss.setCanJoinRaid(false);
        boss.getEquipment().setItemInMainHand(new ItemBuilder(Material.CROSSBOW).addEnchant(Enchantment.MULTISHOT, 1, false).asStack());

        setupInvisibility(boss);

        return List.of(boss);
    };

    public static void setupInvisibility(@NonNull Entity entity) {
        if (entity.isDead() || !(entity instanceof LivingEntity boss)) {
            return;
        }

        boss.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 300 + 20 * random.nextInt(10), 1));

        Bukkit.getScheduler().scheduleSyncDelayedTask(OneblockSkyblock.getInstance(), () -> setupInvisibility(entity), 600 + 20 * random.nextInt(20));
    }

    @NotNull
    public static final LootDigest LOOT = LootDigest.builder("CRACKED_WASTELAND", Weight.of(3), Weight.of(6), Weight.of(9))
            .item(() -> new ItemStack(Material.ACACIA_LOG), 1, 10.0, true, Weight.of(2, 1), Weight.of(4, 2), Weight.of(6, 1))
            .item(() -> new ItemStack(Material.ACACIA_SAPLING), 1, 2.5, true, Weight.of(1, 4), Weight.of(2, 2))

            .item(() -> new ItemStack(Material.LEATHER), 1, 3.5, true, Weight.of(1, 1), Weight.of(2, 2), Weight.of(4, 1))
            .item(() -> new ItemStack(Material.ROTTEN_FLESH), 1, 3.5, true, Weight.of(1, 1), Weight.of(2, 2), Weight.of(4, 1))
            .item(() -> new ItemStack(Material.BONE), 1, 3.5, true, Weight.of(1, 1), Weight.of(2, 2), Weight.of(4, 1))
            .item(() -> new ItemStack(Material.GUNPOWDER), 2, 1.75, true, Weight.of(1, 1), Weight.of(2, 2), Weight.of(4, 1))

            .item(() -> new ItemStack(Material.POTATO), 1, 1.75, Weight.of(2, 1), Weight.of(4, 2), Weight.of(8, 1))
            .item(() -> new ItemStack(Material.CARROT), 1, 1.75, Weight.of(2, 1), Weight.of(4, 2), Weight.of(8, 1))
            .item(() -> new ItemStack(Material.GOLDEN_APPLE), 4, 0.5)

            .item(() -> new ItemStack(Material.COAL), 2, 2, Weight.of(1, 1), Weight.of(2, 2), Weight.of(4, 2), Weight.of(8, 1))
            .item(() -> new ItemStack(Material.IRON_INGOT), 3, 1.5, Weight.of(1, 1), Weight.of(2, 2), Weight.of(4, 1))
            .item(() -> new ItemStack(Material.GOLD_INGOT), 4, 1.0, Weight.of(1, 1), Weight.of(2, 2), Weight.of(4, 1))

            .item(() -> new ItemStack(Material.DEAD_BUSH), 1, 5.0, true)
            .item(() -> new ItemStack(Material.RAIL), 1, 5.0, true, Weight.of(1, 4), Weight.of(2, 2), Weight.of(4, 1))
            .item(() -> new ItemStack(Material.POWERED_RAIL), 2, 2.0, true, Weight.of(1, 2), Weight.of(2, 1))
            .item(() -> new ItemStack(Material.TRIPWIRE_HOOK), 1, 2.0, true)

            .item(() -> new ItemStack(Material.BOW), 2, 2)
            .item(() -> new ItemStack(Material.ARROW), 1, 5, Weight.of(2, 1), Weight.of(4, 2), Weight.of(8, 1))

            .item(() -> new ItemStack(Material.SADDLE), 3, 0.5)
            .item(() -> new ItemStack(Material.LEAD), 3, 0.5)
            .item(() -> new ItemStack(Material.NAME_TAG), 3, 0.5)
            .item(() -> new ItemStack(Material.IRON_HORSE_ARMOR), 3, 0.5)
            .item(() -> new ItemStack(Material.GOLDEN_HORSE_ARMOR), 4, 0.25)

            .item(() -> new ItemStack(Material.EXPERIENCE_BOTTLE), 3, 1.5, Weight.of(4, 4), Weight.of(8, 2), Weight.of(12, 1))
            .item(() -> new ItemStack(Material.BOOK), 2, 1.5, Weight.of(1, 1), Weight.of(2, 2), Weight.of(4, 2), Weight.of(8, 1))
            .item(new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.DURABILITY, Weight.of(1, 1)).asStackSupplier(), 4, 0.25)
            .item(new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.PROTECTION_PROJECTILE, Weight.of(1, 1)).asStackSupplier(), 4, 0.25)
            .item(new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.ARROW_DAMAGE, Weight.of(1, 1)).asStackSupplier(), 4, 0.25)
            .item(new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.ARROW_KNOCKBACK, Weight.of(1, 1)).asStackSupplier(), 4, 0.25)
            .item(new ItemBuilder(Material.ENCHANTED_BOOK).addQuantity(1, 1.0).addWeightedEnchant(Enchantment.DIG_SPEED, Weight.of(1, 1)).asStackSupplier(), 4, 0.25)

            .item(() -> new ItemStack(Material.MUSIC_DISC_MALL), 4, 0.125, true)

            .build();

    @NotNull
    public static final LootDigest REWARDS = LootDigest.builder("CRACKED_WASTELAND_REWARDS", Weight.of(0))
            .item(() -> new ItemStack(Material.CROSSBOW))

            .chain(LOOT, Weight.of(8))

            .build();

    @NotNull
    public static final Phase PHASE = Phase.builder().name(NAME).displayName(DISPLAY_NAME).textColor(TEXT_COLOR).color(BAR_COLOR).blocks(BLOCKS)
            .boss(BOSS)
            .bossName(BOSS_NAME)
            .rewards(REWARDS)
            .result((e, c) -> Util.spawnChest(CHEST_DISPLAY_NAME, LOOT, c).apply(e, c), Weight.of(4, 0.25))

            .result(Util.defaultData(Material.RED_SAND), Weight.of(1, 5.0))
            .result(Util.defaultData(Material.RED_SANDSTONE), Weight.of(1, 2.5))
            .result(Util.defaultData(Material.TERRACOTTA), Weight.of(1, 2.5))
            .result(Util.defaultData(Material.RED_TERRACOTTA), Weight.of(1, 2.5))
            .result(Util.defaultData(Material.ORANGE_TERRACOTTA), Weight.of(1, 2.5))
            .result(Util.defaultData(Material.YELLOW_TERRACOTTA), Weight.of(1, 2.5))
            .result(Util.defaultData(Material.BROWN_TERRACOTTA), Weight.of(1, 2.5))
            .result(Util.defaultData(Material.WHITE_TERRACOTTA), Weight.of(1, 2.5))
            .result(Util.defaultData(Material.LIGHT_GRAY_TERRACOTTA), Weight.of(1, 2.5))
            .result(Util.defaultData(Material.ACACIA_LOG), Weight.of(1, 2.5))
            .result(Util.defaultData(Material.CACTUS), Weight.of(1, 1.25))
            .result(Util.defaultData(Material.COARSE_DIRT), Weight.of(1, 1.25))
            .result(Util.defaultData(Material.BONE_BLOCK), Weight.of(3, 0.875))
            .result(Util.defaultData(Material.COAL_ORE), Weight.of(2, 0.875))
            .result(Util.defaultData(Material.IRON_ORE), Weight.of(3, 0.75))
            .result(Util.defaultData(Material.GOLD_ORE), Weight.of(4, 0.625))

            .result(EntityUtil.spawnEntity(EntityType.HORSE), Weight.of(2, 0.05))
            .result(EntityUtil.spawnEntity(EntityType.LLAMA), Weight.of(3, 0.025))
            .result(EntityUtil.spawnEntity(EntityType.DONKEY), Weight.of(3, 0.025))
            .result(EntityUtil.spawnHusk(), Weight.of(2, 0.125))
            .result(EntityUtil.spawnBabyHusk(), Weight.of(3, 0.02))
            .result(EntityUtil.spawnSkeleton(), Weight.of(2, 0.15))
            .result(EntityUtil.spawnCreeper(), Weight.of(2, 0.1))
            .result(EntityUtil.spawnEntity(EntityType.PILLAGER), Weight.of(2, 0.15))
            .result(EntityUtil.spawnEntity(EntityType.VINDICATOR), Weight.of(2, 0.1))
            .result(EntityUtil.spawnZombieVillager(), Weight.of(2, 0.125))
            .result(EntityUtil.spawnBabyZombieVillager(), Weight.of(3, 0.02))
            .result(EntityUtil.spawnEntity(EntityType.WANDERING_TRADER), Weight.of(4, 0.0125))
            .build();
}
