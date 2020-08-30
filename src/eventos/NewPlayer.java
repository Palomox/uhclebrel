package eventos;

import java.time.LocalDate;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import chat.IChannel;
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
		final Mamerto hpp;
		if (Mamerto.getHPPlayer(jugador, plugin) == null) {
			LocalDate fj = LocalDate.now();
			hpp = new Mamerto(jugador.getUniqueId().toString(), fj);
			plugin.getHoPokePlayers().add(hpp);
		} else {
			hpp = Mamerto.getHPPlayer(jugador, plugin);
		}
		// Canal por defecto
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
		switch (UHCLebrel.instance.juego.getEstado()) {
		case ESPERANDO:
			Runnable sr = new Runnable() {

				@Override
				public void run() {
					Scoreboard.updateScoreboard(event.getPlayer(), ChatColor.translateAlternateColorCodes('&',
							UHCLebrel.instance.getConfig().getString("scoreboard.title")));
					List<?> sb = UHCLebrel.instance.getConfig().getList("scoreboard.lobby.lines");
					for (int i = 0; i < sb.size(); i++) {
						String linea = sb.get(i).toString();
						Scoreboard.updateScoreboard(hpp, ChatColor.translateAlternateColorCodes('&',
								PlaceholderAPI.setPlaceholders(event.getPlayer(), linea)), sb.size() - i);
					}
				}
			};
			Bukkit.getScheduler().scheduleSyncDelayedTask(UHCLebrel.instance, sr, 40);

			break;
		case JUGANDO:
			Runnable srp = new Runnable() {

				@Override
				public void run() {
					Scoreboard.updateScoreboard(event.getPlayer(), ChatColor.translateAlternateColorCodes('&',
							UHCLebrel.instance.getConfig().getString("scoreboard.title")));
					List<?> sb = UHCLebrel.instance.getConfig().getList("scoreboard.durante.lines");
					for (int i = 0; i < sb.size(); i++) {
						String linea = sb.get(i).toString();
						Scoreboard.updateScoreboard(hpp, ChatColor.translateAlternateColorCodes('&',
								PlaceholderAPI.setPlaceholders(event.getPlayer(), linea)), sb.size() - i);
					}
				}
			};
			Bukkit.getScheduler().scheduleSyncDelayedTask(UHCLebrel.instance, srp, 40);
			break;
		case FINALIZADO:
			Runnable srf = new Runnable() {

				@Override
				public void run() {
					Scoreboard.updateScoreboard(event.getPlayer(), ChatColor.translateAlternateColorCodes('&',
							UHCLebrel.instance.getConfig().getString("scoreboard.title")));
					List<?> sb = UHCLebrel.instance.getConfig().getList("scoreboard.final.lines");
					for (int i = 0; i < sb.size(); i++) {
						String linea = sb.get(i).toString();
						Scoreboard.updateScoreboard(hpp, ChatColor.translateAlternateColorCodes('&',
								PlaceholderAPI.setPlaceholders(event.getPlayer(), linea)), sb.size() - i);
					}
				}
			};
			Bukkit.getScheduler().scheduleSyncDelayedTask(UHCLebrel.instance, srf, 40);
			break;
		default:
			break;
		}

		UHCLebrel.instance.todos.addEntry(event.getPlayer().getName());
		event.getPlayer().setScoreboard(UHCLebrel.instance.all);
		/*
		 * Reconectar
		 */
		if (hpp.isDesconectado()) {
			hpp.setDesconectado(false);
		}

	}
}