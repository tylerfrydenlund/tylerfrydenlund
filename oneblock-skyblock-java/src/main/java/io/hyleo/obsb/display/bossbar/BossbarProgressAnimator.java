package io.hyleo.obsb.display.bossbar;


import io.hyleo.obsb.api.display.Animator;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class BossbarProgressAnimator implements Animator<Supplier<Float>, Float> {
    @Override
    public int frames(@NotNull Supplier<Float> progressSupplier) {
        return 1;
    }

    @Override
    public Float animate(@NotNull Supplier<Float> floatSupplier, int frame) {
        return Math.max(0, Math.min(1, floatSupplier.get()));
    }
}
