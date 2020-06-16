package main;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import comandos.Banhammer;
import comandos.ChatCmd;
import comandos.Setspawn;
import comandos.Spawn;
import comandos.SudoCmd;
import eventos.Banhammereado;
import eventos.NewPlayer;
import fr.minuskube.inv.InventoryManager;
import util.ConectorSQL;
import util.HoPokePlayer;
import util.OpLogger;
import util.SQLInit;

public class Main extends JavaPlugin{
	private ConectorSQL conexion;
	public String rutaConfig;
	PluginDescriptionFile plugindesc = getDescription();
	public String version = plugindesc.getVersion();
	public String nombre = plugindesc.getName();
	public InventoryManager invm = new InventoryManager(this);
	private FileConfiguration database = null;
	private File databaseFile = null;
	private ArrayList<HoPokePlayer> players = new ArrayList<HoPokePlayer>();
	private String dbprefix;
	private OpLogger alogger;
	private ArrayList<Player> admins = new ArrayList<Player>();
	private Thread pthread = new Thread("HoCore");
		public void onEnable() {
		Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_GREEN + "[HoPoke] El Plugin ha sido Activado Correctamente");
		registerConfig();
		registerEvents();
		registrarComandos();
		registerDatabase();
		alogger = new OpLogger(this);
		
		FileConfiguration databasefile = getDatabase();
		this.dbprefix = databasefile.getString("database.mysql.prefix");
		this.conexion = new ConectorSQL(databasefile.getString("database.mysql.host"), databasefile.getInt("database.mysql.port"), databasefile.getString("database.mysql.password"), databasefile.getString("database.mysql.user"), databasefile.getString("database.mysql.database"));
		invm.init();
		if(SQLInit.isDBSetup(this)) {
			Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_GREEN+"[HoPoke] La base de datos esta perfecta, continuando inicio");
		} else {
			//aqui se crea la tabla
			Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW+"[HoPoke] La base de datos no tiene las tablas necesarias, creandolas ahora...");
			
			try {
				PreparedStatement comando = this.getSQL().prepareStatement("CREATE TABLE "+this.dbprefix+"usuarios (UUID VARCHAR(80), primeraunion VARCHAR(20), PRIMARY KEY(UUID))");
				comando.executeUpdate();
			}catch(SQLException e) {
				e.printStackTrace();
			}
			
			Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_GREEN+"[HoPoke] Se han creado exitosamente las tablas necesarias");


		}
		loadDb();
		
	}
	public OpLogger getALogger() {
		return this.alogger;
	}
	public void loadDb() {
		Connection db = this.conexion.getConnection();
		try {
		PreparedStatement cmd = db.prepareStatement("SELECT * FROM "+this.dbprefix+"usuarios");
		ResultSet rs = cmd.executeQuery();
		while(rs.next()) {
			String uuid = rs.getString("UUID");
			LocalDate fj = LocalDate.parse(rs.getString("primeraunion"));
			HoPokePlayer pl = new HoPokePlayer(uuid, fj);
			this.players.add(pl);
		}
			
	
		}catch (SQLException e) {
			Bukkit.getConsoleSender().sendMessage("falla loaddb");
			e.printStackTrace();
		}
		
	}
	public void query(String query) {
		try {
		PreparedStatement cmd = this.conexion.getConnection().prepareStatement(query);
		cmd.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
	}
	public void removeHPPlayer(HoPokePlayer pl) {
		for(int i=0; i<this.players.size(); i++) {
			HoPokePlayer tmp = this.players.get(i);
			if(tmp == pl) {
				this.players.remove(i);
			}
		}
	}
	public ResultSet consulta(String query) {
		try {
		PreparedStatement cmd = this.conexion.getConnection().prepareStatement(query);
		ResultSet rs = cmd.executeQuery();
		return rs;
		}catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
		
		}
	/*public void saveDb() {
		for(int i=0; i<=this.players.size(); i++) {
			String uuid = this.players.get(i).getPlayer().getUniqueId().toString();
			String ld = this.players.get(i).getFJ().toString();
			Connection db = this.conexion.getConnection();
			try {
			PreparedStatement cmd = db.prepareStatement("INSERT INTO "+this.dbprefix+"usuarios VALUES ("+uuid+", "+ld+")");
			cmd.executeQuery();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}*/
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
	
	public void registrarComandos() {
		this.getCommand("setspawn").setExecutor(new Setspawn(this));
		this.getCommand("spawn").setExecutor(new Spawn(this));
		this.getCommand("banhammer").setExecutor(new Banhammer(this));
		this.getCommand("c").setExecutor(new ChatCmd(this));
		this.getCommand("sudo").setExecutor(new SudoCmd(this));
		}
	/*public void saveToSQL(ArrayList<HoPokePlayer> jugadores) {
		for(int i=0; i<= jugadores.size(); i++) {
			Connection con = this.getSQL();
			ResultSet aversiesteweyestaenlatabla = null;
			try {
				String UUID = jugadores.get(i).getPlayer().getUniqueId().toString();
				PreparedStatement comando = con.prepareStatement("SELECT FROM "+this.dbprefix+"usuarios WHERE UUID='"+UUID+"'");
				aversiesteweyestaenlatabla = comando.executeQuery();
				if(!aversiesteweyestaenlatabla.next()) {
					PreparedStatement metele = con.prepareStatement("");
					metele.executeQuery();
				}
			}catch (SQLException e) {
				
			}
			
			try{
				PreparedStatement comando = con.prepareStatement("");
			    comando.executeQuery();
			}catch(SQLException e) {
				
			}
		}
	}*/
	public PluginManager getPm() {
		return this.getServer().getPluginManager();
	}
	/*public void loadAdmins() {
		ArrayList<Player> jugadores = new ArrayList<Player>(Bukkit.getOnlinePlayers());
		
		for(int ii =0; ii<jugadores.size(); ii++) {
			if(jugadores.get(ii).hasPermission("hopoke.admin") || jugadores.get(ii).isOp()) {
				admins.add(jugadores.get(ii));
			}
		}		
	}*/
	public ArrayList<Player> getAdmins(){
		return this.admins;
	}
	public void registerEvents() {
		PluginManager pm = this.getPm();
		pm.registerEvents(new Banhammereado(), this);
		pm.registerEvents(new NewPlayer(this), this);
	}
	public void registerConfig() {
		File config = new File(this.getDataFolder(), "config.yml");
		rutaConfig = config.getPath();
		if(!config.exists()) {
			this.getConfig().options().copyDefaults(true);
			saveConfig();
		}
	}

	
	public FileConfiguration getDatabase() {
		if (database == null) {
			reloadDatabase();
		}
		return database;
	}

	public void reloadDatabase() {
		if (database == null) {
			databaseFile = new File(getDataFolder(), "database.yml");
		}
		database = YamlConfiguration.loadConfiguration(databaseFile);
		Reader defConfigStream;
		try {
			defConfigStream = new InputStreamReader(this.getResource("database.yml"), "UTF8");
			if (defConfigStream != null) {
				YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
				database.setDefaults(defConfig);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public void saveDatabase() {
		try {
			database.save(databaseFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void registerDatabase() {
		databaseFile = new File(this.getDataFolder(), "grant.yml");
		if (!databaseFile.exists()) {
			this.getDatabase().options().copyDefaults(true);
			saveDatabase();
		}
	}
	public ArrayList<HoPokePlayer> getHoPokePlayers() {
		return this.players;
	}
	public void addPlayer(HoPokePlayer player) {
		this.players.add(player);
	}
	public Connection getSQL() {
		return this.conexion.getConnection();
	}
	public String getDBPrefix() {
		return this.dbprefix;
	}
	public String getDBName() {
		return this.database.getString("database.mysql.database");
	}
	

}