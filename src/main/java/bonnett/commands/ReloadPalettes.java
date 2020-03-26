package bonnett.commands;

import bonnett.Main;
import org.bukkit.Color;
import org.bukkit.command.CommandSender;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ReloadPalettes {

    private String[] valid = Main.validPalettes;
    private File paletteList = Main.paletteList;

    public void onCommand(CommandSender sender) {
        if (paletteList.isDirectory()) {
            if (!(valid == null)) {
                List<String> validList = new ArrayList<>(Arrays.asList(valid));
                validList.addAll(Arrays.asList(Objects.requireNonNull(paletteList.list())));
                valid = validList.toArray(valid);
            } else { valid = paletteList.list();}
            sender.sendMessage(Color.LIME + "Reloaded all palettes!");
        } else { sender.sendMessage(Color.RED + "Could not find any palettes!"); }
    }
}
