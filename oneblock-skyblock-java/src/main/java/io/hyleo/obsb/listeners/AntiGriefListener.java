package io.hyleo.obsb.listeners;

import io.hyleo.obsb.OneblockSkyblock;
import io.hyleo.obsb.api.Empire;
import io.hyleo.obsb.util.Util;
import lombok.NonNull;
import lombok.val;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class AntiGriefListener implements Listener {
    @NotNull
    private List<@NotNull Location> getOneblocks() {
        return OneblockSkyblock.getEmpires().stream().map(Empire::getOneblock).collect(Collectors.toList());
    }

    private boolean isOneblock(@NonNull Location location) {
        return getOneblocks().stream().anyMatch(l -> l.distance(location) == 0);
    }

    @EventHandler
    void onEntityExplode(@NonNull EntityExplodeEvent event) {
        val locations = getOneblocks();

        locations.removeIf(l -> l.getWorld() != event.getLocation().getWorld());

        event.blockList().removeIf(b -> locations.stream().anyMatch(l -> l.distance(b.getLocation()) == 0));
    }

    @EventHandler
    void onBossChangeBlock(@NonNull EntityChangeBlockEvent event) {

        if (!(event.getEntity() instanceof LivingEntity entity)) {
            return;
        }

        if (ProgressListener.bosses.containsKey(entity)) {
            event.setCancelled(true);
        }

        val block = event.getBlock();

        val location = block.getLocation();

        val locations = getOneblocks();

        locations.removeIf(l -> l.getWorld() != block.getLocation().getWorld());
        event.setCancelled(locations.stream().anyMatch(l -> l.distance(location) == 0));
    }

    @EventHandler
    public void onEntityDamagedByCactusOneblock(@NonNull EntityDamageByBlockEvent event) {

        val block = event.getDamager();

        if (block == null) {
            return;
        }

        val owner = Util.getOneblockOwner(block);

        if (Objects.isNull(owner)) {
            return;
        }

        event.setCancelled(true);
    }

    @EventHandler
    void onBlockPlaceToCloseToOneblock(@NonNull BlockPlaceEvent event) {
        val block = event.getBlock();

        val upper = new Vector(1, 3, 1);
        val lower = new Vector(-1, 1, -1);

        val inRange = OneblockSkyblock.getEmpires().stream()
                .anyMatch(e -> Util.inRange(e.getOneblock(), lower, upper, block.getLocation()));

        if (!inRange) {
            return;
        }

        event.setCancelled(true);
        event.getPlayer().sendMessage(Component.text("You place a block this close to a one block!", NamedTextColor.RED));

    }

    @EventHandler
    void onPistonMoveOneblock(@NonNull BlockPistonExtendEvent event) {
        val movedBlocks = event.getBlocks();
        val movedAnyOneblock = movedBlocks.stream().anyMatch(b -> isOneblock(b.getLocation()));

        event.setCancelled(movedAnyOneblock);
    }

    @EventHandler
    void onOneblockPhysics(@NonNull BlockPhysicsEvent event) {
        val block = event.getBlock();
        val isOneblock = isOneblock(block.getLocation());

        event.setCancelled(isOneblock);
    }


}
