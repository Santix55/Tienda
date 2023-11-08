package p2;

import java.util.ArrayList;

public class PedidoBD {
	public final int SIN_TRAMITAR = -1;
	public final int PENDIENTE = 0;
	public final int ENVIADO = 1;
	public final int ENTREGADO = 2;
	public final int CANCELADO = 3;
	
	private ArrayList<Producto> productos;
	private int codigo;
	private int importe;
	private String fecha;
	private int estado;
	

	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public int getImporte() {
		return importe;
	}
	public void setImporte(int importe) {
		this.importe = importe;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public ArrayList<Producto> getProductos() {
		return productos;
	}
	public void setProductos(ArrayList<Producto> productos2) {
		this.productos = productos2;
	}
	public int getEstado() {
		return estado;
	}
	public void setEstado(int estado) {
		this.estado = estado;
	}
	
}
