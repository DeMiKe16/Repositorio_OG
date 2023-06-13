package vista;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controlador.Controlador;
import modelo.Album;
import modelo.Comentario;
import modelo.Foto;
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
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Color;

public class VentanaFotoPublicada extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JFrame frameFotoPublicada;
	private int opcionDeVuelta;
	private JPanel contentPane;
	private JTextField textFieldComent;
	private JLabel lblLikes;
	private Foto foto;
	private Album album;
	private Usuario usuario;

	/**
	 * Create the frame.
	 * @wbp.parser.constructor
	 */
	public VentanaFotoPublicada(Foto foto) {
		this.opcionDeVuelta = 0;
		this.foto = foto;
		initialize();
	}
	
	public VentanaFotoPublicada(Foto foto, Usuario user) {
		this.opcionDeVuelta = 1;
		this.usuario = user;
		this.foto = foto;
		initialize();
	}
	
	public VentanaFotoPublicada(Foto foto, Album album, Usuario user) {
		this.opcionDeVuelta = 2;
		this.usuario = user;
		this.foto = foto;
		this.album = album;
		initialize();
	}
	
	
	
	public void mostrarVentana() {
		frameFotoPublicada.setLocationRelativeTo(null);
		frameFotoPublicada.setVisible(true);
	}
	
	public void initialize() {
		Usuario autor = Controlador.getUnicaInstancia().getAutorPublicacion(foto);
		frameFotoPublicada = new JFrame();
		frameFotoPublicada.setIconImage(Toolkit.getDefaultToolkit().getImage(VentanaFotoPublicada.class.getResource("/imagenes/camarax32.png")));
		frameFotoPublicada.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frameFotoPublicada.setBounds(100, 100, 770, 531);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		frameFotoPublicada.setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panelFoto = new JPanel();
		contentPane.add(panelFoto, BorderLayout.CENTER);
		GridBagLayout gbl_panelFoto = new GridBagLayout();
		gbl_panelFoto.columnWidths = new int[]{20, 52, 10, 0, 42, 20, 0};
		gbl_panelFoto.rowHeights = new int[]{15, 41, 15, 48, 41, 0, 0, 0, 0};
		gbl_panelFoto.columnWeights = new double[]{1.0, 1.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_panelFoto.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, Double.MIN_VALUE};
		panelFoto.setLayout(gbl_panelFoto);
		
		JLabel lblFoto = new JLabel();
		ImageIcon imagen = new ImageIcon(foto.getPath());
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
		Image scaledImage = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
		ImageIcon icono= new ImageIcon(scaledImage);
		
		JButton btnNewButton = new JButton("");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (opcionDeVuelta == 0) {
					VentanaPrincipal ventana = new VentanaPrincipal();
					ventana.mostrarVentana();
				} else if (opcionDeVuelta == 1) {
					VentanaPerfil ventana = new VentanaPerfil(usuario);
					ventana.mostrarVentana();
				} else if (opcionDeVuelta == 2) {
					VentanaAlbumPublicada ventana = new VentanaAlbumPublicada(album, usuario);
					ventana.mostrarVentana();
				}
				frameFotoPublicada.dispose();
			}
		});
		btnNewButton.setIcon(new ImageIcon(VentanaFotoPublicada.class.getResource("/imagenes/return.png")));
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 0);
		gbc_btnNewButton.gridx = 5;
		gbc_btnNewButton.gridy = 0;
		panelFoto.add(btnNewButton, gbc_btnNewButton);
		lblFoto.setIcon(icono);
		
		GridBagConstraints gbc_lblFoto = new GridBagConstraints();
		gbc_lblFoto.gridwidth = 4;
		gbc_lblFoto.insets = new Insets(0, 0, 5, 5);
		gbc_lblFoto.gridx = 1;
		gbc_lblFoto.gridy = 1;
		panelFoto.add(lblFoto, gbc_lblFoto);
		
		JLabel lblTitulo = new JLabel(foto.getTitulo());
		GridBagConstraints gbc_lblTitulo = new GridBagConstraints();
		gbc_lblTitulo.insets = new Insets(0, 0, 5, 5);
		gbc_lblTitulo.gridx = 1;
		gbc_lblTitulo.gridy = 3;
		panelFoto.add(lblTitulo, gbc_lblTitulo);
		
		JButton btnLikes = new JButton("");
		btnLikes.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(!Controlador.getUnicaInstancia().dioLike(foto)) {
					Controlador.getUnicaInstancia().darLike(foto);
					lblLikes.setText(Controlador.getUnicaInstancia().numeroLikes(foto)+ " Likes");
					btnLikes.setIcon(new ImageIcon(VentanaFotoPublicada.class.getResource("/imagenes/like_dado.png")));
				}
				else {
					Controlador.getUnicaInstancia().quitarLike(foto);
					lblLikes.setText(Controlador.getUnicaInstancia().numeroLikes(foto)+ " Likes");
					btnLikes.setIcon(new ImageIcon(VentanaFotoPublicada.class.getResource("/imagenes/like.png")));		
				}
			}
		});
		if (!Controlador.getUnicaInstancia().dioLike(foto))
			btnLikes.setIcon(new ImageIcon(VentanaFotoPublicada.class.getResource("/imagenes/like.png")));
		else
			btnLikes.setIcon(new ImageIcon(VentanaFotoPublicada.class.getResource("/imagenes/like_dado.png")));
		GridBagConstraints gbc_btnLikes = new GridBagConstraints();
		gbc_btnLikes.insets = new Insets(0, 0, 5, 5);
		gbc_btnLikes.gridx = 3;
		gbc_btnLikes.gridy = 3;
		panelFoto.add(btnLikes, gbc_btnLikes);
		
		lblLikes = new JLabel(Controlador.getUnicaInstancia().numeroLikes(foto) + " Likes");
		GridBagConstraints gbc_lblLikes = new GridBagConstraints();
		gbc_lblLikes.anchor = GridBagConstraints.WEST;
		gbc_lblLikes.insets = new Insets(0, 0, 5, 5);
		gbc_lblLikes.gridx = 4;
		gbc_lblLikes.gridy = 3;
		panelFoto.add(lblLikes, gbc_lblLikes);
		
		JLabel lblAutor = new JLabel("<html><u>"+Controlador.getUnicaInstancia().getLoginUsuario(autor)+"</u></html>");
		
		GridBagConstraints gbc_lblAutor = new GridBagConstraints();
		gbc_lblAutor.insets = new Insets(0, 0, 5, 5);
		gbc_lblAutor.gridx = 1;
		gbc_lblAutor.gridy = 4;
		panelFoto.add(lblAutor, gbc_lblAutor);
		
		JLabel lblFechaFoto = new JLabel(Controlador.getUnicaInstancia().getFechaPublicacion(foto));
		lblFechaFoto.setForeground(Color.GRAY);
		GridBagConstraints gbc_lblFechaFoto = new GridBagConstraints();
		gbc_lblFechaFoto.gridwidth = 3;
		gbc_lblFechaFoto.insets = new Insets(0, 0, 5, 5);
		gbc_lblFechaFoto.gridx = 2;
		gbc_lblFechaFoto.gridy = 4;
		panelFoto.add(lblFechaFoto, gbc_lblFechaFoto);
		
		JLabel lblTituloDescripcion = new JLabel("Descripcion: ");
		GridBagConstraints gbc_lblTituloDescripcion = new GridBagConstraints();
		gbc_lblTituloDescripcion.anchor = GridBagConstraints.WEST;
		gbc_lblTituloDescripcion.insets = new Insets(0, 0, 5, 5);
		gbc_lblTituloDescripcion.gridx = 1;
		gbc_lblTituloDescripcion.gridy = 5;
		panelFoto.add(lblTituloDescripcion, gbc_lblTituloDescripcion);
		
		JTextArea textAreaDescripcion = new JTextArea();
		textAreaDescripcion.setBackground(UIManager.getColor("Button.background"));
		textAreaDescripcion.setWrapStyleWord(true);
		textAreaDescripcion.setEditable(false);
		textAreaDescripcion.setLineWrap(true);
		textAreaDescripcion.setText(foto.getDescripcion());
		GridBagConstraints gbc_textAreaDescripcion = new GridBagConstraints();
		gbc_textAreaDescripcion.gridwidth = 4;
		gbc_textAreaDescripcion.insets = new Insets(0, 0, 5, 5);
		gbc_textAreaDescripcion.fill = GridBagConstraints.BOTH;
		gbc_textAreaDescripcion.gridx = 1;
		gbc_textAreaDescripcion.gridy = 6;
		panelFoto.add(textAreaDescripcion, gbc_textAreaDescripcion);
		
		JPanel panelComentarios = new JPanel();
		panelComentarios.setPreferredSize(new Dimension(330, 520));
		contentPane.add(panelComentarios, BorderLayout.EAST);
		panelComentarios.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_coments = new JPanel();
		panelComentarios.add(panel_coments, BorderLayout.CENTER);
		panel_coments.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		panel_coments.add(scrollPane, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		scrollPane.setViewportView(panel);
		
		mostrarComentarios(panel);
		panel.setLayout(new GridLayout(0, 1, 0, 0));
		
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
						Comentario nuevoComentario = Controlador.getUnicaInstancia().agregarComentarioAPublicacion(foto, textFieldComent.getText());
						textFieldComent.setText(null);
						agregarComentario(nuevoComentario, panel);
						revalidate();
						panel.scrollRectToVisible(getBounds());
					} else {
						textFieldComent.setForeground(Color.RED);
						JOptionPane.showMessageDialog(frameFotoPublicada, "Máximo de 120 caracteres para un comentario",
								"Error comentario", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		btnEnviarComentario.setIcon(new ImageIcon(VentanaFotoPublicada.class.getResource("/imagenes/enviar.png")));
		panel_escribir.add(btnEnviarComentario, BorderLayout.EAST);
		
		textFieldComent = new JTextField();
		textFieldComent.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					if(!textFieldComent.getText().isBlank()) {
						Comentario nuevoComentario = Controlador.getUnicaInstancia().agregarComentarioAPublicacion(foto, textFieldComent.getText());
						textFieldComent.setText(null);
						agregarComentario(nuevoComentario, panel);
						revalidate();
						panel.scrollRectToVisible(getBounds());
					}
				}
			}
		});
		panel_escribir.add(textFieldComent, BorderLayout.CENTER);
		textFieldComent.setColumns(10);
	}
	
	public void mostrarComentarios(JPanel panel) {
		List<Comentario> listaComentarios = Controlador.getUnicaInstancia().obtenerComentarios(foto);
		for(Comentario c: listaComentarios) {
			PanelComentario pc = new PanelComentario(c, foto);
			panel.add(pc, BorderLayout.NORTH);
		}
	}
	
	public void agregarComentario(Comentario comentario, JPanel panel) {
		PanelComentario pc = new PanelComentario(comentario, foto);
		panel.add(pc);
	}

}
