package minecraftPG.Economy.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import minecraftPG.Economy.EconomyManager;
import net.md_5.bungee.api.ChatColor;

/**
 * Class implementing the /balance command.
 * @author HalfLegend
 *
 */
public class BalanceCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (args.length != 0) {
            sender.sendMessage(ChatColor.RED + "There is no arguments required for this command.");
            sender.sendMessage(ChatColor.GREEN + "Type /balance to see your account.");
            return true;
        } else {
            sender.sendMessage(ChatColor.DARK_GRAY + "You have: " + ChatColor.GREEN + EconomyManager.getBalances(sender.getName()) + "$");
        }
        return true;
    }

}
