package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class VOConsumo {
	
	@JsonProperty("ID_HOTEL")
	private Integer ID_HOTEL;
	
	@JsonProperty("ID_HOSTAL")
	private Integer ID_HOSTAL;
	
	@JsonProperty("ID_PERSONA")
	private Integer ID_PERSONA;
	
	@JsonProperty("ID_VIVIENDAU")
	private Integer ID_VIVIENDAU;
	
	@JsonProperty("ID_CLIENTE")
	private Integer ID_CLIENTE;
	
	@JsonProperty("ID_OFERTA")
	private Integer ID_OFERTA;
	
	@JsonProperty("FECHA")
	private String FECHA;
	
	@JsonProperty("VALOR")
	private Integer VALOR;
	
	@JsonProperty("DURACION")
	private Integer DURACION;
	
	@JsonProperty("ID_RESERVA")
	private Integer ID_RESERVA;
	
	public VOConsumo(Integer idHot, Integer idHost, Integer idPer, Integer idViv, Integer idClien, Integer idOf,
			Integer val, Integer dur, Integer res, String date){
		
		ID_HOTEL = idHot;
		ID_HOSTAL = idHost;
		ID_PERSONA = idPer;
		ID_VIVIENDAU = idViv;
		ID_CLIENTE = idClien;
		ID_OFERTA =  idOf;
		FECHA = date;
		VALOR = val;
		DURACION = dur;
		ID_RESERVA =  res;	
	}

	public Integer getID_HOTEL() {
		return ID_HOTEL;
	}

	public void setID_HOTEL(Integer iD_HOTEL) {
		ID_HOTEL = iD_HOTEL;
	}

	public Integer getID_HOSTAL() {
		return ID_HOSTAL;
	}

	public void setID_HOSTAL(Integer iD_HOSTAL) {
		ID_HOSTAL = iD_HOSTAL;
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

	public Integer getID_CLIENTE() {
		return ID_CLIENTE;
	}

	public void setID_CLIENTE(Integer iD_CLIENTE) {
		ID_CLIENTE = iD_CLIENTE;
	}

	public Integer getID_OFERTA() {
		return ID_OFERTA;
	}

	public void setID_OFERTA(Integer iD_OFERTA) {
		ID_OFERTA = iD_OFERTA;
	}

	public String getFECHA() {
		return FECHA;
	}

	public void setFECHA(String fECHA) {
		FECHA = fECHA;
	}

	public Integer getVALOR() {
		return VALOR;
	}

	public void setVALOR(Integer vALOR) {
		VALOR = vALOR;
	}

	public Integer getDURACION() {
		return DURACION;
	}

	public void setDURACION(Integer dURACION) {
		DURACION = dURACION;
	}

	public Integer getID_RESERVA() {
		return ID_RESERVA;
	}

	public void setID_RESERVA(Integer iD_RESERVA) {
		ID_RESERVA = iD_RESERVA;
	}
		

}
