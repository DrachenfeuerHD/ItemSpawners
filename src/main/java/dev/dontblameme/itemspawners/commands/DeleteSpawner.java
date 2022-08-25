package dev.dontblameme.itemspawners.commands;

import dev.dontblameme.itemspawners.main.ItemSpawners;
import dev.dontblameme.itemspawners.spawner.Spawner;
import dev.dontblameme.itemspawners.spawner.SpawnerManager;
import dev.dontblameme.itemspawners.utils.MessageUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Optional;

public class DeleteSpawner implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!sender.hasPermission(ItemSpawners.getCustomConfig().getValue("commandPermission"))) {
            MessageUtils.sendMessage(sender, "noPermissionMessage");
            return false;
        }

        if(!(sender instanceof Player)) return false;

        if(args.length != 1) {
            MessageUtils.sendMessage(sender, "deleteSpawnerHelp");
            return false;
        }

        int id = 0;

        try {
            id = Integer.parseInt(args[0]);
        } catch(NumberFormatException e) {
            MessageUtils.sendMessage(sender, "deleteSpawnerHelp");
            return false;
        }

        Optional<Spawner> spawner = SpawnerManager.getSpawner(id);

        if(!spawner.isPresent()) {
            MessageUtils.sendMessage(sender, "noValidID");
            return false;
        }

        spawner.get().remove(false);
        SpawnerManager.removeSpawner(spawner.get());
        MessageUtils.sendMessage(sender, "successfullyDeleted");

        return true;
    }
}
