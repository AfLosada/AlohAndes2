package rest;

import java.util.List;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import tm.AlohAndesTransactionManager;
import vos.Habitacion;
import vos.Operador;

public class HabitacionService <K extends Operador>
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
		 * Metodo GET que trae a todos los habitaciones en la Base de datos. <br/>
		 * <b>Precondicion: </b> el archivo <em>'conectionData'</em> ha sido inicializado con las credenciales del usuario <br/>
		 * <b>URL: </b> http://localhost:8080/TutorialParranderos/rest/habitaciones <br/>
		 * @return	<b>Response Status 200</b> - JSON que contiene a todos los habitaciones que estan en la Base de Datos <br/>
		 * 			<b>Response Status 500</b> - Excepcion durante el transcurso de la transaccion
		 */			
		@GET
		@Path( "/habitacion")
		@Produces({ MediaType.APPLICATION_JSON })
		public Response getHabitacions() {

			try {
				AlohAndesTransactionManager<K> tm = new AlohAndesTransactionManager<K>(getPath());

				List<Habitacion> habitaciones;
				//Por simplicidad, solamente se obtienen los primeros 50 resultados de la consulta
				habitaciones = tm.getAllHabitaciones();
				return Response.status(200).entity(habitaciones).build();
			} 
			catch (Exception e) {
				return Response.status(500).entity(doErrorMessage(e)).build();
			}
		}

		/**
		 * Metodo GET que trae al habitacion en la Base de Datos con el ID dado por parametro <br/>
		 * <b>Precondicion: </b> el archivo <em>'conectionData'</em> ha sido inicializado con las credenciales del usuario <br/>
		 * <b>URL: </b> http://localhost:8080/TutorialParranderos/rest/habitaciones/{id} <br/>
		 * @return	<b>Response Status 200</b> - JSON Habitacion que contiene al habitacion cuyo ID corresponda al parametro <br/>
		 * 			<b>Response Status 500</b> - Excepcion durante el transcurso de la transaccion
		 */
		@GET
		@Path( "/habitacion/{id: \\d+}" )
		@Produces( { MediaType.APPLICATION_JSON } )
		public Response getHabitacionById( @PathParam( "id" ) Long id )
		{
			try{
				AlohAndesTransactionManager<K> tm = new AlohAndesTransactionManager<K>( getPath( ) );

				Habitacion habitacion = tm.getHabitacionById( id );
				return Response.status( 200 ).entity( habitacion ).build( );			
			}
			catch( Exception e )
			{
				return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
			}
		}

		/**
		 * Metodo que recibe un habitacion en formato JSON y lo agrega a la Base de Datos <br/>
		 * <b>Precondicion: </b> El archivo <em>'conectionData'</em> ha sido inicializado con las credenciales del usuario <br/>
		 * <b>Postcondicion: </b> Se agrega a la Base de datos la informacion correspondiente al habitacion. <br/>
		 * <b>URL: </b> http://localhost:8080/TutorialParranderos/rest/habitaciones <br/>
		 * @param habitacion JSON con la informacion del habitacion que se desea agregar
		 * @return	<b>Response Status 200</b> - JSON que contiene al habitacion que ha sido agregado <br/>
		 * 			<b>Response Status 500</b> - Excepcion durante el transcurso de la transaccion
		 */
		//TODO Requerimiento 3A: Identifique e implemente la anotacion correcta para la realizacion del metodo

		//TODO Requerimiento 3B: Identifique e implemente las anotaciones que indican el tipo de contenido que produce Y consume el metodo 

		@PUT
		@Consumes( { MediaType.APPLICATION_JSON } )
		@Produces( { MediaType.APPLICATION_JSON } )
		public Response addHabitacion(Habitacion habitacion) {

			//TODO Requerimiento 3C: Implemente el metodo a partir de los ejemplos anteriores y utilizando el Transaction Manager de Parranderos 
			try{
				AlohAndesTransactionManager<K> tm = new AlohAndesTransactionManager<K>( getPath( ) );

				tm.addHabitacion(habitacion);
				return Response.status( 200 ).entity( habitacion ).build( );			
			}
			catch( Exception e )
			{
				return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
			}
		}

		
		
		
		/**
		 * Metodo que recibe un habitacion en formato JSON y lo agrega a la Base de Datos <br/>
		 * <b>Precondicion: </b> El archivo <em>'conectionData'</em> ha sido inicializado con las credenciales del usuario <br/>
		 * <b>Postcondicion: </b> Se actualiza la Base de datos con la informacion correspondiente al habitacion.<br/>
		 * @param habitacion JSON con la informacion del habitacion que se desea agregar
		 * @return	<b>Response Status 200</b> - JSON que contiene al habitacion que se desea modificar <br/>
		 * 			<b>Response Status 500</b> - Excepcion durante el transcurso de la transaccion
		 */
		//TODO Requerimiento 5A: Identifique e implemente la anotacion correcta para la realizacion del metodo

		//TODO Requerimiento 5B: Identifique e implemente las anotaciones que indican el tipo de contenido que produce Y consume el metodo 

		@PUT
		@Consumes( { MediaType.APPLICATION_JSON } )
		@Produces( { MediaType.APPLICATION_JSON } )
		public Response updateHabitacion(Habitacion habitacion) {
			//TODO Requerimiento 5B: Implemente el metodo a partir de los ejemplos anteriores y utilizando el Transaction Manager de Parranderos 
			try{
				AlohAndesTransactionManager<K> tm = new AlohAndesTransactionManager<K>( getPath( ) );

				tm.updateHabitacion(habitacion);
				return Response.status( 200 ).entity( habitacion ).build( );			
			}
			catch( Exception e )
			{
				return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
			}
		}

		/**
		 * Metodo que recibe un habitacion en formato JSON y lo elimina de la Base de Datos <br/>
		 * <b>Precondicion: </b> El archivo <em>'conectionData'</em> ha sido inicializado con las credenciales del usuario <br/>
		 * <b>Postcondicion: </b> Se elimina de la Base de datos al habitacion con la informacion correspondiente.<br/>
		 * <b>URL: </b> http://localhost:8080/TutorialParranderos/rest/habitaciones <br/>
		 * @param habitacion JSON con la informacion del habitacion que se desea eliminar
		 * @return	<b>Response Status 200</b> - JSON que contiene al habitacion que se desea eliminar <br/>
		 * 			<b>Response Status 500</b> - Excepcion durante el transcurso de la transaccion
		 */
		//TODO Requerimiento 6A: Identifique e implemente la anotacion correcta para la realizacion del metodo

		//TODO Requerimiento 6B: Identifique e implemente las anotaciones que indican el tipo de contenido que produce Y consume el metodo 

		@DELETE
		@Produces(MediaType.APPLICATION_JSON)
		public Response deleteHabitacion(Habitacion habitacion) {
			//TODO Requerimiento 6C: Implemente el metodo a partir de los ejemplos anteriores y utilizando el Transaction Manager de Parranderos 
			try{
				AlohAndesTransactionManager<K> tm = new AlohAndesTransactionManager<K>( getPath( ) );

				tm.deleteHabitacion(habitacion);
				return Response.status( 200 ).entity( habitacion ).build( );			
			}
			catch( Exception e )
			{
				return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
			}
		}
	
}
