package io.hyleo.obsb.display.hyleo.displays;

import io.hyleo.obsb.display.api.AnimationBuffer;
import io.hyleo.obsb.display.hyleo.text.TextAnimation;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.function.BiConsumer;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Actionbar {

    public static final Actionbar BAR = new Actionbar(); // The only slot for the actionbar

    public static final BiConsumer<Player, List<AnimationBuffer<Actionbar, TextAnimation, Component>>> UPDATE = (p, buffers) ->
            buffers.forEach(b -> p.sendActionBar(b.poll()));

    public static final BiConsumer<Player, List<AnimationBuffer<Actionbar, TextAnimation, Component>>> DESTROY = (p, buffers) -> {
        buffers.forEach(b -> p.sendActionBar(Component.text("")));
    };
}
