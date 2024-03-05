package io.hyleo.obsb.api.display;

import lombok.*;
import lombok.Builder.Default;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

@Builder(builderMethodName = "of")
@Data
@Accessors(fluent = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(staticName = "fast")
@EqualsAndHashCode
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@ToString
public final class Timings {

    @Default
    boolean reversed = false;

    @Default
    int delay = 0, interval = 1, repeatDelay = 0, finalDelay = 0;

    @Default
    Integer repeats = null, timeout = null;

    public int actualDelay() {
        return reversed ? finalDelay : delay;
    }

    public int actualFinalDelay() {
        return reversed ? delay : finalDelay;
    }

    public boolean isFinite() {
        return repeats != null;
    }

    public boolean hasTimeout() {
        return timeout != null;
    }

}
