package io.hyleo.obsb.display.displays;


import io.hyleo.obsb.api.display.AnimationBuffer;
import io.hyleo.obsb.display.text.TextAnimation;
import lombok.experimental.UtilityClass;
import lombok.val;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.title.Title.Times;
import org.bukkit.entity.Player;

import java.time.Duration;
import java.util.List;
import java.util.function.BiConsumer;

@UtilityClass
public class Title {
    public final BiConsumer<Player, List<AnimationBuffer<Boolean, TextAnimation, Component>>> UPDATE = (p, buffers) -> {

        var title = Component.empty();
        var subtitle = Component.empty();

        for (val buffer : buffers) {
            if (buffer.slot()) {
                title = (TextComponent) buffer.poll();
            } else {
                subtitle = (TextComponent) buffer.poll();
            }
        }

        p.showTitle(net.kyori.adventure.title.Title.title(title, subtitle, Times.times(Duration.ZERO, Duration.ofMillis(100), Duration.ZERO)));

    };

}
