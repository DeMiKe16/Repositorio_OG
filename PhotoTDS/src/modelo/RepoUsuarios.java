package modelo;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import DAO.DAOException;
import DAO.FactoriaDAO;

public class RepoUsuarios {
	private static RepoUsuarios unicaInstancia;
	private FactoriaDAO factoria;

	private HashMap<Integer, Usuario> usuarioPorID;
	private HashMap<String, Usuario> usuarioPorLogin;

	public static RepoUsuarios getUnicaInstancia() {
		if (unicaInstancia == null)
			unicaInstancia = new RepoUsuarios();
		return unicaInstancia;
	}

	private RepoUsuarios() {
		usuarioPorID = new HashMap<Integer, Usuario>();
		usuarioPorLogin = new HashMap<String, Usuario>();

		try {
			factoria = FactoriaDAO.getInstancia();

			List<Usuario> listausuario = factoria.getUsuarioDAO().getAll();
			/*for (Usuario usuario : listausuario) {
				usuarioPorID.put(usuario.getId(), usuario);
				usuarioPorLogin.put(usuario.getLogin(), usuario);
			}*/
			listausuario.forEach(usuario -> {
	            usuarioPorID.put(usuario.getId(), usuario);
	            usuarioPorLogin.put(usuario.getLogin(), usuario);
	        });
		} catch (DAOException eDAO) {
			eDAO.printStackTrace();
		}
	}

	public List<Usuario> getUsuarios() throws DAOException {
		return new LinkedList<Usuario>(usuarioPorLogin.values());
	}

	public Usuario getUsuario(String login) {
		return usuarioPorLogin.get(login);
	}

	public Usuario getUsuario(int id) {
		return usuarioPorID.get(id);
	}

	public void addUsuario(Usuario usuario) {
		usuarioPorID.put(usuario.getId(), usuario);
		usuarioPorLogin.put(usuario.getLogin(), usuario);
	}

	public void removeUsuario(Usuario usuario) {
		usuarioPorID.remove(usuario.getId());
		usuarioPorLogin.remove(usuario.getLogin());
	}

	public List<Usuario> filtrarUsuarios(String name) throws DAOException {
		List<Usuario> usuariosRepositorio = this.getUsuarios();
		//Filtramos los usuarios para obtener solo los que tienen el String "name" en su nombre
		List<Usuario> usuariosFiltrado = usuariosRepositorio.stream()
				.filter(u -> u.getLogin().contains(name))
				.collect(Collectors.toList());
		return usuariosFiltrado;
	}

}
