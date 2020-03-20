package hackandslash.hackandslash;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.DirectoryStream;

public final class HackAndSlash extends JavaPlugin {

    public static HackAndSlash plugin;
    public static String[] validTypes;

    @Override
    public void onEnable() {
        plugin = this;
        System.out.println("Hack and slash loading!");

        //Commands.
        this.getCommand("generatedungeon").setExecutor(new generateCommand());

        //Dungeon schematic getter.
        File directoryPath = new File(getDataFolder() + File.separator + "dungeonSchematics");
        validTypes = directoryPath.list();
        System.out.println(validTypes);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

    }
}
