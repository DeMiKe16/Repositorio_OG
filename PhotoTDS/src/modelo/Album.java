package modelo;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Album extends Publicacion {

	private List<Foto> fotos;

	public Album(String titulo, String fecha, String descripcion, List<Usuario> megusta,
			List<Comentario> comentarios, List<Foto> fotos, Usuario autor) {
		super(titulo, fecha, descripcion, megusta, parsearHashTagsAlbum(fotos, descripcion) , comentarios, autor);
		this.fotos = fotos;
	}
	
	public Album(String titulo, String fecha, String descripcion, List<Usuario> megusta, Set<String> listaHash,
			List<Comentario> comentarios, List<Foto> fotos, Usuario usuario) {
		super(titulo, fecha, descripcion, megusta, listaHash , comentarios, usuario);
		this.fotos = fotos;
	}

	public static Set<String> parsearHashTagsAlbum(List<Foto> fotos, String descripcion) {
		Set<String> hashtags = new HashSet<String>();
        String[] palabras = descripcion.split("\\s+");
        /*for (String palabra : palabras) {
            if (palabra.startsWith("#")) {
            		hashtags.add(palabra.substring(1));
            }
        }*/
        Arrays.stream(palabras)
	        .filter(palabra -> palabra.startsWith("#"))
	        .map(palabra -> palabra.substring(1))
	        .forEach(hashtags::add);
        /*for (Foto f: fotos) {
        	hashtags.addAll(Foto.parsearHashTagsFoto(f.getDescripcion()));
        }*/
        fotos.stream()
	        .map(Foto::getDescripcion)
	        .map(Foto::parsearHashTagsFoto)
	        .forEach(hashtags::addAll);
		return hashtags;
	}
	
	public void añadirFoto(Foto f) {
		this.fotos.add(f);
		this.setHashtags(parsearHashTagsAlbum(fotos, getDescripcion()));
	}

	public List<Foto> getFotos() {
		return fotos;
	}
	
	public String getPortada() {
		return fotos.get(0).getPath();
	}

	public void setFotos(List<Foto> fotos) {
		this.fotos = fotos;
	}

	public boolean isUnaFoto() {
		return (this.fotos.size() == 1);
	}

	public boolean isFotoEnAlbum(Foto foto) {
		/*for(Foto f: this.fotos) {
			if (f.equals(foto))
				return true;
		}
		return false;*/
		return fotos.stream().anyMatch(f -> f.equals(foto));
	}

	public boolean eliminarFoto(Foto foto) {
		this.fotos.remove(foto);
		return false;
	}
	
}
