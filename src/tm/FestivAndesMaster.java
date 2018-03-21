package tm;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import dao.DAOBoleta;
import dao.DAOCliente;
import dao.DAOEspectaculo;
import dao.DAOFuncion;
import dao.DAOLocalidad;
import dao.DAOPreferencia;
import dao.DAOSitio;
import dao.DAOUsuario;
import vos.Boleta;
import vos.Cliente;
import vos.Espectaculo;
import vos.Funcion;
import vos.ListaBoletas;
import vos.ListaClientes;
import vos.ListaEspectaculos;
import vos.ListaFunciones;
import vos.ListaLocalidades;
import vos.ListaPreferencias;
import vos.ListaReportesEspectaculo;
import vos.ListaReportesFuncion;
import vos.ListaSitios;
import vos.ListaUsuarios;
import vos.Localidad;
import vos.Preferencia;
import vos.ReporteEspectaculo;
import vos.ReporteFuncion;
import vos.Sitio;
import vos.Usuario;

public class FestivAndesMaster 
{
	/**
	 * Atributo estático que contiene el path relativo del archivo que tiene los datos de la conexión
	 */
	private static final String CONNECTION_DATA_FILE_NAME_REMOTE = "/conexion.properties";

	/**
	 * Atributo estático que contiene el path absoluto del archivo que tiene los datos de la conexión
	 */
	private  String connectionDataPath;

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
	 * Conexión a la base de datos
	 */
	private Connection conn;


	/**
	 * Método constructor de la clase VideoAndesMaster, esta clase modela y contiene cada una de las 
	 * transacciones y la logia de negocios que estas conllevan.
	 * <b>post: </b> Se crea el objeto VideoAndesMaster, se inicializa el path absoluto de el archivo de conexión y se
	 * inicializa los atributos que se usan par la conexión a la base de datos.
	 * @param contextPathP - path absoluto en el servidor del contexto del deploy actual
	 */
	public FestivAndesMaster(String contextPathP) {
		connectionDataPath = contextPathP + CONNECTION_DATA_FILE_NAME_REMOTE;
		initConnectionData();
	}

	/*
	 * Método que  inicializa los atributos que se usan para la conexion a la base de datos.
	 * <b>post: </b> Se han inicializado los atributos que se usan par la conexión a la base de datos.
	 */
	private void initConnectionData() {
		try {
			File arch = new File(this.connectionDataPath);
			Properties prop = new Properties();
			FileInputStream in = new FileInputStream(arch);
			prop.load(in);
			in.close();
			this.url = prop.getProperty("url");
			this.user = prop.getProperty("usuario");
			this.password = prop.getProperty("clave");
			this.driver = prop.getProperty("driver");
			Class.forName(driver);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método que  retorna la conexión a la base de datos
	 * @return Connection - la conexión a la base de datos
	 * @throws SQLException - Cualquier error que se genere durante la conexión a la base de datos
	 */
	private Connection darConexion() throws SQLException {
		System.out.println("Connecting to: " + url + " With user: " + user);
		return DriverManager.getConnection(url, user, password);
	}

	////////////////////////////////////////
	///////Transacciones////////////////////
	////////////////////////////////////////


	/**
	 * Método que modela la transacción que retorna todos los videos de la base de datos.
	 * @return ListaVideos - objeto que modela  un arreglo de videos. este arreglo contiene el resultado de la búsqueda
	 * @throws Exception -  cualquier error que se genere durante la transacción
	 */
	public ListaPreferencias darPrferencias() throws Exception {
		ArrayList<Preferencia> preferencias;
		DAOPreferencia daoPreferencias = new DAOPreferencia();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoPreferencias.setConn(conn);
			preferencias = daoPreferencias.darPrferencias();

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
				daoPreferencias.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaPreferencias(preferencias);
	}

	/**
	 * Método que modela la transacción que busca el/los videos en la base de datos con el nombre entra como parámetro.
	 * @param name - Nombre del video a buscar. name != null
	 * @return ListaVideos - objeto que modela  un arreglo de videos. este arreglo contiene el resultado de la búsqueda
	 * @throws Exception -  cualquier error que se genere durante la transacción
	 */
	//	public ListaVideos buscarVideosPorName(String name) throws Exception {
	//		ArrayList<Video> videos;
	//		DAOTablaVideos daoVideos = new DAOTablaVideos();
	//		try 
	//		{
	//			//////Transacción
	//			this.conn = darConexion();
	//			daoVideos.setConn(conn);
	//			videos = daoVideos.buscarVideosPorName(name);
	//
	//		} catch (SQLException e) {
	//			System.err.println("SQLException:" + e.getMessage());
	//			e.printStackTrace();
	//			throw e;
	//		} catch (Exception e) {
	//			System.err.println("GeneralException:" + e.getMessage());
	//			e.printStackTrace();
	//			throw e;
	//		} finally {
	//			try {
	//				daoVideos.cerrarRecursos();
	//				if(this.conn!=null)
	//					this.conn.close();
	//			} catch (SQLException exception) {
	//				System.err.println("SQLException closing resources:" + exception.getMessage());
	//				exception.printStackTrace();
	//				throw exception;
	//			}
	//		}
	//		return new ListaVideos(videos);
	//	}

	/**
	 * Método que modela la transacción que agrega un solo video a la base de datos.
	 * <b> post: </b> se ha agregado el video que entra como parámetro
	 * @param video - el video a agregar. video != null
	 * @throws Exception - cualquier error que se genera agregando el video
	 */
	public void addPreferencia(Preferencia preferencia) throws Exception {
		DAOPreferencia daoPreferencias = new DAOPreferencia();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoPreferencias.setConn(conn);
			daoPreferencias.addPreferencia(preferencia);
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
				daoPreferencias.cerrarRecursos();
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
	public void addPreferencias(ListaPreferencias preferencias) throws Exception {
		DAOPreferencia daoPreferencias = new DAOPreferencia();
		try 
		{
			//////Transacción - ACID Example
			this.conn = darConexion();
			conn.setAutoCommit(false);
			daoPreferencias.setConn(conn);
			for(Preferencia preferencia : preferencias.getPreferencias())
				daoPreferencias.addPreferencia(preferencia);
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
				daoPreferencias.cerrarRecursos();
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
	public void updatePreferencia(Preferencia preferencia) throws Exception {
		DAOPreferencia daoPreferencia = new DAOPreferencia();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoPreferencia.setConn(conn);
			daoPreferencia.updatePreferencia(preferencia);

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
				daoPreferencia.cerrarRecursos();
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
	public void deletePreferencia(Preferencia preferencia) throws Exception {
		DAOPreferencia daoPreferencia = new DAOPreferencia();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoPreferencia.setConn(conn);
			daoPreferencia.deletePrferencia(preferencia);

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
				daoPreferencia.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public ListaBoletas darBoletas() throws Exception {
		ArrayList<Boleta> boletas;
		DAOBoleta daoBoletas = new DAOBoleta();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoBoletas.setConn(conn);
			boletas = daoBoletas.darBoletas();

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
				daoBoletas.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaBoletas(boletas);
	}

	public void addBoleta(Boleta boleta) throws Exception {
		DAOBoleta daoBoletas = new DAOBoleta();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoBoletas.setConn(conn);
			daoBoletas.addBoleta(boleta);
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
				daoBoletas.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public void venderBoleta(Boleta boleta) throws Exception
	{
		DAOBoleta daoBoletas = new DAOBoleta();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoBoletas.setConn(conn);
			if(boleta.isVendida())
			{
				conn.rollback();
				throw new Exception("La boleta ya esta vendida");
			}
			else if(daoBoletas.todasVendidas())
			{
				conn.rollback();
				throw new Exception("Todas las boletas estan vendidas");
			}
			else
			{
				daoBoletas.venderBoleta(boleta);
				conn.commit();
			}

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
				daoBoletas.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}


	public void addFuncion(Funcion funcion) throws Exception {
		DAOFuncion daoFunciones = new DAOFuncion();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoFunciones.setConn(conn);
			daoFunciones.addFuncion(funcion);
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
				daoFunciones.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public ListaFunciones darFunciones() throws Exception {
		ArrayList<Funcion> funciones;
		DAOFuncion daoFunciones = new DAOFuncion();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoFunciones.setConn(conn);
			funciones = daoFunciones.darFunciones();

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
				daoFunciones.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaFunciones(funciones);
	}

	public void realizarFuncion(Funcion funcion) throws Exception
	{
		DAOFuncion daoFunciones = new DAOFuncion();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoFunciones.setConn(conn);
			daoFunciones.funcionRealizada(funcion);
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
				daoFunciones.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public ListaFunciones buscarFuncionesPorFecha(String fecha) throws Exception {
		ArrayList<Funcion> funciones;
		DAOFuncion daoFunciones = new DAOFuncion();
		try 
		{
			//////Transacci�n
			this.conn = darConexion();
			daoFunciones.setConn(conn);
			funciones = daoFunciones.buscarFuncionesPorFechaIncio(fecha);

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
				daoFunciones.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaFunciones(funciones);
	}

	public ListaReportesFuncion darReportesFuncion() throws Exception {
		ArrayList<ReporteFuncion> reportes;
		DAOFuncion daoFunciones = new DAOFuncion();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoFunciones.setConn(conn);
			reportes = daoFunciones.darReporte();

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
				daoFunciones.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaReportesFuncion(reportes);
	}

	//	public ReporteFuncion darReportePorLocalidad(int idFuncion, int idLocalidad) throws Exception {
	//		ReporteFuncion reporte;
	//		DAOFuncion daoFunciones = new DAOFuncion();
	//		try 
	//		{
	//			//////Transacci�n
	//			this.conn = darConexion();
	//			daoFunciones.setConn(conn);
	//			reporte = daoFunciones.darReportePorLocalidad(idFuncion, idLocalidad);
	//
	//		} catch (SQLException e) {
	//			System.err.println("SQLException:" + e.getMessage());
	//			e.printStackTrace();
	//			throw e;
	//		} catch (Exception e) {
	//			System.err.println("GeneralException:" + e.getMessage());
	//			e.printStackTrace();
	//			throw e;
	//		} finally {
	//			try {
	//				daoFunciones.cerrarRecursos();
	//				if(this.conn!=null)
	//					this.conn.close();
	//			} catch (SQLException exception) {
	//				System.err.println("SQLException closing resources:" + exception.getMessage());
	//				exception.printStackTrace();
	//				throw exception;
	//			}
	//		}
	//		return reporte;
	//	}

	public ListaEspectaculos darEspectaculos() throws Exception {
		ArrayList<Espectaculo> espectaculo;
		DAOEspectaculo daoEspectaculos = new DAOEspectaculo();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoEspectaculos.setConn(conn);
			espectaculo = daoEspectaculos.darEspectaculos();

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
				daoEspectaculos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaEspectaculos(espectaculo);
	}

	public void addEspectaculo(Espectaculo espectaculo) throws Exception {
		DAOEspectaculo daoEspectaculos = new DAOEspectaculo();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoEspectaculos.setConn(conn);
			daoEspectaculos.addEspectaculo(espectaculo);
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
				daoEspectaculos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public void deleteEspectaculo(Espectaculo espectaculo) throws Exception {
		DAOEspectaculo daoEspectaculo = new DAOEspectaculo();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoEspectaculo.setConn(conn);
			daoEspectaculo.deleteEspectaculo(espectaculo);

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
				daoEspectaculo.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public ListaSitios darSitios() throws Exception {
		ArrayList<Sitio> sitios;
		DAOSitio daoSitios = new DAOSitio();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoSitios.setConn(conn);
			sitios = daoSitios.darSitios();

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
				daoSitios.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaSitios(sitios);
	}

	public ListaLocalidades darLocalidades() throws Exception {
		ArrayList<Localidad> localidades;
		DAOLocalidad daoLocalidades = new DAOLocalidad();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoLocalidades.setConn(conn);
			localidades = daoLocalidades.darLocalidades();

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
				daoLocalidades.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaLocalidades(localidades);
	}

	public ListaReportesEspectaculo darReportesEspectaculo() throws Exception {
		ArrayList<ReporteEspectaculo> reporte;
		DAOEspectaculo daoEspectaculos = new DAOEspectaculo();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoEspectaculos.setConn(conn);
			reporte = daoEspectaculos.darReporte();

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
				daoEspectaculos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaReportesEspectaculo(reporte);
	}

	public ListaUsuarios darUsuarios() throws Exception {
		ArrayList<Usuario> usuarios;
		DAOUsuario daoUsuarios = new DAOUsuario();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoUsuarios.setConn(conn);
			usuarios = daoUsuarios.darUsuarios();

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
				daoUsuarios.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaUsuarios(usuarios);
	}

	public Usuario darUsuario( String pId) throws Exception {
		Usuario usuario = null;
		DAOUsuario daoUsuarios = new DAOUsuario();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoUsuarios.setConn(conn);
			usuario = daoUsuarios.darUsuario(pId);

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
				daoUsuarios.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return usuario;
	}

	public void addUsuario(Usuario usuario) throws Exception {
		DAOUsuario daoUsuarios = new DAOUsuario();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoUsuarios.setConn(conn);
			daoUsuarios.addUsuario(usuario);
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
				daoUsuarios.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public ListaClientes darClientes() throws Exception {
		ArrayList<Cliente> clientes;
		DAOCliente daoClientes = new DAOCliente();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoClientes.setConn(conn);
			clientes = daoClientes.darClientes();

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
		return new ListaClientes(clientes);
	}

	public void addCliente(Cliente cliente) throws Exception {
		DAOCliente daoClientes = new DAOCliente();
		try 
		{
			//////Transacción
			
			if(darUsuario(cliente.getIdentificacion()) == null)
			{
				addUsuario(cliente);
			}
			this.conn = darConexion();
			daoClientes.setConn(conn);
			System.out.println(conn.isClosed()); 
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

	public void deleteCliente(Cliente cliente) throws Exception {
		DAOCliente daoCliente = new DAOCliente();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoCliente.setConn(conn);
			daoCliente.deleteCliente(cliente);;

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


	//	public void addSitio(Sitio sitio) throws Exception {
	//		DAOSitio daoSitio = new DAOSitio();
	//		try 
	//		{
	//			//////Transacción
	//			this.conn = darConexion();
	//			daoSitio.setConn(conn);
	//			daoSitio.addCategoria(categoria);;
	//			conn.commit();
	//
	//		} catch (SQLException e) {
	//			System.err.println("SQLException:" + e.getMessage());
	//			e.printStackTrace();
	//			throw e;
	//		} catch (Exception e) {
	//			System.err.println("GeneralException:" + e.getMessage());
	//			e.printStackTrace();
	//			throw e;
	//		} finally {
	//			try {
	//				daoPreferencias.cerrarRecursos();
	//				if(this.conn!=null)
	//					this.conn.close();
	//			} catch (SQLException exception) {
	//				System.err.println("SQLException closing resources:" + exception.getMessage());
	//				exception.printStackTrace();
	//				throw exception;
	//			}
	//		}
	//	}

	/**
	 * Método que modela la transacción que retorna el/los videos mas alquilados
	 * @return ListaVideos - objeto que modela  un arreglo de videos. este arreglo contiene el resultado de la búsqueda
	 * @throws Exception -  cualquier error que se genere durante la transacción
	 */
	//	public ListaVideos videosMasAlquilados() throws Exception {
	//		ArrayList<Video> videos;
	//		DAOTablaVideos daoVideos = new DAOTablaVideos();
	//		try 
	//		{
	//			//////Transacción
	//			this.conn = darConexion();
	//			daoVideos.setConn(conn);
	//			videos = daoVideos.darVideoMasAlquilado();
	//
	//		} catch (SQLException e) {
	//			System.err.println("SQLException:" + e.getMessage());
	//			e.printStackTrace();
	//			throw e;
	//		} catch (Exception e) {
	//			System.err.println("GeneralException:" + e.getMessage());
	//			e.printStackTrace();
	//			throw e;
	//		} finally {
	//			try {
	//				daoVideos.cerrarRecursos();
	//				if(this.conn!=null)
	//					this.conn.close();
	//			} catch (SQLException exception) {
	//				System.err.println("SQLException closing resources:" + exception.getMessage());
	//				exception.printStackTrace();
	//				throw exception;
	//			}
	//		}
	//		return new ListaVideos(videos);
	//	}
}
