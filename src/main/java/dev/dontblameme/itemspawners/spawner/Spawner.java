package dev.dontblameme.itemspawners.spawner;

import dev.dontblameme.itemspawners.main.ItemSpawners;
import dev.dontblameme.utilsapi.utils.TextParser;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;

import java.text.DecimalFormat;
import java.util.Random;

@Getter
public class Spawner {

    private final World world;
    private final Location location;
    private final SpawnerType type;
    private final DecimalFormat df = new DecimalFormat("0.00");
    private double timeUntilNextTier;
    private double timeUntilNextSpawn;
    private double currentTier = 1D;
    private final int id;
    SpawnerArmorstand headBlockStand;
    SpawnerArmorstand customNameStand;
    SpawnerArmorstand untilNextSpawnStand;
    SpawnerArmorstand currentTierStand;
    SpawnerArmorstand timeUntilNextTierStand;


    public Spawner(World world, Location location, SpawnerType type) {
        this.world = world;
        this.location = location;
        this.type = type;
        id = new Random().nextInt();
    }

    public void remove(boolean isSync) {
        headBlockStand.remove(isSync);
        customNameStand.remove(isSync);
        untilNextSpawnStand.remove(isSync);
        currentTierStand.remove(isSync);
        timeUntilNextTierStand.remove(isSync);
    }

    public void create() {
        SpawnerManager.addSpawner(this);

        location.setX(location.getBlockX() + 0.5D);
        location.setY(location.getBlockY());
        location.setZ(location.getBlockZ() + 0.5D);

        headBlockStand = new SpawnerArmorstand(type.getHeadBlock());
        customNameStand = new SpawnerArmorstand(type.getCustomName());
        untilNextSpawnStand = new SpawnerArmorstand("§7Next Drop in: §6" + df.format(timeUntilNextSpawn / 20D) + "s");
        currentTierStand = new SpawnerArmorstand("§7Current Tier: §e" + currentTier);
        timeUntilNextTierStand = new SpawnerArmorstand("§7Next Tier in: §2" + df.format(timeUntilNextTier / 20D) + "s");

        headBlockStand.spawn(world, location);
        customNameStand.spawn(world, location.clone().add(0, 2.5, 0));
        untilNextSpawnStand.spawn(world, location.clone().add(0, 2.25, 0));
        currentTierStand.spawn(world, location.clone().add(0, 2, 0));
        timeUntilNextTierStand.spawn(world, location.clone().add(0, 1.75, 0));

        timeUntilNextTier = type.getTimeBetweenTiers() * 20D;
        timeUntilNextSpawn = (type.getTimeBetweenSpawns() * 20D) / currentTier;

        new Thread(() -> {
            int upCount = 0;
            int downCount = 0;
            while(headBlockStand.isAlive()) {
                float yaw = headBlockStand.getYaw() + 5;

                if(yaw > 0) {
                    downCount++;
                    headBlockStand.moveUp(0.01);
                    customNameStand.moveUp(0.01);
                    untilNextSpawnStand.moveUp(0.01);
                    currentTierStand.moveUp(0.01);
                    timeUntilNextTierStand.moveUp(0.01);
                } else if(yaw < 1) {
                    upCount++;
                    headBlockStand.moveDown(0.01);
                    customNameStand.moveDown(0.01);
                    untilNextSpawnStand.moveDown(0.01);
                    currentTierStand.moveDown(0.01);
                    timeUntilNextTierStand.moveDown(0.01);
                }

                if(yaw >= 180) yaw = -180;

                untilNextSpawnStand.updateCustomName(TextParser.parseHexAndCodes(ItemSpawners.getCustomConfig().getValue("timeUntilNextSpawn", "spawnerMessages").replace("%timeUntilNextSpawn%", df.format(timeUntilNextSpawn / 20D))));
                currentTierStand.updateCustomName(TextParser.parseHexAndCodes(ItemSpawners.getCustomConfig().getValue("currentTier", "spawnerMessages").replace("%currentTier%", (int)currentTier+"")));
                timeUntilNextTierStand.updateCustomName(TextParser.parseHexAndCodes(ItemSpawners.getCustomConfig().getValue("timeUntilNextTier", "spawnerMessages").replace("%timeUntilNextTier%", df.format(timeUntilNextTier / 20D))));

                headBlockStand.setRotation(yaw, 0);
                customNameStand.setRotation(yaw, 0);
                untilNextSpawnStand.setRotation(yaw, 0);
                currentTierStand.setRotation(yaw, 0);
                timeUntilNextTierStand.setRotation(yaw, 0);

                timeUntilNextSpawn--;
                timeUntilNextTier--;

                if(timeUntilNextSpawn <= 0) {
                    Bukkit.getScheduler().scheduleSyncDelayedTask(ItemSpawners.getPlugin(ItemSpawners.class), () -> world.dropItemNaturally(location, new ItemStack(type.getResource(), type.getAmount())), 0);
                    timeUntilNextSpawn = (type.getTimeBetweenSpawns() * 20D) / currentTier;
                }

                if(type.getMaxTier() > currentTier && timeUntilNextTier <= 0) {
                    currentTier++;
                    timeUntilNextTier = type.getTimeBetweenTiers() * 20D;
                }

                if(currentTier >= type.getMaxTier() && timeUntilNextTierStand.isAlive())
                    timeUntilNextTierStand.remove(false);

                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();

    }

}
