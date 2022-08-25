package dev.dontblameme.itemspawners.spawner;

import dev.dontblameme.utilsapi.utils.TextParser;
import lombok.Getter;
import org.bukkit.Material;

@Getter
public class SpawnerType {

    private final int timeBetweenSpawns, amount, timeBetweenTiers, maxTier;
    private final Material resource, headBlock;
    private final String customName, name;

    public SpawnerType(int timeBetweenSpawns, int amount, int timeBetweenTiers, int maxTier, String resource, String headBlock, String customName, String name) {
        this.timeBetweenSpawns = timeBetweenSpawns;
        this.amount = amount;
        this.timeBetweenTiers = timeBetweenTiers;
        this.maxTier = maxTier;
        this.resource = Material.valueOf(resource.toUpperCase());
        this.headBlock = Material.valueOf(headBlock.toUpperCase());
        this.customName = TextParser.parseHexAndCodes(customName);
        this.name = name;
    }
}
