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

	@JsonProperty( value = "idSala")
	private int id;

	@JsonProperty( value = "proposito")
	private String proposito;

	@JsonProperty( value = "tieneCostoAdicional")
	private boolean tieneCostoAdicional;
	
	@JsonProperty( value = "idViviendaU")
	private int idViviendaU;
	
	//
	// Constructor
	//
	
	public Sala(
			@JsonProperty( value = "costo") double costo,
			@JsonProperty( value = "idSala") int id,
			@JsonProperty( value = "proposito") String proposito,
			@JsonProperty( value = "tieneCostoAdicional") boolean tieneCostoAdicional,
			@JsonProperty( value = "idViviendaU") int idViviendaU) {
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