package DAO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import beans.Entidad;
import beans.Propiedad;
import modelo.Comentario;
import modelo.Usuario;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;

public class TDSComentarioDAO implements ComentarioDAO {
	
	private ServicioPersistencia servPersistencia;
	private static TDSComentarioDAO unicaInstancia = null;

	public static TDSComentarioDAO getUnicaInstancia() { // patron singleton
		if (unicaInstancia == null)
			return new TDSComentarioDAO();
		else
			return unicaInstancia;
	}
	
	public TDSComentarioDAO() {
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
	}
	
	public Comentario entidadToComentario(Entidad eComentario) {
		
		String texto = servPersistencia.recuperarPropiedadEntidad(eComentario, "texto");
		Usuario usuario =  TDSUsuarioDAO.getUnicaInstancia().get(Integer.valueOf(servPersistencia.recuperarPropiedadEntidad(eComentario, "usuario")));
		

		Comentario comentario = new Comentario(texto, usuario);
		comentario.setId(eComentario.getId());
		
		PoolDAO.getUnicaInstancia().addObjeto(eComentario.getId(), comentario);

		return comentario;
	}

	private Entidad comentarioToEntidad(Comentario comentario) {
		Entidad eComentario = new Entidad();
		eComentario.setNombre("Comentario");
		
		String aux = "" + comentario.getUsuario().getId();
		
		eComentario.setPropiedades(new ArrayList<Propiedad>(Arrays.asList(
				new Propiedad("texto", comentario.getTexto()),
				new Propiedad("usuario", aux.trim()))));
		
		return eComentario;
	}

	@Override
	public void create(Comentario comentario) {
		Entidad eComentario = this.comentarioToEntidad(comentario);
		eComentario = servPersistencia.registrarEntidad(eComentario);
		comentario.setId(eComentario.getId());
		
	}

	@Override
	public boolean delete(Comentario comentario) {
		Entidad eComentario;
		eComentario = servPersistencia.recuperarEntidad(comentario.getId());

		return servPersistencia.borrarEntidad(eComentario);
	}

	@Override
	public void update(Comentario comentario) {
		Entidad eComentario = servPersistencia.recuperarEntidad(comentario.getId());
		
		String aux = "" + comentario.getUsuario().getId();
		
		for (Propiedad prop : eComentario.getPropiedades()) {
			if (prop.getNombre().equals("texto")) {
				prop.setValor(comentario.getTexto());
			} else if (prop.getNombre().equals("usuario")) {
				prop.setValor(aux.trim());
			}
				
			servPersistencia.modificarPropiedad(prop);
		}
	}

	@Override
	public Comentario get(int id) {
		if (PoolDAO.getUnicaInstancia().contiene(id)) return (Comentario)PoolDAO.getUnicaInstancia().getObjeto(id);
		
		Entidad eComentario = servPersistencia.recuperarEntidad(id);

		return entidadToComentario(eComentario);
	}

	@Override
	public List<Comentario> getAll() {
		List<Entidad> entidades = servPersistencia.recuperarEntidades("Comentario");

		List<Comentario> comentarios = new LinkedList<Comentario>();
		for (Entidad eComentario : entidades) {
			comentarios.add(get(eComentario.getId()));
		}

		return comentarios;
	}
	
}
