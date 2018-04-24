package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class VOClienteDuracion {
	
	@JsonProperty("ID_CLIENTE")
	private Integer ID_CLIENTE;
	
	@JsonProperty("NOMBRE_CLIENTE")
	private String NOMBRE_CLIENTE;

	@JsonProperty("TIPO_CLIENTE")
	private String TIPO_CLIENTE;

	@JsonProperty("DIAS_ESTADIA")
	private Integer DIAS_ESTADIA;
	
	
	public VOClienteDuracion(Integer id, String nom, String tip, Integer dii){
	
		ID_CLIENTE = id;
		NOMBRE_CLIENTE = nom;
		TIPO_CLIENTE =  tip;	
	}


	public Integer getID_CLIENTE() {
		return ID_CLIENTE;
	}


	public void setID_CLIENTE(Integer iD_CLIENTE) {
		ID_CLIENTE = iD_CLIENTE;
	}


	public String getNOMBRE_CLIENTE() {
		return NOMBRE_CLIENTE;
	}


	public void setNOMBRE_CLIENTE(String nOMBRE_CLIENTE) {
		NOMBRE_CLIENTE = nOMBRE_CLIENTE;
	}


	public String getTIPO_CLIENTE() {
		return TIPO_CLIENTE;
	}


	public void setTIPO_CLIENTE(String tIPO_CLIENTE) {
		TIPO_CLIENTE = tIPO_CLIENTE;
	}


	public Integer getDIAS_ESTADIA() {
		return DIAS_ESTADIA;
	}


	public void setDIAS_ESTADIA(Integer dIAS_ESTADIA) {
		DIAS_ESTADIA = dIAS_ESTADIA;
	}
	
	


}
