package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Oferta;
import vos.VOClienteDuracion;
import vos.VOClienteReservas;
import vos.VODisponible;
import vos.VOExtraHotel;
import vos.VOExtraPersona;
import vos.VOExtraViviendaUniversitaria;
import vos.VOFechaDemanda;
import vos.VOFechaIngresos;
import vos.VOHostalExtra;
import vos.VOIndiceHostal;
import vos.VOIndiceHotel;
import vos.VOIndicePersona;
import vos.VOIndiceVivendaU;
import vos.VOUsoCliente;
import vos.VOUsoEspecificoCliente;
import vos.VOUsoHostal;
import vos.VOUsoHotel;
import vos.VOUsoPersona;
import vos.VOUsoVivienda;

public class DAORequerimientosenSQL 
{
	//----------------------------------------------------------------------------------------------------------------------------------
	// CONSTANTES
	//----------------------------------------------------------------------------------------------------------------------------------

	/**
	 * Constante para indicar el usuario Oracle del estudiante
	 */
	//TODO Requerimiento 1H: Modifique la constante, reemplazando al ususario PARRANDEROS por su ususario de Oracle
	public final static String USUARIO = "ISIS2304A811810";

	//----------------------------------------------------------------------------------------------------------------------------------
	// ATRIBUTOS
	//----------------------------------------------------------------------------------------------------------------------------------

	/**
	 * Arraylits de recursos que se usan para la ejecucion de sentencias SQL
	 */
	private ArrayList<Object> recursos;

	/**
	 * Atributo que genera la conexion a la base de datos
	 */
	private Connection conn;

	public DAORequerimientosenSQL() {
		recursos = new ArrayList<Object>();
	}
	//---------------------------------------------------------------------------------
	//Garantizar la transaccionalidad
	//---------------------------------------------------------------------------------

	public void setTransaccionalidad() throws SQLException, Exception{
		String sql = String.format("SET AUTOCOMMIT O SET TRANSACTION ISOLATION LEVEL SERIALIZABLE", USUARIO);
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	//---------------------------------------------------------------------------------
	//RC1--20 Mostrar el dinero recibido por cada operador
	//---------------------------------------------------------------------------------
	public ArrayList<VOHostalExtra> getDineroHostal() throws SQLException, Exception {
		ArrayList<VOHostalExtra> habitacions = new ArrayList<VOHostalExtra>();

		//Aclaracion: Por simplicidad, solamente se obtienen los primeros 50 resultados de la consulta
		String sql = String.format(	"SELECT ID_HOSTAL, VALOR FROM RESERVA WHERE ID_HOSTAL IS NOT NULL AND CONFIRMADA = 'T'", USUARIO);
		String sql1 = String.format("COMMIT",USUARIO);
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		PreparedStatement prepStm = conn.prepareStatement(sql1);
		recursos.add(prepStmt);
		recursos.add(prepStm);

		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			habitacions.add(convertResultSetToVOExtra(rs));
		}

		prepStm.executeQuery();
		return habitacions;
	}



	public ArrayList<VOExtraHotel> getDineroHotel() throws SQLException, Exception {
		ArrayList<VOExtraHotel> habitacions = new ArrayList<VOExtraHotel>();

		//Aclaracion: Por simplicidad, solamente se obtienen los primeros 50 resultados de la consulta
		String sql = String.format(	"SELECT ID_HOSTAL, VALOR FROM RESERVA WHERE ID_HOSTAL IS NOT NULL AND CONFIRMADA = 'T'", USUARIO);
		String sql1 = String.format("COMMIT",USUARIO);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		PreparedStatement prepStm = conn.prepareStatement(sql1);
		recursos.add(prepStmt);
		recursos.add(prepStm);

		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			habitacions.add(convertResultSetToExtraHotel(rs));
		}

		prepStm.executeQuery();
		return habitacions;
	}

	public ArrayList<VOExtraPersona> getDineroPersona() throws SQLException, Exception {
		ArrayList<VOExtraPersona> habitacions = new ArrayList<VOExtraPersona>();

		//Aclaracion: Por simplicidad, solamente se obtienen los primeros 50 resultados de la consulta
		String sql = String.format(	"SELECT ID_PERSONA, VALOR FROM RESERVA WHERE ID_PERSONA IS NOT NULL AND CONFIRMADA = 'T'", USUARIO);
		String sql1 = String.format("COMMIT",USUARIO);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		PreparedStatement prepStm = conn.prepareStatement(sql1);
		recursos.add(prepStmt);
		recursos.add(prepStm);



		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			habitacions.add(convertResultSetToExtraPersona(rs));
		}
		prepStm.executeQuery();
		return habitacions;
	}


	public ArrayList<VOExtraViviendaUniversitaria> getDineroViviendaUniversitaria() throws SQLException, Exception {
		ArrayList<VOExtraViviendaUniversitaria> habitacions = new ArrayList<VOExtraViviendaUniversitaria>();

		//Aclaracion: Por simplicidad, solamente se obtienen los primeros 50 resultados de la consulta
		String sql = String.format(	"SELECT ID_PERSONA, VALOR FROM RESERVA WHERE ID_PERSONA IS NOT NULL AND CONFIRMADA = 'T'", USUARIO);
		String sql1 = String.format("COMMIT",USUARIO);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		PreparedStatement prepStm = conn.prepareStatement(sql1);
		recursos.add(prepStmt);
		recursos.add(prepStm);

		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			habitacions.add(convertResultSetToExtraViviendaUniversitaria(rs));
		}
		prepStm.executeQuery();
		return habitacions;
	}


	//---------------------------------------------------------------------------------
	//RC2--20 Ofertas más reservadas
	//---------------------------------------------------------------------------------

	public ArrayList<Oferta> get20Ofertas() throws SQLException, Exception {
		ArrayList<Oferta> habitacions = new ArrayList<Oferta>();

		//Aclaracion: Por simplicidad, solamente se obtienen los primeros 50 resultados de la consulta
		String sql = String.format(	"SELECT * FROM (SELECT * FROM OFERTA ORDER BY NUM_RESERVAS DESC) WHERE ROWNUM <= 20", USUARIO);
		String sql1 = String.format("COMMIT",USUARIO);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		PreparedStatement prepStm = conn.prepareStatement(sql1);
		recursos.add(prepStmt);
		recursos.add(prepStm);

		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			habitacions.add(convertResultSetToOferta(rs));
		}
		prepStm.executeQuery();
		return habitacions;
	}

	//---------------------------------------------------------------------------------
	//RC3--Mostrar el indice de ocupación de cada una de las ofertas
	//---------------------------------------------------------------------------------

	/*IMPORTANTE*/
	/*Se entenderá el indice de ocupacion a partir de la siguiente interpretación: Si una oferta está ocupada es por que 
	ésta tiene una reserva a su nombre. A partir de esto, se entiende que el indice de ocupacion se evidencia con el 
	numero de reservas actuales y el numero de reservas historicas*/

	public ArrayList<VOIndiceHotel> getIndiceOfertasHotel() throws SQLException, Exception {
		ArrayList<VOIndiceHotel> habitacions = new ArrayList<>();

		//Aclaracion: Por simplicidad, solamente se obtienen los primeros 50 resultados de la consulta
		String sql = String.format(	"SELECT ID_HOTEL,ID_OFERTA, NUMERO_RESERVAS_ACTUALES, NUM_RESERVAS NUM_RESERVAS_HISTORICAS,VIGENTE FROM (SELECT COUNT(ID_HOTEL) NUMERO_RESERVAS_ACTUALES, ID_HOTEL FROM RESERVA GROUP BY  ID_HOTEL NATURAL JOIN OFERTA", USUARIO);
		String sql1 = String.format("COMMIT",USUARIO);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		PreparedStatement prepStm = conn.prepareStatement(sql1);
		recursos.add(prepStmt);
		recursos.add(prepStm);

		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			habitacions.add(convertResultSetToIndiceHotel(rs));
		}
		prepStm.executeQuery();
		return habitacions;
	}


	public ArrayList<VOIndiceHostal> getIndiceOfertasHostal() throws SQLException, Exception {
		ArrayList<VOIndiceHostal> habitacions = new ArrayList<>();

		//Aclaracion: Por simplicidad, solamente se obtienen los primeros 50 resultados de la consulta
		String sql = String.format(	"SELECT ID_HOSTAL,ID_OFERTA, NUMERO_RESERVAS_ACTUALES, NUM_RESERVAS NUM_RESERVAS_HISTORICAS,VIGENTE FROM (SELECT COUNT(ID_HOSTAL) NUMERO_RESERVAS_ACTUALES, ID_HOSTAL FROM RESERVA GROUP BY  ID_HOSTAL NATURAL JOIN OFERTA", USUARIO);
		String sql1 = String.format("COMMIT",USUARIO);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		PreparedStatement prepStm = conn.prepareStatement(sql1);
		recursos.add(prepStmt);
		recursos.add(prepStm);

		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			habitacions.add(convertResultSetToIndiceHostal(rs));
		}
		prepStm.executeQuery();
		return habitacions;
	}

	public ArrayList<VOIndicePersona> getIndiceOfertasPersona() throws SQLException, Exception {
		ArrayList<VOIndicePersona> habitacions = new ArrayList<>();

		//Aclaracion: Por simplicidad, solamente se obtienen los primeros 50 resultados de la consulta
		String sql = String.format(	"SELECT ID_PERSONA,ID_OFERTA, NUMERO_RESERVAS_ACTUALES, NUM_RESERVAS NUM_RESERVAS_HISTORICAS,VIGENTE FROM (SELECT COUNT(ID_PERSONA) NUMERO_RESERVAS_ACTUALES, ID_PERSONA FROM RESERVA GROUP BY  ID_PERSONA NATURAL JOIN OFERTA", USUARIO);
		String sql1 = String.format("COMMIT",USUARIO);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		PreparedStatement prepStm = conn.prepareStatement(sql1);
		recursos.add(prepStmt);
		recursos.add(prepStm);

		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			habitacions.add(convertResultSetToIndicePersona(rs));
		}
		prepStm.executeQuery();
		return habitacions;
	}

	public ArrayList<VOIndiceVivendaU> getIndiceOfertasViviendaU() throws SQLException, Exception {
		ArrayList<VOIndiceVivendaU> habitacions = new ArrayList<>();

		//Aclaracion: Por simplicidad, solamente se obtienen los primeros 50 resultados de la consulta
		String sql = String.format(	"SELECT ID_VIVIENDAU,ID_OFERTA, NUMERO_RESERVAS_ACTUALES, NUM_RESERVAS NUM_RESERVAS_HISTORICAS,VIGENTE FROM (SELECT COUNT(ID_VIVIENDAU) NUMERO_RESERVAS_ACTUALES, ID_VIVIENDAU FROM RESERVA GROUP BY  ID_VIVIENDAU NATURAL JOIN OFERTA", USUARIO);
		String sql1 = String.format("COMMIT",USUARIO);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		PreparedStatement prepStm = conn.prepareStatement(sql1);
		recursos.add(prepStmt);
		recursos.add(prepStm);

		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			habitacions.add(convertResultSetToIndiceViviendaU(rs));
		}
		prepStm.executeQuery();
		return habitacions;
	}

	//---------------------------------------------------------------------------------
	//RC4--Mostrar los alojamientos disponibles en un rango de fechas 
	//		que cumplen requerimientos de servicios
	//---------------------------------------------------------------------------------

	public ArrayList<VODisponible> getAlojamientosDisponiblesHotel(Integer diaInic, Integer diaFin, Integer mesInic, Integer mesFinal,
			String tipoServicioInmobiliario, String tipoServicioPublico) throws SQLException, Exception {
		ArrayList<VODisponible> habitacions = new ArrayList<>();

		String sql = String.format("SELECT * FROM %1$s.(((((HABITACION ha INNER JOIN RESERVA res ON ha.ID_RESERVA = res.ID_RESERVA and ha.ID_HOTEL = res.ID_HOTEL) NATURAL JOIN HABITACIONES_SERVICIOSPUBLICOS) NATURAL JOIN HABITACIONES_INMOBILIARIOS) NATURAL JOIN SERVICIO_INMOBILIARIO)  NATURAL JOIN SERVICIO_PUBLICO) WHERE (TO_NUMBER(SUBSTR(FECHA,1, 2)) >   %2$d AND TO_NUMBER(SUBSTR(FECHA,1, 2)) < %3$d AND TO_NUMBER(SUBSTR(FECHA,4, 2)) >= %4$d AND TO_NUMBER(SUBSTR(FECHA,4, 2)) < %5$d  AND TO_NUMBER(SUBSTR(FECHA,7, 4)) = 2018) AND(TIPO_SERVICIO_INMOBILIARIO = %6$s AND TIPO_SERVICIO_PUBLICO = %7$s)", USUARIO, diaInic, diaFin, mesInic, mesFinal,tipoServicioInmobiliario, tipoServicioPublico);
		String sql1 = String.format("COMMIT",USUARIO);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		PreparedStatement prepStm = conn.prepareStatement(sql1);
		recursos.add(prepStmt);
		recursos.add(prepStm);

		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			habitacions.add(convertResultSetToDisponible(rs));
		}
		prepStm.executeQuery();
		return habitacions;
	}
	
	
	public ArrayList<VODisponible> getAlojamientosDisponiblesHostal(Integer diaInic, Integer diaFin, Integer mesInic, Integer mesFinal,
			String tipoServicioInmobiliario, String tipoServicioPublico) throws SQLException, Exception {
		ArrayList<VODisponible> habitacions = new ArrayList<>();

		String sql = String.format("SELECT * FROM %1$s.(((((HABITACION ha INNER JOIN RESERVA res ON ha.ID_RESERVA = res.ID_RESERVA and ha.ID_HOSTAL = res.ID_HOSTAL) NATURAL JOIN HABITACIONES_SERVICIOSPUBLICOS) NATURAL JOIN HABITACIONES_INMOBILIARIOS) NATURAL JOIN SERVICIO_INMOBILIARIO)  NATURAL JOIN SERVICIO_PUBLICO) WHERE (TO_NUMBER(SUBSTR(FECHA,1, 2)) > %2$d  AND TO_NUMBER(SUBSTR(FECHA,1, 2)) < %3$d AND TO_NUMBER(SUBSTR(FECHA,4, 2)) >= %4$d  AND TO_NUMBER(SUBSTR(FECHA,4, 2)) < %5$d   AND TO_NUMBER(SUBSTR(FECHA,7, 4)) = 2018) AND (TIPO_SERVICIO_INMOBILIARIO = %6$s AND TIPO_SERVICIO_PUBLICO = %7$s)", USUARIO, diaInic, diaFin, mesInic, mesFinal,tipoServicioInmobiliario, tipoServicioPublico);
		String sql1 = String.format("COMMIT",USUARIO);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		PreparedStatement prepStm = conn.prepareStatement(sql1);
		recursos.add(prepStmt);
		recursos.add(prepStm);

		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			habitacions.add(convertResultSetToDisponible(rs));
		}
		prepStm.executeQuery();
		return habitacions;
	}
	
	
	public ArrayList<VODisponible> getAlojamientosDisponiblesPersona(Integer diaInic, Integer diaFin, Integer mesInic, Integer mesFinal,
			String tipoServicioInmobiliario, String tipoServicioPublico) throws SQLException, Exception {
		ArrayList<VODisponible> habitacions = new ArrayList<>();

		String sql = String.format("SELECT * FROM %1$s.(((((HABITACION ha INNER JOIN RESERVA res ON ha.ID_RESERVA = res.ID_RESERVA and ha.ID_PERSONA = res.ID_PERSONA) NATURAL JOIN HABITACIONES_SERVICIOSPUBLICOS) NATURAL JOIN HABITACIONES_INMOBILIARIOS) NATURAL JOIN SERVICIO_INMOBILIARIO)  NATURAL JOIN SERVICIO_PUBLICO) WHERE (TO_NUMBER(SUBSTR(FECHA,1, 2)) > %2$d   AND TO_NUMBER(SUBSTR(FECHA,1, 2)) < %3$d  AND TO_NUMBER(SUBSTR(FECHA,4, 2)) >= %4$d  AND TO_NUMBER(SUBSTR(FECHA,4, 2)) < %5$d  AND TO_NUMBER(SUBSTR(FECHA,7, 4)) = 2018) AND(TIPO_SERVICIO_INMOBILIARIO = %6$s AND TIPO_SERVICIO_PUBLICO = %7$s)", USUARIO, diaInic, diaFin, mesInic, mesFinal,tipoServicioInmobiliario, tipoServicioPublico);
		String sql1 = String.format("COMMIT",USUARIO);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		PreparedStatement prepStm = conn.prepareStatement(sql1);
		recursos.add(prepStmt);
		recursos.add(prepStm);

		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			habitacions.add(convertResultSetToDisponible(rs));
		}
		prepStm.executeQuery();
		return habitacions;
	}
	
	
	public ArrayList<VODisponible> getAlojamientosDisponiblesViviendaU(Integer diaInic, Integer diaFin, Integer mesInic, Integer mesFinal,
			String tipoServicioInmobiliario, String tipoServicioPublico) throws SQLException, Exception {
		ArrayList<VODisponible> habitacions = new ArrayList<>();

		String sql = String.format("SELECT * FROM %1$s.(((((HABITACION ha INNER JOIN RESERVA res ON ha.ID_RESERVA = res.ID_RESERVA and ha.ID_VIVIENDAU = res.ID_VIVIENDAU) NATURAL JOIN HABITACIONES_SERVICIOSPUBLICOS) NATURAL JOIN HABITACIONES_INMOBILIARIOS) NATURAL JOIN SERVICIO_INMOBILIARIO)  NATURAL JOIN SERVICIO_PUBLICO) WHERE (TO_NUMBER(SUBSTR(FECHA,1, 2)) > %2$d  AND TO_NUMBER(SUBSTR(FECHA,1, 2)) < %3$d AND TO_NUMBER(SUBSTR(FECHA,4, 2)) >= %4$d AND TO_NUMBER(SUBSTR(FECHA,4, 2)) < %5$d AND TO_NUMBER(SUBSTR(FECHA,7, 4)) = 2018) AND(TIPO_SERVICIO_INMOBILIARIO = %6$s AND TIPO_SERVICIO_PUBLICO = %7$s)", USUARIO, diaInic, diaFin, mesInic, mesFinal,tipoServicioInmobiliario, tipoServicioPublico);
		String sql1 = String.format("COMMIT",USUARIO);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		PreparedStatement prepStm = conn.prepareStatement(sql1);
		recursos.add(prepStmt);
		recursos.add(prepStm);

		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			habitacions.add(convertResultSetToDisponible(rs));
		}
		prepStm.executeQuery();
		return habitacions;
	}



	//---------------------------------------------------------------------------------
	//RC5--Mostrar el uso de Alohandes para cada tipo de usuario de la comunidad
	//---------------------------------------------------------------------------------

	/**
	 * Usuarios que usan hoteles
	 * VOUsoHotel
	 * @throws SQLException
	 * @throws Exception
	 */
	public ArrayList<VOUsoHotel> getUsoHotel()throws SQLException, Exception{
		ArrayList<VOUsoHotel> habitacions = new ArrayList<>();

		String sql = String.format(	 "SELECT NOMBRE_CLIENTE, TIPO_CLIENTE,ID_HOTEL FROM (CLIENTE  NATURAL JOIN RESERVAS_CLIENTES) NATURAL JOIN RESERVA WHERE ID_HOTEL IS NOT NULL", USUARIO);
		String sql1 = String.format("COMMIT",USUARIO);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		PreparedStatement prepStm = conn.prepareStatement(sql1);
		recursos.add(prepStmt);
		recursos.add(prepStm);

		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			habitacions.add(convertResultSetToUsoHotel(rs));
		}
		prepStm.executeQuery();
		return habitacions;

	}


	/**
	 * Usuarios que usan hosales
	 * VOUsoHostal
	 * @throws SQLException
	 * @throws Exception
	 */
	public ArrayList<VOUsoHostal> getUsoHostal()throws SQLException, Exception{
		ArrayList<VOUsoHostal> habitacions = new ArrayList<>();

		String sql = String.format(" SELECT NOMBRE_CLIENTE, TIPO_CLIENTE,ID_HOSTAL FROM (CLIENTE  NATURAL JOIN RESERVAS_CLIENTES) NATURAL JOIN RESERVA WHERE ID_HOSTAL IS NOT NULL", USUARIO);
		String sql1 = String.format("COMMIT",USUARIO);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		PreparedStatement prepStm = conn.prepareStatement(sql1);
		recursos.add(prepStmt);
		recursos.add(prepStm);

		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			habitacions.add(convertResultSetToUsoHostal(rs));
		}
		prepStm.executeQuery();
		return habitacions;

	}


	/**
	 * Usuarios que usan la vivienda universitaria
	 * VOUsoViviendaU
	 * @throws SQLException
	 * @throws Exception
	 */
	public ArrayList<VOUsoVivienda> getUsoVivienda()throws SQLException, Exception{
		ArrayList<VOUsoVivienda> habitacions = new ArrayList<>();

		String sql = String.format("SELECT NOMBRE_CLIENTE, TIPO_CLIENTE,ID_VIVIENDAU FROM (CLIENTE  NATURAL JOIN RESERVAS_CLIENTES) NATURAL JOIN RESERVA WHERE ID_VIVIENDAU IS NOT NULL", USUARIO);
		String sql1 = String.format("COMMIT",USUARIO);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		PreparedStatement prepStm = conn.prepareStatement(sql1);
		recursos.add(prepStmt);
		recursos.add(prepStm);

		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			habitacions.add(convertResultSetToUsoVivienda(rs));
		}
		prepStm.executeQuery();
		return habitacions;
	}


	/**
	 * Usuarios que usan alojamientos de personas
	 * VOUsoPersona
	 * @throws SQLException
	 * @throws Exception
	 */
	public ArrayList<VOUsoPersona> getUsoPersona()throws SQLException, Exception{
		ArrayList<VOUsoPersona> habitacions = new ArrayList<>();

		String sql = String.format("SELECT NOMBRE_CLIENTE, TIPO_CLIENTE,ID_PERSONA FROM (CLIENTE  NATURAL JOIN RESERVAS_CLIENTES) NATURAL JOIN RESERVA WHERE ID_PERSONA IS NOT NULL", USUARIO);
		String sql1 = String.format("COMMIT",USUARIO);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		PreparedStatement prepStm = conn.prepareStatement(sql1);
		recursos.add(prepStmt);
		recursos.add(prepStm);

		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			habitacions.add(convertResultSetToUsoPersona(rs));
		}
		prepStm.executeQuery();
		return habitacions;
	}



	//---------------------------------------------------------------------------------
	//RC6--Mostrar el uso de Alohandes para cada un usuario dado
	//---------------------------------------------------------------------------------

	/*----------------Uso general de la aplicacion------- */

	public ArrayList<VOUsoCliente> getUsoUsuario(Integer idCliente)throws SQLException, Exception{
		ArrayList<VOUsoCliente> habitacions = new ArrayList<>();

		String sql = String.format("SELECT * FROM %1$s.((SELECT ID_RESERVA,ID_CLIENTE FROM((CLIENTE  NATURAL JOIN RESERVAS_CLIENTES) NATURAL JOIN RESERVA )) iden INNER JOIN HABITACION hab ON iden.ID_RESERVA = hab.ID_RESERVA WHERE ID_CLIENTE = %2$d", USUARIO, idCliente);
		String sql1 = String.format("COMMIT",USUARIO);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		PreparedStatement prepStm = conn.prepareStatement(sql1);
		recursos.add(prepStmt);
		recursos.add(prepStm);

		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			habitacions.add(convertResultSetToUsoCliente(rs));
		}
		prepStm.executeQuery();
		return habitacions;
	}

	/*----------------Detalles especificos en caso que se tengan los detalles------- */

	public ArrayList<VOUsoEspecificoCliente> getUsoEspecificoUsuario(Integer idCliente)throws SQLException, Exception{
		ArrayList<VOUsoEspecificoCliente> habitacions = new ArrayList<>();
		String sql = String.format("SELECT * FROM %1$s.(((((SELECT ID_RESERVA, ID_CLIENTE FROM((CLIENTE  NATURAL JOIN RESERVAS_CLIENTES) NATURAL JOIN RESERVA )) iden INNER JOIN HABITACION hab ON iden.ID_RESERVA = hab.ID_RESERVA) NATURAL JOIN  HABITACIONES_INMOBILIARIOS) NATURAL JOIN HABITACIONES_SERVICIOSPUBLICOS) NATURAL JOIN SERVICIO_PUBLICO) NATURAL JOIN SERVICIO_INMOBILIARIO WHERE ID_CLIENTE = %2$d", USUARIO, idCliente);
		String sql1 = String.format("COMMIT",USUARIO);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		PreparedStatement prepStm = conn.prepareStatement(sql1);
		recursos.add(prepStmt);
		recursos.add(prepStm);

		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			habitacions.add(convertResultSetToUsoEspecificoCliente(rs));
		}
		prepStm.executeQuery();
		return habitacions;
	}



	//---------------------------------------------------------------------------------
	//RC7--Mostrar el uso de Alohandes para cada un usuario dado
	//---------------------------------------------------------------------------------

	/*---------------------------------- Fechas con la mayor demanda ------------------------------- */

	/* Se consideran las 3 fechas de mayor demanda*/
	/* El mes ingresa como un numero*/
	/* Para indicar el tipo de alojamiento se debe ingresar la palabra que representa el identificador de ese tipo e.g.: 
	   1)ID_HOTEL
	   2)ID_HOSTAL
	   3)ID_VIVIENDAU
	   4)ID_PERSONA
	 */

	public ArrayList<VOFechaDemanda> getFechaMayorDemanda(String tipoAlojamiento, Integer mes)throws SQLException, Exception{
		ArrayList<VOFechaDemanda> habitacions = new ArrayList<>();
		String sql = String.format("SELECT * FROM %1$s.(SELECT COUNT(FECHA) CANTIDAD_OCUPADA, FECHA FROM (SELECT * FROM RESERVA WHERE %2$s IS NOT NULL) GROUP BY FECHA ORDER BY CANTIDAD_OCUPADA DESC) WHERE TO_NUMBER(SUBSTR(FECHA,4, 2)) = %3$d AND ROWNUM <= 3", USUARIO, tipoAlojamiento,mes);
		String sql1 = String.format("COMMIT",USUARIO);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		PreparedStatement prepStm = conn.prepareStatement(sql1);
		recursos.add(prepStmt);
		recursos.add(prepStm);

		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			habitacions.add(convertResultSetToFechaDemanda(rs));
		}
		prepStm.executeQuery();
		return habitacions;
	}

	/*---------------------------------- Fechas con los mayores ingresos --------------------------- */

	/* Se consideran las 3 fechas de mayores ingresos*/
	/* El mes ingresa como un numero*/
	/* Para indicar el tipo de alojamiento se debe ingresar la palabra que representa el identificador de ese tipo e.g.: 
	   1)ID_HOTEL
	   2)ID_HOSTAL
	   3)ID_VIVIENDAU
	   4)ID_PERSONA
	 */

	public ArrayList<VOFechaIngresos> getFechaMayorIngresos(String tipoAlojamiento, Integer mes)throws SQLException, Exception{
		ArrayList<VOFechaIngresos> habitacions = new ArrayList<>();
		String sql = String.format("SELECT FECHA,VALOR FROM %1$s.(SELECT * FROM RESERVA ORDER BY VALOR DESC) WHERE TO_NUMBER(SUBSTR(FECHA,4, 2)) = %2$d AND %3$s IS NOT NULL  AND ROWNUM <= 3", USUARIO, mes, tipoAlojamiento);
		String sql1 = String.format("COMMIT",USUARIO);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		PreparedStatement prepStm = conn.prepareStatement(sql1);
		recursos.add(prepStmt);
		recursos.add(prepStm);

		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			habitacions.add(convertResultSetToFechaIngresos(rs));
		}
		prepStm.executeQuery();
		return habitacions;
	}




	/*---------------------------------- Fechas con la menor demanda ------------------------------- */

	/* Se consideran las 3 fechas de menor demanda*/
	/* El mes ingresa como un numero*/
	/* Para indicar el tipo de alojamiento se debe ingresar la palabra que representa el identificador de ese tipo e.g.: 
	   1)ID_HOTEL
	   2)ID_HOSTAL
	   3)ID_VIVIENDAU
	   4)ID_PERSONA
	 */
	/*Se sugiere utilizar ID_HOTEL*/

	public ArrayList<VOFechaDemanda> getFechaMenorDemanda(String tipoAlojamiento, Integer mes)throws SQLException, Exception{
		ArrayList<VOFechaDemanda> habitacions = new ArrayList<>();
		String sql = String.format("SELECT * FROM %1$s.(SELECT COUNT(FECHA) CANTIDAD_OCUPADA, FECHA FROM (SELECT * FROM RESERVA WHERE %2$s IS NOT NULL) GROUP BY FECHA ORDER BY CANTIDAD_OCUPADA ASC) WHERE TO_NUMBER(SUBSTR(FECHA,4, 2)) = %3$d AND ROWNUM <= 3", USUARIO, tipoAlojamiento,mes);
		String sql1 = String.format("COMMIT",USUARIO);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		PreparedStatement prepStm = conn.prepareStatement(sql1);
		recursos.add(prepStmt);
		recursos.add(prepStm);

		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			habitacions.add(convertResultSetToFechaDemanda(rs));
		}
		prepStm.executeQuery();
		return habitacions;
	}



	//---------------------------------------------------------------------------------
	//RC8--Encontrar los clientes frecuentes
	//---------------------------------------------------------------------------------



	/*----------------Caso en el que cliente frecuente se identifica por duracion------- */

	/* Para indicar el tipo de alojamiento se debe ingresar la palabra que representa el identificador de ese tipo e.g.: 
   1)ID_HOTEL
   2)ID_HOSTAL
   3)ID_VIVIENDAU
   4)ID_PERSONA
	 */

	public ArrayList<VOClienteDuracion> getClienteFrecuenteDuracion(String tipoAlojamiento, Integer identificadorAlojamiento)throws SQLException, Exception{
		ArrayList<VOClienteDuracion> habitacions = new ArrayList<>();
		String sql = String.format("SELECT ID_CLIENTE,NOMBRE_CLIENTE, TIPO_CLIENTE, DURACION DIAS_ESTADIA FROM %1$s.(SELECT * FROM RESERVAS_CLIENTES  NATURAL JOIN RESERVA WHERE DURACION >= 15 AND ( %2$s =  %3$d)) NATURAL JOIN CLIENTE   ", USUARIO, tipoAlojamiento,identificadorAlojamiento);
		String sql1 = String.format("COMMIT",USUARIO);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		PreparedStatement prepStm = conn.prepareStatement(sql1);
		recursos.add(prepStmt);
		recursos.add(prepStm);

		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			habitacions.add(convertResultSetToFrecuenteDuracion(rs));
		}
		prepStm.executeQuery();
		return habitacions;
	}
	
	
	/*----------------Caso en el que cliente frecuente se identifica por numero de reservas------- */

	public ArrayList<VOClienteReservas> getClienteFrecuenteReservas()throws SQLException, Exception{
		ArrayList<VOClienteReservas> habitacions = new ArrayList<>();
		String sql = String.format(" SELECT * FROM ((SELECT COUNT(ID_CLIENTE) NUMERO_RESERVAS, ID_CLIENTE FROM RESERVAS_CLIENTES  GROUP BY ID_CLIENTE) NATURAL JOIN CLIENTE) WHERE NUMERO_RESERVAS>=3", USUARIO);
		String sql1 = String.format("COMMIT",USUARIO);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		PreparedStatement prepStm = conn.prepareStatement(sql1);
		recursos.add(prepStmt);
		recursos.add(prepStm);

		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			habitacions.add(convertResultSetToFrecuenteReservas(rs));
		}
		prepStm.executeQuery();
		return habitacions;
	}


	//-----------------------------------------------------------------------------
	//MÉTODOS EXTRAS PARA EL DESARROLLO DE LOS REQUERIMIENTOS DE CONSULTA 
	//-----------------------------------------------------------------------------

	public Oferta convertResultSetToOferta(ResultSet resultSet) throws SQLException {
		boolean rta1 = false;
		boolean rta2 = false;
		boolean rta3 = false;

		String id = resultSet.getString("ID_OFERTA");

		String vigente = resultSet.getString("VIGENTE");

		if(vigente.equals("1"))
		{
			rta1 = true;
		}
		String nombre = resultSet.getString("NUM_RESERVAS");
		String idHostal = resultSet.getString("ID_HOSTAL");
		String idPersona = resultSet.getString("ID_PERSONA");
		String idHotel = resultSet.getString("ID_HOTEL");
		String idViviendaU = resultSet.getString("ID_VIVIENDAU");

		Integer rta4 = null;
		Integer rta5 = null;
		Integer rta6 = null;
		Integer rta7 = null;
		Integer rta8 = null;

		if(idHotel != null )
		{
			rta4 = Integer.parseInt(idHotel);
		}
		if(idHostal != null )
		{
			rta5 = Integer.parseInt(idHostal);
		}
		if(idPersona != null )
		{
			rta6 = Integer.parseInt(idPersona);
		}
		if(idViviendaU != null )
		{
			rta7 = Integer.parseInt(idViviendaU);
		}



		Oferta beb = new Oferta(Integer.parseInt(id), Integer.parseInt(nombre), rta1, rta4, rta6, rta5, rta7);

		return beb;
	}



	private VOExtraPersona convertResultSetToExtraPersona(ResultSet rs) throws SQLException {

		String nombre = rs.getString("valor");
		String idHostal = rs.getString("ID_PERSONA");

		VOExtraPersona rta;
		return rta = new VOExtraPersona(Integer.parseInt(idHostal), Integer.parseInt(nombre));
	}


	private VOExtraViviendaUniversitaria convertResultSetToExtraViviendaUniversitaria(ResultSet rs) throws SQLException {

		String nombre = rs.getString("valor");
		String idHostal = rs.getString("ID_VIVIENDAU");

		VOExtraViviendaUniversitaria rta;
		return rta = new VOExtraViviendaUniversitaria(Integer.parseInt(idHostal), Integer.parseInt(nombre));
	}


	private VOHostalExtra convertResultSetToVOExtra(ResultSet rs) throws SQLException {

		String nombre = rs.getString("valor");
		String idHostal = rs.getString("ID_HOSTAL");

		VOHostalExtra rta;
		return rta = new VOHostalExtra(Integer.parseInt(idHostal), Integer.parseInt(nombre));
	}



	private VOExtraHotel convertResultSetToExtraHotel(ResultSet rs) throws SQLException {

		String nombre = rs.getString("valor");
		String idHostal = rs.getString("ID_HOTEL");

		VOExtraHotel rta;
		return rta = new VOExtraHotel(Integer.parseInt(idHostal), Integer.parseInt(nombre));

	}

	private VOIndiceHotel convertResultSetToIndiceHotel(ResultSet rs) throws SQLException {

		String idHotel = rs.getString("ID_HOTEL");
		String idOferta = rs.getString("ID_OFERTA");
		String numActual = rs.getString("NUMERO_RESERVAS_ACTUALES");
		String numHisto = rs.getString("NUMERO_RESERVAS_HISTORICAS");
		String vigente = rs.getString("VIGENTE");
		Boolean vig = false;

		if(vigente.equals('T')) {
			vig = true;
		}

		VOIndiceHotel rta;
		return rta = new VOIndiceHotel(Integer.parseInt(idHotel), Integer.parseInt(idOferta), Integer.parseInt(numActual),Integer.parseInt(numHisto), vig);

	}

	private VOIndiceHostal convertResultSetToIndiceHostal(ResultSet rs) throws SQLException {

		String idHotel = rs.getString("ID_HOSTAL");
		String idOferta = rs.getString("ID_OFERTA");
		String numActual = rs.getString("NUMERO_RESERVAS_ACTUALES");
		String numHisto = rs.getString("NUMERO_RESERVAS_HISTORICAS");
		String vigente = rs.getString("VIGENTE");
		Boolean vig = false;

		if(vigente.equals('T')) {
			vig = true;
		}

		VOIndiceHostal rta;
		return rta = new VOIndiceHostal(Integer.parseInt(idHotel), Integer.parseInt(idOferta), Integer.parseInt(numActual),Integer.parseInt(numHisto), vig);

	}

	private VOIndicePersona convertResultSetToIndicePersona(ResultSet rs) throws SQLException {

		String idHotel = rs.getString("ID_PERSONA");
		String idOferta = rs.getString("ID_OFERTA");
		String numActual = rs.getString("NUMERO_RESERVAS_ACTUALES");
		String numHisto = rs.getString("NUMERO_RESERVAS_HISTORICAS");
		String vigente = rs.getString("VIGENTE");
		Boolean vig = false;

		if(vigente.equals('T')) {
			vig = true;
		}

		VOIndicePersona rta;
		return rta = new VOIndicePersona(Integer.parseInt(idHotel), Integer.parseInt(idOferta), Integer.parseInt(numActual),Integer.parseInt(numHisto), vig);

	}
	private VOIndiceVivendaU convertResultSetToIndiceViviendaU(ResultSet rs) throws SQLException {

		String idHotel = rs.getString("ID_VIVIENDAU");
		String idOferta = rs.getString("ID_OFERTA");
		String numActual = rs.getString("NUMERO_RESERVAS_ACTUALES");
		String numHisto = rs.getString("NUMERO_RESERVAS_HISTORICAS");
		String vigente = rs.getString("VIGENTE");
		Boolean vig = false;

		if(vigente.equals('T')) {
			vig = true;
		}

		VOIndiceVivendaU rta;
		return rta = new VOIndiceVivendaU(Integer.parseInt(idHotel), Integer.parseInt(idOferta), Integer.parseInt(numActual),Integer.parseInt(numHisto), vig);

	}

	private VOUsoHotel convertResultSetToUsoHotel(ResultSet rs) throws SQLException {

		String nombre = rs.getString("NOMBRE_CLIENTE");
		String tipo = rs.getString("TIPO_CLIENTE");
		String hotel = rs.getString("ID_HOTEL");

		VOUsoHotel rta;
		return rta = new VOUsoHotel (nombre, tipo, hotel);

	}

	private VOUsoHostal convertResultSetToUsoHostal(ResultSet rs) throws SQLException {

		String nombre = rs.getString("NOMBRE_CLIENTE");
		String tipo = rs.getString("TIPO_CLIENTE");
		String hotel = rs.getString("ID_HOSTAL");

		VOUsoHostal rta;
		return rta = new VOUsoHostal (nombre, tipo, hotel);

	}

	private VOUsoVivienda convertResultSetToUsoVivienda(ResultSet rs) throws SQLException {

		String nombre = rs.getString("NOMBRE_CLIENTE");
		String tipo = rs.getString("TIPO_CLIENTE");
		String hotel = rs.getString("ID_VIVIENDAU");

		VOUsoVivienda rta;
		return rta = new VOUsoVivienda (nombre, tipo, hotel);

	}


	private VOUsoPersona convertResultSetToUsoPersona(ResultSet rs) throws SQLException {

		String nombre = rs.getString("NOMBRE_CLIENTE");
		String tipo = rs.getString("TIPO_CLIENTE");
		String hotel = rs.getString("ID_PERSONA");

		VOUsoPersona rta;
		return rta = new VOUsoPersona (nombre, tipo, hotel);

	}

	private VOUsoCliente convertResultSetToUsoCliente(ResultSet rs) throws SQLException {

		String reserva = rs.getString("ID_RESERVA");
		String cliente = rs.getString("ID_CLIENTE");
		String habitacion = rs.getString("ID_HABITACION");
		String capacidad = rs.getString("CAPACIDAD_HABITACION");
		String precio = rs.getString("PRECIO");
		String tam = rs.getString("TAMANIO");
		String tipo = rs.getString("TIPO");
		String ubicacion = rs.getString("UBICACION");
		String reserv2 = rs.getString("ID_RESERVA_1");
		String oferta = rs.getString("ID_OFERTA");
		String hotel = rs.getString("ID_HOTEL");
		String hostal = rs.getString("ID_HOSTAL");
		String persona = rs.getString("ID_PERSONA");
		String vivienda = rs.getString("ID_VIVIENDAU");

		VOUsoCliente rta;
		return rta = new VOUsoCliente (Integer.parseInt(reserva), Integer.parseInt(cliente), Integer.parseInt(habitacion), Integer.parseInt(capacidad),Integer.parseInt(precio),
				Integer.parseInt(tam), tipo, ubicacion, Integer.parseInt(reserv2), Integer.parseInt(oferta), Integer.parseInt(hotel),
				Integer.parseInt(hostal), Integer.parseInt(persona), Integer.parseInt(vivienda));

	}


	private VOUsoEspecificoCliente convertResultSetToUsoEspecificoCliente(ResultSet rs) throws SQLException {

		String reserva = rs.getString("ID_RESERVA");
		String cliente = rs.getString("ID_CLIENTE");
		String habitacion = rs.getString("ID_HABITACION");
		String capacidad = rs.getString("CAPACIDAD_HABITACION");
		String precio = rs.getString("PRECIO");
		String tam = rs.getString("TAMANIO");
		String tipo = rs.getString("TIPO");
		String ubicacion = rs.getString("UBICACION");
		String reserv2 = rs.getString("ID_RESERVA_1");
		String oferta = rs.getString("ID_OFERTA");
		String hotel = rs.getString("ID_HOTEL");
		String hostal = rs.getString("ID_HOSTAL");
		String persona = rs.getString("ID_PERSONA");
		String vivienda = rs.getString("ID_VIVIENDAU");
		String tipoPub  = rs.getString("TIPO_SERVICIO_PUBLICO");
		String costoPub  = rs.getString("COSTO_SERVICIO_PUBLICO");
		String tipoIn  = rs.getString("TIPO_SERVICIO_INMOBILIARIO");
		String costoIn  = rs.getString("COSTO_SERVICIO_INMOBILIARIO");

		VOUsoEspecificoCliente rta;
		return rta = new VOUsoEspecificoCliente (Integer.parseInt(reserva), Integer.parseInt(cliente), Integer.parseInt(habitacion), Integer.parseInt(capacidad),Integer.parseInt(precio),
				Integer.parseInt(tam), tipo, ubicacion, Integer.parseInt(reserv2), Integer.parseInt(oferta), Integer.parseInt(hotel),
				Integer.parseInt(hostal), Integer.parseInt(persona), Integer.parseInt(vivienda), tipoPub,
				Integer.parseInt(costoPub), tipoIn, Integer.parseInt(costoIn));

	}


	private VOFechaDemanda convertResultSetToFechaDemanda(ResultSet rs) throws SQLException {

		String cantidad = rs.getString("CANTIDAD_OCUPADA");
		String fecha = rs.getString("FECHA");

		VOFechaDemanda rta;
		return rta = new VOFechaDemanda (Integer.parseInt(cantidad), fecha);

	}

	private VOFechaIngresos convertResultSetToFechaIngresos(ResultSet rs) throws SQLException {

		String fecha = rs.getString("FECHA");
		String valor = rs.getString("VALOR");


		VOFechaIngresos rta;
		return rta = new VOFechaIngresos (fecha,Integer.parseInt(valor));

	}
	
	
	private VOClienteDuracion convertResultSetToFrecuenteDuracion(ResultSet rs) throws SQLException {

		String id = rs.getString("ID_CLIENTE");
		String nombre = rs.getString("NOMBRE_CLIENTE");
		String tipo = rs.getString("TIPO_CLIENTE");
		String tiempo = rs.getString("DIAS_ESTADIA");

		VOClienteDuracion rta;
		return rta = new VOClienteDuracion(Integer.parseInt(id), nombre, tipo, Integer.parseInt(tiempo));

	}
	
	
	private VOClienteReservas convertResultSetToFrecuenteReservas(ResultSet rs) throws SQLException {

		String id = rs.getString("ID_CLIENTE");
		String num = rs.getString("NUMERO_RESERVAS");
		String miem = rs.getString("MIEMBRO_COMUNIDAD");
		String tipo = rs.getString("TIPO_CLIENTE");
		String nombre = rs.getString("NOMBRE_CLIENTE");
		String edad = rs.getString("EDAD");
		Boolean vig = false;

		if(miem.equals('T')) {
			vig = true;
		}

		

		VOClienteReservas rta;
		return rta = new VOClienteReservas(Integer.parseInt(id), Integer.parseInt(num), vig, tipo, nombre, Integer.parseInt(edad));

	}
	
	private VODisponible convertResultSetToDisponible(ResultSet rs) throws SQLException {

		String idServP= rs.getString("ID_SERVICIO_PUBLICO");
		String idServIn= rs.getString("ID_SERVICIO_INMOBILIARIO");
		String habitacion = rs.getString("ID_HABITACION");
		String capacidad = rs.getString("CAPACIDAD_HABITACION");
		String precio = rs.getString("PRECIO");
		String tam = rs.getString("TAMANIO");
		String tipo = rs.getString("TIPO");
		String ubicacion = rs.getString("UBICACION");
		String reserva = rs.getString("ID_RESERVA");
		String oferta = rs.getString("ID_OFERTA");
		String hotel = rs.getString("ID_HOTEL");
		String hostal = rs.getString("ID_HOSTAL");
		String persona = rs.getString("ID_PERSONA");
		String vivienda = rs.getString("ID_VIVIENDAU");
		String reserv2 = rs.getString("ID_RESERVA_1");
		String confir = rs.getString("CONFIRMADA");
		Boolean miem = false;
		if(confir.equals('T')) {
			miem = true;
		}
		
		String fech = rs.getString("FECHA");
		String tiempo = rs.getString("TIEMPO_CANCELACION");
		String valor = rs.getString("VALOR");
		String hotel1 = rs.getString("ID_HOTEL_1");
		String hostal1 = rs.getString("ID_HOSTAL_1");
		String persona1 = rs.getString("ID_PERSONA_1");
		String vivienda1 = rs.getString("ID_VIVIENDAU_1");
		String duracion = rs.getString("DURACION");
		String anticipado = rs.getString("PAGO_ANTICIPADO");
		Boolean mie = false;
		if(anticipado.equals('T')) {
			mie = true;
		}
		String tipoPub  = rs.getString("TIPO_SERVICIO_PUBLICO");
		String costoPub  = rs.getString("COSTO_SERVICIO_PUBLICO");
		String tipoIn  = rs.getString("TIPO_SERVICIO_INMOBILIARIO");
		String costoIn  = rs.getString("COSTO_SERVICIO_INMOBILIARIO");


		VODisponible rta;
		return rta = new VODisponible(Integer.parseInt(idServP), Integer.parseInt(idServIn), Integer.parseInt(habitacion), Integer.parseInt(capacidad),
				Integer.parseInt(precio), Integer.parseInt(tam),tipo, ubicacion, Integer.parseInt(reserva), Integer.parseInt(oferta),Integer.parseInt(hotel),
				Integer.parseInt(hostal), Integer.parseInt(persona),Integer.parseInt(vivienda),Integer.parseInt(reserv2), miem, fech,tiempo,
				Integer.parseInt(valor), Integer.parseInt(hotel1), Integer.parseInt(hostal1), Integer.parseInt(persona1), Integer.parseInt(vivienda1),
				Integer.parseInt(duracion), mie, tipoPub, Integer.parseInt(costoPub), tipoIn, Integer.parseInt(costoIn));

	}