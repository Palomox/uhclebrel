package events;

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
import util.UHCPlayer;
import util.Scoreboard;

public class PlayerJoinListener implements Listener {
	private UHCLebrel plugin;

	public PlayerJoinListener(UHCLebrel plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void alUnirServidor(PlayerJoinEvent event) {
		Player jugador = event.getPlayer();
		final UHCPlayer hpp;
		if (UHCPlayer.getHPPlayer(jugador, plugin) == null) {
			LocalDate fj = LocalDate.now();
			hpp = new UHCPlayer(jugador.getUniqueId().toString(), fj);
			plugin.getUHCPlayers().add(hpp);
		} else {
			hpp = UHCPlayer.getHPPlayer(jugador, plugin);
		}
		// Canal por defecto
		for (String disc : plugin.getConfig().getConfigurationSection("chat.channels").getKeys(false)) {
			String perm = plugin.getConfig().getString("chat.channels." + disc + ".autojoinperm");
			IChannel tmp = plugin.getChannelByName(plugin.getConfig().getString("chat.channels." + disc + ".name"));
			if (perm.equalsIgnoreCase("none")) {
				hpp.addReadingChannel(tmp);
				if (tmp.addChannelReader(hpp.getPlayer())) {
					jugador.getPlayer().sendMessage(ChatColor.DARK_GREEN + "Ahora también lees " + tmp.getName());
				} else {
					jugador.getPlayer().sendMessage(
							ChatColor.DARK_RED + "No puedes leer " + tmp.getName() + " porque ya lo estás leyendo!");
				}
			}
			if (hpp.getPlayer().hasPermission(perm)) {
				hpp.addReadingChannel(tmp);
				if (tmp.addChannelReader(hpp.getPlayer())) {
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
		switch (UHCLebrel.instance.gameManager.getEstado()) {
		case WAITING:
			Runnable sr = new Runnable() {

				@Override
				public void run() {
					Scoreboard.updateScoreboard(hpp, ChatColor.translateAlternateColorCodes('&',
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
		case PLAYING:
			Runnable srp = new Runnable() {

				@Override
				public void run() {
					Scoreboard.updateScoreboard(hpp, ChatColor.translateAlternateColorCodes('&',
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
		case FINISHING:
			Runnable srf = new Runnable() {

				@Override
				public void run() {
					Scoreboard.updateScoreboard(hpp, ChatColor.translateAlternateColorCodes('&',
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

		UHCLebrel.instance.everyone.addEntry(event.getPlayer().getName());
		event.getPlayer().setScoreboard(UHCLebrel.instance.all);
		/*
		 * Reconectar
		 */
		if (hpp.isDesconectado()) {
			hpp.setDesconectado(false);
		}

	}
}