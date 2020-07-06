package eventos;

import org.bukkit.event.Listener;

import main.Main;
import uhc.Episodio;
import uhc.EpisodioChangeEvent;
import util.Mamerto;
import util.Scoreboard;

public class CambiaEpisodio implements Listener{
	public void onCambioEpisodio(EpisodioChangeEvent e) {
		Main.instance.getJuego().setEpisodio(new Episodio(e.getNuevoEpisodio()));
		for(Mamerto mam : Main.instance.getHoPokePlayers()) {
			Scoreboard.updateScoreboard(mam.getPlayer(), "&e%uhc_episodio%", 4);	
		}
	}
}
