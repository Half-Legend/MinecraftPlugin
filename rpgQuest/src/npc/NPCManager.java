package npc;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.craftbukkit.v1_13_R2.CraftServer;
import org.bukkit.craftbukkit.v1_13_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_13_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;

import com.mojang.authlib.GameProfile;

import minecraftPG.rpgQuest.RpgQuest;
import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_13_R2.EntityPlayer;
import net.minecraft.server.v1_13_R2.MinecraftServer;
import net.minecraft.server.v1_13_R2.PacketPlayOutNamedEntitySpawn;
import net.minecraft.server.v1_13_R2.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_13_R2.PlayerConnection;
import net.minecraft.server.v1_13_R2.PlayerInteractManager;
import net.minecraft.server.v1_13_R2.WorldServer;
import net.minecraft.server.v1_13_R2.PacketPlayOutPlayerInfo.EnumPlayerInfoAction;

/**
 * Class managing all the NPCs.
 * @author HalfLegend
 *
 */
public class NPCManager {
    
    private static RpgQuest rpgQuest;
    public static ArrayList<EntityPlayer> NPCs = new ArrayList<EntityPlayer>();
    
    public NPCManager(RpgQuest instance) {
        rpgQuest = instance;
    }
    
    public static void createNPC(String name, Player player) {
        MinecraftServer server = ((CraftServer)rpgQuest.getServer()).getServer();
        WorldServer world = ((CraftWorld) rpgQuest.getServer().getWorlds().get(0)).getHandle();
        EntityPlayer npc = new EntityPlayer(server, world, new GameProfile(UUID.fromString("05f56d06-5f27-4c32-a486-e119d8e0035d"),name), new PlayerInteractManager(world));
        npc.teleportTo(player.getLocation(), false);
        PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
        connection.sendPacket(new PacketPlayOutPlayerInfo(EnumPlayerInfoAction.ADD_PLAYER, npc));
        connection.sendPacket(new PacketPlayOutNamedEntitySpawn(npc));
        NPCs.add(npc);
    }
    
    public static void displayNPCs() {
        for(int i = 0; i < NPCs.size(); ++i) {
            System.out.println("This is the " + i + "e NPC: " + NPCs.get(i).getName());
        }
    }
    
    /**
     * Add an NPC to the list of NPCs.
     * @param customNPC EntityPlayer
     */
    public static void addNPC(EntityPlayer customNPC) {
        //NPCs.add(customNPC);
    }
    
    /**
     * Remove the given NPC.
     * @param customNPC EntityPlayer
     */
    public static void removeNPC(EntityPlayer customNPC) {
        if(NPCs.contains(customNPC)) {
            NPCs.remove(customNPC);
        }
    }
    
    /**
     * Check if the given npc exist.
     * @param customNPC EntityPlayer
     * @return boolean
     */
    public static boolean NPCExist(EntityPlayer customNPC) {
        return NPCs.contains(customNPC);
    }
    
    /**
     * Gets the NPCs field.
     * @return ArrayList<EntityPlayer>
     */
    public static ArrayList<EntityPlayer> getNPCArray() {
        return NPCs;
    }

    public static RpgQuest getPlugin() {
        return rpgQuest;
    }
}
