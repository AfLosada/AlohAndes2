package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Vivienda;

public class DAOVivienda 
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
	 * Metodo constructor de la clase DAOVivienda <br/>
	 */
	public DAOVivienda() {
		recursos = new ArrayList<Object>();
	}

	/**
	 * Metodo que obtiene la informacion de todos los Viviendaes en la Base de Datos <br/>
	 * <b>Precondicion: </b> la conexion a sido inicializadoa <br/>
	 * @return	lista con la informacion de todos los Viviendaes que se encuentran en la Base de Datos
	 * @throws SQLException Genera excepcion si hay error en la conexion o en la consulta SQL
	 * @throws Exception Si se genera un error dentro del metodo.
	 */
	public ArrayList<Vivienda> getViviendas() throws SQLException, Exception {
		ArrayList<Vivienda> viviendas = new ArrayList<Vivienda>();

		//Aclaracion: Por simplicidad, solamente se obtienen los primeros 50 resultados de la consulta
		String sql = String.format("SELECT * FROM %1$s. VIVIENDA WHERE ROWNUM <= 50", USUARIO);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			viviendas.add(convertResultSetToVivienda(rs));
		}
		return viviendas;
	}


	/**
	 * Metodo que obtiene la informacion del Vivienda en la Base de Datos que tiene el identificador dado por parametro<br/>
	 * <b>Precondicion: </b> la conexion a sido inicializadoa <br/> 
	 * @param id el identificador del Vivienda
	 * @return la informacion del Vivienda que cumple con los criterios de la sentecia SQL
	 * 			Null si no existe el Vivienda conlos criterios establecidos
	 * @throws SQLException SQLException Genera excepcion si hay error en la conexion o en la consulta SQL
	 * @throws Exception Si se genera un error dentro del metodo.
	 */
	public Vivienda findViviendaById(Integer id) throws SQLException, Exception 
	{
		Vivienda Vivienda = null;

		String sql = String.format("SELECT * FROM %1$s.VIVIENDA WHERE ID = %2$d", USUARIO, id); 

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if(rs.next()) {
			Vivienda = convertResultSetToVivienda(rs);
		}

		return Vivienda;
	}

	/**
	 * Metodo que agregar la informacion de un nuevo Vivienda en la Base de Datos a partir del parametro ingresado<br/>
	 * <b>Precondicion: </b> la conexion a sido inicializadoa <br/>  
	 * @param Vivienda Vivienda que desea agregar a la Base de Datos
	 * @throws SQLException SQLException Genera excepcion si hay error en la conexion o en la consulta SQL
	 * @throws Exception Si se genera un error dentro del metodo.
	 */
	public void addVivienda(Vivienda vivienda) throws SQLException, Exception {

		String sql = String.format("INSERT INTO %1$s.VIVIENDA (CAPACIDAD, CARACTERISTICAS_SEGURO, CARACTERISTICAS_VIVIENDA, PRECIO_VIVIENDA, ID_VECINO, ID_VIVIENDA, UBICACION, TIEMPO_USO) VALUES (%2$s, '%3$s', '%4$s', %5$s, %6$s, %7$s,'%8$s, %9$s)", 
				USUARIO, 
				vivienda.getCapacidad(),
				vivienda.getCaracteristicasSeguro(),
				vivienda.getCaracteristicas(),
				vivienda.getPrecioVivienda(),
				vivienda.getIdVecino(),
				vivienda.getIdVivienda(),
				vivienda.getUbicacion(),
				vivienda.getTiempoUso());
		System.out.println(sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}


	/**
	 * Metodo que actualiza la informacion del vivienda en la Base de Datos que tiene el identificador dado por parametro<br/>
	 * <b>Precondicion: </b> la conexion a sido inicializadoa <br/>  
	 * @param vivienda Vivienda que desea actualizar a la Base de Datos
	 * @throws SQLException SQLException Genera excepcion si hay error en la conexion o en la consulta SQL
	 * @throws Exception Si se genera un error dentro del metodo.
	 */
	public void updateVivienda(Vivienda vivienda) throws SQLException, Exception {

		StringBuilder sql = new StringBuilder();
		sql.append (String.format ("UPDATE %s.VIVIENDA ", USUARIO));
		sql.append (String.format (
				"SET capacidad = %1$s, caracteristicasSeguro = '%2$s', caracteristicas = '%3$s', precioVivienda = %4$s, idVecino = %5$s, idVivienda = %6$s  , ubicacion = '%7$s' , tiempoUso= %8$s ",
				vivienda.getCapacidad(),
				vivienda.getCaracteristicasSeguro(),
				vivienda.getCaracteristicas(),
				vivienda.getPrecioVivienda(),
				vivienda.getIdVecino(),
				vivienda.getIdVivienda(),
				vivienda.getUbicacion(),
				vivienda.getTiempoUso()));
		sql.append ("WHERE ID = " + vivienda.getIdVivienda());
		System.out.println(sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql.toString());
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	/**
	 * Metodo que actualiza la informacion del vivienda en la Base de Datos que tiene el identificador dado por parametro<br/>
	 * <b>Precondicion: </b> la conexion a sido inicializadoa <br/>  
	 * @param vivienda Vivienda que desea actualizar a la Base de Datos
	 * @throws SQLException SQLException Genera excepcion si hay error en la conexion o en la consulta SQL
	 * @throws Exception Si se genera un error dentro del metodo.
	 */
	public void deleteVivienda(Vivienda vivienda) throws SQLException, Exception {

		String sql = String.format("DELETE FROM %1$s.VIVIENDA WHERE ID = %3$d", USUARIO, vivienda.getIdVivienda());

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
	 * Metodo que transforma el resultado obtenido de una consulta SQL (sobre la tabla ViviendaES) en una instancia de la clase Vivienda.
	 * @param resultSet ResultSet con la informacion de un Vivienda que se obtuvo de la base de datos.
	 * @return Vivienda cuyos atributos corresponden a los valores asociados a un registro particular de la tabla ViviendaES.
	 * @throws SQLException Si existe algun problema al extraer la informacion del ResultSet.
	 */
	public Vivienda convertResultSetToVivienda(ResultSet resultSet) throws SQLException {
		//TODO Requerimiento 1G: Complete el metodo con los atributos agregados previamente en la clase Vivienda. 
		//						 Tenga en cuenta los nombres de las columnas de la Tabla en la Base de Datos (ID, NOMBRE, PRESUPUESTO, CIUDAD)

		boolean rta1 = false;
		boolean rta2 = false;
		boolean rta3 = false;
	
		String capacidad = resultSet.getString("CAPACIDAD");
		Integer rtax = null;
		if(capacidad != null)
		{
			rtax = Integer.parseInt(capacidad);
		}
		String caracteristicasSeguro = resultSet.getString("CARACTERISTICAS_SEGURO");
		String caracteristicas = resultSet.getString("CARACTERISTICAS_VIVIENDA");
		String idVecino = resultSet.getString("ID_VECINO");
		Integer rtaxd = null;
		if(idVecino != null)
		{
			rtaxd = Integer.parseInt(idVecino);
		}
		String precio = resultSet.getString("PRECIO_VIVIENDA");
		String vivienda = resultSet.getString("ID_VIVIENDA");
		String ubicacion = resultSet.getString("UBICACION");
		String tiempo = resultSet.getString("TIEMPO_USO");


		Vivienda beb = new Vivienda(rtax, caracteristicasSeguro,caracteristicas, Integer.parseInt(vivienda),Double.parseDouble(precio), rtaxd, ubicacion, Integer.parseInt(tiempo));

		return beb;
	}
}

