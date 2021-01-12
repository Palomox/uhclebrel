package uhc;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class EachSecondEvent extends Event{
	private static final HandlerList handlers = new HandlerList();
	
	public EachSecondEvent() {
	}
		public HandlerList getHandlers() {
		return handlers;
		}
		public static HandlerList getHandlerList() {
			return handlers;
		}
}
