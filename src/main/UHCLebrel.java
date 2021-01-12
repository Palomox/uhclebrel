package main;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;

import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.RenderType;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;
import org.bukkit.scoreboard.Team.Option;
import org.bukkit.scoreboard.Team.OptionStatus;

import chat.IChannel;
import chat.NChannel;
import commands.ChannelCmd;
import commands.ChatCmd;
import commands.UhcCMD;
import events.DeathEventListener;
import events.EachSecondListener;
import events.GameChangePartListener;
import events.GameStateChangeListener;
import events.MessageSentListener;
import events.ObfuscateAdvancementName;
import events.PlayerDisconnectListener;
import events.PlayerJoinListener;
import events.SpectatorAttackListener;
import events.SpectatorInteractionListener;
import fr.minuskube.inv.InventoryManager;
import skinsrestorer.bukkit.SkinsRestorer;
import skinsrestorer.bukkit.SkinsRestorerBukkitAPI;
import uhc.EachSecondEvent;
import uhc.GameManager;
import uhc.UHCTeam;
import uhc.UhcPlaceholders;
import util.Messages;
import util.OpLogger;
import util.UHCPlayer;

public class UHCLebrel extends JavaPlugin {
	private final int statsId = 9205;
	public String rutaConfig;
	PluginDescriptionFile plugindesc = getDescription();
	public String version = plugindesc.getVersion();
	public String nombre = plugindesc.getName();
	public InventoryManager invm = new InventoryManager(this);
	private ArrayList<UHCPlayer> players = new ArrayList<UHCPlayer>();
	private OpLogger alogger;
	private ArrayList<Player> admins = new ArrayList<Player>();
	private ArrayList<IChannel> canales = new ArrayList<IChannel>();
	public Team todos;
	public static UHCLebrel instance;
	public GameManager juego;
	private SkinsRestorer skrest;
	public SkinsRestorerBukkitAPI sapi;
	public Timer matar = new Timer();
	public Scoreboard all;
	public Messages messages;
	public HashMap<Integer, String> scoreboard = new HashMap<Integer, String>();
	private FileConfiguration messagesCfg;
	private File messagesCfgFile;


	public void onEnable() {
		instance = this;
		registerPapiExpansions();
		startSeconding();
		registerConfig();
		registerEvents();
		registrarComandos();
		alogger = new OpLogger(this);
		juego = new GameManager();
		invm.init();
		for (String nombre : getConfig().getConfigurationSection("chat.channels").getKeys(false)) {
			String channelName = getConfig().getString("chat.channels." + nombre + ".name");
			String tmp = getConfig().getString("chat.channels." + nombre + ".fastprefix");
			char pre = tmp.charAt(0);
			registerChannels(channelName, pre);
		}
		skrest = JavaPlugin.getPlugin(SkinsRestorer.class);
		sapi = skrest.getSkinsRestorerBukkitAPI();
		startScoreboardTeams();
		Metrics metrics = new Metrics(this, statsId);
		messages = new Messages(messagesCfg);
		Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_GREEN + "[UHC] El Plugin ha sido Activado Correctamente");
	}



	public void startSeconding() {
		this.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {

			@Override
			public void run() {
				Bukkit.getPluginManager().callEvent(new EachSecondEvent());
			}
		}, 0, 20);
	}

	public ArrayList<IChannel> getCanales() {
		return this.canales;
	}
	public void startScoreboardTeams() {
		ScoreboardManager manager = Bukkit.getScoreboardManager();
		org.bukkit.scoreboard.Scoreboard all = manager.getNewScoreboard();
		this.all = all;
		Objective cnom = all.registerNewObjective("corazonesbajo", "health", ChatColor.translateAlternateColorCodes('&', "❤"), RenderType.INTEGER);

		Objective ctab = all.registerNewObjective("corazonestab",
				"health",
				" ",
				RenderType.HEARTS);
		cnom.setDisplaySlot(DisplaySlot.BELOW_NAME);
		ctab.setDisplaySlot(DisplaySlot.PLAYER_LIST);
		todos = all.registerNewTeam("todos");
		todos.allowFriendlyFire();
		todos.setCanSeeFriendlyInvisibles(false);
		todos.setColor(org.bukkit.ChatColor.MAGIC);
		todos.setOption(Option.NAME_TAG_VISIBILITY, OptionStatus.ALWAYS);
	}
	public GameManager getJuego() {
		return this.juego;
	}

	public void registerChannels(String nombre, char pr) {
		IChannel tmp = new NChannel(this, nombre, pr);
		this.canales.add(tmp);
	}

	public OpLogger getALogger() {
		return this.alogger;
	}

	public IChannel getChannelByName(String name) {
		for (int i = 0; i < this.canales.size(); i++) {
			String tmp = this.canales.get(i).getName();
			if (tmp.equalsIgnoreCase(name)) {
				return this.canales.get(i);
			}

		}
		return null;
	}

	public void removeHPPlayer(UHCPlayer pl) {
		for (int i = 0; i < this.players.size(); i++) {
			UHCPlayer tmp = this.players.get(i);
			if (tmp == pl) {
				this.players.remove(i);
			}
		}
	}

	public UHCPlayer getHPByName(String name) {
		for (UHCPlayer p : this.players) {
			Player tmp = p.getPlayer();
			if (tmp.getName().equalsIgnoreCase(name)) {
				return p;
			}
		}
		return null;
	}

	public void onDisable() {
		Bukkit.getConsoleSender()
				.sendMessage(ChatColor.DARK_GREEN + "[UHC] El Plugin ha sido Desactivado Correctamente");
	}

	public void removeAdmin(Player acomparar) {
		ArrayList<Player> admins = this.admins;
		for (int i = 0; i < admins.size(); i++) {
			if (admins.get(i) == acomparar) {
				admins.remove(i);
			}
		}
	}

	public void registerPapiExpansions() {
		if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
			new UhcPlaceholders().register();
		}
	}

	public void loadTeamsFromConfig() {
		FileConfiguration cfg = this.getConfig();
		if (!cfg.contains("juego.equipos")) {
			return;
		}
		for (String teamname : cfg.getConfigurationSection("juego.equipos").getKeys(false)) {
			Location spawn;
			double x = cfg.getDouble("juego.equipos." + teamname + ".spawn.x");
			double y = cfg.getDouble("juego.equipos." + teamname + ".spawn.y");
			double z = cfg.getDouble("juego.equipos." + teamname + ".spawn.z");
			World world = Bukkit.getServer().getWorld(cfg.getString("juego.equipos." + teamname + ".spawn.world"));
			spawn = new Location(world, x, y, z);
			UHCTeam tmp = new UHCTeam(teamname, this.juego.getEquipos().size() + 1);
			tmp.setSpawn(spawn);
			this.getJuego().getEquipos().put(tmp, true);
		}
	}
	public void registrarComandos() {
		this.getCommand("c").setExecutor(new ChatCmd(this));
		this.getCommand("channel").setExecutor(new ChannelCmd(this));
		this.getCommand("uhc").setExecutor(new UhcCMD(this));
	}
	public PluginManager getPm() {
		return this.getServer().getPluginManager();
	}

	public ArrayList<Player> getAdmins() {
		return this.admins;
	}

	public void registerEvents() {
		PluginManager pm = this.getPm();
		pm.registerEvents(new PlayerJoinListener(this), this);
		pm.registerEvents(new PlayerDisconnectListener(this), this);
		pm.registerEvents(new MessageSentListener(this), this);
		pm.registerEvents(new DeathEventListener(), this);
		pm.registerEvents(new GameStateChangeListener(), this);
		pm.registerEvents(new EachSecondListener(), this);
		pm.registerEvents(new GameChangePartListener(), this);
		pm.registerEvents(new SpectatorAttackListener(), this);
		pm.registerEvents(new ObfuscateAdvancementName(), this);
		pm.registerEvents(new SpectatorInteractionListener(), this);
	}

	public void registerConfig() {
		File config = new File(this.getDataFolder(), "config.yml");
		rutaConfig = config.getPath();
		if (!config.exists()) {
			this.getConfig().options().copyDefaults(true);
			saveConfig();
		}
	}

	public ArrayList<UHCPlayer> getHoPokePlayers() {
		return this.players;
	}

	public void addPlayer(UHCPlayer player) {
		this.players.add(player);
	}
	public FileConfiguration getMessages() {
		if (messagesCfg == null) {
			reloadArenas();
		}
		return messagesCfg;
	}

	public void reloadArenas() {
		if (messagesCfg == null) {
			messagesCfgFile = new File(getDataFolder(), "messages.yml");
		}
		messagesCfg = YamlConfiguration.loadConfiguration(messagesCfgFile);
		Reader defConfigStream;
		try {
			defConfigStream = new InputStreamReader(this.getResource("messages.yml"), "UTF8");
			if (defConfigStream != null) {
				YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
				messagesCfg.setDefaults(defConfig);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public void saveArenas() {
		try {
			messagesCfg.save(messagesCfgFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void registerArenas() {
		messagesCfgFile = new File(this.getDataFolder(), "arenas.yml");
		if (!messagesCfgFile.exists()) {
			this.getMessages().options().copyDefaults(true);
			saveArenas();
		}
	}

}