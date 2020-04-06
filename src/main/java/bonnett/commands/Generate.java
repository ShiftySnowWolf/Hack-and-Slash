package bonnett.commands;

import bonnett.Main;
import bonnett.data.UsedChunks;
import bonnett.data.doors.DoorHandler;
import bonnett.data.math.*;
import bonnett.data.paletteHandlers.InvalidPalette;
import bonnett.data.paletteHandlers.RandomSchematic;
import bonnett.generation.Room;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.session.ClipboardHolder;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.block.CommandBlock;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static bonnett.data.doors.DoorHandler.*;

public class Generate {

    public static UsedChunks usedChunks;
    private boolean peace = Main.generate_peaceful;
    private int min = Main.min_size;
    private int max = Main.max_size;

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

        generateDungeon(type, size, alignedLoc);

    }
    
    public void generateDungeon(String type, int size, AlignedLocation alignedLoc) {

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
            return;
        }

        usedChunks.markUsedChunks(clipboard, alignedLoc);
        usedChunks.printUsedChunks();
        new DoorHandler(clipboard, alignedLoc);
        if (hasNorthDoors) {
            Location doorLoc;
            for (Location northDoor : northDoors) {
                if (northDoor.getY() >= 0) {
                    doorLoc = northDoor;
                    new Room(new RandomSchematic().getNext(type, false),
                            doorLoc, Direction.NORTH);
                }
            }
        } else if (hasEastDoors) {
            Location doorLoc;
            for (Location eastDoor : eastDoors) {
                if (eastDoor.getY() >= 0) {
                    doorLoc = eastDoor;
                    new Room(new RandomSchematic().getNext(type, false),
                            doorLoc, Direction.EAST);
                }
            }
        } else if (hasSouthDoors) {
            Location doorLoc;
            for (Location southDoor : southDoors) {
                if (southDoor.getY() >= 0) {
                    doorLoc = southDoor;
                    new Room(new RandomSchematic().getNext(type, false),
                            doorLoc, Direction.SOUTH);
                }
            }
        } else if (hasWestDoors) {
            Location doorLoc;
            for (Location westDoor : westDoors) {
                if (westDoor.getY() >= 0) {
                    doorLoc = westDoor;
                    new Room(new RandomSchematic().getNext(type, false),
                            doorLoc, Direction.WEST);
                }
            }
        }
    }
}