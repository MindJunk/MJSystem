package de.mindjunk.mjsystem.commands;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.mindjunk.mjsystem.files.MessagesFile;
import de.mindjunk.mjsystem.files.ConfigFile;
import de.mindjunk.mjsystem.files.LiveFile;

public class LiveCommand implements CommandExecutor{
	
	private String soundType;

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
			String msgshow = MessagesFile.get().getString("Messages.Modules.Live.show");
			String msgedit = MessagesFile.get().getString("Messages.Modules.Live.edit");
			String msgsound = MessagesFile.get().getString("Messages.Modules.Live.sound");
			Boolean moduleenabled = ConfigFile.get().getBoolean("Modules.Live.enabled");
			if(moduleenabled == true) {
					if(args.length == 1) {
						if(player.hasPermission("mjs.live.show")) {
							soundType = LiveFile.get().getString("Channels." + args[0].toLowerCase() + ".sound.type");
							String link = LiveFile.get().getString("Channels." + args[0].toLowerCase() + ".link");
							Boolean sound = LiveFile.get().getBoolean("Channels." + args[0].toLowerCase() + ".sound.enabled");
							if(link != null) {
								Bukkit.broadcastMessage(prefix + "§a" + args[0].toUpperCase() + " §7ist jetzt LIVE auf §a" + link);
								if(sound == true) {
									for (Player all : Bukkit.getOnlinePlayers()) {
										all.playSound(all.getLocation(), Sound.valueOf(soundType), 0.8F, 1.0F);
									}
								}
							} else
								player.sendMessage(prefix + "§7Der Streamkanal §6" + args[0] + " §7ist nicht auf der Liste!");
						} else
							player.sendMessage(prefix + msgunknown);
					} else if(args.length == 2) {
						String link = LiveFile.get().getString("Channels." + args[0].toLowerCase() + ".link");
						Boolean sound = LiveFile.get().getBoolean("Channels." + args[0].toLowerCase() + ".sound.enabled");
						if (args[1].equalsIgnoreCase("delete")) {
							if(player.hasPermission("mjs.live.edit")) {
								if(link != null) {
									LiveFile.get().set("Channels." + args[0].toLowerCase(), null);
									LiveFile.save();
									player.sendMessage(prefix + "§7Du hast den Streamkanal §6" + args[0] + " §7von der Liste entfernt!");
									System.out.println("[MJSystem]>> Der Spieler " + player.getName() + " hat den Streamkanal " + args[0] + " von der Liste entfernt!");
								} else
									player.sendMessage(prefix + "§7Der Streamkanal §6" + args[0] + " §7ist nicht auf der Liste!");
							} else
								player.sendMessage(prefix + msgunknown);
						} else if (args[1].equalsIgnoreCase("sound")) {
							if(player.hasPermission("mjs.live.edit")) {
								if(link != null) {
									if(sound == true) {
										LiveFile.get().set("Channels." + args[0].toLowerCase() + ".sound.enabled", false);
										LiveFile.save();
										player.sendMessage(prefix + "§7Du hast den Sound für die Livemeldung von §6" + args[0] + " " + colorDisabled + msgdisabled + "!");
										System.out.println("[MJSystem]>> Der Spieler " + player.getName() + " hat den Sound für die Livemeldung von " + args[0] + " " + msgdisabled + "!");
										return false;
									} else
										LiveFile.get().set("Channels." + args[0].toLowerCase() + ".sound.enabled", true);
										LiveFile.save();
										player.sendMessage(prefix + "§7Du hast den Sound für die Livemeldung von §6" + args[0] + " " + colorEnabled + msgenabled + "!");
										System.out.println("[MJSystem]>> Der Spieler " + player.getName() + " hat den Sound für die Livemeldung von " + args[0] + " " + msgenabled + "!");
										return false;
								} else
									player.sendMessage(prefix + "§7Der Streamkanal §6" + args[0] + " §7ist nicht auf der Liste!");
							} else
								player.sendMessage(prefix + msgunknown);
						} else
							if(player.hasPermission("mjs.live.edit")) {
								Date date = new Date();
					            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
								LiveFile.get().set("Channels." + args[0].toLowerCase() + ".link", args[1]);
								LiveFile.get().set("Channels." + args[0].toLowerCase() + ".sound.enabled", true);
								LiveFile.get().set("Channels." + args[0].toLowerCase() + ".sound.type", "ENTITY_MINECART_RIDING");
								LiveFile.get().set("Channels." + args[0].toLowerCase() + ".added.user", player.getName());
								LiveFile.get().set("Channels." + args[0].toLowerCase() + ".added.time", format.format(date));
								LiveFile.save();
								player.sendMessage(prefix + "§7Du hast den Streamkanal §6" + args[0] + " §7mit dem Link §6" + args[1] + " §7zur Liste hinzugefügt!");
								System.out.println("[MJSystem]>> Der Spieler " + player.getName() + " hat den Streamkanal " + args[0] + " mit dem Link " + args[1] + " zur Liste hinzugefügt");
							} else
								player.sendMessage(prefix + msgunknown);
					} else if(args.length == 3) {
						if(player.hasPermission("mjs.live.edit")) {
							if (args[1].equalsIgnoreCase("sound")) {
								LiveFile.get().set("Channels." + args[0].toLowerCase() + ".sound.type", args[2]);
								LiveFile.save();
								player.sendMessage(prefix + "§7Du hast den Sound für die Livemeldung von §6" + args[0] + " §7zu §6" + args[2] + " §7geändert!");
								System.out.println("[MJSystem]>> Der Spieler " + player.getName() + " hat den Sound für die Livemeldung von " + args[0] + " zu " + args[2] + " geändert!");
							} else
								player.sendMessage(prefix + msgedit);
								player.sendMessage(prefix + msgsound);
						} else if(player.hasPermission("mjs.live.show")) {
							player.sendMessage(prefix + msgshow);
						} else
							player.sendMessage(prefix + msgunknown);
					} else
						if(player.hasPermission("mjs.live.edit")) {
							player.sendMessage(prefix + msgedit);
							player.sendMessage(prefix + msgsound);
						} else if(player.hasPermission("mjs.live.show")) {
							player.sendMessage(prefix + msgshow);
						} else
							player.sendMessage(prefix + msgunknown);
			} else if(player.hasPermission("mjs.moduleinfo")) {
				player.sendMessage(prefix + "§7Das " + modulecolor + "Live-Modul §7ist derzeit " + colorDisabled + msgdisabled);
			} else
				player.sendMessage(prefix + msgunknown);
		} else
			sender.sendMessage("[MJ-System]>> Dieser Befehl muss als Spieler ausgeführt werden!");
		return false;
	}

}
