package eventos;

import java.util.Timer;
import java.util.TimerTask;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import chat.IChannel;
import fr.minuskube.netherboard.Netherboard;
import fr.minuskube.netherboard.bukkit.BPlayerBoard;
import main.Main;
import util.Mamerto;

public class DesconectarMamerto implements Listener{
	private Main plugin;
	public DesconectarMamerto(Main plugin) {
		this.plugin = plugin;
	}
	@EventHandler
	public void mamertoDesconecta(PlayerQuitEvent e) {
		Player jugador = e.getPlayer();
		if(jugador.hasPermission("hopoke.admin") || jugador.isOp()) {
			plugin.removeAdmin(jugador);
		}
		Mamerto hpplayer = Mamerto.getHPPlayer(jugador, plugin);
		TimerTask borrar = new TimerTask() {		
			@Override
			public void run() {
				if(hpplayer.isDesconectado()) {
				for(IChannel tmp : hpplayer.getLeyendo()) {
					tmp.getLectores().remove(hpplayer.getPlayer());
				}
				hpplayer.getTeam().getMiembros().put(hpplayer, false);
				Main.instance.removeHPPlayer(hpplayer);
				}
			}
		};
		Main.instance.matar.schedule(borrar, 300000);
		hpplayer.setDesconectado(true);
		/*
		 * Quitar la Scoreboard del jugador
		 */
		BPlayerBoard board = Netherboard.instance().getBoard(jugador);
		board.delete();
		
	}
}
