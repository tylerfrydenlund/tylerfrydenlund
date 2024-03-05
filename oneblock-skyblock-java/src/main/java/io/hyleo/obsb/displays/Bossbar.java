package io.hyleo.obsb.displays;

import io.hyleo.obsb.OneblockSkyblock;
import io.hyleo.obsb.api.display.Timings;
import io.hyleo.obsb.display.Displays;
import io.hyleo.obsb.display.text.Palette;
import io.hyleo.obsb.display.text.Pattern;
import io.hyleo.obsb.display.text.TextAnimation;
import io.hyleo.obsb.listeners.ProgressListener;
import lombok.experimental.UtilityClass;
import lombok.val;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Player;

@UtilityClass
public class Bossbar {

    public void assign(Player player) {
        val slot = BossBar.bossBar(Component.empty(), 1f, BossBar.Color.PINK, BossBar.Overlay.PROGRESS);

        color(player, slot);
        overlay(player, slot);
        progress(player, slot);
        text(player, slot);
    }

    void color(Player player, BossBar slot) {
        val empire = OneblockSkyblock.getEmpire(player);
        Displays.BOSSBARCOLOR.display(player, slot, Timings.of().interval(20).build(), () -> empire.currentPhase().color());
    }

    void overlay(Player player, BossBar slot) {
        val empire = OneblockSkyblock.getEmpire(player);
        Displays.BOSSBAROVERLAY.display(player, slot, Timings.fast(),
                () -> {
                    val phase = empire.currentPhase();
                    return phase.blocks() - empire.blocksBroken() <= 0 ? BossBar.Overlay.NOTCHED_10 : BossBar.Overlay.NOTCHED_20;
                });
    }

    void progress(Player player, BossBar slot) {
        val empire = OneblockSkyblock.getEmpire(player);
        Displays.BOSSBARPROGRESS.display(player, slot, Timings.fast(),
                () -> {

                    if (empire.currentPhase().blocks() - empire.blocksBroken() <= 0) {
                        val bosses = ProgressListener.bosses.keySet().stream().filter(b -> ProgressListener.bosses.get(b) == empire).toList();
                        val totalHealth = bosses.stream().mapToDouble(b -> b.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue()).sum();
                        val currentTotalHealth = bosses.stream().mapToDouble(Damageable::getHealth).sum();
                        return (float) (currentTotalHealth / totalHealth);
                    }
                    val phase = empire.currentPhase();
                    return OneblockSkyblock.inInfinitePhase(empire) ? 1 : Math.max(0, empire.blocksBroken()) / (float) phase.blocks();
                });
    }


    void text(Player player, BossBar slot) {

        val empire = OneblockSkyblock.getEmpire(player);

        val palette = Palette.of(1, NamedTextColor.WHITE, NamedTextColor.WHITE);
        val animation = TextAnimation.of(Pattern.SWIPE, palette, () -> (TextComponent) OneblockSkyblock.getPhase(empire).unwrappedDisplayName().decorate(TextDecoration.BOLD),
                () -> " ", () -> {

                    if (empire.currentPhase().blocks() - empire.blocksBroken() <= 0) {
                        val bosses = ProgressListener.bosses.keySet().stream().filter(b -> ProgressListener.bosses.get(b) == empire).count();

                        var bossName = Component.text("- ", NamedTextColor.GRAY);

                        bossName = bossName.append(empire.currentPhase().bossName());
                        bossName = bossName.append(Component.text(" - [x" + bosses + " remaining]", NamedTextColor.GRAY));


                        return  bossName;
                    }

                    return DisplayUtil.blocksStatus(empire);
                });

        val timings = Timings.of().interval(1).build();

        Displays.BOSSBAR.display(player, slot, timings, animation);
    }


}
