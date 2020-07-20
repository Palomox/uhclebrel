package eventos;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.RenderType;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

import main.Main;
import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.ChatColor;
import uhc.EstadoChangeEvent;
import util.Mamerto;
import util.Scoreboard;

public class CambiaEstado implements Listener{
	private Team todos;
	public CambiaEstado() {
		Main.instance.todos = todos;
	}
	@EventHandler
	public void onJuegoFinalizado(EstadoChangeEvent e) {
		Main plugin = Main.instance;
		switch(e.getNuevoestado()) {
		case FINALIZADO:
			todos.unregister();
			for(Mamerto tmp : Main.instance.getHoPokePlayers()) {
				Main.instance.juego.setSpectator(tmp);
				Scoreboard.clear(tmp.getPlayer());
				for (String linea : plugin.getConfig()
						.getConfigurationSection("scoreboard.final.lines")
						.getKeys(false)) {
					String texto = plugin.getConfig().getString("scoreboard.final.lines." + linea);
					texto = PlaceholderAPI.setPlaceholders(tmp.getPlayer(), texto);
					int line = Integer.valueOf(linea);
					Scoreboard.updateScoreboard(tmp.getPlayer(), texto, line);
				}
			}
			break;
		case JUGANDO:
			for(Mamerto tmp : Main.instance.getHoPokePlayers()) {
				Scoreboard.clear(tmp.getPlayer());
				for (String linea : plugin.getConfig()
						.getConfigurationSection("scoreboard.durante.lines")
						.getKeys(false)) {
					String texto = plugin.getConfig().getString("scoreboard.durante.lines." + linea);
					texto = PlaceholderAPI.setPlaceholders(tmp.getPlayer(), texto);
					int line = Integer.valueOf(linea);
					Scoreboard.updateScoreboard(tmp.getPlayer(), texto, line);
				}
			}
			ScoreboardManager manager = Bukkit.getScoreboardManager();
			org.bukkit.scoreboard.Scoreboard all = manager.getNewScoreboard();
			Objective cnom = all.registerNewObjective("corazonesbajo", "health", "/20", RenderType.INTEGER);
			Objective ctab = all.registerNewObjective("corazonestab", 
					"health",
					" ", 
					RenderType.HEARTS);
			cnom.setDisplaySlot(DisplaySlot.BELOW_NAME);
			ctab.setDisplaySlot(DisplaySlot.PLAYER_LIST);
			todos = all.registerNewTeam("todos");
			todos.allowFriendlyFire();
			todos.setCanSeeFriendlyInvisibles(false);
			todos.setPrefix(ChatColor.MAGIC+"");
			
			for(Mamerto temp : Main.instance.juego.getParticipantes()) {
				temp.setDisplayname(ChatColor.translateAlternateColorCodes('&', "&k"+temp.getPlayer().getName()));
				temp.getPlayer().setDisplayName(temp.getDisplayname());
				temp.getPlayer().setPlayerListName(temp.getDisplayname());
				todos.addEntry(temp.getPlayer().getName());
				temp.getPlayer().setScoreboard(all);
			}
			break;
		default:
			break;
		}
	}
}
