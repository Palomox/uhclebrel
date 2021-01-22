package uhc;

import java.util.StringJoiner;

import org.bukkit.entity.Player;

import main.UHCLebrel;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import net.md_5.bungee.api.ChatColor;
import util.UHCPlayer;

public class UhcPlaceholders extends PlaceholderExpansion{

	@Override
	public String getAuthor() {
		return UHCLebrel.instance.getDescription().getAuthors().toString();
	}

	@Override
	public String getIdentifier() {
		return "uhc";
	}

	@Override
	public String getVersion() {
		return UHCLebrel.instance.getDescription().getVersion().toString();
	}
	
	@Override
	public boolean persist() {
		return true;
	}
	
	@Override
    public boolean canRegister(){
        return true;
    }
	
	@Override
	public String onPlaceholderRequest(Player player, String identifier) {
		switch(identifier) {
		/*
		 * %uhc_episodio%
		 */
		case "episodio":
			return String.valueOf(UHCLebrel.instance.getGameManager().getEpisodio().getId());
		/*
		 * %uhc_ganador%
		 */
		case "ganador":
			return UHCLebrel.instance.getGameManager().getGanador().getNombre();
		/*
		 * %uhc_ganadormiembros%
		 */
		case "ganadormiembros":
			StringJoiner sj = new StringJoiner(", ");
			for(UHCPlayer tmp : UHCLebrel.instance.getGameManager().getGanador().getMiembros().keySet()) {
				sj.add(tmp.getPlayer().getName());
			}
			return sj.toString();
		/*
		 * %uhc_teamname%
		 */
		case "teamname":
			
			if(UHCLebrel.instance.getUHCPlayerByName(player.getName()).getTeam() !=null) {
				switch(UHCLebrel.instance.gameManager.getEstado()) {
				case PLAYING:
					if(UHCLebrel.instance.getUHCPlayerByName(player.getName()).isEspectador()) {
						return UHCLebrel.instance.getUHCPlayerByName(player.getName()).getTeam().getNombre();
					}
					return ChatColor.MAGIC+UHCLebrel.instance.getUHCPlayerByName(player.getName()).getTeam().getNombre();
				default:
					return UHCLebrel.instance.getUHCPlayerByName(player.getName()).getTeam().getNombre();
				}
			}else {
				return "Sin equipo, 1";
			}
		case "rarename":
			if(UHCLebrel.instance.getGameManager().getEstado() == GameStatuses.PLAYING) {
				return UHCLebrel.instance.getUHCPlayerByName(player.getName()).getDisplayname();
			}else {
				return player.getName();
			}	
		}
		
		return null;
	}
}
