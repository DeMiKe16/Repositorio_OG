package DAO;

import java.util.List;

import modelo.Comentario;

public interface ComentarioDAO {
	
	void create(Comentario asistente);
	boolean delete(Comentario asistente);
	void update(Comentario asistente);
	Comentario get(int id);
	List<Comentario> getAll();
	
}
