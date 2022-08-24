package dev.dontblameme.itemspawners.commands;

import dev.dontblameme.itemspawners.main.ItemSpawners;
import dev.dontblameme.itemspawners.utils.MessageUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ConfigReload implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!sender.hasPermission(ItemSpawners.getCustomConfig().getValue("commandPermission"))) {
            MessageUtils.sendMessage(sender, "noPermissionMessage");
            return false;
        }
        ItemSpawners.getCustomConfig().refresh();
        MessageUtils.sendMessage(sender, "successfullyReloaded");

        return true;
    }
}
