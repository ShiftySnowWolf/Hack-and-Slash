package bonnett.commands;

import bonnett.Main;
import org.bukkit.command.CommandSender;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static bonnett.Main.plugin;

public class Reload {

    public void all(CommandSender sender) {
        sender.sendMessage("Reloading plugin...");
        palettes(sender);
        config(sender);
        plugin.genDataFolder();
        sender.sendMessage("Plugin reloaded.");
    }

    public void palettes() {
        File paletteList = Main.paletteFolder;
        if (paletteList.isDirectory()) {
            if (!(Main.validPalettes == null)) {
                List<String> validList = new ArrayList<>(Arrays.asList(Main.validPalettes));
                validList.addAll(Arrays.asList(Objects.requireNonNull(paletteList.list())));
                Main.validPalettes = validList.toArray(Main.validPalettes);
            } else { Main.validPalettes = paletteList.list();}
            assert false;
            List<String> validList = Arrays.asList(Main.validPalettes);
            for (String invalid : Main.invalidPalettes) { validList.remove(invalid); }
            Main.validPalettes = validList.toArray(Main.validPalettes);
        }
    }


    public void palettes(CommandSender sender) {
        File paletteList = Main.paletteFolder;
        if (paletteList.isDirectory()) {
            if (!(Main.validPalettes == null)) {
                List<String> validList = new ArrayList<>(Arrays.asList(Main.validPalettes));
                validList.addAll(Arrays.asList(Objects.requireNonNull(paletteList.list())));
                Main.validPalettes = validList.toArray(Main.validPalettes);
            } else { Main.validPalettes = paletteList.list();}
            assert false;
            List<String> validList = Arrays.asList(Main.validPalettes);
            for (String invalid : Main.invalidPalettes) { validList.remove(invalid); }
            Main.validPalettes = validList.toArray(Main.validPalettes);
            sender.sendMessage("Reloaded all palettes.");
            sender.sendMessage("Valid palettes: " + Arrays.toString(Main.validPalettes));
        } else { sender.sendMessage("Could not find any palettes!"); }
    }

    public void config(CommandSender sender) {
        plugin.loadConfig();
        sender.sendMessage("Reloaded configuration file.");
    }
}
