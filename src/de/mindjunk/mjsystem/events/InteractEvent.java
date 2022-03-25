package de.mindjunk.mjsystem.events;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import de.mindjunk.mjsystem.files.MessagesFile;
import de.mindjunk.mjsystem.main.Main;

public class InteractEvent implements Listener {
	
	@EventHandler
	public void playerInteract(PlayerInteractEvent e) {
		FileConfiguration config = Main.getPlugin().getConfig();
		String prefix = MessagesFile.get().getString("Messages.Defaults.prefix");
		String msgnoperm = MessagesFile.get().getString("Messages.Errors.noPermission");
		Boolean protectionEnabled = config.getBoolean("Modules.Protection.enabled");
		Player player = e.getPlayer();
		if(protectionEnabled == true) {
			if(!player.hasPermission("mjs.interact")) {
				e.setCancelled(true);
				player.sendMessage(prefix + msgnoperm + "mjs.interact");
			}
		}
	}
}
