package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.*;

public class DAOSala 
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

	//----------------------------------------------------------------------------------------------------------------------------------
	// METODOS DE INICIALIZACION
	//----------------------------------------------------------------------------------------------------------------------------------

	/**
	 * Metodo constructor de la clase DAOSala <br/>
	 */
	public DAOSala() {
		recursos = new ArrayList<Object>();
	}

	/**
	 * Metodo que obtiene la informacion de todos los Salaes en la Base de Datos <br/>
	 * <b>Precondicion: </b> la conexion a sido inicializadoa <br/>
	 * @return	lista con la informacion de todos los Salaes que se encuentran en la Base de Datos
	 * @throws SQLException Genera excepcion si hay error en la conexion o en la consulta SQL
	 * @throws Exception Si se genera un error dentro del metodo.
	 */
	public ArrayList<Sala> getSalas() throws SQLException, Exception {
		ArrayList<Sala> salas = new ArrayList<Sala>();

		//Aclaracion: Por simplicidad, solamente se obtienen los primeros 50 resultados de la consulta
		String sql = String.format("SELECT * FROM %1$s.SALA WHERE ROWNUM <= 50", USUARIO);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			salas.add(convertResultSetToSala(rs));
		}
		return salas;
	}


	/**
	 * Metodo que obtiene la informacion del Sala en la Base de Datos que tiene el identificador dado por parametro<br/>
	 * <b>Precondicion: </b> la conexion a sido inicializadoa <br/> 
	 * @param id el identificador del Sala
	 * @return la informacion del Sala que cumple con los criterios de la sentecia SQL
	 * 			Null si no existe el Sala conlos criterios establecidos
	 * @throws SQLException SQLException Genera excepcion si hay error en la conexion o en la consulta SQL
	 * @throws Exception Si se genera un error dentro del metodo.
	 */
	public Sala findSalaById(Integer id) throws SQLException, Exception 
	{
		Sala Sala = null;

		String sql = String.format("SELECT * FROM %1$s.SALA WHERE ID = %2$d", USUARIO, id); 

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if(rs.next()) {
			Sala = convertResultSetToSala(rs);
		}

		return Sala;
	}

	/**
	 * Metodo que agregar la informacion de un nuevo Sala en la Base de Datos a partir del parametro ingresado<br/>
	 * <b>Precondicion: </b> la conexion a sido inicializadoa <br/>  
	 * @param Sala Sala que desea agregar a la Base de Datos
	 * @throws SQLException SQLException Genera excepcion si hay error en la conexion o en la consulta SQL
	 * @throws Exception Si se genera un error dentro del metodo.
	 */
	public void addSala(Sala sala) throws SQLException, Exception {

		String sql = String.format("INSERT INTO %1$s.SALA (COSTO, ID_SALA, PROPOSITO, TIENE_COSTO_ADICIONAL, ID_VIVIENDAU) VALUES (%2$s, %3$s, '%4$s', '%5$s', %6$s)", 
				USUARIO, 
				sala.getCosto(),
				sala.getId(),
				sala.getProposito(),
				sala.toString(sala.isTieneCostoAdicional()), sala.getIdViviendaU() );

		System.out.println(sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}


	/**
	 * Metodo que actualiza la informacion del sala en la Base de Datos que tiene el identificador dado por parametro<br/>
	 * <b>Precondicion: </b> la conexion a sido inicializadoa <br/>  
	 * @param sala Sala que desea actualizar a la Base de Datos
	 * @throws SQLException SQLException Genera excepcion si hay error en la conexion o en la consulta SQL
	 * @throws Exception Si se genera un error dentro del metodo.
	 */
	public void updateSala(Sala sala) throws SQLException, Exception {

		StringBuilder sql = new StringBuilder();
		sql.append (String.format ("UPDATE %s.SALA ", USUARIO));
		sql.append (String.format (
				"SET COSTO = %1$s, ID_SALA = %2$s, PROPOSITO = '%3$s' , TIENE_COSTO_ADICIONAL = '%4$s', ID_VIVIENDAU = %5$s ",
				sala.getCosto(),
				sala.getId(),
				sala.getProposito(),
				sala.toString(sala.isTieneCostoAdicional()), 
				sala.getIdViviendaU()));
		sql.append ("WHERE ID = " + sala.getId());
		System.out.println(sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql.toString());
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	/**
	 * Metodo que actualiza la informacion del sala en la Base de Datos que tiene el identificador dado por parametro<br/>
	 * <b>Precondicion: </b> la conexion a sido inicializadoa <br/>  
	 * @param sala Sala que desea actualizar a la Base de Datos
	 * @throws SQLException SQLException Genera excepcion si hay error en la conexion o en la consulta SQL
	 * @throws Exception Si se genera un error dentro del metodo.
	 */
	public void deleteSala(Sala sala) throws SQLException, Exception {

		String sql = String.format("DELETE FROM %1$s.SALA WHERE ID = %3$d", USUARIO, sala.getId());

		System.out.println(sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}


	//----------------------------------------------------------------------------------------------------------------------------------
	// METODOS AUXILIARES
	//----------------------------------------------------------------------------------------------------------------------------------

	/**
	 * Metodo encargado de inicializar la conexion del DAO a la Base de Datos a partir del parametro <br/>
	 * <b>Postcondicion: </b> el atributo conn es inicializado <br/>
	 * @param connection la conexion generada en el TransactionManager para la comunicacion con la Base de Datos
	 */
	public void setConn(Connection connection){
		this.conn = connection;
	}

	/**
	 * Metodo que cierra todos los recursos que se encuentran en el arreglo de recursos<br/>
	 * <b>Postcondicion: </b> Todos los recurso del arreglo de recursos han sido cerrados.
	 */
	public void cerrarRecursos() {
		for(Object ob : recursos){
			if(ob instanceof PreparedStatement)
				try {
					((PreparedStatement) ob).close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
		}
	}

	/**
	 * Metodo que transforma el resultado obtenido de una consulta SQL (sobre la tabla SalaES) en una instancia de la clase Sala.
	 * @param resultSet ResultSet con la informacion de un Sala que se obtuvo de la base de datos.
	 * @return Sala cuyos atributos corresponden a los valores asociados a un registro particular de la tabla SalaES.
	 * @throws SQLException Si existe algun problema al extraer la informacion del ResultSet.
	 */
	public Sala convertResultSetToSala(ResultSet resultSet) throws SQLException {
		//TODO Requerimiento 1G: Complete el metodo con los atributos agregados previamente en la clase Sala. 
		//						 Tenga en cuenta los nombres de las columnas de la Tabla en la Base de Datos (ID, NOMBRE, PRESUPUESTO, CIUDAD)

		boolean rta1 = false;
		boolean rta2 = false;
		boolean rta3 = false;
		
		String camara = resultSet.getString("TIENE_COSTO_ADICIONAL");

		if(camara.equals("T"))
		{
			rta1 = true;
		}
		String proposito = resultSet.getString("PROPOSITO");
		String costo = resultSet.getString("COSTO");
		String idSala = resultSet.getString("ID_SALA");
		String idViviendaU = resultSet.getString("ID_VIVIENDAU");


		Sala beb = new Sala(Double.parseDouble(costo), Integer.parseInt(idSala), proposito, rta1, Integer.parseInt(idViviendaU));

		return beb;
	}

}
