package bonnett.events;

import bonnett.Main;
import bonnett.commands.GUI;
import bonnett.commands.Reload;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.persistence.PersistentDataContainer;

import javax.annotation.Nonnull;

public class ClickEvent implements Listener {

    @EventHandler
    public void clickEvent(@Nonnull InventoryClickEvent event) {
        if (event.getView().getTitle().startsWith(Main.chatID)) { event.setCancelled(true); }
        if (event.getCurrentItem() == null || event.getCurrentItem().getItemMeta() == null) { return; }
        String itemName = event.getCurrentItem().getItemMeta().getDisplayName();
        CommandSender sender = event.getWhoClicked();
        Player player = (Player) sender;
        String viewName = event.getView().getTitle().replace(Main.chatID + " ", "");
        switch (viewName) {
            case "Commands":
                if (itemName.equals(GUI.generate_name)) {
                    player.closeInventory();
                    GUI gui = new GUI(player);
                    gui.openGenerateGUI(1);
                } else if (itemName.equals(GUI.createPalette_name)) {
                    player.closeInventory();
                } else if (itemName.equals(GUI.deletePalette_name)) {
                    player.closeInventory();
                } else if (itemName.equals(GUI.addSchematic_name)) {
                    player.closeInventory();
                } else if (itemName.equals(GUI.removeSchematic_name)) {
                    player.closeInventory();
                } else if (itemName.equals(GUI.loadSchematic_name)) {
                    player.closeInventory();
                } else if (itemName.equals(GUI.reload_name)) {
                    player.closeInventory();
                    Reload reload = new Reload();
                    reload.all(sender);
                } else if (itemName.equals(GUI.reloadPalettes_name)) {
                    player.closeInventory();
                    Reload reload = new Reload();
                    reload.palettes(sender);
                } else if (itemName.equals(GUI.reloadConfig_name)) {
                    player.closeInventory();
                    Reload reload = new Reload();
                    reload.config(sender);
                }
                break;
            case "[Generate] Available Palettes":
        }
    }
}
