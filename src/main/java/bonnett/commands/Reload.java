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
        softDependencies();
        palettes(sender);
        config(sender);
        plugin.genDataFolder();
        sender.sendMessage("Plugin reloaded.");
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
            sender.sendMessage("Reloaded all palettes.");
            sender.sendMessage("Valid palettes: " + Arrays.toString(valid));
        } else { sender.sendMessage("Could not find any palettes!"); }
    }

    public void config(CommandSender sender) {
        plugin.loadConfig();
        sender.sendMessage("Reloaded configuration file.");
    }

    private void softDependencies() {
        //LuckPerms
        /*Plugin softLuckPerms = Bukkit.getPluginManager().getPlugin("LuckPerms");
        Main.isLuckPerms = softLuckPerms != null;
        if (Main.isLuckPerms) {
            RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
            if (provider != null) {
                Main.api = provider.getProvider();
            }
        }*/

        //Add more below
    }
}
