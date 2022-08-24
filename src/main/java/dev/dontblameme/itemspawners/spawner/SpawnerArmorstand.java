package dev.dontblameme.itemspawners.spawner;

import dev.dontblameme.itemspawners.main.ItemSpawners;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class SpawnerArmorstand {

    private final String customName;
    private final Material headBlock;
    private ArmorStand armorStand;

    public SpawnerArmorstand(String customName) {
        this.customName = customName;
        this.headBlock = null;
    }

    public SpawnerArmorstand(Material headBlock) {
        this.headBlock = headBlock;
        this.customName = "";
    }

    public void spawn(World world, Location location) {
        armorStand = world.spawn(location, ArmorStand.class);

        armorStand.setInvisible(true);
        armorStand.setBasePlate(false);
        armorStand.setMarker(true);
        armorStand.setInvulnerable(true);

        if(!customName.isEmpty()) {
            armorStand.setCustomNameVisible(true);
            armorStand.setCustomName(customName);
        }

        if(headBlock != null)
            armorStand.getEquipment().setHelmet(new ItemStack(headBlock), true);
    }

    public void updateCustomName(String customName) {
        armorStand.setCustomName(customName);
    }

    public void moveUp(double amount) {
        armorStand.teleport(armorStand.getLocation().add(0, amount, 0));
    }

    public void moveDown(double amount) {
        armorStand.teleport(armorStand.getLocation().subtract(0, amount, 0));
    }

    public void remove(boolean isSync) {
        if(armorStand.isDead()) return;

        armorStand.setMarker(false);
        armorStand.setPersistent(false);
        armorStand.setInvisible(false);
        if(isSync)
            armorStand.remove();
        else
            Bukkit.getScheduler().scheduleSyncDelayedTask(JavaPlugin.getProvidingPlugin(ItemSpawners.class), () -> armorStand.remove(), 0);
    }

    public boolean isAlive() {
        return !armorStand.isDead();
    }

    public float getYaw() {
        return armorStand.getEyeLocation().getYaw();
    }

    public void setRotation(float yaw, float pitch) {
        armorStand.setRotation(yaw, pitch);
    }

}
