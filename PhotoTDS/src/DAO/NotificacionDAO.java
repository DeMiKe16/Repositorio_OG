package DAO;

import java.util.List;

import modelo.Notificacion;

public interface NotificacionDAO {

	void create(Notificacion notificacion);
	boolean delete(Notificacion notificacion);
	void update(Notificacion notificacion);
	Notificacion get(int id);
	List<Notificacion> getAll();
}