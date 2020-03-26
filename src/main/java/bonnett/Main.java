package bonnett;

import bonnett.data.CommandGetter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class Main extends JavaPlugin {

    //Configuration Files
    public FileConfiguration config = null;

    //Configuration
    public int generation_speed;
    public int max_size;
    public int min_size;
    public boolean generate_peaceful;
    public boolean include_template;

    //Plugin Info
    public static String pluginName = "[ChunkDungeons]";

    public static Main plugin;
    public static String[] validPalettes;
    public static File paletteList;

    @SuppressWarnings({"ConstantConditions", "Security"})
    @Override
    public void onEnable() {
        plugin = this;

        //Plugin setup.
        pluginSetup();

        //Commands initialization
        this.getCommand("ChunkDungeons").setExecutor(new CommandGetter());
    }

    @Override
    public void onDisable() {
    }

    private void pluginSetup() {
        genDataFolder();
        loadConfig();

        paletteList = new File(getDataFolder().toString() + File.separator + "dungeon_palettes");
        if (paletteList.isDirectory()) {
            validPalettes = paletteList.list();
        }
        System.out.println(pluginName + " >>>> Loaded Templates: " + Arrays.toString(validPalettes));
    }

    private void genDataFolder() {
        //Location of the data folder
        Path dataFolder = Paths.get(String.valueOf(getDataFolder()));
        Path templateFolder = Paths.get(dataFolder + File.separator + "dungeon_palettes");

        //Generate files
        if (!Files.isDirectory(dataFolder)) {
            dataFolder.toFile().mkdirs();
            templateFolder.toFile().mkdirs();
        }

        //Generate config.
        saveResource("config.yml", false);
        config = getConfig();
    }

    private void loadConfig() {
        config = this.getConfig();
        generation_speed = config.getInt("generation_speed");
        max_size = config.getInt("max_size");
        min_size = config.getInt("min_size");
        generate_peaceful = config.getBoolean("generate_peaceful");
        include_template = config.getBoolean("include_template");
    }
}
