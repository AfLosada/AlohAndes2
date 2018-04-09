package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class Reserva 
{

	//
	// Atributos
	//

	@JsonProperty( value = "confirmada")
	private boolean confirmada;


	@JsonProperty( value = "duracion")
	private String duracion;

	@JsonProperty( value = "fecha")
	private String fecha;

	@JsonProperty( value = "id_reserva")
	private int id;

	@JsonProperty( value = "pago_Anticipado")
	private boolean pagoAnticipado;

	@JsonProperty ( value = "tiempo_Cancelacion")
	private String tiempoCancelacion;

	@JsonProperty ( value = "valor")
	private double valor;


	@JsonProperty ( value = "id_Hostal" )
	private int idHostal;

	@JsonProperty ( value = "id_Persona" )
	private int idPersona;

	@JsonProperty ( value = "id_Hotel" )
	private int idHotel;

	@JsonProperty ( value = "id_ViviendaU" )
	private int idViviendaU;

	@JsonProperty ( value = "id_Cliente" )
	private int idCliente;


	//
	// Constructor
	//

	public Reserva(
			@JsonProperty( value = "confirmada" )boolean confirmada,
			@JsonProperty( value = "duracion" ) String duracion,
			@JsonProperty( value = "fecha" ) String fecha,
			@JsonProperty( value = "id_Reserva" ) int id,
			@JsonProperty( value = "pago_anticipado" ) boolean pagoAnticipado,
			@JsonProperty( value = "tiempo_cancelacion" ) String tiempoCancelacion, 
			@JsonProperty( value = "valor" ) double valor,
			@JsonProperty( value = "id_hostal" ) int idHostal,
			@JsonProperty( value = "id_persona" ) int idPersona,
			@JsonProperty( value = "id_hotel" ) int idHotel,
			@JsonProperty( value = "id_viviendau" ) int idViviendaU,
			@JsonProperty( value = "id_cliente" )	int idCliente) {
		super();
		this.confirmada = confirmada;
		this.duracion = duracion;
		this.fecha = fecha;
		this.id = id;
		this.pagoAnticipado = pagoAnticipado;
		this.tiempoCancelacion = tiempoCancelacion;
		this.valor = valor;
		this.idHostal = idHostal;
		this.idPersona = idPersona;
		this.idHotel = idHotel;
		this.idViviendaU = idViviendaU;
		this.idCliente = idCliente;
	}




	//
	// Getters y Setters
	//

	public boolean isConfirmada() {
		return confirmada;
	}



	public void setConfirmada(boolean confirmada) {
		this.confirmada = confirmada;
	}



	public String getDuracion() {
		return duracion;
	}



	public void setDuracion(String duracion) {
		this.duracion = duracion;
	}



	public String getFecha() {
		return fecha;
	}



	public void setFecha(String fecha) {
		this.fecha = fecha;
	}



	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public boolean isPagoAnticipado() {
		return pagoAnticipado;
	}



	public void setPagoAnticipado(boolean pagoAnticipado) {
		this.pagoAnticipado = pagoAnticipado;
	}



	public String getTiempoCancelacion() {
		return tiempoCancelacion;
	}



	public void setTiempoCancelacion(String tiempoCancelacion) {
		this.tiempoCancelacion = tiempoCancelacion;
	}



	public double getValor() {
		return valor;
	}



	public void setValor(double valor) {
		this.valor = valor;
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




	/**
	 * @return the idCliente
	 */
	public int getIdCliente() {
		return idCliente;
	}




	/**
	 * @param idCliente the idCliente to set
	 */
	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}




	public String toString(boolean confirmada2) {
		// TODO Auto-generated method stub
		String rta = "F";
		if(confirmada2)
			rta = "T";
		return rta;
	}




}
