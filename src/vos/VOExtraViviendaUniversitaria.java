package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class VOExtraViviendaUniversitaria 
{
	@JsonProperty("valor")
	private Integer valor;

	@JsonProperty("id_ViviendaUniversitaria")
	private Integer id_ViviendaUniversitaria;
	
	/**
	 * @param id_ViviendaUniversitaria
	 * @param valor
	 */
	public VOExtraViviendaUniversitaria(Integer id_ViviendaUniversitaria, Integer valor) {
		super();
		this.id_ViviendaUniversitaria = id_ViviendaUniversitaria;
		this.valor = valor;
	}

	/**
	 * @return the id_ViviendaUniversitaria
	 */
	public Integer getId_ViviendaUniversitaria() {
		return id_ViviendaUniversitaria;
	}

	/**
	 * @param id_ViviendaUniversitaria the id_ViviendaUniversitaria to set
	 */
	public void setId_ViviendaUniversitaria(Integer id_ViviendaUniversitaria) {
		this.id_ViviendaUniversitaria = id_ViviendaUniversitaria;
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
