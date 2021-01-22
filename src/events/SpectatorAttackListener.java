package events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import main.UHCLebrel;
import util.UHCPlayer;

public class SpectatorAttackListener implements Listener{
	public SpectatorAttackListener() {
	}
	
	@EventHandler
	public void onAtaque(EntityDamageByEntityEvent e) {
		if(e.getDamager() instanceof Player) {
			Player atacante = (Player) e.getDamager();
			for(UHCPlayer mam : UHCLebrel.instance.getGameManager().getMuertos()) {
				if(atacante.equals(mam.getPlayer())) {
					e.setCancelled(true);
				}
			}
		}
	}
}
