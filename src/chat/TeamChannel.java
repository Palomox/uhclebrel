package chat;

import java.util.ArrayList;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import me.clip.placeholderapi.PlaceholderAPI;
import uhc.Equipo;
import util.Mamerto;

public class TeamChannel implements IChannel{
	
	private Equipo team;
	private char prefix;
	public TeamChannel(Equipo dueno, char prefix) {
		this.team = dueno;
		this.prefix = prefix;
	}
	
	public void sendRawMessage(String mensaje) {
		for(Mamerto tmp : this.team.getMiembros().keySet()) {
			tmp.getPlayer().sendMessage(mensaje);
		}
	}
	@Deprecated
	public void sendFormattedMsg(String rawmsg, Player sender) {
		String format = ChatColor.translateAlternateColorCodes('&', "&6[COMPAÑERO] &r%player_name%&8: &r");
		String mensaje = PlaceholderAPI.setPlaceholders(sender, format)+ rawmsg;
		for(Mamerto tmp : this.team.getMiembros().keySet()) {
			if(tmp.isDesconectado()) {
				continue;
			}
			tmp.getPlayer().sendMessage(mensaje);
		}
	}
	@Override
	public String getName() {
		return team.getNombre();
	}
	@Override
	public ArrayList<Player> getLectores() {
		ArrayList<Player> lectores = new ArrayList<Player>();
		for(Mamerto tmp : this.team.getMiembros().keySet()) {
			lectores.add(tmp.getPlayer());
		}
		return lectores;
	}
	public boolean addLector(Player player) {
		return false;
	}
	@Override
	public char getPrefix() {
		return prefix;
	}

	@Override
	public String getFormat() {
		return "&6[COMPAÑERO] &r%s&8: &r%s";
	}
}
