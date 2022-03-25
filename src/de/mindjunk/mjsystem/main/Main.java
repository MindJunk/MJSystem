package de.mindjunk.mjsystem.main;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Leaves;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.permissions.PermissionAttachment;

import de.mindjunk.mjsystem.commands.MJSystemCommand;
import de.mindjunk.mjsystem.commands.CommandlistCommand;
import de.mindjunk.mjsystem.commands.toggle.ToggleCommandlistCommand;
import de.mindjunk.mjsystem.commands.AdminCommandlistCommand;
import de.mindjunk.mjsystem.commands.toggle.ToggleAdminCommandlistCommand;
import de.mindjunk.mjsystem.commands.RulesCommand;
import de.mindjunk.mjsystem.commands.toggle.ToggleRulesCommand;
import de.mindjunk.mjsystem.commands.toggle.ToggleSpawnCommand;
import de.mindjunk.mjsystem.commands.toggle.ToggleStartCommandsCommand;
import de.mindjunk.mjsystem.commands.toggle.ToggleTeleportCommand;
import de.mindjunk.mjsystem.commands.DiscordlinkCommand;
import de.mindjunk.mjsystem.commands.toggle.ToggleDiscordlinkCommand;
import de.mindjunk.mjsystem.commands.PricelistCommand;
import de.mindjunk.mjsystem.commands.toggle.TogglePricelistCommand;
import de.mindjunk.mjsystem.commands.toggle.ToggleProtectionCommand;
import de.mindjunk.mjsystem.commands.HomeCommand;
import de.mindjunk.mjsystem.commands.SetHomeCommand;
import de.mindjunk.mjsystem.commands.SetSpawnCommand;
import de.mindjunk.mjsystem.commands.toggle.ToggleHomeCommand;
import de.mindjunk.mjsystem.commands.toggle.ToggleLeafDecayCommand;
import de.mindjunk.mjsystem.commands.TravelCommand;
import de.mindjunk.mjsystem.commands.SettravelCommand;
import de.mindjunk.mjsystem.commands.SpawnCommand;
import de.mindjunk.mjsystem.commands.TeleportCommand;
import de.mindjunk.mjsystem.commands.DeleteTravelCommand;
import de.mindjunk.mjsystem.commands.toggle.ToggleTravelCommand;
import de.mindjunk.mjsystem.commands.LiveCommand;
import de.mindjunk.mjsystem.commands.toggle.ToggleLiveCommand;
import de.mindjunk.mjsystem.commands.toggle.ToggleLoginCommandsCommand;
import de.mindjunk.mjsystem.commands.LevelStorageCommand;
import de.mindjunk.mjsystem.commands.toggle.ToggleLevelStorageCommand;
import de.mindjunk.mjsystem.commands.LoginSoundCommand;

import de.mindjunk.mjsystem.events.BreakEvent;
import de.mindjunk.mjsystem.events.ChatEvent;
import de.mindjunk.mjsystem.events.ConsumeEvent;
import de.mindjunk.mjsystem.events.PlaceEvent;
import de.mindjunk.mjsystem.events.PickupItemEvent;
import de.mindjunk.mjsystem.events.DropItemEvent;
import de.mindjunk.mjsystem.events.InteractEvent;
import de.mindjunk.mjsystem.files.MessagesFile;
import de.mindjunk.mjsystem.files.PermissionsFile;
import de.mindjunk.mjsystem.files.LogFile;
import de.mindjunk.mjsystem.files.TravelpointsFile;
import de.mindjunk.mjsystem.files.ConfigFile;
import de.mindjunk.mjsystem.files.LiveFile;
import de.mindjunk.mjsystem.files.UserlistFile;
import de.mindjunk.mjsystem.scripts.DownloadFile;

public class Main extends JavaPlugin implements Listener {
	
	//Main Vars
	Boolean protectionEnabled;
	Boolean cmdlistEnabled;
	Boolean acmdlistEnabled;
	Boolean rulesEnabled;
	Boolean discordlinkEnabled;
	Boolean pricelistEnabled;
	Boolean teleportEnabled;
	Boolean spawnteleportEnabled;
	Boolean homepointsEnabled;
	Boolean travelpointsEnabled;
	Boolean liveEnabled;
	Boolean lvlEnabled;
	Boolean leafDecayEnabled;
	Boolean permissionsEnabled;
	Boolean startCommandsEnabled;
	Boolean loginCommandsEnabled;
	Boolean updaterEnabled;
	Boolean autoUpdateEnabled;
	int updateAvailable = 0;
	double currentVersion;
	double latestVersion;
	
	private String soundType;
	public List<String> startCommandList;
	public List<String> loginCommandList;
	
	//LeafDecay
	private final Set<String> leafDecayOnlyInWorlds = new HashSet<>();
    private final Set<String> leafDecayExcludeWorlds = new HashSet<>();
    private long leafDecayBreakDelay;
    private long leafDecayDelay;
    private boolean leafDecaySpawnParticles;
    private boolean leafDecayPlaySound;
    private boolean leafDecayOneByOne;
    private final List<Block> leafDecayScheduledBlocks = new ArrayList<>();
    private static final List<BlockFace> leafDecayNEIGHBORS = Arrays
        .asList(BlockFace.UP,
                BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST,
                BlockFace.DOWN);

    
    public static Main plugin;
	public static Main getPlugin() {
		return plugin;
	}

	
	// start & stop
	public void onEnable() {
		plugin = this;
		this.getServer().getPluginManager().registerEvents(this, this);
		getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[MJSystem]>> ###############################");
		getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[MJSystem]>> #\\                           /#");
		getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[MJSystem]>> # |        MJ-System        | #");
		getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[MJSystem]>> #/                           \\#");
		getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[MJSystem]>> ###############################");
		getServer().getConsoleSender().sendMessage(ChatColor.WHITE + "[MJSystem]>> MJ-System wird gestartet...");
		getServer().getConsoleSender().sendMessage(ChatColor.WHITE + "[MJSystem]>> Daten werden geladen...");
		loadConfig();
		loadLog();
		loadMessages();
		loadUserlist();
		loadPermissions();
		loadTravelpoints();
		loadLive();
		loadStartCommands();
		loadLoginCommands();
		loadModules();
		getCommand("mjsystem").setExecutor(new MJSystemCommand());
		getCommand("toggleprotection").setExecutor(new ToggleProtectionCommand());
		getCommand("commandlist").setExecutor(new CommandlistCommand());
		getCommand("togglecommandlist").setExecutor(new ToggleCommandlistCommand());
		getCommand("admincommandlist").setExecutor(new AdminCommandlistCommand());
		getCommand("toggleadmincommandlist").setExecutor(new ToggleAdminCommandlistCommand());
		getCommand("rules").setExecutor(new RulesCommand());
		getCommand("togglerules").setExecutor(new ToggleRulesCommand());
		getCommand("discord").setExecutor(new DiscordlinkCommand());
		getCommand("togglediscord").setExecutor(new ToggleDiscordlinkCommand());
		getCommand("pricelist").setExecutor(new PricelistCommand());
		getCommand("togglepricelist").setExecutor(new TogglePricelistCommand());
		getCommand("teleport").setExecutor(new TeleportCommand());
		getCommand("toggleteleport").setExecutor(new ToggleTeleportCommand());
		getCommand("spawn").setExecutor(new SpawnCommand());
		getCommand("setspawn").setExecutor(new SetSpawnCommand());
		getCommand("togglespawn").setExecutor(new ToggleSpawnCommand());
		getCommand("home").setExecutor(new HomeCommand());
		getCommand("sethome").setExecutor(new SetHomeCommand());
		getCommand("togglehome").setExecutor(new ToggleHomeCommand());
		getCommand("travel").setExecutor(new TravelCommand());
		getCommand("settravel").setExecutor(new SettravelCommand());
		getCommand("deletetravel").setExecutor(new DeleteTravelCommand());
		getCommand("toggletravel").setExecutor(new ToggleTravelCommand());
		getCommand("live").setExecutor(new LiveCommand());
		getCommand("togglelive").setExecutor(new ToggleLiveCommand());
		getCommand("lvlstorage").setExecutor(new LevelStorageCommand());
		getCommand("togglelvlstorage").setExecutor(new ToggleLevelStorageCommand());
		getCommand("togglestartcommands").setExecutor(new ToggleStartCommandsCommand());
		getCommand("togglelogincommands").setExecutor(new ToggleLoginCommandsCommand());
		getCommand("toggleleafdecay").setExecutor(new ToggleLeafDecayCommand());
		getCommand("loginsound").setExecutor(new LoginSoundCommand());
		getServer().getPluginManager().registerEvents(new ChatEvent(), this);
		getServer().getPluginManager().registerEvents(new BreakEvent(), this);
		getServer().getPluginManager().registerEvents(new PlaceEvent(), this);
		getServer().getPluginManager().registerEvents(new ConsumeEvent(), this);
		getServer().getPluginManager().registerEvents(new PickupItemEvent(), this);
		getServer().getPluginManager().registerEvents(new DropItemEvent(), this);
		getServer().getPluginManager().registerEvents(new InteractEvent(), this);
		getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[MJSystem]>> Daten erfolgreich geladen!");
		getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[MJSystem]>> MJ-System gestartet!");
		if (startCommandsEnabled == true) {
			startCommands();
		}
		if (updaterEnabled == true) {
			updateCheck();
		}
	}

	public void onDisable() {
		getServer().getConsoleSender().sendMessage(ChatColor.RED + "[MJSystem]>> MJ-System wird beendet...");
		getServer().getConsoleSender().sendMessage(ChatColor.WHITE + "[MJSystem]>> Daten werden gespeichert...");
		leafDecayScheduledBlocks.clear();
		ConfigFile.save();
		PermissionsFile.save();
		TravelpointsFile.save();
		LiveFile.save();
		LogFile.save();
		UserlistFile.save();
		getServer().getConsoleSender().sendMessage(ChatColor.WHITE + "[MJSystem]>> Daten erfolgreich gespeichert!");
		getServer().getConsoleSender().sendMessage(ChatColor.RED + "[MJSystem]>> MJ-System beendet!");
	}

	
	// join & quit
	@EventHandler
	public void join(PlayerJoinEvent e) {
		Player player = e.getPlayer();
		String prefix = MessagesFile.get().getString("Messages.Defaults.prefix");
		String msgjoin = MessagesFile.get().getString("Messages.WelcomeMessages.join");
		String msglogin = MessagesFile.get().getString("Messages.WelcomeMessages.login");
		Boolean wlcmEnabled = ConfigFile.get().getBoolean("WelcomeMessages.enabled");
		Boolean soundEnabled = UserlistFile.get().getBoolean(player.getUniqueId() + ".welcomeSound");
		Boolean permissionsEnabled = ConfigFile.get().getBoolean("Modules.Permissions.enabled");
		String knownUser = UserlistFile.get().getString(player.getUniqueId() + ".name");
		String world = player.getWorld().getName();
		double locX = player.getLocation().getX();
		double locY = player.getLocation().getY();
		double locZ = player.getLocation().getZ();
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		if (permissionsEnabled == true) {
			setupPermissions(player);
		}
		Boolean loginCommandsEnabled = ConfigFile.get().getBoolean("Modules.LoginCommands.enabled");
		if (loginCommandsEnabled == true) {
			loginCommands(e);
		}
		if (knownUser != null) {
			UserlistFile.get().set(player.getUniqueId() + ".name", player.getName());
			String group = PermissionsFile.get().getString("Users." + player.getUniqueId() + ".group");
			UserlistFile.get().set(player.getUniqueId() + ".login.world", world);
			UserlistFile.get().set(player.getUniqueId() + ".login.x", locX);
			UserlistFile.get().set(player.getUniqueId() + ".login.y", locY);
			UserlistFile.get().set(player.getUniqueId() + ".login.z", locZ);
			UserlistFile.get().set(player.getUniqueId() + ".login.time", format.format(date));
			UserlistFile.get().set(player.getUniqueId() + ".group", group);
			UserlistFile.save();
			PermissionsFile.get().set("Users." + player.getUniqueId() + ".name", player.getName());
			PermissionsFile.get().set("Users." + player.getUniqueId() + ".group", group);
			PermissionsFile.save();
			if (wlcmEnabled == true) {
				player.sendMessage(prefix + msglogin + " " + player.getName());
				if (soundEnabled == true) {
					soundType = ConfigFile.get().getString("WelcomeMessages.sound.type");
					player.playSound(player.getLocation(), Sound.valueOf(soundType), 0.2F, 1.0F);
				}
			}
		} else {
			UserlistFile.get().set(player.getUniqueId() + ".name", player.getName());
			String defaultGroup = ConfigFile.get().getString("Modules.Permissions.DefaultGroup");
			UserlistFile.get().set(player.getUniqueId() + ".group", defaultGroup);
			UserlistFile.get().set(player.getUniqueId() + ".lvlStorage.storageAmount", 0);
			UserlistFile.get().set(player.getUniqueId() + ".lvlStorage.savedLevels", 0);
			UserlistFile.get().set(player.getUniqueId() + ".lvlStorage.shardLevels", 0);
			UserlistFile.get().set(player.getUniqueId() + ".login.world", world);
			UserlistFile.get().set(player.getUniqueId() + ".login.x", locX);
			UserlistFile.get().set(player.getUniqueId() + ".login.y", locY);
			UserlistFile.get().set(player.getUniqueId() + ".login.z", locZ);
			UserlistFile.get().set(player.getUniqueId() + ".login.time", format.format(date));
			UserlistFile.get().set(player.getUniqueId() + ".welcomeSound", true);
			UserlistFile.get().set(player.getUniqueId() + ".firstLogin.name", player.getName());
			UserlistFile.get().set(player.getUniqueId() + ".firstLogin.time", format.format(date));
			UserlistFile.save();
			PermissionsFile.get().set("Users." + player.getUniqueId() + ".name", player.getName());
			PermissionsFile.get().set("Users." + player.getUniqueId() + ".group", defaultGroup);
			PermissionsFile.get().set("Users." + player.getUniqueId() + ".permissionsTrue", "none");
			PermissionsFile.get().set("Users." + player.getUniqueId() + ".permissionsFalse", "none");
			PermissionsFile.save();
			String user = player.getName();
			List<String> newUser = LogFile.get().getStringList("Events.newUser");
			newUser.add(user + " " + format.format(date));
			LogFile.get().set("Events.newUser", newUser);
			LogFile.save();
			if (wlcmEnabled == true) {
				player.sendMessage(prefix + msgjoin + " " + player.getName());
				if (soundEnabled == true) {
					soundType = ConfigFile.get().getString("WelcomeMessages.sound.type");
					player.playSound(player.getLocation(), Sound.valueOf(soundType), 0.2F, 1.0F);
				}
			}
		}
		if (player.hasPermission("mjs.updater")) {
			if (updateAvailable == 1) {
				player.sendMessage(prefix + "§6Es ist eine neue Version verfügbar!\n"
								 + prefix + "§6Neueste Version: §7" + latestVersion + "\n"
								 + prefix + "§6Installierte Version: §7" + currentVersion + "\n"
								 + "§7>> Download: https://mindjunk.de/mjsystem/MJSystem.jar");
			} else if (updateAvailable == 2) {
				player.sendMessage(prefix + "§6Es ist eine neue Version verfügbar!\n"
						 + prefix + "§6Neueste Version: §7" + latestVersion + "\n"
						 + prefix + "§6Installierte Version: §7" + currentVersion + "\n"
						 + prefix + "§7Die neue Version wird beim nächsten Neustart des Servers installiert.");
			}
		}
	}

	@EventHandler
	public void quit(PlayerQuitEvent quitevent) {
		Player player = quitevent.getPlayer();
		String world = player.getWorld().getName();
		double locX = player.getLocation().getX();
		double locY = player.getLocation().getY();
		double locZ = player.getLocation().getZ();
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		UserlistFile.get().set(player.getUniqueId() + ".logout.world", world);
		UserlistFile.get().set(player.getUniqueId() + ".logout.x", locX);
		UserlistFile.get().set(player.getUniqueId() + ".logout.y", locY);
		UserlistFile.get().set(player.getUniqueId() + ".logout.z", locZ);
		UserlistFile.get().set(player.getUniqueId() + ".logout.time", format.format(date));
		UserlistFile.save();
		playerPermissions.remove(player.getUniqueId());
	}
	
	
	// Start/Login Commands
	public void startCommands() {
		getServer().getConsoleSender().sendMessage(ChatColor.WHITE + "[MJSystem]>> StartCommands werden ausgeführt...");
		for (String c : startCommandList)
	    {
	      int delay = 0;
	      if (c.startsWith("d="))
	      {
	        String rawdelay = c.split("d=")[1].split(";")[0];
	        delay = Integer.parseInt(rawdelay);
	        c = c.substring(3 + rawdelay.length());
	      }
	      final String cmd = c;
	      Bukkit.getScheduler().runTaskLater(this, new Runnable()
	      {
	        public void run()
	        {
	          Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd);
	        }
	      }, delay * 20);
	    }
	}
	
	public void loginCommands(PlayerJoinEvent e) {

		final Player p = e.getPlayer();

		for (String c : loginCommandList) {
			int delay = 0;
			if (c.startsWith("d=")) {
				String rawdelay = c.split("d=")[1].split(";")[0];
				delay = Integer.parseInt(rawdelay);
				c = c.substring(3 + rawdelay.length());
			}
			if (c.startsWith("perm=")) {
				String perm = c.split("perm=")[1].split(";")[0];
				if (p.hasPermission(perm)) {
					c = c.substring(6 + perm.length());
				}
			} else if (c.startsWith("[fj]")) {
				c = c.substring(4);
				if (p.hasPlayedBefore()) {
				}
			} else {
				final String cmd = c.replaceAll("\\{PLAYER\\}", e.getPlayer().getName());
				Bukkit.getScheduler().runTaskLater(this, new Runnable() {
					public void run() {
						CommandSender sender = Bukkit.getConsoleSender();
						String command = cmd;
						if (cmd.startsWith("[p]")) {
							sender = p;
							command = command.substring(3);
						}
						Bukkit.dispatchCommand(sender, command);
					}
				}, delay * 20);
			}
		}
	}
	
	
	//Updater
	public void updateCheck() {
		try {
			File latestVersionFile = new File(Bukkit.getServer().getPluginManager().getPlugin("MJSystem").getDataFolder().toString() + "/latestVersion.yml");
			DownloadFile.download("LatestVersion","http://mindjunk.de/mjsystem/latestVersion.yml", "latestVersion.yml");
			currentVersion = Double.parseDouble(getDescription().getVersion());
			latestVersion = Double.parseDouble(Files.readAllLines(Paths.get(Bukkit.getServer().getPluginManager().getPlugin("MJSystem").getDataFolder().toString() + "/latestVersion.yml")).get(0));
			latestVersionFile.delete();
			if (latestVersion > currentVersion) {
				if (autoUpdateEnabled == true) {
					updateAvailable = 2;
					Files.copy(new URL("http://mindjunk.de/mjsystem/MJSystem.jar").openStream(),Paths.get("plugins/update/MJSystem.jar"));
				} else {
					updateAvailable = 1;
				}
				System.out.println(ChatColor.GREEN + "[MJSystem]>> Es ist eine neue Version verfügbar!");
			} else
				System.out.println(ChatColor.GREEN + "[MJSystem]>> MJ-System ist auf dem neusten Stand!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	// load Data
	public void loadConfig() {
		ConfigFile.setup();
		ConfigFile.save();
	}

	public void loadLog() {
		LogFile.setup();
		LogFile.save();
	}

	public void loadTravelpoints() {
		TravelpointsFile.setup();
		TravelpointsFile.save();
	}

	public void loadLive() {
		LiveFile.setup();
		LiveFile.save();
	}

	public void loadMessages() {
		MessagesFile.setup();
		MessagesFile.save();
	}

	public void loadPermissions() {
		PermissionsFile.setup();
		PermissionsFile.save();
	}

	public void loadUserlist() {
		UserlistFile.setup();
		UserlistFile.save();
	}
	
	public void loadStartCommands() {
		startCommandList  = ConfigFile.get().getStringList("Modules.StartCommands.Commands");
	}
	
	public void loadLoginCommands() {
		loginCommandList  = ConfigFile.get().getStringList("Modules.LoginCommands.Commands");
	}
	
	public void loadLeafDecay() {
		leafDecayOnlyInWorlds.addAll(getConfig().getStringList("Modules.LeafDecay.OnlyInWorlds"));
		leafDecayExcludeWorlds.addAll(getConfig().getStringList("Modules.LeafDecay.ExcludeWorlds"));
		leafDecayBreakDelay = getConfig().getLong("Modules.LeafDecay.BreakDelay");
		leafDecayDelay = getConfig().getLong("Modules.LeafDecay.DecayDelay");
		leafDecayOneByOne = getConfig().getBoolean("Modules.LeafDecay.OneByOne");
		leafDecaySpawnParticles = getConfig().getBoolean("Modules.LeafDecay.SpawnParticles");
		leafDecayPlaySound = getConfig().getBoolean("Modules.LeafDecay.PlaySound");
	}
	
	public void loadModules() {
		String msgenabled = MessagesFile.get().getString("Messages.Defaults.enabled.text");
		String msgdisabled = MessagesFile.get().getString("Messages.Defaults.disabled.text");
		protectionEnabled = ConfigFile.get().getBoolean("Modules.Protection.enabled");
		cmdlistEnabled = ConfigFile.get().getBoolean("Modules.Commandlist.enabled");
		acmdlistEnabled = ConfigFile.get().getBoolean("Modules.AdminCommandlist.enabled");
		rulesEnabled = ConfigFile.get().getBoolean("Modules.Rules.enabled");
		discordlinkEnabled = ConfigFile.get().getBoolean("Modules.Discordlink.enabled");
		pricelistEnabled = ConfigFile.get().getBoolean("Modules.Pricelist.enabled");
		teleportEnabled = ConfigFile.get().getBoolean("Modules.Teleport.enabled");
		spawnteleportEnabled = ConfigFile.get().getBoolean("Modules.SpawnTeleport.enabled");
		homepointsEnabled = ConfigFile.get().getBoolean("Modules.Homepoints.enabled");
		travelpointsEnabled = ConfigFile.get().getBoolean("Modules.Travelpoints.enabled");
		liveEnabled = ConfigFile.get().getBoolean("Modules.Live.enabled");
		lvlEnabled = ConfigFile.get().getBoolean("Modules.LevelStorage.enabled");
		leafDecayEnabled = ConfigFile.get().getBoolean("Modules.LeafDecay.enabled");
		permissionsEnabled = ConfigFile.get().getBoolean("Modules.Permissions.enabled");
		startCommandsEnabled = ConfigFile.get().getBoolean("Modules.StartCommands.enabled");
		loginCommandsEnabled = ConfigFile.get().getBoolean("Modules.LoginCommands.enabled");
		updaterEnabled = ConfigFile.get().getBoolean("Updater.enabled");
		autoUpdateEnabled = ConfigFile.get().getBoolean("Updater.autoUpdate");
		if (protectionEnabled == true) {
			getServer().getConsoleSender().sendMessage(
					ChatColor.WHITE + "[MJSystem]>> Protection-Modul: " + ChatColor.GREEN + msgenabled + "!");
		} else
			getServer().getConsoleSender().sendMessage(
					ChatColor.WHITE + "[MJSystem]>> Protection-Modul: " + ChatColor.RED + msgdisabled + "!");
		if (cmdlistEnabled == true) {
			getServer().getConsoleSender().sendMessage(
					ChatColor.WHITE + "[MJSystem]>> Commandlist-Modul: " + ChatColor.GREEN + msgenabled + "!");
		} else
			getServer().getConsoleSender().sendMessage(
					ChatColor.WHITE + "[MJSystem]>> Commandlist-Modul: " + ChatColor.RED + msgdisabled + "!");
		if (acmdlistEnabled == true) {
			getServer().getConsoleSender().sendMessage(
					ChatColor.WHITE + "[MJSystem]>> AdminCommandlist-Modul: " + ChatColor.GREEN + msgenabled + "!");
		} else
			getServer().getConsoleSender().sendMessage(
					ChatColor.WHITE + "[MJSystem]>> AdminCommandlist-Modul: " + ChatColor.RED + msgdisabled + "!");
		if (rulesEnabled == true) {
			getServer().getConsoleSender()
					.sendMessage(ChatColor.WHITE + "[MJSystem]>> Rules-Modul: " + ChatColor.GREEN + msgenabled + "!");
		} else
			getServer().getConsoleSender()
					.sendMessage(ChatColor.WHITE + "[MJSystem]>> Rules-Modul: " + ChatColor.RED + msgdisabled + "!");
		if (discordlinkEnabled == true) {
			getServer().getConsoleSender()
					.sendMessage(ChatColor.WHITE + "[MJSystem]>> Discordlink-Modul: " + ChatColor.GREEN + msgenabled + "!");
		} else
			getServer().getConsoleSender()
					.sendMessage(ChatColor.WHITE + "[MJSystem]>> Discordlink-Modul: " + ChatColor.RED + msgdisabled + "!");
		if (pricelistEnabled == true) {
			getServer().getConsoleSender().sendMessage(
					ChatColor.WHITE + "[MJSystem]>> Pricelist-Modul: " + ChatColor.GREEN + msgenabled + "!");
		} else
			getServer().getConsoleSender().sendMessage(
					ChatColor.WHITE + "[MJSystem]>> Pricelist-Modul: " + ChatColor.RED + msgdisabled + "!");
		if (teleportEnabled == true) {
			getServer().getConsoleSender().sendMessage(
					ChatColor.WHITE + "[MJSystem]>> Teleport-Modul: " + ChatColor.GREEN + msgenabled + "!");
		} else
			getServer().getConsoleSender().sendMessage(
					ChatColor.WHITE + "[MJSystem]>> Teleport-Modul: " + ChatColor.RED + msgdisabled + "!");
		if (spawnteleportEnabled == true) {
			getServer().getConsoleSender().sendMessage(
					ChatColor.WHITE + "[MJSystem]>> SpawnTeleport-Modul: " + ChatColor.GREEN + msgenabled + "!");
		} else
			getServer().getConsoleSender().sendMessage(
					ChatColor.WHITE + "[MJSystem]>> SpawnTeleport-Modul: " + ChatColor.RED + msgdisabled + "!");
		if (homepointsEnabled == true) {
			getServer().getConsoleSender().sendMessage(
					ChatColor.WHITE + "[MJSystem]>> Homepoints-Modul: " + ChatColor.GREEN + msgenabled + "!");
		} else
			getServer().getConsoleSender().sendMessage(
					ChatColor.WHITE + "[MJSystem]>> Homepoints-Modul: " + ChatColor.RED + msgdisabled + "!");
		if (travelpointsEnabled == true) {
			getServer().getConsoleSender().sendMessage(
					ChatColor.WHITE + "[MJSystem]>> Travelpoints-Modul: " + ChatColor.GREEN + msgenabled + "!");
		} else
			getServer().getConsoleSender().sendMessage(
					ChatColor.WHITE + "[MJSystem]>> Travelpoints-Modul: " + ChatColor.RED + msgdisabled + "!");
		if (liveEnabled == true) {
			getServer().getConsoleSender()
					.sendMessage(ChatColor.WHITE + "[MJSystem]>> Live-Modul: " + ChatColor.GREEN + msgenabled + "!");
		} else
			getServer().getConsoleSender()
					.sendMessage(ChatColor.WHITE + "[MJSystem]>> Live-Modul: " + ChatColor.RED + msgdisabled + "!");
		if (lvlEnabled == true) {
			getServer().getConsoleSender().sendMessage(
					ChatColor.WHITE + "[MJSystem]>> LevelStorage-Modul: " + ChatColor.GREEN + msgenabled + "!");
		} else
			getServer().getConsoleSender().sendMessage(
					ChatColor.WHITE + "[MJSystem]>> LevelStorage-Modul: " + ChatColor.RED + msgdisabled + "!");
		if (leafDecayEnabled == true) {
			getServer().getConsoleSender().sendMessage(
					ChatColor.WHITE + "[MJSystem]>> LeafDecay-Modul: " + ChatColor.GREEN + msgenabled + "!");
			loadLeafDecay();
		} else
			getServer().getConsoleSender().sendMessage(
					ChatColor.WHITE + "[MJSystem]>> LeafDecay-Modul: " + ChatColor.RED + msgdisabled + "!");
		if (permissionsEnabled == true) {
			getServer().getConsoleSender().sendMessage(
					ChatColor.WHITE + "[MJSystem]>> Permissions-Modul: " + ChatColor.GREEN + msgenabled + "!");
		} else
			getServer().getConsoleSender().sendMessage(
					ChatColor.WHITE + "[MJSystem]>> Permissions-Modul: " + ChatColor.RED + msgdisabled + "!");
		if (loginCommandsEnabled == true) {
			getServer().getConsoleSender().sendMessage(
					ChatColor.WHITE + "[MJSystem]>> LoginCommands-Modul: " + ChatColor.GREEN + msgenabled + "!");
		} else
			getServer().getConsoleSender().sendMessage(
					ChatColor.WHITE + "[MJSystem]>> LoginCommands-Modul: " + ChatColor.RED + msgdisabled + "!");
		if (startCommandsEnabled == true) {
			getServer().getConsoleSender().sendMessage(
					ChatColor.WHITE + "[MJSystem]>> StartCommands-Modul: " + ChatColor.GREEN + msgenabled + "!");
		} else
			getServer().getConsoleSender().sendMessage(
					ChatColor.WHITE + "[MJSystem]>> StartCommands-Modul: " + ChatColor.RED + msgdisabled + "!");
		if (updaterEnabled == true) {
			getServer().getConsoleSender().sendMessage(
					ChatColor.WHITE + "[MJSystem]>> Updater: " + ChatColor.GREEN + msgenabled + "!");
			if (autoUpdateEnabled == true) {
				getServer().getConsoleSender().sendMessage(
						ChatColor.WHITE + "[MJSystem]>> Auto-Update: " + ChatColor.GREEN + msgenabled + "!");
			} else
				getServer().getConsoleSender().sendMessage(
						ChatColor.WHITE + "[MJSystem]>> Auto-Update: " + ChatColor.RED + msgdisabled + "!");
		} else
			getServer().getConsoleSender().sendMessage(
					ChatColor.WHITE + "[MJSystem]>> Updater: " + ChatColor.RED + msgdisabled + "!");
	}
	
	
	//LeafDecay
	@EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onBlockBreak(BlockBreakEvent event) {
		if (leafDecayEnabled == true) {
	        onBlockRemove(event.getBlock(), leafDecayBreakDelay);
		}
    }
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onLeavesDecay(LeavesDecayEvent event) {
		if (leafDecayEnabled == true) {
	        onBlockRemove(event.getBlock(), leafDecayDelay);
		}
    }
	
	private void onBlockRemove(final Block oldBlock, long delay) {
        if (!Tag.LOGS.isTagged(oldBlock.getType())
            && !Tag.LEAVES.isTagged(oldBlock.getType())) {
            return;
        }
        final String worldName = oldBlock.getWorld().getName();
        if (!leafDecayOnlyInWorlds.isEmpty() && !leafDecayOnlyInWorlds.contains(worldName)) return;
        if (leafDecayExcludeWorlds.contains(worldName)) return;
        // No return
        Collections.shuffle(leafDecayNEIGHBORS);
        for (BlockFace neighborFace: leafDecayNEIGHBORS) {
            final Block block = oldBlock.getRelative(neighborFace);
            if (!Tag.LEAVES.isTagged(block.getType())) continue;
            Leaves leaves = (Leaves) block.getBlockData();
            if (leaves.isPersistent()) continue;
            if (leafDecayScheduledBlocks.contains(block)) continue;
            if (leafDecayOneByOne) {
                if (leafDecayScheduledBlocks.isEmpty()) {
                    getServer().getScheduler().runTaskLater(this, this::decayOne, delay);
                }
                leafDecayScheduledBlocks.add(block);
            } else {
                getServer().getScheduler().runTaskLater(this, () -> decay(block), delay);
            }
            leafDecayScheduledBlocks.add(block);
        }
    }
	
	private boolean decay(Block block) {
        if (!leafDecayScheduledBlocks.remove(block)) return false;
        if (!block.getWorld().isChunkLoaded(block.getX() >> 4, block.getZ() >> 4)) return false;
        if (!Tag.LEAVES.isTagged(block.getType())) return false;
        Leaves leaves = (Leaves) block.getBlockData();
        if (leaves.isPersistent()) return false;
        if (leaves.getDistance() < 7) return false;
        LeavesDecayEvent event = new LeavesDecayEvent(block);
        getServer().getPluginManager().callEvent(event);
        if (event.isCancelled()) return false;
        if (leafDecaySpawnParticles) {
            block.getWorld()
                .spawnParticle(Particle.BLOCK_DUST,
                               block.getLocation().add(0.5, 0.5, 0.5),
                               8, 0.2, 0.2, 0.2, 0,
                               block.getType().createBlockData());
        }
        if (leafDecayPlaySound) {
            block.getWorld().playSound(block.getLocation(),
                                       Sound.BLOCK_GRASS_BREAK,
                                       SoundCategory.BLOCKS, 0.05f, 1.2f);
        }
        block.breakNaturally();
        return true;
    }
	
	private void decayOne() {
        boolean decayed = false;
        do {
            if (leafDecayScheduledBlocks.isEmpty()) return;
            Block block = leafDecayScheduledBlocks.get(0);
            decayed = decay(block); // Will remove block from list.
        } while (!decayed);
        if (!leafDecayScheduledBlocks.isEmpty()) {
            long delay = leafDecayDelay;
            if (delay <= 0) delay = 1L;
            getServer().getScheduler().runTaskLater(this, this::decayOne, delay);
        }
    }
	
	
	// permissions
	public HashMap<UUID, PermissionAttachment> playerPermissions = new HashMap<>();

	public void setupPermissions(Player player) {
		PermissionAttachment attachment = player.addAttachment(this);
		this.playerPermissions.put(player.getUniqueId(), attachment);
		String group = UserlistFile.get().getString(player.getUniqueId() + ".group");
		for (String permissionsTrue : PermissionsFile.get()
				.getStringList("Permissions.Groups." + group + ".permissionsTrue")) {
			attachment.setPermission(permissionsTrue, true);
		}
		for (String permissionsFalse : PermissionsFile.get()
				.getStringList("Permissions.Groups." + group + ".permissionsFalse")) {
			attachment.setPermission(permissionsFalse, false);
		}

		for (String personalTrue : PermissionsFile.get().getStringList(player.getUniqueId() + ".permissionsTrue")) {
			attachment.setPermission(personalTrue, true);
		}
		for (String personalFalse : PermissionsFile.get().getStringList(player.getUniqueId() + ".permissionsFalse")) {
			attachment.setPermission(personalFalse, true);
		}
	}

}
