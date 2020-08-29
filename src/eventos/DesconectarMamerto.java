package eventos;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;

import chat.IChannel;
import main.UHCLebrel;
import util.Mamerto;

public class DesconectarMamerto implements Listener{
	private UHCLebrel plugin;
	public DesconectarMamerto(UHCLebrel plugin) {
		this.plugin = plugin;
	}
	@EventHandler
	public void mamertoDesconecta(PlayerQuitEvent e) {
		Player jugador = e.getPlayer();
		if(jugador.hasPermission("hopoke.admin") || jugador.isOp()) {
			plugin.removeAdmin(jugador);
		}
		Mamerto hpplayer = Mamerto.getHPPlayer(jugador, plugin);
		BukkitRunnable borrar = new BukkitRunnable() {		
			@Override
			public void run() {
				if(hpplayer.isDesconectado()) {
				for(IChannel tmp : hpplayer.getLeyendo()) {
					tmp.getLectores().remove(hpplayer.getPlayer());
				}
				hpplayer.getTeam().getMiembros().put(hpplayer, false);
				UHCLebrel.instance.removeHPPlayer(hpplayer);
				}
			}
		};
		borrar.runTaskLater(UHCLebrel.instance, 6000);
		hpplayer.setDesconectado(true);
		/*
		 * Quitar la Scoreboard del jugador
		 */
		hpplayer.getPlayer().getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
		
	}
}
