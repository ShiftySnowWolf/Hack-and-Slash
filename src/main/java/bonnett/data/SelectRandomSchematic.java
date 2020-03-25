package bonnett.data;

import bonnett.Main;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Random;

public class SelectRandomSchematic {
    
    private Main plugin = Main.plugin;
    private Random rand = new Random();
    private String paletteFolder = plugin.getDataFolder().toString() + File.separator + "dungeon_palettes" + File.separator;

    public Clipboard getNext(String template, boolean isBoss) throws IOException {
        Clipboard returnSchem;
        boolean isLocal = false;
        String temp = template.toUpperCase();
        
        if (temp.equals("TEMPLATE") || temp.equals("DEFAULT_EERIE") || temp.equals("DEFAULT_MINE") || temp.equals("DEFAULT_OVERGROWTH")) { isLocal = true; }
        
        if (isBoss) {
            returnSchem = selectBossRoom(template);
        } else {
            
            //Decision of which type of schematic to load.
            String path;
            int roomOrHall = rand.nextInt(2); //Range: 0-1 >> 0 = Room | 1 = Hall
            int roomType; //Range: 0-4 >> 0 = One Chunk | 1 = Two Chunk | 2 = Three Chunk | 3 = Four Chunk | 4 = Six Chunk
            int hallType; //Range: 0-3 >> 0 = Straight | 1 = Two Way | 2 = Three Way | 3 = Corner
            String type;
            String subType = null;
            if (roomOrHall == 0) {
                type = "rooms";
                roomType = rand.nextInt(5);
                switch (roomType) {
                    case 0: { subType = "one_chunk";} break;
                    case 1: { subType = "two_chunk"; } break;
                    case 2: { subType = "three_chunk"; } break;
                    case 3: { subType = "four_chunk"; } break;
                    case 4: { subType = "six_chunk"; } break;
                }
            } else {
                type = "halls";
                hallType = rand.nextInt(4);
                switch (hallType) {
                    case 0: { subType = "straight"; } break;
                    case 1: { subType = "two_way"; } break;
                    case 2: { subType = "four_way"; } break;
                    case 3: { subType = "corner"; } break;
                }
            }
            path = template + File.separator + type + File.separator + subType;
            returnSchem =  selectSubRoom(path);
        }
        
        return returnSchem;
    }
    
    private Clipboard selectSubRoom(String path) {
        Clipboard schematic;

        //Grabs the designated schematic.
        File schemLocation = new File(paletteFolder + path);
        if (schemLocation.isDirectory()) {
            String[] subSchem = schemLocation.list();
            assert subSchem != null;
            int selection = rand.nextInt(subSchem.length + 1);
            schemLocation = new File(schemLocation + File.separator + subSchem[selection]);
        } else { return null; }

        ClipboardFormat format = ClipboardFormats.findByFile(schemLocation);

        assert format != null;
        try (ClipboardReader reader = format.getReader(new FileInputStream(schemLocation))) {
            schematic = reader.read();
            return schematic;
        } catch (IOException e) { e.printStackTrace(); }

        return null;
    }
    
    private Clipboard selectBossRoom(String template) throws IOException {
        String path = template + File.separator + "rooms" + File.separator + "boss";
        Clipboard schematic;

        //Grabs the designated schematic.
        File schemLocation = new File(paletteFolder + path);
        if (schemLocation.isDirectory()) {
            String[] subSchem = schemLocation.list();
            assert subSchem != null;
            int selection = rand.nextInt(subSchem.length + 1);
            schemLocation = new File(schemLocation + File.separator + subSchem[selection]);
        } else { return null; }

        ClipboardFormat format = ClipboardFormats.findByFile(schemLocation);

        assert format != null;
        try (ClipboardReader reader = format.getReader(new FileInputStream(schemLocation))) {
            schematic = reader.read();
            return schematic;
        } catch (IOException e) { e.printStackTrace(); }

        return null;
    }
}
