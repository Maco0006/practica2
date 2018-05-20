package org.ingservicios.parking;

public class DtoCoste {
	String matricula;
	double importe;
	
	public DtoCoste(String matricula, double importe) {
		this.matricula = matricula;
		this.importe = importe;
	}
	
	public DtoCoste() {
		this.matricula = "";
		this.importe = 0;
	}

	public String getMatricula() {
		return matricula;
	}
	
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
	
	public double getImporte() {
		return importe;
	}
	
	public void setImporte(double importe) {
		this.importe = importe;
	}
}
