package bonnett.generation;

import bonnett.Main;
import bonnett.data.doors.DoorHandler;
import bonnett.data.math.AlignedLocation;
import bonnett.data.enums.Direction;
import bonnett.data.math.direction.East;
import bonnett.data.math.direction.North;
import bonnett.data.math.direction.South;
import bonnett.data.math.direction.West;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.session.ClipboardHolder;
import org.bukkit.Location;

import java.util.Random;

public class Room {
    Clipboard clip;
    ClipboardHolder clipHolder;
    Location startingLocation;
    AlignedLocation alignedLocation;
    Random random;

    int rotation;

    public Room(Clipboard clipboard, Location doorLocation, Direction generateDirection) {
        clip = clipboard;
        System.out.println(clip.getDimensions().toString());
        clipHolder = new ClipboardHolder(clip);
        startingLocation = doorLocation;
        Direction[] validDirections = new Direction[]{Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST};

        switch (generateDirection) {
            case NORTH: {
                rotation = findClipRotation(validDirections, Direction.NORTH);
                North north = new North(clipHolder, clip, startingLocation, rotation);
                north.generate();
                break;
            }
            case SOUTH: {
                rotation = findClipRotation(validDirections, Direction.SOUTH);
                South south = new South(clipHolder, clip, startingLocation, rotation);
                south.generate();
                break;
            }
            case EAST: {
                rotation = findClipRotation(validDirections, Direction.EAST);
                East east = new East(clipHolder, clip, startingLocation, rotation);
                east.generate();
                break;
            }
            case WEST: {
                rotation = findClipRotation(validDirections, Direction.WEST);
                West west = new West(clipHolder, clip, startingLocation, rotation);
                west.generate();
                break;
            }
        }
    }

    public DoorHandler getDoors() { return new DoorHandler(clip, alignedLocation); }

    public int getClipboardRotation() { return rotation; }

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

    private boolean clipHasNorthDoors() {
        DoorHandler doorHandler = new DoorHandler(clip);
        return doorHandler.hasNorthDoors();
    }

    private boolean clipHasSouthDoors() {
        DoorHandler doorHandler = new DoorHandler(clip);
        return doorHandler.hasSouthDoors();
    }

    private boolean clipHasEastDoors() {
        DoorHandler doorHandler = new DoorHandler(clip);
        return doorHandler.hasEastDoors();
    }

    private boolean clipHasWestDoors() {
        DoorHandler doorHandler = new DoorHandler(clip);
        return doorHandler.hasWestDoors();
    }
}
