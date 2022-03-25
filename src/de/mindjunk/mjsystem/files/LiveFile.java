package de.mindjunk.mjsystem.files;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import net.md_5.bungee.api.ChatColor;

public class LiveFile {
	
	private static File file;
	private static FileConfiguration customFile;
	
	public static void setup() {
		file = new File(Bukkit.getServer().getPluginManager().getPlugin("MJSystem").getDataFolder(), "data/live.yml");
		if (!file.exists()) {
			  Bukkit.getPluginManager().getPlugin("MJSystem").saveResource("data/live.yml", false);
			  System.out.println(ChatColor.WHITE + "[MJSystem]>> Datei \"LIVE\" wurde erstellt");
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
			System.out.println(ChatColor.RED + "[MJSystem]>> Datei \"LIVE\" konnte nicht gespeichert werden");
		}
	}
	
	public static void reload() {
		customFile = YamlConfiguration.loadConfiguration(file);
	}
}
