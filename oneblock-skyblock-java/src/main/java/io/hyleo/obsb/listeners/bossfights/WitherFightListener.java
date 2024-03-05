package io.hyleo.obsb.listeners.bossfights;

import lombok.NonNull;
import lombok.val;
import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class WitherFightListener implements Listener {

    @NotNull
    private static final List<@NotNull Location> blocks = new ArrayList<>();
    @NotNull
    private final List<@NotNull WitherSkull> skulls = new ArrayList<>();

    public static void addBlockLocation(@NonNull Location location) {
        blocks.add(location);
    }

    public static void removeBlockLocation(@NonNull Location location) {
        blocks.remove(location);
    }

    public static void removeBlockLocations(@NonNull Collection<@NotNull Location> locations) {
        blocks.removeAll(locations);
    }

    @EventHandler
    void onBreakSummonArray(@NonNull BlockBreakEvent event) {
        val location = event.getBlock().getLocation();

        for (Location loc : blocks) {
            if (location.distance(loc) != 0) {
                continue;
            }

            event.setCancelled(true);
        }
    }

    @EventHandler
    void witherBlockBreak(@NonNull EntityChangeBlockEvent event) {

        if (!(event.getEntity() instanceof Wither)) {
            return;
        }

        event.setCancelled(true);
    }

    @EventHandler
    void onFireballShoot(@NonNull ProjectileLaunchEvent event) {
        val projectile = event.getEntity();

        if (!(projectile.getShooter() instanceof Wither)) {
            return;
        }

        if (!(projectile instanceof WitherSkull skull)) {
            return;
        }

        skulls.add(skull);
    }

    @EventHandler
    void onWithSkullExplode(@NonNull EntityExplodeEvent event) {
        val projectile = event.getEntity();

        if (!(projectile instanceof WitherSkull)) {
            return;
        }

        if (!skulls.contains(projectile)) {
            return;
        }

        event.blockList().clear();
    }


}
