package bonnett.tabCompleters;

import bonnett.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class generateTabCompleter implements TabCompleter {
    
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> output;
        String[] validTypes = Main.validPalettes;
        if (args.length == 1) {
            if (validTypes == null) {
                output = new ArrayList<>(0);
                return output;
            }
            output = new ArrayList<>(validTypes.length);
            for (String string : validTypes) {
                if (string.startsWith(args[0])) {
                    output.add(string);
                }
            }
            return output;
        }
        output = new ArrayList<>(0);
        return output;
    }
}