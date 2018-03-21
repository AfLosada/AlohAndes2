package rest;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.DAOCliente;
import vos.Cliente;

public class BorradorParaRefasct {

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
	public Vivienda getViviendaById(Long id) throws Exception {
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
}
