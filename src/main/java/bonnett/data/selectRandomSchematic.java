package bonnett.data;

import bonnett.Main;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;

public class selectRandomSchematic {
    
    private Main plugin = Main.plugin;
    private Random rand = new Random();

    public Clipboard getNext(String template, boolean isBoss) throws IOException {
        Clipboard returnSchem;
        boolean isLocal = false;
        String temp = template.toUpperCase();
        
        if (temp.equals("TEMPLATE") || temp.equals("DEFAULT_EERIE") || temp.equals("DEFAULT_MINE") || temp.equals("DEFAULT_OVERGROWTH")) { isLocal = true; }
        
        if (isBoss) {
            returnSchem = selectBossRoom(template, isLocal);
        } else {
            
            //Decision of which type of schematic to load.
            String path;
            int roomOrHall = rand.nextInt(2); //Range: 0-1 >> 0 = Room | 1 = Hall
            int roomType; //Range: 0-4 >> 0 = One Chunk | 1 = Two Chunk | 2 = Three Chunk | 3 = Four Chunk | 4 = Six Chunk
            int hallType; //Range: 0-3 >> 0 = Straight | 1 = Two Way | 2 = Three Way | 3 = Corner
            String type;
            String subType = null;
            if (roomOrHall == 0) {
                type = "room";
                roomType = rand.nextInt(5);
                switch (roomType) {
                    case 0: { subType = "one_chunk";} break;
                    case 1: { subType = "two_chunk"; } break;
                    case 2: { subType = "three_chunk"; } break;
                    case 3: { subType = "four_chunk"; } break;
                    case 4: { subType = "six_chunk"; } break;
                }
            } else {
                type = "hall";
                hallType = rand.nextInt(4);
                switch (hallType) {
                    case 0: { subType = "straight"; } break;
                    case 1: { subType = "two_way"; } break;
                    case 2: { subType = "four_way"; } break;
                    case 3: { subType = "corner"; } break;
                }
            }
            path = template + File.separator + type + File.separator + subType;
            returnSchem =  selectSubRoom(template, type, subType, path);
        }
        
        return returnSchem;
    }
    
    private Clipboard selectSubRoom(String template, String type, String subType, String path) throws IOException {
        Clipboard schematic;

        //Grabs the designated schematic.
        
        return null;
    }
    
    private Clipboard selectBossRoom(String template, boolean isLocal) throws IOException {
        ClipboardFormat format = null;
        Clipboard schematic;

            String path = "dungeon_palettes" + File.separator + template + "room" + File.separator + "boss";
            List<?> schemList;
            Object[] selectRand;
            int listSize;
            int list;

            String schem;
        return null;
    }
}
