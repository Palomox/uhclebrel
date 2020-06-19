package eventos;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import chat.Channel;
import main.Main;
import me.clip.placeholderapi.PlaceholderAPI;
import util.HoPokePlayer;

public class MensajeEnviado implements Listener{
	private Main plugin;
	public MensajeEnviado(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void jugadorMandaMensaje(AsyncPlayerChatEvent e) {
		Player chateador = e.getPlayer();
		HoPokePlayer jugador = HoPokePlayer.getHPPlayer(chateador, plugin);
		Channel amandar = getAmandar(e.getMessage());
		String mensaje = e.getMessage();
		String mensajeText; 
		if(amandar == null) {
			amandar = jugador.getWritingChannel();
			mensajeText = e.getMessage();
		}else {
			 if(jugador.getPlayer().hasPermission(plugin.getConfig().getString("chat.channels."+amandar.getName()+".perm"))){
				 mensajeText = e.getMessage().substring(1);
			 }else {
				 mensajeText=e.getMessage();
				 amandar = jugador.getWritingChannel();
			 }
		}
		mensaje = plugin.getConfig().getString("chat.channels."+amandar.getName()+".format");
		mensaje = PlaceholderAPI.setPlaceholders(chateador, mensaje);
		mensaje = mensaje+mensajeText;
		
		amandar.sendRawMessage(mensaje);
		e.setCancelled(true);
	}
	
	
	private Channel getAmandar(String mensaje) {
		String first = String.valueOf(mensaje.charAt(0));
		for(Channel tmp : plugin.getCanales()) {
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
