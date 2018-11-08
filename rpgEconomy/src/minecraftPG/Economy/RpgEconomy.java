package minecraftPG.Economy;

import org.bukkit.plugin.java.JavaPlugin;

import minecraftPG.Economy.commands.BalanceCommand;
import minecraftPG.Economy.commands.EconomyCommand;
import minecraftPG.Economy.events.EventsClass;
import net.md_5.bungee.api.ChatColor;

/**
 * Main class.
 * @author HalfLegend
 *
 */
public class RpgEconomy extends JavaPlugin{
    
    /**
     * Behavior when the server start.
     */
    public void onEnable() {
        getCommand("Economy").setExecutor(new EconomyCommand());
        getCommand("Balance").setExecutor(new BalanceCommand());
        new EconomyManager(this);
        SLAPI.loadBalances();
        getServer().getPluginManager().registerEvents(new EventsClass(getPlugin(this.getClass())), this);
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "\n\nPlugin as been enabled\n\n");
    }
    
    /**
     * Behavior when the server stop.
     */
    public void onDisable() {
        SLAPI.saveBalances();
    }
}
