package bonnett.commands;

import bonnett.data.Doors;
import bonnett.data.RandomSchematic;
import bonnett.data.UsedChunks;
import bonnett.data.math.*;
import bonnett.data.verification.PaletteChecker;
import bonnett.generation.Room;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.session.ClipboardHolder;
import org.bukkit.Location;
import org.bukkit.block.CommandBlock;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;

public class Generate {

    public static UsedChunks usedChunks;

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String type = args[0];
        
        // Sender type detection and location getter.
        Location senderLoc;
        AlignedLocation alignedLoc;
        if (sender instanceof Player) {
            Player player = (Player) sender;
            senderLoc = player.getLocation();
            alignedLoc = new AlignedLocation(senderLoc);
        } else if (sender instanceof CommandBlock) {
            CommandBlock commBlock = (CommandBlock) sender;
            senderLoc = commBlock.getLocation();
            alignedLoc = new AlignedLocation(senderLoc);
        } else {
            sender.sendMessage("This command cannot be run from console!");
            return false;
        }

        //Validate requested palette
        PaletteChecker validate = new PaletteChecker();
        if (!validate.check(type)) {
            sender.sendMessage("That is not a valid palette!");
            return false;
        }

        // Size integer parsing.
        int size;
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
        
        try {
            return generateDungeon(type, size, alignedLoc);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return false;
    }
    
    public boolean generateDungeon(String type, int size, AlignedLocation alignedLoc) throws IOException {

        // Get random boss room
        RandomSchematic randomSchematic = new RandomSchematic();
        Clipboard clipboard = randomSchematic.getNext(type, true);

        //Generate dungeon
        PasteLocation pasteLoc = new PasteLocation(alignedLoc.toLocation(), clipboard);
        BossRoomMinLocation bRMin = new BossRoomMinLocation(alignedLoc);
        DungeonMinLocation dungeonMinLocation = new DungeonMinLocation(bRMin, size);
        usedChunks = new UsedChunks(size, dungeonMinLocation);

        try (EditSession editSession = WorldEdit.getInstance().getEditSessionFactory()
        .getEditSession(new BukkitWorld(alignedLoc.getWorld()), -1)) {
            Operation operation = new ClipboardHolder(clipboard).createPaste(editSession)
            .to(pasteLoc.toBlockVector3())
            // Configure here.
            .build();
            Operations.complete(operation);
        } catch (WorldEditException e) {
            e.printStackTrace();
            return false;
        }

        usedChunks.markUsedChunks(clipboard, alignedLoc);
        usedChunks.printUsedChunks();
        Doors doors = new Doors(clipboard, alignedLoc);
        if (doors.hasNorthDoors()) {
            Location doorLoc;
            Location[] northDoors = doors.getNorthDoors();
            for (int i = 0; i < northDoors.length; i++) {
                if (northDoors[i].getY() > -1) {
                    doorLoc = new Location(alignedLoc.getWorld(),
                            alignedLoc.getX() + (8 + (16 * i)),
                            alignedLoc.getY() + northDoors[i].getY(),
                            alignedLoc.getZ());
                    Room northRoom = new Room(new RandomSchematic().getNext(type, false),
                            doorLoc, Direction.NORTH);
                }
            }
        } else if (doors.hasSouthDoors()) {
            Location doorLoc;
            Location[] southDoors = doors.getSouthDoors();
            for (int i = 0; i < southDoors.length; i++) {
                if (southDoors[i].getY() > -1) {
                    doorLoc = new Location(alignedLoc.getWorld(),
                            alignedLoc.getX() + (8 + (16 * i)),
                            alignedLoc.getY() + southDoors[i].getY(),
                            alignedLoc.getZ());
                    Room southRoom = new Room(new RandomSchematic().getNext(type, false),
                            doorLoc, Direction.SOUTH);
                }
            }
        } else if (doors.hasEastDoors()) {
            Location doorLoc;
            Location[] eastDoors = doors.getEastDoors();
            for (int i = 0; i < eastDoors.length; i++) {
                if (eastDoors[i].getY() > -1) {
                    doorLoc = new Location(alignedLoc.getWorld(),
                            alignedLoc.getX() + (8 + (16 * i)),
                            alignedLoc.getY() + eastDoors[i].getY(),
                            alignedLoc.getZ());
                    Room eastRoom = new Room(new RandomSchematic().getNext(type, false),
                            doorLoc, Direction.EAST);
                }
            }
        } else if (doors.hasWestDoors()) {
            Location doorLoc;
            Location[] westDoors = doors.getWestDoors();
            for (int i = 0; i < westDoors.length; i++) {
                if (westDoors[i].getY() > -1) {
                    doorLoc = new Location(alignedLoc.getWorld(),
                            alignedLoc.getX() + (8 + (16 * i)),
                            alignedLoc.getY() + westDoors[i].getY(),
                            alignedLoc.getZ());
                    Room westRoom = new Room(new RandomSchematic().getNext(type, false),
                            doorLoc, Direction.WEST);
                }
            }
        }
        return true;
    }
}