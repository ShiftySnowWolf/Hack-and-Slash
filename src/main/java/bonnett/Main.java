package bonnett;

import bonnett.commands.devRandSchem;
import bonnett.commands.generateCommand;
import bonnett.data.tabCompleter;
import com.google.common.collect.ObjectArrays;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Objects;

public class Main extends JavaPlugin {

    //Configuration Files
    public FileConfiguration templateYML = null;
    public FileConfiguration defaultEerieYML = null;
    public FileConfiguration defaultMineYML = null;
    public FileConfiguration defaultOvergrowthYML = null;
    public FileConfiguration config = null;

    //Configuration
    public int generation_speed;
    public int max_size;
    public int min_size;
    public boolean generate_peaceful;
    public boolean include_template;

    //File Paths
    public File localLibs = new File(getDataFolder().toString() + File.separator + "libs");
    public String libStr = localLibs.toString().replace("\\", "/");

    //Plugin Info
    public static String pluginName = "[ChunkDungeons]";

    public static Main plugin;
    public static String[] validPalettes;
    public static File externalPalettes;
    public static String[] localPalettes;

    @SuppressWarnings({"ConstantConditions"})
    @Override
    public void onEnable() {
        plugin = this;

        //Plugin setup.
        pluginSetup();

        //Commands initialization
        this.getCommand("generatedungeon").setExecutor(new generateCommand());
        getCommand("generatedungeon").setTabCompleter(new tabCompleter());
        this.getCommand("generaterand").setExecutor(new devRandSchem());
        getCommand("generaterand").setTabCompleter(new tabCompleter());
    }

    @Override
    public void onDisable() {
    }

    private void pluginSetup() {
        loadConfig();

        try { genDataFolder();
        } catch (IOException e) { e.printStackTrace(); }

        externalPalettes = new File(getDataFolder().toString() + File.separator + "dungeon_schematics");
        if (externalPalettes.isDirectory()) {
            validPalettes = ObjectArrays.concat(localPalettes, Objects.requireNonNull(externalPalettes.list()), String.class);
        } else { validPalettes = localPalettes; }
        System.out.println(pluginName + " >>>> Loaded Templates: " + Arrays.toString(validPalettes));
    }

    private void genDataFolder() throws IOException {
        //Location of the data folder
        Path dataFolder = Paths.get(String.valueOf(getDataFolder()));
        Path templateFolder = Paths.get(dataFolder + File.separator + "dungeon_template");
        Path localYMLFolder = Paths.get(dataFolder + File.separator + "libs");

        //Generate config.
        saveResource("config.yml", false);
        config = getConfig();

        //Generate files
        if (!Files.isDirectory(dataFolder)) {
            dataFolder.toFile().mkdirs();
            templateFolder.toFile().mkdirs();
            localYMLFolder.toFile().mkdirs();
        }
        if (!localLibs.exists()) { localLibs.mkdirs(); }
        extractLocalTemplateYMLS();
        loadTemplateYMLS();
    }

    private void loadConfig() {
        //config.getInt("");
    }

    private void extractLocalTemplateYMLS() throws IOException {
        if (!localLibs.exists()) { localLibs.mkdirs(); }

        InputStream is = getResource("tempYMLS/template.yml");
        assert is != null;
        byte[] buffer = new byte[is.available()];
        is.read(buffer);
        OutputStream os = new FileOutputStream(libStr + "/template.yml");
        os.write(buffer);

        is = getResource("tempYMLS/default_eerie.yml");
        assert is != null;
        buffer = new byte[is.available()];
        is.read(buffer);
        os = new FileOutputStream(libStr + "/default_eerie.yml");
        os.write(buffer);

        is = getResource("tempYMLS/default_mine.yml");
        assert is != null;
        buffer = new byte[is.available()];
        is.read(buffer);
        os = new FileOutputStream(libStr + "/default_mine.yml");
        os.write(buffer);

        is = getResource("tempYMLS/default_overgrowth.yml");
        assert is != null;
        buffer = new byte[is.available()];
        is.read(buffer);
        os = new FileOutputStream(libStr + "/default_overgrowth.yml");
        os.write(buffer);

        is.close();
        os.close();
    }

    public void loadTemplateYMLS() {
        templateYML = YamlConfiguration.loadConfiguration(new File(libStr + "/template.yml"));
        defaultEerieYML = YamlConfiguration.loadConfiguration(new File(libStr + "/default_eerie.yml"));
        defaultMineYML = YamlConfiguration.loadConfiguration(new File(libStr + "/default_mine.yml"));
        defaultOvergrowthYML = YamlConfiguration.loadConfiguration(new File(libStr + "/default_overgrowth.yml"));
    }
}
