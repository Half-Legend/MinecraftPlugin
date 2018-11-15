package minecraftPG.Economy.events;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        if(event.getLine(0).equalsIgnoreCase("Shop")) {
            event.setLine(0, ChatColor.BLUE+ "Shop");
            if(!player.hasPermission("shop")) {
                event.setCancelled(true);
                player.sendMessage(ChatColor.RED + "You don't have the permissions to create a shop");
            } else {
                Material onSell = Material.getMaterial(event.getLine(1).toUpperCase());
                if (onSell == null) {
                    player.sendMessage(ChatColor.RED + event.getLine(1) + " is not a valid item.");
                    event.setCancelled(true);
                }
                Pattern pattern = Pattern.compile("(\\d)+\\s(\\d)+");
                Matcher matcher = pattern.matcher(event.getLine(2));
                if (matcher.matches()) {
                    Pattern patternBuyPrice = Pattern.compile("^(\\d)+");
                    Matcher buyPrice = patternBuyPrice.matcher(event.getLine(2));
                    
                    Pattern patternSellPrice = Pattern.compile("(\\d)+$");
                    Matcher sellPrice = patternSellPrice.matcher(event.getLine(2));
                    
                    if (buyPrice.find() && sellPrice.find()) {
                        int bPrice = Integer.parseInt(buyPrice.group());
                        int sPrice = Integer.parseInt(sellPrice.group());
                        if (bPrice > 0 && sPrice > 0) {
                            event.setLine(2,ChatColor.RED + "B " + buyPrice.group() + "$" + ChatColor.GREEN + " S " + sellPrice.group() + "$");
                        } else {
                            player.sendMessage(ChatColor.RED + "Prices can't less than 1");
                        }
                    } else {
                        player.sendMessage(ChatColor.RED + "Invalid prices input");
                        event.setCancelled(true);
                    }
                } else {
                    player.sendMessage(ChatColor.RED + "This is an incorrect input");
                    event.setCancelled(true);
                }
                
                int quantity = 0;
                try {
                    quantity =  Integer.parseInt(event.getLine(3));
                } catch (Exception e) {
                    player.sendMessage(ChatColor.RED + event.getLine(3) + " is not an integer");
                    event.setCancelled(true);
                } if (quantity <= 0 || quantity > 64) {
                    event.setLine(3, "1");
                } else {
                    event.setLine(3, ChatColor.BLUE + "Amount: " + quantity);
                }
            }
        }
    }
    
    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
         Action action = event.getAction();
         
         Player player = event.getPlayer();
         if( (action.equals(Action.LEFT_CLICK_BLOCK)) || action.equals(Action.RIGHT_CLICK_BLOCK))
         {
             Block block = event.getClickedBlock();
             if (block.getType() == Material.WALL_SIGN) {
                 Sign sign = (Sign) block.getState();
                 if (sign.getLine(0).equalsIgnoreCase(ChatColor.BLUE + "Shop")) {
                     int quantity = getQuantity(sign.getLine(3));
                     Material item = Material.getMaterial(sign.getLine(1).toUpperCase());
                     ItemStack itemStack = new ItemStack(item);
                     itemStack.setAmount(quantity);
                     
                     if (action.equals(Action.LEFT_CLICK_BLOCK)) {
                         String line2 = ChatColor.stripColor(sign.getLine(2));
                         Pattern patternBuyPrice = Pattern.compile("^B\\s(\\d+)");
                         Matcher buyPrice = patternBuyPrice.matcher(line2);
                         
                         int bPrice = 0;
                         if (buyPrice.find()) {
                             bPrice = Integer.parseInt(buyPrice.group(1));
                         }
                         if (EconomyManager.hasEnoughtMoney(player.getName(), bPrice)) {
                             player.getInventory().addItem(itemStack);
                             EconomyManager.add(player.getName(), -bPrice);
                             player.sendMessage(ChatColor.DARK_GRAY + "You bought " + ChatColor.GREEN + quantity + " " 
                             + item.toString() + ChatColor.DARK_GRAY + " for " + ChatColor.RED + bPrice + "$");
                         } else {
                             player.sendMessage(ChatColor.RED + "You don't have enought money to do this.");
                         }
                         
                     } else if (action.equals(Action.RIGHT_CLICK_BLOCK)) {
                         Pattern patternSellPrice = Pattern.compile("(\\d+)(.)$");
                         String line2 = ChatColor.stripColor(sign.getLine(2));
                         Matcher sellPrice = patternSellPrice.matcher(line2);
                         int sPrice = 0;
                         if (sellPrice.find()) {
                             sPrice = Integer.parseInt(sellPrice.group(1));
                         }
                         
                         if(player.getInventory().contains(item, quantity)) {
                             int slot = player.getInventory().first(item);
                             int available = player.getInventory().getItem(slot).getAmount();
                             int remaining = quantity;
                             do {
                                 slot = player.getInventory().first(item);
                                 available = player.getInventory().getItem(slot).getAmount();
                                 if (available < remaining) {
                                     player.getInventory().getItem(slot).setAmount(0);
                                 } else {
                                     player.getInventory().getItem(slot).setAmount(available - remaining);
                                 }
                                 remaining -= available;
                             } while(remaining > 0);
    
                             EconomyManager.add(player.getName(), sPrice);
                             player.sendMessage(ChatColor.DARK_GRAY + "You sold " + ChatColor.RED + quantity + " " 
                                     + item.toString() + ChatColor.DARK_GRAY + " for " + ChatColor.GREEN + sPrice + "$");
                         } else {
                             player.sendMessage(ChatColor.RED + "You need: " + quantity + " " + item.toString() + " to do this.");
                         }
                     }
                 }
             }
         }
    }
    
    int getQuantity(String line) {
        Pattern patternSellPrice = Pattern.compile("(\\d+)$");
        Matcher sellPrice = patternSellPrice.matcher(line);
        int res = 0;
        if(sellPrice.find()) {
            res = Integer.parseInt(sellPrice.group(0));
        } else {
            res = -1;
        }
        return res;
    }
}
