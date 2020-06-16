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
	public String getUUID() {
		return this.UUID;
	}
	public Player getPlayer() {
		Player jugador = Bukkit.getPlayer(this.UUID);
		return jugador;
	}
	public LocalDate getFJ() {
		return this.firstjoin;
	}
	public static HoPokePlayer getHPPlayer(Player jugador, Main plugin) {
		for(int i=0; i<plugin.getHoPokePlayers().size(); i++) {
			HoPokePlayer tmp = plugin.getHoPokePlayers().get(i);
			Player tmpp = tmp.getPlayer();
			if(tmpp == jugador) {
				return tmp;
			}
		}
		return null;
	}
	
}
