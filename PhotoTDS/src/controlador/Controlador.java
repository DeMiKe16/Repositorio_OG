package controlador;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.EventObject;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import DAO.DAOException;
import DAO.FactoriaDAO;
import DAO.TDSAlbumDAO;
import DAO.TDSComentarioDAO;
import DAO.TDSFotoDAO;
import DAO.TDSNotificacionDAO;
import DAO.TDSUsuarioDAO;
import modelo.Album;
import modelo.Comentario;
import modelo.Foto;
import modelo.Notificacion;
import modelo.Publicacion;
import modelo.RepoPublicaciones;
import modelo.RepoUsuarios;
import modelo.Usuario;
import umu.tds.fotos.CargadorFotos;
import umu.tds.fotos.FotosEvent;
import umu.tds.fotos.FotosListener;

public final class Controlador implements FotosListener {

	private String rutaPaquete = "publicaciones";
	private Usuario usuarioActual;
	private static Controlador unicaInstancia;
	private TDSUsuarioDAO usuarioDAO;
	private TDSFotoDAO fotoDAO;
	private TDSAlbumDAO albumDAO;
	private TDSComentarioDAO comentarioDAO;
	private TDSNotificacionDAO notificacionDAO;
	private CargadorFotos cargadorFotos;
	private RepoUsuarios repositorioUsuarios;
	private RepoPublicaciones repositorioPublicaciones;
	
	private Controlador() {
		usuarioActual = null;
		repositorioUsuarios = RepoUsuarios.getUnicaInstancia();
		repositorioPublicaciones = RepoPublicaciones.getUnicaInstancia();
		this.cargadorFotos = new CargadorFotos();
		try {
			usuarioDAO = FactoriaDAO.getInstancia().getUsuarioDAO();
			fotoDAO = FactoriaDAO.getInstancia().getFotoDAO();
			albumDAO = FactoriaDAO.getInstancia().getAlbumDAO();
			comentarioDAO = FactoriaDAO.getInstancia().getComentarioDAO();
			notificacionDAO = FactoriaDAO.getInstancia().getNotificacionDAO();
		} catch (DAOException e) {
			e.printStackTrace();
		}
	}

	public static Controlador getUnicaInstancia() {
		if (unicaInstancia == null)
			unicaInstancia = new Controlador();
		return unicaInstancia;
	}

	public Usuario getUsuarioActual() {
		return usuarioActual;
	}

	public String getLoginUsuario(Usuario usuario) {
		return usuario.getLogin();
	}
	
	public void setLoginUsuario(String login) {
		usuarioActual.setLogin(login);
	}


	public String getPhotoUsr(Usuario usuario) {
		return usuario.getPhotoLogin();
	}

	public String getNombreUsuario(Usuario usuario) {
		return usuario.getNombre();
	}
	
	
	public String getApellidosUsuario(Usuario usuario) {
		return usuario.getApellidos();
	}

	public List<Usuario> getSeguidoresUsuario(Usuario usuario) {
		return usuario.getSeguidores();
	}

	public int getNumeroSeguidoresUsuario(Usuario u) {
		return u.getNumeroSeguidores();
	}

	public int getNumeroSeguidosUsuario(Usuario u) {
		return u.getNumeroSeguidos();
	}

	public List<Usuario> getSeguidosUsuario(Usuario usuario) {
		return usuario.getSeguidos();
	}

	public void setNombreUsuario(String nombre) {
		usuarioActual.setNombre(nombre);
	}

	public boolean esUsuarioRegistrado(String login) {
		return RepoUsuarios.getUnicaInstancia().getUsuario(login) != null;
	}

	public void setApellidos(String apellidos) {
		usuarioActual.setApellidos(apellidos);
	}

	public void setEmail(String email) {
		usuarioActual.setEmail(email);
	}

	public void setDescripcion(String descripcion) {
		usuarioActual.setDescription(descripcion);
	}
	
	private void setPhotoLogin(String path) {
		usuarioActual.setPhotoLogin(path);	
	}
	
	public List<Notificacion> getNotificacionesUsuario(Usuario usuario){
		return usuario.getNotificaciones();
	}

	public void actualizarUsuario(String login, String nombre, String apellidos, String email, String descripcion, String nuevaFoto) {
		if (!login.isBlank() && !esUsuarioRegistrado(login))
			this.setLoginUsuario(login);
		if (!nombre.isBlank())
			this.setNombreUsuario(nombre);
		if (!apellidos.isBlank())
			this.setApellidos(apellidos);
		if (!email.isBlank())
			this.setEmail(email);
		if (!getDescripcionUsuario(usuarioActual).equals(descripcion))
			this.setDescripcion(descripcion);
		if (nuevaFoto != null) {
			nuevaFoto = usuarioActual.guardarFotoPerfilRelativa(rutaPaquete, new File(nuevaFoto));
			if (nuevaFoto != this.getPhotoUsr(Controlador.getUnicaInstancia().getUsuarioActual()))
				this.setPhotoLogin(nuevaFoto);	
		}
		RepoUsuarios.getUnicaInstancia().addUsuario(usuarioActual);	
		usuarioDAO.update(usuarioActual);
	}

	public boolean loginUsuario(String nombre, String password) {
		Usuario usuario = RepoUsuarios.getUnicaInstancia().getUsuario(nombre);
		if (usuario != null && usuario.getPassword().equals(password)) {
			this.usuarioActual = usuario;
			return true;
		}
		return false;
	}

	public boolean registrarUsuario(String nombre, String apellidos, String email, String login, String password,
			String fechaNacimiento, String description, String photologin) {

		if (esUsuarioRegistrado(login))
			return false;

		Usuario usuario = new Usuario(nombre, apellidos, email, login, password, fechaNacimiento, description,
				photologin, new LinkedList<Publicacion>(), new LinkedList<Usuario>(), new LinkedList<Usuario>(),
				new LinkedList<Notificacion>());

		usuarioDAO.create(usuario);

		RepoUsuarios.getUnicaInstancia().addUsuario(usuario);
		return true;
	}

	public boolean borrarUsuario(Usuario usuario) {
		if (!esUsuarioRegistrado(usuario.getLogin()))
			return false;

		usuarioDAO.delete(usuario);

		RepoUsuarios.getUnicaInstancia().removeUsuario(usuario);
		return true;
	}

	public List<Usuario> buscarUsuarios(String name) throws DAOException {
		List<Usuario> searchUser = new LinkedList<Usuario>();
		searchUser = (List<Usuario>) repositorioUsuarios.filtrarUsuarios(name);
		return searchUser;
	}

	public boolean comprobarUsuarioDistinto(Usuario u) {
		if (u.getLogin().equals(this.getLoginUsuario(Controlador.getUnicaInstancia().getUsuarioActual())))
			return true;
		return true;
	}

	@Override
	public void enteradoCambioFotos(EventObject ev) {
		if (ev instanceof FotosEvent) {
			FotosEvent event = (FotosEvent) ev;
			if (event.getOldFotos() != null) {
			}
		}
	}

	public boolean seguir(Usuario usuario) {
		if (!esSeguidor(usuario)) {
			usuario.addSeguidores(usuarioActual);
			usuarioActual.addSeguido(usuario);
			usuarioDAO.update(usuario);
			usuarioDAO.update(usuarioActual);
			return true;
		}
		return false;
	}

	public void dejarSeguir(Usuario usuario) {
		if (esSeguidor(usuario)) {
			usuarioActual.delSeguido(usuario);
			usuario.delSeguidor(usuarioActual);
			usuarioDAO.update(usuario);
			usuarioDAO.update(usuarioActual);
		}
	}

	public boolean esSeguidor(Usuario usuario) {
		if (usuario.contieneSeguidor(usuarioActual))
			return true;
		return false;
	}

	public boolean cargarFotos(String ruta) {
		try {
			cargadorFotos.setArchivoFotos(ruta);
			List<Foto> fotos = Foto.crearFotosPorCargador(cargadorFotos.getArchivoFotos());
			/*for (Foto foto : fotos) {
				subirFoto(foto);
			}*/
			fotos.forEach(this::subirFoto);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public void subirFoto(Foto foto) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy'T'HH:mm:ss");
		LocalDateTime fecha = LocalDateTime.now();
		String fechaFormato = fecha.format(formatter);
		foto.setFecha(fechaFormato);
		usuarioActual.añadirPublicacion(foto);
		fotoDAO.create(foto);
		RepoPublicaciones.getUnicaInstancia().addFoto(foto);
		usuarioDAO.update(usuarioActual);
		Notificacion n = foto.crearNotificacion();
		List<Usuario> seguidores = usuarioActual.getSeguidores();
		/*for(Usuario seguidor: seguidores) {
			seguidor.addNotificacion(n);
		}*/
		seguidores.forEach(seguidor -> seguidor.addNotificacion(n));		
		notificacionDAO.create(n);
		/*for(Usuario seguidor: seguidores) {
			usuarioDAO.update(seguidor);
		}*/
		seguidores.forEach(usuarioDAO::update);
	}
	
	
	public boolean subirFoto(String ruta, String titulo, String descripcion) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy'T'HH:mm:ss");
		LocalDateTime fecha = LocalDateTime.now();
		String fechaFormato = fecha.format(formatter);
		Foto f = usuarioActual.crearFoto(titulo, fechaFormato, descripcion, new LinkedList<Usuario>(),
				new LinkedList<Comentario>(), ruta);
		usuarioActual.añadirPublicacion(f);
		fotoDAO.create(f);
		RepoPublicaciones.getUnicaInstancia().addFoto(f);
		usuarioDAO.update(usuarioActual);
		Notificacion n = f.crearNotificacion();
		List<Usuario> seguidores = usuarioActual.getSeguidores();
		/*for(Usuario seguidor: seguidores) {
			seguidor.addNotificacion(n);
		}*/
		seguidores.forEach(seguidor -> seguidor.addNotificacion(n));
		notificacionDAO.create(n);
		/*for(Usuario seguidor: seguidores) {
			usuarioDAO.update(seguidor);
		}*/
		seguidores.forEach(usuarioDAO::update);
		return true;
	}
	
	public void crearFotoAlbum(List<Foto> album, String ruta, String titulo, String descripcion) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy'T'HH:mm:ss");
		LocalDateTime fecha = LocalDateTime.now();
		String fechaFormato = fecha.format(formatter);
		Foto f = usuarioActual.crearFoto(titulo, fechaFormato, descripcion, new LinkedList<Usuario>(),
				new LinkedList<Comentario>(), ruta);
		album.add(f);
	}
	
	public boolean subirAlbum(List<Foto> album, String titulo, String descripcion) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy'T'HH:mm:ss");
		LocalDateTime fecha = LocalDateTime.now();
		String fechaFormato = fecha.format(formatter);
		Album a = usuarioActual.crearAlbum(titulo, fechaFormato, descripcion, new LinkedList<Usuario>(),
				new LinkedList<Comentario>(), album);
		usuarioActual.añadirPublicacion(a);
		/*for(Foto f: album) {
			fotoDAO.create(f);
		}*/
		 album.forEach(fotoDAO::create);
		albumDAO.create(a);
		RepoPublicaciones.getUnicaInstancia().addAlbum(a);
		usuarioDAO.update(usuarioActual);
		Notificacion n = a.crearNotificacion();
		List<Usuario> seguidores = usuarioActual.getSeguidores();	
		/*for(Usuario seguidor: seguidores) {
			seguidor.addNotificacion(n);
		}*/
		seguidores.forEach(seguidor -> seguidor.addNotificacion(n));
		notificacionDAO.create(n);
		/*for(Usuario seguidor: seguidores) {
			usuarioDAO.update(seguidor);
		}*/
		seguidores.forEach(usuarioDAO::update);
		return true;
	}
	

	public void añadirFotoAlbum(Album album, String rutaDestino, String titulo, String descripcion) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy'T'HH:mm:ss");
		LocalDateTime fecha = LocalDateTime.now();
		String fechaFormato = fecha.format(formatter);
		Foto f = usuarioActual.crearFoto(titulo, fechaFormato, descripcion, new LinkedList<Usuario>(),
				new LinkedList<Comentario>(), rutaDestino);
		fotoDAO.create(f);
		RepoPublicaciones.getUnicaInstancia().addFoto(f);
		album.añadirFoto(f);
		albumDAO.update(album);
		usuarioDAO.update(usuarioActual);	
	}
	
	public void eliminarFoto(Foto foto) {
		if (usuarioActual.eliminarFoto(foto)) {
			Notificacion noti = usuarioActual.getSeguidores().stream()
	                .map(usuario -> usuario.eliminarNotificaciones(foto))
	                .findFirst()
	                .orElse(null);
			if (noti != null) {
	            notificacionDAO.delete(noti);
	        }
			fotoDAO.delete(foto);
			usuarioDAO.update(usuarioActual);
			List<Usuario> seguidores = usuarioActual.getSeguidores();
			seguidores.forEach(usuarioDAO::update);
		}
	}
	
	public void eliminarFotoAlbum(Foto foto, Album a) {
		if (usuarioActual.eliminarFotoAlbum(foto,a)) {
			usuarioDAO.update(usuarioActual);
			fotoDAO.delete(foto);
			albumDAO.update(a);
		}
	}
	
	public void eliminarAlbum(Foto foto, Album a) {
		if (usuarioActual.eliminarAlbum(a)) {
			Notificacion noti = usuarioActual.getSeguidores().stream()
	                .map(usuario -> usuario.eliminarNotificaciones(a))
	                .findFirst()
	                .orElse(null);
			if (noti != null) {
	            notificacionDAO.delete(noti);
	        }
			fotoDAO.delete(foto);
			albumDAO.delete(a);
			usuarioDAO.update(usuarioActual);
			List<Usuario> seguidores = usuarioActual.getSeguidores();
			seguidores.forEach(usuarioDAO::update);
		}
	}
	
	
	public boolean comprobarNotificaciones() {
		return usuarioActual.comprobarNotificaciones();
	}

	public List<Foto> obtenerFotosUsuario(Usuario usuario) {
		return usuario.obtenerFotosUsuario();
	}
	
	public List<Album> obtenerAlbumesUsuario(Usuario usuario) {
		return usuario.obtenerAlbumes();
	}

	public int numeroLikes(Publicacion publicacion) {
		return publicacion.getNumeroMegusta();
	}
	
	public int numeroComentarios(Publicacion publicacion) {
		return publicacion.getNumeroComentarios();
	}

	public List<Foto> ultimasFotos() {
		List<Foto> listaFotos = obtenerFotosUsuario(usuarioActual);
		List<Usuario> seguidos = getSeguidosUsuario(usuarioActual);
		seguidos.stream()
			.map(Usuario::obtenerFotosUsuario)
			.forEach(listaFotos::addAll);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy'T'HH:mm:ss");
		List<Foto> listaFotosOrdenada = listaFotos.stream().sorted(
				Comparator.comparing(f -> LocalDateTime.parse(f.getFecha(), formatter), Comparator.reverseOrder()))
				.collect(Collectors.toList());
		return listaFotosOrdenada;
	}

	public List<Usuario> getListaLike(Foto f) {
		return f.getMegusta();
	}

	public boolean dioLike(Publicacion publicacion) {
		return publicacion.dioMegusta(usuarioActual);
	}

	public boolean darLike(Publicacion publicacion) {	
		publicacion.darMeGusta(usuarioActual);
		if (publicacion instanceof Foto)
			fotoDAO.update((Foto)publicacion);
		else {
			/*for(Foto f: ((Album) publicacion).getFotos()) {
				f.darMeGusta(usuarioActual);
				fotoDAO.update((Foto)f);
			}*/
			((Album) publicacion).getFotos().forEach(f -> {
	            f.darMeGusta(usuarioActual);
	            fotoDAO.update(f);
	        });
			albumDAO.update((Album)publicacion);
		}
		return true;
	}

	public void quitarLike(Publicacion publicacion) {
		publicacion.quitarMeGusta(usuarioActual);
		if (publicacion instanceof Foto)
			fotoDAO.update((Foto)publicacion);
		else {
			/*for(Foto f: ((Album) publicacion).getFotos()) {
				f.quitarMeGusta(usuarioActual);
				fotoDAO.update((Foto)f);
			}*/
			((Album) publicacion).getFotos().forEach(f -> {
	            f.quitarMeGusta(usuarioActual);
	            fotoDAO.update(f);
	        });
			albumDAO.update((Album)publicacion);
		}
	}

	public List<Comentario> obtenerComentarios(Publicacion  publicacion) {
		return publicacion.getComentarios();
	}

	public String obtenerTextoComentario(Comentario comentario) {
		return comentario.getTexto();
	}

	public Usuario getUsuarioDeComentario(Comentario comentario) {
		return comentario.getUsuario();
	}

	public Comentario agregarComentarioAPublicacion(Publicacion  publicacion, String comentario) {
		Comentario coment = new Comentario(comentario, usuarioActual);
		comentarioDAO.create(coment);
		publicacion.addComentario(coment);
		if (publicacion instanceof Foto)
			fotoDAO.update((Foto)publicacion);
		else {
			/*for(Foto f: ((Album) publicacion).getFotos()) {
				f.addComentario(coment);
				fotoDAO.update((Foto)f);
			}*/
			((Album) publicacion).getFotos().forEach(f -> {
	            f.addComentario(coment);
	            fotoDAO.update(f);
	        });
			albumDAO.update((Album)publicacion);
		}
		return coment;
	}
	
	public void eliminarComentario(Publicacion  publicacion, Comentario comentario) {
		publicacion.removeComentario(comentario);
		if (publicacion instanceof Foto)
			fotoDAO.update((Foto)publicacion);
		else {
			/*for(Foto f: ((Album) publicacion).getFotos()) {
				f.removeComentario(comentario);
				fotoDAO.update((Foto)f);
			}*/
			((Album) publicacion).getFotos().forEach(f -> {
	            f.removeComentario(comentario);
	            fotoDAO.update(f);
	        });
			albumDAO.update((Album)publicacion);
		}
		comentarioDAO.delete(comentario);
	}
	
	public Usuario getAutorPublicacion(Publicacion p) {
		return p.getAutor();
	}
	
	public Publicacion getPublicacionNotificacion(Notificacion notificacion) {
		return notificacion.getPublicacion();
	}
	
	public String obtenerPathFoto(Foto foto) {
		return foto.getPath();
	}

	public void eliminarNotificacion(Notificacion n) {
		usuarioActual.deleteNotificacion(n);
		usuarioDAO.update(usuarioActual);
	}

	public List<Publicacion> obtenerPublicacionesPorHashtag(List<String> nothashtags){
		/*List<String> hashtags = new LinkedList<>();
		for(String s: nothashtags) {
			List<String> h = repositorioPublicaciones.hashTagsDeSubcadena(s);
			hashtags.addAll(h);
		}*/
		List<String> hashtags = nothashtags.stream()
		        .flatMap(s -> repositorioPublicaciones.hashTagsDeSubcadena(s).stream())
		        .collect(Collectors.toList());
		
		/*List<Publicacion> publicaciones = new LinkedList<>();
		for(String s: hashtags) {
			List<Foto> fotos = repositorioPublicaciones.getFotoPorHashTag(s);
			List<Album> albumes = repositorioPublicaciones.getAlbumPorHashTag(s);
			if(fotos != null)
				publicaciones.addAll(fotos);
			if(albumes != null)
				publicaciones.addAll(albumes);
		}*/
		
		List<Publicacion> publicaciones = hashtags.stream()
		        .flatMap(s -> {
		            List<Foto> fotos = repositorioPublicaciones.getFotoPorHashTag(s);
		            List<Album> albumes = repositorioPublicaciones.getAlbumPorHashTag(s);
		            Stream<Publicacion> fotoStream = fotos != null ? fotos.stream().map(p -> (Publicacion) p) : Stream.empty();
		            Stream<Publicacion> albumStream = albumes != null ? albumes.stream().map(p -> (Publicacion) p) : Stream.empty();
		            return Stream.concat(fotoStream, albumStream);
		        })
		        .collect(Collectors.toList());

		
		return publicaciones;
	}
	
	public boolean soloUnaFoto(Album album) {
		return album.isUnaFoto();
	}
	
	public String getEmailUsuario(Usuario usuario) {
		return usuario.getEmail();
	}
	
	public String getDescripcionUsuario(Usuario usuario) {
		return usuario.getDescription();
	}
	
	public String getFechaUsuario(Usuario usuario) {
		return usuario.getFechaNacimiento();
	}
	
	public int obtenerTotalLikesUsuario(Usuario usuario) {
		return usuario.obtenerLikesPublicaciones();
	}
	
	public void setPremium(Usuario usuario) {
		usuario.setPremium(true);
		usuarioDAO.update(usuario);
	}
	
	public boolean esPremium(Usuario usuario) {
		return usuario.getPremium();
	}
	
	public String getPortadaAlbum(Album album) {
		return album.getPortada();
	}
	
	public boolean isAlbum(Publicacion publicacion) {
		return (publicacion instanceof Album);
	}
	
	public List<Foto> obtenerFotosMasLikes(){
		return usuarioActual.obtenerFotosUsuario().stream()
				.sorted(Comparator.comparing(Foto::getNumeroMegusta).reversed())
				.limit(10)
				.collect(Collectors.toList());
	}

	public boolean isUsuarioActual(Usuario usuario) {
		return usuarioActual.equals(usuario);
	}

	public String getFechaPublicacion(Publicacion publicacion) {
		return publicacion.getFechaCorto();
	}

	public boolean comprobarDescripciones(String text) {
		return Publicacion.comprobarDescripciones(text);
	}


}
