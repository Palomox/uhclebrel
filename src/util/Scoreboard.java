package util;


import java.util.HashMap;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import fr.minuskube.netherboard.Netherboard;
import fr.minuskube.netherboard.bukkit.BPlayerBoard;

public class Scoreboard {
	
	public static void updateScoreboard(Player vistima, String nombre) {
		BPlayerBoard board = Netherboard.instance().getBoard(vistima);
		board.setName(ChatColor.translateAlternateColorCodes('&', nombre));
	}
	public static void updateScoreboard(Player vistima, String valor, int linea) {
		BPlayerBoard board = Netherboard.instance().getBoard(vistima);
		board.set(valor, linea);
	}
	public static void updateScoreboard(Player vistima, HashMap<Integer, String> valores) {
		BPlayerBoard board = Netherboard.instance().getBoard(vistima);
		for(int linea : valores.keySet()) {
			board.set(valores.get(linea), linea);
		}
	}
	public static void clear(Player vistima) {
		BPlayerBoard board = Netherboard.instance().getBoard(vistima);
		board.clear();
	}
}
