package tm;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import dao.*;
import rest.RequerimientosService;
import vos.*;
import vos.Habitacion.TipoHabitacion;

public class AlohAndesTransactionManager <K extends Operador>
{
	//----------------------------------------------------------------------------------------------------------------------------------
	// CONSTANTES
	//----------------------------------------------------------------------------------------------------------------------------------

	/**
	 * Constante que contiene el path relativo del archivo que tiene los datos de la conexion
	 */
	private static final String CONNECTION_DATA_FILE_NAME_REMOTE = "/conexion.properties";

	/**
	 * Atributo estatico que contiene el path absoluto del archivo que tiene los datos de la conexion
	 */
	private static String CONNECTION_DATA_PATH;

	/**
	 * Constatne que representa el numero maximo de Operadores que pueden haber en una ciudad
	 */
	private final static Integer CANTIDAD_MAXIMA = 345;

	//----------------------------------------------------------------------------------------------------------------------------------
	// ATRIBUTOS
	//----------------------------------------------------------------------------------------------------------------------------------

	/**
	 * Atributo que guarda el usuario que se va a usar para conectarse a la base de datos.
	 */
	private String user;

	/**
	 * Atributo que guarda la clave que se va a usar para conectarse a la base de datos.
	 */
	private String password;

	/**
	 * Atributo que guarda el URL que se va a usar para conectarse a la base de datos.
	 */
	private String url;

	/**
	 * Atributo que guarda el driver que se va a usar para conectarse a la base de datos.
	 */
	private String driver;

	/**
	 * Atributo que representa la conexion a la base de datos
	 */
	private Connection conn;

	//----------------------------------------------------------------------------------------------------------------------------------
	// METODOS DE CONEXION E INICIALIZACION
	//----------------------------------------------------------------------------------------------------------------------------------

	/**
	 * <b>Metodo Contructor de la Clase ParranderosTransactionManager</b> <br/>
	 * <b>Postcondicion: </b>	Se crea un objeto  ParranderosTransactionManager,
	 * 						 	Se inicializa el path absoluto del archivo de conexion,
	 * 							Se inicializna los atributos para la conexion con la Base de Datos
	 * @param contextPathP Path absoluto que se encuentra en el servidor del contexto del deploy actual
	 * @throws IOException Se genera una excepcion al tener dificultades con la inicializacion de la conexion<br/>
	 * @throws ClassNotFoundException 
	 */
	public AlohAndesTransactionManager(String contextPathP) {

		try {
			CONNECTION_DATA_PATH = contextPathP + CONNECTION_DATA_FILE_NAME_REMOTE;
			initializeConnectionData();
		} 
		catch (ClassNotFoundException e) {			
			e.printStackTrace();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Metodo encargado de inicializar los atributos utilizados para la conexion con la Base de Datos.<br/>
	 * <b>post: </b> Se inicializan los atributos para la conexion<br/>
	 * @throws IOException Se genera una excepcion al no encontrar el archivo o al tener dificultades durante su lectura<br/>
	 * @throws ClassNotFoundException 
	 */
	public void initializeConnectionData() throws IOException, ClassNotFoundException {

		FileInputStream fileInputStream = new FileInputStream(new File(AlohAndesTransactionManager.CONNECTION_DATA_PATH));
		Properties properties = new Properties();

		properties.load(fileInputStream);
		fileInputStream.close();

		this.url = properties.getProperty("url");
		this.user = properties.getProperty("usuario");
		this.password = properties.getProperty("clave");
		this.driver = properties.getProperty("driver");

		//Class.forName(driver);
	}

	/**
	 * Metodo encargado de generar una conexion con la Base de Datos.<br/>
	 * <b>Precondicion: </b>Los atributos para la conexion con la Base de Datos han sido inicializados<br/>
	 * @return Objeto Connection, el cual hace referencia a la conexion a la base de datos
	 * @throws SQLException Cualquier error que se pueda llegar a generar durante la conexion a la base de datos
	 */
	public Connection darConexion() throws SQLException 
	{
		System.out.println("[PARRANDEROS APP] Attempting Connection to: " + url + " - By User: " + user);
		Connection coni = DriverManager.getConnection(url, user, password);
		coni.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
		coni.setAutoCommit(false);
		return coni;
	}


	//----------------------------------------------------------------------------------------------------------------------------------
	// METODOS TRANSACCIONALES
	//----------------------------------------------------------------------------------------------------------------------------------

	//TODO INICIO GETS Y PUTS DE TODAS LAS CLASES INDIVIDUALES

	/**
	 * Metodo que modela la transaccion que retorna todos los apartamentoes de la base de datos. <br/>
	 * @return List<Apartamento> - Lista de apartamentoes que contiene el resultado de la consulta.
	 * @throws Exception -  Cualquier error que se genere durante la transaccion
	 */
	public List<Apartamento> getAllApartamentoes() throws Exception {
		DAOApartamento daoApartamento = new DAOApartamento();
		List<Apartamento> apartamento;
		try 
		{
			this.conn = darConexion();
			daoApartamento.setConn(conn);

			//Por simplicidad, solamente se obtienen los primeros 50 resultados de la consulta
			apartamento = daoApartamento.getApartamentos();
			conn.commit();
		}
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				daoApartamento.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return apartamento;
	}

	/**
	 * Metodo que modela la transaccion que busca el apartamento en la base de datos que tiene el ID dado por parametro. <br/>
	 * @param name -id del apartamento a buscar. id != null
	 * @return Apartamento - Apartamento que se obtiene como resultado de la consulta.
	 * @throws Exception -  cualquier error que se genere durante la transaccion
	 */
	public Apartamento getApartamentoById(Integer id) throws Exception {
		DAOApartamento daoApartamento = new DAOApartamento();
		Apartamento apartamento = null;
		try 
		{
			this.conn = darConexion();
			daoApartamento.setConn(conn);
			apartamento = daoApartamento.findApartamentoById(id);
			if(apartamento == null)
			{
				throw new Exception("El apartamento con el id = " + id + " no se encuentra persistido en la base de datos.");				
			}
		} 
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				daoApartamento.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return apartamento;
	}

	/**
	 * Método que modela la transacción que agrega un solo video a la base de datos.
	 * <b> post: </b> se ha agregado el video que entra como parámetro
	 * @param video - el video a agregar. video != null
	 * @throws Exception - cualquier error que se genera agregando el video
	 */
	public void addApartamento(Apartamento apartamento) throws Exception {
		DAOApartamento daoApartamentos = new DAOApartamento();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoApartamentos.setConn(conn);
			daoApartamentos.addApartamento(apartamento);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoApartamentos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}



	/**
	 * Método que modela la transacción que agrega los videos que entran como parámetro a la base de datos.
	 * <b> post: </b> se han agregado los videos que entran como parámetro
	 * @param videos - objeto que modela una lista de videos y se estos se pretenden agregar. videos != null
	 * @throws Exception - cualquier error que se genera agregando los videos
	 */
	public void addApartamentos(ArrayList<Apartamento> apartamentos) throws Exception {
		DAOApartamento daoApartamentos = new DAOApartamento();
		try 
		{
			//////Transacción - ACID Example
			this.conn = darConexion();
			conn.setAutoCommit(false);
			daoApartamentos.setConn(conn);
			for(Apartamento apartamento : (apartamentos))
				daoApartamentos.addApartamento(apartamento);
			conn.commit();
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			try {
				daoApartamentos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}


	/**
	 * Método que modela la transacción que actualiza el video que entra como parámetro a la base de datos.
	 * <b> post: </b> se ha actualizado el video que entra como parámetro
	 * @param video - Video a actualizar. video != null
	 * @throws Exception - cualquier error que se genera actualizando los videos
	 */
	public void updateApartamento(Apartamento apartamento) throws Exception {
		DAOApartamento daoApartamento = new DAOApartamento();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoApartamento.setConn(conn);
			daoApartamento.updateApartamento(apartamento);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoApartamento.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}


	/**
	 * Método que modela la transacción que elimina el video que entra como parámetro a la base de datos.
	 * <b> post: </b> se ha eliminado el video que entra como parámetro
	 * @param video - Video a eliminar. video != null
	 * @throws Exception - cualquier error que se genera actualizando los videos
	 */
	public void deleteApartamento(Apartamento apartamento) throws Exception {
		DAOApartamento daoApartamento = new DAOApartamento();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoApartamento.setConn(conn);
			daoApartamento.deleteApartamento(apartamento);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoApartamento.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	//TODO FIN APARTAMENTOS INICIO CLIENTES


	/**
	 * Metodo que modela la transaccion que retorna todos los clientees de la base de datos. <br/>
	 * @return List<Cliente> - Lista de clientees que contiene el resultado de la consulta.
	 * @throws Exception -  Cualquier error que se genere durante la transaccion
	 */
	public List<Cliente> getAllClientes() throws Exception {
		DAOCliente daoCliente = new DAOCliente();
		List<Cliente> cliente;
		try 
		{
			this.conn = darConexion();
			daoCliente.setConn(conn);

			//Por simplicidad, solamente se obtienen los primeros 50 resultados de la consulta
			cliente = daoCliente.getClientes();
		}
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				daoCliente.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return cliente;
	}

	/**
	 * Metodo que modela la transaccion que busca el cliente en la base de datos que tiene el ID dado por parametro. <br/>
	 * @param name -id del cliente a buscar. id != null
	 * @return Cliente - Cliente que se obtiene como resultado de la consulta.
	 * @throws Exception -  cualquier error que se genere durante la transaccion
	 */
	public Cliente getClienteById(Integer id) throws Exception {
		DAOCliente daoCliente = new DAOCliente();
		Cliente cliente = null;
		try 
		{
			this.conn = darConexion();
			daoCliente.setConn(conn);
			cliente = daoCliente.findClienteById(id);
			if(cliente == null)
			{
				throw new Exception("El cliente con el id = " + id + " no se encuentra persistido en la base de datos.");				
			}
		} 
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				daoCliente.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return cliente;
	}

	/**
	 * Método que modela la transacción que agrega un solo video a la base de datos.
	 * <b> post: </b> se ha agregado el video que entra como parámetro
	 * @param video - el video a agregar. video != null
	 * @throws Exception - cualquier error que se genera agregando el video
	 */
	public void addCliente(Cliente cliente) throws Exception {
		DAOCliente daoClientes = new DAOCliente();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoClientes.setConn(conn);
			daoClientes.addCliente(cliente);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoClientes.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}



	/**
	 * Método que modela la transacción que agrega los videos que entran como parámetro a la base de datos.
	 * <b> post: </b> se han agregado los videos que entran como parámetro
	 * @param videos - objeto que modela una lista de videos y se estos se pretenden agregar. videos != null
	 * @throws Exception - cualquier error que se genera agregando los videos
	 */
	public void addClientes(ArrayList<Cliente> clientes) throws Exception {
		DAOCliente daoClientes = new DAOCliente();
		try 
		{
			//////Transacción - ACID Example
			this.conn = darConexion();
			conn.setAutoCommit(false);
			daoClientes.setConn(conn);
			for(Cliente cliente : (clientes))
				daoClientes.addCliente(cliente);
			conn.commit();
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			try {
				daoClientes.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}


	/**
	 * Método que modela la transacción que actualiza el video que entra como parámetro a la base de datos.
	 * <b> post: </b> se ha actualizado el video que entra como parámetro
	 * @param video - Video a actualizar. video != null
	 * @throws Exception - cualquier error que se genera actualizando los videos
	 */
	public void updateCliente(Cliente cliente) throws Exception {
		DAOCliente daoCliente = new DAOCliente();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoCliente.setConn(conn);
			daoCliente.updateCliente(cliente);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoCliente.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}


	/**
	 * Método que modela la transacción que elimina el video que entra como parámetro a la base de datos.
	 * <b> post: </b> se ha eliminado el video que entra como parámetro
	 * @param video - Video a eliminar. video != null
	 * @throws Exception - cualquier error que se genera actualizando los videos
	 */
	public void deleteCliente(Cliente cliente) throws Exception {
		DAOCliente daoCliente = new DAOCliente();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoCliente.setConn(conn);
			daoCliente.deleteCliente(cliente);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoCliente.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	//TODO FIN CLIENTE INICIO HABITACION

	/**
	 * Metodo que modela la transaccion que retorna todos los habitaciones de la base de datos. <br/>
	 * @return List<Habitacion> - Lista de habitaciones que contiene el resultado de la consulta.
	 * @throws Exception -  Cualquier error que se genere durante la transaccion
	 */
	public List<Habitacion> getAllHabitaciones() throws Exception {
		DAOHabitacion daoHabitacion = new DAOHabitacion();
		List<Habitacion> habitacion;
		try 
		{
			this.conn = darConexion();
			daoHabitacion.setConn(conn);

			//Por simplicidad, solamente se obtienen los primeros 50 resultados de la consulta
			habitacion = daoHabitacion.getHabitacions();
		}
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				daoHabitacion.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return habitacion;
	}

	/**
	 * Metodo que modela la transaccion que busca el habitacion en la base de datos que tiene el ID dado por parametro. <br/>
	 * @param name -id del habitacion a buscar. id != null
	 * @return Habitacion - Habitacion que se obtiene como resultado de la consulta.
	 * @throws Exception -  cualquier error que se genere durante la transaccion
	 */
	public Habitacion getHabitacionById(Integer id) throws Exception {
		DAOHabitacion daoHabitacion = new DAOHabitacion();
		Habitacion habitacion = null;
		try 
		{
			this.conn = darConexion();
			daoHabitacion.setConn(conn);
			habitacion = daoHabitacion.findHabitacionById(id);
			if(habitacion == null)
			{
				throw new Exception("El habitacion con el id = " + id + " no se encuentra persistido en la base de datos.");				
			}
		} 
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				daoHabitacion.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return habitacion;
	}

	/**
	 * Método que modela la transacción que agrega un solo video a la base de datos.
	 * <b> post: </b> se ha agregado el video que entra como parámetro
	 * @param video - el video a agregar. video != null
	 * @throws Exception - cualquier error que se genera agregando el video
	 */
	public void addHabitacion(Habitacion habitacion) throws Exception {
		DAOHabitacion daoHabitacions = new DAOHabitacion();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoHabitacions.setConn(conn);
			daoHabitacions.addHabitacion(habitacion);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoHabitacions.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}



	/**
	 * Método que modela la transacción que agrega los videos que entran como parámetro a la base de datos.
	 * <b> post: </b> se han agregado los videos que entran como parámetro
	 * @param videos - objeto que modela una lista de videos y se estos se pretenden agregar. videos != null
	 * @throws Exception - cualquier error que se genera agregando los videos
	 */
	public void addHabitacions(ArrayList<Habitacion> habitacions) throws Exception {
		DAOHabitacion daoHabitacions = new DAOHabitacion();
		try 
		{
			//////Transacción - ACID Example
			this.conn = darConexion();
			conn.setAutoCommit(false);
			daoHabitacions.setConn(conn);
			for(Habitacion habitacion : (habitacions))
				daoHabitacions.addHabitacion(habitacion);
			conn.commit();
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			try {
				daoHabitacions.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}


	/**
	 * Método que modela la transacción que actualiza el video que entra como parámetro a la base de datos.
	 * <b> post: </b> se ha actualizado el video que entra como parámetro
	 * @param video - Video a actualizar. video != null
	 * @throws Exception - cualquier error que se genera actualizando los videos
	 */
	public void updateHabitacion(Habitacion habitacion) throws Exception {
		DAOHabitacion daoHabitacion = new DAOHabitacion();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoHabitacion.setConn(conn);
			daoHabitacion.updateHabitacion(habitacion);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoHabitacion.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}


	/**
	 * Método que modela la transacción que elimina el video que entra como parámetro a la base de datos.
	 * <b> post: </b> se ha eliminado el video que entra como parámetro
	 * @param video - Video a eliminar. video != null
	 * @throws Exception - cualquier error que se genera actualizando los videos
	 */
	public void deleteHabitacion(Habitacion habitacion) throws Exception {
		DAOHabitacion daoHabitacion = new DAOHabitacion();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoHabitacion.setConn(conn);
			daoHabitacion.deleteHabitacion(habitacion);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoHabitacion.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	//TODO FIN HABITACION INICIO HOSTAL

	/**
	 * Metodo que modela la transaccion que retorna todos los hostales de la base de datos. <br/>
	 * @return List<Hostal> - Lista de hostales que contiene el resultado de la consulta.
	 * @throws Exception -  Cualquier error que se genere durante la transaccion
	 */
	public List<Hostal> getAllHostales() throws Exception {
		DAOHostal daoHostal = new DAOHostal();
		List<Hostal> hostal;
		try 
		{
			this.conn = darConexion();
			daoHostal.setConn(conn);

			//Por simplicidad, solamente se obtienen los primeros 50 resultados de la consulta
			hostal = daoHostal.getHostals();
		}
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				daoHostal.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return hostal;
	}

	/**
	 * Metodo que modela la transaccion que busca el hostal en la base de datos que tiene el ID dado por parametro. <br/>
	 * @param name -id del hostal a buscar. id != null
	 * @return Hostal - Hostal que se obtiene como resultado de la consulta.
	 * @throws Exception -  cualquier error que se genere durante la transaccion
	 */
	public Hostal getHostalById(Integer id) throws Exception {
		DAOHostal daoHostal = new DAOHostal();
		Hostal hostal = null;
		try 
		{
			this.conn = darConexion();
			daoHostal.setConn(conn);
			hostal = daoHostal.findHostalById(id);
			if(hostal == null)
			{
				throw new Exception("El hostal con el id = " + id + " no se encuentra persistido en la base de datos.");				
			}
		} 
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				daoHostal.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return hostal;
	}

	/**
	 * Método que modela la transacción que agrega un solo video a la base de datos.
	 * <b> post: </b> se ha agregado el video que entra como parámetro
	 * @param video - el video a agregar. video != null
	 * @throws Exception - cualquier error que se genera agregando el video
	 */
	public void addHostal(Hostal hostal) throws Exception {
		DAOHostal daoHostals = new DAOHostal();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoHostals.setConn(conn);
			daoHostals.addHostal(hostal);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoHostals.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}



	/**
	 * Método que modela la transacción que agrega los videos que entran como parámetro a la base de datos.
	 * <b> post: </b> se han agregado los videos que entran como parámetro
	 * @param videos - objeto que modela una lista de videos y se estos se pretenden agregar. videos != null
	 * @throws Exception - cualquier error que se genera agregando los videos
	 */
	public void addHostals(ArrayList<Hostal> hostals) throws Exception {
		DAOHostal daoHostals = new DAOHostal();
		try 
		{
			//////Transacción - ACID Example
			this.conn = darConexion();
			conn.setAutoCommit(false);
			daoHostals.setConn(conn);
			for(Hostal hostal : (hostals))
				daoHostals.addHostal(hostal);
			conn.commit();
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			try {
				daoHostals.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}


	/**
	 * Método que modela la transacción que actualiza el video que entra como parámetro a la base de datos.
	 * <b> post: </b> se ha actualizado el video que entra como parámetro
	 * @param video - Video a actualizar. video != null
	 * @throws Exception - cualquier error que se genera actualizando los videos
	 */
	public void updateHostal(Hostal hostal) throws Exception {
		DAOHostal daoHostal = new DAOHostal();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoHostal.setConn(conn);
			daoHostal.updateHostal(hostal);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoHostal.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}


	/**
	 * Método que modela la transacción que elimina el video que entra como parámetro a la base de datos.
	 * <b> post: </b> se ha eliminado el video que entra como parámetro
	 * @param video - Video a eliminar. video != null
	 * @throws Exception - cualquier error que se genera actualizando los videos
	 */
	public void deleteHostal(Hostal hostal) throws Exception {
		DAOHostal daoHostal = new DAOHostal();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoHostal.setConn(conn);
			daoHostal.deleteHostal(hostal);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoHostal.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}


	//TODO FIN HOSTAL INICIO HOTEL


	/**
	 * Metodo que modela la transaccion que retorna todos los hoteles de la base de datos. <br/>
	 * @return List<Hotel> - Lista de hoteles que contiene el resultado de la consulta.
	 * @throws Exception -  Cualquier error que se genere durante la transaccion
	 */
	public List<Hotel> getAllHoteles() throws Exception {
		DAOHotel daoHotel = new DAOHotel();
		List<Hotel> hotel;
		try 
		{
			this.conn = darConexion();
			daoHotel.setConn(conn);

			//Por simplicidad, solamente se obtienen los primeros 50 resultados de la consulta
			hotel = daoHotel.getHotels();
		}
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				daoHotel.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return hotel;
	}

	/**
	 * Metodo que modela la transaccion que busca el hotel en la base de datos que tiene el ID dado por parametro. <br/>
	 * @param name -id del hotel a buscar. id != null
	 * @return Hotel - Hotel que se obtiene como resultado de la consulta.
	 * @throws Exception -  cualquier error que se genere durante la transaccion
	 */
	public Hotel getHotelById(Integer id) throws Exception {
		DAOHotel daoHotel = new DAOHotel();
		Hotel hotel = null;
		try 
		{
			this.conn = darConexion();
			daoHotel.setConn(conn);
			hotel = daoHotel.findHotelById(id);
			if(hotel == null)
			{
				throw new Exception("El hotel con el id = " + id + " no se encuentra persistido en la base de datos.");				
			}
		} 
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				daoHotel.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return hotel;
	}

	/**
	 * Método que modela la transacción que agrega un solo video a la base de datos.
	 * <b> post: </b> se ha agregado el video que entra como parámetro
	 * @param video - el video a agregar. video != null
	 * @throws Exception - cualquier error que se genera agregando el video
	 */
	public void addHotel(Hotel hotel) throws Exception {
		DAOHotel daoHotels = new DAOHotel();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoHotels.setConn(conn);
			daoHotels.addHotel(hotel);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoHotels.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}



	/**
	 * Método que modela la transacción que agrega los hoteles que entran como parámetro a la base de datos.
	 * <b> post: </b> se han agregado los hoteles que entran como parámetro
	 * @param videos - objeto que modela una lista de videos y se estos se pretenden agregar. videos != null
	 * @throws Exception - cualquier error que se genera agregando los videos
	 */
	public void addHotels(ArrayList<Hotel> hotels) throws Exception {
		DAOHotel daoHotels = new DAOHotel();
		try 
		{
			//////Transacción - ACID Example
			this.conn = darConexion();
			conn.setAutoCommit(false);
			daoHotels.setConn(conn);
			for(Hotel hotel : (hotels))
				daoHotels.addHotel(hotel);
			conn.commit();
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			try {
				daoHotels.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}


	/**
	 * Método que modela la transacción que actualiza el video que entra como parámetro a la base de datos.
	 * <b> post: </b> se ha actualizado el video que entra como parámetro
	 * @param video - Video a actualizar. video != null
	 * @throws Exception - cualquier error que se genera actualizando los videos
	 */
	public void updateHotel(Hotel hotel) throws Exception {
		DAOHotel daoHotel = new DAOHotel();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoHotel.setConn(conn);
			daoHotel.updateHotel(hotel);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoHotel.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}


	/**
	 * Método que modela la transacción que elimina el video que entra como parámetro a la base de datos.
	 * <b> post: </b> se ha eliminado el video que entra como parámetro
	 * @param video - Video a eliminar. video != null
	 * @throws Exception - cualquier error que se genera actualizando los videos
	 */
	public void deleteHotel(Hotel hotel) throws Exception {
		DAOHotel daoHotel = new DAOHotel();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoHotel.setConn(conn);
			daoHotel.deleteHotel(hotel);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoHotel.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	//TODO FIN HOTEL INICIO OFERTA

	/**
	 * Metodo que modela la transaccion que retorna todos los ofertaes de la base de datos. <br/>
	 * @return List<Oferta> - Lista de ofertaes que contiene el resultado de la consulta.
	 * @throws Exception -  Cualquier error que se genere durante la transaccion
	 */
	public List<Oferta> getAllOfertaes() throws Exception {
		DAOOferta daoOferta = new DAOOferta();
		List<Oferta> oferta;
		try 
		{
			this.conn = darConexion();
			daoOferta.setConn(conn);

			//Por simplicidad, solamente se obtienen los primeros 50 resultados de la consulta
			oferta = daoOferta.getOfertas();
		}
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				daoOferta.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return oferta;
	}

	/**
	 * Metodo que modela la transaccion que busca el oferta en la base de datos que tiene el ID dado por parametro. <br/>
	 * @param name -id del oferta a buscar. id != null
	 * @return Oferta - Oferta que se obtiene como resultado de la consulta.
	 * @throws Exception -  cualquier error que se genere durante la transaccion
	 */
	public Oferta getOfertaById(Integer id) throws Exception {
		DAOOferta daoOferta = new DAOOferta();
		Oferta oferta = null;
		try 
		{
			this.conn = darConexion();
			daoOferta.setConn(conn);
			oferta = daoOferta.findOfertaById(id);
			if(oferta == null)
			{
				throw new Exception("El oferta con el id = " + id + " no se encuentra persistido en la base de datos.");				
			}
		} 
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				daoOferta.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return oferta;
	}

	/**
	 * Método que modela la transacción que agrega un solo video a la base de datos.
	 * <b> post: </b> se ha agregado el video que entra como parámetro
	 * @param video - el video a agregar. video != null
	 * @throws Exception - cualquier error que se genera agregando el video
	 */
	public void addOferta(Oferta oferta) throws Exception {
		DAOOferta daoOfertas = new DAOOferta();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoOfertas.setConn(conn);
			daoOfertas.addOferta(oferta);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoOfertas.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}



	/**
	 * Método que modela la transacción que agrega los videos que entran como parámetro a la base de datos.
	 * <b> post: </b> se han agregado los videos que entran como parámetro
	 * @param videos - objeto que modela una lista de videos y se estos se pretenden agregar. videos != null
	 * @throws Exception - cualquier error que se genera agregando los videos
	 */
	public void addOfertas(ArrayList<Oferta> ofertas) throws Exception {
		DAOOferta daoOfertas = new DAOOferta();
		try 
		{
			//////Transacción - ACID Example
			this.conn = darConexion();
			conn.setAutoCommit(false);
			daoOfertas.setConn(conn);
			for(Oferta oferta : (ofertas))
				daoOfertas.addOferta(oferta);
			conn.commit();
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			try {
				daoOfertas.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}


	/**
	 * Método que modela la transacción que actualiza el video que entra como parámetro a la base de datos.
	 * <b> post: </b> se ha actualizado el video que entra como parámetro
	 * @param video - Video a actualizar. video != null
	 * @throws Exception - cualquier error que se genera actualizando los videos
	 */
	public void updateOferta(Oferta oferta) throws Exception {
		DAOOferta daoOferta = new DAOOferta();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoOferta.setConn(conn);
			daoOferta.updateOferta(oferta);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoOferta.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}


	/**
	 * Método que modela la transacción que elimina el video que entra como parámetro a la base de datos.
	 * <b> post: </b> se ha eliminado el video que entra como parámetro
	 * @param video - Video a eliminar. video != null
	 * @throws Exception - cualquier error que se genera actualizando los videos
	 */
	public void deleteOferta(Oferta oferta) throws Exception {
		DAOOferta daoOferta = new DAOOferta();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoOferta.setConn(conn);
			daoOferta.deleteOferta(oferta);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoOferta.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	//TODO FIN OFERTA INCIO PERSONA_NATURAL

	/**
	 * Metodo que modela la transaccion que retorna todos los personanaturales de la base de datos. <br/>
	 * @return List<PersonaNatural> - Lista de personanaturales que contiene el resultado de la consulta.
	 * @throws Exception -  Cualquier error que se genere durante la transaccion
	 */
	public List<PersonaNatural> getAllPersonaNaturales() throws Exception {
		DAOPersonaNatural daoPersonaNatural = new DAOPersonaNatural();
		List<PersonaNatural> personanatural;
		try 
		{
			this.conn = darConexion();
			daoPersonaNatural.setConn(conn);

			//Por simplicidad, solamente se obtienen los primeros 50 resultados de la consulta
			personanatural = daoPersonaNatural.getPersonaNaturals();
		}
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				daoPersonaNatural.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return personanatural;
	}

	/**
	 * Metodo que modela la transaccion que busca el personanatural en la base de datos que tiene el ID dado por parametro. <br/>
	 * @param name -id del personanatural a buscar. id != null
	 * @return PersonaNatural - PersonaNatural que se obtiene como resultado de la consulta.
	 * @throws Exception -  cualquier error que se genere durante la transaccion
	 */
	public PersonaNatural getPersonaNaturalById(Integer id) throws Exception {
		DAOPersonaNatural daoPersonaNatural = new DAOPersonaNatural();
		PersonaNatural personanatural = null;
		try 
		{
			this.conn = darConexion();
			daoPersonaNatural.setConn(conn);
			personanatural = daoPersonaNatural.findPersonaNaturalById(id);
			if(personanatural == null)
			{
				throw new Exception("El personanatural con el id = " + id + " no se encuentra persistido en la base de datos.");				
			}
		} 
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				daoPersonaNatural.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return personanatural;
	}

	/**
	 * Método que modela la transacción que agrega un solo video a la base de datos.
	 * <b> post: </b> se ha agregado el video que entra como parámetro
	 * @param video - el video a agregar. video != null
	 * @throws Exception - cualquier error que se genera agregando el video
	 */
	public void addPersonaNatural(PersonaNatural personanatural) throws Exception {
		DAOPersonaNatural daoPersonaNaturals = new DAOPersonaNatural();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoPersonaNaturals.setConn(conn);
			daoPersonaNaturals.addPersonaNatural(personanatural);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoPersonaNaturals.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}



	/**
	 * Método que modela la transacción que agrega los videos que entran como parámetro a la base de datos.
	 * <b> post: </b> se han agregado los videos que entran como parámetro
	 * @param videos - objeto que modela una lista de videos y se estos se pretenden agregar. videos != null
	 * @throws Exception - cualquier error que se genera agregando los videos
	 */
	public void addPersonaNaturals(ArrayList<PersonaNatural> personanaturals) throws Exception {
		DAOPersonaNatural daoPersonaNaturals = new DAOPersonaNatural();
		try 
		{
			//////Transacción - ACID Example
			this.conn = darConexion();
			conn.setAutoCommit(false);
			daoPersonaNaturals.setConn(conn);
			for(PersonaNatural personanatural : (personanaturals))
				daoPersonaNaturals.addPersonaNatural(personanatural);
			conn.commit();
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			try {
				daoPersonaNaturals.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}


	/**
	 * Método que modela la transacción que actualiza el video que entra como parámetro a la base de datos.
	 * <b> post: </b> se ha actualizado el video que entra como parámetro
	 * @param video - Video a actualizar. video != null
	 * @throws Exception - cualquier error que se genera actualizando los videos
	 */
	public void updatePersonaNatural(PersonaNatural personanatural) throws Exception {
		DAOPersonaNatural daoPersonaNatural = new DAOPersonaNatural();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoPersonaNatural.setConn(conn);
			daoPersonaNatural.updatePersonaNatural(personanatural);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoPersonaNatural.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}


	/**
	 * Método que modela la transacción que elimina el video que entra como parámetro a la base de datos.
	 * <b> post: </b> se ha eliminado el video que entra como parámetro
	 * @param video - Video a eliminar. video != null
	 * @throws Exception - cualquier error que se genera actualizando los videos
	 */
	public void deletePersonaNatural(PersonaNatural personanatural) throws Exception {
		DAOPersonaNatural daoPersonaNatural = new DAOPersonaNatural();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoPersonaNatural.setConn(conn);
			daoPersonaNatural.deletePersonaNatural(personanatural);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoPersonaNatural.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	//TODO FIN PERSONANATURAL INICIO SALA

	/**
	 * Metodo que modela la transaccion que retorna todos los salaes de la base de datos. <br/>
	 * @return List<Sala> - Lista de salaes que contiene el resultado de la consulta.
	 * @throws Exception -  Cualquier error que se genere durante la transaccion
	 */
	public List<Sala> getAllSalaes() throws Exception {
		DAOSala daoSala = new DAOSala();
		List<Sala> sala;
		try 
		{
			this.conn = darConexion();
			daoSala.setConn(conn);

			//Por simplicidad, solamente se obtienen los primeros 50 resultados de la consulta
			sala = daoSala.getSalas();
		}
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				daoSala.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return sala;
	}

	/**
	 * Metodo que modela la transaccion que busca el sala en la base de datos que tiene el ID dado por parametro. <br/>
	 * @param name -id del sala a buscar. id != null
	 * @return Sala - Sala que se obtiene como resultado de la consulta.
	 * @throws Exception -  cualquier error que se genere durante la transaccion
	 */
	public Sala getSalaById(Integer id) throws Exception {
		DAOSala daoSala = new DAOSala();
		Sala sala = null;
		try 
		{
			this.conn = darConexion();
			daoSala.setConn(conn);
			sala = daoSala.findSalaById(id);
			if(sala == null)
			{
				throw new Exception("El sala con el id = " + id + " no se encuentra persistido en la base de datos.");				
			}
		} 
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				daoSala.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return sala;
	}

	/**
	 * Método que modela la transacción que agrega un solo video a la base de datos.
	 * <b> post: </b> se ha agregado el video que entra como parámetro
	 * @param video - el video a agregar. video != null
	 * @throws Exception - cualquier error que se genera agregando el video
	 */
	public void addSala(Sala sala) throws Exception {
		DAOSala daoSalas = new DAOSala();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoSalas.setConn(conn);
			daoSalas.addSala(sala);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoSalas.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}



	/**
	 * Método que modela la transacción que agrega los videos que entran como parámetro a la base de datos.
	 * <b> post: </b> se han agregado los videos que entran como parámetro
	 * @param videos - objeto que modela una lista de videos y se estos se pretenden agregar. videos != null
	 * @throws Exception - cualquier error que se genera agregando los videos
	 */
	public void addSalas(ArrayList<Sala> salas) throws Exception {
		DAOSala daoSalas = new DAOSala();
		try 
		{
			//////Transacción - ACID Example
			this.conn = darConexion();
			conn.setAutoCommit(false);
			daoSalas.setConn(conn);
			for(Sala sala : (salas))
				daoSalas.addSala(sala);
			conn.commit();
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			try {
				daoSalas.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}


	/**
	 * Método que modela la transacción que actualiza el video que entra como parámetro a la base de datos.
	 * <b> post: </b> se ha actualizado el video que entra como parámetro
	 * @param video - Video a actualizar. video != null
	 * @throws Exception - cualquier error que se genera actualizando los videos
	 */
	public void updateSala(Sala sala) throws Exception {
		DAOSala daoSala = new DAOSala();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoSala.setConn(conn);
			daoSala.updateSala(sala);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoSala.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}


	/**
	 * Método que modela la transacción que elimina el video que entra como parámetro a la base de datos.
	 * <b> post: </b> se ha eliminado el video que entra como parámetro
	 * @param video - Video a eliminar. video != null
	 * @throws Exception - cualquier error que se genera actualizando los videos
	 */
	public void deleteSala(Sala sala) throws Exception {
		DAOSala daoSala = new DAOSala();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoSala.setConn(conn);
			daoSala.deleteSala(sala);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoSala.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	//TODO FIN SALA INICIO SERVICIO_INMOBILIARIO

	/**
	 * Metodo que modela la transaccion que retorna todos los servicioinmobiliarioes de la base de datos. <br/>
	 * @return List<ServicioInmobiliario> - Lista de servicioinmobiliarioes que contiene el resultado de la consulta.
	 * @throws Exception -  Cualquier error que se genere durante la transaccion
	 */
	public List<ServicioInmobiliario> getAllServicioInmobiliarioes() throws Exception {
		DAOServicioInmobiliario daoServicioInmobiliario = new DAOServicioInmobiliario();
		List<ServicioInmobiliario> servicioinmobiliario;
		try 
		{
			this.conn = darConexion();
			daoServicioInmobiliario.setConn(conn);

			//Por simplicidad, solamente se obtienen los primeros 50 resultados de la consulta
			servicioinmobiliario = daoServicioInmobiliario.getServicioInmobiliarios();
		}
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				daoServicioInmobiliario.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return servicioinmobiliario;
	}

	/**
	 * Metodo que modela la transaccion que busca el servicioinmobiliario en la base de datos que tiene el ID dado por parametro. <br/>
	 * @param name -id del servicioinmobiliario a buscar. id != null
	 * @return ServicioInmobiliario - ServicioInmobiliario que se obtiene como resultado de la consulta.
	 * @throws Exception -  cualquier error que se genere durante la transaccion
	 */
	public ServicioInmobiliario getServicioInmobiliarioById(Integer id) throws Exception {
		DAOServicioInmobiliario daoServicioInmobiliario = new DAOServicioInmobiliario();
		ServicioInmobiliario servicioinmobiliario = null;
		try 
		{
			this.conn = darConexion();
			daoServicioInmobiliario.setConn(conn);
			servicioinmobiliario = daoServicioInmobiliario.findServicioInmobiliarioById(id);
			if(servicioinmobiliario == null)
			{
				throw new Exception("El servicioinmobiliario con el id = " + id + " no se encuentra persistido en la base de datos.");				
			}
		} 
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				daoServicioInmobiliario.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return servicioinmobiliario;
	}

	/**
	 * Método que modela la transacción que agrega un solo video a la base de datos.
	 * <b> post: </b> se ha agregado el video que entra como parámetro
	 * @param video - el video a agregar. video != null
	 * @throws Exception - cualquier error que se genera agregando el video
	 */
	public void addServicioInmobiliario(ServicioInmobiliario servicioinmobiliario) throws Exception {
		DAOServicioInmobiliario daoServicioInmobiliarios = new DAOServicioInmobiliario();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoServicioInmobiliarios.setConn(conn);
			daoServicioInmobiliarios.addServicioInmobiliario(servicioinmobiliario);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoServicioInmobiliarios.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}



	/**
	 * Método que modela la transacción que agrega los videos que entran como parámetro a la base de datos.
	 * <b> post: </b> se han agregado los videos que entran como parámetro
	 * @param videos - objeto que modela una lista de videos y se estos se pretenden agregar. videos != null
	 * @throws Exception - cualquier error que se genera agregando los videos
	 */
	public void addServicioInmobiliarios(ArrayList<ServicioInmobiliario> servicioinmobiliarios) throws Exception {
		DAOServicioInmobiliario daoServicioInmobiliarios = new DAOServicioInmobiliario();
		try 
		{
			//////Transacción - ACID Example
			this.conn = darConexion();
			conn.setAutoCommit(false);
			daoServicioInmobiliarios.setConn(conn);
			for(ServicioInmobiliario servicioinmobiliario : (servicioinmobiliarios))
				daoServicioInmobiliarios.addServicioInmobiliario(servicioinmobiliario);
			conn.commit();
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			try {
				daoServicioInmobiliarios.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}


	/**
	 * Método que modela la transacción que actualiza el video que entra como parámetro a la base de datos.
	 * <b> post: </b> se ha actualizado el video que entra como parámetro
	 * @param video - Video a actualizar. video != null
	 * @throws Exception - cualquier error que se genera actualizando los videos
	 */
	public void updateServicioInmobiliario(ServicioInmobiliario servicioinmobiliario) throws Exception {
		DAOServicioInmobiliario daoServicioInmobiliario = new DAOServicioInmobiliario();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoServicioInmobiliario.setConn(conn);
			daoServicioInmobiliario.updateServicioInmobiliario(servicioinmobiliario);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoServicioInmobiliario.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}


	/**
	 * Método que modela la transacción que elimina el video que entra como parámetro a la base de datos.
	 * <b> post: </b> se ha eliminado el video que entra como parámetro
	 * @param video - Video a eliminar. video != null
	 * @throws Exception - cualquier error que se genera actualizando los videos
	 */
	public void deleteServicioInmobiliario(ServicioInmobiliario servicioinmobiliario) throws Exception {
		DAOServicioInmobiliario daoServicioInmobiliario = new DAOServicioInmobiliario();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoServicioInmobiliario.setConn(conn);
			daoServicioInmobiliario.deleteServicioInmobiliario(servicioinmobiliario);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoServicioInmobiliario.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	//TODO FIN SERVICIO_INMOBILIARIO INICIO SERVICIO_PUBLICO

	/**
	 * Metodo que modela la transaccion que retorna todos los serviciopublicoes de la base de datos. <br/>
	 * @return List<ServicioPublico> - Lista de serviciopublicoes que contiene el resultado de la consulta.
	 * @throws Exception -  Cualquier error que se genere durante la transaccion
	 */
	public List<ServicioPublico> getAllServicioPublicoes() throws Exception {
		DAOServicioPublico daoServicioPublico = new DAOServicioPublico();
		List<ServicioPublico> serviciopublico;
		try 
		{
			this.conn = darConexion();
			daoServicioPublico.setConn(conn);

			//Por simplicidad, solamente se obtienen los primeros 50 resultados de la consulta
			serviciopublico = daoServicioPublico.getServicioPublicos();
		}
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				daoServicioPublico.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return serviciopublico;
	}

	/**
	 * Metodo que modela la transaccion que busca el serviciopublico en la base de datos que tiene el ID dado por parametro. <br/>
	 * @param name -id del serviciopublico a buscar. id != null
	 * @return ServicioPublico - ServicioPublico que se obtiene como resultado de la consulta.
	 * @throws Exception -  cualquier error que se genere durante la transaccion
	 */
	public ServicioPublico getServicioPublicoById(Integer id) throws Exception {
		DAOServicioPublico daoServicioPublico = new DAOServicioPublico();
		ServicioPublico serviciopublico = null;
		try 
		{
			this.conn = darConexion();
			daoServicioPublico.setConn(conn);
			serviciopublico = daoServicioPublico.findServicioPublicoById(id);
			if(serviciopublico == null)
			{
				throw new Exception("El serviciopublico con el id = " + id + " no se encuentra persistido en la base de datos.");				
			}
		} 
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				daoServicioPublico.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return serviciopublico;
	}

	/**
	 * Método que modela la transacción que agrega un solo video a la base de datos.
	 * <b> post: </b> se ha agregado el video que entra como parámetro
	 * @param video - el video a agregar. video != null
	 * @throws Exception - cualquier error que se genera agregando el video
	 */
	public void addServicioPublico(ServicioPublico serviciopublico) throws Exception {
		DAOServicioPublico daoServicioPublicos = new DAOServicioPublico();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoServicioPublicos.setConn(conn);
			daoServicioPublicos.addServicioPublico(serviciopublico);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoServicioPublicos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}



	/**
	 * Método que modela la transacción que agrega los videos que entran como parámetro a la base de datos.
	 * <b> post: </b> se han agregado los videos que entran como parámetro
	 * @param videos - objeto que modela una lista de videos y se estos se pretenden agregar. videos != null
	 * @throws Exception - cualquier error que se genera agregando los videos
	 */
	public void addServicioPublicos(ArrayList<ServicioPublico> serviciopublicos) throws Exception {
		DAOServicioPublico daoServicioPublicos = new DAOServicioPublico();
		try 
		{
			//////Transacción - ACID Example
			this.conn = darConexion();
			conn.setAutoCommit(false);
			daoServicioPublicos.setConn(conn);
			for(ServicioPublico serviciopublico : (serviciopublicos))
				daoServicioPublicos.addServicioPublico(serviciopublico);
			conn.commit();
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			try {
				daoServicioPublicos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}


	/**
	 * Método que modela la transacción que actualiza el video que entra como parámetro a la base de datos.
	 * <b> post: </b> se ha actualizado el video que entra como parámetro
	 * @param video - Video a actualizar. video != null
	 * @throws Exception - cualquier error que se genera actualizando los videos
	 */
	public void updateServicioPublico(ServicioPublico serviciopublico) throws Exception {
		DAOServicioPublico daoServicioPublico = new DAOServicioPublico();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoServicioPublico.setConn(conn);
			daoServicioPublico.updateServicioPublico(serviciopublico);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoServicioPublico.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}


	/**
	 * Método que modela la transacción que elimina el video que entra como parámetro a la base de datos.
	 * <b> post: </b> se ha eliminado el video que entra como parámetro
	 * @param video - Video a eliminar. video != null
	 * @throws Exception - cualquier error que se genera actualizando los videos
	 */
	public void deleteServicioPublico(ServicioPublico serviciopublico) throws Exception {
		DAOServicioPublico daoServicioPublico = new DAOServicioPublico();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoServicioPublico.setConn(conn);
			daoServicioPublico.deleteServicioPublico(serviciopublico);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoServicioPublico.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	//TODO FIN SERVICIO_PUBLICO INICIO VECINO

	/**
	 * Metodo que modela la transaccion que retorna todos los vecinoes de la base de datos. <br/>
	 * @return List<Vecino> - Lista de vecinoes que contiene el resultado de la consulta.
	 * @throws Exception -  Cualquier error que se genere durante la transaccion
	 */
	public List<Vecino> getAllVecinoes() throws Exception {
		DAOVecino daoVecino = new DAOVecino();
		List<Vecino> vecino;
		try 
		{
			this.conn = darConexion();
			daoVecino.setConn(conn);

			//Por simplicidad, solamente se obtienen los primeros 50 resultados de la consulta
			vecino = daoVecino.getVecinos();
		}
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				daoVecino.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return vecino;
	}

	/**
	 * Metodo que modela la transaccion que busca el vecino en la base de datos que tiene el ID dado por parametro. <br/>
	 * @param name -id del vecino a buscar. id != null
	 * @return Vecino - Vecino que se obtiene como resultado de la consulta.
	 * @throws Exception -  cualquier error que se genere durante la transaccion
	 */
	public Vecino getVecinoById(Integer id) throws Exception {
		DAOVecino daoVecino = new DAOVecino();
		Vecino vecino = null;
		try 
		{
			this.conn = darConexion();
			daoVecino.setConn(conn);
			vecino = daoVecino.findVecinoById(id);
			if(vecino == null)
			{
				throw new Exception("El vecino con el id = " + id + " no se encuentra persistido en la base de datos.");				
			}
		} 
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				daoVecino.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return vecino;
	}

	/**
	 * Método que modela la transacción que agrega un solo video a la base de datos.
	 * <b> post: </b> se ha agregado el video que entra como parámetro
	 * @param video - el video a agregar. video != null
	 * @throws Exception - cualquier error que se genera agregando el video
	 */
	public void addVecino(Vecino vecino) throws Exception {
		DAOVecino daoVecinos = new DAOVecino();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoVecinos.setConn(conn);
			daoVecinos.addVecino(vecino);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoVecinos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}



	/**
	 * Método que modela la transacción que agrega los videos que entran como parámetro a la base de datos.
	 * <b> post: </b> se han agregado los videos que entran como parámetro
	 * @param videos - objeto que modela una lista de videos y se estos se pretenden agregar. videos != null
	 * @throws Exception - cualquier error que se genera agregando los videos
	 */
	public void addVecinos(ArrayList<Vecino> vecinos) throws Exception {
		DAOVecino daoVecinos = new DAOVecino();
		try 
		{
			//////Transacción - ACID Example
			this.conn = darConexion();
			conn.setAutoCommit(false);
			daoVecinos.setConn(conn);
			for(Vecino vecino : (vecinos))
				daoVecinos.addVecino(vecino);
			conn.commit();
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			try {
				daoVecinos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}


	/**
	 * Método que modela la transacción que actualiza el video que entra como parámetro a la base de datos.
	 * <b> post: </b> se ha actualizado el video que entra como parámetro
	 * @param video - Video a actualizar. video != null
	 * @throws Exception - cualquier error que se genera actualizando los videos
	 */
	public void updateVecino(Vecino vecino) throws Exception {
		DAOVecino daoVecino = new DAOVecino();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoVecino.setConn(conn);
			daoVecino.updateVecino(vecino);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoVecino.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}


	/**
	 * Método que modela la transacción que elimina el video que entra como parámetro a la base de datos.
	 * <b> post: </b> se ha eliminado el video que entra como parámetro
	 * @param video - Video a eliminar. video != null
	 * @throws Exception - cualquier error que se genera actualizando los videos
	 */
	public void deleteVecino(Vecino vecino) throws Exception {
		DAOVecino daoVecino = new DAOVecino();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoVecino.setConn(conn);
			daoVecino.deleteVecino(vecino);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoVecino.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	//TODO FIN VECINO INICIO VIVIENDA

	/**
	 * Metodo que modela la transaccion que retorna todos los viviendaes de la base de datos. <br/>
	 * @return List<Vivienda> - Lista de viviendaes que contiene el resultado de la consulta.
	 * @throws Exception -  Cualquier error que se genere durante la transaccion
	 */
	public List<Vivienda> getAllViviendaes() throws Exception {
		DAOVivienda daoVivienda = new DAOVivienda();
		List<Vivienda> vivienda;
		try 
		{
			this.conn = darConexion();
			daoVivienda.setConn(conn);

			//Por simplicidad, solamente se obtienen los primeros 50 resultados de la consulta
			vivienda = daoVivienda.getViviendas();
		}
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				daoVivienda.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return vivienda;
	}

	/**
	 * Metodo que modela la transaccion que busca el vivienda en la base de datos que tiene el ID dado por parametro. <br/>
	 * @param name -id del vivienda a buscar. id != null
	 * @return Vivienda - Vivienda que se obtiene como resultado de la consulta.
	 * @throws Exception -  cualquier error que se genere durante la transaccion
	 */
	public Vivienda getViviendaById(Integer id) throws Exception {
		DAOVivienda daoVivienda = new DAOVivienda();
		Vivienda vivienda = null;
		try 
		{
			this.conn = darConexion();
			daoVivienda.setConn(conn);
			vivienda = daoVivienda.findViviendaById(id);
			if(vivienda == null)
			{
				throw new Exception("El vivienda con el id = " + id + " no se encuentra persistido en la base de datos.");				
			}
		} 
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				daoVivienda.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return vivienda;
	}

	/**
	 * Método que modela la transacción que agrega un solo video a la base de datos.
	 * <b> post: </b> se ha agregado el video que entra como parámetro
	 * @param video - el video a agregar. video != null
	 * @throws Exception - cualquier error que se genera agregando el video
	 */
	public void addVivienda(Vivienda vivienda) throws Exception {
		DAOVivienda daoViviendas = new DAOVivienda();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoViviendas.setConn(conn);
			daoViviendas.addVivienda(vivienda);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoViviendas.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}



	/**
	 * Método que modela la transacción que agrega los videos que entran como parámetro a la base de datos.
	 * <b> post: </b> se han agregado los videos que entran como parámetro
	 * @param videos - objeto que modela una lista de videos y se estos se pretenden agregar. videos != null
	 * @throws Exception - cualquier error que se genera agregando los videos
	 */
	public void addViviendas(ArrayList<Vivienda> viviendas) throws Exception {
		DAOVivienda daoViviendas = new DAOVivienda();
		try 
		{
			//////Transacción - ACID Example
			this.conn = darConexion();
			conn.setAutoCommit(false);
			daoViviendas.setConn(conn);
			for(Vivienda vivienda : (viviendas))
				daoViviendas.addVivienda(vivienda);
			conn.commit();
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			try {
				daoViviendas.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}


	/**
	 * Método que modela la transacción que actualiza el video que entra como parámetro a la base de datos.
	 * <b> post: </b> se ha actualizado el video que entra como parámetro
	 * @param video - Video a actualizar. video != null
	 * @throws Exception - cualquier error que se genera actualizando los videos
	 */
	public void updateVivienda(Vivienda vivienda) throws Exception {
		DAOVivienda daoVivienda = new DAOVivienda();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoVivienda.setConn(conn);
			daoVivienda.updateVivienda(vivienda);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoVivienda.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}


	/**
	 * Método que modela la transacción que elimina el video que entra como parámetro a la base de datos.
	 * <b> post: </b> se ha eliminado el video que entra como parámetro
	 * @param video - Video a eliminar. video != null
	 * @throws Exception - cualquier error que se genera actualizando los videos
	 */
	public void deleteVivienda(Vivienda vivienda) throws Exception {
		DAOVivienda daoVivienda = new DAOVivienda();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoVivienda.setConn(conn);
			daoVivienda.deleteVivienda(vivienda);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoVivienda.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	//TODO FIN VIVIENDA INICIO VIVIENDAUNIVERSITARIA

	/**
	 * Metodo que modela la transaccion que retorna todos los vecinoes de la base de datos. <br/>
	 * @return List<Vecino> - Lista de vecinoes que contiene el resultado de la consulta.
	 * @throws Exception -  Cualquier error que se genere durante la transaccion
	 */
	public List<ViviendaUniversitaria> getAllViviendaUniversiatia() throws Exception {
		DAOViviendaUniversitaria daoVecino = new DAOViviendaUniversitaria();
		List<ViviendaUniversitaria> vecino;
		try 
		{
			this.conn = darConexion();
			daoVecino.setConn(conn);

			//Por simplicidad, solamente se obtienen los primeros 50 resultados de la consulta
			vecino = daoVecino.getViviendaUniversitarias();
		}
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				daoVecino.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return vecino;
	}

	/**
	 * Metodo que modela la transaccion que busca el vecino en la base de datos que tiene el ID dado por parametro. <br/>
	 * @param name -id del vecino a buscar. id != null
	 * @return Vecino - Vecino que se obtiene como resultado de la consulta.
	 * @throws Exception -  cualquier error que se genere durante la transaccion
	 */
	public ViviendaUniversitaria getViviendaUniversitariaById(Integer id) throws Exception {
		DAOViviendaUniversitaria daoVecino = new DAOViviendaUniversitaria();
		ViviendaUniversitaria vecino = null;
		try 
		{
			this.conn = darConexion();
			daoVecino.setConn(conn);
			vecino = daoVecino.findViviendaUniversitariaById(id);
			if(vecino == null)
			{
				throw new Exception("El vecino con el id = " + id + " no se encuentra persistido en la base de datos.");				
			}
		} 
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				daoVecino.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return vecino;
	}

	/**
	 * Método que modela la transacción que agrega un solo video a la base de datos.
	 * <b> post: </b> se ha agregado el video que entra como parámetro
	 * @param video - el video a agregar. video != null
	 * @throws Exception - cualquier error que se genera agregando el video
	 */
	public void addViviendaUniversitaria(ViviendaUniversitaria vecino) throws Exception {
		DAOViviendaUniversitaria daoVecinos = new DAOViviendaUniversitaria();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoVecinos.setConn(conn);
			daoVecinos.addViviendaUniversitaria(vecino);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoVecinos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}



	/**
	 * Método que modela la transacción que agrega los videos que entran como parámetro a la base de datos.
	 * <b> post: </b> se han agregado los videos que entran como parámetro
	 * @param videos - objeto que modela una lista de videos y se estos se pretenden agregar. videos != null
	 * @throws Exception - cualquier error que se genera agregando los videos
	 */
	public void addViviendaUniversitarias(ArrayList<ViviendaUniversitaria> vecinos) throws Exception {
		DAOViviendaUniversitaria daoVecinos = new DAOViviendaUniversitaria();
		try 
		{
			//////Transacción - ACID Example
			this.conn = darConexion();
			conn.setAutoCommit(false);
			daoVecinos.setConn(conn);
			for(ViviendaUniversitaria vecino : (vecinos))
				daoVecinos.addViviendaUniversitaria(vecino);
			conn.commit();
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			try {
				daoVecinos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}


	/**
	 * Método que modela la transacción que actualiza el video que entra como parámetro a la base de datos.
	 * <b> post: </b> se ha actualizado el video que entra como parámetro
	 * @param video - Video a actualizar. video != null
	 * @throws Exception - cualquier error que se genera actualizando los videos
	 */
	public void updateViviendaUniversitaria(ViviendaUniversitaria vecino) throws Exception {
		DAOViviendaUniversitaria daoVecino = new DAOViviendaUniversitaria();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoVecino.setConn(conn);
			daoVecino.updateViviendaUniversitaria(vecino);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoVecino.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}


	/**
	 * Método que modela la transacción que elimina el video que entra como parámetro a la base de datos.
	 * <b> post: </b> se ha eliminado el video que entra como parámetro
	 * @param video - Video a eliminar. video != null
	 * @throws Exception - cualquier error que se genera actualizando los videos
	 */
	public void deleteViviendaUniversitaria(ViviendaUniversitaria vecino) throws Exception {
		DAOViviendaUniversitaria daoVecino = new DAOViviendaUniversitaria();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoVecino.setConn(conn);
			daoVecino.deleteViviendaUniversitaria(vecino);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoVecino.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	//TODO FIN VIVIENDAUNIVERSITARIA INICIO RESERVA

	/**
	 * Metodo que modela la transaccion que retorna todas las reservas de la base de datos. <br/>
	 * @return List<Apartamento> - Lista de apartamentoes que contiene el resultado de la consulta.
	 * @throws Exception -  Cualquier error que se genere durante la transaccion
	 */
	public List<Reserva> getAllReservas() throws Exception {
		DAOReserva daoReserva = new DAOReserva();
		List<Reserva> reserva;
		try 
		{
			this.conn = darConexion();
			daoReserva.setConn(conn);

			//Por simplicidad, solamente se obtienen los primeros 50 resultados de la consulta
			reserva = daoReserva.getReservas();
		}
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				daoReserva.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return reserva;
	}

	/**
	 * Metodo que modela la transaccion que busca la reserva en la base de datos que tiene el ID dado por parametro. <br/>
	 * @param name -id de la reserva a buscar. id != null
	 * @return Reserva que se obtiene como resultado de la consulta.
	 * @throws Exception -  cualquier error que se genere durante la transaccion
	 */
	public Reserva getReservaById(Integer id) throws Exception {
		DAOReserva daoReserva = new DAOReserva();
		Reserva reserva = null;
		try 
		{
			this.conn = darConexion();
			daoReserva.setConn(conn);
			reserva = daoReserva.findReservaById(id);
			if(reserva == null)
			{
				throw new Exception("La reserva con el id = " + id + " no se encuentra persistida en la base de datos.");				
			}
		} 
		catch (SQLException sqlException) {
			System.err.println("[EXCEPTION] SQLException:" + sqlException.getMessage());
			sqlException.printStackTrace();
			throw sqlException;
		} 
		catch (Exception exception) {
			System.err.println("[EXCEPTION] General Exception:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		} 
		finally {
			try {
				daoReserva.cerrarRecursos();
				if(this.conn!=null){
					this.conn.close();					
				}
			}
			catch (SQLException exception) {
				System.err.println("[EXCEPTION] SQLException While Closing Resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return reserva;
	}

	/**
	 * M�todo que modela la transacci�n que agrega una sola reserva a la base de datos.
	 * <b> post: </b> se ha agregado la reserva que entra como par�metro
	 * @param reserva - la reserva a agregar. video != null
	 * @throws Exception - cualquier error que se genera agregando la reserva
	 */
	public void addReserva(Reserva reserva) throws Exception {
		DAOReserva daoReserva= new DAOReserva();
		try 
		{
			//////Transaccion
			this.conn = darConexion();
			daoReserva.setConn(conn);
			daoReserva.addReserva(reserva);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoReserva.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}



	/**
	 * Metodo que modela la transaccion que agrega las reservas que entran como parametro a la base de datos.
	 * <b> post: </b> se han agregado las reservas que entran como parametro
	 * @param videos - objeto que modela una lista de reservas y se estas se pretenden agregar. videos != null
	 * @throws Exception - cualquier error que se genera agregando las reservas
	 */
	public void addReservas(ArrayList<Reserva> reservas) throws Exception {
		DAOReserva daoReserva = new DAOReserva();
		try 
		{
			//////Transaccion - ACID Example
			this.conn = darConexion();
			conn.setAutoCommit(false);
			daoReserva.setConn(conn);
			for(Reserva reserva : (reservas))
				daoReserva.addReserva(reserva);
			conn.commit();
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			try {
				daoReserva.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}


	/**
	 * Metodo que modela la transaccion que actualiza la reserva que entra como parametro a la base de datos.
	 * <b> post: </b> se ha actualizado la reserva que entra como parametro
	 * @param reserva - Reserva a actualizar. reserva != null
	 * @throws Exception - cualquier error que se genera actualizando las reservas
	 */
	public void updateReserva(Reserva reserva) throws Exception {
		DAOReserva daoReserva = new DAOReserva();
		try 
		{
			//////Transaccion
			this.conn = darConexion();
			daoReserva.setConn(conn);
			daoReserva.updateReserva(reserva);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoReserva.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}


	/**
	 * Metodo que modela la transaccion que elimina la reserva que entra como parametro a la base de datos.
	 * <b> post: </b> se ha eliminado la reserva que entra como parametro
	 * @param reserva - Reserva a eliminar.reserva != null
	 * @throws Exception - cualquier error que se genera eliminando las reservas
	 */
	public void deleteReserva(Reserva reserva) throws Exception {
		DAOReserva daoReserva = new DAOReserva();
		try 
		{
			//////Transacci�n
			this.conn = darConexion();
			daoReserva.setConn(conn);

			daoReserva.deleteReserva(reserva);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoReserva.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	/**
	 * Metodo que modela la transaccion que elimina la reserva que entra como parametro a la base de datos.
	 * <b> post: </b> se ha eliminado la reserva que entra como parametro
	 * @param reserva - Reserva a eliminar. video != null
	 * @throws Exception - cualquier error que se genera eliminando las reservas
	 */
	public void cancelarReserva(Reserva reserva) throws Exception {
		DAOReserva daoReserva = new DAOReserva();
		try 
		{
			//////Transaccion
			this.conn = darConexion();
			daoReserva.setConn(conn);
			daoReserva.cancelarReserva(reserva);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoReserva.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}


	//TODO FIN RESERVA INICIO REQUERIMIENTOS EXTRA


	/**
	 * Método para agregar una oferta de un hostal
	 */
	public void agregarOfertaHostal(Integer hostal, Integer oferta, List<Integer> habitaciones, Integer sPub, Integer sIn) throws SQLException, Exception
	{
		this.conn = darConexion();
		DAOOferta daoOferta = new DAOOferta();
		daoOferta.setConn(conn);
		DAOHabitacion daoHabitacion = new DAOHabitacion();
		daoHabitacion.setConn(conn);
		DAOHabitacionesServicioInmobiliarioSerInm daoHabitacionesSer = new DAOHabitacionesServicioInmobiliarioSerInm();
		daoHabitacionesSer.setConn(conn);
		DAOHabitacionServicioPublico daoHabitacionSerPub =  new DAOHabitacionServicioPublico();
		daoHabitacionSerPub.setConn(conn);


		Oferta ofertaF = daoOferta.findOfertaById(oferta);

		ofertaF.setIdHostal(hostal);
		daoOferta.updateOferta(ofertaF);

		List<Habitacion> habs = new ArrayList<>();
		for (int i = 0; i < habitaciones.size(); i++) 
		{
			Habitacion act = daoHabitacion.findHabitacionById(habitaciones.get(i));
			act.setIdOferta(oferta);
			act.setIdHostal(hostal);
			List<HabitacionesServiciosInmobiliarios> sisa = daoHabitacionesSer.findHabitacionesServicioInmobiliarioById(habitaciones.get(i));
			for (HabitacionesServiciosInmobiliarios habitacion : sisa) 
			{
				habitacion.setIdServicioInmobiliario(sIn);
				daoHabitacionesSer.updateHabitacionesServicioInmobiliario(habitacion);
			}
			List<HabitacionesServicioPublico> noka = daoHabitacionSerPub.findHabitacionesServicioPublicoById(habitaciones.get(i));
			for (HabitacionesServicioPublico habitacion : noka) 
			{
				habitacion.setIdServicioPublico(sPub);
				daoHabitacionSerPub.updateHabitacionesServicioPublico((habitacion));
			}
			daoHabitacion.updateHabitacion(act);
		}
	}


	/**
	 * Método para agregar una oferta de un hotel
	 */
	public void agregarOfertaHotel(Integer hostal, Integer oferta, List<Integer> habitaciones, Integer sPub, Integer sIn) throws SQLException, Exception
	{
		DAOHotel daoHotel = new DAOHotel();
		daoHotel.setConn(conn);
		DAOOferta daoOferta = new DAOOferta();
		daoOferta.setConn(conn);
		DAOHabitacion daoHabitacion = new DAOHabitacion();
		daoHabitacion.setConn(conn);
		DAOServicioPublico daoServicioPub = new DAOServicioPublico();
		daoServicioPub.setConn(conn);
		DAOServicioInmobiliario daoServicioInm = new DAOServicioInmobiliario();
		daoServicioInm.setConn(conn);
		DAOHabitacionesServicioInmobiliarioSerInm daoHabitacionesSer = new DAOHabitacionesServicioInmobiliarioSerInm();
		daoHabitacionesSer.setConn(conn);
		DAOHabitacionServicioPublico daoHabitacionSerPub =  new DAOHabitacionServicioPublico();
		daoHabitacionSerPub.setConn(conn);



		Oferta ofertta = daoOferta.findOfertaById(oferta);
		ofertta.setIdHotel(hostal);
		daoOferta.updateOferta(ofertta);
		List<Habitacion> habs = new ArrayList<>();
		for (int i = 0; i < habitaciones.size(); i++) 
		{
			Habitacion act = daoHabitacion.findHabitacionById(habitaciones.get(i));
			act.setIdOferta(oferta);
			act.setIdHotel(hostal);
			List<HabitacionesServiciosInmobiliarios> sisa = daoHabitacionesSer.findHabitacionesServicioInmobiliarioById(habitaciones.get(i));
			for (HabitacionesServiciosInmobiliarios habitacion : sisa) 
			{
				habitacion.setIdServicioInmobiliario(sIn);
				daoHabitacionesSer.updateHabitacionesServicioInmobiliario(habitacion);
			}
			List<HabitacionesServicioPublico> noka = daoHabitacionSerPub.findHabitacionesServicioPublicoById(habitaciones.get(i));
			for (HabitacionesServicioPublico habitacion : noka) 
			{
				habitacion.setIdServicioPublico(sPub);
				daoHabitacionSerPub.updateHabitacionesServicioPublico((habitacion));
			}
			daoHabitacion.updateHabitacion(act);
		}
	}



	/**
	 * Método para agregar una oferta de una vivienda universitaria
	 */
	public void agregarOfertaViviendaUniversitaria(Integer viviendau, Integer oferta, List<Integer> habitaciones, Integer sPub, Integer sIn) throws SQLException, Exception
	{
		DAOViviendaUniversitaria daoViviendau = new DAOViviendaUniversitaria();
		DAOOferta daoOferta = new DAOOferta();
		daoOferta.setConn(conn);
		DAOHabitacion daoHabitacion = new DAOHabitacion();
		daoViviendau.setConn(conn);
		daoHabitacion.setConn(conn);
		DAOServicioPublico daoServicioPub = new DAOServicioPublico();
		daoServicioPub.setConn(conn);
		DAOServicioInmobiliario daoServicioInm = new DAOServicioInmobiliario();
		daoServicioInm.setConn(conn);
		DAOHabitacionesServicioInmobiliarioSerInm daoHabitacionesSer = new DAOHabitacionesServicioInmobiliarioSerInm();
		daoHabitacionesSer.setConn(conn);
		DAOHabitacionServicioPublico daoHabitacionSerPub =  new DAOHabitacionServicioPublico();
		daoHabitacionSerPub.setConn(conn);

		Oferta ofertta = daoOferta.findOfertaById(oferta);
		ofertta.setIdViviendaU(viviendau);
		daoOferta.updateOferta(ofertta);
		List<Habitacion> habs = new ArrayList<>();
		for (int i = 0; i < habitaciones.size(); i++) 
		{
			Habitacion act = daoHabitacion.findHabitacionById(habitaciones.get(i));
			act.setIdOferta(oferta);
			act.setIdViviendaU(viviendau);
			List<HabitacionesServiciosInmobiliarios> sisa = daoHabitacionesSer.findHabitacionesServicioInmobiliarioById(habitaciones.get(i));
			for (HabitacionesServiciosInmobiliarios habitacion : sisa) 
			{
				habitacion.setIdServicioInmobiliario(sIn);
				daoHabitacionesSer.updateHabitacionesServicioInmobiliario(habitacion);
			}
			List<HabitacionesServicioPublico> noka = daoHabitacionSerPub.findHabitacionesServicioPublicoById(habitaciones.get(i));
			for (HabitacionesServicioPublico habitacion : noka) 
			{
				habitacion.setIdServicioPublico(sPub);
				daoHabitacionSerPub.updateHabitacionesServicioPublico((habitacion));
			}
			daoHabitacion.updateHabitacion(act);
		}
	}

	/**
	 * Método para agregar una oferta de una persona natural
	 */
	public void agregarOfertaPersonaNatural(Integer persona, Integer oferta, List<Integer> habitaciones, Integer sPub, Integer sIn) throws SQLException, Exception
	{
		DAOPersonaNatural daoPersona = new DAOPersonaNatural();
		daoPersona.setConn(conn);
		DAOOferta daoOferta = new DAOOferta();
		daoOferta.setConn(conn);
		DAOHabitacion daoHabitacion = new DAOHabitacion();
		daoHabitacion.setConn(conn);
		DAOServicioPublico daoServicioPub = new DAOServicioPublico();
		daoServicioPub.setConn(conn);
		DAOServicioInmobiliario daoServicioInm = new DAOServicioInmobiliario();
		daoServicioInm.setConn(conn);
		DAOHabitacionesServicioInmobiliarioSerInm daoHabitacionesSer = new DAOHabitacionesServicioInmobiliarioSerInm();
		daoHabitacionesSer.setConn(conn);
		DAOHabitacionServicioPublico daoHabitacionSerPub =  new DAOHabitacionServicioPublico();
		daoHabitacionSerPub.setConn(conn);

		Oferta ofertta = daoOferta.findOfertaById(oferta);
		ofertta.setIdPersona(persona);
		daoOferta.updateOferta(ofertta);
		List<Habitacion> habs = new ArrayList<>();
		for (int i = 0; i < habitaciones.size(); i++) 
		{
			Habitacion act = daoHabitacion.findHabitacionById(habitaciones.get(i));
			act.setIdOferta(oferta);
			act.setIdPersona(persona);
			List<HabitacionesServiciosInmobiliarios> sisa = daoHabitacionesSer.findHabitacionesServicioInmobiliarioById(habitaciones.get(i));
			for (HabitacionesServiciosInmobiliarios habitacion : sisa) 
			{
				habitacion.setIdServicioInmobiliario(sIn);
				daoHabitacionesSer.updateHabitacionesServicioInmobiliario(habitacion);
			}
			List<HabitacionesServicioPublico> noka = daoHabitacionSerPub.findHabitacionesServicioPublicoById(habitaciones.get(i));
			for (HabitacionesServicioPublico habitacion : noka) 
			{
				habitacion.setIdServicioPublico(sPub);
				daoHabitacionSerPub.updateHabitacionesServicioPublico((habitacion));
			}
			daoHabitacion.updateHabitacion(act);
		}
	}

	//TODO FIN OFERTA INICIO RESERVA

	public void agregarReservaHotel(Integer cliente, Integer reserva, List<Integer> habitaciones, Integer hotel) throws SQLException, Exception
	{
		DAOReserva daoReserva = new DAOReserva();
		daoReserva.setConn(conn);
		DAOHabitacion daoHabitacion = new DAOHabitacion();
		daoHabitacion.setConn(conn);
		DAOOferta daoOferta = new DAOOferta();
		daoOferta.setConn(conn);


		for(int  i = 0; i<habitaciones.size();i++)
		{
			Habitacion sisa = daoHabitacion.findHabitacionById(habitaciones.get(i));
			sisa.setIdReserva(reserva);
			daoHabitacion.updateHabitacion(sisa);
		}
		Reserva sisax = daoReserva.findReservaById(reserva);
		sisax.setIdCliente(cliente);
		daoReserva.updateReserva(sisax);

		ArrayList<Oferta> ofertas = daoOferta.getOfertas();
		boolean tru = false;
		for(int i = 0; i < ofertas.size() && tru; i++)
		{
			Oferta oferta = ofertas.get(i);
			if(oferta.getIdHotel() == hotel)
			{
				oferta.setNumReservas(oferta.getNumReservas() +1);
				tru = true;
				daoOferta.updateOferta(oferta);
			}
		}
	}

	public void agregarReservaHostal(Integer cliente, Integer reserva, List<Integer> habitaciones, Integer hotel) throws SQLException, Exception
	{
		DAOReserva daoReserva = new DAOReserva();
		daoReserva.setConn(conn);
		DAOHabitacion daoHabitacion = new DAOHabitacion();
		daoHabitacion.setConn(conn);
		DAOOferta daoOferta = new DAOOferta();
		daoOferta.setConn(conn);


		for(int  i = 0; i<habitaciones.size();i++)
		{
			Habitacion sisa = daoHabitacion.findHabitacionById(habitaciones.get(i));
			sisa.setIdReserva(reserva);
			daoHabitacion.updateHabitacion(sisa);
		}
		Reserva sisax = daoReserva.findReservaById(reserva);
		sisax.setIdCliente(cliente);
		daoReserva.updateReserva(sisax);

		ArrayList<Oferta> ofertas = daoOferta.getOfertas();
		boolean tru = false;
		for(int i = 0; i < ofertas.size() && tru; i++)
		{
			Oferta oferta = ofertas.get(i);
			if(oferta.getIdHostal() == hotel)
			{
				oferta.setNumReservas(oferta.getNumReservas() +1);
				tru = true;
				daoOferta.updateOferta(oferta);
			}
		}
	}

	public void agregarReservaPersonaNatural(Integer cliente, Integer reserva, List<Integer> habitaciones, Integer hotel) throws SQLException, Exception
	{
		DAOReserva daoReserva = new DAOReserva();
		daoReserva.setConn(conn);
		DAOHabitacion daoHabitacion = new DAOHabitacion();
		daoHabitacion.setConn(conn);
		DAOOferta daoOferta = new DAOOferta();
		daoOferta.setConn(conn);


		for(int  i = 0; i<habitaciones.size();i++)
		{
			Habitacion sisa = daoHabitacion.findHabitacionById(habitaciones.get(i));
			sisa.setIdReserva(reserva);
			daoHabitacion.updateHabitacion(sisa);
		}
		Reserva sisax = daoReserva.findReservaById(reserva);
		sisax.setIdCliente(cliente);
		daoReserva.updateReserva(sisax);

		ArrayList<Oferta> ofertas = daoOferta.getOfertas();
		boolean tru = false;
		for(int i = 0; i < ofertas.size() && tru; i++)
		{
			Oferta oferta = ofertas.get(i);
			if(oferta.getIdPersona() == hotel)
			{
				oferta.setNumReservas(oferta.getNumReservas() +1);
				tru = true;
				daoOferta.updateOferta(oferta);
			}
		}
	}

	public void agregarReservaVecino(Integer cliente, Integer reserva, List<Integer> habitaciones, Integer hotel) throws SQLException, Exception
	{
		DAOReserva daoReserva = new DAOReserva();
		daoReserva.setConn(conn);
		DAOHabitacion daoHabitacion = new DAOHabitacion();
		daoHabitacion.setConn(conn);
		DAOOferta daoOferta = new DAOOferta();
		daoOferta.setConn(conn);


		for(int  i = 0; i<habitaciones.size();i++)
		{
			Habitacion sisa = daoHabitacion.findHabitacionById(habitaciones.get(i));
			sisa.setIdReserva(reserva);
			daoHabitacion.updateHabitacion(sisa);
		}
		Reserva sisax = daoReserva.findReservaById(reserva);
		sisax.setIdCliente(cliente);
		daoReserva.updateReserva(sisax);

		ArrayList<Oferta> ofertas = daoOferta.getOfertas();
		boolean tru = false;
		for(int i = 0; i < ofertas.size() && tru; i++)
		{
			Oferta oferta = ofertas.get(i);
			if(oferta.getIdViviendaU()== hotel)
			{
				oferta.setNumReservas(oferta.getNumReservas() +1);
				tru = true;
				daoOferta.updateOferta(oferta);
			}
		}
	}

	public void agregarReservaViviendaUniversitaria(Integer cliente, Integer reserva, List<Integer> habitaciones, Integer hotel) throws SQLException, Exception
	{
		DAOReserva daoReserva = new DAOReserva();
		DAOHabitacion daoHabitacion = new DAOHabitacion();
		DAOOferta daoOferta = new DAOOferta();


		for(int  i = 0; i<habitaciones.size();i++)
		{
			Habitacion sisa = daoHabitacion.findHabitacionById(habitaciones.get(i));
			sisa.setIdReserva(reserva);
			daoHabitacion.updateHabitacion(sisa);
		}
		Reserva sisax = daoReserva.findReservaById(reserva);
		sisax.setIdCliente(cliente);
		daoReserva.updateReserva(sisax);

		ArrayList<Oferta> ofertas = daoOferta.getOfertas();
		boolean tru = false;
		for(int i = 0; i < ofertas.size() && tru; i++)
		{
			Oferta oferta = ofertas.get(i);
			if(oferta.getIdViviendaU()== hotel)
			{
				oferta.setNumReservas(oferta.getNumReservas() +1);
				tru = true;
				daoOferta.updateOferta(oferta);
			}
		}
	}

	//----------------------------------------------------------------------------------
	//INICIO REQUERIMIENTOS DE CONSULTA
	//----------------------------------------------------------------------------------


	//Requerimiento de consulta 1
	public ArrayList<VOHostalExtra> operadoresConCosas1() throws SQLException, Exception
	{
		DAORequerimientosenSQL daoRequerimientos = new DAORequerimientosenSQL();
		conn = darConexion();
		daoRequerimientos.setConn(conn);
		ArrayList<VOHostalExtra> rta1 = daoRequerimientos.getDineroHostal();
		return rta1;
	}

	public ArrayList<VOExtraHotel> operadoresConCosas2() throws SQLException, Exception
	{
		DAORequerimientosenSQL daoRequerimientos = new DAORequerimientosenSQL();
		conn = darConexion();
		daoRequerimientos.setConn(conn);
		ArrayList<VOExtraHotel> rta1 = daoRequerimientos.getDineroHotel();
		return rta1;
	}

	public ArrayList<VOExtraPersona> operadoresConCosas3() throws SQLException, Exception
	{
		DAORequerimientosenSQL daoRequerimientos = new DAORequerimientosenSQL();
		conn = darConexion();
		daoRequerimientos.setConn(conn);
		ArrayList<VOExtraPersona> rta1 = daoRequerimientos.getDineroPersona();
		return rta1;
	}

	public ArrayList<VOExtraViviendaUniversitaria> operadoresConCosas4() throws SQLException, Exception
	{
		DAORequerimientosenSQL daoRequerimientos = new DAORequerimientosenSQL();
		conn = darConexion();
		daoRequerimientos.setConn(conn);
		ArrayList<VOExtraViviendaUniversitaria> rta1 = daoRequerimientos.getDineroViviendaUniversitaria();
		return rta1;
	}

	//Requerimiento de consulta 2
	public ArrayList<Oferta> operadoresMejores() throws SQLException, Exception
	{
		DAORequerimientosenSQL daoRequerimientos = new DAORequerimientosenSQL();
		conn = darConexion();
		daoRequerimientos.setConn(conn);
		ArrayList<vos.Oferta> rta = daoRequerimientos.get20Ofertas();
		return rta;		
	}

	//Requerimiento de consulta 3
	public ArrayList<VOIndiceHotel> ocupacionHoteles() throws SQLException, Exception
	{
		DAORequerimientosenSQL daoRequerimientos = new DAORequerimientosenSQL();
		conn = darConexion();
		daoRequerimientos.setConn(conn);
		ArrayList<VOIndiceHotel> rta = daoRequerimientos.getIndiceOfertasHotel();
		return rta;		
	}

	public ArrayList<VOIndiceHostal> ocupacionHostales() throws SQLException, Exception
	{
		DAORequerimientosenSQL daoRequerimientos = new DAORequerimientosenSQL();
		conn = darConexion();
		daoRequerimientos.setConn(conn);
		ArrayList<VOIndiceHostal> rta = daoRequerimientos.getIndiceOfertasHostal();
		return rta;		
	}

	public ArrayList<VOIndicePersona> ocupacionPersonas() throws SQLException, Exception
	{
		DAORequerimientosenSQL daoRequerimientos = new DAORequerimientosenSQL();
		conn = darConexion();
		daoRequerimientos.setConn(conn);
		ArrayList<VOIndicePersona> rta = daoRequerimientos.getIndiceOfertasPersona();
		return rta;		
	}

	public ArrayList<VOIndiceVivendaU> ocupacionViviendaU() throws SQLException, Exception
	{
		DAORequerimientosenSQL daoRequerimientos = new DAORequerimientosenSQL();
		conn = darConexion();
		daoRequerimientos.setConn(conn);
		ArrayList<VOIndiceVivendaU> rta = daoRequerimientos.getIndiceOfertasViviendaU();
		return rta;		
	}

	//Requerimiento de consulta 4
	public ArrayList<VODisponible> disponibilidadHotel(Integer diaInic, Integer diaFin, Integer mesInic, Integer mesFinal,
			String tipoServicioInmobiliario, String tipoServicioPublico) throws SQLException, Exception
			{
		DAORequerimientosenSQL daoRequerimientos = new DAORequerimientosenSQL();
		conn = darConexion();
		daoRequerimientos.setConn(conn);
		ArrayList<VODisponible> rta = daoRequerimientos.getAlojamientosDisponiblesHotel(diaInic, diaFin, mesInic, mesFinal, tipoServicioInmobiliario, tipoServicioPublico);
		return rta;		
			}

	public ArrayList<VODisponible> disponibilidadHostal(Integer diaInic, Integer diaFin, Integer mesInic, Integer mesFinal,
			String tipoServicioInmobiliario, String tipoServicioPublico) throws SQLException, Exception
			{
		DAORequerimientosenSQL daoRequerimientos = new DAORequerimientosenSQL();
		conn = darConexion();
		daoRequerimientos.setConn(conn);
		ArrayList<VODisponible> rta = daoRequerimientos.getAlojamientosDisponiblesHostal(diaInic, diaFin, mesInic, mesFinal, tipoServicioInmobiliario, tipoServicioPublico);
		return rta;		
			}

	public ArrayList<VODisponible> disponibilidadPersona(Integer diaInic, Integer diaFin, Integer mesInic, Integer mesFinal,
			String tipoServicioInmobiliario, String tipoServicioPublico) throws SQLException, Exception
			{
		DAORequerimientosenSQL daoRequerimientos = new DAORequerimientosenSQL();
		conn = darConexion();
		daoRequerimientos.setConn(conn);
		ArrayList<VODisponible> rta = daoRequerimientos.getAlojamientosDisponiblesPersona(diaInic, diaFin, mesInic, mesFinal, tipoServicioInmobiliario, tipoServicioPublico);
		return rta;		
			}

	public ArrayList<VODisponible> disponibilidadViviendaU(Integer diaInic, Integer diaFin, Integer mesInic, Integer mesFinal,
			String tipoServicioInmobiliario, String tipoServicioPublico) throws SQLException, Exception
			{
		DAORequerimientosenSQL daoRequerimientos = new DAORequerimientosenSQL();
		conn = darConexion();
		daoRequerimientos.setConn(conn);
		ArrayList<VODisponible> rta = daoRequerimientos.getAlojamientosDisponiblesViviendaU(diaInic, diaFin, mesInic, mesFinal, tipoServicioInmobiliario, tipoServicioPublico);
		return rta;		
			}

	//Requerimiento de consulta 5
	public ArrayList<VOUsoHotel> usoHotel() throws SQLException, Exception
	{
		DAORequerimientosenSQL daoRequerimientos = new DAORequerimientosenSQL();
		conn = darConexion();
		daoRequerimientos.setConn(conn);
		ArrayList<VOUsoHotel> rta = daoRequerimientos.getUsoHotel();
		return rta;		
	}

	public ArrayList<VOUsoHostal> usoHostal() throws SQLException, Exception
	{
		DAORequerimientosenSQL daoRequerimientos = new DAORequerimientosenSQL();
		conn = darConexion();
		daoRequerimientos.setConn(conn);
		ArrayList<VOUsoHostal> rta = daoRequerimientos.getUsoHostal();
		return rta;		
	}

	public ArrayList<VOUsoPersona> usoPersona() throws SQLException, Exception
	{
		DAORequerimientosenSQL daoRequerimientos = new DAORequerimientosenSQL();
		conn = darConexion();
		daoRequerimientos.setConn(conn);
		ArrayList<VOUsoPersona> rta = daoRequerimientos.getUsoPersona();
		return rta;		
	}

	public ArrayList<VOUsoVivienda> usoViviendaU() throws SQLException, Exception
	{
		DAORequerimientosenSQL daoRequerimientos = new DAORequerimientosenSQL();
		conn = darConexion();
		daoRequerimientos.setConn(conn);
		ArrayList<VOUsoVivienda> rta = daoRequerimientos.getUsoVivienda();
		return rta;		
	}

	//Requerimiento de consulta 6
	public ArrayList<VOUsoCliente> usoGeneralCliente(Integer idCliente) throws SQLException, Exception
	{
		DAORequerimientosenSQL daoRequerimientos = new DAORequerimientosenSQL();
		conn = darConexion();
		daoRequerimientos.setConn(conn);
		ArrayList<VOUsoCliente> rta = daoRequerimientos.getUsoUsuario(idCliente);
		return rta;		
	}

	public ArrayList<VOUsoEspecificoCliente> usoEspecificoCliente(Integer idCliente) throws SQLException, Exception
	{
		DAORequerimientosenSQL daoRequerimientos = new DAORequerimientosenSQL();
		conn = darConexion();
		daoRequerimientos.setConn(conn);
		ArrayList<VOUsoEspecificoCliente> rta = daoRequerimientos.getUsoEspecificoUsuario(idCliente);
		return rta;		
	}

	//Requerimiento de consulta 7
	public ArrayList<VOFechaDemanda> fechaMayorDemanda(String tipoAlojamiento, Integer mes) throws SQLException, Exception
	{
		DAORequerimientosenSQL daoRequerimientos = new DAORequerimientosenSQL();
		conn = darConexion();
		daoRequerimientos.setConn(conn);
		ArrayList<VOFechaDemanda> rta = daoRequerimientos.getFechaMayorDemanda(tipoAlojamiento, mes);
		return rta;		
	}

	public ArrayList<VOFechaIngresos> fechaMayorIngresos(String tipoAlojamiento, Integer mes) throws SQLException, Exception
	{
		DAORequerimientosenSQL daoRequerimientos = new DAORequerimientosenSQL();
		conn = darConexion();
		daoRequerimientos.setConn(conn);
		ArrayList<VOFechaIngresos> rta = daoRequerimientos.getFechaMayorIngresos(tipoAlojamiento, mes);
		return rta;		
	}

	public ArrayList<VOFechaDemanda> fechaMenorDemanda(String tipoAlojamiento, Integer mes) throws SQLException, Exception
	{
		DAORequerimientosenSQL daoRequerimientos = new DAORequerimientosenSQL();
		conn = darConexion();
		daoRequerimientos.setConn(conn);
		ArrayList<VOFechaDemanda> rta = daoRequerimientos.getFechaMenorDemanda(tipoAlojamiento, mes);
		return rta;		
	}

	//Requerimiento de consulta 8
	public ArrayList<VOClienteDuracion> clienteFrecuenteDuracion(String tipoAlojamiento, Integer identificadorAlojamiento) throws SQLException, Exception
	{
		DAORequerimientosenSQL daoRequerimientos = new DAORequerimientosenSQL();
		conn = darConexion();
		daoRequerimientos.setConn(conn);
		ArrayList<VOClienteDuracion> rta = daoRequerimientos.getClienteFrecuenteDuracion(tipoAlojamiento, identificadorAlojamiento);
		return rta;		
	}

	public ArrayList<VOClienteReservas> clienteFrecuenteReservas() throws SQLException, Exception
	{
		DAORequerimientosenSQL daoRequerimientos = new DAORequerimientosenSQL();
		conn = darConexion();
		daoRequerimientos.setConn(conn);
		ArrayList<VOClienteReservas> rta = daoRequerimientos.getClienteFrecuenteReservas();
		return rta;		
	}

	//Requerimiento de consulta 10
	public ArrayList<VOConsumo> consumoPrimeraVersionAdmin(Integer idOferta, Integer diaInic, Integer diaFin, Integer mesInic, Integer mesFin,String criterio) throws SQLException, Exception{
		DAORequerimientosenSQL daoRequerimientos = new DAORequerimientosenSQL();
		conn = darConexion();
		daoRequerimientos.setConn(conn);
		ArrayList<VOConsumo> rta = daoRequerimientos.getConsumoPrimeraVersionAdministrador(idOferta, diaInic, diaFin, mesInic, mesFin, criterio);
		return rta;
	}

	public ArrayList<VOConsumo> consumoPrimeraVersionCliente(Integer idOferta, Integer diaInic, Integer diaFin, Integer mesInic, Integer mesFin, Integer IdCli, String criterio) throws SQLException, Exception{
		DAORequerimientosenSQL daoRequerimientos = new DAORequerimientosenSQL();
		conn = darConexion();
		daoRequerimientos.setConn(conn);
		ArrayList<VOConsumo> rta = daoRequerimientos.getConsumoPrimeraVersionCliente(idOferta, diaInic, diaFin, mesInic, mesFin, IdCli, criterio);
		return rta;
	}
	//Requerimiento de consulta 11
	public ArrayList<VOConsumoVersion2> consumoSegundaVersionAdmin(Integer diaInic, Integer diaFin, Integer mesInic, Integer mesFin) throws SQLException, Exception{

		DAORequerimientosenSQL daoRequerimientos = new DAORequerimientosenSQL();
		conn = darConexion();
		daoRequerimientos.setConn(conn);
		ArrayList<VOConsumoVersion2> rta = daoRequerimientos.getConsumoSegundaVersionAdmin(diaInic, diaFin, mesInic, mesFin);
		return rta;
	}

	public ArrayList<VOConsumoVersion2> consumoSegundaVersionCliente(Integer diaInic, Integer diaFin, Integer mesInic, Integer mesFin, Integer cliente) throws SQLException, Exception{

		DAORequerimientosenSQL daoRequerimientos = new DAORequerimientosenSQL();
		conn = darConexion();
		daoRequerimientos.setConn(conn);
		ArrayList<VOConsumoVersion2> rta = daoRequerimientos.getConsumoSegundaVersionCliente(diaInic, diaFin, mesInic, mesFin, cliente);
		return rta;	
	}

	//Requerimiento de consulta 12
	public ArrayList<VOSemanas> semanasDeFecha() throws SQLException, Exception{

		DAORequerimientosenSQL daoRequerimientos =  new DAORequerimientosenSQL();
		conn = darConexion();
		daoRequerimientos.setConn(conn);
		ArrayList<VOSemanas> rta = daoRequerimientos.getSemanaDeFecha();
		return rta;	
	}
	//Requerimiento de consulta 13
	public ArrayList<VOConsumoVersion2> clientesConReservasAlMes(Integer mes) throws SQLException, Exception{

		DAORequerimientosenSQL daoRequerimientos = new DAORequerimientosenSQL();
		conn = darConexion();
		daoRequerimientos.setConn(conn);
		ArrayList<VOConsumoVersion2> rta = daoRequerimientos.getClientesReservasMes(mes);
		return rta;
	}

	public ArrayList<VOConsumoVersion2> clientesConReservasCostosas() throws SQLException, Exception{

		DAORequerimientosenSQL daoRequerimientos = new DAORequerimientosenSQL();
		conn = darConexion();
		daoRequerimientos.setConn(conn);
		ArrayList<VOConsumoVersion2> rta = daoRequerimientos.getClientesReservasCostosas();
		return rta;	
	}

	public ArrayList<VOConsumoVersion2> clientesConReservasEnSuites() throws SQLException, Exception{

		DAORequerimientosenSQL daoRequerimientos = new DAORequerimientosenSQL();
		conn = darConexion();
		daoRequerimientos.setConn(conn);
		ArrayList<VOConsumoVersion2> rta = daoRequerimientos.getClientesReservasSuite();
		return rta;
	}

	//----------------------------------------------------------------------------------
	//FIN REQUERIMIENTOS DE CONSULTA
	//----------------------------------------------------------------------------------


	//DESHABILITAR RESERVA

	public Reserva cancelarReserva(Integer id) throws SQLException
	{
		try
		{
			DAOHabitacion daoHabs = new DAOHabitacion();
			conn = darConexion();
			daoHabs.setConn(conn);
			List<Habitacion> listaHabs = daoHabs.getHabitacions();
			DAOReserva daoRes = new DAOReserva();
			daoRes.setConn(conn);
			for (Habitacion habitacion2 : listaHabs) 
			{
				if(habitacion2.getIdReserva() == id)
				{
					habitacion2.setIdReserva(null);
					daoHabs.updateHabitacion(habitacion2);
				}
			}
			return daoRes.findReservaById(id);
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			conn.rollback();
			return null;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			conn.rollback();
			return null;
		}
	}


	RequerimientosService<Operador> reque = new RequerimientosService();

	//TODO ACTIVAR-DESHABILITAR OFERTAS
	public Oferta cancelarOferta(Integer id)
	{
		Oferta rta = new Oferta(id, id, false, id, id, id, id);

		try
		{
			conn = darConexion();
			DAOOferta daoOferta = new DAOOferta();
			daoOferta.setConn(conn);
			DAOHabitacion daoHab = new DAOHabitacion();
			daoHab.setConn(conn);
			DAOReserva daoRes = new DAOReserva();
			daoRes.setConn(conn);
			DAOCliente daoCli = new DAOCliente();
			daoCli.setConn(conn);
			DAOHabitacionesServicioInmobiliarioSerInm daoHSerIn = new DAOHabitacionesServicioInmobiliarioSerInm();
			daoHSerIn.setConn(conn);
			DAOHabitacionServicioPublico daoHSerPu = new DAOHabitacionServicioPublico();
			daoHSerPu.setConn(conn);
			ArrayList<Habitacion> habs = daoHab.getHabitacions();
			ArrayList<Habitacion> habs1 = new ArrayList<>();
			ArrayList<Cliente> listaClientes = daoCli.getClientes();
			Date fecha = new Date();
			TipoHabitacion tipo = null;

			for (Cliente cliente : listaClientes) 
			{
				ArrayList<Habitacion> habsi = new ArrayList<>();
				ArrayList<Integer> listaServIn = new ArrayList<>();
				ArrayList<Integer> listaServPu = new ArrayList<>();
				for (Habitacion habitacion : habs) 
				{
					Reserva res = daoRes.findReservaById(habitacion.getIdReserva());
					Integer idCli = res.getIdCliente();
					String fechAct = res.getFecha();
					SimpleDateFormat DT = new SimpleDateFormat("dd-mm-aaaa");
					Date fechaa = DT.parse(fechAct);
					Date masBajo = new Date();
					if(fechaa.before(masBajo))
					{
						masBajo = fechaa;
					}
					if(idCli == cliente.getId())
					{
						habsi.add(habitacion);
					}
					fecha = masBajo;

					List<HabitacionesServiciosInmobiliarios> listaS = new ArrayList<>();
					listaS.addAll(daoHSerIn.findHabitacionesServicioInmobiliarioById(habitacion.getId()));
					for (HabitacionesServiciosInmobiliarios habitacionesServiciosInmobiliarios : listaS) 
					{
						listaServIn.add(habitacionesServiciosInmobiliarios.getIdServicioInmobiliario());
					}
					List<HabitacionesServicioPublico> listaSP = new ArrayList<>();
					listaSP.addAll(daoHSerPu.findHabitacionesServicioPublicoById(habitacion.getId()));
					for (HabitacionesServiciosInmobiliarios habitacionesServiciosInmobiliarios : listaS) 
					{
						listaServPu.add(habitacionesServiciosInmobiliarios.getIdServicioInmobiliario());
					}
					tipo = habitacion.getTipo();
				}
				Integer duracion = fecha.compareTo(new Date());

				Integer cantidad = habsi.size();
				reque.requerimientoRF7(new ReservaColectiva(cantidad, listaServIn, listaServPu,"" + tipo.toString() , cantidad, new Date().toString(), "" +duracion, cliente.getId(), new ArrayList<Reserva>()));
			}

			rta = daoOferta.findOfertaById(id);
			rta.setVigente(false);

			daoOferta.updateOferta(rta);

			return rta;
		}
		catch ( Exception e)
		{
			e.getMessage();
			return rta;
		}
	}


	public Oferta activarOferta(Oferta oferta) throws SQLException, Exception
	{

		DAOOferta daoOferta = new DAOOferta();
		conn = darConexion();
		daoOferta.setConn(conn);
		oferta.setVigente(true);
		daoOferta.updateOferta(oferta);
		return oferta;

	}



	//TODO Crear una reserva colectiva

	public ReservaColectiva crearReservaColectiva(ReservaColectiva reCo) throws Exception
	{
		List<Integer> servicioIn = reCo.getIdSInm();
		List<Integer> servicioPub = reCo.getIdSPub();
		Integer cantidad = reCo.getCantidad();
		Integer idReserva = reCo.getId();
		Integer idCliente = reCo.getIdCliente();
		this.conn = darConexion();

		ArrayList<Reserva> reservas = new ArrayList<>();
		DAOReserva daoReserva = new DAOReserva();
		daoReserva.setConn(conn);
		DAOHabitacion daoHabs = new DAOHabitacion();
		daoHabs.setConn(conn);
		DAOHabitacionesServicioInmobiliarioSerInm daoSIn = new DAOHabitacionesServicioInmobiliarioSerInm();
		daoSIn.setConn(conn);
		DAOHabitacionServicioPublico daoSPub = new DAOHabitacionServicioPublico();
		daoSPub.setConn(conn);
		DAOServicioInmobiliario daoSIIN = new DAOServicioInmobiliario();
		daoSIIN.setConn(conn);
		DAOServicioPublico daoSPPUB = new DAOServicioPublico();
		daoSPPUB.setConn(conn);

		try
		{
			ArrayList<Habitacion> habs = daoHabs.getHabitacions();

			ArrayList<Habitacion> listaSirven = new ArrayList<>();

			List<Boolean> seEnc = new ArrayList<>();
			List<Boolean> seEnc2 = new ArrayList<>();
			Double doble = 0.0;

			daoReserva.addReserva(new Reserva(false, Integer.parseInt(reCo.getDuracion()), "31-07-2120", idReserva, false, "40", doble, null, null, null, null, idCliente));

			for (int i = 0; i < habs.size(); i++) 
			{
				Habitacion actual = habs.get(i);

				List<HabitacionesServicioPublico> xd = daoSPub.findHabitacionesServicioPublicoById((actual.getId()));
				List<HabitacionesServiciosInmobiliarios> xd1 = daoSIn.findHabitacionesServicioInmobiliarioById((actual.getId()));

				List<ServicioInmobiliario> listaSIN = new ArrayList<>();
				List<ServicioPublico> listaSPub = new ArrayList<>();
				if(xd1 != null)
				{
					for (HabitacionesServiciosInmobiliarios habitacionesServiciosInmobiliarios : xd1) 
					{
						listaSIN.add(daoSIIN.findServicioInmobiliarioById(habitacionesServiciosInmobiliarios.getIdServicioInmobiliario()));
					}
				}

				if(xd != null)
				{

					for (HabitacionesServicioPublico habitacionesServiciosInmobiliarios : xd) 
					{
						listaSPub.add(daoSPPUB.findServicioPublicoById(habitacionesServiciosInmobiliarios.getIdServicioPublico()));
					}
				}

				if(actual.getTipo().equals(reCo.getTipo()) && actual.getIdReserva() == null)
				{
					if( xd != null)
					{
						if(!xd.isEmpty())
						{
							for (Integer laLegit: servicioPub) 
							{
								for (ServicioPublico servicioPubli : listaSPub) 
								{
									if(laLegit == servicioPubli.getId())
									{
										seEnc.add(true);
									}
								}
							}
						}
					}

					for (Integer laLegit: servicioIn) 
					{
						for (ServicioInmobiliario servicioInmobiliario : listaSIN) 
						{
							if(laLegit == servicioInmobiliario.getId())
							{
								seEnc2.add(true);
							}
						}
					}
				}
				if(seEnc.size() == reCo.getIdSInm().size() && seEnc2.size() == reCo.getIdSPub().size())
					listaSirven.add(actual);

			}
			List<Habitacion> rtaFinal = new ArrayList<>();

			if(listaSirven.size() >= cantidad)
			{
				for (int i = 0; i <= cantidad; i++) 
				{
					Habitacion actual = listaSirven.get(i);
					actual.setIdReserva(idReserva);
					daoHabs.updateHabitacion(actual);
				}

				reCo.setHabitaciones(rtaFinal);

				List<Integer> listaHostal = new ArrayList<>();
				List<Integer> listaHotel = new ArrayList<>();
				List<Integer> listaPersona = new ArrayList<>();
				List<Integer> listaViviendaUniversitaria = new ArrayList<>();

				for (int i = 0; i < rtaFinal.size(); i++) 
				{
					Habitacion actual = rtaFinal.get(i);
					if(actual.getIdHostal() != null)
					{
						listaHostal.add(actual.getId());
					}
					else if (actual.getIdHotel() != null)
					{
						listaHotel.add(actual.getId());
					}
					else if (actual.getIdPersona() != null)
					{
						listaPersona.add(actual.getId());
					}
					else
					{
						listaViviendaUniversitaria.add(actual.getId());
					}
				}

				RequerimientosService<Operador> reSe = new RequerimientosService<Operador>();

				for (int i = 0; i < listaHostal.size(); i++) 
				{
					Habitacion actual = daoHabs.findHabitacionById(listaHostal.get(i));
					reSe.createReservaHostal(new VOReservaHabitaciones( listaHostal, actual.getIdHostal(), idReserva, idCliente));

				}
				for (int i = 0; i < listaHotel.size(); i++) 
				{
					Habitacion actual = daoHabs.findHabitacionById(listaHotel.get(i));
					reSe.createReservaHotel(new VOReservaHabitaciones( listaHotel, actual.getIdHotel(), idReserva, idCliente));
				}
				for (int i = 0; i < listaPersona.size(); i++) 
				{
					Habitacion actual = daoHabs.findHabitacionById(listaPersona.get(i));
					reSe.createReservaPersonaNatural( actual.getIdPersona(), idReserva, listaPersona, idCliente);
				}
				for (int i = 0; i < listaViviendaUniversitaria.size(); i++) 
				{
					Habitacion actual = daoHabs.findHabitacionById(listaViviendaUniversitaria.get(i));
					reSe.createReservaViviendaUniversitaria(actual.getIdViviendaU(), idReserva, listaViviendaUniversitaria, idCliente);
				}
			}
			conn.commit();
			return reCo;
		}
		catch(Exception e)
		{
			conn.rollback();
			throw e;
		}

	}


}
