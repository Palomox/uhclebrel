package eventos;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.block.Skull;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import main.UHCLebrel;
import uhc.Equipo;
import uhc.EstadoChangeEvent;
import uhc.EstadosJuego;
import util.Mamerto;

public class Muerte implements Listener {
	public Muerte() {

	}

	@EventHandler
	public void alMurison(PlayerDeathEvent e) {
		if (UHCLebrel.instance.getJuego().getEstado().equals(EstadosJuego.JUGANDO)) {
			Player muerto = e.getEntity();
			Mamerto mamerto = UHCLebrel.instance.getHPByName(muerto.getName());
			ArrayList<Mamerto> jugadores = UHCLebrel.instance.getHoPokePlayers();
			for (Mamerto tmp : jugadores) {
				tmp.getPlayer().sendTitle(
						ChatColor.translateAlternateColorCodes('&', "&4¡" + muerto.getName() + " ha sido eliminado!"),
						null, 10, 20, 5);
			}
			UHCLebrel.instance.juego.matar(mamerto);
			ArrayList<Equipo> vivos = new ArrayList<Equipo>();
			for (Equipo tmp : UHCLebrel.instance.juego.getEquipos().keySet()) {
				boolean stat = UHCLebrel.instance.juego.getEquipos().get(tmp);
				if (stat) {
					vivos.add(tmp);
				}
			}
			if (vivos.size() <= 1) {
				// Tenemos un ganador bbs
				UHCLebrel.instance.getJuego().setEstado(EstadosJuego.FINALIZADO);
				UHCLebrel.instance.getJuego().setGanador(vivos.get(0));
				Bukkit.getPluginManager().callEvent(new EstadoChangeEvent(EstadosJuego.FINALIZADO));
			}
			
			/*
			 * Poner cabeza
			 */
			if(!mamerto.isDescalificado()) {
			Location cabeza = muerto.getLocation();
			cabeza.setY(cabeza.getY()+2);
			cabeza.getBlock().setType(Material.PLAYER_HEAD);
			BlockState estadocabeza = cabeza.getBlock().getState();
			Skull cabezask = (Skull) estadocabeza;
			cabezask.setOwningPlayer(muerto.getPlayer());
			cabezask.update();
			Location valla = cabeza.clone();
			valla.setY(cabeza.getY()-1);
			valla.getBlock().setType(Material.NETHER_BRICK_FENCE);
			}
		}
	}
}
