package npc;

import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_13_R2.CraftServer;
import org.bukkit.craftbukkit.v1_13_R2.CraftWorld;
import org.bukkit.entity.Player;

import com.mojang.authlib.GameProfile;

import minecraftPG.rpgQuest.RpgQuest;
import net.minecraft.server.v1_13_R2.EntityPlayer;
import net.minecraft.server.v1_13_R2.MinecraftServer;
import net.minecraft.server.v1_13_R2.PlayerInteractManager;
import net.minecraft.server.v1_13_R2.WorldServer;

/**
 * Class allowing the creation of a customNpc.
 * @author geoff
 *
 */
public class CustomNPC {
    
    private EntityPlayer npc;
    //fromString("feebfe1a-ce55-4fe7-b68e-fe6996670c73")
    /**
     * Constructor.
     * @param name String
     * @param location Location
     */
    public CustomNPC(RpgQuest rpgQuest, String name, Location location) {
        MinecraftServer server = ((CraftServer)rpgQuest.getServer()).getServer();
        WorldServer world = ((CraftWorld) rpgQuest.getServer().getWorlds().get(0)).getHandle();
        npc = new EntityPlayer(server, world, new GameProfile(UUID.fromString("05f56d06-5f27-4c32-a486-e119d8e0035d"),ChatColor.RED + "Ma chérie <3"), new PlayerInteractManager(world));
        //npc.teleportTo(location, false);
    }
    
    /**
     * Get the name.
     * @return name
     */
    public String getName() {
        return npc.getName();
    }
    
    /**
     * Get the location.
     * @return location
     */
    public Location getLocation() {
        Player npcPlayer = (Player)npc;
        return npcPlayer.getLocation();
    }
}
