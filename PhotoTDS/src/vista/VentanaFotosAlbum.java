package vista;


import javax.swing.JFrame;
import java.awt.Toolkit;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import javax.swing.JButton;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

import javax.swing.BorderFactory;
import java.util.List;
import java.util.Optional;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.Color;


import controlador.Controlador;
import modelo.Foto;
import modelo.Usuario;

public class VentanaFotosAlbum extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Foto> album;
	private Usuario usuario;
	
	private JFrame frmFotosAlbum;
	private String rutaFoto;
	private JLabel lblFoto;
	private JLabel lblTitulo;
	private JLabel lblDescripcion;
	private JTextArea txtDescripcion;

	private JTextField txtTitulo;
	private JLabel lblError;
	private JLabel lblHashtagError;
	private JLabel lblDescripcionError;

	public void mostrarVentana() {
		frmFotosAlbum.setLocationRelativeTo(null);
		frmFotosAlbum.setVisible(true);
	}

	/**
	 * Create the application.
	 * @param ruta 
	 */
	public VentanaFotosAlbum(String ruta, List<Foto> album, Usuario user) {
		this.rutaFoto = ruta;
		this.album = album;
		this.usuario = user;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmFotosAlbum = new JFrame();
		frmFotosAlbum.setTitle("Subir foto");
		frmFotosAlbum.setIconImage(Toolkit.getDefaultToolkit()
				.getImage(VentanaLoginRegister.class.getResource("/imagenes/camarax32.png")));
		frmFotosAlbum.setBounds(100, 100, 731, 546);
		frmFotosAlbum.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{20, 377, 320, 20, 0};
		gridBagLayout.rowHeights = new int[]{0, 20, 31, 0, 238, 0, 0, 0, 0, 5, 0, 20, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		frmFotosAlbum.getContentPane().setLayout(gridBagLayout);
		
		lblFoto = new JLabel("<html><img src=\"file:\\" + rutaFoto + "\" width=400 height=350></img>");
		GridBagConstraints gbc_lblFoto = new GridBagConstraints();
		gbc_lblFoto.gridheight = 6;
		gbc_lblFoto.fill = GridBagConstraints.VERTICAL;
		gbc_lblFoto.insets = new Insets(0, 0, 5, 5);
		gbc_lblFoto.gridx = 1;
		gbc_lblFoto.gridy = 1;
		frmFotosAlbum.getContentPane().add(lblFoto, gbc_lblFoto);
		
		lblTitulo = new JLabel("Titulo :");
		GridBagConstraints gbc_lblTitulo = new GridBagConstraints();
		gbc_lblTitulo.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblTitulo.insets = new Insets(0, 0, 5, 5);
		gbc_lblTitulo.gridx = 2;
		gbc_lblTitulo.gridy = 1;
		frmFotosAlbum.getContentPane().add(lblTitulo, gbc_lblTitulo);
		
		txtTitulo = new JTextField();
		GridBagConstraints gbc_txtTitulo = new GridBagConstraints();
		gbc_txtTitulo.insets = new Insets(0, 0, 5, 5);
		gbc_txtTitulo.fill = GridBagConstraints.BOTH;
		gbc_txtTitulo.gridx = 2;
		gbc_txtTitulo.gridy = 2;
		frmFotosAlbum.getContentPane().add(txtTitulo, gbc_txtTitulo);
		txtTitulo.setColumns(10);
		
		lblDescripcion = new JLabel("Descripción (Máximo 200 palabras):");
		GridBagConstraints gbc_lblDescripcion = new GridBagConstraints();
		gbc_lblDescripcion.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblDescripcion.insets = new Insets(0, 0, 5, 5);
		gbc_lblDescripcion.gridx = 2;
		gbc_lblDescripcion.gridy = 3;
		frmFotosAlbum.getContentPane().add(lblDescripcion, gbc_lblDescripcion);
		
		lblDescripcionError = new JLabel("LA DESCRIPCIÓN TIENE UN MÁXIMO DE 200 CARÁCTERES");
		lblDescripcionError.setVisible(false);
		lblDescripcionError.setForeground(Color.RED);
		GridBagConstraints gbc_lblDescripcionError = new GridBagConstraints();
		gbc_lblDescripcionError.gridwidth = 2;
		gbc_lblDescripcionError.insets = new Insets(0, 0, 5, 5);
		gbc_lblDescripcionError.gridx = 1;
		gbc_lblDescripcionError.gridy = 7;
		frmFotosAlbum.getContentPane().add(lblDescripcionError, gbc_lblDescripcionError);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridheight = 3;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 2;
		gbc_scrollPane.gridy = 4;
		frmFotosAlbum.getContentPane().add(scrollPane, gbc_scrollPane);
		
		txtDescripcion = new JTextArea();
		scrollPane.setViewportView(txtDescripcion);
		
		JButton btnAtras = new JButton("Atrás");
		btnAtras.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				VentanaAlbumes ventana= new VentanaAlbumes(album, usuario);
				ventana.mostrarVentana();
				frmFotosAlbum.dispose();
			}
		});
		
		lblHashtagError = new JLabel("LOS HASHTAGS NO CUMPLEN LOS REQUISITOS");
		lblHashtagError.setVisible(false);
		lblHashtagError.setForeground(Color.RED);
		GridBagConstraints gbc_lblHashtagError = new GridBagConstraints();
		gbc_lblHashtagError.gridwidth = 2;
		gbc_lblHashtagError.insets = new Insets(0, 0, 5, 5);
		gbc_lblHashtagError.gridx = 1;
		gbc_lblHashtagError.gridy = 8;
		frmFotosAlbum.getContentPane().add(lblHashtagError, gbc_lblHashtagError);
		
		lblError = new JLabel("NO HAS PUESTO EL NOMBRE DEL TITULO");
		lblError.setVisible(false);
		lblError.setForeground(Color.RED);
		lblError.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lblError = new GridBagConstraints();
		gbc_lblError.gridwidth = 2;
		gbc_lblError.insets = new Insets(0, 0, 5, 5);
		gbc_lblError.gridx = 1;
		gbc_lblError.gridy = 9;
		frmFotosAlbum.getContentPane().add(lblError, gbc_lblError);
		GridBagConstraints gbc_btnAtras = new GridBagConstraints();
		gbc_btnAtras.insets = new Insets(0, 0, 5, 5);
		gbc_btnAtras.gridx = 1;
		gbc_btnAtras.gridy = 10;
		frmFotosAlbum.getContentPane().add(btnAtras, gbc_btnAtras);
		
		JButton btnConfirmar = new JButton("Confirmar");
		btnConfirmar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				boolean OK = false;
				OK = comprobacion();
				if (OK) {
					Controlador.getUnicaInstancia().crearFotoAlbum(album, rutaFoto, txtTitulo.getText(), txtDescripcion.getText());
					VentanaAlbumes ventana= new VentanaAlbumes(album, usuario);
					ventana.mostrarVentana();
					frmFotosAlbum.dispose();
				}
			}
		});
		GridBagConstraints gbc_btnConfirmar = new GridBagConstraints();
		gbc_btnConfirmar.insets = new Insets(0, 0, 5, 5);
		gbc_btnConfirmar.gridx = 2;
		gbc_btnConfirmar.gridy = 10;
		frmFotosAlbum.getContentPane().add(btnConfirmar, gbc_btnConfirmar);

	}

	public JFrame getfrmFotosAlbum() {
		return frmFotosAlbum;
	}
	
	private boolean comprobacion() {
		boolean salida = true;
		ocultarErrores();
		if (txtTitulo.getText().trim().isEmpty()) {
			lblError.setVisible(true);
			lblTitulo.setForeground(Color.RED);
			txtTitulo.setBorder(BorderFactory.createLineBorder(Color.RED));
			salida = false;
		}
		if (Controlador.getUnicaInstancia().comprobarDescripciones(txtDescripcion.getText())) {
			lblHashtagError.setVisible(true);
			lblDescripcion.setForeground(Color.RED);
			txtDescripcion.setBorder(BorderFactory.createLineBorder(Color.RED));
			salida = false;
		}
		if (txtDescripcion.getText().trim().length() > 200) {
			lblDescripcionError.setVisible(true);
			lblDescripcion.setForeground(Color.RED);
			txtDescripcion.setBorder(BorderFactory.createLineBorder(Color.RED));
			salida = false;
		}
		return salida;
	}
	
	private void ocultarErrores() {
		lblError.setVisible(false);
		lblTitulo.setForeground(new JLabel().getForeground());
		txtTitulo.setBorder(new JTextField().getBorder());
		lblHashtagError.setVisible(false);
		lblDescripcionError.setVisible(false);
		lblDescripcion.setForeground(new JLabel().getForeground());
		txtDescripcion.setBorder(new JTextField().getBorder());
	}
	
	
}

