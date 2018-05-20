package org.ingservicios.parking;

import java.util.List;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;

public class DaoHistorial implements DaoHistorial_Interface {
	
	private JdbcTemplate jdbcTemplate;//Para trabajar con JDBC que nos proporciona spring
	private DataSource dataSource;
	
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	public void insertar(DtoHistorial dto){
		//Sentencia SQL
		String sql = "insert into historial values(?,?,?,?)";
		
		//Indicamos los parametros (tipo de objeto generico), para la consulta
		//Cuidado con el orden de parámetros, ya que la base de datos puede interpretar mal
		Object[] parametros ={0,dto.getParkingid(),dto.getMatricula(),dto.getTimestamp()};
		this.jdbcTemplate.update(sql,parametros);
	}
	
	public DtoHistorial buscar_matricula(String matricula){
		boolean resul=false;
		
		//Sentencia SQL
		String sql= "select * from historial where matricula = ? ";
		
		//Parámetros para la consulta
		Object[] parametros = {matricula}; //Necesaria para query
		
		//Instancia de la clase de mapeo del usuario
		DtoHistorialMapper mapper = new DtoHistorialMapper();
		
		//Devuelve los usuarios con el nombre
		List<DtoHistorial> listavehiculo = this.jdbcTemplate.query(sql, parametros, mapper);
		
		//Si no hay vehículos
		if (listavehiculo.isEmpty()){
			//Devuelve null
			return null;
		}else{//Si hay
			return listavehiculo.get(listavehiculo.size()-1);
		}
	}
}
