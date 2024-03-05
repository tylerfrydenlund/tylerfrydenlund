package io.hyleo.obsb.display.displays;

import io.hyleo.obsb.api.display.AnimationBuffer;
import io.hyleo.obsb.display.Displays;
import io.hyleo.obsb.display.text.TextAnimation;
import lombok.experimental.UtilityClass;
import lombok.val;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.stream.IntStream;

@UtilityClass
public class Tablist {

    public final BiConsumer<Player, List<AnimationBuffer<Integer, TextAnimation, Component>>> UPDATE = (p, buffers) -> {
        buffers.sort(Comparator.comparingInt(AnimationBuffer::slot));

        Collections.reverse(buffers);

        val header = Component.text();

        val footer = Component.text();

        var lastHead = buffers.get(0).slot(); // Get the greatest line
        var lastFoot = -1; // Max Footer Line

        for (AnimationBuffer<Integer, TextAnimation, Component> AnimationBufferOld : buffers) {
            val line = AnimationBufferOld.slot();
            val isHead = line > -1;

            val builder = isHead ? header : footer;

            val distance = (isHead ? lastHead : lastFoot) - line;

            IntStream.range(0, distance).forEach(i -> builder.append(Component.newline()));

            builder.append(AnimationBufferOld.poll());

            if (isHead) {
                lastHead = Math.min(lastHead, line);
            } else {
                lastFoot = Math.min(lastFoot, line);
            }

        }

        p.sendPlayerListHeaderAndFooter(header.build(), footer.build());

    };


    public final BiConsumer<Player, List<AnimationBuffer<Integer, TextAnimation, Component>>> DESTROY = (p, buffers) -> {

        val schedule = new ArrayList<>(Displays.TABLIST.schedules().get(p).values());

        if (schedule.isEmpty()) {
            return; // No schedule to destroy, prevents Tablist from being cleared multiple times
        }

        // Check if we have any assigned AnimationBuffers that are not being destroyed or that
        // are completed, helps detect if we need to clear the tablist
        schedule.removeIf(AnimationBuffer::destroy);

        if (schedule.isEmpty()) { // If nothing will remain after if this is true, clear tablist
            p.sendPlayerListHeaderAndFooter(Component.empty(), Component.empty());
        }

    };

}
