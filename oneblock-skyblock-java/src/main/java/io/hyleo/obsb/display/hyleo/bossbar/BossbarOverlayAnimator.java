package io.hyleo.obsb.display.hyleo.bossbar;


import io.hyleo.obsb.display.api.Animator;
import net.kyori.adventure.bossbar.BossBar;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class BossbarOverlayAnimator implements Animator<Supplier<BossBar.Overlay>, BossBar.Overlay> {
    @Override
    public int frames(@NotNull Supplier<BossBar.Overlay> overlaySupplier) {
        return 1;
    }

    @Override
    public BossBar.Overlay animate(@NotNull Supplier<BossBar.Overlay> overlaySupplier, int frame) {
        return overlaySupplier.get();
    }
}
