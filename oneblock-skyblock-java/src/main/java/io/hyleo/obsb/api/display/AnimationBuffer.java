package io.hyleo.obsb.api.display;

import com.google.common.collect.Range;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.bukkit.entity.Player;

import java.util.function.Predicate;

/**
 * Some of the magic beans behind Display API. This class is responsible for buffering animations and handling the timing of the animations.
 * It is the mathematical brain behind the animations.
 *
 * @param <Slot>      the slot type (ex: BossBar)
 * @param <Animation> the animation type (ex: TextAnimation)
 * @param <Frame>     the frame type (ex: Component)
 */
@Getter
@Accessors(fluent = true)
@NonNull
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public final class AnimationBuffer<Slot, Animation, Frame> {

    /**
     * The player to display the animation to
     */
    Player player;

    /**
     * Whether the animation supports intervals
     */
    boolean intervalSupport;

    /**
     * The slot to display the animation in
     */
    Slot slot;

    /**
     * The timings of the animation, holds important information about the animation
     */
    Timings timings;

    /**
     * The animator to animate the animation
     */
    Animator<Animation, Frame> animator;

    /**
     * The animation to display
     */
    Animation animation;

    /**
     * The checked condition to display the animation, if the condition is false, the animation will be destroyed
     */
    Predicate<Player> condition;

    /**
     * How many times the animation has been repeated
     */
    @NonFinal
    protected int repeated = -1,

    /**
     * The current number of ticks in the cycle
     */
    ticks = -1;

    /**
     * The number of frames in the animation
     */
    @NonFinal
    Integer frames;

    /**
     * A flag to destroy the animation
     */
    @NonFinal
    @Setter
    boolean destroy;

    public AnimationBuffer(Player player, boolean intervalSupport, Slot slot, Timings timings, Animator<Animation, Frame> animator, Animation animation, Predicate<Player> condition) {
        this.player = player;
        this.intervalSupport = intervalSupport;
        this.slot = slot;
        this.timings = timings;
        this.animator = animator;
        this.animation = animation;
        this.condition = condition;
        frames = animator.frames(animation);
    }

    /**
     * Gets the next frame in the animation, without
     * incrementing the number of ticks
     *
     * @return the next frame
     */
    public Frame peek() {
        return buffer(false);
    }

    /**
     * Gets the next frame in the animation, by
     * incrementing the number of ticks
     *
     * @return the next frame
     */
    public Frame poll() {
        return buffer(true);
    }

    /**
     * Buffers the next frame in the animation
     *
     * @param poll whether to increment the number of ticks
     * @return the next frame
     */
    Frame buffer(boolean poll) {

        if (destroy()) {
            System.out.println("destroy");
            return null;
        }
        val create = create();
        var cycleLength = cycleLength();

        if (create || 0 == cycleLength - ticks) {
            // System.out.println("new cycle");
            frames = animator.frames(animation);
            ticks = 0;
            ++repeated;
            return buffer(poll);
        }

        if (delay()) {
            //  System.out.println("delay");
            ticks += poll ? 1 : 0;
            return intervalSupport ? null : animator.animate(animation, timings.reversed() ? frames - 1 : 0);
        }

        if (finalDelay(cycleLength)) {
            // System.out.println("final delay");
            ticks += poll ? 1 : 0;
            return animator.animate(animation, timings.reversed() ? 0 : frames - 1);
        }

        if (repeatDelay(cycleLength)) {
            //  System.out.println("repeat delay");
            ticks += poll ? 1 : 0;
            return animator.animate(animation, timings.reversed() ? 0 : frames - 1);
        }


        // Normal Frame
        try {
            //  System.out.println("normal frame");
            return animator.animate(animation, ticks / timings.interval());
        } catch (Exception e) {
            System.out.println(ticks);
            System.out.println(cycleLength);
            System.out.println(timings);
            return animator.animate(animation, ticks / timings.interval());
        } finally {
            ticks += poll ? 1 : 0;
        }
    }

    /**
     * Whether the animation should be created
     *
     * @return whether the animation should be created
     */
    public boolean create() {
        return repeated == -1;
    }

    /**
     * Whether the animation should be destroyed
     *
     * @return whether the animation should be destroyed
     */
    public boolean destroy() {

        if (condition.negate().test(player)) {
            return true;
        }

        if (destroy) {
            return true;
        }

        if (!timings.isFinite()) {
            return false;
        }

        if (timings.hasTimeout() && ticks >= timings.timeout()) {
            return true;
        }

        return repeated == timings.repeats() - 1 && ticks >= cycleLength();
    }

    /**
     * The length of the cycle in ticks
     *
     * @return the length of the cycle ticks
     */
    int cycleLength() {

        var offset = 0;

        if (repeated == 0) {
            offset += timings.actualDelay();
        }

        if (timings.isFinite() && repeated == timings.repeats() - 1) {
            offset = timings.actualFinalDelay();
        } else {
            offset += timings.repeatDelay();
        }


        return frames * timings.interval() + offset;
    }

    /**
     * Whether the animation is currently on a delay
     * @return whether the animation is currently on a delay
     */
    boolean delay() {
        return repeated == 0 && 0 > ticks - timings.actualDelay();
    }

    /**
     * Whether the animation is currently on the final delay
     * @param cycleLength the length of the cycle
     * @return whether the animation is currently on the final delay
     */
    boolean finalDelay(int cycleLength) {

        if (!timings.isFinite()) {
            return false;
        }

        return repeated == timings.repeats() - 1 && Range.closedOpen(cycleLength - timings.actualFinalDelay(), cycleLength).contains(ticks);
    }

    /**
     * Whether the animation is currently on the repeat delay
     *
     * @param cycleLength the length of the cycle
     * @return whether the animation is currently on the repeat delay
     */
    boolean repeatDelay(int cycleLength) {
        return Range.closedOpen(cycleLength - timings.repeatDelay(), cycleLength).contains(ticks);
    }
}
