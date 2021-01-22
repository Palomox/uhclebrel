package events;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import chat.IChannel;
import chat.NChannel;
import chat.TeamChannel;
import main.UHCLebrel;
import me.clip.placeholderapi.PlaceholderAPI;
import util.UHCPlayer;

public class MessageSentListener implements Listener {
	private UHCLebrel plugin;

	public MessageSentListener(UHCLebrel plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void jugadorMandaMensaje(AsyncPlayerChatEvent e) {
		Player chateador = e.getPlayer();
		UHCPlayer jugador = UHCPlayer.getHPPlayer(chateador, plugin);
		IChannel amandar = getAmandar(e.getMessage());
		String mensaje = e.getMessage();
		String mensajeText = null; 
		if(amandar == null) {
			amandar = jugador.getWritingChannel();
			mensajeText = e.getMessage();
		}else {
			if (amandar instanceof NChannel) {
				if (jugador.getPlayer().hasPermission(plugin.getConfig().getString("chat.channels." + amandar.getName() + ".perm"))) {
					mensajeText = e.getMessage().substring(1);
				} else if(plugin.getConfig().getString("chat.channels." + amandar.getName() + ".perm").equals("none")){
					mensajeText = e.getMessage().substring(1);
				}else {
					mensajeText = e.getMessage();
					amandar = jugador.getWritingChannel();
				}
			} else if (amandar instanceof TeamChannel) {
				mensajeText = mensaje.substring(1);
			}
		}
		e.setMessage(ChatColor.translateAlternateColorCodes('&', mensajeText));
		e.setFormat(ChatColor.translateAlternateColorCodes('&', PlaceholderAPI.setPlaceholders(e.getPlayer(), amandar.getFormat()).replace(e.getPlayer().getName(), "%s")));
		e.getRecipients().clear();
		e.getRecipients().addAll(amandar.getChannelReaders());
		
	}
	private IChannel getAmandar(String mensaje) {
		String first = String.valueOf(mensaje.charAt(0));
		for(IChannel tmp : plugin.getChannels()) {
			String prefix = String.valueOf(tmp.getPrefix());
			if(!(prefix.equals("none"))) {
			if(prefix.equals(first)) {
				return tmp;
				}	
			}
		}
		return null;
	}

}
