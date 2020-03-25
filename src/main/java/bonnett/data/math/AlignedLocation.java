package bonnett.data.math;

import org.bukkit.Location;
import org.bukkit.World;

public class AlignedLocation {
    Location startingLocation;
    Location alignedLocation;

    public AlignedLocation(Location location) {
        startingLocation = location;
        alignedLocation = findAlignedLocation(startingLocation);
    }

    public Location toLocation() {
        return alignedLocation;
    }

    public World getWorld() {
        return alignedLocation.getWorld();
    }

    public int getX() {
        return (int) alignedLocation.getX();
    }

    public int getY() {
        return (int) alignedLocation.getY();
    }

    public int getZ() {
        return (int) alignedLocation.getZ();
    }

    public double getStartingX() {
        return startingLocation.getX();
    }

    public double getStartingY() {
        return startingLocation.getY();
    }

    public double getStartingZ() {
        return startingLocation.getZ();
    }

    private Location findAlignedLocation(Location location) {
        location.setWorld(startingLocation.getWorld());
        location.setX(startingLocation.getChunk().getX() * 16);
        location.setZ(startingLocation.getChunk().getZ() * 16);
        location.setY(startingLocation.getY());
        return location;
    }
}
