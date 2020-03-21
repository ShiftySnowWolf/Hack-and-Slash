package bonnett;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

public class tabCompleter implements TabCompleter {
    
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> output;
        String[] validTypes = HackAndSlash.validTypes;
        if (args.length == 1) {
            if (validTypes == null) {
                output = new ArrayList<String>(0);
                return output;
            }
            output = new ArrayList<String>(validTypes.length);
            for (String string : validTypes) {
                if (string.startsWith(args[0]) || args[0] == "") {
                    output.add(string);
                }
            }
            return output;
        }
        output = new ArrayList<String>(0);
        return output;
    }
}