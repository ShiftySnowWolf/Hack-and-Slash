package bonnett.commands;

import bonnett.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PluginTabCompleter implements TabCompleter {

    Main plugin = Main.plugin;
    
    @Override
    public List<String> onTabComplete(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String alias, @ Nonnull String[] args) {
        if (!sender.hasPermission("chunkdungeons.admin")) { return null; }
        List<String> commands = new ArrayList<>();
        List<String> completion = new ArrayList<>();
        boolean isGenerate = args[0].equalsIgnoreCase("generate");

        String[] validTypes = Main.validPalettes;

        int min = Main.min_size;
        int max = Main.max_size;
        List<String> range = new ArrayList<>(0);
        for (int count = min; count <= max; count++) { range.add(String.valueOf(count)); }

        switch (args.length) {
            case 1: {
                commands.add("generate");
                commands.add("reload");
                commands.add("reloadconfig");
                commands.add("reloadpalettes");
                StringUtil.copyPartialMatches(args[0], commands, completion);
            } break;

            case 2: {
                if (isGenerate) { commands.addAll(Arrays.asList(validTypes)); }
                StringUtil.copyPartialMatches(args[1], commands, completion);
            } break;

            case 3: {
                if (isGenerate) { commands.addAll(range); }
                StringUtil.copyPartialMatches(args[2], commands, completion);
            } break;
        }
        Collections.sort(completion);
        return completion;
    }
}