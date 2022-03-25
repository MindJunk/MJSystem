package de.mindjunk.mjsystem.scripts;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import de.mindjunk.mjsystem.files.MessagesFile;

public class UploadFile {
	
	public static void upload(String name, String path, String url) {
		String prefix = MessagesFile.get().getString("Messages.Defaults.prefix");
		FTPClient ftpClient = new FTPClient();
		try {
			System.out.println(prefix + ChatColor.WHITE + "Datei \"" + name + "\" wird hochgeladen...");
			ftpClient.connect("access905170562.webspace-data.io", 22);
			ftpClient.login("u107820993-mjs", "b3$^x7B62^rA");
			ftpClient.enterLocalPassiveMode();
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			File localFile = new File(Bukkit.getServer().getPluginManager().getPlugin("MJSystem").getDataFolder().toString() + "/" + path);
			String remoteFile = url;
			InputStream inputStream = new FileInputStream(localFile);
			boolean done = ftpClient.storeFile(remoteFile, inputStream);
			inputStream.close();
			if (done) {
				System.out.println(prefix + ChatColor.WHITE + "Datei \"" + name + "\" erfolgreich hochgeladen!");
			}
		} catch (IOException ex) {
			System.out.println(prefix + ChatColor.RED + "[ERROR] Fehler beim hochladen der Datei \"" + name);

		} finally {
			try {
				if (ftpClient.isConnected()) {
					ftpClient.logout();
					ftpClient.disconnect();
				}

			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

}
