package eventos;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import main.Main;
import uhc.EpisodioChangeEvent;
import uhc.SecondEvent;
import util.Scoreboard;

public class CadaSegundo implements Listener{
	
	public CadaSegundo() {
	}
	
	@EventHandler
	public void onSecond(SecondEvent e) {
		Main.instance.getJuego().getEpisodio().menosUnSec();
		Scoreboard.updateScoreboard(Main.instance.getHoPokePlayers().get(1).getPlayer(), Main.instance.getJuego().getEpisodio().getDuracion().toString()+", "+Main.instance.getJuego().getEpisodio().getId(), 5);
		if(Main.instance.getJuego().getEpisodio().getDuracion().isZero()) {
			Bukkit.getPluginManager().callEvent(new EpisodioChangeEvent(Main.instance.getJuego().getEpisodio().getId()+1));
		}
	}
}
