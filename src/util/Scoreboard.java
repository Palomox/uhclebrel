package util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.RenderType;
import org.bukkit.scoreboard.Team;

public class Scoreboard {

	/**
	 * Reloads the scoreboard, reloading placeholders and so.
	 * @param user the user instance to reload scoreboard
	 */
	public static void reloadScoreboard(UHCPlayer user) {
		Scoreboard.updateScoreboard(user.getPlayer(), user.getScoreboard());
		
	}
	public static void updateScoreboard(Player vistima, String nombre) {
		org.bukkit.scoreboard.Scoreboard board = vistima.getScoreboard();
		Objective sidebar = null;
		if (board.getObjective("sidebar") == null) {
			sidebar = board.registerNewObjective("sidebar", "dummy",
					ChatColor.translateAlternateColorCodes('&', nombre), RenderType.INTEGER);
		} else {
			sidebar = board.getObjective("sidebar");
		}
		sidebar.setDisplaySlot(DisplaySlot.SIDEBAR);
		sidebar.setDisplayName(ChatColor.translateAlternateColorCodes('&', nombre));
	}
	/**
	 * The maximum amount of character per lines is 48
	 * @param user the Mamerto whose scoreboard has to be affected
	 * @param text the String to be set in the Scoreboard.
	 * @param lineNumber the Integer of the line that has to be changed
	 */
	public static void updateScoreboard(UHCPlayer user, String text, int lineNumber) {
		Player vistima = user.getPlayer();
		Objective sidebar = null;
		if (vistima.getScoreboard().getObjective("sidebar") == null) {
			sidebar = vistima.getScoreboard().registerNewObjective("sidebar", "dummy", "Main Scoreboard",
					RenderType.INTEGER);
		} else {
			sidebar = vistima.getScoreboard().getObjective("sidebar");
		}
		sidebar.setDisplaySlot(DisplaySlot.SIDEBAR);
		String entry;
		if (text.length() <= 16) {
			entry = text;
		} else {
			Team line;
			if (sidebar.getScoreboard().getTeam("line" + lineNumber) != null) {
				line = sidebar.getScoreboard().getTeam("line" + lineNumber);
			} else {
				line = sidebar.getScoreboard().registerNewTeam("line" + lineNumber);
			}
			String prefix = text.substring(0, 16);
			String infix = null;
			String suffix = null;
			String splitted = null;
			splitted = text.substring(16);
			splitted = ChatColor.getLastColors(prefix)+splitted;
				if (text.length() >= 32) {
					infix = splitted.substring(0, 16);
				}else {
					infix = splitted;
				}
				if (text.length() > 32) {
					splitted = splitted.substring(16);
					splitted = ChatColor.getLastColors(infix)+splitted;
					if (text.length() <= 48) {
						suffix = splitted;
					} else {
						suffix = splitted.substring(0, 16);
					}
				}
			line.setPrefix(net.md_5.bungee.api.ChatColor.translateAlternateColorCodes('&', prefix));
			if (suffix != null) {
				line.setSuffix(net.md_5.bungee.api.ChatColor.translateAlternateColorCodes('&', suffix));
			}
			line.addEntry(ChatColor.translateAlternateColorCodes('&', infix));
			entry = infix;
		}
		
	/*
	 * Now it checks if the line has to be resetted, and if so it resets it.
	 */
	if(user.getScoreboard().containsKey(lineNumber)) {
		vistima.getScoreboard().resetScores(user.getScoreboard().get(lineNumber));
	}
	/*
	 * Finally, the new value gets put into the Scoreboard. Then 
	 * its also added to the player's scoreboard HashMap.
	 */
	sidebar.getScore(ChatColor.translateAlternateColorCodes('&', entry)).setScore(lineNumber);
	user.getScoreboard().put(lineNumber, entry);
	}
	
	/**
	 * Changes a Player's scoreboard to a new one from a HashMap
	 * @param vistima the Player whose scoreboard has to be changed
	 * @param valores the HashMap of lines of the new Scoreboard
	 */
	public static void updateScoreboard(Player vistima, HashMap<Integer, String> valores) {
		org.bukkit.scoreboard.Scoreboard board = vistima.getScoreboard();
		Objective sidebar = null;
		if (board.getObjective("sidebar") == null) {
			sidebar = board.registerNewObjective("sidebar", "dummy", "Main Scoreboard", RenderType.INTEGER);
		} else {
			sidebar = board.getObjective("sidebar");
		}
		sidebar.setDisplaySlot(DisplaySlot.SIDEBAR);
		List<Object> lineas = Arrays.asList(sidebar.getScoreboard().getEntries().toArray());
		for (int linea : valores.keySet()) {
			sidebar.getScoreboard().resetScores((String) lineas.get(linea));
			sidebar.getScore(valores.get(linea)).setScore(linea);
		}
	}

	public static void clear(UHCPlayer user) {
		Player vistima = user.getPlayer();
		for(int line : user.getScoreboard().keySet()) {
			String text = user.getScoreboard().get(line);
			vistima.getScoreboard().resetScores(text);
		}
	}
}
