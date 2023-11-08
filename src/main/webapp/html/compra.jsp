<%@ page language="java" contentType="text/html; charset=UTF-8" import="p2.*, java.util.ArrayList"
pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<br><br><br>

		<!-- SECCIÓN ADAPTABLE -->
		<div class="vh-100">
		  <div class="container py-5 h-100">
		    <div class="row d-flex align-items-center justify-content-center h-100">
		       <!-- IZQUIERDA -->
		      <div class="col-md-8 col-lg-7 col-xl-6">
		      
		        <!-- LISTA DE PRODUCTOS A COMPRAR -->
		        <% 
		        Object carritoObj = session.getAttribute("carrito");
		        ArrayList<Producto> carrito = carritoObj==null? new ArrayList<Producto>() : (ArrayList<Producto>) carritoObj;

				%>
				<!-- PRINCIPIO CARRO -->
				<div class="col-md-5 col-lg-4 order-md-last">
					<h4 class="d-flex justify-content-between align-items-center mb-3">
						<span class="text-primary">Tu carro</span> <span
							class="badge bg-primary rounded-pill"><%=carrito.size()%></span>
					</h4>
					<ul class="list-group mb-3">
						<%for(Producto p: carrito) {%>
						<li class="list-group-item d-flex justify-content-between lh-sm">
							<div>
								<h6 class="my-0"><%=p.getNombre()%> x <%=p.getCantidad()%><br>
								</h6>
								<small class="text-muted"><%=p.getPrecio()%>₽ unidad</small>
							</div> <span class="text-muted"><%=p.getCantidad() * p.getPrecio()%></span>
						</li>
						<%}%>
						
						<%
						int precioTotal = 0;
						for(Producto p: carrito)
							precioTotal += p.getCantidad() * p.getPrecio();
						%>
						<li class="list-group-item d-flex justify-content-between"><span>Total
								(PokeDollar)</span> <strong><%=precioTotal %>₽</strong></li>
					</ul>
				</div>
				<!-- FIN CARRO -->
		      </div>
		      
		      <!-- DERECHA -->
		      <div class="col-md-7 col-lg-5 col-xl-5 offset-xl-1">
		      	<%
		      	AccesoBD con = AccesoBD.getInstance(); 
		      	Object usuarioObj = session.getAttribute("usuario");
		      	int codigoUsuario = (Integer) usuarioObj;
		      	Usuario usuario = con.getUsuario(codigoUsuario);
		      	%>
		      	
		      	<script>
		      		function setTarjeta() {
		      			document.getElementById("labelPago").innerHTML = '<i class="bi bi-credit-card"></i> Tarjeta de crédito';
		      			document.getElementById("tarjeta/cuenta").value = " <%=usuario.getTarjeta()%>";
		      		}
		      		function setTransferencia() {
		      			document.getElementById("labelPago").innerHTML = '<i class="bi bi-credit-card"></i> Cuenta';
		      			document.getElementById("tarjeta/cuenta").value = " <%=usuario.getCuenta()%>";
		      		}
		      	</script>
		      
		      	<!-- FORMULARIO PARA LA COMPRA -->
		        <form method="post" onsubmit="ProcesarForm(this,'RealizarCompra','cuerpo'); return false">
		          <!-- Nombre -->
		          <div class="form-outline mb-4">
		            <input type="text" name="nombre" class="form-control form-control-lg" value="<%=usuario.getNombre()%>">
		            <label class="form-label"><i class="bi bi-person-fill"></i> Nombre y Apellidos</label>
		          </div>
		          
		          <!-- Telefono -->
		          <div class="form-outline mb-4">
		            <input type="text" name="telefono" class="form-control form-control-lg" value="<%=usuario.getTelefono()%>">
		            <label class="form-label" ><i class="bi bi-telephone-fill"></i> Telefono</label>
		          </div>
		          
		           <!-- Dirección -->
		          <div class="form-outline mb-4">
		            <input type="text" name="direccion" class="form-control form-control-lg" value="<%=usuario.getDomicilio()%>">
		            <label class="form-label" ><i class="bi bi-geo-alt-fill"></i> Dirección</label>
		          </div>
		          
		          <!-- Tipo de pago -->
		          <div class="form-outline mb-4">
			          <div class="form-check form-check-inline">
					      <input class="form-check-input" type="radio" name="tipopago" value="tarjeta" onclick="setTarjeta()" <%if(usuario.getTipopago()==0){%>checked<%}%>>
					      <label class="form-label">Tarjeta</label>
					  </div>
	
					 <div class="form-check form-check-inline">
					       <input class="form-check-input" type="radio" name="tipopago" value="transferencia" onclick="setTransferencia()" <%if(usuario.getTipopago()==1){%>checked<%}%>>
					       <label class="form-label">Transferencia</label>
					  </div>
				  </div>
		          
		          <!-- Tarjeta de crédito o cuenta-->
		          <div class="form-outline mb-4">
		            <input type="text" id="tarjeta/cuenta" name="tarjeta/cuenta" class="form-control form-control-lg" value="<%=usuario.getTipopago()==0?usuario.getTarjeta():usuario.getCuenta() %>">
		            <label class="form-label" id="labelPago"><i class="bi bi-credit-card"></i> <%=usuario.getTipopago()==0?"Tarjeta de crédito":"Cuenta"%></label>
		          </div>
		          
		          <!-- Botón para modificar -->
		          <button type="submit" class="btn btn-success btn-lg btn-block" onclick="Cargar('./html/usuario_registrado.html','cuerpo')"><i class="bi bi-cart-check"></i> FINALIZAR COMPRA</button>
		          
		        </form>
		        <% // Utilizamos una variable en la sesión para informar de los mensajes de Error
				String mensaje = (String)session.getAttribute("mensaje");
				if(mensaje != null) {
					session.removeAttribute("mensaje");
				} else {
					mensaje = "";
				}
				%>
		        <h4 class="text-danger"><%=mensaje %></h4>
		        
		      </div>
		    </div>
		 </div>
		</div>
</body>
</html>