package ssw.generation;

import ssw.data.UsedChunks;
import ssw.data.doors.EastDoors;
import ssw.data.doors.NorthDoors;
import ssw.data.doors.SouthDoors;
import ssw.data.doors.WestDoors;
import ssw.data.enums.Direction;
import ssw.data.math.AlignedLocation;
import ssw.data.math.BossRoomMinLocation;
import ssw.data.math.DungeonMinLocation;
import ssw.data.math.PasteLocation;
import ssw.data.paletteHandlers.SchematicHandler;
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
    private UsedChunks usedChunks;

    public GenerationHandler(String type, int size, AlignedLocation alignedLocation) {
        Location alignedLoc = alignedLocation.getAlignedLocation();
        SchematicHandler schematicHandler = new SchematicHandler(type);
        Clipboard clipboard = schematicHandler.getSchematic();

        //Generate dungeon
        PasteLocation pasteLoc = new PasteLocation(alignedLocation.toLocation(), clipboard);
        BossRoomMinLocation bRMin = new BossRoomMinLocation(alignedLocation);
        DungeonMinLocation dungeonMinLocation = new DungeonMinLocation(bRMin, size);
        usedChunks = new UsedChunks(size, dungeonMinLocation);

        try (EditSession editSession = WorldEdit.getInstance().getEditSessionFactory()
                .getEditSession(new BukkitWorld(alignedLocation.getWorld()), -1)) {
            assert false;
            Operation operation = new ClipboardHolder(clipboard).createPaste(editSession)
                    .to(pasteLoc.toBlockVector3())
                    .build();
            Operations.complete(operation);
        } catch (WorldEditException e) {
            e.printStackTrace();
            return;
        }

        usedChunks.markUsedChunks(clipboard, alignedLocation);
        usedChunks.printUsedChunks();
        if (new NorthDoors(clipboard, alignedLoc).hasDoors()) {
            Location[] northDoors = new NorthDoors(clipboard, alignedLoc).getDoors();
            for (Location doorLoc : northDoors) {
                if (doorLoc.getY() >= 0) {
                    schematicHandler = new SchematicHandler(type, doorLoc, Direction.NORTH);
                    new Room(schematicHandler.getSchematic(), doorLoc, Direction.NORTH);
                }
            }
        } else if (new EastDoors(clipboard, alignedLoc).hasDoors()) {
            Location[] eastDoors = new EastDoors(clipboard, alignedLoc).getDoors();
            for (Location doorLoc : eastDoors) {
                if (doorLoc.getY() >= 0) {
                    schematicHandler = new SchematicHandler(type, doorLoc, Direction.EAST);
                    new Room(schematicHandler.getSchematic(), doorLoc, Direction.EAST);
                }
            }
        } else if (new SouthDoors(clipboard, alignedLoc).hasDoors()) {
            Location[] southDoors = new SouthDoors(clipboard, alignedLoc).getDoors();
            for (Location doorLoc : southDoors) {
                if (doorLoc.getY() >= 0) {
                    schematicHandler = new SchematicHandler(type, doorLoc, Direction.SOUTH);
                    new Room(schematicHandler.getSchematic(), doorLoc, Direction.SOUTH);
                }
            }
        } else if (new WestDoors(clipboard, alignedLoc).hasDoors()) {
            Location[] westDoors = new WestDoors(clipboard, alignedLoc).getDoors();
            for (Location doorLoc : westDoors) {
                if (doorLoc.getY() >= 0) {
                    schematicHandler = new SchematicHandler(type, doorLoc, Direction.WEST);
                    new Room(schematicHandler.getSchematic(), doorLoc, Direction.WEST);
                }
            }
        }
    }
}
