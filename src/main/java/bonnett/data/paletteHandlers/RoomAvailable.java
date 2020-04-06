package bonnett.data.paletteHandlers;

import static bonnett.data.UsedChunks.usedChunks;

public class RoomAvailable {
    //Old: isChunkAvailable, but should now require the chunk that just generated.
    //Now returns a specific number depending on what size room can fit in the given area.
    //Also takes the direction you are checking for.
    public int availableGenerationChunks(int x, int z, String dir) {
        if (usedChunks[x][z] == 1) { return 0; }
        switch (dir) {
            case "N": {

            } break;
            case "E": {} break;
            case "S": {} break;
            case "W": {} break;
        }
        return 0;
    }
}
