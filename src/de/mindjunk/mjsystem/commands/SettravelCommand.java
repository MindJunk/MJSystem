package de.mindjunk.mjsystem.commands;

import java.util.Date;
import java.text.SimpleDateFormat;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.mindjunk.mjsystem.files.ConfigFile;
import de.mindjunk.mjsystem.files.MessagesFile;
import de.mindjunk.mjsystem.files.TravelpointsFile;
import net.md_5.bungee.api.ChatColor;

public class SettravelCommand implements CommandExecutor{

	public boolean onCommand( CommandSender sender, Command command, String label, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			String prefix = MessagesFile.get().getString("Messages.Defaults.prefix");
			String msgnoperm = MessagesFile.get().getString("Messages.Errors.noPermission");
			Boolean moduleEnabled = ConfigFile.get().getBoolean("Modules.Travelpoints.enabled");
			String msgdisabled = MessagesFile.get().getString("Messages.Defaults.disabled.text");
			String colorDisabled = MessagesFile.get().getString("Messages.Defaults.disabled.color");
			String msgset = MessagesFile.get().getString("Messages.Modules.Travelpoints.set");
			String msgunknown = MessagesFile.get().getString("Messages.Errors.unknownCommand");
			String pointName = MessagesFile.get().getString("Messages.Modules.Travelpoints.name");
			if(moduleEnabled == true) {
				if(player.hasPermission("mjs.travel.set")) {
					String world = player.getWorld().getName();
					double locX = player.getLocation().getX();
					double locY = player.getLocation().getY();
					double locZ = player.getLocation().getZ();
		            Date date = new Date();
		            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
					if(args.length == 1) {
						String enabled = TravelpointsFile.get().getString("Travelpoints." + args[0].toUpperCase() + ".enabled");
						TravelpointsFile.get().set("Travelpoints." + args[0].toUpperCase() + ".enabled", true);
						TravelpointsFile.get().set("Travelpoints." + args[0].toUpperCase() + ".world", world);
						TravelpointsFile.get().set("Travelpoints." + args[0].toUpperCase() + ".x", locX);
						TravelpointsFile.get().set("Travelpoints." + args[0].toUpperCase() + ".y", locY);
						TravelpointsFile.get().set("Travelpoints." + args[0].toUpperCase() + ".z", locZ);
						TravelpointsFile.get().set("Travelpoints." + args[0].toUpperCase() + ".yaw", player.getLocation().getYaw());
						TravelpointsFile.get().set("Travelpoints." + args[0].toUpperCase() + ".pitch", player.getLocation().getPitch());
						String list = TravelpointsFile.get().getString("List").replace(" " + args[0].toUpperCase(), "");
						list = list + " " + args[0].toUpperCase();
						TravelpointsFile.get().set("List", list);
						if (enabled != null) {
							TravelpointsFile.get().set("Travelpoints." + args[0].toUpperCase() + ".edited.user", player.getName());
							TravelpointsFile.get().set("Travelpoints." + args[0].toUpperCase() + ".edited.date", format.format(date));
							player.sendMessage(prefix + " §9" + pointName + " §6" + args[0].toUpperCase() + " §7wurde nach §a" + world + " §aX§8=§9" + locX + " §aY§8=§9" + locY + " §aZ§8=§9" + locZ + " §7verschoben!");
							System.out.println(ChatColor.WHITE + "[MJSystem]>> Der Spieler " + ChatColor.BLUE + player.getName() + ChatColor.WHITE + " hat den " + pointName + " \"" + args[0].toUpperCase() + "\" nach " + world + " X=" + locX + " Y=" + locY + " Z=" + locZ + " verschoben!");
							TravelpointsFile.save();
							return false;
						} else
							TravelpointsFile.get().set("Travelpoints." + args[0].toUpperCase() + ".created.user", player.getName());
							TravelpointsFile.get().set("Travelpoints." + args[0].toUpperCase() + ".created.date", format.format(date));
							player.sendMessage(prefix + "§9" + pointName + " §6" + args[0].toUpperCase() + " §7wurde auf Welt §a" + world + " §7bei §aX§8=§9" + locX + " §aY§8=§9" + locY + " §aZ§8=§9" + locZ + " §7erstellt!");
							System.out.println(ChatColor.WHITE + "[MJSystem]>> Der Spieler " + ChatColor.BLUE + player.getName() + ChatColor.WHITE + " hat einen " + pointName + " mit dem Namen \"" + args[0].toUpperCase() + "\" auf Welt " + world + " bei X=" + locX + " Y=" + locY + " Z=" + locZ + " erstellt!");
							TravelpointsFile.save();
							return false;
					} else
						player.sendMessage(prefix + msgset);
				} else
					player.sendMessage(prefix + msgnoperm + "mjs.travel.set");
			} else if(player.hasPermission("mjs.moduleinfo")) {
				player.sendMessage(prefix + "§7Das §6Travelpoints-Modul §7ist derzeit " + colorDisabled + msgdisabled);
			} else
				player.sendMessage(prefix + msgunknown);
		} else
			sender.sendMessage("[MJ-System]>> Dieser Befehl muss als Spieler ausgeführt werden!");
		return false;
	}

}
