package bonnett.data;

import bonnett.data.math.AlignedLocation;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.world.block.BlockState;
import com.sk89q.worldedit.world.block.BlockType;
import org.bukkit.Location;
import org.bukkit.block.Block;

public class Doors {
    Location[] northDoors;
    Location[] southDoors;
    Location[] eastDoors;
    Location[] westDoors;
    BlockVector3[] northDoorsNoLoc;
    BlockVector3[] southDoorsNoLoc;
    BlockVector3[] eastDoorsNoLoc;
    BlockVector3[] westDoorsNoLoc;
    boolean hasNorthDoors;
    boolean hasSouthDoors;
    boolean hasEastDoors;
    boolean hasWestDoors;

    public Doors(Clipboard clipboard, AlignedLocation minPasteLocation) {
        northDoors = getNorthDoors(clipboard, minPasteLocation.toLocation());
        southDoors = getSouthDoors(clipboard, minPasteLocation.toLocation());
        eastDoors = getEastDoors(clipboard, minPasteLocation.toLocation());
        westDoors = getWestDoors(clipboard, minPasteLocation.toLocation());
        hasNorthDoors = hasDoors(northDoors);
        hasSouthDoors = hasDoors(southDoors);
        hasEastDoors = hasDoors(eastDoors);
        hasWestDoors = hasDoors(westDoors);
    }

    public Doors(Clipboard clipboard) {
        northDoorsNoLoc = getNorthDoorsNoLoc(clipboard);
        southDoorsNoLoc= getSouthDoorsNoLoc(clipboard);
        eastDoorsNoLoc = getEastDoorsNoLoc(clipboard);
        westDoorsNoLoc = getWestDoorsNoLoc(clipboard);
        hasNorthDoors = hasDoorsNoLoc(northDoorsNoLoc);
        hasSouthDoors = hasDoorsNoLoc(southDoorsNoLoc);
        hasEastDoors = hasDoorsNoLoc(eastDoorsNoLoc);
        hasWestDoors = hasDoorsNoLoc(westDoorsNoLoc);
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

    public BlockVector3[] getNorthDoorsNoLoc() {
        return northDoorsNoLoc;
    }

    public BlockVector3[] getSouthDoorsNoLoc() {
        return southDoorsNoLoc;
    }

    public BlockVector3[] getEastDoorsNoLoc() {
        return eastDoorsNoLoc;
    }

    public BlockVector3[] getWestDoorsNoLoc() {
        return westDoorsNoLoc;
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

    private boolean hasDoors(Location[] doors) {
        for (Location door : doors) {
            if (door.getY() > -1) {return true;}
        }
        return false;
    }

    private boolean hasDoorsNoLoc(BlockVector3[] doors) {
        for (BlockVector3 door : doors) {
            if (door.getY() > -1) {return true;}
        }
        return false;
    }

    private Location[] getNorthDoors(Clipboard clipboard, Location minPasteLocation) {
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
        return doorLocations;
    }

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
        return doorLocations;
    }

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
        return doorLocations;
    }

    private BlockVector3[] getNorthDoorsNoLoc(Clipboard clipboard) {
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
        return doorLocations;
    }

    private BlockVector3[] getSouthDoorsNoLoc(Clipboard clipboard) {
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

    private BlockVector3[] getWestDoorsNoLoc(Clipboard clipboard) {
        int arraySize = clipboard.getDimensions().getZ() / 16;
        BlockVector3[] doorLocations = new BlockVector3[arraySize];
        int clipboardHeight = clipboard.getDimensions().getY();
        BlockVector3 cornerMin = clipboard.getRegion().getMinimumPoint();
        int zOffset = 8;
        int xLocation = cornerMin.getX();
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
        return doorLocations;
    }

    private BlockVector3[] getEastDoorsNoLoc(Clipboard clipboard) {
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
        return doorLocations;
    }
}
