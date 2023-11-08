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
		
		<!-- SECCIÃN ADAPTABLE -->
		<div class="vh-100">
		  <div class="container py-5 h-100">
		    <div class="row d-flex align-items-center justify-content-center h-100">
		      <div class="col-md-8 col-lg-7 col-xl-6">
		        <img src="img/pawmotLogin.png"
		          class="img-fluid" alt="Phone image">
		      </div>
		      <div class="col-md-7 col-lg-5 col-xl-5 offset-xl-1">
		      
		      	<!-- FORMULARIO DE REGISTRO -->
		        <form method="post" onsubmit="ProcesarForm(this,'Signup','cuerpo'); return false">
		          <!-- Usuario -->
		          <div class="form-outline mb-4">
		            <input type="text" name="usuario" class="form-control form-control-lg">
		            <label class="form-label" ><i class="bi bi-person-circle"></i> Usuario <span class="text-danger"><i class="bi bi-asterisk"></i></span></label>
		          </div>
		
		          <!-- ContraseÃ±a -->
		          <div class="form-outline mb-4">
		            <input type="password" name="clave" class="form-control form-control-lg" >
		            <label class="form-label" ><i class="bi bi-key-fill"></i> Contraseña <span class="text-danger"><i class="bi bi-asterisk"></i></span></label>
		          </div>
		          
		          <!-- Repetir contraseÃ±a -->
		          <div class="form-outline mb-4">
		            <input type="password" name="clave2" class="form-control form-control-lg" >
		            <label class="form-label" ><i class="bi bi-key-fill"></i> Repetir Contraseña <span class="text-danger"><i class="bi bi-asterisk"></i></span></label>
		          </div>
		          
		          <!-- Nombre -->
		          <div class="form-outline mb-4">
		            <input type="text" name="nombre" class="form-control form-control-lg" >
		            <label class="form-label" ><i class="bi bi-person-fill"></i> Nombre y Apellidos</label>
		          </div>
		          
		          <!-- Telefono -->
		          <div class="form-outline mb-4">
		            <input type="text" name="telefono" class="form-control form-control-lg" >
		            <label class="form-label" ><i class="bi bi-telephone-fill"></i> Telefono</label>
		          </div>
		          
		           <!-- DirecciÃ³n -->
		          <div class="form-outline mb-4">
		            <input type="text" name="direccion" class="form-control form-control-lg" >
		            <label class="form-label" ><i class="bi bi-geo-alt-fill"></i> Dirección <span class="text-danger"><i class="bi bi-asterisk"></i></span></label>
		          </div>
		          
		          <!-- Linea de radio buttons -->
		          <div class="form-outline mb-4">
			          <div class="form-check form-check-inline">
					      <input class="form-check-input" type="radio" name="login" value="acceso" onclick="Cargar('./html/usuario.jsp','cuerpo')">
					      <label class="form-label" >Acceso</label>
					  </div>
	
					 <div class="form-check form-check-inline">
					       <input class="form-check-input" type="radio" name="login" value="registro" checked>
					       <label class="form-label" >Registro</label>
					  </div>
				  </div>

		
		          <!-- Botón para entrar -->
		          <button type="submit" class="btn btn-primary btn-lg btn-block"><i class="bi bi-box-arrow-in-right"></i> Registrarse</button>
		          <br><br>
		          <h5><span class="text-danger"> <%=mensaje %> </span></h5>
		        </form>
		        
		      </div>
		    </div>
		 </div>
		</div>
		
	<footer>
	</footer>
</html>