package io.hyleo.obsb.api

import io.hyleo.obsb.OneblockSkyblock
import io.papermc.paper.enchantments.EnchantmentRarity
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.NamespacedKey
import org.bukkit.enchantments.Enchantment
import org.bukkit.enchantments.EnchantmentTarget
import org.bukkit.entity.EntityCategory
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.Plugin
import java.util.*

class PhaseEnchantment(private val phase: Phase) : Enchantment() {

    override fun getName() = "${phase.name()} Targeting"

    override fun getMaxLevel() = Int.MAX_VALUE

    override fun getStartLevel() = 1

    override fun getItemTarget() = EnchantmentTarget.TOOL

    override fun isTreasure() = false

    override fun isCursed() = false

    override fun conflictsWith(other: Enchantment) = false

    override fun canEnchantItem(item: ItemStack) = true

    override fun displayName(level: Int) =
        Component.text("${name.replace("_", " ")} ${integerToRoman(level)}", NamedTextColor.GRAY)

    override fun isTradeable() = true

    override fun isDiscoverable() = true
    override fun getMinModifiedCost(level: Int): Int = 0
    override fun getMaxModifiedCost(level: Int): Int = 0

    override fun getRarity() = EnchantmentRarity.VERY_RARE

    override fun getDamageIncrease(level: Int, entityCategory: EntityCategory) = 0f

    override fun getActiveSlots() = setOf(EquipmentSlot.HAND, EquipmentSlot.OFF_HAND)
    override fun getKey(): NamespacedKey =
        NamespacedKey.fromString(phase.name().lowercase(Locale.getDefault()), OneblockSkyblock.getInstance() as Plugin)
            ?: throw Error("Failed to create phase enchantment")

    override fun translationKey() = getByKey(key)?.translationKey() ?: "enchantment.${key.namespace}.${key.key}"
    private fun integerToRoman(num: Int): String? {
        var num = num
        val values = intArrayOf(1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1)
        val romanLiterals = arrayOf("M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I")
        val roman = StringBuilder()
        for (i in values.indices) {
            while (num >= values[i]) {
                num -= values[i]
                roman.append(romanLiterals[i])
            }
        }



        return roman.toString()
    }


}
