package io.hyleo.obsb.testing_tools;

import io.hyleo.obsb.OneblockSkyblock;
import lombok.val;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

public class PhaseCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        val validate = args.length == 2 || args.length == 3;

        if (!validate) {
            sender.sendMessage(Component.text("This command requires two arguments: <player> <phase>", NamedTextColor.RED));
            return true;
        }

        val player = Bukkit.getPlayer(args[0]);
        val validPlayer = player != null;

        val phaseArg = args[1];
        val validPhaseNumber = Pattern.compile("-?\\d+(\\.\\d+)?").matcher(phaseArg).matches();
        var phase = validPhaseNumber ? Integer.valueOf(phaseArg) : null;
        val validPhase = validPhaseNumber && phase > 0 && phase <= OneblockSkyblock.getPhases().size();


        if (!validPlayer) {
            sender.sendMessage(Component.text("Player " + args[0] + " not found.", NamedTextColor.RED));
            return true;
        }

        if (!validPhaseNumber) {
            sender.sendMessage(Component.text("Phase number is not valid", NamedTextColor.RED));
            return true;
        }

        if (!validPhase) {
            sender.sendMessage(Component.text("Phases must be set between 1 and " + OneblockSkyblock.getPhases().size(),
                    NamedTextColor.RED));
            return true;
        }

        val empire = OneblockSkyblock.getEmpire(player);

        empire.phase(phase - 1);
        empire.onlinePlayers().forEach(p -> p.sendMessage(Component.text("You have been set to the ", NamedTextColor.GREEN)
                .append(OneblockSkyblock.getPhase(phase - 1).unwrappedDisplayName()).append(Component.text(" phase by " + sender.getName(), NamedTextColor.GREEN))));

        sender.sendMessage(Component.text("You have set the phase for ", NamedTextColor.GREEN).append(empire.prefix())
                .append(Component.text(" to ", NamedTextColor.GREEN).append(OneblockSkyblock.getPhase(phase - 1).unwrappedDisplayName())));
        if (args.length == 3) {
            // Set the blocks
            Bukkit.dispatchCommand(sender, "blocks " + args[0] + " " + args[2]);
        } else {
            empire.blocksBroken(0);
        }

        return true;
    }


}
