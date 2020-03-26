package bonnett.data;

import bonnett.Main;
import bonnett.data.math.AlignedLocation;
import bonnett.data.math.DungeonMinLocation;

import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.math.BlockVector2;
import com.sk89q.worldedit.math.BlockVector3;

public class UsedChunks {
    int arraySize;
    int[][] usedChunks;
    BlockVector3 dungeonMinLocation;

    public UsedChunks(int dungeonSize, DungeonMinLocation minLocation) {
        dungeonMinLocation = minLocation.toBlockVector3();
        arraySize = dungeonSize * 2 + 3;
        usedChunks = new int[arraySize][arraySize];
        for (int i = 0; i < arraySize; i++) {
            for (int j = 0; j < arraySize; j++) {
                usedChunks[i][j] = 0;
            }
        }
    }

    public boolean isChunkOccupied(int x, int z) {
        return usedChunks[x][z] > 0;
    }

    public void printUsedChunks() {
        Main.plugin.getLogger().info("Printing used chunks array:");
        for (int i = 0; i < arraySize; i++) {
            String line = "";
            for (int j = 0; j < arraySize; j++) {
                line = line + usedChunks[i][j] + " ";
            }
            Main.plugin.getLogger().info(line);
        }
    }

    public void markUsedChunks(Clipboard clipboard, AlignedLocation alignedLocation) {
        BlockVector3 sizeInChunks = BlockVector3.at(
                clipboard.getDimensions().getX() / 16,
                clipboard.getDimensions().getY(),
                clipboard.getDimensions().getZ() / 16);
        BlockVector2 distanceFromMin = BlockVector2.at(
                dungeonMinLocation.getX() - alignedLocation.getX(),
                dungeonMinLocation.getZ() - alignedLocation.getZ());
        BlockVector2 chunkDistFromMin = distanceFromMin.divide(16);

        for (int i = 0; i < sizeInChunks.getX(); i++) {
            for (int j = 0; j < sizeInChunks.getZ(); j++) {
                usedChunks[chunkDistFromMin.getX() + i][chunkDistFromMin.getZ() + j] = 1;
            }
        }
    }

    public void markUsedChunks(Clipboard clipboard, AlignedLocation alignedLocation, int rotation) {
        switch (rotation) {
            case 0:
            case 180: {
                BlockVector3 sizeInChunks = BlockVector3.at(
                        clipboard.getDimensions().getX() / 16,
                        clipboard.getDimensions().getY(),
                        clipboard.getDimensions().getZ() / 16);
                BlockVector2 distanceFromMin = BlockVector2.at(
                        dungeonMinLocation.getX() - alignedLocation.getX(),
                        dungeonMinLocation.getZ() - alignedLocation.getZ());
                BlockVector2 chunkDistFromMin = distanceFromMin.divide(16);

                for (int i = 0; i < sizeInChunks.getX(); i++) {
                    for (int j = 0; j < sizeInChunks.getZ(); j++) {
                        usedChunks[chunkDistFromMin.getX() + i][chunkDistFromMin.getZ() + j] = 1;
                    }
                }
                break;
            }
            case 90:
            case 270: {
                BlockVector3 sizeInChunks = BlockVector3.at(
                        clipboard.getDimensions().getZ() / 16,
                        clipboard.getDimensions().getY(),
                        clipboard.getDimensions().getX() / 16);
                BlockVector2 distanceFromMin = BlockVector2.at(
                        dungeonMinLocation.getX() - alignedLocation.getX(),
                        dungeonMinLocation.getZ() - alignedLocation.getZ());
                BlockVector2 chunkDistFromMin = distanceFromMin.divide(16);

                for (int i = 0; i < sizeInChunks.getX(); i++) {
                    for (int j = 0; j < sizeInChunks.getZ(); j++) {
                        usedChunks[chunkDistFromMin.getX() + i][chunkDistFromMin.getZ() + j] = 1;
                    }
                }
                break;
            }
            default: {
                Main.plugin.getLogger().warning("Invalid rotation used for mark used chunks");
            }
        }


    }

}
