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
import vos.Operador;
import vos.Vecino;

public class VecinoService <K extends Operador>{

	
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
		 * Metodo GET que trae a todos los vecinoes en la Base de datos. <br/>
		 * <b>Precondicion: </b> el archivo <em>'conectionData'</em> ha sido inicializado con las credenciales del usuario <br/>
		 * <b>URL: </b> http://localhost:8080/TutorialParranderos/rest/vecinoes <br/>
		 * @return	<b>Response Status 200</b> - JSON que contiene a todos los vecinoes que estan en la Base de Datos <br/>
		 * 			<b>Response Status 500</b> - Excepcion durante el transcurso de la transaccion
		 */			
		@GET
		@Produces({ MediaType.APPLICATION_JSON })
		public Response getVecinos() {

			try {
				AlohAndesTransactionManager<K> tm = new AlohAndesTransactionManager<K>(getPath());

				List<Vecino> vecinoes;
				//Por simplicidad, solamente se obtienen los primeros 50 resultados de la consulta
				vecinoes = tm.getAllVecinoes();
				return Response.status(200).entity(vecinoes).build();
			} 
			catch (Exception e) {
				return Response.status(500).entity(doErrorMessage(e)).build();
			}
		}

		/**
		 * Metodo GET que trae al vecino en la Base de Datos con el ID dado por parametro <br/>
		 * <b>Precondicion: </b> el archivo <em>'conectionData'</em> ha sido inicializado con las credenciales del usuario <br/>
		 * <b>URL: </b> http://localhost:8080/TutorialParranderos/rest/vecinoes/{id} <br/>
		 * @return	<b>Response Status 200</b> - JSON Vecino que contiene al vecino cuyo ID corresponda al parametro <br/>
		 * 			<b>Response Status 500</b> - Excepcion durante el transcurso de la transaccion
		 */
		@GET
		@Path( "/vecino/{id: \\d+}" )
		@Produces( { MediaType.APPLICATION_JSON } )
		public Response getVecinoById( @PathParam( "id" ) Integer id )
		{
			try{
				AlohAndesTransactionManager<K> tm = new AlohAndesTransactionManager<K>( getPath( ) );

				Vecino vecino = tm.getVecinoById( id );
				return Response.status( 200 ).entity( vecino ).build( );			
			}
			catch( Exception e )
			{
				return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
			}
		}

		/**
		 * Metodo que recibe un vecino en formato JSON y lo agrega a la Base de Datos <br/>
		 * <b>Precondicion: </b> El archivo <em>'conectionData'</em> ha sido inicializado con las credenciales del usuario <br/>
		 * <b>Postcondicion: </b> Se agrega a la Base de datos la informacion correspondiente al vecino. <br/>
		 * <b>URL: </b> http://localhost:8080/TutorialParranderos/rest/vecinoes <br/>
		 * @param vecino JSON con la informacion del vecino que se desea agregar
		 * @return	<b>Response Status 200</b> - JSON que contiene al vecino que ha sido agregado <br/>
		 * 			<b>Response Status 500</b> - Excepcion durante el transcurso de la transaccion
		 */
		//TODO Requerimiento 3A: Identifique e implemente la anotacion correcta para la realizacion del metodo

		//TODO Requerimiento 3B: Identifique e implemente las anotaciones que indican el tipo de contenido que produce Y consume el metodo 

		@PUT
		@Consumes( { MediaType.APPLICATION_JSON } )
		@Produces( { MediaType.APPLICATION_JSON } )
		public Response addVecino(Vecino vecino) {

			//TODO Requerimiento 3C: Implemente el metodo a partir de los ejemplos anteriores y utilizando el Transaction Manager de Parranderos 
			try{
				AlohAndesTransactionManager<K> tm = new AlohAndesTransactionManager<K>( getPath( ) );

				tm.addVecino(vecino);
				return Response.status( 200 ).entity( vecino ).build( );			
			}
			catch( Exception e )
			{
				return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
			}
		}




		/**
		 * Metodo que recibe un vecino en formato JSON y lo agrega a la Base de Datos <br/>
		 * <b>Precondicion: </b> El archivo <em>'conectionData'</em> ha sido inicializado con las credenciales del usuario <br/>
		 * <b>Postcondicion: </b> Se actualiza la Base de datos con la informacion correspondiente al vecino.<br/>
		 * @param vecino JSON con la informacion del vecino que se desea agregar
		 * @return	<b>Response Status 200</b> - JSON que contiene al vecino que se desea modificar <br/>
		 * 			<b>Response Status 500</b> - Excepcion durante el transcurso de la transaccion
		 */
		//TODO Requerimiento 5A: Identifique e implemente la anotacion correcta para la realizacion del metodo

		//TODO Requerimiento 5B: Identifique e implemente las anotaciones que indican el tipo de contenido que produce Y consume el metodo 

		@PUT
		@Consumes( { MediaType.APPLICATION_JSON } )
		@Produces( { MediaType.APPLICATION_JSON } )
		public Response updateVecino(Vecino vecino) {
			//TODO Requerimiento 5B: Implemente el metodo a partir de los ejemplos anteriores y utilizando el Transaction Manager de Parranderos 
			try{
				AlohAndesTransactionManager<K> tm = new AlohAndesTransactionManager<K>( getPath( ) );

				tm.updateVecino(vecino);
				return Response.status( 200 ).entity( vecino ).build( );			
			}
			catch( Exception e )
			{
				return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
			}
		}

		/**
		 * Metodo que recibe un vecino en formato JSON y lo elimina de la Base de Datos <br/>
		 * <b>Precondicion: </b> El archivo <em>'conectionData'</em> ha sido inicializado con las credenciales del usuario <br/>
		 * <b>Postcondicion: </b> Se elimina de la Base de datos al vecino con la informacion correspondiente.<br/>
		 * <b>URL: </b> http://localhost:8080/TutorialParranderos/rest/vecinoes <br/>
		 * @param vecino JSON con la informacion del vecino que se desea eliminar
		 * @return	<b>Response Status 200</b> - JSON que contiene al vecino que se desea eliminar <br/>
		 * 			<b>Response Status 500</b> - Excepcion durante el transcurso de la transaccion
		 */
		//TODO Requerimiento 6A: Identifique e implemente la anotacion correcta para la realizacion del metodo

		//TODO Requerimiento 6B: Identifique e implemente las anotaciones que indican el tipo de contenido que produce Y consume el metodo 


		@DELETE
		@Produces(MediaType.APPLICATION_JSON)
		public Response deleteVecino(Vecino vecino) {
			//TODO Requerimiento 6C: Implemente el metodo a partir de los ejemplos anteriores y utilizando el Transaction Manager de Parranderos 
			try{
				AlohAndesTransactionManager<K> tm = new AlohAndesTransactionManager<K>( getPath( ) );

				tm.deleteVecino(vecino);
				return Response.status( 200 ).entity( vecino ).build( );			
			}
			catch( Exception e )
			{
				return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
			}
		}
	
}
