package datos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class GenerarDatos 
{
	
	
	
	public static String DIRECCION = "C:\\Users\\Andres Losada\\git\\java-server-master\\Docs\\CSVS\\"; 
	
	private String[] tipoCliente = {"EMPLEADO","PROFESOR","ESTUDIANTE"};
	
	private ArrayList<String> nombresPersonas;
	
	
	public void cargarNombresPersonas()
	{
		FileReader fr = null;
		BufferedReader br = null; 

		nombresPersonas = new ArrayList<>();

		try
		{
			br = new BufferedReader(fr = new FileReader(new File(DIRECCION + "PERSONASNOMBRES.csv" ))); 

			String linea = br.readLine(); 

			while(linea != null)
			{
				String [] partes = linea.split(","); 

				nombresPersonas.add(partes[1].replace("\"", "")); 



				linea = br.readLine(); 
			}



		}catch(Exception e )
		{
			e.printStackTrace();


		}finally
		{
			try {
				fr.close(); 
			}catch(Exception e2)
			{
				e2.printStackTrace();
			}
		}

	}
	
	
	public void crearCliente() throws FileNotFoundException
	{
		File f = new File(DIRECCION + "clientes.csv");
		f.getParentFile().mkdirs();
		PrintWriter pw = new PrintWriter(f);
		StringBuilder sb = new StringBuilder();
		
		Random r = new Random();
		
		sb.append("NOMBRE_CLIENTE");
		sb.append(',');
		sb.append("TIPO_CLIENTE");
		sb.append(',');
		sb.append("ID_CLIENTE");
		sb.append(',');
		sb.append("EDAD");
		sb.append(',');
		sb.append("MIEMBRO_COMUNIDAD");
		sb.append('\n');

		for(int pos = 1; pos < 70000;pos++)
		{
			sb.append(nombresPersonas.get(r.nextInt(nombresPersonas.size())) + " " + nombresPersonas.get(r.nextInt(nombresPersonas.size())));
			sb.append(',');
			sb.append(tipoCliente[(int)Math.floor((Math.random() * 3))]);
			sb.append(',');
			sb.append(pos);
			sb.append(',');
			sb.append((int)(new Random()).nextInt(100-18) + 18);
			sb.append(',');
			
			String miembro = "F";
			if(Math.random() >= 0.5)
			{
				miembro = "T";
			}
			
			sb.append(miembro);
			sb.append('\n');
		}

		pw.write(sb.toString());
		pw.close();
		System.out.println("clientes");
	}

	public void crearHostales() throws FileNotFoundException
	{
		File f = new File(DIRECCION + "hostal.csv");
		f.getParentFile().mkdirs();
		PrintWriter pw = new PrintWriter(f);
		StringBuilder sb = new StringBuilder();
		
		Random r = new Random();
		
		sb.append("NOMBRE");
		sb.append(',');
		sb.append("ID_HOSTAL");
		sb.append(',');
		sb.append("CAPACIDAD_HOSTAL");
		sb.append(',');
		sb.append("HORA_APERTURA");
		sb.append(',');
		sb.append("HORA_CIERRA");
		sb.append(',');
		sb.append("RECEPCION24_HORAS");
		sb.append(',');
		sb.append("CAMARA_COMERCIO");
		sb.append(',');
		sb.append("SUPERINTENDENCIA_TURISMO");
		sb.append('\n');

		
		FileReader fr = null;
		BufferedReader br = null; 

		ArrayList<String >nombresHoteles = new ArrayList<>();

		try
		{
			br = new BufferedReader(fr = new FileReader(new File(DIRECCION + "hostales.txt" ))); 

			String linea = br.readLine(); 

			while(linea != null)
			{ 	
				nombresHoteles.add(linea); 
				linea = br.readLine(); 

			}
		}catch(Exception e )
		{
			e.printStackTrace();


		}finally
		{
			try {
				fr.close(); 
			}catch(Exception e2)
			{
				e2.printStackTrace();
			}
		}
		
		
		for(int pos = 1; pos < nombresHoteles.size();pos++)
		{
			sb.append(nombresHoteles.get(r.nextInt(nombresHoteles.size())));
			sb.append(',');
			sb.append(pos);
			sb.append(',');
			sb.append((int)Math.floor(Math.random() * 900));
			sb.append(',');
			int horaI =(int)Math.floor(Math.random() * 13)%12;
			sb.append(horaI + ":" + ((int)Math.floor(Math.random() * 61))%60);
			sb.append(',');
			int asd = ((int)new Random().nextInt(24-horaI+1) + horaI+1)%24;
			sb.append( asd+ ":" + (int)Math.floor(Math.random() * 61)%60);
			sb.append(',');
			sb.append((int)(new Random()).nextInt(100-18) + 18);
			sb.append(',');
			String miembro = "F";
			if(Math.random() >= 0.5)
			{
				miembro = "T";
			}
			sb.append(miembro);
			sb.append(',');
			miembro = "F";
			if(Math.random() >= 0.5)
			{
				miembro = "T";
			}
			sb.append(miembro);
			sb.append(',');
			 miembro = "F";
			if(Math.random() >= 0.5)
			{
				miembro = "T";
			}
			sb.append(miembro);
			sb.append('\n');
		}

		pw.write(sb.toString());
		pw.close();
		System.out.println("hostales");
	}
	
	public void crearHoteles() throws FileNotFoundException
	{
		File f = new File(DIRECCION + "hotel.csv");
		f.getParentFile().mkdirs();
		PrintWriter pw = new PrintWriter(f);
		StringBuilder sb = new StringBuilder();
		
		Random r = new Random();
		
		sb.append("NOMBRE");
		sb.append(',');
		sb.append("ID_HOSTAL");
		sb.append(',');
		sb.append("CAMARA_COMERCIO");
		sb.append(',');
		sb.append("SUPERINTENDENCIA_TURISMO");
		sb.append('\n');

		
		FileReader fr = null;
		BufferedReader br = null; 

		ArrayList<String >nombresHoteles = new ArrayList<>();

		try
		{
			br = new BufferedReader(fr = new FileReader(new File(DIRECCION + "hoteles.txt" ))); 

			String linea = br.readLine(); 

			while(linea != null)
			{ 	
				nombresHoteles.add(linea); 
				linea = br.readLine(); 

			}
		}catch(Exception e )
		{
			e.printStackTrace();


		}finally
		{
			try {
				fr.close(); 
			}catch(Exception e2)
			{
				e2.printStackTrace();
			}
		}
		
		
		for(int pos = 1; pos < nombresHoteles.size();pos++)
		{
			sb.append(nombresHoteles.get(r.nextInt(nombresHoteles.size())));
			sb.append(',');
			sb.append(pos);
			sb.append(',');
			sb.append((int)Math.floor(Math.random() * 900));
			sb.append(',');
			int horaI =(int)Math.floor(Math.random() * 13)%12;
			sb.append(horaI + ":" + ((int)Math.floor(Math.random() * 61))%60);
			sb.append(',');
			int asd = ((int)new Random().nextInt(24-horaI+1) + horaI+1)%24;
			sb.append( asd+ ":" + (int)Math.floor(Math.random() * 61)%60);
			sb.append(',');
			sb.append((int)(new Random()).nextInt(100-18) + 18);
			sb.append(',');
			String miembro = "F";
			if(Math.random() >= 0.5)
			{
				miembro = "T";
			}
			sb.append(miembro);
			sb.append(',');
			miembro = "F";
			if(Math.random() >= 0.5)
			{
				miembro = "T";
			}
			sb.append(miembro);
			sb.append(',');
			 miembro = "F";
			if(Math.random() >= 0.5)
			{
				miembro = "T";
			}
			sb.append(miembro);
			sb.append('\n');
		}

		pw.write(sb.toString());
		pw.close();
		System.out.println("hoteles");
	}
	
	public void crearPersona() throws FileNotFoundException
	{
		File f = new File(DIRECCION + "persona.csv");
		f.getParentFile().mkdirs();
		PrintWriter pw = new PrintWriter(f);
		StringBuilder sb = new StringBuilder();
		
		Random r = new Random();
		
		sb.append("NOMBRE");
		sb.append(',');
		sb.append("ID_PERSONA");
		sb.append(',');
		sb.append("EDAD");
		sb.append(',');
		sb.append("CAMARA_COMERCIO");
		sb.append(',');
		sb.append("SUPERINTENDENCIA_TURISMO");
		sb.append(',');
		sb.append("MIEMBRO_COMUNIDAD");
		sb.append('\n');
		
		for(int pos = 1; pos < 20000;pos++)
		{
			sb.append(nombresPersonas.get(r.nextInt(nombresPersonas.size())) + nombresPersonas.get(r.nextInt(nombresPersonas.size())));
			sb.append(',');
			sb.append(pos);
			sb.append(',');
			sb.append((int)(new Random()).nextInt(100-18) + 18);
			sb.append(',');
			String miembro = "F";
			if(Math.random() >= 0.5)
			{
				miembro = "T";
			}
			sb.append(miembro);
			sb.append(',');
			miembro = "F";
			if(Math.random() >= 0.5)
			{
				miembro = "T";
			}
			sb.append(miembro);
			sb.append(',');
			 miembro = "F";
			if(Math.random() >= 0.5)
			{
				miembro = "T";
			}
			sb.append(miembro);
			sb.append('\n');
		}

		pw.write(sb.toString());
		pw.close();
		System.out.println("persona");
	}
	
	public void crearViviendaU() throws FileNotFoundException
	{
		
		FileReader fr = null;
		BufferedReader br = null; 

		ArrayList<String >nombresHoteles = new ArrayList<>();

		try
		{
			br = new BufferedReader(fr = new FileReader(new File(DIRECCION + "viviendasu.txt" ))); 

			String linea = br.readLine(); 

			while(linea != null)
			{ 	
				nombresHoteles.add(linea); 
				linea = br.readLine(); 

			}
		}catch(Exception e )
		{
			e.printStackTrace();


		}finally
		{
			try {
				fr.close(); 
			}catch(Exception e2)
			{
				e2.printStackTrace();
			}
		}
		
		PrintWriter pw = new PrintWriter(new File("viviendau.csv"));
		StringBuilder sb = new StringBuilder();
		sb.append("ID_VIVIENDAU");
		sb.append(',');
		sb.append("AMOBLAMIENTO");
		sb.append(',');
		sb.append("DURACION_SERVICIO");
		sb.append(',');
		sb.append("CAMARA_COMERCIO");
		sb.append(',');
		sb.append("SUPERINTENDENCIA_TURISMO");
		sb.append(',');
		sb.append("NOMBRE");
		sb.append(',');
		sb.append("PORTERIA24_HORAS");
		sb.append(',');
		sb.append('\n');
		
		Random r = new Random();

		for(int pos = 1; pos < 10000;pos++)
		{
			sb.append(pos);
			sb.append(',');
			String amoblamiento = "F";
			if(Math.random() > 0.5)
			{
				amoblamiento = "T";
			}
			sb.append(amoblamiento);
			sb.append(',');
			sb.append((int)Math.floor(Math.random()) * 501);
			sb.append(',');
			sb.append(r.nextInt(365-1)+1);
			sb.append(',');
			amoblamiento = "F";
			if(Math.random() > 0.5)
			{
				amoblamiento = "T";
			}
			sb.append(amoblamiento);
			sb.append(',');
			amoblamiento = "F";
			if(Math.random() > 0.5)
			{
				amoblamiento = "T";
			}
			sb.append(amoblamiento);
			sb.append(',');
			sb.append(nombresHoteles.get(pos));
			sb.append(',');
			amoblamiento = "F";
			if(Math.random() > 0.5)
			{
				amoblamiento = "T";
			}
			sb.append(amoblamiento);
			sb.append('\n');
		}

		pw.write(sb.toString());
		pw.close();
		System.out.println("viviendau");
	}
	
	public void crearVentaMenu() throws FileNotFoundException
	{
		PrintWriter pw = new PrintWriter(new File("ventaMenu.csv"));
		StringBuilder sb = new StringBuilder();
		sb.append("IDVENTA");
		sb.append(',');
		sb.append("IDMENU");
		sb.append(',');
		sb.append("CANTIDAD");
		sb.append(',');
		sb.append("IDRESTAURANTE");
		sb.append(',');
		sb.append("PRECIO");
		sb.append(',');
		sb.append("CAMBIOFUERTE");
		sb.append(',');
		sb.append("CAMBIOPOSTRE");
		sb.append(',');
		sb.append("CAMBIOENTRADA");
		sb.append(',');
		sb.append("CAMBIOBEBIDA");
		sb.append(',');
		sb.append("CAMBIOACOMPAÑAMIENTO");
		sb.append('\n');

		for(int pos = 500000; pos < 1000000;pos++)
		{
			sb.append(pos);
			sb.append(',');
			int idmen = ThreadLocalRandom.current().nextInt(1, 999999 + 1);
			sb.append(idmen);
			sb.append(',');
			sb.append(1);
			sb.append(',');
			int rest = ThreadLocalRandom.current().nextInt(1, 999999 + 1);
			sb.append(rest);
			sb.append(',');
			int precio = ThreadLocalRandom.current().nextInt(20000, 40000 + 1);
			sb.append(precio);
			sb.append(',');
			sb.append("");
			sb.append(',');
			sb.append("");
			sb.append(',');
			sb.append("");
			sb.append(',');
			sb.append("");
			sb.append(',');
			sb.append("");
			sb.append('\n');
		}
		
		pw.write(sb.toString());
		pw.close();
		System.out.println("ventaMenu");
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		
		GenerarDatos temp = new GenerarDatos();
		temp.cargarNombresPersonas();
		temp.crearCliente();
		temp.crearHostales();
		temp.crearHoteles();
		temp.crearPersona();
//		temp.crearVentaPlato();
//		temp.crearMenu();
//		temp.crearVentaMenu();
	}

}
