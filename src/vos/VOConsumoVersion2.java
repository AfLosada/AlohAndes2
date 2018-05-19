package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class VOConsumoVersion2 {

	@JsonProperty("ID_CLIENTE")
	private Integer ID_CLIENTE;
	
	@JsonProperty("MIEMBRO_COMUNIDAD")
	private String MIEMBRO_COMUNIDAD;
	
	@JsonProperty("TIPO_CLIENTE")
	private String TIPO_CLIENTE;
	
	@JsonProperty("NOMBRE_CLIENTE")
	private String NOMBRE_CLIENTE;
	
	@JsonProperty("EDAD")
	private Integer EDAD;
	
	public VOConsumoVersion2(Integer idCl, String miem, String tip, String nom, Integer age){
		
		ID_CLIENTE = idCl;
		MIEMBRO_COMUNIDAD = miem;
		TIPO_CLIENTE = tip;
		NOMBRE_CLIENTE = nom;
		EDAD = age;
	}

	public Integer getID_CLIENTE() {
		return ID_CLIENTE;
	}

	public void setID_CLIENTE(Integer iD_CLIENTE) {
		ID_CLIENTE = iD_CLIENTE;
	}

	public String getMIEMBRO_COMUNIDAD() {
		return MIEMBRO_COMUNIDAD;
	}

	public void setMIEMBRO_COMUNIDAD(String mIEMBRO_COMUNIDAD) {
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
