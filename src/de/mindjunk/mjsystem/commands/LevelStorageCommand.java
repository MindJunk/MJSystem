package de.mindjunk.mjsystem.commands;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import de.mindjunk.mjsystem.files.ConfigFile;
import de.mindjunk.mjsystem.files.MessagesFile;
import de.mindjunk.mjsystem.files.UserlistFile;

public class LevelStorageCommand implements CommandExecutor{

	@Override
	public boolean onCommand( CommandSender sender, Command command, String label, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			String prefix = MessagesFile.get().getString("Messages.Defaults.prefix");
			Boolean moduleEnabled = ConfigFile.get().getBoolean("Modules.LevelStorage.enabled");
			String msgdisabled = MessagesFile.get().getString("Messages.Defaults.disabled.text");
			String colorDisabled = MessagesFile.get().getString("Messages.Defaults.disabled.color");
			String lvlName = MessagesFile.get().getString("Messages.Modules.LvlStorage.lvl");
			String storageName = MessagesFile.get().getString("Messages.Modules.LvlStorage.storageName");
			String shardName = MessagesFile.get().getString("Messages.Modules.LvlStorage.shardName");
			String msguse = MessagesFile.get().getString("Messages.Modules.LvlStorage.use");
			String msguseshard = MessagesFile.get().getString("Messages.Modules.LvlStorage.useShard");
			String msgusesynth = MessagesFile.get().getString("Messages.Modules.LvlStorage.useSynth");
			String msgunknown = MessagesFile.get().getString("Messages.Errors.unknownCommand");
			if(moduleEnabled == true) {
				if (player.hasPermission("mjs.lvl")) {
					if (args.length == 2) {
						if (args[0].equalsIgnoreCase("shard")) {
							if (args[1].equalsIgnoreCase("save")) {
								int playerLVL = player.getLevel();
								if (playerLVL != 0) {
									int shardLVL = UserlistFile.get().getInt(player.getUniqueId() + ".lvlStorage.shardLevels");
									if (shardLVL == 0) {
										if (playerLVL < 100) {
											UserlistFile.get().set(player.getUniqueId() + ".lvlStorage.shardLevels", playerLVL);
											UserlistFile.save();
											player.sendMessage(prefix + "§7Du hast §b" + player.getLevel() + " §3" + lvlName + " §7in deinem §3" + shardName + " §7gespeichert");
											player.setLevel(0);
											player.setExp(0);
											return false;
										} else
											UserlistFile.get().set(player.getUniqueId() + ".lvlStorage.shardLevels", 99);
											UserlistFile.save();
											playerLVL = playerLVL - 99;
											player.setLevel(playerLVL);
											player.setExp(0);
											player.sendMessage(prefix + "§7Du hast §b99 §3" + lvlName + " §7in deinem §3" + shardName + " §7gespeichert");
											player.sendMessage(prefix + "§7Dein Charakter hat noch §b" + playerLVL + " §3" + lvlName + " §7übrig");
									} else {
										player.sendMessage(prefix + "§cFehler! §7Du hast bereits einen §3" + shardName + " §7erzeugt. §3" + shardName + " §3" + lvlName + " §8x §b" + shardLVL);
									}
								} else
									player.sendMessage(prefix + "§cFehler! §7Du hast keine §3" + lvlName
											+ "§7, die du speichern kannst");
							} else if (args[1].equalsIgnoreCase("load")) {
								if (UserlistFile.get().getInt(player.getUniqueId() + ".lvlStorage.shardLevels") != 0) {
									int shardLVL = UserlistFile.get().getInt(player.getUniqueId() + ".lvlStorage.shardLevels");
									int newLVL = player.getLevel() + shardLVL;
									UserlistFile.get().set(player.getUniqueId() + ".lvlStorage.shardLevels", 0);
									UserlistFile.save();
									player.setLevel(newLVL);
									player.setExp(0);
									player.sendMessage(prefix + "§7Dein §3" + lvlName + " §7wurde auf §3" + lvlName + " §b" + newLVL + " §7gesetzt");
									player.sendMessage(prefix + "§7Dein §3" + shardName + " §7ist wieder entladen");
								} else
									player.sendMessage(prefix + "§cFehler! §7Du hast keinen §3" + shardName + " §7erzeugt");
							} else
								player.sendMessage(prefix + msguseshard);
						} else if (args[0].equalsIgnoreCase("synth")) {
						    if (args[1].equalsIgnoreCase("one")) {
						    	if( player.getInventory().firstEmpty() != -1) {
							    	int savedLVL = UserlistFile.get().getInt(player.getUniqueId() + ".lvlStorage.savedLevels");
							    	if (savedLVL > 9) {
							    		int levelsUsed = 10;
							    		int newLVL = savedLVL - levelsUsed;
							    		ItemStack DIAMONDS = new ItemStack(Material.DIAMOND, 1);
										PlayerInventory inventory = player.getInventory();
										inventory.addItem(DIAMONDS);
										player.playSound(player.getLocation(), Sound.BLOCK_ENCHANTMENT_TABLE_USE, 1.0F, 0.7F);
										UserlistFile.get().set(player.getUniqueId() + ".lvlStorage.savedLevels", newLVL);
										UserlistFile.save();
										player.sendMessage(prefix + "§7Du hast §b10 §3" + lvlName + " §7zu §b1 §3Diamanten §7synthetisiert");
										player.sendMessage(prefix + "§7In deinem §3" + storageName + " §7sind noch §b" + newLVL + " §3" + lvlName);
							    	} else
							    		player.sendMessage(prefix + "§cFehler! §7Du hast nicht genug §3" + lvlName + " §7in deinem §3" + storageName);
							    } else
							    	player.sendMessage(prefix + "§cFehler! §7Nicht genug Platz im Inventar");
						    } else if (args[1].equalsIgnoreCase("all")) {
						    	if( player.getInventory().firstEmpty() != -1) {
							    	int savedLVL = UserlistFile.get().getInt(player.getUniqueId() + ".lvlStorage.savedLevels");
							    	if (savedLVL > 9) {
							    		int diamondAmount = savedLVL / 10;
							    		int levelsUsed = diamondAmount * 10;
							    		int newLVL = savedLVL - levelsUsed;
							    		ItemStack DIAMONDS = new ItemStack(Material.DIAMOND, diamondAmount);
										PlayerInventory inventory = player.getInventory();
										inventory.addItem(DIAMONDS);
										player.playSound(player.getLocation(), Sound.BLOCK_ENCHANTMENT_TABLE_USE, 1.0F, 0.7F);
										UserlistFile.get().set(player.getUniqueId() + ".lvlStorage.savedLevels", newLVL);
										UserlistFile.save();
										player.sendMessage(prefix + "§7Du hast §b" + levelsUsed + " §3" + lvlName + " §7zu §b" + diamondAmount + " §3Diamanten §7synthetisiert");
										player.sendMessage(prefix + "§7In deinem §3" + storageName + " §7sind noch §b" + newLVL + " §3" + lvlName);
							    	} else
							    		player.sendMessage(prefix + "§cFehler! §7Du hast nicht genug §3" + lvlName + " §7in deinem §3" + storageName);
							    } else
							    	player.sendMessage(prefix + "§cFehler! §7Nicht genug Platz im Inventar");
						    }
						} else
							player.sendMessage(prefix + msgusesynth);
				} else if(args.length == 1) {
					if (args[0].equalsIgnoreCase("save")) {
						if(player.getLevel() != 0) {
							if(UserlistFile.get().getInt(player.getUniqueId() + ".lvlStorage.storageAmount") != 0) {
								int currentLVL = player.getLevel();
								int savedLVL = UserlistFile.get().getInt(player.getUniqueId() + ".lvlStorage.savedLevels");
								int newLVL = savedLVL + currentLVL;
								if(newLVL < 100) {
									UserlistFile.get().set(player.getUniqueId() + ".lvlStorage.savedLevels", newLVL);
									UserlistFile.save();
									player.setLevel(0);
									player.setExp(0);
									player.sendMessage(prefix + "§7Du hast §b" + currentLVL + " §3" + lvlName + " §7in deinem §3" + storageName + " §7gespeichert");
									player.sendMessage(prefix + "§7In deinem §3" + storageName + " §7sind jetzt §b" + newLVL + " §3" + lvlName);
									return false;
								} else
									UserlistFile.get().set(player.getUniqueId() + ".lvlStorage.savedLevels", 99);
									UserlistFile.save();
									currentLVL = newLVL - 99;
									player.setLevel(currentLVL);
									player.setExp(0);
									player.sendMessage(prefix + "§7Dein §3" + storageName + " §7enthält nun §b99 §3" + lvlName);
									player.sendMessage(prefix + "§7Dein Charakter hat noch §b" + currentLVL + " §3" + lvlName + " §7übrig");
									return false;
							} else
								player.sendMessage(prefix + "§cFehler! §7Du hast keinen §3" + storageName);
						} else
							player.sendMessage(prefix + "§cFehler! §7Du hast keine §3" + lvlName + "§7, die du speichern kannst");
					} else if (args[0].equalsIgnoreCase("load")) {
						if (UserlistFile.get().getInt(player.getUniqueId() + ".lvlStorage.storageAmount") != 0) {
							if (UserlistFile.get().getInt(player.getUniqueId() + ".lvlStorage.savedLevels") != 0) {
								int savedLVL = UserlistFile.get().getInt(player.getUniqueId() + ".lvlStorage.savedLevels");
								int newLVL = player.getLevel() + savedLVL; 
								int amount = UserlistFile.get().getInt(player.getUniqueId() + ".lvlStorage.storageAmount");
								int newAmount = amount - 1;
								UserlistFile.get().set(player.getUniqueId() + ".lvlStorage.savedLevels", 0);
								UserlistFile.get().set(player.getUniqueId() + ".lvlStorage.storageAmount", newAmount);
								UserlistFile.save();
								player.setLevel(newLVL);
								player.setExp(0);
								player.playSound(player.getLocation(), Sound.BLOCK_GLASS_BREAK, 0.7F, 0.5F);
								player.sendMessage(prefix + "§7Dein §3" + lvlName + " §7wurde auf §3" + lvlName + " §b" + newLVL + " §7gesetzt");
								player.sendMessage(prefix + "§7Dein §3" + storageName + " §7wurde zerstört!");
								player.sendMessage(prefix + storageName + " §8x §b" + newAmount);
							} else
								player.sendMessage(prefix + "§cFehler! §7Dein §3" + storageName + " §7ist leer");
						} else
							player.sendMessage(prefix + "§cFehler! §7Du besitzt keinen §3" + storageName);
					} else if (args[0].equalsIgnoreCase("check")) {
						int amount = UserlistFile.get().getInt(player.getUniqueId() + ".lvlStorage.storageAmount");
						int savedLVL = UserlistFile.get().getInt(player.getUniqueId() + ".lvlStorage.savedLevels");
						int shardLVL = UserlistFile.get().getInt(player.getUniqueId() + ".lvlStorage.shardLevels");
						player.sendMessage(prefix + "§3" + storageName + " §8x §b" + amount);
						player.sendMessage(prefix + "§3" + lvlName + " §8x §b" + savedLVL);
						player.sendMessage(prefix + "§3" + shardName + " §3" + lvlName + " §8x §b" + shardLVL);
					} else if (args[0].equalsIgnoreCase("buy")) {
						int amount = UserlistFile.get().getInt(player.getUniqueId() + ".lvlStorage.storageAmount");
						int newAmount = amount + 1;
						UserlistFile.get().set(player.getUniqueId() + ".lvlStorage.storageAmount", newAmount);
						UserlistFile.save();
						player.sendMessage(prefix + "§7Du hast einen §3" + storageName + " §7erzeugt");
						player.sendMessage(prefix + "§3" + storageName + " §8x §b" + newAmount);
					} else if (args[0].equalsIgnoreCase("shard")) {
						player.sendMessage(prefix + msguseshard);
					} else
						player.sendMessage(prefix + msguse);
				} else
					player.sendMessage(prefix + msguse);
				} else
					player.sendMessage(prefix + msgunknown);
			} else if(player.hasPermission("mjs.moduleinfo")) {
				player.sendMessage(prefix + "§7Das §6LevelStorage-Modul §7ist derzeit " + colorDisabled + msgdisabled);
			} else
				player.sendMessage(prefix + msgunknown);
		} else
			sender.sendMessage("[MJ-System]>> Dieser Befehl muss als Spieler ausgeführt werden!");
		return false;
	}

}
