package io.hyleo.obsb.display.hyleo.displays;


import io.hyleo.obsb.display.api.AnimationBuffer;
import io.hyleo.obsb.display.hyleo.Destination;
import io.hyleo.obsb.display.hyleo.ScoreboardUtil;
import io.hyleo.obsb.display.hyleo.text.TextAnimation;
import lombok.experimental.UtilityClass;
import lombok.val;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

@UtilityClass
public class Scoreboard {

    public final Consumer<Player> SETUP = ScoreboardUtil::showScoreboard;


    public final Consumer<Player> CLEANUP = ScoreboardUtil::scoreboard;


    public final Function<Player, Boolean> VIEW_CONDITION = ScoreboardUtil::viewingScoreboard;


    public final BiConsumer<Player, List<AnimationBuffer<Destination, TextAnimation, Component>>> CREATE = (p, buffers) -> {
        val destinations = new ArrayList<Destination>();

        buffers.forEach(b -> {
            destinations.add(b.slot()); // Copy all destinations
            b.poll(); // Increment all buffers
        });

        // Remove non sidebar buffers for padding
        buffers.removeIf(b -> !b.slot().isSidebarPrefix() && !b.slot().isSidebarSuffix());

        // Sort sidebar buffers
        if (!buffers.isEmpty()) {
            buffers.sort((b1, b2) -> !b1.slot().isScored() || !b2.slot().isScored() ? 0
                    : Integer.compare(b1.slot().score(), b2.slot().score())); // Ensure Order
        }

        // Pad the sidebar with blank lines between set lines
        ScoreboardUtil.padSidebar(destinations, buffers);

        // Create teams from destinations & adds fake/real ps accordingly
        ScoreboardUtil.initializeDestinations(p, destinations);
    };


    public final BiConsumer<Player, List<AnimationBuffer<Destination, TextAnimation, Component>>> UPDATE = (p, buffers) ->
    {
            /*
			  We know we are viewing the correct scoreboard because of shouldDisplay().
			  This is quicker than calling DisplayAPI.scoreboard(p)
			 */
        val scoreboard = p.getScoreboard();

        buffers.forEach(b -> {

            val destination = b.slot();

            val slot = destination.slot();
            val tag = destination.tag();

            val team = ScoreboardUtil.getOrRegisterTeam(p, destination);

            val text = b.poll();

            if (tag == Destination.Tag.OBJECTIVE_NAME) {
                Objects.requireNonNull(scoreboard.getObjective(slot)).displayName(text);
            }

            if (tag == Destination.Tag.PREFIX) {
                assert team != null;
                team.prefix(text);
            }

            if (tag == Destination.Tag.SUFFIX) {
                assert team != null;
                team.suffix(text);
            }

        });
    };


    public final BiConsumer<Player, List<AnimationBuffer<Destination, TextAnimation, Component>>> DESTROY = (p, buffers) -> {
			/*
			  We know we are viewing the correct scoreboard because of shouldDisplay().
			  This is quicker than calling DisplayAPI.scoreboard(p)
			 */
        val scoreboard = p.getScoreboard();

        buffers.forEach(b -> {

            val destination = b.slot();

            val slot = destination.slot();
            val objective = scoreboard.getObjective(slot);

            val team = ScoreboardUtil.getOrRegisterTeam(p, destination);

            val pName = ScoreboardUtil.playerName(destination, team);

            assert pName != null;
            assert objective != null;
            objective.getScore(pName).resetScore();

            // Slot can be null if referencing name tag destinations
            if (slot != null && !ScoreboardUtil.hasEntries(objective)) {
                scoreboard.clearSlot(slot);
            }

        });

    };

}
