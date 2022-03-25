package de.mindjunk.mjsystem.files;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import net.md_5.bungee.api.ChatColor;

public class MessagesFile {
	
	private static File file;
	private static FileConfiguration customFile;
	
	public static void setup() {
		file = new File(Bukkit.getServer().getPluginManager().getPlugin("MJSystem").getDataFolder(), "messages.yml");
		if (!file.exists()) {
			  Bukkit.getPluginManager().getPlugin("MJSystem").saveResource("messages.yml", false);
			  System.out.println(ChatColor.WHITE + "[MJSystem]>> Datei \"MESSAGES\" wurde erstellt");
			}
		customFile = YamlConfiguration.loadConfiguration(file);
	}
	
	public static FileConfiguration get() {
		return customFile;
	}
	
	public static void save() {
		try {
			customFile.save(file);
		}catch (IOException e) {
			System.out.println(ChatColor.RED + "[MJSystem]>> Datei \"MESSAGES\" konnte nicht gespeichert werden");
		}
	}
	
	public static void reload() {
		customFile = YamlConfiguration.loadConfiguration(file);
	}
}
