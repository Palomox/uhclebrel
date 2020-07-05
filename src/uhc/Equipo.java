package uhc;

import java.util.HashMap;
import java.util.Set;

import chat.IChannel;
import chat.TeamChannel;
import main.Main;
import util.Mamerto;

public class Equipo {
	private HashMap<Mamerto, Boolean> miembros = new HashMap<Mamerto, Boolean>();
	private String nombre;
	private int id;
	private IChannel channel;
	public Equipo(String nombre, int id) {
		this.nombre = nombre;
		channel = new TeamChannel();
		this.id = id;
	}
	public void addMamerto(Mamerto add) {
		this.miembros.put(add, true);
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public HashMap<Mamerto, Boolean> getMiembros(){
		return this.miembros;
	}
	public int getId() {
		return this.id;
	}
	public static Equipo getEquipoById(int id) {
		Main plugin = Main.instance;
		for(Equipo tmp : plugin.juego.getEquipos().keySet()) {
			int ac = tmp.getId();
			if(ac == id) {
				return tmp;
			}
		}
		return null;
	}
	public boolean compasVivos() {
		Set<Mamerto> nombres = this.miembros.keySet();
		for(Mamerto mam : nombres) {
			boolean vivo = this.miembros.get(mam);
			if(!vivo) {
				return false;
			}
		}
		return true;
	}
}
