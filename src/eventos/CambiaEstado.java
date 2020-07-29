package eventos;

import java.util.List;

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
				List<String> lineas = (List<String>) plugin.getConfig().getList("scoreboard.final.lines");
				for (int i=lineas.size(); i>0;i--) {
					Scoreboard.updateScoreboard(tmp.getPlayer(), PlaceholderAPI.setPlaceholders(tmp.getPlayer(), lineas.get(i-1)), i);
				}
			}
			break;
		case JUGANDO:
			for(Mamerto tmp : UHCLebrel.instance.getHoPokePlayers()) {
				Scoreboard.clear(tmp.getPlayer());
				List<String> lineas = (List<String>) plugin.getConfig().getList("scoreboard.durante.lines");
				for (int i=lineas.size(); i>0;i--) {
					Scoreboard.updateScoreboard(tmp.getPlayer(), PlaceholderAPI.setPlaceholders(tmp.getPlayer(), PlaceholderAPI.setPlaceholders(tmp.getPlayer(), lineas.get(i-1))), i);
				}
				}
			for(Mamerto temp : UHCLebrel.instance.juego.getParticipantes()) {
				temp.setDisplayname(ChatColor.translateAlternateColorCodes('&', "&k"+temp.getPlayer().getName()));
				temp.getPlayer().setDisplayName(temp.getDisplayname());
				temp.getPlayer().setPlayerListName(temp.getDisplayname());
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
