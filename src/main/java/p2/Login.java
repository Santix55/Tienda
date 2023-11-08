package p2;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.err.println("\nPost Login");
		
		String url = request.getParameter("url");
		String usuario = request.getParameter("usuario");
		String clave = request.getParameter("clave");
		HttpSession session = request.getSession(true);
		
		System.err.println("url: "+url);
		System.err.println("usuario: "+usuario);
		System.err.println("clave:"+clave);
		
		AccesoBD con = AccesoBD.getInstance();
		if((usuario!=null)&&(clave!=null)) {
			int codigo = con.comprobarUsuarioBD(usuario,clave);
			if(codigo>0) {
				session.setAttribute("usuario",codigo);
			}
			else {
				session.setAttribute("mensaje", "Usuario y/o clave incorrectos");
			}
		}
		
		System.err.println("Redirección");
		response.sendRedirect(url);
		//doGet(request, response);
	}

}
