package bonnett;

import bonnett.commands.PluginTabCompleter;
import bonnett.commands.CommandGetter;
import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class Main extends JavaPlugin {

    //Soft dependencies
    public static LuckPerms api;
    public static boolean isLuckPerms;

    //Configuration Files
    public static FileConfiguration config = null;

    //Configuration
    public static int generation_speed;
    public static int max_size;
    public static int min_size;
    public static boolean generate_peaceful;
    public static boolean include_template;

    //Plugin Info
    public static String pluginName = "[ChunkDungeons]";

    public static Main plugin;
    public static String[] invalidPalettes;
    public static String[] validPalettes;
    public static String[] blacklist = new String[0];
    public static File paletteList;

    @SuppressWarnings({"ConstantConditions"})
    @Override
    public void onEnable() {
        plugin = this;

        //Plugin setup.
        pluginSetup();

        //Commands initialization
        this.getCommand("chunkdungeons").setExecutor(new CommandGetter());
        this.getCommand("chunkdungeons").setTabCompleter(new PluginTabCompleter());
    }

    @Override
    public void onDisable() {
    }

    private void pluginSetup() {
        genDataFolder();
        loadConfig();

        RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        if (provider != null) { api = provider.getProvider(); }
        Plugin softLuckPerms = Bukkit.getPluginManager().getPlugin("LuckPerms");
        isLuckPerms = softLuckPerms != null;

        paletteList = new File(getDataFolder().toString() + File.separator + "dungeon_palettes");
        if (paletteList.isDirectory()) {
            validPalettes = paletteList.list();
        }
        getLogger().info(">>> Loaded Templates:" + Arrays.toString(validPalettes));
    }

    public void genDataFolder() {
        //Location of the data folder
        Path dataFolder = Paths.get(String.valueOf(getDataFolder()));
        Path templateFolder = Paths.get(dataFolder + File.separator + "dungeon_palettes");

        //Generate files
        if (!Files.isDirectory(dataFolder)) {
            if (!dataFolder.toFile().mkdirs() || templateFolder.toFile().mkdirs()) {
                getLogger().warning("Failed to make data folder and or directories!");
            }
        }

        //Generate config.
        saveResource("config.yml", false);
        config = getConfig();
    }

    public void loadConfig() {
        config = this.getConfig();
        generation_speed = config.getInt("generation_speed");
        max_size = config.getInt("max_size");
        min_size = config.getInt("min_size");
        generate_peaceful = config.getBoolean("generate_peaceful");
        include_template = config.getBoolean("include_template");
    }
}
