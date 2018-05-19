package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Oferta;
import vos.VOClienteDuracion;
import vos.VOClienteReservas;
import vos.VOConsumo;
import vos.VOConsumoVersion2;
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
import vos.VOSemanas;
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

	//	public void setTransaccionalidad() throws SQLException, Exception{
	//		String sql = String.format("SET AUTOCOMMIT O SET TRANSACTION ISOLATION LEVEL SERIALIZABLE", USUARIO);
	//		PreparedStatement prepStmt = conn.prepareStatement(sql);
	//		recursos.add(prepStmt);
	//		prepStmt.executeQuery();
	//	}
	//	
	public void setConn(Connection connection){
		this.conn = connection;
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
		String sql = String.format(	"SELECT ID_HOTEL, VALOR FROM RESERVA WHERE ID_HOTEL IS NOT NULL AND CONFIRMADA = 'T'", USUARIO);
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
		String sql = String.format(	"SELECT ID_VIVIENDAU, VALOR FROM RESERVA WHERE ID_VIVIENDAU IS NOT NULL AND CONFIRMADA = 'T'", USUARIO);
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
	//RC2--20 Ofertas mas reservadas
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
	//RC3--Mostrar el indice de ocupacion de cada una de las ofertas
	//---------------------------------------------------------------------------------

	/*IMPORTANTE*/
	/*Se entendera el indice de ocupacion a partir de la siguiente interpretacion: Si una oferta esta ocupada es por que 
	esta tiene una reserva a su nombre. A partir de esto, se entiende que el indice de ocupacion se evidencia con el 
	numero de reservas actuales y el numero de reservas historicas*/

	public ArrayList<VOIndiceHotel> getIndiceOfertasHotel() throws SQLException, Exception {
		ArrayList<VOIndiceHotel> habitacions = new ArrayList<>();

		//Aclaracion: Por simplicidad, solamente se obtienen los primeros 50 resultados de la consulta
		String sql = String.format(	"SELECT ID_HOTEL,ID_OFERTA, NUMERO_RESERVAS_ACTUALES, NUM_RESERVAS NUM_RESERVAS_HISTORICAS,VIGENTE FROM (SELECT COUNT(ID_HOTEL) NUMERO_RESERVAS_ACTUALES, ID_HOTEL FROM RESERVA GROUP BY ID_HOTEL) NATURAL JOIN OFERTA", USUARIO);
		String sql1 = String.format("COMMIT",USUARIO);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		PreparedStatement prepStm = conn.prepareStatement(sql1);
		recursos.add(prepStmt);
		recursos.add(prepStm);

		ResultSet rs = prepStm.executeQuery();
		rs = prepStmt.executeQuery();

		while (rs.next()) {
			habitacions.add(convertResultSetToIndiceHotel(rs));
		}
		prepStm.executeQuery();
		return habitacions;
	}


	public ArrayList<VOIndiceHostal> getIndiceOfertasHostal() throws SQLException, Exception {
		ArrayList<VOIndiceHostal> habitacions = new ArrayList<>();

		//Aclaracion: Por simplicidad, solamente se obtienen los primeros 50 resultados de la consulta
		String sql = String.format(	"SELECT ID_HOSTAL,ID_OFERTA, NUMERO_RESERVAS_ACTUALES, NUM_RESERVAS NUM_RESERVAS_HISTORICAS,VIGENTE FROM (SELECT COUNT(ID_HOSTAL) NUMERO_RESERVAS_ACTUALES, ID_HOSTAL FROM RESERVA GROUP BY  ID_HOSTAL) NATURAL JOIN OFERTA", USUARIO);
		String sql1 = String.format("COMMIT",USUARIO);


		PreparedStatement prepStmt = conn.prepareStatement(sql);
		PreparedStatement prepStm = conn.prepareStatement(sql1);
		recursos.add(prepStmt);
		recursos.add(prepStm);

		ResultSet rs = prepStmt.executeQuery();
		prepStm.executeQuery();

		while (rs.next()) {
			habitacions.add(convertResultSetToIndiceHostal(rs));
		}
		return habitacions;
	}

	public ArrayList<VOIndicePersona> getIndiceOfertasPersona() throws SQLException, Exception {
		ArrayList<VOIndicePersona> habitacions = new ArrayList<>();

		//Aclaracion: Por simplicidad, solamente se obtienen los primeros 50 resultados de la consulta
		String sql = String.format(	"SELECT ID_PERSONA,ID_OFERTA, NUMERO_RESERVAS_ACTUALES, NUM_RESERVAS NUM_RESERVAS_HISTORICAS,VIGENTE FROM (SELECT COUNT(ID_PERSONA) NUMERO_RESERVAS_ACTUALES, ID_PERSONA FROM RESERVA GROUP BY  ID_PERSONA) NATURAL JOIN OFERTA", USUARIO);
		String sql1 = String.format("COMMIT",USUARIO);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		PreparedStatement prepStm = conn.prepareStatement(sql1);
		recursos.add(prepStmt);
		recursos.add(prepStm);

		ResultSet rs = prepStmt.executeQuery();
		prepStm.executeQuery();

		while (rs.next()) {
			habitacions.add(convertResultSetToIndicePersona(rs));
		}
		return habitacions;
	}

	public ArrayList<VOIndiceVivendaU> getIndiceOfertasViviendaU() throws SQLException, Exception {
		ArrayList<VOIndiceVivendaU> habitacions = new ArrayList<>();

		//Aclaracion: Por simplicidad, solamente se obtienen los primeros 50 resultados de la consulta
		String sql = String.format(	"SELECT ID_VIVIENDAU,ID_OFERTA, NUMERO_RESERVAS_ACTUALES, NUM_RESERVAS NUM_RESERVAS_HISTORICAS,VIGENTE FROM (SELECT COUNT(ID_VIVIENDAU) NUMERO_RESERVAS_ACTUALES, ID_VIVIENDAU FROM RESERVA GROUP BY  ID_VIVIENDAU) NATURAL JOIN OFERTA", USUARIO);
		String sql1 = String.format("COMMIT",USUARIO);


		PreparedStatement prepStmt = conn.prepareStatement(sql);
		PreparedStatement prepStm = conn.prepareStatement(sql1);
		recursos.add(prepStmt);
		recursos.add(prepStm);

		ResultSet rs = prepStm.executeQuery();
		rs = prepStmt.executeQuery();

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

		String sql = String.format("SELECT * FROM (((((SELECT ha.ID_HABITACION, res.FECHA, res.ID_RESERVA, ha.ID_HOTEL, ha.ID_OFERTA FROM %1$s.HABITACION ha INNER JOIN %1$s.RESERVA res ON ha.ID_RESERVA = res.ID_RESERVA and ha.ID_HOTEL = res.ID_HOTEL) NATURAL JOIN %1$s.HABITACIONES_SERVICIOSPUBLICOS) NATURAL JOIN %1$s.HABITACIONES_INMOBILIARIOS) NATURAL JOIN %1$s.SERVICIO_INMOBILIARIO) NATURAL JOIN %1$s.SERVICIO_PUBLICO) WHERE (TO_NUMBER(SUBSTR(FECHA,1, 2)) > %2$d AND TO_NUMBER(SUBSTR(FECHA,1, 2)) < %3$d AND TO_NUMBER(SUBSTR(FECHA,4, 2)) >= %4$d AND TO_NUMBER(SUBSTR(FECHA,4, 2)) < %5$d AND TO_NUMBER(SUBSTR(FECHA,7, 4)) = 2018) AND(TIPO_SERVICIO_INMOBILIARIO = '%6$s' AND TIPO_SERVICIO_PUBLICO = '%7$s')", USUARIO, diaInic, diaFin, mesInic, mesFinal,tipoServicioInmobiliario, tipoServicioPublico);
		String sql1 = String.format("COMMIT",USUARIO);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		PreparedStatement prepStm = conn.prepareStatement(sql1);
		recursos.add(prepStmt);
		recursos.add(prepStm);

		ResultSet rs = prepStm.executeQuery();
		rs = prepStmt.executeQuery();

		while (rs.next()) {
			habitacions.add(convertResultSetToDisponible(rs));
		}
		prepStm.executeQuery();
		return habitacions;
	}


	public ArrayList<VODisponible> getAlojamientosDisponiblesHostal(Integer diaInic, Integer diaFin, Integer mesInic, Integer mesFinal,
			String tipoServicioInmobiliario, String tipoServicioPublico) throws SQLException, Exception {
		ArrayList<VODisponible> habitacions = new ArrayList<>();

		String sql = String.format("SELECT * FROM (((((SELECT ha.ID_HABITACION, res.FECHA, res.ID_RESERVA, ha.ID_HOSTAL, ha.ID_OFERTA FROM %1$s.HABITACION ha INNER JOIN %1$s.RESERVA res ON ha.ID_RESERVA = res.ID_RESERVA and ha.ID_HOSTAL = res.ID_HOSTAL) NATURAL JOIN %1$s.HABITACIONES_SERVICIOSPUBLICOS) NATURAL JOIN %1$s.HABITACIONES_INMOBILIARIOS) NATURAL JOIN %1$s.SERVICIO_INMOBILIARIO) NATURAL JOIN %1$s.SERVICIO_PUBLICO) WHERE (TO_NUMBER(SUBSTR(FECHA,1, 2)) > %2$d AND TO_NUMBER(SUBSTR(FECHA,1, 2)) < %3$d AND TO_NUMBER(SUBSTR(FECHA,4, 2)) >= %4$d AND TO_NUMBER(SUBSTR(FECHA,4, 2)) < %5$d AND TO_NUMBER(SUBSTR(FECHA,7, 4)) = 2018) AND(TIPO_SERVICIO_INMOBILIARIO = '%6$s' AND TIPO_SERVICIO_PUBLICO = '%7$s')", USUARIO, diaInic, diaFin, mesInic, mesFinal,tipoServicioInmobiliario, tipoServicioPublico);
		String sql1 = String.format("COMMIT",USUARIO);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		PreparedStatement prepStm = conn.prepareStatement(sql1);
		recursos.add(prepStmt);
		recursos.add(prepStm);

		ResultSet rs = prepStm.executeQuery();
		rs = prepStmt.executeQuery();

		while (rs.next()) {
			habitacions.add(convertResultSetToDisponible(rs));
		}
		prepStm.executeQuery();
		return habitacions;
	}


	public ArrayList<VODisponible> getAlojamientosDisponiblesPersona(Integer diaInic, Integer diaFin, Integer mesInic, Integer mesFinal,
			String tipoServicioInmobiliario, String tipoServicioPublico) throws SQLException, Exception {
		ArrayList<VODisponible> habitacions = new ArrayList<>();

		String sql = String.format("SELECT * FROM (((((SELECT ha.ID_HABITACION, res.FECHA, res.ID_RESERVA, ha.ID_PERSONA, ha.ID_OFERTA FROM %1$s.HABITACION ha INNER JOIN %1$s.RESERVA res ON ha.ID_RESERVA = res.ID_RESERVA and ha.ID_PERSONA = res.ID_PERSONA) NATURAL JOIN %1$s.HABITACIONES_SERVICIOSPUBLICOS) NATURAL JOIN %1$s.HABITACIONES_INMOBILIARIOS) NATURAL JOIN %1$s.SERVICIO_INMOBILIARIO) NATURAL JOIN %1$s.SERVICIO_PUBLICO) WHERE (TO_NUMBER(SUBSTR(FECHA,1, 2)) > %2$d AND TO_NUMBER(SUBSTR(FECHA,1, 2)) < %3$d AND TO_NUMBER(SUBSTR(FECHA,4, 2)) >= %4$d AND TO_NUMBER(SUBSTR(FECHA,4, 2)) < %5$d AND TO_NUMBER(SUBSTR(FECHA,7, 4)) = 2018) AND(TIPO_SERVICIO_INMOBILIARIO = '%6$s' AND TIPO_SERVICIO_PUBLICO = '%7$s')", USUARIO, diaInic, diaFin, mesInic, mesFinal,tipoServicioInmobiliario, tipoServicioPublico);
		String sql1 = String.format("COMMIT",USUARIO);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		PreparedStatement prepStm = conn.prepareStatement(sql1);
		recursos.add(prepStmt);
		recursos.add(prepStm);

		ResultSet rs = prepStm.executeQuery();
		rs = prepStmt.executeQuery();

		while (rs.next()) {
			habitacions.add(convertResultSetToDisponible(rs));
		}
		prepStm.executeQuery();
		return habitacions;
	}


	public ArrayList<VODisponible> getAlojamientosDisponiblesViviendaU(Integer diaInic, Integer diaFin, Integer mesInic, Integer mesFinal,
			String tipoServicioInmobiliario, String tipoServicioPublico) throws SQLException, Exception {
		ArrayList<VODisponible> habitacions = new ArrayList<>();

		String sql = String.format("SELECT * FROM (((((SELECT ha.ID_HABITACION, res.FECHA, res.ID_RESERVA, ha.ID_VIVIENDAU, ha.ID_OFERTA FROM %1$s.HABITACION ha INNER JOIN %1$s.RESERVA res ON ha.ID_RESERVA = res.ID_RESERVA and ha.ID_VIVIENDAU = res.ID_VIVIENDAU) NATURAL JOIN %1$s.HABITACIONES_SERVICIOSPUBLICOS) NATURAL JOIN %1$s.HABITACIONES_INMOBILIARIOS) NATURAL JOIN %1$s.SERVICIO_INMOBILIARIO) NATURAL JOIN %1$s.SERVICIO_PUBLICO) WHERE (TO_NUMBER(SUBSTR(FECHA,1, 2)) > %2$d AND TO_NUMBER(SUBSTR(FECHA,1, 2)) < %3$d AND TO_NUMBER(SUBSTR(FECHA,4, 2)) >= %4$d AND TO_NUMBER(SUBSTR(FECHA,4, 2)) < %5$d AND TO_NUMBER(SUBSTR(FECHA,7, 4)) = 2018) AND(TIPO_SERVICIO_INMOBILIARIO = '%6$s' AND TIPO_SERVICIO_PUBLICO = '%7$s')", USUARIO, diaInic, diaFin, mesInic, mesFinal,tipoServicioInmobiliario, tipoServicioPublico);
		String sql1 = String.format("COMMIT",USUARIO);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		PreparedStatement prepStm = conn.prepareStatement(sql1);
		recursos.add(prepStmt);
		recursos.add(prepStm);

		ResultSet rs = prepStm.executeQuery();
		rs = prepStmt.executeQuery();

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

		String sql = String.format("SELECT * FROM ((SELECT ID_RESERVA,ID_CLIENTE FROM((%1$s.CLIENTE  NATURAL JOIN %1$s.RESERVAS_CLIENTES) NATURAL JOIN %1$s.RESERVA )) iden INNER JOIN %1$s.HABITACION hab ON iden.ID_RESERVA = hab.ID_RESERVA) WHERE ID_CLIENTE = %2$d", USUARIO, idCliente);
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
		String sql = String.format("SELECT * FROM (((((SELECT ID_RESERVA, ID_CLIENTE FROM((%1$s.CLIENTE  NATURAL JOIN %1$s.RESERVAS_CLIENTES) NATURAL JOIN %1$s.RESERVA )) iden INNER JOIN %1$s.HABITACION hab ON iden.ID_RESERVA = hab.ID_RESERVA) NATURAL JOIN  %1$s.HABITACIONES_INMOBILIARIOS) NATURAL JOIN %1$s.HABITACIONES_SERVICIOSPUBLICOS) NATURAL JOIN %1$s.SERVICIO_PUBLICO) NATURAL JOIN %1$s.SERVICIO_INMOBILIARIO WHERE ID_CLIENTE = %2$d", USUARIO, idCliente);
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
		String sql = String.format("SELECT * FROM (SELECT COUNT(FECHA) CANTIDAD_OCUPADA, FECHA FROM (SELECT * FROM %1$s.RESERVA WHERE %2$s IS NOT NULL) GROUP BY FECHA ORDER BY CANTIDAD_OCUPADA DESC) WHERE TO_NUMBER(SUBSTR(FECHA,4, 2)) = '%3$d' AND ROWNUM <= 3", USUARIO, tipoAlojamiento,mes);
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
		String sql = String.format("SELECT FECHA,VALOR FROM (SELECT * FROM %1$s.RESERVA ORDER BY VALOR DESC) WHERE TO_NUMBER(SUBSTR(FECHA,4, 2)) = %2$d AND %3$s IS NOT NULL  AND ROWNUM <= 3", USUARIO, mes, tipoAlojamiento);
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
		String sql = String.format("SELECT * FROM (SELECT COUNT(FECHA) CANTIDAD_OCUPADA, FECHA FROM (SELECT * FROM %1$s.RESERVA WHERE %2$s IS NOT NULL) GROUP BY FECHA ORDER BY CANTIDAD_OCUPADA ASC) WHERE TO_NUMBER(SUBSTR(FECHA,4, 2)) = %3$d AND ROWNUM <= 3", USUARIO, tipoAlojamiento,mes);
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
		String sql = String.format("SELECT ID_CLIENTE,NOMBRE_CLIENTE, TIPO_CLIENTE, DURACION DIAS_ESTADIA FROM (SELECT * FROM %1$s.RESERVAS_CLIENTES  NATURAL JOIN %1$s.RESERVA WHERE DURACION >= 15 AND ( %2$s =  %3$d)) NATURAL JOIN %1$s.CLIENTE", USUARIO, tipoAlojamiento,identificadorAlojamiento);
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
		String sql = String.format("SELECT * FROM ((SELECT COUNT(ID_CLIENTE) NUMERO_RESERVAS, ID_CLIENTE FROM RESERVAS_CLIENTES  GROUP BY ID_CLIENTE) NATURAL JOIN CLIENTE) WHERE NUMERO_RESERVAS>=3", USUARIO);
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

	//---------------------------------------------------------------------------------
	//RC10--Consultar consumo en Alohandes Version 1
	//---------------------------------------------------------------------------------

	//Administrador

	public ArrayList<VOConsumo> getConsumoPrimeraVersionAdministrador(Integer idOferta, Integer diaInic, Integer diaFin, Integer mesInic, Integer mesFin, String criterio)throws SQLException, Exception{
		ArrayList<VOConsumo> consum = new ArrayList<>();
		String sql = String.format("SELECT * FROM (SELECT ID_HOTEL, ID_HOSTAL, ID_PERSONA, ID_VIVIENDAU, ID_CLIENTE, ID_OFERTA, FECHA, VALOR, DURACION, ID_RESERVA FROM ((SELECT ID_OFERTA, ID_HOTEL FROM %1$s.OFERTA WHERE ID_OFERTA = %2$d) NATURAL JOIN %1$s.RESERVA) NATURAL JOIN %1$s.RESERVAS_CLIENTES) WHERE (TO_NUMBER(SUBSTR(FECHA,1, 2)) > %3$d AND TO_NUMBER(SUBSTR(FECHA,1, 2)) < %4$d AND TO_NUMBER(SUBSTR(FECHA,4, 2)) >= %5$d AND TO_NUMBER(SUBSTR(FECHA,4, 2)) < %6$d AND TO_NUMBER(SUBSTR(FECHA,7, 4)) = 2018) ORDER BY %7$s ",USUARIO, idOferta, diaInic, diaFin, mesInic, mesFin, criterio);
		String sql1 = String.format("COMMIT", USUARIO);

		PreparedStatement prepStmt  = conn.prepareStatement(sql);
		PreparedStatement prepStm = conn.prepareStatement(sql1);
		recursos.add(prepStmt);
		recursos.add(prepStm);

		ResultSet rs = prepStmt.executeQuery();

		while(rs.next()){
			consum.add(convertResultSetToConsumo(rs));
		}
		prepStm.executeQuery();
		return consum;
	}

	//Cliente Especifico

	public ArrayList<VOConsumo> getConsumoPrimeraVersionCliente(Integer idOferta, Integer diaInic, Integer diaFin, Integer mesInic, Integer mesFin, Integer IdCli, String criterio)throws SQLException, Exception{
		ArrayList<VOConsumo> consum = new ArrayList<>();
		String sql = String.format("SELECT * FROM (SELECT ID_HOTEL, ID_HOSTAL, ID_PERSONA, ID_VIVIENDAU, ID_CLIENTE, ID_OFERTA, FECHA, VALOR, DURACION, ID_RESERVA FROM (SELECT * FROM ((SELECT ID_OFERTA, ID_HOTEL FROM %1$s.OFERTA WHERE ID_OFERTA = %2$d) NATURAL JOIN %1$s.RESERVA) NATURAL JOIN %1$s.RESERVAS_CLIENTES) WHERE (TO_NUMBER(SUBSTR(FECHA,1, 2)) > %3$d AND TO_NUMBER(SUBSTR(FECHA,1, 2)) < %4$d AND TO_NUMBER(SUBSTR(FECHA,4, 2)) >= %5$d AND TO_NUMBER(SUBSTR(FECHA,4, 2)) < %6$d AND TO_NUMBER(SUBSTR(FECHA,7, 4)) = 2018)) WHERE ID_CLIENTE = %7$d ORDER BY %8$s ", USUARIO, idOferta, diaInic, diaFin, mesInic, mesFin, IdCli, criterio);
		String sql1 = String.format("COMMIT", USUARIO);

		PreparedStatement prepStmt  = conn.prepareStatement(sql);
		PreparedStatement prepStm = conn.prepareStatement(sql1);
		recursos.add(prepStmt);
		recursos.add(prepStm);

		ResultSet rs = prepStmt.executeQuery();

		while(rs.next()){
			consum.add(convertResultSetToConsumo(rs));
		}
		prepStm.executeQuery();
		return consum;
	}


	//---------------------------------------------------------------------------------
	//RC11--Consultar consumo en Alohandes Version 2
	//---------------------------------------------------------------------------------

	//Administrador

	public ArrayList<VOConsumoVersion2> getConsumoSegundaVersionAdmin(Integer diaInic, Integer diaFin, Integer mesInic, Integer mesFin)throws SQLException, Exception{
		ArrayList<VOConsumoVersion2> consum = new ArrayList<>();
		String sql = String.format("SELECT * FROM %1$s.CLIENTE WHERE ID_CLIENTE NOT IN(SELECT ID_CLIENTE FROM %1$s.RESERVAS_CLIENTES NATURAL JOIN %1$s.RESERVA WHERE (TO_NUMBER(SUBSTR(FECHA,1, 2)) > %2$d AND TO_NUMBER(SUBSTR(FECHA,1, 2)) < %3$d AND TO_NUMBER(SUBSTR(FECHA,4, 2)) >= %4$d AND TO_NUMBER(SUBSTR(FECHA,4, 2)) < %5$d AND TO_NUMBER(SUBSTR(FECHA,7, 4)) = 2018)) AND ID_CLIENTE = %6$d",USUARIO, diaInic, diaFin, mesInic, mesFin);
		String sql1 = String.format("COMMIT", USUARIO);

		PreparedStatement prepStmt  = conn.prepareStatement(sql);
		PreparedStatement prepStm = conn.prepareStatement(sql1);
		recursos.add(prepStmt);
		recursos.add(prepStm);

		ResultSet rs = prepStmt.executeQuery();

		while(rs.next()){
			consum.add(convertResultSetToConsumoVersion(rs));
		}
		prepStm.executeQuery();
		return consum;
	}

	//Cliente Especifico

	public ArrayList<VOConsumoVersion2> getConsumoSegundaVersionCliente(Integer diaInic, Integer diaFin, Integer mesInic, Integer mesFin, Integer cliente)throws SQLException, Exception{
		ArrayList<VOConsumoVersion2> consum = new ArrayList<>();
		String sql = String.format("SELECT * FROM %1$s.CLIENTE WHERE ID_CLIENTE NOT IN(SELECT ID_CLIENTE FROM %1$s.RESERVAS_CLIENTES NATURAL JOIN %1$s.RESERVA WHERE (TO_NUMBER(SUBSTR(FECHA,1, 2)) > %2$d AND TO_NUMBER(SUBSTR(FECHA,1, 2)) < %3$d AND TO_NUMBER(SUBSTR(FECHA,4, 2)) >= %4$d AND TO_NUMBER(SUBSTR(FECHA,4, 2)) < %5$d AND TO_NUMBER(SUBSTR(FECHA,7, 4)) = 2018)) AND ID_CLIENTE = %6$d",USUARIO, diaInic, diaFin, mesInic, mesFin, cliente);
		String sql1 = String.format("COMMIT", USUARIO);

		PreparedStatement prepStmt  = conn.prepareStatement(sql);
		PreparedStatement prepStm = conn.prepareStatement(sql1);
		recursos.add(prepStmt);
		recursos.add(prepStm);

		ResultSet rs = prepStmt.executeQuery();

		while(rs.next()){
			consum.add(convertResultSetToConsumoVersion(rs));
		}
		prepStm.executeQuery();
		return consum;
	}

	//---------------------------------------------------------------------------------
	//RC12--Consultar funcionamiento
	//---------------------------------------------------------------------------------

	public ArrayList<VOSemanas> getSemanaDeFecha()throws SQLException, Exception{
		ArrayList<VOSemanas> consum = new ArrayList<>();
		String sql = String.format("SELECT ofi.ID_OFERTA,ofi.ID_HOSTAL, ofi.ID_HOTEL, ofi.ID_PERSONA, ofi.ID_VIVIENDAU, SEMANA, FECHA, ID_RESERVA NUM_RESERVAS FROM (%1$s.OFERTA ofi INNER JOIN (SELECT * FROM (SELECT ID_RESERVA, TO_NUMBER(TO_CHAR(TO_DATE(FECHAS_DAT,'DD/MM/YYYY'),'IW')) SEMANA, FECHA, ID_HOSTAL, ID_HOTEL, ID_VIVIENDAU, ID_PERSONA FROM (SELECT ID_RESERVA,TO_DATE(FECHA,'DD-MM-YYYY') FECHAS_DAT, CONFIRMADA, ID_HOTEL, ID_VIVIENDAU, ID_PERSONA, ID_HOSTAL,FECHA FROM %1$s.RESERVA)))t1 ON (ofi.ID_HOSTAL = t1.ID_HOSTAL OR ofi.ID_HOSTAL is Null AND t1.id_HOSTAL is Null) AND (ofi.ID_HOTEL = t1.ID_HOTEL OR ofi.ID_HOTEL is Null AND t1.id_HOTEL is Null) AND (ofi.ID_VIVIENDAU = t1.ID_VIVIENDAU OR ofi.ID_VIVIENDAU is Null AND t1.id_VIVIENDAU is Null) AND (ofi.ID_PERSONA = t1.ID_PERSONA OR ofi.ID_PERSONA is Null AND t1.id_PERSONA IS NULL)) ORDER BY SEMANA",USUARIO);
		String sql1 = String.format("COMMIT", USUARIO);

		PreparedStatement prepStmt  = conn.prepareStatement(sql);
		PreparedStatement prepStm = conn.prepareStatement(sql1);
		recursos.add(prepStmt);
		recursos.add(prepStm);

		ResultSet rs = prepStmt.executeQuery();

		while(rs.next()){
			consum.add(convertResultSetToSemanas(rs));
		}
		prepStm.executeQuery();
		return consum;
	} 

	//---------------------------------------------------------------------------------
	//RC13--Consultar los Buenos Clientes
	//---------------------------------------------------------------------------------

	//Con Reservas una vez al mes

	public ArrayList<VOConsumoVersion2> getClientesReservasMes(Integer mes)throws SQLException, Exception{
		ArrayList<VOConsumoVersion2> consum = new ArrayList<>();
		String sql = String.format("SELECT * FROM %1$s.CLIENTE NATURAL JOIN(SELECT ID_CLIENTE FROM %1$s.RESERVAS_CLIENTES NATURAL JOIN (SELECT ID_RESERVA FROM %1$s.RESERVA WHERE (TO_NUMBER(SUBSTR(FECHA,4, 2))) = %2$d))",USUARIO, mes);
		String sql1 = String.format("COMMIT", USUARIO);

		PreparedStatement prepStmt  = conn.prepareStatement(sql);
		PreparedStatement prepStm = conn.prepareStatement(sql1);
		recursos.add(prepStmt);
		recursos.add(prepStm);

		ResultSet rs = prepStmt.executeQuery();

		while(rs.next()){
			consum.add(convertResultSetToConsumoVersion(rs));
		}
		prepStm.executeQuery();
		return consum;
	}

	//Con Reservas en alojamientos costosos (costo > 40000 COP)

	public ArrayList<VOConsumoVersion2> getClientesReservasCostosas()throws SQLException, Exception{
		ArrayList<VOConsumoVersion2> consum = new ArrayList<>();
		String sql = String.format("SELECT * FROM %1$s.CLIENTE NATURAL JOIN (SELECT ID_CLIENTE FROM %1$s.RESERVAS_CLIENTES NATURAL JOIN (SELECT res.ID_RESERVA FROM (%1$s.RESERVA res INNER JOIN %1$s.HABITACION hab ON res.ID_RESERVA = hab.ID_RESERVA) WHERE PRECIO > 40000))", USUARIO);
		String sql1 = String.format("COMMIT", USUARIO);

		PreparedStatement prepStmt  = conn.prepareStatement(sql);
		PreparedStatement prepStm = conn.prepareStatement(sql1);
		recursos.add(prepStmt);
		recursos.add(prepStm);

		ResultSet rs = prepStmt.executeQuery();

		while(rs.next()){
			consum.add(convertResultSetToConsumoVersion(rs));
		}
		prepStm.executeQuery();
		return consum;
	}


	//Con Reservas en Suites

	public ArrayList<VOConsumoVersion2> getClientesReservasSuite()throws SQLException, Exception{
		ArrayList<VOConsumoVersion2> consum = new ArrayList<>();
		String sql = String.format("SELECT * FROM %1$s.CLIENTE NATURAL JOIN (SELECT ID_CLIENTE FROM %1$s.RESERVAS_CLIENTES NATURAL JOIN(SELECT res.ID_RESERVA FROM (%1$s.RESERVA res INNER JOIN %1$s.HABITACION hab ON res.ID_RESERVA = hab.ID_RESERVA) WHERE TIPO = 'SUITE'))",USUARIO);
		String sql1 = String.format("COMMIT", USUARIO);

		PreparedStatement prepStmt  = conn.prepareStatement(sql);
		PreparedStatement prepStm = conn.prepareStatement(sql1);
		recursos.add(prepStmt);
		recursos.add(prepStm);

		ResultSet rs = prepStmt.executeQuery();

		while(rs.next()){
			consum.add(convertResultSetToConsumoVersion(rs));
		}
		prepStm.executeQuery();
		return consum;
	}


	//-----------------------------------------------------------------------------
	//METODOS EXTRAS PARA EL DESARROLLO DE LOS REQUERIMIENTOS DE CONSULTA 
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
		String numHisto = rs.getString("NUM_RESERVAS_HISTORICAS");
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
		String numHisto = rs.getString("NUM_RESERVAS_HISTORICAS");
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
		String numHisto = rs.getString("NUM_RESERVAS_HISTORICAS");
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
		String numHisto = rs.getString("NUM_RESERVAS_HISTORICAS");
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
		String oferta = rs.getString("ID_OFERTA");
		Integer ofertaI = null;
		if(oferta != null)
		{
			ofertaI = Integer.parseInt(oferta);
		}
		String hotel = rs.getString("ID_HOTEL");
		Integer hotelI = null;
		if(hotel != null)
		{
			hotelI = Integer.parseInt(hotel);
		}
		String hostal = rs.getString("ID_HOSTAL");
		Integer hostalI = null;
		if(hostal != null)
		{
			hostalI = Integer.parseInt(hostal);
		}
		String persona = rs.getString("ID_PERSONA");
		Integer personaI = null;
		if(persona != null)
		{
			personaI = Integer.parseInt(persona);
		}
		String vivienda = rs.getString("ID_VIVIENDAU");
		Integer viviendaI = null;
		if(vivienda != null)
		{
			viviendaI = Integer.parseInt(vivienda);
		}

		VOUsoCliente rta;
		return rta = new VOUsoCliente (Integer.parseInt(reserva), Integer.parseInt(cliente), Integer.parseInt(habitacion), Integer.parseInt(capacidad),Integer.parseInt(precio),
				Integer.parseInt(tam), tipo, ubicacion, null, ofertaI, hotelI,
				hostalI, personaI, viviendaI);

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
		String oferta = rs.getString("ID_OFERTA");
		Integer ofertaI = null;
		if(oferta != null)
		{
			ofertaI = Integer.parseInt(oferta);
		}
		String hotel = rs.getString("ID_HOTEL");
		Integer hotelI = null;
		if(hotel != null)
		{
			hotelI = Integer.parseInt(hotel);
		}
		String hostal = rs.getString("ID_HOSTAL");
		Integer hostalI = null;
		if(hostal != null)
		{
			hostalI = Integer.parseInt(hostal);
		}
		String persona = rs.getString("ID_PERSONA");
		Integer personaI = null;
		if(persona != null)
		{
			personaI = Integer.parseInt(persona);
		}
		String vivienda = rs.getString("ID_VIVIENDAU");
		Integer viviendaI = null;
		if(vivienda != null)
		{
			viviendaI = Integer.parseInt(vivienda);
		}
		String tipoPub  = rs.getString("TIPO_SERVICIO_PUBLICO");
		String costoPub  = rs.getString("COSTO_SERVICIO_PUBLICO");
		String tipoIn  = rs.getString("TIPO_SERVICIO_INMOBILIARIO");
		String costoIn  = rs.getString("COSTO_SERVICIO_INMOBILIARIO");

		VOUsoEspecificoCliente rta;
		return rta = new VOUsoEspecificoCliente (Integer.parseInt(reserva), Integer.parseInt(cliente), Integer.parseInt(habitacion), Integer.parseInt(capacidad),Integer.parseInt(precio),
				Integer.parseInt(tam), tipo, ubicacion, Integer.parseInt(reserva), ofertaI, hotelI,
				hostalI, personaI,  viviendaI, tipoPub,
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


	private VOConsumo convertResultSetToConsumo(ResultSet rs) throws SQLException {

		String idHotel = rs.getString("ID_HOTEL");
		String idHostal = rs.getString("ID_HOSTAL");
		String idPersona = rs.getString("ID_PERSONA");
		String idViviendaU = rs.getString("ID_VIVIENDAU");
		String idCliente = rs.getString("ID_CLIENTE");
		String idOfer = rs.getString("ID_OFERTA");
		String fecha = rs.getString("FECHA");
		String valor = rs.getString("VALOR");
		String duracion  = rs.getString("DURACION");
		String idRes = rs.getString("ID_RESERVA");

		VOConsumo rta;
		return rta = new VOConsumo(Integer.parseInt(idHotel), Integer.parseInt(idHostal), Integer.parseInt(idPersona), 
				Integer.parseInt(idViviendaU), Integer.parseInt(idCliente), Integer.parseInt(idOfer), Integer.parseInt(valor),
				Integer.parseInt(duracion), Integer.parseInt(idRes), fecha);

	}

	private VODisponible convertResultSetToDisponible(ResultSet rs) throws SQLException {

		String idServP= rs.getString("ID_SERVICIO_PUBLICO");
		String idServIn= rs.getString("ID_SERVICIO_INMOBILIARIO");
		String tipoPub  = rs.getString("TIPO_SERVICIO_PUBLICO");
		String costoPub  = rs.getString("COSTO_SERVICIO_PUBLICO");
		String tipoIn  = rs.getString("TIPO_SERVICIO_INMOBILIARIO");
		String costoIn  = rs.getString("COSTO_SERVICIO_INMOBILIARIO");
		String habitacion = rs.getString("ID_HABITACION");
		String reserva = rs.getString("ID_RESERVA");
		String oferta = rs.getString("ID_OFERTA");
		try
		{
			String hotel = rs.getString("ID_HOTEL");
			VODisponible rta;
			return rta = new VODisponible(Integer.parseInt(idServP), Integer.parseInt(idServIn), Integer.parseInt(habitacion), null,
					null, null,null, null, Integer.parseInt(reserva), Integer.parseInt(oferta),Integer.parseInt(hotel),
					null, null,null,null, null, null,null,
					null, null, null, null, null,
					null, null, tipoPub, Integer.parseInt(costoPub), tipoIn, Integer.parseInt(costoIn));
		}
		catch(SQLException e)
		{
			try
			{
				String hostal = rs.getString("ID_HOSTAL");
				VODisponible rta;
				return rta = new VODisponible(Integer.parseInt(idServP), Integer.parseInt(idServIn), Integer.parseInt(habitacion), null,
						null, null,null, null, Integer.parseInt(reserva), Integer.parseInt(oferta),null,
						Integer.parseInt(hostal), null,null,null, null, null,null,
						null, null, null, null, null,
						null, null, tipoPub, Integer.parseInt(costoPub), tipoIn, Integer.parseInt(costoIn));
			}
			catch(SQLException e2)
			{
				try
				{
					String persona = rs.getString("ID_PERSONA");
					VODisponible rta;
					return rta = new VODisponible(Integer.parseInt(idServP), Integer.parseInt(idServIn), Integer.parseInt(habitacion), null,
							null, null,null, null, Integer.parseInt(reserva), Integer.parseInt(oferta),null,
							null, Integer.parseInt(persona),null,null, null, null,null,
							null, null, null, null, null,
							null, null, tipoPub, Integer.parseInt(costoPub), tipoIn, Integer.parseInt(costoIn));
				}
				catch(SQLException ex)
				{
					String vivienda = rs.getString("ID_VIVIENDAU");
					VODisponible rta;
					return rta = new VODisponible(Integer.parseInt(idServP), Integer.parseInt(idServIn), Integer.parseInt(habitacion), null,
							null, null,null, null, Integer.parseInt(reserva), Integer.parseInt(oferta),null,
							null, null,Integer.parseInt(vivienda),null, null, null,null,
							null, null, null, null, null,
							null, null, tipoPub, Integer.parseInt(costoPub), tipoIn, Integer.parseInt(costoIn));
				}
			}
		}
	}

	private VOConsumoVersion2 convertResultSetToConsumoVersion(ResultSet rs) throws SQLException {

		String idCliente = rs.getString("ID_CLIENTE");
		String miembro = rs.getString("MIEMBRO_COMUNIDAD");
		String tipo = rs.getString("TIPO_CLIENTE");
		String nombre = rs.getString("NOMBRE_CLIENTE");
		String edad = rs.getString("EDAD");

		VOConsumoVersion2 rta;
		return rta = new VOConsumoVersion2(Integer.parseInt(idCliente), miembro, tipo, nombre, Integer.parseInt(edad));
	}
	
	private VOSemanas convertResultSetToSemanas(ResultSet rs) throws SQLException {

		String idOferta = rs.getString("ID_OFERTA");
		String idHostal = rs.getString("ID_HOSTAL");
		String idHotel = rs.getString("ID_HOTEL");
		String idPersona = rs.getString("ID_PERSONA");
		String vivienda = rs.getString("ID_VIVIENDAU");
		String semana = rs.getString("SEMANA");
		String fecha = rs.getString("FECHA");
		String numero = rs.getString("NUM_RESERVAS");

		VOSemanas rta;
		return rta = new VOSemanas(Integer.parseInt(idOferta), Integer.parseInt(idHostal), Integer.parseInt(idHotel),
				Integer.parseInt(idPersona), Integer.parseInt(vivienda), Integer.parseInt(semana), fecha, Integer.parseInt(numero));
	}
}