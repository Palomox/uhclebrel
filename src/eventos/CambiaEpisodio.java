package eventos;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import main.UHCLebrel;
import me.clip.placeholderapi.PlaceholderAPI;
import skinsrestorer.shared.exception.SkinRequestException;
import uhc.Episodio;
import uhc.EpisodioChangeEvent;
import util.Mamerto;
import util.Scoreboard;

public class CambiaEpisodio implements Listener{
	public CambiaEpisodio() {
		
	}
	@EventHandler
	public void onCambioEpisodio(EpisodioChangeEvent e) {
		UHCLebrel.instance.getJuego().setEpisodio(new Episodio(e.getNuevoEpisodio()));
		Bukkit.getConsoleSender().sendMessage("Se dispara cambioEpisodio");
		for(Mamerto mam : UHCLebrel.instance.getHoPokePlayers()) {
			Scoreboard.updateScoreboard(mam, ChatColor.translateAlternateColorCodes('&', PlaceholderAPI.setPlaceholders(mam.getPlayer(), "&e%uhc_episodio%")), 4);	
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
				World ovw = Bukkit.getWorld("uhc");
				World net = Bukkit.getWorld("uhc_nether");
				World end = Bukkit.getWorld("uhc_the_end");
				ovw.setPVP(false);
				net.setPVP(false);
				end.setPVP(false);
				Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&6[ANUNCIO] &5Se ha acabado el pacto de caballeros, &4&lPVP ACTIVADO A PARTIR DE ESTE MOMENTO."));
				break;
			default:
				break;
		}
		
		/*
		 * Resetear Skins.
		 */
		ArrayList<String> nombres = new ArrayList<String>();
		for(Mamerto jugador : UHCLebrel.instance.juego.getParticipantes()) {
			nombres.add(jugador.getPlayer().getName());
		}
		Random random = new Random();
		for(Mamerto vict : UHCLebrel.instance.juego.getParticipantes()) {
			int tmp = random.nextInt(nombres.size()-1);
			while(vict.getPlayer().getName().equals(nombres.get(tmp))) {
				tmp = random.nextInt(nombres.size()-1);
			}
			try {
				UHCLebrel.instance.sapi.setSkin(vict.getPlayer().getName(), nombres.get(tmp));
				UHCLebrel.instance.sapi.applySkin(vict.getPlayer());
			} catch (SkinRequestException e1) {
				e1.printStackTrace();
			}
			vict.getPlayer().sendMessage(ChatColor.DARK_GREEN+"Ahora tienes la Skin de "+nombres.get(tmp));
			nombres.remove(tmp);
		}
		
	}	
}
