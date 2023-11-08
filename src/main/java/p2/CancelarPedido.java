package p2;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CancelarPedido
 */
@WebServlet("/CancelarPedido")
public class CancelarPedido extends HttpServlet {
	private static final long serialVersionUID = 1L;


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.err.println("CancelarPedido");
		
		var codigoPedido_str = request.getParameter("codigo");
		int codigoPedido = Integer.parseInt(codigoPedido_str);
		System.err.println("Código de pedido : "+codigoPedido);
		
		var con = AccesoBD.getInstance();
		con.cancelarPedido(codigoPedido);
		
		request.getSession(true).setAttribute("mensaje", "<script>vaciarCarrito("+con.getStockMapJS()+")</script>");
		
		response.sendRedirect("html/usuario.jsp");
	}

}
