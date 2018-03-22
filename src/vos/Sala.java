package vos;

import org.codehaus.jackson.annotate.JsonProperty;
import vos.*;

import org.codehaus.jackson.annotate.JsonProperty;

public class Sala 
{


	//
	// Atributos
	//

	@JsonProperty( value = "costo")
	private double costo;

	@JsonProperty( value = "id_sala")
	private int id;

	@JsonProperty( value = "proposito")
	private String proposito;

	@JsonProperty( value = "tiene_Costo_Adicional")
	private boolean tieneCostoAdicional;
	
	@JsonProperty( value = "id_ViviendaU")
	private int idViviendaU;
	
	//
	// Constructor
	//
	
	public Sala(double costo, int id, String proposito, boolean tieneCostoAdicional, int idViviendaU) {
		super();
		this.costo = costo;
		this.id = id;
		this.proposito = proposito;
		this.tieneCostoAdicional = tieneCostoAdicional;
		this.idViviendaU = idViviendaU;
	}

	//
	// Getters y Setters
	//
	
	public double getCosto() {
		return costo;
	}

	public void setCosto(double costo) {
		this.costo = costo;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getProposito() {
		return proposito;
	}

	public void setProposito(String proposito) {
		this.proposito = proposito;
	}

	public boolean isTieneCostoAdicional() {
		return tieneCostoAdicional;
	}

	public void setTieneCostoAdicional(boolean tieneCostoAdicional) {
		this.tieneCostoAdicional = tieneCostoAdicional;
	}

	public String toString(boolean tieneCostoAdicional2) 
	{
		String rta = "F";
		// TODO Auto-generated method stub
		if(tieneCostoAdicional2)
			rta = "T";
		return rta;
	}
	
	public int getIdViviendaU(){
		return idViviendaU;
	}
	
	public void setIdViviendaU(int id){
		idViviendaU = id;
	}
	
}