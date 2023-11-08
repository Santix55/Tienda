<%@page language="java" contentType="text/html charset=UTF-8" import="p2.*" pageEncoding="UTF-8" %>
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
		%>
	
		<%
		AccesoBD con = AccesoBD.getInstance(); 
		Object usuarioObj = session.getAttribute("usuario");
		int codigoUsuario = (Integer) usuarioObj;
		Usuario usuario = con.getUsuario(codigoUsuario);
		%>
		      	
		
		<!-- SECCIÓN ADAPTABLE -->
		<div class="vh-100">
		  <div class="container py-5 h-100">
		    <div class="row d-flex align-items-center justify-content-center h-100">
		      <div class="col-md-8 col-lg-7 col-xl-6">
		        <img src="img/pawmotLogin.png"
		          class="img-fluid" alt="Phone image">
		      </div>
		      <div class="col-md-7 col-lg-5 col-xl-5 offset-xl-1">
		      
		      	<!-- FORMULARIO DE CONSULTA O MODIFICACIÃN -->
		        <form method="post" onsubmit="ProcesarForm(this,'ModificarUsuario','cuerpo'); return false">
		          <!-- Nombre -->
		          <div class="form-outline mb-4">
		            <input type="text" name="nombre" class="form-control form-control-lg" value="<%=usuario.getNombre()%>">
		            <label class="form-label"><i class="bi bi-person-fill"></i> Nombre y Apellidos</label>
		          </div>
		          
		          <!-- ContraseÃ±a -->
		          <div class="form-outline mb-4">
		            <input type="password" name="clave" class="form-control form-control-lg" >
		            <label class="form-label" ><i class="bi bi-key-fill"></i> Contraseña <span class="text-info"><i class="bi bi-asterisk"></i> Deja este campo vacío para dejar la anterior</span></label>
		          </div>
		          
		          <!-- Repetir contraseÃ±a -->
		          <div class="form-outline mb-4">
		            <input type="password" name="clave2" class="form-control form-control-lg" >
		            <label class="form-label" ><i class="bi bi-key-fill"></i> Repetir Contraseña <span class="text-info"><i class="bi bi-asterisk"></i></span></label>
		          </div>
		          
		          <!-- Telefono -->
		          <div class="form-outline mb-4">
		            <input type="text" name="telefono" class="form-control form-control-lg" value="<%=usuario.getTelefono()%>">
		            <label class="form-label" ><i class="bi bi-telephone-fill"></i> Telefono</label>
		          </div>
		          
		          <!-- DirecciÃ³n -->
		          <div class="form-outline mb-4">
		            <input type="text" name="direccion" class="form-control form-control-lg" value="<%=usuario.getDomicilio()%>">
		            <label class="form-label" ><i class="bi bi-geo-alt-fill"></i> Dirección <span class="text-danger"><i class="bi bi-asterisk"></i></span></label>
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
		          
		          <!-- Tarjeta de credito -->
		          <div class="form-outline mb-4">
		            <input type="text" name="tarjeta" class="form-control form-control-lg" value="<%=usuario.getTarjeta()%>">
		            <label class="form-label" ><i class="bi bi-credit-card"></i> Tarjeta</label>
		          </div>
		          
		          <!-- Cuenta Bancaria -->
		          <div class="form-outline mb-4">
		            <input type="text" name="cuenta" class="form-control form-control-lg" value="<%=usuario.getCuenta()%>">
		            <label class="form-label" ><i class="bi bi-credit-card"></i> Cuenta bancaria</label>
		          </div>
		          
		          
		          <!-- Botón para modificar -->
		          <button type="submit" class="btn btn-success btn-lg btn-block"><i class="bi bi-pen"></i> Modificar</button>
		          <button type="button" class="btn btn-secondary btn-lg btn-block" onclick="Cargar('./html/usuario.jsp','cuerpo')"><i class="bi bi-arrow-return-left"></i> Volver</button>
		          <h5><span class="text-danger"> <%=mensaje %> </span></h5>
		        </form>
		        
		      </div>
		    </div>
		 </div>
		</div>
		
	<footer>
	</footer>
</html>