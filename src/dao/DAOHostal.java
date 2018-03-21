package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Hostal;

public class DAOHostal 
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
	 * Metodo constructor de la clase DAOHostal <br/>
	 */
	public DAOHostal() {
		recursos = new ArrayList<Object>();
	}

	/**
	 * Metodo que obtiene la informacion de todos los Hostales en la Base de Datos <br/>
	 * <b>Precondicion: </b> la conexion a sido inicializadoa <br/>
	 * @return	lista con la informacion de todos los Hostales que se encuentran en la Base de Datos
	 * @throws SQLException Genera excepcion si hay error en la conexion o en la consulta SQL
	 * @throws Exception Si se genera un error dentro del metodo.
	 */
	public ArrayList<Hostal> getHostals() throws SQLException, Exception {
		ArrayList<Hostal> hostals = new ArrayList<Hostal>();

		//Aclaracion: Por simplicidad, solamente se obtienen los primeros 50 resultados de la consulta
		String sql = String.format("SELECT * FROM %1$s.HOSTAL WHERE ROWNUM <= 50", USUARIO);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			hostals.add(convertResultSetToHostal(rs));
		}
		return hostals;
	}


	/**
	 * Metodo que obtiene la informacion del Hostal en la Base de Datos que tiene el identificador dado por parametro<br/>
	 * <b>Precondicion: </b> la conexion a sido inicializadoa <br/> 
	 * @param id el identificador del Hostal
	 * @return la informacion del Hostal que cumple con los criterios de la sentecia SQL
	 * 			Null si no existe el Hostal conlos criterios establecidos
	 * @throws SQLException SQLException Genera excepcion si hay error en la conexion o en la consulta SQL
	 * @throws Exception Si se genera un error dentro del metodo.
	 */
	public Hostal findHostalById(Long id) throws SQLException, Exception 
	{
		Hostal Hostal = null;

		String sql = String.format("SELECT * FROM %1$s.HOSTAL WHERE ID = %2$d", USUARIO, id); 

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if(rs.next()) {
			Hostal = convertResultSetToHostal(rs);
		}

		return Hostal;
	}

	/**
	 * Metodo que agregar la informacion de un nuevo Hostal en la Base de Datos a partir del parametro ingresado<br/>
	 * <b>Precondicion: </b> la conexion a sido inicializadoa <br/>  
	 * @param Hostal Hostal que desea agregar a la Base de Datos
	 * @throws SQLException SQLException Genera excepcion si hay error en la conexion o en la consulta SQL
	 * @throws Exception Si se genera un error dentro del metodo.
	 */
	public void addHostal(Hostal hostal) throws SQLException, Exception {

		String sql = String.format("INSERT INTO %1$s.HOSTAL (CAMARA_COMERCIO, NOMBRE, SUPERINTENDENCIA_TURISMO, CAPACIDAD_HOSTAL, ID_HOSTAL, HORA_APERTURA, HORA_CIERRE, RECEPCION24_HORAS) VALUES (%2$s, '%3$s', '%4$s', '%5$s', '%6$s', '%7$s', '%8$s', '%9$s', '%10$s')", 
				USUARIO, 
				hostal.toString(hostal.isCamaraComercio()),
				hostal.getNombreOperador(),
				hostal.toString(hostal.isSuperIntendenciaTurismo()),
				hostal.getCapacidad(),
				hostal.getId(),
				hostal.getHoraApertura(),
				hostal.getHoraCierre(),
				hostal.toString(hostal.isRecepcion24horas()));
		System.out.println(sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}


	/**
	 * Metodo que actualiza la informacion del hostal en la Base de Datos que tiene el identificador dado por parametro<br/>
	 * <b>Precondicion: </b> la conexion a sido inicializadoa <br/>  
	 * @param hostal Hostal que desea actualizar a la Base de Datos
	 * @throws SQLException SQLException Genera excepcion si hay error en la conexion o en la consulta SQL
	 * @throws Exception Si se genera un error dentro del metodo.
	 */
	public void updateHostal(Hostal hostal) throws SQLException, Exception {

		StringBuilder sql = new StringBuilder();
		sql.append (String.format ("UPDATE %s.HOSTAL ", USUARIO));
		sql.append (String.format (
				"SET CAMARA_COMERCIO = '%1$s', NOMBRE = '%2$s', SUPERINTENDENCIA_TURISMO = '%3$s' , CAPACIDAD_HOSTAL = %4$s, ID_HOSTAL= %5$s,  HORA_APERTURA = '%6$s', HORA_CIERRE '%7$s',RECEPCION24_HORAS '%8$s'",
				hostal.toString(hostal.isCamaraComercio()),
				hostal.getNombreOperador(),
				hostal.toString(hostal.isSuperIntendenciaTurismo()),
				hostal.getCapacidad(),
				hostal.getId(),
				hostal.getHoraApertura(),
				hostal.getHoraCierre(),
				hostal.toString(hostal.isRecepcion24horas())));
		sql.append ("WHERE ID = " + hostal.getId ());
		System.out.println(sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql.toString());
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	/**
	 * Metodo que actualiza la informacion del hostal en la Base de Datos que tiene el identificador dado por parametro<br/>
	 * <b>Precondicion: </b> la conexion a sido inicializadoa <br/>  
	 * @param hostal Hostal que desea actualizar a la Base de Datos
	 * @throws SQLException SQLException Genera excepcion si hay error en la conexion o en la consulta SQL
	 * @throws Exception Si se genera un error dentro del metodo.
	 */
	public void deleteHostal(Hostal hostal) throws SQLException, Exception {

		String sql = String.format("DELETE FROM %1$s.HOSTAL WHERE ID = %3$d", USUARIO, hostal.getId());

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
	 * Metodo que transforma el resultado obtenido de una consulta SQL (sobre la tabla HostalES) en una instancia de la clase Hostal.
	 * @param resultSet ResultSet con la informacion de un Hostal que se obtuvo de la base de datos.
	 * @return Hostal cuyos atributos corresponden a los valores asociados a un registro particular de la tabla HostalES.
	 * @throws SQLException Si existe algun problema al extraer la informacion del ResultSet.
	 */
	public Hostal convertResultSetToHostal(ResultSet resultSet) throws SQLException {
		//TODO Requerimiento 1G: Complete el metodo con los atributos agregados previamente en la clase Hostal. 
		//						 Tenga en cuenta los nombres de las columnas de la Tabla en la Base de Datos (ID, NOMBRE, PRESUPUESTO, CIUDAD)

		boolean rta1 = false;
		boolean rta2 = false;
		boolean rta3 = false;


		String camara = resultSet.getString("CAMARA_COMERCIO");

		if(camara.equals("T"))
		{
			rta1 = true;
		}
		String nombre = resultSet.getString("NOMBRE");
		String superI = resultSet.getString("SUPERINTENDENCIA_TURISMO");
		if(superI.equals("T"))
		{
			rta2 = true;
		}
		String capacidad = resultSet.getString("CAPACIDAD_HOSTAL");
		String id = resultSet.getString("ID_HOSTAL");
		String horaA = resultSet.getString("HORA_APERTURA");
		String horaC = resultSet.getString("HORA_CIERRRE");
		String recep = resultSet.getString("RECEPCION24_HORAS");
		if(recep.equals("T"))
		{
			rta3 = true;
		}

		Hostal beb = new Hostal(rta1, nombre, rta2, Integer.parseInt(capacidad), Integer.parseInt(id),horaA,horaC,rta3 );

		return beb;
	}
}
