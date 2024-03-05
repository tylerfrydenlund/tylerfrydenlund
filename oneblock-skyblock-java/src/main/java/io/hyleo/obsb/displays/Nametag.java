package io.hyleo.obsb.displays;

import io.hyleo.obsb.OneblockSkyblock;
import io.hyleo.obsb.api.display.Timings;
import io.hyleo.obsb.display.Destination;
import io.hyleo.obsb.display.Displays;
import io.hyleo.obsb.display.text.Palette;
import io.hyleo.obsb.display.text.Pattern;
import io.hyleo.obsb.display.text.TextAnimation;
import io.hyleo.obsb.api.Empire;
import io.hyleo.obsb.util.Util;
import lombok.val;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Nametag {

    public static void assign(Player player) {
        val empire = OneblockSkyblock.getEmpire(player);

        player.displayName(Component.text(player.getName(), empire.color()));
        player.customName(Component.text(player.getName(), empire.color()));

        for (val online : Bukkit.getOnlinePlayers()) {
            prefix(online, player, OneblockSkyblock.getEmpire(online));
            suffix(online, player, OneblockSkyblock.getEmpire(online));
        }

        for (val viewer : Bukkit.getOnlinePlayers()) {
            prefix(player, viewer, empire);
            suffix(player, viewer, empire);
        }

    }

    static void prefix(Player player, Player viewer, Empire empire) {
        val destination = Destination.prefix(player);

        val palette = Palette.of(20, empire.color(), empire.getComplimentaryTextColor());
        val animation = TextAnimation.of(Pattern.SLIDE, palette,
                () -> Component.text("[", NamedTextColor.DARK_GRAY), empire::name,
                () -> Component.text("] ", NamedTextColor.DARK_GRAY));

        val timings = Timings.of().interval(1).repeatDelay(100).build();

        Displays.SCOREBOARD.display(viewer, destination, timings, animation);
    }

    static void suffix(Player player, Player viewer, Empire empire) {
        val destination = Destination.suffix(player);
        val palette = Palette.of(20, NamedTextColor.GRAY, NamedTextColor.BLACK);

        val animation = TextAnimation.of(Pattern.FLASH, palette, Component::empty, () -> " [" + DisplayUtil.phaseNumber(empire) + "] ", () -> {

            var suffix = Util.obfuscatePhaseName(empire, OneblockSkyblock.getEmpire(viewer));

            suffix = suffix.append(DisplayUtil.blocksStatus(empire));

            return (TextComponent) suffix;
        });

        val timings = Timings.of().interval(3).repeatDelay(100).build();

        Displays.SCOREBOARD.display(viewer, destination, timings, animation);
    }
}
