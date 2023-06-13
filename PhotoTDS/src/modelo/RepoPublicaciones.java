package modelo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import DAO.DAOException;
import DAO.FactoriaDAO;

public class RepoPublicaciones {

	private static RepoPublicaciones unicaInstancia;
	private FactoriaDAO factoria;

	private HashMap<String, List<Foto>> FotoPorHashTag;
	private HashMap<String, Foto> FotoPorTitulo;
	private HashMap<String, List<Album>> AlbumPorHashTag;
	private HashMap<String, Album> AlbumPorTitulo;

	public static RepoPublicaciones getUnicaInstancia() {
		if (unicaInstancia == null)
			unicaInstancia = new RepoPublicaciones();
		return unicaInstancia;
	}

	private RepoPublicaciones() {
		FotoPorHashTag = new HashMap<String, List<Foto>>();
		FotoPorTitulo = new HashMap<String, Foto>();
		AlbumPorHashTag = new HashMap<String, List<Album>>();
		AlbumPorTitulo = new HashMap<String, Album>();

		try {
			factoria = FactoriaDAO.getInstancia();

			List<Foto> listaFoto = factoria.getFotoDAO().getAll();
			/*for (Foto foto : listaFoto) {
				for (String hashTag : foto.getHashtags()) {
					List<Foto> listaFotos = FotoPorHashTag.get(hashTag);
					if (listaFotos == null) {
						listaFotos = new ArrayList<>();
						FotoPorHashTag.put(hashTag, listaFotos);
					}
					listaFotos.add(foto);
				}
				FotoPorTitulo.put(foto.getTitulo(), foto);
			}*/
			 listaFoto.forEach(foto -> {
		            foto.getHashtags().forEach(hashTag -> {
		                List<Foto> listaFotos = FotoPorHashTag.computeIfAbsent(hashTag, k -> new ArrayList<>());
		                listaFotos.add(foto);
		            });
		            FotoPorTitulo.put(foto.getTitulo(), foto);
		        });
			List<Album> listaAlbum = factoria.getAlbumDAO().getAll();
			/*for (Album album : listaAlbum) {
				for (String hashTag : album.getHashtags()) {
					List<Album> listaAlbumes = AlbumPorHashTag.get(hashTag);
					if (listaAlbumes == null) {
						listaAlbumes = new ArrayList<>();
						AlbumPorHashTag.put(hashTag, listaAlbumes);
					}
					listaAlbumes.add(album);
				}
				//AlbumPorHashTag.put(hashTag, album);
				AlbumPorTitulo.put(album.getTitulo(), album);
			}*/
			listaAlbum.forEach(album -> {
	            album.getHashtags().forEach(hashTag -> {
	                List<Album> listaAlbumes = AlbumPorHashTag.computeIfAbsent(hashTag, k -> new ArrayList<>());
	                listaAlbumes.add(album);
	            });
	            AlbumPorTitulo.put(album.getTitulo(), album);
	        });

		} catch (DAOException eDAO) {
			eDAO.printStackTrace();
		}
	}

	public List<Foto> getFotos() throws DAOException {
		return new LinkedList<Foto>(FotoPorTitulo.values());
	}

	public List<Album> getAlbum() throws DAOException {
		return new LinkedList<Album>(AlbumPorTitulo.values());
	}

	public Foto getFotoPorTitulo(String titulo) {
		return FotoPorTitulo.get(titulo);
	}

	public List<Foto> getFotoPorHashTag(String hashtag) {
		return FotoPorHashTag.get(hashtag);
	}

	public Album getAlbumPorTitulo(String titulo) {
		return AlbumPorTitulo.get(titulo);
	}

	public List<Album> getAlbumPorHashTag(String hashtag) {
		return AlbumPorHashTag.get(hashtag);
	}

	public void addFoto(Foto foto) {
		/*for (String hashTag : foto.getHashtags()) {
			List<Foto> listaFotos = FotoPorHashTag.get(hashTag);
			if (listaFotos == null) {
				listaFotos = new ArrayList<>();
				FotoPorHashTag.put(hashTag, listaFotos);
			}
			listaFotos.add(foto);
		}*/
		foto.getHashtags().forEach(hashTag -> {
	        List<Foto> listaFotos = FotoPorHashTag.computeIfAbsent(hashTag, k -> new ArrayList<>());
	        listaFotos.add(foto);
	    });
		FotoPorTitulo.put(foto.getTitulo(),foto);
	}
	

	public void addAlbum(Album album) {
		/*for (String hashTag : album.getHashtags()) {
			List<Album> listaAlbumes = AlbumPorHashTag.get(hashTag);
			if (listaAlbumes == null) {
				listaAlbumes = new ArrayList<>();
				AlbumPorHashTag.put(hashTag, listaAlbumes);
			}
			listaAlbumes.add(album);
		}*/
		album.getHashtags().forEach(hashTag -> {
	        List<Album> listaAlbumes = AlbumPorHashTag.computeIfAbsent(hashTag, k -> new ArrayList<>());
	        listaAlbumes.add(album);
	    });
		AlbumPorTitulo.put(album.getTitulo(), album);
	}

	public void removeFoto(Foto foto) {
		/*for (String hashTag : foto.getHashtags())
			FotoPorHashTag.remove(hashTag, foto);*/
		foto.getHashtags().forEach(hashTag -> FotoPorHashTag.remove(hashTag, foto));
		FotoPorTitulo.remove(foto.getTitulo(), foto);
	}

	public void removeAlbum(Album album) {
		/*for (String hashTag : album.getHashtags())
			AlbumPorHashTag.remove(hashTag, album);*/
		album.getHashtags().forEach(hashTag -> AlbumPorHashTag.remove(hashTag, album));
		AlbumPorTitulo.remove(album.getTitulo(), album);
	}
	
	public List<String> hashTagsDeSubcadena(String subcadena){
		List<String> hashtags = new ArrayList<>();
		/*for(String s: FotoPorHashTag.keySet()) {
			if(s.contains(subcadena))
				hashtags.add(s);
		}
		for(String s: AlbumPorHashTag.keySet()) {
			if(s.contains(subcadena))
				hashtags.add(s);
		}*/
		FotoPorHashTag.keySet().stream()
        	.filter(s -> s.contains(subcadena))
        	.forEach(hashtags::add);

		AlbumPorHashTag.keySet().stream()
        	.filter(s -> s.contains(subcadena))
        	.forEach(hashtags::add);
		
		return hashtags;
	}
	
}
