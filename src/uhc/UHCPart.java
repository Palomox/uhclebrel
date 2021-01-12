package uhc;

import java.time.Duration;

public class UHCPart {
	private int id;
	private Duration duracion;
	public UHCPart(int id) {
		this.id = id;
		duracion = Duration.ofMinutes(20);
	}
	
	public int getId() {
		return this.id;
	}
	public void menosUnSec() {
		this.duracion = this.duracion.minusSeconds(1);
	}

	public Duration getDuracion() {
		return duracion;
	}

	public void setDuracion(Duration duracion) {
		this.duracion = duracion;
	}
	
}
