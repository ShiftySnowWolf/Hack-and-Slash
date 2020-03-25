package bonnett.data.math;

import org.bukkit.Location;

public class AlignLocation {
    Location startingLocation;
    Location alignedLocation;

    public AlignLocation(Location location) {
        startingLocation = location;
        alignLocation();
    }

    public Location getAlignedLocation() {
        return alignedLocation;
    }

    private void alignLocation() {
        alignedLocation.setWorld(startingLocation.getWorld());
        alignedLocation.setX(startingLocation.getChunk().getX() * 16);
        alignedLocation.setZ(startingLocation.getChunk().getZ() * 16);
        alignedLocation.setY(startingLocation.getY());
    }
}
