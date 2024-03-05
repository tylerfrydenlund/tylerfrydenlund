package io.hyleo.obsb.util;

import com.google.common.collect.Range;
import io.hyleo.obsb.OneblockSkyblock;
import io.hyleo.obsb.api.Empire;
import io.hyleo.obsb.api.LootDigest;
import io.hyleo.obsb.api.Weight;
import io.hyleo.obsb.phases.Infinite;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import lombok.val;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.Container;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Levelled;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.block.data.type.Leaves;
import org.bukkit.block.data.type.Snow;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Bee;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.loot.LootContext;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

@UtilityClass
public class Util {
    /**
     * Returns all players that could be found by their UUIDs
     *
     * @param uuids The UUIDs of the players
     * @return The players that could be found
     */
    @NotNull
    public List<@NotNull Player> uuidsToPlayers(@NonNull List<@NotNull UUID> uuids) {
        return uuids.stream().map(Bukkit::getPlayer).filter(Objects::nonNull).collect(Collectors.toList());
    }

    /**
     * A factory method for generating a named chest with
     * a given loot table that has been modified by a given context.
     * Normally used for spawning chests in a phase.
     *
     * @param name      The name of the chest
     * @param lootTable The loot table to fill the chest with
     * @param context   The context to modify the loot table with
     * @return A function that spawns the chest
     */
    @NotNull
    public BiFunction<BlockBreakEvent, LootContext, BlockData> spawnChest(@NonNull Component name, @NonNull LootDigest lootTable, @NonNull LootContext context) {
        return (e, c) -> {
            val block = e.getBlock();
            /*
               It is redundant to set the block type but the system can not run the code
               below before it returns the material to set the block.
               This is a workaround.
             */
            block.setType(Material.CHEST);

            val chest = (Chest) block.getState();

            Bukkit.getScheduler().runTaskLater(OneblockSkyblock.getInstance(), () -> {
                chest.getSnapshotInventory().clear();
                chest.customName(name);
                lootTable.fillInventory(chest.getSnapshotInventory(), new Random(), context);
                chest.update();
            }, 2);

            return chest.getBlockData();
        };
    }

    /**
     * Returns the empire that owns the block given oneblock.
     * If the block is not owned by any empire, null is returned.
     *
     * @param block The block to check
     * @return The empire that owns the given oneblock block
     */
    @Nullable
    public Empire getOneblockOwner(@NonNull Block block) {
        Empire oneBlockOwner = null;

        for (val e : OneblockSkyblock.getEmpires()) {
            if (e.isOneBlock(block.getLocation())) {
                oneBlockOwner = e;
                break;
            }
        }

        return oneBlockOwner;
    }

    /**
     * Selects a random value from a map of weights.
     * If the map is empty, the default value is returned.
     * If the map is not empty, the weights are used to select a random value.
     * If no value could be found, the default value is returned.
     * A random instance is used to select the value.
     * A provided random can have a seed for procedural generation.
     *
     * @param random       The random instance to use
     * @param weights      The weights to select from
     * @param defaultValue The default value for if something goes wrong
     * @param <T>          The type you are trying to randomly select
     * @return A random value from the weights
     */

    @NotNull
    public <T> T selectRandom(@NonNull Random random, @NonNull Map<T, ? extends Weight> weights, @NonNull T defaultValue) {

        if (weights.isEmpty()) {
            return defaultValue;
        }

        val totalWeight = weights.values().stream().mapToDouble(Weight::weight).sum();
        var r = random.nextDouble() * totalWeight;

        for (val entry : weights.entrySet()) {
            if (r < entry.getValue().weight()) {
                return entry.getKey();
            }
            r -= entry.getValue().weight();
        }

        // Ensure the result is never null
        Optional<T> result = weights.keySet().stream().findAny();

        return result.orElse(defaultValue);
    }

    /**
     * Adjusts the weight based on the context.
     * Weights are adjusted based on the luck and looting of the context.
     *
     * @param weight  The weight to adjust
     * @param context The context to adjust the weight with
     * @return The adjusted weight
     */
    @NotNull
    public Weight adjustWeight(@NonNull Weight weight, @NonNull LootContext context) {
        val looting = context.getLootedEntity() == null ? 0 : context.getLootingModifier();
        val quality = weight.quality();
        val adjusted = weight.weight() + quality * (looting + context.getLuck());

        return Weight.of(quality, adjusted);
    }

    /**
     * Adjusts the weights based on the context.
     * Weights are adjusted based on the luck and looting of the context.
     *
     * @param weights The weights to adjust
     * @param context The context to adjust the weights with
     * @param <T>     The type of the weights
     * @return The adjusted weights
     */
    @NotNull
    public <T> Map<@NotNull T, @NotNull Weight> adjustWeights(@NonNull Map<T, @NonNull Weight> weights, @NonNull LootContext context) {
        return weights.entrySet().stream().collect(
                Collectors.toMap(Map.Entry::getKey, e -> adjustWeight(e.getValue(), context)));
    }

    /**
     * Checks if a block is at a location.
     *
     * @param block    The block to check
     * @param location The location to check
     * @return True if the block is at the location, false otherwise
     */
    public boolean isBlockAt(@NonNull Block block, @NonNull Location location) {

        boolean world = block.getWorld() == location.getWorld();
        boolean x = block.getX() == location.getBlockX();
        boolean y = block.getY() == location.getBlockY();
        boolean z = block.getZ() == location.getBlockZ();

        return world && x && y && z;
    }

    /**
     * Centers the x and z coordinates of a location for
     * perfect entity placement.
     *
     * @param location The location to center
     * @return The centered location
     */
    @NotNull
    public Location center(@NonNull Location location) {

        location = location.clone();

        if (location.getX() % 1.0 == 0) {
            location.add(0.5, 0, 0);
        }

        if (location.getZ() % 1.0 == 0) {
            location.add(0, 0, 0.5);
        }

        return location;
    }

    /**
     * Remaps a map of quantities or otherwise general purpose integers that would also simultaneously express the quality of a weight,
     * to a map of weights.
     * This is merely a convenience method for creating a map of weights from a map of generic integer values.
     *
     * @param weights The weights to remap
     * @return The remapped weights
     */
    @NotNull
    public Map<@NotNull Integer, @NotNull Weight> remap(@NonNull Map<@NotNull Integer, @NotNull Double> weights) {
        return weights.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> Weight.of(e.getKey(), e.getValue())));
    }

    /**
     * Creates a factory method for generating a potion item stack.
     * The potion is created with a given type, extended and upgraded.
     * Generally used for creating potion items for loot tables.
     *
     * @param type     The type of the potion
     * @param extended Whether the potion is extended
     * @param upgrade  Whether the potion is upgraded
     * @return A function that creates the potion item stack
     */
    @NotNull

    public Supplier<@NotNull ItemStack> potion(@NonNull PotionType type, boolean extended, boolean upgrade) {
        return () -> {
            ItemStack stack = new ItemStack(Material.POTION);
            PotionMeta meta = (PotionMeta) stack.getItemMeta();
            PotionData data = new PotionData(type, extended, upgrade);
            meta.setBasePotionData(data);
            stack.setItemMeta(meta);
            return stack;
        };
    }

    /**
     * Creates a factory method for generating a splash potion item stack.
     * The potion is created with a given type, extended and upgraded.
     * Generally used for creating splash potion items for loot tables.
     *
     * @param type     The type of the potion
     * @param extended Whether the potion is extended
     * @param upgrade  Whether the potion is upgraded
     * @return A function that creates the splash potion item stack
     */
    @NotNull
    public Supplier<@NotNull ItemStack> splashPotion(@NonNull PotionType type, boolean extended, boolean upgrade) {
        return () -> {
            ItemStack stack = new ItemStack(Material.SPLASH_POTION);
            PotionMeta meta = (PotionMeta) stack.getItemMeta();
            PotionData data = new PotionData(type, extended, upgrade);
            meta.setBasePotionData(data);
            stack.setItemMeta(meta);
            return stack;
        };
    }

    /**
     * Creates a factory method for generating a completely random potion
     *
     * @return A function that creates a random potion item stack
     */
    @NotNull
    public Supplier<@NotNull ItemStack> randomPotion() {
        Random random = new Random();

        return () -> {
            ItemStack stack = null;

            if (random.nextDouble() > 0.9) {
                stack = new ItemStack(Material.LINGERING_POTION);
            } else if (random.nextDouble() > 0.6) {
                stack = new ItemStack(Material.SPLASH_POTION);
            } else {
                stack = new ItemStack(Material.POTION);
            }

            PotionMeta meta = (PotionMeta) stack.getItemMeta();
            PotionType type = PotionType.values()[random.nextInt(PotionType.values().length)];
            boolean extended = type.isExtendable() && random.nextDouble() > 0.66;
            boolean upgraded = type.isUpgradeable() && random.nextDouble() > 0.66;
            PotionData data = new PotionData(type, extended, upgraded);
            meta.setBasePotionData(data);
            stack.setItemMeta(meta);

            return stack;
        };
    }

    /**
     * Creates a factory method for creating default block data for a given material.
     * The provided material must be a block.
     *
     * @param material The material to create the block data for (must be a block)
     * @return A function that creates the default block data
     */
    @NotNull
    public BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, @NotNull BlockData> defaultData(@NonNull Material material) {
        assert material.isBlock();
        return (e, c) -> material.createBlockData();
    }

    /**
     * Creates a factory method for creating waterlogged block data for a given material.
     * The provided material must be a waterlogged block.
     *
     * @param material The material to create the block data for (must be a waterlogged block)
     * @return A function that creates the waterlogged block data
     */
    @NotNull
    public BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, @NotNull Waterlogged> waterlogged(@NonNull Material material) {
        assert material.createBlockData() instanceof Waterlogged;
        return (e, c) -> {
            Waterlogged data = (Waterlogged) material.createBlockData();
            data.setWaterlogged(true);

            return data;
        };
    }

    /**
     * Creates a factory method for creating leaves block data for a given material.
     * The provided material must be a leaves block.
     *
     * @param material The material to create the block data for (must be a leaves block)
     * @return A function that creates the leaves block data
     */
    @NotNull
    public BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, @NotNull Leaves> leaves(@NonNull Material material) {
        assert material.createBlockData() instanceof Leaves;
        return (e, c) -> {
            Leaves data = (Leaves) material.createBlockData();
            data.setPersistent(true);
            return data;
        };
    }

    /**
     * Adds bees to a hive block.
     * The amount of bees added is random (between 0 and 3).
     *
     * @param hive The hive to add bees to
     */
    public void addBeesToHive(@NonNull Block hive) {
        assert hive.getType() == Material.BEEHIVE;

        val weights = Map.of(0, Weight.of(2), 1, Weight.of(1), 2, Weight.of(2), 3, Weight.of(1));
        int bees = Util.selectRandom(new Random(), weights, 0);

        for (int i = 0; i < bees; i++) {
            Bee bee = (Bee) hive.getWorld().spawnEntity(hive.getLocation().add(0, 1.5, 0), EntityType.BEE);
            bee.setHive(hive.getLocation());
        }
    }


    /**
     * Creates a factory method for creating snow block data.
     * The snow block data is created with a random amount of layers (between 0 and 7).
     *
     * @return A function that creates the snow block data
     */
    @NotNull
    public BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, @NotNull Snow> snow() {
        return (e, c) -> {
            val random = new Random();
            Snow data = (Snow) Material.SNOW.createBlockData();
            data.setLayers(random.nextInt(7) + 1);
            return data;
        };
    }

    /**
     * Creates a factory method for creating cauldron block data.
     * The cauldron block data is created with a random amount of water (between 0 and 3).
     *
     * @return A function that creates the cauldron block data
     */
    @NotNull
    public BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, @NotNull Levelled> cauldron() {
        return (e, c) -> {
            val random = new Random();
            Levelled data = (Levelled) Material.WATER_CAULDRON.createBlockData();
            data.setLevel(random.nextInt(1, 4));
            return data;
        };
    }

    /**
     * Creates a factory method for creating a flaming block data.
     * The block is set on fire with a random chance.
     *
     * @param material The material to create the block data for
     * @param soulFire Whether the fire is soul fire
     * @return A function that creates the flaming block data
     */
    @NotNull
    public BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, @NotNull BlockData> flamingBlock(@NonNull Material material, boolean soulFire) {
        return (e, c) -> {
            val data = material.createBlockData();
            e.getBlock().getLocation().add(0, 1, 0).getBlock().setType(soulFire ? Material.SOUL_FIRE : Material.FIRE);
            return data;
        };
    }

    /**
     * Creates a factory method for creating a loot context for a given entity.
     * The context is created with the luck and looting of the entity.
     *
     * @param entity         The entity to create the context for
     * @param additionalLuck The additional luck to add to the context
     * @return A function that creates the loot context
     */
    @NotNull
    public LootContext getContext(@NonNull LivingEntity entity, float additionalLuck) {
        val luckPotion = entity.getPotionEffect(PotionEffectType.LUCK);

        val lootingLevel = Objects.isNull(luckPotion) ? 0 :
                entity.getActiveItem().getEnchantmentLevel(Enchantment.LOOT_BONUS_MOBS);

        val luck = additionalLuck + (luckPotion != null ? luckPotion.getAmplifier() : 0);

        return new LootContext.Builder(entity.getLocation()).luck(luck).lootingModifier(lootingLevel)
                .build();
    }

    /**
     * Spawns particles above all oneblocks for easy identification.
     * The particles are spawned for all online players.
     */
    public void spawnOneblockParticleIdentifiers() {
        for (val oneBlock : OneblockSkyblock.getEmpires().stream().map(Empire::getOneblock).toList()) {
            val block = oneBlock.getBlock();
            val location = block.getLocation().add(0.5, 1.1, 0.5);
            val data = block.getType().createBlockData();

            for (val player : Bukkit.getOnlinePlayers()) {
                if (player.getWorld() != block.getWorld()) {
                    continue;
                }

                player.spawnParticle(Particle.BLOCK_DUST,
                        location, 1, 0.0, 0, 0.0, 0, data);
            }
        }
    }

    /**
     * Checks if a location (the subject) is within the range of two vectors (the bounds) offset by a given location (the origin).
     *
     * @param origin  The origin to offset the bounds by
     * @param boundA  The first bound to check
     * @param boundB  The second bound to check
     * @param subject The location to check if it is within the bounds
     * @return True if the subject is within the bounds, false otherwise
     */
    public boolean inRange(@NonNull Location origin, @NonNull Vector boundA, @NonNull Vector boundB, @NonNull Location subject) {
        val world = origin.getWorld();

        val upper = new Location(world, Math.max(boundA.getX(), boundB.getX()) + origin.getX(),
                Math.max(boundA.getY(), boundB.getY()) + origin.getY(),
                Math.max(boundA.getZ(), boundB.getZ()) + origin.getZ());

        val lower = new Location(world, Math.min(boundA.getX(), boundB.getX()) + origin.getX(),
                Math.min(boundA.getY(), boundB.getY()) + origin.getY(),
                Math.min(boundA.getZ(), boundB.getZ()) + origin.getZ());

        return subject.getX() >= lower.getX() && subject.getX() <= upper.getX()
                && subject.getY() >= lower.getY() && subject.getY() <= upper.getY()
                && subject.getZ() >= lower.getZ() && subject.getZ() <= upper.getZ();
    }

    /**
     * Converts a component to a plain alphanumeric string.
     *
     * @param component The component to convert
     * @return The plain alphanumeric string
     */
    @NotNull
    public String componentToPlainAlphanumeric(@Nullable Component component) {
        return PlainTextComponentSerializer.plainText()
                .serialize(component != null ? component : Component.text(""))
                .replaceAll("[^a-zA-Z0-9]", "");
    }

    /**
     * Replaces an item with the default name of a block if the item has the same name as the block.
     *
     * @param block     The block to replace the item with
     * @param container The container to replace the item with
     * @return A function that replaces the item with the default name of the block
     */
    @NotNull
    public UnaryOperator<@NotNull ItemStack> replaceItemWithDefaultName(@NonNull Block block, @NonNull Container container) {
        return i -> {

            if (i.getType() == block.getType() && i.getItemMeta().hasDisplayName() &&
                    componentToPlainAlphanumeric(container.customName())
                            .equalsIgnoreCase(componentToPlainAlphanumeric(i.displayName()))) {
                return new ItemStack(i.getType(), i.getAmount());
            }

            return i;
        };
    }

    /**
     * Checks if a player is standing on a block.
     *
     * @param player The player to check
     * @param block  The block to check
     * @return True if the player is standing on the block, false otherwise
     */
    public boolean isStandingOn(@NonNull Player player, @NonNull Block block) {

        val location = player.getLocation();
        val blockLocation = block.getLocation();

        return location.getBlockX() == blockLocation.getBlockX() &&
                location.getBlockY() == blockLocation.getBlockY() + 1 &&
                location.getBlockZ() == blockLocation.getBlockZ();
    }

    /**
     * Checks if a player is in a block.
     *
     * @param player The player to check
     * @param block  The block to check
     * @return True if the player is in the block, false otherwise
     */
    public boolean isInBlock(@NonNull Player player, @NonNull Block block) {
        return headInBlock(player, block) || feetInBlock(player, block);
    }

    /**
     * Checks if a player's head is in a block.
     *
     * @param player The player to check
     * @param block  The block to check
     * @return True if the player's head is in the block, false otherwise
     */
    public boolean headInBlock(@NonNull Player player, @NonNull Block block) {
        return player.getEyeLocation().add(0, 1, 0).getBlock() == block;
    }

    /**
     * Checks if a player's feet are in a block.
     *
     * @param player The player to check
     * @param block  The block to check
     * @return True if the player's feet are in the block, false otherwise
     */

    public boolean feetInBlock(@NonNull Player player, @NonNull Block block) {
        val location = player.getLocation();
        val y = Double.valueOf(block.getY());
        return location.getY() % 1.0d != 0 && Range.closedOpen(y, y + 1d).contains(location.getY());
    }

    /**
     * Copies the provided location and sets the yaw and pitch to the player's current yaw and pitch.
     *
     * @param player   The player to copy the yaw and pitch from
     * @param location The location to copy the yaw and pitch to
     * @return The location with the yaw and pitch copied
     */
    @NotNull
    public Location copyYawAndPitchToNewLocation(@NonNull Player player, @NonNull Location location) {
        val yaw = player.getLocation().getYaw();
        val pitch = player.getLocation().getPitch();

        location = location.clone();
        location.setYaw(yaw);
        location.setPitch(pitch);

        return location;
    }

    /**
     * Obfuscates the phase name of an empire for a viewer.
     * If the viewer is in the same phase number or higher, or the empire is in the infinite phase, the phase name is returned as is.
     * If the viewer is in a later phase, the phase name is obfuscated.
     *
     * @param empire The empire to obfuscate the phase name of
     * @param viewer The viewer to obfuscate the phase name for
     * @return The obfuscated phase name
     */
    @NotNull
    public Component obfuscatePhaseName(@NonNull  Empire empire,@NonNull Empire viewer) {
        if (viewer.phase() >= empire.phase() || empire.currentPhase() == Infinite.PHASE) {
            return empire.currentPhase().unwrappedDisplayName();
        }

        val phase = empire.currentPhase();
        val serializer = LegacyComponentSerializer.builder().build();
        val phaseNameLength = serializer.serialize(phase.unwrappedDisplayName()).length();

        return Component.text("|".repeat(phaseNameLength), phase.textColor()).decorate(TextDecoration.OBFUSCATED);
    }

    /**
     * Calculates the amount of experience required to reach a level.
     *
     * @param level The level to calculate the experience for
     * @return The amount of experience required to reach the level
     */
    public static int getExpToLevelUp(int level) {
        if (level <= 15) {
            return 2 * level + 7;
        } else if (level <= 30) {
            return 5 * level - 38;
        } else {
            return 9 * level - 158;
        }
    }

    /**
     * Calculates the amount of experience in a player's experience bar.
     *
     * @param player The player to calculate the experience for
     * @return The amount of experience in the player's experience bar
     */
    public static int getPlayerExp(@NonNull Player player) {
        int exp = 0;
        int level = player.getLevel();

        // Get the amount of XP in past levels
        exp += getExpAtLevel(level);

        // Get amount of XP towards next level
        exp += Math.round(getExpToLevelUp(level) * player.getExp());

        return exp;
    }

    /**
     * Calculates the amount of experience required to reach a level.
     *
     * @param level The level to calculate the experience for
     * @return The amount of experience required to reach the level
     */
    public static int getExpAtLevel(int level) {
        if (level <= 16) {
            return (int) (Math.pow(level, 2) + 6 * level);
        } else if (level <= 31) {
            return (int) (2.5 * Math.pow(level, 2) - 40.5 * level + 360.0);
        } else {
            return (int) (4.5 * Math.pow(level, 2) - 162.5 * level + 2220.0);
        }
    }
}
