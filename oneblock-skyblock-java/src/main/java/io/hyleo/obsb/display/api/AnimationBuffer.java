package io.hyleo.obsb.display.api;

import com.google.common.collect.Range;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.bukkit.entity.Player;

import java.util.function.Predicate;

@Getter
@Accessors(fluent = true)
@NonNull
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public final class AnimationBuffer<Slot, Animation, Frame> {

    Player player;
    boolean intervalSupport;

    Slot slot;
    Timings timings;

    Animator<Animation, Frame> animator;

    Animation animation;

    Predicate<Player> condition;

    @NonFinal
    protected int repeated = -1, ticks = -1;

    @NonFinal
    Integer frames;

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


    public Frame peek() {
        return buffer(false);
    }

    public Frame poll() {
        return buffer(true);
    }

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


    public boolean create() {
        return repeated == -1;
    }

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

    boolean delay() {
        return repeated == 0 && 0 > ticks - timings.actualDelay();
    }

    boolean finalDelay(int cycleLength) {

        if (!timings.isFinite()) {
            return false;
        }

        return repeated == timings.repeats() - 1 && Range.closedOpen(cycleLength - timings.actualFinalDelay(), cycleLength).contains(ticks);
    }

    boolean repeatDelay(int cycleLength) {
        return Range.closedOpen(cycleLength - timings.repeatDelay(), cycleLength).contains(ticks);
    }
}
