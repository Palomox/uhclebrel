package uhc;

import java.util.StringJoiner;

import org.bukkit.entity.Player;

import main.UHCLebrel;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import net.md_5.bungee.api.ChatColor;
import util.Mamerto;

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
			return String.valueOf(UHCLebrel.instance.getJuego().getEpisodio().getId());
		/*
		 * %uhc_ganador%
		 */
		case "ganador":
			return UHCLebrel.instance.getJuego().getGanador().getNombre();
		/*
		 * %uhc_ganadormiembros%
		 */
		case "ganadormiembros":
			StringJoiner sj = new StringJoiner(", ");
			for(Mamerto tmp : UHCLebrel.instance.getJuego().getGanador().getMiembros().keySet()) {
				sj.add(tmp.getPlayer().getName());
			}
			return sj.toString();
		/*
		 * %uhc_teamname%
		 */
		case "teamname":
			
			if(UHCLebrel.instance.getHPByName(player.getName()).getTeam() !=null) {
				switch(UHCLebrel.instance.juego.getEstado()) {
				case JUGANDO:
					if(UHCLebrel.instance.getHPByName(player.getName()).isEspectador()) {
						return UHCLebrel.instance.getHPByName(player.getName()).getTeam().getNombre();
					}
					return ChatColor.MAGIC+UHCLebrel.instance.getHPByName(player.getName()).getTeam().getNombre();
				default:
					return UHCLebrel.instance.getHPByName(player.getName()).getTeam().getNombre();
				}
			}else {
				return "Sin equipo, 1";
			}
		case "rarename":
			if(UHCLebrel.instance.getJuego().getEstado() == EstadosJuego.JUGANDO) {
				return UHCLebrel.instance.getHPByName(player.getName()).getDisplayname();
			}else {
				return player.getName();
			}	
		}
		
		return null;
	}
}
