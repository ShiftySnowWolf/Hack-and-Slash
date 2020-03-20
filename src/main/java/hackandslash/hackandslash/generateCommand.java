package hackandslash.hackandslash;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class generateCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 3) {
            switch (args[1].toUpperCase()) {
                case "STRENGTH":{
                    generateType1Dungeon(args[0], args[3]);
                    break;
                }
                case "INTELECT":{
                    generateType2Dungeon(args[0], args[3]);
                    break;
                }
                case "WALKTHROUGH":{
                    generateType3Dungeon(args[0], args[3]);
                    break;
                }
                default: {
                    sender.sendMessage("Invalid Dungeon Type!");
                }

            }
        } else {
            sender.sendMessage("Usage: /generatedungeon <name> <type> <sizeInChunks>");
        }
        return false;
    }

    public void generateType1Dungeon(String name, int size) {

    }

    public void generateType2Dungeon(String name, int size) {

    }

    public void generateType3Dungeon(String name, int size) {

    }

}