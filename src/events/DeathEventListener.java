package events;

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
import uhc.UHCTeam;
import uhc.StatusChangeEvent;
import uhc.GameStatuses;
import util.UHCPlayer;

public class DeathEventListener implements Listener {
	public DeathEventListener() {

	}

	@EventHandler
	public void alMurison(PlayerDeathEvent e) {
		if (UHCLebrel.instance.getGameManager().getEstado().equals(GameStatuses.PLAYING)) {
			Player muerto = e.getEntity();
			UHCPlayer mamerto = UHCLebrel.instance.getUHCPlayerByName(muerto.getName());
			ArrayList<UHCPlayer> jugadores = UHCLebrel.instance.getUHCPlayers();
			for (UHCPlayer tmp : jugadores) {
				tmp.getPlayer().sendTitle(
						ChatColor.translateAlternateColorCodes('&', "&4¡" + muerto.getName() + " ha sido eliminado!"),
						null, 10, 20, 5);
			}
			UHCLebrel.instance.gameManager.matar(mamerto);
			ArrayList<UHCTeam> vivos = new ArrayList<UHCTeam>();
			for (UHCTeam tmp : UHCLebrel.instance.gameManager.getEquipos().keySet()) {
				boolean stat = UHCLebrel.instance.gameManager.getEquipos().get(tmp);
				if (stat) {
					vivos.add(tmp);
				}
			}
			if (vivos.size() <= 1) {
				// Tenemos un ganador bbs
				UHCLebrel.instance.getGameManager().setEstado(GameStatuses.FINISHING);
				UHCLebrel.instance.getGameManager().setGanador(vivos.get(0));
				Bukkit.getPluginManager().callEvent(new StatusChangeEvent(GameStatuses.FINISHING));
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
