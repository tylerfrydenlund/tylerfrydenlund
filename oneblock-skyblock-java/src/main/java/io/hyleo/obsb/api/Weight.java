package io.hyleo.obsb.api;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;

@Accessors(fluent = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Weight {

    int quality;
    double weight;

    @NotNull
    public static Weight of(int quality, double weight) {
        return new Weight(quality, weight);
    }

    @NotNull
    public static Weight of(int quality) {
        return new Weight(quality, 1);
    }

}
