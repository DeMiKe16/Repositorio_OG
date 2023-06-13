package vista;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import controlador.Controlador;
import modelo.Foto;
import modelo.Usuario;

import java.awt.GridLayout;
import java.awt.Image;

public class VentanaAlbumes{

	private JFrame frmAlbumes;
	private JLabel lblTitulo;
	private JLabel lblDescripcion;
	private JTextArea txtDescripcion;
	private JEditorPane DragAndDrop;
	private File ruta  = new File(VentanaLoginRegister.class.getResource("/imagenes/plusx128.png").getPath());
	private JLabel[] fotos;
	private List<Foto> album;
	private Usuario usuario;

	private JTextField txtTitulo;
	private JLabel lblError;
	private JPanel panelAlbum;
	private JLabel lblErrorFormato;
	private JLabel lblErrorFoto;
	private JLabel lblHashtagError;
	private JLabel lblDescripcionError;

	public void mostrarVentana() {
		frmAlbumes.setLocationRelativeTo(null);
		frmAlbumes.setVisible(true);
	}

	public VentanaAlbumes(List<Foto> album, Usuario user) {
		this.album = album;
		this.usuario = user;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	@SuppressWarnings("serial")
	private void initialize() {
		System.out.println(album);
		frmAlbumes = new JFrame();
		frmAlbumes.setTitle("Subir album");
		frmAlbumes.setIconImage(Toolkit.getDefaultToolkit()
				.getImage(VentanaLoginRegister.class.getResource("/imagenes/camarax32.png")));
		frmAlbumes.setBounds(100, 100, 731, 547);
		frmAlbumes.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{25, 160, 80, 160, 30, 254, 20, 0};
		gridBagLayout.rowHeights = new int[]{10, 20, 31, 0, 140, -1, 139, 0, 0, 20, 0, 20, 20, 10, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		frmAlbumes.getContentPane().setLayout(gridBagLayout);
		
		lblTitulo = new JLabel("Titulo :");
		GridBagConstraints gbc_lblTitulo = new GridBagConstraints();
		gbc_lblTitulo.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblTitulo.insets = new Insets(0, 0, 5, 5);
		gbc_lblTitulo.gridx = 5;
		gbc_lblTitulo.gridy = 1;
		frmAlbumes.getContentPane().add(lblTitulo, gbc_lblTitulo);
		
		txtTitulo = new JTextField();
		GridBagConstraints gbc_txtTitulo = new GridBagConstraints();
		gbc_txtTitulo.insets = new Insets(0, 0, 5, 5);
		gbc_txtTitulo.fill = GridBagConstraints.BOTH;
		gbc_txtTitulo.gridx = 5;
		gbc_txtTitulo.gridy = 2;
		frmAlbumes.getContentPane().add(txtTitulo, gbc_txtTitulo);
		txtTitulo.setColumns(10);
		
		lblDescripcion = new JLabel("Descripción (Máximo 200 palabras):");
		GridBagConstraints gbc_lblDescripcion = new GridBagConstraints();
		gbc_lblDescripcion.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblDescripcion.insets = new Insets(0, 0, 5, 5);
		gbc_lblDescripcion.gridx = 5;
		gbc_lblDescripcion.gridy = 3;
		frmAlbumes.getContentPane().add(lblDescripcion, gbc_lblDescripcion);
		
		panelAlbum = new JPanel();
		GridBagConstraints gbc_panelAlbum = new GridBagConstraints();
		gbc_panelAlbum.fill = GridBagConstraints.HORIZONTAL;
		gbc_panelAlbum.gridwidth = 3;
		gbc_panelAlbum.anchor = GridBagConstraints.NORTH;
		gbc_panelAlbum.gridheight = 6;
		gbc_panelAlbum.insets = new Insets(0, 0, 5, 5);
		gbc_panelAlbum.gridx = 1;
		gbc_panelAlbum.gridy = 1;
		frmAlbumes.getContentPane().add(panelAlbum, gbc_panelAlbum);
		panelAlbum.setLayout(new GridLayout(0, 4, 0, 0));
		
		fotos = new JLabel[album.size()];
		
		for (int i = 0; i < album.size(); i++) {
			fotos[i] = new JLabel();
			ImageIcon imagenFotos = new ImageIcon(album.get(i).getPath());
			Image imagenFotosACambiar = imagenFotos.getImage(); // transform it 
			ImageIcon iconoFotos= new ImageIcon(imagenFotosACambiar.getScaledInstance(100, 100,  java.awt.Image.SCALE_SMOOTH)); 
			fotos[i].setIcon(iconoFotos);
			fotos[i].setHorizontalAlignment(SwingConstants.LEFT);
			panelAlbum.add(fotos[i]);
		}
		
		if(album.size() < 16) {
			DragAndDrop = new JEditorPane();
			panelAlbum.add(DragAndDrop);
			DragAndDrop.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					JFileChooser fileChooser = new JFileChooser();
					if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
						File foto = new File(fileChooser.getSelectedFile().getAbsolutePath());
						int i = foto.getAbsolutePath().lastIndexOf('.');
						String extension = foto.getAbsolutePath().substring(i + 1);
						if (extension.equals("png") || extension.equals("jpg")) {
							lblErrorFormato.setVisible(false);
							String ruta = fileChooser.getSelectedFile().getAbsolutePath();
							VentanaFotosAlbum ventanaFotosAlbum = new VentanaFotosAlbum(ruta, album, usuario);
							ventanaFotosAlbum.getfrmFotosAlbum().setVisible(true);
							frmAlbumes.dispose();
						} else {
							lblErrorFormato.setVisible(true);
						}
					}
				}
			});
			DragAndDrop.setEditable(false);
			DragAndDrop.setContentType("text/html");
			DragAndDrop.setText("<html><body style='text-align: center;'><><img src=\"file:\\" + ruta.getAbsolutePath() + "\" width=85 height=85></body></img>");
			DragAndDrop.setBorder(new JTextField().getBorder());
			DragAndDrop.setForeground(new JTextField().getForeground());
			DragAndDrop.setDropTarget(new DropTarget() {
				public synchronized void drop(DropTargetDropEvent evt) {
					try {
						evt.acceptDrop(DnDConstants.ACTION_COPY);
						@SuppressWarnings("unchecked")
						List<File> droppedFiles = (List<File>) evt.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
						File foto = droppedFiles.get(0);
						int i = foto.getAbsolutePath().lastIndexOf('.');
						String extension = "";
						if (i > 0) {
							extension = foto.getAbsolutePath().substring(i + 1);
						}
						if (extension.equals("png") || extension.equals("jpg")) {
							//lblPhotoLoginError.setVisible(false);
							VentanaFotosAlbum ventanaFotosAlbum = new VentanaFotosAlbum(foto.getAbsolutePath(), album, usuario);
							ventanaFotosAlbum.getfrmFotosAlbum().setVisible(true);
							frmAlbumes.dispose();
						} else {
							//lblPhotoLoginError.setVisible(true);
							foto = new File(VentanaLoginRegister.class.getResource("/imagenes/usuario.png").getPath());
						}
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			});
		}
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridheight = 3;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 5;
		gbc_scrollPane.gridy = 4;
		frmAlbumes.getContentPane().add(scrollPane, gbc_scrollPane);
		
		txtDescripcion = new JTextArea();
		scrollPane.setViewportView(txtDescripcion);
		
		lblHashtagError = new JLabel("LOS HASHTAGS NO CUMPLEN LOS REQUISITOS");
		lblHashtagError.setVisible(false);
		
		lblDescripcionError = new JLabel("LA DESCRIPCIÓN TIENE UN MÁXIMO DE 200 CARÁCTERES");
		lblDescripcionError.setVisible(false);
		lblDescripcionError.setForeground(Color.RED);
		GridBagConstraints gbc_lblDescripcionError = new GridBagConstraints();
		gbc_lblDescripcionError.gridwidth = 5;
		gbc_lblDescripcionError.insets = new Insets(0, 0, 5, 5);
		gbc_lblDescripcionError.gridx = 1;
		gbc_lblDescripcionError.gridy = 7;
		frmAlbumes.getContentPane().add(lblDescripcionError, gbc_lblDescripcionError);
		lblHashtagError.setForeground(Color.RED);
		GridBagConstraints gbc_lblHashtagError = new GridBagConstraints();
		gbc_lblHashtagError.gridwidth = 5;
		gbc_lblHashtagError.insets = new Insets(0, 0, 5, 5);
		gbc_lblHashtagError.gridx = 1;
		gbc_lblHashtagError.gridy = 8;
		frmAlbumes.getContentPane().add(lblHashtagError, gbc_lblHashtagError);
		
		JButton btnAtras = new JButton("Atrás");
		btnAtras.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				VentanaPrincipal ventana = new VentanaPrincipal();
				ventana.mostrarVentana();
				frmAlbumes.dispose();
			}
		});
		
		lblErrorFoto = new JLabel("NO HAS ELEGIDO NINGUNA FOTO");
		lblErrorFoto.setForeground(Color.RED);
		GridBagConstraints gbc_lblErrorFoto = new GridBagConstraints();
		gbc_lblErrorFoto.gridwidth = 5;
		gbc_lblErrorFoto.insets = new Insets(0, 0, 5, 0);
		gbc_lblErrorFoto.gridx = 1;
		gbc_lblErrorFoto.gridy = 9;
		frmAlbumes.getContentPane().add(lblErrorFoto, gbc_lblErrorFoto);
		lblErrorFoto.setVisible(false);
		
		lblError = new JLabel("NO HAS PUESTO EL NOMBRE DEL TITULO");
		lblError.setVisible(false);
		lblError.setForeground(Color.RED);
		lblError.setHorizontalAlignment(SwingConstants.CENTER);
		
		GridBagConstraints gbc_lblError = new GridBagConstraints();
		gbc_lblError.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblError.gridwidth = 5;
		gbc_lblError.insets = new Insets(0, 0, 5, 0);
		gbc_lblError.gridx = 1;
		gbc_lblError.gridy = 10;
		frmAlbumes.getContentPane().add(lblError, gbc_lblError);
		
		lblErrorFormato = new JLabel("SOLO SE PUEDEN SUBIR FOTOS .PNG O .JPG");
		lblErrorFormato.setVisible(false);
		lblErrorFormato.setForeground(Color.RED);
		
		GridBagConstraints gbc_lblErrorFormato = new GridBagConstraints();
		gbc_lblErrorFormato.insets = new Insets(0, 0, 5, 0);
		gbc_lblErrorFormato.gridwidth = 5;
		gbc_lblErrorFormato.gridx = 1;
		gbc_lblErrorFormato.gridy = 11;
		frmAlbumes.getContentPane().add(lblErrorFormato, gbc_lblErrorFormato);
		GridBagConstraints gbc_btnAtras = new GridBagConstraints();
		gbc_btnAtras.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnAtras.insets = new Insets(0, 0, 5, 5);
		gbc_btnAtras.gridx = 2;
		gbc_btnAtras.gridy = 12;
		frmAlbumes.getContentPane().add(btnAtras, gbc_btnAtras);
		
		JButton btnConfirmar = new JButton("Confirmar");
		btnConfirmar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				boolean OK = false;
				ocultarErrores();
				OK = comprobacion();
				if (OK) {
					Controlador.getUnicaInstancia().subirAlbum(album, txtTitulo.getText(), txtDescripcion.getText());
					VentanaPrincipal window = new VentanaPrincipal();
					window.mostrarVentana();
					frmAlbumes.dispose();
				}
			}
		});
		GridBagConstraints gbc_btnConfirmar = new GridBagConstraints();
		gbc_btnConfirmar.insets = new Insets(0, 0, 5, 5);
		gbc_btnConfirmar.gridx = 5;
		gbc_btnConfirmar.gridy = 12;
		frmAlbumes.getContentPane().add(btnConfirmar, gbc_btnConfirmar);

	}

	public JFrame getfrmAlbumes() {
		return frmAlbumes;
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
		if (album.size() == 0) {
			lblErrorFoto.setVisible(true);
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
		lblErrorFoto.setVisible(false);
		lblErrorFormato.setVisible(false);
		lblHashtagError.setVisible(false);
		lblDescripcionError.setVisible(false);
		lblDescripcion.setForeground(new JLabel().getForeground());
		txtDescripcion.setBorder(new JTextField().getBorder());
		lblDescripcion.setForeground(new JLabel().getForeground());
		txtDescripcion.setBorder(new JTextField().getBorder());
	}
	
	
}
