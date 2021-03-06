package rest;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.swing.plaf.FontUIResource;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
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
import vos.VOClienteDuracion;
import vos.VOClienteReservas;
import vos.VOConsumo;
import vos.VOConsumoVersion2;
import vos.VODisponible;
import vos.VOExtraHotel;
import vos.VOExtraPersona;
import vos.VOExtraViviendaUniversitaria;
import vos.VOFechaDemanda;
import vos.VOFechaIngresos;
import vos.VOHostalExtra;
import vos.VOIndiceHostal;
import vos.VOIndiceHotel;
import vos.VOIndicePersona;
import vos.VOIndiceVivendaU;
import vos.VOOfertaHabitaciones;
import vos.VOReservaHabitaciones;
import vos.VOSemanas;
import vos.VOUsoCliente;
import vos.VOUsoEspecificoCliente;
import vos.VOUsoHostal;
import vos.VOUsoHotel;
import vos.VOUsoPersona;
import vos.VOUsoVivienda;
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

	private Map<Integer,ReservaColectiva> hashDeReservasColectivas;

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
	@Path("oferta/viviendaUniversitaria")
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



	@POST
	@Path("reserva/hotel")
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
	@Path("reserva/Hostal")
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
	@Path("reserva/viviendauniversitaria")
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
	@Path("reserva/personanatural")
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
	@Path("reserva/viviendauniversitaria")
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

	//--------------------------------------------------------------------------------------
	// REQUERIMIENTOS DE CONSULTA
	//--------------------------------------------------------------------------------------


	//RC1------------------------------------------------------------------
	@GET
	@Path("dinero/hostal")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getDineroHostal() {

		try {
			AlohAndesTransactionManager<K> tm = new AlohAndesTransactionManager<K>(getPath());
			System.out.println(tm);
			ArrayList<VOHostalExtra> hostal = tm.operadoresConCosas1();
			System.out.println(hostal);
			return Response.status(200).entity(hostal).build();
		} 
		catch (Exception e) {
			System.out.println(e);
			System.out.println(e.getMessage());
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}

	@GET
	@Path("dinero/hotel")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getDineroHotel() {
		try {
			AlohAndesTransactionManager<K> tm = new AlohAndesTransactionManager<K>(getPath());

			ArrayList<VOExtraHotel> hostal = tm.operadoresConCosas2();
			return Response.status(200).entity(hostal).build();
		} 
		catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}

	@GET
	@Path("dinero/persona")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getDineroPersona() {

		try {
			AlohAndesTransactionManager<K> tm = new AlohAndesTransactionManager<K>(getPath());

			ArrayList<VOExtraPersona> hostal = tm.operadoresConCosas3();
			return Response.status(200).entity(hostal).build();
		} 
		catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}

	@GET
	@Path("dinero/viviendau")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getDineroViviendaU() {

		try {
			AlohAndesTransactionManager<K> tm = new AlohAndesTransactionManager<K>(getPath());

			ArrayList<VOExtraViviendaUniversitaria> hostal = tm.operadoresConCosas4();
			return Response.status(200).entity(hostal).build();
		} 
		catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}


	//RC2----------------------------------------------------------------------------

	@GET
	@Path("20ofertas")
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

	//RC3----------------------------------------------------------------------------

	@GET
	@Path("indice/hotel")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getIndiceHotel() {
		try {
			AlohAndesTransactionManager<K> tm = new AlohAndesTransactionManager<K>(getPath());

			ArrayList<VOIndiceHotel> sisa = tm.ocupacionHoteles();
			return Response.status(200).entity(sisa).build();
		} 
		catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}

	@GET
	@Path("indice/hostal")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getIndiceHostal() {


		try {
			AlohAndesTransactionManager<K> tm = new AlohAndesTransactionManager<K>(getPath());

			ArrayList<VOIndiceHostal> sisa = tm.ocupacionHostales();
			return Response.status(200).entity(sisa).build();
		} 
		catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}

	@GET
	@Path("indice/persona")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getIndicePersona() {

		try {
			AlohAndesTransactionManager<K> tm = new AlohAndesTransactionManager<K>(getPath());

			ArrayList<VOIndicePersona> sisa = tm.ocupacionPersonas();
			return Response.status(200).entity(sisa).build();
		} 
		catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}

	@GET
	@Path("indice/viviendau")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getIndiceViviendaU() {

		try {
			AlohAndesTransactionManager<K> tm = new AlohAndesTransactionManager<K>(getPath());

			ArrayList<VOIndiceVivendaU> sisa = tm.ocupacionViviendaU();
			return Response.status(200).entity(sisa).build();
		} 
		catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}

	//RC4----------------------------------------------------------------------------

	@GET
	@Path("disponible/hotel/{diaInic: \\d+}/{diaFin: \\d+}/{mesInic: \\d+}/{mesFin: \\d+}/{tipoInmo: .+}/{tipoPub: .+}")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getDisponibleHotel(@PathParam("diaInic") Integer diaInic,@PathParam("diaFin") Integer diaFin, @PathParam("mesInic") Integer mesInic, @PathParam("mesFin") Integer mesFin,@PathParam("tipoInmo") String tipoServicioInmobiliario, @PathParam("tipoPub") String tipoServicioPublico) {

		try {
			System.out.println("SOY HGAY!");
			AlohAndesTransactionManager<K> tm = new AlohAndesTransactionManager<K>(getPath());

			ArrayList<VODisponible> sisa = tm.disponibilidadHotel(diaInic, diaFin, mesInic, mesFin, tipoServicioInmobiliario, tipoServicioPublico);
			return Response.status(200).entity(sisa).build();
		} 
		catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}

	@GET
	@Path("disponible/hostal/{diaInic: \\d+}/{diaFin: \\d+}/{mesInic: \\d+}/{mesFin: \\d+}/{tipoInmo: .+}/{tipoPub: .+}")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getDisponibleHostal(@PathParam("diaInic") Integer diaInic,@PathParam("diaFin") Integer diaFin, @PathParam("mesInic") Integer mesInic, @PathParam("mesFin") Integer mesFin,@PathParam("tipoInmo") String tipoServicioInmobiliario, @PathParam("tipoPub") String tipoServicioPublico) {

		try {
			AlohAndesTransactionManager<K> tm = new AlohAndesTransactionManager<K>(getPath());

			ArrayList<VODisponible> sisa = tm.disponibilidadHostal(diaInic, diaFin, mesInic, mesFin, tipoServicioInmobiliario, tipoServicioPublico);
			return Response.status(200).entity(sisa).build();
		} 
		catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}

	@GET
	@Path("disponible/persona/{diaInic: \\d+}/{diaFin: \\d+}/{mesInic: \\d+}/{mesFin: \\d+}/{tipoInmo: .+}/{tipoPub: .+}")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getDisponiblePersona(@PathParam("diaInic") Integer diaInic,@PathParam("diaFin") Integer diaFin, @PathParam("mesInic") Integer mesInic, @PathParam("mesFin") Integer mesFin,@PathParam("tipoInmo") String tipoServicioInmobiliario, @PathParam("tipoPub") String tipoServicioPublico) {

		try {
			AlohAndesTransactionManager<K> tm = new AlohAndesTransactionManager<K>(getPath());

			ArrayList<VODisponible> sisa = tm.disponibilidadPersona(diaInic, diaFin, mesInic, mesFin, tipoServicioInmobiliario, tipoServicioPublico);
			return Response.status(200).entity(sisa).build();
		} 
		catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}

	@GET
	@Path("disponible/vivienda/{diaInic: \\d+}/{diaFin: \\d+}/{mesInic: \\d+}/{mesFin: \\d+}/{tipoInmo: .+}/{tipoPub: .+}")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getDisponibleViviendaU(@PathParam("diaInic") Integer diaInic,@PathParam("diaFin") Integer diaFin, @PathParam("mesInic") Integer mesInic, @PathParam("mesFin") Integer mesFin,@PathParam("tipoInmo") String tipoServicioInmobiliario, @PathParam("tipoPub") String tipoServicioPublico) {

		try {
			AlohAndesTransactionManager<K> tm = new AlohAndesTransactionManager<K>(getPath());

			ArrayList<VODisponible> sisa = tm.disponibilidadViviendaU(diaInic, diaFin, mesInic, mesFin, tipoServicioInmobiliario, tipoServicioPublico);
			return Response.status(200).entity(sisa).build();
		} 
		catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}

	//RC5----------------------------------------------------------------------------
	@GET
	@Path("uso/hotel")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getUsoHotel() {

		try {
			AlohAndesTransactionManager<K> tm = new AlohAndesTransactionManager<K>(getPath());

			ArrayList<VOUsoHotel> sisa = tm.usoHotel();
			return Response.status(200).entity(sisa).build();
		} 
		catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}

	@GET
	@Path("uso/hostal")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getUsoHostal() {

		try {
			AlohAndesTransactionManager<K> tm = new AlohAndesTransactionManager<K>(getPath());

			ArrayList<VOUsoHostal> sisa = tm.usoHostal();
			return Response.status(200).entity(sisa).build();
		} 
		catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}


	@GET
	@Path("uso/persona")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getUsoPersona() {

		try {
			AlohAndesTransactionManager<K> tm = new AlohAndesTransactionManager<K>(getPath());

			ArrayList<VOUsoPersona> sisa = tm.usoPersona();
			return Response.status(200).entity(sisa).build();
		} 
		catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}


	@GET
	@Path("uso/viviendaU")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getUsoVivienda() {

		try {
			AlohAndesTransactionManager<K> tm = new AlohAndesTransactionManager<K>(getPath());

			ArrayList<VOUsoVivienda> sisa = tm.usoViviendaU();
			return Response.status(200).entity(sisa).build();
		} 
		catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}

	//RC6----------------------------------------------------------------------------

	/*----------------Uso general de la aplicacion------- */
	@GET
	@Path("uso/general/{id: \\d+}")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getUsoGeneralAplicacion(@PathParam("id") Integer id) {

		try {
			AlohAndesTransactionManager<K> tm = new AlohAndesTransactionManager<K>(getPath());

			ArrayList<VOUsoCliente> sisa = tm.usoGeneralCliente(id);
			return Response.status(200).entity(sisa).build();
		} 
		catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}

	/*----------------Detalles especificos(si es que  se tienen)------- */

	@GET
	@Path("uso/especifico/{id: \\d+}")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getUsoEspecificoAplicacion(@PathParam("id") Integer id) {

		try {
			AlohAndesTransactionManager<K> tm = new AlohAndesTransactionManager<K>(getPath());

			ArrayList<VOUsoEspecificoCliente> sisa = tm.usoEspecificoCliente(id);
			return Response.status(200).entity(sisa).build();
		} 
		catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}


	//RC7----------------------------------------------------------------------------

	@GET
	@Path("fecha/mayordemanda/{tipo: .+}/{mes: \\d+}")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getFechaMayorDemanda(@PathParam("tipo") String tipo, @PathParam("mes") Integer mes) {

		try {
			AlohAndesTransactionManager<K> tm = new AlohAndesTransactionManager<K>(getPath());

			ArrayList<VOFechaDemanda> sisa = tm.fechaMayorDemanda(tipo, mes);
			return Response.status(200).entity(sisa).build();
		} 
		catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}

	@GET
	@Path("fecha/mayoringresos/{tipo: .+}/{mes: \\d+}")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getFechaMayorIngresos(@PathParam("tipo") String tipo, @PathParam("mes") Integer mes) {

		try {
			AlohAndesTransactionManager<K> tm = new AlohAndesTransactionManager<K>(getPath());

			ArrayList<VOFechaIngresos> sisa = tm.fechaMayorIngresos(tipo, mes);
			return Response.status(200).entity(sisa).build();
		} 
		catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}

	@GET
	@Path("fecha/menordemanda/{tipo: .+}/{mes: \\d+}")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getFechaMenorDemanda(@PathParam("tipo") String tipo, @PathParam("mes") Integer mes) {

		try {
			AlohAndesTransactionManager<K> tm = new AlohAndesTransactionManager<K>(getPath());

			ArrayList<VOFechaDemanda> sisa = tm.fechaMenorDemanda(tipo, mes);
			return Response.status(200).entity(sisa).build();
		} 
		catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}

	//RC8----------------------------------------------------------------------------

	@GET
	@Path("clienteduracion/{tipo: .+}/{id: \\d+}")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getClientePorDuracion(@PathParam("tipo") String tipo, @PathParam("id") Integer identificador) {

		try {
			AlohAndesTransactionManager<K> tm = new AlohAndesTransactionManager<K>(getPath());

			ArrayList<VOClienteDuracion> sisa = tm.clienteFrecuenteDuracion(tipo, identificador);
			return Response.status(200).entity(sisa).build();
		} 
		catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}

	@GET
	@Path("clientereserva")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getClientePorReseva() {

		try {
			AlohAndesTransactionManager<K> tm = new AlohAndesTransactionManager<K>(getPath());

			ArrayList<VOClienteReservas> sisa = tm.clienteFrecuenteReservas();
			return Response.status(200).entity(sisa).build();
		} 
		catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}

	//RC10----------------------------------------------------------------------------

	@GET
	@Path("consumoPrimeraAdmin/{idOferta: \\d+}/{diai: \\d+}/{diaf: \\d+}/{mesi: \\d+}/{mesf: \\d+}/{criterio: .+}")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getConsumoPrimeraAdmin(@PathParam("idOferta") Integer idOferta, @PathParam("diai") Integer diaInic, @PathParam("diaf") Integer diaFin, @PathParam("mesi") Integer mesInic, @PathParam("mesf") Integer mesFin, @PathParam("criterio") String criterio){
		try{
			AlohAndesTransactionManager<K> tm = new AlohAndesTransactionManager<K>(getPath());

			ArrayList<VOConsumo> resp = tm.consumoPrimeraVersionAdmin(idOferta, diaInic, diaFin, mesInic, mesFin, criterio);
			return Response.status(200).entity(resp).build();
		}
		catch(Exception e){
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}


	@GET
	@Path("consumoPrimeraCliente/{idOferta: \\d+}/{diai: \\d+}/{diaf: \\d+}/{mesi: \\d+}/{mesf: \\d+}/{idCliente: \\d+}/{criterio: .+}")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getConsumoPrimeraCliente(@PathParam("idOferta") Integer idOferta, @PathParam("diai") Integer diaInic, @PathParam("diaf") Integer diaFin, @PathParam("mesi") Integer mesInic, @PathParam("mesf") Integer mesFin, @PathParam("idCliente") Integer IdCli, @PathParam("criterio") String criterio){
		try{
			AlohAndesTransactionManager<K> tm = new AlohAndesTransactionManager<K>(getPath());

			ArrayList<VOConsumo> resp = tm.consumoPrimeraVersionCliente(idOferta, diaInic, diaFin, mesInic, mesFin, IdCli, criterio);
			return Response.status(200).entity(resp).build();
		}
		catch(Exception e){
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}

	//RC11----------------------------------------------------------------------------

	@GET
	@Path("consumoSegundaAdmin/{diai: \\d+}/{diaf: \\d+}/{mesi: \\d+}/{mesf: \\d+}")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getConsumoSegundaAdmin( @PathParam("diai") Integer diaInic, @PathParam("diaf") Integer diaFin, @PathParam("mesi") Integer mesInic, @PathParam("mesf") Integer mesFin){
		try{
			AlohAndesTransactionManager<K> tm = new AlohAndesTransactionManager<K>(getPath());

			ArrayList<VOConsumoVersion2> resp = tm.consumoSegundaVersionAdmin( diaInic, diaFin, mesInic, mesFin);
			return Response.status(200).entity(resp).build();
		}
		catch(Exception e){
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}

	@GET
	@Path("consumoSegundaCliente/{diai: \\d+}/{diaf: \\d+}/{mesi: \\d+}/{mesf: \\d+}/{idCliente: \\d+}")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getConsumoSegundaCliente( @PathParam("diai") Integer diaInic, @PathParam("diaf") Integer diaFin, @PathParam("mesi") Integer mesInic, @PathParam("mesf") Integer mesFin, @PathParam("idCliente") Integer IdCli){
		try{
			AlohAndesTransactionManager<K> tm = new AlohAndesTransactionManager<K>(getPath());

			ArrayList<VOConsumoVersion2> resp = tm.consumoSegundaVersionCliente(diaInic, diaFin, mesInic, mesFin, IdCli);
			return Response.status(200).entity(resp).build();
		}
		catch(Exception e){
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}

	//RC12----------------------------------------------------------------------------

	@GET
	@Path("semanasFecha")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getSemanasDeFecha(){

		try {
			AlohAndesTransactionManager<K> tm = new AlohAndesTransactionManager<K>(getPath());

			ArrayList<VOSemanas> resp = tm.semanasDeFecha();
			return Response.status(200).entity(resp).build();
		} 
		catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}

	//RC13----------------------------------------------------------------------------
	@GET
	@Path("clientesConReservasMes/{mes: \\d+}")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getClientesConReservasAlMes(@PathParam("mes") Integer mes){

		try {
			AlohAndesTransactionManager<K> tm = new AlohAndesTransactionManager<K>(getPath());

			ArrayList<VOConsumoVersion2> resp = tm.clientesConReservasAlMes(mes);
			return Response.status(200).entity(resp).build();
		} 
		catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}


	@GET
	@Path("clientesConReservasCostosas")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getClientesConReservasCostosas(){

		try {
			AlohAndesTransactionManager<K> tm = new AlohAndesTransactionManager<K>(getPath());

			ArrayList<VOConsumoVersion2> resp = tm.clientesConReservasCostosas();
			return Response.status(200).entity(resp).build();
		} 
		catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}


	@GET
	@Path("clientesConReservasSuites")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getClientesConReservasSuites(){

		try {
			AlohAndesTransactionManager<K> tm = new AlohAndesTransactionManager<K>(getPath());

			ArrayList<VOConsumoVersion2> resp = tm.clientesConReservasEnSuites();
			return Response.status(200).entity(resp).build();
		} 
		catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}

	//-------------------------------------------------------------------------------------------
	//FIN REQUERIMIENTOS DE CONSULTA
	//-------------------------------------------------------------------------------------------

	//RF7
	@PUT
	@Path("/RF7")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response requerimientoRF7(ReservaColectiva reCo)
	{
		AlohAndesTransactionManager<K> tm = new AlohAndesTransactionManager<K>(getPath());
		try
		{
			tm.crearReservaColectiva(reCo);
			return Response.status(200).entity(reCo).build();
		}
		catch(Exception e)
		{
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}

	//RF8

	@GET
	@Path( "/RF8/{id: \\d+}" )
	@Produces( { MediaType.APPLICATION_JSON } )
	public Response requerimientoRF8( @PathParam("id") Integer id)
	{
		AlohAndesTransactionManager<K> tm = new AlohAndesTransactionManager<K>(getPath());
		try 
		{
			Reserva reservita = tm.cancelarReserva(id);
			return Response.status(200).entity(reservita).build();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}

	//RF9


	@PUT	
	@Path( "/RF9/{id: \\d+}" )
	@Produces( { MediaType.APPLICATION_JSON } )
	public Response requerimientoRF9(@PathParam ("id")Integer id)
	{
		AlohAndesTransactionManager<K> tm = new AlohAndesTransactionManager<K>(getPath());

		Oferta reservas;
		reservas = tm.cancelarOferta(id);
		return Response.status(200).entity(reservas).build();
	}

	//RF10

	@PUT
	@Path( "/RF10/{id: \\d+}" )
	@Produces( { MediaType.APPLICATION_JSON } )
	public Response requerimientoRF10(@PathParam ("id") Integer id)
	{
		Oferta reservas = new Oferta(id, id, false, id, id, id, id);
		try
		{
			AlohAndesTransactionManager<K> tm = new AlohAndesTransactionManager<K>(getPath());

			DAOOferta daoOferta = new DAOOferta();
			Connection conn = tm.darConexion();
			daoOferta.setConn(conn);

			reservas = tm.activarOferta(daoOferta.findOfertaById(id));
			return Response.status(200).entity(reservas).build();
		}
		catch (Exception e)
		{
			return Response.status(500).entity(reservas).build();
		}
	}

}
