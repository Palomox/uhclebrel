package eventos;

import java.time.LocalDate;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import chat.IChannel;
import fr.minuskube.netherboard.Netherboard;
import fr.minuskube.netherboard.bukkit.BPlayerBoard;
import main.UHCLebrel;
import me.clip.placeholderapi.PlaceholderAPI;
import util.Mamerto;
import util.Scoreboard;

public class NewPlayer implements Listener {
	private UHCLebrel plugin;

	public NewPlayer(UHCLebrel plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void alUnirServidor(PlayerJoinEvent event) {
		Player jugador = event.getPlayer();
		// Comprueba si es admin, asi le añade al arraylist de admins.
		if (jugador.isOp() || jugador.hasPermission("hopoke.admin")) {
			plugin.getAdmins().add(jugador);
		}
		Mamerto hpp;
		if(Mamerto.getHPPlayer(jugador, plugin) ==null) {
			LocalDate fj = LocalDate.now();
			hpp = new Mamerto(jugador.getUniqueId().toString(), fj);
			plugin.getHoPokePlayers().add(hpp);
		}	
		// Canal por defecto
		hpp = Mamerto.getHPPlayer(jugador, plugin);
		for (String disc : plugin.getConfig().getConfigurationSection("chat.channels").getKeys(false)) {
			String perm = plugin.getConfig().getString("chat.channels." + disc + ".autojoinperm");
			IChannel tmp = plugin.getChannelByName(plugin.getConfig().getString("chat.channels." + disc + ".name"));
			if (perm.equalsIgnoreCase("none")) {
				hpp.addReadingChannel(tmp);
				if (tmp.addLector(hpp.getPlayer())) {
					jugador.getPlayer().sendMessage(ChatColor.DARK_GREEN + "Ahora también lees " + tmp.getName());
				} else {
					jugador.getPlayer().sendMessage(
							ChatColor.DARK_RED + "No puedes leer " + tmp.getName() + " porque ya lo estás leyendo!");
				}
			}
			if (hpp.getPlayer().hasPermission(perm)) {
				hpp.addReadingChannel(tmp);
				if (tmp.addLector(hpp.getPlayer())) {
					jugador.getPlayer().sendMessage(ChatColor.DARK_GREEN + "Ahora también lees " + tmp.getName());
				} else {
					jugador.getPlayer().sendMessage(
							ChatColor.DARK_RED + "No puedes leer " + tmp.getName() + " porque ya lo estas leyendo!");
				}
			}
		}
		IChannel wr = plugin.getChannelByName(plugin.getConfig().getString("chat.defaultwritingchannel"));
		hpp.setWritingChannel(wr);
		/*
		 * Creacion de la Scoreboard
		 */
		switch(UHCLebrel.instance.juego.getEstado()) {
		case ESPERANDO:
		BPlayerBoard board = Netherboard.instance().createBoard(hpp.getPlayer(), "Main Scoreboard");
		board.setName(ChatColor.translateAlternateColorCodes('&', "&6&lUHC Lebrel T.2"));
		for (String linea : plugin.getConfig().getConfigurationSection("scoreboard.lobby.lines").getKeys(false)) {
			String texto = plugin.getConfig().getString("scoreboard.lobby.lines." + linea);
			texto = PlaceholderAPI.setPlaceholders(hpp.getPlayer(), texto);
			int line = Integer.valueOf(linea);
			board.set(texto, line);
		}
			break;
		case JUGANDO:
			Netherboard.instance().createBoard(hpp.getPlayer(), "Main");
			for (String linea : plugin.getConfig()
					.getConfigurationSection("scoreboard.durante.lines")
					.getKeys(false)) {
				String texto = plugin.getConfig().getString("scoreboard.durante.lines." + linea);
				texto = PlaceholderAPI.setPlaceholders(hpp.getPlayer(), texto);
				int line = Integer.valueOf(linea);
				Scoreboard.updateScoreboard(hpp.getPlayer(), texto, line);
			}
			break;
		case FINALIZADO:
		BPlayerBoard boardf = Netherboard.instance().createBoard(hpp.getPlayer(), "Main Scoreboard");
		boardf.setName(ChatColor.translateAlternateColorCodes('&', "&6&lUHC Lebrel T.2"));
		for (String linea : plugin.getConfig().getConfigurationSection("scoreboard.final.lines").getKeys(false)) {
			String texto = plugin.getConfig().getString("scoreboard.final.lines." + linea);
			texto = PlaceholderAPI.setPlaceholders(hpp.getPlayer(), texto);
			int line = Integer.valueOf(linea);
			boardf.set(ChatColor.translateAlternateColorCodes('&', texto), line);
		}
		break;
		default:
			break;
		}
		/*
		 * Reconectar
		 */
		if(hpp.isDesconectado()) {
			hpp.setDesconectado(false);
		}
		
	}
}