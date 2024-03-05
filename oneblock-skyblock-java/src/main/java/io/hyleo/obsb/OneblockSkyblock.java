package io.hyleo.obsb;

import com.google.common.collect.ImmutableList;
import io.hyleo.obsb.api.EasyWorldGenerator;
import io.hyleo.obsb.api.Empire;
import io.hyleo.obsb.api.Phase;
import io.hyleo.obsb.api.PhaseEnchantment;
import io.hyleo.obsb.listeners.*;
import io.hyleo.obsb.listeners.bossfights.SageFightListener;
import io.hyleo.obsb.listeners.bossfights.WardenBossFightListener;
import io.hyleo.obsb.listeners.bossfights.WitchFightListener;
import io.hyleo.obsb.listeners.bossfights.WitherFightListener;
import io.hyleo.obsb.phases.*;
import io.hyleo.obsb.testing_tools.*;
import io.hyleo.obsb.util.Util;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import lombok.val;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.stream.Stream;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public final class OneblockSkyblock extends JavaPlugin implements Listener {

    private static OneblockSkyblock instance;

    /**
     * Gets the instance of the plugin created by the server
     *
     * @return the instance of the plugin
     */
    @NotNull
    public static OneblockSkyblock getInstance() {
        return Objects.requireNonNull(instance);
    }

    @NotNull
    List<@NotNull Phase> phases = new ArrayList<>();
    @NotNull
    Map<@NotNull Phase, @NotNull PhaseEnchantment> enchantments = new HashMap<>();

    @NotNull
    Map<@NonNull UUID, @NotNull Empire> empires = new HashMap<>();

    @NotNull
    Map<@NotNull UUID, @NotNull UUID> playerEmpires = new HashMap<>();

    @Override
    public void onLoad() {
        kickAllPlayers();
        instance = this;
    }

    @Override
    public void onEnable() {

        registerEvents();
        registerCommands();

        registerPhases();
        registerEnchantments();

        loadEmpires();
        Bukkit.getScheduler().runTaskTimer(this, Util::spawnOneblockParticleIdentifiers, 0, 5);
    }

    @Override
    public void onDisable() {
        empires.values().forEach(e -> {
            try {
                e.save();
            } catch (IOException ex) {
                getLogger().log(Level.SEVERE, "Failed to save empire " + e.name(), ex);
            }
        });
        kickAllPlayers();
    }

    /*
     * Kicks all the players from the server called when the plugin is disabled
     */
    void kickAllPlayers() {
        Bukkit.getOnlinePlayers().forEach(p -> p.kick(MessagesFactory.WELL_BE_BACK));
    }

    @Override
    public @Nullable ChunkGenerator getDefaultWorldGenerator(@NonNull String worldName, String id) {
        return EasyWorldGenerator.empty();
    }

    /**
     * Gets the total number of players that can join the server
     * Is calculated by adding the number of players in each empire
     *
     * @return the total number of players that can join the server
     */
    public static int getMaxPlayers() {
        return instance.empires.values().stream().mapToInt(e -> e.players().size()).sum();
    }

    /**
     * Gets the phase with the given index
     *
     * @param phase number starting at 0
     * @return the phase with the given index
     */
    @NonNull
    public static Phase getPhase(int phase) {
        return instance.phases.get(phase);
    }

    /**
     * Gets the index of the given phase
     *
     * @param phase the phase to get the index of
     * @return the index of the given phase
     */
    public static int getPhase(@NonNull Phase phase) {
        return instance.phases.indexOf(phase);
    }

    /**
     * Gets what phase the given empire is on
     *
     * @param empire the empire to get the phase of
     * @return the phase of the given empire
     */
    @NotNull
    public static Phase getPhase(@NonNull Empire empire) {
        return instance.phases.get(empire.phase());
    }

    /**
     * Gets all the phases in the game
     *
     * @return an immutable copy all the phases in the game
     * @apiNote the phases are in order
     */
    @NotNull
    public static List<@NotNull Phase> getPhases() {
        return ImmutableList.copyOf(instance.phases);
    }

    /**
     * Checks if the given player is in an empire
     *
     * @param player the player to check
     * @return true if the given player is in an empire
     */
    public static boolean isInEmpire(@NonNull Player player) {
        return instance.playerEmpires.containsKey(player.getUniqueId());
    }

    /**
     * Gets the empire of the given player
     *
     * @param player the player to get the empire of
     * @return the empire (team) of the given player
     */
    @NotNull
    public static Empire getEmpire(@NonNull Player player) {
        assert isInEmpire(player);

        return getEmpire(instance.playerEmpires.get(player.getUniqueId()));
    }

    /**
     * Gets the empire of the given an offline player just in case the player is not online
     * but certain operations need to be performed
     *
     * @param player the player to get the empire of
     * @return the empire (team) of the given player
     */
    @NotNull
    public static Empire getEmpire(@NonNull OfflinePlayer player) {
        return instance.empires.get(player.getUniqueId());
    }

    /**
     * Gets the empire with the given UUID
     *
     * @param empireUUID the UUID of the empire to get (must be a valid empire UUID)
     * @return the empire with the given UUID
     */
    @NotNull
    public static Empire getEmpire(@NonNull UUID empireUUID) {
        return instance.empires.get(empireUUID);
    }

    /**
     * Gets all the empires in the game
     *
     * @return an immutable copy of all the empires in the game
     */
    @NotNull
    public static List<@NotNull Empire> getEmpires() {
        return ImmutableList.copyOf(instance.empires.values());
    }

    /**
     * Checks if the given empire is in the infinite phase
     *
     * @param empire the empire to check
     * @return true if the given empire is in the infinite phase
     */
    public static boolean inInfinitePhase(Empire empire) {
        return empire.phase() == getPhases().size() - 1;
    }

    /**
     * Gets the phase targeting enchantment for the given phase
     *
     * @param phase the phase to get the enchantment of
     * @return the phase enchantment for the given phase
     * @see PhaseEnchantment
     */
    @NotNull
    public static PhaseEnchantment getTargetingEnchantment(Phase phase) {
        return instance.enchantments.get(phase);
    }

    void registerEvents() {
        val pm = getServer().getPluginManager();

        pm.registerEvents(new ButtonClickListener(), this);
        pm.registerEvents(new AnvilListener(), this);
        pm.registerEvents(new ProgressListener(), this);
        pm.registerEvents(new AntiGriefListener(), this);
        pm.registerEvents(new SpecialRulesListener(), this);
        pm.registerEvents(new LoginListener(), this);
        pm.registerEvents(new WorldListener(), this);
        pm.registerEvents(new WitchFightListener(), this);
        pm.registerEvents(new SageFightListener(), this);
        pm.registerEvents(new ServerPingListener(), this);
        pm.registerEvents(new GearDownGradeListener(), this);
        pm.registerEvents(new GhastFireballListener(), this);
        pm.registerEvents(new UpgradedCaveSpiderListener(), this);
        pm.registerEvents(new WitherFightListener(), this);
        pm.registerEvents(new WardenBossFightListener(), this);
        // pm.registerEvents(KotlinTestLoginListener.INSTANCE, this);
        pm.registerEvents(this, this);
    }

    void registerCommands() {

        Objects.requireNonNull(getCommand("phase")).setExecutor(new PhaseCommand());
        Objects.requireNonNull(getCommand("blocks")).setExecutor(new BlocksCommand());
        Objects.requireNonNull(getCommand("bossfight")).setExecutor(new BossFightCommand());
        Objects.requireNonNull(getCommand("gear")).setExecutor(new GearCommand());
        Objects.requireNonNull(getCommand("next")).setExecutor(new LazyPhaseCommand(true));
        Objects.requireNonNull(getCommand("previous")).setExecutor(new LazyPhaseCommand(false));
    }

    /**
     * Registers a phase to the game
     * Is internal to the one OneblockSkyblock
     * and should not be called after the plugin has been enabled
     *
     * @param phase the phase to register
     */
    void registerPhase(@NonNull Phase phase) {
        phases.add(phase);
        val enchantment = new PhaseEnchantment(phase);
        enchantments.put(phase, enchantment);
    }

    void registerPhases() {
        registerPhase(RollingHills.PHASE);
        registerPhase(YawningCavern.PHASE);
        registerPhase(TheUndergrowth.PHASE);
        registerPhase(CrackedWasteland.PHASE);
        registerPhase(CambrianEstuary.PHASE);
        registerPhase(BoggedCloudforest.PHASE);
        registerPhase(GleamingDepths.PHASE);
        registerPhase(DesolatePeaks.PHASE);
        registerPhase(KaleidoscopeLibrary.PHASE);
        registerPhase(ScorchedTerrorscape.PHASE);
        registerPhase(UnfathomableAbyss.PHASE);
        registerPhase(GroundZero.PHASE);
        registerPhase(Infinite.PHASE);
    }

    /**
     * Registers an empire to the game
     * Is internal to the one OneblockSkyblock
     * and should not be called after the plugin has been enabled
     */
    void loadEmpires() {
        empires.clear();
        playerEmpires.clear();

        Stream.of(Objects.requireNonNull(instance.getDataFolder().listFiles())).filter(f -> f.getName().endsWith(".json")).forEach(f -> {
            try {
                val empire = Empire.loadFrom(f);
                empires.put(empire.id(), empire);
                empire.players().stream().filter(Objects::nonNull).forEach(p -> instance.playerEmpires.put(p, empire.id()));
            } catch (IOException e) {
                getLogger().log(Level.SEVERE, "Failed to load team from file " + f.getName(), e);
            }
        });
    }


    void registerEnchantments() {
        enchantments.values().forEach(this::registerEnchantment);
    }

    public void registerEnchantment(Enchantment enchantment) {
//        if(Enchantment.getByKey(enchantment.getKey()) != null) return;
//        try {
//            Enchantment.registerEnchantment(enchantment);
//        } catch (Throwable t) {
//            t.printStackTrace();
//        }
    }

}
