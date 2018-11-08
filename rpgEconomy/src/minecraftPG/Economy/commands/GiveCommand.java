package minecraftPG.Economy.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import minecraftPG.Economy.EconomyManager;
import net.md_5.bungee.api.ChatColor;

public class GiveCommand implements CommandExecutor{

    private Plugin plugin;
    
    public GiveCommand(Plugin plugin) {
        this.plugin = plugin;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (args.length != 2) {
            sender.sendMessage(ChatColor.RED + "This command require 2 arguments.");
            sender.sendMessage(ChatColor.GREEN + "Type: /give <Player> <Amount>");
            return true;
        } else {
            Player receiver = null;
            try {
                receiver = plugin.getServer().getPlayer(args[0]);
            } catch (Exception e) {
                sender.sendMessage(ChatColor.RED + "Invalid name of player!");
            }
            if (receiver != null) {
                double amount = 0;
                try {
                    amount = Double.parseDouble(args[1]);
                } catch (Exception e){
                    sender.sendMessage(ChatColor.RED + "Invalid amount of money!");
                    return true;
                }
              //We need to make sure that the player has enough money
                if(EconomyManager.hasEnoughtMoney(sender.getName(), amount) && amount != 0) {
                    EconomyManager.add(sender.getName(), -amount);
                    EconomyManager.add(receiver.getName(), amount);
                    
                    sender.sendMessage(ChatColor.DARK_GRAY + "You sent " + amount + "$ to " + receiver.getName());
                    receiver.sendMessage(ChatColor.GREEN + sender.getName() + " gave you " + amount + "$");
                } else {
                    sender.sendMessage(ChatColor.RED + "You don't have enought money to do this.");
                    return true;
                }
            } else {
                sender.sendMessage(ChatColor.RED + "Invalid target.");
            }
        }
        return true;
    }

}
