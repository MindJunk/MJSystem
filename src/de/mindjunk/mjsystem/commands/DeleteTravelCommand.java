package de.mindjunk.mjsystem.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.mindjunk.mjsystem.files.ConfigFile;
import de.mindjunk.mjsystem.files.MessagesFile;
import de.mindjunk.mjsystem.files.TravelpointsFile;

public class DeleteTravelCommand implements CommandExecutor{

	public boolean onCommand( CommandSender sender, Command command, String label, String[] args) {
		String prefix = MessagesFile.get().getString("Messages.Defaults.prefix");
		String msgnoperm = MessagesFile.get().getString("Messages.Errors.noPermission");
		Boolean moduleEnabled = ConfigFile.get().getBoolean("Modules.Travelpoints.enabled");
		String msgdisabled = MessagesFile.get().getString("Messages.Defaults.disabled.text");
		String colorDisabled = MessagesFile.get().getString("Messages.Defaults.disabled.color");
		String msgdelete = MessagesFile.get().getString("Messages.Modules.Travelpoints.delete");
		String pointName = MessagesFile.get().getString("Messages.Modules.Travelpoints.name");
		String doesntExist = MessagesFile.get().getString("Messages.Errors.doesntExist");
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(moduleEnabled == true) {
				if(player.hasPermission("mjs.travel.delete")) {
					if(args.length == 1) {
						String isSet = TravelpointsFile.get().getString("Travelpoints." + args[0].toUpperCase() + ".enabled");
						if(isSet != null) {
							TravelpointsFile.get().set("Travelpoints." + args[0].toUpperCase(), null);
							String list = TravelpointsFile.get().getString("List").replace(" " + args[0].toUpperCase(), "");
							TravelpointsFile.get().set("List", list);
							TravelpointsFile.save();
							player.sendMessage(prefix + "§9" + pointName + " §6" + args[0].toUpperCase() + " §7wurde entfernt!");
						} else
							player.sendMessage(prefix + "§9" + pointName + " §6" + args[0].toUpperCase() + " " + doesntExist);
					} else
						player.sendMessage(prefix + msgdelete);
				} else
					player.sendMessage(prefix + msgnoperm + "mjs.travel.delete");
			} else if(player.hasPermission("mjs.moduleinfo")) {
				player.sendMessage(prefix + "§7Das §6Travelpoints-Modul §7ist derzeit " + colorDisabled + msgdisabled);
			} else
				return false;
		} else
			if(moduleEnabled == true) {
				if(args.length == 1) {
					String isSet = TravelpointsFile.get().getString("Travelpoints." + args[0].toUpperCase() + ".enabled");
					if(isSet != null) {
						TravelpointsFile.get().set("Travelpoints." + args[0].toUpperCase(), null);
						String list = TravelpointsFile.get().getString("List").replace(" " + args[0].toUpperCase(), "");
						TravelpointsFile.get().set("List", list);
						TravelpointsFile.save();
						sender.sendMessage(prefix + "§9" + pointName + " §6" + args[0].toUpperCase() + " §7wurde entfernt!");
					} else
						sender.sendMessage(prefix + "§9" + pointName + " §6" + args[0].toUpperCase() + " " + doesntExist);
				} else
					sender.sendMessage(prefix + msgdelete);
			} else 
				sender.sendMessage(prefix + "§7Das §6Travelpoints-Modul §7ist derzeit " + colorDisabled + msgdisabled);
		return false;
	}

}
