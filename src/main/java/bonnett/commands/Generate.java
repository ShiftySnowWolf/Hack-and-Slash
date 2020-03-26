package bonnett.commands;

import bonnett.Main;
import bonnett.data.Doors;
import bonnett.data.RandomSchematic;
import bonnett.data.UsedChunks;
import bonnett.data.math.*;

import bonnett.generation.Room;
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
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.math.transform.AffineTransform;
import com.sk89q.worldedit.session.ClipboardHolder;

import org.bukkit.Location;
import org.bukkit.block.CommandBlock;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.graalvm.compiler.lir.LIRInstruction;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Generate {

    public static UsedChunks usedChunks;

    private AlignedLocation alignedLoc;
    private DungeonMinLocation dungeonMinLocation;
    private String[] types = Main.validPalettes;
    private File paletteList = Main.paletteList;
    private File typePath;

    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        String type = strings[0];
        typePath = null;
        int size;
        Location senderLoc;
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
            senderLoc = player.getLocation();
            alignedLoc = new AlignedLocation(senderLoc);
        } else if (commandSender instanceof CommandBlock) {
            CommandBlock commBlock = (CommandBlock) commandSender;
            senderLoc = commBlock.getLocation();
            alignedLoc = new AlignedLocation(senderLoc);
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
    
    public boolean generateDungeon(int size, AlignedLocation alignedLoc) throws IOException {

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
        PasteLocation pasteLoc = new PasteLocation(alignedLoc.toLocation(), clipboard);
        BossRoomMinLocation bRMin = new BossRoomMinLocation(alignedLoc);
        dungeonMinLocation = new DungeonMinLocation(bRMin, size);
        usedChunks = new UsedChunks(size, dungeonMinLocation);

        try (EditSession editSession = WorldEdit.getInstance().getEditSessionFactory()
        .getEditSession(new BukkitWorld(alignedLoc.getWorld()), -1)) {
            Operation operation = new ClipboardHolder(clipboard).createPaste(editSession)
            .to(pasteLoc.toBlockVector3())
            // Configure here.
            .build();
            Operations.complete(operation);

            usedChunks.markUsedChunks(clipboard, alignedLoc);
            usedChunks.printUsedChunks();
            Doors doors = new Doors(clipboard, alignedLoc);
            if (doors.hasNorthDoors()) {
                Location doorLoc;
                Location[] northDoors = doors.getNorthDoors();
                for (int i = 0; i < northDoors.length; i++) {
                    if (northDoors[i].getY() > -1) {
                        doorLoc = new Location(alignedLoc.getWorld(),
                                alignedLoc.getX() + (8 + (16 * i)),
                                alignedLoc.getY() + northDoors[i].getY(),
                                alignedLoc.getZ());
                        Room northRoom = new Room(new RandomSchematic().getNext(typePath.toString(), false),
                                doorLoc, Direction.NORTH);
                    }
                }
            } else if (doors.hasSouthDoors()) {
                Location doorLoc;
                Location[] southDoors = doors.getSouthDoors();
                for (int i = 0; i < southDoors.length; i++) {
                    if (southDoors[i].getY() > -1) {
                        doorLoc = new Location(alignedLoc.getWorld(),
                                alignedLoc.getX() + (8 + (16 * i)),
                                alignedLoc.getY() + southDoors[i].getY(),
                                alignedLoc.getZ());
                        Room northRoom = new Room(new RandomSchematic().getNext(typePath.toString(), false),
                                doorLoc, Direction.SOUTH);
                    }
                }
            } else if (doors.hasEastDoors()) {
                Location doorLoc;
                Location[] eastDoors = doors.getEastDoors();
                for (int i = 0; i < eastDoors.length; i++) {
                    if (eastDoors[i].getY() > -1) {
                        doorLoc = new Location(alignedLoc.getWorld(),
                                alignedLoc.getX() + (8 + (16 * i)),
                                alignedLoc.getY() + eastDoors[i].getY(),
                                alignedLoc.getZ());
                        Room northRoom = new Room(new RandomSchematic().getNext(typePath.toString(), false),
                                doorLoc, Direction.NORTH);
                    }
                }
            } else if (doors.hasWestDoors()) {
                Location doorLoc;
                Location[] westDoors = doors.getWestDoors();
                for (int i = 0; i < westDoors.length; i++) {
                    if (westDoors[i].getY() > -1) {
                        doorLoc = new Location(alignedLoc.getWorld(),
                                alignedLoc.getX() + (8 + (16 * i)),
                                alignedLoc.getY() + westDoors[i].getY(),
                                alignedLoc.getZ());
                        Room northRoom = new Room(new RandomSchematic().getNext(typePath.toString(), false),
                                doorLoc, Direction.NORTH);
                    }
                }
            }
        } catch (WorldEditException e) {
            e.printStackTrace();
            return false;
        }
        // <END OF UNFINISHED CODE BLOCK>'
        return true;
    }

    public void generateNorthRoom(File typePath, Location doorLoc) throws IOException {
        String extension = ".schem";
        File room = new File(typePath + "\\rooms\\one_chunk\\testRoom" + extension);
        Location destination;
        Clipboard clipboard;
        ClipboardFormat format = ClipboardFormats.findByFile(room);
        try (ClipboardReader reader = format.getReader(new FileInputStream(room))) {
            clipboard = reader.read();
        }
        BlockVector3 dim = clipboard.getDimensions();
        Location loc = doorLoc.subtract(8 + dim.getX() - 16, 0, dim.getZ());

        int rotation, doorHeight = 0;
        Doors doorsNoLoc = new Doors(clipboard);
        if (doorsNoLoc.hasNorthDoors()) {
            System.out.println("Found north doors");
            rotation = 180;
            BlockVector3[] northDoors = doorsNoLoc.getNorthDoorsNoLoc();
            for (BlockVector3 northDoor : northDoors) {
                if (northDoor.getBlockY() > -1) {
                    doorHeight = northDoor.getBlockY();
                    break;
                }
            }
            destination = loc.add(dim.getX(), -1 * doorHeight, dim.getZ());

        } else if (doorsNoLoc.hasSouthDoors()) {
            System.out.println("Found south doors");
            rotation = 0;
            BlockVector3[] southDoors = doorsNoLoc.getSouthDoorsNoLoc();
            for (BlockVector3 southDoor : southDoors) {
                if (southDoor.getBlockY() > -1) {
                    doorHeight = southDoor.getBlockY();
                    break;
                }
            }
            destination = loc.add(0, -1 * doorHeight, 0);
        } else if (doorsNoLoc.hasEastDoors()) {
            System.out.println("Found east doors");
            rotation = 90;
            BlockVector3[] eastDoors = doorsNoLoc.getEastDoorsNoLoc();
            for (BlockVector3 eastDoor : eastDoors) {
                if (eastDoor.getBlockY() > -1) {
                    doorHeight = eastDoor.getBlockY();
                    break;
                }
            }
            destination = loc.add(dim.getX(), -1 * doorHeight, 0);
        } else if (doorsNoLoc.hasWestDoors()) {
            System.out.println("Found west doors");
            rotation = -90;
            BlockVector3[] westDoors = doorsNoLoc.getWestDoorsNoLoc();
            int i;
            for (i = 0; i < westDoors.length; i++) {
                if (westDoors[i].getBlockY() > -1) {
                    doorHeight = westDoors[i].getBlockY();
                    break;
                }
            }
            destination = loc.add(16 * i, -1 * doorHeight, dim.getZ());
        } else {
            System.err.println("File doesn't have a valid door!");
            return;
        }

        AlignedLocation alignedLocation = new AlignedLocation(destination);
        PasteLocation pasteLocation = new PasteLocation(alignedLocation.toLocation(), clipboard);

        ClipboardHolder holder = new ClipboardHolder(clipboard);
        holder.setTransform(new AffineTransform().rotateY(rotation));

        try (EditSession editSession = WorldEdit.getInstance().getEditSessionFactory()
        .getEditSession(new BukkitWorld(loc.getWorld()), -1)) {
            Operation operation = holder.createPaste(editSession).to(pasteLocation.toBlockVector3()).build();

            Operations.complete(operation);
            System.out.println("Dimensions: " + clipboard.getDimensions().toString());
            usedChunks.markUsedChunks(clipboard, alignedLocation);
            usedChunks.printUsedChunks();
        } catch (WorldEditException e) {
            e.printStackTrace();

        }

    }

}