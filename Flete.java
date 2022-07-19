package tp;

public class Flete extends Transporte {

	private int cantidadAcompaniantes;
	private double costoPorAcompaniante;

	public Flete(String matricula, double cargaMax, double capacidad, double costoKm, int cantidadAcompaniantes,
			double costoPorAcompaniante) {

		
		super(matricula, cargaMax, capacidad, false, 0, 0);
		this.cantidadAcompaniantes = cantidadAcompaniantes;
		this.costoPorAcompaniante = costoPorAcompaniante;
		
		if (cantidadAcompaniantes <= 0) {
			throw new RuntimeException ("NO SE PUEDE CREAR EL FLETE CON LA MATRICULA: "+ matricula + " PORQUE SU CANTIDAD DE ACOMPAÑANTES ES 0 (CERO)");	
		}
		if (costoPorAcompaniante <= 0) {
			throw new RuntimeException ("NO SE PUEDE CREAR EL FLETE CON LA MATRICULA: "+ matricula + " PORQUE SU COSTO POR ACOMPAÑANTES ES 0 (CERO)");	
		}

	}

	public double costoFinal() {

		return (cantidadAcompaniantes * costoPorAcompaniante) + costoPorKm();

	}
	
	public void asignarDestino (String destino, int distancia) {
		
		setcantidadKmDestino(distancia);
		setdestinoAsignado(destino);
		
		}

	public double getCantidadAcompañantes() {
		return cantidadAcompaniantes;
	}

	public double getCostoPorAcompañante() {
		return costoPorAcompaniante;
	}



	@Override
	public String toString() {
		return "FLETE: \n" + "Cantidad de acompaniantes= " + cantidadAcompaniantes
				+ "\nCosto por acompaniante= " + costoPorAcompaniante + "\nCapacidad maxima volumen= " + getCapacidadMaxVolumen()
				+ "\nCapacidad maxima peso= " + getCapacidadMaxPeso() + "\n¿Tiene refrigeracion?= " + getTieneRefrigeracion()
				+ "\nCosto por Km= " + getCostoPorKm() + "\nSeguro de carga= " + getSeguroCarga() + "\n¿Esta en viaje?= " + getenViaje()
				+ "\nCantidad de Km a destino= " + getCantidadKmDestino() + "\nDestino asignado= " + getDestinoAsignado() + "\n"+ mostrarMercaderia()+ "\n\n";
	}

}
