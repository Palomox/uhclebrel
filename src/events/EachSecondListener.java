package events;

import java.time.Duration;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import main.UHCLebrel;
import uhc.PartChangeEvent;
import uhc.UHCTeam;
import uhc.GameStatuses;
import uhc.EachSecondEvent;
import util.UHCPlayer;
import util.Scoreboard;

public class EachSecondListener implements Listener {
	int linea = UHCLebrel.instance.getConfig().getInt("scoreboard.lineatiempo");
	public EachSecondListener() {
	}

	@EventHandler
	public void onSecond(EachSecondEvent e) {
		if (UHCLebrel.instance.getGameManager().getEstado().equals(GameStatuses.PLAYING)) {
			UHCLebrel.instance.getGameManager().getEpisodio().menosUnSec();
			//long s = UHCLebrel.instance.getGameManager().getEpisodio().getDuracion().getSeconds();
			for(UHCTeam tmp : UHCLebrel.instance.getGameManager().getEquipos().keySet()) {
				for(UHCPlayer mam : tmp.getMiembros().keySet()) {
					/*if(!mam.isDesconectado()){
					Scoreboard.updateScoreboard(mam,
					ChatColor.translateAlternateColorCodes('&', String.format("&e%02d:%02d", (s % 3600) / 60, (s % 60))),
					linea);
					}*/
					Scoreboard.reloadScoreboard(mam);
				}
			}

			if (UHCLebrel.instance.getGameManager().getEpisodio().getDuracion().equals(Duration.ZERO)) {
				Bukkit.getPluginManager().callEvent(new PartChangeEvent(UHCLebrel.instance.getGameManager().getEpisodio().getId() + 1));
			}
		}
	}
}
