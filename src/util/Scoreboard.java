package util;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import fr.minuskube.netherboard.Netherboard;
import fr.minuskube.netherboard.bukkit.BPlayerBoard;

public class Scoreboard {
	
	public static void updateScoreboard(Player vistima) {
		BPlayerBoard board = Netherboard.instance().getBoard(vistima);
		board.setName(ChatColor.translateAlternateColorCodes('&', "&4&lHoPoke"));
		
	}
}
