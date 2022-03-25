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

public class TeleportCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		String prefix = MessagesFile.get().getString("Messages.Defaults.prefix");
		String msgnoperm = MessagesFile.get().getString("Messages.Errors.noPermission");
		Boolean moduleEnabled = ConfigFile.get().getBoolean("Modules.Teleport.enabled");
		String msgdisabled = MessagesFile.get().getString("Messages.Defaults.disabled.text");
		String colorDisabled = MessagesFile.get().getString("Messages.Defaults.disabled.color");
		String msgunknown = MessagesFile.get().getString("Messages.Errors.unknownCommand");
		String msguse = MessagesFile.get().getString("Messages.Modules.Teleport.use");
		Boolean soundEnabled;
		Player target1;
		Player target2;
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (moduleEnabled == true) {
				if (player.hasPermission("mjs.teleport.use")) {
					if (args.length == 1) {
						target2 = Bukkit.getServer().getPlayer(args[0]);
						if (target2 != null) {
							if (player.getName().equalsIgnoreCase(target2.getName())) {
								player.sendMessage(prefix + "§cDu kannst dich nicht zu dir selbst teleportieren!");
							} else {
								player.teleport(target2);
								soundEnabled = UserlistFile.get().getBoolean(player.getUniqueId() + ".welcomeSound");
								if (soundEnabled == true) {
									String soundType = ConfigFile.get().getString("WelcomeMessages.sound.type");
									player.playSound(player.getLocation(), Sound.valueOf(soundType), 0.2F, 1.0F);
								}
								player.sendMessage(prefix + "§7Du hast dich zu " + target2.getName() + " teleportiert!");
							}
						} else
							player.sendMessage(prefix + "§7Spieler §6" + args[0] + " §7nicht gefunden!");
					} else if (args.length == 2) {
						target1 = Bukkit.getServer().getPlayer(args[0]);
						target2 = Bukkit.getServer().getPlayer(args[1]);
						if (target1 != null) {
							if (target2 != null) {
								if (target1.getName().equalsIgnoreCase(target2.getName())) {
									player.sendMessage(prefix + "§cDu kannst einen Spieler nicht zu sich selbst teleportieren!");
								} else {
									target1.teleport(target2);
									soundEnabled = UserlistFile.get().getBoolean(target1.getUniqueId() + ".welcomeSound");
									if (soundEnabled == true) {
										String soundType = ConfigFile.get().getString("WelcomeMessages.sound.type");
										target1.playSound(target1.getLocation(), Sound.valueOf(soundType), 0.2F, 1.0F);
									}
									if (player.getName().equalsIgnoreCase(target1.getName())) {
										player.sendMessage(prefix + "§7Du hast dich zu " + target2.getName() + " teleportiert!");
									} else {
										player.sendMessage(prefix + "§7Du hast " + target1.getName() + " zu " + target2.getName() + " teleportiert!");
										target1.sendMessage(prefix + "§7Du wurdest zu " + target2.getName() + " teleportiert!");
									}
								}
							} else
								player.sendMessage(prefix + "§7Spieler §6" + args[1] + " §7nicht gefunden!");
						} else
							player.sendMessage(prefix + "§7Spieler §6" + args[0] + " §7nicht gefunden!");
					} else if (args.length == 3) {
						World world = player.getWorld();
						double locX = Double.parseDouble(args[0]);
						double locY = Double.parseDouble(args[1]);
						double locZ = Double.parseDouble(args[2]);
						Location location = new Location(world, locX, locY, locZ);
						player.teleport(location);
						soundEnabled = UserlistFile.get().getBoolean(player.getUniqueId() + ".welcomeSound");
						if (soundEnabled == true) {
							String soundType = ConfigFile.get().getString("WelcomeMessages.sound.type");
							player.playSound(player.getLocation(), Sound.valueOf(soundType), 0.2F, 1.0F);
						}
						player.sendMessage(prefix + "§7Du hast dich zu §aX§8=§9" + locX + " §aY§8=§9" + locY + " §aZ§8=§9" + locZ + " §7teleportiert!");
					} else if (args.length == 4) {
						target1 = Bukkit.getServer().getPlayer(args[0]);
						if (target1 != null) {
							World world = target1.getWorld();
							double locX = Double.parseDouble(args[1]);
							double locY = Double.parseDouble(args[2]);
							double locZ = Double.parseDouble(args[3]);
							Location location = new Location(world, locX, locY, locZ);
							target1.teleport(location);
							soundEnabled = UserlistFile.get().getBoolean(target1.getUniqueId() + ".welcomeSound");
							if (soundEnabled == true) {
								String soundType = ConfigFile.get().getString("WelcomeMessages.sound.type");
								target1.playSound(target1.getLocation(), Sound.valueOf(soundType), 0.2F, 1.0F);
							}
							if (player.getName().equalsIgnoreCase(target1.getName())) {
								player.sendMessage(prefix + "§7Du hast dich zu §aX§8=§9" + locX + " §aY§8=§9" + locY + " §aZ§8=§9" + locZ + " §7teleportiert!");
							} else {
								player.sendMessage(prefix + "§7Du hast " + target1.getName() + " zu §aX§8=§9" + locX + " §aY§8=§9" + locY + " §aZ§8=§9" + locZ + " §7teleportiert!");
								target1.sendMessage(prefix + "§7Du wurdest zu §aX§8=§9" + locX + " §aY§8=§9" + locY + " §aZ§8=§9" + locZ + " §7teleportiert!");
							}
						} else
							player.sendMessage(prefix + "§7Spieler §6" + args[0] + " §7nicht gefunden!");
					} else 
						player.sendMessage(prefix + msguse);
				} else
					player.sendMessage(prefix + msgnoperm + "mjs.teleport.use");
			} else if (player.hasPermission("mjs.moduleinfo")) {
				player.sendMessage(prefix + "§7Das §6Teleport-Modul §7ist derzeit " + colorDisabled + msgdisabled);
			} else
				player.sendMessage(prefix + msgunknown);
		} else
			sender.sendMessage("[MJ-System]>> Dieser Befehl muss als Spieler ausgeführt werden!");
		return false;
	}

}
