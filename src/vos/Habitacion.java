package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Habitacion 
{
	
	//
	// Atributos
	//
	
	@JsonProperty( value = "capacidad_habitacion" )
	private int capacidad;

	@JsonProperty( value = "id_habitacion" )
	private int id;
	
	@JsonProperty( value = "precio" )
	private int precio;
	
	@JsonProperty( value = "tamanio" )
	private double tamanio;
	
	@JsonProperty( value = "ubicacion" )
	private String ubicacion;
	
	@JsonProperty( value = "tipo" )
	private TipoHabitacion tipo;

	@JsonProperty ( value = "id_Oferta")
	private int idOferta;
	
	@JsonProperty ( value = "id_Reserva")
	private int idReserva;
	
	@JsonProperty (value = "id_Hotel")
	private int idHotel;
	
	@JsonProperty (value = "id_Hostal")
	private int idHostal;
	
	@JsonProperty (value = "id_Persona")
	private int idPersona;
	
	@JsonProperty (value = "id_ViviendaU")
	private int idViviendaU;
	
	
	private enum TipoHabitacion
	{
		Compartida, Individual, Suite, SemiSuite, Estandar;
	}
	
	
	//
	// Constructor
	//
	
	public Habitacion(int capacidad, int id, int precio, double tamanio, String ubicacion, String tipo, int idReserva, int idOfertax, int idHotel, int idHostal, int idPersona, int idViviendaU) 
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
	
	public int getCapacidad() {
		return capacidad;
	}


	public void setCapacidad(int capacidad) {
		this.capacidad = capacidad;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public int getPrecio() {
		return precio;
	}


	public void setPrecio(int precio) {
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
	public int getIdOferta() {
		return idOferta;
	}

	/**
	 * @param idOferta the idOferta to set
	 */
	public void setIdOferta(int idOferta) {
		this.idOferta = idOferta;
	}
	
	/**
	 * @return the idReserva
	 */
	public int getIdReserva() {
		return idReserva;
	}

	/**
	 * @param idReserva the idReserva to set
	 */
	public void setIdReserva(int idReserva) {
		this.idReserva = idReserva;
	}

	/**
	 * @return the idHotel
	 */
	public int getIdHotel() {
		return idHotel;
	}

	/**
	 * @param idHotel the idHotel to set
	 */
	public void setIdHotel(int idHotel) {
		this.idHotel = idHotel;
	}

	/**
	 * @return the idHostal
	 */
	public int getIdHostal() {
		return idHostal;
	}

	/**
	 * @param idHostal the idHostal to set
	 */
	public void setIdHostal(int idHostal) {
		this.idHostal = idHostal;
	}

	/**
	 * @return the idPersona
	 */
	public int getIdPersona() {
		return idPersona;
	}

	/**
	 * @param idPersona the idPersona to set
	 */
	public void setIdPersona(int idPersona) {
		this.idPersona = idPersona;
	}

	/**
	 * @return the idViviendaU
	 */
	public int getIdViviendaU() {
		return idViviendaU;
	}

	/**
	 * @param idViviendaU the idViviendaU to set
	 */
	public void setIdViviendaU(int idViviendaU) {
		this.idViviendaU = idViviendaU;
	}

	
}
