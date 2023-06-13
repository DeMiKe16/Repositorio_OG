package DAO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.StringTokenizer;

import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;
import beans.Entidad;
import beans.Propiedad;
import modelo.Album;
import modelo.Foto;
import modelo.Notificacion;
import modelo.Publicacion;
import modelo.Usuario;

/**
 * 
 * Clase que implementa el Adaptador DAO concreto de Usuario para el tipo H2.
 * 
 */
public final class TDSUsuarioDAO implements UsuarioDAO {

	private ServicioPersistencia servPersistencia;
	private static TDSUsuarioDAO unicaInstancia = null;

	public static TDSUsuarioDAO getUnicaInstancia() { // patron singleton
		if (unicaInstancia == null)
			return new TDSUsuarioDAO();
		else
			return unicaInstancia;
	}
	
	public TDSUsuarioDAO() {
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
	}

	private Usuario entidadToUsuario(Entidad eUsuario) {

		String nombre = servPersistencia.recuperarPropiedadEntidad(eUsuario, "nombre");
		String apellidos = servPersistencia.recuperarPropiedadEntidad(eUsuario, "apellidos");
		String email = servPersistencia.recuperarPropiedadEntidad(eUsuario, "email");
		String login = servPersistencia.recuperarPropiedadEntidad(eUsuario, "login");
		String password = servPersistencia.recuperarPropiedadEntidad(eUsuario, "password");
		String fechaNacimiento = servPersistencia.recuperarPropiedadEntidad(eUsuario, "fechaNacimiento");
		String description = servPersistencia.recuperarPropiedadEntidad(eUsuario, "description");
		String photologin = servPersistencia.recuperarPropiedadEntidad(eUsuario, "photologin");
		
		Usuario usuario = new Usuario(nombre, apellidos, email, login, password, fechaNacimiento, description, photologin, new LinkedList<Publicacion>(), new LinkedList<Usuario>(), new LinkedList<Usuario>(), new LinkedList<Notificacion>());
		usuario.setId(eUsuario.getId());
		PoolDAO.getUnicaInstancia().addObjeto(eUsuario.getId(), usuario);
		
		List<Publicacion> publicaciones = new LinkedList<Publicacion>();
		
		publicaciones.addAll(obtenerListaFotos(servPersistencia.recuperarPropiedadEntidad(eUsuario, "fotos")));
		publicaciones.addAll(obtenerListaAlbumes(servPersistencia.recuperarPropiedadEntidad(eUsuario, "albumes")));
		List<Usuario> seguidores  = obtenerListaUsuario(servPersistencia.recuperarPropiedadEntidad(eUsuario, "seguidores"));
		List<Usuario> seguidos = obtenerListaUsuario(servPersistencia.recuperarPropiedadEntidad(eUsuario, "seguidos"));
		List<Notificacion> notificaciones  = obtenerListaNotificacion(servPersistencia.recuperarPropiedadEntidad(eUsuario, "notificaciones"));
		boolean premium = Boolean.parseBoolean(servPersistencia.recuperarPropiedadEntidad(eUsuario, "premium"));
		
		
		usuario.setPublicaciones(publicaciones);
		usuario.setSeguidores(seguidores);
		usuario.setSeguidos(seguidos);
		usuario.setNotificaciones(notificaciones);
		usuario.setPremium(premium);

		return usuario;
	}

	private Entidad usuarioToEntidad(Usuario usuario) {
		Entidad eUsuario = new Entidad();
		eUsuario.setNombre("Usuario");

		eUsuario.setPropiedades(new ArrayList<Propiedad>(Arrays.asList(
				new Propiedad("nombre", usuario.getNombre()),
				new Propiedad("apellidos", usuario.getApellidos()),
				new Propiedad("email", usuario.getEmail()),
				new Propiedad("login", usuario.getLogin()),
				new Propiedad("password", usuario.getPassword()),
				new Propiedad("fechaNacimiento", usuario.getFechaNacimiento()), 
				new Propiedad("description", usuario.getDescription()),
				new Propiedad("photologin", usuario.getPhotoLogin()),
				new Propiedad("fotos", obtenerCodigosFotos(usuario.getPublicaciones())),
				new Propiedad("albumes", obtenerCodigosAlbumes(usuario.getPublicaciones())),
				new Propiedad("seguidores", obtenerCodigosUsuario(usuario.getSeguidores())),
				new Propiedad("seguidos",obtenerCodigosUsuario(usuario.getSeguidos())),
				new Propiedad("notificaciones", obtenerCodigosNotificaciones(usuario.getNotificaciones())),
				new Propiedad("premium", String.valueOf(usuario.getPremium())))));
		return eUsuario;
	}

	public void create(Usuario usuario) {
		Entidad eUsuario = this.usuarioToEntidad(usuario);
		eUsuario = servPersistencia.registrarEntidad(eUsuario);
		usuario.setId(eUsuario.getId());
	}

	public boolean delete(Usuario usuario) {
		Entidad eUsuario;
		eUsuario = servPersistencia.recuperarEntidad(usuario.getId());

		return servPersistencia.borrarEntidad(eUsuario);
	}

	/**
	 * Permite que un Usuario modifique su perfil: password y email
	 */
	public void update(Usuario usuario) {
		Entidad eUsuario = servPersistencia.recuperarEntidad(usuario.getId());

		for (Propiedad prop : eUsuario.getPropiedades()) {
			if (prop.getNombre().equals("password")) {
				prop.setValor(usuario.getPassword());
			} else if (prop.getNombre().equals("email")) {
				prop.setValor(usuario.getEmail());
			} else if (prop.getNombre().equals("nombre")) {
				prop.setValor(usuario.getNombre());
			} else if (prop.getNombre().equals("apellidos")) {
				prop.setValor(usuario.getApellidos());
			} else if (prop.getNombre().equals("login")) {
				prop.setValor(usuario.getLogin());
			} else if (prop.getNombre().equals("fechaNacimiento")) {
				prop.setValor(usuario.getFechaNacimiento());
			} else if (prop.getNombre().equals("description")) {
				prop.setValor(usuario.getDescription());
			} else if  (prop.getNombre().equals("photologin")) {
				prop.setValor(usuario.getPhotoLogin());
			} else if  (prop.getNombre().equals("fotos")) {
				prop.setValor(String.valueOf(obtenerCodigosFotos(usuario.getPublicaciones())));
			} else if  (prop.getNombre().equals("albumes")) {
				prop.setValor(String.valueOf(obtenerCodigosAlbumes(usuario.getPublicaciones())));
			} else if  (prop.getNombre().equals("seguidores")) {
				prop.setValor(String.valueOf(obtenerCodigosUsuario(usuario.getSeguidores())));
			} else if  (prop.getNombre().equals("seguidos")) {
				prop.setValor(String.valueOf(obtenerCodigosUsuario(usuario.getSeguidos())));
			} else if  (prop.getNombre().equals("notificaciones")) {
				prop.setValor(String.valueOf(obtenerCodigosNotificaciones(usuario.getNotificaciones())));
			} else if (prop.getNombre().equals("premium")) {
				prop.setValor(String.valueOf(usuario.getPremium()));
			}
			servPersistencia.modificarPropiedad(prop);
		}
	}

	public Usuario get(int id) {
		
		if (PoolDAO.getUnicaInstancia().contiene(id)) return (Usuario)PoolDAO.getUnicaInstancia().getObjeto(id);
		
		Entidad eUsuario = servPersistencia.recuperarEntidad(id);
		
		return 	entidadToUsuario(eUsuario);
	}

	public List<Usuario> getAll() {
		List<Entidad> entidades = servPersistencia.recuperarEntidades("Usuario");

		List<Usuario> usuarios = new LinkedList<Usuario>();
		for (Entidad eUsuario : entidades) {
			usuarios.add(get(eUsuario.getId()));
		}

		return usuarios;
	}
	
	private String obtenerCodigosFotos(List<Publicacion> listaPublicacion) {
		String aux = "";
		for (Publicacion pub : listaPublicacion) {
			if (pub instanceof Foto) {
				aux += pub.getId() + " ";
			}
		}
		return aux.trim();
	}
	
	private String obtenerCodigosAlbumes(List<Publicacion> listaPublicacion) {
		String aux = "";
		for (Publicacion pub : listaPublicacion) {
			if (pub instanceof Album) {
				aux += pub.getId() + " ";
			}
		}
		return aux.trim();
	}
	
	private String obtenerCodigosUsuario(List<Usuario> listaUsuario) {
		String aux = "";
		for (Usuario usu : listaUsuario) {
			aux += usu.getId() + " ";
		}
		return aux.trim();
	}
	
	private String obtenerCodigosNotificaciones(List<Notificacion> listaNotifi) {
		String aux = "";
		for (Notificacion noti : listaNotifi) {
			aux += noti.getId() + " ";
		}
		return aux.trim();
	}
	
	public List<Publicacion> obtenerListaFotos(String listaPublicacion) {
		
		List<Publicacion> listaPub = new LinkedList<Publicacion>();
		StringTokenizer strTok = new StringTokenizer(listaPublicacion, " ");
		TDSFotoDAO adaptadorFoto = TDSFotoDAO.getUnicaInstancia();
		while (strTok.hasMoreTokens()) {
			String cadena = (String)strTok.nextElement();
			listaPub.add(adaptadorFoto.get(Integer.valueOf(cadena)));
		}
		return listaPub;
	}
	
public List<Publicacion> obtenerListaAlbumes(String listaPublicacion) {
		
		List<Publicacion> listaPub = new LinkedList<Publicacion>();
		StringTokenizer strTok = new StringTokenizer(listaPublicacion, " ");
		TDSAlbumDAO adaptadorAlbum = TDSAlbumDAO.getUnicaInstancia();
		while (strTok.hasMoreTokens()) {
			String cadena = (String)strTok.nextElement();
			listaPub.add(adaptadorAlbum.get(Integer.valueOf(cadena)));
		}
		return listaPub;
	}
	
	public List<Usuario> obtenerListaUsuario(String listaUsuario) {
		
		List<Usuario> listaUsu = new LinkedList<Usuario>();
		StringTokenizer strTok = new StringTokenizer(listaUsuario, " ");
		while (strTok.hasMoreTokens()) {
			String cadena = (String)strTok.nextElement();
				listaUsu.add(get(Integer.valueOf(cadena)));
		}
		return listaUsu;
	}

	public List<Notificacion> obtenerListaNotificacion(String listaNotificacion) {
			
			List<Notificacion> listaNoti = new LinkedList<Notificacion>();
			StringTokenizer strTok = new StringTokenizer(listaNotificacion, " ");
			TDSNotificacionDAO adaptadorNoti = TDSNotificacionDAO.getUnicaInstancia();
			while (strTok.hasMoreTokens()) {
				String cadena = (String)strTok.nextElement();
					listaNoti.add(adaptadorNoti.get(Integer.valueOf(cadena)));
			}
			return listaNoti;
	}
}

