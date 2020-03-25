package bonnett.commands;

import bonnett.Main;
import bonnett.data.Doors;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector2;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.math.transform.AffineTransform;
import com.sk89q.worldedit.session.ClipboardHolder;
import com.sk89q.worldedit.world.block.BlockState;
import com.sk89q.worldedit.world.block.BlockType;
import org.bukkit.Location;
import org.bukkit.block.CommandBlock;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Generate {
    
    public Location alignedLoc;
    public BlockVector3 absMinLocation;
    public int arraySize;
    
    private String[] types = Main.validPalettes;
    private File paletteList = Main.paletteList;
    private File typePath;

    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        String type = strings[0];
        typePath = null;
        int size;
        Location loc;
        // Type folder location finder.
        for (String t : types) {
            if (t.compareToIgnoreCase(type) == 0) {
                typePath = new File(paletteList + "\\" + t);
            } else {
                commandSender.sendMessage("There is no dungeon type called: " + type.toUpperCase());
                return false;
            }
        }
        // Size integer parsing.
        try {
            size = Integer.parseInt(strings[1]);
            if (size < 1) {
                commandSender.sendMessage("Size must be greater than 0!");
                return false;
            }
        } catch (NumberFormatException e) {
            commandSender.sendMessage("Size entered was not a whole number!");
            return false;
        }
        // Sender type detection and location getter.
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            loc = player.getLocation();
            alignedLoc = new Location(loc.getWorld(), loc.getChunk().getX() * 16, loc.getY(),
            loc.getChunk().getZ() * 16);
        } else if (commandSender instanceof CommandBlock) {
            CommandBlock commBlock = (CommandBlock) commandSender;
            loc = commBlock.getLocation();
            alignedLoc = new Location(loc.getWorld(), loc.getChunk().getX() * 16, loc.getY(),
            loc.getChunk().getZ() * 16);
        } else {
            commandSender.sendMessage("This command cannot be run from console!");
            return false;
        }
        
        try {
            return generateDungeon(size, alignedLoc);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return false;
    }
    
    public boolean generateDungeon(int size, Location loc) throws IOException {
        arraySize = size * 2 + 3;
        int[][] usedChunks = new int[arraySize][arraySize];
        for (int i = 0; i < arraySize; i++) {
            for (int j = 0; j < arraySize; j++) {
                usedChunks[i][j] = 0;
            }
        }
        printUsedChunks(usedChunks);
        // Generate boss room. <THIS IS AN UNFINISHED CODE BLOCK>
        String extension = ".schem";
        File bossRoom = new File(typePath + "\\rooms\\boss\\bossRoom" + extension);
        System.out.println(bossRoom);
        ClipboardFormat format = ClipboardFormats.findByFile(bossRoom);
        assert format != null;
        Clipboard clipboard;
        try (ClipboardReader reader = format.getReader(new FileInputStream(bossRoom))) {
            clipboard = reader.read();
        }
        BlockVector3 copyLoc = clipboard.getOrigin();
        BlockVector3 cornerMin = clipboard.getRegion().getMinimumPoint();
        BlockVector3 offset = BlockVector3.at(
        cornerMin.getX() - copyLoc.getX(),
        cornerMin.getY() - copyLoc.getY(),
        cornerMin.getZ() - copyLoc.getZ());
        BlockVector3 adjLoc = BlockVector3.at(
        loc.getX() - offset.getX(),
        loc.getY() - offset.getY(),
        loc.getZ() - offset.getZ());
        absMinLocation = BlockVector3.at(
        loc.getX() + (size * 16), 
        loc.getY(), 
        loc.getZ() + (size * 16));

        try (EditSession editSession = WorldEdit.getInstance().getEditSessionFactory()
        .getEditSession(new BukkitWorld(loc.getWorld()), -1)) {
            Operation operation = new ClipboardHolder(clipboard).createPaste(editSession)
            .to(adjLoc)
            // Configure here.
            .build();

            Operations.complete(operation);
            usedChunks = markUsedChunks(clipboard, loc, absMinLocation, usedChunks);
            printUsedChunks(usedChunks);
            Doors doors = new Doors(clipboard, loc);
            if (hasNorthDoors(clipboard)) {
                Location doorLoc;
                Location[] northDoors = doors.getNorthDoors();
                for (int i = 0; i < northDoors.length; i++) {
                    if (northDoors[i].getY() > -1) {
                        doorLoc = new Location(loc.getWorld(), loc.getX() + (8 + (16 * i)), loc.getY() + northDoors[i].getY(), loc.getZ());
                        generateNorthRoom(typePath, doorLoc, usedChunks);
                    }
                }
            } else if (hasSouthDoors(clipboard)) {
                
            }
        } catch (WorldEditException e) {
            e.printStackTrace();
            return false;
        }
        // <END OF UNFINISHED CODE BLOCK>'
        return true;
    }
    
    public int[][] generateNorthRoom(File typePath, Location doorLoc, int[][] usedChunks) throws IOException {
        String extension = ".schem";
        File room = new File(typePath + "\\rooms\\one_chunk\\testRoom" + extension);
        Location transLoc;
        Clipboard clipboard;
        ClipboardFormat format = ClipboardFormats.findByFile(room);
        try (ClipboardReader reader = format.getReader(new FileInputStream(room))) {
            clipboard = reader.read();
        }
        BlockVector3 dim = clipboard.getDimensions();
        Location loc = doorLoc.subtract(8 + dim.getX() - 16, 0, dim.getZ());

        int rotation, doorHeight = 0;
        int[] doors;
        if (hasNorthDoors(clipboard)) {
            System.out.println("Found north doors");
            rotation = 180;
            doors = getNorthDoors(clipboard);
            for (int i = 0; i < doors.length; i++) {
                if (doors[i] > -1) {
                    doorHeight = doors[i];
                    break;
                }
            }
            transLoc = loc.add(dim.getX(), -1 * doorHeight, dim.getZ());
            
        } else if (hasSouthDoors(clipboard)) {
            System.out.println("Found south doors");
            rotation = 0;
            doors = getSouthDoors(clipboard);
            for (int i = 0; i < doors.length; i++) {
                if (doors[i] > -1) {
                    doorHeight = doors[i];
                    break;
                }
            }
            transLoc = loc.add(0, -1 * doorHeight, 0);
        } else if (hasEastDoors(clipboard)) {
            System.out.println("Found east doors");
            rotation = 90;
            doors = getEastDoors(clipboard);
            for (int i = 0; i < doors.length; i++) {
                if (doors[i] > -1) {
                    doorHeight = doors[i];
                    break;
                }
            }
            transLoc = loc.add(dim.getX(), -1 * doorHeight, 0);
        } else if (hasWestDoors(clipboard)) {
            System.out.println("Found west doors");
            rotation = -90;
            doors = getWestDoors(clipboard);
            int i;
            for (i = 0; i < doors.length; i++) {
                if (doors[i] > -1) {
                    doorHeight = doors[i];
                    break;
                }
            }
            transLoc = loc.add(16 * i, -1 * doorHeight, dim.getZ());
        } else {
            System.err.println("File doesn't have a valid door!");
            return usedChunks;
        }
        System.out.println("transLoc: " + transLoc.toString());
        
        BlockVector3 copyLoc = clipboard.getOrigin();
        BlockVector3 cornerMin = clipboard.getRegion().getMinimumPoint();
        BlockVector3 offset = BlockVector3.at(
        cornerMin.getX() - copyLoc.getX(), 
        cornerMin.getY() - copyLoc.getY(), 
        cornerMin.getZ() - copyLoc.getZ());
        BlockVector3 adjLoc = BlockVector3.at(
        transLoc.getX() - offset.getX(),
        transLoc.getY() - offset.getY(),
        transLoc.getZ() - offset.getZ());
        System.out.println("adjLoc: " + adjLoc.toString());
        
        ClipboardHolder holder = new ClipboardHolder(clipboard);
        holder.setTransform(new AffineTransform().rotateY(rotation));

        try (EditSession editSession = WorldEdit.getInstance().getEditSessionFactory()
        .getEditSession(new BukkitWorld(loc.getWorld()), -1)) {
            Operation operation = holder.createPaste(editSession).to(adjLoc).build();
            
            Operations.complete(operation);
            System.out.println("Dimensions: " + clipboard.getDimensions().toString());
            usedChunks = markUsedChunks(clipboard, loc, absMinLocation, usedChunks);
            printUsedChunks(usedChunks);
        } catch (WorldEditException e) {
            e.printStackTrace();
            
        }
        
        return usedChunks;
    }
    
    /**
    * @param clipboard
    * Clipboard containing the .schem being added to the dungeon
    * @param loc
    * Location at lowest corner of the region
    * @param absMinLocation
    * Location at the lowest possible corner of the dungeon
    * @param usedChunks
    * Array of usedChunks to append new used chunks to
    * @return
    * int[][] of usedChunks after appending, 0 = unused, 1 = used
    */
    
    public int[][] markUsedChunks(Clipboard clipboard, Location loc, BlockVector3 absMinLocation, int[][] usedChunks) {
        BlockVector3 sizeInChunks = BlockVector3.at(
        clipboard.getDimensions().getX() / 16,
        clipboard.getDimensions().getY(),
        clipboard.getDimensions().getZ() / 16);
        BlockVector2 distanceFromMin = BlockVector2.at(
        absMinLocation.getX() - loc.getX(), 
        absMinLocation.getZ() - loc.getZ());
        BlockVector2 chunkDistFromMin = distanceFromMin.divide(16);

        for (int i = 0; i < sizeInChunks.getX(); i++) {
            for (int j = 0; j < sizeInChunks.getZ(); j++) {
                usedChunks[chunkDistFromMin.getX() + i][chunkDistFromMin.getZ() + j] = 1;
            }
        }
        return usedChunks;
    }
    
    /**
    * @param usedChunks
    * int[][] of all used chunks
    * @param x
    * Chunk X coord to check
    * @param z
    * Chunk Z coord to check
    * @return
    * True if occupied, False if available
    */
    
    public boolean isChunkOccupied(int[][] usedChunks, int x, int z) {
        return usedChunks[x][z] > 0;
    }
    
    /**
    * Prints used chunks to console
    * @param usedChunks
    * Array to mark new used chunks onto
    */
    
    public void printUsedChunks(int[][] usedChunks) {
        System.out.println("Printing used chunks array:");
        for (int i = 0; i < arraySize; i++) {
            String line = "";
            for (int j = 0; j < arraySize; j++) {
                line = line + usedChunks[i][j] + " ";
            }
            System.out.println(line);
        }
    }

    /**
     * Checks if clipboard has north doors
     * @param clipboard
     * Clipboard containing schematic to check
     * @return
     * True if has north doors, False if not
     */

    public boolean hasNorthDoors(Clipboard clipboard) {
        int[] doors = getNorthDoors(clipboard);
        for (int i = 0; i < doors.length; i++) {
            if (doors[i] > -1) {
                return true;
            }
        }
        return false;
    }

    /**
    * Checks if clipboard has south doors
    * @param clipboard
    * Clipboard containing schematic to check
    * @return
    * True if has south doors, False if not
    */

    public boolean hasSouthDoors(Clipboard clipboard) {
        int[] doors = getSouthDoors(clipboard);
        for (int i = 0; i < doors.length; i++) {
            if (doors[i] > -1) {
                return true;
            }
        }
        return false;
    }

    /**
    * Checks if clipboard has west doors
    * @param clipboard
    * Clipboard containing schematic to check
    * @return
    * True if has west doors, False if not
    */

    public boolean hasWestDoors(Clipboard clipboard) {
        int[] doors = getWestDoors(clipboard);
        for (int i = 0; i < doors.length; i++) {
            if (doors[i] > -1) {
                return true;
            }
        }
        return false;
    }

    /**
    * Checks if clipboard has east doors
    * @param clipboard
    * Clipboard containing schematic to check
    * @return
    * True if has east doors, False if not
    */

    public boolean hasEastDoors(Clipboard clipboard) {
        int[] doors = getEastDoors(clipboard);
        for (int i = 0; i < doors.length; i++) {
            if (doors[i] > -1) {
                return true;
            }
        }
        return false;
    }

    /**
    * @param clipboard
    * Clipboard that contains the schematic to check
    * @return
    * int[] containing doors in order of lowest X to highest X, where
    * -1 = No door; > -1 == Y offset of door
    */

    public int[] getNorthDoors(Clipboard clipboard) {
        int arraySize = clipboard.getDimensions().getX() / 16;
        int[] doorLocations = new int[arraySize];
        int clipboardHeight = clipboard.getDimensions().getY();
        BlockVector3 cornerMin = clipboard.getRegion().getMinimumPoint();
        int xOffset = 8;
        int xLocation = cornerMin.getX() + xOffset;
        int yLocation = cornerMin.getY();
        int zLocation = cornerMin.getZ();

        // First run offests 8. Subsequent runs offset 16.
        for (int i = 0; i < arraySize; i++) {
            doorLocations[i] = -1;
            for (int startingYLocation = cornerMin.getY(); yLocation < startingYLocation + clipboardHeight; yLocation++) {
                BlockVector3 checkLoc = BlockVector3.at(xLocation, yLocation, zLocation);
                BlockState checkBlock = clipboard.getBlock(checkLoc);
                if (checkBlock.getBlockType().equals(new BlockType("minecraft:iron_block"))) {
                    doorLocations[i] = yLocation - startingYLocation;
                    break;
                }
            }
            xLocation += 16;
            yLocation = cornerMin.getY();
        }
        return doorLocations;
    }

    /**
    * @param clipboard
    * Clipboard that contains the schematic to check.
    * @return
    * int[] containing doors in order of lowest X to highest X, where
    * -1 = No door; > -1 == Y offset of door
    */

    public int[] getSouthDoors(Clipboard clipboard) {
        int arraySize = clipboard.getDimensions().getX() / 16;
        int[] doorLocations = new int[arraySize];
        int clipboardHeight = clipboard.getDimensions().getY();
        BlockVector3 cornerMin = clipboard.getRegion().getMinimumPoint();
        BlockVector3 cornerMax = clipboard.getRegion().getMaximumPoint();
        int xOffset = 8;
        int xLocation = cornerMin.getX() + xOffset;
        int yLocation = cornerMin.getY();
        int zLocation = cornerMax.getZ();

        // First run offests 8. Subsequent runs offset 16.
        for (int i = 0; i < arraySize; i++) {
            doorLocations[i] = -1;
            for (int startingYLocation = cornerMin.getY(); yLocation < startingYLocation + clipboardHeight; yLocation++) {
                BlockVector3 checkLoc = BlockVector3.at(xLocation, yLocation, zLocation);
                BlockState checkBlock = clipboard.getBlock(checkLoc);
                if (checkBlock.getBlockType().equals(new BlockType("minecraft:iron_block"))) {
                    doorLocations[i] = yLocation - startingYLocation;
                    break;
                }
            }
            xLocation += 16;
            yLocation = cornerMin.getY();
        }
        return doorLocations;
    }

    /**
    * @param clipboard
    * Clipboard that contains the schematic to check.
    * @return
    * int[] containing doors in order of lowest Z to highest Z, where
    * -1 = No door; > -1 == Y offset of door
    */

    public int[] getWestDoors(Clipboard clipboard) {
        int arraySize = clipboard.getDimensions().getZ() / 16;
        int[] doorLocations = new int[arraySize];
        int clipboardHeight = clipboard.getDimensions().getY();
        BlockVector3 cornerMin = clipboard.getRegion().getMinimumPoint();
        int zOffset = 8;
        int xLocation = cornerMin.getX();
        int yLocation = cornerMin.getY();
        int zLocation = cornerMin.getZ() + zOffset;

        // First run offests 8. Subsequent runs offset 16.
        for (int i = 0; i < arraySize; i++) {
            doorLocations[i] = -1;
            for (int startingYLocation = cornerMin.getY(); yLocation < startingYLocation + clipboardHeight; yLocation++) {
                BlockVector3 checkLoc = BlockVector3.at(xLocation, yLocation, zLocation);
                BlockState checkBlock = clipboard.getBlock(checkLoc);
                if (checkBlock.getBlockType().equals(new BlockType("minecraft:iron_block"))) {
                    doorLocations[i] = yLocation - startingYLocation;
                    break;
                }
            }
            zLocation += 16;
            yLocation = cornerMin.getY();
        }
        return doorLocations;
    }

    /**
    * @param clipboard
    * Clipboard that contains the schematic to check.
    * @return
    * int[] containing doors in order of lowest Z to highest Z, where
    * -1 = No door; > -1 == Y offset of door
    */

    public int[] getEastDoors(Clipboard clipboard) {
        int arraySize = clipboard.getDimensions().getZ() / 16;
        int[] doorLocations = new int[arraySize];
        int clipboardHeight = clipboard.getDimensions().getY();
        BlockVector3 cornerMin = clipboard.getRegion().getMinimumPoint();
        BlockVector3 cornerMax = clipboard.getRegion().getMaximumPoint();
        int zOffset = 8;
        int xLocation = cornerMax.getX();
        int yLocation = cornerMin.getY();
        int zLocation = cornerMin.getZ() + zOffset;

        // First run offests 8. Subsequent runs offset 16.
        for (int i = 0; i < arraySize; i++) {
            doorLocations[i] = -1;
            for (int startingYLocation = cornerMin.getY(); yLocation < startingYLocation + clipboardHeight; yLocation++) {
                BlockVector3 checkLoc = BlockVector3.at(xLocation, yLocation, zLocation);
                BlockState checkBlock = clipboard.getBlock(checkLoc);
                if (checkBlock.getBlockType().equals(new BlockType("minecraft:iron_block"))) {
                    doorLocations[i] = yLocation - startingYLocation;
                    break;
                }
            }
            zLocation += 16;
            yLocation = cornerMin.getY();
        }
        return doorLocations;
    }
}