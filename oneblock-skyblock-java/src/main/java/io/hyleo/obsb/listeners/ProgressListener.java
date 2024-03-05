package io.hyleo.obsb.listeners;

import com.google.common.collect.Maps;
import io.hyleo.obsb.OneblockSkyblock;
import io.hyleo.obsb.api.Empire;
import io.hyleo.obsb.api.InfiniteTags;
import io.hyleo.obsb.api.Phase;
import io.hyleo.obsb.api.Weight;
import io.hyleo.obsb.phases.Infinite;
import io.hyleo.obsb.util.ExpValues;
import io.hyleo.obsb.util.Util;
import io.papermc.paper.event.entity.EntityMoveEvent;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.val;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.Container;
import org.bukkit.block.data.type.Leaves;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.loot.LootContext;

import java.util.*;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProgressListener implements Listener {

    public static final Map<LivingEntity, Empire> bosses = new HashMap<>();
    public static final Map<Player, Integer> respawning = new HashMap<>();

    public ProgressListener() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(OneblockSkyblock.getInstance(), () -> {
            for (Map.Entry<Player, Integer> entry : respawning.entrySet()) {
                val player = entry.getKey();
                val time = entry.getValue();

                if (time <= 0 || !player.isOnline()) {
                    respawning.remove(player);
                    continue;
                }

                val newTime = time - 1;

                player.showTitle(
                        Title.title(Component.empty(),
                                Component.text("" + newTime, newTime <= 5 ? NamedTextColor.RED : NamedTextColor.GOLD)));
                respawning.put(player, newTime);
            }
        }, 0, 20);
    }

    @EventHandler
    void onBreak(BlockBreakEvent event) {
        val player = event.getPlayer();
        val block = event.getBlock();
        val empire = OneblockSkyblock.getEmpire(player);
        val context = Util.getContext(player, 0);

        try {

            if (!isValidBreak(player, empire, block)) {
                event.setCancelled(true);
                sendInvalidBreakMessage(player);
                return;
            }

            if (!empire.isOneBlock(block.getLocation())) {
                return;
            }

            val phase = empire.currentPhase();

            empire.blocksBroken(empire.blocksBroken() + 1);
            empire.totalBlocksBroken(empire.totalBlocksBroken() + 1);
            itemDrops(event, player, block);


            if (!checkIfComplete(event, player, phase)) {
                replaceBlock(phase, block, event, context);
            }

            if (block.getType().equals(Material.BEE_NEST)) {
                Util.addBeesToHive(block);
            }
        } catch (Exception e) {
            e.printStackTrace();
            event.setCancelled(true);
            player.sendMessage(Component.text("Failed to process block break", NamedTextColor.RED));
        }

    }

    void expAdjustment(BlockBreakEvent event) {
        val random = new Random();
        val material = event.getBlock().getType();

        if (ExpValues.expValues.get(material) == null) {
            return;
        }

        val range = ExpValues.expValues.get(material);

        event.setExpToDrop(random.nextInt(range.getKey(), range.getValue()));
    }

    void itemDrops(BlockBreakEvent event, Player player, Block block) {
        val random = new Random();
        val drops = new ArrayList<>(block.getDrops());

        if (block.getState() instanceof Container container) {
            val items = container.getInventory().getContents();
            drops.addAll(Arrays.stream(items).filter(Objects::nonNull).toList());

            drops.replaceAll(Util.replaceItemWithDefaultName(block, container));
        }

        if (block.getBlockData() instanceof Leaves) {
            drops.add(new ItemStack(block.getType()));
        }

        if (block.getType().equals(Material.TUBE_CORAL_BLOCK)) {
            drops.clear();
            drops.add(new ItemStack(Material.TUBE_CORAL_BLOCK));
        }

        if (block.getType().equals(Material.BRAIN_CORAL_BLOCK)) {
            drops.clear();
            drops.add(new ItemStack(Material.BRAIN_CORAL_BLOCK));
        }

        if (block.getType().equals(Material.BUBBLE_CORAL_BLOCK)) {
            drops.clear();
            drops.add(new ItemStack(Material.BUBBLE_CORAL_BLOCK));
        }

        if (block.getType().equals(Material.FIRE_CORAL_BLOCK)) {
            drops.clear();
            drops.add(new ItemStack(Material.FIRE_CORAL_BLOCK));
        }

        if (block.getType().equals(Material.HORN_CORAL_BLOCK)) {
            drops.clear();
            drops.add(new ItemStack(Material.HORN_CORAL_BLOCK));
        }

        val dropLocation = block.getLocation().add(0.5, 1, 0.5);

        drops.forEach(item -> block.getWorld().dropItemNaturally(dropLocation, item));

        expAdjustment(event);

        int total = event.getExpToDrop();
        int exp = 0;

        while (exp < total) {
            val orb = (ExperienceOrb) dropLocation.getWorld().spawn(dropLocation, ExperienceOrb.class);

            int quantity = random.nextInt(1, total - exp + 1);

            orb.setExperience(exp);
            exp += quantity;
        }

        event.setDropItems(false);
    }

    @EventHandler
    void logout(PlayerQuitEvent event) {
        val player = event.getPlayer();
        val empire = OneblockSkyblock.getEmpire(player);

        if (bosses.values().contains(empire) && empire.onlinePlayers().size() <= 1) {
            resetBoss(empire, player);

            Bukkit.getOnlinePlayers().forEach(p -> p.sendMessage(failedBossFight(empire, p)));
        }
    }

    @EventHandler
    void playerRespawn(PlayerDeathEvent event) {
        val player = event.getPlayer();
        val empire = OneblockSkyblock.getEmpire(player);

        if (!bosses.containsValue(empire)) {
            return;
        }

        respawning.put(player, 15);
        hidePlayer(player);

        if (respawning.keySet().containsAll(empire.onlinePlayers())) {
            resetBoss(empire, player);

            Bukkit.getOnlinePlayers().forEach(p -> p.sendMessage(failedBossFight(empire, p)));
        }
    }

    @EventHandler
    void bossDeath(EntityDeathEvent event) {
        val entity = event.getEntity();

        val empire = bosses.get(entity);

        if (empire == null) {
            return;
        }

        val currentPhase = empire.phase();
        val nextPhase = currentPhase + 1;

        val phase = OneblockSkyblock.getPhase(currentPhase);
        phase.bossDeath().accept(event, event.getEntity().getKiller());

        bosses.remove(entity);

        // Displays remaining bosses to the empire if there are any
        if (bosses.containsValue(empire)) {
            val remainingBosses = new ArrayList<>(bosses.values());
            remainingBosses.removeIf(e -> e != empire);

            if (!remainingBosses.isEmpty()) {
                empire.onlinePlayers().forEach(p -> p.sendMessage(tellRemainingBosses(remainingBosses.size())));
            }
            return;
        }

        val oneBlock = empire.getOneblock();
        val block = oneBlock.getBlock();

        block.setType(Material.CHEST);
        val chest = (Chest) block.getState();

        chest.customName(phase.unwrappedDisplayName().
                append(Component.text(" - Rewards")
                        .color(NamedTextColor.GOLD)
                        .decorate(TextDecoration.BOLD)));

        phase.rewards()
                .fillInventory(chest.getSnapshotInventory(), new Random(),
                        Util.getContext((LivingEntity) Objects.requireNonNull(entity.getLastDamageCause()).getEntity(), phase.rewardsLuck()));
        chest.update();

        empire.phase(nextPhase);
        empire.blocksBroken(-1); // Account for level up chest not counting towards progress
        empire.totalBlocksBroken(empire.totalBlocksBroken() - 1);

        Bukkit.getOnlinePlayers().forEach(p -> p.sendMessage(tellNextPhase(empire, p)));

    }

    private Component tellNextPhase(Empire empire, Player player) {
        return Component.textOfChildren(Component.text("The ", NamedTextColor.GREEN), empire.prefix(), Component.text(" has advanced to phase ", NamedTextColor.GREEN), Util.obfuscatePhaseName(empire, OneblockSkyblock.getEmpire(player)));
    }

    @EventHandler
    void respawnLock(PlayerMoveEvent event) {
        if (respawning.containsKey(event.getPlayer())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    void moveEvent(EntityMoveEvent event) {
        val entity = event.getEntity();
        val empire = bosses.get(entity);

        if (Objects.isNull(empire)) {
            return;
        }

        if (event.getTo().getY() < entity.getWorld().getMinHeight()) {
            val vehicle = entity.getVehicle();

            if (vehicle != null) {
                Bukkit.getScheduler().runTaskLater(OneblockSkyblock.getInstance(), () -> vehicle.addPassenger(entity), 20);
            }

            entity.teleport(empire.getOneblock().add(0.5, 1, 0.5));
        }

    }

    @EventHandler
    void bossTarget(EntityTargetLivingEntityEvent event) {
        if (respawning.containsKey(event.getTarget())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    void bossDamage(EntityDamageEvent event) {
        val entity = event.getEntity();
        val empire = bosses.get(entity);

        if (Objects.isNull(empire)) {
            return;
        }

        if (event.getCause() != EntityDamageEvent.DamageCause.ENTITY_ATTACK && event.getCause() != EntityDamageEvent.DamageCause.ENTITY_SWEEP_ATTACK && event.getCause() != EntityDamageEvent.DamageCause.PROJECTILE) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    void onChestEmpty(InventoryCloseEvent event) {

        val player = (Player) event.getPlayer();
        val empire = OneblockSkyblock.getEmpire(player);

        val inventory = event.getInventory();
        val holder = inventory.getHolder();

        if (!(holder instanceof Chest chest)) {
            return;
        }

        val block = chest.getBlock();

        if (!empire.isOneBlock(block.getLocation())) {
            return;
        }

        if (inventory.isEmpty()) {
            player.breakBlock(block);
        }


    }

    void kickPlayer(Player player) {
        player.kick(Component.text("You are not on a empire").color(NamedTextColor.RED));
    }

    boolean isValidBreak(Player player, Empire empire, Block block) {
        try {
            Empire oneBlockOwner = Util.getOneblockOwner(block);
            // No Owner of the block means it's not a one block, if it is an owned block, the breaking team must own it
            return oneBlockOwner == null || oneBlockOwner == empire;
        } catch (Exception e) {
            e.printStackTrace();
            player.sendMessage(Component.text("Failed to check if block break is valid", NamedTextColor.RED));
            return false;
        }
    }

    void sendInvalidBreakMessage(Player player) {
        player.sendMessage(Component.text("You can only break your empire's \"One Block\"").color(NamedTextColor.RED));
    }

    void replaceBlock(Phase phase, Block block, BlockBreakEvent event, LootContext context) {
        val weights = new HashMap<>(phase.results());

        adjustWeightsForInfinitePhase(Infinite.BLOCK_TAGS, phase, event, weights);

        val result = Util.selectRandom(new Random(), weights, Phase.blockBreakError());
        val data = result.apply(event, context);
        val player = event.getPlayer();

        if (Objects.nonNull(data)) {
            Bukkit.getScheduler().runTaskLater(OneblockSkyblock.getInstance(), () -> {
                block.setBlockData(data, false);
                dontFall(player, block);
            }, 1L);
        } else {
            replaceBlock(phase, block, event, context);
        }

    }

    <T> void adjustWeightsForInfinitePhase(InfiniteTags<T> infiniteTags, Phase phase, BlockBreakEvent event, Map<T, Weight> weights) {

        if (phase != Infinite.PHASE) return;

        val adjustedPercents = new HashMap<>(Maps.asMap(new HashSet<>(OneblockSkyblock.getPhases()), p -> 0));

        val tool = event.getPlayer().getInventory().getItemInMainHand();
        if (tool.getItemMeta() == null) return;

        OneblockSkyblock.getPhases().forEach(p -> adjustedPercents.computeIfPresent(p, (k, v) ->
                v + tool.getEnchantments().getOrDefault(OneblockSkyblock.getTargetingEnchantment(p), 0)
        ));


        weights.forEach((r, w) -> {

                    val tags = infiniteTags.getTags(r);

                    if (tags == null) {
                        return;
                    }

                    val numerator = tags.stream().mapToInt(adjustedPercents::get).sum();
                    val weightMultiplier = 1 + (double) numerator * 0.10;

                    weights.computeIfPresent(r, (k, v) -> Weight.of(v.quality(), v.weight() * weightMultiplier));

                }
        );

    }

    void dontFall(Player player, Block block) {
        val location = player.getLocation();
        val standingOn = Util.isStandingOn(player, block);
        if (standingOn) {
            player.teleport(Util.copyYawAndPitchToNewLocation(player, location));
        } else if (Util.feetInBlock(player, block)) {
            player.teleport(Util.copyYawAndPitchToNewLocation(player, block.getLocation().add(
                    location.getX() % 1d,
                    1,
                    location.getZ() % 1d)));
        }
    }

    boolean checkIfComplete(BlockBreakEvent event, Player player, Phase phase) {
        val empire = OneblockSkyblock.getEmpire(player);
        val remainingBlocks = phase.blocks() - empire.blocksBroken();

        if (remainingBlocks <= 10 && remainingBlocks > 0) {

            empire.onlinePlayers().forEach(p -> p.showTitle(
                    Title.title(Component.empty(),
                            Component.text("" + remainingBlocks, NamedTextColor.GRAY))));
        }

        if (empire.blocksBroken() < phase.blocks()) {
            return false;
        }

        val block = event.getBlock();
        Bukkit.getScheduler().runTaskLater(OneblockSkyblock.getInstance(), () -> {
            block.setBlockData(Material.BEDROCK.createBlockData());
            dontFall(event.getPlayer(), block);
        }, 1L);

        val phaseBosses = phase.boss().apply(event, player);
        phaseBosses.forEach(b -> bosses.put(b, empire));

        for (val boss : phaseBosses) {
            boss.setRemoveWhenFarAway(false);
            boss.setPersistent(true);

            if (boss.customName() != null) {
                boss.customName(phase.bossName());
            }

        }

        Bukkit.getOnlinePlayers().forEach(p -> p.sendMessage(startedBossFight(empire, p)));

        return true;
    }

    private Component startedBossFight(Empire empire, Player player) {
        return Component.textOfChildren(empire.prefix(), Component.text(" has started the boss fight for phase "), Util.obfuscatePhaseName(empire, OneblockSkyblock.getEmpire(player)));
    }

    private Component failedBossFight(Empire empire, Player player) {
        return Component.textOfChildren(empire.prefix(), Component.text(" has failed the boss fight for phase "), Util.obfuscatePhaseName(empire, OneblockSkyblock.getEmpire(player)));
    }

    Component tellRemainingBosses(int remaining) {
        return Component.text("An an enemy has been defeated. There are " + remaining + " enemies left to defeat.")
                .color(NamedTextColor.RED);
    }

    void resetBoss(Empire empire, Player player) {
        for (LivingEntity entity : new ArrayList<>(bosses.keySet())) {
            val emp = bosses.get(entity);

            if (emp.equals(empire)) {
                bosses.remove(entity);
                entity.setHealth(0);
            }
        }

        empire.onlinePlayers().forEach(respawning::remove);
        empire.onlinePlayers().forEach(this::showPlayer);
        empire.blocksBroken(empire.currentPhase().blocks() - 11);

        Bukkit.getPluginManager().callEvent(new BlockBreakEvent(player.getWorld().getBlockAt(empire.getOneblock()), player));
    }

    void hidePlayer(Player player) {
        Bukkit.getOnlinePlayers().forEach(p -> p.hidePlayer(OneblockSkyblock.getInstance(), player));
    }

    void showPlayer(Player player) {
        Bukkit.getOnlinePlayers().forEach(p -> p.showPlayer(OneblockSkyblock.getInstance(), player));
    }


}
