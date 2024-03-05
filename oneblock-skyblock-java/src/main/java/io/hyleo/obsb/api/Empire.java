package io.hyleo.obsb.api;

import com.google.gson.*;
import io.hyleo.obsb.OneblockSkyblock;
import io.hyleo.obsb.util.Util;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Accessors(fluent = true)
@Getter
@Setter
public class Empire {


    @NotNull
    final static JsonSerializer<@NotNull Location> LOCATION_SERIALIZER = (w, t, ctx) -> {
        JsonObject obj = new JsonObject();
        obj.add("world", new JsonPrimitive(w.getWorld().getName()));
        obj.add("x", new JsonPrimitive(w.getX()));
        obj.add("y", new JsonPrimitive(w.getY()));
        obj.add("z", new JsonPrimitive(w.getZ()));
        return obj;
    };

    @NotNull
    final static JsonDeserializer<@NotNull Location> LOCATION_DESERIALIZER = (w, t, ctx) -> {
        JsonObject obj = w.getAsJsonObject();
        World world = Bukkit.getWorld(obj.get("world").getAsString());
        double x = obj.get("x").getAsDouble();
        double y = obj.get("y").getAsDouble();
        double z = obj.get("z").getAsDouble();
        return new Location(world, x, y, z);
    };

    @NotNull
    final static JsonSerializer<@NotNull TextColor> TEXT_COLOR_SERIALIZER = (c, t, ctx) -> new JsonPrimitive(c.asHexString());

    @NotNull
    final static JsonDeserializer<@NotNull TextColor> TEXT_COLOR_DESERIALIZER = (c, t, ctx) -> Objects.requireNonNull(TextColor.fromHexString(c.getAsString()));

    @NotNull
    public static Gson gson() {
        return new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(TextColor.class, TEXT_COLOR_SERIALIZER)
                .registerTypeAdapter(TextColor.class, TEXT_COLOR_DESERIALIZER)
                .registerTypeAdapter(Location.class, LOCATION_SERIALIZER)
                .registerTypeAdapter(Location.class, LOCATION_DESERIALIZER)
                .create();
    }

    @NotNull
    public static Empire loadFrom(@NonNull File file) throws IOException,
            IllegalArgumentException {
        val json = Files.readString(file.toPath());
        val gson = gson();

        Empire empire = gson.fromJson(json, Empire.class);
        empire.id(UUID.fromString(file.getName().replaceAll(".json", "")));

        return empire;
    }


    @Setter(AccessLevel.PRIVATE)
    UUID id;

    @NotNull
    final String name;

    @NotNull
    final TextColor color;

    int phase;

    long blocksBroken;

    long totalBlocksBroken;

    @NotNull
    final List<@NotNull UUID> players;

    @NotNull
    final Location oneBlock;

    @NotNull
    public List<@NotNull Player> onlinePlayers() {
        return Util.uuidsToPlayers(players);
    }


    public boolean isOneBlock(@NonNull Location location) {
        return Util.isBlockAt(oneBlock.getBlock(), location);
    }

    public boolean isMember(@NonNull Player player) {
        return players.contains(player.getUniqueId());
    }

    public boolean save() throws IOException {
        val gson = gson();

        val json = gson.toJson(this);

        val config = new File(OneblockSkyblock.getInstance().getDataFolder(), id.toString() + ".json");

        try {
            return config.createNewFile();
        } finally {
            if (!config.exists()) OneblockSkyblock.getInstance().saveResource(config.getName(), false);
            Files.write(config.toPath(), json.getBytes());
        }

    }

    @NotNull
    public Location getOneblock() {
        return oneBlock.clone();
    }

    @NotNull
    public TextColor getComplimentaryTextColor() {
        val hexColor = Integer.parseInt(color.asHexString().replace("#", ""), 16);
        val red = hexColor >> 16;
        val green = hexColor >> 8 & 0xFF;
        val blue = hexColor & 0xFF;

        return Objects.requireNonNull(TextColor.fromHexString("#" + (red << 16 | green << 8 | blue)));
    }

    @NotNull
    public Phase currentPhase() {
        return OneblockSkyblock.getPhase(phase);
    }

    @NotNull
    public Component prefix() {
        return Component.text("[", getComplimentaryTextColor())
                .append(Component.text(name, color()))
                .append(Component.text("]", getComplimentaryTextColor()));
    }

    @NotNull
    public String wrappedDisplayNameLine1() {

        val split = name().split(" ");

        var text = new StringBuilder();

        for (String s : split) {
            if (text.length() + s.length() >= 14) {
                break;
            }
            text.append(s).append(" ");
        }

        return text.toString();
    }

    @NotNull
    public String wrappedDisplayNameLine2() {

        val split = name().split(" ");

        var line1Text = new StringBuilder();

        var text = new StringBuilder();

        for (String s : split) {
            if (line1Text.length() + s.length() < 14) {
                line1Text.append(s).append(" ");
                continue;
            }
            text.append(s).append(" ");
        }

        return text.toString();
    }
}
