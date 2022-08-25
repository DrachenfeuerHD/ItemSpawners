package dev.dontblameme.itemspawners.commands;

import dev.dontblameme.itemspawners.main.ItemSpawners;
import dev.dontblameme.itemspawners.spawner.Spawner;
import dev.dontblameme.itemspawners.spawner.SpawnerTypesManager;
import dev.dontblameme.itemspawners.spawner.SpawnerType;
import dev.dontblameme.itemspawners.utils.MessageUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Optional;

public class SetSpawner implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!sender.hasPermission(ItemSpawners.getCustomConfig().getValue("commandPermission"))) {
            MessageUtils.sendMessage(sender, "noPermissionMessage");
            return false;
        }

        if(!(sender instanceof Player)) return false;

        Player p = (Player) sender;

        if(args.length != 1) {
            MessageUtils.sendMessage(sender, "setSpawnerHelp");
            return false;
        }

        Optional<SpawnerType> type = SpawnerTypesManager.getSpawnerType(args[0]);

        if(!type.isPresent()) {
            MessageUtils.sendMessage(sender, "noValidSpawnerElement");
            return false;
        }

        new Spawner(p.getWorld(), p.getLocation(), type.get()).create();
        MessageUtils.sendMessage(sender, "successfullyCreatedSpawner");

        return false;
    }

}
