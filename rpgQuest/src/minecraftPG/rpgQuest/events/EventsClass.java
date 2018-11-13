package minecraftPG.rpgQuest.events;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;

import minecraftPG.rpgQuest.RpgQuest;
import net.md_5.bungee.api.ChatColor;

/**
 * Class managing the events.
 * @author HalfLegend
 *
 */
public class EventsClass implements Listener {

    private RpgQuest plugin;
    
    public EventsClass(RpgQuest instance) {
        plugin = instance;
    }
    
    @EventHandler
    public void onInteract(PlayerInteractEvent event) {

        Player player = event.getPlayer();
        Action action = event.getAction();
        if(action.equals(Action.LEFT_CLICK_BLOCK)) {
            player.sendMessage(ChatColor.RED + "Clicking the air");
            Location playerLocation = player.getLocation();
            //CustomVillager guard = new CustomVillager((World) player.getWorld(), player);
            Zombie craftVillager = player.getWorld().spawn(player.getLocation(), Zombie.class);
            craftVillager.setAI(false);
            craftVillager.setSilent(true);
            craftVillager.setInvulnerable(true);
            
            craftVillager.setCustomName(ChatColor.BLUE + "Guard");
            craftVillager.setCustomNameVisible(true);
            
            EntityEquipment equipment = craftVillager.getEquipment();
            
            //craftVillager.getP
            equipment.setBoots(new ItemStack(Material.IRON_BOOTS));
            equipment.setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
            equipment.setLeggings(new ItemStack(Material.IRON_LEGGINGS));
            equipment.setHelmet(new ItemStack(Material.IRON_HELMET));
            equipment.setItemInMainHand(new ItemStack(Material.IRON_SWORD));
        }
    }
}
