package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.HabitacionesServiciosInmobiliarios;

public class DAOHabitacionesServicioInmobiliarioSerInm {


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
	 * Metodo constructor de la clase DAOHabitacionesServicioInmobiliario <br/>
	 */
	public DAOHabitacionesServicioInmobiliarioSerInm() {
		recursos = new ArrayList<Object>();
	}

	/**
	 * Metodo que obtiene la informacion de todos los HabitacionesServicioInmobiliarioes en la Base de Datos <br/>
	 * <b>Precondicion: </b> la conexion a sido inicializadoa <br/>
	 * @return	lista con la informacion de todos los HabitacionesServicioInmobiliarioes que se encuentran en la Base de Datos
	 * @throws SQLException Genera excepcion si hay error en la conexion o en la consulta SQL
	 * @throws Exception Si se genera un error dentro del metodo.
	 */
	public ArrayList<HabitacionesServiciosInmobiliarios> getHabitacionesServicioInmobiliarios() throws SQLException, Exception {
		ArrayList<HabitacionesServiciosInmobiliarios> habitacionesServicioInmobiliarios = new ArrayList<HabitacionesServiciosInmobiliarios>();

		//Aclaracion: Por simplicidad, solamente se obtienen los primeros 50 resultados de la consulta
		String sql = String.format("SELECT * FROM %1$s.HABITACIONES_INMOBILIARIOS WHERE ROWNUM <= 50", USUARIO);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			habitacionesServicioInmobiliarios.add(convertResultSetToHabitacionesServicioInmobiliario(rs));
		}
		return habitacionesServicioInmobiliarios;
	}


	/**
	 * Metodo que obtiene la informacion del HabitacionesServicioInmobiliario en la Base de Datos que tiene el identificador dado por parametro<br/>
	 * <b>Precondicion: </b> la conexion a sido inicializadoa <br/> 
	 * @param id el identificador del HabitacionesServicioInmobiliario
	 * @return la informacion del HabitacionesServicioInmobiliario que cumple con los criterios de la sentecia SQL
	 * 			Null si no existe el HabitacionesServicioInmobiliario conlos criterios establecidos
	 * @throws SQLException SQLException Genera excepcion si hay error en la conexion o en la consulta SQL
	 * @throws Exception Si se genera un error dentro del metodo.
	 */
	public HabitacionesServiciosInmobiliarios findHabitacionesServicioInmobiliarioById (Integer id) throws SQLException, Exception 
	{
		HabitacionesServiciosInmobiliarios HabitacionesServicioInmobiliario = null;

		String sql = String.format("SELECT * FROM %1$s.HABITACIONES_INMOBILIARIOS WHERE ID = %2$d", USUARIO, id); 

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if(rs.next()) {
			HabitacionesServicioInmobiliario = convertResultSetToHabitacionesServicioInmobiliario(rs);
		}

		return HabitacionesServicioInmobiliario;
	}

	/**
	 * Metodo que agregar la informacion de un nuevo HabitacionesServicioInmobiliario en la Base de Datos a partir del parametro ingresado<br/>
	 * <b>Precondicion: </b> la conexion a sido inicializadoa <br/>  
	 * @param HabitacionesServicioInmobiliario HabitacionesServicioInmobiliario que desea agregar a la Base de Datos
	 * @throws SQLException SQLException Genera excepcion si hay error en la conexion o en la consulta SQL
	 * @throws Exception Si se genera un error dentro del metodo.
	 */
	public void addHabitacionesServicioInmobiliario(HabitacionesServiciosInmobiliarios habitacionesServicioInmobiliario) throws SQLException, Exception {

		String sql = String.format("INSERT INTO %1$s.HABITACIONES_INMOBILIARIOS (ID_SERVICIO_INMOBILIARIO, ID_HABITACION) VALUES (%2$s, '%3$s')", 
				USUARIO, 
				habitacionesServicioInmobiliario.getIdServicioInmobiliario(),
				habitacionesServicioInmobiliario.getIdHabitacion());
		System.out.println(sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}


	/**
	 * Metodo que actualiza la informacion del habitacionesServicioInmobiliario en la Base de Datos que tiene el identificador dado por parametro<br/>
	 * <b>Precondicion: </b> la conexion a sido inicializadoa <br/>  
	 * @param habitacionesServicioInmobiliario HabitacionesServicioInmobiliario que desea actualizar a la Base de Datos
	 * @throws SQLException SQLException Genera excepcion si hay error en la conexion o en la consulta SQL
	 * @throws Exception Si se genera un error dentro del metodo.
	 */
	public void updateHabitacionesServicioInmobiliario(HabitacionesServiciosInmobiliarios habitacionesServicioInmobiliario) throws SQLException, Exception {

		StringBuilder sql = new StringBuilder();
		sql.append (String.format ("UPDATE %s.HABITACIONES_INMOBILIARIOS ", USUARIO));
		sql.append (String.format (
				"SET ID_SERVICIO_INMOBILIARIO = '%1$s', ID_HABITACION = '%2$s'",
				habitacionesServicioInmobiliario.getIdServicioInmobiliario(),
				habitacionesServicioInmobiliario.getIdHabitacion()));
		sql.append ("WHERE ID = " + habitacionesServicioInmobiliario.getIdServicioInmobiliario());
		System.out.println(sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql.toString());
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	/**
	 * Metodo que actualiza la informacion del habitacionesServicioInmobiliario en la Base de Datos que tiene el identificador dado por parametro<br/>
	 * <b>Precondicion: </b> la conexion a sido inicializadoa <br/>  
	 * @param habitacionesServicioInmobiliario HabitacionesServicioInmobiliario que desea actualizar a la Base de Datos
	 * @throws SQLException SQLException Genera excepcion si hay error en la conexion o en la consulta SQL
	 * @throws Exception Si se genera un error dentro del metodo.
	 */
	public void deleteHabitacionesServicioInmobiliario(HabitacionesServiciosInmobiliarios habitacionesServicioInmobiliario) throws SQLException, Exception {

		String sql = String.format("DELETE FROM %1$s.HABITACIONES_INMOBILIARIOS WHERE ID = %3$d", USUARIO, "" + habitacionesServicioInmobiliario.getIdServicioInmobiliario() + habitacionesServicioInmobiliario.getIdHabitacion());

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
	 * Metodo que transforma el resultado obtenido de una consulta SQL (sobre la tabla HabitacionesServicioInmobiliarioES) en una instancia de la clase HabitacionesServicioInmobiliario.
	 * @param resultSet ResultSet con la informacion de un HabitacionesServicioInmobiliario que se obtuvo de la base de datos.
	 * @return HabitacionesServicioInmobiliario cuyos atributos corresponden a los valores asociados a un registro particular de la tabla HabitacionesServicioInmobiliarioES.
	 * @throws SQLException Si existe algun problema al extraer la informacion del ResultSet.
	 */
	public HabitacionesServiciosInmobiliarios convertResultSetToHabitacionesServicioInmobiliario(ResultSet resultSet) throws SQLException {
		//TODO Requerimiento 1G: Complete el metodo con los atributos agregados previamente en la clase HabitacionesServicioInmobiliario. 
		//						 Tenga en cuenta los nombres de las columnas de la Tabla en la Base de Datos (ID, NOMBRE, PRESUPUESTO, CIUDAD)

		String capacidad = resultSet.getString("ID_HABITACION");
//		String precio = resultSet.getString("precio");
//		String tamanio = resultSet.getString("tamanio");
//		String ubicacion = resultSet.getString("ubicacion");
		String id = resultSet.getString("ID_SERVICIO_INMOBILIARIO");
//		String tipo = resultSet.getString("tipo");
//		String idReserva = resultSet.getString("idReserva");
//		String idOferta = resultSet.getString("idOferta");
//		String idHotel = resultSet.getString("idHotel");
//		String idHostal = resultSet.getString("idHostal");
//		String idPersona = resultSet.getString("idPersona");
//		String idViviendaU = resultSet.getString("idViviendaU");

		/**
		 * habitacionesServicioInmobiliario.getCapacidad(),
					habitacionesServicioInmobiliario.getId(), 
					habitacionesServicioInmobiliario.getPrecio(), 
					habitacionesServicioInmobiliario.getTamanio(),
					habitacionesServicioInmobiliario.getUbicacion(),
					habitacionesServicioInmobiliario.getTipo());
		 */

		HabitacionesServiciosInmobiliarios beb = new HabitacionesServiciosInmobiliarios(Integer.parseInt(id), Integer.parseInt(capacidad));

		return beb;
	}

}
