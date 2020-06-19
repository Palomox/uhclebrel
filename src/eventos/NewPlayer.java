package eventos;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import chat.Channel;
import fr.minuskube.netherboard.Netherboard;
import fr.minuskube.netherboard.bukkit.BPlayerBoard;
import main.Main;
import me.clip.placeholderapi.PlaceholderAPI;
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
			ResultSet rs = plugin.consulta("SELECT * FROM "+plugin.getDBPrefix()+"usuarios WHERE UUID = '"+jugador.getUniqueId()+"'");
		if(!(rs.next())) {
			//Jugador es nuevo
			Bukkit.getConsoleSender().sendMessage("SELECT * FROM "+plugin.getDBPrefix()+"usuarios WHERE UUID = '"+jugador.getUniqueId()+"'");
			Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&4¡Es la primera vez que "+jugador.getName()+" se une a HoPoke!"));
			LocalDate fj = LocalDate.now();
			HoPokePlayer hpp = new HoPokePlayer(jugador.getUniqueId().toString(), fj);
			plugin.getHoPokePlayers().add(hpp);
			plugin.getEcon().createPlayerAccount(hpp.getPlayer().getPlayer());
			plugin.query("INSERT INTO "+plugin.getDBPrefix()+"usuarios VALUES ('"+jugador.getUniqueId()+"', '"+fj.toString()+"')");
		}else {
			//Ya hay registros nerd
			String uuid = rs.getString("UUID");
			LocalDate fj2 = LocalDate.parse(rs.getString("primeraunion"));
			HoPokePlayer hppo = new HoPokePlayer(uuid, fj2);
			double dinero = rs.getDouble("money"); 
			hppo.setDinero(dinero);
			plugin.getHoPokePlayers().add(hppo);
		}
		}catch(SQLException e) {
			Bukkit.getConsoleSender().sendMessage("Sqle en el guey este");
			e.printStackTrace();
		}
		//Canal por defecto
		HoPokePlayer pl = HoPokePlayer.getHPPlayer(jugador, plugin);
		for(String disc : plugin.getConfig().getConfigurationSection("chat.channels").getKeys(false)) {
			String perm = plugin.getConfig().getString("chat.channels."+disc+".autojoinperm");
			Channel tmp = plugin.getChannelByName(plugin.getConfig().getString("chat.channels."+disc+".name"));
			if(perm.equalsIgnoreCase("none")) {
				pl.addReadingChannel(tmp);
				if(tmp.addLector(pl.getPlayer())) {
					jugador.getPlayer().sendMessage(ChatColor.DARK_GREEN+"Ahora también lees "+tmp.getName());
				}else {
					jugador.getPlayer().sendMessage(ChatColor.DARK_RED+"No puedes leer "+tmp.getName()+" porque ya lo estas leyendo!");
				}			
			}
			if(pl.getPlayer().hasPermission(perm)) {
				pl.addReadingChannel(tmp);
				if(tmp.addLector(pl.getPlayer())) {
					jugador.getPlayer().sendMessage(ChatColor.DARK_GREEN+"Ahora también lees "+tmp.getName());
				}else {
					jugador.getPlayer().sendMessage(ChatColor.DARK_RED+"No puedes leer "+tmp.getName()+" porque ya lo estas leyendo!");
				}			}
		}
		Channel wr = plugin.getChannelByName(plugin.getConfig().getString("chat.defaultwritingchannel"));
		pl.setWritingChannel(wr);		
		/*
		 * Creacion de la Scoreboard
		 */
		BPlayerBoard board = Netherboard.instance().createBoard(pl.getPlayer(), "Main Scoreboard");
		board.setName(ChatColor.translateAlternateColorCodes('&', "&4&lHoPoke"));
		int i=1;
		for(String linea : plugin.getConfig().getConfigurationSection("scoreboard.lines").getKeys(false)) {
			String texto = plugin.getConfig().getString("scoreboard.lines."+linea);
			texto = PlaceholderAPI.setPlaceholders(pl.getPlayer(), texto);
			board.set(texto, i);
			i++;
		}	
	}
}