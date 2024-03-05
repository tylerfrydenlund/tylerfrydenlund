package io.hyleo.obsb.listeners.bossfights;

import io.hyleo.obsb.OneblockSkyblock;
import io.hyleo.obsb.listeners.ProgressListener;
import io.hyleo.obsb.phases.GroundZero;
import io.hyleo.obsb.phases.UnfathomableAbyss;
import lombok.NonNull;
import lombok.val;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.entity.Warden;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class WardenBossFightListener implements Listener {
    @EventHandler
    public void onWardenBossRetarget(@NonNull EntityDamageByEntityEvent event) {

        val entity = event.getEntity();
        val damager = event.getDamager();

        if (!(entity instanceof Warden warden) || !(damager instanceof Player player)) {
            return;
        }

        val empire = ProgressListener.bosses.get(warden);
        val random = new Random();

        if (empire == null || random.nextFloat() > 0.25) {
            return;
        }

        for (Player p : Bukkit.getOnlinePlayers()) {
            warden.setAnger(p, 0);
        }

        warden.setAnger(player, 150);
        warden.setTarget(player);
    }

    @EventHandler
    public void onWardenBossDamage(@NonNull EntityDamageEvent event) {
        val entity = event.getEntity();

        if (!(entity instanceof Warden warden)) {
            return;
        }

        val empire = ProgressListener.bosses.get(entity);

        if (empire == null || empire.currentPhase() != UnfathomableAbyss.PHASE) {
            return;
        }


        if (warden.getHealth() - event.getFinalDamage() >= 200) {
            return;
        }

        val location = warden.getLocation().clone();

        event.setDamage(warden.getHealth() - 200);

        warden.getWorld().playSound(Sound.sound(Key.key("entity.warden.angry"), Sound.Source.HOSTILE, 1.0F, 1.0F), location.getX(), location.getY(), location.getZ());
        warden.teleport(warden.getLocation().add(0, 10000, 0));

        wardenDeath(warden, location);
    }

    @EventHandler
    public void onPlayerDamageWardenBoss(@NonNull EntityDamageByEntityEvent event) {
        val entity = event.getEntity();
        val damager = event.getDamager();

        if (!(entity instanceof Player) || !(damager instanceof Warden)) {
            return;
        }

        val empire = ProgressListener.bosses.get(damager);

        if (empire == null) {
            return;
        }

        if (!event.getCause().equals(EntityDamageEvent.DamageCause.SONIC_BOOM)) {
            return;
        }

        if (empire.currentPhase() == UnfathomableAbyss.PHASE) {
            event.setDamage(event.getFinalDamage() / 5);
        } else if (empire.currentPhase() == GroundZero.PHASE) {
            event.setDamage(event.getFinalDamage() / 4);
        }
    }

    @EventHandler
    public void onpPlayerDeath(@NonNull PlayerDeathEvent event) {
        val player = event.getPlayer();

        for (val entity : player.getWorld().getEntities()) {

            if (!(entity instanceof Warden warden)) {
                continue;
            }

            warden.setAnger(player, 0);
        }
    }

    public void wardenDeath(Warden warden, Location location) {
        new BukkitRunnable() {
            Random random = new Random();
            int beats = 0;

            @Override
            public void run() {
                if (beats >= 10) {
                    warden.getWorld().playSound(Sound.sound(Key.key("entity.warden.roar"), Sound.Source.HOSTILE, 1.0F, 1.0F), location.getX(), location.getY(), location.getZ());
                    warden.setHealth(0);
                    warden.teleport(new Location(location.getWorld(), location.getX(), 0, location.getY()));

                    this.cancel();
                }

                warden.getWorld().playSound(Sound.sound(Key.key("entity.warden.heartbeat"), Sound.Source.HOSTILE, 2.0F, 1.0F), location.getX(), location.getY(), location.getZ());
                warden.getWorld().spawnParticle(Particle.SHRIEK, location.clone().add(0, 0.5, 0), 1, 0);
                warden.getWorld().spawnParticle(Particle.SHRIEK, location.clone().add(0, 0.5, 0), 1, 2);
                warden.getWorld().spawnParticle(Particle.SHRIEK, location.clone().add(0, 0.5, 0), 1, 4);
                warden.getWorld().spawnParticle(Particle.SHRIEK, location.clone().add(0, 0.5, 0), 1, 6);
                warden.getWorld().spawnParticle(Particle.SHRIEK, location.clone().add(0, 0.5, 0), 1, 8);
                warden.getWorld().spawnParticle(Particle.SHRIEK, location.clone().add(0, 0.5, 0), 1, 10);
                warden.getWorld().spawnParticle(Particle.SHRIEK, location.clone().add(0, 0.5, 0), 1, 12);
                warden.getWorld().spawnParticle(Particle.SHRIEK, location.clone().add(0, 0.5, 0), 1, 14);
                warden.getWorld().spawnParticle(Particle.SHRIEK, location.clone().add(0, 0.5, 0), 1, 16);
                warden.getWorld().spawnParticle(Particle.SHRIEK, location.clone().add(0, 0.5, 0), 1, 18);


                if (beats == 5) {
                    new BukkitRunnable() {
                        int changes = 0;

                        @Override
                        public void run() {
                            if (changes >= 4) {
                                this.cancel();
                            }

                            warden.setHealth(random.nextDouble(0.1, 400.0));
                            changes++;
                        }
                    }.runTaskTimer(OneblockSkyblock.getInstance(), 0, 10);
                } else if (beats == 7) {
                    new BukkitRunnable() {
                        int changes = 0;

                        @Override
                        public void run() {
                            if (changes >= 8) {
                                this.cancel();
                            }

                            warden.setHealth(random.nextDouble(0.1, 400.0));
                            changes++;
                        }
                    }.runTaskTimer(OneblockSkyblock.getInstance(), 0, 5);
                } else if (beats == 9) {
                    new BukkitRunnable() {
                        int changes = 0;

                        @Override
                        public void run() {
                            if (changes >= 19) {
                                this.cancel();
                            }

                            warden.setHealth(random.nextDouble(0.1, 400.0));
                            changes++;
                        }
                    }.runTaskTimer(OneblockSkyblock.getInstance(), 0, 1);
                }
                beats++;
            }
        }.runTaskTimer(OneblockSkyblock.getInstance(), 20, 20);
    }
}
