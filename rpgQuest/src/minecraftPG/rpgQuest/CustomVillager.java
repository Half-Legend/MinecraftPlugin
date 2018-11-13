package minecraftPG.rpgQuest;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;

import net.minecraft.server.v1_13_R2.EntityVillager;

public class CustomVillager extends EntityVillager{

    public CustomVillager(net.minecraft.server.v1_13_R2.World world, Player player) {
        /*super(world);
        Villager craftVillager = player.getWorld().spawn(player.getLocation(), Villager.class);
        
        craftVillager.setCustomName(ChatColor.BLUE + "Guard");
        craftVillager.setCustomNameVisible(true);
        
        EntityEquipment equipment = craftVillager.getEquipment();
        
        equipment.setBoots(new ItemStack(Material.IRON_BOOTS));
        equipment.setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
        equipment.setLeggings(new ItemStack(Material.IRON_LEGGINGS));
        equipment.setHelmet(new ItemStack(Material.IRON_HELMET));
        equipment.setItemInMainHand(new ItemStack(Material.IRON_SWORD));*/
    }

    /*public CustomVillager(World world, Player player) {
        super();
        Villager craftVillager = player.getWorld().spawn(player.getLocation(), Villager.class);
        
        craftVillager.setCustomName(ChatColor.BLUE + "Guard");
        craftVillager.setCustomNameVisible(true);
        
        EntityEquipment equipment = craftVillager.getEquipment();
        
        equipment.setBoots(new ItemStack(Material.IRON_BOOTS));
        equipment.setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
        equipment.setLeggings(new ItemStack(Material.IRON_LEGGINGS));
        equipment.setHelmet(new ItemStack(Material.IRON_HELMET));
        equipment.setItemInMainHand(new ItemStack(Material.IRON_SWORD));
        
        
    }*/

}
