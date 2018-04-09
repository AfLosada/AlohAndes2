package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class VOExtraPersona {

	

	@JsonProperty("valor")
	private Integer valor;

	@JsonProperty("idPersona")
	private Integer id_Persona;
	
	/**
	 * @param id_Persona
	 * @param valor
	 */
	public VOExtraPersona(Integer id_Persona, Integer valor) {
		super();
		this.id_Persona = id_Persona;
		this.valor = valor;
	}

	/**
	 * @return the id_Persona
	 */
	public Integer getId_Persona() {
		return id_Persona;
	}

	/**
	 * @param id_Persona the id_Persona to set
	 */
	public void setId_Persona(Integer id_Persona) {
		this.id_Persona = id_Persona;
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
