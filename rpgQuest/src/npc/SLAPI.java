package npc;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import minecraftPG.rpgQuest.RpgQuest;
import net.minecraft.server.v1_13_R2.EntityPlayer;

/**
 * Class loading & saving the config file.
 * @author HalfLegend
 *
 */
public class SLAPI {
    
    private static RpgQuest plugin = NPCManager.getPlugin();
    
    public static void saveNPC() {
        
        for (EntityPlayer NPC : NPCManager.getNPCMap().keySet()) {
            plugin.getConfig().set("NPC." + NPC, NPCManager.getNPCMap().get(NPC));
        }
        
        /*for (String player : EconomyManager.getBalanceMap().keySet()) {
            plugin.getConfig().set("balance." + player, EconomyManager.getBalanceMap().get(player));
        }*/
        plugin.saveConfig();
    }
    
    /**
     * 
     */
    public static void loadNPC() {
        /*
        if(plugin.getConfig().contains("balance")) {
            for (String player : plugin.getConfig().getConfigurationSection("balance").getKeys(false)) {
                EconomyManager.setBalance(player, plugin.getConfig().getDouble("balance."+ player));
            }
        }*/
    }
}
