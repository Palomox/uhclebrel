package eventos;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

import main.Main;
import me.clip.placeholderapi.PlaceholderAPI;
import uhc.Episodio;
import uhc.EpisodioChangeEvent;
import util.Mamerto;
import util.Scoreboard;

public class CambiaEpisodio implements Listener{
	public CambiaEpisodio() {
		
	}
	@EventHandler
	public void onCambioEpisodio(EpisodioChangeEvent e) {
		Main.instance.getJuego().setEpisodio(new Episodio(e.getNuevoEpisodio()));
		Bukkit.getConsoleSender().sendMessage("Se dispara cambioEpisodio");
		for(Mamerto mam : Main.instance.getHoPokePlayers()) {
			Scoreboard.updateScoreboard(mam.getPlayer(), ChatColor.translateAlternateColorCodes('&', PlaceholderAPI.setPlaceholders(mam.getPlayer(), "&e%uhc_episodio%")), 4);	
			mam.getPlayer().sendTitle(ChatColor.translateAlternateColorCodes('&', "&6Comienza la parte "+e.getNuevoEpisodio()), null, 5, 20, 10);
		}
		
		/*
		 * Casos especificos de un episodio.
		 */
		switch(e.getNuevoEpisodio()) {
			case 10:
				Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&6[ANUNCIO] &5Ha comenzado la parte 10, todo el mundo ha de ir a 0, 0."));
				break;
			case 3:
				World main = Bukkit.getServer().getWorld("uhc");
				main.setPVP(true);
				break;
			default:
				break;
		}
	}
}
