package bonnett.generation;

import bonnett.Main;
import bonnett.commands.Generate;
import bonnett.data.Doors;
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
import com.sk89q.worldedit.math.transform.AffineTransform;
import com.sk89q.worldedit.session.ClipboardHolder;
import org.bukkit.Location;

import java.util.Random;

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

    public Doors getDoors() {
        return new Doors(clip, alignedLocation);
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
                        case SOUTH: {
                            return 0;
                        }
                        case EAST: {
                            return 270;
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
        AlignedLocation alignedLocation;
        PasteLocation pasteLocation;
        switch (rotation) {
            case 0:
            case 180: {
                alignedLocation = new AlignedLocation(
                        new Location(startingLocation.getWorld(),
                                startingLocation.getBlockX(),
                                startingLocation.getBlockY(),
                                startingLocation.getZ() - clip.getDimensions().getZ()));
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
                             startingLocation.getZ() - clip.getDimensions().getX()));
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
        AlignedLocation alignedLocation;
        PasteLocation pasteLocation;
        switch (rotation) {
            case 0:
            case 180: {
                alignedLocation = new AlignedLocation(
                        new Location(startingLocation.getWorld(),
                                startingLocation.getBlockX() + clip.getDimensions().getX(),
                                startingLocation.getBlockY(),
                                startingLocation.getZ()));
                usedChunks.markUsedChunks(clip, alignedLocation, rotation);
                usedChunks.printUsedChunks();
                break;
            }
            case 90:
            case 270: {
                alignedLocation = new AlignedLocation(
                        new Location(startingLocation.getWorld(),
                                startingLocation.getBlockX() + clip.getDimensions().getZ(),
                                startingLocation.getBlockY(),
                                startingLocation.getZ()));
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

    private void generateWest() {
        AlignedLocation alignedLocation;
        PasteLocation pasteLocation;
        System.out.println("Rotation: " + rotation);
        switch (rotation) {
            case 0:
            case 180: {
                alignedLocation = new AlignedLocation(
                        new Location(startingLocation.getWorld(),
                                startingLocation.getBlockX() - clip.getDimensions().getX(),
                                startingLocation.getBlockY(),
                                startingLocation.getZ()));
                usedChunks.markUsedChunks(clip, alignedLocation, rotation);
                usedChunks.printUsedChunks();
                break;
            }
            case 90:
            case 270: {
                System.out.println("Starting location X: " + startingLocation.getX());
                System.out.println("Starting location Y: " + startingLocation.getBlockY());
                System.out.println("Starting location Z: " + startingLocation.getZ());
                alignedLocation = new AlignedLocation(
                        new Location(startingLocation.getWorld(),
                                startingLocation.getBlockX() - clip.getDimensions().getZ(),
                                startingLocation.getBlockY(),
                                startingLocation.getZ()));
                usedChunks.markUsedChunks(clip, alignedLocation, rotation);
                usedChunks.printUsedChunks();
                break;
            }
            default: {
                Main.plugin.getLogger().warning("Failed to find rotation in Room.java");
                return;
            }
        }
        System.out.println("Aligned location: " + alignedLocation.getY());
        pasteLocation = new PasteLocation(alignedLocation.toLocation(), clip, rotation, Direction.WEST);
        System.out.println(pasteLocation.toBlockVector3().getX());
        System.out.println(pasteLocation.toBlockVector3().getY());
        System.out.println(pasteLocation.toBlockVector3().getZ());

        clipHolder.setTransform(new AffineTransform().rotateY(-rotation));

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

    private boolean clipHasNorthDoors() {
        return new Doors(clip).hasNorthDoors();
    }

    private boolean clipHasSouthDoors() {
        return new Doors(clip).hasSouthDoors();
    }

    private boolean clipHasEastDoors() {
        return new Doors(clip).hasEastDoors();
    }

    private boolean clipHasWestDoors() {
        return new Doors(clip).hasWestDoors();
    }
}
