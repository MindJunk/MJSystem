package de.mindjunk.mjsystem.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import de.mindjunk.mjsystem.files.ConfigFile;
import de.mindjunk.mjsystem.files.MessagesFile;
import de.mindjunk.mjsystem.main.Main;
import net.md_5.bungee.api.ChatColor;

public class DiscordlinkCommand implements CommandExecutor{
	
	Plugin plugin = Main.getPlugin(Main.class);
	
	public boolean onCommand( CommandSender sender, Command command, String label, String[] args) {
		String prefix = MessagesFile.get().getString("Messages.Defaults.prefix");
		String modulecolor = MessagesFile.get().getString("Messages.Defaults.moduleColor");
		String msgdisabled = MessagesFile.get().getString("Messages.Defaults.disabled.text");
		String colorDisabled = MessagesFile.get().getString("Messages.Defaults.disabled.color");
		String msgunknown = MessagesFile.get().getString("Messages.Errors.unknownCommand");
		String msgshow = MessagesFile.get().getString("Messages.Modules.Discordlink.show");
		String msgset = MessagesFile.get().getString("Messages.Modules.Discordlink.set");
		Boolean moduleenabled = ConfigFile.get().getBoolean("Modules.Discordlink.enabled");
		String link = ConfigFile.get().getString("Modules.Discordlink.link");
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(moduleenabled == true) {
				if(args.length == 0) {
					if(player.hasPermission("mjs.discordlink.show")) {
						player.sendMessage(prefix + "§9Community Discord: §a" + link);
					} else
						player.sendMessage(prefix + msgunknown);
				} else if(args.length == 1) {
					if(player.hasPermission("mjs.discordlink.set")) {
						plugin.getConfig().set("Modules.Discordlink.link", args[0]);
						plugin.getConfig().set("Modules.Discordlink.changed", player.getName());
						plugin.saveConfig();
						player.sendMessage(prefix + "§7Du hast den Discord-Link zu §6" + args[0] + " §7geändert!");
						System.out.println(ChatColor.WHITE + "[MJSystem]>> Der Spieler " + ChatColor.BLUE + player.getName() + ChatColor.WHITE + " hat den Discord-Link zu " + ChatColor.BLUE + args[0] + ChatColor.WHITE + " geändert");
					} else if(player.hasPermission("mjs.discordlink.show")) {
						player.sendMessage(prefix + msgshow);
					} else
						player.sendMessage(prefix + msgunknown);
				} else
					if(player.hasPermission("mjs.discordlink.set")) {
						player.sendMessage(prefix + msgset);
					} else if(player.hasPermission("mjs.discordlink.show")) {
						player.sendMessage(prefix + msgshow);
					} else
						player.sendMessage(prefix + msgunknown);
			} else if(player.hasPermission("mjs.moduleinfo")) {
				player.sendMessage(prefix + "§7Das " + modulecolor + "Discordlink-Modul §7ist derzeit " + colorDisabled + msgdisabled);
			} else
				player.sendMessage(prefix + msgunknown);
		} else
			if(moduleenabled == true) {
				if(args.length == 0) {
					sender.sendMessage(prefix + "§9Community Discord: §a" + link);
				} else if(args.length == 1) {
					plugin.getConfig().set("Modules.Discordlink.link", args[0]);
					plugin.getConfig().set("Modules.Discordlink.changed", "CONSOLE");
					plugin.saveConfig();
					sender.sendMessage(prefix + "§7Du hast den Discord-Link zu §6" + args[0] + " §7geändert!");
				} else
					sender.sendMessage(prefix + msgunknown);
			} else
				sender.sendMessage(prefix + "§7Das " + modulecolor + "Discordlink-Modul §7ist derzeit " + colorDisabled + msgdisabled);
		return false;
	}

}