package dev.dontblameme.itemspawners.spawner;

import dev.dontblameme.utilsapi.config.customconfig.CustomConfig;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SpawnerManager {

    private SpawnerManager() {}

    private static final List<Spawner> spawners = new ArrayList<>();

    public static void addSpawner(Spawner type) {
        spawners.add(type);
    }

    public static void removeSpawner(Spawner type) {
        spawners.remove(type);
    }

    public static List<Spawner> getSpawners() {
        return spawners;
    }

    public static Optional<Spawner> getSpawner(int id) {
        return spawners.stream().filter(spawner -> spawner.getId() == id).findFirst();
    }

    public static void readSpawnersFromFile(CustomConfig spawnerConfig) {
        spawnerConfig.getKeys(false).forEach(key -> {
            World world = Bukkit.getWorld(spawnerConfig.getValue("world", key));

            new Spawner(world, new Location(world, (int)spawnerConfig.getObject("x", key), (int)spawnerConfig.getObject("y", key), (int)spawnerConfig.getObject("z", key)), SpawnerTypesManager.getSpawnerType(key).get()).create();
        });
    }

    public static void removeEverySpawner() {
        spawners.forEach(s -> s.remove(true));
        spawners.clear();
    }
}
