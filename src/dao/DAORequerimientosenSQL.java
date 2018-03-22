package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Habitacion;
import vos.Oferta;
import vos.VOExtraHotel;
import vos.VOExtraPersona;
import vos.VOExtraViviendaUniversitaria;
import vos.VOHostalExtra;

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
	
	//TODO TODO DE LOS OPERADORES
	
	public ArrayList<VOHostalExtra> getCosasHostal() throws SQLException, Exception {
		ArrayList<VOHostalExtra> habitacions = new ArrayList<VOHostalExtra>();

		//Aclaracion: Por simplicidad, solamente se obtienen los primeros 50 resultados de la consulta
		String sql = String.format(	"SELECT ID_HOSTAL, VALOR\r\n" + 
				"FROM RESERVA\r\n" + 
				"WHERE ID_HOSTAL IS NOT NULL AND CONFIRMADA = 'T';", USUARIO);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			habitacions.add(convertResultSetToVOExtra(rs));
		}
		return habitacions;
	}



	public ArrayList<VOExtraHotel> getCosasHotel() throws SQLException, Exception {
		ArrayList<VOExtraHotel> habitacions = new ArrayList<VOExtraHotel>();

		//Aclaracion: Por simplicidad, solamente se obtienen los primeros 50 resultados de la consulta
		String sql = String.format(	"SELECT ID_HOSTAL, VALOR\r\n" + 
				"FROM RESERVA\r\n" + 
				"WHERE ID_HOSTAL IS NOT NULL AND CONFIRMADA = 'T';", USUARIO);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			habitacions.add(convertResultSetToExtraHotel(rs));
		}
		return habitacions;
	}
	
	public ArrayList<VOExtraPersona> getCosasPersona() throws SQLException, Exception {
		ArrayList<VOExtraPersona> habitacions = new ArrayList<VOExtraPersona>();

		//Aclaracion: Por simplicidad, solamente se obtienen los primeros 50 resultados de la consulta
		String sql = String.format(	"SELECT ID_PERSONA, VALOR\r\n" + 
				"FROM RESERVA\r\n" + 
				"WHERE ID_PERSONA IS NOT NULL AND CONFIRMADA = 'T';", USUARIO);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			habitacions.add(convertResultSetToExtraPersona(rs));
		}
		return habitacions;
	}
	
	
	public ArrayList<VOExtraViviendaUniversitaria> getCosasViviendaUniversitaria() throws SQLException, Exception {
		ArrayList<VOExtraViviendaUniversitaria> habitacions = new ArrayList<VOExtraViviendaUniversitaria>();

		//Aclaracion: Por simplicidad, solamente se obtienen los primeros 50 resultados de la consulta
		String sql = String.format(	"SELECT ID_PERSONA, VALOR\r\n" + 
				"FROM RESERVA\r\n" + 
				"WHERE ID_PERSONA IS NOT NULL AND CONFIRMADA = 'T';", USUARIO);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			habitacions.add(convertResultSetToExtraViviendaUniversitaria(rs));
		}
		return habitacions;
	}
	
	
	
	private VOExtraPersona convertResultSetToExtraPersona(ResultSet rs) throws SQLException {
		// TODO Auto-generated method stub
		String nombre = rs.getString("valor");
		String idHostal = rs.getString("ID_PERSONA");

		VOExtraPersona rta;
		return rta = new VOExtraPersona(Integer.parseInt(idHostal), Integer.parseInt(nombre));
	}
	
	
	
	
	
	

	private VOExtraViviendaUniversitaria convertResultSetToExtraViviendaUniversitaria(ResultSet rs) throws SQLException {
		// TODO Auto-generated method stub
		String nombre = rs.getString("valor");
		String idHostal = rs.getString("ID_VIVIENDAUNIVERSITARIA");

		VOExtraViviendaUniversitaria rta;
		return rta = new VOExtraViviendaUniversitaria(Integer.parseInt(idHostal), Integer.parseInt(nombre));
	}
	
	
	private VOHostalExtra convertResultSetToVOExtra(ResultSet rs) throws SQLException {
		// TODO Auto-generated method stub
		String nombre = rs.getString("valor");
		String idHostal = rs.getString("ID_HOSTAL");

		VOHostalExtra rta;
		return rta = new VOHostalExtra(Integer.parseInt(idHostal), Integer.parseInt(nombre));
	}
	
	
	
	private VOExtraHotel convertResultSetToExtraHotel(ResultSet rs) throws SQLException 
	{
		// TODO Auto-generated method stub

		String nombre = rs.getString("valor");
		String idHostal = rs.getString("ID_Hotel");
		
		VOExtraHotel rta;
		return rta = new VOExtraHotel(Integer.parseInt(idHostal), Integer.parseInt(nombre));
		
	}


	//TODO EXTRAS PARA QUE FUNCIONE
	
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
