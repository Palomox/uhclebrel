package eventos;

import org.bukkit.Bukkit;
import org.bukkit.advancement.Advancement;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.TranslatableComponent;
import net.md_5.bungee.api.chat.hover.content.Text;

public class CambiarNombreAdvancements implements Listener{
	public CambiarNombreAdvancements() {
	}
	@EventHandler
	public void onAdvancement(PlayerAdvancementDoneEvent e) {
		Advancement adv = e.getAdvancement();
		TranslatableComponent mensaje = new TranslatableComponent("chat.type.advancement.task");
		TextComponent nombre = new TextComponent(e.getPlayer().getName());
		HoverEvent h = new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(new TranslatableComponent("advancements."+adv.getKey().getKey().replace('/', '.')+".description").getTranslate()));
		nombre.setColor(ChatColor.MAGIC);
		mensaje.addWith(nombre);
		TranslatableComponent nom = new TranslatableComponent("advancements."+adv.getKey().getKey().replace('/', '.')+".title");
		nom.setHoverEvent(h);
		mensaje.addWith(nombre.toLegacyText());
		for(Player p : Bukkit.getOnlinePlayers()) {
			p.sendMessage(mensaje);
		}
		}
    }
