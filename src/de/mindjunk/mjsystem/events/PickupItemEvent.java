package de.mindjunk.mjsystem.events;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;

import de.mindjunk.mjsystem.main.Main;

@SuppressWarnings("deprecation")
public class PickupItemEvent implements Listener {
	
	@EventHandler
	public void playerPickupItem(PlayerPickupItemEvent e) {
		FileConfiguration config = Main.getPlugin().getConfig();
		Boolean protectionEnabled = config.getBoolean("Modules.Protection.enabled");
		Player player = e.getPlayer();
		if(protectionEnabled == true) {
			if(!player.hasPermission("mjs.pickup")) {
				e.setCancelled(true);
			}
		}
	}
}
