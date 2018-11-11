package minecraftPG.Economy.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import minecraftPG.Economy.EconomyManager;
import net.md_5.bungee.api.ChatColor;

/**
 * Class implementing the /economy ... commands
 * @author HalfLegend
 *
 */
public class EconomyCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (sender.hasPermission("EconomyPerm")) {
            if (args.length != 3) {
                sender.sendMessage(ChatColor.RED + "This command requires 3 arguments!");
                sender.sendMessage(ChatColor.GREEN + "Type: /economy <add/remove/set> <player> <amount>");
                return true;
            }
            if (args[0].equalsIgnoreCase("add") || args[0].equalsIgnoreCase("remove") || args[0].equalsIgnoreCase("set")) {
                if (!EconomyManager.hasAccount(args[1])) {
                    sender.sendMessage(ChatColor.RED + "This player doesn't have an account");
                } else {
                    double amount = 0;
                    try {
                        amount = Double.parseDouble(args[2]);
                    } catch (Exception e){
                        sender.sendMessage(ChatColor.RED + "Invalid amount of money!");
                    }
                    if (args[0].equalsIgnoreCase("add")) {
                        EconomyManager.add(args[1], amount);
                        sender.sendMessage(ChatColor.DARK_GRAY + "You just received: " + ChatColor.GREEN + amount + "$");
                    } else if (args[0].equalsIgnoreCase("remove")) {
                        EconomyManager.add(args[1], -amount);
                        sender.sendMessage(ChatColor.DARK_GRAY + "You spent: " + ChatColor.RED + amount + "$");
                    } else {
                        EconomyManager.setBalance(args[1], amount);
                        sender.sendMessage(ChatColor.DARK_GRAY + "Your balance has been set to: " + ChatColor.GREEN + amount + "$");
                    }
                }
            } else {
                sender.sendMessage(ChatColor.RED + "Invalid parameter.");
                sender.sendMessage(ChatColor.GREEN + "Try with: <add/remove/set>");
            }
        } else {
            sender.sendMessage(ChatColor.RED + "You don't have the permissions to use this command.");
        }
        return true;
    }

}
