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

public class PricelistCommand implements CommandExecutor{
	
	Plugin plugin = Main.getPlugin(Main.class);
	
	public boolean onCommand( CommandSender sender, Command command, String label, String[] args) {
		String prefix = MessagesFile.get().getString("Messages.Defaults.prefix");
		String modulecolor = MessagesFile.get().getString("Messages.Defaults.moduleColor");
		String msgdisabled = MessagesFile.get().getString("Messages.Defaults.disabled.text");
		String colorDisabled = MessagesFile.get().getString("Messages.Defaults.disabled.color");
		String msgunknown = MessagesFile.get().getString("Messages.Errors.unknownCommand");
		String msgshow = MessagesFile.get().getString("Messages.Modules.Pricelist.show");
		String msgset = MessagesFile.get().getString("Messages.Modules.Pricelist.set");
		Boolean moduleenabled = ConfigFile.get().getBoolean("Modules.Pricelist.enabled");
		String link = ConfigFile.get().getString("Modules.Pricelist.link");
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(moduleenabled == true) {
				if(args.length == 0) {
					if(player.hasPermission("mjs.pricelist.show")) {
						player.sendMessage(prefix + "§9Preisliste: §a" + link);
					} else
						player.sendMessage(prefix + msgunknown);
				} else if(args.length == 1) {
					if(player.hasPermission("mjs.pricelist.set")) {
						plugin.getConfig().set("Modules.Pricelist.link", args[0]);
						plugin.getConfig().set("Modules.Pricelist.changed", player.getName());
						plugin.saveConfig();
						player.sendMessage(prefix + "§7Du hast den Pricelist-Link zu §6" + args[0] + " §7geändert!");
						System.out.println(ChatColor.WHITE + "[MJSystem]>> Der Spieler " + ChatColor.BLUE + player.getName() + ChatColor.WHITE + " hat den Pricelist-Link zu " + ChatColor.BLUE + args[0] + ChatColor.WHITE + " geändert");
					} else if(player.hasPermission("mjs.pricelist.show")) {
						player.sendMessage(prefix + msgshow);
					} else
						player.sendMessage(prefix + msgunknown);
				} else
					if(player.hasPermission("mjs.pricelist.set")) {
						player.sendMessage(prefix + msgset);
					} else if(player.hasPermission("mjs.pricelist.show")) {
						player.sendMessage(prefix + msgshow);
					} else
						player.sendMessage(prefix + msgunknown);
			} else if(player.hasPermission("mjs.moduleinfo")) {
				player.sendMessage(prefix + "§7Das " + modulecolor + "Pricelist-Modul §7ist derzeit " + colorDisabled + msgdisabled);
			} else
				player.sendMessage(prefix + msgunknown);
		} else
			if(moduleenabled == true) {
				if(args.length == 0) {
					sender.sendMessage(prefix + "§9Preisliste: §a" + link);
				} else if(args.length == 1) {
					plugin.getConfig().set("Modules.Pricelist.link", args[0]);
					plugin.getConfig().set("Modules.Pricelist.changed", "CONSOLE");
					plugin.saveConfig();
					sender.sendMessage(prefix + "§7Du hast den Pricelist-Link zu §6" + args[0] + " §7geändert!");
				} else
					sender.sendMessage(prefix + msgunknown);
			} else
				sender.sendMessage(prefix + "§7Das " + modulecolor + "Pricelist-Modul §7ist derzeit " + colorDisabled + msgdisabled);
		return false;
	}

}