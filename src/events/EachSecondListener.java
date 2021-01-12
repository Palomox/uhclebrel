package events;

import java.time.Duration;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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
	int linea = 2;
	public EachSecondListener() {
	}

	@EventHandler
	public void onSecond(EachSecondEvent e) {
		if (UHCLebrel.instance.getJuego().getEstado().equals(GameStatuses.PLAYING)) {
			UHCLebrel.instance.getJuego().getEpisodio().menosUnSec();
			long s = UHCLebrel.instance.getJuego().getEpisodio().getDuracion().getSeconds();
			for(UHCTeam tmp : UHCLebrel.instance.getJuego().getEquipos().keySet()) {
				for(UHCPlayer mam : tmp.getMiembros().keySet()) {
					if(!mam.isDesconectado()){
					Scoreboard.updateScoreboard(mam,
					ChatColor.translateAlternateColorCodes('&', String.format("&e%02d:%02d", (s % 3600) / 60, (s % 60))),
					linea);
					}
				}
			}
			
			if (UHCLebrel.instance.getJuego().getEpisodio().getDuracion().equals(Duration.ZERO)) {
				Bukkit.getConsoleSender().sendMessage("Antes de dispararse el evento.");
				Bukkit.getPluginManager().callEvent(new PartChangeEvent(UHCLebrel.instance.getJuego().getEpisodio().getId() + 1));
			}
		}
	}
}
