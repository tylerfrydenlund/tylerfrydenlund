package io.hyleo.obsb.display.displays;

import io.hyleo.obsb.api.display.AnimationBuffer;
import lombok.experimental.UtilityClass;
import net.kyori.adventure.bossbar.BossBar;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.function.BiConsumer;

@UtilityClass
public class Bossbar {

    public <A, F> BiConsumer<Player, List<AnimationBuffer<BossBar, A, F>>> create() {
        return (p, AnimationBuffers) -> AnimationBuffers.forEach(b -> {
            p.showBossBar(b.slot());
            b.poll();
        });
    }

    public <A, F> BiConsumer<Player, List<AnimationBuffer<BossBar, A, F>>> update(BiConsumer<BossBar, F> update) {
        return (p, AnimationBuffers) -> AnimationBuffers.forEach(b -> update.accept(b.slot(), b.poll()));
    }

    public <A, F> BiConsumer<Player, List<AnimationBuffer<BossBar, A, F>>> destroy() {
        return (p, AnimationBuffers) -> AnimationBuffers.forEach(b -> {
            p.hideBossBar(b.slot());
            b.poll();
        });
    }


}
