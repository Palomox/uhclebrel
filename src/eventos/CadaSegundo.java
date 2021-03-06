package eventos;

import java.time.Duration;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import main.UHCLebrel;
import uhc.EpisodioChangeEvent;
import uhc.Equipo;
import uhc.EstadosJuego;
import uhc.SecondEvent;
import util.Mamerto;
import util.Scoreboard;

public class CadaSegundo implements Listener {
	int linea = 2;
	public CadaSegundo() {
	}

	@EventHandler
	public void onSecond(SecondEvent e) {
		if (UHCLebrel.instance.getJuego().getEstado().equals(EstadosJuego.JUGANDO)) {
			UHCLebrel.instance.getJuego().getEpisodio().menosUnSec();
			long s = UHCLebrel.instance.getJuego().getEpisodio().getDuracion().getSeconds();
			for(Equipo tmp : UHCLebrel.instance.getJuego().getEquipos().keySet()) {
				for(Mamerto mam : tmp.getMiembros().keySet()) {
					if(!mam.isDesconectado()){
					Scoreboard.updateScoreboard(mam,
					ChatColor.translateAlternateColorCodes('&', String.format("&e%02d:%02d", (s % 3600) / 60, (s % 60))),
					linea);
					}
				}
			}
			
			if (UHCLebrel.instance.getJuego().getEpisodio().getDuracion().equals(Duration.ZERO)) {
				Bukkit.getConsoleSender().sendMessage("Antes de dispararse el evento.");
				Bukkit.getPluginManager().callEvent(new EpisodioChangeEvent(UHCLebrel.instance.getJuego().getEpisodio().getId() + 1));
			}
		}
	}
}
