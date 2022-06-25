package me.amelia.leancore.Commands;

import me.amelia.leancore.Main;
import me.despical.commandframework.CommandArguments;
import me.despical.commandframework.Completer;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TabCompletion {
    private final Main plugin;

    public TabCompletion(Main plugin) {
        this.plugin = plugin;

        this.plugin.getCommands().registerCommands(this);
    }

    @Completer(name = "leancore")
    public List<String> onTabComplete(CommandArguments arguments) {
        List<String> completions = new ArrayList<>(), commands = this.plugin.getCommands().getCommands().stream().map(cmd -> cmd.name().replace(arguments.getLabel() + '.', "")).collect(Collectors.toList());

        String args[] = arguments.getArguments(), arg = args[0];

        if (args.length == 1) StringUtil.copyPartialMatches(arg, commands, completions);

        completions.sort(null);

        return completions;
    }
}