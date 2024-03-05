package io.hyleo.obsb.display.bossbar;


import io.hyleo.obsb.api.display.Animator;
import net.kyori.adventure.bossbar.BossBar.Color;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class BossbarColorAnimator implements Animator<Supplier<Color>, Color> {
    @Override
    public int frames(@NotNull Supplier<Color> colorSupplier) {
        return 1;
    }

    @Override
    public Color animate(@NotNull Supplier<Color> colorSupplier, int frame) {
        return colorSupplier.get();
    }
}
