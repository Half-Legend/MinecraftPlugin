package minecraftPG.Economy.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;

import minecraftPG.Economy.EconomyManager;
import net.md_5.bungee.api.ChatColor;

public class EventsClass implements Listener {
    
    private Plugin plugin;

    public EventsClass(Plugin plugin) {
        this.plugin = plugin;
    }
    
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        
        if(!player.hasPlayedBefore()) {
            plugin.getServer().broadcastMessage(ChatColor.BLUE + "Welcome to " + player.getName() + "!");
        } else {
            player.sendMessage(ChatColor.BLUE + "Welcome bakc " + player.getName());
        }
        if (!EconomyManager.hasAccount(player.getName())) {
            EconomyManager.setBalance(player.getName(), 0);
        }
        
    }
}
