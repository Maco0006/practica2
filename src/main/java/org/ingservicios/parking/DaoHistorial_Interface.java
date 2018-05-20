package org.ingservicios.parking;

public interface DaoHistorial_Interface {
	
	public void insertar(DtoHistorial dto);
	
	public DtoHistorial buscar_matricula(String matricula);
}
