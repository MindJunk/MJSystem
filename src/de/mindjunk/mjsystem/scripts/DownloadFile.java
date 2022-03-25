package de.mindjunk.mjsystem.scripts;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import de.mindjunk.mjsystem.files.MessagesFile;

public class DownloadFile {
	
	public static void download(String name, String url, String path) {
		String prefix = MessagesFile.get().getString("Messages.Defaults.prefix");
		try {
			Files.copy(
				    new URL(url).openStream(),
				    Paths.get(Bukkit.getServer().getPluginManager().getPlugin("MJSystem").getDataFolder().toString() + "/" + path));
		} catch (Exception e) {
			System.out.println(prefix + ChatColor.RED + "[ERROR] Fehler beim herunterladen der Datei \"" + name + "\" von \"" + url + "\"");
		}
	}

}
