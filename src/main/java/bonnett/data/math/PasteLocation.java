package bonnett.data.math;

import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.math.BlockVector3;
import org.bukkit.Location;

public class PasteLocation {
    Location pasteLocation;

    /**
      * Generates the location to paste a schematic so that the minLocation where blocks appear is at a given destination
      */

    public PasteLocation(Location destination, Clipboard clipboard) {
        pasteLocation = findPasteLocation(destination, clipboard);
    }

    public Location toLocation() {
        return pasteLocation;
    }

    public BlockVector3 toBlockVector3() {
        return BlockVector3.at(pasteLocation.getX(), pasteLocation.getY(), pasteLocation.getZ());
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
