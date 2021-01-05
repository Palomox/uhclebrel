package util;

import org.bukkit.configuration.file.FileConfiguration;

public class Messages {
	public String prefix;
	public String newPart;
	public String newSkin;
	
	public Messages(FileConfiguration config) {
		prefix = config.getString("messages.prefix");
		newPart = config.getString("messages.newPart");
		newSkin = config.getString("messages.newSkin");
		
	}
	
}
