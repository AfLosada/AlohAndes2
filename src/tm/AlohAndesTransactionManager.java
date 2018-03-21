package tm;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

import dao.*;
import vos.*;

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
		private void initializeConnectionData() throws IOException, ClassNotFoundException {

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
		private Connection darConexion() throws SQLException {
			System.out.println("[PARRANDEROS APP] Attempting Connection to: " + url + " - By User: " + user);
			return DriverManager.getConnection(url, user, password);
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
		public Apartamento getApartamentoById(Long id) throws Exception {
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
		public Cliente getClienteById(Long id) throws Exception {
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
		public Habitacion getHabitacionById(Long id) throws Exception {
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
		public Hostal getHostalById(Long id) throws Exception {
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
		public Hotel getHotelById(Long id) throws Exception {
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
		 * Método que modela la transacción que agrega los videos que entran como parámetro a la base de datos.
		 * <b> post: </b> se han agregado los videos que entran como parámetro
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
		
		
		
}
