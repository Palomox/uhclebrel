package eventos;

import java.time.Duration;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import main.Main;
import uhc.EpisodioChangeEvent;
import uhc.Equipo;
import uhc.EstadosJuego;
import uhc.SecondEvent;
import util.Mamerto;
import util.Scoreboard;

public class CadaSegundo implements Listener {

	public CadaSegundo() {
	}

	@EventHandler
	public void onSecond(SecondEvent e) {
		if (Main.instance.getJuego().getEstado().equals(EstadosJuego.JUGANDO)) {
			Main.instance.getJuego().getEpisodio().menosUnSec();
			long s = Main.instance.getJuego().getEpisodio().getDuracion().getSeconds();
			for(Equipo tmp : Main.instance.getJuego().getEquipos().keySet()) {
				for(Mamerto mam : tmp.getMiembros().keySet()) {
					Scoreboard.updateScoreboard(mam.getPlayer(),
					ChatColor.translateAlternateColorCodes('&', String.format("&e%02d:%02d", (s % 3600) / 60, (s % 60))),
					2);
				}
			}
			
			if (Main.instance.getJuego().getEpisodio().getDuracion().equals(Duration.ZERO)) {
				Bukkit.getConsoleSender().sendMessage("Antes de dispararse el evento.");
				Bukkit.getPluginManager().callEvent(new EpisodioChangeEvent(Main.instance.getJuego().getEpisodio().getId() + 1));
			}
		}
	}
}
