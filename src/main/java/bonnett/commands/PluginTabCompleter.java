package bonnett.commands;

import bonnett.Main;
import bonnett.data.enums.RoomType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;

import javax.annotation.Nonnull;
import java.io.File;
import java.util.*;

import static bonnett.Main.*;

public class PluginTabCompleter implements TabCompleter {
    
    @Override
    public List<String> onTabComplete(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String alias, @ Nonnull String[] args) {
        if (!sender.hasPermission("chunkdungeons.admin")) { return null; }
        List<String> commands = new ArrayList<>();
        List<String> completion = new ArrayList<>();
        boolean isGenerate = args[0].equalsIgnoreCase("generate");
        boolean isDelPalette = args[0].equalsIgnoreCase("deletepalette");
        boolean isNewSchem = args[0].equalsIgnoreCase("addschematic");
        boolean isDelSchem = args[0].equalsIgnoreCase("removeschematic");
        boolean isLoadSchem = args[0].equalsIgnoreCase("loadschematic");

        String[] validTypes = validPalettes;

        int min = min_size;
        int max = max_size;
        List<String> range = new ArrayList<>(0);
        for (int count = min; count <= max; count++) { range.add(String.valueOf(count)); }
        List<String> roomTypes = new ArrayList<>(0);
        for (RoomType type : RoomType.values()) { roomTypes.add(String.valueOf(type).toLowerCase()); }

        switch (args.length) {
            case 1: { // Completes '/chunkdungeons [HERE]'
                commands.add("generate");
                commands.add("reload");
                commands.add("reloadconfig");
                commands.add("reloadpalettes");
                commands.add("createpalette");
                commands.add("deletepalette");
                commands.add("addschematic");
                commands.add("removeschematic");
                commands.add("loadschematic");
                StringUtil.copyPartialMatches(args[0], commands, completion);
            } break;
            case 2: { // Completes '/chunkdungeons <generate/addschematic> [HERE]'
                if (isGenerate || isDelPalette || isNewSchem || isDelSchem || isLoadSchem) { commands.addAll(Arrays.asList(validTypes)); }
                StringUtil.copyPartialMatches(args[1], commands, completion);
            } break;
            case 3: { // Completes '/chunkdungeons generate <palette> [HERE]'
                if (isGenerate) { commands.addAll(range); }
                if (isNewSchem || isDelSchem || isLoadSchem) { commands.addAll(roomTypes); }
                StringUtil.copyPartialMatches(args[2], commands, completion);
            } break;
            case 4: {
                if (isDelSchem || isLoadSchem) {
                    File typePath;
                    switch (args[2]) {
                        case "straight_hall":
                        case "three_way_hall":
                        case "four_way_hall":
                        case "corner_hall":
                            typePath = new File(Main.paletteFolder + fs + args[1] + fs + "halls" + fs + args[2]);
                            break;
                        case "large_boss":
                        case "small_boss":
                            typePath = new File(Main.paletteFolder + fs + args[1] + fs + "boss_rooms" + fs + args[2]);
                            break;
                        default:
                            typePath = new File(Main.paletteFolder + fs + args[1] + fs + "rooms" + fs + args[2]);
                    }
                    List<String> schematics = new ArrayList<>(0);
                    schematics.addAll(Arrays.asList(Objects.requireNonNull(typePath.list())));
                    StringUtil.copyPartialMatches(args[3], schematics, completion);
                }
            }
        }
        Collections.sort(completion);
        return completion;
    }
}