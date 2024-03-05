package io.hyleo.obsb.listeners

import io.hyleo.obsb.api.ButtonClick
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent

class ButtonClickListener : Listener {

    @EventHandler
    fun buttonClick(event: InventoryClickEvent) {

        val item = event.currentItem ?: return
        val clickType = event.click

        val buttonActions = item.enchantments.keys.filterIsInstance<ButtonClick>().filter { it.clickType == clickType }

        buttonActions.forEach {
            try {

                println("calling action")
                val newItem = it.action(event)
                event.inventory.setItem(event.slot, newItem)

            } catch (e: Throwable) {
                e.stackTrace
            }
        }

    }




}
