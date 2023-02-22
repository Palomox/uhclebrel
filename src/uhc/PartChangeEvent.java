package uhc;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PartChangeEvent extends Event{
	private static final HandlerList handlers = new HandlerList();
	private int nuevoEpisodio;
	
	public PartChangeEvent(int nuevoEpi) {
		this.nuevoEpisodio = nuevoEpi ;
	}
	public HandlerList getHandlers() {
		return handlers;
	}
	
	public static HandlerList getHandlerList() {
		return handlers;
	}
	public int getNuevoEpisodio() {
		return this.nuevoEpisodio;
	}

}
