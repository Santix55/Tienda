package p2;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class RecogerCarrito
 */
@WebServlet("/RecogerCarrito")
public class RecogerCarrito extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.err.println("\nRecoger Carrito");
		
		HttpSession session = request.getSession(true);
		Integer codigoUsuario = (Integer)session.getAttribute("usuario");
		
		System.err.println("codigo de usuario: "+codigoUsuario);
		
		if(codigoUsuario==null || codigoUsuario<=0) { // Si no esta registrado
			session.setAttribute("mensaje", "Debes de registrarte antes de compar");
			response.sendRedirect("html/usuario.jsp");
		} else {
			ArrayList<Producto> productos = convertirJSONCarrito(request);
			session.setAttribute("carrito", productos);
			response.sendRedirect("html/compra.jsp");
		}
		
	}

	private ArrayList<Producto> convertirJSONCarrito(HttpServletRequest request)  {
		var carritoJSON = new ArrayList<Producto>();

		try {
			JsonReader jsonReader = Json.createReader(
					new InputStreamReader(request.getInputStream()));
			JsonArray jobj = jsonReader.readArray();
			
			for(int i=0; i<jobj.size(); i++) {
				JsonObject prod = jobj.getJsonObject(i);
				var nuevo = new Producto();
				nuevo.setCodigo(prod.getInt("codigo"));
				nuevo.setNombre(prod.getString("nombre"));
				nuevo.setPrecio(prod.getInt("precio"));
				nuevo.setCantidad(prod.getInt("cantidad"));
				carritoJSON.add(nuevo);
			}
		} catch (IOException e) {
			System.err.println("Problema con request.getInputSteam()");
			e.printStackTrace();
		}
		
		return carritoJSON;
	}
}
