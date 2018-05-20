package datos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class GenerarDatos 
{
	
	
	
	public static String DIRECCION = "C:\\Users\\Andres Losada\\git\\java-server-master\\Docs\\CSVS\\"; 
	
	public static int NUM_HOSTALES = 80;
	public static int NUM_HOTELES = 60;
	public static int NUM_VIVIENDAU = 387;
	public static int NUM_PERSONAS = 20000;
	

	public static int IDS_HOSTALES = 80;
	public static int IDS_HOTELES = 60;
	public static int IDS_VIVIENDAU = 387;
	public static int IDS_PERSONAS = 20000;
	
	private String[] tipoCliente = {"EMPLEADO","PROFESOR","ESTUDIANTE"};
	
	private ArrayList<String> nombresPersonas;
	
	private HashMap<Integer, Integer[]> hashOferta = new HashMap<>();

	private HashMap<Integer, Integer> hashReserva = new HashMap<>();

	
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
	
	
	/**
	 * @throws FileNotFoundException
	 */
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

		for(int pos = 17; pos < 70001;pos++)
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
		File f = new File(DIRECCION + "hostales.csv");
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
		
		
		for(int pos = 45343; pos < 45343 + NUM_HOSTALES;pos++)
		{
			String sisa = nombresHoteles.get(r.nextInt(NUM_HOSTALES));
			if(sisa.length() >= 32)
			{
				sisa = sisa.substring(0, 30);
			}
			sb.append(sisa);
			sb.append(',');
			sb.append(pos);
			sb.append(',');
			sb.append((int)Math.floor(Math.random() * 900));
			sb.append(',');
			int horaI =(int)Math.floor(Math.random() * 13)%13;
			sb.append(horaI + ":" + ((int)Math.floor(Math.random() * (61)))%60);
			sb.append(',');
			int asd = ((int)new Random().nextInt(24-horaI+1) + horaI+1)%24;
			sb.append( asd+ ":" + (int)Math.floor(Math.random() * (61))%60);
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
		sb.append("ID_HOTEL");
		sb.append(',');
		sb.append("CAPACIDAD_HOTEL");
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
		
		
		for(int pos = 10001; pos < 10000+10001;pos++)
		{
			sb.append(nombresHoteles.get(r.nextInt(NUM_HOTELES)));
			sb.append(',');
			sb.append(pos);
			sb.append(',');
			sb.append((int)(Math.random()*800));
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
		
		for(int pos = 312; pos < 20000+311;pos++)
		{
			sb.append(nombresPersonas.get(r.nextInt(NUM_PERSONAS)) + nombresPersonas.get(r.nextInt(NUM_PERSONAS)));
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
		
		PrintWriter pw = new PrintWriter(new File(DIRECCION + "viviendau.csv"));
		StringBuilder sb = new StringBuilder();
		sb.append("ID_VIVIENDAU");
		sb.append(',');
		sb.append("AMOBLAMIENTO");
		sb.append(',');
		sb.append("CAPACIDAD_VIVIENDA");
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
		sb.append('\n');
		
		Random r = new Random();

		for(int pos = 10001; pos < NUM_VIVIENDAU+10000;pos++)
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
			sb.append(r.nextInt(46));
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
			String sisa = nombresHoteles.get(pos%nombresHoteles.size());
			if(sisa.length() >=32)
			{
				sisa = sisa.substring(0, 30);
			}
			sb.append(sisa);
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
	
	public void crearOferta() throws FileNotFoundException
	{
		PrintWriter pw = new PrintWriter(new File(DIRECCION + "oferta.csv"));
		StringBuilder sb = new StringBuilder();
		sb.append("ID_OFERTA");
		sb.append(',');
		sb.append("NUM_RESERVAS");
		sb.append(',');
		sb.append("ID_HOSTAL");
		sb.append(',');
		sb.append("ID_HOTEL");
		sb.append(',');
		sb.append("ID_VIVIENDAU");
		sb.append(',');
		sb.append("ID_PERSONA");
		sb.append(',');
		sb.append("VIGENTE");
		sb.append('\n');
		
		Random r = new Random();

		for(int pos = 104; pos < 100104;pos++)
		{

			Integer[] garu = new Integer[4];
			
			sb.append(pos);
			sb.append(',');
			sb.append(Math.floorDiv((int)Math.random() * 10000, 50));
			sb.append(',');
			boolean ya2 = false;
			int ya = 0;
			if(Math.random() > 0.5 && !ya2)
			{
				int xd = r.nextInt(45343+NUM_HOSTALES-45343)+45343;
				sb.append(xd);
				sb.append(',');
				sb.append(',');
				sb.append(',');
				sb.append(',');
				ya2 = true;
				garu[0] = xd; 
			}
			else if(Math.random() > 0.5 && !ya2)
			{
				sb.append(',');
				int xd = r.nextInt(10001+NUM_HOTELES-10000)+10001;
				sb.append(xd);
				sb.append(',');
				sb.append(',');
				sb.append(',');
				ya2 = true;
				garu[1] = xd;
			}
			else if (Math.random() > 0.5 && !ya2)
			{
				sb.append(',');
				sb.append(',');
				int xd = r.nextInt(10001+NUM_VIVIENDAU-10000)+10001;
				sb.append(xd);
				sb.append(',');
				sb.append(',');
				ya2 = true;
				garu[2] = xd;
			}
			else
			{
				sb.append(',');
				sb.append(',');
				sb.append(',');
				int xd = r.nextInt(311+20000-20000)+20000;
				sb.append(xd);
				sb.append(',');
				garu[3] = xd;
			}
			String bulean = "F";
			if(Math.random() > 0.5)
			{
				bulean = "T";
			}
			sb.append(bulean);
			sb.append('\n');
			hashOferta.put(pos, garu);
		}
		pw.write(sb.toString());
		pw.close();
		System.out.println("ofertas");
	}
	
	
	public void crearReserva() throws FileNotFoundException
	{
		PrintWriter pw = new PrintWriter(new File(DIRECCION + "reserva.csv"));
		StringBuilder sb = new StringBuilder();
		sb.append("ID_OFERTA");
		sb.append(',');
		sb.append("CONFIRMADA");
		sb.append(',');
		sb.append("FECHA");
		sb.append(',');
		sb.append("TIEMPO_CANCELACION");
		sb.append(',');
		sb.append("VALOR");
		sb.append(',');
		sb.append("ID_HOSTAL");
		sb.append(',');
		sb.append("ID_HOTEL");
		sb.append(',');
		sb.append("ID_VIVIENDAU");
		sb.append(',');
		sb.append("ID_PERSONA");
		sb.append(',');
		sb.append("DURACION");
		sb.append(',');
		sb.append("PAGO_ANTICIPADO");
		sb.append('\n');
		
		Random r = new Random();

		for(int pos = 667; pos < 500667;pos++)
		{

			Integer[] garu = new Integer[4];
			
			sb.append(pos);
			sb.append(',');
			String bulean = "F";
			if(Math.random() > 0.5)
			{
				bulean = "T";
			}
			sb.append(bulean);
			sb.append(',');
			
			//Generador de fechas
			
			String fecha ="" +  ((int)(Math.random() *28)) +  "-" + ((int)(Math.random() *12)) + "-2018";
			sb.append(fecha);
			sb.append(',');
			//Generador de horas
			String hora = "" + ((int)(Math.random() *24)) + ":" + ((int)(Math.random() * 60));
			sb.append(hora);
			sb.append(',');
			
			int valor = ((int)(r.nextInt(400000-1000))+1000);
			sb.append(valor);
			sb.append(',');
			
			boolean ya2 = false;
			int ya = 0;
			if(Math.random() > 0.5 && !ya2)
			{
				int xd = r.nextInt(45343+NUM_HOSTALES-45343)+45343;
				sb.append(xd);
				sb.append(',');
				sb.append(',');
				sb.append(',');
				sb.append(',');
				ya2 = true;
				garu[0] = xd; 
			}
			else if(Math.random() > 0.5 && !ya2)
			{
				sb.append(',');
				int xd = r.nextInt(10001+NUM_HOTELES-10000)+10001;
				sb.append(xd);
				sb.append(',');
				sb.append(',');
				sb.append(',');
				ya2 = true;
				garu[1] = xd;
			}
			else if (Math.random() > 0.5 && !ya2)
			{
				sb.append(',');
				sb.append(',');
				int xd = r.nextInt(10001+NUM_VIVIENDAU-10000)+10001;
				sb.append(xd);
				sb.append(',');
				sb.append(',');
				ya2 = true;
				garu[2] = xd;
			}
			else
			{
				sb.append(',');
				sb.append(',');
				sb.append(',');
				int xd = r.nextInt(311+20000-20000)+20000;
				sb.append(xd);
				sb.append(',');
				garu[3] = xd;
			}
			bulean = "F";
			if(Math.random() > 0.5)
			{
				bulean = "T";
			}
			sb.append(bulean);
			sb.append(',');
			
			//duracion
			int duracion = ((int)(Math.random() * 365));
			sb.append(duracion);
			sb.append(',');
			bulean = "F";
			if(Math.random() > 0.5)
			{
				bulean = "T";
			}
			sb.append(bulean);
			sb.append('\n');
		}
		pw.write(sb.toString());
		pw.close();
		System.out.println("reserva");
	}
	
	public void crearClienteReserva() throws FileNotFoundException
	{

		PrintWriter pw = new PrintWriter(new File(DIRECCION + "reservaclientes.csv"));
		StringBuilder sb = new StringBuilder();
		Random r = new Random();
		sb.append("ID_RESERVA");
		sb.append(',');
		sb.append("ID_CLIENTE");
		sb.append('\n');
		for(int pos = 667; pos < 500667;pos++)
		{
			sb.append(pos);
			sb.append(',');
			sb.append(r.nextInt(70001-17)+17);
			sb.append('\n');
		}
		pw.write(sb.toString());
		pw.close();
		System.out.println("reservaclientes");
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		
		GenerarDatos temp = new GenerarDatos();
		temp.cargarNombresPersonas();
		temp.crearCliente();
		temp.crearHostales();
		temp.crearHoteles();
		temp.crearPersona();
		temp.crearViviendaU();
		temp.crearOferta();
		temp.crearReserva();
		temp.crearClienteReserva();
		
//		temp.crearMenu();
//		temp.crearVentaMenu();
	}

}
