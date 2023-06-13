package DAO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import beans.Entidad;
import beans.Propiedad;
import modelo.Album;
import modelo.Foto;
import modelo.Notificacion;
import modelo.Publicacion;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;

public class TDSNotificacionDAO implements NotificacionDAO {

	
	private ServicioPersistencia servPersistencia;
	private static TDSNotificacionDAO unicaInstancia = null;

	public static TDSNotificacionDAO getUnicaInstancia() { // patron singleton
		if (unicaInstancia == null)
			return new TDSNotificacionDAO();
		else
			return unicaInstancia;
	}
	
	public TDSNotificacionDAO() {
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
	}
	
	public Notificacion entidadToNotificacion(Entidad eNotificacion) {
		String fecha = servPersistencia.recuperarPropiedadEntidad(eNotificacion, "fecha");
		Foto f =  obtenerFoto(servPersistencia.recuperarPropiedadEntidad(eNotificacion, "foto"));
		Album a =  obtenerAlbum(servPersistencia.recuperarPropiedadEntidad(eNotificacion, "album"));
		
		Publicacion publicacion;
		if (a == null) 
			publicacion = f;
		else 
			publicacion = a;
		
		Notificacion notificacion = new Notificacion(fecha, publicacion);
		notificacion.setId(eNotificacion.getId());
		
		PoolDAO.getUnicaInstancia().addObjeto(eNotificacion.getId(), notificacion);

		return notificacion;
	}

	private Entidad notificacionToEntidad(Notificacion notificacion) {
		Entidad eNotificacion = new Entidad();
		eNotificacion.setNombre("Notificacion");
		
		eNotificacion.setPropiedades(new ArrayList<Propiedad>(Arrays.asList(
				new Propiedad("fecha", notificacion.getFecha()),
				new Propiedad("foto", obtenerCodigoFoto(notificacion.getPublicacion())),
				new Propiedad("album", obtenerCodigoAlbum(notificacion.getPublicacion())))));
		
		return eNotificacion;
	}

	@Override
	public void create(Notificacion notificacion) {
		Entidad eNotificacion = this.notificacionToEntidad(notificacion);
		eNotificacion = servPersistencia.registrarEntidad(eNotificacion);
		notificacion.setId(eNotificacion.getId());
	}
	
	@Override
	public boolean delete(Notificacion notificacion) {
		Entidad eNotificacion;
		eNotificacion = servPersistencia.recuperarEntidad(notificacion.getId());

		return servPersistencia.borrarEntidad(eNotificacion);
	}

	@Override
	public void update(Notificacion notificacion) {
		Entidad eNotificacion = servPersistencia.recuperarEntidad(notificacion.getId());
		
		for (Propiedad prop : eNotificacion.getPropiedades()) {
			if (prop.getNombre().equals("fecha")) {
				prop.setValor(notificacion.getFecha());
			} else if (prop.getNombre().equals("foto")) {
				prop.setValor(obtenerCodigoFoto(notificacion.getPublicacion()));
			} else if (prop.getNombre().equals("album")) {
				prop.setValor(obtenerCodigoAlbum(notificacion.getPublicacion()));
			}
			servPersistencia.modificarPropiedad(prop);
		}
	}

	@Override
	public Notificacion get(int id) {
		if (PoolDAO.getUnicaInstancia().contiene(id)) return (Notificacion)PoolDAO.getUnicaInstancia().getObjeto(id);
		
		Entidad eNotificacion = servPersistencia.recuperarEntidad(id);

		return entidadToNotificacion(eNotificacion);
	}

	@Override
	public List<Notificacion> getAll() {
		List<Entidad> entidades = servPersistencia.recuperarEntidades("Comentario");

		List<Notificacion> notificaciones = new LinkedList<Notificacion>();
		for (Entidad eNotificacion : entidades) {
			notificaciones.add(get(eNotificacion.getId()));
		}

		return notificaciones;
	}
	
	private String obtenerCodigoFoto(Publicacion publicacion) {
		String aux = "";
		if (publicacion instanceof Foto)
			aux += publicacion.getId()  + " ";
		return aux.trim();
	}
	
	private String obtenerCodigoAlbum(Publicacion publicacion) {
		String aux = "";
		if (publicacion instanceof Album)
			aux += publicacion.getId()  + " ";
		return aux.trim();
	}
	
	private Foto obtenerFoto(String publicacion) {
		StringTokenizer strTok = new StringTokenizer(publicacion, " ");
		TDSFotoDAO adaptadorFoto = TDSFotoDAO.getUnicaInstancia();
		Foto pub = null;
		while (strTok.hasMoreTokens()) {
			String cadena = (String)strTok.nextElement();
			pub = adaptadorFoto.get(Integer.valueOf(cadena));
		}
		return pub;
	}
	
	private Album obtenerAlbum(String publicacion) {
		StringTokenizer strTok = new StringTokenizer(publicacion, " ");
		TDSAlbumDAO adaptadorAlbum = TDSAlbumDAO.getUnicaInstancia();
		Album pub = null;
		while (strTok.hasMoreTokens()) {
			String cadena = (String)strTok.nextElement();
			pub = adaptadorAlbum.get(Integer.valueOf(cadena));
		}
		return pub;
	}
	
	
	
}
