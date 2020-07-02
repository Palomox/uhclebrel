package uhc;

import java.util.ArrayList;

import chat.IChannel;
import chat.TeamChannel;
import main.Main;
import util.Mamerto;

public class Equipo {
	private ArrayList<Mamerto> miembros = new ArrayList<Mamerto>();
	private String nombre;
	private int id;
	private IChannel channel;
	public Equipo(String nombre, int id) {
		this.nombre = nombre;
		channel = new TeamChannel();
		this.id = id;
	}
	public void addMamerto(Mamerto add) {
		this.miembros.add(add);
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public ArrayList<Mamerto> getMiembros(){
		return this.miembros;
	}
	public int getId() {
		return this.id;
	}
	public static Equipo getEquipoById(int id) {
		Main plugin = Main.instance;
		for(Equipo tmp : plugin.juego.getEquipos()) {
			int ac = tmp.getId();
			if(ac == id) {
				return tmp;
			}
		}
		return null;
	}
}
