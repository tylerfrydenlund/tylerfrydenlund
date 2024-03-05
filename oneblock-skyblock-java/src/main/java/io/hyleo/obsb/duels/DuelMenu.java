package io.hyleo.obsb.duels;

import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import org.bukkit.entity.Player;

@RequiredArgsConstructor(staticName = "create")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Accessors(fluent = true)
@Data
public class DuelMenu {

    Player organizer;
  //  Duel.DuelBuilder builder = Duel.organize().organizer(organizer.getUniqueId());
  //  Inventory menu = Bukkit.createInventory(organizer, 54, Component.text("Create a Duel", NamedTextColor.DARK_AQUA));

    public void open() {
      //  organizer.openInventory(menu);
    }

}
