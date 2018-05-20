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
	
	public boolean actualizar_coste(String matricula, double importe){
		DtoCoste dtos = leer_matricula(matricula);
		
		if(dtos != null){
			if(dtos.getImporte() != importe){
				System.out.println("Matricula--> "+dtos.getMatricula()+" Importe antiguo: "
						+ dtos.getImporte() + " Importe nuevo: " + importe);
				
				String sql = "update coste set Importe= ? where Matricula= ?";
		
				this.jdbcTemplate.update(sql,importe, matricula);
				return true;
			}else{
				System.out.println("Acaba de estacionar...");
				return false;
			}	
		}else{
			return false;
		}
	}
}
