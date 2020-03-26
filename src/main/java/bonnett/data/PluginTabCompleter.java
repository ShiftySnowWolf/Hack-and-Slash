package bonnett.data;

import bonnett.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class PluginTabCompleter implements TabCompleter {
    
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> output;
        String[] validTypes = Main.validPalettes;

        switch (args.length) {
            case 1: {
                output = new ArrayList<>(0);
                output.add("generate");
                output.add("reloadpalettes");
                return output;
            }
            case 2: {
                if (validTypes == null) {
                    output = new ArrayList<>(0);
                    return output;
                }
                output = new ArrayList<>(validTypes.length);
                for (String s : validTypes) {
                    if (s.startsWith(args[0])) {
                        output.add(s);
                    }
                }
                return output;
            }
        }
        output = new ArrayList<>(0);
        return output;
    }
}