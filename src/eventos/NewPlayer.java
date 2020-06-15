package eventos;

import java.time.LocalDate;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import main.Main;
import util.HoPokePlayer;

public class NewPlayer implements Listener{
	private Main plugin;
	public NewPlayer(Main plugin) {
		this.plugin = plugin;
	}
	@EventHandler
	public void alUnirServidor(PlayerJoinEvent event) {
		Player jugador = event.getPlayer();
		if(jugador.isOp() || jugador.hasPermission("hopoke.admin")) {
			plugin.getAdmins().add(jugador);
		}
		if(HoPokePlayer.getHPPlayer(jugador, plugin) !=null) {
			
		}else {
			//Jugador es nuevo.
			Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&4¡Es la primera vez que "+jugador.getName()+" se une a HoPoke!"));
			HoPokePlayer hpplayer = new HoPokePlayer(jugador.getUniqueId().toString(), LocalDate.now());
			plugin.addPlayer(hpplayer);
			plugin.query("INSERT INTO "+plugin.getDBPrefix()+"usuarios VALUES ("+hpplayer.getPlayer().getUniqueId()+", "+hpplayer.getFJ()+")");
		}
	}

}
