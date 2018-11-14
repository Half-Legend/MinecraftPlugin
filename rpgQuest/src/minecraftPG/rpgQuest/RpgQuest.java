package minecraftPG.rpgQuest;

import org.bukkit.plugin.java.JavaPlugin;
import minecraftPG.rpgQuest.events.EventsClass;
import npc.NPCCommands;
import npc.NPCManager;

public class RpgQuest extends JavaPlugin{
    
    //private EntityPlayer npc;
    
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new EventsClass(getPlugin(this.getClass())), this);
        getCommand("NPC").setExecutor(new NPCCommands(getPlugin(this.getClass())));
        new NPCManager(getPlugin(this.getClass()));
        //getServer().getPluginManager().registerEvents(this, this);
    }
    
    public void onDisable() {
        
    }
    
    /*@EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        npc.teleportTo(event.getPlayer().getLocation(), false);
        
        PlayerConnection connection = ((CraftPlayer) event.getPlayer()).getHandle().playerConnection;
        connection.sendPacket(new PacketPlayOutPlayerInfo(EnumPlayerInfoAction.ADD_PLAYER, npc));
        connection.sendPacket(new PacketPlayOutNamedEntitySpawn(npc));
    }*/
}
