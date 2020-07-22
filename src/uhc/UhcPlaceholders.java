package uhc;

import java.util.StringJoiner;

import org.bukkit.entity.Player;

import main.Main;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import net.md_5.bungee.api.ChatColor;
import util.Mamerto;

public class UhcPlaceholders extends PlaceholderExpansion{

	@Override
	public String getAuthor() {
		return Main.instance.getDescription().getAuthors().toString();
	}

	@Override
	public String getIdentifier() {
		return "uhc";
	}

	@Override
	public String getVersion() {
		return Main.instance.getDescription().getVersion().toString();
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
			return String.valueOf(Main.instance.getJuego().getEpisodio().getId());
		/*
		 * %uhc_ganador%
		 */
		case "ganador":
			return Main.instance.getJuego().getGanador().getNombre();
		/*
		 * %uhc_ganadormiembros%
		 */
		case "ganadormiembros":
			StringJoiner sj = new StringJoiner(", ");
			for(Mamerto tmp : Main.instance.getJuego().getGanador().getMiembros().keySet()) {
				sj.add(tmp.getPlayer().getName());
			}
			return sj.toString();
		/*
		 * %uhc_teamname%
		 */
		case "teamname":
			
			if(Main.instance.getHPByName(player.getName()).getTeam() !=null) {
				switch(Main.instance.juego.getEstado()) {
				case JUGANDO:
					return ChatColor.MAGIC+Main.instance.getHPByName(player.getName()).getTeam().getNombre();
				default:
					return Main.instance.getHPByName(player.getName()).getTeam().getNombre();
				}
			}else {
				return "Sin equipo 1";
			}
		case "rarename":
			if(Main.instance.getJuego().getEstado() == EstadosJuego.JUGANDO) {
				return Main.instance.getHPByName(player.getName()).getDisplayname();
			}else {
				return player.getName();
			}	
		}
		
		return null;
	}
}
