<%@ page language="java" contentType="text/html; charset=UTF-8" import="p2.*, java.util.List"
pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">

	<head>
		<meta charset="UTF-8">
		<title>Tienda Pokemon</title>
	</head>
	
	<body>
		<%
		AccesoBD con = AccesoBD.getInstance();
		List<ProductoBD> productos = con.obtenerProductosBD();
		%>
		<p>Productos JSP</p>
		<div class="container">
			<div class="row">

			<%
				for(ProductoBD producto: productos) {
					int c = producto.getCodigo();
					%>
					<script>limitarStockProducto(<%=c%>)</script>
					<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12" style="margin-bottom:40px;">
					<img id="imagen<%=c%>" src="<%=producto.getImagen()%>" class="img-thumbnail" alt="img/producto0.png"><br>
					<table>
						<tr>
							<td colspan="2"> <h6 style="text-align: center;" id="nombre<%=c%>"><%=producto.getNombre()%></h6> </td>
						</tr>
						<tr>
							<td colspan="2"> <h6 style="text-align: center;"> <output id="precio<%=c%>"><%=producto.getPrecio()%></output>₽</h6> </td>
						</tr>
						<tr>
							<td><input type="number" style="width: 75px;" value="1" min="1" max="<%=producto.getStock()%>" class="form-control" id="cantidad<%=c%>"></td>
							<td><button type="button" class="btn btn-primary"  onclick="anyadirAlCarrito(<%=c%>)" id="comprar<%=c%>"><i class="bi bi-bag-plus"></i> Comprar</button></td>
						</tr>
					</table>
					<p><small><%=producto.getDescripcion()%></small></p>
					</div>
					<%
				}
			%>
			</div>
		</div>
	
		<footer>
		</footer>
	</body>
</html>