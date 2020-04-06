package bonnett.generation;

import bonnett.data.UsedChunks;
import bonnett.data.doors.DoorHandler;
import bonnett.data.math.*;
import bonnett.data.paletteHandlers.SchematicHandler;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.session.ClipboardHolder;
import org.bukkit.Location;

import static bonnett.data.UsedChunks.markUsedChunks;
import static bonnett.data.UsedChunks.printUsedChunks;
import static bonnett.data.doors.DoorHandler.*;
import static bonnett.data.doors.DoorHandler.westDoors;

public class GenerationHandler {
    public static Clipboard clipboard = null;
    public GenerationHandler(String type, int size, AlignedLocation alignedLoc) {
        new SchematicHandler(type);

        //Generate dungeon
        PasteLocation pasteLoc = new PasteLocation(alignedLoc.toLocation(), clipboard);
        BossRoomMinLocation bRMin = new BossRoomMinLocation(alignedLoc);
        DungeonMinLocation dungeonMinLocation = new DungeonMinLocation(bRMin, size);
        new UsedChunks(size, dungeonMinLocation);

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

        markUsedChunks(clipboard, alignedLoc);
        printUsedChunks();
        new DoorHandler(clipboard, alignedLoc);
        if (hasNorthDoors) {
            for (Location doorLoc : northDoors) {
                if (doorLoc.getY() >= 0) {
                    new SchematicHandler(type, doorLoc, Direction.NORTH);
                    new Room(clipboard, doorLoc, Direction.NORTH);
                }
            }
        } else if (hasEastDoors) {
            for (Location doorLoc : eastDoors) {
                if (doorLoc.getY() >= 0) {
                    new SchematicHandler(type, doorLoc, Direction.EAST);
                    new Room(clipboard, doorLoc, Direction.EAST);
                }
            }
        } else if (hasSouthDoors) {
            for (Location doorLoc : southDoors) {
                if (doorLoc.getY() >= 0) {
                    new SchematicHandler(type, doorLoc, Direction.SOUTH);
                    new Room(clipboard, doorLoc, Direction.SOUTH);
                }
            }
        } else if (hasWestDoors) {
            for (Location doorLoc : westDoors) {
                if (doorLoc.getY() >= 0) {
                    new SchematicHandler(type, doorLoc, Direction.WEST);
                    new Room(clipboard, doorLoc, Direction.WEST);
                }
            }
        }
    }
}
