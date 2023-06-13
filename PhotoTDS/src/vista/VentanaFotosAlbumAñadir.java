package vista;


import javax.swing.JFrame;
import java.awt.Toolkit;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import javax.swing.JButton;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

import javax.swing.BorderFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.Color;

import controlador.Controlador;
import modelo.Album;

public class VentanaFotosAlbumAñadir extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String rutaPaquete = "publicaciones";
	private Album album;
	
	private JFrame frmFotos;
	private JFrame ventanaPrincipal;
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
		frmFotos.setLocationRelativeTo(null);
		frmFotos.setVisible(true);
	}

	/**
	 * Create the application.
	 * @param ruta 
	 */
	public VentanaFotosAlbumAñadir(JFrame invocante, String ruta, Album album) {
		this.ventanaPrincipal = invocante;
		this.rutaFoto = ruta;
		this.album = album;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmFotos = new JFrame();
		frmFotos.setTitle("Subir foto");
		frmFotos.setIconImage(Toolkit.getDefaultToolkit()
				.getImage(VentanaLoginRegister.class.getResource("/imagenes/camarax32.png")));
		frmFotos.setBounds(100, 100, 731, 546);
		frmFotos.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{20, 377, 320, 20, 0};
		gridBagLayout.rowHeights = new int[]{0, 20, 31, 0, 238, 0, 0, 0, 0, 5, 0, 20, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		frmFotos.getContentPane().setLayout(gridBagLayout);
		
		lblFoto = new JLabel("<html><img src=\"file:\\" + rutaFoto + "\" width=400 height=350></img>");
		GridBagConstraints gbc_lblFoto = new GridBagConstraints();
		gbc_lblFoto.gridheight = 6;
		gbc_lblFoto.fill = GridBagConstraints.VERTICAL;
		gbc_lblFoto.insets = new Insets(0, 0, 5, 5);
		gbc_lblFoto.gridx = 1;
		gbc_lblFoto.gridy = 1;
		frmFotos.getContentPane().add(lblFoto, gbc_lblFoto);
		
		lblTitulo = new JLabel("Titulo :");
		GridBagConstraints gbc_lblTitulo = new GridBagConstraints();
		gbc_lblTitulo.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblTitulo.insets = new Insets(0, 0, 5, 5);
		gbc_lblTitulo.gridx = 2;
		gbc_lblTitulo.gridy = 1;
		frmFotos.getContentPane().add(lblTitulo, gbc_lblTitulo);
		
		txtTitulo = new JTextField();
		GridBagConstraints gbc_txtTitulo = new GridBagConstraints();
		gbc_txtTitulo.insets = new Insets(0, 0, 5, 5);
		gbc_txtTitulo.fill = GridBagConstraints.BOTH;
		gbc_txtTitulo.gridx = 2;
		gbc_txtTitulo.gridy = 2;
		frmFotos.getContentPane().add(txtTitulo, gbc_txtTitulo);
		txtTitulo.setColumns(10);
		
		lblDescripcionError = new JLabel("LA DESCRIPCIÓN TIENE UN MÁXIMO DE 200 CARÁCTERES");
		lblDescripcionError.setVisible(false);
		lblDescripcionError.setForeground(Color.RED);
		GridBagConstraints gbc_lblDescripcionError = new GridBagConstraints();
		gbc_lblDescripcionError.gridwidth = 2;
		gbc_lblDescripcionError.insets = new Insets(0, 0, 5, 5);
		gbc_lblDescripcionError.gridx = 1;
		gbc_lblDescripcionError.gridy = 7;
		frmFotos.getContentPane().add(lblDescripcionError, gbc_lblDescripcionError);
		
		lblDescripcion = new JLabel("Descripción (Máximo 200 palabras):");
		GridBagConstraints gbc_lblDescripcion = new GridBagConstraints();
		gbc_lblDescripcion.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblDescripcion.insets = new Insets(0, 0, 5, 5);
		gbc_lblDescripcion.gridx = 2;
		gbc_lblDescripcion.gridy = 3;
		frmFotos.getContentPane().add(lblDescripcion, gbc_lblDescripcion);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridheight = 3;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 2;
		gbc_scrollPane.gridy = 4;
		frmFotos.getContentPane().add(scrollPane, gbc_scrollPane);
		
		txtDescripcion = new JTextArea();
		txtDescripcion.setLineWrap(true);
		scrollPane.setViewportView(txtDescripcion);
		
		JButton btnAtras = new JButton("Atrás");
		btnAtras.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ventanaPrincipal.setVisible(true);
				frmFotos.dispose();
			}
		});
		
		lblError = new JLabel("NO HAS PUESTO EL NOMBRE DEL TITULO");
		lblError.setVisible(false);
		
		lblHashtagError = new JLabel("LOS HASHTAGS NO CUMPLEN LOS REQUISITOS");
		lblHashtagError.setVisible(false);
		lblHashtagError.setForeground(Color.RED);
		GridBagConstraints gbc_lblHashtagError = new GridBagConstraints();
		gbc_lblHashtagError.gridwidth = 2;
		gbc_lblHashtagError.insets = new Insets(0, 0, 5, 5);
		gbc_lblHashtagError.gridx = 1;
		gbc_lblHashtagError.gridy = 8;
		frmFotos.getContentPane().add(lblHashtagError, gbc_lblHashtagError);
		lblError.setForeground(Color.RED);
		lblError.setHorizontalAlignment(SwingConstants.CENTER);
		
		GridBagConstraints gbc_lblError = new GridBagConstraints();
		gbc_lblError.gridwidth = 2;
		gbc_lblError.insets = new Insets(0, 0, 5, 5);
		gbc_lblError.gridx = 1;
		gbc_lblError.gridy = 9;
		frmFotos.getContentPane().add(lblError, gbc_lblError);
		GridBagConstraints gbc_btnAtras = new GridBagConstraints();
		gbc_btnAtras.insets = new Insets(0, 0, 5, 5);
		gbc_btnAtras.gridx = 1;
		gbc_btnAtras.gridy = 10;
		frmFotos.getContentPane().add(btnAtras, gbc_btnAtras);
		
		JButton btnConfirmar = new JButton("Confirmar");
		btnConfirmar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				boolean OK = false;
				OK = comprobacion();
				if (OK) {
					File foto = new File(rutaFoto);
					String rutaDestino = "src/" + rutaPaquete.replace(".", "/") + "/" + foto.getName();
					File imagenDestino = new File(rutaDestino);
					if (!imagenDestino.getParentFile().exists()) {
			            imagenDestino.getParentFile().mkdirs();
			        }
			        
			        // Copia la imagen desde la ubicación original a la ubicación de destino
			        FileOutputStream fos;
			        FileInputStream fis;
					try {
						fos = new FileOutputStream(imagenDestino);
						fis = new FileInputStream(foto);
						byte[] buffer = new byte[1024];
				        int length;
				        while ((length = fis.read(buffer)) > 0) {
				            fos.write(buffer, 0, length);
				        }
				        fos.close();
				        fis.close();
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					Controlador.getUnicaInstancia().añadirFotoAlbum(album, rutaDestino, txtTitulo.getText(), txtDescripcion.getText());
					VentanaPrincipal window = new VentanaPrincipal();
					window.mostrarVentana();
					JOptionPane.showMessageDialog(window.getFrmVentanaPrincipal(), "Foto añadida correctamente",
					"Foto añadida", JOptionPane.INFORMATION_MESSAGE);
					frmFotos.dispose();
				}
			}
		});
		GridBagConstraints gbc_btnConfirmar = new GridBagConstraints();
		gbc_btnConfirmar.insets = new Insets(0, 0, 5, 5);
		gbc_btnConfirmar.gridx = 2;
		gbc_btnConfirmar.gridy = 10;
		frmFotos.getContentPane().add(btnConfirmar, gbc_btnConfirmar);

	}

	public JFrame getFrmFotos() {
		return frmFotos;
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

