package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class ConectorSQL {
	private Connection connection;
	
	private String host;
	private int port;
	private String password;
	private String user;
	private String database;

	public ConectorSQL(String host, int port, String password, String user, String database) {
		this.host = host;
		this.port = port;
		this.password = password;
		this.user = user;
		this.database = database;
		
		try{
			synchronized (this) {
				if(connection !=null && !connection.isClosed()) {
					Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED+"[HoPoke] Se jodió la bicicleta - no se ha podido conectar a la base de datos.");
					return;
				}
				Class.forName("com.mysql.jdbc.Driver");
				this.connection = DriverManager.getConnection("jdbc:mysql://"+this.host+":"+this.port+"/"+this.database, this.user, this.password);
				Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_GREEN+"[HoPoke] Conexión a la BD realizada - ya puedes dejar de cruzar los dedos.");
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}catch(ClassNotFoundException e) {
			Bukkit.getConsoleSender().sendMessage("cnfe");
		}
	}

	public Connection getConnection() {
		return connection;
	}
	
	
	
	
	
	

}
