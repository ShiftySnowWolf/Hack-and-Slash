package ssw.data.doors;

import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.world.block.BlockState;
import com.sk89q.worldedit.world.block.BlockType;
import org.bukkit.Location;

public class NorthDoors extends DoorHandler {
    public NorthDoors(Clipboard clipboard) { findDoorsNoLoc(clipboard); }
    public NorthDoors(Clipboard clipboard, Location location) { findDoors(clipboard, location); }

    @Override
    void findDoors(Clipboard clipboard, Location minPasteLocation) {
        int arraySize = clipboard.getDimensions().getX() / 16;
        Location[] doorLocations = new Location[arraySize];
        int clipboardHeight = clipboard.getDimensions().getY();
        BlockVector3 cornerMin = clipboard.getRegion().getMinimumPoint();
        int xOffset = 8;
        int xLocation = cornerMin.getX() + xOffset;
        int yLocation = cornerMin.getY();
        int zLocation = cornerMin.getZ();
        System.out.println("cornermin x: " + minPasteLocation.getX());

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
                    System.out.println("door x: " + doorLocations[i].getX());
                    System.out.println("door y: " + doorLocations[i].getY());
                    System.out.println("door z: " + doorLocations[i].getZ());
                    break;
                }
            }
            xLocation += 16;
            xOffset += 16;
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
        int arraySize = clipboard.getDimensions().getX() / 16;
        BlockVector3[] doorLocations = new BlockVector3[arraySize];
        int clipboardHeight = clipboard.getDimensions().getY();
        BlockVector3 cornerMin = clipboard.getRegion().getMinimumPoint();
        int xOffset = 8;
        int xLocation = cornerMin.getX() + xOffset;
        int yLocation = cornerMin.getY();
        int zLocation = cornerMin.getZ();

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
        setDoorsNoLoc(doorLocations);
        for (BlockVector3 door : doorLocations) {
            if (door.getY() >= 0) { setHasDoors(true);
            } else { setHasDoors(false); }
        }
    }
}
