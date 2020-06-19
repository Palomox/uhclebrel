package eventos;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import chat.Channel;
import fr.minuskube.netherboard.Netherboard;
import fr.minuskube.netherboard.bukkit.BPlayerBoard;
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
		HoPokePlayer hpplayer = HoPokePlayer.getHPPlayer(jugador, plugin);
		for(int i=0; i<hpplayer.getLeyendo().size(); i++) {
			Channel tmp = hpplayer.getLeyendo().get(i);
			tmp.getLectores().remove(hpplayer.getPlayer());
		}
		plugin.removeHPPlayer(hpplayer);
		
		/*
		 * Quitar la Scoreboard del jugador
		 */
		BPlayerBoard board = Netherboard.instance().getBoard(jugador);
		board.delete();
		
	}
}
