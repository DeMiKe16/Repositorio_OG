package modelo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Usuario {

	private int id;
	private String nombre;
	private String apellidos;
	private String email;
	private String login;
	private Optional<String> description;
	private String photoLogin;
	private String password;
	private String fechaNacimiento;
	private List<Publicacion> publicaciones;
	private List<Usuario> seguidores;
	private List<Usuario> seguidos;
	private List<Notificacion> notificaciones;
	private boolean premium;

	public Usuario(String nombre, String apellidos, String email, String login, String password, String fechaNacimiento,
			String description, String photologin, List<Publicacion> publicaciones, List<Usuario> seguidores, List<Usuario> seguidos, List<Notificacion> notificaciones) {
		this.id = 0;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.email = email;
		this.login = login;
		this.password = password;
		this.fechaNacimiento = fechaNacimiento;
		this.description = Optional.ofNullable(description);
		this.photoLogin = this.guardarFotoPerfilRelativa("publicaciones", new File(photologin));
		this.publicaciones = publicaciones;
		this.seguidores = seguidores;
		this.seguidos = seguidos;
		this.notificaciones = notificaciones;
		this.premium = false;
	}
	
	public String guardarFotoPerfilRelativa(String ruta, File fichero) {
		String rutaDestino = "src/" + ruta.replace(".", "/") + "/" + fichero.getName();
		File imagenDestino = new File(rutaDestino);
		if (imagenDestino.exists()) {
			return fichero.getPath();
		} else {
			if (!imagenDestino.getParentFile().exists()) {
	            imagenDestino.getParentFile().mkdirs();
	        }
	        // Copia la imagen desde la ubicación original a la ubicación de destino
	        FileOutputStream fos;
	        FileInputStream fis;
			try {
				fos = new FileOutputStream(imagenDestino);
				fis = new FileInputStream(fichero);
				byte[] buffer = new byte[1024];
		        int length;
		        while ((length = fis.read(buffer)) > 0) {
		            fos.write(buffer, 0, length);
		        }
		        fos.close();
		        fis.close();
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return rutaDestino;
		}
	}

	public void añadirPublicacion(Publicacion pub) {
		publicaciones.add(pub);
		
	}
	
	public void borrarPublicacion(Publicacion pub) {
		publicaciones.remove(pub);
	}
	
	public boolean contieneSeguidor(Usuario usuario) {
		if(seguidores.contains(usuario))
			return true;
		return false;
	}
	
	public List<Foto> obtenerFotosUsuario(){
		return publicaciones.stream()
				.filter(p -> p instanceof Foto)
				.map(p-> (Foto)p)
				.collect(Collectors.toList());
	}
	
	public List<Album> obtenerAlbumes() {
		/*List<Album> albumes = new LinkedList<Album>();
		for (Publicacion p : this.publicaciones) {
			if (p instanceof Album) {
				albumes.add((Album) p);
			}
		}*/
		List<Album> albumes = publicaciones.stream()
	            .filter(p -> p instanceof Album)
	            .map(p -> (Album) p)
	            .collect(Collectors.toList());
		return albumes;
	}
	
	public Foto crearFoto(String titulo, String fechaFormato, String descripcion, LinkedList<Usuario> linkedList,
		LinkedList<Comentario> linkedList2, String ruta) {
		Foto auxiliar = new Foto(titulo, fechaFormato, descripcion, new LinkedList<Usuario>(),
				new LinkedList<Comentario>(), ruta, this);
		return auxiliar;
	}
	
	public Album crearAlbum(String titulo, String fechaFormato, String descripcion, LinkedList<Usuario> linkedList
			, LinkedList<Comentario> linkedList2, List<Foto> fotos) {
			Album auxiliar = new Album(titulo, fechaFormato, descripcion, new LinkedList<Usuario>(),
					new LinkedList<Comentario>(), fotos, this);
			return auxiliar;
		}
	
	public boolean eliminarFoto(Foto foto) {
		return this.publicaciones.remove(foto);	
	}
	
	
	//Revisar
	public boolean eliminarFotoAlbum(Foto foto, Album album) {
		/*for(Publicacion p: publicaciones) {
			if (p.equals(album)) {
				if (album.isFotoEnAlbum(foto)) {
					return album.eliminarFoto(foto);
				}
			}
		}
		return false;*/
		return publicaciones.stream()
	            .filter(p -> p.equals(album))
	            .findFirst()
	            .map(p -> album.isFotoEnAlbum(foto) && album.eliminarFoto(foto))
	            .orElse(false);
	}
	
	public boolean eliminarAlbum(Album album) {
		return this.publicaciones.remove(album);	
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getDescription() {
		return description.orElse("");
	}

	public void setDescription(String description) {
		this.description = Optional.ofNullable(description);
	}

	public String getPhotoLogin() {
		return photoLogin;
	}

	public void setPhotoLogin(String photoLogin) {
		this.photoLogin = photoLogin;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(String fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}
	
	public List<Publicacion> getPublicaciones() {
		return publicaciones;
	}

	public void setPublicaciones(List<Publicacion> publicaciones) {
		this.publicaciones = publicaciones;
	}

	public List<Usuario> getSeguidores() {
		return seguidores;
	}
	
	public int getNumeroSeguidores() {
		return seguidores.size();
	}
	
	public void setSeguidores(List<Usuario> seguidores) {
		this.seguidores = seguidores;
	}
	
	public void addSeguidores(Usuario usuario) {
		if (!this.seguidores.contains(usuario))
			this.seguidores.add(usuario);
	}
	
	public void delSeguidor(Usuario usuario) {
		if(this.seguidores.contains(usuario))
			this.seguidores.remove(usuario);
	}
	
	public List<Usuario> getSeguidos() {
		return seguidos;
	}

	public int getNumeroSeguidos() {
		return seguidos.size();
	}
	
	public void setSeguidos(List<Usuario> seguidos) {
		this.seguidos = seguidos;
	}
	
	public void addSeguido(Usuario usuario) {
		if(!this.seguidos.contains(usuario))
			this.seguidos.add(usuario);
	}
	
	public void delSeguido(Usuario usuario) {
		if(this.seguidos.contains(usuario))
			this.seguidos.remove(usuario);
	}
	
	public List<Notificacion> getNotificaciones() {
		return notificaciones;
	}

	public void setNotificaciones(List<Notificacion> notificaciones) {
		this.notificaciones = notificaciones;
	}
	
	public void addNotificacion(Notificacion notificacion) {
		notificaciones.add(notificacion);
	}
	
	public void deleteNotificacion(Notificacion notificacion) {
		notificaciones.remove(notificacion);
	}
	
	public boolean getPremium() {
		return this.premium;
	}
	
	public void setPremium(boolean premium) {
		this.premium = premium;
	}
	
	public int obtenerLikesPublicaciones() {
		/*int totalLikes = 0;
		for(Publicacion p: publicaciones) {
			if(p instanceof Foto) {
				totalLikes += p.getNumeroMegusta();
			} else {
				List<Foto> listaFotos = ((Album) p).getFotos();
				for(Foto f: listaFotos) {
					totalLikes += f.getNumeroMegusta();
				}
			}
		}*/
		int totalLikes = publicaciones.stream()
	            .mapToInt(p -> {
	                if (p instanceof Foto) {
	                    return p.getNumeroMegusta();
	                } else {
	                    List<Foto> listaFotos = ((Album) p).getFotos();
	                    return listaFotos.stream().mapToInt(Foto::getNumeroMegusta).sum();
	                }
	            })
	            .sum();
		return totalLikes;
	}
	
	public Notificacion eliminarNotificaciones(Publicacion publicacion){
		Notificacion noti = null;
		for(Notificacion notificacion: notificaciones) {
			if(notificacion.comprobarPublicacion(publicacion)) {
				if (noti == null) {
					noti = notificacion;
				}
				notificaciones.remove(notificacion);
			}
				
		}
		return noti;
	}

	public boolean comprobarNotificaciones() {
		return !this.notificaciones.isEmpty();
	}
}
