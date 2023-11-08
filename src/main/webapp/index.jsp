<%@ page language="java" contentType="text/html; charset=UTF-8" import="p2.*, java.util.List"
pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		
		<!-- BOOTSTRAP LINK -->
		<link
			href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css"
			rel="stylesheet"
			integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65"
			crossorigin="anonymous">
		
		<!-- ICONOS -->
		<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.3/font/bootstrap-icons.css">
			
		<!-- SCRIPT LIBCAPAS -->
		<script src="./js/libCapas2223.js"></script>
		
		<!-- SCRIPT GESTIÓN DEL CARRITO -->
		<script src="./js/carrito.js"></script>

		<!-- PESTAÑA -->
		<title>Tienda Pokemon</title>
		<link rel="icon" type="image/x-icon" href="img/logo.png">
		
		<!-- NOMBRE -->
		
		
		<!-- CARGAR EN JS EL MÁXIMO DE STOCK DE LOS PRODUCTOS -->
		<%
		AccesoBD con = AccesoBD.getInstance();
		List<ProductoBD> productos = con.obtenerProductosBD();
		%>
		
		<%for(ProductoBD producto:productos) {%>
		<script>
			productoStock.set(<%=producto.getCodigo()%>,<%=producto.getStock()%>);
		</script>
		<%}%>

	</head>
	
	<body onload="Cargar('./html/inicio.html','cuerpo'); iniciarCarrito();"> <!-- cargar inicio.html al principio -->

		
		<!-- BARRA SUPERIOR -->	
		<nav class="navbar fixed-top navbar-expand-md navbar-dark bg-dark">
			<div class="container-fluid">
				<a class="navbar-brand" href="#">
					Tienda Pokemon
					<img src="img/logo.png" height="28" alt="CoolBrand">
					<output id="nombre-usuario-registrado">
					  <% if (session.getAttribute("username") == null) { %>
					    <%= "" %>
					  <% } else { %>
					    <i class="bi bi-person-circle"></i><%= session.getAttribute("username") %>
					  <% } %>
					</output>
					
				</a>
				<button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarsExampleDefault">
					<span class="navbar-toggler-icon"> </span>
				</button>
				
				<div class="collapse navbar-collapse" id="navbarsExampleDefault">
				
					<!-- Menú de navegación -->
					<ul class="navbar-nav me-auto">
						<li class="nav-item"><a class="nav-link" href="#" onclick="Cargar('./html/inicio.html','cuerpo')">Inicio</a>
						</li>
						<li class="nav-item"><a class="nav-link" href="#" onclick="Cargar('./html/productos.jsp','cuerpo')">Productos</a>
						</li>
						<li class="nav-item"><a class="nav-link" href="#" onclick="Cargar('./html/empresa.html','cuerpo')">Empresa</a>
						</li>
						<li class="nav-item"><a class="nav-link" href="#" onclick="Cargar('./html/contacto.html','cuerpo')">Contacto</a>
						</li>
						<li class="nav-item"><a class="nav-link" href="#" onclick="Cargar('./html/usuario.jsp','cuerpo')">Usuario</a>
						</li>
						<li class="nav-item"><a class="nav-link" href="#" onclick="Cargar('./html/carrito.html','cuerpo'); setTimeout(cargarApartadoCarrito,100)">Carrito</a>
						</li>
					</ul>
					
					<!-- Carrito -->
					<div class="navbar-nav dropdown">
						<a class="nav-link dropdown-toggle" href="#" id="navbarDropdown"
						role="button" data-bs-toggle="dropdown"> <i class="bi bi-cart"></i> <output id="numero de productos">0 productos</output> | <output id="precio total">0</output>₽ <i class="bi bi-cash-coin"></i> </a>
						<ul class="dropdown-menu" id="carrito desplegable">
							<li class="dropdown-item"><button type="button" class="btn btn-success"><i class="bi bi-cart-check"></i> Comprar </button>0â½</li>
						</ul>
					</div>
					
				</div>
			</div>
		</nav>
		
		<!-- SCRIPT DE BOOTSTARPT -->
		<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4"
		crossorigin="anonymous"></script>
		
		<!-- CARGAR UNO DE LOS HTML DEL CUERPO -->
		<div id="cuerpo"></div>	
		
		<footer>
		</footer>
	</body>

</html>