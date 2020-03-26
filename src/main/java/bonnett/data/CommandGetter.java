package bonnett.data;

import bonnett.commands.Generate;
import bonnett.commands.ReloadPalettes;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandGetter implements CommandExecutor {

    @Override
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String label, String[] args) {


        switch (args[0]) {
            case "generate": {
                List<String> entryRemover = new ArrayList<>(Arrays.asList(args));
                entryRemover.remove(0);
                String[] newStrings = entryRemover.toArray(args);
                Generate generate = new Generate();
                generate.onCommand(sender, newStrings);
            } break;
            case "reloadpalettes": {
                ReloadPalettes reload = new ReloadPalettes();
                reload.onCommand(sender);
            } break;
            default: {
                sender.sendMessage("You did it wrong fucker!");
            }
        }

        return false;
    }
}
