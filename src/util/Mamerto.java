package util;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.destroystokyo.paper.profile.ProfileProperty;

import chat.IChannel;
import main.UHCLebrel;
import uhc.Equipo;

public class Mamerto {
	
	private boolean vanished;
	private LocalDate firstjoin;
	private String UUID;
	private IChannel escribiendo;
	private ArrayList<IChannel> leyendo;
	private double dinero;
	private Equipo team;
	private String displayname;
	private boolean desconectado;
	private boolean descalificado;
	private boolean espectador;
	private HashMap<Integer, String> scoreboard;
	ProfileProperty p;
	
	public Mamerto(String UUID, LocalDate firstjoin) {
		this.UUID = UUID;
		this.firstjoin = firstjoin;
		this.escribiendo = null;
		this.leyendo = new ArrayList<IChannel>();
		this.dinero = 0;
		this.desconectado = false;
		this.descalificado = false;
		this.espectador = false;
		this.scoreboard = new HashMap<Integer, String>();
	}
	
	public boolean isEspectador() {
		return espectador;
	}

	public void setEspectador(boolean espectador) {
		this.espectador = espectador;
	}

	public boolean isDescalificado() {
		return descalificado;
	}

	public void setDescalificado(boolean descalificado) {
		this.descalificado = descalificado;
	}

	public String getDisplayname() {
		return displayname;
	}
	

	public boolean isDesconectado() {
		return desconectado;
	}

	public void setDesconectado(boolean desconectado) {
		this.desconectado = desconectado;
	}

	public void setDisplayname(String displayname) {
		this.displayname = displayname;
	}

	public Equipo getTeam() {
		return team;
	}

	public void setTeam(Equipo team) {
		this.team = team;
	}

	public boolean estaLeyendo(IChannel c) {
		for (int i=0; i<leyendo.size(); i++) {
			IChannel tmp = leyendo.get(i);
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
	
	public ArrayList<IChannel> getLeyendo() {
		return leyendo;
	}
	public void setWritingChannel(IChannel channel) {
		this.escribiendo = channel;
	}
	public IChannel getWritingChannel() {
		return this.escribiendo;
	}
	public void addReadingChannel(IChannel channel) {
		if(!(this.estaLeyendo(channel))) {
			this.leyendo.add(channel);
		}
	}
	public void removeReadingChannel(IChannel channel) {
		for(int i =0; i<this.leyendo.size(); i++) {
			if(this.leyendo.get(i).equals(channel)) {
				this.leyendo.remove(i);
			}
		}
	}
	public static Mamerto getHPPlayer(Player jugador, UHCLebrel plugin) {
		for(int i=0; i<plugin.getHoPokePlayers().size(); i++) {
			Mamerto tmp = plugin.getHoPokePlayers().get(i);
			Player tmpp = tmp.getPlayer();
			if(tmpp.equals(jugador)) {
				return tmp;
			}
			}
		return null;
	}

	public HashMap<Integer, String> getScoreboard() {
		return scoreboard;
	}
	
}
