package ssw.commands;

import com.sk89q.worldedit.EmptyClipboardException;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ssw.Main;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandGetter implements CommandExecutor {

@Override
public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String label, @Nonnull String[] args) {
    Player player = null;
    boolean senderIsPlayer = false;
    if (sender instanceof Player) { player = (Player) sender; senderIsPlayer = true; }
    if (!sender.hasPermission("chunkdungeons.admin")) { return false; }

    switch (args[0]) {
        case "generate": {
            List<String> entryRemover = new ArrayList<>(Arrays.asList(args));
            entryRemover.remove(0);
            String[] newStrings = entryRemover.toArray(args);
            Generate generate = new Generate();
            generate.onCommand(sender, newStrings);
            return true;
        }
        case "createpalette": {
            List<String> entryRemover = new ArrayList<>(Arrays.asList(args));
            entryRemover.remove(0);
            String[] newStrings = entryRemover.toArray(args);
            PaletteCommands newPalette = new PaletteCommands();
            newPalette.createPalette(sender, newStrings);
            return true;
        }
        case "deletepalette": {
            List<String> entryRemover = new ArrayList<>(Arrays.asList(args));
            entryRemover.remove(0);
            String[] newStrings = entryRemover.toArray(args);
            PaletteCommands newPalette = new PaletteCommands();
            newPalette.deletePalette(sender, newStrings);
            return true;
        }
        case "addschematic": {
            List<String> entryRemover = new ArrayList<>(Arrays.asList(args));
            entryRemover.remove(0);
            String[] newStrings = entryRemover.toArray(args);
            PaletteCommands newPalette = new PaletteCommands();
            try { newPalette.addSchematic(sender, newStrings);
            } catch (EmptyClipboardException e) {
                sender.sendMessage("There was an error adding the new schematic!");
                e.printStackTrace();
            }
            return true;
        }
        case "removeschematic": {
            List<String> entryRemover = new ArrayList<>(Arrays.asList(args));
            entryRemover.remove(0);
            String[] newStrings = entryRemover.toArray(args);
            PaletteCommands newPalette = new PaletteCommands();
            newPalette.removeSchematic(sender, newStrings);
            return true;
        }
        case "loadschematic": {
            List<String> entryRemover = new ArrayList<>(Arrays.asList(args));
            entryRemover.remove(0);
            String[] newStrings = entryRemover.toArray(args);
            PaletteCommands newPalette = new PaletteCommands();
            newPalette.loadSchematic(sender, newStrings);
            return true;
        }
        case "reload": {
            Reload reload = new Reload();
            reload.all(sender);
            return true;
        }
        case "reloadpalettes": {
            Reload reload = new Reload();
            reload.palettes(sender);
            return true;
        }
        case "reloadconfig":{
            Reload reload = new Reload();
            reload.config(sender);
            return true;
        }
        case "gui": {
            if (senderIsPlayer) {
                GUI gui = new GUI(player);
                gui.openMainGUI();
            } else { sender.sendMessage(Main.chatID = " Only a player can use this command."); }
            return true;
        }
    }
    return false;
}
}
