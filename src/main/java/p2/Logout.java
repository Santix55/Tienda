package p2;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Logout
 */
@WebServlet(description = "Cierra sesión y borra todas las variables", urlPatterns = { "/Logout" })
public class Logout extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.err.println("\nPost Logout");
		
		/*
		HttpSession session = request.getSession(true);
		session.removeAttribute("usuario");
		response.sendRedirect("html/usuario.jsp");
		*/
		
		HttpSession session = request.getSession(false);
		if (session != null) {
		    session.invalidate();
		}
		response.sendRedirect("html/usuario.jsp");
	}

}
