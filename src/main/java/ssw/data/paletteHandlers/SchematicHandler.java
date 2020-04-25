package ssw.data.paletteHandlers;

import ssw.commands.Reload;
import ssw.data.enums.Direction;
import ssw.data.enums.Orientation;
import ssw.data.enums.RoomType;
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

import static ssw.Main.*;
import static ssw.data.UsedChunks.usedChunks;

public class SchematicHandler {
    private Random rand = new Random();
    private String paletteFolder = plugin.getDataFolder().toString() + File.separator + "dungeon_palettes" + File.separator;
    private Clipboard clip;
    private RoomType roomReturn;
    private Orientation orientation;

    // The constructor that gets a new boss room.
    public SchematicHandler(String palette) {
        String path = palette + File.separator + "rooms" + File.separator + "boss";
        Clipboard schematic;
        String[] subSchem;
        int selection;

        // Grabs a random boss room from the requested palette.
        File schemLocation = new File(paletteFolder + path);
        if (schemLocation.isDirectory()) {
            subSchem = schemLocation.list();
            assert subSchem != null;
            selection = rand.nextInt(subSchem.length);
            schemLocation = new File(schemLocation + File.separator + subSchem[selection]);
        } else { return; } // Returns if there are no boss rooms in the requested palette.

        ClipboardFormat format = ClipboardFormats.findByFile(schemLocation);

        assert format != null;
        try (ClipboardReader reader = format.getReader(new FileInputStream(schemLocation))) {
            schematic = reader.read();

            if (sizeChecker(schematic, subSchem[selection])) {
                InvalidPalette.add(palette);
                return; // Returns if the schematic isn't divisible by a chunk.
            }
            clip = schematic;
            roomReturn = RoomType.SMALL_BOSS;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // The constructor that gets a new room or a hallway.
    public SchematicHandler(String palette, Location loc, Direction dir) {
        String path = palette + File.separator;
        Clipboard schematic;
        String[] subSchem;
        int selection;
        //Get available space for the next schematic.
        int spaceAvailable = getSpace(((int) loc.getX()), (int) loc.getZ(), dir);
        int roomType;
        switch (spaceAvailable) {
            case 0: roomType = 1; break;
            case 2: roomType = 2; break;
            case 7: roomType = 3; break;
            case 18: roomType = 4; break;
            case 31: roomType = 5; break;
            default: return;
        }

        // Randomizes the type of schematic to generate.
        String type;
        String subType = null;
        // Range: 0-1 >> 0 = Room | 1 = Hall
        int roomOrHall = rand.nextInt(1);
        if (roomOrHall == 0) {
            type = "rooms";
            //Range: 0-4 >> 0 = One Chunk | 1 = Two Chunk | 2 = Three Chunk | 3 = Four Chunk | 4 = Six Chunk
            roomType = rand.nextInt(1); // Change to: 'roomType' whe ready to accept all room types.
            switch (roomType) {
                case 0: subType = "one_chunk"; roomReturn = RoomType.ONE_CHUNK; break;
                case 1: subType = "two_chunk"; roomReturn = RoomType.TWO_CHUNK; break;
                case 2: subType = "three_chunk"; roomReturn = RoomType.THREE_CHUNK; break;
                case 3: subType = "four_chunk"; roomReturn = RoomType.FOUR_CHUNK; break;
                case 4: subType = "six_chunk"; roomReturn = RoomType.SIX_CHUNK; break;
            }
        } else {
            type = "halls";
            //Range: 0-3 >> 0 = Straight | 1 = Two Way | 2 = Three Way | 3 = Corner
            int hallType = rand.nextInt(4);
            switch (hallType) {
                case 0: subType = "straight"; roomReturn = RoomType.STRAIGHT_HALL; break;
                case 1: subType = "three_way"; roomReturn = RoomType.THREE_WAY_HALL; break;
                case 2: subType = "four_way"; roomReturn = RoomType.FOUR_WAY_HALL; break;
                case 3: subType = "corner"; roomReturn = RoomType.CORNER_HALL; break;
            }
        }
        path = path + type + File.separator + subType;

        // Grabs the a random schematic from the randomized path.
        File saveLoc = new File(paletteFolder + path);
        if (saveLoc.isDirectory()) {
            subSchem = saveLoc.list();
            assert subSchem != null;
            selection = rand.nextInt(subSchem.length);
            saveLoc = new File(saveLoc + File.separator + subSchem[selection]);
        } else { return; }

        ClipboardFormat format = ClipboardFormats.findByFile(saveLoc);

        assert format != null;
        try (ClipboardReader reader = format.getReader(new FileInputStream(saveLoc))) {
            schematic = reader.read();

            if (sizeChecker(schematic, subSchem[selection])) {
                InvalidPalette.add(palette);
                return;
            }
            switch (roomReturn) {
                case TWO_CHUNK:
                case THREE_CHUNK:
                case SIX_CHUNK: oblongOrientation(schematic, dir); break;
                default: clip = schematic;
            }
        } catch (IOException e) { e.printStackTrace(); }
    }

    public Clipboard getSchematic() { return clip; }
    public RoomType getRoom() { return roomReturn; }
    public Orientation getOrientation() { return orientation; }

    // Checks if the schematic is evenly divisible by a chunk.
    private boolean sizeChecker(Clipboard clipboard, String palette) {
        double checkX = (double) clipboard.getDimensions().getX() / 16;
        double checkZ = (double) clipboard.getDimensions().getZ() / 16;
        if ((checkX % 1) == 0 && (checkZ % 1) == 0) { return true;
        } else {
            List<String> addBlacklist = Arrays.asList(invalidPalettes);
            addBlacklist.add(palette);
            invalidPalettes = addBlacklist.toArray(String[]::new);
            new Reload().palettes();
            return false;
        }
    }

    private void oblongOrientation(Clipboard clip, Direction dir) {
        double z = (double) clip.getDimensions().getZ() / 16;
        double x = (double) clip.getDimensions().getX() / 16;
        if (z > x) { orientation = Orientation.NS;
        } else if (z < x) { orientation = Orientation.WE;
        } else { orientation = Orientation.SQUARE; }
        switch (dir) {
            case NORTH:
            case SOUTH:
                switch (orientation) {
                    case NS: orientation = Orientation.LONG; break;
                    case WE: orientation = Orientation.WIDE; break;
                } break;
            case EAST:
            case WEST:
                switch (orientation) {
                    case NS: orientation = Orientation.WIDE; break;
                    case WE: orientation = Orientation.LONG; break;
                } break;
        }
    }

    // Check generating area in chunks for what rooms can be generated there.
    private int getSpace(int x, int z, Direction dir) {
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