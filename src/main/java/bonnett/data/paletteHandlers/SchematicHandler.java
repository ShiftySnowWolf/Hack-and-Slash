package bonnett.data.paletteHandlers;

import bonnett.data.math.Direction;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import org.bukkit.Location;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static bonnett.Main.*;

public class SchematicHandler {
    private Random rand = new Random();
    private String paletteFolder = plugin.getDataFolder().toString() + File.separator + "dungeon_palettes" + File.separator;
    private Clipboard clip;

    //Boss room
    public SchematicHandler(String palette) {
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
        } else {
            return;
        }

        ClipboardFormat format = ClipboardFormats.findByFile(schemLocation);

        assert format != null;
        try (ClipboardReader reader = format.getReader(new FileInputStream(schemLocation))) {
            schematic = reader.read();

            if (sizeChecker(schematic, subSchem[selection])) {
                InvalidPalette.add(palette);
                return;
            }
            clip = schematic;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Standard room
    public SchematicHandler(String palette, Location loc, Direction dir) {
        String path = palette + File.separator;
        Clipboard schematic;
        String[] subSchem;
        int selection;
        //Get available space
        SpaceAvailable spaceAvailable = new SpaceAvailable(((int) loc.getX()), (int) loc.getZ(), dir);
        int roomType;
        switch (spaceAvailable.getSpace()) {
            case 0: roomType = 1; break;
            case 2: roomType = 2; break;
            case 7: roomType = 3; break;
            case 18: roomType = 4; break;
            case 31: roomType = 5; break;
            default: return;
        }
        //Decision of which type of schematic to load.
        String type;
        String subType = null;
        //Range: 0-1 >> 0 = Room | 1 = Hall
        int roomOrHall = rand.nextInt(1);
        if (roomOrHall == 0) {
            type = "rooms";
            //Range: 0-4 >> 0 = One Chunk | 1 = Two Chunk | 2 = Three Chunk | 3 = Four Chunk | 4 = Six Chunk
            roomType = rand.nextInt(roomType);
            switch (roomType) {
                case 0: subType = "one_chunk"; break;
                case 1: subType = "two_chunk"; break;
                case 2: subType = "three_chunk"; break;
                case 3: subType = "four_chunk"; break;
                case 4: subType = "six_chunk"; break;
            }
        } else {
            type = "halls";
            //Range: 0-3 >> 0 = Straight | 1 = Two Way | 2 = Three Way | 3 = Corner
            int hallType = rand.nextInt(4);
            switch (hallType) {
                case 0: subType = "straight"; break;
                case 1: subType = "two_way"; break;
                case 2: subType = "four_way"; break;
                case 3: subType = "corner"; break;
            }
        }
        path = path + type + File.separator + subType;

        //Grabs the designated schematic.
        File schemLocation = new File(paletteFolder + path);
        if (schemLocation.isDirectory()) {
            subSchem = schemLocation.list();
            assert subSchem != null;
            selection = rand.nextInt(subSchem.length);
            schemLocation = new File(schemLocation + File.separator + subSchem[selection]);
        } else { return; }

        ClipboardFormat format = ClipboardFormats.findByFile(schemLocation);

        assert format != null;
        try (ClipboardReader reader = format.getReader(new FileInputStream(schemLocation))) {
            schematic = reader.read();

            if (sizeChecker(schematic, subSchem[selection])) {
                InvalidPalette.add(palette);
                return;
            }
            clip = schematic;
        } catch (IOException e) { e.printStackTrace(); }
    }

    public Clipboard getSchematic() { return clip; }

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

        return exit;
    }
}