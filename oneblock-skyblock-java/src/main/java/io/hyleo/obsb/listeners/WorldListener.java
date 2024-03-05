package io.hyleo.obsb.listeners;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.math.transform.AffineTransform;
import com.sk89q.worldedit.session.ClipboardHolder;
import io.hyleo.obsb.OneblockSkyblock;
import io.hyleo.obsb.util.Util;
import lombok.NonNull;
import lombok.val;
import org.bukkit.GameRule;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.entity.Evoker;
import org.bukkit.entity.Pillager;
import org.bukkit.entity.Vindicator;
import org.bukkit.entity.WitherSkeleton;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.world.WorldLoadEvent;

import java.io.IOException;

public class WorldListener implements Listener {

    @EventHandler
    void worldLoad(@NonNull WorldLoadEvent event) {
        val world = event.getWorld();

        if (world.getName().equals("obsb")) {
            OneblockSkyblock.getEmpires().stream().map(e -> e.getOneblock().getBlock())
                    .filter(b -> b.getType() == Material.AIR)
                    .forEach(b -> b.setType(Material.GRASS_BLOCK));
        }

        world.setGameRule(GameRule.DO_IMMEDIATE_RESPAWN, true);
        pasteObsbLogo(world);
        setWorldBorder(world.getWorldBorder());
    }


    @EventHandler
    void playerRespawn(@NonNull PlayerRespawnEvent event) {
        val player = event.getPlayer();
        var location = OneblockSkyblock.getEmpire(player).getOneblock();

        location = Util.center(location);
        location.add(0, 1.1, 0);

        event.setRespawnLocation(location);
    }

    @EventHandler
    void onEntityDeath(@NonNull EntityDeathEvent event) {
        val entity = event.getEntity();

        if (entity instanceof Evoker) {
            event.getDrops().clear();
        }

        if (entity instanceof Vindicator) {
            event.getDrops().removeIf(item -> item.getType().equals(Material.EMERALD));
        }

        if (entity instanceof Pillager) {
            event.getDrops().removeIf(item -> item.getType().equals(Material.EMERALD));
        }

        if (entity instanceof WitherSkeleton) {
            event.getDrops().removeIf(item -> item.getType().equals(Material.WITHER_SKELETON_SKULL));
        }
    }

    void setWorldBorder(@NonNull WorldBorder border) {
        border.setCenter(0, 0);
        border.setSize(198);
        border.setWarningDistance(0);
    }

    void pasteObsbLogo(@NonNull World world) {

        ClipboardFormat format = ClipboardFormats.findByAlias("schem");

        try (ClipboardReader reader = format.getReader(getClass().getClassLoader().getResourceAsStream("logo.schem"))) {

            try (EditSession editSession = WorldEdit.getInstance().newEditSession(BukkitAdapter.adapt(world))) {
                Clipboard clipboard = reader.read();
                val holder = new ClipboardHolder(clipboard);
                holder.setTransform(new AffineTransform().rotateY(270));

                val paste = holder.createPaste(editSession)
                        .ignoreAirBlocks(true)
                        .to(BlockVector3.at(100, 100, 0))
                        .build();

                Operations.complete(paste);
            } catch (WorldEditException e) {
                throw new RuntimeException(e);
            } finally {
                reader.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
