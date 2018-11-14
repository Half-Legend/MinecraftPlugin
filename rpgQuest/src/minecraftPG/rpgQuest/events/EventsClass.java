package minecraftPG.rpgQuest.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import minecraftPG.rpgQuest.RpgQuest;

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
            //CustomVillager guard = new CustomVillager((World) player.getWorld(), player);
            //HumanEntity guard = player.getWorld().spawn(player.getLocation(), HumanEntity.class);
            /*guard.setSilent(true);
            
            guard.setCustomName(ChatColor.BLUE + "Guard");
            guard.setCustomNameVisible(true);
            
            EntityEquipment equipment = guard.getEquipment();
            
            //craftVillager.getP
            equipment.setBoots(new ItemStack(Material.IRON_BOOTS));
            equipment.setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
            equipment.setLeggings(new ItemStack(Material.IRON_LEGGINGS));
            equipment.setHelmet(new ItemStack(Material.IRON_HELMET));
            equipment.setItemInMainHand(new ItemStack(Material.IRON_SWORD));*/

        }
    }
}
