package eventos;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import main.Main;
import util.HoPokePlayer;

public class InitialChannel implements Listener{
	private Main plugin;
	public InitialChannel(Main plugin) {
		this.plugin = plugin;
	}
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		Player jugador = e.getPlayer();
		//Canal por defecto
		HoPokePlayer pl = HoPokePlayer.getHPPlayer(jugador, plugin);
		if(jugador.hasPermission("hopoke.admin")) {
			//Staff
			pl.addReadingChannel(
					plugin.getCanales()
					.get(0));
		}else {
			//Global
			pl.addReadingChannel(plugin.getCanales().get(1));
		}
		pl.setWritingChannel(plugin.getCanales().get(1));
		Bukkit.getConsoleSender().sendMessage(pl.getPlayer().getName());		
	}
}
