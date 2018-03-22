package vos;

import java.util.List;

import javax.swing.text.rtf.RTFEditorKit;

import org.codehaus.jackson.annotate.JsonProperty;

public class Oferta 
{
	@JsonProperty( value = "id_Oferta")
	private Integer id;

	@JsonProperty( value = "num_Reservas")
	private Integer numReservas;

	@JsonProperty( value = "vigente")
	private boolean vigente;

	@JsonProperty ( value = "id_Hostal" )
	private Integer idHostal;

	@JsonProperty ( value = "id_Persona" )
	private Integer idPersona;

	@JsonProperty ( value = "id_Hotel" )
	private Integer idHotel;

	@JsonProperty ( value = "id_ViviendaU" )
	private Integer idViviendaU;

	@JsonProperty ( value = "id_Cliente" )
	private Integer idCliente;

	//
	// Constructor
	//


	/**
	 * @param id
	 * @param numReservas
	 * @param vigente
	 * @param idHostal
	 * @param idPersona
	 * @param idHotel
	 * @param idViviendaU
	 */
	public Oferta(@JsonProperty( value = "id_Oferta")Integer id, 
			@JsonProperty( value = "num_Reservas")Integer numReservas, 
			@JsonProperty( value = "vigente")boolean vigente,
			@JsonProperty ( value = "id_Hostal" )Integer idHostal,
			@JsonProperty ( value = "id_Persona" )Integer idPersona,
			@JsonProperty ( value = "id_Hotel" )Integer idHotel, 
			@JsonProperty ( value = "id_Hotel" )Integer idViviendaU) {
		super();
		this.id = id;
		this.numReservas = numReservas;
		this.vigente = vigente;
		this.idHostal = idHostal;
		this.idPersona = idPersona;
		this.idHotel = idHotel;
		this.idViviendaU = idViviendaU;

	}
	
	//
	// Getters y Setters
	//


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getNumReservas() {
		return numReservas;
	}

	public void setNumReservas(Integer numReservas) {
		this.numReservas = numReservas;
	}

	public boolean isVigente() {
		return vigente;
	}

	public void setVigente(boolean vigente) {
		this.vigente = vigente;
	}

	/**
	 * @return the idHostal
	 */
	public Integer getIdHostal() {
		return idHostal;
	}



	/**
	 * @param idHostal the idHostal to set
	 */
	public void setIdHostal(Integer idHostal) {
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




	public String toString(boolean vigente2) 
	{
		String rta = "F";
		// TODO Auto-generated method stub
		if(vigente2)
			rta = "T";
		return rta;
	}


}
