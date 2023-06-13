package vista;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controlador.Controlador;
import modelo.Album;
import modelo.Comentario;
import modelo.Usuario;

import java.awt.Toolkit;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.Image;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagConstraints;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.ScrollPaneConstants;
import java.awt.Insets;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.awt.GridLayout;
import java.awt.CardLayout;
import java.awt.Color;

public class VentanaAlbumPublicada extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JFrame frameAlbumPublicada;
	private JPanel contentPane;
	private JTextField textFieldComent;
	private JLabel lblLikes;
	private Album album;
	private Usuario usuario;
	private PanelFotoAlbumVisual[] fotos;
	private String[] nombre_paneles;
	private int fotoActual;

	/**
	 * @wbp.parser.constructor
	 */
	public VentanaAlbumPublicada(Album album) {
		this.album = album;
		this.usuario = null;
		this.fotos = new PanelFotoAlbumVisual[album.getFotos().size()];
		this.nombre_paneles = new String[album.getFotos().size()];
		this.fotoActual = 0;
		initialize();
	}
	
	public VentanaAlbumPublicada(Album album, Usuario user) {
		this.album = album;
		this.usuario = user;
		this.fotos = new PanelFotoAlbumVisual[album.getFotos().size()];
		this.nombre_paneles = new String[album.getFotos().size()];
		this.fotoActual = 0;
		initialize();
	}

	public void mostrarVentana() {
		frameAlbumPublicada.setLocationRelativeTo(null);
		frameAlbumPublicada.setVisible(true);
	}
	
	public void initialize() {
		Usuario autor = Controlador.getUnicaInstancia().getAutorPublicacion(album);
		frameAlbumPublicada = new JFrame();
		frameAlbumPublicada.setIconImage(Toolkit.getDefaultToolkit().getImage(VentanaAlbumPublicada.class.getResource("/imagenes/camarax32.png")));
		frameAlbumPublicada.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frameAlbumPublicada.setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		frameAlbumPublicada.setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panelAlbum = new JPanel();
		contentPane.add(panelAlbum, BorderLayout.CENTER);
		GridBagLayout gbl_panelAlbum = new GridBagLayout();
		gbl_panelAlbum.columnWidths = new int[]{20, 25, 100, 50, 100, 25, 20, 0};
		gbl_panelAlbum.rowHeights = new int[]{15, 100, 50, 100, 15, 48, 41, 0, 0, 0, 0};
		gbl_panelAlbum.columnWeights = new double[]{1.0, 0.0, 0.0, 1.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_panelAlbum.rowWeights = new double[]{0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, Double.MIN_VALUE};
		panelAlbum.setLayout(gbl_panelAlbum);
		ImageIcon imagen = new ImageIcon(album.getFotos().get(0).getPath());
		Image originalImage = imagen.getImage();
		int newWidth = 250;
		int newHeight = 250;
		int originalWidth = originalImage.getWidth(null);
		int originalHeight = originalImage.getHeight(null);
		double aspectRatio = (double) originalWidth/originalHeight;
		if (originalWidth > originalHeight) {
		    newHeight = (int) (newWidth / aspectRatio);
		} else {
		    newWidth = (int) (newHeight * aspectRatio);
		}
		
		JButton btnVolver = new JButton("");
		btnVolver.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (usuario != null) {
					VentanaPerfil ventana = new VentanaPerfil(usuario);
					ventana.mostrarVentana();

				} else {
					VentanaPrincipal ventana = new VentanaPrincipal();
					ventana.mostrarVentana();
				}
				frameAlbumPublicada.dispose();
			}
		});
		btnVolver.setIcon(new ImageIcon(VentanaAlbumPublicada.class.getResource("/imagenes/return.png")));
		GridBagConstraints gbc_btnVolver = new GridBagConstraints();
		gbc_btnVolver.insets = new Insets(0, 0, 5, 0);
		gbc_btnVolver.gridx = 6;
		gbc_btnVolver.gridy = 0;
		panelAlbum.add(btnVolver, gbc_btnVolver);
		
		if (album.getFotos().size() < 16 && Controlador.getUnicaInstancia().isUsuarioActual(usuario)) {
			JButton btnAñadir = new JButton("");
			btnAñadir.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					JFileChooser fileChooser = new JFileChooser();
					if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
						String ruta = fileChooser.getSelectedFile().getAbsolutePath();
						VentanaFotosAlbumAñadir ventanaFotoAlbumAñadir = new VentanaFotosAlbumAñadir(frameAlbumPublicada, ruta, album);
						ventanaFotoAlbumAñadir.getFrmFotos().setVisible(true);
						frameAlbumPublicada.dispose();
					}
				}
			});
			btnAñadir.setIcon(new ImageIcon(VentanaAlbumPublicada.class.getResource("/imagenes/upload.png")));
			GridBagConstraints gbc_btnAñadir = new GridBagConstraints();
			gbc_btnAñadir.insets = new Insets(0, 0, 5, 5);
			gbc_btnAñadir.gridx = 1;
			gbc_btnAñadir.gridy = 0;
			panelAlbum.add(btnAñadir, gbc_btnAñadir);
		}
		
		JPanel panelFotosCard = new JPanel();
		GridBagConstraints gbc_panelFotosCard = new GridBagConstraints();
		gbc_panelFotosCard.gridheight = 3;
		gbc_panelFotosCard.gridwidth = 3;
		gbc_panelFotosCard.insets = new Insets(0, 0, 5, 5);
		gbc_panelFotosCard.fill = GridBagConstraints.BOTH;
		gbc_panelFotosCard.gridx = 2;
		gbc_panelFotosCard.gridy = 1;
		panelAlbum.add(panelFotosCard, gbc_panelFotosCard);
		panelFotosCard.setLayout(new CardLayout(0, 0));
		
		JButton btnNewButton = new JButton("");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				CardLayout cl = (CardLayout) (panelFotosCard.getLayout());
				if (fotoActual == 0) {
					fotoActual = album.getFotos().size() - 1;
					cl.show(panelFotosCard, nombre_paneles[fotoActual]);
				} else {
					fotoActual--;
					cl.show(panelFotosCard, nombre_paneles[fotoActual]);
				}
			}
		});
	
		btnNewButton.setIcon(new ImageIcon(VentanaAlbumPublicada.class.getResource("/imagenes/flecha-izquierda.png")));
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.fill = GridBagConstraints.VERTICAL;
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton.gridx = 1;
		gbc_btnNewButton.gridy = 2;
		panelAlbum.add(btnNewButton, gbc_btnNewButton);
		
		JButton btnNewButton_1 = new JButton("");
		btnNewButton_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				CardLayout cl = (CardLayout) (panelFotosCard.getLayout());
				if (fotoActual == (album.getFotos().size() - 1)) {
					fotoActual = 0;
					cl.show(panelFotosCard, nombre_paneles[fotoActual]);
				} else {
					fotoActual++;
					cl.show(panelFotosCard, nombre_paneles[fotoActual]);
				}
			}
		});
		btnNewButton_1.setIcon(new ImageIcon(VentanaAlbumPublicada.class.getResource("/imagenes/flecha-derecha.png")));
		
		GridBagConstraints gbc_btnNewButton_1 = new GridBagConstraints();
		gbc_btnNewButton_1.fill = GridBagConstraints.VERTICAL;
		gbc_btnNewButton_1.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton_1.gridx = 5;
		gbc_btnNewButton_1.gridy = 2;
		panelAlbum.add(btnNewButton_1, gbc_btnNewButton_1);
		
		for(int i = 0; i < album.getFotos().size(); i++) {
			fotos[i] = new PanelFotoAlbumVisual(album.getFotos().get(i), album, 250, usuario, frameAlbumPublicada);
			nombre_paneles[i] = "panelFoto_" + i;
			panelFotosCard.add(fotos[i], nombre_paneles[i]);
		}
		
		JLabel lblTitulo = new JLabel(album.getTitulo());
		GridBagConstraints gbc_lblTitulo = new GridBagConstraints();
		gbc_lblTitulo.gridwidth = 2;
		gbc_lblTitulo.insets = new Insets(0, 0, 5, 5);
		gbc_lblTitulo.gridx = 1;
		gbc_lblTitulo.gridy = 5;
		panelAlbum.add(lblTitulo, gbc_lblTitulo);
		
		JButton btnLikes = new JButton("");

		btnLikes.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(!Controlador.getUnicaInstancia().dioLike(album)) {
					Controlador.getUnicaInstancia().darLike(album);
					lblLikes.setText(Controlador.getUnicaInstancia().numeroLikes(album)+ " Likes");
					btnLikes.setIcon(new ImageIcon(VentanaAlbumPublicada.class.getResource("/imagenes/like_dado.png")));
				}
				else {
					Controlador.getUnicaInstancia().quitarLike(album);
					lblLikes.setText(Controlador.getUnicaInstancia().numeroLikes(album)+ " Likes");
					btnLikes.setIcon(new ImageIcon(VentanaAlbumPublicada.class.getResource("/imagenes/like.png")));		
				}
			}
		});
		if (!Controlador.getUnicaInstancia().dioLike(album))
			btnLikes.setIcon(new ImageIcon(VentanaAlbumPublicada.class.getResource("/imagenes/like.png")));
		else
			btnLikes.setIcon(new ImageIcon(VentanaAlbumPublicada.class.getResource("/imagenes/like_dado.png")));
		GridBagConstraints gbc_btnLikes = new GridBagConstraints();
		gbc_btnLikes.insets = new Insets(0, 0, 5, 5);
		gbc_btnLikes.gridx = 4;
		gbc_btnLikes.gridy = 5;
		panelAlbum.add(btnLikes, gbc_btnLikes);
		
		lblLikes = new JLabel(Controlador.getUnicaInstancia().numeroLikes(album) + " Likes");
		GridBagConstraints gbc_lblLikes = new GridBagConstraints();
		gbc_lblLikes.insets = new Insets(0, 0, 5, 5);
		gbc_lblLikes.gridx = 5;
		gbc_lblLikes.gridy = 5;
		panelAlbum.add(lblLikes, gbc_lblLikes);
		
		JLabel lblAutor = new JLabel("<html><u>"+Controlador.getUnicaInstancia().getLoginUsuario(autor)+"</u></html>");
		
		GridBagConstraints gbc_lblAutor = new GridBagConstraints();
		gbc_lblAutor.insets = new Insets(0, 0, 5, 5);
		gbc_lblAutor.gridx = 2;
		gbc_lblAutor.gridy = 6;
		panelAlbum.add(lblAutor, gbc_lblAutor);
		
		JLabel lblFechaFoto = new JLabel(Controlador.getUnicaInstancia().getFechaPublicacion(album));
		lblFechaFoto.setForeground(Color.GRAY);
		GridBagConstraints gbc_lblFechaFoto = new GridBagConstraints();
		gbc_lblFechaFoto.gridwidth = 2;
		gbc_lblFechaFoto.insets = new Insets(0, 0, 5, 5);
		gbc_lblFechaFoto.gridx = 4;
		gbc_lblFechaFoto.gridy = 6;
		panelAlbum.add(lblFechaFoto, gbc_lblFechaFoto);
		
		JLabel lblTituloDescripcion = new JLabel("Descripcion: ");
		GridBagConstraints gbc_lblTituloDescripcion = new GridBagConstraints();
		gbc_lblTituloDescripcion.gridwidth = 2;
		gbc_lblTituloDescripcion.anchor = GridBagConstraints.WEST;
		gbc_lblTituloDescripcion.insets = new Insets(0, 0, 5, 5);
		gbc_lblTituloDescripcion.gridx = 1;
		gbc_lblTituloDescripcion.gridy = 7;
		panelAlbum.add(lblTituloDescripcion, gbc_lblTituloDescripcion);
		
		JTextArea textAreaDescripcion = new JTextArea();
		textAreaDescripcion.setBackground(UIManager.getColor("Button.background"));
		textAreaDescripcion.setWrapStyleWord(true);
		textAreaDescripcion.setEditable(false);
		textAreaDescripcion.setLineWrap(true);
		textAreaDescripcion.setText(album.getDescripcion());
		GridBagConstraints gbc_textAreaDescripcion = new GridBagConstraints();
		gbc_textAreaDescripcion.gridwidth = 5;
		gbc_textAreaDescripcion.insets = new Insets(0, 0, 5, 5);
		gbc_textAreaDescripcion.fill = GridBagConstraints.BOTH;
		gbc_textAreaDescripcion.gridx = 1;
		gbc_textAreaDescripcion.gridy = 8;
		panelAlbum.add(textAreaDescripcion, gbc_textAreaDescripcion);
		
		JPanel panelComentarios = new JPanel();
		panelComentarios.setPreferredSize(new Dimension(330, 520));
		contentPane.add(panelComentarios, BorderLayout.EAST);
		panelComentarios.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_coments = new JPanel();
		panelComentarios.add(panel_coments, BorderLayout.CENTER);
		panel_coments.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		panel_coments.add(scrollPane, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		scrollPane.setViewportView(panel);
		panel.setLayout(new GridLayout(0, 1, 0, 0));
		
		mostrarComentarios(panel);
		
		JPanel panel_escribir = new JPanel();
		panelComentarios.add(panel_escribir, BorderLayout.SOUTH);
		panel_escribir.setLayout(new BorderLayout(0, 0));
		
		JButton btnEnviarComentario = new JButton("");
		btnEnviarComentario.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(!textFieldComent.getText().isBlank()) {
					if (textFieldComent.getText().trim().length() <= 120) {
						textFieldComent.setForeground(new JTextField().getForeground());
						Comentario nuevoComentario = Controlador.getUnicaInstancia().agregarComentarioAPublicacion(album, textFieldComent.getText());
						textFieldComent.setText(null);
						agregarComentario(nuevoComentario, panel);
						revalidate();
						panel.scrollRectToVisible(getBounds());
					} else {
						textFieldComent.setForeground(Color.RED);
						JOptionPane.showMessageDialog(frameAlbumPublicada, "Máximo de 120 caracteres para un comentario",
								"Error comentario", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		btnEnviarComentario.setIcon(new ImageIcon(VentanaAlbumPublicada.class.getResource("/imagenes/enviar.png")));
		panel_escribir.add(btnEnviarComentario, BorderLayout.EAST);
		
		textFieldComent = new JTextField();
		panel_escribir.add(textFieldComent, BorderLayout.CENTER);
		textFieldComent.setColumns(10);
	}
	
	public void mostrarComentarios(JPanel panel) {
		List<Comentario> listaComentarios = Controlador.getUnicaInstancia().obtenerComentarios(album);
		for(Comentario c: listaComentarios) {
			PanelComentario pc = new PanelComentario(c, album);
			panel.add(pc);
		}
	}
	
	public void agregarComentario(Comentario comentario, JPanel panel) {
		PanelComentario pc = new PanelComentario(comentario, album);
		panel.add(pc);
	}

}
