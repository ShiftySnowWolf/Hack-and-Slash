package bonnett;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.session.ClipboardHolder;
import com.sk89q.worldedit.world.World;

import org.bukkit.Location;
import org.bukkit.block.CommandBlock;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class generateCommand implements CommandExecutor {

    Clipboard clipboard;
    String[] types = HackAndSlash.validTypes;
    File dir = HackAndSlash.directoryPath;
    String extension = ".schem";

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String type = args[0];
        File typePath = null;
        int size;
        Location loc;
        //Type folder location finder.
        for (String s : types) {
            if (s.compareToIgnoreCase(type) == 0) {
                typePath = new File(dir + "\\" + type.toLowerCase());
            } else {
                sender.sendMessage("There is no dungeon type called: " + type.toUpperCase());
                return false;
            }
        }
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

        try {
            generateDungeon(typePath, size, loc);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    public void generateDungeon(File typePath, int size, Location loc) throws IOException {
        //Generate boss room. <THIS IS AN UNFINISHED CODE BLOCK>
        File bossRoom = new File(typePath + "\\rooms\\boss\\bossTemplate" + extension);
        System.out.println(bossRoom);
        ClipboardFormat format = ClipboardFormats.findByFile(bossRoom);
        assert format != null;
        try (ClipboardReader reader = format.getReader(new FileInputStream(bossRoom))) {
            clipboard = (Clipboard) reader.read();
        }
        try (EditSession editSession = WorldEdit.getInstance().getEditSessionFactory().getEditSession(new BukkitWorld(loc.getWorld()), -1)) {
            Operation operation = new ClipboardHolder(clipboard)
                    .createPaste(editSession)
                    .to(BlockVector3.at(loc.getChunk().getX(), loc.getY(), loc.getChunk().getZ()))
                    //Configure here.
                    .build();
            Operations.complete(operation);
        } catch (WorldEditException e) {
            e.printStackTrace();
        }
        // <END OF UNFINISHED CODE BLOCK>
    }
}