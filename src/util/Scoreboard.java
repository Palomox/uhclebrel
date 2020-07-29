package util;


import java.util.List;
import java.util.Arrays;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.RenderType;


public class Scoreboard {
	
	public static void updateScoreboard(Player vistima, String nombre) {
		org.bukkit.scoreboard.Scoreboard board = vistima.getScoreboard();
		Objective sidebar = null;
		if(board.getObjective("sidebar") == null) {
			sidebar = board.registerNewObjective("sidebar", "dummy", ChatColor.translateAlternateColorCodes('&', nombre), RenderType.INTEGER);
		}else {
			sidebar = board.getObjective("sidebar");
		}
		sidebar.setDisplaySlot(DisplaySlot.SIDEBAR);
		sidebar.setDisplayName(ChatColor.translateAlternateColorCodes('&', nombre));
	}
	public static void updateScoreboard(Player vistima, String valor, int linea) {
		Objective sidebar = null;
		if(vistima.getScoreboard().getObjective("sidebar") == null) {
			sidebar = vistima.getScoreboard().registerNewObjective("sidebar", "dummy", "Main Scoreboard", RenderType.INTEGER);
		}else {
			sidebar = vistima.getScoreboard().getObjective("sidebar");
		}
		sidebar.setDisplaySlot(DisplaySlot.SIDEBAR);
		List<Object> lineas = Arrays.asList(sidebar.getScoreboard().getEntries().toArray());
		sidebar.getScoreboard().resetScores((String) lineas.get(linea));
		sidebar.getScore(ChatColor.translateAlternateColorCodes('&', valor)).setScore(linea);
	}
	public static void updateScoreboard(Player vistima, HashMap<Integer, String> valores) {
		org.bukkit.scoreboard.Scoreboard board = vistima.getScoreboard();
		Objective sidebar = null;
		if(board.getObjective("sidebar") == null) {
			sidebar = board.registerNewObjective("sidebar", "dummy", "Main Scoreboard", RenderType.INTEGER);
			}else {
			sidebar = board.getObjective("sidebar");			
		}
		sidebar.setDisplaySlot(DisplaySlot.SIDEBAR);
		List<Object> lineas = Arrays.asList(sidebar.getScoreboard().getEntries().toArray());
		for(int linea : valores.keySet()) {
			sidebar.getScoreboard().resetScores((String) lineas.get(linea));
			sidebar.getScore(valores.get(linea)).setScore(linea);
		}
	}
	public static void clear(Player vistima) {
		vistima.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
	}
}
