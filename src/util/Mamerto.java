package util;

import java.time.LocalDate;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import chat.NChannel;
import main.Main;

public class Mamerto {
	/*private Main plugin;
	public HoPokePlayer(Main plugin) {
		this.plugin = plugin;
	}*/
	private boolean vanished;
	private LocalDate firstjoin;
	private String UUID;
	private NChannel escribiendo;
	private ArrayList<NChannel> leyendo;
	private double dinero;
	
	
	public Mamerto(String UUID, LocalDate firstjoin) {
		this.UUID = UUID;
		this.firstjoin = firstjoin;
		this.escribiendo = null;
		this.leyendo = new ArrayList<NChannel>();
		this.dinero = 0;
	}
	
	public boolean estaLeyendo(NChannel c) {
		for (int i=0; i<leyendo.size(); i++) {
			NChannel tmp = leyendo.get(i);
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
	
	public ArrayList<NChannel> getLeyendo() {
		return leyendo;
	}
	public void setWritingChannel(NChannel channel) {
		this.escribiendo = channel;
	}
	public NChannel getWritingChannel() {
		return this.escribiendo;
	}
	public void addReadingChannel(NChannel channel) {
		if(!(this.estaLeyendo(channel))) {
			this.leyendo.add(channel);
		}
	}
	public void removeReadingChannel(NChannel channel) {
		for(int i =0; i<this.leyendo.size(); i++) {
			if(this.leyendo.get(i).equals(channel)) {
				this.leyendo.remove(i);
			}
		}
	}
	public static Mamerto getHPPlayer(Player jugador, Main plugin) {
		for(int i=0; i<plugin.getHoPokePlayers().size(); i++) {
			Mamerto tmp = plugin.getHoPokePlayers().get(i);
			Player tmpp = tmp.getPlayer();
			if(tmpp.equals(jugador)) {
				return tmp;
			}
			}
		return null;
	}
	
}
