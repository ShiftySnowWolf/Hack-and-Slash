package ssw.data.math.direction;

import ssw.data.doors.*;
import ssw.data.math.AlignedLocation;
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

public class West {

    private ClipboardHolder clipHolder;
    private Clipboard clip;
    private Location startLoc;
    private int rotation;

    public West(ClipboardHolder clipboardHolder, Clipboard clipboard, Location startingLocation, int currentRotation) {
        clipHolder = clipboardHolder;
        clip = clipboard;
        startLoc = startingLocation;
        rotation = currentRotation;
    }

    public void generate() {
        AlignedLocation alignedLocation;
        clip.setOrigin(clip.getMinimumPoint());
        alignedLocation = new AlignedLocation(
                new Location(startLoc.getWorld(),
                        startLoc.getBlockX(),
                        startLoc.getBlockY(),
                        startLoc.getBlockZ()));
//        GenerationHandler.usedChunks.markUsedChunks(clip, alignedLocation, rotation);
//        GenerationHandler.usedChunks.printUsedChunks();

        clipHolder.setTransform(new AffineTransform().rotateY(-rotation));
        BlockVector3 door = BlockVector3.at(0,0,0);
        switch (rotation) {
            case 0: {
                System.out.println("east");
                if (clip.getDimensions().getZ() > 16) {
                    alignedLocation.add(0, 0, clip.getDimensions().getZ() - 17);
                }
                BlockVector3[] eastDoorsNoLoc = new EastDoors(clip).getDoorsNoLoc();
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
                BlockVector3[] westDoorsNoLoc = new WestDoors(clip).getDoorsNoLoc();
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
                BlockVector3[] northDoorsNoLoc = new NorthDoors(clip).getDoorsNoLoc();
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
                BlockVector3[] southDoorsNoLoc = new SouthDoors(clip).getDoorsNoLoc();
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

    private void oneChunk() {}
    private void twoChunk() {}
    private void threeChunk() {}
    private void fourChunk() {}
    private void sixChunk() {}
}
