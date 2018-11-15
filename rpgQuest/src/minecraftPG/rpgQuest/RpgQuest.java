package minecraftPG.rpgQuest;

import org.bukkit.plugin.java.JavaPlugin;

import minecraftPG.rpgQuest.events.EventsClass;
import npc.NPCCommands;
import npc.NPCManager;
import npc.SLAPI;

public class RpgQuest extends JavaPlugin{
    
    //private EntityPlayer npc;
    
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new EventsClass(getPlugin(this.getClass())), this);
        getCommand("NPC").setExecutor(new NPCCommands(getPlugin(this.getClass())));
        new NPCManager(getPlugin(this.getClass()));
        //getServer().getPluginManager().registerEvents(this, this);
        SLAPI.loadNPC();
    }
    
    public void onDisable() {
        SLAPI.saveNPC();
    }
    
    /*@EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        npc.teleportTo(event.getPlayer().getLocation(), false);
        
        PlayerConnection connection = ((CraftPlayer) event.getPlayer()).getHandle().playerConnection;
        connection.sendPacket(new PacketPlayOutPlayerInfo(EnumPlayerInfoAction.ADD_PLAYER, npc));
        connection.sendPacket(new PacketPlayOutNamedEntitySpawn(npc));
    }*/
}
