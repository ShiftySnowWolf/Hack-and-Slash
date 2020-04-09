package bonnett.data.doors;

import bonnett.data.math.AlignedLocation;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.math.BlockVector3;
import org.bukkit.Location;

import static bonnett.data.doors.NorthDoors.*;
import static bonnett.data.doors.EastDoors.*;
import static bonnett.data.doors.SouthDoors.*;
import static bonnett.data.doors.WestDoors.*;

public class DoorHandler {
    private static Location[] northDoors;
    private static Location[] southDoors;
    private static Location[] eastDoors;
    private static Location[] westDoors;
    private static BlockVector3[] northDoorsNoLoc;
    private static BlockVector3[] southDoorsNoLoc;
    private static BlockVector3[] eastDoorsNoLoc;
    private static BlockVector3[] westDoorsNoLoc;
    private static boolean hasNorthDoors;
    private static boolean hasSouthDoors;
    private static boolean hasEastDoors;
    private static boolean hasWestDoors;

    public DoorHandler(Clipboard clipboard, AlignedLocation minPasteLocation) {
        northDoors = findNorthDoors(clipboard, minPasteLocation.toLocation());
        eastDoors = findEastDoors(clipboard, minPasteLocation.toLocation());
        southDoors = findSouthDoors(clipboard, minPasteLocation.toLocation());
        westDoors = findWestDoors(clipboard, minPasteLocation.toLocation());
        hasNorthDoors = hasDoors(northDoors);
        hasEastDoors = hasDoors(eastDoors);
        hasSouthDoors = hasDoors(southDoors);
        hasWestDoors = hasDoors(westDoors);
    }

    public DoorHandler(Clipboard clipboard) {
        northDoorsNoLoc = findNorthDoorsNoLoc(clipboard);
        eastDoorsNoLoc = findEastDoorsNoLoc(clipboard);
        southDoorsNoLoc= findSouthDoorsNoLoc(clipboard);
        westDoorsNoLoc = findWestDoorsNoLoc(clipboard);
        hasNorthDoors = hasDoorsNoLoc(northDoorsNoLoc);
        hasEastDoors = hasDoorsNoLoc(eastDoorsNoLoc);
        hasSouthDoors = hasDoorsNoLoc(southDoorsNoLoc);
        hasWestDoors = hasDoorsNoLoc(westDoorsNoLoc);
    }

    public boolean hasNorthDoors() {return hasNorthDoors;}
    public boolean hasEastDoors() {return hasEastDoors;}
    public boolean hasSouthDoors() {return hasSouthDoors;}
    public boolean hasWestDoors() {return hasWestDoors;}

    public Location[] getNorthDoors() {return northDoors;}
    public Location[] getEastDoors() {return eastDoors;}
    public Location[] getSouthDoors() {return southDoors;}
    public Location[] getWestDoors() {return westDoors;}

    public BlockVector3[] getNorthDoorsNoLoc() {return northDoorsNoLoc;}
    public BlockVector3[] getEastDoorsNoLoc() {return eastDoorsNoLoc;}
    public BlockVector3[] getSouthDoorsNoLoc() {return southDoorsNoLoc;}
    public BlockVector3[] getWestDoorsNoLoc() {return westDoorsNoLoc;}

    private boolean hasDoors(Location[] doors) {
        for (Location door : doors) { if (door.getY() >= 0) { return true; }}
        return false;
    }

    private boolean hasDoorsNoLoc(BlockVector3[] doors) {
        for (BlockVector3 door : doors) { if (door.getY() >= 0) { return true; }}
        return false;
    }
}
