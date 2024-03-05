package io.hyleo.obsb.testing_tools;

import io.hyleo.obsb.OneblockSkyblock;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.val;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LazyPhaseCommand implements CommandExecutor {

    boolean next;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
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
        val phase = empire.phase() + (next ? 2 : 0);

        if (next && phase > OneblockSkyblock.getPhases().size()) {
            sender.sendMessage(Component.text("This player is already on the last phase!", NamedTextColor.RED));
            return true;
        }

        if (!next && phase < 1) {
            sender.sendMessage(Component.text("This player is already on the first phase!", NamedTextColor.RED));
            return true;
        }

        Bukkit.dispatchCommand(sender, "phase " + args[0] + " " + phase);

        return true;
    }
}
