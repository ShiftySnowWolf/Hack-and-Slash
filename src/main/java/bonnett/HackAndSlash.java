package bonnett;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class HackAndSlash extends JavaPlugin {

    public static HackAndSlash plugin;
    public static String[] validTemplates;
    public static File directoryPath;

    @SuppressWarnings("ConstantConditions")
    @Override
    public void onEnable() {
        plugin = this;
        ResourceCopy resCopy = new ResourceCopy();
        //This is the resource location in the .jar
        String resPath = getServer().getWorldContainer().getPath() + File.separator + "plugins" + "hackAndSlash-1.0-SNAPSHOT" + File.separator + "dungeon_templates";

        //Dungeon template directory generation
        if (getDataFolder() == null) {
            File dataFolder = new File(getServer().getWorldContainer().getPath() + File.separator + "plugins", "HackAndSlash");
            if (dataFolder.mkdirs()) {
                System.out.println("[HackandSlash] Successfully created data folder");
            } else {
                System.out.println("[HackandSlash] Failed to create data folder");
            }
        }
        try { //Tries to copy resources to external directory.
            resCopy.copyResourcesToDir(getDataFolder(), true, resPath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Dungeon template getter.
        directoryPath = new File(getDataFolder() + File.separator + "dungeon_schematics");
        validTemplates = directoryPath.list();
        System.out.println(Arrays.toString(validTemplates));

        //Commands initialization
        this.getCommand("generatedungeon").setExecutor(new generateCommand());
        getCommand("generatedungeon").setTabCompleter(new tabCompleter());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

    }
}
