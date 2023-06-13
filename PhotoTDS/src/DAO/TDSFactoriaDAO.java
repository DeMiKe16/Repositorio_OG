package DAO;

/** 
 * Factoria concreta DAO para el Servidor de Persistencia de la asignatura TDS.
 * 
 */

public final class TDSFactoriaDAO extends FactoriaDAO {
	
	public TDSFactoriaDAO() {	}
	
	@Override
	public TDSUsuarioDAO getUsuarioDAO() {	
		return new TDSUsuarioDAO(); 
	}
	
	@Override
	public TDSFotoDAO getFotoDAO() {	
		return new TDSFotoDAO(); 
	}
	
	@Override
	public TDSAlbumDAO getAlbumDAO() {	
		return new TDSAlbumDAO(); 
	}
	
	@Override
	public TDSComentarioDAO getComentarioDAO() {
		return new TDSComentarioDAO();
	}
	
	@Override
	public TDSNotificacionDAO getNotificacionDAO() {
		return new TDSNotificacionDAO();
	}

}
