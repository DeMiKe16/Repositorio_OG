package modelo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import controlador.Controlador;
import umu.tds.fotos.Fotos;

public class Foto extends Publicacion {

	private String path;

	public Foto(String titulo, String fecha, String descripcion, List<Usuario> megusta, List<Comentario> comentarios, String path, Usuario autor) {
		super(titulo, fecha, descripcion, megusta, parsearHashTagsFoto(descripcion),comentarios, autor);
		this.path = this.guardarFotoRelativa("publicaciones", new File(path));

	}
	
	public Foto(String titulo, String fecha, String descripcion, List<Usuario> megusta, Set<String> listaHash,
			List<Comentario> comentarios, String path, Usuario usuario) {
		super(titulo, fecha, descripcion, megusta, listaHash ,comentarios, usuario);
		this.path = this.guardarFotoRelativa("publicaciones", new File(path));
	}
	
	public static Set<String> parsearHashTagsFoto(String descripcion) {
		//Set<String> hashtags = new HashSet<String>();
        String[] palabras = descripcion.split("\\s+");
        
        /*for (String palabra : palabras) {
            if (palabra.startsWith("#")) {
            	if (!hashtags.contains(palabra.substring(1)))
            		hashtags.add(palabra.substring(1));
            }
        }
        return hashtags;*/
        return Arrays.stream(palabras)
                .filter(palabra -> palabra.startsWith("#"))
                .map(palabra -> palabra.substring(1))
                .collect(Collectors.toSet());
	}
	
	public static List<Foto> crearFotosPorCargador(Fotos archivoFotos) {
		List<umu.tds.fotos.Foto> fotos = archivoFotos.getFoto();
		List<Foto> listaFoto = new LinkedList<Foto>();

		/*for (umu.tds.fotos.Foto photo : fotos) {
			System.out.println(photo);
			String titulo = photo.getTitulo();
			String descripcion = photo.getDescripcion();
			String path = photo.getPath();
			Set<String> hashtags = new HashSet<>();
			for(HashTag h: photo.getHashTags())
				hashtags.addAll(h.getHashTag());
			Usuario autor = Controlador.getUnicaInstancia().getUsuarioActual();
			Foto auxuliar = new Foto(titulo, LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")), descripcion, new LinkedList<Usuario>(),
				hashtags ,new LinkedList<Comentario>(), path, autor);
			listaFoto.add(auxuliar);
		}*/
		fotos.forEach(photo -> {
	        System.out.println(photo);
	        String titulo = photo.getTitulo();
	        String descripcion = photo.getDescripcion();
	        String path = photo.getPath();
	        Set<String> hashtags = photo.getHashTags().stream()
	                .flatMap(h -> h.getHashTag().stream())
	                .collect(Collectors.toSet());
	        Usuario autor = Controlador.getUnicaInstancia().getUsuarioActual();
	        Foto auxiliar = new Foto(titulo, LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")),
	                descripcion, new LinkedList<>(), hashtags, new LinkedList<>(), path, autor);
	        listaFoto.add(auxiliar);
	    });
		
		return listaFoto;
	}
	
	public String guardarFotoRelativa(String ruta, File fichero) {
		String rutaDestino = "src/" + ruta.replace(".", "/") + "/" + fichero.getName();
		File imagenDestino = new File(rutaDestino);
		if (imagenDestino.exists()) {
			return fichero.getPath();
		} else {
			if (!imagenDestino.getParentFile().exists()) {
	            imagenDestino.getParentFile().mkdirs();
	        }
	        // Copia la imagen desde la ubicación original a la ubicación de destino
	        FileOutputStream fos;
	        FileInputStream fis;
			try {
				fos = new FileOutputStream(imagenDestino);
				fis = new FileInputStream(fichero);
				byte[] buffer = new byte[1024];
		        int length;
		        while ((length = fis.read(buffer)) > 0) {
		            fos.write(buffer, 0, length);
		        }
		        fos.close();
		        fis.close();
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return rutaDestino;
		}
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Override
	public String toString() {
		return super.toString() + "Foto [path=" + path + "]";
	}

}
