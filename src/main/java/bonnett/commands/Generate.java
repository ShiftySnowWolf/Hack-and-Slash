package bonnett.commands;

import bonnett.data.math.AlignedLocation;
import bonnett.data.paletteHandlers.InvalidPalette;
import bonnett.generation.GenerationHandler;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.block.CommandBlock;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static bonnett.Main.*;

public class Generate {

    private boolean peace = generate_peaceful;
    private int min = min_size;
    private int max = max_size;

    public void onCommand(CommandSender sender, String[] args) {
        String diff;
        String type = args[0];
        
        // Sender type detection and location getter.
        Location senderLoc;
        AlignedLocation alignedLoc;
        if (sender instanceof Player) {
            Player player = (Player) sender;
            senderLoc = player.getLocation();
            alignedLoc = new AlignedLocation(senderLoc);
            diff = player.getWorld().getDifficulty().toString();
        } else if (sender instanceof CommandBlock) {
            CommandBlock commBlock = (CommandBlock) sender;
            senderLoc = commBlock.getLocation();
            alignedLoc = new AlignedLocation(senderLoc);
            diff = commBlock.getWorld().getDifficulty().toString();
        } else {
            sender.sendMessage("This command cannot be run from console!");
            return;
        }

        if (!peace && diff.equals("difficulty.peaceful")) { return; }

        //Validate requested palette
        InvalidPalette invalid = new InvalidPalette();
        if (invalid.check(type)) { sender.sendMessage("That is not a valid palette!"); return; }

        // Size integer parsing.
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