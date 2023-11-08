console.log("carrito.js");


/*
⠀⠈⠛⠻⠶⣶⡄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠈⢻⣆⣀⣀⣀⣀⣀⣀⣀⣀⣀⣀⣀⣀⣀⣀⣀⣀⣀⣀⣀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⢻⡏⠉⠉⠉⠉⢹⡏⠉⠉⠉⠉⣿⠉⠉⠉⠉⠉⣹⠇⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠈⣿⣀⣀⣀⣀⣸⣧⣀⣀⣀⣀⣿⣄⣀⣀⣀⣠⡿⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠀⠸⣧⠀⠀⠀⢸⡇⠀⠀⠀⠀⣿⠁⠀⠀⠀  ⣿⠃⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠀⠀⢹⣧⣤⣤⣼⣧⣤⣤⣤⣤⣿⣤⣤⣤⣼⡏⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢿⠀⠀⢸⡇⠀⠀⠀⠀⣿⠀⠀  ⢠⡿⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢸⣷⠤⠼⠷⠤⠤⠤⠤⠿⠦⠤⠾⠃⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣾⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠀⠀⢾⣷⢶⣶⠶⠶⠶⠶⠶⠶⣶⠶⣶⡶⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠸⣧⣠⡿⠀⠀⠀⠀⠀⠀⢷⣄⣼⠇⠀
*/


/*
  __  __         _     _               ___         _           _         _         
 |  \/  |___  __| |___| |___   _  _   / __|___ _ _| |_ _ _ ___| |__ _ __| |___ _ _ 
 | |\/| / _ \/ _` / -_) / _ \ | || | | (__/ _ \ ' \  _| '_/ _ \ / _` / _` / _ \ '_|
 |_|  |_\___/\__,_\___|_\___/  \_, |  \___\___/_||_\__|_| \___/_\__,_\__,_\___/_|  
                               |__/ 
*/

/**
 * Array de objetos de productos
 * Cada producto tiene:
 *  - codigo
 *  - nombre
 *  - cantidad
 *  - precio
 *  - imagen
 */
let carrito = [];

/**
 * Clave: código del producto
 * Valor: stock máximo de dicho producto
 */
let productoStock = new Map();

/**
 * Se ejecuta al cargar la página. Inicaliza el carrito localStorage si es posible.
 */
function iniciarCarrito() {
	try {
		const carritoJSON = localStorage.getItem("carrito");
		carrito = JSON.parse(carritoJSON);
		if(carrito == null)
			carrito = [];
		else
			console.log("Se cargó el carrito a partir de localStorage");
	} catch (exception) {
		console.log("No se cargó el carrito a partir de localStorage");
		carrito = [];
	}
	cargarMenuCarrito();
	console.log(carrito);
}

/**
 * Anyade al carrito un producto
 * @param idx es el código del producto
 */
function anyadirAlCarrito(idx) {
	console.log("anyadirAlCarrito "+idx);
	
	// Obtener los datos de la interfaz
	let codigo = idx;
	let nombre = document.getElementById("nombre"+idx).textContent;
	let cantidad = parseInt(document.getElementById("cantidad"+idx).value);
	let precio = parseInt(document.getElementById("precio"+idx).textContent);
	let imagen = document.getElementById("imagen"+idx).getAttribute("src");
	
	// Evitar que haya que el usuario inserte el máximo a mano y sobrepase el stock
	if(cantidad > document.getElementById("cantidad"+idx).max) {
		alert("No se añadió al carrito, ya que no hay tanto stock");
		return;
	}
	
	/*
	// Mostrar los datos de la interfaz, para ver si son correctos
	console.log("codigo: "+codigo);
	console.log("nombre: "+nombre);
	console.log("cantidad: "+cantidad);
	console.log("pecio: "+precio)
	*/
	
	//  Buscar si ya hay productos con el mismo códgo y añadir al carrito
	let anyadido = false;
	for(let producto of carrito){
		if(producto.codigo == codigo) {
			producto.precio = precio;
			producto.imagen = imagen;
			producto.cantidad += cantidad;
			anyadido = true;
		}
	}
	
	// Si no esta en el carrito insertar, el nuevo producto
	if(!anyadido){
		let producto = new Object();
		producto.codigo = codigo;
		producto.nombre = nombre;
		producto.cantidad = cantidad;
		producto.precio = precio;
		producto.imagen = imagen;
		carrito.push(producto);
	}
		
	limitarStockProducto(codigo);
	
	console.log(carrito);
	cargarMenuCarrito();
	
	guardarCarrito();
	
	alert("Se han añadido "+cantidad+" unididad"+(cantidad==1?"":"es")+" de "+nombre);
}

/**
 * Borra el elemento del carrito y actualiza la vista
 * @param idx índice del elemento del carrito a borrar
 */
function borrarDelCarrito(idx) {
	console.log("borrarDelCarrito "+idx);
	alert("Se han eliminado todas las unidades de "+carrito[idx].nombre);
	
	const codigo = carrito[idx].codigo;
	carrito.splice(idx,1);
	console.log(carrito);
	cargarMenuCarrito();
	
	// intenta actualizar la lista del apartado carrito, si esta en el apartado "Carrito"
	try{cargarApartadoCarrito();} catch(e){/*Nada*/}
	
	// intenta quitar el limite de compra de producto, si esta en el apartado "Productos"
	try{limitarStockProducto(codigo);}catch(e){/*Nada*/}

	guardarCarrito();
}

/**
 * Se llama cuando se modifica la cantidad de un producto en el carrito
 * @param idx índice del elemento del carrito a borrar
 */
function modificarCantidad(idx) {
	// Eliminado por molesto
	//alert("Se ha modificado la cantidad de "+carrito[idx].nombre);
	
	console.log("modifcarCantidad "+idx);
	const nuevaCantidad = parseInt(document.getElementById("inputCantidad"+idx).value);
	const max = parseInt(document.getElementById("inputCantidad"+idx).max);
	
	if(nuevaCantidad>max) {
		alert("Sobrepasaste el limite de existencias, no se modificó la cantidad");
		return;
	}
	
	carrito[idx].cantidad = nuevaCantidad;
	cargarMenuCarrito();
	cargarApartadoCarrito();
}

/**
 * Guarda en localStorage el carrito
 */
function guardarCarrito() {
	localStorage.setItem("carrito",JSON.stringify(carrito));
}

/**
 * Se llama cuando se ha realizado un intento de compra (con o sin éxito) y se desea volver a cargar toda la interfaz
 * @param nuevoStock Nuevo stock que se obtiene de la base de datos tras realizar la compra, si es null no se actualiza el stock
 */
function vaciarCarrito(nuevoStock) {
	carrito = [];
	guardarCarrito();
	cargarMenuCarrito();
	if(nuevoStock!=null)
		productoStock = nuevoStock;
}


/*
 __   ___    _        
 \ \ / (_)__| |_ __ _ 
  \ V /| (_-<  _/ _` |
   \_/ |_/__/\__\__,_|
*/

/**
 * Carga los elementos del carrito carrito en la vista del menú
 */
function cargarMenuCarrito () {
	console.log("cargarVistaCarrito");

	let desplegable = document.getElementById("carrito desplegable");
	
	// Borrar todos los elementos anteriores
	while(desplegable.firstChild){
		desplegable.removeChild(desplegable.firstChild);
	}
	
	// Añadir todos los elementos <li> a la lista del desplegable
	for(let i=0; i<carrito.length; i++){
	  // Creamos un nuevo elemento <li> y le agregamos una clase 'dropdown-item'
	  let nuevoElemento = document.createElement("li");
	  nuevoElemento.className = "dropdown-item";
	
	  // Creamos un nuevo elemento <button> y le agregamos una clase 'btn btn-danger' y un icono
	  let botonEliminar = document.createElement("button");
	  botonEliminar.type = "button";
	  botonEliminar.className = "btn btn-danger";
	  botonEliminar.setAttribute("onClick","borrarDelCarrito("+i+")");
	  
	  let iconoEliminar = document.createElement("i");
	  iconoEliminar.className = "bi bi-bag-x";
	  
	  botonEliminar.appendChild(iconoEliminar);
	  
	
	  // Creamos un nuevo nodo de texto y le asignamos los valores del producto en el carrito
	  let textoNodo = document.createTextNode("  "+carrito[i].nombre + " x" + carrito[i].cantidad + " " + carrito[i].precio);
	
	  // Agregamos el botón y el texto a nuestro nuevo elemento <li>
	  nuevoElemento.appendChild(botonEliminar);
	  nuevoElemento.appendChild(textoNodo);
	  
	  desplegable.appendChild(nuevoElemento);
	}
	
	// ELEMENTO DEL FINAL DE LA LISTA CON LA OPCIÓN DE COMPRAR
	// Calcular el precio total
	let precioTotal = 0;
	for(let producto of carrito)
		precioTotal += producto.precio * producto.cantidad;

    // Crear un elemnto <li> que contendrá nuestro boton de compra	
	let nuevoElementoComprar = document.createElement("li");
	nuevoElementoComprar.className = "dropdown-item";
	
	// Creamos un nuevo elemento <button> y le agregamos una clase 'btn btn-success' y un icono
	let botonComprar = document.createElement("button");
	botonComprar.type = "button";
	botonComprar.className = "btn btn-success";
	botonComprar.setAttribute("onclick","EnviarCarrito('RecogerCarrito','cuerpo',carrito)");
	let iconoComprar = document.createElement("i");
	
	iconoComprar.className = "bi bi-cart-check";
	botonComprar.appendChild(iconoComprar);
	
	let textoComprar = document.createTextNode(" Comprar");
	botonComprar.appendChild(textoComprar);
	
	// Creamos un nuevo nodo de texto y le asignamos el precio total concatenado con el símbolo del pokedollar
	let textoNodo = document.createTextNode(" "+precioTotal + "\u20BD")
	
	
	// Agregamos el botón y el texto a nuestro nuevo elemento <li>
	nuevoElementoComprar.appendChild(botonComprar);
	nuevoElementoComprar.appendChild(textoNodo);
	
	desplegable.appendChild(nuevoElementoComprar);
	
	// ACTUALIZAR INDICADORES DEL MENU
	document.getElementById("numero de productos").innerHTML = carrito.length + " producto" + (carrito.length!=1?"s":"");
	document.getElementById("precio total").innerHTML = precioTotal;
}

/**
 * Se utiliza cuando se quiere cargar los productos que hay en el carrito
 */
function cargarApartadoCarrito() {
	console.log("cargarApartadoCarrito");
	let divCarrito = document.getElementById("div carrito");
	
	// Quitar todos los hijos del carrito
	while(divCarrito.firstChild)
		divCarrito.removeChild(divCarrito.firstChild);
	
	let precioTotal = 0;
	for(let i=0; i<carrito.length; i++) {
		const producto = carrito[i];
		
		// Crea el elemento <div> y le añade las clases correspondientes
		const div = document.createElement("div");
		div.classList.add("col-lg-3", "col-md-4", "col-sm-6", "col-xs-12");
		div.style.marginBottom = "40px";
		
		// Crea el elemento <img> y le añade la imagen del producto
		const img = document.createElement("img");
		img.src = producto.imagen;
		img.classList.add("img-thumbnail");
		img.alt = producto.nombre;
		div.appendChild(img);
		
		// Crea el elemento <table>
		const tabla = document.createElement("table");
		
		// Crea el elemento <tr> para el nombre del producto
		const filaNombre = document.createElement("tr");
		const tdNombre = document.createElement("td");
		tdNombre.colSpan = "2";
		const nombre = document.createElement("h6");
		nombre.style.textAlign = "center";
		nombre.textContent = producto.nombre;
		tdNombre.appendChild(nombre);
		filaNombre.appendChild(tdNombre);
		tabla.appendChild(filaNombre);
		
		// Crea el elemento <tr> para el precio del producto
		const filaPrecio = document.createElement("tr");
		const tdPrecio = document.createElement("td");
		tdPrecio.colSpan = "2";
		const precio = document.createElement("h6");
		precio.style.textAlign = "center";
		precio.innerHTML = `${producto.precio}₽`;
		tdPrecio.appendChild(precio);
		filaPrecio.appendChild(tdPrecio);
		tabla.appendChild(filaPrecio);
		
		// Crea el elemento <tr> para la cantidad del producto y el botón de eliminar
		const filaCantidad = document.createElement("tr");
		const tdCantidad = document.createElement("td");
		const inputCantidad = document.createElement("input");
		inputCantidad.id = "inputCantidad"+i;
		inputCantidad.type = "number";
		inputCantidad.style.width = "75px";
		inputCantidad.value = producto.cantidad;
		inputCantidad.min = "1";
		inputCantidad.max = productoStock.get(producto.codigo);
		inputCantidad.setAttribute("onchange","modificarCantidad("+i+")");
		inputCantidad.classList.add("form-control");
		tdCantidad.appendChild(inputCantidad);
		filaCantidad.appendChild(tdCantidad);
		
		const tdEliminar = document.createElement("td");
		const botonEliminar = document.createElement("button");
		botonEliminar.type = "button";
		botonEliminar.classList.add("btn", "btn-danger");
		botonEliminar.innerHTML = '<i class="bi bi-trash3"></i> Eliminar';
		botonEliminar.setAttribute("onclick","borrarDelCarrito("+i+")")
		tdEliminar.appendChild(botonEliminar);
		filaCantidad.appendChild(tdEliminar);
		
		tabla.appendChild(filaCantidad);
		div.appendChild(tabla);
		
		divCarrito.appendChild(div);
		precioTotal += producto.precio * producto.cantidad;
	}
	
	// Actualizar footer
	document.getElementById("footer-precio-total").innerHTML = precioTotal;
	document.getElementById("footer-carrito-size").innerHTML = carrito.length+" producto"+(carrito.length==1?"":"s");
}

/**
 * Limita la cantidad máxima que se puede comprar de un producto. En el apartado Prodcutos
 * @param codigo Codigo del producto
 */
function limitarStockProducto(codigo) {
	const cantidad = getCantidadCarritoByCodigo(codigo);
	const stockTotal = productoStock.get(codigo);
	const nuevoStock = stockTotal - cantidad;
	
	let boton = document.getElementById("comprar"+codigo);
	let input = document.getElementById("cantidad"+codigo);
	
	if(nuevoStock<1) { // Si no hay stock para comprar
		boton.disabled = true;
		boton.innerHTML = `Agotado`
		
		input.min = 0;
		input.max = 0;
		input.value = 0;
		input.disabled = true;
	}
	else { // Si hay stock para comprar
		boton.disabled = false;
		boton.innerHTML = `<i class="bi bi-bag-plus"></i> Comprar`
		
		input.min = 1;
		input.max = nuevoStock;
		input.value = 1;
		input.disabled = false;
	}
	
	console.log("limitarStockProducto \n"+
	 "codigo="+codigo+"\n"+
	 "cantidad="+cantidad+"\n"+
	 "stockTotal="+stockTotal+"\n"+
	 "nuevoStock="+nuevoStock+"\n");
}

/**
 * Búsqueda secuencial con parada. Función auxiliar de limitarStockProductos.
 * @param codigo Codigo del producto del que se desea encontrar la cantidad
 * @return Cantidad del producto
 */
function getCantidadCarritoByCodigo(codigo) {
	for(const producto of carrito) {
		if(producto.codigo == codigo)
			return producto.cantidad;
	}
	return 0;
}

/**
 * Limita la cantidad máxima que se puede del carrito en el apartado "Carrito"
 * @param idx índice en el carrito
 */
function limitarStockCarrito(idx) {
	const codigo = carrito[idx];
	const stock = productoStock.get(codigo);
	let input = document.getElementById("inputCantidad0");
	input.max = stock;
}