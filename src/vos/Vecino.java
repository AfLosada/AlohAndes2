package vos;

import java.util.List;


import org.codehaus.jackson.annotate.JsonProperty;

public class Vecino extends PersonaNatural
{


	//
	// Atributos
	//
	
	@JsonProperty("idVecino")
	private Integer idVecino;

	@JsonProperty("idVivienda")
	private Integer idVivienda;

	
	//
	// Constructor
	//
	
	public Vecino(
			@JsonProperty("camaraComercio")boolean camaraComercio, 
			@JsonProperty("nombre")String nombreOperador, 
			@JsonProperty("superIntegerendeciaTurismo")boolean superIntegerendenciaTurismo, 
			@JsonProperty("edad")Integer edad, 
			@JsonProperty("idPersona")Integer id,
			@JsonProperty("miembro")boolean miembro, 
			@JsonProperty("idVecino")Integer idVecino,
			@JsonProperty("idVivienda") Integer idVivienda) {
		super(camaraComercio, nombreOperador, superIntegerendenciaTurismo, edad, id, miembro);
		this.idVecino = idVecino;
		this.idVivienda = idVivienda;
	}
	

	//
	// Getters y Setters
	//
	
	/**
	 * @return the idVecino
	 */
	public Integer getIdVecino() {
		return idVecino;
	}


	/**
	 * @param idVecino the idVecino to set
	 */
	public void setIdVecino(Integer idVecino) {
		this.idVecino = idVecino;
	}
	
	/**
	 * @return the idVivienda
	 */
	public Integer getIdVivienda() {
		return idVivienda;
	}


	/**
	 * @param idVivienda the idVivienda to set
	 */
	public void setIdVivienda(Integer idVivienda) {
		this.idVivienda = idVivienda;
	}

	
}
