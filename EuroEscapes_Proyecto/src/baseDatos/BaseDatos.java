package baseDatos;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import entidades.Cliente;
import entidades.Itinerario;
import entidades.Pais;

public class BaseDatos {

	// Asigna la ruta del archivo a una variable
	private static final String archivoConexion = "./src/baseDatos/conexionBD.txt";

	/**
	 * Obtiene los valores de configuracion desde un archivo de texto
	 * @param campo El campo cuyo valor se quiere obtener
	 * @param archivo La ruta del archivo de configuración
	 * @return El valor del campo, o null si no se encuentra o hay un error
	 */
	public static String lecturaFicheroConexion(String campo, String archivo) {
		String linea;
		try {
			BufferedReader br = new BufferedReader(new FileReader(archivoConexion));
			while ((linea = br.readLine()) != null) { // Lectura linea por linea
				if (campo.equals(linea.split("-")[0])) { // Verificacion si el campo coincide
					if (linea.split("-")[1].equals("null")) {
						return "";
					}

					return linea.split("-")[1]; // Retorno del valor del campo
				}
			}
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Ha habido un error durante el procesamiento del fichero");
			System.exit(0);
		}

		return null;
	}
	
	/**
	 * Obtiene la conexion a la base de datos usando los parametros del archivo de configuracion
	 * @return La conexion a la base de datos
	 * @throws SQLException Si ocurre un error al obtener la conexion
	 */
	public static Connection getConnection() throws SQLException {
		String ip = lecturaFicheroConexion("ip", archivoConexion); // Obtencion de la IP desde el archivo
		String port = lecturaFicheroConexion("port", archivoConexion); // Obtencion del puerto desde el archivo
		String db = lecturaFicheroConexion("db", archivoConexion); // Obtencion del nombre de la base de datos desde el archivo
		String user = lecturaFicheroConexion("user", archivoConexion); // Obtencion del usuario desde el archivo
		String password = lecturaFicheroConexion("password", archivoConexion); // Obtencion de la contraseña desde el archivo

		String url = "jdbc:mysql://" + ip + ":" + port + "/" + db; // Construccion de la URL de conexion
		return DriverManager.getConnection(url, user, password); // Obtencion y retorno de la conexion
	}
	
	/**
	 * Verifica si un DNI existe en la base de datos
	 * @param dni El DNI a verificar
	 * @return true si el DNI existe, false en caso contrario
	 * @throws SQLException Si ocurre un error en la consulta
	 */
	public static boolean dniExiste(String dni) throws SQLException {

		String dniQuery = "SELECT COUNT(*) FROM cliente WHERE dni = ?"; 
		try (Connection conexion = getConnection(); PreparedStatement sentencia = conexion.prepareStatement(dniQuery)) {
			sentencia.setString(1, dni); // Establecer el parametro DNI en la consulta
			try (ResultSet resultado = sentencia.executeQuery()) {
				if (resultado.next()) {
					return resultado.getInt(1) > 0; // Retornar true si el DNI existe
				}
			}
		}
		return false;
	}

	/**
	 * Verifica si la contraseña para un DNI es correcta
	 * @param dni El DNI del cliente
	 * @param password La contraseña a verificar
	 * @return true si la contraseña es correcta, false en caso contrario
	 * @throws SQLException Si ocurre un error en la consulta
	 */
	public static boolean verificarPassword(String dni, String password) throws SQLException {

		String queryPassword = "SELECT passwd FROM cliente WHERE dni = ?";
		try (Connection conexion = getConnection();
				PreparedStatement sentencia = conexion.prepareStatement(queryPassword)) {
			sentencia.setString(1, dni);
			try (ResultSet resultado = sentencia.executeQuery()) {
				if (resultado.next()) {
					String passwordBD = resultado.getString("passwd"); // Obtencion de la contraseña de la base de datos
					return password.equals(passwordBD); // Comparacion de la contraseña ingresada con la de la base de datos
				}
			}
		}
		return false;
	}

	/**
	 * Ejecuta instrucciones SQL desde un archivo
	 * @param archivo La ruta del archivo que contiene las instrucciones SQL
	 * @throws SQLException Si ocurre un error durante la ejecución de las instrucciones
	 * @throws IOException Si ocurre un error al leer el archivo
	 */
	public static void ejecutarSQLDesdeFichero(String archivo) throws SQLException, IOException {

		try (Connection conexion = getConnection();
				Statement sentencia = conexion.createStatement();
				BufferedReader br = new BufferedReader(new FileReader("./src/baseDatos/insercionesSQL.txt"))) {

			StringBuilder sb = new StringBuilder();
			String linea;
			while ((linea = br.readLine()) != null) { // Lectura de linea por linea
				sb.append(linea).append(" "); // Concatenacion de las lineas 
			}
			sentencia.execute(sb.toString()); // Ejecucion de la consulta concatenada
		}
	}

	/**
	 * Verifica si la tabla de paises tiene datos, y si no, ejecuta un script SQL desde un archivo
	 * @throws SQLException Si ocurre un error durante la consulta o la ejecucion del script
	 * @throws IOException Si ocurre un error al leer el archivo
	 */
	public static void verificarTablaPaises() throws SQLException, IOException {

		Connection conexion = null;
		Statement sentencia = null;
		ResultSet resultado = null;

		try {
			conexion = getConnection();
			sentencia = conexion.createStatement();
			String query = "SELECT * FROM pais LIMIT 1";
			resultado = sentencia.executeQuery(query);

			if (!resultado.next()) { // Si no hay datos, ejecuta el script SQL
				ejecutarSQLDesdeFichero("./src/baseDeDatos/insercionesSQL.txt");
			}
		} finally {
			if (resultado != null) {
				resultado.close(); // Cerrar ResultSet
			}
			if (sentencia != null) {
				sentencia.close(); // Cerrar Statement
			}
			if (conexion != null) {
				conexion.close(); // Cerrar conexión
			}
		}
	}

	/**
	 * Obtiene una lista de paises desde la base de datos
	 * @return Una lista de objetos Pais
	 * @throws SQLException Si ocurre un error durante la consulta
	 */
	public static List<Pais> obtenerPaises() throws SQLException {

		List<Pais> paises = new ArrayList<>();
		String query = "SELECT id, nombre FROM pais";

		try (Connection conexion = getConnection();
				Statement sentencia = conexion.createStatement();
				ResultSet resultado = sentencia.executeQuery(query)) {

			while (resultado.next()) {
				int id = resultado.getInt("id");
				String nombre = resultado.getString("nombre");
				paises.add(new Pais(id, nombre));
			}
		}

		return paises;
	}
	
	/**
	 * Inserta un nuevo cliente en la base de datos
	 * @param cliente El objeto Cliente a insertar
	 * @throws SQLException Si ocurre un error durante la insercion
	 */
	public static void insertarCliente(Cliente cliente) throws SQLException {

		String sqlInsertarCliente = "INSERT INTO cliente (dni, passwd, nombre, primer_apellido, segundo_apellido, contacto) VALUES (?, ?, ?, ?, ?, ?)";

		try (Connection conexion = getConnection();

				PreparedStatement sentencia = conexion.prepareStatement(sqlInsertarCliente)) {
			sentencia.setString(1, cliente.getDni());
			sentencia.setString(2, cliente.getPassword());
			sentencia.setString(3, cliente.getNombre());
			sentencia.setString(4, cliente.getPrimerApellido());
			sentencia.setString(5, cliente.getSegundoApellido());
			sentencia.setString(6, cliente.getContacto());

			sentencia.executeUpdate();
		}
	}
	
	/**
	 * Inserta un nuevo itinerario en la base de datos
	 * @param itinerario El objeto Itinerario a insertar
	 * @throws SQLException Si ocurre un error durante la insercion
	 */
	public static void insertarItinerario(Itinerario itinerario) throws SQLException {

		Connection conexion = null;
		PreparedStatement sentencia = null;

		try {
			conexion = getConnection(); 
			
			String queryInsertarItinerario = "INSERT INTO itinerario (id_pais, medio_transporte, duracion) VALUES (?, ?, ?)";
			sentencia = conexion.prepareStatement(queryInsertarItinerario);
			sentencia.setInt(1, itinerario.getPais().getId());
			sentencia.setString(2, itinerario.getMedioTransporte());
			sentencia.setInt(3, itinerario.getDuracion());

			sentencia.executeUpdate();

		} finally {
			// Cierre de los recursos para asegurar que se cierran aunque ocurra una excepcion
			if (sentencia != null) {
				sentencia.close();
			}
			if (conexion != null) {
				conexion.close();
			}
		}
	}
	
	/**
	 * Obtiene el ultimo itinerario insertado en la base de datos
	 * @return El ultimo itinerario insertado
	 * @throws SQLException Si ocurre un error durante la consulta
	 */
	public static Itinerario obtenerUltimoItinerario() throws SQLException {
		Connection conexion = null;
		PreparedStatement sentencia = null;
		ResultSet resultado = null;
		Itinerario itinerario = null;

		try {
			conexion = getConnection(); 

			String queryObtenerItinerario = "SELECT id_pais, medio_transporte, duracion FROM itinerario ORDER BY id_pais DESC LIMIT 1";
			sentencia = conexion.prepareStatement(queryObtenerItinerario);
			resultado = sentencia.executeQuery();

			if (resultado.next()) {
				int idPais = resultado.getInt("id_pais");
				String medioTransporte = resultado.getString("medio_transporte");
				int duracion = resultado.getInt("duracion");

				Pais pais = obtenerPaisPorId(idPais); // Metodo para obtener un objeto Pais por su ID
				itinerario = new Itinerario(pais, medioTransporte, duracion);
			}
		} finally {
			// Cierre de los recursos para asegurar que se cierran aunque ocurra una excepcion
			if (resultado != null) {
				resultado.close();
			}
			if (sentencia != null) {
				sentencia.close();
			}
			if (conexion != null) {
				conexion.close();
			}
		}

		return itinerario;
	}
	
	/**
	 * Obtiene un pais por su ID desde la base de datos
	 * @param id El ID del pais a obtener
	 * @return El pais correspondiente al ID
	 * @throws SQLException Si ocurre un error durante la consulta
	 */
	public static Pais obtenerPaisPorId(int id) throws SQLException {
		Connection conexion = null;
		PreparedStatement sentencia = null;
		ResultSet resultado = null;
		Pais pais = null;

		try {
			conexion = getConnection(); 

			String queryObtenerPais = "SELECT id, nombre FROM pais WHERE id = ?";
			sentencia = conexion.prepareStatement(queryObtenerPais);
			sentencia.setInt(1, id);
			resultado = sentencia.executeQuery();

			if (resultado.next()) {
				int paisId = resultado.getInt("id");
				String nombre = resultado.getString("nombre");
				pais = new Pais(paisId, nombre);
			}
		} finally {
			// Cierre de los recursos para asegurar que se cierran aunque ocurra una excepcion
			if (resultado != null) {
				resultado.close();
			}
			if (sentencia != null) {
				sentencia.close();
			}
			if (conexion != null) {
				conexion.close();
			}
		}

		return pais;
	}
	
	/**
	 * Actualiza un itinerario en la base de datos
	 * @param itinerario El objeto Itinerario con los nuevos datos
	 * @throws SQLException Si ocurre un error durante la actualización
	 */
	public static void actualizarItinerario(Itinerario itinerario) throws SQLException {
		Connection conexion = null;
		PreparedStatement sentencia = null;

		try {
			conexion = BaseDatos.getConnection();

			String queryActaulizar = "UPDATE itinerario SET id_pais = ?, medio_transporte = ?, duracion = ? WHERE id_pais = ?";
			sentencia = conexion.prepareStatement(queryActaulizar);
			sentencia.setInt(1, itinerario.getPais().getId());
			sentencia.setString(2, itinerario.getMedioTransporte());
			sentencia.setInt(3, itinerario.getDuracion());
			sentencia.setInt(4, itinerario.getPais().getId());
			sentencia.executeUpdate();

		} finally {
			// Cierre de los recursos para asegurar que se cierran aunque ocurra una excepcion
			if (sentencia != null) {
				try {
					sentencia.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (conexion != null) {
				try {
					conexion.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * Elimina el ultimo itinerario insertado en la base de datos
	 * @param itinerario El objeto Itinerario que representa el itinerario a eliminar
	 * @throws SQLException Si ocurre un error durante la eliminación
	 */
	public static void eliminarItinerario(Itinerario itinerario) throws SQLException {
		Connection conexion = null;
		PreparedStatement sentencia = null;

		try {
			conexion = getConnection(); 

			String queryEliminar = "DELETE FROM itinerario WHERE id_pais = ? ORDER BY id DESC LIMIT 1";

			sentencia = conexion.prepareStatement(queryEliminar);

			// Establecer el parametro del id del pais en la sentencia SQL
			//Reemplaza 'idPais' con la variable que almacena el ID del país
			sentencia.setInt(1, itinerario.getPais().getId()); 
			sentencia.executeUpdate();
			
		} finally {
			// Cerrar la conexion y la declaracion preparada
			if (sentencia != null) {
				sentencia.close();
			}
			if (conexion != null) {
				conexion.close();
			}
		}
	}
}