package dev.dontblameme.itemspawners.commands;

import dev.dontblameme.itemspawners.main.ItemSpawners;
import dev.dontblameme.itemspawners.spawner.Spawner;
import dev.dontblameme.itemspawners.spawner.SpawnerManager;
import dev.dontblameme.itemspawners.spawner.SpawnerType;
import dev.dontblameme.itemspawners.spawner.SpawnerTypesManager;
import dev.dontblameme.itemspawners.utils.MessageUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GetSpawners implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!sender.hasPermission(ItemSpawners.getCustomConfig().getValue("commandPermission"))) {
            MessageUtils.sendMessage(sender, "noPermissionMessage");
            return false;
        }

        if(!(sender instanceof Player)) return false;

        String message = ItemSpawners.getCustomConfig().getValue("getSpawnersMessage", "messages");
        StringBuilder available = new StringBuilder();
        StringBuilder placed = new StringBuilder();

        for(SpawnerType spawner : SpawnerTypesManager.getSpawnerTypes())
            available.append(String.copyValueOf(ItemSpawners.getCustomConfig().getValue("availableSpawner", "messages").toCharArray()).replace("%spawnerName%", spawner.getName())).append("\n").trimToSize();

        for(Spawner s : SpawnerManager.getSpawners())
            placed.append(String.copyValueOf(ItemSpawners.getCustomConfig().getValue("placedSpawner", "messages").toCharArray()).replace("%spawnerName%", s.getType().getName()).replace("%spawnerID%", s.getId()+"").replace("%coordinates%", s.getLocation().getBlockX() + ", " + s.getLocation().getBlockY() + ", " + s.getLocation().getBlockZ())).append("\n").trimToSize();

        message = message.replace("%availableSpawners%", available.toString()).replace("%placed%", placed.toString());

        MessageUtils.sendCustomMessage(sender, message);

        return false;
    }
}
