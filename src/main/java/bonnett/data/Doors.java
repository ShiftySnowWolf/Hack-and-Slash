package bonnett.data;

import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.world.block.BlockState;
import com.sk89q.worldedit.world.block.BlockType;
import org.bukkit.Location;

public class Doors {
    Location[] northDoors;
    Location[] southDoors;
    Location[] eastDoors;
    Location[] westDoors;
    boolean hasNorthDoors;
    boolean hasSouthDoors;
    boolean hasEastDoors;
    boolean hasWestDoors;

    public Doors(Clipboard clipboard, Location minPasteLocation) {
        northDoors = getNorthDoors(clipboard, minPasteLocation);
        southDoors = getSouthDoors(clipboard, minPasteLocation);
        eastDoors = getEastDoors(clipboard, minPasteLocation);
        westDoors = getWestDoors(clipboard, minPasteLocation);
        hasNorthDoors = hasDoors(northDoors);
        hasSouthDoors = hasDoors(southDoors);
        hasEastDoors = hasDoors(eastDoors);
        hasWestDoors = hasDoors(westDoors);
    }

    public Location[] getNorthDoors() {
        return northDoors;
    }

    public Location[] getSouthDoors() {
        return southDoors;
    }

    public Location[] getEastDoors() {
        return eastDoors;
    }

    public Location[] getWestDoors() {
        return westDoors;
    }

    public boolean hasNorthDoors() {
        return hasNorthDoors;
    }

    public boolean hasSouthDoors() {
        return hasSouthDoors;
    }

    public boolean hasEastDoors() {
        return hasEastDoors;
    }

    public boolean hasWestDoors() {
        return hasWestDoors;
    }

    /**
     * Checks if clipboard has north doors
     * @param doors
     * int[] containing door values
     * @return
     * True if has north doors, False if not
     */

    private boolean hasDoors(Location[] doors) {
        for (Location door : doors) {
            if (door.getY() > -1) {return true;}
        }
        return false;
    }

    /**
     * @param clipboard
     * Clipboard that contains the schematic to check
     * @return
     * int[] containing doors in order of lowest X to highest X, where
     * -1 = No door; > -1 == Y offset of door
     */

    private Location[] getNorthDoors(Clipboard clipboard, Location minPasteLocation) {
        int arraySize = clipboard.getDimensions().getX() / 16;
        Location[] doorLocations = new Location[arraySize];
        int clipboardHeight = clipboard.getDimensions().getY();
        BlockVector3 cornerMin = clipboard.getRegion().getMinimumPoint();
        int xOffset = 8;
        int xLocation = cornerMin.getX() + xOffset;
        int yLocation = cornerMin.getY();
        int zLocation = cornerMin.getZ();

        // First run offsets 8. Subsequent runs offset 16.
        for (int i = 0; i < arraySize; i++) {
            doorLocations[i].setWorld(minPasteLocation.getWorld());
            doorLocations[i].setY(-1);
            doorLocations[i].setX(minPasteLocation.getX() + xOffset);
            doorLocations[i].setZ(minPasteLocation.getZ());
            for (int startingYLocation = cornerMin.getY(); yLocation < startingYLocation + clipboardHeight; yLocation++) {
                BlockVector3 checkLoc = BlockVector3.at(xLocation, yLocation, zLocation);
                BlockState checkBlock = clipboard.getBlock(checkLoc);
                if (checkBlock.getBlockType().equals(new BlockType("minecraft:iron_block"))) {
                    doorLocations[i].setY(yLocation - startingYLocation);
                    break;
                }
            }
            xLocation += 16;
            xOffset += 16;
            yLocation = cornerMin.getY();
        }
        return doorLocations;
    }

    /**
     * @param clipboard
     * Clipboard that contains the schematic to check.
     * @return
     * int[] containing doors in order of lowest X to highest X, where
     * -1 = No door; > -1 == Y offset of door
     */

    private Location[] getSouthDoors(Clipboard clipboard, Location minPasteLocation) {
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
            doorLocations[i].setWorld(minPasteLocation.getWorld());
            doorLocations[i].setY(-1);
            doorLocations[i].setX(minPasteLocation.getX() + xOffset);
            doorLocations[i].setZ(minPasteLocation.getZ());
            for (int startingYLocation = cornerMin.getY(); yLocation < startingYLocation + clipboardHeight; yLocation++) {
                BlockVector3 checkLoc = BlockVector3.at(xLocation, yLocation, zLocation);
                BlockState checkBlock = clipboard.getBlock(checkLoc);
                if (checkBlock.getBlockType().equals(new BlockType("minecraft:iron_block"))) {
                    doorLocations[i].setY(yLocation - startingYLocation);
                    break;
                }
            }
            xLocation += 16;
            xOffset += 16;
            yLocation = cornerMin.getY();
        }
        return doorLocations;
    }

    /**
     * @param clipboard
     * Clipboard that contains the schematic to check.
     * @return
     * int[] containing doors in order of lowest Z to highest Z, where
     * -1 = No door; > -1 == Y offset of door
     */

    private Location[] getWestDoors(Clipboard clipboard, Location minPasteLocation) {
        int arraySize = clipboard.getDimensions().getZ() / 16;
        Location[] doorLocations = new Location[arraySize];
        int clipboardHeight = clipboard.getDimensions().getY();
        BlockVector3 cornerMin = clipboard.getRegion().getMinimumPoint();
        int zOffset = 8;
        int xLocation = cornerMin.getX();
        int yLocation = cornerMin.getY();
        int zLocation = cornerMin.getZ() + zOffset;

        // First run offsets 8. Subsequent runs offset 16.
        for (int i = 0; i < arraySize; i++) {
            doorLocations[i].setWorld(minPasteLocation.getWorld());
            doorLocations[i].setY(-1);
            doorLocations[i].setX(minPasteLocation.getX());
            doorLocations[i].setZ(minPasteLocation.getZ() + zOffset);
            for (int startingYLocation = cornerMin.getY(); yLocation < startingYLocation + clipboardHeight; yLocation++) {
                BlockVector3 checkLoc = BlockVector3.at(xLocation, yLocation, zLocation);
                BlockState checkBlock = clipboard.getBlock(checkLoc);
                if (checkBlock.getBlockType().equals(new BlockType("minecraft:iron_block"))) {
                    doorLocations[i].setY(yLocation - startingYLocation);
                    break;
                }
            }
            zLocation += 16;
            zOffset += 16;
            yLocation = cornerMin.getY();
        }
        return doorLocations;
    }

    /**
     * @param clipboard
     * Clipboard that contains the schematic to check.
     * @return
     * int[] containing doors in order of lowest Z to highest Z, where
     * -1 = No door; > -1 == Y offset of door
     */

    private Location[] getEastDoors(Clipboard clipboard, Location minPasteLocation) {
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
            doorLocations[i].setWorld(minPasteLocation.getWorld());
            doorLocations[i].setY(-1);
            doorLocations[i].setX(minPasteLocation.getX());
            doorLocations[i].setZ(minPasteLocation.getZ() + zOffset);
            for (int startingYLocation = cornerMin.getY(); yLocation < startingYLocation + clipboardHeight; yLocation++) {
                BlockVector3 checkLoc = BlockVector3.at(xLocation, yLocation, zLocation);
                BlockState checkBlock = clipboard.getBlock(checkLoc);
                if (checkBlock.getBlockType().equals(new BlockType("minecraft:iron_block"))) {
                    doorLocations[i].setY(yLocation - startingYLocation);
                    break;
                }
            }
            zLocation += 16;
            zOffset += 16;
            yLocation = cornerMin.getY();
        }
        return doorLocations;
    }
}
