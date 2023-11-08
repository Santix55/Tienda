package p2;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.*;

public final class AccesoBD {
	private static AccesoBD instanciaUnica = null;
	private Connection conexionBD = null;

	public static AccesoBD getInstance() {
		if (instanciaUnica == null) {
			instanciaUnica = new AccesoBD();
		}
		return instanciaUnica;
	}
	

	private AccesoBD() {
		abrirConexionBD();
	}
	
	public void abrirConexionBD() {
		if (conexionBD == null)
		{ // daw es el nombre de la base de datos que hemos creado con anterioridad.
			String nombreConexionBD = "jdbc:mysql://localhost/daw";
			try { // root y sin clave es el usuario por defecto que crea XAMPP.
				Class.forName("com.mysql.cj.jdbc.Driver");
				conexionBD = DriverManager.getConnection(nombreConexionBD,"root","");
			}
			catch(Exception e) {
				System.err.println("No se ha podido conectar a la base de datos");
				System.err.println(e.getMessage());
			}
		}
	}
	
	public void cerrarConexionBD() {
		if (conexionBD != null) {
			try {
				conexionBD.close();
				conexionBD = null;
			}
			catch(Exception e) {
				System.err.println("No se ha podido desconectar de la base de datos");
				System.err.println(e.getMessage());
			}
		}
	}
	
	public boolean comprobarAcceso() {
		abrirConexionBD();
		boolean res = (conexionBD != null);
		cerrarConexionBD();
		return res;
	}
	
	public List<ProductoBD> obtenerProductosBD() {
		abrirConexionBD();
		
		ArrayList<ProductoBD> productos = new ArrayList<>();
		
		try {
			String con = "SELECT codigo, descripcion, precio, existencias, imagen, nombre FROM productos";
			Statement s = conexionBD.createStatement();
			ResultSet resultado = s.executeQuery(con);
			
			while(resultado.next()) {
				ProductoBD producto = new ProductoBD();
				
				producto.setCodigo(resultado.getInt("codigo"));
				producto.setDescripcion(resultado.getString("descripcion"));
				producto.setPrecio(resultado.getInt("precio"));
				producto.setStock(resultado.getInt("existencias"));
				producto.setImagen(resultado.getString("imagen"));
				producto.setNombre(resultado.getString("nombre"));
				
				productos.add(producto);
			}
		}
		catch(Exception e) {
			System.err.println("Error ejecutando la consulta de a la base de datos");
			System.err.println(e.getMessage());
		}
		
		return productos;
	}


	public int comprobarUsuarioBD(String usuario, String clave) {
		abrirConexionBD();
		try {
			clave = hash256(clave);
			System.err.println(clave);
			
			String con="SELECT codigo FROM usuarios WHERE usuario=? AND clave=?";
			PreparedStatement s = conexionBD.prepareStatement(con);
			s.setString(1, usuario);
			s.setString(2, clave);
			ResultSet resultado = s.executeQuery();
			
			if(resultado.next()) { // Si se encuentra el par usuario/clave en BD
				return resultado.getInt("codigo");
			}
		}
		catch(Exception e) {
			System.err.println("Error verificando usuario/clave");
			System.err.println(e.getMessage());
		}
		return -1;
	}
	
	
	public int registrarUsuarioBD(String usuario, String nombre, String clave, String direccion, String telefono) {
		this.abrirConexionBD();
		try {
			String claveHash = this.hash256(clave);
			System.err.println("hash: "+claveHash);
			
			// Crear usuario
			String con = "INSERT INTO usuarios (usuario, clave, nombre, domicilio, telefono) VALUES (?,?,?,?,?)";
			PreparedStatement s = conexionBD.prepareStatement(con);
			
			s.setString(1, usuario);
			s.setString(2, claveHash);
			s.setString(3, nombre);
			s.setString(4, direccion);
			s.setString(5, telefono);
			
			s.executeUpdate();
			
			// Obtener clave del usuario
			con = "SELECT codigo FROM usuarios";
			s = conexionBD.prepareStatement(con);
			var result = s.executeQuery();
			if(result.next()) { // Ir al dato que te devuelve la consulta
				return result.getInt("codigo");
			}
		}
		catch(Exception e) {
			System.err.println("Error insertando usuario nuevo");
			System.err.println(e.getMessage());
		}
		return -1;
	}
	
	private String hash256 (String s) {
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("SHA-256");
			byte[] hash = md.digest(s.getBytes());
			String hashStr = Base64.getEncoder().encodeToString(hash);
			return hashStr;
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			System.err.println("Hash 256 error");
			e.printStackTrace();
		}
		return null;
	}


	public boolean existeUsuario(String usuario) {
		this.abrirConexionBD();
		try {
			String con="SELECT usuario FROM usuarios WHERE usuario=?";
			PreparedStatement s = conexionBD.prepareStatement(con);
			s.setString(1, usuario);
			ResultSet resultado = s.executeQuery();
			
			if(resultado.next()) { // Si se encuentra el par usuario/clave en BD
				return true;
			}
		}
		catch(Exception e) {
			System.err.println("Error verificando si existe el usuario");
			System.err.println(e.getMessage());
		}
		return false;
	}

	public final int ERROR_EN_CONSULTA = -1;
	public final int NO_EXISTE_PRODUCTO = -2;
	public int getStock(int codigo) {
		this.abrirConexionBD();
		try {
			String con = "SELECT existencias FROM productos WHERE codigo=?";
			PreparedStatement s = conexionBD.prepareStatement(con);
			s.setInt(1, codigo);
			ResultSet resultado = s.executeQuery();
			
			if(resultado.next())
				return resultado.getInt("existencias");
			else
				return NO_EXISTE_PRODUCTO; // NO EXISTE
		}
		catch(Exception e) {
			System.err.println("Error ejecutando la consulta getStock");
			System.err.println(e.getMessage());
		}
		return ERROR_EN_CONSULTA; // PROBLEMAS CON LA CONSULTA
	}
	
	public Usuario getUsuario(int codigo) {
		this.abrirConexionBD();
		try {
			String con = "SELECT * FROM usuarios WHERE codigo=?";
			PreparedStatement s = conexionBD.prepareStatement(con);
			s.setInt(1, codigo);
			ResultSet resultado = s.executeQuery();
			
			if(resultado.next()) {
				var usuario = new Usuario();
				usuario.setCodigo(codigo);
				usuario.setUsername(resultado.getString("usuario"));
				usuario.setNombre(resultado.getString("nombre"));
				
				usuario.setDomicilio(resultado.getString("domicilio"));
				usuario.setTelefono(resultado.getString("telefono"));
				
				usuario.setTipopago(resultado.getInt("tipopago"));
				usuario.setTarjeta(resultado.getString("tarjeta"));
				usuario.setCuenta(resultado.getString("cuenta"));
				return usuario;
			}
			else
				return null;
			
		}
		catch(Exception e) {
			System.err.println("Error ejecutando la consulta getUsuario");
			System.err.println(e.getMessage());
		}
		return null;
	}


	public boolean modificarUsuario(String nombre, String clave, String direccion, String telefono, int tipoPago, String tarjeta, String cuenta, int codigoUsuario) {
		this.abrirConexionBD();
		try {
			clave = clave.equals("")? "": this.hash256(clave);
			String con = "UPDATE usuarios SET nombre=?, domicilio=?, telefono=?, tipopago=?, tarjeta=?, cuenta=?, clave=IF(?<>'', ?, clave) WHERE codigo=?";
			PreparedStatement s = conexionBD.prepareStatement(con);
			s.setString(1, nombre);
			s.setString(2, direccion);
			s.setString(3, telefono);
			s.setInt(4, tipoPago);
			s.setString(5,tarjeta);
			s.setString(6, cuenta);
			s.setString(7, clave);
			s.setString(8, clave);
			s.setInt(9, codigoUsuario);
			s.executeUpdate();
		}
		catch(Exception e) {
			System.err.println("Error ejecutando la consulta modificarUsuario");
			System.err.println(e.getMessage());
			return false;
		}
		return true;
	}


	public void comprar(ArrayList<Producto> carrito, int codigoUsuario) {
		this.abrirConexionBD();
		try {
			// Generar pedido sin importe
			var con = "INSERT INTO pedidos (persona) VALUES (?)";
			var s = conexionBD.prepareStatement(con);
			s.setInt(1, codigoUsuario);
			s.executeUpdate();
			
			
			// Generar detalles y calcular el importe
			int importe = 0;
			for(var producto: carrito) {
				con = "INSERT INTO detalle (codigo_pedido, codigo_producto, unidades, precio_unitario) VALUES ((SELECT MAX(codigo) FROM pedidos),?,?,?)";
				s = conexionBD.prepareStatement(con);
				s.setInt(1, producto.getCodigo());
				s.setInt(2, producto.getCantidad());
				s.setInt(3, producto.getPrecio());
				importe += producto.getPrecio() * producto.getCantidad();
				s.executeUpdate();
			}
			
			// Añadir el campo importe al pedido
			con = "UPDATE pedidos SET importe=? WHERE codigo=(SELECT MAX(codigo) FROM pedidos)";
			s = conexionBD.prepareStatement(con);
			s.setInt(1, importe);
			s.executeUpdate();
			
			// Actualizar las existencias de los productos
			for(var producto: carrito) {
				con = "UPDATE productos SET existencias = existencias - ? WHERE codigo = ?";
				s = conexionBD.prepareStatement(con);
				s.setInt(1, producto.getCantidad());
				s.setInt(2, producto.getCodigo());
				s.executeUpdate();
			}	
		}
		catch(Exception e) {
			System.err.println("Error ejecutando la consulta comprar");
			System.err.println(e.getMessage());
		}
	}

	/**
	 * Consulta el stock de todos los productos de la base de datos y genera un mapa
	 * con todos todos los códigos (como clave) y todas las existencias (como valor)
	 * @return String con el código con el mapa o string que tiene escrito "null" si falla
	 */
	public String getStockMapJS() {
		this.abrirConexionBD();
		try {
			String con = "SELECT codigo, existencias FROM productos";
			PreparedStatement s = conexionBD.prepareStatement(con);
			var result = s.executeQuery();
			
			String code = "new Map([";
			while(result.next())
				code += "["+result.getInt(1)+","+result.getInt(2)+"],";
			code = code.substring(0, code.length()-1); // quitar ',' final
			code += "])";
			
			return code;
		}
		catch(Exception e) {
			System.err.println("Error ejecutando la consulta getMapStockJS");
			System.err.println(e.getMessage());
		}
		return "null";
	}
	
	
	
	public List<PedidoBD> obtenerPedidosBD (int codigo_usuario) {
		var pedidos = new ArrayList<PedidoBD>();
		this.abrirConexionBD();
		try {
			String con = "SELECT codigo, fecha, estado, importe FROM pedidos WHERE persona=?";
			PreparedStatement s = conexionBD.prepareStatement(con);
			s.setInt(1, codigo_usuario);
			var cursorPedidos = s.executeQuery();
			
			while(cursorPedidos.next()) {
				PedidoBD pedido = new PedidoBD();
				pedidos.add(pedido);
				
				int codigo_pedido = cursorPedidos.getInt(1);
				Date fecha = cursorPedidos.getDate(2);
				int estado = cursorPedidos.getInt(3);
				SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yy");
				var productos = new ArrayList<Producto>();
				int importe = cursorPedidos.getInt(4);
				
				pedido.setCodigo(codigo_pedido);
				pedido.setFecha(formato.format(fecha));
				pedido.setProductos(productos);
				pedido.setEstado(estado);
				pedido.setImporte(importe);
				
				con = "SELECT d.codigo_producto, d.unidades, d.precio_unitario, p.nombre "
						+ "FROM detalle d "
						+ "JOIN productos p ON p.codigo = d.codigo_producto "
						+ "WHERE d.codigo_pedido = ?";
				PreparedStatement s2 = conexionBD.prepareStatement(con);
				s2.setInt(1, codigo_pedido);
				var cursorProductos = s2.executeQuery();
				
				while(cursorProductos.next()) {
					Producto producto = new Producto();
					productos.add(producto);
					
					producto.setCodigo(cursorProductos.getInt(1));
					producto.setCantidad(cursorProductos.getInt(2));
					producto.setPrecio(cursorProductos.getInt(3));
					producto.setNombre(cursorProductos.getString(4));
				}
			}
		}
		catch(Exception e) {
			System.err.println("Error ejecutando las consultas de obtenerPedidoBD");
			System.err.println(e.getMessage());
		}
		Collections.reverse(pedidos);
		return pedidos;
	}


	
	public void cancelarPedido(int codigoPedido) {
		this.abrirConexionBD();
		
		try {
			// Marcar pedido como cancelado
			String con = "UPDATE pedidos SET estado=3 WHERE codigo=?"; // estado3 = cancelado
			PreparedStatement s = conexionBD.prepareStatement(con);
			s.setInt(1, codigoPedido);
			s.executeUpdate();
			
			// Obtener productos del pedido y sus unidades
			con = "SELECT codigo_producto, unidades FROM detalle WHERE codigo_pedido=?";
			s = conexionBD.prepareStatement(con);
			s.setInt(1, codigoPedido);
			var cursor = s.executeQuery();
			
			while(cursor.next()) {	// para todos los productos
				int codigoProducto = cursor.getInt(1);
				int unidades = cursor.getInt(2);
				
				// Actualizar producto sumandole la unidades del pedido
				con = "UPDATE productos SET existencias = existencias + ? WHERE codigo = ?";
				s = conexionBD.prepareStatement(con);
				s.setInt(1, unidades);
				s.setInt(2, codigoProducto);
				s.executeUpdate();
			}
					
		}
		catch(Exception e) {
			System.err.println("Error ejecutando las consultas de cancelarPedido");
			System.err.println(e.getMessage());
		}
	}


	/**
	 * Se utiliza cuando se ha aceptado una compra y el usuario cambia los sus datos personales
	 * @param nombreUsuario
	 * @param telefono
	 * @param direccion
	 * @param tipopago "tarjeta" o "transferencia"
	 * @param tarjeta_cuenta Tarjeta o Cuenta que ha introducido el usuario (dependiendo de tipopago)
	 */
	public void modificarUsuarioPago(String nombreUsuario, String telefono, String direccion, String tipopago, String tarjeta_cuenta, int codigo) {
		this.abrirConexionBD();
		
		try {
			int tipopago_valor;		// Valor en la base de datos del campo tipopago
			String tipopago_nombre;	// Columna en la base de datos
			if(tipopago.equals("transferencia")) {
				tipopago_valor = 1;
				tipopago_nombre = "cuenta";
			}
			else {
				tipopago_valor = 0;
				tipopago_nombre = "tarjeta";
			}
			
			String con = "UPDATE usuarios SET nombre=?, telefono=?, domicilio=?, tipopago=?, "+tipopago_nombre+"=? WHERE codigo=?";
			System.err.println("Consulta modificarUsuarioPago: "+con);
			var s = conexionBD.prepareStatement(con);
			s.setString(1, nombreUsuario);
			s.setString(2, telefono);
			s.setString(3, direccion);
			s.setInt(4, tipopago_valor);
			s.setString(5, tarjeta_cuenta);
			s.setInt(6, codigo);
			s.executeUpdate();
		}
		catch(Exception e) {
			System.err.println("Error ejecutando las consultas de modificarUsuarioPago");
			System.err.println(e.getMessage());
		}
	}
}

