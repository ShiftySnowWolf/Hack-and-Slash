package bonnett.data.math;

import com.sk89q.worldedit.math.BlockVector3;
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

    public BlockVector3 toBlockVector3() {
        return BlockVector3.at(alignedLocation.getX(), alignedLocation.getY(), alignedLocation.getZ());
    }

    public World getWorld() {
        return alignedLocation.getWorld();
    }

    public int getX() {
        return alignedLocation.getBlockX();
    }

    public int getY() {
        return alignedLocation.getBlockY();
    }

    public int getZ() {
        return alignedLocation.getBlockZ();
    }

    public void add(int x, int y, int z) {
        alignedLocation.setX(alignedLocation.getX() + x);
        alignedLocation.setY(alignedLocation.getY() + y);
        alignedLocation.setZ(alignedLocation.getZ() + z);
    }

    public void subtract(int x, int y, int z) {
        alignedLocation.setX(alignedLocation.getX() - x);
        alignedLocation.setY(alignedLocation.getY() - y);
        alignedLocation.setZ(alignedLocation.getZ() - z);
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
