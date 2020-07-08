package eventos;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import main.Main;
import util.Mamerto;

public class EspectadorAtaca implements Listener{
	public EspectadorAtaca() {
	}
	
	@EventHandler
	public void onAtaque(EntityDamageByEntityEvent e) {
		if(e.getDamager() instanceof Player) {
			Player atacante = (Player) e.getDamager();
			for(Mamerto mam : Main.instance.getJuego().getMuertos()) {
				if(atacante.equals(mam.getPlayer())) {
					e.setCancelled(true);
				}
			}
		}
	}
}
