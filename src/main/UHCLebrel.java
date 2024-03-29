package main;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
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
import commands.CreateTeamCMD;
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
import metrics.Metrics;
import net.skinsrestorer.api.SkinsRestorerAPI;
import uhc.EachSecondEvent;
import uhc.GameManager;
import uhc.UHCTeam;
import uhc.UhcPlaceholders;
import util.Messages;
import util.OpLogger;
import util.UHCPlayer;

public class UHCLebrel extends JavaPlugin {
	private final int statsId = 9205;
	public String configPath;
	PluginDescriptionFile plugindesc = getDescription();
	public String version = plugindesc.getVersion();
	public String name = plugindesc.getName();
	private ArrayList<UHCPlayer> players = new ArrayList<UHCPlayer>();
	private OpLogger alogger;
	private ArrayList<IChannel> channels = new ArrayList<IChannel>();
	public Team everyone;
	private boolean premadeTeams;
	public static UHCLebrel instance;
	public GameManager gameManager;
	public SkinsRestorerAPI sapi;
	public Timer killTimer = new Timer();
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
		registerCommands();
		alogger = new OpLogger(this);
		gameManager = new GameManager();
		for (String nombre : getConfig().getConfigurationSection("chat.channels").getKeys(false)) {
			String channelName = getConfig().getString("chat.channels." + nombre + ".name");
			String tmp = getConfig().getString("chat.channels." + nombre + ".fastprefix");
			char pre = tmp.charAt(0);
			registerChannels(channelName, pre);
		}
		sapi = SkinsRestorerAPI.getApi();
		startScoreboardTeams();
		Metrics metrics = new Metrics(this, statsId);
		registerMessages();
		reloadMessages();
		this.premadeTeams = this.getConfig().getBoolean("juego.preequipos");
		messages = new Messages(messagesCfg);
		Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_GREEN + "[UHC] El Plugin ha sido Activado Correctamente");
	}

	public void startSeconding() {
		this.getServer().getScheduler().scheduleSyncRepeatingTask(this, () -> {
			Bukkit.getPluginManager().callEvent(new EachSecondEvent());
		}, 0, 20);
	}

	public ArrayList<IChannel> getChannels() {
		return this.channels;
	}

	public void startScoreboardTeams() {
		ScoreboardManager manager = Bukkit.getScoreboardManager();
		org.bukkit.scoreboard.Scoreboard all = manager.getNewScoreboard();
		this.all = all;
		Objective cnom = all.registerNewObjective("corazonesbajo", "health",
				ChatColor.translateAlternateColorCodes('&', "❤"), RenderType.INTEGER);

		Objective ctab = all.registerNewObjective("corazonestab", "health", " ", RenderType.HEARTS);
		cnom.setDisplaySlot(DisplaySlot.BELOW_NAME);
		ctab.setDisplaySlot(DisplaySlot.PLAYER_LIST);
		everyone = all.registerNewTeam("todos");
		everyone.allowFriendlyFire();
		everyone.setCanSeeFriendlyInvisibles(false);
		everyone.setColor(org.bukkit.ChatColor.MAGIC);
		everyone.setOption(Option.NAME_TAG_VISIBILITY, OptionStatus.ALWAYS);
	}

	public GameManager getGameManager() {
		return this.gameManager;
	}

	public void registerChannels(String nombre, char pr) {
		IChannel tmp = new NChannel(this, nombre, pr);
		this.channels.add(tmp);
	}

	public OpLogger getALogger() {
		return this.alogger;
	}

	public IChannel getChannelByName(String name) {
		for (int i = 0; i < this.channels.size(); i++) {
			String tmp = this.channels.get(i).getName();
			if (tmp.equalsIgnoreCase(name)) {
				return this.channels.get(i);
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

	public UHCPlayer getUHCPlayerByName(String name) {
		//Bukkit.getScheduler().runTaskAsynchronously(this, (t) -> {} );
		//Callable<UHCPlayer> executeStream = () -> {
			return this.players.stream().filter((p) -> {
				return p.getPlayer().getName().equals(name);
			}).findFirst().get();
		//};
		//Bukkit.getScheduler().runTaskAsynchronously(this, (Runnable) executeStream);
	}

	public void onDisable() {
		Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_GREEN + "[UHC] Succesfully disabled the plugin");
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
			UHCTeam tmp = new UHCTeam(teamname, this.gameManager.getEquipos().size() + 1);
			this.getGameManager().getEquipos().put(tmp, true);
		}
	}

	public void registerCommands() {
		this.getCommand("c").setExecutor(new ChatCmd(this));
		this.getCommand("channel").setExecutor(new ChannelCmd(this));
		this.getCommand("uhc").setExecutor(new UhcCMD(this));
		this.getCommand("createteam").setExecutor(new CreateTeamCMD());
	}

	public boolean isPremadeTeams() {
		return premadeTeams;
	}

	public PluginManager getPm() {
		return this.getServer().getPluginManager();
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
		configPath = config.getPath();
		if (!config.exists()) {
			this.getConfig().options().copyDefaults(true);
			saveConfig();
		}
	}

	public ArrayList<UHCPlayer> getUHCPlayers() {
		return this.players;
	}

	public void addPlayer(UHCPlayer player) {
		this.players.add(player);
	}

	public FileConfiguration getMessages() {
		if (messagesCfg == null) {
			reloadMessages();
		}
		return messagesCfg;
	}

	public void reloadMessages() {
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

	public void saveMessages() {
		try {
			messagesCfg.save(messagesCfgFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void registerMessages() {
		messagesCfgFile = new File(this.getDataFolder(), "messages.yml");
		if (!messagesCfgFile.exists()) {
			this.getMessages().options().copyDefaults(true);
			saveMessages();
		}
	}

}