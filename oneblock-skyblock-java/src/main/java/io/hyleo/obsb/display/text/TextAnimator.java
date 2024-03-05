package io.hyleo.obsb.display.text;

import io.hyleo.obsb.api.display.Animator;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.val;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextDecoration.State;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TextAnimator implements Animator<TextAnimation, Component> {

    Map<TextAnimation, AnimationState> states = new HashMap<>();

    /**
     * Determined by the animations pattern
     */
    @Override
    public int frames(@NotNull TextAnimation animation) {
        val text = animation.text().get();

        val colors = animation.colors();

        val frames = text.isEmpty() ? 1 : animation.pattern()
                .frames()
                .apply(colors, text.length());

        val state = new AnimationState(frames, text, colors, 0);

        states.put(animation, state);

        return frames;
    }

    /**
     * Animated by the pattern, but decorations are appended through here.
     */
    @Override
    public Component animate(@NotNull TextAnimation animation, int frame) {

        val state = state(animation);

        if (state.textLength() == 0) {
            return Component.textOfChildren(animation.preText.get(), animation.subText.get());
        }

        state.frame(frame);
        var text = animation.pattern()
                .text()
                .apply(animation, state);

        text = (TextComponent) text
                .decorations(animation.decorations()
                        .stream()
                        .collect(
                                Collectors.toMap((d) -> d, (s) -> State.TRUE)));

        return Component.textOfChildren(animation.preText.get(), text, animation.subText.get());
    }

    /**
     * Gets the current state of the animation
     *
     * @param animation to check
     * @return the state of the animation
     */
    public AnimationState state(TextAnimation animation) {
        return states.get(animation);
    }

}
