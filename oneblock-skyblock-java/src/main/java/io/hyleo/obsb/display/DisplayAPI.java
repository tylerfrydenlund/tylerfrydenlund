package io.hyleo.obsb.display;

import io.hyleo.obsb.display.bossbar.BossbarColorAnimator;
import io.hyleo.obsb.display.bossbar.BossbarOverlayAnimator;
import io.hyleo.obsb.display.bossbar.BossbarProgressAnimator;
import io.hyleo.obsb.display.text.TextAnimator;

public class DisplayAPI {

    public static final TextAnimator TEXT_ANIMATOR = new TextAnimator();
    public static final BossbarColorAnimator BOSSBAR_COLOR_ANIMATOR = new BossbarColorAnimator();
    public static final BossbarProgressAnimator BOSSBAR_PROGRESS_ANIMATOR = new BossbarProgressAnimator();
    public static final BossbarOverlayAnimator BOSSBAR_OVERLAY_ANIMATOR = new BossbarOverlayAnimator();


}
