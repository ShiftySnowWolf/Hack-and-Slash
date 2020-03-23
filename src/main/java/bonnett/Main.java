package bonnett;

import com.google.common.collect.ObjectArrays;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class Main extends JavaPlugin {


    public FileConfiguration templateYML = null;
    public FileConfiguration defaultEerieYML = null;
    public FileConfiguration defaultMineYML = null;
    public FileConfiguration defaultOvergrowthYML = null;
    public File localLibs = new File(getDataFolder().toString() + File.separator + "libs");
    public String libStr = localLibs.toString().replace("\\", "/");
    public File templateYMLPath = new File(libStr + "/template.yml");
    public File defaultEerieYMLPath = new File(libStr + "/default_eerie.yml");
    public File defaultMineYMLPath = new File(libStr + "/default_mine.yml");
    public File defaultOvergrowthYMLPath = new File(libStr + "/default_overgrowth.yml");

    public static String pluginJarName = "chunkDungeons";
    public static String pluginName = "[Chunk_Dungeons]";
    public static String pluginVersion = "Dev-Re-1.0";

    public static Main plugin;
    public static String[] validTemplates;
    public static File externalTemplates;
    public static String jarUntrimmedPath;
    public static String jarPath;
    public static Path dataFolderPath;
    public static String[] localTemplates = {"Template", "Default_Eerie", "Default_Mine", "Default_Overgrowth"};

    @SuppressWarnings({"ConstantConditions"})
    @Override
    public void onEnable() {
        //Instancing needed variables
        plugin = this;
        jarUntrimmedPath = getServer().getWorldContainer().getAbsolutePath();
        int pathLength = jarUntrimmedPath.length();
        jarPath = jarUntrimmedPath.substring(0, pathLength - 1) + "/plugins/" + pluginJarName + "-" + pluginVersion + ".jar!";
        System.out.println(pluginName + " >>>> Jar Location: " + jarPath);
        dataFolderPath = getDataFolder().toPath();

        //Generate data folder if it doesn't exist
        try { genDataFolder();
        } catch (IOException e) { e.printStackTrace(); }

        //Dungeon template getter.
        if ((new File(jarUntrimmedPath + "/dungeon_templates").exists())) {
            System.out.println("Found local templates!!!!");
        } else { System.out.println("Didn't Find local templates!!!!"); }

        externalTemplates = new File(getDataFolder() + File.separator + "dungeon_schematics");
        if (externalTemplates.isDirectory()) {
            validTemplates = ObjectArrays.concat(localTemplates, externalTemplates.list(), String.class);
        } else { validTemplates = localTemplates; }
        System.out.println(pluginName + " >>>> Loaded Templates: " + Arrays.toString(validTemplates));

        //Commands initialization
        this.getCommand("generatedungeon").setExecutor(new generateCommand());
        getCommand("generatedungeon").setTabCompleter(new tabCompleter());
    }

    @Override
    public void onDisable() { /*Plugin shutdown logic*/ }

    public final void genDataFolder() throws IOException {
        //Location of the data folder
        Path dataFolder = Paths.get(String.valueOf(Main.plugin.getDataFolder()));
        Path templateFolder = Paths.get(dataFolder + File.separator + "dungeon_template");
        Path localYMLFolder = Paths.get(dataFolder + File.separator + "libs");

        if (!Files.isDirectory(dataFolder)) {
            dataFolder.toFile().mkdirs();
            templateFolder.toFile().mkdirs();
            localYMLFolder.toFile().mkdirs();
        }
        if (!localLibs.exists()) {
            localLibs.mkdirs();
        }
        extractLocalTemplateYMLS();
        loadTemplateYMLS();
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

    public final void loadTemplateYMLS() {
        templateYML = YamlConfiguration.loadConfiguration(templateYMLPath);
        defaultEerieYML = YamlConfiguration.loadConfiguration(defaultEerieYMLPath);
        defaultMineYML = YamlConfiguration.loadConfiguration(defaultMineYMLPath);
        defaultOvergrowthYML = YamlConfiguration.loadConfiguration(defaultOvergrowthYMLPath);
    }
}
