package bonnett;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Arrays;

public class HackAndSlash extends JavaPlugin {

    public static HackAndSlash plugin;
    public static String[] validTypes;
    public static File directoryPath;

    @Override
    public void onEnable() {
        plugin = this;

        //Dungeon template directory generation
        new genResourceDir();
        
        //Dungeon type getter.
        directoryPath = new File(getDataFolder() + File.separator + "dungeonSchematics");
        validTypes = directoryPath.list();
        System.out.println(Arrays.toString(validTypes));

        //Commands.
        //noinspection ConstantConditions
        this.getCommand("generatedungeon").setExecutor(new generateCommand());
        getCommand("generatedungeon").setTabCompleter(new tabCompleter());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

    }
}
