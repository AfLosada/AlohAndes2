package rest;

import java.util.List;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import tm.AlohAndesTransactionManager;
import vos.Cliente;
import vos.Habitacion;
import vos.Hostal;
import vos.Hotel;
import vos.Oferta;
import vos.Operador;
import vos.PersonaNatural;
import vos.Reserva;
import vos.ServicioInmobiliario;
import vos.ServicioPublico;
import vos.Vecino;
import vos.ViviendaUniversitaria;
@Path("/requerimientos")
public class RequerimientosService <K extends Operador>
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
	
	
	//----------------------------------------------------------------------------------------
	// METODOS REST
	// --------------------------------------------------------------------------------------

	//TODO Ofertas
	
	@POST
	@Path("/requerimientos/oferta/hotel")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response createOfertaHostal(Hostal hostal, Oferta oferta, List<Habitacion> habitaciones, ServicioPublico sPub, ServicioInmobiliario sIn) {

		try {
			AlohAndesTransactionManager<K> tm = new AlohAndesTransactionManager<K>(getPath());

			tm.agregarOfertaHostal(hostal, oferta, habitaciones, sPub, sIn);
			return Response.status(200).entity(oferta).build();
		} 
		catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}
	

	@POST
	@Path("/requerimientos/oferta/hotel")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response createOfertaHotel(Hotel hostal, Oferta oferta, List<Habitacion> habitaciones, ServicioPublico sPub, ServicioInmobiliario sIn) {

		try {
			AlohAndesTransactionManager<K> tm = new AlohAndesTransactionManager<K>(getPath());

			tm.agregarOfertaHotel(hostal, oferta, habitaciones, sPub, sIn);
			return Response.status(200).entity(oferta).build();
		} 
		catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}
	
	@POST
	@Path("/requerimientos/oferta/PersonaNatural")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response createOfertaPersonaNatural(PersonaNatural hostal, Oferta oferta, List<Habitacion> habitaciones, ServicioPublico sPub, ServicioInmobiliario sIn) {

		try {
			AlohAndesTransactionManager<K> tm = new AlohAndesTransactionManager<K>(getPath());

			tm.agregarOfertaPersonaNatural(hostal, oferta, habitaciones, sPub, sIn);
			return Response.status(200).entity(oferta).build();
		} 
		catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}
	
	@POST
	@Path("/requerimientos/oferta/viviendaUniversitaria")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response createOfertaViviendaUniversitaria(ViviendaUniversitaria hostal, Oferta oferta, List<Habitacion> habitaciones, ServicioPublico sPub, ServicioInmobiliario sIn) {

		try {
			AlohAndesTransactionManager<K> tm = new AlohAndesTransactionManager<K>(getPath());

			tm.agregarOfertaViviendaUniversitaria(hostal, oferta, habitaciones, sPub, sIn);
			return Response.status(200).entity(oferta).build();
		} 
		catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}	
	
	//TODO Reserva
	
	@POST
	@Path("/requerimientos/reserva/hotel")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response createReservaHotel(Hotel hotel, Reserva reserva, List<Habitacion> habitaciones, Cliente cliente) {

		try {
			AlohAndesTransactionManager<K> tm = new AlohAndesTransactionManager<K>(getPath());

			tm.agregarReservaHotel(cliente, reserva, habitaciones, hotel);
			return Response.status(200).entity(reserva).build();
		} 
		catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}
	
	@POST
	@Path("/requerimientos/reserva/Hostal")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response createReservaHostal(Hostal hotel, Reserva reserva, List<Habitacion> habitaciones, Cliente cliente) {

		try {
			AlohAndesTransactionManager<K> tm = new AlohAndesTransactionManager<K>(getPath());

			tm.agregarReservaHostal(cliente, reserva, habitaciones, hotel);
			return Response.status(200).entity(reserva).build();
		} 
		catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}
	
	@POST
	@Path("/requerimientos/reserva/viviendauniversitaria")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response createReservaViviendaUniversitaria(Hostal hotel, Reserva reserva, List<Habitacion> habitaciones, Cliente cliente) {

		try {
			AlohAndesTransactionManager<K> tm = new AlohAndesTransactionManager<K>(getPath());

			tm.agregarReservaHostal(cliente, reserva, habitaciones, hotel);
			return Response.status(200).entity(reserva).build();
		} 
		catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}
	
	@POST
	@Path("/requerimientos/reserva/personanatural")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response createReservaPersonaNatural(PersonaNatural hotel, Reserva reserva, List<Habitacion> habitaciones, Cliente cliente) {

		try {
			AlohAndesTransactionManager<K> tm = new AlohAndesTransactionManager<K>(getPath());

			tm.agregarReservaPersonaNatural(cliente, reserva, habitaciones, hotel);
			return Response.status(200).entity(reserva).build();
		} 
		catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}
	
	@POST
	@Path("/requerimientos/reserva/viviendauniversitaria")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response createReservaVecino(Vecino hotel, Reserva reserva, List<Habitacion> habitaciones, Cliente cliente) {

		try {
			AlohAndesTransactionManager<K> tm = new AlohAndesTransactionManager<K>(getPath());

			tm.agregarReservaVecino(cliente, reserva, habitaciones, hotel);
			return Response.status(200).entity(reserva).build();
		} 
		catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}
	
	
}
