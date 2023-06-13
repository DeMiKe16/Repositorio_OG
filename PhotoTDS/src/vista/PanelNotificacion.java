package vista;

import javax.swing.JPanel;

import controlador.Controlador;
import modelo.Album;
import modelo.Foto;
import modelo.Notificacion;
import modelo.Publicacion;
import modelo.Usuario;

import java.awt.GridBagLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PanelNotificacion extends JPanel {

	private static final long serialVersionUID = 1L;
	private Notificacion noti;
	private JFrame ventanaPrincipal;
	private Usuario usuario;
	private Publicacion pub;

	public PanelNotificacion(Notificacion n, JFrame vPrincipal, JFrame frmNotificaciones) {
		
		this.noti = n;
		this.ventanaPrincipal = vPrincipal;
		this.pub = Controlador.getUnicaInstancia().getPublicacionNotificacion(n);
		this.usuario = Controlador.getUnicaInstancia().getAutorPublicacion(pub);
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 30, 0, 22, 0, 0, 23, 10, 0};
		gridBagLayout.rowHeights = new int[]{10, 0, 10, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel lbl_fotoPerfil = new JLabel();
		ImageIcon imagen = new ImageIcon(Controlador.getUnicaInstancia().getPhotoUsr(usuario));
		Image imagenACambiar = imagen.getImage();
		ImageIcon icono= new ImageIcon(imagenACambiar.getScaledInstance(30, 30,  java.awt.Image.SCALE_SMOOTH)); 
		lbl_fotoPerfil.setIcon(icono);
		
		GridBagConstraints gbc_lbl_fotoPerfil = new GridBagConstraints();
		gbc_lbl_fotoPerfil.insets = new Insets(0, 0, 5, 5);
		gbc_lbl_fotoPerfil.gridx = 1;
		gbc_lbl_fotoPerfil.gridy = 1;
		add(lbl_fotoPerfil, gbc_lbl_fotoPerfil);
		
		JLabel lblAutor = new JLabel("<html><u>"+Controlador.getUnicaInstancia().getLoginUsuario(usuario)+"</u></html>");
		lblAutor.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Controlador.getUnicaInstancia().eliminarNotificacion(noti);
				VentanaPerfil perfilAjeno = new VentanaPerfil(usuario);
				Container parent = lblAutor.getParent().getParent();
				parent.remove(lblAutor.getParent());
				perfilAjeno.mostrarVentana();
				frmNotificaciones.dispose();
				ventanaPrincipal.dispose();
			}
		});
		GridBagConstraints gbc_lblAutor = new GridBagConstraints();
		gbc_lblAutor.insets = new Insets(0, 0, 5, 5);
		gbc_lblAutor.gridx = 3;
		gbc_lblAutor.gridy = 1;
		add(lblAutor, gbc_lblAutor);
		
		JLabel lblMensaje = new JLabel("ha realizado una nueva publicación");
		GridBagConstraints gbc_lblMensaje = new GridBagConstraints();
		gbc_lblMensaje.insets = new Insets(0, 0, 5, 5);
		gbc_lblMensaje.gridx = 4;
		gbc_lblMensaje.gridy = 1;
		add(lblMensaje, gbc_lblMensaje);
		JLabel lblPreview = new JLabel();
		
		if(pub instanceof Foto) {
			ImageIcon imagenPreview = new ImageIcon(Controlador.getUnicaInstancia().obtenerPathFoto((Foto)pub));
			Image imagenACambiarPreview = imagenPreview.getImage();
			ImageIcon iconoPreview= new ImageIcon(imagenACambiarPreview.getScaledInstance(30, 30,  java.awt.Image.SCALE_SMOOTH)); 
			lblPreview.setIcon(iconoPreview);
			lblPreview.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					Controlador.getUnicaInstancia().eliminarNotificacion(noti);
					Container parent = lblAutor.getParent().getParent();
					parent.remove(lblPreview.getParent());
					parent.getParent().revalidate();
					parent.getParent().repaint();
					VentanaFotoPublicada fotoPublicada = new VentanaFotoPublicada((Foto)pub);
					fotoPublicada.mostrarVentana();
					ventanaPrincipal.dispose();
					frmNotificaciones.dispose();
				}
			});
		}else {
			ImageIcon imagenPreview = new ImageIcon(Controlador.getUnicaInstancia().getPortadaAlbum((Album)pub));
			Image imagenACambiarPreview = imagenPreview.getImage();
			ImageIcon iconoPreview= new ImageIcon(imagenACambiarPreview.getScaledInstance(30, 30,  java.awt.Image.SCALE_SMOOTH)); 
			lblPreview.setIcon(iconoPreview);
			lblPreview.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					Controlador.getUnicaInstancia().eliminarNotificacion(noti);
					Container parent = lblAutor.getParent().getParent();
					parent.remove(lblPreview.getParent());
					parent.getParent().revalidate();
					parent.getParent().repaint();
					VentanaAlbumPublicada albumPublicado = new VentanaAlbumPublicada((Album)pub);
					albumPublicado.mostrarVentana();
					ventanaPrincipal.dispose();
					frmNotificaciones.dispose();
				}
			});
		}
		GridBagConstraints gbc_lblPreview = new GridBagConstraints();
		gbc_lblPreview.anchor = GridBagConstraints.EAST;
		gbc_lblPreview.insets = new Insets(0, 0, 5, 5);
		gbc_lblPreview.gridx = 6;
		gbc_lblPreview.gridy = 1;
		add(lblPreview, gbc_lblPreview);	

	}

}
