package datos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.plaf.ActionMapUIResource;

public class GenerarDatos 
{

//"C:\\Users\\Andres Losada\\git\\java-server-master\\Docs\\CSVS\\"
	
	public static String DIRECCION = "C:\\Users\\Andres Losada\\git\\java-server-master\\Docs\\CSVS\\"; 

	public static Integer NUM_HOSTALES = 80;
	public static Integer NUM_HOTELES = 60;
	public static Integer NUM_VIVIENDAU = 387;
	public static Integer NUM_PERSONAS = 20000;


	public static Integer IDS_HOSTALES = 80;
	public static Integer IDS_HOTELES = 60;
	public static Integer IDS_VIVIENDAU = 387;
	public static Integer IDS_PERSONAS = 20000;

	private String[] tipoCliente = {"EMPLEADO","PROFESOR","ESTUDIANTE"};

	private String[] tipoHabitacion = {"SEMISUITE","ESTANDAR","DOBLE","SUITE"};

	private String[] ubicacion = {"VISTA A LA CALLE","ULTIMO PISO","SEGUNDO PISO","ULTIMO PISO", "VISTA AL JARDIN"};
	
	private String[] carSeguro= {"COVERTURA PARCIAL", "COVERTURA TOTAL"}; //TAM2
	
	private String[] carVivienda = {"COLONIAL", "VISTA A LA MONTAÑA", "GRANDE", "ACABADOS EN MADERA","ESPACIOS AMPLIOS", "CERCA AL TRANSPORTE PUBLICO"}; //TAM6
	
	private String[] location= {"NORTE", "SUR", "ESTE", "OESTE"};//TAM4

	private ArrayList<String> nombresPersonas;

	private HashMap<Integer, Integer[]> hashOferta = new HashMap<Integer,Integer[]>();

	private HashMap<Integer, Integer[]> hashReserva = new HashMap<Integer,Integer[]>();

	private HashMap<Integer, Integer> hashPersona = new HashMap<Integer,Integer>();

	//Hash para cada proveedor y su oferta


	private Map<Integer, Integer> hashHotelOferta = new HashMap<Integer,Integer>();
	private Map<Integer, Integer> hashHostalOferta = new HashMap<Integer,Integer>();
	private Map<Integer, Integer> hashPersonaOferta = new HashMap<Integer,Integer>();
	private Map<Integer, Integer> hashViviendaUOferta = new HashMap<Integer,Integer>();
	private Map<Integer, Integer> hashHotelOferta2 = new HashMap<Integer,Integer>();
	private Map<Integer, Integer> hashHostalOferta2 = new HashMap<Integer,Integer>();
	private Map<Integer, Integer> hashPersonaOferta2 = new HashMap<Integer,Integer>();
	private Map<Integer, Integer> hashViviendaUOferta2 = new HashMap<Integer,Integer>();


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

		for(Integer pos = 17; pos < 70001;pos++)
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


		for(Integer pos = 45343; pos < 45343 + NUM_HOSTALES;pos++)
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
			Integer horaI =(int)Math.floor(Math.random() * 13)%13;
			sb.append(horaI + ":" + ((int)Math.floor(Math.random() * (61)))%60);
			sb.append(',');
			Integer asd = ((int)new Random().nextInt(24-horaI+1) + horaI+1)%24;
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


		for(Integer pos = 10001; pos < 10000+10001;pos++)
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

		for(Integer pos = 312; pos < 20000+311;pos++)
		{
			sb.append(nombresPersonas.get(r.nextInt(NUM_PERSONAS)) + nombresPersonas.get(r.nextInt(NUM_PERSONAS)));
			sb.append(',');
			sb.append(pos);
			sb.append(',');
			sb.append((Integer)(new Random()).nextInt(100-18) + 18);
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

		for(Integer pos = 10001; pos < NUM_VIVIENDAU+10000;pos++)
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

		for(Integer pos = 104; pos < 100104;pos++)
		{
			Integer[] garu = new Integer[4];

			sb.append(pos);
			sb.append(',');
			sb.append(Math.floorDiv((int)Math.random() * 10000, 50));
			sb.append(',');
			boolean ya2 = false;
			Integer ya = 0;
			if(Math.random() > 0.5 && !ya2)
			{
				Integer xd = r.nextInt(45422-45344)+45343;
				sb.append(xd);
				sb.append(',');
				sb.append(',');
				sb.append(',');
				sb.append(',');
				ya2 = true;
				garu[0] = xd;
				hashHostalOferta.put(xd, pos);
				hashHostalOferta2.put(pos,xd);
			}
			else if(Math.random() > 0.5 && !ya2)
			{
				sb.append(',');
				Integer xd = r.nextInt(20000-10002)+10001;
				sb.append(xd);
				sb.append(',');
				sb.append(',');
				sb.append(',');
				ya2 = true;
				garu[1] = xd;
				hashHotelOferta.put(xd, pos);
				hashHotelOferta2.put(pos,xd);			
			}
			else if (Math.random() > 0.5 && !ya2)
			{
				sb.append(',');
				sb.append(',');
				Integer xd = r.nextInt(10386-10002)+10001;
				sb.append(xd);
				sb.append(',');
				sb.append(',');
				ya2 = true;
				garu[2] = xd;
				hashViviendaUOferta.put(xd, pos);
				hashViviendaUOferta2.put(pos,xd);
			}
			else
			{
				sb.append(',');
				sb.append(',');
				sb.append(',');
				Integer xd = r.nextInt(20310-313)+312;
				sb.append(xd);
				sb.append(',');
				garu[3] = xd;
				hashPersonaOferta.put(xd, pos);
				hashPersonaOferta2.put(pos,xd);
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


	public void crearHabitacion() throws FileNotFoundException
	{
		PrintWriter pw = new PrintWriter(new File(DIRECCION + "habitacion.csv"));
		StringBuilder sb = new StringBuilder();
		sb.append("ID_HABITACION");
		sb.append(',');
		sb.append("CAPACIDAD_HABITACION");
		sb.append(',');
		sb.append("PRECIO");
		sb.append(',');
		sb.append("TAMANIO");
		sb.append(',');
		sb.append("TIPO");
		sb.append(',');
		sb.append("UBICACION");
		sb.append(',');
		sb.append("ID_RESERVA");
		sb.append(',');
		sb.append("ID_OFERTA");
		sb.append(',');
		sb.append("ID_HOTEL");
		sb.append(',');
		sb.append("ID_HOSTAL");
		sb.append(',');
		sb.append("ID_PERSONA");
		sb.append(',');
		sb.append("ID_VIVIENDAU");
		sb.append('\n');

		Random r = new Random();

		//Revisar estos numeros
		for(Integer pos = 104; pos < 600104;pos++)
		{
			sb.append(pos);
			sb.append(',');
			//capacidad de la habitacion
			sb.append(r.nextInt((6-1)+1)+ 1);
			sb.append(',');
			//Precio de la habitacion
			Integer valor = ((Integer)(r.nextInt(600104-10001))+10000);
			sb.append(valor);
			sb.append(',');
			//Tamanio de la habitacion
			sb.append(r.nextInt((12-3)+1)+ 3);
			sb.append(',');
			//Tipo
			sb.append(ubicacion[(int)Math.floor((Math.random() * 4))]);
			sb.append(',');
			//Ubicacion
			sb.append(ubicacion[(int)Math.floor((Math.random() * 5))]);
			sb.append(',');


			//oferta y sus alojamientos

			//generar un numero aleatorio que me de el id de la oferta
			//Sacar la oferta del hash y encontrar sus alojamientos
			//Pero al revez




			//identificar el id de la reserva

			Integer idReserva = r.nextInt(500667-667) +667;
			sb.append(idReserva);
			sb.append(',');
			Integer[] proveedores = hashReserva.get(idReserva);
			Integer tipo = 0;
			Integer id = 0;
			for (Integer i = 0; i < proveedores.length; i++) 
			{
				if(proveedores[i] != null)
				{
					tipo = i;
					id = proveedores[i];
				}
			}
			if(tipo == 0)
			{
				Integer xd = id;
	//			System.out.println(hashHostalOferta.containsKey(xd));;
				Integer kool = hashHostalOferta.get(xd);
				if(kool == null)
				{
					kool = r.nextInt(100104-105)+104;
				}
				sb.append(kool);
				sb.append(',');
				sb.append(xd);
				sb.append(',');
				//2
				sb.append(',');
				//3
				sb.append(',');
				//4
				sb.append('\n');
			}
			else if(tipo == 1)
			{
				Integer xd = id;
		//		System.out.println(hashHotelOferta.containsKey(xd));
				Integer kool = hashHotelOferta.get(xd);
				if(kool == null)
				{
					kool = r.nextInt(100104-105)+104;
				}
				sb.append(kool);
				sb.append(',');
				//1
				sb.append(',');
				sb.append(xd);
				sb.append(',');
				//3
				sb.append(',');
				//4
				sb.append('\n');
			}
			else if(tipo == 2)
			{
				Integer xd = id;
			//	System.out.println(hashPersonaOferta.containsKey(xd));
				Integer kool = hashPersonaOferta.get(xd);
				if(kool == null)
				{
					kool = r.nextInt(100104-105)+104;
				}
				sb.append(kool);
				sb.append(',');
				//1
				sb.append(',');
				//2
				sb.append(',');
				sb.append(xd);
				sb.append(',');
				//4
				sb.append('\n');
			}
			else
			{
				Integer xd = id;
			//	System.out.println(hashViviendaUOferta.containsKey(xd));
				Integer kool =hashViviendaUOferta.get(xd);
				if(kool == null)
				{
					kool = r.nextInt(100104-105)+104;
				}
				sb.append(kool);
				sb.append(',');
				sb.append(',');
				sb.append(',');
				sb.append(',');
				sb.append(xd);
				sb.append('\n');
			}
		}
		pw.write(sb.toString());
		pw.close();
		System.out.println("habitacion");
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

		Collection<Integer> jasgasgo1 = hashHostalOferta2.values();
		Iterator<Integer> it1 = jasgasgo1.iterator();
		Collection<Integer> jasgasgo2 = hashHotelOferta2.values();
		Iterator<Integer> it2 = jasgasgo2.iterator();
		Collection<Integer> jasgasgo3 = hashViviendaUOferta2.values();
		Iterator<Integer> it3 = jasgasgo3.iterator();
		Collection<Integer> jasgasgo4 = hashPersonaOferta2.values();
		Iterator<Integer> it4 = jasgasgo4.iterator();

		for(Integer pos = 667; pos < 500667;pos++)
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

			String fecha ="0" +  ((int)(Math.random() *28)) +  "-0" + ((int)(Math.random() *12)) + "-2018";
			sb.append(fecha);
			sb.append(',');
			//Generador de horas
			String hora = "" + ((int)(Math.random() *24)) + ":" + ((int)(Math.random() * 60));
			sb.append(hora);
			sb.append(',');

			Integer valor = ((Integer)(r.nextInt(400000-1000))+1000);
			sb.append(valor);
			sb.append(',');

			boolean ya2 = false;
			Integer ya = 0;
			if(Math.random() <= 0.25 && !ya2)
			{
				boolean ya3 = false;
				Integer xd = 0;
				if(!it1.hasNext())
				{
					it1 = jasgasgo1.iterator();
				}
				xd = it1.next();
				sb.append(xd);
				sb.append(',');
				sb.append(',');
				sb.append(',');
				sb.append(',');
				ya2 = true;
				garu[0] = xd; 
			}
			else if(Math.random() <= 0.25 && !ya2)
			{
				sb.append(',');
				Integer xd = 0;
				if(!it2.hasNext())
				{
					it2 = jasgasgo2.iterator();
				}
				xd = it2.next();
				sb.append(xd);
				sb.append(',');
				sb.append(',');
				sb.append(',');
				ya2 = true;
				garu[1] = xd;
			}
			else if (Math.random() <= 0.25 && !ya2)
			{
				sb.append(',');
				sb.append(',');
				Integer xd = 0;
				if(!it3.hasNext())
				{
					it3 = jasgasgo3.iterator();
				}
				xd = it3.next();
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
				Integer xd = 0;
				if(!it4.hasNext())
				{
					it4 = jasgasgo4.iterator();
				}
				xd = it4.next();
				sb.append(xd);
				sb.append(',');
				garu[3] = xd;
			}
			//duracion
			Integer duracion = ((int)(Math.random() * 365));
			sb.append(duracion);
			sb.append(',');
			bulean = "F";
			if(Math.random() > 0.5)
			{
				bulean = "T";
			}
			sb.append(bulean);
			sb.append('\n');
			hashReserva.put(pos, garu);
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
		for(Integer pos = 667; pos < 500667;pos++)
		{
			sb.append(pos);
			sb.append(',');
			sb.append((int)(Math.random() *70001));
			sb.append('\n');
		}
		pw.write(sb.toString());
		pw.close();
		System.out.println("reservaclientes");
	}

	public void crearApartamento() throws FileNotFoundException
	{

		PrintWriter pw = new PrintWriter(new File(DIRECCION + "apartamento.csv"));
		StringBuilder sb = new StringBuilder();
		Random r = new Random();
		sb.append("ID_APARTAMENTO");
		sb.append(',');
		sb.append("AMOBLADO");
		sb.append(',');
		sb.append("CAPACIDAD_APTO");
		sb.append(',');
		sb.append("PRECIO_APTO");
		sb.append(',');
		sb.append("ID_PERSONA");
		sb.append(',');
		sb.append("ID_OFERTA");
		sb.append(',');
		sb.append("INCLUYE_SERVICIOS");
		sb.append('\n');

		System.out.println(hashPersonaOferta.size());


		Iterator<Integer> it1 = hashPersonaOferta.keySet().iterator();
		Iterator<Integer> it2 = hashPersonaOferta.values().iterator();
		for(Integer pos = 124; pos < 100125;pos++)
		{
			sb.append(pos);
			sb.append(',');
			String bulean = "F";
			if(Math.random() > 0.5)
			{
				bulean = "T";
			}
			sb.append(bulean);
			sb.append(',');
			Integer capacidad = r.nextInt(10-1)+10;
			sb.append(capacidad);
			sb.append(',');
			Integer precio = (r.nextInt(1000-1)+1)*1000;
			sb.append(precio);
			sb.append(',');
			Integer persona = 0;
			Integer oferta = 0;
			if(it1.hasNext())
			{
				persona = it1.next();
			}
			else
			{
				it1 = hashPersonaOferta.keySet().iterator();
				persona = it1.next();
			}
			if(it2.hasNext())
			{
				oferta = it2.next();
			}
			else
			{
				it2 = hashPersonaOferta.values().iterator();
				oferta = it2.next();
			}
			sb.append(persona);
			sb.append(',');
			sb.append(oferta);
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
		System.out.println("apartamento");
	}
	
	public void crearHabitacionesServiciosPublicos() throws FileNotFoundException
	{

		PrintWriter pw = new PrintWriter(new File(DIRECCION + "habitacionesserviciospublicos.csv"));
		StringBuilder sb = new StringBuilder();
		Random r = new Random();
		sb.append("ID_HABITACION");
		sb.append(',');
		sb.append("ID_SERVICIO_PUBLICO");
		sb.append('\n');
		for(Integer pos =104; pos < 600104;pos++)
		{
			sb.append(pos);
			sb.append(',');
			sb.append(r.nextInt(54-45)+45);
			sb.append('\n');
		}
		pw.write(sb.toString());
		pw.close();
		System.out.println("habitacionesserviciospublicos");
	}

	
	public void crearHabitacionesServiciosInmobiliarios() throws FileNotFoundException
	{

		PrintWriter pw = new PrintWriter(new File(DIRECCION + "habitacionesserviciosinmobiliarios.csv"));
		StringBuilder sb = new StringBuilder();
		Random r = new Random();
		sb.append("ID_HABITACION");
		sb.append(',');
		sb.append("ID_SERVICIO_INMOBILIARIO");
		sb.append('\n');
		for(Integer pos =104; pos < 600104;pos++)
		{
			sb.append(pos);
			sb.append(',');
			sb.append(r.nextInt(58-41)+41);
			sb.append('\n');
		}
		pw.write(sb.toString());
		pw.close();
		System.out.println("habitacionesserviciosinmobiliarios");
	}

	
	public void crearVecino() throws FileNotFoundException
	{

		System.out.println("creando los vecinos");
		PrintWriter pw = new PrintWriter(new File(DIRECCION + "vecino.csv"));
		StringBuilder sb = new StringBuilder();
		Random r = new Random();
		sb.append("ID_VECINO");
		sb.append(',');
		sb.append("ID_PERSONA");
		sb.append('\n');
		

		for(int pos = 312; pos < 20000+311;pos++)
		{
			sb.append(pos);
			sb.append(',');
			sb.append(pos);
			sb.append('\n');
			
		}
		pw.write(sb.toString());
		pw.close();
		System.out.println("vecino");
	}
	
	public void crearVivienda() throws FileNotFoundException
	{
		File f = new File(DIRECCION + "vivienda.csv");
		f.getParentFile().mkdirs();
		PrintWriter pw = new PrintWriter(f);
		StringBuilder sb = new StringBuilder();

		Random r = new Random();

		sb.append("ID_VIVIENDA");
		sb.append(',');
		sb.append("CARACTERISTICAS_SEGURO");
		sb.append(',');
		sb.append("CARACTERISTICAS_VIVIENDA");
		sb.append(',');
		sb.append("PRECIO_VIVIENDA");
		sb.append(',');
		sb.append("ID_VECINO");
		sb.append(',');
		sb.append("CAPACIDAD");
		sb.append(',');
		sb.append("UBICACION");
		sb.append(',');
		sb.append("TIEMPO_USO");
		sb.append(',');
		sb.append('\n');

		for(int pos = 312; pos < 20000+311;pos++)
		{
			sb.append(pos);
			sb.append(',');
			sb.append(carSeguro[(int)Math.floor((Math.random() * 2))]);
			sb.append(',');
			sb.append(carVivienda[(int)Math.floor((Math.random() * 6))]);
			sb.append(',');
			Integer valor = ((Integer)(r.nextInt(700000-100000))+100000);
			sb.append(valor);
			sb.append(',');
			sb.append(pos);
			sb.append(',');
			//capacidad de la vivienda
			sb.append(r.nextInt((18-1)+1)+ 1);
			sb.append(',');
			sb.append(location[(int)Math.floor((Math.random() * 4))]);
			sb.append(',');
			//tiempo de uso de la vivienda
			sb.append(r.nextInt((50-1)+1)+ 1);
			sb.append(',');
			sb.append('\n');
		}

		pw.write(sb.toString());
		pw.close();
		System.out.println("vivienda");
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
		temp.crearHabitacion();
		temp.crearApartamento();
		temp.crearHabitacionesServiciosInmobiliarios();
		temp.crearHabitacionesServiciosPublicos();

	}

}
