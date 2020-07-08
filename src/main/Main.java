package main;

import java.io.File;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import chat.IChannel;
import chat.NChannel;
import comandos.ChannelCmd;
import comandos.ChatCmd;
import comandos.HoCoreCMD;
import comandos.Setspawn;
import comandos.Spawn;
import comandos.SudoCmd;
import eventos.CadaSegundo;
import eventos.CambiaEpisodio;
import eventos.CambiaEstado;
import eventos.EspectadorAtaca;
import eventos.MensajeEnviado;
import eventos.Muerte;
import eventos.NewPlayer;
import eventos.QuitarListaAdmins;
import fr.minuskube.inv.InventoryManager;
import uhc.Episodio;
import uhc.Equipo;
import uhc.Juego;
import uhc.SecondEvent;
import uhc.UhcPlaceholders;
import util.Mamerto;
import util.OpLogger;

public class Main extends JavaPlugin{
	public String rutaConfig;
	PluginDescriptionFile plugindesc = getDescription();
	public String version = plugindesc.getVersion();
	public String nombre = plugindesc.getName();
	public InventoryManager invm = new InventoryManager(this);
	private ArrayList<Mamerto> players = new ArrayList<Mamerto>();
	private OpLogger alogger;
	private ArrayList<Player> admins = new ArrayList<Player>();
	private ArrayList<IChannel> canales = new ArrayList<IChannel>();
	public static Main instance;
	public Juego juego;
	public Event finalizar;
	
		public void onEnable() {
		instance = this;
		registerPapiExpansions();
		startSeconding();
		Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_GREEN + "[HoPoke] El Plugin ha sido Activado Correctamente");
		registerConfig();
		registerEvents();
		registrarComandos();
		alogger = new OpLogger(this);
		juego = new Juego();
		invm.init();
		for(String nombre : getConfig().getConfigurationSection("chat.channels").getKeys(false)) {
			String channelName = getConfig().getString("chat.channels."+nombre+".name");
			String tmp = getConfig().getString("chat.channels."+nombre+".fastprefix");
			char pre = tmp.charAt(0);
			registerChannels(channelName, pre);
		}
	}
	public void startSeconding() {
		this.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			
			@Override
			public void run() {
				Bukkit.getPluginManager().callEvent(new SecondEvent());
			}
		}, 0, 20);
	}
	public ArrayList<IChannel> getCanales(){
		return this.canales;
	}
	public Juego getJuego() {
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
		for(int i=0; i<this.canales.size(); i++) {
			String tmp = this.canales.get(i).getName();
			if(tmp.equalsIgnoreCase(name)) {
				return this.canales.get(i);
			}
			
		}
		return null;
	}
	public void removeHPPlayer(Mamerto pl) {
		for(int i=0; i<this.players.size(); i++) {
			Mamerto tmp = this.players.get(i);
			if(tmp == pl) {
				this.players.remove(i);
			}
		}
	}
	public Mamerto getHPByName(String name) {
		for(Mamerto p: this.players) {
			Player tmp = p.getPlayer();
			if(tmp.getName().equalsIgnoreCase(name)) {
				return p;
			}
		}
		return null;
	}
	public void onDisable() {
		//saveDb();
		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "[CTW]El Plugin ha sido Desactivado Correctamente");
	}
	public void removeAdmin(Player acomparar) {
		ArrayList<Player> admins = this.admins;
		for(int i=0; i< admins.size(); i++) {
			if(admins.get(i)==acomparar) {
				admins.remove(i);
			}
		}
	}
	
	public void registerPapiExpansions() {
		if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null){
            new UhcPlaceholders().register();
      }
	}
	public void loadTeamsFromConfig() {
		FileConfiguration cfg = this.getConfig();
		if(!cfg.contains("juego.equipos")) {
			return;
		}
		for(String teamname : cfg.getConfigurationSection("juego.equipos").getKeys(false)) {
			Location spawn;
			double x = cfg.getDouble("juego.equipos."+teamname+".spawn.x");
			double y = cfg.getDouble("juego.equipos."+teamname+".spawn.y");
			double z = cfg.getDouble("juego.equipos."+teamname+".spawn.z");
			World world = Bukkit.getServer().getWorld(cfg.getString("juego.equipos."+teamname+".spawn.world"));
			spawn = new Location(world, x, y, z);
			Equipo tmp = new Equipo(teamname, this.juego.getEquipos().size()+1);
			tmp.setSpawn(spawn);
			this.getJuego().getEquipos().put(tmp, true);
		}
	}
	public void registrarComandos() {
		this.getCommand("setspawn").setExecutor(new Setspawn(this));
		this.getCommand("spawn").setExecutor(new Spawn(this));
		this.getCommand("c").setExecutor(new ChatCmd(this));
		this.getCommand("sudo").setExecutor(new SudoCmd(this));
		this.getCommand("channel").setExecutor(new ChannelCmd(this));
		this.getCommand("uhc").setExecutor(new HoCoreCMD(this));
		}
	public PluginManager getPm() {
		return this.getServer().getPluginManager();
	}
	public ArrayList<Player> getAdmins(){
		return this.admins;
	}
	public void registerEvents() {
		PluginManager pm = this.getPm();
		pm.registerEvents(new NewPlayer(this), this);
		pm.registerEvents(new QuitarListaAdmins(this), this);
		pm.registerEvents(new MensajeEnviado(this), this);
		pm.registerEvents(new Muerte(), this);
		pm.registerEvents(new CambiaEstado(), this);
		pm.registerEvents(new CadaSegundo(), this);
		pm.registerEvents(new CambiaEpisodio(), this);
		pm.registerEvents(new EspectadorAtaca(), this);
	}
	public void registerConfig() {
		File config = new File(this.getDataFolder(), "config.yml");
		rutaConfig = config.getPath();
		if(!config.exists()) {
			this.getConfig().options().copyDefaults(true);
			saveConfig();
		}
	}
	public ArrayList<Mamerto> getHoPokePlayers() {
		return this.players;
	}
	public void addPlayer(Mamerto player) {
		this.players.add(player);
	}	

}