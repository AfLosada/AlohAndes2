package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class VOClienteReservas {
	
	@JsonProperty("ID_CLIENTE")
	private Integer ID_CLIENTE;
	
	@JsonProperty("NUMERO_RESERVAS")
	private Integer NUMERO_RESERVAS;
	
	@JsonProperty("MIEMBRO_COMUNIDAD")
	private Boolean MIEMBRO_COMUNIDAD;
	
	@JsonProperty("TIPO_CLIENTE")
	private String TIPO_CLIENTE;
	
	@JsonProperty("NOMBRE_CLIENTE")
	private String NOMBRE_CLIENTE;
	
	@JsonProperty("EDAD")
	private Integer EDAD;

	
	public VOClienteReservas(Integer id, Integer numRes, Boolean mim, String tipoCli, String nom, Integer ed){
		
		ID_CLIENTE = id;
		NUMERO_RESERVAS = numRes;
		MIEMBRO_COMUNIDAD = mim;
		TIPO_CLIENTE = tipoCli;
		NOMBRE_CLIENTE = nom;
		EDAD  = ed;
	}


	public Integer getID_CLIENTE() {
		return ID_CLIENTE;
	}


	public void setID_CLIENTE(Integer iD_CLIENTE) {
		ID_CLIENTE = iD_CLIENTE;
	}


	public Integer getNUMERO_RESERVAS() {
		return NUMERO_RESERVAS;
	}


	public void setNUMERO_RESERVAS(Integer nUMERO_RESERVAS) {
		NUMERO_RESERVAS = nUMERO_RESERVAS;
	}


	public Boolean getMIEMBRO_COMUNIDAD() {
		return MIEMBRO_COMUNIDAD;
	}


	public void setMIEMBRO_COMUNIDAD(Boolean mIEMBRO_COMUNIDAD) {
		MIEMBRO_COMUNIDAD = mIEMBRO_COMUNIDAD;
	}


	public String getTIPO_CLIENTE() {
		return TIPO_CLIENTE;
	}


	public void setTIPO_CLIENTE(String tIPO_CLIENTE) {
		TIPO_CLIENTE = tIPO_CLIENTE;
	}


	public String getNOMBRE_CLIENTE() {
		return NOMBRE_CLIENTE;
	}


	public void setNOMBRE_CLIENTE(String nOMBRE_CLIENTE) {
		NOMBRE_CLIENTE = nOMBRE_CLIENTE;
	}


	public Integer getEDAD() {
		return EDAD;
	}


	public void setEDAD(Integer eDAD) {
		EDAD = eDAD;
	}
	
	




}
