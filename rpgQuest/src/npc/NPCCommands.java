package npc;

import java.util.UUID;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_13_R2.CraftServer;
import org.bukkit.craftbukkit.v1_13_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_13_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;

import com.mojang.authlib.GameProfile;

import minecraftPG.rpgQuest.RpgQuest;
import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_13_R2.EntityPlayer;
import net.minecraft.server.v1_13_R2.MinecraftServer;
import net.minecraft.server.v1_13_R2.PacketPlayOutNamedEntitySpawn;
import net.minecraft.server.v1_13_R2.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_13_R2.PacketPlayOutPlayerInfo.EnumPlayerInfoAction;
import net.minecraft.server.v1_13_R2.PlayerConnection;
import net.minecraft.server.v1_13_R2.PlayerInteractManager;
import net.minecraft.server.v1_13_R2.WorldServer;

public class NPCCommands implements CommandExecutor{

    private RpgQuest rpgQuest;
    
    public NPCCommands(RpgQuest instance) {
        rpgQuest = instance;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        Player player = (Player)sender;
        if (sender.hasPermission("NPC")) {
            if (args.length != 2) {
                sender.sendMessage(ChatColor.RED + "This commands require 2 arguments.");
                sender.sendMessage(ChatColor.GREEN + "Use /NPC <spawn/remove> <name> to create/remove an NPC.");
                return true;
            } else {
                if (args[0].equalsIgnoreCase("spawn")) {
                    sender.sendMessage(ChatColor.BLUE + "You want to spawn a new NPC.");
                    //CustomNPC customNPC = new CustomNPC(NPCManager.getPlugin(),args[1],player.getLocation());
                    if(!args[1].isEmpty()) {
                        sender.sendMessage(ChatColor.GREEN + "Calling createNPC");
                        NPCManager.createNPC(args[1], player.getLocation());
                    }
                } else if (args[0].equalsIgnoreCase("remove")){
                    sender.sendMessage(ChatColor.BLUE + "You want to remove an NPC.");
                    int id;
                    try {
                        id = Integer.parseInt(args[1]);
                        if(NPCManager.removeNPC(id)) {
                            sender.sendMessage(ChatColor.GREEN + "The NPC has been removed");
                        } else {
                            sender.sendMessage(ChatColor.RED + "The NPC is still here!");
                        }
                        sender.sendMessage(ChatColor.BLUE + "Trying to remove the entity: " + id);
                    } catch (Exception e) {
                        sender.sendMessage(ChatColor.RED + args[1] + " it not a valid id, expecting an integer.");
                        return true;
                    }
                } else {
                    sender.sendMessage(ChatColor.RED + "Invalid arguments, expecting <spawn/remove>.");
                    return true;
                }
            }
        }
        return true;
    }

}
