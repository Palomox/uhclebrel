package uhc;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class SecondEvent extends Event{
	private static final HandlerList handlers = new HandlerList();
	
	public SecondEvent() {
	}
		public HandlerList getHandlers() {
		return handlers;
		}
		public static HandlerList getHandlerList() {
			return handlers;
		}
}
