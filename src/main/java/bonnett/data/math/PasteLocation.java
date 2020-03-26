package bonnett.data.math;

import bonnett.Main;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.math.BlockVector2;
import com.sk89q.worldedit.math.BlockVector3;
import org.bukkit.Location;
import org.bukkit.World;

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
        switch (rotation) {
            case 0: {
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
            case 90: {/*
                BlockVector3 copyLoc = clipboard.getOrigin();
                BlockVector3 cornerMin = clipboard.getRegion().getMinimumPoint();
                BlockVector3 clipCenter = BlockVector3.at(
                        clipboard.getRegion().getCenter().getX(),
                        clipboard.getRegion().getCenter().getY(),
                        clipboard.getRegion().getCenter().getZ());
                BlockVector2 offsetFromCenter = BlockVector2.at(
                        clipCenter.getX() - copyLoc.getX(),
                        clipCenter.getZ() - copyLoc.getZ());
                BlockVector3 offset = BlockVector3.at(
                        cornerMin.getX() - copyLoc.getX(),
                        cornerMin.getY() - copyLoc.getY(),
                        cornerMin.getZ() - copyLoc.getZ());
                Location unRotated = new Location(destination.getWorld(),
                        destination.getX() - offset.getX(),
                        destination.getY() - offset.getY(),
                        destination.getZ() - offset.getZ());
                switch (pasteDirection) {
                    case WEST: {
                        return new Location(destination.getWorld(),
                                unRotated.getX() + clipboard.getDimensions().getZ(),
                                unRotated.getY(),
                                unRotated.getZ() - 2 * (offsetFromCenter.getX()));
                    }
                }*/
                BlockVector3 copyLoc = clipboard.getOrigin();
                BlockVector3 cornerMin = clipboard.getRegion().getMinimumPoint();
                BlockVector3 clipCenter = BlockVector3.at(
                        clipboard.getRegion().getCenter().getX(),
                        clipboard.getRegion().getCenter().getY(),
                        clipboard.getRegion().getCenter().getZ());
                BlockVector2 offsetFromCenter = BlockVector2.at(
                        clipCenter.getX() - copyLoc.getX(),
                        clipCenter.getZ() - copyLoc.getZ());
                BlockVector3 offset = BlockVector3.at(
                        cornerMin.getX() - copyLoc.getX(),
                        cornerMin.getY() - copyLoc.getY(),
                        cornerMin.getZ() - copyLoc.getZ());
                return new Location(destination.getWorld(),
                        destination.getX() - offset.getX() + ((2 * offsetFromCenter.getZ()) + 1),
                        destination.getY() - offset.getY(),
                        destination.getZ() - offset.getZ());
            }
            case 180: {
                System.out.println("Unimplemented 180");
            }
            case 270: {
                System.out.println("Unimplemented 270");
            }
            default: {
                Main.plugin.getLogger().warning("Invalid rotation");
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
