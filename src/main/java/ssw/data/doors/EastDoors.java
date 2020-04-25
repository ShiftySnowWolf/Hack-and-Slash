package ssw.data.doors;

import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.world.block.BlockState;
import com.sk89q.worldedit.world.block.BlockType;
import org.bukkit.Location;

public class EastDoors extends DoorHandler {
    public EastDoors(Clipboard clipboard) { findDoorsNoLoc(clipboard); }
    public EastDoors(Clipboard clipboard, Location location) { findDoors(clipboard, location); }

    @Override
    void findDoors(Clipboard clipboard, Location minPasteLocation) {
        int arraySize = clipboard.getDimensions().getZ() / 16;
        Location[] doorLocations = new Location[arraySize];
        int clipboardHeight = clipboard.getDimensions().getY();
        BlockVector3 cornerMin = clipboard.getRegion().getMinimumPoint();
        BlockVector3 cornerMax = clipboard.getRegion().getMaximumPoint();
        int zOffset = 8;
        int xLocation = cornerMax.getX();
        int yLocation = cornerMin.getY();
        int zLocation = cornerMin.getZ() + zOffset;

        // First run offsets 8. Subsequent runs offset 16.
        for (int i = 0; i < arraySize; i++) {
            doorLocations[i] = new Location(minPasteLocation.getWorld(),
                    minPasteLocation.getX(),
                    -1,
                    minPasteLocation.getZ() + zOffset);

            for (int startingYLocation = cornerMin.getY(); yLocation < startingYLocation + clipboardHeight; yLocation++) {
                BlockVector3 checkLoc = BlockVector3.at(xLocation, yLocation, zLocation);
                BlockState checkBlock = clipboard.getBlock(checkLoc);
                if (checkBlock.getBlockType().equals(new BlockType("minecraft:iron_block"))) {
                    doorLocations[i].setY(yLocation - startingYLocation + minPasteLocation.getBlockY());
                    break;
                }
            }
            zLocation += 16;
            zOffset += 16;
            yLocation = cornerMin.getY();
        }
        setDoors(doorLocations);
        for (Location door : doorLocations) {
            if (door.getY() >= 0) { setHasDoors(true);
            } else { setHasDoors(false); }
        }
    }

    @Override
    void findDoorsNoLoc(Clipboard clipboard) {
        int arraySize = clipboard.getDimensions().getZ() / 16;
        BlockVector3[] doorLocations = new BlockVector3[arraySize];
        int clipboardHeight = clipboard.getDimensions().getY();
        BlockVector3 cornerMin = clipboard.getRegion().getMinimumPoint();
        BlockVector3 cornerMax = clipboard.getRegion().getMaximumPoint();
        int zOffset = 8;
        int xLocation = cornerMax.getX();
        int yLocation = cornerMin.getY();
        int zLocation = cornerMin.getZ() + zOffset;

        // First run offsets 8. Subsequent runs offset 16.
        for (int i = 0; i < arraySize; i++) {
            doorLocations[i] = BlockVector3.at(0, -1, zOffset);
            for (int startingYLocation = cornerMin.getY(); yLocation < startingYLocation + clipboardHeight; yLocation++) {
                BlockVector3 checkLoc = BlockVector3.at(xLocation, yLocation, zLocation);
                BlockState checkBlock = clipboard.getBlock(checkLoc);
                if (checkBlock.getBlockType().equals(new BlockType("minecraft:iron_block"))) {
                    doorLocations[i] = doorLocations[i].withY(yLocation - startingYLocation);
                    break;
                }
            }
            zLocation += 16;
            zOffset += 16;
            yLocation = cornerMin.getY();
        }
        setDoorsNoLoc(doorLocations);
        for (BlockVector3 door : doorLocations) {
            if (door.getY() >= 0) { setHasDoors(true);
            } else { setHasDoors(false); }
        }
    }
}
