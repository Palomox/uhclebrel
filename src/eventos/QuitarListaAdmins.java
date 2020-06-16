package eventos;

import java.util.ArrayList;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import main.Main;
import util.HoPokePlayer;

public class QuitarListaAdmins implements Listener{
	private Main plugin;
	public QuitarListaAdmins(Main plugin) {
		this.plugin = plugin;
	}
	@EventHandler
	public void alAdminSalir(PlayerQuitEvent e) {
		Player jugador = e.getPlayer();
		if(jugador.hasPermission("hopoke.admin") || jugador.isOp()) {
			plugin.removeAdmin(jugador);
		}
		
		plugin.removeHPPlayer(HoPokePlayer.getHPPlayer(jugador, plugin));
	}
}
