<%@page language="java" contentType="text/html charset=UTF-8" import="p2.*,java.util.List" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">

	<head>
		<meta charset="UTF-8">
		<title>Tienda Pokemon</title>
	</head>

	<body>
		<%
		// Utilizamos una variable en la sesión para informar de los mensajes de Error
		String mensaje = (String)session.getAttribute("mensaje");
		if(mensaje != null) {
			session.removeAttribute("mensaje");
		} else {
			mensaje = "";
		}
		
		// Si no hay usuario o este no es válido
		if((session.getAttribute("usuario")==null) || (Integer)session.getAttribute("usuario")<=0)
		{
		%>
		<!-- PÁGINA DE LOGIN -->
		<!-- SECCIÓN ADAPTABLE -->
		<div class="vh-100">
		  <div class="container py-5 h-100">
		    <div class="row d-flex align-items-center justify-content-center h-100">
		      <div class="col-md-8 col-lg-7 col-xl-6">
		        <img src="img/pawmotLogin.png"
		          class="img-fluid" alt="Phone image">
		      </div>
		      <div class="col-md-7 col-lg-5 col-xl-5 offset-xl-1">
		      
		      	<!-- FORMULARIO DE AUTENTICACION -->
		        <form method="post" onsubmit="ProcesarForm(this,'Login','cuerpo'); return false">
		          <!-- URL oculta -->
		          <div class="form-outline mb-4">
		        	<input type="hidden" name="url" value="html/usuario.jsp">
		          </div>
		          
		          <!-- Usuario -->
		          <div class="form-outline mb-4">
		            <input type="text" name="usuario" class="form-control form-control-lg">
		            <label class="form-label"><i class="bi bi-person-circle"></i> Usuario</label>
		          </div>
		
		          <!-- Contraseña -->
		          <div class="form-outline mb-4">
		            <input type="password" name="clave" class="form-control form-control-lg">
		            <label class="form-label"><i class="bi bi-key-fill"></i> Contraseña</label>
		          </div>
		          
		          <!-- Linea de radio buttons -->
		          <div class="form-outline mb-4">
			          <div class="form-check form-check-inline">
					      <input class="form-check-input" type="radio" name="login" value="acceso" checked>
					      <label class="form-label">Acceso</label>
					  </div>
	
					 <div class="form-check form-check-inline">
					       <input class="form-check-input" type="radio" name="login" value="registro" onclick="Cargar('./html/usuario_registro.jsp','cuerpo')">
					       <label class="form-label">Registro</label>
					  </div>
				  </div>

		
		          <!-- Botón para entrar -->
		          <button type="submit" class="btn btn-primary btn-lg btn-block"><i class="bi bi-box-arrow-in-right"></i> Entrar</button>
		          <br>
		          <br>
		          <h4 class="text-danger"><%=mensaje %></h4>
		        </form>
		        
		      </div>
		    </div>
		 </div>
		<%
		}
		else
		{
		%>
			
			<!-- OPCIONES DE USUARIO -->
			<br>
			<br>
			<br>
			
			<form method="post" onsubmit="ProcesarForm(this,'Logout','cuerpo'); return false">
				<button type="submit" class="btn btn-outline-danger"><i class="bi bi-x-square-fill"></i> Cerrar sesión</button> <h3 class="text-danger"><%=mensaje %></h3>
			</form>
			
			<p class="h1">
			<strong> Bienvenido </strong>
			</p>
			
			<p class="h4">
			En la sección de Usuario puedes consultar tus pedidos o <a href="#" onclick="Cargar('./html/usuario_datos.jsp','cuerpo')"><i class="bi bi-person-vcard"></i> consultar o modificar tus datos personales</a>
			</p>
			
			<hr class="hr" >
			
			<%
			AccesoBD con = AccesoBD.getInstance();
			List<PedidoBD> pedidos = con.obtenerPedidosBD((Integer) session.getAttribute("usuario"));
			%>
			
			<%for (PedidoBD pedido: pedidos) { %>
				<%if (pedido.getEstado() == pedido.PENDIENTE) {%>
					<form method="post" onsubmit="ProcesarForm(this,'CancelarPedido','cuerpo'); return false">
						<input type="hidden" name="codigo" value="<%=pedido.getCodigo()%>"></input>
						<button type="submit" class="btn btn-danger"> <i class="bi bi-x-lg"></i> Cancelar Pedido Pendiente </button>
					</form>
				<%}
				else if(pedido.getEstado() == pedido.CANCELADO) {%>
					<button type="button" class="btn btn-outline-danger" disabled> <i class="bi bi-x-lg"></i> Pedido Cancelado </button>
				<%}
				else if(pedido.getEstado() == pedido.ENVIADO) {%>
					<button type="button" class="btn btn-outline-primary" disabled> <i class="bi bi-send-fill"></i> Pedido Enviado </button>
				<%} 
				else if(pedido.getEstado() == pedido.ENTREGADO) {%>
					<button type="button" class="btn btn-outline-success" disabled> <i class="bi bi-check2"></i> Pedido Entregado </button>
				<%}%>
				
				<p class="h3"><strong> ID DE PEDIDO: <%=pedido.getCodigo()%> </strong></p>
				<p class="h3"> Total = <%=pedido.getImporte() %>&#8381</p>
				<p class="h4"> Fecha = <%=pedido.getFecha() %></p><p class="h3">
				
				<table class="table table-striped">
				  <thead>
				    <tr>
				      <th scope="col">Nombre</th>
				      <th scope="col">Codigo</th>
				      <th scope="col">Unidades</th>
				      <th scope="col">Precio Unitario (&#8381)</th>
				      <th scope="col">Precio (&#8381)</th>
				    </tr>
				  </thead>
				  
				  <tbody>
				  
				  	<%for (Producto producto: pedido.getProductos()) {%>
				  	<tr>
				  	  <!-- Nombre -->
				      <th scope="row"><%=producto.getNombre()%></th>
				      
				      <!-- Codigo -->
				      <th scope="row"><%=producto.getCodigo()%></th>
				      
				      <!-- Unidades -->
				      <td><%=producto.getCantidad() %></td>
				      
				      <!-- Precio Unitario -->
				      <td><%=producto.getPrecio() %></td>
				      
				      <!-- Precio-->
				      <td><%=producto.getPrecio() * producto.getCantidad() %></td>
				  	</tr>
				  	<%}%>
				  	
				  </tbody>
				 </table>
				 <hr class="hr" >
		<%}
	  }%>
		</div>
		<footer>
		</footer>
	</body>
</html>