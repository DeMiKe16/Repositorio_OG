package vista;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.Image;

import controlador.Controlador;
import modelo.Comentario;
import modelo.Publicacion;
import modelo.Usuario;

import java.awt.Container;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTextArea;
import javax.swing.UIManager;

public class PanelComentario extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton btnBorrarComentario;

	/**
	 * Create the panel.
	 */
	public PanelComentario(Comentario c, Publicacion publicacion) {
		//System.out.println(c.toString());
		Usuario usuario = Controlador.getUnicaInstancia().getUsuarioDeComentario(c);
		setBorder(null);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 5, 0, 205, 4, 0};
		gridBagLayout.rowHeights = new int[]{10, 0, 0, 3, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		setPreferredSize(new Dimension(313, 50));
		
		JLabel lblNewLabel_FotoPerfil = new JLabel();
		ImageIcon imagen = new ImageIcon(Controlador.getUnicaInstancia().getPhotoUsr(usuario));
		Image imagenACambiar = imagen.getImage();
		ImageIcon icono= new ImageIcon(imagenACambiar.getScaledInstance(30, 30,  java.awt.Image.SCALE_SMOOTH)); 
		lblNewLabel_FotoPerfil.setIcon(icono);
		
		GridBagConstraints gbc_lblNewLabel_FotoPerfil = new GridBagConstraints();
		gbc_lblNewLabel_FotoPerfil.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_FotoPerfil.gridx = 1;
		gbc_lblNewLabel_FotoPerfil.gridy = 1;
		add(lblNewLabel_FotoPerfil, gbc_lblNewLabel_FotoPerfil);
		
		JLabel lblNewLabel_Login = new JLabel(Controlador.getUnicaInstancia().getLoginUsuario(usuario) +":");
		GridBagConstraints gbc_lblNewLabel_Login = new GridBagConstraints();
		gbc_lblNewLabel_Login.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_Login.gridx = 2;
		gbc_lblNewLabel_Login.gridy = 1;
		add(lblNewLabel_Login, gbc_lblNewLabel_Login);
		
		JTextArea textArea = new JTextArea(Controlador.getUnicaInstancia().obtenerTextoComentario(c));
		textArea.setBackground(UIManager.getColor("Button.background"));
		GridBagConstraints gbc_textArea = new GridBagConstraints();
		gbc_textArea.fill = GridBagConstraints.HORIZONTAL;
		textArea.setEditable(false);
		textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
		gbc_textArea.gridwidth = 2;
		gbc_textArea.insets = new Insets(0, 0, 5, 5);
		gbc_textArea.gridx = 4;
		gbc_textArea.gridy = 1;
		add(textArea, gbc_textArea);
		
		if(usuario.equals(Controlador.getUnicaInstancia().getUsuarioActual())) {
			btnBorrarComentario = new JButton("");
			btnBorrarComentario.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					Controlador.getUnicaInstancia().eliminarComentario(publicacion, c);
					Container parent = btnBorrarComentario.getParent().getParent();		
					parent.remove(btnBorrarComentario.getParent());
					parent.getParent().revalidate();
					parent.getParent().repaint();
					//scrollRectToVisible
				}
			});
			btnBorrarComentario.setIcon(new ImageIcon(PanelComentario.class.getResource("/imagenes/borrar.png")));
			GridBagConstraints gbc_btnBorrarComentario = new GridBagConstraints();
			gbc_btnBorrarComentario.anchor = GridBagConstraints.EAST;
			gbc_btnBorrarComentario.insets = new Insets(0, 0, 5, 5);
			gbc_btnBorrarComentario.gridx = 6;
			gbc_btnBorrarComentario.gridy = 1;
			add(btnBorrarComentario, gbc_btnBorrarComentario);
		}

	}

}
