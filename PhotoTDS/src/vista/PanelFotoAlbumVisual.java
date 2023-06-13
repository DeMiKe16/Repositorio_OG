package vista;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingConstants;

import controlador.Controlador;
import modelo.Album;
import modelo.Foto;
import modelo.Usuario;

public class PanelFotoAlbumVisual extends JPanel {

	private static final long serialVersionUID = 1L;
	private JLabel lblFoto = new JLabel();
	private Foto foto;
	private Album album;
	private Usuario usuario;
	private JPopupMenu popupMenu;
	private JButton buttonEliminar;
	private JFrame perfil;
	
	/**
	 * Create the panel.
	 */
	public PanelFotoAlbumVisual(Foto f, Album a, int tam, Usuario user, JFrame perfil) {
		FlowLayout flowLayout = (FlowLayout) getLayout();
		flowLayout.setVgap(0);
		flowLayout.setHgap(0);
		this.foto = f;
		this.usuario = user;
		this.album = a;
		this.perfil = perfil;
		System.out.println(f.toString());
		
		lblFoto = new JLabel("");
		ImageIcon imagenFotos = new ImageIcon(f.getPath());
		Image imagenFotosACambiar = imagenFotos.getImage(); // transform it 
		ImageIcon iconoFotos= new ImageIcon(imagenFotosACambiar.getScaledInstance(tam, tam,  java.awt.Image.SCALE_SMOOTH)); 
		lblFoto.setIcon(iconoFotos);
		lblFoto.setHorizontalAlignment(SwingConstants.LEFT);
		add(lblFoto);
		
		popupMenu = new JPopupMenu();
		if(Controlador.getUnicaInstancia().isUsuarioActual(usuario)) {
			buttonEliminar = new JButton("Eliminar");
			buttonEliminar.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if (Controlador.getUnicaInstancia().soloUnaFoto(album)) {
						Controlador.getUnicaInstancia().eliminarAlbum(foto, a);
						VentanaPrincipal principal = new VentanaPrincipal();
						principal.mostrarVentana();
						JOptionPane.showMessageDialog(principal.getFrmVentanaPrincipal(), "Album borrado correctamente.", "Album borrado", JOptionPane.INFORMATION_MESSAGE);
						perfil.dispose();
					} else  {
						Controlador.getUnicaInstancia().eliminarFotoAlbum(foto, a);
						VentanaPrincipal principal = new VentanaPrincipal();
						principal.mostrarVentana();
						JOptionPane.showMessageDialog(principal.getFrmVentanaPrincipal(), "Foto de album borrada correctamente.", "Foto borrada", JOptionPane.INFORMATION_MESSAGE);
						perfil.dispose();
					}
				}
					
			});
			popupMenu.add(buttonEliminar);
		}
		addPopup(lblFoto, popupMenu);
	}
	
	private void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					mouseClicked(e);
				}
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON3) {
					popup.show(e.getComponent(), e.getX(), e.getY());
				} else if (e.getButton() == MouseEvent.BUTTON1) {
					VentanaFotoPublicada ventanaPublic = new VentanaFotoPublicada(foto, album, usuario);
					ventanaPublic.mostrarVentana();
					perfil.dispose();
				}
			}
			
		});
	}
	
}
