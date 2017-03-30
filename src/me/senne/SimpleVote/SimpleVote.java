package me.senne.SimpleVote;


import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import me.senne.SimpleVote.Metrics;
import me.senne.SimpleVote.Updater;
import me.senne.SimpleVote.Updater.ReleaseType;
import me.senne.SimpleVote.Updater.UpdateType;

public class SimpleVote extends JavaPlugin implements Listener{	
	public static boolean update = false;
	public static String name = "";
	public static ReleaseType type = null;
	public static String version = "";
	public static String link = "";
	
	public void onEnable() {
		Updater updater = new Updater(this, 49457, getFile(), UpdateType.NO_DOWNLOAD, true);
		update = updater.getResult() == Updater.UpdateResult.UPDATE_AVAILABLE; // Determine if there is an update ready for us
		name = updater.getLatestName(); // Get the latest name
		version = updater.getLatestGameVersion(); // Get the latest game version
		type = updater.getLatestType(); // Get the latest file's type
		link = updater.getLatestFileLink(); // Get the latest link
		getConfig().options().copyDefaults(true);
		Bukkit.getPluginManager().registerEvents(this, this);
		saveConfig();
		Server server =Bukkit.getServer();
		ConsoleCommandSender console = server.getConsoleSender();
		console.sendMessage(ChatColor.GREEN + "SimpleVote Plugin Enabled!");
		try {
	        Metrics metrics = new Metrics(this);
	        metrics.start();
	    } catch (IOException e) {
	        // Failed to submit the stats :-(
	    }	
	}
	public void onDisable() {
		Server server =Bukkit.getServer();
		ConsoleCommandSender console = server.getConsoleSender();
		console.sendMessage(ChatColor.RED + "SimpleVote Plugin Disabled!");
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event)	{
	  Player player = event.getPlayer();
	  if(SimpleVote.update){
		  if(player.isOp()){
	    player.sendMessage(ChatColor.GREEN + "An update is available: " + ChatColor.AQUA + SimpleVote.name + ChatColor.GREEN + " at " + SimpleVote.link);
	  }
	  }
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {		
		if (cmd.getName().equalsIgnoreCase("vote")) {
			if (!sender.hasPermission("simplevote.vote")) {
				sender.sendMessage(ChatColor.RED + "You are not permitted to do this!");
				return true;
			}
			for (String s : getConfig().getStringList("VoteURL")) {
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', s));
            }		
		}
		if (cmd.getName().equalsIgnoreCase("website")) {
			if (getConfig().getBoolean("EnableWebURL") == true) {
				if (!sender.hasPermission("simplevote.web")) {
					sender.sendMessage(ChatColor.RED + "You are not permitted to do this!");
					return true;
				}
				sender.sendMessage(ChatColor.translateAlternateColorCodes ('&', getConfig().getString("Website")));
				return true;
			}
		}
		if (cmd.getName().equalsIgnoreCase("donate")) {
			if (getConfig().getBoolean("EnableDonateURL") == true) {
				if (!sender.hasPermission("simplevote.donate")) {
					sender.sendMessage(ChatColor.RED + "You are not permitted to do this!");
					return true;
				}
				sender.sendMessage(ChatColor.translateAlternateColorCodes ('&', getConfig().getString("DonateURL")));
				return true;
			}
		}
		if (cmd.getName().equalsIgnoreCase("dynurl")) {
			if (getConfig().getBoolean("EnableDynMapURL") == true) {
				if (!sender.hasPermission("simplevote.dynurl")) {
					sender.sendMessage(ChatColor.RED + "You are not permitted to do this!");
					return true;
				}
				sender.sendMessage(ChatColor.translateAlternateColorCodes ('&', getConfig().getString("DynMapURL")));
				return true;
			}
		}
		if (cmd.getName().equalsIgnoreCase("svreload")) {
			if (!sender.hasPermission("simplevote.reload")) {
				sender.sendMessage(ChatColor.RED + "You are not permitted to do this!");
				return true;
			}
			reloadConfig();
			sender.sendMessage(ChatColor.GREEN +  "Config has been reloaded!");
		}
		return true;
	}
}
