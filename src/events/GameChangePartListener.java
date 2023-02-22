package events;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import main.UHCLebrel;
import me.clip.placeholderapi.PlaceholderAPI;
import net.skinsrestorer.api.PlayerWrapper;
import net.skinsrestorer.api.exception.SkinRequestException;
import uhc.PartChangeEvent;
import uhc.UHCPart;
import util.Scoreboard;
import util.UHCPlayer;

public class GameChangePartListener implements Listener {
	public GameChangePartListener() {

	}

	@EventHandler
	public void onCambioEpisodio(PartChangeEvent e) {
		UHCLebrel.instance.getGameManager().setEpisodio(new UHCPart(e.getNuevoEpisodio()));
		for (UHCPlayer mam : UHCLebrel.instance.getUHCPlayers()) {
			// Scoreboard.updateScoreboard(mam, ChatColor.translateAlternateColorCodes('&',
			// PlaceholderAPI.setPlaceholders(mam.getPlayer(), "&e%uhc_episodio%")), 4);
			Scoreboard.reloadScoreboard(mam);
			mam.getPlayer().sendTitle(
					ChatColor.translateAlternateColorCodes('&',
							ChatColor.translateAlternateColorCodes('&',
									PlaceholderAPI.setPlaceholders(mam.getPlayer(),
											UHCLebrel.instance.messages.newPart))
									+ e.getNuevoEpisodio()),
					null, 5, 20, 10);
		}

		/*
		 * Casos especificos de un episodio.
		 */
		final int episodioWorldborder = UHCLebrel.instance.getConfig().getInt("partes.worldborder");
		final int episodioInicioPvp = UHCLebrel.instance.getConfig().getInt("partes.iniciopvp");
		if (e.getNuevoEpisodio() == episodioInicioPvp) {
			World ovw = Bukkit.getWorld(UHCLebrel.instance.getConfig().getString("juego.mundos.overworld"));
			World net = Bukkit.getWorld(UHCLebrel.instance.getConfig().getString("juego.mundos.nether"));
			World end = Bukkit.getWorld(UHCLebrel.instance.getConfig().getString("juego.mundos.end"));
			ovw.setPVP(true);
			net.setPVP(true);
			end.setPVP(true);
			Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&',
					"&6[ANUNCIO] &5Se ha acabado el pacto de caballeros, &4&lPVP ACTIVADO A PARTIR DE ESTE MOMENTO."));
		}
		if (e.getNuevoEpisodio() == episodioWorldborder) {
			World ovw = Bukkit.getWorld(UHCLebrel.instance.getConfig().getString("juego.mundos.overworld"));
			World net = Bukkit.getWorld(UHCLebrel.instance.getConfig().getString("juego.mundos.nether"));
			final int smallWb = UHCLebrel.instance.getConfig().getInt("juego.worldborder.final");
			final int shrinkTime = UHCLebrel.instance.getConfig().getInt("juego.worldborder.tiempo");

			ovw.getWorldBorder().setSize(smallWb, Integer.toUnsignedLong(shrinkTime));
			net.getWorldBorder().setSize(smallWb / 8, Integer.toUnsignedLong(shrinkTime));
			Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', String.format(
					"&6[ANUNCIO] &5Ha comenzado la parte %d, todo el mundo ha de ir a 0, 0.", episodioWorldborder)));
		}

		/*
		 * Resetear Skins.
		 */
		ArrayList<String> nombres = new ArrayList<>();
		for (UHCPlayer jugador : UHCLebrel.instance.gameManager.getParticipantes()) {
			nombres.add(jugador.getPlayer().getName());
		}
		Random random = new Random();
		for (UHCPlayer vict : UHCLebrel.instance.gameManager.getParticipantes()) {
			ArrayList<String> purgedNames = new ArrayList<>(nombres);
			purgedNames.remove(vict.getPlayer().getName());
			int tmp = random.nextInt(purgedNames.size());

			try {
				UHCLebrel.instance.sapi.setSkin(vict.getPlayer().getName(), purgedNames.get(tmp));
				UHCLebrel.instance.sapi.applySkin(new PlayerWrapper(vict.getPlayer()));
			} catch (SkinRequestException e1) {
				e1.printStackTrace();
			}

			vict.getPlayer().sendMessage(ChatColor.DARK_GREEN + "Ahora tienes la Skin de " + purgedNames.get(tmp));
			nombres.remove(purgedNames.get(tmp));
		}

	}
}
