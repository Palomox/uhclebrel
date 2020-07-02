package util;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import chat.NChannel;
import main.Main;

public class OpLogger {
	private Main plugin;
	
	Logger oplog = Logger.getLogger("StaffLog");
	FileHandler fh;
	public OpLogger(Main plugin) {
		this.plugin = plugin;
		try {
			fh = new FileHandler(plugin.getDataFolder().getAbsolutePath()+"/stafflog.log");
			oplog.addHandler(fh);
			Formatter formatter = new Formatter() {
				@Override
				public String format(LogRecord lr) {
					return "[S] "+lr.getMessage();
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
		NChannel staff = plugin.getChannelByName("Staff");
		staff.sendRawMessage(ChatColor.translateAlternateColorCodes('&', "&8[&3S&8 | &7&o"+issuer.getName()+": "+info+"&r&8]"));
		oplog.info(issuer.getName()+": "+info);
	}
	
	
}
