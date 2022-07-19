package tp;

import java.util.Iterator;
import java.util.LinkedList;

public class Deposito {

	private LinkedList<Paquete> paquetes = new LinkedList<Paquete>();
	private double capacidadMaxDeposito;
	private boolean esFrio;
	private double capacidadActual;

	public LinkedList<Paquete> getPaquetes() {
		return paquetes;
	}

	public double getCapacidadMaxDeposito() {
		return capacidadMaxDeposito;
	}

	public boolean isEsFrio() {
		return esFrio;
	}

	public double getCapacidadActual() {
		return capacidadActual;
	}

	public Deposito(int Capacidad, boolean frio) {

		this.capacidadMaxDeposito = Capacidad;
		this.esFrio = frio;
	}

	@Override
	public String toString() {
		
		StringBuilder s = new StringBuilder("PAQUETES EN DEPOSITO:\n");
		
		for(Paquete p: paquetes) {
			s.append(p.toString());
			s.append("\n");
		}
		return s.toString();
	}

	void eliminarPaquete(Paquete p) {
		paquetes.remove(p);
	}

	LinkedList<Paquete> separarPaquetes(String destino) { // separa los paquetes por destino

		LinkedList<Paquete> paquetesConElMismoDestino = new LinkedList<Paquete>();
		Iterator<Paquete> iterador = paquetes.iterator();

		while (iterador.hasNext()) {
			Paquete p = iterador.next();

			if (p.getDestino().equals(destino)) {
				paquetesConElMismoDestino.add(p);
				iterador.remove();

			}

		}

		return paquetesConElMismoDestino;

	}

	public boolean estaVacio() {
		return paquetes.isEmpty();

	}

	public boolean agregarPaquete(Paquete paquete) { // agrega el paquete a la lista de paquetes
		

		if (capacidadMaxDeposito >= (capacidadActual + paquete.getVolumen())) {

			paquetes.add(paquete);
			capacidadActual = capacidadActual + paquete.getVolumen();
			return true;
		} 
			
			return false;
	}

}