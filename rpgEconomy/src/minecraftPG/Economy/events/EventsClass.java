package minecraftPG.Economy.events;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
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
            player.sendMessage(ChatColor.BLUE + "Welcome back " + player.getName());
        }
        if (!EconomyManager.hasAccount(player.getName())) {
            EconomyManager.setBalance(player.getName(), 0);
        }
        
    }

    @EventHandler
    public void onSignChange(SignChangeEvent event) {
        Player player = event.getPlayer();
        if(event.getLine(0).equalsIgnoreCase("[Buy]")) {
            if(!player.hasPermission("shop")) {
                event.setCancelled(true);
                player.sendMessage(ChatColor.RED + "You don't have the permissions to create a shop");
            } else {
                int quantity = 0;
                try {
                    quantity =  Integer.parseInt(event.getLine(1));
                } catch (Exception e) {
                    player.sendMessage(ChatColor.RED + event.getLine(1) + " is not an integer");
                    event.setCancelled(true);
                } if (quantity <= 0 || quantity > 64) {
                    event.setLine(1, "1");
                }
                
                double price = 0;
                try {
                    price = Double.parseDouble(event.getLine(2));
                } catch (Exception e) {
                    player.sendMessage(ChatColor.RED + event.getLine(2) + " is not a double");
                    event.setCancelled(true);
                }
                if (price < 0) {
                    player.sendMessage(ChatColor.RED + "The price should be a positive number");
                    event.setCancelled(true);
                }
                Material onSell = Material.getMaterial(event.getLine(3).toUpperCase());
                if (onSell == null) {
                    player.sendMessage(ChatColor.RED + event.getLine(3) + " is not a valid item.");
                    event.setCancelled(true);
                }
            }
        }
    }
    
    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
         Action action = event.getAction();
         Block block = event.getClickedBlock();
         Player player = event.getPlayer();
         
         //Actions on right click.
         if (action.equals(Action.RIGHT_CLICK_BLOCK)) {
             if (block.getType() == Material.WALL_SIGN) {
                 Sign sign = (Sign) block.getState();
                 if(sign.getLine(0).equalsIgnoreCase("[Buy]")) {
                     int quantity = Integer.parseInt(sign.getLine(1));
                     double price = Double.parseDouble(sign.getLine(2));
                     Material onSell = Material.getMaterial(sign.getLine(3).toUpperCase());
                     
                     if (EconomyManager.hasEnoughtMoney(player.getName(), price)) {
                         giveItem(player, quantity, onSell);
                         EconomyManager.add(player.getName(), -price);
                         player.sendMessage(ChatColor.DARK_GRAY + "You bought " + ChatColor.GREEN + quantity + " " 
                         + onSell.toString() + ChatColor.DARK_GRAY + " for " + ChatColor.RED + price + "$");
                     } else {
                         player.sendMessage(ChatColor.RED + "You don't have enought money to do this.");
                     }
                 }
             }
         }
    }

    private void giveItem(Player player, int quantity, Material onSell) {
        ItemStack item = new ItemStack(onSell);
        item.setAmount(quantity);
        player.getInventory().addItem(item);
    }
}
