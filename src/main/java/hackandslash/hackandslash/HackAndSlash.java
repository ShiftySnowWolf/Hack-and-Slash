package hackandslash.hackandslash;

import org.bukkit.plugin.java.JavaPlugin;

public final class HackAndSlash extends JavaPlugin {

    public static HackAndSlash plugin;

    @Override
    public void onEnable() {
        plugin = this;
        
        System.out.println("Hack and slash loading!");
        // /generatedungeon name type sizeInChunks
        this.getCommand("generatedungeon").setExecutor(new generateCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

    }
}
