package bonnett;

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
import com.sk89q.worldedit.session.ClipboardHolder;
import com.sk89q.worldedit.world.block.BlockState;
import com.sk89q.worldedit.world.block.BlockType;

import org.bukkit.Location;
import org.bukkit.block.CommandBlock;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class generateCommand implements CommandExecutor {
    
    public Location alignedLoc;
    
    private String[] types = HackAndSlash.validTemplates;
    private File dir = HackAndSlash.directoryPath;
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String type = args[0];
        File typePath = null;
        int size;
        Location loc;
        // Type folder location finder.
        for (String s : types) {
            if (s.compareToIgnoreCase(type) == 0) {
                typePath = new File(dir + "\\" + s.toLowerCase()); // Jon Jon told me to do types => s
            } else {
                sender.sendMessage("There is no dungeon type called: " + type.toUpperCase());
                return false;
            }
        }
        // Size integer parsing.
        try {
            size = Integer.parseInt(args[1]);
            if (size < 1) {
                sender.sendMessage("Size must be greater than 0!");
                return false;
            }
        } catch (NumberFormatException e) {
            sender.sendMessage("Size entered was not a whole number!");
            return false;
        }
        // Sender type detection and location getter.
        if (sender instanceof Player) {
            Player player = (Player) sender;
            loc = player.getLocation();
            alignedLoc = new Location(loc.getWorld(), loc.getChunk().getX() * 16, loc.getY(),
            loc.getChunk().getZ() * 16);
        } else if (sender instanceof CommandBlock) {
            CommandBlock commBlock = (CommandBlock) sender;
            loc = commBlock.getLocation();
            alignedLoc = new Location(loc.getWorld(), loc.getChunk().getX() * 16, loc.getY(),
            loc.getChunk().getZ() * 16);
        } else {
            sender.sendMessage("This command cannot be run from console!");
            return false;
        }
        
        try {
            return generateDungeon(typePath, size, alignedLoc);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return false;
    }
    
    public boolean generateDungeon(File typePath, int size, Location loc) throws IOException {
        int arraySize = size * 2 + 3;
        int[][] usedChunks = new int[arraySize][arraySize];
        for (int i = 0; i < arraySize; i++) {
            for (int j = 0; j < arraySize; j++) {
                usedChunks[i][j] = 0;
            }
        }
        printUsedChunks(usedChunks, arraySize);
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
        BlockVector3 minLocation = BlockVector3.at(
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
            usedChunks = markUsedChunks(clipboard, loc, minLocation, usedChunks);
            printUsedChunks(usedChunks, arraySize);
        } catch (WorldEditException e) {
            e.printStackTrace();
            return false;
        }
        // <END OF UNFINISHED CODE BLOCK>'
        return true;
    }

    /**
     * @param clipboard
     * Clipboard containing the .schem being added to the dungeon.
     * @param loc
     * Location at lowest corner of the region.
     * @param minLocation
     * Location at the lowest possible corner of the dungeon.
     * @param usedChunks
     * Array of usedChunks to append new used chunks to.
     * @return
     * int[][] of usedChunks after appending.
     */
    
    public int[][] markUsedChunks(Clipboard clipboard, Location loc, BlockVector3 minLocation, int[][] usedChunks) {
        BlockVector3 sizeInChunks = BlockVector3.at(
        clipboard.getDimensions().getX() / 16, 
        clipboard.getDimensions().getY(), 
        clipboard.getDimensions().getZ() / 16);
        BlockVector2 distanceFromMin = BlockVector2.at(
        minLocation.getX() - loc.getX(), 
        minLocation.getZ() - loc.getZ());
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
        if (usedChunks[x][z] > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @param usedChunks
     * Array to mark new used chunks onto
     * @param arraySize
     * Size of usedChunks
     */
    
    public void printUsedChunks(int[][] usedChunks, int arraySize) {
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
    * @param clipboard
    * Clipboard that contains the schematic to check
    * @return
    * int[] containing doors in order of lowest X to highest X, where
    * -1 = No door; > -1 == Y coordinate of door
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
                    doorLocations[i] = yLocation;
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
    * -1 = No door; > -1 == Y coordinate of door
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
                    doorLocations[i] = yLocation;
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
    * -1 = No door; > -1 == Y coordinate of door
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
                    doorLocations[i] = yLocation;
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
    * -1 = No door; > -1 == Y coordinate of door
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
                    doorLocations[i] = yLocation;
                    break;
                }
            }
            zLocation += 16;
            yLocation = cornerMin.getY();
        }
        return doorLocations;
    }
}