package uhc;

import org.bukkit.entity.Player;

import main.Main;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;

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
		}
		return null;
	}
}
