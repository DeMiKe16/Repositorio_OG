package vista;

import java.awt.Component;
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

import controlador.Controlador;
import modelo.Foto;
import modelo.Usuario;

import javax.swing.SwingConstants;
import java.awt.FlowLayout;

public class PanelFotoVisual extends JPanel {

	private static final long serialVersionUID = 1L;
	private JLabel lblFoto = new JLabel();
	private Foto foto;
	private Usuario usuario;
	private JPopupMenu popupMenu;
	private JButton buttonEliminar;
	private JFrame perfil;
	
	/**
	 * Create the panel.
	 */
	public PanelFotoVisual(Foto f, int tam, Usuario user, JFrame perfil) {
		setBorder(null);
		FlowLayout flowLayout = (FlowLayout) getLayout();
		flowLayout.setVgap(0);
		flowLayout.setHgap(0);
		this.foto = f;
		this.perfil = perfil;
		this.usuario = user;
		System.out.println(f.toString());
		
		lblFoto = new JLabel("");
		ImageIcon imagenFotos = new ImageIcon(f.getPath());
		Image imagenFotosACambiar = imagenFotos.getImage(); // transform it 
		ImageIcon iconoFotos= new ImageIcon(imagenFotosACambiar.getScaledInstance(tam, tam,  java.awt.Image.SCALE_SMOOTH)); 
		lblFoto.setIcon(iconoFotos);
		lblFoto.setHorizontalAlignment(SwingConstants.LEFT);
		add(lblFoto);
		
		popupMenu = new JPopupMenu();
		if (Controlador.getUnicaInstancia().isUsuarioActual(usuario)) {
			buttonEliminar = new JButton("Eliminar");
			buttonEliminar.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
						Controlador.getUnicaInstancia().eliminarFoto(foto);
						VentanaPrincipal principal = new VentanaPrincipal();
						principal.mostrarVentana();
						JOptionPane.showMessageDialog(principal.getFrmVentanaPrincipal(), "Foto borrada correctamente.", "Foto borrada", JOptionPane.INFORMATION_MESSAGE);
						perfil.dispose();
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
					VentanaFotoPublicada ventanaPublic = new VentanaFotoPublicada(foto, usuario);
					ventanaPublic.mostrarVentana();
					perfil.dispose();
				}
			}
			
		});
	}


}
