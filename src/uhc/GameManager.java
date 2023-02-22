package uhc;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import main.UHCLebrel;
import util.UHCPlayer;

public class GameManager {
	private HashMap<UHCTeam, Boolean> equipos = new HashMap<UHCTeam, Boolean>();
	private ArrayList<UHCPlayer> participantes = new ArrayList<UHCPlayer>();
	private GameStatuses estado;
	private UHCTeam ganador;
	private UHCPart episodio;

	public GameManager() {
		estado = GameStatuses.WAITING;
		ganador = null;

	}

	public UHCPart getEpisodio() {
		return this.episodio;
	}
	public void setEpisodio(UHCPart aponer) {
		this.episodio = aponer;
	}
	public UHCTeam getGanador() {
		return this.ganador;
	}

	public GameStatuses getEstado() {
		return estado;
	}

	public void setEstado(GameStatuses estado) {
		this.estado = estado;
	}

	public void addMammert(UHCPlayer tmp) {
		participantes.add(tmp);
	}

	public HashMap<UHCTeam, Boolean> getEquipos() {
		return equipos;
	}

	public ArrayList<UHCPlayer> getParticipantes() {
		return participantes;
	}

	public void addTeam(UHCTeam team) {
		this.equipos.put(team, true);
	}

	public void removeTeam(UHCTeam team) {
		this.equipos.remove(team);
	}
	public void setSpectator(UHCPlayer aspect) {
		UHCLebrel.instance.getServer().getScheduler().scheduleSyncDelayedTask(UHCLebrel.instance, new Runnable() {
			public void run() {
				aspect.getPlayer().spigot().respawn();
				aspect.getPlayer().setCanPickupItems(false);
				aspect.getPlayer().setGameMode(GameMode.ADVENTURE);
				aspect.getPlayer().getInventory().clear();
				aspect.getPlayer().setFireTicks(0);
				aspect.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1, false, false));
				aspect.getPlayer().setInvulnerable(true);
				aspect.getPlayer().setDisplayName(aspect.getPlayer().getName());
				aspect.setDisplayname(aspect.getPlayer().getDisplayName());
				aspect.getPlayer().setPlayerListName(aspect.getPlayer().getDisplayName());
				aspect.setEspectador(true);
				aspect.getPlayer().setAllowFlight(true);
				aspect.getPlayer().setFlying(true);
			}
		}, 10);
	}
	public void matar(UHCPlayer desafortunado) {
		for (UHCPlayer tmp : participantes) {
			if (tmp.equals(desafortunado)) {
				participantes.remove(tmp);
				desafortunado.setMuerto(true);
				this.setSpectator(desafortunado);
				desafortunado.getTeam().getMiembros().put(desafortunado, false);
				if (!(desafortunado.getTeam().compasVivos())) {
					equipos.put(desafortunado.getTeam(), false);
					for (UHCPlayer all : UHCLebrel.instance.getUHCPlayers()) {
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

	public void setGanador(UHCTeam equipo) {
		this.ganador = equipo;
	}
}
