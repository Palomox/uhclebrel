package eventos;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import main.Main;
import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.ChatColor;
import uhc.EstadoChangeEvent;
import util.Mamerto;
import util.Scoreboard;

public class CambiaEstado implements Listener{
	
	public CambiaEstado() {
		
	}
	@EventHandler
	public void onJuegoFinalizado(EstadoChangeEvent e) {
		Main plugin = Main.instance;
		switch(e.getNuevoestado()) {
		case FINALIZADO:
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
			for(Mamerto temp : Main.instance.juego.getParticipantes()) {
				temp.setDisplayname(ChatColor.translateAlternateColorCodes('&', "&k"+temp.getPlayer().getName()));
				temp.getPlayer().setDisplayName(temp.getDisplayname());
				temp.getPlayer().setPlayerListName(temp.getDisplayname());
				
			}
			break;
		default:
			break;
		}
	}
}
