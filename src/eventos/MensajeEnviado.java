package eventos;

import java.util.ArrayList;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import chat.Channel;
import main.Main;
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
		Channel amandar = jugador.getWritingChannel();
		ArrayList<Player> receptores = amandar.getLectores();
		String mensaje = chateador.getName()+"> "+e.getMessage();
		for(int i=0; i<receptores.size(); i++) {
			receptores.get(i).sendMessage(mensaje);
		}
		e.setCancelled(true);
	}
}
