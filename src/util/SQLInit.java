package util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import main.Main;

public class SQLInit {
	/*private Main plugin;
	public SQLInit(Main plugin) {
		this.plugin = plugin;
	}*/
	
	public void setupTables() {
		
	}
	public static boolean isDBSetup(Main plugin) {
		try {
			PreparedStatement comando = plugin.getSQL().prepareStatement("SHOW TABLES;");
			ResultSet resultado = comando.executeQuery();
			while(resultado.next()) {
				if(resultado.getString("Tables_in_"+plugin.getDBName()).equals(plugin.getDBPrefix()+"usuarios")) {
					return true;
				}else {
					continue;
				}
			}
		}catch (SQLException e) {
			
		}
		return false;
	}
}
