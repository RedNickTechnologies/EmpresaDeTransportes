package tp;

import java.util.LinkedList;
import java.util.Objects;
import java.util.Set;

public abstract class Transporte {

	private double capacidadMaxVolumen;
	private double capacidadMaxPeso;
	private boolean tieneRefrigeracion;
	private double costoPorKm;
	private double seguroCarga;
	private boolean enViaje;
	private int cantidadKmDestino;
	private String matricula;
	private String destinoAsignado;
	private LinkedList<Paquete> mercaderia = new LinkedList<Paquete>();

	public Transporte(String matricula, double capacidadMaxPeso, double capacidadMaxVolumen, boolean tieneRefrigeracion,
			double costoKm, double seguroCarga) {

		if (seguroCarga < 0) {
			throw new RuntimeException("NO SE PUEDE CREAR EL TRANSPORTE CON LA MATRICULA: " + matricula
					+ " PORQUE EL COSTO DEL SEGURO DE CARGA DEBE SER MAYOR O IGUAL A 0 (CERO)");
		}

		if (capacidadMaxPeso <= 0 || capacidadMaxVolumen <= 0) {
			throw new RuntimeException("NO SE PUEDE CREAR EL TRANSPORTE CON LA MATRICULA: " + matricula
					+ " PORQUE SU CAPACIDAD MAXIMA DE PESO O VOLUMEN DEBE SER MAYOR A 0 (CERO)");
		}
		if (costoKm < 0) {
			throw new RuntimeException("NO SE PUEDE CREAR EL TRANSPORTE CON LA MATRICULA: " + matricula
					+ " PORQUE SU COSTO POR KM DEBE SER MAYOR A 0 (CERO)");
		}

		this.matricula = matricula;
		this.capacidadMaxVolumen = capacidadMaxVolumen;
		this.capacidadMaxPeso = capacidadMaxPeso;
		this.tieneRefrigeracion = tieneRefrigeracion;
		this.costoPorKm = costoKm;
		this.seguroCarga = seguroCarga;

	}

	public String getMercaderia() {
		return ("mercaderia:\n" + mercaderia);
	}

	private void descargar() { // vacia la lista de mercaderia
		mercaderia.clear();
	}

	@Override
	public int hashCode() {
		return Objects.hash(destinoAsignado);
	}

	public boolean getenViaje() {

		return enViaje;

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
		Transporte other = (Transporte) obj;

		if (destinoAsignado == other.destinoAsignado && mercaderia != null && other.mercaderia != null
				&& mercaderia.size() != 0 && other.mercaderia.size() != 0
				&& mercaderia.size() == other.mercaderia.size()) {

			return cargaIgual(mercaderia, other.mercaderia);

		}

		return false;
	}

	private boolean cargaIgual(LinkedList<Paquete> lista1, LinkedList<Paquete> lista2) { // Metodo auxiliar del metodo
																							// equals
																							// compara las listas de las
																							// mercaderias de los
																							// camiones para saber si
																							// son iguales

		if(lista1.size()!= lista2.size())
			return false;
		
		boolean cargaIgual = true;
		for (Paquete p : lista1) {

			boolean paqueteIgual1 = false;
			for (Paquete v : lista2) {
				paqueteIgual1 = paqueteIgual1 || v.equals(p);
			}
			cargaIgual = cargaIgual && paqueteIgual1;
		}
		return cargaIgual;

	}

	protected void setcantidadKmDestino(int d) {
		cantidadKmDestino = d;
	}
	protected void setdestinoAsignado(String d) {
		destinoAsignado = d;
	}

	protected double costoPorKm() {
		return cantidadKmDestino * costoPorKm;
	}
	
	public abstract double costoFinal();

	public void agregarMercaderia(Paquete p) {
		mercaderia.add(p);
	}

	public abstract void asignarDestino(String destino, int dist);
	
	public void iniciarViaje(String matricula) {

		if (enViaje) {
			throw new RuntimeException(
					"el camion: " + matricula + " ya se encuentra en viaje y no se puede volver a inicializar");

		} else if (mercaderia.isEmpty()) {
			throw new RuntimeException(
					"el camion: " + matricula + " no puede realizar el viaje porque no tiene mercaderia");
		} else {
			enViaje = true;
		}

	}

	public void finalizarViaje(String matricula) {

		if (!enViaje) {
			throw new RuntimeException(
					"el camion: " + matricula + " NO se encuentra en viaje, por lo tanto no se puede finalizar");
		} else {
			descargar();
			enViaje = false;
		}

	}

	public double getCapacidadMaxVolumen() {
		return capacidadMaxVolumen;
	}

	public double getCapacidadMaxPeso() {
		return capacidadMaxPeso;
	}

	public boolean getTieneRefrigeracion() {
		return tieneRefrigeracion;
	}

	public double getCostoPorKm() {
		return costoPorKm;
	}

	public double getSeguroCarga() {
		return seguroCarga;
	}

	public int getCantidadKmDestino() {
		return cantidadKmDestino;
	}

	public String getMatricula() {
		return matricula;
	}

	public String getDestinoAsignado() {
		return destinoAsignado;
	}

	
	protected String mostrarMercaderia() {
		
		StringBuilder s = new StringBuilder("PAQUETES:\n");
		s.append("[");
		//s.append("\n");
		
		for(Paquete p: mercaderia) {
			s.append(p.toString());
			s.append("\n");
		}
		s.append("]");
		
		return s.toString();
	}
	
	public abstract String toString();

}