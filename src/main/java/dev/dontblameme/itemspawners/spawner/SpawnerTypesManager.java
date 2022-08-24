package dev.dontblameme.itemspawners.spawner;

import dev.dontblameme.utilsapi.config.customconfig.CustomConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SpawnerTypesManager {

    private static final List<SpawnerType> spawners = new ArrayList<>();

    public static void addType(SpawnerType type) {
        spawners.add(type);
    }

    public static void removeType(SpawnerType type) {
        spawners.remove(type);
    }

    public static List<SpawnerType> getSpawnerTypes() {
        return spawners;
    }

    public static Optional<SpawnerType> getSpawnerType(String name) {
        return spawners.stream().filter(spawner -> spawner.getName().equalsIgnoreCase(name)).findFirst();
    }

    public static void readSpawnersTypesFromFile(CustomConfig spawnerConfig) {
        spawners.clear();
        spawnerConfig.getKeys(false).forEach(key -> spawners.add(new SpawnerType((int)spawnerConfig.getObject("timeBetweenSpawns", key), (int)spawnerConfig.getObject("amount", key), (int)spawnerConfig.getObject("timeBetweenTiers", key), (int)spawnerConfig.getObject("maxTier", key), spawnerConfig.getValue("resource", key), spawnerConfig.getValue("headBlock", key), spawnerConfig.getValue("spawnerName", key), key)));
    }
}
