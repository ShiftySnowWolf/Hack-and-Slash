package ssw.commands;

import ssw.Main;
import org.bukkit.command.CommandSender;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static ssw.Main.invalidPalettes;
import static ssw.Main.plugin;

public class Reload {

    public void all(CommandSender sender) {
        sender.sendMessage(Main.chatID + " Reloading plugin...");
        palettes(sender);
        config(sender);
        sender.sendMessage(Main.chatID + " Plugin has been reloaded!");
    }

    public void palettes() {
        if (Main.paletteFolder.isDirectory()) {
            File paletteList = Main.paletteFolder;
            List<String> validList = new ArrayList<>(Arrays.asList(Objects.requireNonNull(paletteList.list())));
            if (invalidPalettes != null) {for (String invalid : Main.invalidPalettes) { validList.remove(invalid); }}
            Main.validPalettes = validList.toArray(Main.validPalettes);
        }
    }


    public void palettes(CommandSender sender) {
        if (Main.paletteFolder.isDirectory()) {
            File paletteList = Main.paletteFolder;
            List<String> validList = new ArrayList<>(Arrays.asList(Objects.requireNonNull(paletteList.list())));
            Main.validPalettes = validList.toArray(Main.validPalettes);
            sender.sendMessage(Main.chatID + " Reloaded all palettes.");
            sender.sendMessage(Main.chatID + " Valid palettes: " + Arrays.toString(Main.validPalettes));
        } else { sender.sendMessage(Main.chatID + " Could not find any palettes!"); }
    }

    public void config(CommandSender sender) {
        plugin.loadConfig();
        sender.sendMessage(Main.chatID + " Reloaded configuration file.");
    }
}
