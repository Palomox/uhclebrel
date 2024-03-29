package events;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scoreboard.Team;

import main.UHCLebrel;
import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.ChatColor;
import uhc.StatusChangeEvent;
import util.UHCPlayer;
import util.Scoreboard;

public class GameStateChangeListener implements Listener {
	private Team todos;

	public GameStateChangeListener() {
		UHCLebrel.instance.everyone = todos;
	}

	@EventHandler
	public void onJuegoFinalizado(StatusChangeEvent e) {
		UHCLebrel plugin = UHCLebrel.instance;
		switch (e.getNuevoestado()) {
		case FINISHING:
			todos.unregister();
			for (UHCPlayer tmp : UHCLebrel.instance.getUHCPlayers()) {
				UHCLebrel.instance.gameManager.setSpectator(tmp);
				Scoreboard.clear(tmp);
				List<?> lineas = plugin.getConfig().getList("scoreboard.final.lines");
				for (int i=0; i<lineas.size(); i++) {
					Scoreboard.updateScoreboard(tmp,
							PlaceholderAPI.setPlaceholders(tmp.getPlayer(), lineas.get(i).toString()), lineas.size()-i);
				}
			}
			break;
		case PLAYING:
			for (UHCPlayer tmp : UHCLebrel.instance.getUHCPlayers()) {
				List<?> lineas = plugin.getConfig().getList("scoreboard.durante.lines");
				Scoreboard.clear(tmp);
				for (int i = 0; i < lineas.size(); i++) {
					String linea = lineas.get(i).toString();
					Scoreboard.updateScoreboard(tmp, ChatColor.translateAlternateColorCodes('&', PlaceholderAPI.setPlaceholders(tmp.getPlayer(), linea)), lineas.size()-i);
				}
			}
			for (UHCPlayer temp : UHCLebrel.instance.gameManager.getParticipantes()) {
				temp.setDisplayname(ChatColor.translateAlternateColorCodes('&', "&k" + temp.getPlayer().getName()));
				temp.getPlayer().setDisplayName(temp.getDisplayname());
				temp.getPlayer().setPlayerListName(temp.getDisplayname());
			}
			break;
		default:
			break;
		}
	}
}
