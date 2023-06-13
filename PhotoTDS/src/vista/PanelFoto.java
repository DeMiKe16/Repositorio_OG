package vista;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;

import controlador.Controlador;
import modelo.Foto;
import modelo.Usuario;

public class PanelFoto extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private JFrame ventanaPrincipal;
	private Foto foto;
	private Usuario usuario;
	private JLabel lblfotoPublic;
	private JLabel lblnumLikes;
	private JLabel lblNumComment;
	
	public PanelFoto(Foto f, Usuario user, JFrame vPrincipal) {
		this.ventanaPrincipal = vPrincipal;
		this.foto = f;
		this.usuario = user;
				
		setBorder(new LineBorder(Color.LIGHT_GRAY));	
		System.out.println(foto.toString());
		setPreferredSize(new Dimension(400, 160));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{10, 0, 50, 0, 10, 0, 100, 59, 0, 0, 10, 0};
		gridBagLayout.rowHeights = new int[]{30, 20, 42, 0, 0, 30, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		lblfotoPublic = new JLabel();
		lblfotoPublic.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				VentanaFotoPublicada ventanaPublic = new VentanaFotoPublicada(f);
				ventanaPublic.mostrarVentana();
				ventanaPrincipal.dispose();
			}
		});
		ImageIcon imagen = new ImageIcon(Controlador.getUnicaInstancia().obtenerPathFoto(foto));
		Image imagenACambiar = imagen.getImage(); // transform it 
		ImageIcon icono= new ImageIcon(imagenACambiar.getScaledInstance(140, 140,  java.awt.Image.SCALE_SMOOTH)); 
		lblfotoPublic.setIcon(icono);
		GridBagConstraints gbc_lblfotoPublic = new GridBagConstraints();
		gbc_lblfotoPublic.gridheight = 4;
		gbc_lblfotoPublic.insets = new Insets(0, 0, 5, 5);
		gbc_lblfotoPublic.gridx = 1;
		gbc_lblfotoPublic.gridy = 1;
		add(lblfotoPublic, gbc_lblfotoPublic);
		
		JButton btnlike = new JButton();
		btnlike.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(!Controlador.getUnicaInstancia().dioLike(foto)) {
					Controlador.getUnicaInstancia().darLike(foto);
					lblnumLikes.setText(Controlador.getUnicaInstancia().numeroLikes(foto)+ " Likes");
					btnlike.setIcon(new ImageIcon(PanelFoto.class.getResource("/IMAGENES/like_dado.png")));
				}
				else {
					Controlador.getUnicaInstancia().quitarLike(foto);
					btnlike.setIcon(new ImageIcon(PanelFoto.class.getResource("/IMAGENES/like.png")));
					lblnumLikes.setText(Controlador.getUnicaInstancia().numeroLikes(foto)+ " Likes");
					
				}
			}
		});
		
		if (!Controlador.getUnicaInstancia().dioLike(foto))
			btnlike.setIcon(new ImageIcon(PanelFoto.class.getResource("/IMAGENES/like.png")));
		else
			btnlike.setIcon(new ImageIcon(PanelFoto.class.getResource("/IMAGENES/like_dado.png")));
		GridBagConstraints gbc_btnlike = new GridBagConstraints();
		gbc_btnlike.insets = new Insets(0, 0, 5, 5);
		gbc_btnlike.gridx = 3;
		gbc_btnlike.gridy = 2;
		add(btnlike, gbc_btnlike);
		ImageIcon imagen_perfil = new ImageIcon(Controlador.getUnicaInstancia().getPhotoUsr(usuario));
		Image imagenACambiarPerfil = imagen_perfil.getImage();
		ImageIcon iconoPerfil = new ImageIcon(imagenACambiarPerfil.getScaledInstance(30, 30,  java.awt.Image.SCALE_SMOOTH)); 
		
		JLabel lblfotoPerfil = new JLabel();
		lblfotoPerfil.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				irAlPerfil(usuario,ventanaPrincipal);
			}
		});
		GridBagConstraints gbc_lblfotoPerfil = new GridBagConstraints();
		gbc_lblfotoPerfil.gridheight = 2;
		gbc_lblfotoPerfil.insets = new Insets(0, 0, 5, 5);
		gbc_lblfotoPerfil.gridx = 7;
		gbc_lblfotoPerfil.gridy = 2;
		add(lblfotoPerfil, gbc_lblfotoPerfil);	
		
		lblnumLikes = new JLabel(Controlador.getUnicaInstancia().numeroLikes(foto)+ " Likes");
		lblnumLikes.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				DefaultListModel<Usuario> listModel = new DefaultListModel<>();
				for(Usuario u: Controlador.getUnicaInstancia().getListaLike(foto)) {
					listModel.addElement(u);
				}
				JList<Usuario> usersList = new JList<>(listModel);
		        usersList.setCellRenderer(new UsuarioRenderer());
		        JScrollPane pane = new JScrollPane(usersList);
		        JFrame frame = createFrame();
		        frame.getContentPane().add(pane);
		        frame.setLocationRelativeTo(null);
		        frame.setVisible(true);
			}
		});
		GridBagConstraints gbc_lblnumLikes = new GridBagConstraints();
		gbc_lblnumLikes.insets = new Insets(0, 0, 5, 5);
		gbc_lblnumLikes.gridx = 5;
		gbc_lblnumLikes.gridy = 2;
		add(lblnumLikes, gbc_lblnumLikes);
		
		JButton btnComent = new JButton();
		btnComent.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				VentanaComentario ventComent = new VentanaComentario(foto, PanelFoto.this);
				ventComent.mostrarVentana();
			}
		});
		JLabel lblNewLabel = new JLabel(Controlador.getUnicaInstancia().getLoginUsuario(usuario));
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.gridheight = 2;
		gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 8;
		gbc_lblNewLabel.gridy = 2;
		add(lblNewLabel, gbc_lblNewLabel);
		
		btnComent.setIcon(new ImageIcon(PanelFoto.class.getResource("/IMAGENES/comentario.png")));
		GridBagConstraints gbc_btnComent = new GridBagConstraints();
		gbc_btnComent.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnComent.insets = new Insets(0, 0, 5, 5);
		gbc_btnComent.gridx = 3;
		gbc_btnComent.gridy = 3;
		add(btnComent, gbc_btnComent);
		lblfotoPerfil.setIcon(iconoPerfil);
		
		lblNumComment = new JLabel(Controlador.getUnicaInstancia().numeroComentarios(foto)+ " Comentarios");
		GridBagConstraints gbc_lblNumComment = new GridBagConstraints();
		gbc_lblNumComment.insets = new Insets(0, 0, 5, 5);
		gbc_lblNumComment.gridx = 5;
		gbc_lblNumComment.gridy = 3;
		add(lblNumComment, gbc_lblNumComment);
		
		JLabel lblFechaFoto = new JLabel(Controlador.getUnicaInstancia().getFechaPublicacion(foto));
		lblFechaFoto.setForeground(Color.GRAY);
		GridBagConstraints gbc_lblFechaFoto = new GridBagConstraints();
		gbc_lblFechaFoto.gridwidth = 2;
		gbc_lblFechaFoto.insets = new Insets(0, 0, 5, 5);
		gbc_lblFechaFoto.gridx = 7;
		gbc_lblFechaFoto.gridy = 4;
		add(lblFechaFoto, gbc_lblFechaFoto);
	}
	
	private static JFrame createFrame() {
        JFrame searchframe = new JFrame("Likes");
        searchframe.setTitle("Likes");
        searchframe.setIconImage(
				Toolkit.getDefaultToolkit().getImage(VentanaPrincipal.class.getResource("/imagenes/camarax32.png")));
        searchframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        searchframe.setSize(new Dimension(300, 300));
        return searchframe;
    }
	
	public void irAlPerfil(Usuario autor, JFrame ventanaPrincipal) {
			VentanaPerfil perfil = new VentanaPerfil(usuario);
			perfil.mostrarVentana();
			ventanaPrincipal.dispose();
	}

	public void actualizarComentarios() {
		lblNumComment.setText(Controlador.getUnicaInstancia().numeroComentarios(foto)+ " Comentarios");
		
	}

}
