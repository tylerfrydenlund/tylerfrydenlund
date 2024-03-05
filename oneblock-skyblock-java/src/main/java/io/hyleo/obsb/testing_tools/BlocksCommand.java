package io.hyleo.obsb.testing_tools;

import io.hyleo.obsb.MessagesFactory;
import io.hyleo.obsb.OneblockSkyblock;
import lombok.val;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

public class BlocksCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(MessagesFactory.COMMAND_ONLY_PLAYERS);
            return true;
        }

        val validate = args.length == 2;

        if (!validate) {
            sender.sendMessage(MessagesFactory.commandRequiresArguments("player", "blocks"));
            return true;
        }

        val player = Bukkit.getPlayer(args[0]);
        val validPlayer = player != null;

        if (!validPlayer) {
            sender.sendMessage(MessagesFactory.playerNotFound(args[0]));
            return true;
        }

        val empire = OneblockSkyblock.getEmpire(player);

        if (empire == null) {
            sender.sendMessage(MessagesFactory.playerNotInEmpire(args[0]));
            return true;
        }
        val phase = OneblockSkyblock.getPhase(empire.phase());

        val blocksArg = args[1];
        val validBlocksNumber = Pattern.compile("-?\\d+(\\.\\d+)?").matcher(blocksArg).matches();
        val blocks = validBlocksNumber ? Long.valueOf(blocksArg) : null;
        val validBlocks = validBlocksNumber && blocks >= 0 && blocks <= phase.blocks();

        if (!validBlocksNumber) {
            sender.sendMessage(MessagesFactory.BLOCKS_MUST_BE_INT);
            return true;
        }

        if (!validBlocks) {
            sender.sendMessage(MessagesFactory.blocksMustBeBetween(phase.blocks()));
            return true;
        }

        empire.blocksBroken(blocks);

        empire.onlinePlayers().forEach(p -> p.sendMessage(
                Component.text("Your empire's broken blocks has been set to ", NamedTextColor.GREEN)
                        .append(Component.text(blocks + "", NamedTextColor.AQUA)
                                .append(Component.text(" by " + sender.getName(), NamedTextColor.GREEN)))));

        sender.sendMessage(Component.text("You have set the blocks of ", NamedTextColor.GREEN)
                .append(empire.prefix())
                .append(Component.text(" to ", NamedTextColor.GREEN)
                        .append(Component.text(blocks.toString(), NamedTextColor.AQUA))));

        if (blocks == phase.blocks()) {
            val event = new BlockBreakEvent(empire.getOneblock().getBlock(), player);
            Bukkit.getPluginManager().callEvent(event);

            empire.onlinePlayers().forEach(p -> p.sendMessage(
                    Component.text("Your boss fight has been started by " + sender.getName(), NamedTextColor.GREEN)));

            sender.sendMessage(Component.text("You started ", NamedTextColor.GREEN)
                    .append(empire.prefix())
                    .append(Component.text("'s boss fight!' " + blocks, NamedTextColor.GREEN)));

        }

        return true;
    }
}
