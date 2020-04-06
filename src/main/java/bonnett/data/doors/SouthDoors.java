package bonnett.data.doors;

import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.world.block.BlockState;
import com.sk89q.worldedit.world.block.BlockType;
import org.bukkit.Location;

import static bonnett.data.doors.DoorHandler.*;

public class SouthDoors {
    public static Location[] getSouthDoors() {
        return southDoors;
    }
    public static BlockVector3[] getSouthDoorsNoLoc() {
        return southDoorsNoLoc;
    }
    public static boolean hasSouthDoors() {
        return hasSouthDoors;
    }

    public static Location[] getSouthDoors(Clipboard clipboard, Location minPasteLocation) {
        int arraySize = clipboard.getDimensions().getX() / 16;
        Location[] doorLocations = new Location[arraySize];
        int clipboardHeight = clipboard.getDimensions().getY();
        BlockVector3 cornerMin = clipboard.getRegion().getMinimumPoint();
        BlockVector3 cornerMax = clipboard.getRegion().getMaximumPoint();
        int xOffset = 8;
        int xLocation = cornerMin.getX() + xOffset;
        int yLocation = cornerMin.getY();
        int zLocation = cornerMax.getZ();

        // First run offsets 8. Subsequent runs offset 16.
        for (int i = 0; i < arraySize; i++) {
            doorLocations[i] = new Location(
                    minPasteLocation.getWorld(),
                    minPasteLocation.getX() + xOffset,
                    -1,
                    minPasteLocation.getZ());

            for (int startingYLocation = cornerMin.getY(); yLocation < startingYLocation + clipboardHeight; yLocation++) {
                BlockVector3 checkLoc = BlockVector3.at(xLocation, yLocation, zLocation);
                BlockState checkBlock = clipboard.getBlock(checkLoc);
                if (checkBlock.getBlockType().equals(new BlockType("minecraft:iron_block"))) {
                    doorLocations[i].setY(yLocation - startingYLocation + minPasteLocation.getBlockY());
                    break;
                }
            }
            xLocation += 16;
            xOffset += 16;
            yLocation = cornerMin.getY();
        }
        return doorLocations;
    }

    public static BlockVector3[] getSouthDoorsNoLoc(Clipboard clipboard) {
        int arraySize = clipboard.getDimensions().getX() / 16;
        BlockVector3[] doorLocations = new BlockVector3[arraySize];
        int clipboardHeight = clipboard.getDimensions().getY();
        BlockVector3 cornerMin = clipboard.getRegion().getMinimumPoint();
        BlockVector3 cornerMax = clipboard.getRegion().getMaximumPoint();
        int xOffset = 8;
        int xLocation = cornerMin.getX() + xOffset;
        int yLocation = cornerMin.getY();
        int zLocation = cornerMax.getZ();

        // First run offsets 8. Subsequent runs offset 16.
        for (int i = 0; i < arraySize; i++) {
            doorLocations[i] = BlockVector3.at(xOffset, -1, 0);
            for (int startingYLocation = cornerMin.getY(); yLocation < startingYLocation + clipboardHeight; yLocation++) {
                BlockVector3 checkLoc = BlockVector3.at(xLocation, yLocation, zLocation);
                BlockState checkBlock = clipboard.getBlock(checkLoc);
                if (checkBlock.getBlockType().equals(new BlockType("minecraft:iron_block"))) {
                    doorLocations[i] = doorLocations[i].withY(yLocation - startingYLocation);
                    break;
                }
            }
            xLocation += 16;
            xOffset += 16;
            yLocation = cornerMin.getY();
        }
        return doorLocations;
    }
}
