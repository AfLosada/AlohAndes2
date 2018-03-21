package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Cliente 


{
	
	//
	// Atributos
	//
	
	@JsonProperty(value = "id_Cliente")
	private int id;

	@JsonProperty(value = "edad")
	private int edad;
	

	@JsonProperty(value = "miembro_comunidad")
	private boolean miembro;
	

	@JsonProperty(value = "nombre_cliente")
	private String nombre;
	

	@JsonProperty(value = "tipo_cliente")
	private String tipo;
	
	
	
	
	//
	// Metodo Constructor
	//
	
	

	public Cliente(	@JsonProperty(value = "id_Cliente") int id2, @JsonProperty(value = "edad")int pEdad, @JsonProperty(value = "miembro_comunidad")  boolean pMiembroComunidadUniversitario, @JsonProperty(value = "nombre_cliente")	 String pnombre, @JsonProperty(value = "tipo_cliente")  String ptipo)
	{
		this.id = id2;
		this.edad = pEdad;
		this.miembro = pMiembroComunidadUniversitario;
		this.nombre = pnombre;
		this.tipo = ptipo;
	}
	
	//
	// Getters y Setters
	//
	
	
	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public int getEdad() {
		return edad;
	}


	public void setEdad(int edad) {
		this.edad = edad;
	}


	public boolean isMiembro() {
		return miembro;
	}


	public void setMiembro(boolean miembro) {
		this.miembro = miembro;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public String getTipo() {
		return tipo;
	}


	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public String toString(boolean sisa)
	{
		String rta = "F";
		if(sisa)
			rta = "T";
		return rta;
	}
	
}
