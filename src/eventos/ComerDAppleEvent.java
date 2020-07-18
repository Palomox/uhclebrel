package eventos;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ComerDAppleEvent implements Listener{
	
	public ComerDAppleEvent() {
	}
	@EventHandler
	public void onComida(PlayerItemConsumeEvent e) {
		if(!e.getItem().getItemMeta().hasCustomModelData()) {
			return;
		}
		Player player = e.getPlayer();
		switch (e.getItem().getItemMeta().getCustomModelData()) {
		case 247:
			/*
			 * DiamondApple Normal
			 */
			e.getPlayer().getInventory().clear(e.getPlayer().getInventory().getHeldItemSlot());
			//Doy los efectos 
			ArrayList<PotionEffect> efectos = new ArrayList<PotionEffect>();
			efectos.add(new PotionEffect(PotionEffectType.REGENERATION, 100,4 ));
			player.addPotionEffects(efectos);
			player.setAbsorptionAmount(10);
			e.setCancelled(true);
			break;
		case 246:
			/*
			 * DiamondApple Tocha
			 */
			e.getPlayer().getInventory().clear(e.getPlayer().getInventory().getHeldItemSlot());
			ArrayList<PotionEffect> efectos2 = new ArrayList<PotionEffect>();
			efectos2.add(new PotionEffect(PotionEffectType.REGENERATION, 600,4 ));
			efectos2.add(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 8400, 2));
			efectos2.add(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 8400, 2));
			player.addPotionEffects(efectos2);
			player.setAbsorptionAmount(20);
			e.setCancelled(true);
			break;
		default:
			return;
		}
	}
}
