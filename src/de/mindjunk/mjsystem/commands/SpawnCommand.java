package de.mindjunk.mjsystem.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.mindjunk.mjsystem.files.ConfigFile;
import de.mindjunk.mjsystem.files.MessagesFile;
import de.mindjunk.mjsystem.files.UserlistFile;

public class SpawnCommand implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		Location spawn;
		Boolean moduleEnabled = ConfigFile.get().getBoolean("Modules.SpawnTeleport.enabled");
		String prefix = MessagesFile.get().getString("Messages.Defaults.prefix");
		String msgnoperm = MessagesFile.get().getString("Messages.Errors.noPermission");
		String msgdisabled = MessagesFile.get().getString("Messages.Defaults.disabled.text");
		String colorDisabled = MessagesFile.get().getString("Messages.Defaults.disabled.color");
		String msgunknown = MessagesFile.get().getString("Messages.Errors.unknownCommand");
		String msguse = MessagesFile.get().getString("Messages.Modules.SpawnTeleport.use");
		String msguseconsole = MessagesFile.get().getString("Messages.Modules.SpawnTeleport.useconsole");
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (moduleEnabled == true) {
				if (args.length == 0) {
					if (player.hasPermission("mjs.spawn.use")) {
						spawn = player.getWorld().getSpawnLocation();
						Boolean soundEnabled = UserlistFile.get().getBoolean(player.getUniqueId() + ".welcomeSound");
						if (soundEnabled == true) {
							String soundType = ConfigFile.get().getString("WelcomeMessages.sound.type");
							player.playSound(player.getLocation(), Sound.valueOf(soundType), 0.2F, 1.0F);
						}
						player.sendMessage(prefix + " §7Du teleportierst dich zum §9Spawn");
						player.teleport(spawn);
					} else
						player.sendMessage(prefix + msgnoperm + "mjs.spawn.use");
				} else if (args.length == 1) {
					if (player.hasPermission("mjs.spawn.use")) {
						spawn = Bukkit.getWorld(args[0]).getSpawnLocation();
						if (spawn == null) {
							player.sendMessage(prefix + " §cWelt existiert nicht!");
						} else {
							Boolean soundEnabled = UserlistFile.get().getBoolean(player.getUniqueId() + ".welcomeSound");
							if (soundEnabled == true) {
								String soundType = ConfigFile.get().getString("WelcomeMessages.sound.type");
								player.playSound(player.getLocation(), Sound.valueOf(soundType), 0.2F, 1.0F);
							}
							player.sendMessage(
									prefix + " §7Du teleportierst dich zum §9Spawn von " + args[0].toUpperCase());
							player.teleport(spawn);
						}
					} else
						player.sendMessage(prefix + msgnoperm + "mjs.spawn.use");
				} else if (args.length == 2) {
					if (player.hasPermission("mjs.spawn.use.others")) {
						spawn = Bukkit.getWorld(args[0]).getSpawnLocation();
						Player target = Bukkit.getPlayer(args[1]);
						if (spawn == null) {
							player.sendMessage(prefix + " §cWelt existiert nicht!");
						} else {
							if (target != null) {
								Boolean soundEnabled = UserlistFile.get()
										.getBoolean(target.getUniqueId() + ".welcomeSound");
								if (soundEnabled == true) {
									String soundType = ConfigFile.get().getString("WelcomeMessages.sound.type");
									target.playSound(target.getLocation(), Sound.valueOf(soundType), 0.2F, 1.0F);
								}
								player.sendMessage(prefix + " §7Du teleportierst " + target + " zum §9Spawn von "
										+ args[0].toUpperCase());
								target.teleport(spawn);
							} else
								player.sendMessage(prefix + "§7Spieler §6" + args[0] + " §7nicht gefunden!");
						}
					} else
						player.sendMessage(prefix + msgnoperm + "mjs.spawn.use.others");
				} else
					player.sendMessage(prefix + msguse);
			} else if (player.hasPermission("mjs.moduleinfo")) {
				player.sendMessage(
						prefix + " §7Das §6SpawnTeleport-Modul §7ist derzeit " + colorDisabled + msgdisabled);
			} else
				player.sendMessage(prefix + msgunknown);
		} else {
			if (args.length == 1) {
				Player target = Bukkit.getPlayer(args[0]);
				if (target != null) {
					String world = target.getWorld().getName();
					spawn = Bukkit.getWorld(world).getSpawnLocation();
					Boolean soundEnabled = UserlistFile.get().getBoolean(target.getUniqueId() + ".welcomeSound");
					if (soundEnabled == true) {
						String soundType = ConfigFile.get().getString("WelcomeMessages.sound.type");
						target.playSound(target.getLocation(), Sound.valueOf(soundType), 0.2F, 1.0F);
					}
					sender.sendMessage(prefix + " §7Du teleportierst " + target + " zum §9Spawn");
					target.teleport(spawn);
				} else
					sender.sendMessage(prefix + "§7Spieler §6" + args[0] + " §7nicht gefunden!");
			} else if (args.length == 2) {
				spawn = Bukkit.getWorld(args[0]).getSpawnLocation();
				Player target = Bukkit.getPlayer(args[1]);
				if (spawn == null) {
					sender.sendMessage(prefix + " §cWelt existiert nicht!");
				} else {
					if (target != null) {
						Boolean soundEnabled = UserlistFile.get().getBoolean(target.getUniqueId() + ".welcomeSound");
						if (soundEnabled == true) {
							String soundType = ConfigFile.get().getString("WelcomeMessages.sound.type");
							target.playSound(target.getLocation(), Sound.valueOf(soundType), 0.2F, 1.0F);
						}
						sender.sendMessage(
								prefix + " §7Du teleportierst " + target + " zum §9Spawn von " + args[0].toUpperCase());
						target.teleport(spawn);
					} else
						sender.sendMessage(prefix + "§7Spieler §6" + args[0] + " §7nicht gefunden!");
				}
			} else
				sender.sendMessage(prefix + msguseconsole);
		}
		return false;
	}

}
