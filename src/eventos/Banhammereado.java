package eventos;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;



public class Banhammereado implements Listener{
	@EventHandler
	public void alSerBanhammereado(EntityDamageByEntityEvent e) {
		Bukkit.getConsoleSender().sendMessage("evento0va");
		if(e.getDamager() instanceof Player && e.getEntity() instanceof Player) {
			Player vistima = (Player) e.getEntity();
			Player estaf = (Player) e.getDamager();
			ItemStack banhammeritem = new ItemStack(Material.DIAMOND_SHOVEL);
			estaf.sendMessage("evento 1 disparado");
			
			if(estaf.getInventory().getItemInMainHand().equals(banhammeritem) && (estaf.hasPermission("hopoke.banhammer") || estaf.isOp())) {
				Location vistimal = vistima.getLocation();
				vistima.getWorld().spawnEntity(vistimal, EntityType.LIGHTNING);
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "ban "+vistima.getName()+" El Banhammer ha hablado.");
				estaf.sendMessage("va");
			}
		}
		
		
	}
}
