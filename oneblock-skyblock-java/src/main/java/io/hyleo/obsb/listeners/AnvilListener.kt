package io.hyleo.obsb.listeners

import io.hyleo.obsb.OneblockSkyblock
import io.hyleo.obsb.util.ItemBuilder
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.PrepareAnvilEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.Damageable
import kotlin.math.min

class AnvilListener : Listener {

    private val materialAmounts = mutableMapOf<Material, Int>()

    init {

        Material.values().filter { it.name.lowercase().contains("helmet") }.associateWith { 5 }.toMap(materialAmounts)
        Material.values().filter { it.name.lowercase().contains("chestplate") }.associateWith { 8 }
            .toMap(materialAmounts)
        Material.values().filter { it.name.lowercase().contains("leggings") }.associateWith { 7 }.toMap(materialAmounts)
        Material.values().filter { it.name.lowercase().contains("boots") }.associateWith { 4 }.toMap(materialAmounts)

        Material.values().filter { it.name.lowercase().contains("sword") }.associateWith { 2 }.toMap(materialAmounts)
        Material.values().filter { it.name.lowercase().contains("pickaxe") }.associateWith { 3 }.toMap(materialAmounts)
        Material.values().filter { it.name.lowercase().contains("axe") }.associateWith { 3 }.toMap(materialAmounts)
        Material.values().filter { it.name.lowercase().contains("shovel") }.associateWith { 1 }.toMap(materialAmounts)

        Material.values().filter { it.name.lowercase().contains("shears") }.associateWith { 2 }.toMap(materialAmounts)

    }

    @EventHandler
    fun anvilPrepare(event: PrepareAnvilEvent) {

        var repairCost = 0
        val inventory = event.inventory
        val first = inventory.firstItem ?: return
        val second = inventory.secondItem


        // The first item is being changed by a second item
        if (second != null) {
            // If the item is being repaired in someway
            if (first.isRepairableBy(second)) {

                val max = first.type.maxDurability
                val newDurability = if (first.type == second.type) {
                    val firstDamage = (first.itemMeta as Damageable).damage
                    val secondDamage = (second.itemMeta as Damageable).damage

                    min(firstDamage - (max - secondDamage), 0)
                } else {
                    min(
                        0, (first.itemMeta as Damageable).damage - ((second.amount / (materialAmounts[first.type]
                            ?: 1)) * max)
                    )
                }

                val result = ItemBuilder(event.result, false)
                result.setDamage(newDurability)
                event.result = result.asStack()
            }

            // If the Item is being enchanted somehow
            if (first.isRepairableBy(second) || (first.type == Material.ENCHANTED_BOOK && second.type == Material.ENCHANTED_BOOK) || (second.type == Material.ENCHANTED_BOOK && materialAmounts.containsKey(
                    first.type
                ))
            ) {
                if (event.result == null) {
                    event.result = ItemStack(first.type)
                }


                val combinedEnchantments =
                    combineEnchantments(event.result!!, ItemBuilder.allEnchants(first), ItemBuilder.allEnchants(second))

                val result = ItemBuilder(event.result, false)

                combinedEnchantments.forEach { (e, l) ->
                    result.addEnchant(e, l, true)
                }

                event.result = result.asStack()
            }
        }


        if (inventory.renameText!!.isNotEmpty()) {
            if (event.result == null) {
                event.result = first.clone()
            }

            ++repairCost
        }

        // If the item is being renamed
        if (event.inventory.renameText!!.isNotEmpty()) {
            val result = event.result ?: return
            result.itemMeta.displayName(Component.text(event.inventory.renameText!!))

            event.result = result
        }

        Bukkit.getScheduler().runTaskLater(OneblockSkyblock.getInstance(), Runnable {
            inventory.maximumRepairCost = Int.MAX_VALUE
            inventory.result = event.result
            inventory.repairCost = repairCost + cost(event.result ?: return@Runnable)
        }, 2)
    }

    private fun nextEnchantmentLevel(firstEnchantLevel: Int, secondEnchantLevel: Int) =
        if (firstEnchantLevel == secondEnchantLevel)
            firstEnchantLevel + 1 else maxOf(firstEnchantLevel, secondEnchantLevel)

    private fun combineEnchantments(
        item: ItemStack, firstEnchantments: Map<Enchantment, Int>, secondEnchantments: Map<Enchantment, Int>
    ): Map<Enchantment, Int> {

        val enchantments = firstEnchantments.toMutableMap()

        enchantments.replaceAll { k, v -> nextEnchantmentLevel(v, secondEnchantments[k] ?: 0) }
        enchantments += secondEnchantments.filter { (k, _) -> !enchantments.containsKey(k) }
            .filter { (k, _) -> k.canEnchantItem(item) }

        //  enchantments.forEach { println("${it.key} - ${it.value}") }

        return enchantments

    }


    private fun cost(result: ItemStack): Int {
        var cost = 0
        val enchantments = ItemBuilder.allEnchants(result)
        for (enchantment in enchantments.keys) {
            val level = enchantments[enchantment]!!
            cost += (enchantment.rarity.ordinal + 1) * level * if (enchantment.isCursed) -1 else 1
        }

//        if(result.itemMeta is Damageable) {
//            val damage = (result.itemMeta as Damageable).damage
//            cost *= kotlin.math.abs(ceil((damage / result.type.maxDurability.toInt()).toDouble()).toInt())
//        }

        return cost
    }

}
