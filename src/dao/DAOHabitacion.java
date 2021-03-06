package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Habitacion;

public class DAOHabitacion 
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
	 * Metodo constructor de la clase DAOHabitacion <br/>
	 */
	public DAOHabitacion() {
		recursos = new ArrayList<Object>();
	}

	/**
	 * Metodo que obtiene la informacion de todos los Habitaciones en la Base de Datos <br/>
	 * <b>Precondicion: </b> la conexion a sido inicializadoa <br/>
	 * @return	lista con la informacion de todos los Habitaciones que se encuentran en la Base de Datos
	 * @throws SQLException Genera excepcion si hay error en la conexion o en la consulta SQL
	 * @throws Exception Si se genera un error dentro del metodo.
	 */
	public ArrayList<Habitacion> getHabitacions() throws SQLException, Exception {
		ArrayList<Habitacion> habitacions = new ArrayList<Habitacion>();

		//Aclaracion: Por simplicidad, solamente se obtienen los primeros 50 resultados de la consulta
		String sql = String.format("SELECT * FROM %1$s.HABITACION WHERE ROWNUM <= 50", USUARIO);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		
		while (rs.next()) {
			habitacions.add(convertResultSetToHabitacion(rs));
		}
		return habitacions;
	}


	/**
	 * Metodo que obtiene la informacion del Habitacion en la Base de Datos que tiene el identificador dado por parametro<br/>
	 * <b>Precondicion: </b> la conexion a sido inicializadoa <br/> 
	 * @param id el identificador del Habitacion
	 * @return la informacion del Habitacion que cumple con los criterios de la sentecia SQL
	 * 			Null si no existe el Habitacion conlos criterios establecidos
	 * @throws SQLException SQLException Genera excepcion si hay error en la conexion o en la consulta SQL
	 * @throws Exception Si se genera un error dentro del metodo.
	 */
	public Habitacion findHabitacionById(Integer id) throws SQLException, Exception 
	{
		Habitacion Habitacion = null;

		String sql = String.format("SELECT * FROM %1$s.HABITACION WHERE ID_Habitacion = %2$d", USUARIO, id); 

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if(rs.next()) {
			Habitacion = convertResultSetToHabitacion(rs);
		}

		return Habitacion;
	}

	/**
	 * Metodo que agregar la informacion de un nuevo Habitacion en la Base de Datos a partir del parametro ingresado<br/>
	 * <b>Precondicion: </b> la conexion a sido inicializadoa <br/>  
	 * @param Habitacion Habitacion que desea agregar a la Base de Datos
	 * @throws SQLException SQLException Genera excepcion si hay error en la conexion o en la consulta SQL
	 * @throws Exception Si se genera un error dentro del metodo.
	 */
	public void addHabitacion(Habitacion habitacion) throws SQLException, Exception {

		String sql = String.format("INSERT INTO %1$s.Habitacion (capacidad_habitacion, id_Habitacion, precio, tamanio, tipo, ubicion, id_reserva, id_oferta, id_hotel, id_hostal, id_persona, id_viviendau) VALUES (%2$s, %3$s, %4$s, %5$s, '%6$s', '%7$s', %8$s, %9$s, %10$s, %11$s, %12$s)", 
				USUARIO, 
				habitacion.getCapacidad(),
				habitacion.getId(), 
				habitacion.getPrecio(), 
				habitacion.getTamanio(),
				habitacion.getUbicacion(),
				habitacion.getTipo(),
				habitacion.getIdReserva(),
				habitacion.getIdOferta(),
				habitacion.getIdHotel(),
				habitacion.getIdHostal(),
				habitacion.getIdPersona(),
				habitacion.getIdViviendaU());
		System.out.println(sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}


	/**
	 * Metodo que actualiza la informacion del habitacion en la Base de Datos que tiene el identificador dado por parametro<br/>
	 * <b>Precondicion: </b> la conexion a sido inicializadoa <br/>  
	 * @param habitacion Habitacion que desea actualizar a la Base de Datos
	 * @throws SQLException SQLException Genera excepcion si hay error en la conexion o en la consulta SQL
	 * @throws Exception Si se genera un error dentro del metodo.
	 */
	public void updateHabitacion(Habitacion habitacion) throws SQLException, Exception {

		StringBuilder sql = new StringBuilder();
		sql.append (String.format ("UPDATE %s.HABITACION ", USUARIO));
		sql.append (String.format (
				"SET CAPACIDAD_HABITACION= %1$s, ID_HABITACION = %2$s, PRECIO = %3$s , TAMANIO = %4$s, UBICACION = '%5$s', TIPO = '%6$s', ID_RESERVA = %7$s, ID_OFERTA = %8$s, ID_HOSTEL = %9$s, ID_HOSTAL = %10$s, ID_PERSONA = %11$s, ID_VIVIENDAU = %12$s",
				habitacion.getCapacidad(),
				habitacion.getId(), 
				habitacion.getPrecio(), 
				habitacion.getTamanio(),
				habitacion.getUbicacion(),
				habitacion.getTipo(),
				habitacion.getIdReserva(),
				habitacion.getIdOferta(),
				habitacion.getIdHotel(),
				habitacion.getIdHostal(),
				habitacion.getIdPersona(),
				habitacion.getIdViviendaU()));
		sql.append ("WHERE ID = " + habitacion.getId () + ";");
		System.out.println(sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql.toString());
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	/**
	 * Metodo que actualiza la informacion del habitacion en la Base de Datos que tiene el identificador dado por parametro<br/>
	 * <b>Precondicion: </b> la conexion a sido inicializadoa <br/>  
	 * @param habitacion Habitacion que desea actualizar a la Base de Datos
	 * @throws SQLException SQLException Genera excepcion si hay error en la conexion o en la consulta SQL
	 * @throws Exception Si se genera un error dentro del metodo.
	 */
	public void deleteHabitacion(Habitacion habitacion) throws SQLException, Exception {

		String sql = String.format("DELETE FROM %1$s.HABITACION WHERE ID = %3$d", USUARIO, habitacion.getId());

		System.out.println(sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
	
	
	
	public ArrayList<Habitacion> findHabsPorCliente(Integer id) throws SQLException
	{
		ArrayList<Habitacion> habs = new ArrayList<>();

		String sql = String.format("SELECT * FROM %1$s.HABITACION WHERE ID_Cliente = %2$d", USUARIO, id); 

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if(rs.next()) {
			habs.add(convertResultSetToHabitacion(rs));
		}

		return habs;
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
	 * Metodo que transforma el resultado obtenido de una consulta SQL (sobre la tabla HabitacionES) en una instancia de la clase Habitacion.
	 * @param resultSet ResultSet con la informacion de un Habitacion que se obtuvo de la base de datos.
	 * @return Habitacion cuyos atributos corresponden a los valores asociados a un registro particular de la tabla HabitacionES.
	 * @throws SQLException Si existe algun problema al extraer la informacion del ResultSet.
	 */
	public Habitacion convertResultSetToHabitacion(ResultSet resultSet) throws SQLException {
		//TODO Requerimiento 1G: Complete el metodo con los atributos agregados previamente en la clase Habitacion. 
		//						 Tenga en cuenta los nombres de las columnas de la Tabla en la Base de Datos (ID, NOMBRE, PRESUPUESTO, CIUDAD)

		String capacidad = resultSet.getString("capacidad_habitacion");
		String precio = resultSet.getString("precio");
		String tamanio = resultSet.getString("tamanio");
		String ubicacion = resultSet.getString("ubicacion");
		String id = resultSet.getString("id_habitacion");
		String tipo = resultSet.getString("tipo");
		String idReserva = resultSet.getString("id_reserva");
		String idOferta = resultSet.getString("id_oferta");
		String idHotel = resultSet.getString("id_hotel");
		String idHostal = resultSet.getString("id_hostal");
		String idPersona = resultSet.getString("id_persona");
		String idViviendaU = resultSet.getString("id_viviendau");

		/**
		 * habitacion.getCapacidad(),
				habitacion.getId(), 
				habitacion.getPrecio(), 
				habitacion.getTamanio(),
				habitacion.getUbicacion(),
				habitacion.getTipo());
		 */
		
		Integer rta1 = null;
		Integer rta2 = null;
		Integer rta3 = null;
		Integer rta4 = null;
		Integer rta5 = null;
		Integer rta6 = null;
		
		if(idReserva != null )
		{
			rta1 = Integer.parseInt(idReserva);
		}
		if(idOferta != null )
		{
			rta2 = Integer.parseInt(idOferta);
		}
		if(idHotel != null )
		{
			rta3 = Integer.parseInt(idHotel);
		}
		if(idHostal != null )
		{
			rta4 = Integer.parseInt(idHostal);
		}
		if(idPersona != null )
		{
			rta5 = Integer.parseInt(idPersona);
		}
		if(idViviendaU != null )
		{
			rta6 = Integer.parseInt(idViviendaU);
		}
		
		Habitacion beb = new Habitacion(Integer.parseInt(capacidad), Integer.parseInt(id), Integer.parseInt(precio), Double.parseDouble(tamanio), ubicacion,tipo, rta1, rta2, rta3, rta4, rta5, rta6);

		return beb;
	}
}
