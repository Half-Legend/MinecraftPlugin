package npc;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_13_R2.CraftServer;
import org.bukkit.craftbukkit.v1_13_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_13_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;

import com.mojang.authlib.GameProfile;

import minecraftPG.rpgQuest.RpgQuest;
import net.minecraft.server.v1_13_R2.EntityPlayer;
import net.minecraft.server.v1_13_R2.MinecraftServer;
import net.minecraft.server.v1_13_R2.PacketLoginOutDisconnect;
import net.minecraft.server.v1_13_R2.PacketPlayOutNamedEntitySpawn;
import net.minecraft.server.v1_13_R2.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_13_R2.PacketPlayOutPlayerInfo.EnumPlayerInfoAction;
import net.minecraft.server.v1_13_R2.PlayerConnection;
import net.minecraft.server.v1_13_R2.PlayerInteractManager;
import net.minecraft.server.v1_13_R2.WorldServer;

/**
 * Class managing all the NPCs.
 * @author HalfLegend
 *
 */
public class NPCManager {
    
    private static RpgQuest rpgQuest;
    public static HashMap<EntityPlayer, Location> NPCs = new HashMap<EntityPlayer, Location>();
   // public static ArrayList<EntityPlayer> NPCs = new ArrayList<EntityPlayer>();
    
    public NPCManager(RpgQuest instance) {
        rpgQuest = instance;
    }
    
    /**
     * Create an NPC and tell to every player to display it.
     * @param name String
     * @param location Location
     */
    public static void createNPC(String name, Location location) {
        MinecraftServer server = ((CraftServer)rpgQuest.getServer()).getServer();
        WorldServer world = ((CraftWorld) rpgQuest.getServer().getWorlds().get(0)).getHandle();
        EntityPlayer npc = new EntityPlayer(server, world, new GameProfile(UUID.fromString("05f56d06-5f27-4c32-a486-e119d8e0035d"),name), new PlayerInteractManager(world));
        npc.teleportTo(location, false);
        
        //Tell to every player to display the NPC
        for (Player allPlayer : Bukkit.getOnlinePlayers()) {
            PlayerConnection connection = ((CraftPlayer) allPlayer).getHandle().playerConnection;
            connection.sendPacket(new PacketPlayOutPlayerInfo(EnumPlayerInfoAction.ADD_PLAYER, npc));
            connection.sendPacket(new PacketPlayOutNamedEntitySpawn(npc));
        }   
        NPCs.put(npc, location);
    }
    
    /**
     * Remove an NPC 
     * @param customNPC
     */
    public static boolean removeNPC(int id) {
        EntityPlayer customNPC = null;
        for (EntityPlayer NPC : NPCs.keySet()) {
            System.out.println("ID: " + NPC.getId());
            if (NPC.getId() == id) {
                System.out.println("customNPC has been set");
                customNPC = NPC;
            }
        }
        if (customNPC != null) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
                connection.sendPacket(new PacketPlayOutPlayerInfo(EnumPlayerInfoAction.REMOVE_PLAYER,customNPC));
            }
            NPCs.remove(customNPC);
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * Check if the given npc exist.
     * @param customNPC EntityPlayer
     * @return boolean
     */
    public static boolean NPCExist(EntityPlayer customNPC) {
        //return NPCs.contains(customNPC);
        return false;
    }
    
    /**
     * Gets the NPCs field.
     * @return ArrayList<EntityPlayer>
     */
    public static HashMap<EntityPlayer, Location> getNPCMap() {
        return NPCs;
    }

    public static RpgQuest getPlugin() {
        return rpgQuest;
    }
}
