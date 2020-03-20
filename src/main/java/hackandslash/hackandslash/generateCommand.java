package hackandslash.hackandslash;

import com.sk89q.worldedit.WorldEdit;

import org.bukkit.Location;
import org.bukkit.block.CommandBlock;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class generateCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        String type = args[0];
        int size;
        Location loc;
        //Size integer parsing.
        try {
            size = Integer.parseInt(args[1]);
            if (size < 1) {
                sender.sendMessage("Size must be greater than 0!");
                return false;
            }
        } catch (NumberFormatException e) {
            sender.sendMessage("Size entered was not a whole number!");
            return false;
        }
        //Sender type detection and location getter.
        if (sender instanceof Player) {
            Player player = (Player) sender;
            loc = player.getLocation();
        } else if (sender instanceof CommandBlock) {
            CommandBlock commBlock = (CommandBlock) sender;
            loc = commBlock.getLocation();
        } else {
            sender.sendMessage("This command cannot be run from console!");
            return false;
        }

        generateDungeon(type, size, loc);

        return false;
    }

    public void generateDungeon(String type, int size, Location loc) {

    }
}