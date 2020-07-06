package uhc;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class EpisodioChangeEvent extends Event{
	private static final HandlerList handlers = new HandlerList();
	private int nuevoEpisodio;
	
	public EpisodioChangeEvent(int nuevoEpi) {
		this.nuevoEpisodio = nuevoEpi ;
	}
	public HandlerList getHandlers() {
		return handlers;
	}
	
	public static HandlerList gatHandlerList() {
		return handlers;
	}

}
