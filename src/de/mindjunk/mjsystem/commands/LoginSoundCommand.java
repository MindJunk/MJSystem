package de.mindjunk.mjsystem.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import de.mindjunk.mjsystem.files.MessagesFile;
import de.mindjunk.mjsystem.files.UserlistFile;
import de.mindjunk.mjsystem.main.Main;

public class LoginSoundCommand implements CommandExecutor{
	
	Plugin plugin = Main.getPlugin(Main.class);

	public boolean onCommand( CommandSender sender, Command command, String label, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			String prefix = MessagesFile.get().getString("Messages.Defaults.prefix");
			String modulecolor = MessagesFile.get().getString("Messages.Defaults.moduleColor");
			String msgenabled = MessagesFile.get().getString("Messages.Defaults.enabled.text");
			String msgdisabled = MessagesFile.get().getString("Messages.Defaults.disabled.text");
			String colorEnabled = MessagesFile.get().getString("Messages.Defaults.enabled.color");
			String colorDisabled = MessagesFile.get().getString("Messages.Defaults.disabled.color");
			String msgunknown = MessagesFile.get().getString("Messages.Errors.unknownCommand");
			String msgtoggle = MessagesFile.get().getString("Messages.Defaults.toggleLoginSound");
			if(player.hasPermission("mjs.loginSound.toggle")) {
				if(args.length == 0) {
					Boolean soundEnabled = UserlistFile.get().getBoolean(player.getUniqueId() + ".welcomeSound");
					if(soundEnabled == true) {
						UserlistFile.get().set(player.getUniqueId() + ".welcomeSound", false);
						player.sendMessage(prefix + "§7Du hast deinen " + modulecolor + "Login-Sound " + colorDisabled + msgdisabled + "§7!");
						UserlistFile.save();
						return false;
					} else
						UserlistFile.get().set(player.getUniqueId() + ".welcomeSound", true);
						player.sendMessage(prefix + "§7Du hast deinen " + modulecolor + "Login-Sound " + colorEnabled + msgenabled + "§7!");
						UserlistFile.save();
						return false;
				} else
					player.sendMessage(prefix + msgtoggle);
			} else
				player.sendMessage(prefix + msgunknown);
		} else
			sender.sendMessage("[MJ-System]>> Dieser Befehl muss als Spieler ausgeführt werden!");
		return false;
	}

}