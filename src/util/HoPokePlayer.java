package util;

import java.time.LocalDate;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import chat.Channel;
import main.Main;

public class HoPokePlayer {
	/*private Main plugin;
	public HoPokePlayer(Main plugin) {
		this.plugin = plugin;
	}*/
	private boolean vanished;
	private LocalDate firstjoin;
	private String UUID;
	private Channel escribiendo;
	private ArrayList<Channel> leyendo;
	private double dinero;
	
	
	public HoPokePlayer(String UUID, LocalDate firstjoin) {
		this.UUID = UUID;
		this.firstjoin = firstjoin;
		this.escribiendo = null;
		this.leyendo = new ArrayList<Channel>();
		this.dinero = 0;
	}
	
	public boolean estaLeyendo(Channel c) {
		for (int i=0; i<leyendo.size(); i++) {
			Channel tmp = leyendo.get(i);
			if(tmp.equals(c)) {
				return true;
			}
		}
		return false;
	}

	public String getUUID() {
		return this.UUID;
	}
	public Player getPlayer() {
		Player jugador = Bukkit.getPlayer(java.util.UUID.fromString(UUID));
		return jugador;
	}
	public void setDinero(double dinero) {
		this.dinero = dinero;
	}
	public void addDinero(double dinero) {
		this.dinero = this.dinero + dinero;
	}
	public void removeDinero(double aremover) {
		this.dinero = this.dinero - dinero;
	}
	public double getDinero() {
		return this.dinero;
	}
	public LocalDate getFJ() {
		return this.firstjoin;
	}
	
	public ArrayList<Channel> getLeyendo() {
		return leyendo;
	}
	public void setWritingChannel(Channel channel) {
		this.escribiendo = channel;
	}
	public Channel getWritingChannel() {
		return this.escribiendo;
	}
	public void addReadingChannel(Channel channel) {
		if(!(this.estaLeyendo(channel))) {
			this.leyendo.add(channel);
		}
	}
	public void removeReadingChannel(Channel channel) {
		for(int i =0; i<this.leyendo.size(); i++) {
			if(this.leyendo.get(i).equals(channel)) {
				this.leyendo.remove(i);
			}
		}
	}
	public static HoPokePlayer getHPPlayer(Player jugador, Main plugin) {
		for(int i=0; i<plugin.getHoPokePlayers().size(); i++) {
			HoPokePlayer tmp = plugin.getHoPokePlayers().get(i);
			Player tmpp = tmp.getPlayer();
			if(tmpp.equals(jugador)) {
				return tmp;
			}
			}
		return null;
	}
	
}
