package de.mindjunk.mjsystem.commands.toggle;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import de.mindjunk.mjsystem.files.MessagesFile;
import de.mindjunk.mjsystem.main.Main;
import net.md_5.bungee.api.ChatColor;

public class ToggleStartCommandsCommand implements CommandExecutor{
	
	Plugin plugin = Main.getPlugin(Main.class);

	public boolean onCommand( CommandSender sender, Command command, String label, String[] args) {
		String prefix = MessagesFile.get().getString("Messages.Defaults.prefix");
		String modulecolor = MessagesFile.get().getString("Messages.Defaults.moduleColor");
		String msgenabled = MessagesFile.get().getString("Messages.Defaults.enabled.text");
		String msgdisabled = MessagesFile.get().getString("Messages.Defaults.disabled.text");
		String colorEnabled = MessagesFile.get().getString("Messages.Defaults.enabled.color");
		String colorDisabled = MessagesFile.get().getString("Messages.Defaults.disabled.color");
		String msgunknown = MessagesFile.get().getString("Messages.Errors.unknownCommand");
		String msgtoggle = MessagesFile.get().getString("Messages.Modules.StartCommands.toggle");
		String msgnoperm = MessagesFile.get().getString("Messages.Errors.noPermission");
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(player.hasPermission("mjs.startcommands.toggle")) {
				if(args.length == 0) {
					FileConfiguration config = Main.getPlugin().getConfig();
					Boolean moduleenabled = config.getBoolean("Modules.StartCommands.enabled");
					if(moduleenabled == true) {
						plugin.getConfig().set("Modules.StartCommands.enabled", false);
						player.sendMessage(prefix + "§7Das " + modulecolor + "StartCommands-Modul §7wurde " + colorDisabled + msgdisabled + "§7!");
						System.out.println(ChatColor.WHITE + "[MJSystem]>> Der Spieler " + ChatColor.BLUE + player.getName() + ChatColor.WHITE + " hat das StartCommands-Modul " + ChatColor.RED + msgdisabled + "!");
						plugin.saveConfig();
						return false;
					} else
						plugin.getConfig().set("Modules.StartCommands.enabled", true);
						player.sendMessage(prefix + "§7Das " + modulecolor + "StartCommands-Modul §7wurde " + colorEnabled + msgenabled + "§7!");
						System.out.println(ChatColor.WHITE + "[MJSystem]>> Der Spieler " + ChatColor.BLUE + player.getName() + ChatColor.WHITE + " hat das StartCommands-Modul " + ChatColor.GREEN + msgenabled + "!");
						plugin.saveConfig();
						return false;
				} else
					player.sendMessage(prefix + msgtoggle);
			} else
				player.sendMessage(prefix + msgnoperm + "mjs.startcommands.toggle");
		} else
			if(args.length == 0) {
				FileConfiguration config = Main.getPlugin().getConfig();
				Boolean moduleenabled = config.getBoolean("Modules.StartCommands.enabled");
				if(moduleenabled == true) {
					plugin.getConfig().set("Modules.StartCommands.enabled", false);
					plugin.saveConfig();
					sender.sendMessage(prefix + "§7Das " + modulecolor + "StartCommands-Modul §7wurde " + colorDisabled + msgdisabled + "§7!");
					return false;
				} else
					plugin.getConfig().set("Modules.StartCommands.enabled", true);
					plugin.saveConfig();
					sender.sendMessage(prefix + "§7Das " + modulecolor + "StartCommands-Modul §7wurde " + colorEnabled + msgenabled + "§7!");
					return false;
			} else
				sender.sendMessage(prefix + msgunknown);
		return false;
	}

}