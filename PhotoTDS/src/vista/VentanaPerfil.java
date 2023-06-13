package vista;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;

import javax.swing.JPanel;

import controlador.Controlador;
import modelo.Album;
import modelo.Foto;
import modelo.Usuario;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Insets;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.Dimension;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.SwingConstants;
import javax.swing.UIManager;

public class VentanaPerfil extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JFrame frmPerfil;
	private JTextField textUsuario;
	private JTextField textNombre;
	private JTextField textApellidos;
	private JTextField textEmail;
	private JTextArea textDescripcion;
	private File nuevaFoto = null;
	private JLabel lblAlbum;
	private JLabel lblFoto;
	private JLabel Seguidores;
	private JLabel Seguidos;
	private PanelFotoVisual[] panelesFotos;
	private PanelAlbumVisual[] panelesAlbumes;
	private JEditorPane DragAndDrop;
	private JPanel panelFotos;
	private JPanel panelAlbumes;
	private File ruta = new File(VentanaLoginRegister.class.getResource("/imagenes/plusx128.png").getPath());
	
	private JLabel lblPhotoLoginError;
	private JLabel lblDescripcionError;
	private JLabel lblCamposError;
	private JLabel lblNombreUsuarioError;
	
	private List<Foto> fotos;
	private List<Album> albumes;
	private Usuario usuario;
	private JLabel lblUsuarioEditar;
	private JLabel lblDescripcionEditar;
	
	

	public VentanaPerfil(Usuario user) {
		this.usuario = user;
		initialize();
	}

	public void mostrarVentana() {
		frmPerfil.setLocationRelativeTo(null);
		frmPerfil.setVisible(true);
	}

	@SuppressWarnings("serial")
	private void initialize() {
		frmPerfil = new JFrame();
		frmPerfil.setIconImage(
				Toolkit.getDefaultToolkit().getImage(VentanaPerfil.class.getResource("/imagenes/camarax32.png")));
		frmPerfil.setBounds(100, 100, 486, 500);
		frmPerfil.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panelCard = new JPanel();
		frmPerfil.getContentPane().add(panelCard, BorderLayout.CENTER);
		panelCard.setLayout(new CardLayout(0, 0));

		JPanel panelPerfil = new JPanel();
		panelCard.add(panelPerfil, "panelPerfil");
		panelPerfil.setLayout(new BorderLayout(0, 0));

		JPanel panelSuperior = new JPanel();
		panelSuperior.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panelPerfil.add(panelSuperior, BorderLayout.NORTH);
		GridBagLayout gbl_panelSuperior = new GridBagLayout();
		gbl_panelSuperior.columnWidths = new int[] { 15, 82, 15, 50, 15, 0, 0, 0, 15, 0 };
		gbl_panelSuperior.rowHeights = new int[] { 10, 3, 5, 5, 0, 122, 0 };
		gbl_panelSuperior.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 0.0, 0.0,
				Double.MIN_VALUE };
		gbl_panelSuperior.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		panelSuperior.setLayout(gbl_panelSuperior);

		JButton btnVolver = new JButton("");
		btnVolver.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				VentanaPrincipal profileWindow = new VentanaPrincipal();
				profileWindow.mostrarVentana();
				frmPerfil.dispose();
			}
		});
		
		if(Controlador.getUnicaInstancia().isUsuarioActual(usuario) && Controlador.getUnicaInstancia().esPremium(usuario)) {
			JLabel lblPremium = new JLabel("");
			lblPremium.setIcon(new ImageIcon(VentanaPerfil.class.getResource("/imagenes/premium.png")));
			GridBagConstraints gbc_lblPremium = new GridBagConstraints();
			gbc_lblPremium.insets = new Insets(0, 0, 5, 5);
			gbc_lblPremium.gridx = 3;
			gbc_lblPremium.gridy = 1;
			panelSuperior.add(lblPremium, gbc_lblPremium);
		}
		
		btnVolver.setIcon(new ImageIcon(VentanaPerfil.class.getResource("/imagenes/return.png")));
		GridBagConstraints gbc_btnVolver = new GridBagConstraints();
		gbc_btnVolver.insets = new Insets(0, 0, 5, 5);
		gbc_btnVolver.gridx = 7;
		gbc_btnVolver.gridy = 1;
		panelSuperior.add(btnVolver, gbc_btnVolver);

		JLabel lblFotoPerfil = new JLabel();
		ImageIcon imagen = new ImageIcon(Controlador.getUnicaInstancia().getPhotoUsr(usuario));
		Image imagenACambiar = imagen.getImage(); // transform it
		ImageIcon icono = new ImageIcon(imagenACambiar.getScaledInstance(70, 70, java.awt.Image.SCALE_SMOOTH));
		lblFotoPerfil.setIcon(icono);
		GridBagConstraints gbc_lblFotoPerfil = new GridBagConstraints();
		gbc_lblFotoPerfil.gridwidth = 2;
		gbc_lblFotoPerfil.gridheight = 2;
		gbc_lblFotoPerfil.insets = new Insets(0, 0, 5, 5);
		gbc_lblFotoPerfil.gridx = 1;
		gbc_lblFotoPerfil.gridy = 1;
		panelSuperior.add(lblFotoPerfil, gbc_lblFotoPerfil);

		JLabel lblNickName = new JLabel(Controlador.getUnicaInstancia().getLoginUsuario(usuario));
		lblNickName.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		GridBagConstraints gbc_lblNickName = new GridBagConstraints();
		gbc_lblNickName.anchor = GridBagConstraints.WEST;
		gbc_lblNickName.insets = new Insets(0, 0, 5, 5);
		gbc_lblNickName.gridx = 3;
		gbc_lblNickName.gridy = 2;
		panelSuperior.add(lblNickName, gbc_lblNickName);

		Seguidores = new JLabel(Controlador.getUnicaInstancia().getNumeroSeguidoresUsuario(usuario) + " Seguidores");
		Seguidores.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				mostrarLista("seguidores");
			}
		});

		if (Controlador.getUnicaInstancia().isUsuarioActual(usuario)) {
			JButton btnEditar = new JButton("Editar perfil");
			GridBagConstraints gbc_btnEditar = new GridBagConstraints();
			gbc_btnEditar.insets = new Insets(0, 0, 5, 5);
			gbc_btnEditar.gridx = 5;
			gbc_btnEditar.gridy = 2;
			panelSuperior.add(btnEditar, gbc_btnEditar);
			btnEditar.setIcon(new ImageIcon(VentanaPerfil.class.getResource("/imagenes/gear.png")));
			btnEditar.setFont(new Font("Segoe UI", Font.BOLD, 12));
	
			btnEditar.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					CardLayout cl = (CardLayout) (panelCard.getLayout());
					cl.show(panelCard, "panelEdit");
				}
			});
		
		
			JButton btnPremium = new JButton();
			btnPremium.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					VentanaPremium ventanaPrem = new VentanaPremium(frmPerfil);
					ventanaPrem.mostrarVentana();
					frmPerfil.setEnabled(false);
				}
			});
			btnPremium.setIcon(new ImageIcon(VentanaPerfil.class.getResource("/imagenes/premium.png")));
			GridBagConstraints gbc_btnPremium = new GridBagConstraints();
			gbc_btnPremium.insets = new Insets(0, 0, 5, 5);
			gbc_btnPremium.gridx = 7;
			gbc_btnPremium.gridy = 2;
			panelSuperior.add(btnPremium, gbc_btnPremium);	
		} else {
			JButton followbtn = new JButton();
			if(!Controlador.getUnicaInstancia().esSeguidor(usuario))
				followbtn.setText("Seguir");
			else
				followbtn.setText("Dejar de seguir");
			followbtn.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if(followbtn.getText().equals("Seguir")) {
						Controlador.getUnicaInstancia().seguir(usuario);
						Seguidores.setText(Controlador.getUnicaInstancia().getNumeroSeguidoresUsuario(usuario) + " Seguidores");
						followbtn.setText("Dejar de seguir");
					}else {
						Controlador.getUnicaInstancia().dejarSeguir(usuario);
						Seguidores.setText(Controlador.getUnicaInstancia().getNumeroSeguidoresUsuario(usuario) + " Seguidores");
						followbtn.setText("Seguir");
					}
				}
			});
			GridBagConstraints gbc_followbtn = new GridBagConstraints();
			gbc_followbtn.insets = new Insets(0, 0, 5, 5);
			gbc_followbtn.gridx = 5;
			gbc_followbtn.gridy = 2;
			panelSuperior.add(followbtn, gbc_followbtn);
			
		}
		
		
		JLabel lblNombre = new JLabel(Controlador.getUnicaInstancia().getNombreUsuario(usuario) + " " + Controlador.getUnicaInstancia().getApellidosUsuario(usuario));
		GridBagConstraints gbc_lblNombre = new GridBagConstraints();
		gbc_lblNombre.gridwidth = 3;
		gbc_lblNombre.insets = new Insets(0, 0, 5, 5);
		gbc_lblNombre.gridx = 1;
		gbc_lblNombre.gridy = 3;
		panelSuperior.add(lblNombre, gbc_lblNombre);
		GridBagConstraints gbc_Seguidores = new GridBagConstraints();
		gbc_Seguidores.insets = new Insets(0, 0, 5, 5);
		gbc_Seguidores.gridx = 3;
		gbc_Seguidores.gridy = 4;
		panelSuperior.add(Seguidores, gbc_Seguidores);

		Seguidos = new JLabel(Controlador.getUnicaInstancia().getNumeroSeguidosUsuario(usuario) + " Seguidos");
		Seguidos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				mostrarLista("seguidos");
			}
		});
		GridBagConstraints gbc_Seguidos = new GridBagConstraints();
		gbc_Seguidos.insets = new Insets(0, 0, 5, 5);
		gbc_Seguidos.gridx = 5;
		gbc_Seguidos.gridy = 4;
		panelSuperior.add(Seguidos, gbc_Seguidos);
		
		JTextArea textArea = new JTextArea(Controlador.getUnicaInstancia().getDescripcionUsuario(usuario));
		textArea.setFont(new Font("Tahoma", Font.PLAIN, 11));
		textArea.setBackground(UIManager.getColor("Button.background"));
		textArea.setEditable(false);
		textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
		GridBagConstraints gbc_textArea = new GridBagConstraints();
		gbc_textArea.gridwidth = 3;
		gbc_textArea.insets = new Insets(0, 0, 5, 5);
		gbc_textArea.fill = GridBagConstraints.BOTH;
		gbc_textArea.gridx = 1;
		gbc_textArea.gridy = 5;
		panelSuperior.add(textArea, gbc_textArea);

		JPanel panelPublicaciones = new JPanel();
		panelPerfil.add(panelPublicaciones, BorderLayout.CENTER);
		panelPublicaciones.setLayout(new BorderLayout(0, 0));

		JPanel panelSeleccion = new JPanel();
		panelPublicaciones.add(panelSeleccion, BorderLayout.NORTH);
		GridBagLayout gbl_panelSeleccion = new GridBagLayout();
		gbl_panelSeleccion.columnWidths = new int[] { 0, 70, 70, 0, 0 };
		gbl_panelSeleccion.rowHeights = new int[] { 0, 0 };
		gbl_panelSeleccion.columnWeights = new double[] { 1.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		gbl_panelSeleccion.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		panelSeleccion.setLayout(gbl_panelSeleccion);

		JPanel panelCardSeleccion = new JPanel();
		panelPublicaciones.add(panelCardSeleccion, BorderLayout.CENTER);
		panelCardSeleccion.setLayout(new CardLayout(0, 0));

		JScrollPane scrollPaneFotos = new JScrollPane();
		panelCardSeleccion.add(scrollPaneFotos, "PanelFoto");

		panelFotos = new JPanel();
		scrollPaneFotos.setViewportView(panelFotos);
		panelFotos.setLayout(new GridLayout(0, 3));

		fotos = new LinkedList<Foto>(Controlador.getUnicaInstancia().obtenerFotosUsuario(usuario));
		panelesFotos = new PanelFotoVisual[fotos.size()];

		for (int i = 0; i < fotos.size(); i++) {
			panelesFotos[i] = new PanelFotoVisual(fotos.get(i), 155, usuario, frmPerfil);
			FlowLayout flowLayout_1 = (FlowLayout) panelesFotos[i].getLayout();
			flowLayout_1.setVgap(0);
			flowLayout_1.setHgap(0);
			panelFotos.add(panelesFotos[i]);
		}

		JScrollPane scrollPaneAlbumes = new JScrollPane();
		panelCardSeleccion.add(scrollPaneAlbumes, "PanelAlbum");

		panelAlbumes = new JPanel();
		scrollPaneAlbumes.setViewportView(panelAlbumes);
		panelAlbumes.setLayout(new GridLayout(0, 3));

		albumes = Controlador.getUnicaInstancia().obtenerAlbumesUsuario(usuario);
		panelesAlbumes = new PanelAlbumVisual[albumes.size()];

		for (int i = 0; i < albumes.size(); i++) {
			panelesAlbumes[i] = new PanelAlbumVisual(albumes.get(i), 155, usuario, frmPerfil);
			FlowLayout flowLayout_1 = (FlowLayout) panelesAlbumes[i].getLayout();
			flowLayout_1.setVgap(0);
			flowLayout_1.setHgap(0);
			panelAlbumes.add(panelesAlbumes[i]);
		}

		JPanel panelEdit = new JPanel();
		panelCard.add(panelEdit, "panelEdit");
		GridBagLayout gbl_panelEdit = new GridBagLayout();
		gbl_panelEdit.columnWidths = new int[] { 15, 45, 225, 86, 70, 15, 0 };
		gbl_panelEdit.rowHeights = new int[] { 0, 5, 0, 0, 10, 0, 0, 0, 28, 150, 0, 0, 0, 0, 0, 10, 0 };
		gbl_panelEdit.columnWeights = new double[] { 0.0, 0.0, 1.0, 1.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_panelEdit.rowWeights = new double[] { 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0,
				Double.MIN_VALUE };
		panelEdit.setLayout(gbl_panelEdit);

		JButton btnNewButton_1 = new JButton("");
		btnNewButton_1.setIcon(new ImageIcon(VentanaPerfil.class.getResource("/imagenes/return.png")));
		GridBagConstraints gbc_btnNewButton_1 = new GridBagConstraints();
		gbc_btnNewButton_1.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton_1.gridx = 4;
		gbc_btnNewButton_1.gridy = 1;
		btnNewButton_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				CardLayout cl = (CardLayout) (panelCard.getLayout());
				cl.show(panelCard, "panelPerfil");
			}
		});
		panelEdit.add(btnNewButton_1, gbc_btnNewButton_1);

		JLabel lblIntroduccion1 = new JLabel("En esta ventana puedes modificar");
		lblIntroduccion1.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		GridBagConstraints gbc_lblIntroduccion1 = new GridBagConstraints();
		gbc_lblIntroduccion1.gridwidth = 2;
		gbc_lblIntroduccion1.insets = new Insets(0, 0, 5, 5);
		gbc_lblIntroduccion1.gridx = 2;
		gbc_lblIntroduccion1.gridy = 2;
		panelEdit.add(lblIntroduccion1, gbc_lblIntroduccion1);

		JLabel lblIntroduccion2 = new JLabel(" tus datos de usuario");
		lblIntroduccion2.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		GridBagConstraints gbc_lblIntroduccion2 = new GridBagConstraints();
		gbc_lblIntroduccion2.gridwidth = 2;
		gbc_lblIntroduccion2.insets = new Insets(0, 0, 5, 5);
		gbc_lblIntroduccion2.gridx = 2;
		gbc_lblIntroduccion2.gridy = 3;
		panelEdit.add(lblIntroduccion2, gbc_lblIntroduccion2);

		lblUsuarioEditar = new JLabel("Usuario:");
		GridBagConstraints gbc_lblUsuarioEditar = new GridBagConstraints();
		gbc_lblUsuarioEditar.anchor = GridBagConstraints.EAST;
		gbc_lblUsuarioEditar.insets = new Insets(0, 0, 5, 5);
		gbc_lblUsuarioEditar.gridx = 1;
		gbc_lblUsuarioEditar.gridy = 5;
		panelEdit.add(lblUsuarioEditar, gbc_lblUsuarioEditar);

		textUsuario = new JTextField();
		GridBagConstraints gbc_textUsuario = new GridBagConstraints();
		gbc_textUsuario.insets = new Insets(0, 0, 5, 5);
		gbc_textUsuario.fill = GridBagConstraints.HORIZONTAL;
		gbc_textUsuario.gridx = 2;
		gbc_textUsuario.gridy = 5;
		panelEdit.add(textUsuario, gbc_textUsuario);
		textUsuario.setColumns(10);

		JLabel lblNombreEditar = new JLabel("Nombre:");
		GridBagConstraints gbc_lblNombreEditar = new GridBagConstraints();
		gbc_lblNombreEditar.anchor = GridBagConstraints.EAST;
		gbc_lblNombreEditar.insets = new Insets(0, 0, 5, 5);
		gbc_lblNombreEditar.gridx = 1;
		gbc_lblNombreEditar.gridy = 6;
		panelEdit.add(lblNombreEditar, gbc_lblNombreEditar);

		textNombre = new JTextField();
		GridBagConstraints gbc_textNombre = new GridBagConstraints();
		gbc_textNombre.insets = new Insets(0, 0, 5, 5);
		gbc_textNombre.fill = GridBagConstraints.HORIZONTAL;
		gbc_textNombre.gridx = 2;
		gbc_textNombre.gridy = 6;
		panelEdit.add(textNombre, gbc_textNombre);
		textNombre.setColumns(10);

		JLabel lblApellidosEditar = new JLabel("Apellidos:");
		GridBagConstraints gbc_lblApellidosEditar = new GridBagConstraints();
		gbc_lblApellidosEditar.anchor = GridBagConstraints.EAST;
		gbc_lblApellidosEditar.insets = new Insets(0, 0, 5, 5);
		gbc_lblApellidosEditar.gridx = 1;
		gbc_lblApellidosEditar.gridy = 7;
		panelEdit.add(lblApellidosEditar, gbc_lblApellidosEditar);

		textApellidos = new JTextField();
		GridBagConstraints gbc_textApellidos = new GridBagConstraints();
		gbc_textApellidos.insets = new Insets(0, 0, 5, 5);
		gbc_textApellidos.fill = GridBagConstraints.HORIZONTAL;
		gbc_textApellidos.gridx = 2;
		gbc_textApellidos.gridy = 7;
		panelEdit.add(textApellidos, gbc_textApellidos);
		textApellidos.setColumns(10);

		JLabel lblEmailEditar = new JLabel("Email:");
		GridBagConstraints gbc_lblEmailEditar = new GridBagConstraints();
		gbc_lblEmailEditar.anchor = GridBagConstraints.EAST;
		gbc_lblEmailEditar.insets = new Insets(0, 0, 5, 5);
		gbc_lblEmailEditar.gridx = 1;
		gbc_lblEmailEditar.gridy = 8;
		panelEdit.add(lblEmailEditar, gbc_lblEmailEditar);

		textEmail = new JTextField();
		GridBagConstraints gbc_textEmail = new GridBagConstraints();
		gbc_textEmail.insets = new Insets(0, 0, 5, 5);
		gbc_textEmail.fill = GridBagConstraints.HORIZONTAL;
		gbc_textEmail.gridx = 2;
		gbc_textEmail.gridy = 8;
		panelEdit.add(textEmail, gbc_textEmail);
		textEmail.setColumns(10);
		
		JLabel lblPhotoLoginEditar = new JLabel("Foto de perfil:");
		GridBagConstraints gbc_lblPhotoLoginEditar = new GridBagConstraints();
		gbc_lblPhotoLoginEditar.fill = GridBagConstraints.VERTICAL;
		gbc_lblPhotoLoginEditar.gridwidth = 2;
		gbc_lblPhotoLoginEditar.insets = new Insets(0, 0, 5, 5);
		gbc_lblPhotoLoginEditar.gridx = 3;
		gbc_lblPhotoLoginEditar.gridy = 8;
		panelEdit.add(lblPhotoLoginEditar, gbc_lblPhotoLoginEditar);

		lblDescripcionEditar = new JLabel("Descripcion:");
		GridBagConstraints gbc_lblDescripcionEditar = new GridBagConstraints();
		gbc_lblDescripcionEditar.anchor = GridBagConstraints.EAST;
		gbc_lblDescripcionEditar.insets = new Insets(0, 0, 5, 5);
		gbc_lblDescripcionEditar.gridx = 1;
		gbc_lblDescripcionEditar.gridy = 9;
		panelEdit.add(lblDescripcionEditar, gbc_lblDescripcionEditar);

		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 2;
		gbc_scrollPane.gridy = 9;
		panelEdit.add(scrollPane, gbc_scrollPane);

		textDescripcion = new JTextArea();
		textDescripcion.setLineWrap(true);
		textArea.setLineWrap(true);
		scrollPane.setViewportView(textDescripcion);

		JButton btnConfirmar = new JButton("Confirmar cambios");
		GridBagConstraints gbc_btnConfirmar = new GridBagConstraints();
		gbc_btnConfirmar.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnConfirmar.gridwidth = 2;
		gbc_btnConfirmar.insets = new Insets(0, 0, 5, 5);
		gbc_btnConfirmar.gridx = 2;
		gbc_btnConfirmar.gridy = 14;
		btnConfirmar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String login = textUsuario.getText();
				String nombre = textNombre.getText();
				String apellidos = textApellidos.getText();
				String email = textEmail.getText();
				String descrip = textDescripcion.getText();
				String photologin;
				if (nuevaFoto == null) {
					photologin = null;
				} else {
					photologin = nuevaFoto.getPath();
				}
				boolean OK = false;
				OK = comprobacion();
				if (OK) {
				Controlador.getUnicaInstancia().actualizarUsuario(login, nombre, apellidos, email,descrip, photologin);
					VentanaPrincipal principal = new VentanaPrincipal();
					principal.mostrarVentana();
					JOptionPane.showMessageDialog(principal.getFrmVentanaPrincipal(),
							"Usuario modificado correctamente.",
							"Editar perfil", JOptionPane.INFORMATION_MESSAGE);
					vaciarTextosEditar();
					frmPerfil.dispose();
				} 
			}
		});
		
		lblPhotoLoginError = new JLabel("SOLO SE PUEDEN SUBIR FOTOS .PNG O .JPG");
		lblPhotoLoginError.setForeground(Color.RED);
		lblPhotoLoginError.setVisible(false);
		
		DragAndDrop = new JEditorPane();
		DragAndDrop.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					nuevaFoto = new File(fileChooser.getSelectedFile().getAbsolutePath());
					int i = nuevaFoto.getAbsolutePath().lastIndexOf('.');
					String extension = nuevaFoto.getAbsolutePath().substring(i + 1);
					if (extension.equals("png") || extension.equals("jpg")) {
						lblPhotoLoginError.setVisible(false);
						DragAndDrop.setText("<html><img src=\"file:\\" + nuevaFoto.getAbsolutePath() + "\" width=200 height=200></img>");
					} else {
						lblPhotoLoginError.setVisible(true);
						DragAndDrop.setText("<html><body style='text-align: center;'><img src=\"file:\\" + ruta.getAbsolutePath() + "\" width=120 height=120></body></img>");
						nuevaFoto = null;
					}
				}
			}
		});
		DragAndDrop.setEditable(false);
		DragAndDrop.setContentType("text/html");
		DragAndDrop.setText("<html><body style='text-align: center;'><img src=\"file:\\" + ruta.getAbsolutePath() + "\" width=120 height=120></body></img>");
		DragAndDrop.setBorder(new JTextField().getBorder());
		DragAndDrop.setForeground(new JTextField().getForeground());
		DragAndDrop.setDropTarget(new DropTarget() {
			public synchronized void drop(DropTargetDropEvent evt) {
				try {
					evt.acceptDrop(DnDConstants.ACTION_COPY);
					@SuppressWarnings("unchecked")
					List<File> droppedFiles = (List<File>) evt.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
					nuevaFoto = droppedFiles.get(0);
					int i = nuevaFoto.getAbsolutePath().lastIndexOf('.');
					String extension = "";
					if (i > 0) {
						extension = nuevaFoto.getAbsolutePath().substring(i + 1);
					}
					if (extension.equals("png") || extension.equals("jpg")) {
						lblPhotoLoginError.setVisible(false);
						DragAndDrop.setText("<html><img src=\"file:\\" + nuevaFoto.getAbsolutePath()
								+ "\" width=200 height=200></img>");
					} else {
						lblPhotoLoginError.setVisible(true);
						DragAndDrop.setText("<html><body style='text-align: center;'><img src=\"file:\\" + ruta.getAbsolutePath() + "\" width=120 height=120></body></img>");
						nuevaFoto = null;
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		GridBagConstraints gbc_editorPane = new GridBagConstraints();
		gbc_editorPane.gridwidth = 2;
		gbc_editorPane.insets = new Insets(0, 0, 5, 5);
		gbc_editorPane.fill = GridBagConstraints.HORIZONTAL;
		gbc_editorPane.gridx = 3;
		gbc_editorPane.gridy = 9;
		panelEdit.add(DragAndDrop, gbc_editorPane);
		
		lblDescripcionError = new JLabel("LA DESCRIPCIÓN NO DEBE TENER MAS DE 200 CARÁCTERES");
		lblDescripcionError.setVisible(false);
		
		lblCamposError = new JLabel("NO HAY NINGUN CAMBIO APLICABLE");
		lblCamposError.setVisible(false);
		lblCamposError.setForeground(Color.RED);
		GridBagConstraints gbc_lblCamposError = new GridBagConstraints();
		gbc_lblCamposError.gridwidth = 4;
		gbc_lblCamposError.insets = new Insets(0, 0, 5, 5);
		gbc_lblCamposError.gridx = 1;
		gbc_lblCamposError.gridy = 10;
		panelEdit.add(lblCamposError, gbc_lblCamposError);
		lblDescripcionError.setForeground(Color.RED);
		GridBagConstraints gbc_lblDescripcionError = new GridBagConstraints();
		gbc_lblDescripcionError.gridwidth = 4;
		gbc_lblDescripcionError.insets = new Insets(0, 0, 5, 5);
		gbc_lblDescripcionError.gridx = 1;
		gbc_lblDescripcionError.gridy = 11;
		panelEdit.add(lblDescripcionError, gbc_lblDescripcionError);
		
		lblNombreUsuarioError = new JLabel("ESTE NOMBRE DE USUARIO YA ESTA COGIDO");
		lblNombreUsuarioError.setVisible(false);
		lblNombreUsuarioError.setForeground(Color.RED);
		GridBagConstraints gbc_lblNombreUsuarioError = new GridBagConstraints();
		gbc_lblNombreUsuarioError.gridwidth = 4;
		gbc_lblNombreUsuarioError.insets = new Insets(0, 0, 5, 5);
		gbc_lblNombreUsuarioError.gridx = 1;
		gbc_lblNombreUsuarioError.gridy = 12;
		panelEdit.add(lblNombreUsuarioError, gbc_lblNombreUsuarioError);
		
		GridBagConstraints gbc_lblPhotoLoginError = new GridBagConstraints();
		gbc_lblPhotoLoginError.gridwidth = 4;
		gbc_lblPhotoLoginError.insets = new Insets(0, 0, 5, 5);
		gbc_lblPhotoLoginError.gridx = 1;
		gbc_lblPhotoLoginError.gridy = 13;
		panelEdit.add(lblPhotoLoginError, gbc_lblPhotoLoginError);

		panelEdit.add(btnConfirmar, gbc_btnConfirmar);

		lblAlbum = new JLabel("ALBUMES");
		lblAlbum.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				lblFoto.setBorder(new JLabel().getBorder());
				lblAlbum.setBorder(new LineBorder(Color.gray));
				CardLayout cl = (CardLayout) (panelCardSeleccion.getLayout());
				cl.show(panelCardSeleccion, "PanelAlbum");
			}
		});

		lblAlbum.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lblAlbum = new GridBagConstraints();
		gbc_lblAlbum.fill = GridBagConstraints.BOTH;
		gbc_lblAlbum.insets = new Insets(0, 0, 0, 5);
		gbc_lblAlbum.gridx = 2;
		gbc_lblAlbum.gridy = 0;
		panelSeleccion.add(lblAlbum, gbc_lblAlbum);

		lblFoto = new JLabel("FOTOS");
		lblFoto.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				lblAlbum.setBorder(new JLabel().getBorder());
				lblFoto.setBorder(new LineBorder(Color.gray));
				CardLayout cl = (CardLayout) (panelCardSeleccion.getLayout());
				cl.show(panelCardSeleccion, "PanelFoto");
			}
		});
		lblFoto.setHorizontalAlignment(SwingConstants.CENTER);
		lblFoto.setBorder(new LineBorder(Color.gray));
		GridBagConstraints gbc_lblFotos = new GridBagConstraints();
		gbc_lblFotos.fill = GridBagConstraints.BOTH;
		gbc_lblFotos.insets = new Insets(0, 0, 0, 5);
		gbc_lblFotos.gridx = 1;
		gbc_lblFotos.gridy = 0;
		panelSeleccion.add(lblFoto, gbc_lblFotos);
	}
	
	private boolean comprobacion() {
		boolean salida = true;
		ocultarErrores();
		if(textUsuario.getText().trim().isBlank()  && (textNombre.getText().trim().isBlank() || Controlador.getUnicaInstancia().getNombreUsuario(usuario).equals(textNombre.getText().trim())) &&
				(textApellidos.getText().trim().isBlank() || Controlador.getUnicaInstancia().getApellidosUsuario(usuario).equals(textApellidos.getText().trim())) &&
				(textEmail.getText().trim().isBlank() || Controlador.getUnicaInstancia().getEmailUsuario(usuario).equals(textEmail.getText().trim())) &&
				(Controlador.getUnicaInstancia().getDescripcionUsuario(usuario).equals(textDescripcion.getText().trim())) && (nuevaFoto == null)) {
			lblCamposError.setVisible(true);
			return false;
		}
		if (Controlador.getUnicaInstancia().esUsuarioRegistrado(textUsuario.getText().trim())) {
			lblNombreUsuarioError.setVisible(true);
			lblUsuarioEditar.setForeground(Color.RED);
			textUsuario.setBorder(BorderFactory.createLineBorder(Color.RED));
			return false;
		}
		if(textDescripcion.getText().trim().length() > 200) {
			lblDescripcionError.setVisible(true);
			lblDescripcionEditar.setForeground(Color.RED);
			textDescripcion.setBorder(BorderFactory.createLineBorder(Color.RED));
			return false;
		}
		return salida;
	}
	
	private void ocultarErrores() {	
		lblCamposError.setVisible(false);
		lblNombreUsuarioError.setVisible(false);
		lblDescripcionError.setVisible(false);
		lblUsuarioEditar.setForeground(new JLabel().getForeground());
		textUsuario.setBorder(new JTextField().getBorder());
		lblDescripcionEditar.setForeground(new JLabel().getForeground());
		textDescripcion.setBorder(new JTextField().getBorder());
	}

	private void vaciarTextosEditar() {
		textNombre.setText(null);
		textApellidos.setText(null);
		textEmail.setText(null);
		textDescripcion.setText(null);
	}

	public void mostrarLista(String tipo) {
		DefaultListModel<Usuario> listModel = new DefaultListModel<>();
		if (tipo.equals("seguidos")) {
			for (Usuario u : Controlador.getUnicaInstancia().getSeguidosUsuario(usuario)) {
				listModel.addElement(u);
			}
		} else {
			for (Usuario u : Controlador.getUnicaInstancia().getSeguidoresUsuario(usuario)) {
				listModel.addElement(u);
			}
		}
		JList<Usuario> usersList = new JList<>(listModel);
		usersList.setCellRenderer(new UsuarioRenderer());
		JScrollPane pane = new JScrollPane(usersList);
		JFrame frame = createFrame(tipo);
		frame.getContentPane().add(pane);
		usersList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) {
					VentanaPerfil profileWindow = new VentanaPerfil(usersList.getSelectedValue());
					profileWindow.mostrarVentana();
					frmPerfil.setVisible(false);
					frame.setVisible(false);
				}
			}
		});
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	private static JFrame createFrame(String tipo) {
		JFrame searchframe = new JFrame("Lista de " + tipo);
		searchframe.setTitle("Lista de " + tipo);
		searchframe.setIconImage(
				Toolkit.getDefaultToolkit().getImage(VentanaPrincipal.class.getResource("/imagenes/camarax32.png")));
		searchframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		searchframe.setSize(new Dimension(300, 300));
		return searchframe;
	}
}