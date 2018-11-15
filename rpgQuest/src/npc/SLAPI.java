package npc;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import minecraftPG.rpgQuest.RpgQuest;
import net.minecraft.server.v1_13_R2.EntityPlayer;

/**
 * Class loading & saving the config file.
 * @author HalfLegend
 *
 */
public class SLAPI {
    
    private static RpgQuest plugin = NPCManager.getPlugin();
    
    /**
     * Save all the NPCs into the config file.
     */
    public static void saveNPC() {
        for (EntityPlayer NPC : NPCManager.getNPCMap().keySet()) {
            Location location = NPCManager.getNPCMap().get(NPC);
            double x = location.getX();
            double y = location.getY();
            double z = location.getZ();
            String[] list = {"x: " + x, "y: " + y, "z: " + z, "id: " + NPCManager.getId(NPC)};
            plugin.getConfig().set("NPC." + NPC.getName(), list);
        }
        plugin.saveConfig();
    }
    
    /**
     * Load each NPCs from the config file.
     */
    public static void loadNPC() {
        if (plugin.getConfig().contains("NPC")) {
            double x;
            double y;
            double z;
            int id;
            World world = Bukkit.getWorlds().get(0);
            
            for (String name : plugin.getConfig().getConfigurationSection("NPC").getKeys(false)) {
                System.out.println("Configuration loading: " + name);
                List pos = plugin.getConfig().getList("NPC." + name);
                x = getDouble((String) pos.get(0));
                y = getDouble((String) pos.get(1));
                z = getDouble((String) pos.get(2));
                id = getId((String) pos.get(3));
                
                Location location = new Location(world, x, y, z);
                NPCManager.createNPC(name, location, id);
            }
        }
        /*
        if(plugin.getConfig().contains("balance")) {
            for (String player : plugin.getConfig().getConfigurationSection("balance").getKeys(false)) {
                EconomyManager.setBalance(player, plugin.getConfig().getDouble("balance."+ player));
            }
        }*/
    }
    
    /**
     * Delete an NPC from the config file.
     * @param name String
     * @param id int
     */
    public static void deleteNPC(String name, int id) {
        if (plugin.getConfig().contains("NPC")) {
            for (String NPCName : plugin.getConfig().getConfigurationSection("NPC").getKeys(false)) {
                if (NPCName.equalsIgnoreCase(name)) {
                    List pos = plugin.getConfig().getList("NPC." + name);
                    int NPCid = getId((String) pos.get(3));
                    if (NPCid == id) {
                        plugin.getConfig().set("NPC." + name, null);
                    }
                }
            }
        }
    }

    private static int getId(String line) {
        Pattern pattern = Pattern.compile("\\D* (\\d+)$");
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            System.out.println("id found");
            for(int i=0; i <= matcher.groupCount(); i++) {
                // affichage de la sous-chaîne capturée
                System.out.println("Groupe " + i + " : " + matcher.group(i));
            }
            int id = Integer.parseInt(matcher.group(1));
            return id;
        } else {
            System.out.println("Could not find the id");
        }
        return 0;
    }

    private static double getDouble(String line) {
        Pattern pattern = Pattern.compile("\\D* (-?\\d+\\.\\d+)$");
        Matcher matcher = pattern.matcher(line);

        if (matcher.find()) {
            System.out.println("Found! :)");
            double res = Double.parseDouble(matcher.group(1));
            return res;
        } else {
            System.out.println("Error in loading the coordinates of an NPC");
        }
        return 0;
    }
}
