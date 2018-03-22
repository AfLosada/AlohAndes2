package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class VOHostalExtra
{
	

	@JsonProperty("valor")
	private Integer valor;

	@JsonProperty("id_Hostal")
	private Integer id_Hostal;
	
	/**
	 * @param id_Hostal
	 * @param valor
	 */
	public VOHostalExtra(Integer id_Hostal, Integer valor) {
		super();
		this.id_Hostal = id_Hostal;
		this.valor = valor;
	}

	/**
	 * @return the id_Hostal
	 */
	public Integer getId_Hostal() {
		return id_Hostal;
	}

	/**
	 * @param id_Hostal the id_Hostal to set
	 */
	public void setId_Hostal(Integer id_Hostal) {
		this.id_Hostal = id_Hostal;
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
