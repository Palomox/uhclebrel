package chat;

import java.util.ArrayList;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import me.clip.placeholderapi.PlaceholderAPI;
import uhc.UHCTeam;
import util.UHCPlayer;

public class TeamChannel implements IChannel{

	private UHCTeam team;
	private char prefix;
	public TeamChannel(UHCTeam dueno, char prefix) {
		this.team = dueno;
		this.prefix = prefix;
	}

	public void sendRawMessage(String mensaje) {
		this.team.getMiembros().forEach((player, vivo) -> player.getPlayer().sendMessage(mensaje));
	}
	@Deprecated
	public void sendFormattedMsg(String rawmsg, Player sender) {
		String format = ChatColor.translateAlternateColorCodes('&', "&6[COMPAÑERO] &r%player_name%&8: &r");
		String mensaje = PlaceholderAPI.setPlaceholders(sender, format)+ rawmsg;
		for(UHCPlayer tmp : this.team.getMiembros().keySet()) {
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
	public ArrayList<Player> getChannelReaders() {
		ArrayList<Player> lectores = new ArrayList<Player>();
		for(UHCPlayer tmp : this.team.getMiembros().keySet()) {
			lectores.add(tmp.getPlayer());
		}
		return lectores;
	}
	public boolean addChannelReader(Player player) {
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
