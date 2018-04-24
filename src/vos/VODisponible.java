package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class VODisponible {
	
	@JsonProperty("ID_SERVICIO_PUBLICO")
	private Integer ID_SERVICIO_PUBLICO;
	
	@JsonProperty("ID_SERVICIO_INMOBILIARIO")
	private Integer ID_SERVICIO_INMOBILIARIO;
	
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
	
	@JsonProperty("ID_RESERVA")
	private Integer ID_RESERVA;
	
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
	
	@JsonProperty("ID_RESERVA_1")
	private Integer ID_RESERVA_1;
	
	@JsonProperty("CONFIRMADA")
	private Boolean CONFIRMADA;
	
	@JsonProperty("FECHA")
	private String FECHA;
	
	@JsonProperty("TIEMPO_CANCELACION")
	private String TIEMPO_CANCELACION;
	
	@JsonProperty("VALOR")
	private Integer VALOR;
	
	@JsonProperty("ID_HOTEL_1")
	private Integer ID_HOTEL_1;
	
	@JsonProperty("ID_HOSTAL_1")
	private Integer ID_HOSTAL_1;
	
	@JsonProperty("ID_PERSONA_1")
	private Integer ID_PERSONA_1;
	
	@JsonProperty("ID_VIVIENDAU_1")
	private Integer ID_VIVIENDAU_1;
	
	@JsonProperty("DURACION")
	private Integer DURACION;
	
	@JsonProperty("PAGO_ANTICIPADO")
	private Boolean PAGO_ANTICIPADO;
	
	@JsonProperty("TIPO_SERVICIO_PUBLICO")
	private String TIPO_SERVICIO_PUBLICO;
	
	@JsonProperty("COSTO_SERVICIO_PUBLICO")
	private Integer COSTO_SERVICIO_PUBLICO;

	@JsonProperty("TIPO_SERVICIO_INMOBILIARIO")
	private String TIPO_SERVICIO_INMOBILIARIO;
	
	@JsonProperty("COSTO_SERVICIO_INMOBILIARIO")
	private Integer COSTO_SERVICIO_INMOBILIARIO;
	
	public VODisponible(Integer idServP, Integer idServIn, Integer habitacion, Integer capacidad,
			Integer precio, Integer tam,String tip,String ubicacion, Integer reserva, Integer oferta, Integer hotel,
			Integer hostal , Integer persona,Integer vivienda,Integer eserv2, Boolean conf, String fech,String tiempo,
			Integer valor, Integer hotel1, Integer hostal1, Integer persona1, Integer vivienda1,
			Integer duracion, Boolean pago, String tipoPub, Integer costoPub, String tipoIn, Integer costoIn){
		
		ID_SERVICIO_PUBLICO = idServP;
		ID_SERVICIO_INMOBILIARIO = idServP;
		ID_HABITACION = habitacion;
		CAPACIDAD_HABITACION = capacidad;
		PRECIO = precio;
		TAMANIO = tam;
		TIPO = tip;
		UBICACION = ubicacion;
		ID_RESERVA = reserva;
	    ID_OFERTA = oferta;
		ID_HOTEL = hotel;
		ID_HOSTAL = hostal;
		ID_PERSONA = persona;
		ID_VIVIENDAU = vivienda;
		ID_RESERVA_1 = eserv2;
		CONFIRMADA = conf;
		FECHA = fech;
		TIEMPO_CANCELACION = tiempo;
		VALOR  = valor;
		ID_HOTEL_1 = hotel1;
		ID_HOSTAL_1 = hostal1;
		ID_PERSONA_1 = persona1;
		ID_VIVIENDAU_1 = vivienda1;
		DURACION = duracion;
		PAGO_ANTICIPADO = pago;
		TIPO_SERVICIO_PUBLICO = tipoPub;
		COSTO_SERVICIO_PUBLICO = costoPub;
		TIPO_SERVICIO_INMOBILIARIO = tipoIn;
		COSTO_SERVICIO_INMOBILIARIO = costoIn;
	}

	public Integer getID_SERVICIO_PUBLICO() {
		return ID_SERVICIO_PUBLICO;
	}

	public void setID_SERVICIO_PUBLICO(Integer iD_SERVICIO_PUBLICO) {
		ID_SERVICIO_PUBLICO = iD_SERVICIO_PUBLICO;
	}

	public Integer getID_SERVICIO_INMOBILIARIO() {
		return ID_SERVICIO_INMOBILIARIO;
	}

	public void setID_SERVICIO_INMOBILIARIO(Integer iD_SERVICIO_INMOBILIARIO) {
		ID_SERVICIO_INMOBILIARIO = iD_SERVICIO_INMOBILIARIO;
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

	public Integer getID_RESERVA() {
		return ID_RESERVA;
	}

	public void setID_RESERVA(Integer iD_RESERVA) {
		ID_RESERVA = iD_RESERVA;
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

	public Integer getID_RESERVA_1() {
		return ID_RESERVA_1;
	}

	public void setID_RESERVA_1(Integer iD_RESERVA_1) {
		ID_RESERVA_1 = iD_RESERVA_1;
	}

	public Boolean getCONFIRMADA() {
		return CONFIRMADA;
	}

	public void setCONFIRMADA(Boolean cONFIRMADA) {
		CONFIRMADA = cONFIRMADA;
	}

	public String getFECHA() {
		return FECHA;
	}

	public void setFECHA(String fECHA) {
		FECHA = fECHA;
	}

	public String getTIEMPO_CANCELACION() {
		return TIEMPO_CANCELACION;
	}

	public void setTIEMPO_CANCELACION(String tIEMPO_CANCELACION) {
		TIEMPO_CANCELACION = tIEMPO_CANCELACION;
	}

	public Integer getVALOR() {
		return VALOR;
	}

	public void setVALOR(Integer vALOR) {
		VALOR = vALOR;
	}

	public Integer getID_HOTEL_1() {
		return ID_HOTEL_1;
	}

	public void setID_HOTEL_1(Integer iD_HOTEL_1) {
		ID_HOTEL_1 = iD_HOTEL_1;
	}

	public Integer getID_HOSTAL_1() {
		return ID_HOSTAL_1;
	}

	public void setID_HOSTAL_1(Integer iD_HOSTAL_1) {
		ID_HOSTAL_1 = iD_HOSTAL_1;
	}

	public Integer getID_PERSONA_1() {
		return ID_PERSONA_1;
	}

	public void setID_PERSONA_1(Integer iD_PERSONA_1) {
		ID_PERSONA_1 = iD_PERSONA_1;
	}

	public Integer getID_VIVIENDAU_1() {
		return ID_VIVIENDAU_1;
	}

	public void setID_VIVIENDAU_1(Integer iD_VIVIENDAU_1) {
		ID_VIVIENDAU_1 = iD_VIVIENDAU_1;
	}

	public Integer getDURACION() {
		return DURACION;
	}

	public void setDURACION(Integer dURACION) {
		DURACION = dURACION;
	}

	public Boolean getPAGO_ANTICIPADO() {
		return PAGO_ANTICIPADO;
	}

	public void setPAGO_ANTICIPADO(Boolean pAGO_ANTICIPADO) {
		PAGO_ANTICIPADO = pAGO_ANTICIPADO;
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
