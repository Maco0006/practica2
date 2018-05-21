package org.ingservicios.parking;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

public class DaoCoste implements DaoCoste_Interface {
	
	private JdbcTemplate jdbcTemplate;//Para trabajar con JDBC que nos proporciona spring
	private DataSource dataSource;
	
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	public DtoCoste leer_matricula(String matricula){
		
		String sql = "select * from coste where matricula = ? ";
		
		//Parámetros para la consulta
		Object[] parametros = {matricula}; //Necesaria para query
		
		DtoCosteMapper mapper = new DtoCosteMapper();
		
		List<DtoCoste> matriculas = this.jdbcTemplate.query(sql,parametros,mapper);
		
		if (matriculas.isEmpty()){
			//Devuelve null
			System.out.println("Matricula no presente en la BBDD");
			return null;
		}else{//Si hay
			//Devolvemos el primer objeto de la lista, que será el buscado
			System.out.println("Matricula presente en la BBDD");
			return matriculas.get(0);
		}
	}
	
	public void insertar_matricula(DtoHistorial dto){
		//Sentencia SQL
		String sql = "insert into coste (Matricula) values(?)";
				
		//Indicamos los parametros (tipo de objeto generico), para la consulta
		//Cuidado con el orden de parámetros, ya que la base de datos puede interpretar mal
		Object[] parametros ={dto.getMatricula()};
		this.jdbcTemplate.update(sql,parametros);
	}
	
	public boolean actualizar_coste(String matricula, double importe){
		DtoCoste dtos = leer_matricula(matricula);
		
		if(dtos != null){
			if(dtos.getImporte() < importe){
				
				String sql = "update coste set Importe= ? where Matricula= ?";
				
				Object[] parametros ={importe,dtos.getMatricula()};
				
				this.jdbcTemplate.update(sql,parametros);
				
				System.out.println("Matricula--> "+dtos.getMatricula()+" Importe inicial: "
						+ dtos.getImporte() + " Importe final: " + importe);
				return true;
			}else{
				String sql = "update coste set Importe= ? where Matricula= ?";
				
				Object[] parametros ={importe,dtos.getMatricula()};
				
				this.jdbcTemplate.update(sql,parametros);
				
				System.out.println("Acaba de estacionar...");
				return false;
			}	
		}else{
			return false;
		}
	}
}
