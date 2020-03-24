package bonnett.data;

import bonnett.Main;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class selectRandomSchematic {
    
    private Main plugin = Main.plugin;
    private Random rand = new Random();
    private String schem;
    
    public Clipboard getNext(String template, boolean isBoss) throws IOException {
        //ClipboardFormat format = null;
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
            returnSchem =  selectSubRoom(template, isLocal, type, subType, path);
        }
        
        return returnSchem;
    }
    
    private Clipboard selectSubRoom(String template, boolean isLocal, String type, String subType, String path) throws IOException {
        ClipboardFormat format = null;
        Clipboard schematic;
        
        
        //Grabs a schematic from Local or External respectively.
        if (isLocal) {
            path = "dungeon_palettes" + File.separator + template + type + File.separator + subType;
            List<?> schemList;
            Object[] selectRand;
            int listSize;
            int list;
            switch(template.toUpperCase()) {
                case "TEMPLATE": {
                    schemList = new ArrayList<>(
                    Objects.requireNonNull(plugin.templateYML.getList(type + "s." + subType)));
                    listSize = schemList.size();
                    list = rand.nextInt(listSize + 1);
                    selectRand = new String[listSize];
                    schemList.toArray(selectRand);
                    
                    schem = (String) selectRand[list];
                    path = path + File.separator + schem;
                    assert false;
                    try (ClipboardReader reader = format.getReader(plugin.getResource(path))) {
                        schematic = reader.read();
                        return schematic;
                    }
                }
                
                case "DEFAULT_EERIE": {
                    schemList = new ArrayList<>(
                    Objects.requireNonNull(plugin.defaultEerieYML.getList(type + "s." + subType)));
                    listSize = schemList.size();
                    list = rand.nextInt(listSize + 1);
                    selectRand = new String[listSize];
                    schemList.toArray(selectRand);
                    
                    schem = (String) selectRand[list];
                    path = path + File.separator + schem;
                    assert false;
                    try (ClipboardReader reader = format.getReader(plugin.getResource(path))) {
                        schematic = reader.read();
                        return schematic;
                    }
                }
                
                case "DEFAULT_MINE": {
                    schemList = new ArrayList<>(
                    Objects.requireNonNull(plugin.defaultMineYML.getList(type + "s." + subType)));
                    listSize = schemList.size();
                    list = rand.nextInt(listSize + 1);
                    selectRand = new String[listSize];
                    schemList.toArray(selectRand);
                    
                    schem = (String) selectRand[list];
                    path = path + File.separator + schem;
                    assert false;
                    try (ClipboardReader reader = format.getReader(plugin.getResource(path))) {
                        schematic = reader.read();
                        return schematic;
                    }
                }
                
                case "DEFAULT_OVERGROWTH": {
                    schemList = new ArrayList<>(
                    Objects.requireNonNull(plugin.defaultOvergrowthYML.getList(type + "s." + subType)));
                    listSize = schemList.size();
                    list = rand.nextInt(listSize + 1);
                    selectRand = new String[listSize];
                    schemList.toArray(selectRand);
                    
                    schem = (String) selectRand[list];
                    path = path + File.separator + schem;
                    assert false;
                    try (ClipboardReader reader = format.getReader(plugin.getResource(path))) {
                        schematic = reader.read();
                        return schematic;
                    }
                }
            }
            
            
        } else {
            path = plugin.getDataFolder().toString() + File.separator + "dungeon_templates" + path;
        }
        
        return null;
    }
    
    private Clipboard selectBossRoom(String template, boolean isLocal) throws IOException {
        ClipboardFormat format = null;
        Clipboard schematic;
        
        if (isLocal) {
            String path = "dungeon_palettes" + File.separator + template + "room" + File.separator + "boss";
            List<?> schemList;
            Object[] selectRand;
            int listSize;
            int list;
            
            switch(template.toUpperCase()) {
                case "TEMPLATE": {
                    schemList = new ArrayList<>(
                    Objects.requireNonNull(plugin.templateYML.getList("rooms.boss")));
                    listSize = schemList.size();
                    list = rand.nextInt(listSize + 1);
                    selectRand = new String[listSize];
                    schemList.toArray(selectRand);
                    
                    schem = (String) selectRand[list];
                    path = path + File.separator + schem;
                    assert false;
                    try (ClipboardReader reader = format.getReader(plugin.getResource(path))) {
                        schematic = reader.read();
                        return schematic;
                    }
                }
                
                case "DEFAULT_EERIE": {
                    schemList = new ArrayList<>(
                    Objects.requireNonNull(plugin.defaultEerieYML.getList("rooms.boss")));
                    listSize = schemList.size();
                    list = rand.nextInt(listSize + 1);
                    selectRand = new String[listSize];
                    schemList.toArray(selectRand);
                    
                    schem = (String) selectRand[list];
                    path = path + File.separator + schem;
                    assert false;
                    try (ClipboardReader reader = format.getReader(plugin.getResource(path))) {
                        schematic = reader.read();
                        return schematic;
                    }
                }
                
                case "DEFAULT_MINE": {
                    schemList = new ArrayList<>(
                    Objects.requireNonNull(plugin.defaultMineYML.getList("rooms.boss")));
                    listSize = schemList.size();
                    list = rand.nextInt(listSize + 1);
                    selectRand = new String[listSize];
                    schemList.toArray(selectRand);
                    
                    schem = (String) selectRand[list];
                    path = path + File.separator + schem;
                    assert false;
                    try (ClipboardReader reader = format.getReader(plugin.getResource(path))) {
                        schematic = reader.read();
                        return schematic;
                    }
                }
                
                case "DEFAULT_OVERGROWTH": {
                    schemList = new ArrayList<>(
                    Objects.requireNonNull(plugin.defaultOvergrowthYML.getList("rooms.boss")));
                    listSize = schemList.size();
                    list = rand.nextInt(listSize + 1);
                    selectRand = new String[listSize];
                    schemList.toArray(selectRand);
                    
                    schem = (String) selectRand[list];
                    path = path + File.separator + schem;
                    assert false;
                    try (ClipboardReader reader = format.getReader(plugin.getResource(path))) {
                        schematic = reader.read();
                        return schematic;
                    }
                }
            }
        } else {
            System.out.println("Selected external palette.");
        }
        return null;
    }
}
