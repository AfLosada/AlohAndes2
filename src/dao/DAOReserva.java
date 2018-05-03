package dao;

import java.util.List;
import java.sql.Connection;
import java.util.Date;

import javax.ws.rs.Path;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import vos.Reserva;
@Path("/reservas")
public class DAOReserva {

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
	 * Metodo constructor de la clase DAOReserva <br/>
	 */
	public DAOReserva() {
		recursos = new ArrayList<Object>();
	}

	/**
	 * Metodo que obtiene la informacion de todas las Reservaes en la Base de Datos <br/>
	 * <b>Precondicion: </b> la conexion a sido inicializadoa <br/>
	 * @return	lista con la informacion de todos los Reservaes que se encuentran en la Base de Datos
	 * @throws SQLException Genera excepcion si hay error en la conexion o en la consulta SQL
	 * @throws Exception Si se genera un error dentro del metodo.
	 */
	public ArrayList<Reserva> getReservas() throws SQLException, Exception {
		ArrayList<Reserva> reservas = new ArrayList<Reserva>();

		//Aclaracion: Por simplicidad, solamente se obtienen los primeros 50 resultados de la consulta
		String sql = String.format("SELECT * FROM %1$s.RESERVA WHERE ROWNUM <= 50", USUARIO);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			reservas.add(convertResultSetToReserva(rs));
		}
		return reservas;
	}


	/**
	 * Metodo que obtiene la informacion del Reserva en la Base de Datos que tiene el identificador dado por parametro<br/>
	 * <b>Precondicion: </b> la conexion a sido inicializadoa <br/> 
	 * @param id el identificador del Reserva
	 * @return la informacion del Reserva que cumple con los criterios de la sentecia SQL
	 * 			Null si no existe el Reserva conlos criterios establecidos
	 * @throws SQLException SQLException Genera excepcion si hay error en la conexion o en la consulta SQL
	 * @throws Exception Si se genera un error dentro del metodo.
	 */
	public Reserva findReservaById(Integer id) throws SQLException, Exception 
	{
		Reserva Reserva = null;

		String sql = String.format("SELECT * FROM %1$s.RESERVA WHERE ID = %2$d", USUARIO, id); 

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if(rs.next()) {
			Reserva = convertResultSetToReserva(rs);
		}

		return Reserva;
	}

	/**
	 * Metodo que agregar la informacion de un nuevo Reserva en la Base de Datos a partir del parametro ingresado<br/>
	 * <b>Precondicion: </b> la conexion a sido inicializadoa <br/>  
	 * @param Reserva Reserva que desea agregar a la Base de Datos
	 * @throws SQLException SQLException Genera excepcion si hay error en la conexion o en la consulta SQL
	 * @throws Exception Si se genera un error dentro del metodo.
	 */
	public void addReserva(Reserva reserva) throws SQLException, Exception {

		Integer dur = Integer.parseInt((reserva.getDuracion()));
		
		if(dur <= 3)
		{
			throw new Exception("La duracion debe ser de más de 3 días");
		}
		else if(dur <7 )
		{
			throw new Exception ("La duracion debe ser minimo de una semana");
		}
	   if(reserva.getIdViviendaU() != 0){
		   DAOCliente cliente = new DAOCliente();
		   String tipo = cliente.findClienteById(reserva.getIdCliente()).getTipo();
		   if( tipo != "ESTUDIANTE" || tipo != "PROFESOR" || tipo != "EMPLEADO" || tipo != "PROFESOR_VISITANTE"){
			   throw new Exception("El cliente de tipo " + tipo+" no puede reservar una Vivienda Universitaria.");
		   }
	   }
	   
	
	
		String sql = String.format("INSERT INTO %1$s.RESERVA (CONFIRMADA, DURACION, FECHA, ID_RESERVA , PAGO_ANTICIPADO, TIEMPO_CANCELACION, VALOR , ID_HOSTAL, ID_PERSONA, ID_HOTEL, ID_VIVIENDAU, ID_CLIENTE) VALUES (%2$s, '%3$s', '%4$s', '%5$s', '%6$s', '%7$s', '%8$s', '%9$s', '%10$s', '%11$s', '%12$s')", 
				USUARIO, 
				reserva.toString(reserva.isConfirmada()),
				reserva.getDuracion(),
				reserva.getFecha(),
				reserva.getId(),
				reserva.toString(reserva.isPagoAnticipado()),
				reserva.getTiempoCancelacion(),
				reserva.getidHostal(),
				reserva.getIdHotel(),
				reserva.getIdViviendaU(),
				reserva.getIdCliente());
		System.out.println(sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}


	/**
	 * Metodo que actualiza la informacion del reserva en la Base de Datos que tiene el identificador dado por parametro<br/>
	 * <b>Precondicion: </b> la conexion a sido inicializadoa <br/>  
	 * @param reserva Reserva que desea actualizar a la Base de Datos
	 * @throws SQLException SQLException Genera excepcion si hay error en la conexion o en la consulta SQL
	 * @throws Exception Si se genera un error dentro del metodo.
	 */
	public void updateReserva(Reserva reserva) throws SQLException, Exception {

		StringBuilder sql = new StringBuilder();
		sql.append (String.format ("UPDATE %s.RESERVA ", USUARIO));
		sql.append (String.format (
				"SET CONFIRMADA = '%1$s', DURACION = '%2$s', FECHA = '%3$s' , ID_RESERVA = '%4$s', PAGO_ANTICIPADO = '%5$s', TIEMPO_CANCELACION = '%6$s', ID_HOSTAL = %7$s, ID_PERSONA = %8$s, ID_HOTEL = %9$s, ID_PERSONA = %10$s, ID_HOTEL = %11$s, ID_VIVIENDAU = %12$s, ID_CLIENTE = %13$s",
				reserva.toString(reserva.isConfirmada()),
				reserva.getDuracion(),
				reserva.getFecha(),
				reserva.getId(),
				reserva.toString(reserva.isPagoAnticipado()),
				reserva.getTiempoCancelacion(),
				reserva.getidHostal(),
				reserva.getIdHotel(),
				reserva.getIdViviendaU(),
				reserva.getIdCliente()));
		sql.append ("WHERE ID = " + reserva.getId());
		System.out.println(sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql.toString());
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	/**
	 * Metodo que actualiza la informacion del reserva en la Base de Datos que tiene el identificador dado por parametro<br/>
	 * <b>Precondicion: </b> la conexion a sido inicializadoa <br/>  
	 * @param reserva Reserva que desea actualizar a la Base de Datos
	 * @throws SQLException SQLException Genera excepcion si hay error en la conexion o en la consulta SQL
	 * @throws Exception Si se genera un error dentro del metodo.
	 */
	public void deleteReserva(Reserva reserva) throws SQLException, Exception {

		String sql = String.format("DELETE FROM %1$s.RESERVA WHERE ID = %3$d", USUARIO, reserva.getId());

		System.out.println(sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}


	public void cancelarReserva(Reserva reserva) throws ParseException {
		// TODO Auto-generated method stub
		String tiempoCanc = reserva.getTiempoCancelacion();
		Date fecha = new SimpleDateFormat("mmdd-mm-yyyy").parse(tiempoCanc);
		DateFormat dateFormat = new SimpleDateFormat("dd-mm-yyyy");
		Date actual = new Date();
		dateFormat.format(actual.before(fecha));
		if(actual.before(fecha))
		{
			reserva.setConfirmada(false);
			StringBuilder sql = new StringBuilder();
			sql.append (String.format ("UPDATE %s.RESERVA ", USUARIO));
			sql.append (String.format (
					
					"SET CONFIRMADA = '%1$s', DURACION = %2$s, FECHA = '%3$s' , ID_RESERVA = %4$s, PAGO_ANTICIPADO= '%5$s', TIEMPO_CANCELACION = '%6$s', VALOR  = %7$s, ID_HOSTAL = %8$s, ID_PERSONA = %9$s, ID_HOTEL = %10$s, ID_VIVIENDAU = %11$s, ID_CLIENTE = %12$s",
					reserva.toString(reserva.isConfirmada()),
					reserva.getDuracion(),
					reserva.getFecha(),
					reserva.getId(),
					reserva.toString(reserva.isPagoAnticipado()),
					reserva.getTiempoCancelacion(),
					reserva.getidHostal(),
					reserva.getIdHotel(),
					reserva.getIdViviendaU(),
					reserva.getIdCliente()));
			sql.append ("WHERE ID = " + reserva.getId());
			System.out.println(sql);
		}
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
	 * Metodo que transforma el resultado obtenido de una consulta SQL (sobre la tabla ReservaES) en una instancia de la clase Reserva.
	 * @param resultSet ResultSet con la informacion de un Reserva que se obtuvo de la base de datos.
	 * @return Reserva cuyos atributos corresponden a los valores asociados a un registro particular de la tabla ReservaES.
	 * @throws Exception 
	 * @throws NumberFormatException 
	 */
	public Reserva convertResultSetToReserva(ResultSet resultSet) throws NumberFormatException, Exception {
		//TODO Requerimiento 1G: Complete el metodo con los atributos agregados previamente en la clase Reserva. 
		//						 Tenga en cuenta los nombres de las columnas de la Tabla en la Base de Datos (ID, NOMBRE, PRESUPUESTO, CIUDAD)

		boolean rta1 = false;
		boolean rta2 = false;
		boolean rta3 = false;
		
		String confirmada = resultSet.getString("CONFIRMADA");

		if(confirmada.equals("1"))
		{
			rta1 = true;
		}
		String duracion = resultSet.getString("DURACION");
		String fecha = resultSet.getString("FECHA");
		String pagoAnticipado = resultSet.getString("PAGO_ANTICIPADO");
		if(pagoAnticipado != null)
		{
			if(pagoAnticipado.equals("1"))
			rta2 = true;
		}
		String id = resultSet.getString("ID_RESERVA");
		
		String tiempoCancelacion = resultSet.getString("TIEMPO_CANCELACION");
		String valor = resultSet.getString("VALOR");
		String idHostal = resultSet.getString("ID_HOSTAL");
		Integer rta10 = null;
		if(idHostal != null)
		{
			rta10 =Integer.parseInt(idHostal);
		}
		String idPersona = resultSet.getString("ID_PERSONA");
		Integer rta11 = null;
		if(idPersona != null)
		{
			rta11 =Integer.parseInt(idPersona);
		}
		String idHotel = resultSet.getString("ID_HOTEL");

		Integer rta12 = null;
		if(idHotel != null)
		{
			rta12 =Integer.parseInt(idHotel);
		}
		String idViviendaU = resultSet.getString("ID_VIVIENDAU");

		Integer rta13 = null;
		if(idViviendaU != null)
		{
			rta13 =Integer.parseInt(idViviendaU);
		}
		String idCliente = resultSet.getString("ID_CLIENTE");

		Integer rta14 = null;
		if(idCliente != null)
		{
			rta14 =Integer.parseInt(idCliente);
		}
		
		

		Reserva beb = new Reserva(rta1, duracion, fecha, Integer.parseInt(id), rta2, tiempoCancelacion, Double.parseDouble(valor), rta10, rta11, rta12, rta13, rta14);

		return beb;
	}

}
