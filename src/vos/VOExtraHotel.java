package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class VOExtraHotel {
	
	@JsonProperty("valor")
	private Integer valor;

	@JsonProperty("idHotel")
	private Integer id_Hotel;
	
	/**
	 * @param id_Hotel
	 * @param valor
	 */
	public VOExtraHotel(Integer id_Hotel, Integer valor) {
		super();
		this.id_Hotel = id_Hotel;
		this.valor = valor;
	}

	/**
	 * @return the id_Hotel
	 */
	public Integer getId_Hotel() {
		return id_Hotel;
	}

	/**
	 * @param id_Hotel the id_Hotel to set
	 */
	public void setId_Hotel(Integer id_Hotel) {
		this.id_Hotel = id_Hotel;
	}

	/**
	 * @return the valor
	 */
	public Integer getValor() {
		return valor;
	}

	/**
	 * @param valor the valor to set
	 */
	public void setValor(Integer valor) {
		this.valor = valor;
	}
	
}
