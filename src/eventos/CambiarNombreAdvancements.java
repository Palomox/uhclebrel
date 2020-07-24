package eventos;

import org.bukkit.Bukkit;
import org.bukkit.advancement.Advancement;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TranslatableComponent;

public class CambiarNombreAdvancements implements Listener{
	public CambiarNombreAdvancements() {
	}
	@EventHandler
	public void onAdvancement(PlayerAdvancementDoneEvent e) {
		Advancement adv = e.getAdvancement();
		TranslatableComponent mensaje = new TranslatableComponent("chat.type.advancement.task");
		mensaje.addWith(ChatColor.translateAlternateColorCodes('&', "&r"+e.getPlayer().getName()+"&r"));
		mensaje.addWith("["+adv.getKey().getKey().replace('_', ' ')+"]");
		for(Player p : Bukkit.getOnlinePlayers()) {
			p.sendMessage(mensaje);
		}
		}
    }
