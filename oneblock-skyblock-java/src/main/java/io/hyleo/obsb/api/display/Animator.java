package io.hyleo.obsb.api.display;

import org.jetbrains.annotations.NotNull;

/**
 * @param <Animation> The type of animation to be played.
 * @param <Frame>     The type of frame to be played.
 */
public interface Animator<Animation, Frame> {


    /**
     * This method is in place to support functions which may change the cycle
     * length. In an instance like text animations, the number frames to complete 1
     * cycle is dependent on the length of the string for the text. If this text
     * changes, so would the cycle length.
     * <p>
     * Functions for options, are applied at the end of each cycle. The next cycle
     * length may change in duration depending on the new options. Options are
     * updated at the end of each cycle to ensure smooth generation of frames.
     *
     * @param animation used to calculate the current cycle length
     * @return the current cycle length in ticks
     * @since 1.0.0
     */
    int frames(@NotNull Animation animation);

    /**
     * A mathematical representation of what the synthesizer will do. This method
     * can be accessed randomly using the number of ticks between 0-(frames - 1).
     * This is a non-linear method. At any point it should be able to provide an
     * output from the given options and ticks.
     *
     * @param animation used to calculate the current frame
     * @param frame     into the cycle
     * @return The frame at the number of ticks in time
     * @since 1.0.0
     */
    Frame animate(@NotNull Animation animation, int frame);

}
