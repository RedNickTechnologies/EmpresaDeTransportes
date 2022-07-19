package tp;

public class MegaTrailer extends Transporte {

	private double costoFijo;
	private double costoComidaConductor;
	private int minimoDeKm;

	public MegaTrailer(String matricula, double cargaMax, double capacidad, boolean tieneRefrigeracion, double costoKm,
			double seguroCarga, double costoFijo, double costoComida) {
		
		
		super(matricula, cargaMax, capacidad, tieneRefrigeracion, costoKm, seguroCarga);
		this.costoFijo = costoFijo;
		this.costoComidaConductor = costoComida;
		this.minimoDeKm = 501;
		
		if (costoFijo <= 0) {
			throw new RuntimeException ("NO SE PUEDE CREAR EL MEGA TRAILER CON LA MATRICULA: "+ matricula + " PORQUE SU COSTO FIJO DEBE SER MAYOR A 0 (CERO)");
		}
		if (costoComidaConductor <= 0) {
			throw new RuntimeException ("NO SE PUEDE CREAR EL MEGA TRAILER CON LA MATRICULA: "+ matricula + " PORQUE EL COSTO DE COMIDA DEL CONDUCTOR DEBE SER MAYOR A 0 (CERO)");
		}
	}

	public double costoFinal() {

		return costoFijo + costoPorKm() + costoComidaConductor + getSeguroCarga();

	}

	public void asignarDestino (String destino, int distancia) {
		
		if (distancia < minimoDeKm) {
			throw new RuntimeException("Este viaje no se puede asignar al TRAILER: " + getMatricula()
			+ " porque la distancia hasta el destino: " + destino + " es mayor a 500km ");

		} else {
			setcantidadKmDestino(distancia);
			setdestinoAsignado(destino);
		}
	}
	

	public double getCostoFijo() {
		return costoFijo;
	}

	public double getCostoComidaConductor() {
		return costoComidaConductor;
	}

	@Override
	public String toString() {
		return "MEGATRAILER: \n" +  "cantidad minima de Km= " + minimoDeKm + "costo Fijo= " + costoFijo
				+ "\nCosto comida conductor= " + costoComidaConductor + "\nCapacidad maxima volumen= " + getCapacidadMaxVolumen()
				+ "\nCapacidad maxima peso= " + getCapacidadMaxPeso() + "\n¿Tiene refrigeracion?= " + getTieneRefrigeracion()
				+ "\nCosto por Km= " + getCostoPorKm() + "\nSeguro de carga= " + getSeguroCarga() + "\n¿Esta en viaje?= " + getenViaje()
				+ "\nCantidad de Km a destino= " + getCantidadKmDestino() + "\nDestino asignado= " + getDestinoAsignado()+ "\n"+ mostrarMercaderia()+ "\n\n";
	}



}
