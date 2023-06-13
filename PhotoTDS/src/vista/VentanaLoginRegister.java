package vista;

import javax.swing.JFrame;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JMenuBar;
import javax.swing.JRadioButtonMenuItem;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.BorderFactory;
import java.io.File;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import javax.swing.JRadioButton;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.CardLayout;

import com.toedter.calendar.JDateChooser;

import controlador.Controlador;

public class VentanaLoginRegister extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JFrame frmLoginSignUp;
	private JTextField txtFieldUsuarioLogin;
	private JPasswordField txtpasswordFieldLogin;
	private JLabel lblNombreSignUp;
	private JLabel lblApellidosSignUp;
	private JLabel lblFechaNacimientoSignUp;
	private JLabel lblEmailSignUp;
	private JLabel lblUsuarioSignUp;
	private JLabel lblPasswordSignUp;
	private JLabel lblDescripcionSignUp;
	private JTextField txtNombreSignUp;
	private JTextField txtApellidosSignUp;
	private JTextField txtEmailSignUp;
	private JTextField txtUsuarioSignUp;
	private JTextArea txtDescripcionSignUp;
	private JPasswordField txtPasswordSignUp;
	private JButton btnRegistrarSignUp;
	private JButton btnCancelarSignUp;
	private JEditorPane DragAndDrop;
	private JDateChooser txtFechaNacimientoSignUp;
	private File ruta  = new File(VentanaLoginRegister.class.getResource("/imagenes/plusx128.png").getPath());

	private File PhotoLogin = new File(VentanaLoginRegister.class.getResource("/imagenes/usuario.png").getPath());

	private JLabel lblDescripcionError;
	private JLabel lblCampoError;
	private JLabel lblUsuarioError;
	private JLabel lblPhotoLoginError;
	private JLabel lblEdadError;
	

	/**
	 * Create the application.
	 */
	public VentanaLoginRegister() {
		initialize();
	}
	
	public void mostrarVentana() {
		frmLoginSignUp.setLocationRelativeTo(null);
		frmLoginSignUp.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	@SuppressWarnings("serial")
	private void initialize() {
		frmLoginSignUp = new JFrame();
		frmLoginSignUp.setTitle("Login PhotoTDS");
		frmLoginSignUp.setIconImage(Toolkit.getDefaultToolkit()
				.getImage(VentanaLoginRegister.class.getResource("/imagenes/camarax32.png")));
		frmLoginSignUp.setBounds(100, 100, 731, 546);
		frmLoginSignUp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmLoginSignUp.getContentPane().setLayout(new BorderLayout(0, 0));

		JPanel panel_CardLayout = new JPanel();
		frmLoginSignUp.getContentPane().add(panel_CardLayout, BorderLayout.CENTER);
		panel_CardLayout.setLayout(new CardLayout(0, 0));

		JPanel panelLogin = new JPanel();
		panel_CardLayout.add(panelLogin, "panelLogin");
		panelLogin.setLayout(new BorderLayout(0, 0));

		JPanel panelNorteLogin = new JPanel();
		panelLogin.add(panelNorteLogin, BorderLayout.NORTH);
		panelNorteLogin.setLayout(new BorderLayout(0, 0));

		JLabel lblBienvenidoLogin = new JLabel("Bienvenido a PhotoTDS\r\n");
		lblBienvenidoLogin.setHorizontalAlignment(SwingConstants.CENTER);
		lblBienvenidoLogin.setFont(new Font("Gentium Book Basic", Font.PLAIN, 20));
		panelNorteLogin.add(lblBienvenidoLogin, BorderLayout.NORTH);

		JLabel lblImagenLogin = new JLabel("");
		lblImagenLogin.setIcon(new ImageIcon(VentanaLoginRegister.class.getResource("/imagenes/camarax64.png")));
		lblImagenLogin.setHorizontalAlignment(SwingConstants.CENTER);
		panelNorteLogin.add(lblImagenLogin, BorderLayout.CENTER);

		JLabel lblNewLabelLogin = new JLabel("Aqu\u00ED puedes iniciar sesi\u00F3n en tu cuenta");
		panelNorteLogin.add(lblNewLabelLogin, BorderLayout.SOUTH);
		lblNewLabelLogin.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabelLogin.setFont(new Font("Gentium Book Basic", Font.PLAIN, 15));

		JPanel panelSurlogin = new JPanel();
		panelSurlogin.setToolTipText("");
		panelSurlogin.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelLogin.add(panelSurlogin, BorderLayout.SOUTH);
		GridBagLayout gbl_panelSurlogin = new GridBagLayout();
		gbl_panelSurlogin.columnWidths = new int[] { 0, 100, 0, 0 };
		gbl_panelSurlogin.rowHeights = new int[] { 0, 0, 41, 0 };
		gbl_panelSurlogin.columnWeights = new double[] { 1.0, 0.0, 1.0, Double.MIN_VALUE };
		gbl_panelSurlogin.rowWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panelSurlogin.setLayout(gbl_panelSurlogin);

		JLabel lblNewLabel = new JLabel("\u00BFA\u00FAn no tienes una cuenta?");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 1;
		gbc_lblNewLabel.gridy = 0;
		panelSurlogin.add(lblNewLabel, gbc_lblNewLabel);

		JButton btnNewButtonLogin = new JButton("Registrate!");
		btnNewButtonLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		GridBagConstraints gbc_btnNewButtonLogin = new GridBagConstraints();
		gbc_btnNewButtonLogin.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButtonLogin.gridx = 1;
		gbc_btnNewButtonLogin.gridy = 1;
		panelSurlogin.add(btnNewButtonLogin, gbc_btnNewButtonLogin);
		btnNewButtonLogin.setIcon(new ImageIcon(VentanaLoginRegister.class.getResource("/imagenes/SignUp.png")));

		btnNewButtonLogin.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				CardLayout cl = (CardLayout) (panel_CardLayout.getLayout());
				cl.show(panel_CardLayout, "panelSignUp");
				vaciarTextosLogin();
				ocultarErrores();
			}
		});

		JButton btnLogin = new JButton("\u00DAnete!");
		btnLogin.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				boolean login = Controlador.getUnicaInstancia().loginUsuario(txtFieldUsuarioLogin.getText(),
						new String(txtpasswordFieldLogin.getPassword()));

				if (login) {
					VentanaPrincipal window = new VentanaPrincipal();
					window.mostrarVentana();
					frmLoginSignUp.dispose();
				} else
					JOptionPane.showMessageDialog(frmLoginSignUp, "Nombre de usuario o contraseï¿½a no valido", "Error",
							JOptionPane.ERROR_MESSAGE);
			}
		});
		btnLogin.setIcon(new ImageIcon(VentanaLoginRegister.class.getResource("/imagenes/login.png")));
		GridBagConstraints gbc_btnLogin = new GridBagConstraints();
		gbc_btnLogin.insets = new Insets(0, 0, 0, 5);
		gbc_btnLogin.fill = GridBagConstraints.BOTH;
		gbc_btnLogin.gridx = 1;
		gbc_btnLogin.gridy = 2;
		panelSurlogin.add(btnLogin, gbc_btnLogin);

		JPanel panelGridLogin = new JPanel();
		panelGridLogin.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "",
				TitledBorder.LEADING, TitledBorder.BOTTOM, null, new Color(0, 0, 0)));
		panelLogin.add(panelGridLogin, BorderLayout.CENTER);
		GridBagLayout gbl_panelGridLogin = new GridBagLayout();
		gbl_panelGridLogin.columnWidths = new int[] { 10, 30, 350, 66, 10, 0 };
		gbl_panelGridLogin.rowHeights = new int[] { 20, 20, 0, 0, 10, 0, 0, 0, 0 };
		gbl_panelGridLogin.columnWeights = new double[] { 0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_panelGridLogin.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		panelGridLogin.setLayout(gbl_panelGridLogin);

		JLabel lblUsuarioLogin = new JLabel("Usuario :");
		GridBagConstraints gbc_lblUsuarioLogin = new GridBagConstraints();
		gbc_lblUsuarioLogin.anchor = GridBagConstraints.EAST;
		gbc_lblUsuarioLogin.insets = new Insets(0, 0, 5, 5);
		gbc_lblUsuarioLogin.gridx = 1;
		gbc_lblUsuarioLogin.gridy = 2;
		panelGridLogin.add(lblUsuarioLogin, gbc_lblUsuarioLogin);

		txtFieldUsuarioLogin = new JTextField();
		txtFieldUsuarioLogin.setColumns(10);
		GridBagConstraints gbc_textFieldUsuarioLogin = new GridBagConstraints();
		gbc_textFieldUsuarioLogin.gridwidth = 2;
		gbc_textFieldUsuarioLogin.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldUsuarioLogin.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldUsuarioLogin.gridx = 2;
		gbc_textFieldUsuarioLogin.gridy = 2;
		panelGridLogin.add(txtFieldUsuarioLogin, gbc_textFieldUsuarioLogin);

		JLabel lblpasswordLogin = new JLabel("Contrase\u00F1a :");
		GridBagConstraints gbc_lblpasswordLogin = new GridBagConstraints();
		gbc_lblpasswordLogin.anchor = GridBagConstraints.EAST;
		gbc_lblpasswordLogin.insets = new Insets(0, 0, 5, 5);
		gbc_lblpasswordLogin.gridx = 1;
		gbc_lblpasswordLogin.gridy = 3;
		panelGridLogin.add(lblpasswordLogin, gbc_lblpasswordLogin);

		txtpasswordFieldLogin = new JPasswordField();
		txtpasswordFieldLogin.setEchoChar('*');
		GridBagConstraints gbc_passwordFieldLogin = new GridBagConstraints();
		gbc_passwordFieldLogin.insets = new Insets(0, 0, 5, 5);
		gbc_passwordFieldLogin.fill = GridBagConstraints.HORIZONTAL;
		gbc_passwordFieldLogin.gridx = 2;
		gbc_passwordFieldLogin.gridy = 3;
		panelGridLogin.add(txtpasswordFieldLogin, gbc_passwordFieldLogin);

		JRadioButton rdbtnLoginCheck = new JRadioButton("Ver");
		rdbtnLoginCheck.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (rdbtnLoginCheck.isSelected()) {
					txtpasswordFieldLogin.setEchoChar((char) 0); // password = JPasswordField
				} else {
					txtpasswordFieldLogin.setEchoChar('*');
				}
			}
		});
		rdbtnLoginCheck.setFont(new Font("Gentium Book Basic", Font.PLAIN, 14));
		GridBagConstraints gbc_rdbtnLoginCheck = new GridBagConstraints();
		gbc_rdbtnLoginCheck.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnLoginCheck.gridx = 3;
		gbc_rdbtnLoginCheck.gridy = 3;
		panelGridLogin.add(rdbtnLoginCheck, gbc_rdbtnLoginCheck);

		JPanel panelSignUp = new JPanel();
		panel_CardLayout.add(panelSignUp, "panelSignUp");
		panelSignUp.setLayout(new BorderLayout(0, 0));

		JPanel panelGridSignUp = new JPanel();
		panelGridSignUp.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelSignUp.add(panelGridSignUp, BorderLayout.CENTER);
		GridBagLayout gbl_panelGridSignUp = new GridBagLayout();
		gbl_panelGridSignUp.columnWidths = new int[] { 0, 206, 97, 195, 85, 10, 0 };
		gbl_panelGridSignUp.rowHeights = new int[] { 0, 0, 0, 0, -17, 0, 30, 0, 0, 28, 0 };
		gbl_panelGridSignUp.columnWeights = new double[] { 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_panelGridSignUp.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		panelGridSignUp.setLayout(gbl_panelGridSignUp);

		lblUsuarioSignUp = new JLabel("Usuario :");
		GridBagConstraints gbc_lblNewLabelSignUp = new GridBagConstraints();
		gbc_lblNewLabelSignUp.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabelSignUp.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabelSignUp.gridx = 0;
		gbc_lblNewLabelSignUp.gridy = 0;
		panelGridSignUp.add(lblUsuarioSignUp, gbc_lblNewLabelSignUp);

		txtUsuarioSignUp = new JTextField();
		txtUsuarioSignUp.setColumns(10);
		GridBagConstraints gbc_textFieldSignUp = new GridBagConstraints();
		gbc_textFieldSignUp.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldSignUp.gridwidth = 4;
		gbc_textFieldSignUp.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldSignUp.gridx = 1;
		gbc_textFieldSignUp.gridy = 0;
		panelGridSignUp.add(txtUsuarioSignUp, gbc_textFieldSignUp);

		lblNombreSignUp = new JLabel("Nombre :");
		GridBagConstraints gbc_lblNewLabelSignUp_1 = new GridBagConstraints();
		gbc_lblNewLabelSignUp_1.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabelSignUp_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabelSignUp_1.gridx = 0;
		gbc_lblNewLabelSignUp_1.gridy = 1;
		panelGridSignUp.add(lblNombreSignUp, gbc_lblNewLabelSignUp_1);

		txtNombreSignUp = new JTextField();
		txtNombreSignUp.setColumns(10);
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.gridwidth = 4;
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 1;
		panelGridSignUp.add(txtNombreSignUp, gbc_textField);

		lblApellidosSignUp = new JLabel("Apellidos :");
		GridBagConstraints gbc_lblUsuario = new GridBagConstraints();
		gbc_lblUsuario.insets = new Insets(0, 0, 5, 5);
		gbc_lblUsuario.anchor = GridBagConstraints.EAST;
		gbc_lblUsuario.gridx = 0;
		gbc_lblUsuario.gridy = 2;
		panelGridSignUp.add(lblApellidosSignUp, gbc_lblUsuario);

		txtApellidosSignUp = new JTextField();
		txtApellidosSignUp.setColumns(10);
		GridBagConstraints gbc_textFieldSignUp_1 = new GridBagConstraints();
		gbc_textFieldSignUp_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldSignUp_1.gridwidth = 4;
		gbc_textFieldSignUp_1.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldSignUp_1.gridx = 1;
		gbc_textFieldSignUp_1.gridy = 2;
		panelGridSignUp.add(txtApellidosSignUp, gbc_textFieldSignUp_1);

		lblEmailSignUp = new JLabel("Email :");
		GridBagConstraints gbc_lblNewLabelSignUp_2 = new GridBagConstraints();
		gbc_lblNewLabelSignUp_2.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabelSignUp_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabelSignUp_2.gridx = 0;
		gbc_lblNewLabelSignUp_2.gridy = 3;
		panelGridSignUp.add(lblEmailSignUp, gbc_lblNewLabelSignUp_2);

		txtEmailSignUp = new JTextField();
		txtEmailSignUp.setColumns(10);
		GridBagConstraints gbc_textFieldSignUp_2 = new GridBagConstraints();
		gbc_textFieldSignUp_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldSignUp_2.gridwidth = 4;
		gbc_textFieldSignUp_2.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldSignUp_2.gridx = 1;
		gbc_textFieldSignUp_2.gridy = 3;
		panelGridSignUp.add(txtEmailSignUp, gbc_textFieldSignUp_2);

		lblDescripcionSignUp = new JLabel("Descripcion :");
		GridBagConstraints gbc_lblPhotoLogin = new GridBagConstraints();
		gbc_lblPhotoLogin.anchor = GridBagConstraints.EAST;
		gbc_lblPhotoLogin.insets = new Insets(0, 0, 5, 5);
		gbc_lblPhotoLogin.gridx = 0;
		gbc_lblPhotoLogin.gridy = 4;
		panelGridSignUp.add(lblDescripcionSignUp, gbc_lblPhotoLogin);

		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridwidth = 4;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.gridx = 1;
		gbc_scrollPane.gridy = 4;
		panelGridSignUp.add(scrollPane, gbc_scrollPane);

		txtDescripcionSignUp = new JTextArea();
		txtDescripcionSignUp.setLineWrap(true);
		txtDescripcionSignUp.setFont(new Font("Tahoma", Font.PLAIN, 11));
		scrollPane.setViewportView(txtDescripcionSignUp);

		lblFechaNacimientoSignUp = new JLabel("Fecha de nacimiento :");
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_2.fill = GridBagConstraints.VERTICAL;
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2.gridx = 0;
		gbc_lblNewLabel_2.gridy = 6;
		panelGridSignUp.add(lblFechaNacimientoSignUp, gbc_lblNewLabel_2);

		txtFechaNacimientoSignUp = new JDateChooser();
		txtFechaNacimientoSignUp.getCalendarButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		GridBagConstraints gbc_dateChooser = new GridBagConstraints();
		gbc_dateChooser.gridheight = 3;
		gbc_dateChooser.insets = new Insets(0, 0, 5, 5);
		gbc_dateChooser.fill = GridBagConstraints.BOTH;
		gbc_dateChooser.gridx = 1;
		gbc_dateChooser.gridy = 5;
		panelGridSignUp.add(txtFechaNacimientoSignUp, gbc_dateChooser);

		JLabel lblPhotoLogin = new JLabel("Foto de perfil :");
		gbc_lblPhotoLogin = new GridBagConstraints();
		gbc_lblPhotoLogin.anchor = GridBagConstraints.EAST;
		gbc_lblPhotoLogin.insets = new Insets(0, 0, 5, 5);
		gbc_lblPhotoLogin.gridx = 2;
		gbc_lblPhotoLogin.gridy = 6;
		panelGridSignUp.add(lblPhotoLogin, gbc_lblPhotoLogin);

		DragAndDrop = new JEditorPane();
		DragAndDrop.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					PhotoLogin = new File(fileChooser.getSelectedFile().getAbsolutePath());
					int i = PhotoLogin.getAbsolutePath().lastIndexOf('.');
					String extension = PhotoLogin.getAbsolutePath().substring(i + 1);
					if (extension.equals("png") || extension.equals("jpg")) {
						lblPhotoLoginError.setVisible(false);
						DragAndDrop.setText("<html><img src=\"file:\\" + PhotoLogin.getAbsolutePath() + "\" width=200 height=200></img>");
					} else {
						lblPhotoLoginError.setVisible(true);
						PhotoLogin = new File(VentanaLoginRegister.class.getResource("/imagenes/usuario.png").getPath());
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
					PhotoLogin = droppedFiles.get(0);
					int i = PhotoLogin.getAbsolutePath().lastIndexOf('.');
					String extension = "";
					if (i > 0) {
						extension = PhotoLogin.getAbsolutePath().substring(i + 1);
					}
					if (extension.equals("png") || extension.equals("jpg")) {
						lblPhotoLoginError.setVisible(false);
						DragAndDrop.setText("<html><img src=\"file:\\" + PhotoLogin.getAbsolutePath()
								+ "\" width=200 height=200></img>");
					} else {
						lblPhotoLoginError.setVisible(true);
						PhotoLogin = new File(VentanaLoginRegister.class.getResource("/imagenes/usuario.png").getPath());
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		GridBagConstraints gbc_DragAndDrop = new GridBagConstraints();
		gbc_DragAndDrop.gridwidth = 2;
		gbc_DragAndDrop.gridheight = 3;
		gbc_DragAndDrop.fill = GridBagConstraints.BOTH;
		gbc_DragAndDrop.insets = new Insets(0, 0, 5, 5);
		gbc_DragAndDrop.gridx = 3;
		gbc_DragAndDrop.gridy = 5;
		panelGridSignUp.add(DragAndDrop, gbc_DragAndDrop);

		JButton btnNewButton = new JButton("Borrar Foto");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				PhotoLogin = new File(VentanaLoginRegister.class.getResource("/imagenes/usuario.png").getPath());
				DragAndDrop.setText("<html><body style='text-align: center;'><img src=\"file:\\" + ruta.getAbsolutePath() + "\" width=120 height=120></body></img>");
			}
		});
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton.gridx = 2;
		gbc_btnNewButton.gridy = 7;
		panelGridSignUp.add(btnNewButton, gbc_btnNewButton);

		lblPhotoLoginError = new JLabel("SOLO SE PUEDEN SUBIR FOTOS .PNG O .JPG");
		lblPhotoLoginError.setForeground(Color.RED);
		lblPhotoLoginError.setVisible(false);
		GridBagConstraints gbc_lbEdad = new GridBagConstraints();
		gbc_lbEdad.gridwidth = 2;
		gbc_lbEdad.insets = new Insets(0, 0, 5, 5);
		gbc_lbEdad.gridx = 3;
		gbc_lbEdad.gridy = 8;
		panelGridSignUp.add(lblPhotoLoginError, gbc_lbEdad);

		lblPasswordSignUp = new JLabel("Contrase\u00F1a :");
		GridBagConstraints gbc_lblNewLabelSignUp_4 = new GridBagConstraints();
		gbc_lblNewLabelSignUp_4.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabelSignUp_4.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabelSignUp_4.gridx = 0;
		gbc_lblNewLabelSignUp_4.gridy = 9;
		panelGridSignUp.add(lblPasswordSignUp, gbc_lblNewLabelSignUp_4);

		txtPasswordSignUp = new JPasswordField();
		txtPasswordSignUp.setEchoChar('*');
		GridBagConstraints gbc_passwordFieldSignUp = new GridBagConstraints();
		gbc_passwordFieldSignUp.gridwidth = 3;
		gbc_passwordFieldSignUp.fill = GridBagConstraints.HORIZONTAL;
		gbc_passwordFieldSignUp.insets = new Insets(0, 0, 0, 5);
		gbc_passwordFieldSignUp.gridx = 1;
		gbc_passwordFieldSignUp.gridy = 9;
		panelGridSignUp.add(txtPasswordSignUp, gbc_passwordFieldSignUp);

		JPanel panelSurSignUp = new JPanel();
		panelSurSignUp.setToolTipText("");
		panelSurSignUp.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelSignUp.add(panelSurSignUp, BorderLayout.SOUTH);
		GridBagLayout gbl_panelSurSignUp = new GridBagLayout();
		gbl_panelSurSignUp.columnWidths = new int[] { 12, 131, 137, 0, 10, 0 };
		gbl_panelSurSignUp.rowHeights = new int[] { 0, 0, 0, 0, 41, 0 };
		gbl_panelSurSignUp.columnWeights = new double[] { 1.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE };
		gbl_panelSurSignUp.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panelSurSignUp.setLayout(gbl_panelSurSignUp);

		lblCampoError = new JLabel("FALTAN CAMPOS POR RELLENAR");
		lblCampoError.setForeground(Color.RED);
		lblCampoError.setVisible(false);

		GridBagConstraints gbc_lblNewLabelError1 = new GridBagConstraints();
		gbc_lblNewLabelError1.gridwidth = 2;
		gbc_lblNewLabelError1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabelError1.gridx = 1;
		gbc_lblNewLabelError1.gridy = 0;
		panelSurSignUp.add(lblCampoError, gbc_lblNewLabelError1);
		gbc_lblUsuario.gridwidth = 2;
		gbc_lblUsuario.insets = new Insets(0, 0, 5, 5);
		gbc_lblUsuario.gridx = 1;
		gbc_lblUsuario.gridy = 1;

		JRadioButton rdbtnNewRadioButtonSignUp = new JRadioButton("Ver");
		rdbtnNewRadioButtonSignUp.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (rdbtnNewRadioButtonSignUp.isSelected()) {
					txtPasswordSignUp.setEchoChar((char) 0); // password = JPasswordField
				} else {
					txtPasswordSignUp.setEchoChar('*');
				}
			}
		});
		rdbtnNewRadioButtonSignUp.setFont(new Font("Gentium Book Basic", Font.PLAIN, 14));
		GridBagConstraints gbc_rdbtnNewRadioButtonSignUp = new GridBagConstraints();
		gbc_rdbtnNewRadioButtonSignUp.fill = GridBagConstraints.HORIZONTAL;
		gbc_rdbtnNewRadioButtonSignUp.insets = new Insets(0, 0, 0, 5);
		gbc_rdbtnNewRadioButtonSignUp.gridx = 4;
		gbc_rdbtnNewRadioButtonSignUp.gridy = 9;
		panelGridSignUp.add(rdbtnNewRadioButtonSignUp, gbc_rdbtnNewRadioButtonSignUp);

		lblUsuarioError = new JLabel("ESE NOMBRE DE USUARIO YA EST\u00C1 REGISTRADO");
		lblUsuarioError.setForeground(Color.RED);
		lblUsuarioError.setVisible(false);
		GridBagConstraints gbc_lblUsuarioError = new GridBagConstraints();
		gbc_lblUsuarioError.gridwidth = 2;
		gbc_lblUsuarioError.insets = new Insets(0, 0, 5, 5);
		gbc_lblUsuarioError.gridx = 1;
		gbc_lblUsuarioError.gridy = 1;
		panelSurSignUp.add(lblUsuarioError, gbc_lblUsuarioError);

		btnRegistrarSignUp = new JButton("Crear cuenta");
		btnRegistrarSignUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnRegistrarSignUp.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				boolean OK = false;
				OK = comprobacion();
				if (OK) {
					boolean registrado = false;
					registrado = Controlador.getUnicaInstancia().registrarUsuario(txtNombreSignUp.getText(),
							txtApellidosSignUp.getText(), txtEmailSignUp.getText(), txtUsuarioSignUp.getText(),
							new String(txtPasswordSignUp.getPassword()), txtFechaNacimientoSignUp.getDate().toString(),
							txtDescripcionSignUp.getText(), PhotoLogin.getPath());
					if (registrado) {
						CardLayout cl = (CardLayout) (panel_CardLayout.getLayout());
						cl.show(panel_CardLayout, "panelLogin");
						JOptionPane.showMessageDialog(frmLoginSignUp, "Usuario registrado correctamente.", "Registro",
								JOptionPane.INFORMATION_MESSAGE);
						vaciarTextosSignUp();
						ocultarErrores();
					} else {
						JOptionPane.showMessageDialog(frmLoginSignUp, "No se ha podido llevar a cabo el registro.\n",
								"Registro", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		
		
		
		lblDescripcionError = new JLabel("LA DESCRIPCIÓN NO DEBE TENER MAS DE 200 CARÁCTERES");
		lblDescripcionError.setVisible(false);
		lblDescripcionError.setForeground(Color.RED);
		GridBagConstraints gbc_lblDescripcionError = new GridBagConstraints();
		gbc_lblDescripcionError.gridwidth = 2;
		gbc_lblDescripcionError.insets = new Insets(0, 0, 5, 5);
		gbc_lblDescripcionError.gridx = 1;
		gbc_lblDescripcionError.gridy = 2;
		panelSurSignUp.add(lblDescripcionError, gbc_lblDescripcionError);
		
		lblEdadError = new JLabel("NO SE ADMITEN MENORES DE EDAD");
		lblEdadError.setVisible(false);
		lblEdadError.setForeground(Color.RED);
		GridBagConstraints gbc_lblEdadError;
		gbc_lblEdadError = new GridBagConstraints();
		gbc_lblEdadError.gridwidth = 2;
		gbc_lblEdadError.insets = new Insets(0, 0, 5, 5);
		gbc_lblEdadError.gridx = 1;
		gbc_lblEdadError.gridy = 3;
		panelSurSignUp.add(lblEdadError, gbc_lblEdadError);
		btnRegistrarSignUp.setIcon(new ImageIcon(VentanaLoginRegister.class.getResource("/imagenes/signup.png")));
		GridBagConstraints gbc_btnLogin_1_1 = new GridBagConstraints();
		gbc_btnLogin_1_1.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnLogin_1_1.insets = new Insets(0, 0, 0, 5);
		gbc_btnLogin_1_1.gridx = 1;
		gbc_btnLogin_1_1.gridy = 4;
		panelSurSignUp.add(btnRegistrarSignUp, gbc_btnLogin_1_1);

		btnCancelarSignUp = new JButton("Volver al Login");
		btnCancelarSignUp.setIcon(new ImageIcon(VentanaLoginRegister.class.getResource("/imagenes/login.png")));
		GridBagConstraints gbc_btnNewButton_1_1 = new GridBagConstraints();
		gbc_btnNewButton_1_1.insets = new Insets(0, 0, 0, 5);
		gbc_btnNewButton_1_1.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnNewButton_1_1.gridx = 2;
		gbc_btnNewButton_1_1.gridy = 4;
		panelSurSignUp.add(btnCancelarSignUp, gbc_btnNewButton_1_1);
		btnCancelarSignUp.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				CardLayout cl = (CardLayout) (panel_CardLayout.getLayout());
				cl.show(panel_CardLayout, "panelLogin");
				vaciarTextosSignUp();
				ocultarErrores();
			}
		});

		JPanel panelNorteSignUp = new JPanel();
		panelSignUp.add(panelNorteSignUp, BorderLayout.NORTH);
		panelNorteSignUp.setLayout(new BorderLayout(0, 0));

		JLabel lblNewLabel_6_1_1 = new JLabel("Aqu\u00ED puedes crear tu cuenta de PhotoTDS");
		lblNewLabel_6_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_6_1_1.setFont(new Font("Gentium Book Basic", Font.PLAIN, 15));
		panelNorteSignUp.add(lblNewLabel_6_1_1, BorderLayout.SOUTH);

		JLabel lblImagen_1_1 = new JLabel("");
		lblImagen_1_1.setIcon(new ImageIcon(VentanaLoginRegister.class.getResource("/imagenes/camarax64.png")));
		lblImagen_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		panelNorteSignUp.add(lblImagen_1_1, BorderLayout.CENTER);

		JLabel lblBienvenido_1_1 = new JLabel("Bienvenido a PhotoTDS\r\n");
		lblBienvenido_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblBienvenido_1_1.setFont(new Font("Gentium Book Basic", Font.PLAIN, 20));
		panelNorteSignUp.add(lblBienvenido_1_1, BorderLayout.NORTH);

		JMenuBar menuBar = new JMenuBar();
		frmLoginSignUp.setJMenuBar(menuBar);

		JRadioButtonMenuItem rdbtnmntmNewRadioItem = new JRadioButtonMenuItem("Modo nocturno");
		rdbtnmntmNewRadioItem.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (rdbtnmntmNewRadioItem.isSelected()) {
					try {
						UIManager.setLookAndFeel("com.jtattoo.plaf.noire.NoireLookAndFeel");
					} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
							| UnsupportedLookAndFeelException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					SwingUtilities.updateComponentTreeUI(frmLoginSignUp);
				} else {
					try {
						UIManager.setLookAndFeel("com.jtattoo.plaf.fast.FastLookAndFeel");
					} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
							| UnsupportedLookAndFeelException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					SwingUtilities.updateComponentTreeUI(frmLoginSignUp);
				}
			}
		});
		rdbtnmntmNewRadioItem.setFont(new Font("Gentium Book Basic", Font.PLAIN, 14));
		menuBar.add(rdbtnmntmNewRadioItem);

		ocultarErrores();
	}

	private boolean comprobacion() {
		boolean salida = true;
		ocultarErrores();
		if (txtNombreSignUp.getText().trim().isEmpty()) {
			lblCampoError.setVisible(true);
			lblNombreSignUp.setForeground(Color.RED);
			txtNombreSignUp.setBorder(BorderFactory.createLineBorder(Color.RED));
			salida = false;
		}
		if (txtApellidosSignUp.getText().trim().isEmpty()) {
			lblCampoError.setVisible(true);
			lblApellidosSignUp.setForeground(Color.RED);
			txtApellidosSignUp.setBorder(BorderFactory.createLineBorder(Color.RED));
			salida = false;
		}
		if (txtEmailSignUp.getText().trim().isEmpty()) {
			lblCampoError.setVisible(true);
			lblEmailSignUp.setForeground(Color.RED);
			txtEmailSignUp.setBorder(BorderFactory.createLineBorder(Color.RED));
			salida = false;
		}
		if (txtUsuarioSignUp.getText().trim().isEmpty()) {
			lblCampoError.setVisible(true);
			lblUsuarioSignUp.setForeground(Color.RED);
			txtUsuarioSignUp.setBorder(BorderFactory.createLineBorder(Color.RED));
			salida = false;
		}
		if (txtDescripcionSignUp.getText().trim().length() > 200 ) {
			lblDescripcionError.setVisible(true);
			lblDescripcionSignUp.setForeground(Color.RED);
			txtDescripcionSignUp.setBorder(BorderFactory.createLineBorder(Color.RED));
		}
		String password = new String(txtPasswordSignUp.getPassword());
		if (password.isEmpty()) {
			lblCampoError.setVisible(true);
			lblPasswordSignUp.setForeground(Color.RED);
			txtPasswordSignUp.setBorder(BorderFactory.createLineBorder(Color.RED));
			salida = false;
		}
		if (!lblUsuarioError.getText().isEmpty()
				&& Controlador.getUnicaInstancia().esUsuarioRegistrado(txtUsuarioSignUp.getText())) {
			lblUsuarioError.setText("Ya existe ese usuario");
			lblUsuarioError.setVisible(true);
			lblUsuarioSignUp.setForeground(Color.RED);
			txtUsuarioSignUp.setBorder(BorderFactory.createLineBorder(Color.RED));
			salida = false;
		}
		if (txtFechaNacimientoSignUp.getDate() == null) {
			lblCampoError.setVisible(true);
			lblFechaNacimientoSignUp.setForeground(Color.RED);
			txtFechaNacimientoSignUp.setBorder(BorderFactory.createLineBorder(Color.RED));
			salida = false;
		}
		if (Period.between(txtFechaNacimientoSignUp.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), LocalDate.now()).getYears() <= 18) {
			lblEdadError.setVisible(true);
			lblFechaNacimientoSignUp.setForeground(Color.RED);
			txtFechaNacimientoSignUp.setBorder(BorderFactory.createLineBorder(Color.RED));
			salida = false;
		}
		
		frmLoginSignUp.revalidate();
		frmLoginSignUp.pack();
		return salida;
	}

	private void ocultarErrores() {

		lblEdadError.setVisible(false);
		lblCampoError.setVisible(false);
		lblPhotoLoginError.setVisible(false);
		lblDescripcionError.setVisible(false);
		lblNombreSignUp.setForeground(new JLabel().getForeground());
		txtNombreSignUp.setBorder(new JTextField().getBorder());
		lblApellidosSignUp.setForeground(new JLabel().getForeground());
		txtApellidosSignUp.setBorder(new JTextField().getBorder());
		lblEmailSignUp.setForeground(new JLabel().getForeground());
		txtEmailSignUp.setBorder(new JTextField().getBorder());
		lblDescripcionSignUp.setForeground(new JLabel().getForeground());
		txtDescripcionSignUp.setBorder(new JTextField().getBorder());
		lblUsuarioSignUp.setForeground(new JLabel().getForeground());
		txtUsuarioSignUp.setBorder(new JTextField().getBorder());
		lblPasswordSignUp.setForeground(new JLabel().getForeground());
		txtPasswordSignUp.setBorder(new JTextField().getBorder());
		lblFechaNacimientoSignUp.setForeground(new JLabel().getForeground());
		txtFechaNacimientoSignUp.setBorder(new JTextField().getBorder());
	}

	private void vaciarTextosSignUp() {

		txtNombreSignUp.setText(null);
		txtApellidosSignUp.setText(null);
		txtFechaNacimientoSignUp.setDate(null);
		DragAndDrop.setText("<html><body style='text-align: center;'><img src=\"file:\\" + ruta.getAbsolutePath() + "\" width=120 height=120></body></img>");
		txtEmailSignUp.setText(null);
		txtUsuarioSignUp.setText(null);
		txtDescripcionSignUp.setText(null);
		txtPasswordSignUp.setText(null);

	}

	private void vaciarTextosLogin() {

		txtFieldUsuarioLogin.setText(null);
		txtpasswordFieldLogin.setText(null);

	}

}