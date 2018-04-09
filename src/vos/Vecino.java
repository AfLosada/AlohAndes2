package vos;

import java.util.List;


import org.codehaus.jackson.annotate.JsonProperty;

public class Vecino extends PersonaNatural
{


	//
	// Atributos
	//
	
	@JsonProperty("id_Vecino")
	private int idVecino;

	@JsonProperty("id_Vivienda")
	private int idVivienda;

	
	//
	// Constructor
	//
	
	public Vecino(
			@JsonProperty("camara_comercio")boolean camaraComercio, 
			@JsonProperty("nombre")String nombreOperador, 
			@JsonProperty("superintendecia_turismo")boolean superIntendenciaTurismo, 
			@JsonProperty("edad")int edad, 
			@JsonProperty("id_persona")int id,
			@JsonProperty("miembro_comunidad")boolean miembro, 
			@JsonProperty("id_Vecino")int idVecino,
			@JsonProperty("id_Vivienda") int idVivienda) {
		super(camaraComercio, nombreOperador, superIntendenciaTurismo, edad, id, miembro);
		this.idVecino = idVecino;
		this.idVivienda = idVivienda;
	}
	

	//
	// Getters y Setters
	//
	
	/**
	 * @return the idVecino
	 */
	public int getIdVecino() {
		return idVecino;
	}


	/**
	 * @param idVecino the idVecino to set
	 */
	public void setIdVecino(int idVecino) {
		this.idVecino = idVecino;
	}
	
	/**
	 * @return the idVivienda
	 */
	public int getIdVivienda() {
		return idVivienda;
	}


	/**
	 * @param idVivienda the idVivienda to set
	 */
	public void setIdVivienda(int idVivienda) {
		this.idVivienda = idVivienda;
	}

	
}
