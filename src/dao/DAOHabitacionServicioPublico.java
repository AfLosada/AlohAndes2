package dao;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import vos.HabitacionesServicioPublico;

public class DAOHabitacionServicioPublico 
{
	//----------------------------------------------------------------------------------------------------------------------------------
	// CONSTANTES
	//----------------------------------------------------------------------------------------------------------------------------------

	/**
	 * Constante para indicar el usuario Oracle del estudiante
	 */
	//TODO Requerimiento 1H: Modifique la constante, reemplazando al ususario PARRANDEROS por su ususario de Oracle
	public final static String USUARIO = "ISIS2304A881810";

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
	 * Metodo constructor de la clase DAOHabitacionesServicioPublico <br/>
	 */
	public DAOHabitacionServicioPublico() {
		recursos = new ArrayList<Object>();
	}

	/**
	 * Metodo que obtiene la informacion de todos los HabitacionesServicioPublicoes en la Base de Datos <br/>
	 * <b>Precondicion: </b> la conexion a sido inicializadoa <br/>
	 * @return	lista con la informacion de todos los HabitacionesServicioPublicoes que se encuentran en la Base de Datos
	 * @throws SQLException Genera excepcion si hay error en la conexion o en la consulta SQL
	 * @throws Exception Si se genera un error dentro del metodo.
	 */
	public ArrayList<HabitacionesServicioPublico> getHabitacionesServicioPublicos() throws SQLException, Exception {
		ArrayList<HabitacionesServicioPublico> habitacionesServicioPublicos = new ArrayList<HabitacionesServicioPublico>();

		//Aclaracion: Por simplicidad, solamente se obtienen los primeros 50 resultados de la consulta
		String sql = String.format("SELECT * FROM %1$s.HABITACIONES_SERVICIOSPUBLICOS WHERE ROWNUM <= 50", USUARIO);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			habitacionesServicioPublicos.add(convertResultSetToHabitacionesServicioPublico(rs));
		}
		return habitacionesServicioPublicos;
	}


	/**
	 * Metodo que obtiene la informacion del HabitacionesServicioPublico en la Base de Datos que tiene el identificador dado por parametro<br/>
	 * <b>Precondicion: </b> la conexion a sido inicializadoa <br/> 
	 * @param id el identificador del HabitacionesServicioPublico
	 * @return la informacion del HabitacionesServicioPublico que cumple con los criterios de la sentecia SQL
	 * 			Null si no existe el HabitacionesServicioPublico conlos criterios establecidos
	 * @throws SQLException SQLException Genera excepcion si hay error en la conexion o en la consulta SQL
	 * @throws Exception Si se genera un error dentro del metodo.
	 */
	public HabitacionesServicioPublico findHabitacionesServicioPublicoById(Integer id) throws SQLException, Exception 
	{
		HabitacionesServicioPublico HabitacionesServicioPublico = null;

		String sql = String.format("SELECT * FROM %1$s.HABITACIONES_SERVICIOSPUBLICOS WHERE ID = %2$d", USUARIO, id); 

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if(rs.next()) {
			HabitacionesServicioPublico = convertResultSetToHabitacionesServicioPublico(rs);
		}

		return HabitacionesServicioPublico;
	}

	/**
	 * Metodo que agregar la informacion de un nuevo HabitacionesServicioPublico en la Base de Datos a partir del parametro ingresado<br/>
	 * <b>Precondicion: </b> la conexion a sido inicializadoa <br/>  
	 * @param HabitacionesServicioPublico HabitacionesServicioPublico que desea agregar a la Base de Datos
	 * @throws SQLException SQLException Genera excepcion si hay error en la conexion o en la consulta SQL
	 * @throws Exception Si se genera un error dentro del metodo.
	 */
	public void addHabitacionesServicioPublico(HabitacionesServicioPublico habitacionesServicioPublico) throws SQLException, Exception {

		String sql = String.format("INSERT INTO %1$s.HABITACIONES_SERVICIOSPUBLICOS (ID_SERVICIO_PUBLICO, ID_HABITACION) VALUES (%2$s, '%3$s')", 
				USUARIO, 
				habitacionesServicioPublico.getIdServicioPublico(),
				habitacionesServicioPublico.getIdHabitacion());
		System.out.println(sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}


	/**
	 * Metodo que actualiza la informacion del habitacionesServicioPublico en la Base de Datos que tiene el identificador dado por parametro<br/>
	 * <b>Precondicion: </b> la conexion a sido inicializadoa <br/>  
	 * @param habitacionesServicioPublico HabitacionesServicioPublico que desea actualizar a la Base de Datos
	 * @throws SQLException SQLException Genera excepcion si hay error en la conexion o en la consulta SQL
	 * @throws Exception Si se genera un error dentro del metodo.
	 */
	public void updateHabitacionesServicioPublico(HabitacionesServicioPublico habitacionesServicioPublico) throws SQLException, Exception {

		StringBuilder sql = new StringBuilder();
		sql.append (String.format ("UPDATE %s.HABITACIONES_SERVICIOSPUBLICOS ", USUARIO));
		sql.append (String.format (
				"SET ID_SERVICIO_PUBLICO = '%1$s', ID_HABITACION = '%2$s'",
				habitacionesServicioPublico.getIdServicioPublico(),
				habitacionesServicioPublico.getIdHabitacion()));
		sql.append ("WHERE ID = " + habitacionesServicioPublico.getIdServicioPublico());
		System.out.println(sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql.toString());
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	/**
	 * Metodo que actualiza la informacion del habitacionesServicioPublico en la Base de Datos que tiene el identificador dado por parametro<br/>
	 * <b>Precondicion: </b> la conexion a sido inicializadoa <br/>  
	 * @param habitacionesServicioPublico HabitacionesServicioPublico que desea actualizar a la Base de Datos
	 * @throws SQLException SQLException Genera excepcion si hay error en la conexion o en la consulta SQL
	 * @throws Exception Si se genera un error dentro del metodo.
	 */
	public void deleteHabitacionesServicioPublico(HabitacionesServicioPublico habitacionesServicioPublico) throws SQLException, Exception {

		String sql = String.format("DELETE FROM %1$s.HABITACIONES_SERVICIOSPUBLICOS WHERE ID = %3$d", USUARIO, "" + habitacionesServicioPublico.getIdServicioPublico() + habitacionesServicioPublico.getIdHabitacion());

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
	 * Metodo que transforma el resultado obtenido de una consulta SQL (sobre la tabla HabitacionesServicioPublicoES) en una instancia de la clase HabitacionesServicioPublico.
	 * @param resultSet ResultSet con la informacion de un HabitacionesServicioPublico que se obtuvo de la base de datos.
	 * @return HabitacionesServicioPublico cuyos atributos corresponden a los valores asociados a un registro particular de la tabla HabitacionesServicioPublicoES.
	 * @throws SQLException Si existe algun problema al extraer la informacion del ResultSet.
	 */
	public HabitacionesServicioPublico convertResultSetToHabitacionesServicioPublico(ResultSet resultSet) throws SQLException {
		//TODO Requerimiento 1G: Complete el metodo con los atributos agregados previamente en la clase HabitacionesServicioPublico. 
		//						 Tenga en cuenta los nombres de las columnas de la Tabla en la Base de Datos (ID, NOMBRE, PRESUPUESTO, CIUDAD)

		String capacidad = resultSet.getString("ID_HABITACION");
		String id = resultSet.getString("ID_SERVICIO_PUBLICO");

		/**
		 * habitacionesServicioPublico.getCapacidad(),
						habitacionesServicioPublico.getId(), 
						habitacionesServicioPublico.getPrecio(), 
						habitacionesServicioPublico.getTamanio(),
						habitacionesServicioPublico.getUbicacion(),
						habitacionesServicioPublico.getTipo());
		 */

		HabitacionesServicioPublico beb = new HabitacionesServicioPublico(Integer.parseInt(id), Integer.parseInt(capacidad));

		return beb;
	}

}
