package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class ReservaColectiva 
{

	@JsonProperty ( value = "id_ReservaColectiva")
	Integer id;
	
	@JsonProperty ( value = "reserva")
	List<Integer> reserva;

	@JsonProperty ( value = "id_ServicioPublico")
	List<Integer> idSPub;
	
	@JsonProperty ( value = "id_ServicioInmobiliario")
	List<Integer> idSInm;
	
	@JsonProperty ( value = "tipo")
	String tipo;

	@JsonProperty ( value = "cantidad")
	Integer cantidad;

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
	 * @return the reserva
	 */
	public List<Integer> getReserva() {
		return reserva;
	}

	/**
	 * @param reserva the reserva to set
	 */
	public void setReserva(List<Integer> reserva) {
		this.reserva = reserva;
	}

	/**
	 * @param id
	 * @param reserva
	 */
	public ReservaColectiva(Integer id, List<Integer> reserva) {
		super();
		this.id = id;
		this.reserva = reserva;
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
	 * @param id
	 * @param reserva
	 * @param idSPub
	 * @param idSInm
	 * @param tipo
	 * @param cantidad
	 */
	public ReservaColectiva(Integer id, List<Integer> reserva, List<Integer> idSPub, List<Integer> idSInm, String tipo,
			Integer cantidad) {
		super();
		this.id = id;
		this.reserva = reserva;
		this.idSPub = idSPub;
		this.idSInm = idSInm;
		this.tipo = tipo;
		this.cantidad = cantidad;
	}
}
