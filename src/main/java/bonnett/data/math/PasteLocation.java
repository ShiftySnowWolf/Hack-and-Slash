package bonnett.data.math;

import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.math.BlockVector3;
import org.bukkit.Location;

public class PasteLocation {
    Location pasteLocation;

    public PasteLocation(Location destination, Clipboard clipboard) {
        pasteLocation = findPasteLocation(destination, clipboard);
    }

    public Location getPasteLocation() {
        return pasteLocation;
    }

    private Location findPasteLocation(Location destination, Clipboard clipboard) {
        BlockVector3 copyLoc = clipboard.getOrigin();
        BlockVector3 cornerMin = clipboard.getRegion().getMinimumPoint();
        BlockVector3 offset = BlockVector3.at(
                cornerMin.getX() - copyLoc.getX(),
                cornerMin.getY() - copyLoc.getY(),
                cornerMin.getZ() - copyLoc.getZ());
        Location pasteLocation = new Location(destination.getWorld(),
                destination.getX() - offset.getX(),
                destination.getY() - offset.getY(),
                destination.getZ() - offset.getZ());
        return pasteLocation;
    }

}
