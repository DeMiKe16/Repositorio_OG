package modelo;

public class Notificacion {

	private int id;
	private String fecha;
	private Publicacion publicacion;
	
	public Notificacion(String fecha, Publicacion publicacion) {
		super();
		this.fecha = fecha;
		this.publicacion = publicacion;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public Publicacion getPublicacion() {
		return publicacion;
	}
	public void setPublicacion(Publicacion publicacion) {
		this.publicacion = publicacion;
	}
	public boolean comprobarPublicacion(Publicacion publicacion) {
		return this.publicacion.equals(publicacion);
	}

	@Override
	public String toString() {
		return "Notificacion [id=" + id + ", fecha=" + fecha + ", publicacion=" + publicacion + "]";
	}	
	
}
