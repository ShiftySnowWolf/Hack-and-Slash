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
    public static Location[] northDoors;
    public static Location[] southDoors;
    public static Location[] eastDoors;
    public static Location[] westDoors;
    public static BlockVector3[] northDoorsNoLoc;
    public static BlockVector3[] southDoorsNoLoc;
    public static BlockVector3[] eastDoorsNoLoc;
    public static BlockVector3[] westDoorsNoLoc;
    public static boolean hasNorthDoors;
    public static boolean hasSouthDoors;
    public static boolean hasEastDoors;
    public static boolean hasWestDoors;

    public DoorHandler(Clipboard clipboard, AlignedLocation minPasteLocation) {
        northDoors = getNorthDoors(clipboard, minPasteLocation.toLocation());
        eastDoors = getEastDoors(clipboard, minPasteLocation.toLocation());
        southDoors = getSouthDoors(clipboard, minPasteLocation.toLocation());
        westDoors = getWestDoors(clipboard, minPasteLocation.toLocation());
        hasNorthDoors = hasDoors(northDoors);
        hasEastDoors = hasDoors(eastDoors);
        hasSouthDoors = hasDoors(southDoors);
        hasWestDoors = hasDoors(westDoors);
    }

    public DoorHandler(Clipboard clipboard) {
        northDoorsNoLoc = getNorthDoorsNoLoc(clipboard);
        eastDoorsNoLoc = getEastDoorsNoLoc(clipboard);
        southDoorsNoLoc= getSouthDoorsNoLoc(clipboard);
        westDoorsNoLoc = getWestDoorsNoLoc(clipboard);
        hasNorthDoors = hasDoorsNoLoc(northDoorsNoLoc);
        hasEastDoors = hasDoorsNoLoc(eastDoorsNoLoc);
        hasSouthDoors = hasDoorsNoLoc(southDoorsNoLoc);
        hasWestDoors = hasDoorsNoLoc(westDoorsNoLoc);
    }

    private boolean hasDoors(Location[] doors) {
        for (Location door : doors) { if (door.getY() >= 0) { return true; }}
        return false;
    }

    private boolean hasDoorsNoLoc(BlockVector3[] doors) {
        for (BlockVector3 door : doors) { if (door.getY() >= 0) { return true; }}
        return false;
    }
}
