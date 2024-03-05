package io.hyleo.obsb.api;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

@Accessors(fluent = true)
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
class ItemWeight extends Weight {
    boolean preventChaining;

    @NotNull
    Map<@NotNull Integer, @NotNull Weight> amounts;

    ItemWeight(int amount, double weight, boolean preventChaining, @NonNull Map<@NotNull Integer, @NotNull Weight> amounts) {
        super(amount, weight);
        this.preventChaining = preventChaining;
        this.amounts = amounts;
    }

    @NotNull
    public static ItemWeight of(int amount, double weight, boolean preventChaining, @NonNull Map<@NotNull Integer, @NotNull Weight> amounts) {
        return new ItemWeight(amount, weight, preventChaining, amounts);
    }


}
