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
    public static int id = 0;
    public static HashMap<EntityPlayer, Location> NPCs = new HashMap<EntityPlayer, Location> ();
    public static HashMap<EntityPlayer, Integer> NPCsid = new HashMap<EntityPlayer, Integer>();
    
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
        while(NPCsid.containsValue(id)) {
            ++id;
        }
        NPCsid.put(npc, id);
    }
    
    /**
     * Create an NPC with a special id and tell every player to display it. Usefull when loading the config file
     * @param name
     * @param location
     * @param NPCid
     */
    public static void createNPC(String name, Location location, int NPCid) {
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
        NPCsid.put(npc, NPCid);
    }
    
    /**
     * Remove an NPC 
     * @param customNPC
     */
    public static boolean removeNPC(int id) {
        EntityPlayer customNPC = null;
        for (EntityPlayer NPC : NPCs.keySet()) {
            System.out.println("ID: " + NPCsid.get(NPC));
            if (NPCsid.get(NPC) == id) {
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
            NPCsid.remove(customNPC);
            SLAPI.deleteNPC(customNPC.getName(), id);
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * Load every NPCs so that each player can see them.
     */
    /*public static void loadNPCs() {
        for (EntityPlayer NPC : NPCs.keySet()) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
                connection.sendPacket(new PacketPlayOutPlayerInfo(EnumPlayerInfoAction.ADD_PLAYER, NPC));
                connection.sendPacket(new PacketPlayOutNamedEntitySpawn(NPC));
            }
        }
    }*/
    
    /**
     * Load every NPCs for one player.
     * @param player
     */
    public static void loadNPCs(Player player) {
        for (EntityPlayer NPC : NPCs.keySet()) {
            PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
            connection.sendPacket(new PacketPlayOutPlayerInfo(EnumPlayerInfoAction.ADD_PLAYER, NPC));
            connection.sendPacket(new PacketPlayOutNamedEntitySpawn(NPC));
        }
    }
    
    /**
     * Check if the given npc exist.
     * @param customNPC EntityPlayer
     * @return boolean
     */
    public static boolean NPCExist(EntityPlayer customNPC) {
        if (NPCs.containsKey(customNPC)) {
            return true;
        } else {
            return false;
        }
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

    public static int getId(EntityPlayer NPC) {
        if (NPCExist(NPC)) {
            int npcId = NPCsid.get(NPC);
            return npcId;
        } else {
            return -1;
        }
    }
}
