package ssw.commands;

import ssw.data.math.AlignedLocation;
import ssw.data.paletteHandlers.InvalidPalette;
import ssw.generation.GenerationHandler;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.block.CommandBlock;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static ssw.Main.*;

public class Generate {

    private boolean peace = generate_peaceful;
    private int min = min_size;
    private int max = max_size;

    public void onCommand(CommandSender sender, String[] args) {
        String diff;
        String type = args[0];
        
        // Detect the sender type and gets the location of the sender.
        Location senderLoc;
        AlignedLocation alignedLoc;
        if (sender instanceof Player) { // Sender is a player.
            Player player = (Player) sender;
            senderLoc = player.getLocation();
            alignedLoc = new AlignedLocation(senderLoc);
            diff = player.getWorld().getDifficulty().toString();
        } else if (sender instanceof CommandBlock) { // Sender is a command block.
            CommandBlock commBlock = (CommandBlock) sender;
            senderLoc = commBlock.getLocation();
            alignedLoc = new AlignedLocation(senderLoc);
            diff = commBlock.getWorld().getDifficulty().toString();
        } else { // Sender is the console
            sender.sendMessage("This command cannot be run from console!");
            return;
        }

        if (!peace && diff.equals("difficulty.peaceful")) { return; }

        // Check if the requested palette is of a valid type.
        InvalidPalette invalid = new InvalidPalette();
        if (invalid.check(type)) { sender.sendMessage("That is not a valid palette!"); return; }

        // Check if the requested dungeon size is within acceptable parameters.
        int size;
        try {
            size = Integer.parseInt(args[1]);
            if (size < min || size > max || size < 1) {
                sender.sendMessage(Color.RED + "Size must be: " + min + "-" + max + " and greater than 0.");
                return;
            }
        } catch (NumberFormatException e) {
            sender.sendMessage("Size entered was not a whole number!");
            return;
        }
        new GenerationHandler(type, size, alignedLoc);
    }
}