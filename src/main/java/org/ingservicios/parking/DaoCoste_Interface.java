package org.ingservicios.parking;

public interface DaoCoste_Interface {

	public DtoCoste leer_matricula(String matricula);
	
	public boolean actualizar_coste(String matricula, double importe);
	
}
