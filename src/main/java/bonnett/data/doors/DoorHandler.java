package bonnett.data.doors;

import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.math.BlockVector3;
import org.bukkit.Location;

public abstract class DoorHandler {
    private static Location[] doors;
    private static BlockVector3[] doorsNoLoc;
    private static boolean hasDoors;

    public Location[] getDoors() { return doors; }
    public BlockVector3[] getDoorsNoLoc() { return doorsNoLoc; }
    public boolean hasDoors() { return hasDoors; }

    void setHasDoors(boolean areDoors) { hasDoors = areDoors; }
    void setDoors(Location[] allDoors) { doors = allDoors; }
    void setDoorsNoLoc(BlockVector3[] allDoorsNoLoc) { doorsNoLoc = allDoorsNoLoc; }

    abstract void findDoors(Clipboard clipboard, Location location);
    abstract void findDoorsNoLoc(Clipboard clipboard);
}