package vos;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

import dao.DAOHabitacion;
import tm.AlohAndesTransactionManager;

public class Reserva 
{

	//
	// Atributos
	//

	@JsonProperty( value = "confirmada")
	private boolean confirmada;


	@JsonProperty( value = "duracion")
	private Integer duracion;

	@JsonProperty( value = "fecha")
	private String fecha;

	@JsonProperty( value = "idReserva")
	private Integer id;

	@JsonProperty( value = "pagoAnticipado")
	private boolean pagoAnticipado;

	@JsonProperty ( value = "tiempoCancelacion")
	private String tiempoCancelacion;

	@JsonProperty ( value = "valor")
	private double valor;


	@JsonProperty ( value = "idHostal" )
	private Integer idHostal;

	@JsonProperty ( value = "idPersona" )
	private Integer idPersona;

	@JsonProperty ( value = "idHotel" )
	private Integer idHotel;

	@JsonProperty ( value = "idViviendaU" )
	private Integer idViviendaU;

	@JsonProperty ( value = "idCliente" )
	private Integer idCliente;


	//
	// Constructor
	//

	public Reserva(
			@JsonProperty( value = "confirmada" )boolean confirmada,
			@JsonProperty( value = "duracion" ) Integer duracion,
			@JsonProperty( value = "fecha" ) String fecha,
			@JsonProperty( value = "idReserva" ) Integer id,
			@JsonProperty( value = "pagoAnticipado" ) boolean pagoAnticipado,
			@JsonProperty( value = "tiempoCancelacion" ) String tiempoCancelacion, 
			@JsonProperty( value = "valor" ) double valor,
			@JsonProperty( value = "idHostal" ) Integer idHostal,
			@JsonProperty( value = "idPersona" ) Integer idPersona,
			@JsonProperty( value = "idHotel" ) Integer idHotel,
			@JsonProperty( value = "idViviendaU" ) Integer idViviendaU,
			@JsonProperty( value = "idCliente" )	Integer idCliente) throws SQLException, Exception {
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



	public Integer getDuracion() {
		return duracion;
	}



	public void setDuracion(Integer duracion) {
		this.duracion = duracion;
	}



	public String getFecha() {
		return fecha;
	}



	public void setFecha(String fecha) {
		this.fecha = fecha;
	}



	public Integer getId() {
		return id;
	}



	public void setId(Integer id) {
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
	public Integer getidHostal() {
		return idHostal;
	}




	/**
	 * @param idHostal the idHostal to set
	 */
	public void setidHostal(Integer idHostal) {
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




	public String toString(boolean confirmada2) {
		// TODO Auto-generated method stub
		String rta = "F";
		if(confirmada2)
			rta = "T";
		return rta;
	}
}
