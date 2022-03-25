package de.mindjunk.mjsystem.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import de.mindjunk.mjsystem.files.MessagesFile;
import de.mindjunk.mjsystem.files.PermissionsFile;
import de.mindjunk.mjsystem.files.ConfigFile;
import de.mindjunk.mjsystem.files.LiveFile;
import de.mindjunk.mjsystem.files.TravelpointsFile;
import de.mindjunk.mjsystem.files.UserlistFile;
import de.mindjunk.mjsystem.main.Main;

public class MJSystemCommand implements CommandExecutor {

	Plugin plugin = Main.getPlugin(Main.class);

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		String msgreload = MessagesFile.get().getString("Messages.Defaults.pluginReload");
		String msgreloaded = MessagesFile.get().getString("Messages.Defaults.pluginReloaded");
		if (sender instanceof Player) {
			Player player = (Player) sender;
			String msgnoperm = MessagesFile.get().getString("Messages.Errors.noPermission");
			String prefix = MessagesFile.get().getString("Messages.Defaults.prefix");
			String cmdcolor = MessagesFile.get().getString("Messages.Defaults.commandColor");
			String modulecolor = MessagesFile.get().getString("Messages.Defaults.moduleColor");
			String msgunknown = MessagesFile.get().getString("Messages.Errors.unknownCommand");
			String helptext = MessagesFile.get().getString("Messages.Defaults.helptext");
			if (args.length == 2) {
				
			} else if (args.length == 1) {
				if (args[0].equalsIgnoreCase("info")) {
					if (player.hasPermission("mjs.info")) {
						player.sendMessage("§8[§7MJ-System§8]>> §aMJ System Plugin");
						player.sendMessage("§8[§7MJ-System§8]>> §7Version: §2" + plugin.getDescription().getVersion());
						player.sendMessage("§8[§7MJ-System§8]>> §7Autor: §aMindJunk");
						player.sendMessage("§8[§7MJ-System§8]>> §7Hilfe: " + cmdcolor + "/mjs help");
						player.sendMessage("§8[§7MJ-System§8]>> §7Website: §9www.mindjunk.de");
					} else
						player.sendMessage(prefix + msgnoperm + "mjs.info");
				} else if (args[0].equalsIgnoreCase("help")) {
					if (player.hasPermission("mjs.help")) {
						player.sendMessage(helptext);
						player.sendMessage("§8| " + cmdcolor + "/mjs help §8- §7Zeigt diese Hilfeseite an");

						if (player.hasPermission("mjs.info")) {
							player.sendMessage("§8| " + cmdcolor + "/mjs info §8- §7Zeigt die Plugin-Informationen an");
						}

						if (player.hasPermission("mjs.reload")) {
							player.sendMessage("§8| " + cmdcolor + "/mjs reload §8- §7Lädt alle MJS-Dateien neu");
						}

						if (player.hasPermission("mjs.cmdlist.show")) {
							player.sendMessage("§8|----------");
							player.sendMessage(
									"§8| " + cmdcolor + "/cmd §8- §7Zeigt einen Link zur Befehlsliste im Chat");
						}

						if (player.hasPermission("mjs.cmdlist.set")) {
							player.sendMessage(
									"§8| " + cmdcolor + "/cmd §o<URL>§r §8- §7Bearbeite den Link zur Befehlsliste");
						}

						if (player.hasPermission("mjs.cmdlist.toggle")) {
							player.sendMessage("§8| " + cmdcolor + "/tcmd §8- §7Aktiviert/Deaktiviert das "
									+ modulecolor + "§oCommandlist-Modul");
						}

						if (player.hasPermission("mjs.acmdlist.show")) {
							player.sendMessage("§8|----------");
							player.sendMessage(
									"§8| " + cmdcolor + "/acmd §8- §7Zeigt einen Link zur Admin-Befehlsliste im Chat");
						}

						if (player.hasPermission("mjs.acmdlist.set")) {
							player.sendMessage("§8| " + cmdcolor
									+ "/acmd §o<URL>§r §8- §7Bearbeite den Link zur Admin-Befehlsliste");
						}

						if (player.hasPermission("mjs.acmdlist.toggle")) {
							player.sendMessage("§8| " + cmdcolor + "/tacmd §8- §7Aktiviert/Deaktiviert das "
									+ modulecolor + "§oAdminCommandlist-Modul");
						}

						if (player.hasPermission("mjs.rules.show")) {
							player.sendMessage("§8|----------");
							player.sendMessage(
									"§8| " + cmdcolor + "/ru §8- §7Zeigt einen Link zu den Serverregeln im Chat");
						}

						if (player.hasPermission("mjs.rules.set")) {
							player.sendMessage(
									"§8| " + cmdcolor + "/ru §o<URL>§r §8- §7Bearbeite den Link zu den Serverregeln");
						}

						if (player.hasPermission("mjs.rules.toggle")) {
							player.sendMessage("§8| " + cmdcolor + "/tru §8- §7Aktiviert/Deaktiviert das " + modulecolor
									+ "§oRules-Modul");
						}

						if (player.hasPermission("mjs.pricelist.show")) {
							player.sendMessage("§8|----------");
							player.sendMessage("§8| " + cmdcolor + "/pr §8- §7Zeigt einen Link zur Preisliste im Chat");
						}

						if (player.hasPermission("mjs.pricelist.set")) {
							player.sendMessage(
									"§8| " + cmdcolor + "/pr §o<URL>§r §8- §7Bearbeite den Link zur Preisliste");
						}

						if (player.hasPermission("mjs.pricelist.toggle")) {
							player.sendMessage("§8| " + cmdcolor + "/tpr §8- §7Aktiviert/Deaktiviert das " + modulecolor
									+ "§oPricelist-Modul");
						}

						if (player.hasPermission("mjs.discord.show")) {
							player.sendMessage("§8|----------");
							player.sendMessage(
									"§8| " + cmdcolor + "/dc §8- §7Zeigt einen Link zum Discordserver im Chat");
						}

						if (player.hasPermission("mjs.discord.set")) {
							player.sendMessage(
									"§8| " + cmdcolor + "/dc §o<URL>§r §8- §7Bearbeite den Link zum Discordserver");
						}

						if (player.hasPermission("mjs.discord.toggle")) {
							player.sendMessage("§8| " + cmdcolor + "/tdc §8- §7Aktiviert/Deaktiviert das " + modulecolor
									+ "§oDiscord-Modul");
						}
						
						String homeName = MessagesFile.get().getString("Messages.Modules.Homepoints.name");
						if (player.hasPermission("mjs.home.use")) {
							player.sendMessage("§8|----------");
							player.sendMessage("§8| " + cmdcolor + "/h§r §8- §7Teleportiere dich zu §9" + homeName);
						}

						if (player.hasPermission("mjs.home.set")) {
							player.sendMessage("§8| " + cmdcolor + "/sh§r §8- §7Erstelle/bearbeite §9" + homeName);
						}
						
						String travelName = MessagesFile.get().getString("Messages.Modules.Travelpoints.name");
						if (player.hasPermission("mjs.travel.use")) {
							player.sendMessage("§8|----------");
							player.sendMessage("§8| " + cmdcolor + "/trv§r §8- §7Zeige die §9" + travelName + "-Liste");
							player.sendMessage("§8| " + cmdcolor + "/trv §o<REISEPUNKT>§r §8- §7Teleportiere dich zu §9" + travelName);
						}

						if (player.hasPermission("mjs.travel.edit")) {
							player.sendMessage("§8| " + cmdcolor + "/strv §o<REISEPUNKT>§r §8- §7Erstelle/bearbeite §9" + travelName);
							player.sendMessage("§8| " + cmdcolor + "/dtrv §o<REISEPUNKT>§r §8- §7Entferne §9" + travelName);
						}

						if (player.hasPermission("mjs.travel.toggle")) {
							player.sendMessage("§8| " + cmdcolor + "/tw §8- §7Aktiviert/Deaktiviert das " + modulecolor + "§oTravelpoints-Modul");
						}

						if (player.hasPermission("mjs.live.show")) {
							player.sendMessage("§8|----------");
							player.sendMessage(
									"§8| " + cmdcolor + "/live §o<NAME>§r §8- §7Postet eine Livestream-Meldung");
						}

						if (player.hasPermission("mjs.live.edit")) {
							player.sendMessage("§8| " + cmdcolor
									+ "/live §o<NAME> <URL>§r §8- §7Füge der Liste einen Streamkanal hinzu oder ändere dessen Link");
							player.sendMessage("§8| " + cmdcolor
									+ "/live §o<NAME> delete§r §8- §7Lösche einen Streamkanal von der Liste");
							player.sendMessage("§8| " + cmdcolor
									+ "/live §o<NAME> sound§r §8- §7Aktiviere/Deaktiviere den Sound für einen Streamkanal");
							player.sendMessage("§8| " + cmdcolor
									+ "/live §o<NAME> sound <SOUND>§r §8- §7Ändere den Sound für einen Streamkanal");
						}

						if (player.hasPermission("mjs.live.toggle")) {
							player.sendMessage("§8| " + cmdcolor + "/tl §8- §7Aktiviert/Deaktiviert das " + modulecolor
									+ "§oLive-Modul");
						}

					} else
						player.sendMessage(prefix + msgnoperm + "mjs.help");
				} else if (args[0].equalsIgnoreCase("reload")) {
					if (player.hasPermission("mjs.reload")) {
						player.sendMessage(prefix + "§f" + msgreload);
						ConfigFile.reload();
						PermissionsFile.reload();
						MessagesFile.reload();
						UserlistFile.reload();
						TravelpointsFile.reload();
						LiveFile.reload();
						player.sendMessage(prefix + "§a" + msgreloaded);
					} else
						player.sendMessage(prefix + msgnoperm + "mjs.reload");
				} else
					player.sendMessage(prefix + msgunknown);
			} else
				player.sendMessage(prefix + msgunknown);
		} else if (args.length == 1) {
			if (args[0].equalsIgnoreCase("info")) {
				System.out.println(ChatColor.WHITE + "[MJ-System]>> " + ChatColor.GREEN + "MJ System Plugin");
				System.out.println(ChatColor.WHITE + "[MJ-System]>> " + ChatColor.GREEN + "Version: " + plugin.getDescription().getVersion());
				System.out.println(ChatColor.WHITE + "[MJ-System]>> " + ChatColor.GREEN + "Autor: MindJunk");
				System.out.println(ChatColor.WHITE + "[MJ-System]>> " + ChatColor.GREEN + "Website: www.mindjunk.de");
			} else if (args[0].equalsIgnoreCase("reload")) {
				System.out.println(ChatColor.WHITE + "[MJ-System]>> " + msgreload);
				ConfigFile.reload();
				PermissionsFile.reload();
				MessagesFile.reload();
				UserlistFile.reload();
				TravelpointsFile.reload();
				LiveFile.reload();
				System.out.println(ChatColor.WHITE + "[MJ-System]>> " + ChatColor.GREEN + msgreloaded);
			}
		}
		return false;
	}

}
