package bonnett.commands;

import bonnett.Main;
import bonnett.data.enums.RoomType;
import com.sk89q.worldedit.EmptyClipboardException;
import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.entity.Player;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.*;
import com.sk89q.worldedit.session.ClipboardHolder;
import org.bukkit.command.CommandSender;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import static bonnett.Main.fs;

public class PaletteCommands {
    public void createPalette(CommandSender sender, String[] args) {
        boolean alreadyExists = false;
        for (String palette : Main.validPalettes) {
            if (palette.compareToIgnoreCase(args[0]) == 0) {
                alreadyExists = true;
                break;
            }
        }
        if (!alreadyExists) {
            if (new File(Main.paletteFolder + fs + args[0]).mkdirs()) {
                for (RoomType type : RoomType.values()) {
                    switch (type) {
                        case STRAIGHT_HALL:
                        case THREE_WAY_HALL:
                        case FOUR_WAY_HALL:
                        case CORNER_HALL:
                            if (!new File(Main.paletteFolder + fs + args[0] + fs + "halls" + fs + type.toString().toLowerCase()).mkdirs()) {
                                sender.sendMessage("Error in making: " + args[0].toUpperCase());
                            }
                            break;
                        case LARGE_BOSS:
                        case SMALL_BOSS:
                            if (!new File(Main.paletteFolder + fs + args[0] + fs + "boss_rooms" + fs + type.toString().toLowerCase()).mkdirs()) {
                                sender.sendMessage("Error in making: " + args[0].toUpperCase());
                            }
                            break;
                        default:
                            if (!new File(Main.paletteFolder + fs + args[0] + fs + "rooms" + fs + type.toString().toLowerCase()).mkdirs()) {
                                sender.sendMessage("Error in making: " + args[0].toUpperCase());
                            }
                    }
                }
                sender.sendMessage("Created a new palette called: " + args[0].toUpperCase());
                new Reload().palettes();
            } else { sender.sendMessage("Failed to create palette!"); }
        } else { sender.sendMessage("This palette already exists!"); }
    }

    public void deletePalette(CommandSender sender, String[] args) {
        if (new File(Main.paletteFolder.toString() + File.separator + args[0]).delete()) {
            sender.sendMessage("Successfully deleted: " + args[0]);
        } else { sender.sendMessage("Failed to delete: " + args[0]); }
    }
    public void addSchematic(CommandSender sender, String[] args) throws EmptyClipboardException {
        if (sender instanceof org.bukkit.entity.Player) {
            org.bukkit.entity.Player btPlayer = (org.bukkit.entity.Player) sender;
            Player player = BukkitAdapter.adapt(btPlayer);
            WorldEdit worldEdit = WorldEdit.getInstance();
            LocalSession ls = worldEdit.getSessionManager().get(player);
            Clipboard clip = ls.getClipboard().getClipboard();
            String ext = null;
            if (!args[2].endsWith(".schem")) { ext = ".schem"; }
            File saveLoc = new File(Main.paletteFolder + fs + args[0] + fs);
            switch (args[1]) {
                case "straight_hall":
                case "three_way_hall":
                case "four_way_hall":
                case "corner_hall":
                    saveLoc = new File(saveLoc + fs + "halls" + fs + args[1] + fs + args[2] + ext);
                    break;
                case "small_boss":
                case "large_boss":
                    saveLoc = new File(saveLoc + fs + "boss_rooms" + fs + args[1] + fs + args[2] + ext);
                    break;
                default:
                    saveLoc = new File(saveLoc + fs + "rooms" + fs + args[1] + fs + args[2] + ext);
            }
            sender.sendMessage(saveLoc.toString());
            if (args[2] != null) {
                try (ClipboardWriter writer = BuiltInClipboardFormat.SPONGE_SCHEMATIC.getWriter(new FileOutputStream(saveLoc))) {
                    writer.write(clip);
                } catch (IOException e) { e.printStackTrace(); }
            }
        }
    }

    public void removeSchematic(CommandSender sender, String[] args) {
        File saveLoc = new File(Main.paletteFolder + fs + args[0] + fs);
        switch (args[1]) {
            case "straight_hall":
            case "three_way_hall":
            case "four_way_hall":
            case "corner_hall":
                saveLoc = new File(saveLoc + fs + "halls" + fs + args[1] + fs + args[2]);
                break;
            case "small_boss":
            case "large_boss":
                saveLoc = new File(saveLoc + fs + "boss_rooms" + fs + args[1] + fs + args[2]);
                break;
            default:
                saveLoc = new File(saveLoc + fs + "rooms" + fs + args[1] + fs + args[2]);
        }
        if (saveLoc.delete()) { sender.sendMessage("Successfully removed '" + args[2] + "' from: " + args[0]);
        } else { sender.sendMessage("Failed to remove '" + args[2] + "' from: " + args[0]); }
    }

    public void loadSchematic(CommandSender sender, String[] args) {
        if (sender instanceof org.bukkit.entity.Player) {
            org.bukkit.entity.Player btPlayer = (org.bukkit.entity.Player) sender;
            Player player = BukkitAdapter.adapt(btPlayer);
            WorldEdit worldEdit = WorldEdit.getInstance();
            LocalSession ls = worldEdit.getSessionManager().get(player);
            File saveLoc = new File(Main.paletteFolder + fs + args[0] + fs);
            switch (args[1]) {
                case "straight_hall":
                case "three_way_hall":
                case "four_way_hall":
                case "corner_hall":
                    saveLoc = new File(saveLoc + fs + "halls" + fs + args[1] + fs + args[2]);
                    break;
                case "small_boss":
                case "large_boss":
                    saveLoc = new File(saveLoc + fs + "boss_rooms" + fs + args[1] + fs + args[2]);
                    break;
                default:
                    saveLoc = new File(saveLoc + fs + "rooms" + fs + args[1] + fs + args[2]);
            }
            ClipboardFormat format = ClipboardFormats.findByFile(saveLoc);
            assert format != null;
            try (ClipboardReader reader = format.getReader(new FileInputStream(saveLoc))) {
                ls.setClipboard(new ClipboardHolder(reader.read()));
                sender.sendMessage("Loaded '" + args[2] + "' from: " + args[0]);
            } catch (IOException e) { e.printStackTrace();
            sender.sendMessage("Failed to load '" + args[2] + "' from: " + args[0]);
            }
        }
    }
}
