package io.hyleo.obsb.display.api;

import lombok.*;
import lombok.Builder.Default;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.*;

@Builder
@Accessors(fluent = true)
@Data
@NotNull
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public final class Display<Slot, Animation, Frame> {

    public static <Slot, Animation, Frame> DisplayBuilder<Slot, Animation, Frame> builder(Supplier<Plugin> plugin, Animator<Animation, Frame> animator) {
        return new DisplayBuilder<Slot, Animation, Frame>().plugin(plugin).animator(animator);
    }

    Map<Player, Map<Slot, AnimationBuffer<Slot, Animation, Frame>>> schedules = new HashMap<>();
    List<Player> wasSetup = new ArrayList<>();
    @NotNull
    Supplier<Plugin> plugin;
    @NotNull
    Animator<Animation, Frame> animator;

    @Default
    boolean intervalSupport = false;

    @Default
    @NotNull
    Consumer<Player> setup = (p) -> {
    }, cleanup = (p) -> {
    };

    @Default
    @NotNull
    Function<Player, Boolean> condition = (p) -> true;

    @Default
    @NotNull
    BiConsumer<Player, List<AnimationBuffer<Slot, Animation, Frame>>> create = (p, bs) -> bs
            .forEach(AnimationBuffer::poll);
    @Default
    @NotNull
    BiConsumer<Player, List<AnimationBuffer<Slot, Animation, Frame>>> update = (p, bs) -> bs
            .forEach(AnimationBuffer::poll);
    @Default
    @NotNull
    BiConsumer<Player, List<AnimationBuffer<Slot, Animation, Frame>>> destroy = (p, bs) -> bs
            .forEach(AnimationBuffer::poll);

    @NonFinal
    BukkitTask task;

    void setup(@NonNull Player player) {

        if (task == null) {
            task = Bukkit.getScheduler().runTaskTimer(plugin.get(), () -> {
                try {
                    display();
                } catch (Exception e) {
                    task.cancel();
                    e.printStackTrace();
                }
            }, 1, 1);
        }

        if (wasSetup.contains(player)) {
            return;
        }

        schedules.put(player, new HashMap<>());
        wasSetup.add(player);
        setup.accept(player);
    }

    void cleanup(@NonNull Player player) {

        schedules.remove(player);

        if (!wasSetup.contains(player)) {
            return;
        }

        wasSetup.remove(player);
        cleanup.accept(player);
    }

    public Map<Player, Map<Slot, AnimationBuffer<Slot, Animation, Frame>>> schedules() {
        return new HashMap<>(schedules);
    }

    public void display(@NonNull Player player, @NonNull Slot slot, @NonNull Timings timings, @NonNull Animation animation) {
        display(player, slot, timings, animation, (p) -> true);
    }

    public void display(@NonNull Player player, @NonNull Slot slot, @NonNull Timings timings, @NonNull Animation animation, @NonNull Predicate<@NotNull Player> condition) {
        setup(player);
        val schedule = schedules.get(player);

        val buffer = new AnimationBuffer<>(player, intervalSupport(), slot, timings, animator, animation, condition);
        schedule.put(slot, buffer);
    }

    @SafeVarargs
    public final void hide(@NonNull Player player, @NonNull Slot... slots) {
        hide(player, List.of(slots));
    }

    public void hide(@NonNull Player player, @NonNull Collection<@NotNull Slot> slots) {

        val schedule = schedules.get(player);

        val toDestroy = new ArrayList<AnimationBuffer<Slot, Animation, Frame>>();

        slots.forEach(s -> toDestroy.add(schedule.remove(s)));

        toDestroy.removeIf(Objects::isNull);

        toDestroy.forEach(b -> b.destroy(true));
    }

    public void hideAll(@NonNull Player player) {
        val schedule = schedules.get(player);

        if (player.isOnline()) {
            hide(player, new ArrayList<>(schedule.keySet()));
            return;
        }

        schedules.remove(player);

    }

    void display() {

        for (val entry : new ArrayList<>(schedules.entrySet())) {

            val player = entry.getKey();

            if (!condition().apply(player)) {
                continue; // Skip current player
            }

            if (!player.isOnline()) {
                cleanup(player);
                continue; // Skip current player
            }

            val schedule = entry.getValue().values();
            val destroy = copyThenFilter(schedule, shouldDestroy);
            val create = copyThenFilter(schedule, AnimationBuffer::create);


            dispatchIfNotEmpty(player, destroy, destroy());
            dispatchIfNotEmpty(player, create, create());

            val update = copyThenFilter(schedule, Predicate.not(shouldDestroy));

            val onInterval = update.stream().filter(b -> Objects.isNull(b.peek())).toList();

            update.removeAll(onInterval); // Skip this update Cycle
            onInterval.forEach(AnimationBuffer::poll); // Increment the frame

            dispatchIfNotEmpty(player, update, update());

            schedule.removeIf(AnimationBuffer::destroy);
        }

    }

    <B> void dispatchIfNotEmpty(@NonNull Player player, @NonNull List<@NotNull B> animationBuffers, @NonNull BiConsumer<@NotNull Player, @NotNull List<@NotNull B>> dispatchFunction) {
        if (animationBuffers.isEmpty()) {
            return;
        }
        //DisplayAPI.logger().info(function + " for " + AnimationBuffers.size() + " AnimationBuffers for " + player.getName());
        // New Array list prevents AnimationBuffer list from tampering
        dispatchFunction.accept(player, new ArrayList<>(animationBuffers));
    }

    <B> List<@NotNull B> copyThenFilter(@NonNull Collection<@NotNull B> AnimationBuffers, @NonNull Predicate<@NotNull B> filter) {

        val bs = new ArrayList<>(AnimationBuffers);

        bs.removeIf(Predicate.not(filter));

        return bs;
    }

    @NotNull
    final Predicate<@NotNull AnimationBuffer<@NotNull Slot, @NotNull Animation, @NotNull Frame>> shouldDestroy = b -> {
        ++b.ticks; // Check ahead by 1 tick (this is hacky and kind of no no)
        try {
            return b.destroy();
        } finally {
            --b.ticks; // Make sure we dont mess up the actual tick count
        }
    };

}
