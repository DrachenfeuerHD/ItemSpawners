package dev.dontblameme.itemspawners.utils;

import dev.dontblameme.itemspawners.main.ItemSpawners;
import dev.dontblameme.utilsapi.utils.TextParser;
import org.bukkit.command.CommandSender;

public class MessageUtils {

    private MessageUtils() {}

    public static void sendMessage(CommandSender sender, String messageKey) {
        sender.sendMessage(TextParser.parseHexAndCodes(ItemSpawners.getCustomConfig().getValue("prefix") + ItemSpawners.getCustomConfig().getValue(messageKey, "messages")));
    }

    public static void sendCustomMessage(CommandSender sender, String customMessage) {
        sender.sendMessage(TextParser.parseHexAndCodes(ItemSpawners.getCustomConfig().getValue("prefix") + customMessage));
    }

}
