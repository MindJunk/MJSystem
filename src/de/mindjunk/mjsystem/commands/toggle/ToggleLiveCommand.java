package de.mindjunk.mjsystem.commands.toggle;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import de.mindjunk.mjsystem.files.MessagesFile;
import de.mindjunk.mjsystem.main.Main;

public class ToggleLiveCommand implements CommandExecutor{
	
	Plugin plugin = Main.getPlugin(Main.class);

	public boolean onCommand( CommandSender sender, Command command, String label, String[] args) {
		String prefix = MessagesFile.get().getString("Messages.Defaults.prefix");
		String modulecolor = MessagesFile.get().getString("Messages.Defaults.moduleColor");
		String msgenabled = MessagesFile.get().getString("Messages.Defaults.enabled.text");
		String msgdisabled = MessagesFile.get().getString("Messages.Defaults.disabled.text");
		String colorEnabled = MessagesFile.get().getString("Messages.Defaults.enabled.color");
		String colorDisabled = MessagesFile.get().getString("Messages.Defaults.disabled.color");
		String msgunknown = MessagesFile.get().getString("Messages.Errors.unknownCommand");
		String msgtoggle = MessagesFile.get().getString("Messages.Modules.Live.toggle");
		String msgnoperm = MessagesFile.get().getString("Messages.Errors.noPermission");
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(player.hasPermission("mjs.live.toggle")) {
				if(args.length == 0) {
					FileConfiguration config = Main.getPlugin().getConfig();
					Boolean moduleenabled = config.getBoolean("Modules.Live.enabled");
					if(moduleenabled == true) {
						plugin.getConfig().set("Modules.Live.enabled", false);
						player.sendMessage(prefix + "§7Das " + modulecolor + "Live-Modul §7wurde " + colorDisabled + msgdisabled + "§7!");
						System.out.println("[MJSystem]>> Der Spieler " + player.getName() + " hat das Live-Modul " + msgdisabled + "!");
						plugin.saveConfig();
						return false;
					} else
						plugin.getConfig().set("Modules.Live.enabled", true);
						player.sendMessage(prefix + "§7Das " + modulecolor + "Live-Modul §7wurde " + colorEnabled + msgenabled + "§7!");
						System.out.println("[MJSystem]>> Der Spieler " + player.getName() + " hat das Live-Modul " + msgenabled + "!");
						plugin.saveConfig();
						return false;
				} else
					player.sendMessage(prefix + msgtoggle);
			} else
				player.sendMessage(prefix + msgnoperm + "mjs.live.toggle");
		} else
			if(args.length == 0) {
				FileConfiguration config = Main.getPlugin().getConfig();
				Boolean moduleenabled = config.getBoolean("Modules.Live.enabled");
				if(moduleenabled == true) {
					plugin.getConfig().set("Modules.Live.enabled", false);
					plugin.saveConfig();
					sender.sendMessage(prefix + "§7Das " + modulecolor + "Live-Modul §7wurde " + colorDisabled + msgdisabled + "§7!");
					return false;
				} else
					plugin.getConfig().set("Modules.Live.enabled", true);
					plugin.saveConfig();
					sender.sendMessage(prefix + "§7Das " + modulecolor + "Live-Modul §7wurde " + colorEnabled + msgenabled + "§7!");
					return false;
			} else
				sender.sendMessage(prefix + msgunknown);
		return false;
	}

}