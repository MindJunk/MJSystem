package de.mindjunk.mjsystem.commands;

import java.util.Date;
import java.text.SimpleDateFormat;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.mindjunk.mjsystem.files.ConfigFile;
import de.mindjunk.mjsystem.files.MessagesFile;
import de.mindjunk.mjsystem.files.UserlistFile;

public class SetHomeCommand implements CommandExecutor{
	
	@Override
	public boolean onCommand( CommandSender sender, Command command, String label, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			String prefix = MessagesFile.get().getString("Messages.Defaults.prefix");
			String msgnoperm = MessagesFile.get().getString("Messages.Errors.noPermission");
			Boolean moduleEnabled = ConfigFile.get().getBoolean("Modules.Homepoints.enabled");
			String msgdisabled = MessagesFile.get().getString("Messages.Defaults.disabled.text");
			String colorDisabled = MessagesFile.get().getString("Messages.Defaults.disabled.color");
			String msgset = MessagesFile.get().getString("Messages.Modules.Homepoints.set");
			String msgsetothers = MessagesFile.get().getString("Messages.Modules.Homepoints.setOthers");
			String msgunknown = MessagesFile.get().getString("Messages.Errors.unknownCommand");
			String pointName = MessagesFile.get().getString("Messages.Modules.Homepoints.name");
			if(moduleEnabled == true) {
				if(args.length == 0) {
					if(player.hasPermission("mjs.home.set")) {
						String world = player.getWorld().getName();
						double locX = player.getLocation().getX();
						double locY = player.getLocation().getY();
						double locZ = player.getLocation().getZ();
						Date date = new Date();
						SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
						UserlistFile.get().set(player.getUniqueId() + ".home.enabled", true);
						UserlistFile.get().set(player.getUniqueId() + ".home.world", world);
						UserlistFile.get().set(player.getUniqueId() + ".home.x", locX);
						UserlistFile.get().set(player.getUniqueId() + ".home.y", locY);
						UserlistFile.get().set(player.getUniqueId() + ".home.z", locZ);
						UserlistFile.get().set(player.getUniqueId() + ".home.yaw", player.getLocation().getYaw());
						UserlistFile.get().set(player.getUniqueId() + ".home.pitch", player.getLocation().getPitch());
						UserlistFile.get().set(player.getUniqueId() + ".home.time", format.format(date));
						UserlistFile.save();
						player.sendMessage(prefix + "§9" + pointName + " §7wurde auf Welt §a" + world + " §7bei §aX§8=§9" + locX + " §aY§8=§9" + locY + " §aZ§8=§9" + locZ + " §7gesetzt!");
					} else
						player.sendMessage(prefix + msgnoperm + "mjs.home.set");
				} else if(args.length == 1) {
					if(player.hasPermission("mjs.home.set.others")) {
						Player target = Bukkit.getPlayer(args[0]);
						if(target != null) {
							String world = player.getWorld().getName();
							double locX = player.getLocation().getX();
							double locY = player.getLocation().getY();
							double locZ = player.getLocation().getZ();
							Date date = new Date();
							SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
							UserlistFile.get().set(target.getUniqueId() + ".home.enabled", true);
							UserlistFile.get().set(target.getUniqueId() + ".home.world", world);
							UserlistFile.get().set(target.getUniqueId() + ".home.x", locX);
							UserlistFile.get().set(target.getUniqueId() + ".home.y", locY);
							UserlistFile.get().set(target.getUniqueId() + ".home.z", locZ);
							UserlistFile.get().set(target.getUniqueId() + ".home.yaw", player.getLocation().getYaw());
							UserlistFile.get().set(target.getUniqueId() + ".home.pitch", player.getLocation().getPitch());
							UserlistFile.get().set(target.getUniqueId() + ".home.time", format.format(date));
							UserlistFile.save();
							player.sendMessage(prefix + "§9" + pointName + " §7von " + args[0] + " wurde auf Welt §a" + world + " §7bei §aX§8=§9" + locX + " §aY§8=§9" + locY + " §aZ§8=§9" + locZ + " §7gesetzt!");
						} else
							player.sendMessage(prefix + "§7Der Spieler §6" + args[0] + " §7ist nicht auf dem Server!");
					} else
						player.sendMessage(prefix + msgnoperm + "mjs.home.set.others");
				} else
					if(player.hasPermission("mjs.home.set.others")) {
						player.sendMessage(prefix + msgsetothers);
					} else if(player.hasPermission("mjs.home.set")) {
						player.sendMessage(prefix + msgset);
					} else
						player.sendMessage(prefix + msgunknown);
			} else if(player.hasPermission("mjs.moduleinfo")) {
				player.sendMessage(prefix + "§7Das §6Homepoints-Modul §7ist derzeit " + colorDisabled + msgdisabled);
			} else
				player.sendMessage(prefix + msgunknown);
		} else
			sender.sendMessage("[MJ-System]>> Dieser Befehl muss als Spieler ausgeführt werden!");
		return false;
	}

}
