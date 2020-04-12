package bonnett.generation;

import bonnett.data.UsedChunks;
import bonnett.data.doors.DoorHandler;
import bonnett.data.enums.Direction;
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

public class GenerationHandler {
    public static UsedChunks usedChunks;

    public GenerationHandler(String type, int size, AlignedLocation alignedLoc) {
        SchematicHandler schematicHandler = new SchematicHandler(type);
        Clipboard clipboard = schematicHandler.getSchematic();

        //Generate dungeon
        PasteLocation pasteLoc = new PasteLocation(alignedLoc.toLocation(), clipboard);
        BossRoomMinLocation bRMin = new BossRoomMinLocation(alignedLoc);
        DungeonMinLocation dungeonMinLocation = new DungeonMinLocation(bRMin, size);
        usedChunks = new UsedChunks(size, dungeonMinLocation);

        try (EditSession editSession = WorldEdit.getInstance().getEditSessionFactory()
                .getEditSession(new BukkitWorld(alignedLoc.getWorld()), -1)) {
            assert false;
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
        DoorHandler doorHandler = new DoorHandler(clipboard, alignedLoc);
        if (doorHandler.hasNorthDoors()) {
            Location[] northDoors = doorHandler.getNorthDoors();
            for (Location doorLoc : northDoors) {
                if (doorLoc.getY() >= 0) {
                    schematicHandler = new SchematicHandler(type, doorLoc, Direction.NORTH);
                    new Room(schematicHandler.getSchematic(), doorLoc, Direction.NORTH);
                }
            }
        } else if (doorHandler.hasEastDoors()) {
            Location[] eastDoors = doorHandler.getEastDoors();
            for (Location doorLoc : eastDoors) {
                if (doorLoc.getY() >= 0) {
                    schematicHandler = new SchematicHandler(type, doorLoc, Direction.EAST);
                    new Room(schematicHandler.getSchematic(), doorLoc, Direction.EAST);
                }
            }
        } else if (doorHandler.hasSouthDoors()) {
            Location[] southDoors = doorHandler.getSouthDoors();
            for (Location doorLoc : southDoors) {
                if (doorLoc.getY() >= 0) {
                    schematicHandler = new SchematicHandler(type, doorLoc, Direction.SOUTH);
                    new Room(schematicHandler.getSchematic(), doorLoc, Direction.SOUTH);
                }
            }
        } else if (doorHandler.hasWestDoors()) {
            Location[] westDoors = doorHandler.getWestDoors();
            for (Location doorLoc : westDoors) {
                if (doorLoc.getY() >= 0) {
                    schematicHandler = new SchematicHandler(type, doorLoc, Direction.WEST);
                    new Room(schematicHandler.getSchematic(), doorLoc, Direction.WEST);
                }
            }
        }
    }
}
