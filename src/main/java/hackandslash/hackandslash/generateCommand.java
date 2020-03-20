package hackandslash.hackandslash;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class generateCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length == 3) {
                try {
                    switch (args[1].toUpperCase()) {
                        case "STRENGTH":{
                            generateType1Dungeon(args[0], Integer.parseInt(args[3]), player.getLocation());
                            break;
                        }
                        case "INTELECT":{
                            generateType2Dungeon(args[0], Integer.parseInt(args[3]), player.getLocation());
                            break;
                        }
                        case "WALKTHROUGH":{
                            generateType3Dungeon(args[0], Integer.parseInt(args[3]), player.getLocation());
                            break;
                        }
                        default: {
                            sender.sendMessage("Invalid Dungeon Type!");
                        }
        
                    }
                } catch (NumberFormatException exception) {
                    sender.sendMessage("Size must be a number!");
                }
                
            } else {
                sender.sendMessage("Usage: /generatedungeon <name> <type> <sizeInChunks>");
            }
        }
        return false;
    }

    public void generateType1Dungeon(String name, int size, Location loc) {

    }

    public void generateType2Dungeon(String name, int size, Location loc) {

    }

    public void generateType3Dungeon(String name, int size, Location loc) {

    }

}