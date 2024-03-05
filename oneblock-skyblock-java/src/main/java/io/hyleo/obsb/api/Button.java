package io.hyleo.obsb.api;

import io.papermc.paper.enchantments.EnchantmentRarity;
import lombok.*;
import lombok.experimental.FieldDefaults;
import net.kyori.adventure.text.Component;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.EntityCategory;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Set;
import java.util.function.BiFunction;

@Builder
@Data
@FieldDefaults(makeFinal = true, level =  AccessLevel.PRIVATE)
public class Button extends Enchantment {

    String name;

    InventoryAction action;

    public Button(@NotNull String name, @NotNull InventoryAction action) {
        super();
        this.name = name;
        this.action = action;
    }

    @Override
    public @NotNull String getName() {
        return name;
    }

    @Override
    public int getMaxLevel() {
        return 0;
    }

    @Override
    public int getStartLevel() {
        return 0;
    }

    @Override
    public @NotNull EnchantmentTarget getItemTarget() {
        return EnchantmentTarget.ALL;
    }

    @Override
    public boolean isTreasure() {
        return false;
    }

    @Override
    public boolean isCursed() {
        return false;
    }

    @Override
    public boolean conflictsWith(@NotNull Enchantment other) {
        return false;
    }

    @Override
    public boolean canEnchantItem(@NonNull ItemStack item) {
        return true;
    }

    @Override
    public @NotNull Component displayName(int level) {
        return Component.empty();
    }

    @Override
    public boolean isTradeable() {
        return false;
    }

    @Override
    public boolean isDiscoverable() {
        return false;
    }

    @Override
    public int getMinModifiedCost(int level) {
        return 0;
    }

    @Override
    public int getMaxModifiedCost(int level) {
        return 0;
    }

    @Override
    public @NotNull EnchantmentRarity getRarity() {
        return EnchantmentRarity.COMMON;
    }

    @Override
    public float getDamageIncrease(int level, @NotNull EntityCategory entityCategory) {
        return 0;
    }

    @Override
    public @NotNull Set<EquipmentSlot> getActiveSlots() {
        return Set.of(EquipmentSlot.values());
    }


    @Override
    public @NotNull String translationKey() {
        return Objects.requireNonNull(Enchantment.getByKey(getKey())).translationKey();
    }

    @Override
    public @NotNull NamespacedKey getKey() {
        return NamespacedKey.minecraft(name);
    }
}
