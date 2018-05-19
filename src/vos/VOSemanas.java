package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class VOSemanas {
	
	@JsonProperty("ID_OFERTA")
	private Integer ID_OFERTA;
	
	@JsonProperty("ID_HOSTAL")
	private Integer ID_HOSTAL;
	
	@JsonProperty("ID_HOTEL")
	private Integer ID_HOTEL;
	
	@JsonProperty("ID_PERSONA")
	private Integer ID_PERSONA;
	
	@JsonProperty("ID_VIVIENDAU")
	private Integer ID_VIVIENDAU;
	
	@JsonProperty("SEMANA")
	private Integer SEMANA;
	
	@JsonProperty("FECHA")
	private String FECHA;
	
	@JsonProperty("NUM_RESERVAS")
	private Integer NUM_RESERVAS;
	
	public VOSemanas(Integer idOf, Integer idHost, Integer idHot, Integer idPersona, Integer idVivienda, 
			Integer week, String date, Integer num){
		
		ID_OFERTA = idOf;
		ID_HOSTAL = idHost;
		ID_HOTEL = idHot;
		ID_PERSONA = idPersona;
		ID_VIVIENDAU = idVivienda;
		SEMANA = week;
		FECHA = date;
		NUM_RESERVAS = num;		
	}

	public Integer getID_OFERTA() {
		return ID_OFERTA;
	}

	public void setID_OFERTA(Integer iD_OFERTA) {
		ID_OFERTA = iD_OFERTA;
	}

	public Integer getID_HOSTAL() {
		return ID_HOSTAL;
	}

	public void setID_HOSTAL(Integer iD_HOSTAL) {
		ID_HOSTAL = iD_HOSTAL;
	}

	public Integer getID_HOTEL() {
		return ID_HOTEL;
	}

	public void setID_HOTEL(Integer iD_HOTEL) {
		ID_HOTEL = iD_HOTEL;
	}

	public Integer getID_PERSONA() {
		return ID_PERSONA;
	}

	public void setID_PERSONA(Integer iD_PERSONA) {
		ID_PERSONA = iD_PERSONA;
	}

	public Integer getID_VIVIENDAU() {
		return ID_VIVIENDAU;
	}

	public void setID_VIVIENDAU(Integer iD_VIVIENDAU) {
		ID_VIVIENDAU = iD_VIVIENDAU;
	}

	public Integer getSEMANA() {
		return SEMANA;
	}

	public void setSEMANA(Integer sEMANA) {
		SEMANA = sEMANA;
	}

	public String getFECHA() {
		return FECHA;
	}

	public void setFECHA(String fECHA) {
		FECHA = fECHA;
	}

	public Integer getNUM_RESERVAS() {
		return NUM_RESERVAS;
	}

	public void setNUM_RESERVAS(Integer nUM_RESERVAS) {
		NUM_RESERVAS = nUM_RESERVAS;
	}
	
	
	
	

}
