package io.hyleo.obsb.display.displays;

import io.hyleo.obsb.api.display.AnimationBuffer;
import io.hyleo.obsb.display.text.TextAnimation;
import lombok.experimental.UtilityClass;
import lombok.val;
import net.kyori.adventure.audience.MessageType;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.function.BiConsumer;

@UtilityClass
public class Chat {

    public final BiConsumer<Player, List<AnimationBuffer<MessageType, TextAnimation, Component>>> UPDATE = (p, buffers) ->
            buffers.forEach(b -> {
                val type = b.slot();
                val text = b.poll();

                p.sendMessage(text, type);
            });


}
