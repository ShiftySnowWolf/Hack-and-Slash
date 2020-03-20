package bonnett;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Arrays;
import java.util.Objects;

public class HackAndSlash extends JavaPlugin {

    public static HackAndSlash plugin;
    public static String[] validTypes;
    public static File directoryPath;

    @Override
    public void onEnable() {
        plugin = this;
        //Commands.
        this.getCommand("generatedungeon").setExecutor(new generateCommand());

        //Dungeon type getter.
        directoryPath = new File(getDataFolder() + File.separator + "dungeonSchematics");
        validTypes = directoryPath.list();
        System.out.println(Arrays.toString(validTypes));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

    }
}
