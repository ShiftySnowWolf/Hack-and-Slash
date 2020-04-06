package bonnett.data.math;

import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.math.BlockVector3;
import org.bukkit.Location;
import org.bukkit.World;

import static bonnett.Main.plugin;

public class PasteLocation {
    Location pasteLocation;

    /**
      * Generates the location to paste a schematic so that the minLocation where blocks appear is at a given destination
      */

    public PasteLocation(Location destination, Clipboard clipboard) {
        pasteLocation = findPasteLocation(destination, clipboard);
    }

    public PasteLocation(Location destination, Clipboard clipboard, int rotation, Direction pasteDirection) {
        pasteLocation = findPasteLocation(destination, clipboard, rotation, pasteDirection);
    }

    public Location toLocation() {
        return pasteLocation;
    }

    public BlockVector3 toBlockVector3() {
        return BlockVector3.at(pasteLocation.getX(), pasteLocation.getY(), pasteLocation.getZ());
    }

    public World getWorld() {
        return pasteLocation.getWorld();
    }

    private Location findPasteLocation(Location destination, Clipboard clipboard, int rotation, Direction pasteDirection) {
        clipboard.setOrigin(BlockVector3.at(
                clipboard.getRegion().getCenter().getX(),
                clipboard.getRegion().getCenter().getY(),
                clipboard.getRegion().getCenter().getZ()));
        switch (rotation) {
            case 0: {

            }
            case 90: {
                switch (pasteDirection) {
                    case NORTH: {

                    }
                    case SOUTH: {

                    }
                    case EAST: {

                    }
                    case WEST: {

                    }
                }
            }
            case 180: {
                System.out.println("Unimplemented 180");
            }
            case 270: {
                System.out.println("Unimplemented 270");
            }
            default: {
                plugin.getLogger().warning("Invalid rotation");
                return null;
            }
        }
    }

    private Location findPasteLocation(Location destination, Clipboard clipboard) {
        BlockVector3 copyLoc = clipboard.getOrigin();
        BlockVector3 cornerMin = clipboard.getRegion().getMinimumPoint();
        BlockVector3 offset = BlockVector3.at(
                cornerMin.getX() - copyLoc.getX(),
                cornerMin.getY() - copyLoc.getY(),
                cornerMin.getZ() - copyLoc.getZ());
        return new Location(destination.getWorld(),
                destination.getX() - offset.getX(),
                destination.getY() - offset.getY(),
                destination.getZ() - offset.getZ());
    }

}
