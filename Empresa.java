package tp;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;

public class Empresa {

	private String cuit;
	private String nombre;
	private Deposito depositoFrio;
	private Deposito depositoComun;
	
	private HashMap<String, Integer> destinos = new HashMap<String, Integer>();
	private HashMap<String, String> viajes = new HashMap<String, String>();
	private HashMap<String, Transporte> transportes = new HashMap<String, Transporte>();

	public Empresa(String cuit, String nombre, int capMax) {

		if (capMax <= 0) {
			throw new RuntimeException("NO SE PUDE CREAR LA EMPRESA CON EL NOMBRE: " + nombre
					+ " PORQUE LA CAPACIDAD DE SUS DEPOSITOS DEBE SER MAYOR A 0 (CERO)");
		}

		this.nombre = nombre;
		this.cuit = cuit;
		depositoComun = new Deposito(capMax, false);
		depositoFrio = new Deposito(capMax, true);

	}

	public boolean incorporarPaquete(String destino, double peso, double volumen, boolean refrigeracion) { // incorpora
																											// los
																											// paquetes

																											// a sus

																											// correspondiente
																											// depositos

		Paquete paquete = new Paquete(destino, peso, volumen, refrigeracion);
		Deposito dep = getDeposito(refrigeracion);
		return dep.agregarPaquete(paquete);

	}
	
	private Deposito getDeposito(boolean frio) {
		return (frio?depositoFrio:depositoComun);
		}
	

	public double obtenerCostoViaje(String matricula) { // obtiene el costo de viaje de los transportes siempre y cuando
														// esten en viaje

		if (existeMatricula(matricula)) {

			if (!transportes.get(matricula).getenViaje()) {

				throw new RuntimeException("NO se puede obtener el costo del camion porque NO esta en viaje");
			}

			else {
				return transportes.get(matricula).costoFinal();
			}
		} else {
			throw new RuntimeException(
					"NO SE PUEDE OBTENER EL COSTO DEL VIAJE PORQUE NO EXISTE LA MATRICULA: " + matricula);

		}

	}

	public void agregarDestino(String destino, int cantidadKm) { // agrega un destino junto con su cantidad de km a la
																	// coleccion de destinos, si el destino esta
																	// repetido no lo agrega

		if (destinos.containsKey(destino)) {
			
				throw new RuntimeException(
						"NO se puede agregar el destino porque ya existe el destino: " + destino);
				
		} else {
			destinos.put(destino, cantidadKm);
		}
		
}

	public void agregarTrailer(String matricula, double peso, double volumen, boolean refrigeracion, double costoKm, // crea
																														// los
																														// transportes
																														// siempre
																														// y
																														// cuando
																														// sus
																														// matriculas
																														// no
																														// hayan
																														// sido
																														// agregadas
																														// previamente
			double segCarga) {

		if (existeMatricula(matricula)) {
			throw new RuntimeException(
					"NO SE PUEDE CREAR EL TRANSPORTE CON LA SIGUIENTE MATRICULA " + matricula + " PORQUE YA EXISTE");
		}

		CamionTrailer trailer = new CamionTrailer(matricula, peso, volumen, refrigeracion, costoKm, segCarga);
		transportes.put(matricula, trailer);

	}

	public void agregarMegaTrailer(String matricula, double peso, double volumen, boolean refrigeracion, double costoKm, // crea
																															// los
																															// transportes
																															// siempre
																															// y
																															// cuando
																															// sus
																															// matriculas
																															// no
																															// hayan
																															// sido
																															// agregadas
																															// previamente
			double segCarga, double costoFijo, double costoComida) {

		if (existeMatricula(matricula)) {
			throw new RuntimeException(
					"NO SE PUEDE CREAR EL TRANSPORTE CON LA SIGUIENTE MATRICULA " + matricula + " PORQUE YA EXISTE");

		}

		MegaTrailer mega = new MegaTrailer(matricula, peso, volumen, refrigeracion, costoKm, segCarga, costoFijo,
				costoComida);
		transportes.put(matricula, mega);
	}

	public void agregarFlete(String matricula, double peso, double volumen, double costoKm, int cantidadAcompaniantes, // crea
																														// los
																														// transportes
																														// siempre
																														// y
																														// cuando
																														// sus
																														// matriculas
																														// no
																														// hayan
																														// sido
																														// agregadas
																														// previamente
			int costoPorAcompaniante) {

		if (existeMatricula(matricula)) {
			throw new RuntimeException(
					"NO SE PUEDE CREAR EL TRANSPORTE CON LA SIGUIENTE MATRICULA " + matricula + " PORQUE YA EXISTE");

		}

		Flete flete = new Flete(matricula, peso, volumen, costoKm, cantidadAcompaniantes, costoPorAcompaniante);
		transportes.put(matricula, flete);

	}

	public void asignarDestino(String matricula, String destino) {// asigna los destinos (que fueron previamente
																	// agregados a la lista de viajes) a los transportes
																	// y y saca la cantidad de km hasta el destino para
																	// calcular el costo del viaje
																	// y con la cantidad de distancia en km hasta el
																	// destino calcula si dicho
																	// trnasporte puede hacer el viaje

		if (destinos.containsKey(destino)) {
			
			if (existeMatricula(matricula)) {
				
				asignarDestino(matricula, destino, destinos.get(destino));
				//transportes.get(matricula).asignarDestino(destino, destinos.get(destino));
				viajes.put(matricula, destino );
				
			}
			
		} else {
			throw new RuntimeException(
					"NO SE PUEDE ASIGNAR EL DESTINO PORQUE NO EXISTE LA MATRICULA: " + matricula);
		}  

	}

	public double cargarTransporte(String matricula) {// carga el trasnporte de acuerdo a si tiene refrigeracion o no
														// para cargar un transporte se fija que exista la matricula, si
														// tiene o no refrigeracion, si tiene un destino asignado y NO
														// esta en viaje
		double volumen = 0;
		double peso = 0;

		LinkedList<Paquete> paquetesDeposito;

		if (existeMatricula(matricula)) {

				if (!camionEnViaje(matricula) && destinoAsignado(matricula) != null) {
					
					Deposito dep = getDeposito(tieneRefrigeracion(matricula));

					paquetesDeposito = dep.separarPaquetes(destinoAsignado(matricula));
					
					for (Paquete p : paquetesDeposito) {

						if ((peso + p.getPeso()) <= capacidadMaxPeso(matricula)
								&& volumen + (p.getVolumen()) <= capacidadMaxVolumen(matricula)) {

							agregarMercaderia(matricula, p);
							peso = peso + p.getPeso();
							volumen = volumen + p.getVolumen();
							dep.eliminarPaquete(p);
						}
					}
				}

				else if (camionEnViaje(matricula)) {
					throw new RuntimeException("NO SE PUEDE CARGAR EL TRANSPORTE: " + matricula
							+ " PORQUE ACTUALMENTE SE ENCUENTRA EN VIAJE");

				} else if (destinoAsignado(matricula) == null) {

					throw new RuntimeException("NO SE PUEDE CARGAR EL TRANSPORTE: " + matricula
							+ " PORQUE ACTUALMENTE NO SE ENCUENTRA CON UN DESTINO ASIGNADO");
				}
			} else {
				throw new RuntimeException("NO SE PUEDE CARGAR EL CAMION PORQUE NO EXISTE LA MATRICULA: " + matricula);
			}
		return volumen;
	}


	public void iniciarViaje(String matricula) {// inicia el viaje de la matricula solicitada
												// si el camion ya esta en viaje o no tiene mercaderia genera una
												// excepcion

		if (existeMatricula(matricula)) {
			transportes.get(matricula).iniciarViaje(matricula);
		} else {
			throw new RuntimeException(
					"NO SE PUEDE INICIAR EL VIAJE DEL CAMION SOLICITADO PORQUE NO EXISTE LA MATRICULA: " + matricula);

		}

	}

	public void finalizarViaje(String matricula) {// finaliza el viaje solo si el camion ya incializo su viaje
													// llama a descargar que vacia la mercaderia del camion (lista del
													// camion) y
													// setea que el camion no se encuentra en viaje

		if (existeMatricula(matricula)) {
			transportes.get(matricula).finalizarViaje(matricula);
			viajes.remove(matricula);
		}

		else {
			throw new RuntimeException(
					"NO SE PUEDE FINALIZAR EL VIAJE DEL CAMION SOLICITADO PORQUE NO EXISTE LA MATRICULA: " + matricula);
		}
		
	}

	public String obtenerTransporteIgual(String matricula) { // verifica que la matricula exista, luego recorre las
																// listas de todos los transportes y verifica con equals
																// si hay otro igual


		if (existeMatricula(matricula)) {
			
			Transporte transporte = transportes.get(matricula);
			
			for (Transporte t:transportes.values()) {
				
				if (transporte.getMatricula() != t.getMatricula()) {
					
					if (t.equals(transporte)) {
						return t.getMatricula();
					}
				}

			}
			
		} else {
			throw new RuntimeException(
			"NO SE PUEDE OBTENER UN TRANSPORTE IGUAL PORQUE NO EXISTE LA MATRICULA: " + matricula);
		}
		
		return null;
	}


	private boolean existeMatricula(String matricula) {
		return transportes.containsKey(matricula);
	}

	private boolean tieneRefrigeracion(String matricula) {
		return transportes.get(matricula).getTieneRefrigeracion();
	}

	private boolean camionEnViaje(String matricula) {
		return transportes.get(matricula).getenViaje();
	}

	private String destinoAsignado(String matricula) {
		return transportes.get(matricula).getDestinoAsignado();
	}

	private double capacidadMaxPeso(String matricula) {
		return transportes.get(matricula).getCapacidadMaxPeso();
	}

	private double capacidadMaxVolumen(String matricula) {
		return transportes.get(matricula).getCapacidadMaxVolumen();
	}

	private void agregarMercaderia(String matricula, Paquete p) {
		transportes.get(matricula).agregarMercaderia(p);

	}

	private int cantidadTransportes() {
		return transportes.size();
	}
	
	private void asignarDestino(String matricula, String destino, int distancia) {
		transportes.get(matricula).asignarDestino(destino, distancia);
	}


	public String mostrarTransporte (String matricula) {

		if (existeMatricula(matricula)) {
			
			return transportes.get(matricula).toString();
		} else {
			throw new RuntimeException(
					"NO SE PUEDE VER LOS DATOS DEL TRANSPORTE PORQUE NO EXISTE SU MATRICULA: " + matricula);
		}

	}
	
	public String mostrarDeposito(boolean deposito) {
		Deposito dep = getDeposito(deposito);
		
		return dep.toString();
	}

	public String toString() {

		StringBuilder sb = new StringBuilder();
		sb.append("EMPRESA: ");
		sb.append(nombre);
		sb.append("\n");
		sb.append("Cuit: ");
		sb.append(cuit);
		sb.append("\n");
		
		sb.append("Capacidad depositos: ");
		sb.append(depositoFrio.getCapacidadMaxDeposito());
		sb.append("\n");
		sb.append("Capacidad deposito frio: ");
		sb.append(depositoFrio.getCapacidadActual());
		sb.append("\n");
		sb.append("Capacidad deposito comun: ");
		sb.append(depositoComun.getCapacidadActual());
		sb.append("\n");
		sb.append("\n");
		
		sb.append("VIAJES:\n");
		sb.append(viajes);
		sb.append("\n");
		sb.append("Cantidad de transportes: ");
		sb.append(cantidadTransportes());
		sb.append("\n");
		
		sb.append(" \n	TRANSPORTES:\n" + transportes + "\n");


		return sb.toString();
	}

}