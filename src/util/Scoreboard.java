package util;

import java.util.HashMap;

import org.bukkit.ChatColor;

import fr.minuskube.netherboard.Netherboard;
import fr.minuskube.netherboard.bukkit.BPlayerBoard;
import me.clip.placeholderapi.PlaceholderAPI;

public class Scoreboard {

	public static void updateScoreboard(UHCPlayer vistima, String nombre) {
		BPlayerBoard board = Netherboard.instance().getBoard(vistima.getPlayer()) == null ? Netherboard.instance().createBoard(vistima.getPlayer(), nombre) : Netherboard.instance().getBoard(vistima.getPlayer());
		board.setName(ChatColor.translateAlternateColorCodes('&', nombre));
	}

	public static void updateScoreboard(UHCPlayer vistima, String valor, int linea) {
		BPlayerBoard board = Netherboard.instance().getBoard(vistima.getPlayer()) == null ? Netherboard.instance().createBoard(vistima.getPlayer(), "scoreboard") : Netherboard.instance().getBoard(vistima.getPlayer());
		vistima.getScoreboard().put(linea, valor);
		board.set(valor, linea);
	}

	public static void updateScoreboard(UHCPlayer vistima, HashMap<Integer, String> valores) {
		BPlayerBoard board = Netherboard.instance().getBoard(vistima.getPlayer()) == null ? Netherboard.instance().createBoard(vistima.getPlayer(), "scoreboard") : Netherboard.instance().getBoard(vistima.getPlayer());
		for (int linea : valores.keySet()) {
			vistima.getScoreboard().put(linea, valores.get(linea));
			board.set(valores.get(linea), linea);
		}
	}

	public static void clear(UHCPlayer vistima) {
		BPlayerBoard board = Netherboard.instance().getBoard(vistima.getPlayer()) == null ? Netherboard.instance().createBoard(vistima.getPlayer(), "scoreboard") : Netherboard.instance().getBoard(vistima.getPlayer());
		vistima.getScoreboard().clear();
		board.clear();
	}

	public static void reloadScoreboard(UHCPlayer mam) {
		BPlayerBoard board = Netherboard.instance().getBoard(mam.getPlayer());
		board.clear();
		mam.getScoreboard().forEach((k, v) -> {
			board.set(PlaceholderAPI.setPlaceholders(mam.getPlayer(), v), k);
		});
	}
}