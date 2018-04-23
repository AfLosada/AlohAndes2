package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Habitacion 
{

	//
	// Atributos
	//

	@JsonProperty( value = "capacidad_habitacion" )
	private Integer capacidad;

	@JsonProperty( value = "id_habitacion" )
	private Integer id;

	@JsonProperty( value = "precio_habitacion" )
	private Integer precio;

	@JsonProperty( value = "tamanio" )
	private double tamanio;

	@JsonProperty( value = "ubicacion" )
	private String ubicacion;

	@JsonProperty( value = "tipo" )
	private TipoHabitacion tipo;

	@JsonProperty ( value = "id_Oferta")
	private Integer idOferta;

	@JsonProperty ( value = "id_Reserva")
	private Integer idReserva;

	@JsonProperty (value = "id_Hotel")
	private Integer idHotel;

	@JsonProperty (value = "id_Hostal")
	private Integer idHostal;

	@JsonProperty (value = "id_Persona")
	private Integer idPersona;

	@JsonProperty (value = "id_ViviendaU")
	private Integer idViviendaU;


	public enum TipoHabitacion
	{
		COMPARTIDA, INDIVIDUAL, SUITE, SEMISUITE, ESTANDAR, DOBLE;
	}


	//
	// Constructor
	//

	public Habitacion(
			@JsonProperty( value = "capacidad_habitacion" )Integer capacidad,
			@JsonProperty( value = "id_habitacion" )Integer id,
			@JsonProperty( value = "precio_habitacion" ) Integer precio,
			@JsonProperty( value = "precio" ) double tamanio,
			@JsonProperty( value = "ubicacion" ) String ubicacion,
			@JsonProperty( value = "tipo" ) String tipo,
			@JsonProperty( value = "id_Reserva" ) Integer idReserva,
			@JsonProperty( value = "id_Oferta" ) Integer idOfertax,
			@JsonProperty( value = "id_Hotel" ) Integer idHotel,
			@JsonProperty( value = "id_Hostal" ) Integer idHostal,
			@JsonProperty( value = "id_Persona" ) Integer idPersona,
			@JsonProperty( value = "id_ViviendaU" ) Integer idViviendaU) 
	{
		super();
		this.capacidad = capacidad;
		this.id = id;
		this.precio = precio;
		this.tamanio = tamanio;
		this.ubicacion = ubicacion;
		this.tipo = TipoHabitacion.valueOf(tipo);
		this.idReserva = idReserva;
		this.idOferta = idOfertax;
		this.idHotel = idHotel;
		this.idHostal = idHostal;
		this.idViviendaU = idViviendaU;
		this.idPersona = idPersona;

	}

	//
	// Getters y Setters
	//

	public Integer getCapacidad() {
		return capacidad;
	}


	public void setCapacidad(Integer capacidad) {
		this.capacidad = capacidad;
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public Integer getPrecio() {
		return precio;
	}


	public void setPrecio(Integer precio) {
		this.precio = precio;
	}


	public double getTamanio() {
		return tamanio;
	}


	public void setTamanio(double tamanio) {
		this.tamanio = tamanio;
	}


	public String getUbicacion() {
		return ubicacion;
	}


	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}


	public TipoHabitacion getTipo() {
		return tipo;
	}


	public void setTipo(TipoHabitacion tipo) {
		this.tipo = tipo;
	}

	/**
	 * @return the idOferta
	 */
	public Integer getIdOferta() {
		return idOferta;
	}

	/**
	 * @param idOferta the idOferta to set
	 */
	public void setIdOferta(Integer idOferta) {
		this.idOferta = idOferta;
	}

	/**
	 * @return the idReserva
	 */
	public Integer getIdReserva() {
		return idReserva;
	}

	/**
	 * @param idReserva the idReserva to set
	 */
	public void setIdReserva(Integer idReserva) {
		this.idReserva = idReserva;
	}

	/**
	 * @return the idHotel
	 */
	public Integer getIdHotel() {
		return idHotel;
	}

	/**
	 * @param idHotel the idHotel to set
	 */
	public void setIdHotel(Integer idHotel) {
		this.idHotel = idHotel;
	}

	/**
	 * @return the idHostal
	 */
	public Integer getIdHostal() {
		return idHostal;
	}

	/**
	 * @param idHostal the idHostal to set
	 */
	public void setIdHostal(Integer idHostal) {
		this.idHostal = idHostal;
	}

	/**
	 * @return the idPersona
	 */
	public Integer getIdPersona() {
		return idPersona;
	}

	/**
	 * @param idPersona the idPersona to set
	 */
	public void setIdPersona(Integer idPersona) {
		this.idPersona = idPersona;
	}

	/**
	 * @return the idViviendaU
	 */
	public Integer getIdViviendaU() {
		return idViviendaU;
	}

	/**
	 * @param idViviendaU the idViviendaU to set
	 */
	public void setIdViviendaU(Integer idViviendaU) {
		this.idViviendaU = idViviendaU;
	}


}
