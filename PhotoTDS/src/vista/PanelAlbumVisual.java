package vista;

import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import modelo.Album;
import modelo.Usuario;

public class PanelAlbumVisual extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel lblFoto = new JLabel();
	private Album album;
	private Usuario usuario;
	private JFrame perfil;

	public PanelAlbumVisual(Album a, int tam, Usuario user , JFrame frame) {
		FlowLayout flowLayout = (FlowLayout) getLayout();
		flowLayout.setVgap(0);
		flowLayout.setHgap(0);
		this.album = a;
		this.usuario = user;
		this.perfil = frame;
		lblFoto = new JLabel("");
		ImageIcon imagenFotos = new ImageIcon(a.getFotos().get(0).getPath());
		Image imagenFotosACambiar = imagenFotos.getImage(); // transform it 
		ImageIcon iconoFotos= new ImageIcon(imagenFotosACambiar.getScaledInstance(tam, tam,  java.awt.Image.SCALE_SMOOTH)); 
		lblFoto.setIcon(iconoFotos);
		lblFoto.setHorizontalAlignment(SwingConstants.LEFT);
		add(lblFoto);
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				VentanaAlbumPublicada ventanaPublic = new VentanaAlbumPublicada(album, usuario);
				ventanaPublic.mostrarVentana();
				perfil.dispose();
			}
		});
	}
}