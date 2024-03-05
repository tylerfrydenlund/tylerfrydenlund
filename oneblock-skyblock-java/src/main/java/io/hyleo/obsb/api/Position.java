package io.hyleo.obsb.api;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NonNull;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

@Builder
@Accessors(fluent = true, chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Position {

    @NotNull
    public static Position from(@NonNull Location location) {
        return Position.builder()
                .world(location.getWorld().getName())
                .x(location.getBlockX())
                .y(location.getBlockY())
                .z(location.getBlockZ())
                .build();
    }

    @NotNull
    final String world;

    final double x, y, z;

    @NotNull
    public Location toLocation() {
        return new Location(Bukkit.getWorld(world), x, y, z);
    }

    @NotNull
    public World getWorld() {
        return Objects.requireNonNull(Bukkit.getWorld(world));
    }


}
