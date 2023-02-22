package uhc;

import java.time.Duration;

import main.UHCLebrel;

public class UHCPart {
	private int id;
	private Duration duracion;
	public UHCPart(int id) {
		this.id = id;
		duracion = Duration.ofMinutes(UHCLebrel.instance.getConfig().getInt("partes.duracion"));
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
