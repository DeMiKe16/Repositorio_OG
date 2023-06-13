package DAO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.StringTokenizer;

import beans.Entidad;
import beans.Propiedad;
import modelo.Album;
import modelo.Comentario;
import modelo.Foto;
import modelo.Usuario;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;

public class TDSAlbumDAO implements AlbumDAO {
	
	private ServicioPersistencia servPersistencia;
	private static TDSAlbumDAO unicaInstancia = null;

	public static TDSAlbumDAO getUnicaInstancia() { // patron singleton
		if (unicaInstancia == null)
			return new TDSAlbumDAO();
		else
			return unicaInstancia;
	}
	
	public TDSAlbumDAO() {
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
	}

	public Album entidadToAlbum(Entidad eAlbum) {

		String titulo = servPersistencia.recuperarPropiedadEntidad(eAlbum, "titulo");
		String fecha = servPersistencia.recuperarPropiedadEntidad(eAlbum, "fecha");
		String descripcion = servPersistencia.recuperarPropiedadEntidad(eAlbum, "descripcion");
		List<Usuario> megusta = obtenerListaUsuario(servPersistencia.recuperarPropiedadEntidad(eAlbum, "megusta"));
		String hashtags = servPersistencia.recuperarPropiedadEntidad(eAlbum, "hashtags");
		List<Comentario> comentarios = obtenerListaComentarios(servPersistencia.recuperarPropiedadEntidad(eAlbum, "comentarios"));
		List<Foto> fotos = obtenerListaFotos(servPersistencia.recuperarPropiedadEntidad(eAlbum, "fotos"));
		Usuario usuario = TDSUsuarioDAO.getUnicaInstancia().get(Integer.valueOf(servPersistencia.recuperarPropiedadEntidad(eAlbum, "usuario")));
		
		String[] lista = hashtags.split(" ");
		
		Set<String> SetHash = new HashSet<String>(Arrays.asList(lista));
		
		Album album = new Album(titulo, fecha, descripcion, megusta, SetHash, comentarios, fotos, usuario);
		album.setId(eAlbum.getId());
		
		PoolDAO.getUnicaInstancia().addObjeto(eAlbum.getId(), album);

		return album;
	}
	
	private Entidad albumToEntidad(Album album) {
		Entidad eAlbum = new Entidad();
		eAlbum.setNombre("Album");

		String listaHashtag = "";
		for (String s : album.getHashtags())
		{
			listaHashtag += s + " ";
		}
		String aux = "" + album.getIdAutor();
		
		eAlbum.setPropiedades(new ArrayList<Propiedad>(Arrays.asList(
				new Propiedad("titulo", album.getTitulo()),
				new Propiedad("fecha", album.getFecha()),
				new Propiedad("descripcion", album.getDescripcion()),
				new Propiedad("megusta", obtenerCodigosUsuario(album.getMegusta())),
				new Propiedad("hashtags", listaHashtag),
				new Propiedad("fotos", obtenerCodigosFotos(album.getFotos())), 
				new Propiedad("comentarios", obtenerCodigosComentarios(album.getComentarios())),
				new Propiedad("usuario", aux.trim()))));
		
		return eAlbum;
	}

	@Override
	public void create(Album album) {
		Entidad eAlbum = this.albumToEntidad(album);
		eAlbum = servPersistencia.registrarEntidad(eAlbum);
		album.setId(eAlbum.getId());
	}

	@Override
	public boolean delete(Album album) {
		Entidad eAlbum;
		eAlbum = servPersistencia.recuperarEntidad(album.getId());

		return servPersistencia.borrarEntidad(eAlbum);
	}

	@Override
	public void update(Album album) {
		Entidad eAlbum = servPersistencia.recuperarEntidad(album.getId());

		String listaHashtag = "";
		for (String s : album.getHashtags())
		{
			s.replace(" ", ".");
			listaHashtag += s + ",";
		}
		String aux = "" + album.getIdAutor();
		
		for (Propiedad prop : eAlbum.getPropiedades()) {
			if (prop.getNombre().equals("titulo")) {
				prop.setValor(album.getTitulo());
			} else if (prop.getNombre().equals("fecha")) {
				prop.setValor(album.getFecha());
			} else if (prop.getNombre().equals("descripcion")) {
				prop.setValor(album.getDescripcion());
			} else if (prop.getNombre().equals("megusta")) {
				prop.setValor(obtenerCodigosUsuario(album.getMegusta()));
			} else if (prop.getNombre().equals("hashtags")) {
				prop.setValor(listaHashtag);
			} else if (prop.getNombre().equals("comentarios")) {
				prop.setValor(obtenerCodigosComentarios(album.getComentarios()));
			} else if (prop.getNombre().equals("fotos")) {
				prop.setValor(obtenerCodigosFotos(album.getFotos()));
			} else if (prop.getNombre().equals("usuario")) {
				prop.setValor(aux.trim());
			}
			servPersistencia.modificarPropiedad(prop);
		}
	}

	@Override
	public Album get(int id) {
		
		if (PoolDAO.getUnicaInstancia().contiene(id)) return (Album)PoolDAO.getUnicaInstancia().getObjeto(id);
		
		Entidad eFoto = servPersistencia.recuperarEntidad(id);

		return entidadToAlbum(eFoto);
	}

	@Override
	public List<Album> getAll() {
		List<Entidad> entidades = servPersistencia.recuperarEntidades("Album");

		List<Album> albumes = new LinkedList<Album>();
		for (Entidad eAlbum : entidades) {
			albumes.add(get(eAlbum.getId()));
		}

		return albumes;
	}
	
	private List<Comentario> obtenerListaComentarios(String listaComentarios) {
		List<Comentario> listaComent = new LinkedList<Comentario>();
		StringTokenizer strTok = new StringTokenizer(listaComentarios, " ");
		TDSComentarioDAO adaptadorComentario = TDSComentarioDAO.getUnicaInstancia();
		while (strTok.hasMoreTokens()) {
			listaComent.add(adaptadorComentario.get(Integer.valueOf((String)strTok.nextElement())));
		}
		return listaComent;
	}
	
	private String obtenerCodigosComentarios(List<Comentario> listaComentarios) {
		String aux = "";
		for (Comentario coment : listaComentarios) {
			aux += coment.getId() + " ";
		}
		return aux.trim();
	}
	
	private List<Usuario> obtenerListaUsuario(String listaUsuario){
		List<Usuario> listaUsu = new LinkedList<Usuario>();
		StringTokenizer strTok = new StringTokenizer(listaUsuario, " ");
		TDSUsuarioDAO adaptadorUsuario = TDSUsuarioDAO.getUnicaInstancia();
		while (strTok.hasMoreTokens()) {
			String cadena = (String)strTok.nextElement();
				listaUsu.add(adaptadorUsuario.get(Integer.valueOf(cadena)));
		}
		return listaUsu;
	}
	
	private List<Foto> obtenerListaFotos(String listaFotos) {
		List<Foto> listaPhoto = new LinkedList<Foto>();
		StringTokenizer strTok = new StringTokenizer(listaFotos, " ");
		TDSFotoDAO adaptadorFoto = TDSFotoDAO.getUnicaInstancia();
		while (strTok.hasMoreTokens()) {
			listaPhoto.add(adaptadorFoto.get(Integer.valueOf((String)strTok.nextElement())));
		}
		return listaPhoto;
	}
	
	private String obtenerCodigosFotos(List<Foto> listaFotos) {
		String aux = "";
		for (Foto foto : listaFotos) {
			aux += foto.getId() + " ";
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
}
