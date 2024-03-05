package io.hyleo.obsb.testing_tools;

import lombok.val;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.stream.Collectors;

public class GearCommand implements CommandExecutor {

    public enum GearType {
        IRON, GOLDEN, DIAMOND, NETHERITE;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command.");
            return true;
        }

        if (args.length != 1) {
            sender.sendMessage(Component.text("Usage: /gear <type>", NamedTextColor.RED));
            return true;
        }

        try {
            val type = GearType.valueOf(args[0].toUpperCase());
            applyGear((Player) sender, type);
            sender.sendMessage(Component.text("You have been given gear of type ", NamedTextColor.GREEN)
                    .append(Component.text(type.name(), NamedTextColor.AQUA))
                    .append(Component.text(".", NamedTextColor.GREEN)));
        } catch (IllegalArgumentException e) {
            sender.sendMessage(Component.text("Invalid gear type. Valid gear types: ").color(NamedTextColor.RED)
                    .append(Component.newline())
                    .append(Component.text(Arrays.stream(GearType.values())
                            .map(n -> String.valueOf(n))
                            .collect(Collectors.joining(", ", "{", "}")), NamedTextColor.AQUA)));
            return true;
        }

        return true;
    }

    void applyGear(Player player, GearType type) {

        val inventory = player.getInventory();
        inventory.clear();
        inventory.setItem(0, new ItemStack(Material.valueOf(type.name() + "_SWORD")));
        inventory.setItem(1, new ItemStack(Material.valueOf(type.name() + "_PICKAXE")));
        inventory.setItem(2, new ItemStack(Material.valueOf(type.name() + "_AXE")));
        inventory.setItem(3, new ItemStack(Material.valueOf(type.name() + "_SHOVEL")));
        inventory.setItem(4, new ItemStack(Material.SHEARS));


        inventory.setHelmet(new ItemStack(Material.valueOf(type.name() + "_HELMET")));
        inventory.setChestplate(new ItemStack(Material.valueOf(type.name() + "_CHESTPLATE")));
        inventory.setLeggings(new ItemStack(Material.valueOf(type.name() + "_LEGGINGS")));
        inventory.setBoots(new ItemStack(Material.valueOf(type.name() + "_BOOTS")));
    }
}
