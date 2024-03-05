package io.hyleo.obsb.displays;

import io.hyleo.obsb.OneblockSkyblock;
import io.hyleo.obsb.api.Empire;
import io.hyleo.obsb.api.display.Timings;
import io.hyleo.obsb.display.Destination;
import io.hyleo.obsb.display.Displays;
import io.hyleo.obsb.display.text.Palette;
import io.hyleo.obsb.display.text.Pattern;
import io.hyleo.obsb.display.text.TextAnimation;
import lombok.experimental.UtilityClass;
import lombok.val;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;

import java.text.SimpleDateFormat;
import java.util.Date;

@UtilityClass
public class Sidebar {


    public void assign(Player player) {

        val empire = OneblockSkyblock.getEmpire(player);

        title(player);
        date(player, 9);
//        empty(player, 11);
//        onlinePlayers(player, 10);
//        deaths(player, 9);

        blocks(player, empire, 7);
        empireNameTop(player, empire, 6);
        empireNameBottom(player, empire, 5);
        phaseInfo(player, empire, 4);
        phaseNameTop(player, empire, 3);
        phaseNameBottom(player, empire, 2);

        youArePlaying(player, 1);
    }

    void title(Player player) {
        val destination = Destination.sidebarTitle();

        val colors = OneblockSkyblock.getEmpires().stream().map(Empire::color).toArray(TextColor[]::new);

        val palette = Palette.of(13, DisplayUtil.mclaren, DisplayUtil.eggshell, DisplayUtil.mclaren);

        val version = OneblockSkyblock.getInstance().getDescription().getVersion();

        val animation = TextAnimation.of(Pattern.SLIDE, palette, Component::empty, () -> "SKYBLOCK", ()-> Component.text(" v" + version, NamedTextColor.DARK_GRAY), TextDecoration.BOLD);

        val timings = Timings.of().interval(2).repeatDelay(200).build();

        Displays.SCOREBOARD.display(player, destination, timings, animation);
    }

    void empty(Player player, int line) {
        val destination = Destination.sidebarLine(line, false);
        val palette = Palette.of(20, NamedTextColor.WHITE);
        val animation = TextAnimation.of(Pattern.SWIPE, palette, () -> "");

        val timings = Timings.fast();

        Displays.SCOREBOARD.display(player, destination, timings, animation);
    }


    void date(Player player, int line) {
        String pattern = "MM/dd/yy";

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        val destination = Destination.sidebarLine(line, false);
        val palette = Palette.of(1, NamedTextColor.WHITE, NamedTextColor.WHITE);
        val animation = TextAnimation.of(Pattern.SWIPE, palette, () -> Component.textOfChildren(Component.text(simpleDateFormat.format(new Date()), NamedTextColor.DARK_GRAY)),
                () -> "");

        val timings = Timings.fast();

        Displays.SCOREBOARD.display(player, destination, timings, animation);
    }
    void blocks(Player player, Empire empire, int line) {
        val destination = Destination.sidebarLine(line, false);
        val palette = Palette.of(1, NamedTextColor.WHITE, NamedTextColor.WHITE);
        val animation = TextAnimation.of(Pattern.SWIPE, palette, () -> Component.text("Blocks:", DisplayUtil.eggshell),
                () -> " ", () -> Component.text(String.format("%,d",empire.totalBlocksBroken()), DisplayUtil.lime));

        val timings = Timings.fast();

        Displays.SCOREBOARD.display(player, destination, timings, animation);
    }

    void empireNameTop(Player player, Empire empire, int line) {

        val destination = Destination.sidebarLine(line, false);
        val palette = Palette.of(20, empire.color(), empire.getComplimentaryTextColor());
        val animation = TextAnimation.of(Pattern.SWIPE, palette, () -> Component.text("Empire: ",DisplayUtil.eggshell),
                empire::wrappedDisplayNameLine1);

        val timings = Timings.fast();

        Displays.SCOREBOARD.display(player, destination, timings, animation);
    }

    void empireNameBottom(Player player, Empire empire, int line) {

        val destination = Destination.sidebarLine(line, false);
        val palette = Palette.of(20, empire.color(), empire.getComplimentaryTextColor());
        val animation = TextAnimation.of(Pattern.SWIPE, palette, () -> Component.text("          "),
                empire::wrappedDisplayNameLine2);

        val timings = Timings.fast();

        Displays.SCOREBOARD.display(player, destination, timings, animation);
    }


    void onlinePlayers(Player player, int line) {

        val destination = Destination.sidebarLine(line, false);
        val palette = Palette.of(20, NamedTextColor.GREEN, NamedTextColor.GOLD);
        val animation = TextAnimation.of(Pattern.FLASH, palette, () -> Component.text("Online: ", DisplayUtil.eggshell),
                () -> Bukkit.getOnlinePlayers().size() + "");

        val timings = Timings.of().interval(2).repeatDelay(100).build();

        Displays.SCOREBOARD.display(player, destination, timings, animation);


    }

    void deaths(Player player, int line) {

        val destination = Destination.sidebarLine(line, false);
        val palette = Palette.of(20, NamedTextColor.RED, NamedTextColor.YELLOW);
        val animation = TextAnimation.of(Pattern.FLASH, palette, () -> Component.text("Deaths: ",DisplayUtil.eggshell),
                () -> player.getStatistic(Statistic.DEATHS) + "");

        val timings = Timings.of().interval(2).repeatDelay(100).build();
        Displays.SCOREBOARD.display(player, destination, timings, animation);
    }
    void phaseInfo(Player player, Empire empire, int line) {
        val destination = Destination.sidebarLine(line, false);
        val palette = Palette.of(20, NamedTextColor.GRAY, NamedTextColor.BLACK);
        val animation = TextAnimation.of(Pattern.FLASH, palette, () -> Component.text("Phase:",DisplayUtil.eggshell),
                () -> " [" + DisplayUtil.phaseNumber(empire) + "] ", () -> DisplayUtil.blocksStatus(empire));

        val timings = Timings.of().interval(1).repeatDelay(100).build();

        Displays.SCOREBOARD.display(player, destination, timings, animation);
    }
    void phaseNameTop(Player player, Empire empire, int line) {

        val destination = Destination.sidebarLine(line, false);
        val palette = Palette.of(20, NamedTextColor.GRAY, NamedTextColor.BLACK);
        val animation = TextAnimation.of(Pattern.FLASH, palette, () -> Component.text("        "), ()-> " ", () -> (TextComponent) OneblockSkyblock.getPhase(empire).wrappedDisplayNameLine1());

        val timings = Timings.of().interval(1).repeatDelay(100).build();

        Displays.SCOREBOARD.display(player, destination, timings, animation);
    }

    void phaseNameBottom(Player player, Empire empire, int line) {

        val destination = Destination.sidebarLine(line, false);
        val palette = Palette.of(20, NamedTextColor.GRAY, NamedTextColor.BLACK);
        val animation = TextAnimation.of(Pattern.FLASH, palette, () -> Component.text("        "), ()-> " ", () -> (TextComponent) OneblockSkyblock.getPhase(empire).wrappedDisplayNameLine2());

        val timings = Timings.of().interval(1).repeatDelay(100).build();

        Displays.SCOREBOARD.display(player, destination, timings, animation);
    }




    void youArePlaying(Player player, int line) {
        val destination = Destination.sidebarLine(line, false);
        val palette = Palette.of(1, NamedTextColor.WHITE);
        val animation = TextAnimation.of(Pattern.FLASH, palette, () -> "");

        val timings = Timings.of().interval(1).repeatDelay(100).build();

        Displays.SCOREBOARD.display(player, destination, timings, animation);
    }
}
