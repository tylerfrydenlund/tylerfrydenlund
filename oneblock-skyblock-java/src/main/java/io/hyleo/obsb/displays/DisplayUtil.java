package io.hyleo.obsb.displays;

import io.hyleo.obsb.api.Empire;
import io.hyleo.obsb.phases.Infinite;
import lombok.experimental.UtilityClass;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;

@UtilityClass
public class DisplayUtil {

  public static  TextColor mclaren = TextColor.fromHexString("#E0610E");
  public static TextColor eggshell = TextColor.fromHexString("#F0EAD6");

  public static TextColor lime = TextColor.fromHexString("#32CD32");

    public static String percent(long value, long max) {
        return String.format("%.1f%%", (double) value / max * 100);
    }

    public TextComponent blocksStatus(Empire empire) {

        if (empire.currentPhase() == Infinite.PHASE) {
            return Component.textOfChildren(Component.text("- ", NamedTextColor.GRAY), Component.text("" + String.format("%,d", empire.blocksBroken()), lime));
        }

        return Component.textOfChildren(Component.text(" - ", NamedTextColor.GRAY), Component.text(percent(blocksBroken(empire), totalBlocks(empire)), lime));
    }

    long blocksBroken(Empire empire) {
        return Math.min(Math.max(0, empire.blocksBroken()), empire.currentPhase().blocks());
    }

    long totalBlocks(Empire empire) {
        return empire.currentPhase().blocks();
    }


    public static String phaseNumber(Empire empire) {
        return empire.currentPhase() == Infinite.PHASE ? "\u221E" : (empire.phase() + 1) + "";
    }

}
