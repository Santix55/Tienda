package p2;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class ModificarUsuario
 */
@WebServlet("/ModificarUsuario")
public class ModificarUsuario extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.err.println("\nRealizarCompra");
		
		HttpSession session = request.getSession(true);
		var codigoUsuario = (Integer) session.getAttribute("usuario");
		
		// Parametros mandados por el usuario
		String nombre = (String) request.getParameter("nombre");
		String clave = (String) request.getParameter("clave");
		String clave2 = (String) request.getParameter("clave2");
		String telefono = (String) request.getParameter("telefono");
		String direccion = (String) request.getParameter("direccion");
		String tipopago = (String) request.getParameter("tipopago");
		String cuenta = (String) request.getParameter("cuenta");
		String tarjeta = (String) request.getParameter("tarjeta");
		int tipoPago = tipopago.equals("transferencia")?1:0;
		
		System.err.println("Nombre de usuario: " + nombre);
		System.err.println("clave: " + clave);
		System.err.println("clave2: " + clave2);
		System.err.println("Teléfono: " + telefono);
		System.err.println("Dirección: " + direccion);
		System.err.println("Tipo de pago: " + tipopago);
		System.err.println("Cuenta: " + cuenta);
		System.err.println("Tarjeta: " + tarjeta);
		
		
		// ERRORES //
		// Si el usuario no se puede obtener el usuario
		if (codigoUsuario == null || codigoUsuario<=0 ) {
			session.setAttribute("mensaje", "No se pudo cargar el usuario");
			response.sendRedirect("html/usuario.jsp");
			return;
		}
		
		// Direccion equivocada
		if (direccion.equals("")) {
			session.setAttribute("mensaje", "Los campos con <i class=\"bi bi-asterisk\"></i> son obligatorios");
			response.sendRedirect("html/usuario_datos.jsp");
			return;
		}
		
		// Ambas contraseñas no coinciden
		if (!clave.equals(clave2)) {
			session.setAttribute("mensaje", "Las contraseñas deben coincidir o dejarse vacías");
			response.sendRedirect("html/usuario_datos.jsp");
			return;
		}
		
		// TODOS LOS CAMPOS CORRECTOS //
		var con = AccesoBD.getInstance();
		if(con.modificarUsuario(nombre, clave, direccion, telefono, tipoPago, tarjeta, cuenta, codigoUsuario)) {
			session.setAttribute("mensaje", "Se modificó correctamente el usuario");
			response.sendRedirect("html/usuario.jsp");
		} else {
			session.setAttribute("mensaje", "Problema con el acceso a la base de datos");
			response.sendRedirect("html/usuario_datos.jsp");
		}
	}
}
