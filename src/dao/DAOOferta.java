package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Apartamento;
import vos.Habitacion;
import vos.Oferta;

public class DAOOferta 
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
	 * Metodo constructor de la clase DAOOferta <br/>
	 */
	public DAOOferta() {
		recursos = new ArrayList<Object>();
	}

	/**
	 * Metodo que obtiene la informacion de todos los Ofertaes en la Base de Datos <br/>
	 * <b>Precondicion: </b> la conexion a sido inicializadoa <br/>
	 * @return	lista con la informacion de todos los Ofertaes que se encuentran en la Base de Datos
	 * @throws SQLException Genera excepcion si hay error en la conexion o en la consulta SQL
	 * @throws Exception Si se genera un error dentro del metodo.
	 */
	public ArrayList<Oferta> getOfertas() throws SQLException, Exception {
		ArrayList<Oferta> ofertas = new ArrayList<Oferta>();

		//Aclaracion: Por simplicidad, solamente se obtienen los primeros 50 resultados de la consulta
		String sql = String.format("SELECT * FROM %1$s.OFERTA WHERE ROWNUM <= 50", USUARIO);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			ofertas.add(convertResultSetToOferta(rs));
		}
		return ofertas;
	}


	/**
	 * Metodo que obtiene la informacion del Oferta en la Base de Datos que tiene el identificador dado por parametro<br/>
	 * <b>Precondicion: </b> la conexion a sido inicializadoa <br/> 
	 * @param id el identificador del Oferta
	 * @return la informacion del Oferta que cumple con los criterios de la sentecia SQL
	 * 			Null si no existe el Oferta conlos criterios establecidos
	 * @throws SQLException SQLException Genera excepcion si hay error en la conexion o en la consulta SQL
	 * @throws Exception Si se genera un error dentro del metodo.
	 */
	public Oferta findOfertaById(Integer id) throws SQLException, Exception 
	{
		Oferta Oferta = null;

		String sql = String.format("SELECT * FROM %1$s.OFERTA WHERE ID = %2$d", USUARIO, id); 

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if(rs.next()) {
			Oferta = convertResultSetToOferta(rs);
		}

		return Oferta;
	}

	/**
	 * Metodo que agregar la informacion de un nuevo Oferta en la Base de Datos a partir del parametro ingresado<br/>
	 * <b>Precondicion: </b> la conexion a sido inicializadoa <br/>  
	 * @param Oferta Oferta que desea agregar a la Base de Datos
	 * @throws SQLException SQLException Genera excepcion si hay error en la conexion o en la consulta SQL
	 * @throws Exception Si se genera un error dentro del metodo.
	 */
	public void addOferta(Oferta oferta) throws SQLException, Exception {
		String sql = String.format("INSERT INTO %1$s.OFERTA (ID_OFERTA, NUM_RESERVAS, VIGENTE,ID_HOSTAL, ID_PERSONA, ID_HOTEL, ID_VIVIENDAU) VALUES (%2$s, '%3$s', '%4$s', '%5$s', '%6$s', '%7$s', '%8$s', '%9$s', '%10$s', '%11$s')", 

				USUARIO, 
				oferta.getId(),
				oferta.getNumReservas(),
				oferta.toString(oferta.isVigente()),
				oferta.getIdHostal(),
				oferta.getIdPersona(),
				oferta.getIdHotel(),
				oferta.getIdViviendaU());
		System.out.println(sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}


	/**
	 * Metodo que actualiza la informacion del oferta en la Base de Datos que tiene el identificador dado por parametro<br/>
	 * <b>Precondicion: </b> la conexion a sido inicializadoa <br/>  
	 * @param oferta Oferta que desea actualizar a la Base de Datos
	 * @throws SQLException SQLException Genera excepcion si hay error en la conexion o en la consulta SQL
	 * @throws Exception Si se genera un error dentro del metodo.
	 */
	public void updateOferta(Oferta oferta) throws SQLException, Exception {

		StringBuilder sql = new StringBuilder();
		sql.append (String.format ("UPDATE %s.OFERTA ", USUARIO));
		sql.append (String.format (
				"SET ID_OFERTA = '%1$s', NUM_RESERVAS = '%2$s', VIGENTE = '%3$s' , ID_HOSTAL = '%4$s', ID_PERSONA = '%5$s', ID_HOTEL = '%6$s', ID_VIVIENDAU = '%7$s'",
				oferta.getId(),
				oferta.getNumReservas(),
				oferta.toString(oferta.isVigente()),
				oferta.getIdHostal(),
				oferta.getIdPersona(),
				oferta.getIdHotel(),
				oferta.getIdViviendaU()));
		sql.append ("WHERE ID = " + oferta.getId());
		System.out.println(sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql.toString());
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	/**
	 * Metodo que actualiza la informacion del oferta en la Base de Datos que tiene el identificador dado por parametro<br/>
	 * <b>Precondicion: </b> la conexion a sido inicializadoa <br/>  
	 * @param oferta Oferta que desea actualizar a la Base de Datos
	 * @throws SQLException SQLException Genera excepcion si hay error en la conexion o en la consulta SQL
	 * @throws Exception Si se genera un error dentro del metodo.
	 */
	public void deleteOferta(Oferta oferta) throws SQLException, Exception {

		String sql = String.format("DELETE FROM %1$s.OFERTA WHERE ID = %3$d", USUARIO, oferta.getId());
		
		DAOHabitacion sisa = new DAOHabitacion();
		DAOApartamento ap = new DAOApartamento();
		ArrayList<Habitacion> habs = sisa.getHabitacions();
		for (int i = 0; i< habs.size(); i++) 
		{
			if(habs.get(i).getIdOferta() == oferta.getId())
				{
					habs.get(i).setIdOferta(0);
				}
		}
		
		ArrayList<Apartamento> aps = ap.getApartamentos();
		for(int i = 0; i< aps.size(); i++)
		{
			if (aps.get(i).getIdOferta() == oferta.getId())
			{
				aps.get(i).setIdOferta(0);
			}
		}
		
		oferta.getId();

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
	 * Metodo que transforma el resultado obtenido de una consulta SQL (sobre la tabla OfertaES) en una instancia de la clase Oferta.
	 * @param resultSet ResultSet con la informacion de un Oferta que se obtuvo de la base de datos.
	 * @return Oferta cuyos atributos corresponden a los valores asociados a un registro particular de la tabla OfertaES.
	 * @throws SQLException Si existe algun problema al extraer la informacion del ResultSet.
	 */
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

		Oferta beb = new Oferta(Integer.parseInt(id), Integer.parseInt(nombre), rta1, Integer.parseInt(idHostal), Integer.parseInt(idPersona), Integer.parseInt(idHotel), Integer.parseInt(idViviendaU));

		return beb;
	}

}

