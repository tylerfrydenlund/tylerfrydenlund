package io.hyleo.obsb.listeners;

import io.hyleo.obsb.OneblockSkyblock;
import io.hyleo.obsb.api.Empire;
import io.hyleo.obsb.displays.Bossbar;
import io.hyleo.obsb.displays.Nametag;
import io.hyleo.obsb.displays.Sidebar;
import lombok.NonNull;
import lombok.val;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;

public class LoginListener implements Listener {

    @EventHandler
    void login(@NonNull PlayerJoinEvent event) {
        val player = event.getPlayer();

        if (!OneblockSkyblock.isInEmpire(player)) {
            kick(player);
            return;
        }

        val empire = OneblockSkyblock.getEmpire(player);

        player.teleport(empire.getOneblock().add(0.5, 2, 0.5));

        Bossbar.assign(player);
        Sidebar.assign(player);
        Nametag.assign(player);

        event.joinMessage(joinOrLeaveMessage(player, false, empire));
    }

    @EventHandler
    void quit(PlayerQuitEvent event) {
        event.quitMessage(joinOrLeaveMessage(event.getPlayer(), true, OneblockSkyblock.getEmpire(event.getPlayer())));
    }

    @NotNull
    Component joinOrLeaveMessage(@NonNull Player player, boolean left, @NonNull Empire empire) {
        val color = empire.color();
        val status = " " + (left ? "left" : "joined");

        return Component.text("[", color)
                .append(Component.text(empire.name(), color))
                .append(Component.text("] ", color))
                .append(Component.text(player.getName(), color))
                .append(Component.text(status + " the game!", empire.getComplimentaryTextColor()));
    }

    void kick(Player player) {
        player.kick(
                Component.text("You are not apart of an Empire", NamedTextColor.RED)
                        .append(Component.newline())
                        .append(Component.text("Ask Hypenage or LeoArcturus to be apart of an Empire", NamedTextColor.AQUA)));
    }


}
