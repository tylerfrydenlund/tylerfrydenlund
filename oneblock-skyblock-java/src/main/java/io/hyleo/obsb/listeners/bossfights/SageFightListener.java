package io.hyleo.obsb.listeners.bossfights;

import io.hyleo.obsb.listeners.ProgressListener;
import lombok.NonNull;
import lombok.val;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class SageFightListener implements Listener {
    @NotNull
    private final List<@NotNull Entity> arrows = new ArrayList<>();
    @NotNull
    private final List<@NotNull Entity> lightningArrows = new ArrayList<>();
    @NotNull
    private final Random random = new Random();

    @EventHandler
    void arrowShoot(@NonNull EntityShootBowEvent event) {
        val entity = event.getEntity();
        val customNameComponent = entity.customName();
        val projectile = event.getProjectile();

        if (customNameComponent == null) {
            return;
        }

        val customName = LegacyComponentSerializer.legacyAmpersand().serialize(customNameComponent);

        if (customName.equalsIgnoreCase("Sir Nostradamus the Acolyte")) {
            lightningArrows.add(projectile);
        }

        if (random.nextInt(4) != 1) {
            return;
        }

        PotionEffect potionEffect = getPotionEffect(customName);

        if (potionEffect == null) {
            return;
        }

        Arrow arrow = (Arrow) entity.getWorld().spawnEntity(projectile.getLocation(), EntityType.ARROW);

        arrow.addCustomEffect(potionEffect, true);
        arrow.setVelocity(projectile.getVelocity());
        arrows.add(arrow);

        event.setCancelled(true);
    }

    @Nullable
    private static PotionEffect getPotionEffect(@NonNull String customName) {
        PotionEffect potionEffect = null;

        if (customName.equalsIgnoreCase("Sir Seighildan the Swift")) {

            potionEffect = new PotionEffect(PotionEffectType.SLOW, 100, 1);

        } else if (customName.equalsIgnoreCase("Sir Domerthocles the Vicious")) {

            potionEffect = new PotionEffect(PotionEffectType.WEAKNESS, 60, 1);

        } else if (customName.equalsIgnoreCase("Sir Tsoptihscus the Apothecary")) {

            potionEffect = new PotionEffect(PotionEffectType.POISON, 100, 1);
        }

        return potionEffect;
    }

    @EventHandler
    void onBossArrowImpact(@NonNull ProjectileHitEvent event) {
        val projectile = event.getEntity();
        val entity = event.getHitEntity();

        if (!(entity instanceof LivingEntity)) {
            return;
        }

        if (arrows.contains(projectile) && ProgressListener.bosses.get(entity) != null) {
            event.setCancelled(true);
        }

        if (lightningArrows.contains(projectile) && entity instanceof Player player && random.nextInt(4) == 1) {

            val strike = entity.getWorld().strikeLightningEffect(entity.getLocation());
            player.damage(5, strike);

        }
    }
}
