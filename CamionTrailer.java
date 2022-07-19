package tp;

public class CamionTrailer extends Transporte {

	private int limiteKm;

	public CamionTrailer(String matricula, double cargaMax, double capacidad, boolean tieneRefrigeracion,
			double costoKm, double seguroCarga) {

		super(matricula, cargaMax, capacidad, tieneRefrigeracion, costoKm, seguroCarga);
		this.limiteKm = 500;

	}


	public double costoFinal() {
		return (costoPorKm() + getSeguroCarga());

	}
	
	public void asignarDestino (String destino, int distancia) {
		
		if (distancia > limiteKm) {
			throw new RuntimeException("Este viaje no se puede asignar a un TRAILER: " + getMatricula()
			+ " porque la distancia hasta el destino: " + destino + " es mayor a 500km ");

		} else {
			setcantidadKmDestino(distancia);
			setdestinoAsignado(destino);
		}
	}






	@Override
	public String toString() {
		
		return "CAMIONTRAILER: \n"+ "limite de Km= " + limiteKm + "\nCapacidad maxima volumen= "
				+ getCapacidadMaxVolumen() + "\nCapacidad maxima peso= " + getCapacidadMaxPeso() + "\n¿Tiene refrigeracion?= "
				+ getTieneRefrigeracion() + "\nCosto por Km= " + getCostoPorKm() + "\nSeguro de carga= " + getSeguroCarga()
				+ "\n¿Esta en viaje?= " + getenViaje() + "\nCantidad de Km a destino= " + getCantidadKmDestino() + "\nDestino asignado= "
				+ getDestinoAsignado()  +"\n"+ mostrarMercaderia()+ "\n\n";
		
	}

}
