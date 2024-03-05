package io.hyleo.obsb.api;

import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

@RequiredArgsConstructor(staticName = "at")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Accessors(fluent = true)
@Data
public class Oneblock {

    @NotNull
    final UUID id = UUID.randomUUID();

    @NotNull
    final Location location;

    int phase;

    long blocksBroken;

}
