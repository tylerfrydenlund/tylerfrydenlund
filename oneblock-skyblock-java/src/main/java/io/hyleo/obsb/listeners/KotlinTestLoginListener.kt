package io.hyleo.obsb.listeners

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.event.ClickEvent
import net.kyori.adventure.text.event.HoverEvent
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.Style
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import java.net.URL

object KotlinTestLoginListener : Listener {


    // component + component = component
    operator fun Component.plus(component: Component) = Component.textOfChildren(this@plus, component)

    // component + string = component
    operator fun Component.plus(string: String) = this@plus.append(Component.text(string, this@plus.style()))

    operator fun Component.plus(click: ClickEvent) = this@plus.clickEvent(click)

    operator fun <T> Component.plus(hover: HoverEvent<T>) = this@plus.hoverEvent(hover)

    operator fun Component.plus(url: URL) = this@plus.clickEvent(ClickEvent.openUrl(url.toExternalForm()))
        .hoverEvent(HoverEvent.showText(NamedTextColor.WHITE + "Click to visit: " + (NamedTextColor.GREEN + url.toExternalForm())))

    operator fun TextDecoration.plus(color: TextColor) = Style.style(color, this@plus)

    operator fun TextColor.plus(decoration: TextDecoration) = Style.style(this@plus, decoration)

    operator fun Style.plus(style: Style) = this@plus.merge(style)

    operator fun Style.plus(component: Component) = component.style(this@plus)

    operator fun Style.plus(string: String) = Component.text(string, this@plus)

    // decoration + component = component
    operator fun TextDecoration.plus(component: Component) = component.decorate(this@plus)

    // text color + string = component
    operator fun TextColor.plus(text: String) = Component.text(text, this@plus)


    @EventHandler
    fun join(event: PlayerJoinEvent) {

        val player = event.player
        val url = URL("https://www.google.com")

        player.sendMessage(TextDecoration.BOLD + NamedTextColor.DARK_AQUA + "Welcome back " + (NamedTextColor.RED + "[ADMIN] " + player.name + url))

    }

}