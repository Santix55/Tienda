package p2;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class RealizarCompra
 */
@WebServlet("/RealizarCompra")
public class RealizarCompra extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.err.println("\nRealizarCompra");
		
		HttpSession session = request.getSession(true);
		String mensaje = "";
		var carritoObj = session.getAttribute("carrito");
		var codigoUsuario = (Integer) session.getAttribute("usuario");
		
		// Parametros mandados por el usuario
		String nombreUsuario = (String) request.getParameter("nombre");
		String telefono = (String) request.getParameter("telefono");
		String direccion = (String) request.getParameter("direccion");
		String tipopago = (String) request.getParameter("tipopago");
		String tarjeta_cuenta = (String) request.getParameter("tarjeta/cuenta");
		
		System.err.println("Nombre de usuario: " + nombreUsuario);
		System.err.println("Teléfono: " + telefono);
		System.err.println("Dirección: " + direccion);
		System.err.println("Tipo de pago: " + tipopago);
		System.err.println("Tarjeta/cuenta: " + tarjeta_cuenta);
		
		// Errores
		if (carritoObj == null)
			mensaje = "No se pudo cargar el carrito";
		else if (codigoUsuario == null || codigoUsuario<=0 )
			mensaje = "No se pudo cargar el usuario";
		else if (direccion.equals(""))
			mensaje = "No se introdujo direccion";
		else if (tarjeta_cuenta.equals(""))
			mensaje = "No se introdujo una cuenta o tarjeta";
		else {
			// Comprobar si hay stock de todos los productos
			ArrayList<Producto> carrito = (ArrayList<Producto>) carritoObj;
			AccesoBD con = AccesoBD.getInstance();
			for(Producto producto: carrito) {
				int stock = con.getStock(producto.getCodigo());
				if(stock == con.ERROR_EN_CONSULTA) {
					mensaje += "ERRROR EN CONSULTA \n";
					break;
				}
				else if(stock == con.NO_EXISTE_PRODUCTO)
					mensaje += "No existe el producto "+producto.getNombre();
				else if(stock - producto.getCantidad() < 0)
					mensaje += "No hay suficiente stock de "+producto.getNombre()+"\n";
			}
		}
		
		// Si esta todo correcto, crear el pedido
		if(mensaje.equals("")) {
			ArrayList<Producto> carrito = (ArrayList<Producto>) carritoObj;
			AccesoBD con = AccesoBD.getInstance();
			con.comprar(carrito, codigoUsuario);
			con.modificarUsuarioPago(nombreUsuario, telefono, direccion, tipopago, tarjeta_cuenta, codigoUsuario);
		}
		
		// Al final del mensaje añadir un <script> que vacia el carrito de "carrito.js" y actualiza la UI
		// (el script se pone en el mensaje porque solo se va a ejecutar una vez, y se borra el mensaje
		AccesoBD con = AccesoBD.getInstance();
		mensaje += "<script>vaciarCarrito("+con.getStockMapJS()+")</script>";
		
		
		// Comprobar si hay errores para redirigir
		System.err.println(mensaje);
		session.setAttribute("mensaje", mensaje);
		response.sendRedirect("html/usuario.jsp");
		
	}
}
