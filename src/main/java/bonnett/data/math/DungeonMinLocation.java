package bonnett.data.math;

import com.sk89q.worldedit.math.BlockVector3;
import org.bukkit.Location;

public class DungeonMinLocation {
    Location dungeonMinLocation;

    public DungeonMinLocation(BossRoomMinLocation bossRoomMinLocation, int dungeonSize) {
        dungeonMinLocation = findDungeonMinLocation(bossRoomMinLocation.toLocation(), dungeonSize);
    }

    public Location toLocation() {
        return dungeonMinLocation;
    }

    public BlockVector3 toBlockVector3() {
        return  BlockVector3.at(dungeonMinLocation.getX(), dungeonMinLocation.getY(), dungeonMinLocation.getZ());
    }

    private Location findDungeonMinLocation(Location bossRoomMinLocation, int dungeonSize) {
        return new Location(bossRoomMinLocation.getWorld(),
                bossRoomMinLocation.getX() - (dungeonSize * 16),
                0,
                bossRoomMinLocation.getZ() - (dungeonSize * 16));
    }
}
