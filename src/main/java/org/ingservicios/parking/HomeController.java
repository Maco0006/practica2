package org.ingservicios.parking;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.ingservicios.parking.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private final double tarifa = 0.5/3600000; //0.5 € la hora
	/**
	* @param resultados de tipo HttpStatus para definir el estado de la respuesta
	* Códigos: 201, 208, 500, 400, 503 (RFC 7231)
	*/
	private static final HttpStatus[] estados = {HttpStatus.CREATED,HttpStatus.ALREADY_REPORTED,
		HttpStatus.INTERNAL_SERVER_ERROR,HttpStatus.BAD_REQUEST,HttpStatus.SERVICE_UNAVAILABLE};
	
	@Autowired
	DaoHistorial daoh;
	
	@Autowired
	DaoCoste daoc;
	
	/**
	* Recurso por defecto, devuelve una página web, que simula el cliente RaspBerry
	* Método Get
	*
	* @return home, de tipo String y pertenece al nombre del JSP.
	*/
	@RequestMapping(value = {"/","/testparking"}, method = RequestMethod.GET)
	public String home() {
		return "testparking";
	}
	
	/**
	*
	* @param datosCliente de tipo DtoRaspberry en formato JSON
	* @return resputa de tipo ResponseEntity<DtoRaspberry>, se modifica el estado de la
	respuesta y los datos esta codfiicados en JSON
	* @throws ParseException
	*/
	
	
	@RequestMapping(value = "/registroMatricula", method = RequestMethod.POST)
	public ResponseEntity<DtoHistorial> matricula(@RequestBody DtoHistorial historial) 
			throws ParseException {
		
		//Estado de la respuesta por defecto
		HttpStatus estado = estados[4];
		System.out.println("ParkingId: "+ historial.getParkingid()+"Matricula: "
				+ historial.getMatricula());
		//Cabeceras
		HttpHeaders cabeceras = new HttpHeaders();
		
		//Obtenemos la marca de tiempo y extraemos los datos
		Date fecha = new Date();
		
		//Obtenemos el timestamp
		SimpleDateFormat formatofecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = formatofecha.parse(formatofecha.format(fecha));

		int parkingId = historial.getParkingid();
		String matricula = historial.getMatricula();
		DtoHistorial dtomap = new DtoHistorial(parkingId,matricula,date);
		
		switch (parkingId){
			case 0:
				//Comprobamos si existe el vehículo (devuelve última vez que entró)
				DtoHistorial coche = daoh.buscar_matricula(matricula);
				Boolean existe;
				if(coche==null){
					existe = false;
				}else{
					if(coche.getParkingid()==1){
						existe = true;
					}else{
						existe = false;
					}
				}
				
				
				//Si el coche no está dentro del parking y está en tabla credito
				if(!existe){
					//Introducimos en la base de datos
					daoh.insertar(dtomap);
					//Devuelvo la respuesta con Status created
					estado = estados[0];
				}else{
					//Ya introducido
					estado = estados[1];
					System.out.println("No puede entrar dos veces.");
				}
				break;
			
			case 1:
				//Comprobamos si existe el vehículo (devuelve última vez que entró)
				DtoHistorial vehiculo = daoh.buscar_matricula(matricula);
				
				if(vehiculo!=null){
					//Si no sale dos veces seguidas
					if(vehiculo.getParkingid()!=1){
						//Obtenemos el tiempo de estacionamiento en milisegundos
						long tiempo = date.getTime() - vehiculo.getTimestamp().getTime();
						//Si no se produjo error al obtener el tiempo de estacionamiento
						if(tiempo>0){
							//Actualizamos el precio
							Boolean exito = daoc.actualizar_coste(matricula, tiempo * tarifa);
							if(!exito){
								//Fallo al actualizar en tabla coste
								estado = estados[4];
							}else{
								//Registramos la salida en la tabla de historial
								daoh.insertar(dtomap);
								//Devolvemos la respuesta con OK
								estado = estados[0];
							}
						}else{
							//Se produjo un error al obtener el tiempo de estacionamiento 
							// Internal error
							
							estado = estados[2];
							System.out.println("Error al obtener el tiempo");
						}
					}
					else{
						//El vehiculo tiene registrado una entrada con una salida.
						//Ya introducido
						estado = estados[1];
						System.out.println("No puede salir dos veces");
					}
				}
				else{
					//No tiene registrado ninguna entrada el vehículo
					//Bad request
					estado = estados[3];
					System.out.println("El vehiculo no esta en la tabla de historial.");
				}
				
				break;
				
			default:
				//No podemos atender a esta petición
				//Bad request
				estado = estados[3];
				System.out.println("ParkingID no registrado");
				break;
		}
		ResponseEntity<DtoHistorial> respuesta = new
		ResponseEntity<DtoHistorial>(historial,cabeceras,estado);
		return respuesta;
	}
}
