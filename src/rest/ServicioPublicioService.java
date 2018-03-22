package rest;

import java.util.List;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import tm.AlohAndesTransactionManager;
import vos.Operador;
import vos.ServicioPublico;
@Path("/serviciospublicos")
public class ServicioPublicioService <K extends Operador>
{

	//----------------------------------------------------------------------------------------------------------------------------------
	// ATRIBUTOS
	//----------------------------------------------------------------------------------------------------------------------------------

	/**
	 * Atributo que usa la anotacion @Context para tener el ServletContext de la conexion actual.
	 */
	@Context
	private ServletContext context;

	//----------------------------------------------------------------------------------------------------------------------------------
	// METODOS DE INICIALIZACION
	//----------------------------------------------------------------------------------------------------------------------------------
	/**
	 * Metodo que retorna el path de la carpeta WEB-INF/ConnectionData en el deploy actual dentro del servidor.
	 * @return path de la carpeta WEB-INF/ConnectionData en el deploy actual.
	 */
	private String getPath() {
		return context.getRealPath("WEB-INF/ConnectionData");
	}


	private String doErrorMessage(Exception e){
		return "{ \"ERROR\": \""+ e.getMessage() + "\"}" ;
	}

	//----------------------------------------------------------------------------------------------------------------------------------
	// METODOS REST
	//----------------------------------------------------------------------------------------------------------------------------------

	/**
	 * Metodo GET que trae a todos los serviciopublicoes en la Base de datos. <br/>
	 * <b>Precondicion: </b> el archivo <em>'conectionData'</em> ha sido inicializado con las credenciales del usuario <br/>
	 * <b>URL: </b> http://localhost:8080/TutorialParranderos/rest/serviciopublicoes <br/>
	 * @return	<b>Response Status 200</b> - JSON que contiene a todos los serviciopublicoes que estan en la Base de Datos <br/>
	 * 			<b>Response Status 500</b> - Excepcion durante el transcurso de la transaccion
	 */			
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getServicioPublicos() {

		try {
			AlohAndesTransactionManager<K> tm = new AlohAndesTransactionManager<K>(getPath());

			List<ServicioPublico> serviciopublicoes;
			//Por simplicidad, solamente se obtienen los primeros 50 resultados de la consulta
			serviciopublicoes = tm.getAllServicioPublicoes();
			return Response.status(200).entity(serviciopublicoes).build();
		} 
		catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}

	/**
	 * Metodo GET que trae al serviciopublico en la Base de Datos con el ID dado por parametro <br/>
	 * <b>Precondicion: </b> el archivo <em>'conectionData'</em> ha sido inicializado con las credenciales del usuario <br/>
	 * <b>URL: </b> http://localhost:8080/TutorialParranderos/rest/serviciopublicoes/{id} <br/>
	 * @return	<b>Response Status 200</b> - JSON ServicioPublico que contiene al serviciopublico cuyo ID corresponda al parametro <br/>
	 * 			<b>Response Status 500</b> - Excepcion durante el transcurso de la transaccion
	 */
	@GET
	@Path( "/serviciopublico/{id: \\d+}" )
	@Produces( { MediaType.APPLICATION_JSON } )
	public Response getServicioPublicoById( @PathParam( "id" ) Integer id )
	{
		try{
			AlohAndesTransactionManager<K> tm = new AlohAndesTransactionManager<K>( getPath( ) );

			ServicioPublico serviciopublico = tm.getServicioPublicoById( id );
			return Response.status( 200 ).entity( serviciopublico ).build( );			
		}
		catch( Exception e )
		{
			return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
		}
	}

	/**
	 * Metodo que recibe un serviciopublico en formato JSON y lo agrega a la Base de Datos <br/>
	 * <b>Precondicion: </b> El archivo <em>'conectionData'</em> ha sido inicializado con las credenciales del usuario <br/>
	 * <b>Postcondicion: </b> Se agrega a la Base de datos la informacion correspondiente al serviciopublico. <br/>
	 * <b>URL: </b> http://localhost:8080/TutorialParranderos/rest/serviciopublicoes <br/>
	 * @param serviciopublico JSON con la informacion del serviciopublico que se desea agregar
	 * @return	<b>Response Status 200</b> - JSON que contiene al serviciopublico que ha sido agregado <br/>
	 * 			<b>Response Status 500</b> - Excepcion durante el transcurso de la transaccion
	 */
	//TODO Requerimiento 3A: Identifique e implemente la anotacion correcta para la realizacion del metodo

	//TODO Requerimiento 3B: Identifique e implemente las anotaciones que indican el tipo de contenido que produce Y consume el metodo 

	@POST
	@Consumes( { MediaType.APPLICATION_JSON } )
	@Produces( { MediaType.APPLICATION_JSON } )
	public Response addServicioPublico(ServicioPublico serviciopublico) {

		//TODO Requerimiento 3C: Implemente el metodo a partir de los ejemplos anteriores y utilizando el Transaction Manager de Parranderos 
		try{
			AlohAndesTransactionManager<K> tm = new AlohAndesTransactionManager<K>( getPath( ) );

			tm.addServicioPublico(serviciopublico);
			return Response.status( 200 ).entity( serviciopublico ).build( );			
		}
		catch( Exception e )
		{
			return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
		}
	}




	/**
	 * Metodo que recibe un serviciopublico en formato JSON y lo agrega a la Base de Datos <br/>
	 * <b>Precondicion: </b> El archivo <em>'conectionData'</em> ha sido inicializado con las credenciales del usuario <br/>
	 * <b>Postcondicion: </b> Se actualiza la Base de datos con la informacion correspondiente al serviciopublico.<br/>
	 * @param serviciopublico JSON con la informacion del serviciopublico que se desea agregar
	 * @return	<b>Response Status 200</b> - JSON que contiene al serviciopublico que se desea modificar <br/>
	 * 			<b>Response Status 500</b> - Excepcion durante el transcurso de la transaccion
	 */
	//TODO Requerimiento 5A: Identifique e implemente la anotacion correcta para la realizacion del metodo

	//TODO Requerimiento 5B: Identifique e implemente las anotaciones que indican el tipo de contenido que produce Y consume el metodo 

	@PUT
	@Consumes( { MediaType.APPLICATION_JSON } )
	@Produces( { MediaType.APPLICATION_JSON } )
	public Response updateServicioPublico(ServicioPublico serviciopublico) {
		//TODO Requerimiento 5B: Implemente el metodo a partir de los ejemplos anteriores y utilizando el Transaction Manager de Parranderos 
		try{
			AlohAndesTransactionManager<K> tm = new AlohAndesTransactionManager<K>( getPath( ) );

			tm.updateServicioPublico(serviciopublico);
			return Response.status( 200 ).entity( serviciopublico ).build( );			
		}
		catch( Exception e )
		{
			return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
		}
	}

	/**
	 * Metodo que recibe un serviciopublico en formato JSON y lo elimina de la Base de Datos <br/>
	 * <b>Precondicion: </b> El archivo <em>'conectionData'</em> ha sido inicializado con las credenciales del usuario <br/>
	 * <b>Postcondicion: </b> Se elimina de la Base de datos al serviciopublico con la informacion correspondiente.<br/>
	 * <b>URL: </b> http://localhost:8080/TutorialParranderos/rest/serviciopublicoes <br/>
	 * @param serviciopublico JSON con la informacion del serviciopublico que se desea eliminar
	 * @return	<b>Response Status 200</b> - JSON que contiene al serviciopublico que se desea eliminar <br/>
	 * 			<b>Response Status 500</b> - Excepcion durante el transcurso de la transaccion
	 */
	//TODO Requerimiento 6A: Identifique e implemente la anotacion correcta para la realizacion del metodo

	//TODO Requerimiento 6B: Identifique e implemente las anotaciones que indican el tipo de contenido que produce Y consume el metodo 


	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteServicioPublico(ServicioPublico serviciopublico) {
		//TODO Requerimiento 6C: Implemente el metodo a partir de los ejemplos anteriores y utilizando el Transaction Manager de Parranderos 
		try{
			AlohAndesTransactionManager<K> tm = new AlohAndesTransactionManager<K>( getPath( ) );

			tm.deleteServicioPublico(serviciopublico);
			return Response.status( 200 ).entity( serviciopublico ).build( );			
		}
		catch( Exception e )
		{
			return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
		}
	}

}
