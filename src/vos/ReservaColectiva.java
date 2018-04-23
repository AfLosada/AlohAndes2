package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class ReservaColectiva 
{

	@JsonProperty ( value = "id_ReservaColectiva")
	Integer id;

	@JsonProperty ( value = "id_ServicioPublico")
	List<Integer> idSPub;
	
	@JsonProperty ( value = "id_ServicioInmobiliario")
	List<Integer> idSInm;
	
	@JsonProperty ( value = "tipo")
	String tipo;

	@JsonProperty ( value = "cantidad")
	Integer cantidad;
	
	@JsonProperty ( value = "fecha")
	String fecha;
	
	@JsonProperty ( value = "duracion")
	String duracion;
	
	@JsonProperty ( value = "habitaciones")
	List<Habitacion> habitaciones;
	
	@JsonProperty ( value = "id_Cliente")
	Integer idCliente;
	
	@JsonProperty ( value = "reservas" )
	List<Reserva> reservas;
	
	/**
	 * @param id
	 * @param reserva
	 * @param idSPub
	 * @param idSInm
	 * @param tipo
	 * @param cantidad
	 */
	public ReservaColectiva(
			@JsonProperty ( value = "id_ReservaColectiva")Integer id,
			@JsonProperty ( value = "id_ServicioPublico")List<Integer> idSPub, 
			@JsonProperty ( value = "id_ServicioInmobiliario")List<Integer> idSInm, 
			@JsonProperty ( value = "tipo")String tipo,
			@JsonProperty ( value = "cantidad")Integer cantidad, 
			@JsonProperty ( value = "fecha")String fecha,
			@JsonProperty ( value = "duracion") String duracion,
			@JsonProperty ( value = "id_Cliente") Integer idCliente,
			@JsonProperty ( value = "reservas" ) List<Reserva> reservas) {
		super();
		this.id = id;
		this.idSPub = idSPub;
		this.idSInm = idSInm;
		this.tipo = tipo;
		this.cantidad = cantidad;
		this.fecha = fecha;
		this.idCliente = idCliente;
		this.reservas = reservas;
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	
	/**
	 * @return the idSPub
	 */
	public List<Integer> getIdSPub() {
		return idSPub;
	}

	/**
	 * @param idSPub the idSPub to set
	 */
	public void setIdSPub(List<Integer> idSPub) {
		this.idSPub = idSPub;
	}

	/**
	 * @return the idSInm
	 */
	public List<Integer> getIdSInm() {
		return idSInm;
	}

	/**
	 * @param idSInm the idSInm to set
	 */
	public void setIdSInm(List<Integer> idSInm) {
		this.idSInm = idSInm;
	}

	/**
	 * @return the tipo
	 */
	public String getTipo() {
		return tipo;
	}

	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	/**
	 * @return the cantidad
	 */
	public Integer getCantidad() {
		return cantidad;
	}

	/**
	 * @param cantidad the cantidad to set
	 */
	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	/**
	 * @return the fecha
	 */
	public String getFecha() {
		return fecha;
	}

	/**
	 * @param fecha the fecha to set
	 */
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	/**
	 * @return the duracion
	 */
	public String getDuracion() {
		return duracion;
	}

	/**
	 * @param duracion the duracion to set
	 */
	public void setDuracion(String duracion) {
		this.duracion = duracion;
	}

	/**
	 * @return the habitaciones
	 */
	public List<Habitacion> getHabitaciones() {
		return habitaciones;
	}

	/**
	 * @param habitaciones the habitaciones to set
	 */
	public void setHabitaciones(List<Habitacion> habitaciones) {
		this.habitaciones = habitaciones;
	}

	/**
	 * @return the idCliente
	 */
	public Integer getIdCliente() {
		return idCliente;
	}

	/**
	 * @param idCliente the idCliente to set
	 */
	public void setIdCliente(Integer idCliente) {
		this.idCliente = idCliente;
	}

	/**
	 * @return the reservas
	 */
	public List<Reserva> getReservas() {
		return reservas;
	}

	/**
	 * @param reservas the reservas to set
	 */
	public void setReservas(List<Reserva> reservas) {
		this.reservas = reservas;
	}

	
}
