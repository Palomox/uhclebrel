package uhc;

import java.util.ArrayList;

import org.bukkit.GameMode;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import util.Mamerto;

public class Juego {
	private ArrayList<Equipo> equipos = new ArrayList<Equipo>();
	private ArrayList<Mamerto> participantes = new ArrayList<Mamerto>();
	private ArrayList<Mamerto> muertos = new ArrayList<Mamerto>();
	
	public Juego() {
		
	}
	public void addMammert(Mamerto tmp) {
		participantes.add(tmp);
	}
	public ArrayList<Equipo> getEquipos() {
		return equipos;
	}

	public void addTeam(Equipo team) {
		this.equipos.add(team);
	}
	public void removeTeam(Equipo team) {
		this.equipos.remove(team);
	}
	public void matar(Mamerto desafortunado) {
		for(Mamerto tmp : participantes) {
			if(tmp.equals(desafortunado)) {
				participantes.remove(tmp);
				muertos.add(desafortunado);
				desafortunado.getPlayer().setAllowFlight(true);
				desafortunado.getPlayer().setFlying(true);
				desafortunado.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1, false, false));
				break;
			}
		}
	}
}
