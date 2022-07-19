package tp;

import java.util.Objects;

public class Paquete {

	private double peso;
	private double volumen;
	private String destino;
	private boolean esFrio;

	public Paquete(String destino, double peso, double volumen, boolean frio) {
		
		if (peso <= 0 || volumen <=0){
			throw new RuntimeException ("NO SE PUEDE AGREGAR EL PAQUETE PORQUE EL PESO O VOLUMEN DEBE SER MAYOR A 0 (CERO)");
		} 

		this.peso = peso;
		this.volumen = volumen;
		this.destino = destino;
		this.esFrio = frio;

	}

	public double getPeso() {
		return peso;
	}

	public double getVolumen() {
		return volumen;

	}

	public String getDestino() {
		return destino;
	}

	public boolean getEsFrio() {
		return esFrio;
	}

	public String toString() {
		return ("Peso: " + peso + "Volumen: " + volumen + "Destino: " + destino + "Necesita Frio: " + esFrio);
	}

	@Override
	public int hashCode() {
		return Objects.hash(destino, esFrio, peso, volumen);
	}

	@Override
	public boolean equals(Object obj) {
		
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Paquete other = (Paquete) obj;

		return destino.equals(other.destino) && esFrio == other.esFrio && getPeso() == other.getPeso()
				&& getVolumen() == other.getVolumen();
	}

}
