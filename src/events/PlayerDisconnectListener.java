package events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;

import chat.IChannel;
import main.UHCLebrel;
import util.UHCPlayer;

public class PlayerDisconnectListener implements Listener{
	private UHCLebrel plugin;
	public PlayerDisconnectListener(UHCLebrel plugin) {
		this.plugin = plugin;
	}
	@EventHandler
	public void mamertoDesconecta(PlayerQuitEvent e) {
		Player jugador = e.getPlayer();
		UHCPlayer hpplayer = UHCPlayer.getHPPlayer(jugador, plugin);
		BukkitRunnable borrar = new BukkitRunnable() {
			@Override
			public void run() {
				if(hpplayer.isDesconectado()) {
				for(IChannel tmp : hpplayer.getLeyendo()) {
					tmp.getChannelReaders().remove(hpplayer.getPlayer());
				}
				hpplayer.getTeam().getMiembros().put(hpplayer, false);
				UHCLebrel.instance.removeUHCPlayer(hpplayer);
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
