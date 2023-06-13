package DAO;

import java.util.List;

import modelo.Foto;

public interface FotoDAO {
	
	void create(Foto asistente);
	boolean delete(Foto asistente);
	void update(Foto asistente);
	Foto get(int id);
	List<Foto> getAll();
	
}
