package eventos;

import java.sql.ResultSet;
import java.sql.SQLException;
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
		//Comprueba si es admin, asi le añade al arraylist de admins.
		if(jugador.isOp() || jugador.hasPermission("hopoke.admin")) {
			plugin.getAdmins().add(jugador);
		}
		//Comprueba si es nuevo o no.
		try {
		if(plugin.consulta("SELECT * FROM "+plugin.getDBPrefix()+"usuarios WHERE UUID = '"+jugador.getUniqueId()+"'").next()) {
			//Jugador es nuevo
			Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&4¡Es la primera vez que "+jugador.getName()+" se une a HoPoke!"));
			LocalDate fj = LocalDate.now();
			HoPokePlayer hpp = new HoPokePlayer(jugador.getUniqueId().toString(), fj);
			plugin.getHoPokePlayers().add(hpp);
			plugin.query("INSERT INTO "+plugin.getDBPrefix()+"usuarios VALUES ('"+jugador.getUniqueId()+"', '"+fj.toString()+"')");
		}else {
			//Ya hay registros nerd
			ResultSet rs = plugin.consulta("SELECT * FROM "+plugin.getDBPrefix()+"usuarios WHERE UUID = '"+jugador.getUniqueId()+"'");
			rs.next();
			String uuid = rs.getString("UUID");
			LocalDate fj2 = LocalDate.parse(rs.getString("primeraunion"));
			HoPokePlayer hppo = new HoPokePlayer(uuid, fj2);
			plugin.getHoPokePlayers().add(hppo);
		}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		
		
		
		/*if(HoPokePlayer.getHPPlayer(jugador, plugin) !=null) {
			
		}else {
			//Jugador es nuevo.
			Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&4¡Es la primera vez que "+jugador.getName()+" se une a HoPoke!"));
			HoPokePlayer hpplayer = new HoPokePlayer(jugador.getUniqueId().toString(), LocalDate.now());
			plugin.addPlayer(hpplayer);
			plugin.query("INSERT INTO "+plugin.getDBPrefix()+"usuarios (UUID, primeraunion) VALUES ('"+hpplayer.getUUID()+"', '"+hpplayer.getFJ()+"')");
		}*/
	}

}
