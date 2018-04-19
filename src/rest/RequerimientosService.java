	package rest;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.swing.plaf.FontUIResource;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import dao.DAOCliente;
import dao.DAOHabitacion;
import dao.DAOHostal;
import dao.DAOHotel;
import dao.DAOOferta;
import dao.DAOPersonaNatural;
import dao.DAOReserva;
import dao.DAOServicioInmobiliario;
import dao.DAOServicioPublico;
import dao.DAOVecino;
import dao.DAOViviendaUniversitaria;
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
import vos.VOExtraHotel;
import vos.VOExtraPersona;
import vos.VOExtraViviendaUniversitaria;
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

	public Oferta getOfertaByID(Integer id){
		DAOOferta dao = new DAOOferta();
		Oferta resp = null;
		try {
			resp = dao.findOfertaById(id);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}
	public Reserva getReservaByID(Integer id){
		DAOReserva dao = new DAOReserva();
		Reserva resp= null;
		try {
			resp = dao.findReservaById(id);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp; 
	}

	public Cliente getClienteByID(Integer id){
		DAOCliente dao = new DAOCliente();
		Cliente resp= null;
		try {
			resp = dao.findClienteById(id);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp; 
	}
	public ServicioPublico getServicioPublicoByID(Integer id){
		DAOServicioPublico dao = new DAOServicioPublico();
		ServicioPublico resp= null;
		try {
			resp = dao.findServicioPublicoById(id);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp; 
	}

	public ServicioInmobiliario getServicioInmobiliarioByID(Integer id){
		DAOServicioInmobiliario dao = new DAOServicioInmobiliario();
		ServicioInmobiliario resp= null;
		try {
			resp = dao.findServicioInmobiliarioById(id);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp; 
	}

	public Hostal getHostalByID(Integer id){
		DAOHostal dao = new DAOHostal();
		Hostal resp= null;
		try {
			resp = dao.findHostalById(id);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp; 
	}

	public Hotel getHotellByID(Integer id){
		DAOHotel dao = new DAOHotel();
		Hotel resp= null;
		try {
			resp = dao.findHotelById(id);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp; 
	}

	public Habitacion getHabitacionByID(Integer id){
		DAOHabitacion dao = new DAOHabitacion();
		Habitacion resp= null;
		try {
			resp = dao.findHabitacionById(id);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp; 
	}

	public PersonaNatural getPersonaByID(Integer id){
		DAOPersonaNatural dao = new DAOPersonaNatural();
		PersonaNatural resp= null;
		try {
			resp = dao.findPersonaNaturalById(id);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp; 
	}

	public ViviendaUniversitaria getViviendaUByID(Integer id){
		DAOViviendaUniversitaria dao = new DAOViviendaUniversitaria();
		ViviendaUniversitaria resp= null;
		try {
			resp = dao.findViviendaUniversitariaById(id);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp; 
	}
	public Vecino getVecinoByID(Integer id){
		DAOVecino dao = new DAOVecino();
		Vecino resp= null;
		try {
			resp = dao.findVecinoById(id);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp; 
	}


	@POST
	@Path("/oferta/hostal/")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response createOfertaHostal(Integer idHostal, Integer idOferta, List<Integer> identificadores, Integer idSPub, Integer idSIn) {

		Hostal hostal = getHostalByID(idHostal);
		Oferta oferta = getOfertaByID(idOferta);
		ServicioPublico sPub = getServicioPublicoByID(idSPub);
		ServicioInmobiliario sIn = getServicioInmobiliarioByID(idSIn);
		List<Habitacion> habitaciones = new ArrayList<>();
		for (Integer i = 0; i < identificadores.size(); i++) {
			Integer id = identificadores.get(i);
			Habitacion hab = getHabitacionByID(id);
			habitaciones.add(hab);
		}
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
	@Path("/oferta/hotel")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response createOfertaHotel(Integer idHotel, Integer idOferta, List<Integer> identificadores, Integer idSPub, Integer idSIn) {
		Hotel hotel = getHotellByID(idHotel);
		Oferta oferta = getOfertaByID(idOferta);
		ServicioPublico sPub = getServicioPublicoByID(idSPub);
		ServicioInmobiliario sIn = getServicioInmobiliarioByID(idSIn);
		List<Habitacion> habitaciones = new ArrayList<>();
		for (Integer i = 0; i < identificadores.size(); i++) {
			Integer id = identificadores.get(i);
			Habitacion hab = getHabitacionByID(id);
			habitaciones.add(hab);
		}

		try {
			AlohAndesTransactionManager<K> tm = new AlohAndesTransactionManager<K>(getPath());

			tm.agregarOfertaHotel(hotel, oferta, habitaciones, sPub, sIn);
			return Response.status(200).entity(oferta).build();
		} 
		catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}

	@POST
	@Path("/requerimientos/oferta/PersonaNatural")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response createOfertaPersonaNatural(Integer idPersona, Integer idOferta, List<Integer> identificadores, Integer idSPub, Integer idSIn) {
		PersonaNatural persona = getPersonaByID(idPersona);
		Oferta oferta = getOfertaByID(idOferta);
		ServicioPublico sPub = getServicioPublicoByID(idSPub);
		ServicioInmobiliario sIn = getServicioInmobiliarioByID(idSIn);
		List<Habitacion> habitaciones = new ArrayList<>();
		for (Integer i = 0; i < identificadores.size(); i++) {
			Integer id = identificadores.get(i);
			Habitacion hab = getHabitacionByID(id);
			habitaciones.add(hab);
		}	
		try {
			AlohAndesTransactionManager<K> tm = new AlohAndesTransactionManager<K>(getPath());

			tm.agregarOfertaPersonaNatural(persona, oferta, habitaciones, sPub, sIn);
			return Response.status(200).entity(oferta).build();
		} 
		catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}

	@POST
	@Path("/requerimientos/oferta/viviendaUniversitaria")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response createOfertaViviendaUniversitaria(Integer idViviendaU, Integer idOferta, List<Integer> identificadores, Integer idSPub, Integer idSIn) {
		ViviendaUniversitaria viviendaU = getViviendaUByID(idViviendaU);
		Oferta oferta = getOfertaByID(idOferta);
		ServicioPublico sPub = getServicioPublicoByID(idSPub);
		ServicioInmobiliario sIn = getServicioInmobiliarioByID(idSIn);
		List<Habitacion> habitaciones = new ArrayList<>();
		for (Integer i = 0; i < identificadores.size(); i++) {
			Integer id = identificadores.get(i);
			Habitacion hab = getHabitacionByID(id);
			habitaciones.add(hab);
		}	
		try {
			AlohAndesTransactionManager<K> tm = new AlohAndesTransactionManager<K>(getPath());

			tm.agregarOfertaViviendaUniversitaria(viviendaU, oferta, habitaciones, sPub, sIn);
			return Response.status(200).entity(oferta).build();
		} 
		catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}	

	//TODO Reserva

	@POST
	@Path("/requerimientos/reserva/hotel")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response createReservaHotel(Integer idHotel, Integer idReserva, List<Integer> identificadores, Integer idCliente) {
		Hotel hotel = getHotellByID(idHotel);
		Reserva reserva = getReservaByID(idReserva);
		Cliente cliente = getClienteByID(idCliente);
		List<Habitacion> habitaciones = new ArrayList<>();
		for (Integer i = 0; i < identificadores.size(); i++) {
			Integer id = identificadores.get(i);
			Habitacion hab = getHabitacionByID(id);
			habitaciones.add(hab);
		}
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
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response createReservaHostal(Integer idHostal, Integer idReserva, List<Integer> identificadores, Integer idCliente) {
		Hostal hostal = getHostalByID(idHostal);
		Reserva reserva = getReservaByID(idReserva);
		Cliente cliente = getClienteByID(idCliente);
		List<Habitacion> habitaciones = new ArrayList<>();
		for (Integer i = 0; i < identificadores.size(); i++) {
			Integer id = identificadores.get(i);
			Habitacion hab = getHabitacionByID(id);
			habitaciones.add(hab);
		}
		try {
			AlohAndesTransactionManager<K> tm = new AlohAndesTransactionManager<K>(getPath());

			tm.agregarReservaHostal(cliente, reserva, habitaciones, hostal);
			return Response.status(200).entity(reserva).build();
		} 
		catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}

	@POST
	@Path("/requerimientos/reserva/viviendauniversitaria")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response createReservaViviendaUniversitaria(Integer idVivienda, Integer idReserva, List<Integer> identificadores, Integer idCliente) {

		ViviendaUniversitaria vivienda = getViviendaUByID(idVivienda);
		Reserva reserva = getReservaByID(idReserva);
		Cliente cliente = getClienteByID(idCliente);
		List<Habitacion> habitaciones = new ArrayList<>();
		for (Integer i = 0; i < identificadores.size(); i++) {
			Integer id = identificadores.get(i);
			Habitacion hab = getHabitacionByID(id);
			habitaciones.add(hab);
		}
		try {
			AlohAndesTransactionManager<K> tm = new AlohAndesTransactionManager<K>(getPath());

			tm.agregarReservaViviendaUniversitaria(cliente, reserva, habitaciones, vivienda);
			return Response.status(200).entity(reserva).build();
		} 
		catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}

	@POST
	@Path("/requerimientos/reserva/personanatural")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response createReservaPersonaNatural(Integer idPersona, Integer idReserva, List<Integer> identificadores, Integer idCliente) {

		PersonaNatural persona = getPersonaByID(idPersona);
		Reserva reserva = getReservaByID(idReserva);
		Cliente cliente = getClienteByID(idCliente);
		List<Habitacion> habitaciones = new ArrayList<>();
		for (Integer i = 0; i < identificadores.size(); i++) {
			Integer id = identificadores.get(i);
			Habitacion hab = getHabitacionByID(id);
			habitaciones.add(hab);
		}
		try {
			AlohAndesTransactionManager<K> tm = new AlohAndesTransactionManager<K>(getPath());

			tm.agregarReservaPersonaNatural(cliente, reserva, habitaciones, persona);
			return Response.status(200).entity(reserva).build();
		} 
		catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}

	@POST
	@Path("/requerimientos/reserva/viviendauniversitaria")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response createReservaVecino(Integer idVecino, Integer idReserva, List<Integer> identificadores, Integer idCliente) {

		Vecino vecino = getVecinoByID(idVecino);
		Reserva reserva = getReservaByID(idReserva);
		Cliente cliente = getClienteByID(idCliente);
		List<Habitacion> habitaciones = new ArrayList<>();
		for (Integer i = 0; i < identificadores.size(); i++) {
			Integer id = identificadores.get(i);
			Habitacion hab = getHabitacionByID(id);
			habitaciones.add(hab);
		}
		try {
			AlohAndesTransactionManager<K> tm = new AlohAndesTransactionManager<K>(getPath());

			tm.agregarReservaVecino(cliente, reserva, habitaciones, vecino);
			return Response.status(200).entity(reserva).build();
		} 
		catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}


	//TODO REQ CONSULTA

	@POST
	@Path("/requerimientos/20ofertas")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getOfertasCool() {
		

		try {
			AlohAndesTransactionManager<K> tm = new AlohAndesTransactionManager<K>(getPath());

			ArrayList<Oferta> sisa = tm.operadoresMejores();
			return Response.status(200).entity(sisa).build();
		} 
		catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}


	@POST
	@Path("/requerimientos/cosasoperadores")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getCosasOperadores() {

		try {
			AlohAndesTransactionManager<K> tm = new AlohAndesTransactionManager<K>(getPath());

			ArrayList sisa = tm.operadoresConCosas1();
			ArrayList<VOExtraHotel> b = tm.operadoresConCosas2();
			ArrayList<VOExtraPersona> c = tm.operadoresConCosas3();
			ArrayList<VOExtraViviendaUniversitaria> d = tm.operadoresConCosas4();
			for(Integer i = 0; i<b.size(); i++)
			{
				sisa.add(b.get(i));
			}
			for(Integer i = 0; i<c.size(); i++)
			{
				sisa.add(c.get(i));
			}
			for(Integer i = 0; i<d.size(); i++)
			{
				sisa.add(d.get(i));
			}
			return Response.status(200).entity(sisa).build();
		} 
		catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}


}
