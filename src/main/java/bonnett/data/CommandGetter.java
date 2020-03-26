package bonnett.data;

import bonnett.commands.Generate;
import bonnett.commands.ReloadTemplates;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandGetter implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        List<String> entryRemover = new ArrayList<>(Arrays.asList(strings));
        entryRemover.remove(0);
        String[] newStrings = entryRemover.toArray(strings);

        switch (strings[0]) {
            case "generate": {
                Generate generate = new Generate();
                generate.onCommand(commandSender, command, s, strings);
            } break;
            case "reloadTemplates": {
                ReloadTemplates reload = new ReloadTemplates();
                reload.onCommand(commandSender, command, s, strings);
            } break;
        }

        return false;
    }
}
