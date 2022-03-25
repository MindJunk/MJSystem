package de.mindjunk.mjsystem.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.mindjunk.mjsystem.files.ConfigFile;
import de.mindjunk.mjsystem.files.MessagesFile;
import de.mindjunk.mjsystem.files.UserlistFile;

public class HomeCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		String prefix = MessagesFile.get().getString("Messages.Defaults.prefix");
		String msgnoperm = MessagesFile.get().getString("Messages.Errors.noPermission");
		Boolean moduleEnabled = ConfigFile.get().getBoolean("Modules.Homepoints.enabled");
		String msgdisabled = MessagesFile.get().getString("Messages.Defaults.disabled.text");
		String colorDisabled = MessagesFile.get().getString("Messages.Defaults.disabled.color");
		String msgunknown = MessagesFile.get().getString("Messages.Errors.unknownCommand");
		String msguse = MessagesFile.get().getString("Messages.Modules.Homepoints.use");
		String msguseconsole = MessagesFile.get().getString("Messages.Modules.Homepoints.useconsole");
		String pointName = MessagesFile.get().getString("Messages.Modules.Homepoints.name");
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (moduleEnabled == true) {
				if (args.length == 0) {
					if (player.hasPermission("mjs.home.use")) {
						boolean enabled = UserlistFile.get().getBoolean(player.getUniqueId() + ".home.enabled");
						if (enabled == true) {
							World world = Bukkit.getWorld(UserlistFile.get().getString(player.getUniqueId() + ".home.world"));
							double locX = UserlistFile.get().getDouble(player.getUniqueId() + ".home.x");
							double locY = UserlistFile.get().getDouble(player.getUniqueId() + ".home.y");
							double locZ = UserlistFile.get().getDouble(player.getUniqueId() + ".home.z");
							float yaw = (float) UserlistFile.get().getDouble(player.getUniqueId() + ".home.yaw");
							float pitch = (float) UserlistFile.get().getDouble(player.getUniqueId() + ".home.pitch");
							Location location = new Location(world, locX, locY, locZ, yaw, pitch);
							Boolean soundEnabled = UserlistFile.get().getBoolean(player.getUniqueId() + ".welcomeSound");
							if (soundEnabled == true) {
								String soundType = ConfigFile.get().getString("WelcomeMessages.sound.type");
								player.playSound(player.getLocation(), Sound.valueOf(soundType), 0.2F, 1.0F);
							}
							player.sendMessage(prefix + "§7Du hast dich teleportiert!");
							player.teleport(location);
						} else
							player.sendMessage(prefix + "§c" + pointName + " wurde noch nicht gesetzt!");
					} else
						player.sendMessage(prefix + msgnoperm + "mjs.home.use");
				} else if (args.length == 1) {
					if (player.hasPermission("mjs.home.use.others")) {
						Player target = Bukkit.getServer().getPlayer(args[0]);
						if (target != null) {
							boolean enabled = UserlistFile.get().getBoolean(target.getUniqueId() + ".home.enabled");
							if (enabled == true) {
								World world = Bukkit.getWorld(UserlistFile.get().getString(target.getUniqueId() + ".home.world"));
								double locX = UserlistFile.get().getDouble(target.getUniqueId() + ".home.x");
								double locY = UserlistFile.get().getDouble(target.getUniqueId() + ".home.y");
								double locZ = UserlistFile.get().getDouble(target.getUniqueId() + ".home.z");
								float yaw = (float) UserlistFile.get().getDouble(target.getUniqueId() + ".home.yaw");
								float pitch = (float) UserlistFile.get().getDouble(target.getUniqueId() + ".home.pitch");
								Location location = new Location(world, locX, locY, locZ, yaw, pitch);
								Boolean soundEnabled = UserlistFile.get().getBoolean(target.getUniqueId() + ".welcomeSound");
								if (soundEnabled == true) {
									String soundType = ConfigFile.get().getString("WelcomeMessages.sound.type");
									target.playSound(target.getLocation(), Sound.valueOf(soundType), 0.2F, 1.0F);
								}
								player.sendMessage(prefix + "§7Du hast " + target.getName() + " teleportiert!");
								target.sendMessage(prefix + "§7Du wurdest teleportiert!");
								target.teleport(location);
							} else
								player.sendMessage(prefix + "§c" + pointName + " von " + target.getName() + " wurde noch nicht gesetzt!");
						} else
							player.sendMessage(prefix + "§7Spieler §6" + args[0] + " §7nicht gefunden!");
					} else
						player.sendMessage(prefix + msgnoperm + "mjs.home.use.others");
				} else
					player.sendMessage(prefix + msguse);
			} else if (player.hasPermission("mjs.moduleinfo")) {
				player.sendMessage(prefix + "§7Das §6Homepoints-Modul §7ist derzeit " + colorDisabled + msgdisabled);
			} else
				player.sendMessage(prefix + msgunknown);
		} else {
			if (moduleEnabled == true) {
				if (args.length == 1) {
					Player target = Bukkit.getServer().getPlayer(args[0]);
					if (target != null) {
						boolean enabled = UserlistFile.get().getBoolean(target.getUniqueId() + ".home.enabled");
						if (enabled == true) {
							World world = Bukkit.getWorld(UserlistFile.get().getString(target.getUniqueId() + ".home.world"));
							double locX = UserlistFile.get().getDouble(target.getUniqueId() + ".home.x");
							double locY = UserlistFile.get().getDouble(target.getUniqueId() + ".home.y");
							double locZ = UserlistFile.get().getDouble(target.getUniqueId() + ".home.z");
							float yaw = (float) UserlistFile.get().getDouble(target.getUniqueId() + ".home.yaw");
							float pitch = (float) UserlistFile.get().getDouble(target.getUniqueId() + ".home.pitch");
							Location location = new Location(world, locX, locY, locZ, yaw, pitch);
							Boolean soundEnabled = UserlistFile.get().getBoolean(target.getUniqueId() + ".welcomeSound");
							if (soundEnabled == true) {
								String soundType = ConfigFile.get().getString("WelcomeMessages.sound.type");
								target.playSound(target.getLocation(), Sound.valueOf(soundType), 0.2F, 1.0F);
							}
							sender.sendMessage(prefix + "§7Du hast " + target.getName() + " teleportiert!");
							target.sendMessage(prefix + "§7Du wurdest teleportiert!");
							target.teleport(location);
						} else
							sender.sendMessage(prefix + "§c" + pointName + " von " + target.getName() + " wurde noch nicht gesetzt!");
					} else
						sender.sendMessage(prefix + "§7Spieler §6" + args[0] + " §7nicht gefunden!");
				} else
					sender.sendMessage(prefix + msguseconsole);
			} else
				sender.sendMessage(prefix + "§7Das §6Homepoints-Modul §7ist derzeit " + colorDisabled + msgdisabled);
		}
		return false;
	}

}
