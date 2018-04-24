package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class VOUsoEspecificoCliente {
	
	@JsonProperty("ID_RESERVA")
	private Integer ID_RESERVA;

	@JsonProperty("ID_CLIENTE")
	private Integer ID_CLIENTE;

	@JsonProperty("ID_HABITACION")
	private Integer ID_HABITACION;

	@JsonProperty("CAPACIDAD_HABITACION")
	private Integer CAPACIDAD_HABITACION;

	@JsonProperty("PRECIO")
	private Integer PRECIO;

	@JsonProperty("TAMANIO")
	private Integer TAMANIO;

	@JsonProperty("TIPO")
	private String TIPO;

	@JsonProperty("UBICACION")
	private String UBICACION;

	@JsonProperty("ID_RESERVA_1")
	private Integer ID_RESERVA_1;

	@JsonProperty("ID_OFERTA")
	private Integer ID_OFERTA;

	@JsonProperty("ID_HOTEL")
	private Integer ID_HOTEL;

	@JsonProperty("ID_HOSTAL")
	private Integer ID_HOSTAL;

	@JsonProperty("ID_PERSONA")
	private Integer ID_PERSONA;

	@JsonProperty("ID_VIVIENDAU")
	private Integer ID_VIVIENDAU;
	
	@JsonProperty("TIPO_SERVICIO_PUBLICO")
	private String TIPO_SERVICIO_PUBLICO;
	
	@JsonProperty("COSTO_SERVICIO_PUBLICO")
	private Integer COSTO_SERVICIO_PUBLICO;
	
	
	@JsonProperty("TIPO_SERVICIO_INMOBILIARIO")
	private String TIPO_SERVICIO_INMOBILIARIO;
	
	@JsonProperty("COSTO_SERVICIO_INMOBILIARIO")
	private Integer COSTO_SERVICIO_INMOBILIARIO;



	public VOUsoEspecificoCliente(Integer idRes, Integer idCli, Integer hab,Integer cap, Integer prec, Integer tam, 
			String tip, String ubi, Integer res2, Integer ofer, Integer hote, Integer hosta, Integer per, Integer viv,
			String tipPub, Integer prePu, String tipIn, Integer preIn){
		
		ID_RESERVA = idRes;
		ID_CLIENTE = idCli;
		ID_HABITACION = hab;
		CAPACIDAD_HABITACION =cap ;
		PRECIO = prec;
		TAMANIO = tam;
		TIPO = tip;
		UBICACION = ubi;
		ID_RESERVA_1 = res2;
		ID_OFERTA = ofer;
		ID_HOTEL = hote;
		ID_HOSTAL = hosta;
		ID_PERSONA = per;
		ID_VIVIENDAU= viv;
		TIPO_SERVICIO_PUBLICO = tipPub;
		COSTO_SERVICIO_PUBLICO = prePu;
		TIPO_SERVICIO_INMOBILIARIO = tipIn;
		COSTO_SERVICIO_INMOBILIARIO = preIn;
	}



	public Integer getID_RESERVA() {
		return ID_RESERVA;
	}



	public void setID_RESERVA(Integer iD_RESERVA) {
		ID_RESERVA = iD_RESERVA;
	}



	public Integer getID_CLIENTE() {
		return ID_CLIENTE;
	}



	public void setID_CLIENTE(Integer iD_CLIENTE) {
		ID_CLIENTE = iD_CLIENTE;
	}



	public Integer getID_HABITACION() {
		return ID_HABITACION;
	}



	public void setID_HABITACION(Integer iD_HABITACION) {
		ID_HABITACION = iD_HABITACION;
	}



	public Integer getCAPACIDAD_HABITACION() {
		return CAPACIDAD_HABITACION;
	}



	public void setCAPACIDAD_HABITACION(Integer cAPACIDAD_HABITACION) {
		CAPACIDAD_HABITACION = cAPACIDAD_HABITACION;
	}



	public Integer getPRECIO() {
		return PRECIO;
	}



	public void setPRECIO(Integer pRECIO) {
		PRECIO = pRECIO;
	}



	public Integer getTAMANIO() {
		return TAMANIO;
	}



	public void setTAMANIO(Integer tAMANIO) {
		TAMANIO = tAMANIO;
	}



	public String getTIPO() {
		return TIPO;
	}



	public void setTIPO(String tIPO) {
		TIPO = tIPO;
	}



	public String getUBICACION() {
		return UBICACION;
	}



	public void setUBICACION(String uBICACION) {
		UBICACION = uBICACION;
	}



	public Integer getID_RESERVA_1() {
		return ID_RESERVA_1;
	}



	public void setID_RESERVA_1(Integer iD_RESERVA_1) {
		ID_RESERVA_1 = iD_RESERVA_1;
	}



	public Integer getID_OFERTA() {
		return ID_OFERTA;
	}



	public void setID_OFERTA(Integer iD_OFERTA) {
		ID_OFERTA = iD_OFERTA;
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



	public String getTIPO_SERVICIO_PUBLICO() {
		return TIPO_SERVICIO_PUBLICO;
	}



	public void setTIPO_SERVICIO_PUBLICO(String tIPO_SERVICIO_PUBLICO) {
		TIPO_SERVICIO_PUBLICO = tIPO_SERVICIO_PUBLICO;
	}



	public Integer getCOSTO_SERVICIO_PUBLICO() {
		return COSTO_SERVICIO_PUBLICO;
	}



	public void setCOSTO_SERVICIO_PUBLICO(Integer cOSTO_SERVICIO_PUBLICO) {
		COSTO_SERVICIO_PUBLICO = cOSTO_SERVICIO_PUBLICO;
	}



	public String getTIPO_SERVICIO_INMOBILIARIO() {
		return TIPO_SERVICIO_INMOBILIARIO;
	}



	public void setTIPO_SERVICIO_INMOBILIARIO(String tIPO_SERVICIO_INMOBILIARIO) {
		TIPO_SERVICIO_INMOBILIARIO = tIPO_SERVICIO_INMOBILIARIO;
	}



	public Integer getCOSTO_SERVICIO_INMOBILIARIO() {
		return COSTO_SERVICIO_INMOBILIARIO;
	}



	public void setCOSTO_SERVICIO_INMOBILIARIO(Integer cOSTO_SERVICIO_INMOBILIARIO) {
		COSTO_SERVICIO_INMOBILIARIO = cOSTO_SERVICIO_INMOBILIARIO;
	}
	
	

}
