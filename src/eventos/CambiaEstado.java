package eventos;

import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.RenderType;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;
import org.bukkit.scoreboard.Team.Option;
import org.bukkit.scoreboard.Team.OptionStatus;

import main.UHCLebrel;
import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.ChatColor;
import uhc.EstadoChangeEvent;
import uhc.EstadosJuego;
import util.Mamerto;
import util.Scoreboard;

public class CambiaEstado implements Listener{
	private Team todos;
	public CambiaEstado() {
		UHCLebrel.instance.todos = todos;
	}
	@EventHandler
	public void onJuegoFinalizado(EstadoChangeEvent e) {
		UHCLebrel plugin = UHCLebrel.instance;
		switch(e.getNuevoestado()) {
		case FINALIZADO:
			todos.unregister();
			for(Mamerto tmp : UHCLebrel.instance.getHoPokePlayers()) {
				UHCLebrel.instance.juego.setSpectator(tmp);
				Scoreboard.clear(tmp.getPlayer());
				for (String linea : plugin.getConfig()
						.getConfigurationSection("scoreboard.final.lines")
						.getKeys(false)) {
					String texto = plugin.getConfig().getString("scoreboard.final.lines." + linea);
					texto = PlaceholderAPI.setPlaceholders(tmp.getPlayer(), texto);
					int line = Integer.valueOf(linea);
					Scoreboard.updateScoreboard(tmp.getPlayer(), texto, line);
				}
			}
			break;
		case JUGANDO:
			for(Mamerto tmp : UHCLebrel.instance.getHoPokePlayers()) {
				Scoreboard.clear(tmp.getPlayer());
				for (String linea : plugin.getConfig()
						.getConfigurationSection("scoreboard.durante.lines")
						.getKeys(false)) {
					String texto = plugin.getConfig().getString("scoreboard.durante.lines." + linea);
					texto = PlaceholderAPI.setPlaceholders(tmp.getPlayer(), texto);
					int line = Integer.valueOf(linea);
					Scoreboard.updateScoreboard(tmp.getPlayer(), texto, line);
				}
			}
			ScoreboardManager manager = Bukkit.getScoreboardManager();
			org.bukkit.scoreboard.Scoreboard all = manager.getNewScoreboard();
			Objective cnom = all.registerNewObjective("corazonesbajo", "health", ChatColor.RED+""+ChatColor.BOLD+"❤", RenderType.INTEGER);
			Objective ctab = all.registerNewObjective("corazonestab", 
					"health",
					" ", 
					RenderType.HEARTS);
			cnom.setDisplaySlot(DisplaySlot.BELOW_NAME);
			ctab.setDisplaySlot(DisplaySlot.PLAYER_LIST);
			todos = all.registerNewTeam("todos");
			todos.allowFriendlyFire();
			todos.setCanSeeFriendlyInvisibles(false);
			todos.setColor(org.bukkit.ChatColor.MAGIC);
			todos.setOption(Option.NAME_TAG_VISIBILITY, OptionStatus.ALWAYS);
			
			for(Mamerto temp : UHCLebrel.instance.juego.getParticipantes()) {
				temp.setDisplayname(ChatColor.translateAlternateColorCodes('&', "&k"+temp.getPlayer().getName()));
				temp.getPlayer().setDisplayName(temp.getDisplayname());
				temp.getPlayer().setPlayerListName(temp.getDisplayname());
				todos.addEntry(temp.getPlayer().getName());
				temp.getPlayer().setScoreboard(all);
			}
			World ovw = Bukkit.getWorld("uhc");
			World net = Bukkit.getWorld("uhc_nether");
			World end = Bukkit.getWorld("uhc_the_end");
			ovw.setPVP(false);
			net.setPVP(false);
			end.setPVP(false);
			ovw.getWorldBorder().setSize(3000);
			net.getWorldBorder().setSize(1500);
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "time set day");
			ovw.setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS, false);
			net.setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS, false);
			end.setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS, false);
			break;
		default:
			break;
		}
	}
}
