package bonnett.commands;

import java.io.IOException;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.session.ClipboardHolder;

import org.bukkit.Location;
import org.bukkit.block.CommandBlock;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import bonnett.data.selectRandomSchematic;

public class devRandSchem implements CommandExecutor {
    // generate random schem
    
    public Location alignedLoc;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String type = args[0];
        Location loc;

        if (sender instanceof Player) {
            Player player = (Player) sender;
            loc = player.getLocation();
            alignedLoc = new Location(loc.getWorld(), loc.getChunk().getX() * 16, loc.getY(),
            loc.getChunk().getZ() * 16);
        } else if (sender instanceof CommandBlock) {
            CommandBlock commBlock = (CommandBlock) sender;
            loc = commBlock.getLocation();
            alignedLoc = new Location(loc.getWorld(), loc.getChunk().getX() * 16, loc.getY(),
            loc.getChunk().getZ() * 16);
        } else {
            sender.sendMessage("This command cannot be run from console!");
            return false;
        }
        
        selectRandomSchematic test = new selectRandomSchematic();
        Clipboard clipboard;
        try {
            clipboard = test.getNext(type, false);
        } catch (NullPointerException | IOException e) {
            e.printStackTrace();
            return false;
        }
        
        BlockVector3 copyLoc = clipboard.getOrigin();
        BlockVector3 cornerMin = clipboard.getRegion().getMinimumPoint();
        BlockVector3 offset = BlockVector3.at(
        cornerMin.getX() - copyLoc.getX(),
        cornerMin.getY() - copyLoc.getY(),
        cornerMin.getZ() - copyLoc.getZ());
        BlockVector3 adjLoc = BlockVector3.at(
        loc.getX() - offset.getX(),
        loc.getY() - offset.getY(),
        loc.getZ() - offset.getZ());
        
        try (EditSession editSession = WorldEdit.getInstance().getEditSessionFactory()
        .getEditSession(new BukkitWorld(loc.getWorld()), -1)) {
            Operation operation = new ClipboardHolder(clipboard).createPaste(editSession)
            .to(adjLoc)
            // Configure here.
            .build();
            
            Operations.complete(operation);
        } catch (WorldEditException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
