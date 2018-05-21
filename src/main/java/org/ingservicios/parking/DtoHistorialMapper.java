package org.ingservicios.parking;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class DtoHistorialMapper implements RowMapper<DtoHistorial> {
	
	/**
	 * Método para mapear los atributos del Objeto DtoUsuario con los de la tabla de la base
	de datos
	 */
	public DtoHistorial mapRow(ResultSet rs, int rowNum) throws SQLException{
		
		DtoHistorial dto = new DtoHistorial();
		dto.setParkingId(rs.getInt("ParkingId"));
		dto.setMatricula(rs.getString("Matricula"));
		dto.setTimestamp(rs.getTimestamp("TimeStamp"));
		return dto;
		
	}
}
