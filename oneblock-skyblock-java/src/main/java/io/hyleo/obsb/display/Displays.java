package io.hyleo.obsb.display;


import io.hyleo.obsb.OneblockSkyblock;
import io.hyleo.obsb.api.display.Animator;
import io.hyleo.obsb.api.display.Display;
import io.hyleo.obsb.display.displays.*;
import io.hyleo.obsb.display.text.TextAnimation;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.bossbar.BossBar.Color;
import net.kyori.adventure.text.Component;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class Displays {

    public static final Display<Actionbar, TextAnimation, Component> ACTIONBAR = Display.<Actionbar, TextAnimation, Component>builder(OneblockSkyblock::getInstance, DisplayAPI.TEXT_ANIMATOR)
            .update(Actionbar.UPDATE).destroy(Actionbar.DESTROY).build();

    public static final Display<BossBar, TextAnimation, Component> BOSSBAR = bossbarDisplay(DisplayAPI.TEXT_ANIMATOR, BossBar::name);
    public static final Display<BossBar, Supplier<Color>, Color> BOSSBARCOLOR = bossbarDisplay(DisplayAPI.BOSSBAR_COLOR_ANIMATOR, BossBar::color);
    public static final Display<BossBar, Supplier<Float>, Float> BOSSBARPROGRESS = bossbarDisplay(DisplayAPI.BOSSBAR_PROGRESS_ANIMATOR, BossBar::progress);
    public static final Display<BossBar, Supplier<BossBar.Overlay>, BossBar.Overlay> BOSSBAROVERLAY = bossbarDisplay(DisplayAPI.BOSSBAR_OVERLAY_ANIMATOR, BossBar::overlay);

    public static final Display<Destination, TextAnimation, Component> SCOREBOARD = Display.<Destination, TextAnimation, Component>builder(OneblockSkyblock::getInstance, DisplayAPI.TEXT_ANIMATOR)
            .setup(Scoreboard.SETUP).cleanup(Scoreboard.CLEANUP).condition(Scoreboard.VIEW_CONDITION)
            .create(Scoreboard.CREATE).update(Scoreboard.UPDATE).destroy(Scoreboard.DESTROY).build();

    public static final Display<Integer, TextAnimation, Component> TABLIST = Display.<Integer, TextAnimation, Component>builder(OneblockSkyblock::getInstance, DisplayAPI.TEXT_ANIMATOR)
            .update(Tablist.UPDATE).destroy(Tablist.DESTROY).build();

    public static final Display<Boolean, TextAnimation, Component> TITLE = Display.<Boolean, TextAnimation, Component>builder(OneblockSkyblock::getInstance, DisplayAPI.TEXT_ANIMATOR)
            .update(Title.UPDATE).build();

    private static <A, F> Display<BossBar, A, F> bossbarDisplay(Animator<A, F> animator, BiConsumer<BossBar, F> update) {
        return Display.<BossBar, A, F>builder(OneblockSkyblock::getInstance, animator)
                .create(Bossbar.create())
                .update(Bossbar.update(update))
                .destroy(Bossbar.destroy()).build();
    }

}
