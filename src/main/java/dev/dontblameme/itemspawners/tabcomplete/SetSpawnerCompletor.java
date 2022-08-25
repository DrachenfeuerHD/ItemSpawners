package dev.dontblameme.itemspawners.tabcomplete;

import dev.dontblameme.itemspawners.spawner.Spawner;
import dev.dontblameme.itemspawners.spawner.SpawnerManager;
import dev.dontblameme.itemspawners.spawner.SpawnerType;
import dev.dontblameme.itemspawners.spawner.SpawnerTypesManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class SetSpawnerCompletor implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {

        List<String> completions = new ArrayList<>();

        if(args.length  > 1)
            return completions;

        switch(command.getName().toUpperCase()) {
            case "SETSPAWNER":
                StringUtil.copyPartialMatches(args[0], SpawnerTypesManager.getSpawnerTypes().stream().map(SpawnerType::getName).collect(Collectors.toList()), completions);
                break;
            case "CONFIGRELOAD":
                break;
            case "DELETESPAWNER":
                StringUtil.copyPartialMatches(args[0], SpawnerManager.getSpawners().stream().map(Spawner::getId).map(i -> i+"").collect(Collectors.toList()), completions);
                break;
        }

        Collections.sort(completions);

        return completions;
    }
}
