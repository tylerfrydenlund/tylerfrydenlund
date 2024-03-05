package io.hyleo.obsb.listeners;

import lombok.NonNull;
import lombok.val;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Ghast;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class GhastFireballListener implements Listener {
    @NotNull
    private final List<@NotNull Fireball> fireballs = new ArrayList<>();

    @EventHandler
    public void onFireballShoot(@NonNull ProjectileLaunchEvent event) {
        val projectile = event.getEntity();

        if (!(projectile.getShooter() instanceof Ghast)) {
            return;
        }

        if (!(projectile instanceof Fireball fireball)) {
            return;
        }

        fireballs.add(fireball);
    }

    @EventHandler
    public void onFireballExplode(@NonNull EntityExplodeEvent event) {
        val projectile = event.getEntity();

        if (!(projectile instanceof Fireball)) {
            return;
        }

        if (fireballs.contains(projectile)) {
            event.blockList().clear();
        }
    }
}
