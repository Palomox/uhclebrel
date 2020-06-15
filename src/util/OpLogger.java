package util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import main.Main;

public class OpLogger {
	Logger oplog = Logger.getLogger("StaffLog");
	FileHandler fh;
	public OpLogger(Main plugin) {
		
		try {
			fh = new FileHandler(plugin.getDataFolder().getAbsolutePath()+"/stafflog.log");
			oplog.addHandler(fh);
			Formatter formatter = new Formatter() {
				@Override
				public String format(LogRecord lr) {
					return "[S] ";
				}
			};
			fh.setFormatter(formatter);
			
		}catch(SecurityException e) {
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public void logCmd(String info, Player issuer) {
		ArrayList<Player> admins = new ArrayList<Player>();
		ArrayList<Player> jugadores = new ArrayList<Player>(Bukkit.getOnlinePlayers());
		
		for(int ii =0; ii<=jugadores.size(); ii++) {
			if(jugadores.get(ii).hasPermission("") || jugadores.get(ii).isOp()) {
				admins.add(jugadores.get(ii));
			}
		}
		for(int i=0; i<=admins.size(); i++) {
			Player tmp =admins.get(i);
			tmp.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&7S&8] &7&i"+issuer.getName()+": "+info));
		}
		oplog.info(issuer.getName()+": "+info);
	}
	
	
}
