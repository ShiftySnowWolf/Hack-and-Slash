package bonnett.commands;

import bonnett.Main;
import org.bukkit.Color;
import org.bukkit.command.CommandSender;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Reload {
    Main plugin = Main.plugin;

    public void all() {

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

    }

    private void softDependencies() {

    }
}
