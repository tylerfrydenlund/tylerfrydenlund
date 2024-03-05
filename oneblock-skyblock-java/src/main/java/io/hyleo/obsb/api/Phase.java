package io.hyleo.obsb.api;

import io.hyleo.obsb.OneblockSkyblock;
import io.hyleo.obsb.util.Util;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import net.kyori.adventure.bossbar.BossBar.Color;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.loot.LootContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Map;
import java.util.Random;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

@Builder
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Accessors(fluent = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NonNull
@Getter
@Setter
public class Phase {

    @NotNull
    String name;

    @NotNull
    String displayName;

    @NotNull
    TextColor textColor;

    @NotNull
    Color color;

    long blocks;

    @Builder.Default
    @NotNull
    Component bossName = Component.text("Unnamed Boss");

    @NonNull
    BiFunction<BlockBreakEvent, Player, Collection<LivingEntity>> boss;

    @Builder.Default
    @NotNull
    BiConsumer<@NotNull EntityDeathEvent, @NotNull Player> bossDeath = (e, p) -> {
    };

    @NotNull
    LootDigest rewards;

    @Builder.Default
    int rewardsLuck = 0;


    public int phase() {
        return OneblockSkyblock.getPhase(this);
    }

    @NotNull
    @Singular(value = "result", ignoreNullCollections = true)
    Map<@NotNull BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @Nullable BlockData>, @NotNull Weight> results;

    @NotNull
    public BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @NotNull BlockData> randomResult() {
        return Util.selectRandom(new Random(), results, blockBreakError());
    }

    @NotNull
    public BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @Nullable BlockData> randomResult(@NonNull LootContext context) {
        val results = Util.adjustWeights(results(), context);

        return Util.selectRandom(new Random(), results, blockBreakError());
    }

    @NotNull
    public static BiFunction<@NotNull BlockBreakEvent, @NotNull LootContext, ? extends @Nullable BlockData> blockBreakError() {
        return (event, context) -> {
            event.getPlayer().sendMessage(Component.text("Failed to replace block randomly, try again", NamedTextColor.RED));
            return Util.defaultData(Material.GRASS_BLOCK).apply(event, context);
        };
    }

    public boolean isInfinite() {
        return OneblockSkyblock.getPhase(this) == OneblockSkyblock.getPhases().size() - 1;
    }

    @NotNull
    public Component unwrappedDisplayName() {
        return Component.text(displayName(), textColor());
    }

    @NotNull
    public Component wrappedDisplayNameLine1() {

        val split = displayName().split(" ");

        var text = new StringBuilder();

        for (String s : split) {
            if (text.length() + s.length() >= 14) {
                break;
            }
            text.append(s).append(" ");
        }

        return Component.text(text.toString(), textColor());
    }

    @NotNull
    public Component wrappedDisplayNameLine2() {

        val split = displayName().split(" ");

        var line1Text = new StringBuilder();

        var text = new StringBuilder();

        for (String s : split) {
            if (line1Text.length() + s.length() < 14) {
                line1Text.append(s).append(" ");
                continue;
            }
            text.append(s).append(" ");
        }

        return Component.text(text.toString(), textColor());
    }

}
