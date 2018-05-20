package org.ingservicios.parking;

import java.util.Date;
import java.text.SimpleDateFormat;

public class DtoHistorial {
	
	private int parkingId;
	private String matricula;
	private Date timestamp;
	
	//Constructor por defecto.
	public DtoHistorial() {
	this.parkingId = 0;
	this.matricula = "";
	this.timestamp = null;
	}
	//Constructor 1 con parámetros.
	public DtoHistorial(int parkingid, String matricula, Date timestamp){
	this.parkingId = parkingid;
	this.matricula = matricula;
	this.timestamp = timestamp;
	}
	//Constructor 2 con parámetros.
	public DtoHistorial(String parkingid, String matricula, Date timestamp){
	this.parkingId = Integer.parseInt(parkingid);
	this.matricula = matricula;
	this.timestamp = timestamp;
	}
	//Métodos de acceso.
	public Date getTimestamp() {
	return timestamp;
	}
	public void setTimestamp(Date timestamp) {
	this.timestamp = timestamp;
	}
	public int getParkingid() {
	return parkingId;
	}
	public void setParkingid(int parkingid) {
	this.parkingId = parkingid;
	}
	public String getMatricula() {
	return matricula;
	}
	public void setMatricula(String matricula) {
	this.matricula = matricula;
	}
}
