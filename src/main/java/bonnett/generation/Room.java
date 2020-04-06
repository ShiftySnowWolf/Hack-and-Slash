package bonnett.generation;

import bonnett.Main;
import bonnett.commands.Generate;
import bonnett.data.doors.DoorHandler;
import bonnett.data.UsedChunks;
import bonnett.data.math.AlignedLocation;

import bonnett.data.math.Direction;
import bonnett.data.math.PasteLocation;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.math.transform.AffineTransform;
import com.sk89q.worldedit.session.ClipboardHolder;
import org.bukkit.Location;
import org.bukkit.material.Door;

import java.util.Random;

import static bonnett.data.doors.DoorHandler.*;

public class Room {
    Clipboard clip;
    ClipboardHolder clipHolder;
    Location startingLocation;
    AlignedLocation alignedLocation;
    Random random;
    UsedChunks usedChunks;

    int rotation;

    public Room(Clipboard clipboard, Location doorLocation, Direction generateDirection) {
        clip = clipboard;
        new DoorHandler(clipboard);
        System.out.println(clip.getDimensions().toString());
        clipHolder = new ClipboardHolder(clip);
        startingLocation = doorLocation;
        Direction[] validDirections = new Direction[]{Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST};
        usedChunks = Generate.usedChunks;

        switch (generateDirection) {
            case NORTH: {
                rotation = findClipRotation(validDirections, Direction.NORTH);
                generateNorth();
                break;
            }
            case SOUTH: {
                rotation = findClipRotation(validDirections, Direction.SOUTH);
                generateSouth();
                break;
            }
            case EAST: {
                rotation = findClipRotation(validDirections, Direction.EAST);
                generateEast();
                break;
            }
            case WEST: {
                rotation = findClipRotation(validDirections, Direction.WEST);
                generateWest();
                break;
            }
        }
    }

    public DoorHandler getDoors() {
        return new DoorHandler(clip, alignedLocation);
    }

    public int getClipboardRotation() {
        return rotation;
    }

    private int findClipRotation(Direction[] validDirections, Direction generateDirection) {
        random = new Random();
        int randInt = random.nextInt(validDirections.length);
        Direction checkDirection = intToDirectionMapping(randInt, validDirections);
        Direction[] newValidDirections;
        switch (checkDirection) {
            case NORTH: {
                if (clipHasNorthDoors()) {
                    switch (generateDirection) {
                        case NORTH: {
                            return 180;
                        }
                        case EAST: {
                            return 270;
                        }
                        case SOUTH: {
                            return 0;
                        }
                        case WEST: {
                            return 90;
                        }
                    }
                } else {
                    newValidDirections = new Direction[validDirections.length - 1];
                    int count = 0;
                    for (Direction validDirection : validDirections) {
                        if (validDirection != Direction.NORTH) {
                            newValidDirections[count] = validDirection;
                            count++;
                        }
                    }
                    return findClipRotation(newValidDirections, generateDirection);
                }
            }
            case SOUTH: {
                if (clipHasSouthDoors()) {
                    switch (generateDirection) {
                        case NORTH: {
                            return 0;
                        }
                        case SOUTH: {
                            return 180;
                        }
                        case EAST: {
                            return 90;
                        }
                        case WEST: {
                            return 270;
                        }
                    }
                } else {
                    newValidDirections = new Direction[validDirections.length - 1];
                    int count = 0;
                    for (Direction validDirection : validDirections) {
                        if (validDirection != Direction.SOUTH) {
                            newValidDirections[count] = validDirection;
                            count++;
                        }
                    }
                    return findClipRotation(newValidDirections, generateDirection);
                }
            }
            case EAST: {
                if (clipHasEastDoors()) {
                    switch (generateDirection) {
                        case NORTH: {
                            return 270;
                        }
                        case SOUTH: {
                            return 90;
                        }
                        case EAST: {
                            return 180;
                        }
                        case WEST: {
                            return 0;
                        }
                    }
                } else {
                    newValidDirections = new Direction[validDirections.length - 1];
                    int count = 0;
                    for (Direction validDirection : validDirections) {
                        if (validDirection != Direction.EAST) {
                            newValidDirections[count] = validDirection;
                            count++;
                        }
                    }
                    return findClipRotation(newValidDirections, generateDirection);
                }
            }
            case WEST: {
                if (clipHasWestDoors()) {
                    switch (generateDirection) {
                        case NORTH: {
                            return 90;
                        }
                        case SOUTH: {
                            return 270;
                        }
                        case EAST: {
                            return 0;
                        }
                        case WEST: {
                            return 180;
                        }
                    }
                } else {
                    newValidDirections = new Direction[validDirections.length - 1];
                    int count = 0;
                    for (Direction validDirection : validDirections) {
                        if (validDirection != Direction.WEST) {
                            newValidDirections[count] = validDirection;
                            count++;
                        }
                    }
                    return findClipRotation(newValidDirections, generateDirection);
                }
            }
        }
        Main.plugin.getLogger().warning("This message should never run from Room.java");
        return findClipRotation(validDirections,generateDirection);
    }

    private Direction intToDirectionMapping(int num, Direction[] validDirections) {
        return validDirections[num];
    }

    private void generateNorth() {
        // Still Working on north
        AlignedLocation alignedLocation;
        clip.setOrigin(clip.getMinimumPoint());
        alignedLocation = new AlignedLocation(
                new Location(startingLocation.getWorld(),
                        startingLocation.getBlockX(),
                        startingLocation.getBlockY(),
                        startingLocation.getBlockZ()));
        usedChunks.markUsedChunks(clip, alignedLocation, rotation);
        usedChunks.printUsedChunks();

        clipHolder.setTransform(new AffineTransform().rotateY(-rotation));

        new DoorHandler(clip);
        BlockVector3 door = BlockVector3.at(0,0,0);
        switch (rotation) {
            case 0: {
                System.out.println("south");
                if (clip.getDimensions().getZ() > 16) {
                    alignedLocation.add(0, 0, clip.getDimensions().getX() - 17);
                }
                for (BlockVector3 d : southDoorsNoLoc) {
                    if (d.getY() > -1) {
                        door = d;
                        break;
                    }
                }
                System.out.println(door.getX());
                try (EditSession editSession = WorldEdit.getInstance().getEditSessionFactory()
                        .getEditSession(new BukkitWorld(alignedLocation.getWorld()), -1)) {
                    Operation operation = clipHolder.createPaste(editSession)
                            .to(alignedLocation.toBlockVector3().subtract(door.getX(), 0, 1))
                            .build();

                    Operations.complete(operation);
                } catch (WorldEditException e) {
                    e.printStackTrace();
                }
                return;
            }
            case 180: {
                System.out.println("north");
                for (BlockVector3 d : northDoorsNoLoc) {
                    if (d.getY() > -1) {
                        door = d;
                        break;
                    }
                }
                System.out.println(door.getX());
                if (clip.getDimensions().getX() > 16) {
                    alignedLocation.add(clip.getDimensions().getZ(), 0, clip.getDimensions().getX() - 17);
                }
                try (EditSession editSession = WorldEdit.getInstance().getEditSessionFactory()
                        .getEditSession(new BukkitWorld(alignedLocation.getWorld()), -1)) {
                    Operation operation = clipHolder.createPaste(editSession)
                            .to(alignedLocation.toBlockVector3().subtract(door.getX(), 0, 1))
                            .build();

                    Operations.complete(operation);
                } catch (WorldEditException e) {
                    e.printStackTrace();
                }
                return;
            }
            case 90: {
                System.out.println("east");
                if (clip.getDimensions().getZ() > 16) {
                    alignedLocation.add(0, 0, clip.getDimensions().getZ() - 17);
                }
                for (BlockVector3 d : eastDoorsNoLoc) {
                    if (d.getY() > -1) {
                        door = d;
                        break;
                    }
                }
                System.out.println(door.getX());
                try (EditSession editSession = WorldEdit.getInstance().getEditSessionFactory()
                        .getEditSession(new BukkitWorld(alignedLocation.getWorld()), -1)) {
                    Operation operation = clipHolder.createPaste(editSession)
                            .to(alignedLocation.toBlockVector3().subtract(door.getZ(), 0, 1))
                            .build();

                    Operations.complete(operation);
                } catch (WorldEditException e) {
                    e.printStackTrace();
                }
                return;
            }
            case 270: {
                System.out.println("west");
                if (clip.getDimensions().getZ() > 16) {
                    alignedLocation.add(0, 0, clip.getDimensions().getZ() - 17);
                }
                for (BlockVector3 d : westDoorsNoLoc) {
                    if (d.getY() > -1) {
                        door = d;
                        break;
                    }
                }
                System.out.println(door.getX());
                try (EditSession editSession = WorldEdit.getInstance().getEditSessionFactory()
                        .getEditSession(new BukkitWorld(alignedLocation.getWorld()), -1)) {
                    Operation operation = clipHolder.createPaste(editSession)
                            .to(alignedLocation.toBlockVector3().subtract(door.getZ(), 0, 1))
                            .build();

                    Operations.complete(operation);
                } catch (WorldEditException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private void generateSouth() {
        AlignedLocation alignedLocation;
        PasteLocation pasteLocation;
        switch (rotation) {
            case 0:
            case 180: {
                alignedLocation = new AlignedLocation(
                        new Location(startingLocation.getWorld(),
                                startingLocation.getBlockX(),
                                startingLocation.getBlockY(),
                                startingLocation.getZ() + clip.getDimensions().getZ()));
                usedChunks.markUsedChunks(clip, alignedLocation, rotation);
                usedChunks.printUsedChunks();
                break;
            }
            case 90:
            case 270: {
                alignedLocation = new AlignedLocation(
                        new Location(startingLocation.getWorld(),
                                startingLocation.getBlockX(),
                                startingLocation.getBlockY(),
                                startingLocation.getZ() + clip.getDimensions().getX()));
                usedChunks.markUsedChunks(clip, alignedLocation, rotation);
                usedChunks.printUsedChunks();
                break;
            }
            default: {
                Main.plugin.getLogger().warning("Failed to find rotation in Room.java");
                return;
            }
        }
        pasteLocation = new PasteLocation(alignedLocation.toLocation(), clip);

        clipHolder.setTransform(new AffineTransform().rotateY(rotation));

        try (EditSession editSession = WorldEdit.getInstance().getEditSessionFactory()
                .getEditSession(new BukkitWorld(pasteLocation.getWorld()), -1)) {
            Operation operation = clipHolder.createPaste(editSession)
                    .to(pasteLocation.toBlockVector3())
                    .build();

            Operations.complete(operation);
        } catch (WorldEditException e) {
            e.printStackTrace();
        }

    }

    private void generateEast() {
        // Currently set like west, needs to be adjusted
        AlignedLocation alignedLocation;
        clip.setOrigin(clip.getMinimumPoint());
        alignedLocation = new AlignedLocation(
                new Location(startingLocation.getWorld(),
                        startingLocation.getBlockX(),
                        startingLocation.getBlockY(),
                        startingLocation.getBlockZ()));
        usedChunks.markUsedChunks(clip, alignedLocation, rotation);
        usedChunks.printUsedChunks();

        clipHolder.setTransform(new AffineTransform().rotateY(-rotation));

        new DoorHandler(clip);
        BlockVector3 door = BlockVector3.at(0,0,0);
        switch (rotation) {
            case 0: {
                System.out.println("west");
                if (clip.getDimensions().getZ() > 16) {
                    alignedLocation.add(0, 0, clip.getDimensions().getZ() - 17);
                }
                for (BlockVector3 d : eastDoorsNoLoc) {
                    if (d.getY() > -1) {
                        door = d;
                        break;
                    }
                }
                try (EditSession editSession = WorldEdit.getInstance().getEditSessionFactory()
                        .getEditSession(new BukkitWorld(alignedLocation.getWorld()), -1)) {
                    Operation operation = clipHolder.createPaste(editSession)
                            .to(alignedLocation.toBlockVector3().subtract(1, 0, door.getZ() - 8))
                            .build();

                    Operations.complete(operation);
                } catch (WorldEditException e) {
                    e.printStackTrace();
                }
                return;
            }
            case 180: {
                System.out.println("east");
                for (BlockVector3 d : westDoorsNoLoc) {
                    if (d.getY() > -1) {
                        door = d;
                        break;
                    }
                }
                if (clip.getDimensions().getZ() > 16) {
                    alignedLocation.add(0, 0, clip.getDimensions().getZ() - 17);
                }
                try (EditSession editSession = WorldEdit.getInstance().getEditSessionFactory()
                        .getEditSession(new BukkitWorld(alignedLocation.getWorld()), -1)) {
                    Operation operation = clipHolder.createPaste(editSession)
                            .to(alignedLocation.toBlockVector3().subtract(1, 0, door.getZ() - 8))
                            .build();

                    Operations.complete(operation);
                } catch (WorldEditException e) {
                    e.printStackTrace();
                }
                return;
            }
            case 90: {
                System.out.println("south");
                if (clip.getDimensions().getZ() > 16) {
                    alignedLocation.add(0, 0, clip.getDimensions().getX() - 17);
                }
                for (BlockVector3 d : northDoorsNoLoc) {
                    if (d.getY() > -1) {
                        door = d;
                        break;
                    }
                }
                try (EditSession editSession = WorldEdit.getInstance().getEditSessionFactory()
                        .getEditSession(new BukkitWorld(alignedLocation.getWorld()), -1)) {
                    Operation operation = clipHolder.createPaste(editSession)
                            .to(alignedLocation.toBlockVector3().subtract(1, 0, door.getX() - 8))
                            .build();

                    Operations.complete(operation);
                } catch (WorldEditException e) {
                    e.printStackTrace();
                }
                return;
            }
            case 270: {
                System.out.println("north");
                if (clip.getDimensions().getZ() > 16) {
                    alignedLocation.add(0, 0, clip.getDimensions().getX() - 17);
                }
                for (BlockVector3 d : southDoorsNoLoc) {
                    if (d.getY() > -1) {
                        door = d;
                        break;
                    }
                }
                try (EditSession editSession = WorldEdit.getInstance().getEditSessionFactory()
                        .getEditSession(new BukkitWorld(alignedLocation.getWorld()), -1)) {
                    Operation operation = clipHolder.createPaste(editSession)
                            .to(alignedLocation.toBlockVector3().subtract(1, 0, door.getX() - 8))
                            .build();

                    Operations.complete(operation);
                } catch (WorldEditException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private void generateWest() {
        AlignedLocation alignedLocation;
        clip.setOrigin(clip.getMinimumPoint());
        alignedLocation = new AlignedLocation(
                new Location(startingLocation.getWorld(),
                        startingLocation.getBlockX(),
                        startingLocation.getBlockY(),
                        startingLocation.getBlockZ()));
        usedChunks.markUsedChunks(clip, alignedLocation, rotation);
        usedChunks.printUsedChunks();

        clipHolder.setTransform(new AffineTransform().rotateY(-rotation));

        new DoorHandler(clip);
        BlockVector3 door = BlockVector3.at(0,0,0);
        switch (rotation) {
            case 0: {
                System.out.println("east");
                if (clip.getDimensions().getZ() > 16) {
                    alignedLocation.add(0, 0, clip.getDimensions().getZ() - 17);
                }
                for (BlockVector3 d : eastDoorsNoLoc) {
                    if (d.getY() > -1) {
                        door = d;
                        break;
                    }
                }
                try (EditSession editSession = WorldEdit.getInstance().getEditSessionFactory()
                        .getEditSession(new BukkitWorld(alignedLocation.getWorld()), -1)) {
                    Operation operation = clipHolder.createPaste(editSession)
                            .to(alignedLocation.toBlockVector3().subtract(1, 0, door.getZ() - 8))
                            .build();

                    Operations.complete(operation);
                } catch (WorldEditException e) {
                    e.printStackTrace();
                }
                return;
            }
            case 180: {
                System.out.println("west");
                for (BlockVector3 d : westDoorsNoLoc) {
                    if (d.getY() > -1) {
                        door = d;
                        break;
                    }
                }
                if (clip.getDimensions().getZ() > 16) {
                    alignedLocation.add(0, 0, clip.getDimensions().getZ() - 17);
                }
                try (EditSession editSession = WorldEdit.getInstance().getEditSessionFactory()
                        .getEditSession(new BukkitWorld(alignedLocation.getWorld()), -1)) {
                    Operation operation = clipHolder.createPaste(editSession)
                            .to(alignedLocation.toBlockVector3().subtract(1, 0, door.getZ() - 8))
                            .build();

                    Operations.complete(operation);
                } catch (WorldEditException e) {
                    e.printStackTrace();
                }
                return;
            }
            case 90: {
                System.out.println("north");
                if (clip.getDimensions().getZ() > 16) {
                    alignedLocation.add(0, 0, clip.getDimensions().getX() - 17);
                }
                for (BlockVector3 d : northDoorsNoLoc) {
                    if (d.getY() > -1) {
                        door = d;
                        break;
                    }
                }
                try (EditSession editSession = WorldEdit.getInstance().getEditSessionFactory()
                        .getEditSession(new BukkitWorld(alignedLocation.getWorld()), -1)) {
                    Operation operation = clipHolder.createPaste(editSession)
                            .to(alignedLocation.toBlockVector3().subtract(1, 0, door.getX() - 8))
                            .build();

                    Operations.complete(operation);
                } catch (WorldEditException e) {
                    e.printStackTrace();
                }
                return;
            }
            case 270: {
                System.out.println("South");
                if (clip.getDimensions().getZ() > 16) {
                    alignedLocation.add(0, 0, clip.getDimensions().getX() - 17);
                }
                for (BlockVector3 d : southDoorsNoLoc) {
                    if (d.getY() > -1) {
                        door = d;
                        break;
                    }
                }
                try (EditSession editSession = WorldEdit.getInstance().getEditSessionFactory()
                        .getEditSession(new BukkitWorld(alignedLocation.getWorld()), -1)) {
                    Operation operation = clipHolder.createPaste(editSession)
                            .to(alignedLocation.toBlockVector3().subtract(1, 0, door.getX() - 8))
                            .build();

                    Operations.complete(operation);
                } catch (WorldEditException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private boolean clipHasNorthDoors() {
        new DoorHandler(clip);
        return hasNorthDoors;
    }

    private boolean clipHasSouthDoors() {
        new DoorHandler(clip);
        return hasSouthDoors;
    }

    private boolean clipHasEastDoors() {
        new DoorHandler(clip);
        return hasEastDoors;
    }

    private boolean clipHasWestDoors() {
        new DoorHandler(clip);
        return hasWestDoors;
    }
}
