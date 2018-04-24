package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class VOIndiceHotel {
	
	@JsonProperty("ID_HOTEL")
	private Integer ID_HOTEL;
	
	@JsonProperty("ID_OFERTA")
	private Integer ID_OFERTA;
	
	@JsonProperty("NUMERO_RESERVAS_ACTUALES")
	private Integer NUMERO_RESERVAS_ACTUALES;
	
	@JsonProperty("NUMERO_RESERVAS_HISTORICAS")
	private Integer NUMERO_RESERVAS_HISTORICAS;
	
	@JsonProperty("VIGENTE")
	private Boolean VIGENTE;
	
	
	public VOIndiceHotel(Integer idHotel, Integer idOfert, Integer numResAct, Integer numResHis, Boolean vig){
		super();
		ID_HOTEL = idHotel;
		ID_OFERTA = idOfert;
		NUMERO_RESERVAS_ACTUALES = numResAct;
		NUMERO_RESERVAS_HISTORICAS = numResHis;
	}


	public Integer getID_HOTEL() {
		return ID_HOTEL;
	}


	public void setID_HOTEL(Integer iD_HOTEL) {
		ID_HOTEL = iD_HOTEL;
	}


	public Integer getID_OFERTA() {
		return ID_OFERTA;
	}


	public void setID_OFERTA(Integer iD_OFERTA) {
		ID_OFERTA = iD_OFERTA;
	}


	public Integer getNUMERO_RESERVAS_ACTUALES() {
		return NUMERO_RESERVAS_ACTUALES;
	}


	public void setNUMERO_RESERVAS_ACTUALES(Integer nUMERO_RESERVAS_ACTUALES) {
		NUMERO_RESERVAS_ACTUALES = nUMERO_RESERVAS_ACTUALES;
	}


	public Integer getNUMERO_RESERVAS_HISTORICAS() {
		return NUMERO_RESERVAS_HISTORICAS;
	}


	public void setNUMERO_RESERVAS_HISTORICAS(Integer nUMERO_RESERVAS_HISTORICAS) {
		NUMERO_RESERVAS_HISTORICAS = nUMERO_RESERVAS_HISTORICAS;
	}
	
	
	public String toString(Boolean confirmada2) {
		// TODO Auto-generated method stub
		String rta = "F";
		if(confirmada2)
			rta = "T";
		return rta;
	}
}
