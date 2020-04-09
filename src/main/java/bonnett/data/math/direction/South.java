package bonnett.data.math.direction;

import bonnett.Main;
import bonnett.data.math.AlignedLocation;
import bonnett.data.math.PasteLocation;
import bonnett.generation.GenerationHandler;
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

public class South {
    private ClipboardHolder clipHolder;
    private Clipboard clip;
    private Location startLoc;
    private int rotation;

    public South(ClipboardHolder clipboardHolder, Clipboard clipboard, Location startingLocation, int currentRotation) {
        clipHolder = clipboardHolder;
        clip = clipboard;
        startLoc = startingLocation;
        rotation = currentRotation;
    }

    public void generate() {
        AlignedLocation alignedLocation;
        PasteLocation pasteLocation;
        switch (rotation) {
            case 0:
            case 180: {
                alignedLocation = new AlignedLocation(
                        new Location(startLoc.getWorld(),
                                startLoc.getBlockX(),
                                startLoc.getBlockY(),
                                startLoc.getZ() + clip.getDimensions().getZ()));
                GenerationHandler.usedChunks.markUsedChunks(clip, alignedLocation, rotation);
                GenerationHandler.usedChunks.printUsedChunks();
                break;
            }
            case 90:
            case 270: {
                alignedLocation = new AlignedLocation(
                        new Location(startLoc.getWorld(),
                                startLoc.getBlockX(),
                                startLoc.getBlockY(),
                                startLoc.getZ() + clip.getDimensions().getX()));
                GenerationHandler.usedChunks.markUsedChunks(clip, alignedLocation, rotation);
                GenerationHandler.usedChunks.printUsedChunks();
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
}
