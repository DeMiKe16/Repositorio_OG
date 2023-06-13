package modelo;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public abstract class Publicacion {
	
	private int id;
	private String titulo;
	private String fecha;
	private Optional<String> descripcion;
	private List<Usuario> megusta;
	private Set<String> hashtags;
	private List<Comentario> comentarios;
	private Usuario autor;
	
	
	
	public Publicacion(String titulo, String fecha, String descripcion, List<Usuario> megusta,
			Set<String> hashtags, List<Comentario> comentarios, Usuario autor) {
		super();
		this.id = 0;
		this.titulo = titulo;
		this.fecha = fecha;
		this.descripcion = Optional.ofNullable(descripcion);
		this.megusta = megusta;
		this.hashtags = hashtags;
		this.comentarios = comentarios;
		this.autor = autor;
	}
	
	public Publicacion() {
		super();
		this.id = 0;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = Optional.ofNullable(descripcion);
	}

	public void setMegusta(List<Usuario> megusta) {
		this.megusta = megusta;
	}

	public void setHashtags(Set<String> hashtags) {
		this.hashtags = hashtags;
	}

	public void setComentarios(List<Comentario> comentarios) {
		this.comentarios = comentarios;
	}

	public String getTitulo() {
		return titulo;
	}
	
	public String getFecha() {
		return fecha;
	}
	 public String getFechaCorto() {
		return this.fecha.split("T")[0];	 
	 }
	
	public String getDescripcion() {
		return descripcion.orElse("");
	}
	public List<Usuario> getMegusta() {
		return megusta;
	}
	
	public int getNumeroMegusta() {
		return megusta.size();
	}
	
	public Usuario getAutor() {
		return autor;
	}
	
	public int getIdAutor() {
		return autor.getId();
	}

	public boolean dioMegusta(Usuario usuario) {
		if (this.megusta.contains(usuario)) 
			return true;
		return false;
	}	
	
	public void darMeGusta(Usuario usuario) {
		if (!dioMegusta(usuario))
			this.megusta.add(usuario);
	}
	
	public void quitarMeGusta(Usuario usuario) {
		this.megusta.remove(usuario);
	}
	
	public Set<String> getHashtags() {
		return hashtags;
	}
	public List<Comentario> getComentarios() {
		return comentarios;
	}
	
	public void addComentario(Comentario comentario) {
		this.comentarios.add(comentario);
	}
	
	public void removeComentario(Comentario comentario) {
		this.comentarios.remove(comentario);
	}
	
	public int getNumeroComentarios() {
		return comentarios.size();
	}
	
	public Notificacion crearNotificacion() {
		Notificacion n = new Notificacion(fecha, this);
		return n;
	}
	
	@Override
	public String toString() {
		return "Publicacion [id=" + id + ", titulo=" + titulo + ", fecha=" + fecha + ", descripcion=" + descripcion
				+ ", megusta=" + megusta + ", hashtags=" + hashtags + ", comentarios=" + comentarios + "]";
	}

	public static boolean comprobarDescripciones(String text) {
		String[] palabras = text.split("\\s+");
		Set<String> hashtags = new HashSet<String>();
	    /*for (String palabra : palabras) {
	    	if (palabra.startsWith("#")) 
	    		hashtags.add(palabra);
	    	if ((palabra.startsWith("#") && palabra.length() - 1 > 15) || hashtags.size() > 4) {
	    		return true; 
	    	} 
	    }
	    return false;*/
		long count = Arrays.stream(palabras)
	            .filter(palabra -> palabra.startsWith("#"))
	            .peek(hashtags::add)
	            .filter(palabra -> palabra.length() - 1 > 15 || hashtags.size() > 4)
	            .count();
	    return count > 0;
	}
}
	

