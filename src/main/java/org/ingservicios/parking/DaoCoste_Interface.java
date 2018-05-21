package org.ingservicios.parking;

public interface DaoCoste_Interface {

	public DtoCoste leer_matricula(String matricula);
		
	public void insertar_matricula(DtoHistorial dto);
	
	public boolean actualizar_coste(String matricula, double importe);
	
}
