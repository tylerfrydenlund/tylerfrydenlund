package io.hyleo.obsb.api.display;

import com.google.common.collect.ImmutableMap;
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
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.*;

/**
 * A display is a collection of slots and timings, that are responsible for displaying an animation to a player
 * <p></p>
 * Displays will automatically update the frames for each slot and dispatch the new frames to the player
 * by using the animator to generate the frames for each slot, the timings to determine when to update the frames
 * and the animation to determine what to display
 * <p></p>
 * Displays are a powerful way to display animations to a player, and can be used to display scoreboards, bossbars, titles, actionbars, and more
 * They are also capable of displaying multiple animations at once, and can be used to display complex animations to a player
 * <p></p>
 * Displays system has been years in the making and only gets better. A lot of thought and effort has been put into making the system.
 * <p></p>
 * In the future, a reactive Display system would be ideal to further increase computational efficiency
 * <p></p>
 * @param <Slot>     the type of the slot to use (ex: Bossbar, Title, ActionBar, etc)
 * @param <Animation> the type of the animation to use (ex: BossbarColorAnimation, TextAnimation, etc)
 * @param <Frame>   the type of the frame to use (ex: BossbarColor, Text, etc)
 */
@Builder
@Accessors(fluent = true)
@Data
@NotNull
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public final class Display<Slot, Animation, Frame> {

    /**
     * Create a new DisplayBuilder with the required parameters
     *
     * @param plugin      the plugin scheduling the display
     * @param animator    the animator to use to generate frames
     * @param <Slot>      the type of the slot to use (ex: Bossbar, Title, ActionBar, etc)
     * @param <Animation> the type of the animation to use (ex: BossbarColorAnimation, TextAnimation, etc)
     * @param <Frame>     the type of the frame to use (ex: BossbarColor, Text, etc)
     * @return a new DisplayBuilder
     */
    public static <Slot, Animation, Frame> DisplayBuilder<@NotNull Slot, @NotNull Animation, @Nullable Frame> builder(@NonNull Supplier<@NotNull Plugin> plugin, @NonNull Animator<@NotNull Animation, @Nullable Frame> animator) {
        return new DisplayBuilder<Slot, Animation, Frame>().plugin(plugin).animator(animator);
    }

    /**
     * The schedules for each player
     * <p>
     * A schedule is a map of slots to animation buffers that are responsible for generating the frames for each slot
     */
    @NotNull
    Map<@NotNull Player, @NotNull Map<@NotNull Slot, @NotNull AnimationBuffer<@NotNull Slot, @NotNull Animation, @Nullable Frame>>> schedules = new HashMap<>();

    /**
     * The players that have had their display set up
     */
    @NotNull
    List<@NotNull Player> wasSetup = new ArrayList<>();

    /**
     * The plugin scheduling the display
     */
    @NotNull
    Supplier<@NotNull Plugin> plugin;

    /**
     * The animator to use to generate frames from an animation
     */
    @NotNull
    Animator<@NotNull Animation, @NotNull Frame> animator;

    /**
     * Should the frame be updated every tick or only when the animation changes.
     * Certain displays will bug out if you sent them an update every tick
     */
    @Default
    boolean intervalSupport = false;

    /**
     * The function to run when the display is set up for a player
     */
    @Default
    @NotNull
    Consumer<@NotNull Player> setup = (p) -> {
    }, cleanup = (p) -> {
    };

    /**
     * Checks if the display should be updated for a player
     */
    @Default
    @NotNull
    Function<@NotNull Player, @NotNull Boolean> condition = (p) -> true;

    /**
     * A function to be ran when a slot is scheduled to the display
     * first created for the player (is only ran once per slot unless it is destroyed)
     */
    @Default
    @NotNull
    BiConsumer<@NotNull Player, @NotNull List<@NotNull AnimationBuffer<@NotNull Slot, @NotNull Animation, @Nullable Frame>>> create = (p, bs) -> bs
            .forEach(AnimationBuffer::poll);

    /**
     * A function responsible for dispatching the new frames to the player.
     * This can be in the form of a bossbar update, title update, or scoreboard update etc
     */
    @Default
    @NotNull
    BiConsumer<@NotNull Player, @NotNull List<@NotNull AnimationBuffer<@NotNull Slot, @NotNull Animation, @Nullable Frame>>> update = (p, bs) -> bs
            .forEach(AnimationBuffer::poll);

    /**
     * A function responsible for destroying the slot for the player. This is ran when the slot is removed from the display
     * or when the player is no longer online, or when the condition returns false, or when the display is destroyed, or
     * when the animation for a slot is finished
     */
    @Default
    @NotNull
    BiConsumer<@NotNull Player, @NotNull List<AnimationBuffer<@NotNull Slot, @NotNull Animation, @Nullable Frame>>> destroy = (p, bs) -> bs
            .forEach(AnimationBuffer::poll);

    @NonFinal
    BukkitTask task;

    /**
     * Should only be called once per player
     *
     * @param player the player to setup the display for
     */
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

    /**
     * Should only be called once per player, is called when the player is no longer online
     *
     * @param player the player to cleanup the display for
     */
    void cleanup(@NonNull Player player) {

        schedules.remove(player);

        if (!wasSetup.contains(player)) {
            return;
        }

        wasSetup.remove(player);
        cleanup.accept(player);
    }

    /**
     * Gets a copy of the schedules for each player
     *
     * @return a copy of the schedules for each player
     */
    @NotNull
    public Map<@NotNull Player, @NotNull Map<@NotNull Slot, @NotNull AnimationBuffer<@NotNull Slot, @NotNull Animation, @NotNull Frame>>> schedules() {
        return ImmutableMap.copyOf(schedules);
    }

    /**
     * Displays an animation to a player
     *
     * @param player    the player to display the animation to
     * @param slot      the slot to display the animation in
     * @param timings   the timings for the animation
     * @param animation the animation to display
     */
    public void display(@NonNull Player player, @NonNull Slot slot, @NonNull Timings timings, @NonNull Animation animation) {
        display(player, slot, timings, animation, (p) -> true);
    }

    /**
     * Displays an animation to a player that is conditional
     *
     * @param player    the player to display the animation to
     * @param slot      the slot to display the animation in
     * @param timings   the timings for the animation
     * @param animation the animation to display
     * @param condition the checked condition for the animation to be displayed
     */
    public void display(@NonNull Player player, @NonNull Slot slot, @NonNull Timings timings, @NonNull Animation animation, @NonNull Predicate<@NotNull Player> condition) {
        setup(player);
        val schedule = schedules.get(player);

        val buffer = new AnimationBuffer<>(player, intervalSupport(), slot, timings, animator, animation, condition);
        schedule.put(slot, buffer);
    }

    /**
     * Hides a slot(s) from a player
     *
     * @param player the player to hide the slot(s) from
     * @param slots  the slot(s) to hide
     */
    @SafeVarargs
    public final void hide(@NonNull Player player, @NonNull Slot... slots) {
        hide(player, List.of(slots));
    }

    /**
     * Hides a slot(s) from a player
     *
     * @param player the player to hide the slot(s) from
     * @param slots  the slot(s) to hide
     */
    public void hide(@NonNull Player player, @NonNull Collection<@NotNull Slot> slots) {

        val schedule = schedules.get(player);

        val toDestroy = new ArrayList<AnimationBuffer<Slot, Animation, Frame>>();

        slots.forEach(s -> toDestroy.add(schedule.remove(s)));

        toDestroy.removeIf(Objects::isNull);

        toDestroy.forEach(b -> b.destroy(true));
    }

    /**
     * Hides all slots from a player
     *
     * @param player the player to hide all slots from
     */
    public void hideAll(@NonNull Player player) {
        val schedule = schedules.get(player);

        if (player.isOnline()) {
            hide(player, new ArrayList<>(schedule.keySet()));
            return;
        }

        schedules.remove(player);

    }

    /**
     * The core display function that is ran every tick
     * This function is responsible for updating the display for each player
     * and dispatching the new frames to the player
     */
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

    /**
     * Dispatches the new frames to the player
     *
     * @param player           the player to dispatch the new frames to
     * @param animationBuffers the animation buffers to dispatch
     * @param dispatchFunction the function to dispatch the new frames
     * @param <B>              the type of the animation buffer
     */
    <B> void dispatchIfNotEmpty(@NonNull Player player, @NonNull List<@NotNull B> animationBuffers, @NonNull BiConsumer<@NotNull Player, @NotNull List<@NotNull B>> dispatchFunction) {
        if (animationBuffers.isEmpty()) {
            return;
        }
        //DisplayAPI.logger().info(function + " for " + AnimationBuffers.size() + " AnimationBuffers for " + player.getName());
        // New Array list prevents AnimationBuffer list from tampering
        dispatchFunction.accept(player, new ArrayList<>(animationBuffers));
    }

    /**
     * Creates a new list of animation buffers from the given collection and filters them
     *
     * @param AnimationBuffers the animation buffers to copy and filter
     * @param filter           the filter to apply to the new list of animation buffers
     * @param <B>              the type of the animation buffer
     * @return a new list of animation buffers from the given collection and filters them
     */
    <B> List<@NotNull B> copyThenFilter(@NonNull Collection<@NotNull B> AnimationBuffers, @NonNull Predicate<@NotNull B> filter) {

        val bs = new ArrayList<>(AnimationBuffers);

        bs.removeIf(Predicate.not(filter));

        return bs;
    }

    /**
     * A function to check if the animation buffer should be destroyed by incrementing it ahead by 1 tick
     * then checking if it should be destroyed
     * then decrementing it back to the original tick count
     */
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
