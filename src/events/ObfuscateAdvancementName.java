package events;

import org.bukkit.Bukkit;
import org.bukkit.advancement.Advancement;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.HoverEventSource;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.TranslatableComponent;
import net.md_5.bungee.api.chat.hover.content.Text;

public class ObfuscateAdvancementName implements Listener{
	public ObfuscateAdvancementName() {
	}
	@EventHandler
	public void onAdvancement(PlayerAdvancementDoneEvent e) {
		Advancement adv = e.getAdvancement();
		if(adv.getKey().getKey().toString().contains("recipe")) {
			return;
		}
		Component message = MiniMessage.get().parse("<hover:show_text:'<lang:advancements."+adv.getKey().getKey().replace('/', '.')+".description>'><lang:chat.type.advancement.task:'<obfuscated>"+e.getPlayer().getName()+"':'<lang:advancements."+adv.getKey().getKey().replace('/', '.')+".description>'>");
		/*TranslatableComponent mensaje = new TranslatableComponent("chat.type.advancement.task");
		TextComponent nombre = new TextComponent(e.getPlayer().getName());
		HoverEvent h = new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(new TranslatableComponent("advancements."+adv.getKey().getKey().replace('/', '.')+".description").getTranslate()));
		nombre.setColor(ChatColor.MAGIC);
		mensaje.addWith(nombre.toLegacyText());
		TranslatableComponent nom = new TranslatableComponent("advancements."+adv.getKey().getKey().replace('/', '.')+".title");
		nom.setHoverEvent(h);
		mensaje.addWith(nom);*/
		for(Player p : Bukkit.getOnlinePlayers()) {
			p.sendMessage(message);
		}
		}
    }
