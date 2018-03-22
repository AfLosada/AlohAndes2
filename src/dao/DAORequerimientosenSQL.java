package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Habitacion;
import vos.Oferta;

public class DAORequerimientosenSQL 
{
	//----------------------------------------------------------------------------------------------------------------------------------
	// CONSTANTES
	//----------------------------------------------------------------------------------------------------------------------------------

	/**
	 * Constante para indicar el usuario Oracle del estudiante
	 */
	//TODO Requerimiento 1H: Modifique la constante, reemplazando al ususario PARRANDEROS por su ususario de Oracle
	public final static String USUARIO = "ISIS2304A811810";

	//----------------------------------------------------------------------------------------------------------------------------------
	// ATRIBUTOS
	//----------------------------------------------------------------------------------------------------------------------------------

	/**
	 * Arraylits de recursos que se usan para la ejecucion de sentencias SQL
	 */
	private ArrayList<Object> recursos;

	/**
	 * Atributo que genera la conexion a la base de datos
	 */
	private Connection conn;
	
	public DAORequerimientosenSQL() {
		recursos = new ArrayList<Object>();
	}
	
	
	//TODO 20 Ofertas más reservadas
	
	public ArrayList<Oferta> get20Ofertas() throws SQLException, Exception {
		ArrayList<Oferta> habitacions = new ArrayList<Oferta>();

		//Aclaracion: Por simplicidad, solamente se obtienen los primeros 50 resultados de la consulta
		String sql = String.format(	"SELECT *" + "FROM (SELECT *" + "FROM OFERTA" + "ORDER BY NUM_RESERVAS DESC)" + "WHERE ROWNUM <= 20", USUARIO);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			habitacions.add(convertResultSetToOferta(rs));
		}
		return habitacions;
	}
	
	public Oferta convertResultSetToOferta(ResultSet resultSet) throws SQLException {
		//TODO Requerimiento 1G: Complete el metodo con los atributos agregados previamente en la clase Oferta. 
		//						 Tenga en cuenta los nombres de las columnas de la Tabla en la Base de Datos (ID, NOMBRE, PRESUPUESTO, CIUDAD)

		boolean rta1 = false;
		boolean rta2 = false;
		boolean rta3 = false;
		
		String id = resultSet.getString("ID_OFERTA");

		String vigente = resultSet.getString("VIGENTE");

		if(vigente.equals("1"))
		{
			rta1 = true;
		}
		String nombre = resultSet.getString("NUM_RESERVAS");
		String idHostal = resultSet.getString("ID_HOSTAL");
		String idPersona = resultSet.getString("ID_PERSONA");
		String idHotel = resultSet.getString("ID_HOTEL");
		String idViviendaU = resultSet.getString("ID_VIVIENDAU");
		
		Integer rta4 = null;
		Integer rta5 = null;
		Integer rta6 = null;
		Integer rta7 = null;
		Integer rta8 = null;
		
		if(idHotel != null )
		{
			rta4 = Integer.parseInt(idHotel);
		}
		if(idHostal != null )
		{
			rta5 = Integer.parseInt(idHostal);
		}
		if(idPersona != null )
		{
			rta6 = Integer.parseInt(idPersona);
		}
		if(idViviendaU != null )
		{
			rta7 = Integer.parseInt(idViviendaU);
		}
		
		

		Oferta beb = new Oferta(Integer.parseInt(id), Integer.parseInt(nombre), rta1, rta4, rta6, rta5, rta7);

		return beb;
	}
	
	
}
