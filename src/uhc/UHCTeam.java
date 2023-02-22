package uhc;

import java.util.HashMap;
import java.util.Set;

import org.bukkit.Location;

import chat.IChannel;
import chat.TeamChannel;
import main.UHCLebrel;
import util.UHCPlayer;

public class UHCTeam {
	private HashMap<UHCPlayer, Boolean> miembros = new HashMap<UHCPlayer, Boolean>();
	private String nombre;
	private int id;
	private IChannel channel;
	public UHCTeam(String nombre, int id) {
		this.nombre = nombre;
		channel = new TeamChannel(this, '^');
		this.id = id;
	}
	public void addMamerto(UHCPlayer add) {
		this.miembros.put(add, true);
	}
	public String getNombre() {
		return nombre;
	}
	public IChannel getChannel() {
		return this.channel;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public HashMap<UHCPlayer, Boolean> getMiembros(){
		return this.miembros;
	}
	public int getId() {
		return this.id;
	}
	public static UHCTeam getEquipoById(int id) {
		UHCLebrel plugin = UHCLebrel.instance;
		for(UHCTeam tmp : plugin.gameManager.getEquipos().keySet()) {
			int ac = tmp.getId();
			if(ac == id) {
				return tmp;
			}
		}
		return null;
	}
	public boolean compasVivos() {
		Set<UHCPlayer> nombres = this.miembros.keySet();
		for(UHCPlayer mam : nombres) {
			if(!mam.isMuerto()) {
				return true;
			}
		}
		return false;
	}
}
