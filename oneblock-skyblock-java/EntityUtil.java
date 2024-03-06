package io.hyleo.obsb.util;

import io.hyleo.obsb.OneblockSkyblock;
import io.hyleo.obsb.listeners.UpgradedCaveSpiderListener;
import io.hyleo.obsb.listeners.bossfights.WitchFightListener;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import lombok.experimental.UtilityClass;
import lombok.val;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.loot.LootContext;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Random;
import java.util.function.BiFunction;

@UtilityClass
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
public class EntityUtil {
    Random random = new Random();
    Vector spawnVector = new Vector(0, 1.5, 0);

    /**
     * Spawns a vanilla entity above location of the block break event
     *
     * @param type the type of entity to spawn
     * @return a function that spawns the entity
     */
    @NotNull
    public BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, @Nullable BlockData> spawnEntity(@NonNull EntityType type) {
        return (e, c) -> {
            val spawnLocation = e.getBlock().getLocation().add(spawnVector);
            e.getBlock().getWorld().spawnEntity(spawnLocation, type);
            return null;
        };
    }

    /**
     * Spawns a slime with a size of 2 above location of the block break event
     *
     * @return a function that spawns the entity
     */
    @NotNull
    public BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, @Nullable BlockData> spawnSlime() {
        return (e, c) -> {
            val spawnLocation = e.getBlock().getLocation().add(spawnVector);
            val slime = (Slime) e.getBlock().getWorld().spawnEntity(spawnLocation, EntityType.SLIME);

            slime.setSize(2);

            return null;
        };
    }

    /**
     * Spawns a zombie above location of the block break event
     * The zombie is set to not burn in daylight and is an adult
     *
     * @return a function that spawns the entity
     */
    @NotNull
    public BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, @Nullable BlockData> spawnZombie() {
        return (e, c) -> {
            val spawnLocation = e.getBlock().getLocation().add(spawnVector);
            val zombie = (Zombie) e.getBlock().getWorld().spawnEntity(spawnLocation, EntityType.ZOMBIE);

            zombie.setShouldBurnInDay(false);
            zombie.setAdult();

            return null;
        };
    }

    /**
     * Spawns a baby zombie above location of the block break event
     * The zombie is set to not burn in daylight and is a baby
     *
     * @return a function that spawns the entity
     */
    @NotNull
    public BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, @Nullable BlockData> spawnBabyZombie() {
        return (e, c) -> {
            val spawnLocation = e.getBlock().getLocation().add(spawnVector);
            val zombie = (Zombie) e.getBlock().getWorld().spawnEntity(spawnLocation, EntityType.ZOMBIE);

            zombie.setShouldBurnInDay(false);
            zombie.setBaby();

            return null;
        };
    }

    /**
     * Spawns a skeleton above location of the block break event
     * The skeleton is set to not burn in daylight
     *
     * @return a function that spawns the entity
     */
    @NotNull
    public BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, @Nullable BlockData> spawnSkeleton() {
        return (e, c) -> {
            val spawnLocation = e.getBlock().getLocation().add(spawnVector);
            val skeleton = (Skeleton) e.getBlock().getWorld().spawnEntity(spawnLocation, EntityType.SKELETON);

            skeleton.setShouldBurnInDay(false);

            return null;
        };
    }

    /**
     * Spawns a creeper above location of the block break event
     * The creeper has its max fuse ticks set and its fuse ticks
     * to prevent it from exploding on spawn
     *
     * @return a function that spawns the entity
     */
    @NotNull
    public BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, @Nullable BlockData> spawnCreeper() {
        return (e, c) -> {
            val spawnLocation = e.getBlock().getLocation().add(spawnVector);
            val creeper = (Creeper) e.getBlock().getWorld().spawnEntity(spawnLocation, EntityType.CREEPER);

            creeper.setMaxFuseTicks(60);
            creeper.setFuseTicks(0);

            return null;
        };
    }

    /**
     * Spawns a husk above location of the block break event
     * The husk is set to be an adult
     *
     * @return a function that spawns the entity
     */
    @NotNull
    public BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, @Nullable BlockData> spawnHusk() {
        return (e, c) -> {
            val spawnLocation = e.getBlock().getLocation().add(spawnVector);
            val husk = (Husk) e.getBlock().getWorld().spawnEntity(spawnLocation, EntityType.HUSK);

            husk.setAdult();

            return null;
        };
    }

    /**
     * Spawns a baby husk above location of the block break event
     * The husk is set to be a baby
     *
     * @return a function that spawns the entity
     */
    @NotNull
    public BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, @Nullable BlockData> spawnBabyHusk() {
        return (e, c) -> {
            val spawnLocation = e.getBlock().getLocation().add(spawnVector);
            val husk = (Husk) e.getBlock().getWorld().spawnEntity(spawnLocation, EntityType.HUSK);

            husk.setBaby();

            return null;
        };
    }

    /**
     * Spawns a zombie villager above location of the block break event
     * The zombie villager will not burn in daylight is set to be an adult
     *
     * @return a function that spawns the entity
     */
    @NotNull
    public BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, @Nullable BlockData> spawnZombieVillager() {
        return (e, c) -> {
            val spawnLocation = e.getBlock().getLocation().add(spawnVector);
            val zombieVillager = (ZombieVillager) e.getBlock().getWorld().spawnEntity(spawnLocation, EntityType.ZOMBIE_VILLAGER);

            zombieVillager.setShouldBurnInDay(false);
            zombieVillager.setAdult();

            return null;
        };
    }

    /**
     * Spawns a baby zombie villager above location of the block break event
     * The zombie villager will not burn in daylight is set to be a baby
     *
     * @return a function that spawns the entity
     */
    @NotNull
    public BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, @Nullable BlockData> spawnBabyZombieVillager() {
        return (e, c) -> {
            val spawnLocation = e.getBlock().getLocation().add(spawnVector);
            val zombieVillager = (ZombieVillager) e.getBlock().getWorld().spawnEntity(spawnLocation, EntityType.ZOMBIE_VILLAGER);

            zombieVillager.setShouldBurnInDay(false);
            zombieVillager.setBaby();

            return null;
        };
    }

    /**
     * Spawns a drowned above location of the block break event
     * The drowned will not burn in daylight is set to be an adult
     *
     * @return a function that spawns the entity
     */
    @NotNull
    public BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, @Nullable BlockData> spawnDrowned() {
        return (e, c) -> {
            val spawnLocation = e.getBlock().getLocation().add(spawnVector);
            val drowned = (Drowned) e.getBlock().getWorld().spawnEntity(spawnLocation, EntityType.DROWNED);

            drowned.setShouldBurnInDay(false);
            drowned.setAdult();

            return null;
        };
    }

    /**
     * Spawns a baby drowned above location of the block break event
     * The drowned will not burn in daylight is set to be a baby
     *
     * @return a function that spawns the entity
     */
    @NotNull
    public BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, @Nullable BlockData> spawnBabyDrowned() {
        return (e, c) -> {
            val spawnLocation = e.getBlock().getLocation().add(spawnVector);
            val drowned = (Drowned) e.getBlock().getWorld().spawnEntity(spawnLocation, EntityType.DROWNED);

            drowned.setShouldBurnInDay(false);
            drowned.setBaby();

            return null;
        };
    }

    /**
     * Spawns a frog above location of the block break event
     * The frog will be of the specified variant
     *
     * @param variant the variant of the frog to spawn
     * @return a function that spawns the entity
     */
    @NotNull
    public BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, @Nullable BlockData> spawnFrog(@NonNull Frog.Variant variant) {
        return (e, c) -> {
            val spawnLocation = e.getBlock().getLocation().add(spawnVector);
            val entity = (Frog) e.getBlock().getWorld().spawnEntity(spawnLocation, EntityType.FROG);
            entity.setVariant(variant);
            return null;
        };
    }

    /**
     * Spawns a big slime above location of the block break event
     * The slime can be of any size between 3 and 8
     *
     * @return a function that spawns the entity
     */
    @NotNull
    public BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, @Nullable BlockData> spawnBigSlime() {
        return (e, c) -> {
            val spawnLocation = e.getBlock().getLocation().add(spawnVector);
            val slime = (Slime) e.getBlock().getWorld().spawnEntity(spawnLocation, EntityType.SLIME);

            slime.setSize(random.nextInt(3, 9));
            return null;
        };
    }

    /**
     * Spawns an upgraded zombie above location of the block break event
     * The zombie will not burn in daylight and will be an adult
     * The zombie will have an iron chestplate with protection 3 and a diamond sword
     *
     * @return a function that spawns the entity
     */
    @NotNull
    public BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, @Nullable BlockData> spawnUpgradedZombie1() {
        return (e, c) -> {
            val spawnLocation = e.getBlock().getLocation().add(spawnVector);
            val zombie = (Zombie) e.getBlock().getWorld().spawnEntity(spawnLocation, EntityType.ZOMBIE);

            zombie.setShouldBurnInDay(false);
            zombie.setAdult();
            zombie.getEquipment().clear();
            zombie.getEquipment().setChestplate(new ItemBuilder(Material.IRON_CHESTPLATE).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3, false).asStack());
            zombie.getEquipment().setItemInMainHand(new ItemBuilder(Material.DIAMOND_SWORD).asStack());

            return null;
        };
    }

    /**
     * Spawns an upgraded zombie above location of the block break event
     * The zombie will not burn in daylight and will be an adult
     * The zombie will have an iron chestplate and leggings with protection 3 and a diamond sword with sharpness 2 and knockback 1
     *
     * @return a function that spawns the entity
     */

    @NotNull
    public BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, @Nullable BlockData> spawnUpgradedZombie2() {
        return (e, c) -> {
            val spawnLocation = e.getBlock().getLocation().add(spawnVector);
            val zombie = (Zombie) e.getBlock().getWorld().spawnEntity(spawnLocation, EntityType.ZOMBIE);

            zombie.setShouldBurnInDay(false);
            zombie.setAdult();
            zombie.getEquipment().clear();
            zombie.getEquipment().setChestplate(new ItemBuilder(Material.IRON_CHESTPLATE).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3, false).asStack());
            zombie.getEquipment().setLeggings(new ItemBuilder(Material.IRON_LEGGINGS).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3, false).asStack());
            zombie.getEquipment().setItemInMainHand(new ItemBuilder(Material.DIAMOND_SWORD).addEnchant(Enchantment.DAMAGE_ALL, 2, false).addEnchant(Enchantment.KNOCKBACK, 1, false).asStack());

            return null;
        };
    }

    /**
     * Spawns an upgraded skeleton above location of the block break event
     * The skeleton will not burn in daylight and will have an iron helmet and a bow with power 1 and punch 1
     *
     * @return a function that spawns the entity
     */
    @NotNull
    public BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, @Nullable BlockData> spawnUpgradedSkeleton1() {
        return (e, c) -> {
            val spawnLocation = e.getBlock().getLocation().add(spawnVector);
            val skeleton = (Skeleton) e.getBlock().getWorld().spawnEntity(spawnLocation, EntityType.SKELETON);

            skeleton.setShouldBurnInDay(false);
            skeleton.getEquipment().clear();
            skeleton.getEquipment().setHelmet(new ItemBuilder(Material.IRON_HELMET).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, false).asStack());
            skeleton.getEquipment().setItemInMainHand(new ItemBuilder(Material.BOW).addEnchant(Enchantment.ARROW_DAMAGE, 1, false).addEnchant(Enchantment.ARROW_KNOCKBACK, 1, false).asStack());

            return null;
        };
    }

    /**
     * Spawns an upgraded skeleton above location of the block break event
     * The skeleton will not burn in daylight and will have an iron helmet and boots and a bow with power 2 and punch 2
     *
     * @return a function that spawns the entity
     */
    @NotNull
    public BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, @Nullable BlockData> spawnUpgradedSkeleton2() {
        return (e, c) -> {
            val skeleton = (Skeleton) e.getBlock().getWorld().spawnEntity(e.getBlock().getLocation().add(0.5, 1.5, 0.5), EntityType.SKELETON);

            skeleton.setShouldBurnInDay(false);
            skeleton.getEquipment().clear();
            skeleton.getEquipment().setHelmet(new ItemBuilder(Material.IRON_HELMET).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2, false).asStack());
            skeleton.getEquipment().setBoots(new ItemBuilder(Material.IRON_BOOTS).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2, false).asStack());
            skeleton.getEquipment().setItemInMainHand(new ItemBuilder(Material.BOW).addEnchant(Enchantment.ARROW_DAMAGE, 2, false).addEnchant(Enchantment.ARROW_KNOCKBACK, 2, false).asStack());

            return null;
        };
    }

    /**
     * Spawns a spider jockey above location of the block break event
     * The skeleton will not burn in daylight and the spider will have the skeleton as a passenger
     *
     * @return a function that spawns the entity
     */
    @NotNull
    public BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, @Nullable BlockData> spawnSpiderJockey() {
        return (e, c) -> {
            val spawnLocation = e.getBlock().getLocation().add(spawnVector);
            val skeleton = (Skeleton) e.getBlock().getWorld().spawnEntity(spawnLocation, EntityType.SKELETON);
            val spider = (Spider) e.getBlock().getWorld().spawnEntity(spawnLocation, EntityType.SPIDER);

            skeleton.setShouldBurnInDay(false);
            spider.addPassenger(skeleton);

            return null;
        };
    }

    /**
     * Spawns a chicken jockey above location of the block break event
     * The zombie will be a baby, will not burn in daylight and the chicken will have the zombie as a passenger
     *
     * @return a function that spawns the entity
     */
    @NotNull
    public BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, @Nullable BlockData> spawnChickenJockey() {
        return (e, c) -> {
            val spawnLocation = e.getBlock().getLocation().add(spawnVector);
            val zombie = (Zombie) e.getBlock().getWorld().spawnEntity(spawnLocation, EntityType.ZOMBIE);
            val chicken = (Chicken) e.getBlock().getWorld().spawnEntity(spawnLocation, EntityType.CHICKEN);

            zombie.setBaby();
            zombie.setShouldBurnInDay(false);
            chicken.addPassenger(zombie);

            return null;
        };
    }

    /**
     * Spawns a snowman above location of the block break event
     * The snowman be derpy
     *
     * @return a function that spawns the entity
     */
    @NotNull
    public BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, @Nullable BlockData> spawnSnowman() {
        return (e, c) -> {
            val spawnLocation = e.getBlock().getLocation().add(spawnVector);
            val snowman = (Snowman) e.getBlock().getWorld().spawnEntity(spawnLocation, EntityType.SNOWMAN);

            snowman.setDerp(true);

            return null;
        };
    }

    /**
     * Spawns an upgraded husk above location of the block break event
     * The husk will be an adult and will have a diamond sword with sharpness 4 and knockback 2
     *
     * @return a function that spawns the entity
     */
    @NotNull
    public BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, @Nullable BlockData> spawnUpgradedHusk1() {
        return (e, c) -> {
            val spawnLocation = e.getBlock().getLocation().add(spawnVector);
            val husk = (Husk) e.getBlock().getWorld().spawnEntity(spawnLocation, EntityType.HUSK);

            husk.setAdult();
            husk.getEquipment().clear();
            husk.getEquipment().setItemInMainHand(new ItemBuilder(Material.DIAMOND_SWORD).addEnchant(Enchantment.DAMAGE_ALL, 4, false).addEnchant(Enchantment.KNOCKBACK, 2, false).asStack());

            return null;
        };
    }

    /**
     * Spawns a stray above location of the block break event
     * The stray will not burn in daylight
     *
     * @return a function that spawns the entity
     */
    @NotNull
    public BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, @Nullable BlockData> spawnStray() {
        return (e, c) -> {
            val spawnLocation = e.getBlock().getLocation().add(spawnVector);
            val stray = (Stray) e.getBlock().getWorld().spawnEntity(spawnLocation, EntityType.STRAY);

            stray.setShouldBurnInDay(false);

            return null;
        };
    }

    /**
     * Spawns an upgraded stray above location of the block break event
     * The stray will not burn in daylight and will have a bow with power 4 and punch 2
     *
     * @return a function that spawns the entity
     */
    @NotNull
    public BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, @Nullable BlockData> spawnUpgradedStray1() {
        return (e, c) -> {
            val spawnLocation = e.getBlock().getLocation().add(spawnVector);
            val stray = (Stray) e.getBlock().getWorld().spawnEntity(spawnLocation, EntityType.STRAY);

            stray.setShouldBurnInDay(false);
            stray.getEquipment().clear();
            stray.getEquipment().setItemInMainHand(new ItemBuilder(Material.BOW).addEnchant(Enchantment.ARROW_DAMAGE, 4, false).addEnchant(Enchantment.ARROW_KNOCKBACK, 2, false).asStack());

            return null;
        };
    }

    /**
     * Spawns a pillager above location of the block break event
     * The pillager will have a crossbow with quick charge 2, power 3, and punch 1
     *
     * @return a function that spawns the entity
     */
    @NotNull
    public BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, @Nullable BlockData> spawnUpgradedPillager() {
        return (e, c) -> {
            val spawnLocation = e.getBlock().getLocation().add(spawnVector);
            val pillager = (Pillager) e.getBlock().getWorld().spawnEntity(spawnLocation, EntityType.PILLAGER);

            pillager.getEquipment().clear();
            pillager.getEquipment().setItemInMainHand(new ItemBuilder(Material.CROSSBOW).addEnchant(Enchantment.ARROW_DAMAGE, 3, false).addEnchant(Enchantment.ARROW_KNOCKBACK, 1, false).addEnchant(Enchantment.QUICK_CHARGE, 2, false).asStack());

            return null;
        };
    }

    /**
     * Spawns an upgraded vindicator above location of the block break event
     * The vindicator will have an iron axe with sharpness 4 and knockback 1
     *
     * @return a function that spawns the entity
     */
    @NotNull
    public BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, @Nullable BlockData> spawnUpgradedVindicator() {
        return (e, c) -> {
            val vindicator = (Vindicator) e.getBlock().getWorld().spawnEntity(e.getBlock().getLocation().add(0.5, 1.5, 0.5), EntityType.VINDICATOR);

            vindicator.getEquipment().clear();
            vindicator.getEquipment().setItemInMainHand(new ItemBuilder(Material.IRON_AXE).addEnchant(Enchantment.DAMAGE_ALL, 4, false).addEnchant(Enchantment.KNOCKBACK, 1, false).asStack());

            return null;
        };
    }

    /**
     * Spawns an "ascended" skeleton above location of the block break event
     * The skeleton will not burn in daylight and will have a bow with power 3, punch 2, flame 1, and piercing 4
     *
     * @return a function that spawns the entity
     */
    @NotNull
    public BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, @Nullable BlockData> spawnAscendedSkeleton() {
        return (e, c) -> {
            val skeleton = (Skeleton) e.getBlock().getWorld().spawnEntity(e.getBlock().getLocation().add(0.5, 1.5, 0.5), EntityType.SKELETON);

            skeleton.setShouldBurnInDay(false);
            skeleton.getEquipment().clear();
            skeleton.getEquipment().setItemInMainHand(new ItemBuilder(Material.BOW).addEnchant(Enchantment.ARROW_DAMAGE, 3, false).addEnchant(Enchantment.ARROW_KNOCKBACK, 2, false).addEnchant(Enchantment.ARROW_FIRE, 1, false).addEnchant(Enchantment.PIERCING, 4, false).asStack());

            return null;
        };
    }

    /**
     * Spawns an "ascended" witch above location of the block break event
     * The witch will be added to the WitchFightListener to be tracked
     *
     * @return a function that spawns the entity
     * @see WitchFightListener
     */
    @NotNull
    public BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, @Nullable BlockData> spawnAscendedWitch() {
        return (e, c) -> {
            val witch = (Witch) e.getBlock().getWorld().spawnEntity(e.getBlock().getLocation().add(0.5, 1.5, 0.5), EntityType.WITCH);

            WitchFightListener.addWitch(witch);

            return null;
        };
    }

    /**
     * Spawns a silverfish mob above location of the block break event
     * The amount of silverfish spawned is random between 1 and 12
     *
     * @return a function that spawns the entity
     */
    @NotNull
    public BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, @Nullable BlockData> spawnSilverfishMob() {
        return (e, c) -> {
            val count = random.nextInt(1, 13);

            for (int i = 0; i < count; i++) {
                val xOff = random.nextInt(0, 21) / 10d;
                val zOff = random.nextInt(0, 21) / 10d;
                val spawnLocation = e.getBlock().getLocation().add(xOff, 2, zOff);

                e.getBlock().getWorld().spawnEntity(spawnLocation, EntityType.SILVERFISH);
            }

            return null;
        };
    }

    /**
     * Spawns an endermite mob above location of the block break event
     *
     * @return a function that spawns the entity
     */
    @NotNull
    public BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, @Nullable BlockData> spawnEndermiteMob() {
        return (e, c) -> {
            val count = random.nextInt(1, 13);

            for (int i = 0; i < count; i++) {
                val xOff = random.nextInt(0, 21) / 10d;
                val zOff = random.nextInt(0, 21) / 10d;
                val spawnLocation = e.getBlock().getLocation().add(xOff, 2, zOff);

                e.getBlock().getWorld().spawnEntity(spawnLocation, EntityType.ENDERMITE);
            }

            return null;
        };
    }

    /**
     * Spawns a piglin  above location of the block break event
     * The piglin will be immune to zombification
     *
     * @return a function that spawns the entity
     */
    @NotNull
    public BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, @Nullable BlockData> spawnPiglin() {
        return (e, c) -> {
            val spawnLocation = e.getBlock().getLocation().add(spawnVector);
            val piglin = (Piglin) e.getBlock().getWorld().spawnEntity(spawnLocation, EntityType.PIGLIN);

            piglin.setImmuneToZombification(true);

            return null;
        };
    }

    /**
     * Spawns a piglin brute  above location of the block break event
     * The piglin brute will be immune to zombification
     *
     * @return a function that spawns the entity
     */
    @NotNull
    public BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, @Nullable BlockData> spawnPiglinBrute() {
        return (e, c) -> {
            val spawnLocation = e.getBlock().getLocation().add(spawnVector);
            val piglinBrute = (PiglinBrute) e.getBlock().getWorld().spawnEntity(spawnLocation, EntityType.PIGLIN_BRUTE);

            piglinBrute.setImmuneToZombification(true);

            return null;
        };
    }

    /**
     * Spawns a hoglin above location of the block break event
     * The hoglin will be immune to zombification
     *
     * @return a function that spawns the entity
     */
    @NotNull
    public BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, @Nullable BlockData> spawnHoglin() {
        return (e, c) -> {
            val spawnLocation = e.getBlock().getLocation().add(spawnVector);
            val hoglin = (Hoglin) e.getBlock().getWorld().spawnEntity(spawnLocation, EntityType.HOGLIN);

            hoglin.setImmuneToZombification(true);

            return null;
        };
    }

    /**
     * Spawns a ghast above location of the block break event
     * The ghast will be spawned 5 blocks above the block break event location
     *
     * @return a function that spawns the entity
     */
    @NotNull
    public BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, @Nullable BlockData> spawnGhast() {
        return (e, c) -> {

            val spawnLocation = e.getBlock().getLocation().add(0.5, 5.0, 0.5);
            e.getBlock().getWorld().spawnEntity(spawnLocation, EntityType.GHAST);

            return null;
        };
    }

    /**
     * Spawns an upgraded zombie above location of the block break event
     * The zombie will not burn in daylight and will be an adult
     * The zombie will have a diamond chestplate with protection 2, iron leggings with protection 3, diamond boots with protection 2,
     * and a diamond sword with sharpness 3 and knockback 2
     *
     * @return a function that spawns the entity
     */
    @NotNull
    public BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, @Nullable BlockData> spawnUpgradedZombie3() {
        return (e, c) -> {
            val spawnLocation = e.getBlock().getLocation().add(spawnVector);
            val zombie = (Zombie) e.getBlock().getWorld().spawnEntity(spawnLocation, EntityType.ZOMBIE);

            zombie.setShouldBurnInDay(false);
            zombie.setAdult();
            zombie.getEquipment().clear();
            zombie.getEquipment().setChestplate(new ItemBuilder(Material.DIAMOND_CHESTPLATE).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2, false).asStack());
            zombie.getEquipment().setLeggings(new ItemBuilder(Material.IRON_LEGGINGS).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3, false).asStack());
            zombie.getEquipment().setBoots(new ItemBuilder(Material.DIAMOND_BOOTS).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2, false).asStack());
            zombie.getEquipment().setItemInMainHand(new ItemBuilder(Material.DIAMOND_SWORD).addEnchant(Enchantment.DAMAGE_ALL, 3, false).addEnchant(Enchantment.KNOCKBACK, 2, false).asStack());

            return null;
        };
    }

    /**
     * Spawns an upgraded skeleton above location of the block break event
     * The skeleton will not burn in daylight and will have a diamond helmet with protection 2, iron chestplate with protection 3, diamond boots with protection 2,
     * and a bow with power 4 and punch 2
     *
     * @return a function that spawns the entity
     */
    @NotNull
    public BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, @Nullable BlockData> spawnUpgradedSkeleton3() {
        return (e, c) -> {
            val spawnLocation = e.getBlock().getLocation().add(spawnVector);
            val skeleton = (Skeleton) e.getBlock().getWorld().spawnEntity(spawnLocation, EntityType.SKELETON);

            skeleton.setShouldBurnInDay(false);
            skeleton.getEquipment().clear();
            skeleton.getEquipment().setHelmet(new ItemBuilder(Material.DIAMOND_HELMET).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2, false).asStack());
            skeleton.getEquipment().setChestplate(new ItemBuilder(Material.IRON_CHESTPLATE).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3, false).asStack());
            skeleton.getEquipment().setBoots(new ItemBuilder(Material.DIAMOND_BOOTS).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2, false).asStack());
            skeleton.getEquipment().setItemInMainHand(new ItemBuilder(Material.BOW).addEnchant(Enchantment.ARROW_DAMAGE, 4, false).addEnchant(Enchantment.ARROW_KNOCKBACK, 2, false).asStack());

            return null;
        };
    }

    /**
     * Spawns an upgraded husk above location of the block break event
     * The husk will be an adult and will have a diamond chestplate with protection 4 and a diamond sword with sharpness 4 and knockback 2
     *
     * @return a function that spawns the entity
     */
    @NotNull
    public BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, @Nullable BlockData> spawnUpgradedHusk2() {
        return (e, c) -> {
            val spawnLocation = e.getBlock().getLocation().add(spawnVector);
            val husk = (Husk) e.getBlock().getWorld().spawnEntity(spawnLocation, EntityType.HUSK);

            husk.setAdult();
            husk.getEquipment().clear();
            husk.getEquipment().setChestplate(new ItemBuilder(Material.DIAMOND_CHESTPLATE).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4, false).asStack());
            husk.getEquipment().setItemInMainHand(new ItemBuilder(Material.DIAMOND_SWORD).addEnchant(Enchantment.DAMAGE_ALL, 4, false).addEnchant(Enchantment.KNOCKBACK, 2, false).asStack());

            return null;
        };
    }

    /**
     * Spawns an upgraded stray above location of the block break event
     * The stray will not burn in daylight and will have diamond leggings with protection 2 and diamond boots with protection 2,
     * and a bow with power 4 and punch 2
     *
     * @return a function that spawns the entity
     */
    @NotNull
    public BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, @Nullable BlockData> spawnUpgradedStray2() {
        return (e, c) -> {
            val spawnLocation = e.getBlock().getLocation().add(spawnVector);
            val stray = (Stray) e.getBlock().getWorld().spawnEntity(spawnLocation, EntityType.STRAY);

            stray.setShouldBurnInDay(false);
            stray.getEquipment().clear();
            stray.getEquipment().setLeggings(new ItemBuilder(Material.DIAMOND_LEGGINGS).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2, false).asStack());
            stray.getEquipment().setBoots(new ItemBuilder(Material.DIAMOND_BOOTS).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2, false).asStack());
            stray.getEquipment().setItemInMainHand(new ItemBuilder(Material.BOW).addEnchant(Enchantment.ARROW_DAMAGE, 4, false).addEnchant(Enchantment.ARROW_KNOCKBACK, 2, false).asStack());

            return null;
        };
    }


    /**
     * Spawns an upgraded cave spider above location of the block break event
     * The cave spider will be added to the UpgradedCaveSpiderListener to be tracked
     *
     * @return a function that spawns the entity
     * @see UpgradedCaveSpiderListener
     */
    @NotNull
    public BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, @Nullable BlockData> spawnUpgradedCaveSpider() {
        return (e, c) -> {
            val spawnLocation = e.getBlock().getLocation().add(spawnVector);
            val spider = (CaveSpider) e.getBlock().getWorld().spawnEntity(spawnLocation, EntityType.CAVE_SPIDER);

            UpgradedCaveSpiderListener.addSpider(spider);

            return null;
        };
    }

    /**
     * Spawns a spider jockey above location of the block break event
     * The skeleton will not burn in daylight and the spider will have the skeleton as a passenger
     *
     * @return a function that spawns the entity
     */
    @NotNull
    public BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, @Nullable BlockData> spawnCaveSpiderJockey() {
        return (e, c) -> {
            val spawnLocation = e.getBlock().getLocation().add(spawnVector);
            val skeleton = (Skeleton) e.getBlock().getWorld().spawnEntity(spawnLocation, EntityType.SKELETON);
            val spider = (CaveSpider) e.getBlock().getWorld().spawnEntity(spawnLocation, EntityType.CAVE_SPIDER);

            skeleton.setShouldBurnInDay(false);
            spider.addPassenger(skeleton);

            return null;
        };
    }

    /**
     * Spawns a charged creeper above location of the block break event
     * The creeper will be powered and will have an extended fuse time so
     * that it does not explode immediately
     *
     * @return a function that spawns the entity
     */
    @NotNull
    public BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, @Nullable BlockData> spawnChargedCreeper() {
        return (e, c) -> {
            val spawnLocation = e.getBlock().getLocation().add(spawnVector);
            val creeper = (Creeper) e.getBlock().getWorld().spawnEntity(spawnLocation, EntityType.CREEPER);

            creeper.setPowered(true);
            creeper.setMaxFuseTicks(100);
            creeper.setFuseTicks(0);

            return null;
        };
    }

    @NotNull
    public Sound[] wardenSounds = new Sound[]{
            Sound.sound(Key.key("entity.warden.agitated"), Sound.Source.HOSTILE, 1.0F, 1.0F),
            Sound.sound(Key.key("entity.warden.ambient"), Sound.Source.HOSTILE, 1.0F, 1.0F),
            Sound.sound(Key.key("entity.warden.angry"), Sound.Source.HOSTILE, 1.0F, 1.0F),
            Sound.sound(Key.key("entity.warden.heartbeat"), Sound.Source.HOSTILE, 1.0F, 1.0F),
            Sound.sound(Key.key("entity.warden.nearby_close"), Sound.Source.HOSTILE, 1.0F, 1.0F),
            Sound.sound(Key.key("entity.warden.nearby_closer"), Sound.Source.HOSTILE, 1.0F, 1.0F),
            Sound.sound(Key.key("entity.warden.nearby_closest"), Sound.Source.HOSTILE, 1.0F, 1.0F)
    };

    /**
     * Plays a random warden sound to all players in the empire of the player who broke the block
     *
     * @return a function that plays the sound
     */
    @NotNull
    public BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, @Nullable BlockData> playRandomWardenSound() {
        return (e, c) -> {
            Random random = new Random();

            val empire = OneblockSkyblock.getEmpire(e.getPlayer());

            Audience audience = Audience.audience(empire.onlinePlayers().toArray(new Audience[0]));

            val randomIndex = random.nextInt(wardenSounds.length);
            audience.playSound(wardenSounds[randomIndex]);

            return null;
        };
    }

    /**
     * Spawns a warden blip above location of the block break event
     * The warden will "blip" into existence and then disappear almost immediately
     * The warden will be invisible and will be teleported to the void so that it dies instantly
     *
     * @return a function that spawns the entity
     */
    @NotNull
    public BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, @Nullable BlockData> wardenBlip() {
        return (e, c) -> {
            val world = e.getBlock().getWorld();
            val spawnLocation = e.getBlock().getLocation().add(spawnVector);
            val warden = (Warden) world.spawnEntity(spawnLocation, EntityType.WARDEN);

            warden.setAI(false);
            playRandomWardenSound();

            Bukkit.getScheduler().scheduleSyncDelayedTask(OneblockSkyblock.getInstance(), () -> {
                warden.setInvisible(true);
                warden.teleport(new Location(world, spawnLocation.getBlockX(), -25, spawnLocation.getBlockZ()));
                warden.setHealth(0);
            }, 10);

            return null;
        };
    }

    /**
     * Spawns a skulker above the location of the block break event
     *
     * @return a function that spawns the entity
     */
    @NotNull
    public BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, @Nullable BlockData> spawnShulker() {
        return (e, c) -> {
            val spawnLocation = e.getBlock().getLocation().add(spawnVector);
            e.getBlock().getWorld().spawnEntity(spawnLocation, EntityType.SHULKER);

            return null;
        };
    }

    /**
     * Spawns a phantom above the location of the block break event
     * The phantom will not burn in daylight
     *
     * @return a function that spawns the entity
     */
    @NotNull
    public BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, @Nullable BlockData> spawnPhantom() {
        return (e, c) -> {
            val spawnLocation = e.getBlock().getLocation().add(spawnVector);
            val phantom = (Phantom) e.getBlock().getWorld().spawnEntity(spawnLocation, EntityType.PHANTOM);

            phantom.setShouldBurnInDay(false);

            return null;
        };
    }

    /**
     * Spawns a warden that will pursue the player who broke the block
     * The warden will persist for a short time before disappearing
     *
     * @return a function that spawns the entity
     */
    @NotNull
    public BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, @Nullable BlockData> wardenPursuit() {
        return (e, c) -> {
            val world = e.getBlock().getWorld();
            val spawnLocation = e.getBlock().getLocation().add(spawnVector);
            val warden = (Warden) world.spawnEntity(spawnLocation, EntityType.WARDEN);

            Bukkit.getScheduler().scheduleSyncDelayedTask(OneblockSkyblock.getInstance(), () -> {
                warden.setInvisible(true);
                warden.teleport(new Location(world, spawnLocation.getBlockX(), -25, spawnLocation.getBlockZ()));
                warden.setHealth(0);
                warden.setAnger(e.getPlayer(), 80);
            }, 200);

            return null;
        };
    }
}
