package io.hyleo.obsb.listeners;

import io.hyleo.obsb.OneblockSkyblock;
import io.hyleo.obsb.util.ItemBuilder;
import lombok.val;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GearDownGradeListener implements Listener {

    static List<String> tools = List.of("sword", "pickaxe", "axe", "hoe", "shovel");
    static List<String> armor = List.of("helmet", "chestplate", "leggings", "boots");
    static List<String> items = Stream.concat(tools.stream(), armor.stream())
            .collect(Collectors.toList());
    static List<String> ranking = List.of("netherite", "diamond", "golden", "iron", "stone", "wooden");
    static Map<String, String> armorRemaps = Map.of("stone", "chainmail", "wooden", "leather");

    static Map<Material, Material> downgrades = new HashMap<>();

    static {
        for (val material : Material.values()) {
            val split = material.name().split("_");
            val type = split[0];
            val name = material.name().replaceFirst(type, "");

            if (split.length == 1 || items.stream().noneMatch(i -> name.replaceAll("_", "")
                    .toLowerCase().contains(i))) {
                continue;
            }

            val rank = type.equalsIgnoreCase("chainmail") ? ranking.indexOf("stone") : ranking.indexOf(type.toLowerCase());

            if (rank == ranking.size() - 1) continue;

            val nextRank = ranking.get(rank + 1);

            val newRank = tools.contains(name.replaceFirst("_", "").toLowerCase()) ?
                    nextRank : armorRemaps.getOrDefault(nextRank, nextRank);


            downgrades.put(material, rank == -1 || rank == ranking.size() - 1 ? null :
                    Material.valueOf(newRank.toUpperCase() + name));
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    void death(PlayerDeathEvent event) {
        val player = event.getPlayer();
        val empire = OneblockSkyblock.getEmpire(player);
        val inventory = player.getInventory().getContents();

        if (ProgressListener.bosses.containsValue(empire)) {
            event.setKeepInventory(true);
            event.getDrops().clear();
            return;
        }

        if (player.getLastDamageCause().getCause() != EntityDamageEvent.DamageCause.VOID) {
            return;
        }

        for (var index = 0; index < inventory.length; ++index) {
            val item = downGrade(inventory[index]);

            if (item.getType() != Material.AIR) {
                event.getDrops().remove(inventory[index]);
            }
            inventory[index] = item;

        }

        Bukkit.getScheduler().runTaskLater(OneblockSkyblock.getInstance(), () -> {
            player.getInventory().setContents(inventory);
            player.updateInventory();
        }, 1);

        event.setShouldDropExperience(false);
        event.setNewLevel(player.getLevel() - 1);
    }

    ItemStack downGrade(ItemStack item) {
        if (item == null) return new ItemStack(Material.AIR);

        var newItem = new ItemStack(item);
        val newType = downgrades.get(item.getType());
        newItem.setType(newType == null ? Material.AIR : newType);

        val builder = new ItemBuilder(newItem);

        val downLevels = newItem.getEnchantments().entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue() - 1));

        downLevels.entrySet().stream().filter(e -> e.getValue() > 0)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)).forEach((e,l)->
                builder.addEnchant(e, l, true));

        newItem = builder.asStack();


        ItemStack finalNewItem = newItem;
        downLevels.forEach((k, v) -> {
            if (v <= 0) finalNewItem.removeEnchantment(k);
        });

        if (newItem.hasItemMeta()) {
            val meta = newItem.getItemMeta();
            meta.displayName(new ItemStack(newType == null ? Material.AIR : newType).displayName());
            if (meta instanceof Damageable damageable) {
                val ratio = ((double) newType.getMaxDurability()) / item.getType().getMaxDurability();

                damageable.setDamage((int) (ratio *
                        ((Damageable) item.getItemMeta()).getDamage()));

                newItem.setItemMeta(meta);
            }

        }

        return newItem;
    }

}
