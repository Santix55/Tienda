package p2;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Signup
 */
@WebServlet("/Signup")
public class Signup extends HttpServlet {
	private static final long serialVersionUID = 1L;


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.err.println("\nSignUp");
		
		HttpSession session = request.getSession(true);
		AccesoBD con = AccesoBD.getInstance();
		String usuario = request.getParameter("usuario");
		String nombre = request.getParameter("nombre");
		String clave = request.getParameter("clave");
		String clave2 = request.getParameter("clave2");
		String telefono = request.getParameter("telefono");
		String direccion = request.getParameter("direccion");
		
		System.err.println("usuario: "+usuario);
		System.err.println("nombre: "+nombre);
		System.err.println("clave: "+clave);
		System.err.println("clave2: "+clave2);
		System.err.println("telefono: "+telefono);
		System.err.println("direccion: "+direccion);
		
		String mensaje = "";
		if (esVacio(usuario) || esVacio(clave) || esVacio(clave2) || esVacio(direccion))
			mensaje = "Los campos con <i class=\"bi bi-asterisk\"></i> no se pueden dejar vacios";
		else if (!clave.equals(clave2))
			mensaje = "No has puesto 2 veces la misma contraseña";
		else if (con.existeUsuario(usuario))
			mensaje = "Ese nombre de usuario ya esta en uso, elige otro";
		else if (telefono.length()>=9)
			mensaje = "Telefono muy largo";
		else if (usuario.length()>=32)
			mensaje = "Nombre de usuario muy largo";
		else if (direccion.length()>=32)
			mensaje = "Direccion muy larga";
		
		if(esVacio(mensaje)) { // Todo correcto
			int codigoUsuario = con.registrarUsuarioBD(usuario, nombre, clave, direccion, telefono);
			session.setAttribute("usuario",codigoUsuario);
			response.sendRedirect("html/usuario.jsp");
		} 
		else { // Hay algún error
			session.setAttribute("mensaje",mensaje);
			response.sendRedirect("html/usuario_registro.jsp");
		}
		
		
	}
	
	private boolean esVacio(String s) {
		return s.equals("");
	}

}
