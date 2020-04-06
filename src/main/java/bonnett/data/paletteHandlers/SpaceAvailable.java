package bonnett.data.paletteHandlers;

import bonnett.data.math.Direction;

import static bonnett.data.UsedChunks.usedChunks;

public class SpaceAvailable {
    public static int availableSpace(int x, int z, Direction dir) {
        int space = 0;
        if (usedChunks[x][z] != 0) { return -1; }
        switch (dir) {
            case NORTH: {
                //Two chunk space
                if (usedChunks[x-1][z] == 0
                        || usedChunks[x][z-1] == 0
                        || usedChunks[x][z+1] == 0
                ) { space += 2; }
                //Three chunk space
                if ((usedChunks[x-1][z] == 0 && usedChunks[x-2][z] == 0)
                        || (usedChunks[x][z-1] == 0 && usedChunks[x][z-2] == 0)
                        || (usedChunks[x][z+1] == 0 && usedChunks[x][z+2] == 0)
                ) { space += 5; }
                //Four chunk space
                if ((usedChunks[x-1][z] == 0 && usedChunks[x][z-1] == 0 && usedChunks[x-1][z-1] == 0)
                        || (usedChunks[x-1][z] == 0 && usedChunks[x][z+1] == 0 && usedChunks[x-1][z+1] == 0)
                ) { space += 11; }
                //Six chunk space
                if ((usedChunks[x-1][z] == 0 && usedChunks[x-2][z] == 0 && usedChunks[x][z-1] == 0 && usedChunks[x-1][z-1] == 0 && usedChunks[x-2][z-1] == 0)
                        || (usedChunks[x-1][z] == 0 && usedChunks[x-2][z] == 0 && usedChunks[x][z+1] == 0 && usedChunks[x-1][z+1] == 0 && usedChunks[x-2][z+1] == 0)
                        || (usedChunks[x][z-1] == 0 && usedChunks[x][z+1] == 0 && usedChunks[x-1][z] == 0 && usedChunks[x-1][z-1] == 0 && usedChunks[x-1][z+1] == 0)
                        || (usedChunks[x][z-1] == 0 && usedChunks[x][z-2] == 0 && usedChunks[x-1][z] == 0 && usedChunks[x-1][z-1] == 0 && usedChunks[x-1][z-2] == 0)
                        || (usedChunks[x][z+1] == 0 && usedChunks[x][z+2] == 0 && usedChunks[x-1][z] == 0 && usedChunks[x-1][z+1] == 0 && usedChunks[x-1][z+2] == 0)
                ) { space += 23; }
            } break;
            case EAST: {
                //Two chunk space
                if (usedChunks[x][z+1] == 0
                        || usedChunks[x-1][z] == 0
                        || usedChunks[x+1][z] == 0
                ) { space += 2; }
                //Three chunk space
                if ((usedChunks[x][z+1] == 0 && usedChunks[x][z+2] == 0)
                        || (usedChunks[x-1][z] == 0 && usedChunks[x-2][z] == 0)
                        || (usedChunks[x+1][z] == 0 && usedChunks[x+2][z] == 0)
                ) { space += 5; }
                //Four chunk space
                if ((usedChunks[x][z+1] == 0 && usedChunks[x-1][z] == 0 && usedChunks[x-1][z+1] == 0)
                        || (usedChunks[x][z+1] == 0 && usedChunks[x+1][z] == 0 && usedChunks[x+1][z+1] == 0)
                ) { space += 11; }
                //Six chunk space
                if ((usedChunks[x][z+1] == 0 && usedChunks[x][z+2] == 0 && usedChunks[x-1][z] == 0 && usedChunks[x-1][z+1] == 0 && usedChunks[x-1][z+2] == 0)
                        || (usedChunks[x][z+1] == 0 && usedChunks[x][z+2] == 0 && usedChunks[x+1][z] == 0 && usedChunks[x+1][z+1] == 0 && usedChunks[x+1][z+2] == 0)
                        || (usedChunks[x-1][z] == 0 && usedChunks[x+1][z] == 0 && usedChunks[x][z+1] == 0 && usedChunks[x-1][z+1] == 0 && usedChunks[x+1][z+1] == 0)
                        || (usedChunks[x-1][z] == 0 && usedChunks[x-2][z] == 0 && usedChunks[x][z+1] == 0 && usedChunks[x-1][z+1] == 0 && usedChunks[x-2][z+1] == 0)
                        || (usedChunks[x+1][z] == 0 && usedChunks[x+2][z] == 0 && usedChunks[x][z+1] == 0 && usedChunks[x+1][z+1] == 0 && usedChunks[x+2][z+1] == 0)
                ) { space += 23; }
            } break;
            case SOUTH: {
                //Two chunk space
                if (usedChunks[x+1][z] == 0
                        || usedChunks[x][z-1] == 0
                        || usedChunks[x][z+1] == 0
                ) { space += 2; }
                //Three chunk space
                if ((usedChunks[x+1][z] == 0 && usedChunks[x+2][z] == 0)
                        || (usedChunks[x][z-1] == 0 && usedChunks[x][z-2] == 0)
                        || (usedChunks[x][z+1] == 0 && usedChunks[x][z+2] == 0)
                ) { space += 5; }
                //Four chunk space
                if ((usedChunks[x+1][z] == 0 && usedChunks[x][z-1] == 0 && usedChunks[x+1][z-1] == 0)
                        || (usedChunks[x+1][z] == 0 && usedChunks[x][z+1] == 0 && usedChunks[x+1][z+1] == 0)
                ) { space += 11; }
                //Six chunk space
                if ((usedChunks[x+1][z] == 0 && usedChunks[x+2][z] == 0 && usedChunks[x][z-1] == 0 && usedChunks[x+1][z-1] == 0 && usedChunks[x+2][z-1] == 0)
                        || (usedChunks[x+1][z] == 0 && usedChunks[x+2][z] == 0 && usedChunks[x][z+1] == 0 && usedChunks[x+1][z+1] == 0 && usedChunks[x+2][z+1] == 0)
                        || (usedChunks[x][z-1] == 0 && usedChunks[x][z+1] == 0 && usedChunks[x+1][z] == 0 && usedChunks[x+1][z-1] == 0 && usedChunks[x+1][z+1] == 0)
                        || (usedChunks[x][z-1] == 0 && usedChunks[x][z-2] == 0 && usedChunks[x+1][z] == 0 && usedChunks[x+1][z-1] == 0 && usedChunks[x+1][z-2] == 0)
                        || (usedChunks[x][z+1] == 0 && usedChunks[x][z+2] == 0 && usedChunks[x+1][z] == 0 && usedChunks[x+1][z+1] == 0 && usedChunks[x+1][z+2] == 0)
                ) { space += 23; }
            } break;
            case WEST: {
                //Two chunk space
                if (usedChunks[x][z-1] == 0
                        || usedChunks[x-1][z] == 0
                        || usedChunks[x+1][z] == 0
                ) { space += 2; }
                //Three chunk space
                if ((usedChunks[x][z-1] == 0 && usedChunks[x][z-2] == 0)
                        || (usedChunks[x-1][z] == 0 && usedChunks[x-2][z] == 0)
                        || (usedChunks[x+1][z] == 0 && usedChunks[x+2][z] == 0)
                ) { space += 5; }
                //Four chunk space
                if ((usedChunks[x][z-1] == 0 && usedChunks[x-1][z] == 0 && usedChunks[x-1][z-1] == 0)
                        || (usedChunks[x][z-1] == 0 && usedChunks[x+1][z] == 0 && usedChunks[x+1][z-1] == 0)
                ) { space += 11; }
                //Six chunk space
                if ((usedChunks[x][z-1] == 0 && usedChunks[x][z-2] == 0 && usedChunks[x-1][z] == 0 && usedChunks[x-1][z-1] == 0 && usedChunks[x-1][z-2] == 0)
                        || (usedChunks[x][z-1] == 0 && usedChunks[x][z-2] == 0 && usedChunks[x+1][z] == 0 && usedChunks[x+1][z-1] == 0 && usedChunks[x+1][z-2] == 0)
                        || (usedChunks[x-1][z] == 0 && usedChunks[x+1][z] == 0 && usedChunks[x][z-1] == 0 && usedChunks[x-1][z-1] == 0 && usedChunks[x+1][z-1] == 0)
                        || (usedChunks[x-1][z] == 0 && usedChunks[x-2][z] == 0 && usedChunks[x][z-1] == 0 && usedChunks[x-1][z-1] == 0 && usedChunks[x-2][z-1] == 0)
                        || (usedChunks[x+1][z] == 0 && usedChunks[x+2][z] == 0 && usedChunks[x][z-1] == 0 && usedChunks[x+1][z-1] == 0 && usedChunks[x+2][z-1] == 0)
                ) { space += 23; }
            } break;
        }
        return space;
    }
}
