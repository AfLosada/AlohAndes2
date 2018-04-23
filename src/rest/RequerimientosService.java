	package rest;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;
import javax.swing.plaf.FontUIResource;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
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
import vos.ReservaColectiva;
import vos.ServicioInmobiliario;
import vos.ServicioPublico;
import vos.VOExtraHotel;
import vos.VOExtraPersona;
import vos.VOExtraViviendaUniversitaria;
import vos.VOOfertaHabitaciones;
import vos.VOReservaHabitaciones;
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
	
	AlohAndesTransactionManager<Operador> tm;

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

	public Oferta getOfertaByID(Integer id) throws SQLException{
		DAOOferta dao = new DAOOferta();
		dao.setConn(tm.darConexion());
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
	public Reserva getReservaByID(Integer id) throws SQLException{
		DAOReserva dao = new DAOReserva();
		dao.setConn(tm.darConexion());
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

	public Cliente getClienteByID(Integer id) throws SQLException{
		DAOCliente dao = new DAOCliente();
		dao.setConn(tm.darConexion());
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
	public ServicioPublico getServicioPublicoByID(Integer id) throws SQLException{
		DAOServicioPublico dao = new DAOServicioPublico();
		dao.setConn(tm.darConexion());
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

	public ServicioInmobiliario getServicioInmobiliarioByID(Integer id) throws SQLException{
		DAOServicioInmobiliario dao = new DAOServicioInmobiliario();
		dao.setConn(tm.darConexion());
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

	public Hostal getHostalByID(Integer id) throws SQLException{
		DAOHostal dao = new DAOHostal();
		dao.setConn(tm.darConexion());
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

	public Hotel getHotellByID(Integer id) throws SQLException{
		DAOHotel dao = new DAOHotel();
		dao.setConn(tm.darConexion());
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

	public Habitacion getHabitacionByID(Integer id) throws SQLException{
		DAOHabitacion dao = new DAOHabitacion();
		dao.setConn(tm.darConexion());
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


	@PUT
	@Path("/oferta/hostal/")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response createOfertaHostal(VOOfertaHabitaciones vohab) throws SQLException {

		Integer hostal = vohab.getIdProv();
		Integer oferta = vohab.getIdOferta();
		Integer sPub = vohab.getIdSPub();
		Integer sIn = vohab.getIdSIn();
		List<Integer> habitaciones = vohab.getHabitaciones();
		try {
			AlohAndesTransactionManager<K> tm = new AlohAndesTransactionManager<K>(getPath());

			tm.agregarOfertaHostal(hostal, oferta, habitaciones, sPub, sIn);
			return Response.status(200).build();
		} 
		catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}


	@PUT
	@Path("/oferta/hotel/")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response createOfertaHotel(VOOfertaHabitaciones vohab) throws SQLException {

		Integer hostal = vohab.getIdProv();
		Integer oferta = vohab.getIdOferta();
		Integer sPub = vohab.getIdSPub();
		Integer sIn = vohab.getIdSIn();
		List<Integer> habitaciones = vohab.getHabitaciones();
		try {
			AlohAndesTransactionManager<K> tm = new AlohAndesTransactionManager<K>(getPath());

			tm.agregarOfertaHotel(hostal, oferta, habitaciones, sPub, sIn);
			return Response.status(200).build();
		} 
		catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}

	@PUT
	@Path("/oferta/PersonaNatural")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response createOfertaPersonaNatural(VOOfertaHabitaciones vohab) throws SQLException {
		Integer hostal = vohab.getIdProv();
		Integer oferta = vohab.getIdOferta();
		Integer sPub = vohab.getIdSPub();
		Integer sIn = vohab.getIdSIn();
		List<Integer> habitaciones = vohab.getHabitaciones();
		try {
			AlohAndesTransactionManager<K> tm = new AlohAndesTransactionManager<K>(getPath());

			tm.agregarOfertaPersonaNatural(hostal, oferta, habitaciones, sPub, sIn);
			return Response.status(200).build();
		} 
		catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}

	@PUT
	@Path("/oferta/viviendaUniversitaria")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response createOfertaViviendaUniversitaria(VOOfertaHabitaciones vohab) throws SQLException {
		Integer hostal = vohab.getIdProv();
		Integer oferta = vohab.getIdOferta();
		Integer sPub = vohab.getIdSPub();
		Integer sIn = vohab.getIdSIn();
		List<Integer> habitaciones = vohab.getHabitaciones();
		try {
			AlohAndesTransactionManager<K> tm = new AlohAndesTransactionManager<K>(getPath());

			tm.agregarOfertaPersonaNatural(hostal, oferta, habitaciones, sPub, sIn);
			return Response.status(200).build();
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
	public Response createReservaHotel(VOReservaHabitaciones voRe) throws SQLException {
		Hotel hotel = getHotellByID(voRe.getIdProv());
		Reserva reserva = getReservaByID(voRe.getIdReserva());
		Cliente cliente = getClienteByID(voRe.getIdCliente());
		List<Integer> habitaciones = voRe.getHabitaciones();
		try {
			AlohAndesTransactionManager<K> tm = new AlohAndesTransactionManager<K>(getPath());

			tm.agregarReservaHotel(cliente.getId(), reserva.getId(), habitaciones, hotel.getIdHotel());
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
	public Response createReservaHostal(VOReservaHabitaciones voRe) throws SQLException {
		Hostal hotel = getHostalByID(voRe.getIdProv());
		Reserva reserva = getReservaByID(voRe.getIdReserva());
		Cliente cliente = getClienteByID(voRe.getIdCliente());
		List<Integer> habitaciones = voRe.getHabitaciones();
		try {
			AlohAndesTransactionManager<K> tm = new AlohAndesTransactionManager<K>(getPath());

			tm.agregarReservaHotel(cliente.getId(), reserva.getId(), habitaciones, hotel.getId());
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
	public Response createReservaViviendaUniversitaria(Integer idVivienda, Integer idReserva, List<Integer> identificadores, Integer idCliente) throws SQLException {

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

			tm.agregarReservaViviendaUniversitaria(cliente.getId(), reserva.getId(), identificadores, vivienda.getId());
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
	public Response createReservaPersonaNatural(Integer idPersona, Integer idReserva, List<Integer> identificadores, Integer idCliente) throws SQLException {

		PersonaNatural persona = getPersonaByID(idPersona);
		Reserva reserva = getReservaByID(idReserva);
		Cliente cliente = getClienteByID(idCliente);
		try {
			AlohAndesTransactionManager<K> tm = new AlohAndesTransactionManager<K>(getPath());

			tm.agregarReservaPersonaNatural(cliente.getId(), reserva.getId(), identificadores, persona.getId());
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
	public Response createReservaVecino(Integer idVecino, Integer idReserva, List<Integer> identificadores, Integer idCliente) throws SQLException {

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

			tm.agregarReservaVecino(cliente.getId(), reserva.getId(), identificadores, vecino.getId());
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

	//TODO RF7
	
	public Response requerimientoRF7(ReservaColectiva reCo) throws SQLException, Exception
	{
		Integer id = reCo.getId();
		List<Integer> servicioIn = reCo.getIdSInm();
		List<Integer> servicioPub = reCo.getIdSPub();
		Integer cantidad = reCo.getCantidad();
		
		ArrayList<Reserva> reservas = new ArrayList<>();
		DAOReserva daoReserva = new DAOReserva();
		DAOHabitacion daoHabs = new DAOHabitacion();
		DAOServicioInmobiliario daoSIn = new DAOServicioInmobiliario();
		DAOServicioPublico daoSPub = new DAOServicioPublico();
		
		ArrayList<Reserva> xd2 = daoReserva.getReservas();
		ArrayList<Habitacion> habs = daoHabs.getHabitacions();
		
		
		
		for (int i = 0; i < habs.size(); i++) 
		{
			Habitacion actual = habs.get(i);
			if(actual.getTipo().equals(reCo.getTipo()))
			{
				
			}
		}
		
		
		
	}
	

}
