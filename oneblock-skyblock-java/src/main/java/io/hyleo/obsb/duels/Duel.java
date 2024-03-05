package io.hyleo.obsb.duels;

import io.hyleo.obsb.OneblockSkyblock;
import io.hyleo.obsb.util.Util;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;


@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Data
@Accessors(fluent = true)
public class Duel {

    public static long ACCEPT_TIMEOUT = 60000; // 1 minute


    public static Builder organize() {
        return new Builder();
    }

    public static class Builder extends DuelBuilder {

        public Duel invite() {
            AtomicReference<BukkitTask> reference = new AtomicReference<>();
            AtomicInteger time = new AtomicInteger((int) (-ACCEPT_TIMEOUT / 1000));

            val duel = super.invite();
            val task = Bukkit.getScheduler().runTaskTimer(OneblockSkyblock.getInstance(), () -> duel.run(reference.get(), time), 0, 20);

            reference.set(task);

            return duel;
        }
    }

    @lombok.Builder(builderMethodName = "organize", buildMethodName = "invite")
    public Duel(@NotNull UUID organizer, @NotNull @Singular("vs") List<Collection<UUID>> teams, @NonNull Integer duration) {
        this.organizer = organizer;
        this.teams = teams;
        this.duration = (long) (duration * 1000);
        respond(organizer, true);
    }

    long organized = System.currentTimeMillis();

    @Getter(AccessLevel.PRIVATE)
    Map<UUID, Boolean> answers = new HashMap<>(); // True if accepted, false if declined, Null if waiting answer

    @Getter(AccessLevel.PRIVATE)
    Map<UUID, DuelStats> stats = new HashMap<>();

    @NonNull UUID organizer;

    List<Collection<UUID>> teams;

    Long duration;

    @NonFinal
    @Setter(AccessLevel.PRIVATE)
    Collection<UUID> winners = List.of();

    @NonFinal
    @Setter(AccessLevel.PRIVATE)
    Long started;

    public boolean validNumberOfTeams() {
        return teams.size() > 1;
    }

    public boolean validNumberOfPlayers() {
        return teams.stream().mapToInt(Collection::size).sum() > 1;
    }

    public boolean validNumberOfPlayersPerTeam() {
        return teams.stream().allMatch(team -> team.size() > 0);
    }

    public boolean validTeamAssignments() {
        return teams.stream().allMatch(team -> teams.stream().filter(t -> t != team).noneMatch(t -> t.stream().anyMatch(team::contains)));
    }

    public List<Collection<UUID>> teams() {
        return teams.stream().map(HashSet::new).collect(Collectors.toList());
    }

    public List<UUID> players() {
        return teams.stream().flatMap(Collection::stream).collect(Collectors.toList());
    }

    public int totalPlayers() {
        return players().size();
    }

    public boolean allAccepted() {
        return whoAccepted().size() == totalPlayers();
    }

    public List<UUID> whoAccepted() {
        return answers.entrySet().stream().filter(entry -> Objects.nonNull(entry.getValue()) && entry.getValue()).map(Map.Entry::getKey).collect(Collectors.toList());
    }

    public List<UUID> whoDeclined() {
        return answers.entrySet().stream().filter(entry -> Objects.nonNull(entry.getValue()) && !entry.getValue()).map(Map.Entry::getKey).collect(Collectors.toList());
    }

    public List<UUID> whoDidntAnswer() {
        return players().stream().filter(uuid -> !answers.containsKey(uuid)).collect(Collectors.toList());
    }

    public DuelStatus status() {
        return allAccepted() ? DuelStatus.ACCEPTED : !whoDeclined().isEmpty() ? DuelStatus.DECLINED : System.currentTimeMillis() - organized >= ACCEPT_TIMEOUT ? DuelStatus.TIMEOUT : DuelStatus.WAITING;
    }

    public boolean finished() {
        return started != null && started + duration < System.currentTimeMillis();
    }

    public boolean hasTeam(UUID player) {
        return teams.stream().anyMatch(team -> team.contains(player));
    }

    public Collection<UUID> team(UUID player) {
        return new ArrayList<>(teams.stream().filter(t -> t.contains(player)).findFirst().orElseGet(List::of));
    }

    public long timeRemaining() {
        return started == null ? duration : started + duration - System.currentTimeMillis();
    }

    public DuelStatus respond(UUID responder, boolean accept) {


        if (!hasTeam(responder)) {
            return DuelStatus.NOT_IN_DUEL;
        }

        if (System.currentTimeMillis() - organized >= ACCEPT_TIMEOUT) {
            return DuelStatus.TIMEOUT;
        }

        answers.putIfAbsent(responder, accept);

        val player = Bukkit.getPlayer(responder);
        val response = answers.get(responder);

        if (response && player != null) {
            DuelsActionbar.show(player, this);
            accepted(player);
        }

        if (!response) {
            declined(responder);
        }

        return status();
    }


    public void run(BukkitTask task, AtomicInteger time) {

        val status = status();

        if (time.get() == (-ACCEPT_TIMEOUT / 1000)) {
            if (invalid()) {
                task.cancel();
                return;
            }
            players().forEach(player -> stats.put(player, new DuelStats()));
        }

        if (status == DuelStatus.TIMEOUT || status == DuelStatus.DECLINED) {
            task.cancel();
            if (status == DuelStatus.TIMEOUT) {
                timeout();
            }
            return;
        }

        if (status == DuelStatus.WAITING) {
            waiting(Math.abs(time.getAndDecrement()));
            return;
        }

        if (status == DuelStatus.ACCEPTED && started == null) {
            started = System.currentTimeMillis();
            time.set(0);
        }

        if (finished()) {
            task.cancel();
            tie();
            return;
        }

        dueling(time.getAndIncrement());

    }

    boolean invalid() {
        if (!validNumberOfTeams()) {
            invalidNumberOfTeams();
            return true;
        }

        if (!validNumberOfPlayers() || !validNumberOfPlayersPerTeam()) {
            invalidNumberOfPlayers();
            return true;
        }

        if (!validTeamAssignments()) {
            invalidTeamAssignments();
            return true;
        }

        return false;
    }

    Component invalidDuel() {
        return Component.text("Invalid duel: ", NamedTextColor.RED);
    }

    void invalidNumberOfTeams() {
        val player = Bukkit.getPlayer(organizer);
        if (player == null) return;

        player.sendMessage(invalidDuel().append(Component.text("A duel must have at least 2 teams", NamedTextColor.GRAY)));

    }

    void invalidNumberOfPlayers() {
        val player = Bukkit.getPlayer(organizer);
        if (player == null) return;

        player.sendMessage(invalidDuel().append(Component.text("A duel must have at least 2 players on different teams", NamedTextColor.GRAY)));
    }


    void invalidTeamAssignments() {
        val player = Bukkit.getPlayer(organizer);
        if (player == null) return;

        player.sendMessage(invalidDuel().append(Component.text("You have players on more than 1 team", NamedTextColor.GRAY)));

    }

    void waiting(int time) {
        if (time % 15 != 0) return;

        val waitingOn = whoDidntAnswer();
        val message = Component.text("Waiting for other players to accept the duel ", NamedTextColor.GRAY).append(Component.text("(" + waitingOn.size() + "/" + totalPlayers() + ")", NamedTextColor.YELLOW));

        Util.uuidsToPlayers(whoAccepted()).forEach(player -> player.sendMessage(message));

    }

    void tie() {
        Util.uuidsToPlayers(players()).forEach(player -> player.sendMessage(Component.text("The duel has ended in a tie", NamedTextColor.GRAY)));
    }

    void timeout() {
        val organizer = Bukkit.getOfflinePlayer(this.organizer);

        Util.uuidsToPlayers(players())
                .forEach(player -> player.sendMessage(Component.text("Duel request from " + organizer.getName() + " has timed out", NamedTextColor.RED)));
    }

    void accepted(Player player) {
        whoAccepted().forEach(accepted -> {
            val acceptedPlayer = Bukkit.getPlayer(accepted);
            if (acceptedPlayer == null) return;
            acceptedPlayer.sendMessage(Component.text(player.getName() + " has accepted the duel", NamedTextColor.GREEN));
        });
    }

    void declined(UUID responder) {
        val organizer = Bukkit.getPlayer(organizer());
        if (organizer == null) return;

        val player = Bukkit.getPlayer(responder);

        if (player != null) {
            player.sendMessage(Component.text("You have declined the duel from ", NamedTextColor.RED)
                    .append(organizer.displayName()));
        }

        val responderName = player != null ? player.displayName() : Component.text("Someone", NamedTextColor.RED);

        Util.uuidsToPlayers(whoAccepted())
                .forEach(p -> p.sendMessage(Component.textOfChildren(responderName,
                        Component.text(" has declined the duel", NamedTextColor.RED))));
    }

    void dueling(int time) {

    }

}
