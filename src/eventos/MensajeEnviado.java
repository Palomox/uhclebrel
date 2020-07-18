package eventos;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import chat.IChannel;
import chat.NChannel;
import chat.TeamChannel;
import main.Main;
import me.clip.placeholderapi.PlaceholderAPI;
import util.Mamerto;

public class MensajeEnviado implements Listener {
	private Main plugin;

	public MensajeEnviado(Main plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void jugadorMandaMensaje(AsyncPlayerChatEvent e) {
		Player chateador = e.getPlayer();
		Mamerto jugador = Mamerto.getHPPlayer(chateador, plugin);
		IChannel amandar = getAmandar(e.getMessage());
		String mensaje = e.getMessage();
		String mensajeText; 
		if(amandar == null) {
			amandar = jugador.getWritingChannel();
			mensajeText = e.getMessage();
			amandar.sendFormattedMsg(mensajeText, chateador);
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
				amandar.sendFormattedMsg(mensajeText, chateador);
			} else if (amandar instanceof TeamChannel) {
				mensajeText = mensaje.substring(1);
				amandar.sendFormattedMsg(mensajeText, chateador);
			}
		}
		
	e.setCancelled(true);
	}

	private IChannel getAmandar(String mensaje) {
		String first = String.valueOf(mensaje.charAt(0));
		for(IChannel tmp : plugin.getCanales()) {
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
