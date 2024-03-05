package io.hyleo.obsb.testing_tools;

import io.hyleo.obsb.OneblockSkyblock;
import lombok.val;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class BossFightCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(Component.text("This command is only available for players.", NamedTextColor.RED));
            return true;
        }

        val validate = args.length == 1;

        if (!validate) {
            sender.sendMessage(Component.text("This command requires one argument: <player>", NamedTextColor.RED));
            return true;
        }

        val player = Bukkit.getPlayer(args[0]);
        val validPlayer = player != null;

        if (!validPlayer) {
            sender.sendMessage(Component.text("Player " + args[0] + " not found.", NamedTextColor.RED));
            return true;
        }

        val empire = OneblockSkyblock.getEmpire(player);

        if (empire == null) {
            sender.sendMessage(Component.text("Player is not in an empire", NamedTextColor.RED));
            return true;
        }

        val phase = OneblockSkyblock.getPhase(empire.phase());

        Bukkit.dispatchCommand(sender, "blocks " + args[0] + " " + phase.blocks());

        return true;
    }
}
