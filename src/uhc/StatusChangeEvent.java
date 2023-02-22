package uhc;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class StatusChangeEvent extends Event {
	private GameStatuses nuevoestado;
	private static final HandlerList handlers = new HandlerList();
	
	public StatusChangeEvent(GameStatuses nuevo) {
		this.nuevoestado = nuevo;
	}
	
	
		public GameStatuses getNuevoestado() {
			return nuevoestado;
		}


		public void setNuevoestado(GameStatuses nuevoestado) {
			this.nuevoestado = nuevoestado;
		}


		public HandlerList getHandlers() {
		return handlers;
		}
		public static HandlerList getHandlerList() {
			return handlers;
		}
}
