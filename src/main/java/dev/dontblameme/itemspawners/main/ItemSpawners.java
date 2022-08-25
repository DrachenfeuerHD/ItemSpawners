package dev.dontblameme.itemspawners.main;

import dev.dontblameme.itemspawners.commands.ConfigReload;
import dev.dontblameme.itemspawners.commands.DeleteSpawner;
import dev.dontblameme.itemspawners.commands.GetSpawners;
import dev.dontblameme.itemspawners.commands.SetSpawner;
import dev.dontblameme.itemspawners.spawner.SpawnerManager;
import dev.dontblameme.itemspawners.spawner.SpawnerTypesManager;
import dev.dontblameme.itemspawners.tabcomplete.CustomTabCompletor;
import dev.dontblameme.utilsapi.config.customconfig.CustomConfig;
import org.bukkit.plugin.java.JavaPlugin;

public final class ItemSpawners extends JavaPlugin {

    private static CustomConfig config;

    @Override
    public void onEnable() {
        config = new CustomConfig(this, "config.yml");
        CustomConfig spawnerTypes = new CustomConfig(this, "spawnerTypes.yml");
        CustomConfig spawners = new CustomConfig(this, "permanentSpawners.yml");

        getCommand("SetSpawner").setExecutor(new SetSpawner());
        getCommand("GetSpawners").setExecutor(new GetSpawners());
        getCommand("DeleteSpawner").setExecutor(new DeleteSpawner());
        getCommand("ConfigReload").setExecutor(new ConfigReload());

        getCommand("SetSpawner").setTabCompleter(new CustomTabCompletor());
        getCommand("GetSpawners").setTabCompleter(new CustomTabCompletor());
        getCommand("DeleteSpawner").setTabCompleter(new CustomTabCompletor());
        getCommand("ConfigReload").setTabCompleter(new CustomTabCompletor());

        SpawnerTypesManager.readSpawnersTypesFromFile(spawnerTypes);
        SpawnerManager.readSpawnersFromFile(spawners);
    }

    @Override
    public void onDisable() {
        SpawnerManager.removeEverySpawner();
    }

    public static CustomConfig getCustomConfig() {
        return config;
    }
}
