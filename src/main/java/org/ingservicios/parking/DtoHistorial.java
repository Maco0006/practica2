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
	public DtoHistorial(int parkingId, String matricula, Date timestamp){
	this.parkingId = parkingId;
	this.matricula = matricula;
	this.timestamp = timestamp;
	}
	//Constructor 2 con parámetros.
	public DtoHistorial(String parkingId, String matricula, Date timestamp){
	this.parkingId = Integer.parseInt(parkingId);
	this.matricula = matricula;
	this.timestamp = timestamp;
	}
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	public int getParkingId() {
	return parkingId;
	}
	public void setParkingId(int parkingId) {
	this.parkingId = parkingId;
	}
	public String getMatricula() {
	return matricula;
	}
	public void setMatricula(String matricula) {
	this.matricula = matricula;
	}
}
