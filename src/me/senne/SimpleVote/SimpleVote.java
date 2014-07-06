package me.senne.SimpleVote;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class SimpleVote extends JavaPlugin{	
	
	public void onEnable() {
		getConfig().options().copyDefaults(true);
		saveConfig();
		Server server =Bukkit.getServer();
		ConsoleCommandSender console = server.getConsoleSender();
		console.sendMessage(ChatColor.GREEN + "SimpleVote Plugin Enabled!");
	}
	public void onDisable() {
		Server server =Bukkit.getServer();
		ConsoleCommandSender console = server.getConsoleSender();
		console.sendMessage(ChatColor.RED + "SimpleVote Plugin Disabled!");
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
