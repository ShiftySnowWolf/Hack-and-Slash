package bonnett.data.math;

import com.sk89q.worldedit.math.BlockVector3;
import org.bukkit.Location;

public class BossRoomMinLocation {
    Location bossRoomMinLocation;

    public BossRoomMinLocation(AlignedLocation location) {
        bossRoomMinLocation = location.toLocation();
    }

    public Location toLocation() {
        return bossRoomMinLocation;
    }

    public BlockVector3 toBlockVector3() {
        return BlockVector3.at(bossRoomMinLocation.getX(), bossRoomMinLocation.getY(), bossRoomMinLocation.getZ());
    }

    public int getX() {
        return bossRoomMinLocation.getBlockX();
    }

    public int getY() {
        return  bossRoomMinLocation.getBlockY();
    }

    public int getZ() {
        return  bossRoomMinLocation.getBlockZ();
    }
}
