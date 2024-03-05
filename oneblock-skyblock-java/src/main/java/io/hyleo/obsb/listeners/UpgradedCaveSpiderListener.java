package io.hyleo.obsb.listeners;

import lombok.NonNull;
import lombok.val;
import org.bukkit.entity.CaveSpider;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


public class UpgradedCaveSpiderListener implements Listener {

    @NotNull
    private static final List<@NotNull CaveSpider> spiders = new ArrayList<>();

    public static void addSpider(@NonNull CaveSpider spider) {
        spiders.add(spider);
    }

    public static void removeSpider(@NonNull CaveSpider spider) {
        spiders.remove(spider);
    }

    @EventHandler
    void upgradeSpiderPoison(@NotNull EntityDamageByEntityEvent event) {
        val damager = event.getDamager();
        val damagee = event.getEntity();

        if (!(damager instanceof CaveSpider spider) || !(damagee instanceof LivingEntity livingDamagee)) {
            return;
        }

        if (!spiders.contains(spider)) {
            return;
        }

        if (livingDamagee.hasPotionEffect(PotionEffectType.POISON)) {
            return;
        }

        livingDamagee.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 140, 1));

    }

}
