package io.hyleo.obsb.listeners;

import io.hyleo.obsb.OneblockSkyblock;
import lombok.NonNull;
import lombok.val;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.plugin.PluginDescriptionFile;
import org.jetbrains.annotations.NotNull;

public class ServerPingListener implements Listener {

    @EventHandler
    void serverPing(@NonNull ServerListPingEvent event) {

        val instance = OneblockSkyblock.getInstance();
        val pluginDescription = instance.getDescription();

        val topLine = topLine(pluginDescription);
        val bottomLine = bottomLine(pluginDescription);

        event.setMaxPlayers(OneblockSkyblock.getMaxPlayers());
        event.motd(Component.textOfChildren(topLine, Component.newline(), bottomLine));
    }

    @NotNull
    Component topLine(@NonNull PluginDescriptionFile pluginDescription) {
        val apiVersion = pluginDescription.getAPIVersion();

        return Component.text("Oneblock Skyblock", TextColor.fromHexString("#6495ED"))
                .append(Component.text(" [" + apiVersion + ".x]", TextColor.fromHexString("#0BDA51")));
    }
    @NotNull
    Component bottomLine(@NonNull PluginDescriptionFile pluginDescription) {
        val pluginVersion = pluginDescription.getVersion();

        return Component.text("Version: ", NamedTextColor.GRAY)
                .append(Component.text(pluginVersion, NamedTextColor.DARK_AQUA));
    }

}
