package DAO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.StringTokenizer;

import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;
import beans.Entidad;
import beans.Propiedad;
import modelo.Comentario;
import modelo.Foto;
import modelo.Usuario;

/**
 * 
 * Clase que implementa el Adaptador DAO concreto de Usuario para el tipo H2.
 * 
 */
public final class TDSFotoDAO implements FotoDAO {

	private ServicioPersistencia servPersistencia;
	private static TDSFotoDAO unicaInstancia = null;

	public static TDSFotoDAO getUnicaInstancia() { // patron singleton
		if (unicaInstancia == null)
			return new TDSFotoDAO();
		else
			return unicaInstancia;
	}
	
	public TDSFotoDAO() {
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
	}

	private Foto entidadToFoto(Entidad eFoto) {
		// recuperar propiedades que no son objetos

		String titulo = servPersistencia.recuperarPropiedadEntidad(eFoto, "titulo");
		String fecha = servPersistencia.recuperarPropiedadEntidad(eFoto, "fecha");
		String descripcion = servPersistencia.recuperarPropiedadEntidad(eFoto, "descripcion");		
		String hashtags = servPersistencia.recuperarPropiedadEntidad(eFoto, "hashtags");
		String path = servPersistencia.recuperarPropiedadEntidad(eFoto, "path");
		Usuario usuario =  TDSUsuarioDAO.getUnicaInstancia().get(Integer.valueOf(servPersistencia.recuperarPropiedadEntidad(eFoto, "usuario")));	
		List<Usuario> megusta = obtenerListaUsuario(servPersistencia.recuperarPropiedadEntidad(eFoto, "megusta"));
		List<Comentario> comentarios = obtenerListaComentarios(servPersistencia.recuperarPropiedadEntidad(eFoto, "comentarios"));
		
		String[] lista = hashtags.split(" ");
		
		Set<String> SetHash = new HashSet<String>(Arrays.asList(lista));
		
		Foto foto = new Foto(titulo, fecha, descripcion, megusta, SetHash, comentarios, path, usuario);
		foto.setId(eFoto.getId());
		
		PoolDAO.getUnicaInstancia().addObjeto(eFoto.getId(), foto);

		return foto;
	}
	
	private Entidad fotoToEntidad(Foto foto) {
		Entidad eFoto = new Entidad();
		eFoto.setNombre("Foto");

		String listaHashtag = "";
		for (String s : foto.getHashtags())
		{
			listaHashtag += s + " ";
		}
		String aux = "" + foto.getIdAutor(); 
		
		eFoto.setPropiedades(new ArrayList<Propiedad>(Arrays.asList(
				new Propiedad("titulo", foto.getTitulo()),
				new Propiedad("fecha", foto.getFecha()),
				new Propiedad("descripcion", foto.getDescripcion()),
				new Propiedad("megusta", obtenerCodigosUsuario(foto.getMegusta())),
				new Propiedad("hashtags", listaHashtag),
				new Propiedad("path", foto.getPath()), 
				new Propiedad("comentarios", obtenerCodigosComentarios(foto.getComentarios())),
				new Propiedad("usuario", aux.trim()))));
		return eFoto;
	}

	@Override
	public void create(Foto foto) {
		Entidad eFoto = this.fotoToEntidad(foto);
		eFoto = servPersistencia.registrarEntidad(eFoto);
		foto.setId(eFoto.getId());
	}

	@Override
	public boolean delete(Foto foto) {
		Entidad eFoto;
		eFoto = servPersistencia.recuperarEntidad(foto.getId());

		return servPersistencia.borrarEntidad(eFoto);
	}

	@Override
	public void update(Foto foto) {
		Entidad eFoto = servPersistencia.recuperarEntidad(foto.getId());

		String listaHashtag = "";
		for (String s : foto.getHashtags())
		{
			s.replace(" ", ".");
			listaHashtag += s + ",";
		}
		
		String aux = "" + foto.getIdAutor();
		
		for (Propiedad prop : eFoto.getPropiedades()) {
			if (prop.getNombre().equals("titulo")) {
				prop.setValor(foto.getTitulo());
			} else if (prop.getNombre().equals("fecha")) {
				prop.setValor(foto.getFecha());
			} else if (prop.getNombre().equals("descripcion")) {
				prop.setValor(foto.getDescripcion());
			} else if (prop.getNombre().equals("megusta")) {
				prop.setValor(String.valueOf(obtenerCodigosUsuario(foto.getMegusta())));
			} else if (prop.getNombre().equals("hashtags")) {
				prop.setValor(listaHashtag);
			} else if (prop.getNombre().equals("comentarios")) {
				prop.setValor(obtenerCodigosComentarios(foto.getComentarios()));
			} else if (prop.getNombre().equals("path")) {
				prop.setValor(foto.getPath());
			} else if (prop.getNombre().equals("usuario")){
				prop.setValor(aux.trim());
			}
			servPersistencia.modificarPropiedad(prop);
		}
	}	

	@Override
	public Foto get(int id) {
		
		if (PoolDAO.getUnicaInstancia().contiene(id)) return (Foto)PoolDAO.getUnicaInstancia().getObjeto(id);
		
		Entidad eFoto = servPersistencia.recuperarEntidad(id);

		return entidadToFoto(eFoto);
	}

	@Override
	public List<Foto> getAll() {
		List<Entidad> entidades = servPersistencia.recuperarEntidades("Foto");

		List<Foto> fotos = new LinkedList<Foto>();
		for (Entidad eFoto : entidades) {
			fotos.add(get(eFoto.getId()));
		}

		return fotos;
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
		
	private List<Comentario> obtenerListaComentarios(String listaComentarios) {
		List<Comentario> listaComent = new LinkedList<Comentario>();
		StringTokenizer strTok = new StringTokenizer(listaComentarios, " ");
		TDSComentarioDAO adaptadorComentario = TDSComentarioDAO.getUnicaInstancia();
		while (strTok.hasMoreTokens()) {
			listaComent.add(adaptadorComentario.get(Integer.valueOf((String)strTok.nextElement())));
		}
		return listaComent;
	}
	
	private String obtenerCodigosUsuario(List<Usuario> listaUsuario) {
		String aux = "";
		for (Usuario usu : listaUsuario) {
			aux += usu.getId() + " ";
		}
		return aux.trim();
	}
	
	private String obtenerCodigosComentarios(List<Comentario> listaComentarios) {
		String aux = "";
		for (Comentario coment : listaComentarios) {
			aux += coment.getId() + " ";
		}
		return aux.trim();
	}
}