package bonnett.data;

import bonnett.commands.generate;
import bonnett.commands.reloadTemplates;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class commandGetter implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        List<String> entryRemover = new ArrayList<>(Arrays.asList(strings));
        entryRemover.remove(0);
        String[] newStrings = entryRemover.toArray(strings);

        switch (strings[0]) {
            case "generate": {
                generate generate = new generate();
                generate.onCommand(commandSender, command, s, strings);
            } break;
            case "reloadTemplates": {
                reloadTemplates reload = new reloadTemplates();
                reload.onCommand(commandSender, command, s, strings);
            } break;
        }

        return false;
    }
}
