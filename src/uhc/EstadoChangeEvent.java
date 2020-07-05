package uhc;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class EstadoChangeEvent extends Event {
	private EstadosJuego nuevoestado;
	private static final HandlerList handlers = new HandlerList();
	
	public EstadoChangeEvent(EstadosJuego nuevo) {
		this.nuevoestado = nuevo;
	}
	
	
		public EstadosJuego getNuevoestado() {
			return nuevoestado;
		}


		public void setNuevoestado(EstadosJuego nuevoestado) {
			this.nuevoestado = nuevoestado;
		}


		public HandlerList getHandlers() {
		return handlers;
		}
		public static HandlerList getHandlersList() {
			return handlers;
		}
}
