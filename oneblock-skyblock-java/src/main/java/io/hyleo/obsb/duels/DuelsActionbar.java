package io.hyleo.obsb.duels;

import io.hyleo.obsb.api.display.Timings;
import io.hyleo.obsb.display.Displays;
import io.hyleo.obsb.display.displays.Actionbar;
import io.hyleo.obsb.display.text.Palette;
import io.hyleo.obsb.display.text.Pattern;
import io.hyleo.obsb.display.text.TextAnimation;
import lombok.val;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

public class DuelsActionbar {


    public static void show(Player player, Duel duel) {

        val palette = Palette.of(1, NamedTextColor.RED);
        val animation = TextAnimation.of(Pattern.SLIDE, palette, () -> (TextComponent) timeRemaining(player, duel),
                () -> remainingOpponents(duel, player), () -> (TextComponent) yourTeam(duel, player));

        val timings = Timings.of().interval(1).build();

        Displays.ACTIONBAR.display(player, Actionbar.BAR, timings, animation, (p) -> !duel.finished());
    }


    static Component seperator() {
        return Component.text(" - ", NamedTextColor.GRAY);
    }

    static String timer(long time) {

        SimpleDateFormat localDateFormat = new SimpleDateFormat("mm:ss");

        return localDateFormat.format(new Date(time));
    }

    static Component timeRemaining(Player player, Duel duel) {
        val status = duel.status();

        if (status == DuelStatus.WAITING) {
            return Component.textOfChildren(Component.text("Waiting for players...", NamedTextColor.AQUA), seperator());
        }
        val remaining = duel.timeRemaining();

        if (status != DuelStatus.ACCEPTED || remaining < 0) {
            Displays.ACTIONBAR.hide(player, Actionbar.BAR);
        }

        val timeRemaining = Component.text("Time: ", NamedTextColor.WHITE);
        val time = Component.text(timer(remaining), NamedTextColor.AQUA);
        return Component.textOfChildren(timeRemaining, time, seperator());

    }

    static String remainingOpponents(Duel duel, Player player) {

        val status = duel.status();

        if (status == DuelStatus.WAITING) {
            return timer(((Duel.ACCEPT_TIMEOUT + duel.organized()) - System.currentTimeMillis()));
        }

        return "Opponents: " + duel.teams().stream()
                .filter(team -> new HashSet<>(team).equals(new HashSet<>(duel.team(player.getUniqueId()))))
                .mapToInt(Collection::size).sum();
    }

    static Component yourTeam(Duel duel, Player player) {

        val status = duel.status();

        if (status == DuelStatus.WAITING) {
            return Component.textOfChildren(seperator(), Component.text(duel.whoAccepted().size() + "/" + duel.players().size(), NamedTextColor.GREEN));
        }

        val yourTeam = Component.text("Team: ", NamedTextColor.WHITE);
        val team = Component.text(duel.team(player.getUniqueId()).size(), NamedTextColor.GREEN);
        return Component.textOfChildren(seperator(), yourTeam, team);
    }


}
