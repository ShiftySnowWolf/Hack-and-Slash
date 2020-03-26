package bonnett.commands;

import bonnett.Main;
import org.bukkit.Color;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Reload {
    Main plugin = Main.plugin;

    public void all(CommandSender sender) {
        softDependencies();
        palettes(sender);
        config();
    }

    public void palettes(CommandSender sender) {
        String[] valid = Main.validPalettes;
        File paletteList = Main.paletteList;

        if (paletteList.isDirectory()) {
            if (!(valid == null)) {
                List<String> validList = new ArrayList<>(Arrays.asList(valid));
                validList.addAll(Arrays.asList(Objects.requireNonNull(paletteList.list())));
                valid = validList.toArray(valid);
            } else { valid = paletteList.list();}
            sender.sendMessage(Color.LIME + "Reloaded all palettes!");
        } else { sender.sendMessage(Color.RED + "Could not find any palettes!"); }
    }

    public void config() {
        FileConfiguration config = plugin.getConfig();
        Main.generation_speed = config.getInt("generation_speed");
        Main.max_size = config.getInt("max_size");
        Main.min_size = config.getInt("min_size");
        Main.generate_peaceful = config.getBoolean("generate_peaceful");
        Main.include_template = config.getBoolean("include_template");
    }

    private void softDependencies() {

    }
}
