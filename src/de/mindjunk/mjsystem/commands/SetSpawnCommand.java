package de.mindjunk.mjsystem.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.mindjunk.mjsystem.files.ConfigFile;
import de.mindjunk.mjsystem.files.MessagesFile;

public class SetSpawnCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Boolean moduleEnabled = ConfigFile.get().getBoolean("Modules.SpawnTeleport.enabled");
		String prefix = MessagesFile.get().getString("Messages.Defaults.prefix");
		String msgdisabled = MessagesFile.get().getString("Messages.Defaults.disabled.text");
		String colorDisabled = MessagesFile.get().getString("Messages.Defaults.disabled.color");
		String msgunknown = MessagesFile.get().getString("Messages.Errors.unknownCommand");
		String msguse = MessagesFile.get().getString("Messages.Modules.SpawnTeleport.set");
		String msguseconsole = MessagesFile.get().getString("Messages.Modules.SpawnTeleport.setconsole");
		String msgnoperm = MessagesFile.get().getString("Messages.Errors.noPermission");
		String world;
		double locX;
		double locY;
		double locZ;
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(moduleEnabled == true) {
				if(player.hasPermission("mjs.spawn.set")) {
					if(args.length == 0) {
						world = player.getWorld().getName();
						locX = player.getLocation().getX();
						locY = player.getLocation().getY();
						locZ = player.getLocation().getZ();
						player.getWorld().setSpawnLocation(player.getLocation());
						player.sendMessage(prefix + "§7Der Spawn von Welt §6" + world +  " §7wurde bei §aX§8=§9" + locX + " §aY§8=§9" + locY + " §aZ§8=§9" + locZ + " §7gesetzt!");
					} else if(args.length == 3) {
						world = player.getWorld().getName();
						locX = Double.parseDouble(args[0]);
						locY = Double.parseDouble(args[1]);
						locZ = Double.parseDouble(args[2]);
						Location spawn = new Location(player.getWorld(), locX, locY, locZ);
						player.getWorld().setSpawnLocation(spawn);
						player.sendMessage(prefix + "§7Der Spawn von Welt §6" + world +  " §7wurde bei §aX§8=§9" + locX + " §aY§8=§9" + locY + " §aZ§8=§9" + locZ + " §7gesetzt!");
					} else if(args.length == 4) {
						world = args[0];
						locX = Double.parseDouble(args[1]);
						locY = Double.parseDouble(args[2]);
						locZ = Double.parseDouble(args[3]);
						Location spawn = new Location(Bukkit.getWorld(world), locX, locY, locZ);
						Bukkit.getWorld(world).setSpawnLocation(spawn);
						player.sendMessage(prefix + "§7Der Spawn von Welt §6" + world +  " §7wurde bei §aX§8=§9" + locX + " §aY§8=§9" + locY + " §aZ§8=§9" + locZ + " §7gesetzt!");
					} else
						player.sendMessage(prefix + msguse);
				} else
					player.sendMessage(prefix + msgnoperm + "mjs.spawn.set");
			} else if(player.hasPermission("mjs.moduleinfo")) {
				player.sendMessage(prefix + " §7Das §6SpawnTeleport-Modul §7ist derzeit " + colorDisabled + msgdisabled);
			} else
				player.sendMessage(prefix + msgunknown);
		} else {
			if(moduleEnabled == true) {
				if(args.length == 4) {
					world = args[0];
					locX = Double.parseDouble(args[1]);
					locY = Double.parseDouble(args[2]);
					locZ = Double.parseDouble(args[3]);
					Location spawn = new Location(Bukkit.getWorld(world), locX, locY, locZ);
					Bukkit.getWorld(world).setSpawnLocation(spawn);
					sender.sendMessage(prefix + "§7Der Spawn von Welt §6" + world +  " §7wurde bei §aX§8=§9" + locX + " §aY§8=§9" + locY + " §aZ§8=§9" + locZ + " §7gesetzt!");
				} else
					sender.sendMessage(prefix + msguseconsole);
			} else
				sender.sendMessage(prefix + " §7Das §6SpawnTeleport-Modul §7ist derzeit " + colorDisabled + msgdisabled);
		}
		return false;
	}

}
