package de.mindjunk.mjsystem.files;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import net.md_5.bungee.api.ChatColor;

public class ConfigFile {
	
	private static File file;
	private static FileConfiguration customFile;
	
	public static void setup() {
		file = new File(Bukkit.getServer().getPluginManager().getPlugin("MJSystem").getDataFolder(), "config.yml");
		if (!file.exists()) {
			  Bukkit.getPluginManager().getPlugin("MJSystem").saveResource("config.yml", false);
			  System.out.println(ChatColor.WHITE + "[MJSystem]>> Datei \"CONFIG\" wurde erstellt");
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
			System.out.println(ChatColor.RED + "[MJSystem]>> Datei \"CONFIG\" konnte nicht gespeichert werden");
		}
	}
	
	public static void reload() {
		customFile = YamlConfiguration.loadConfiguration(file);
	}
}
