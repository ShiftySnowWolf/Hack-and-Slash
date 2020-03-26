package bonnett.commands;

import bonnett.Main;
import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Reload {
    Main plugin = Main.plugin;

    public void all(CommandSender sender) {
        sender.sendMessage(Color.YELLOW + "Reloading plugin...");
        softDependencies();
        palettes(sender);
        config(sender);
        plugin.genDataFolder();
        sender.sendMessage(Color.LIME + "Plugin reloaded.");
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
            sender.sendMessage(Color.LIME + "Reloaded all palettes.");
            sender.sendMessage(Color.LIME + "Valid palettes: " + Arrays.toString(valid));
        } else { sender.sendMessage(Color.RED + "Could not find any palettes!"); }
    }

    public void config(CommandSender sender) {
        plugin.loadConfig();
        sender.sendMessage(Color.LIME + "Reloaded configuration file.");
    }

    private void softDependencies() {
        //LuckPerms
        RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        if (provider != null) { Main.api = provider.getProvider(); }
        Plugin softLuckPerms = Bukkit.getPluginManager().getPlugin("LuckPerms");
        Main.isLuckPerms = softLuckPerms != null;

        //Add more below
    }
}
