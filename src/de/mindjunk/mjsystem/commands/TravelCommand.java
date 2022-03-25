package de.mindjunk.mjsystem.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.mindjunk.mjsystem.scripts.ClickChat;
import de.mindjunk.mjsystem.files.ConfigFile;
import de.mindjunk.mjsystem.files.MessagesFile;
import de.mindjunk.mjsystem.files.TravelpointsFile;
import de.mindjunk.mjsystem.files.UserlistFile;

public class TravelCommand implements CommandExecutor{

	public boolean onCommand( CommandSender sender, Command command, String label, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			String prefix = MessagesFile.get().getString("Messages.Defaults.prefix");
			String msgnoperm = MessagesFile.get().getString("Messages.Errors.noPermission");
			Boolean moduleEnabled = ConfigFile.get().getBoolean("Modules.Travelpoints.enabled");
			String msgdisabled = MessagesFile.get().getString("Messages.Defaults.disabled.text");
			String colorDisabled = MessagesFile.get().getString("Messages.Defaults.disabled.color");
			String msgunknown = MessagesFile.get().getString("Messages.Errors.unknownCommand");
			String msguse = MessagesFile.get().getString("Messages.Modules.Travelpoints.use");
			String pointName = MessagesFile.get().getString("Messages.Modules.Travelpoints.name");
			String pointName2 = MessagesFile.get().getString("Messages.Modules.Travelpoints.name2");
			String doesntExist = MessagesFile.get().getString("Messages.Errors.noTravelpoint");
			if(moduleEnabled == true) {
				if(player.hasPermission("mjs.travel.use")) {
					if(args.length == 0) {
						player.sendMessage("§7|| =======[§9" + pointName2 + "§7]=======");
						ClickChat.travelPoints(player);
					} else if(args.length == 1) {
						Boolean enabled = TravelpointsFile.get().getBoolean("Travelpoints." + args[0].toUpperCase() + ".enabled");
						if(enabled == true) {
							World world = Bukkit.getWorld(TravelpointsFile.get().getString("Travelpoints." + args[0].toUpperCase() + ".world"));
							double locX = TravelpointsFile.get().getDouble("Travelpoints." + args[0].toUpperCase() + ".x");
							double locY = TravelpointsFile.get().getDouble("Travelpoints." + args[0].toUpperCase() + ".y");
							double locZ = TravelpointsFile.get().getDouble("Travelpoints." + args[0].toUpperCase() + ".z");
							float yaw = (float) TravelpointsFile.get().getDouble("Travelpoints." + args[0].toUpperCase() + ".yaw");
							float pitch = (float) TravelpointsFile.get().getDouble("Travelpoints." + args[0].toUpperCase() + ".pitch");
							Location location = new Location(world, locX, locY, locZ, yaw, pitch);
							Boolean soundEnabled = UserlistFile.get().getBoolean(player.getUniqueId() + ".welcomeSound");
							if(soundEnabled == true) {
								String soundType = ConfigFile.get().getString("WelcomeMessages.sound.type");
								player.playSound(player.getLocation(), Sound.valueOf(soundType), 0.2F, 1.0F);
							}
							player.sendMessage(prefix + " §7Du teleportierst dich zu §9" + pointName + " §6" + args[0].toUpperCase() + "§7!");
							player.teleport(location);
						} else
							player.sendMessage(prefix + " §9" + pointName + " §6" + args[0].toUpperCase() + " " + doesntExist + " " + colorDisabled + msgdisabled);
					} else
						player.sendMessage(prefix + msguse);
				} else
					player.sendMessage(prefix + msgnoperm + "mjs.travel.use");
			} else if(player.hasPermission("mjs.moduleinfo")) {
				player.sendMessage(prefix + " §7Das §6Travelpoints-Modul §7ist derzeit " + colorDisabled + msgdisabled);
			} else
				player.sendMessage(prefix + msgunknown);
		} else
			sender.sendMessage("[MJ-System]>> Dieser Befehl muss als Spieler ausgeführt werden!");
		return false;
	}

}
