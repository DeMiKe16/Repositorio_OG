package test;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import DAO.TDSFotoDAO;
import DAO.TDSUsuarioDAO;
import controlador.Controlador;
import modelo.Comentario;
import modelo.Foto;
import modelo.Notificacion;
import modelo.Publicacion;
import modelo.RepoPublicaciones;
import modelo.RepoUsuarios;
import modelo.Usuario;


public class PruebasControlador {

	@Test //Funcionará la primera vez, debido a la base de datos, si se detecta que la cuenta ya ha sido creado, dará falso
	public void testRegistrarUsuario() {
		String nombre = "usuario";
		String apellidos = "1";
		String email = "usuario1@um.es";
		String login = "usuario1";
		String password = "abc";
		String photologin = "rutaperfilusuario.png";
		String fechaNacimiento = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
		boolean resultado = false; //False en caso de que ya se crease el usuario
		assertEquals(resultado, Controlador.getUnicaInstancia().registrarUsuario(nombre, apellidos, email, login, password, fechaNacimiento, null, photologin));
	}
	
	@Test
	public void testSubirFoto() {
		String nombre = "usuario1";
		String password = "abc";
		Controlador.getUnicaInstancia().loginUsuario(nombre, password);
		String ruta = "rutadeejemplo.png";
		String titulo = "Foto test";
		String descripcion = "Esto es una #prueba";
		boolean resultado = true;
		assertEquals(resultado, Controlador.getUnicaInstancia().subirFoto(ruta, titulo, descripcion));
	}
	
	@Test
	public void testCrearFoto() {
		String nombre = "usuario1";
		String password = "abc";
		Controlador.getUnicaInstancia().loginUsuario(nombre, password);
		List<Foto> fotos = new LinkedList<Foto>();
		String ruta1 = "rutadeejemplo1.png";
		String titulo1 = "Album test1";
		String descripcion1 = "Esto es una prueba de album1";
		Controlador.getUnicaInstancia().crearFotoAlbum(fotos, ruta1, titulo1, descripcion1);
		String tituloAlbum = "Album prueba";
		String descripcionAlbum = "Esto es una prueba del album";
		boolean resultado = true;
		assertEquals(resultado, Controlador.getUnicaInstancia().subirAlbum(fotos, tituloAlbum, descripcionAlbum));	
	}
	
	@Test
	public void testCrearAlbum() {
		String nombre = "usuario1";
		String password = "abc";
		Controlador.getUnicaInstancia().loginUsuario(nombre, password);
		List<Foto> fotos = new LinkedList<Foto>();
		String ruta1 = "rutadeejemplo1.png";
		String titulo1 = "Album test1";
		String descripcion1 = "Esto es una prueba de album1";
		Controlador.getUnicaInstancia().crearFotoAlbum(fotos, ruta1, titulo1, descripcion1);
		String tituloAlbum = "Album prueba";
		String descripcionAlbum = "Esto es una prueba del album";
		boolean resultado = true;
		assertEquals(resultado, Controlador.getUnicaInstancia().subirAlbum(fotos, tituloAlbum, descripcionAlbum));	
	}
	
	@Test
	public void testSeguirUsuario() {
		String nombre = "usuario";
		String apellidos = "2";
		String email = "usuario2@um.es";
		String login = "usuario2";
		String password = "abc";
		String fechaNacimiento = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
		String photologin = "rutaperfilusuario.png";
		Usuario usuarioPrueba = new Usuario(nombre, apellidos, email, login, password, fechaNacimiento, null, photologin, new LinkedList<Publicacion>(),new LinkedList<Usuario>(), new LinkedList<Usuario>(), new LinkedList<Notificacion>());
		TDSUsuarioDAO.getUnicaInstancia().create(usuarioPrueba);
		RepoUsuarios.getUnicaInstancia().addUsuario(usuarioPrueba);
		String nombre1 = "usuario1";
		String password1 = "abc";
		Controlador.getUnicaInstancia().loginUsuario(nombre1, password1);
		boolean resultado = true;
		assertEquals(resultado, Controlador.getUnicaInstancia().seguir(usuarioPrueba));	
	}
	
	@Test
	public void testDarMegustaPublicacion() {
		String nombre = "usuario1";
		String password = "abc";
		Controlador.getUnicaInstancia().loginUsuario(nombre, password);
		Usuario usuarioActual = Controlador.getUnicaInstancia().getUsuarioActual();
		String ruta = "rutadeejemplo.png";
		String titulo = "Foto test";
		String descripcion = "Esto es una #prueba";
		String fechaNacimiento = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
		Foto fotoPrueba = new Foto(titulo, fechaNacimiento, descripcion, new LinkedList<Usuario>(), new LinkedList<Comentario>(), ruta, usuarioActual);
		TDSFotoDAO.getUnicaInstancia().create(fotoPrueba);
		RepoPublicaciones.getUnicaInstancia().addFoto(fotoPrueba);
		boolean resultado = true;
		assertEquals(resultado, Controlador.getUnicaInstancia().darLike(fotoPrueba));	
	}
	
	
	
	
	
	

}
