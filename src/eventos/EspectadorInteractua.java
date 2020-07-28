package eventos;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import main.UHCLebrel;

public class EspectadorInteractua implements Listener{
	public EspectadorInteractua() {
	}
	@EventHandler
	public void onInteract(PlayerInteractEntityEvent e) {
		if(UHCLebrel.instance.getHPByName(e.getPlayer().getName()).isEspectador()) {
			e.setCancelled(true);
		}
	}

}
