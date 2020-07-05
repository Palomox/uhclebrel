package eventos;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import main.Main;
import uhc.EstadoChangeEvent;
import util.Mamerto;

public class CambiaEstado implements Listener{
	
	public CambiaEstado() {
		
	}
	@EventHandler
	public void onJuegoFinalizado(EstadoChangeEvent e) {
		switch(e.getNuevoestado()) {
		case FINALIZADO:
			for(Mamerto tmp : Main.instance.getHoPokePlayers()) {
				Main.instance.juego.setSpectator(tmp);
			}
			break;
		case JUGANDO:
			break;
		default:
			break;
		}
	}
}
