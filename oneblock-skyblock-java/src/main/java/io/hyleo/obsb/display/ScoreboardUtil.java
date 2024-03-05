package io.hyleo.obsb.display;

import io.hyleo.obsb.api.display.AnimationBuffer;
import io.hyleo.obsb.display.text.TextAnimation;
import lombok.NonNull;
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
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.stream.IntStream;


@UtilityClass
public class ScoreboardUtil {

    /*
     * A map of players to their scoreboards. This is used to keep track of the
     * scoreboards that have been created for each player by the plugin
     */
    @NotNull
    private final Map<@NotNull Player, @NotNull Scoreboard> scoreboards = new HashMap<>();

    /**
     * A map of teams to their fake players. These will represent lines on the scoreboard
     */
    @NotNull
    private final Map<@NotNull Team, @NotNull String> fakePlayers = new HashMap<>(16 * Bukkit.getMaxPlayers() / 2); // 16 sidebar lines
    // per player, and lets assume only half of the players are ever online
    // All displaySlots for a dummy objective

    /**
     * This map is used to store the aliases of objectives that are assigned to a players scoreboard
     */
    @NotNull
    private final Map<@NotNull String, @NotNull String> objectiveAliases = new HashMap<>(DisplaySlot.values().length);

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
    @NotNull
    public String fakePlayer(@NonNull Team team) {

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

    public boolean fakeExists(@Nullable Team team) {
        if (team == null) {
            return false;
        }
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
    @NotNull
    public String objectiveAlias(@NonNull DisplaySlot slot, @NonNull String criteria) {

        val objectiveName = slot.name() + "_" + criteria;

        return objectiveAliases.computeIfAbsent(objectiveName, (s) -> UUID.randomUUID().toString().substring(0, 15));
    }

    /**
     * Checks if a display slot is a sidebar slot
     *
     * @param slot to test
     * @return if the slot is one of the sidebar slots
     */
    public boolean isSidebarSlot(@Nullable DisplaySlot slot) {
        return slot != null && slot.name().toLowerCase().contains("sidebar");
    }

    /**
     * Converts a line number to a useable scoreboard team name
     *
     * @param line to convert
     * @return the team name
     */
    @NotNull
    public String lineToTeamName(int line) {
        return "line-" + line;
    }

    /**
     * Create a new objective or reassign an existing
     *
     * @param player   to assign the objective to
     * @param slot     to assign the objective to
     * @param criteria to assign the objective to
     */
    public void criteria(@NonNull Player player, @NonNull DisplaySlot slot, @NonNull String criteria) {
        val scoreboard = scoreboard(player);

        val name = objectiveAlias(slot, criteria);

        assert scoreboard != null;
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

    /**
     * Set or reset a score for a player in a display slot.
     * Setting a score to null will reset the score
     *
     * @param player to set the score for
     * @param entry  to set the score for
     * @param slot   to set the score for
     * @param score  to set the score to
     */

    public void score(@NonNull Player player, @NonNull String entry, @NonNull DisplaySlot slot, @Nullable Integer score) {
        val s = Objects.requireNonNull(getOrRegisterObjective(player, slot)).getScore(entry);

        if (score == null) {
            s.resetScore();
            return;
        }

        s.setScore(score);

    }

    /**
     * Get or register an objective for a player in a display
     *
     * @param player to get or register the objective for
     * @param slot   to get or register the objective for
     * @return the objective
     */
    @NotNull
    public Objective getOrRegisterObjective(@NonNull Player player, @NonNull DisplaySlot slot) {
        val scoreboard = scoreboard(player);
        assert scoreboard != null;

        var objective = scoreboard.getObjective(slot);

        if (objective == null) {
            criteria(player, slot, "dummy");
            objective = scoreboard.getObjective(slot);
        }

        assert objective != null;
        return objective;

    }

    /**
     * Get or register a team for a player
     * Returns null if the destination is an objective
     *
     * @param player      to get or register the team for
     * @param destination to get or register the team for
     * @return the team
     */
    @Nullable
    public Team getOrRegisterTeam(@NonNull Player player, @NonNull Destination destination) {

        if (destination.isObjective()) {
            return null;
        }

        val p = destination.player();
        val score = destination.score();

        // Checks if we will get/create a sidebar team or nametag team
        val teamName = destination.isNametagElement() ? p.getName() : ScoreboardUtil.lineToTeamName(score);

        val scoreboard = scoreboard(player);
        assert scoreboard != null;

        val team = scoreboard.getTeam(teamName);

        return team != null ? team : scoreboard.registerNewTeam(teamName);
    }

    /**
     * Gets or creates a scoreboard if the player is online
     * Returns null if the player is not online and removes the scoreboard from the map
     *
     * @param player to get or create the scoreboard for
     * @return the scoreboard
     */
    @Nullable
    public Scoreboard scoreboard(@NonNull Player player) {
        return player.isOnline()
                ? scoreboards.computeIfAbsent(player, (p) -> Bukkit.getScoreboardManager().getNewScoreboard())
                : scoreboards.compute(player, (p, s) -> null);
    }

    /**
     * Shows a players scoreboard
     *
     * @param player to show a scoreboard to
     */
    public void showScoreboard(@NonNull Player player) {
        player.setScoreboard(Objects.requireNonNull(scoreboard(player)));
    }

    /**
     * Checks if a player is viewing their assigned scoreboard
     *
     * @param player to check
     * @return if the player is viewing their assigned scoreboard
     */
    public boolean viewingScoreboard(@NonNull Player player) {
        return player.getScoreboard() == scoreboard(player);
    }

    /**
     * Adds blank lines to the scoreboard where there are no assigned lines
     * but there should be blank lines created
     *
     * @param destinations that need blank lines
     * @param oldBuffers
     */
    public void padSidebar(@NonNull List<@NotNull Destination> destinations,
                           @NonNull List<@NotNull AnimationBuffer<@NotNull Destination, @NotNull TextAnimation, @NotNull Component>> oldBuffers) {
        var lastLine = 0;

        for (val bufferOld : oldBuffers) {

            Destination destination = bufferOld.slot();

            int line = destination.score();

            IntStream.range(lastLine + 1, line).forEach(i -> destinations.add(Destination.custom()
                    .tag(destination.tag()).player(destination.player()).score(i).slot(destination.slot()).build()));

            lastLine = line;
        }
    }

    /**
     * Gets a fake player name given a destination and team
     *
     * @param destination in the scoreboard where the player resides
     * @param team the player is assigned to
     * @return the fake player name or null if the destination is a sidebar title
     */
    @Nullable
    public String playerName(@NonNull Destination destination, @Nullable Team team) {

        val player = destination.player();

        if (destination.isSidebarTitle()) {
            return null;
        }

        boolean correctElement = destination.isNametagElement() || destination.isBelowName();
        // Checks to see if we will use a real player, or fake player
        if ((correctElement || team == null) && player != null) {
            return player.getName();
        } else {
            assert team != null;
            return ScoreboardUtil.fakePlayer(team);
        }
    }

    /**
     * Initializes the destinations of a player on a scoreboard
     *
     * @param player to initialize the destinations for
     * @param destinations to initialize
     */
    public void initializeDestinations(@NonNull Player player, @NonNull List<@NotNull Destination> destinations) {

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

    /**
     * Checks if an objective has any entries where the score is set
     *
     * @param objective to check
     * @return if the objective has any entries where the score is set
     */

    public boolean hasEntries(@NonNull Objective objective) {
        val scoreboard = objective.getScoreboard();
        return Objects.requireNonNull(scoreboard).getEntries().stream().anyMatch(e -> objective.getScore(e).isScoreSet());
    }
}
