package util;

import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;

public class Messages {
	public String prefix;
	public String newPart;
	public String newSkin;
	public String teamsMade;
	public String playerTaken;
	public String teamCreated;
	public String addedMember;
	public String tooFar;

	public Messages(FileConfiguration config) {
		prefix = config.getString("messages.prefix");
		newPart = config.getString("messages.newPart");
		newSkin = config.getString("messages.newSkin");
		teamsMade = config.getString("messages.teamsMade");
		playerTaken = config.getString("messages.playerTaken");
		addedMember = config.getString("messages.addedMember");
		teamCreated = config.getString("messages.teamCreated");
		tooFar = config.getString("messages.tooFar");
	}

}
