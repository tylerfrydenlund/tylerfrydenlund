package io.hyleo.obsb.api

import io.papermc.paper.enchantments.EnchantmentRarity
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.NamespacedKey
import org.bukkit.enchantments.Enchantment
import org.bukkit.enchantments.EnchantmentTarget
import org.bukkit.entity.EntityCategory
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.inventory.ItemStack

class ButtonClick(
    private val description: String,
    val clickType: ClickType,
    val action: (InventoryClickEvent) -> ItemStack
) : Enchantment() {

    init {
        //     registerEnchantment(this)
    }

    override fun getName() = description

    override fun getMaxLevel() = 1

    override fun getStartLevel() = 1
    override fun getItemTarget() = EnchantmentTarget.ALL

    override fun isTreasure() = false

    override fun isCursed() = false

    override fun conflictsWith(other: Enchantment) = false

    override fun canEnchantItem(item: ItemStack) = true

    override fun displayName(level: Int) = Component.textOfChildren(
        Component.text(clickType.toString(), NamedTextColor.WHITE),
        Component.text(""),
        Component.text(description, NamedTextColor.GREEN)
    )

    override fun isTradeable() = false

    override fun isDiscoverable() = false
    override fun getMinModifiedCost(level: Int): Int = 0

    override fun getMaxModifiedCost(level: Int): Int = 0

    override fun getRarity() = EnchantmentRarity.COMMON

    override fun getDamageIncrease(level: Int, entityCategory: EntityCategory) = 0f

    override fun getActiveSlots(): Set<EquipmentSlot> = setOf(*EquipmentSlot.values())
    override fun getKey(): NamespacedKey = NamespacedKey.minecraft(
        description.replace(" ", "_").lowercase()
    )

    override fun translationKey() = getByKey(key)?.translationKey() ?: throw Exception("Translation Key was null")
}
