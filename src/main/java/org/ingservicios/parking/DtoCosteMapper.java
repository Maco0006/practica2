package org.ingservicios.parking;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class DtoCosteMapper implements RowMapper<DtoCoste> {
	
	public DtoCoste mapRow(ResultSet rs, int rowNum) throws SQLException{
		
		DtoCoste dto = new DtoCoste();
		dto.setMatricula(rs.getString("Matricula"));
		dto.setImporte(rs.getDouble("Importe"));
		return dto;
		
	}
}
