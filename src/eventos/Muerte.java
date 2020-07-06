package eventos;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import main.Main;
import uhc.Equipo;
import uhc.EstadoChangeEvent;
import uhc.EstadosJuego;
import util.Mamerto;

public class Muerte implements Listener{
	public Muerte() {
		
	}
	
	@EventHandler
	public void alMurison(PlayerDeathEvent e) {
		Player muerto = e.getEntity();
		Mamerto mamerto = Main.instance.getHPByName(muerto.getName());
		ArrayList<Mamerto> jugadores = Main.instance.getHoPokePlayers();
		Bukkit.getConsoleSender().sendMessage("murision disparada");
		for(Mamerto tmp : jugadores) {
			tmp.getPlayer().sendTitle(ChatColor.translateAlternateColorCodes('&', "&4¡"+muerto.getName()+" ha sido eliminado!"), null, 10, 20, 5);
		}
		Main.instance.juego.matar(mamerto);
		ArrayList<Equipo> vivos = new ArrayList<Equipo>();
		for(Equipo tmp : Main.instance.juego.getEquipos().keySet()) {
			boolean stat = Main.instance.juego.getEquipos().get(tmp);
			if (stat) {
				vivos.add(tmp);
			}
		}
		if(vivos.size()<=1) {
			//Tenemos un ganador bbs
			Main.instance.getJuego().setEstado(EstadosJuego.FINALIZADO);
			Main.instance.getJuego().setGanador(Main.instance.getHoPokePlayers().get(0).getTeam());
			Bukkit.getPluginManager().callEvent(new EstadoChangeEvent(EstadosJuego.FINALIZADO));
		}
		}
}
