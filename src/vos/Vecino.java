package vos;

import java.util.List;


import org.codehaus.jackson.annotate.JsonProperty;

public class Vecino extends PersonaNatural
{


	//
	// Atributos
	//
	
	@JsonProperty("idVecino")
	private int idVecino;

	@JsonProperty("idVivienda")
	private int idVivienda;

	
	//
	// Constructor
	//
	
	public Vecino(
			@JsonProperty("camaraComercio")boolean camaraComercio, 
			@JsonProperty("nombre")String nombreOperador, 
			@JsonProperty("superintendeciaTurismo")boolean superIntendenciaTurismo, 
			@JsonProperty("edad")int edad, 
			@JsonProperty("idPersona")int id,
			@JsonProperty("miembroComunidad")boolean miembro, 
			@JsonProperty("idVecino")int idVecino,
			@JsonProperty("idVivienda") int idVivienda) {
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
