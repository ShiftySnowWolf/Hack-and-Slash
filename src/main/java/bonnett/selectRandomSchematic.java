package bonnett;

import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;

import java.io.File;
import java.util.Random;

public class selectRandomSchematic {

    private String tOnR = "tOnR.schem";
    private String tTwR = "tTwR.schem";
    private String tTrR = "tTrR.schem";
    private String tFoR = "tFoR.schem";
    private String tSiR = "tSiR.schem";

    private String tStH = "tStH.schem";
    private String tTwH = "tTwH.schem";
    private String tTrH = "tTrH.schem";
    private String tCoH = "tCoH.schem";

    public Clipboard selectSubRoom(String template) {
        ClipboardFormat format = null;
        Clipboard schematic = null;
        boolean isLocal = false;
        Random rand = new Random();

        //Decision of which type of schematic to load.
        String path = null;
        int roomOrHall = rand.nextInt(2); //Range: 0-1 >> 0 = Room | 1 = Hall
        int roomType; //Range: 0-4 >> 0 = One Chunk | 1 = Two Chunk | 2 = Three Chunk | 3 = Four Chunk | 4 = Six Chunk
        int hallType; //Range: 0-3 >> 0 = Straight | 1 = Two Way | 2 = Three Way | 3 = Corner
        String schem;
        String type;
        String room = null;
        String hall = null;
        if (roomOrHall == 0) {
            type = "room";
            roomType = rand.nextInt(5);
            switch (roomType) {
                case 0: { room = "one_chunk";} break;
                case 1: { room = "two_chunk"; } break;
                case 2: { room = "three_chunk"; } break;
                case 3: { room = "four_chunk"; } break;
                case 4: { room = "six_chunk"; } break;
            }
            path = type + File.separator + room;
        } else {
            type = "hall";
            hallType = rand.nextInt(4);
            switch (hallType) {
                case 0: {
                    hall = "straight";
                } break;
                case 1: { hall = "two_way"; } break;
                case 2: { hall = "four_way"; } break;
                case 3: { hall = "corner"; } break;
            }
            path = type + File.separator + hall;
        }

        //Decides where the template is located.
        String temp = template.toUpperCase();
        if (temp.equals("TEMPLATE") || temp.equals("DEFAULT_EERIE") || temp.equals("DEFAULT_MINE") || temp.equals("DEFAULT_OVERGROWTH")) { isLocal = true; }

        //Grabs a schematic from Local or External respectively.
        if (isLocal) {
            path = temp + File.separator + path;


        } else {

        }

        return schematic;
    }

    public Clipboard selectBossRoom(String template) {
        Clipboard schematic = null;

        return schematic;
    }
}
