package util;

import java.time.LocalDate;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import main.Main;

public class HoPokePlayer {
	/*private Main plugin;
	public HoPokePlayer(Main plugin) {
		this.plugin = plugin;
	}*/
	private boolean vanished;
	private LocalDate firstjoin;
	private String UUID;
	
	
	public HoPokePlayer(String UUID, LocalDate firstjoin) {
		this.UUID = UUID;
		this.firstjoin = firstjoin;
	}
	
	public Player getPlayer() {
		Player jugador = Bukkit.getPlayer(this.UUID);
		return jugador;
	}
	public LocalDate getFJ() {
		return this.firstjoin;
	}
	public static HoPokePlayer getHPPlayer(Player jugador, Main plugin) {
		if(plugin.getHoPokePlayers().size() == 0) {
			return null;
		}else {
		for(int i=0; i<=plugin.getHoPokePlayers().size(); i++) {
			
			Player acomparar = plugin.getHoPokePlayers().get(i).getPlayer();
			if(jugador == acomparar) {
				return plugin.getHoPokePlayers().get(i);
			}
			
		}
		}
		return null;
		
		
	}
	
}
