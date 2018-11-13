package minecraftPG.rpgQuest;

import org.bukkit.plugin.java.JavaPlugin;

import minecraftPG.rpgQuest.events.EventsClass;

public class RpgQuest extends JavaPlugin{
    
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new EventsClass(getPlugin(this.getClass())), this);
        
    }
    
    public void onDisable() {
        
    }
}
