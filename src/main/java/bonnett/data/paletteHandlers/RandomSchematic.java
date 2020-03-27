package bonnett.data.paletteHandlers;

import bonnett.Main;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class RandomSchematic {
    
    private Main plugin = Main.plugin;
    private String[] invalid = Main.invalidPalettes;
    private String[] blacklist = Main.blacklist;
    private Random rand = new Random();
    private String paletteFolder = plugin.getDataFolder().toString() + File.separator + "dungeon_palettes" + File.separator;

    public Clipboard getNext(String palette, boolean isBoss) {
        Clipboard returnSchem;
        
        if (isBoss) {
            returnSchem = selectBossRoom(palette, 0);
        } else {
            
            //Decision of which type of schematic to load.
            String path;
            int roomOrHall = rand.nextInt(1); //Range: 0-1 >> 0 = Room | 1 = Hall
            int roomType; //Range: 0-4 >> 0 = One Chunk | 1 = Two Chunk | 2 = Three Chunk | 3 = Four Chunk | 4 = Six Chunk
            int hallType; //Range: 0-3 >> 0 = Straight | 1 = Two Way | 2 = Three Way | 3 = Corner
            String type;
            String subType = null;
            if (roomOrHall == 0) {
                type = "rooms";
                roomType = rand.nextInt(2);
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
            path = palette + File.separator + type + File.separator + subType;
            returnSchem =  selectSubRoom(palette, path, 0);
        }
        
        return returnSchem;
    }
    
    private Clipboard selectSubRoom(String palette, String path, int tries) {
        if (tries >= 4) {
            plugin.getLogger().warning("Too many invalid schematics in the '" + palette.toUpperCase() + "' palette!\n" +
                    "Disabling this palette! Please make sure your schematics are divisible by 16 on X and Z planes.");
            return null;
        }

        Clipboard schematic;
        String[] subSchem;
        int selection;

        //Grabs the designated schematic.
        File schemLocation = new File(paletteFolder + path);
        if (schemLocation.isDirectory()) {
            subSchem = schemLocation.list();
            assert subSchem != null;
            selection = rand.nextInt(subSchem.length);
            schemLocation = new File(schemLocation + File.separator + subSchem[selection]);
        } else {
            return null;
        }

        ClipboardFormat format = ClipboardFormats.findByFile(schemLocation);

        assert format != null;
        try (ClipboardReader reader = format.getReader(new FileInputStream(schemLocation))) {
            schematic = reader.read();

            if (Arrays.asList(blacklist).contains(subSchem[selection]) || sizeChecker(schematic, subSchem[selection])) {
                selectSubRoom(palette, path, tries + 1);
            }

            return schematic;
        } catch (IOException e) { e.printStackTrace(); }

        return null;
    }

    private Clipboard selectBossRoom(String palette, int tries) {
        if (tries >= 4) {
            System.err.println("Too many invalid schematics in the '" + palette.toUpperCase() + "' palette!");
            return null;
        }

        String path = palette + File.separator + "rooms" + File.separator + "boss";
        Clipboard schematic;
        String[] subSchem;
        int selection;

        //Grabs the designated schematic.
        File schemLocation = new File(paletteFolder + path);
        if (schemLocation.isDirectory()) {
            subSchem = schemLocation.list();
            assert subSchem != null;
            selection = rand.nextInt(subSchem.length);
            schemLocation = new File(schemLocation + File.separator + subSchem[selection]);
        } else { return null; }

        ClipboardFormat format = ClipboardFormats.findByFile(schemLocation);

        assert format != null;
        try (ClipboardReader reader = format.getReader(new FileInputStream(schemLocation))) {
            schematic = reader.read();

            if (Arrays.asList(blacklist).contains(subSchem[selection]) || sizeChecker(schematic, subSchem[selection])) {
                selectBossRoom(palette, tries + 1);
            }

            return schematic;
        } catch (IOException e) { e.printStackTrace(); }

        return null;
    }

    private boolean sizeChecker(Clipboard clipboard, String selected) {
        boolean exit = false;

        double checkX = (double) clipboard.getDimensions().getX() / 16;
        double checkZ = (double) clipboard.getDimensions().getZ() / 16;
        if ((checkX % 1) == 0 && (checkZ % 1) == 0) { exit = true; }
        if (!exit) {
            List<String> addBlacklist = Arrays.asList(blacklist);
            addBlacklist.add(selected);
            blacklist = addBlacklist.toArray(String[]::new);
        }

        return !exit;
    }
}