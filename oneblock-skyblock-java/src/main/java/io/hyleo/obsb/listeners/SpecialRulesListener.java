package io.hyleo.obsb.listeners;

import io.hyleo.obsb.MessagesFactory;
import io.hyleo.obsb.api.PhaseEnchantment;
import io.hyleo.obsb.util.ItemBuilder;
import lombok.NonNull;
import lombok.val;
import org.bukkit.damage.DamageSource;
import org.bukkit.damage.DamageType;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Phantom;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.raid.RaidTriggerEvent;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;

import java.util.Map;

public class SpecialRulesListener implements Listener {

    @EventHandler
    void onSleep(@NonNull PlayerBedEnterEvent event) {
        val player = event.getPlayer();

        player.sendMessage(MessagesFactory.BED_ENTER);
        event.setCancelled(true);
    }

    @EventHandler
    void onFallInVoid(@NonNull PlayerMoveEvent event) {

        val player = event.getPlayer();
        val loc = player.getLocation();

        if (loc.getY() >= player.getWorld().getMinHeight()) {
            return;
        }

        val damageSource = DamageSource.builder(DamageType.OUT_OF_WORLD).build();
        val damageEvent = new EntityDamageEvent(player, EntityDamageEvent.DamageCause.VOID, damageSource, player.getHealth());

        player.setLastDamageCause(damageEvent);
        player.setHealth(0);
    }

    @EventHandler
    void onPhantomSpawn(@NonNull EntitySpawnEvent event) {
        val entity = event.getEntity();

        if (entity.getType() != EntityType.PHANTOM) {
            return;
        }

        val phantom = (Phantom) entity;
        val uuid = phantom.getSpawningEntity();

        if (uuid == null) {
            return;
        }

        event.setCancelled(true);
    }

    @EventHandler
    void onRaidEvent(@NonNull RaidTriggerEvent event) {
        event.setCancelled(true);
    }

    //@EventHandler
    void useAnvil(@NonNull PrepareAnvilEvent event) {
        val first = event.getInventory().getFirstItem();
        val second = event.getInventory().getSecondItem();

        if (first == null || second == null) {
            return;
        }

        val secondMeta = second.getItemMeta();
        val enchantments = secondMeta instanceof EnchantmentStorageMeta storageMeta ? storageMeta.getStoredEnchants() : secondMeta.getEnchants();

        for (Map.Entry<Enchantment, Integer> enchantment : enchantments.entrySet()) {

            val enchant = enchantment.getKey();

            if (!(enchant instanceof PhaseEnchantment)) {
                continue;
            }

            val level = enchantment.getValue();

            val result = event.getResult() == null ? first : event.getResult();

            val stack = new ItemBuilder(result).addEnchant(enchant, level, true).asStack();

            event.setResult(stack);
        }
    }
}
