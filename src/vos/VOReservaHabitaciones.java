package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class VOReservaHabitaciones 
{
	@JsonProperty ( value = "id_Prov")
	Integer idProv;
	
	@JsonProperty ( value = "id_Reserva")
	Integer idReserva;
	
	@JsonProperty ( value = "habitaciones")
	private List<Integer> habitaciones;

	@JsonProperty ( value = "id_Cliente")
	private Integer idCliente;
	
	
	/**
	 * @param habitaciones
	 */
	public VOReservaHabitaciones(
			@JsonProperty ( value = "habitaciones") List<Integer> habitaciones,
			@JsonProperty ( value = "id_Prov")Integer idProv,
			@JsonProperty ( value = "id_Reserva")Integer idReserva, 
			@JsonProperty ( value = "id_Cliente") Integer idCliente)
	{
		super();
		this.habitaciones = habitaciones;
		this.idReserva = idReserva;
		this.idProv = idProv;
		this.idCliente = idCliente;
	}

	/**
	 * @return the idProv
	 */
	public Integer getIdProv() {
		return idProv;
	}

	/**
	 * @param idProv the idProv to set
	 */
	public void setIdProv(Integer idProv) {
		this.idProv = idProv;
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
	 * @return the habitaciones
	 */
	public List<Integer> getHabitaciones() {
		return habitaciones;
	}

	/**
	 * @param habitaciones the habitaciones to set
	 */
	public void setHabitaciones(List<Integer> habitaciones) {
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
}
