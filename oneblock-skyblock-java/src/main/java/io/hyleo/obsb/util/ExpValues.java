package io.hyleo.obsb.util;

import lombok.experimental.UtilityClass;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

@UtilityClass
public class ExpValues {

    /**
     * A map of materials to their range of xp orbs they will drop when mined
     * The first integer is the minimum amount of xp orbs that will drop,
     * and the second integer is the maximum amount of xp orbs that will drop (exclusive)
     */
    public final Map<@NotNull Material, Map.@NotNull Entry<@NotNull Integer, @NotNull Integer>> expValues = Map.of(
            Material.IRON_ORE, Map.entry(1, 3),
            Material.DEEPSLATE_IRON_ORE, Map.entry(1, 3),
            Material.GOLD_ORE, Map.entry(1, 4),
            Material.DEEPSLATE_GOLD_ORE, Map.entry(1, 4)
    );

}
