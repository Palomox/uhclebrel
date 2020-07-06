package uhc;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import main.Main;
import util.Mamerto;

public class Juego {
	private HashMap<Equipo, Boolean> equipos = new HashMap<Equipo, Boolean>();
	private ArrayList<Mamerto> participantes = new ArrayList<Mamerto>();
	private ArrayList<Mamerto> muertos = new ArrayList<Mamerto>();
	private EstadosJuego estado;
	private Equipo ganador;
	private Episodio episodio;

	public Juego() {
		estado = EstadosJuego.ESPERANDO;
		ganador = null;
		
	}
	
	public Episodio getEpisodio() {
		return this.episodio;
	}
	
	public void setEpisodio(Episodio aponer) {
		this.episodio = aponer;
	}
	public Equipo getGanador() {
		return this.ganador;
	}

	public EstadosJuego getEstado() {
		return estado;
	}

	public void setEstado(EstadosJuego estado) {
		this.estado = estado;
	}

	public void addMammert(Mamerto tmp) {
		participantes.add(tmp);
	}

	public HashMap<Equipo, Boolean> getEquipos() {
		return equipos;
	}

	public void addTeam(Equipo team) {
		this.equipos.put(team, true);
	}

	public void removeTeam(Equipo team) {
		this.equipos.remove(team);
	}
	public void setSpectator(Mamerto aspect) {
		aspect.getPlayer().setAllowFlight(true);
		aspect.getPlayer().setFlying(true);
		Main.instance.getServer().getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable() {
			public void run() {
				aspect.getPlayer().spigot().respawn();
				aspect.getPlayer().setGameMode(GameMode.ADVENTURE);
				aspect.getPlayer().getInventory().clear();
				aspect.getPlayer().setFireTicks(0);
				aspect.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1, false, false));
				aspect.getPlayer().setInvulnerable(true);
			}
		}, 40);
	}
	public void matar(Mamerto desafortunado) {
		for (Mamerto tmp : participantes) {
			if (tmp.equals(desafortunado)) {
				participantes.remove(tmp);
				muertos.add(desafortunado);
				this.setSpectator(desafortunado);
				desafortunado.getTeam().getMiembros().put(desafortunado, false);
				if (!(desafortunado.getTeam().compasVivos())) {
					equipos.put(desafortunado.getTeam(), false);
					for (Mamerto all : Main.instance.getHoPokePlayers()) {
						all.getPlayer().sendTitle(
								ChatColor.translateAlternateColorCodes('&', "&4&l¡El equipo "
										+ desafortunado.getTeam().getNombre() + " ha sido eliminado!"),
								null, 10, 20, 10);
					}
				}
				break;
			}
		}
	}

	public void setGanador(Equipo equipo) {
		this.ganador = equipo;
	}
}
