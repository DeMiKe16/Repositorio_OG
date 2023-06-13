package DAO;

import java.util.List;

import modelo.Album;

public interface AlbumDAO {

	void create(Album asistente);
	boolean delete(Album asistente);
	void update(Album asistente);
	Album get(int id);
	List<Album> getAll();
}
