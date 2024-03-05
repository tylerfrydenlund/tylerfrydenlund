package io.hyleo.obsb.listeners.bossfights;

import com.destroystokyo.paper.event.entity.WitchConsumePotionEvent;
import com.destroystokyo.paper.event.entity.WitchThrowPotionEvent;
import io.hyleo.obsb.listeners.ProgressListener;
import lombok.NonNull;
import lombok.val;
import org.bukkit.entity.Witch;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class WitchFightListener implements Listener {

    @NotNull
    private static final List<@NotNull Witch> witches = new ArrayList<>();

    public static void addWitch(Witch witch) {
        witches.add(witch);
    }

    public static void removeWitch(Witch witch) {
        witches.remove(witch);
    }

    public static boolean isWitchBoss(@NonNull Witch witch) {

        if (ProgressListener.bosses.get(witch) == null) {
            return false;
        }

        return witches.contains(witch);
    }

    @EventHandler
    void onWitchBossPotionConsume(@NonNull WitchConsumePotionEvent event) {
        val witch = event.getEntity();

        if (!isWitchBoss(witch)) {
            return;
        }

        val potion = event.getPotion();
        val meta = (PotionMeta) potion.getItemMeta();
        val data = new PotionData(meta.getBasePotionData().getType(), false, true);
        meta.setBasePotionData(data);
        potion.setItemMeta(meta);
        event.setPotion(potion);
    }

    @EventHandler
    void onWitchBossPotionThrow(@NonNull WitchThrowPotionEvent event) {
        val witch = event.getEntity();

        if (!isWitchBoss(witch)) {
            return;
        }

        val potion = event.getPotion();
        val meta = (PotionMeta) potion.getItemMeta();

        if (meta.getBasePotionData().getType() == PotionType.WEAKNESS) {
            return;
        }

        val data = new PotionData(meta.getBasePotionData().getType(), false, true);
        meta.setBasePotionData(data);
        potion.setItemMeta(meta);
        event.setPotion(potion);
    }


}
