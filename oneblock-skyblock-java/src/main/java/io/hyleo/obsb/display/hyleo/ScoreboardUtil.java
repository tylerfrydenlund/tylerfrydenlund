package io.hyleo.obsb.display.hyleo;

import io.hyleo.obsb.display.api.AnimationBuffer;
import io.hyleo.obsb.display.hyleo.text.TextAnimation;
import lombok.experimental.UtilityClass;
import lombok.val;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.IntStream;


@UtilityClass
public class ScoreboardUtil {

    private final Map<Player, Scoreboard> scoreboards = new HashMap<>();

    private final Map<Team, String> fakePlayers = new HashMap<>(16 * Bukkit.getMaxPlayers() / 2); // 16 sidebar lines
    // per player, and lets assume only half of the players are ever online
    // All displaySlots for a dummy objective
    private final Map<String, String> objectiveAliases = new HashMap<>(DisplaySlot.values().length);

    static {
        // Populate the map with aliases for all slots using dummy criteria
        List.of(DisplaySlot.values()).forEach(s -> objectiveAlias(s, "dummy"));
    }

    /**
     * Creates an invisible player name of chat colors with a chat color reset
     * trailing. This name is used to register scores to a fake player to appear on
     * a sidebar.
     * <p>
     * For sidebars to work as a display, we assign a fake player to a team with a
     * score (the line number). The prefix and suffix are used for displaying text.
     * <p>
     * This needs to be chat colors (which can not be seen as text characters) so a
     * set of text does not appear on the sidebar between the lines prefix and
     * suffix.
     *
     * @param team to create a fake player for.
     * @return A length of 7 chat colors with a rest on the end
     */
    public String fakePlayer(Team team) {

        var fakePlayer = fakePlayers.get(team);

        if (fakePlayer != null) {
            return fakePlayer;
        }

        val entry = new StringBuilder();

        val hashCode = team.getName().hashCode();

        IntStream.range(0, 6)
                .forEach(i -> entry.append(Objects.requireNonNull(ChatColor.getByChar(Integer.toHexString((hashCode * i) % 16)))));

        fakePlayer = entry.toString() + ChatColor.RESET;

        fakePlayers.put(team, fakePlayer);

        return fakePlayer;
    }

    public boolean fakeExists(Team team) {
        return fakePlayers.containsKey(team);
    }

    /**
     * An objectives criteria can not be reassigned after being registered to the
     * scoreboard. As a result, new objectives must be made. For our system to not
     * "loose" the existing objectives, we created a naming scheme for an objective
     * at a display slot with a specific criteria.
     * <p>
     * This in turn allows developers to still have the best of both worlds when
     * changing the criteria of an objective. They have the Display and the original
     * functionality.
     * <p>
     * WARNING: This is still a somewhat limiting system. A display slot can only
     * have one of each criteria type. Ex: There can not be 2 objectives with player
     * kill criteria assigned to the same DisplaySlot
     * <p>
     * <p>
     * This is probably going to be an unused feature for sidebars but may get used
     * for teams in the tablist, or below name.
     *
     * @param slot     the objective with the criteria will be assigned to
     * @param criteria to be assigned to the objective
     * @return The alias of the combined slot and criteria
     */
    public String objectiveAlias(DisplaySlot slot, String criteria) {

        val objectiveName = slot.name() + "_" + criteria;

        return objectiveAliases.computeIfAbsent(objectiveName, (s) -> UUID.randomUUID().toString().substring(0, 15));
    }

    /**
     * Checks if a display slot is a sidebar slot
     *
     * @param slot to test
     * @return if the slot is one of the sidebar slots
     */
    public boolean isSidebarSlot(DisplaySlot slot) {
        return slot != null && slot.name().toLowerCase().contains("sidebar");
    }

    public String lineToTeamName(int line) {
        return "line-" + line;
    }

    public void criteria(Player player, DisplaySlot slot, String criteria) {
        val scoreboard = scoreboard(player);

        val name = objectiveAlias(slot, criteria);

        val previous = scoreboard.getObjective(slot);

        var objective = scoreboard.getObjective(name);

        objective = objective != null ? objective
                : scoreboard.registerNewObjective(name, criteria,
                previous != null ? previous.displayName() : Component.empty());

        if (previous == objective) {
            return;
        }

        for (val entry : scoreboard.getEntries()) {

            if (previous == null) {
                break;
            }

            val score = previous.getScore(entry);

            val theoretical = Bukkit.getPlayerExact(entry); // Is a real player assigned
            boolean exists = ScoreboardUtil.fakeExists(scoreboard.getEntryTeam(entry)); // Is a fake player assigned

            // We don't want to copy other entries that do not belong to us from the
            // objective
            if (!score.isScoreSet() || (theoretical == null && !exists)) {
                continue;
            }

            objective.getScore(score.getEntry()).setScore(score.getScore());

        }

        if (previous != null) {
            previous.setDisplaySlot(null);
        }
        objective.setDisplaySlot(slot);

    }

    public void score(Player player, String entry, DisplaySlot slot, Integer score) {
        val s = Objects.requireNonNull(getOrRegisterObjective(player, slot)).getScore(entry);

        if (score == null) {
            s.resetScore();
            return;
        }

        s.setScore(score);

    }

    public Objective getOrRegisterObjective(Player player, DisplaySlot slot) {
        val scoreboard = scoreboard(player);
        var objective = scoreboard.getObjective(slot);

        if (objective == null) {
            criteria(player, slot, "dummy");
            objective = scoreboard.getObjective(slot);
        }

        return objective;

    }

    public Team getOrRegisterTeam(Player player, Destination destination) {

        if (destination.isObjective()) {
            return null;
        }

        val p = destination.player();
        val score = destination.score();

        // Checks if we will get/create a sidebar team or nametag team
        val teamName = destination.isNametagElement() ? p.getName() : ScoreboardUtil.lineToTeamName(score);

        val scoreboard = scoreboard(player);

        val team = scoreboard.getTeam(teamName);

        return team != null ? team : scoreboard.registerNewTeam(teamName);
    }

    public Scoreboard scoreboard(Player player) {
        return player.isOnline()
                ? scoreboards.computeIfAbsent(player, (p) -> Bukkit.getScoreboardManager().getNewScoreboard())
                : scoreboards.compute(player, (p, s) -> null);
    }

    public void showScoreboard(Player player) {
        player.setScoreboard(scoreboard(player));
    }

    public boolean viewingScoreboard(Player player) {
        return player.getScoreboard() == scoreboard(player);
    }

    public void padSidebar(List<Destination> destinations,
                           List<AnimationBuffer<Destination, TextAnimation, Component>> bufferOlds) {
        var lastLine = 0;

        for (val bufferOld : bufferOlds) {

            Destination destination = bufferOld.slot();

            int line = destination.score();

            IntStream.range(lastLine + 1, line).forEach(i -> destinations.add(Destination.custom()
                    .tag(destination.tag()).player(destination.player()).score(i).slot(destination.slot()).build()));

            lastLine = line;
        }
    }

    public String playerName(Destination destination, Team team) {

        val player = destination.player();

        if (destination.isSidebarTitle()) {
            return null;
        }

        boolean correctElement = destination.isNametagElement() || destination.isBelowName();
        // Checks to see if we will use a real player, or fake player
        return (correctElement || team == null) && player != null ? player.getName() : ScoreboardUtil.fakePlayer(team);
    }

    public void initializeDestinations(Player player, @NotNull List<Destination> destinations) {

        for (val destination : destinations) {

            if (destination.isUnknown()) {
                throw new RuntimeException("Unknown Board Destination: " + destination);
            }

            val slot = destination.slot();
            val score = destination.score();

            val team = getOrRegisterTeam(player, destination);
            val playerName = playerName(destination, team);

            if (playerName == null) {
                continue;
            }

            if (team != null) {
                // Registers the fake/real player to the team
                team.addEntry(playerName);
            }
            // Will only set the fake/real players score if the score is set
            if (score != null) {
                score(player, playerName, slot, score);
            }

        }
    }

    public boolean hasEntries(Objective objective) {
        val scoreboard = objective.getScoreboard();
        return Objects.requireNonNull(scoreboard).getEntries().stream().anyMatch(e -> objective.getScore(e).isScoreSet());
    }
}
