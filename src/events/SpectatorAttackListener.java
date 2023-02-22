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
		if(e.getDamager() instanceof Player p) {
			if(UHCLebrel.instance.getUHCPlayerByName(p.getName()).isEspectador()) {
				e.setCancelled(true);
			}
		}
	}
}
