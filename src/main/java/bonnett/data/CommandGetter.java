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
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {


        switch (args[0]) {
            case "generate": {
                List<String> entryRemover = new ArrayList<>(Arrays.asList(args));
                entryRemover.remove(0);
                String[] newStrings = entryRemover.toArray(args);
                Generate generate = new Generate();
                generate.onCommand(commandSender, command, label, newStrings);
            } break;
            case "reloadpalettes": {
                List<String> entryRemover = new ArrayList<>(Arrays.asList(args));
                entryRemover.remove(0);
                String[] newStrings = entryRemover.toArray(args);
                ReloadTemplates reload = new ReloadTemplates();
                reload.onCommand(commandSender, command, label, newStrings);
            } break;
            default: {
                commandSender.sendMessage("You did it wrong fucker");
            }
        }

        return false;
    }
}
