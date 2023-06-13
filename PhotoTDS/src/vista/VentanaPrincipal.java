package vista;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import DAO.DAOException;
import controlador.Controlador;
import modelo.Album;
import modelo.Foto;
import modelo.Publicacion;
import modelo.Usuario;
import umu.tds.fotos.CargadorFotos;

import java.awt.Toolkit;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import javax.swing.JList;
import javax.swing.JOptionPane;

import pulsador.Luz;
import pulsador.IEncendidoListener;

import java.util.Arrays;
import java.util.EventObject;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.GridLayout;
import java.awt.Image;

public class VentanaPrincipal{
	
	private JFrame frmVentanaPrincipal;
	private CargadorFotos cargador;
	private JTextField searchText;

	public VentanaPrincipal() {
		initialize();
	}
	
	public void mostrarVentana() {
		frmVentanaPrincipal.setLocationRelativeTo(null);
		frmVentanaPrincipal.setVisible(true);
	}

	public JFrame getFrmVentanaPrincipal() {
		return frmVentanaPrincipal;
	}

	public void initialize() {

		frmVentanaPrincipal = new JFrame();
		frmVentanaPrincipal.setSize(new Dimension(731, 546));
		frmVentanaPrincipal.setPreferredSize(new Dimension(700, 500));
		frmVentanaPrincipal.setIconImage(
				Toolkit.getDefaultToolkit().getImage(VentanaPrincipal.class.getResource("/imagenes/camarax32.png")));
		frmVentanaPrincipal.setTitle("PhotoTDS");
		frmVentanaPrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		cargador = new umu.tds.fotos.CargadorFotos();

		//System.out.println(usuario.toString());

		cargador.addOyente(Controlador.getUnicaInstancia());

		JPanel contentPane = (JPanel) frmVentanaPrincipal.getContentPane();
		contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		contentPane.setLayout(new BorderLayout());

		JPanel panel = new JPanel();
		frmVentanaPrincipal.getContentPane().add(panel, BorderLayout.NORTH);
		panel.setLayout(new BorderLayout(0, 0));

		JPanel panelSuperior = new JPanel();
		panel.add(panelSuperior, BorderLayout.NORTH);
		GridBagLayout gbl_panelSuperior = new GridBagLayout();
		gbl_panelSuperior.columnWidths = new int[] { 25, 64, 30, 53, 0, 100, 0, 30, 20, 0, 30, 25, 0 };
		gbl_panelSuperior.rowHeights = new int[] { 64, 0 };
		gbl_panelSuperior.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_panelSuperior.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		panelSuperior.setLayout(gbl_panelSuperior);

		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setIcon(new ImageIcon(VentanaPrincipal.class.getResource("/imagenes/camarax64.png")));
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel.gridx = 1;
		gbc_lblNewLabel.gridy = 0;
		panelSuperior.add(lblNewLabel, gbc_lblNewLabel);

		JButton btnFotos = new JButton("");
		btnFotos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					String ruta = fileChooser.getSelectedFile().getAbsolutePath();
					int i = ruta.lastIndexOf('.');
					String extension = "";
					if (i > 0) {
						extension = ruta.substring(i + 1);
					}
					if (extension.equals("png") || extension.equals("jpg")) {
						VentanaFotos ventanaFotos = new VentanaFotos(frmVentanaPrincipal, ruta);
						ventanaFotos.getFrmFotos().setVisible(true);
						frmVentanaPrincipal.dispose();
					} else {
						JOptionPane.showMessageDialog(frmVentanaPrincipal, "Formato incorrecto. Solo se admite fotos con formato PNG o JPG",
								"Formato incorrecto", JOptionPane.INFORMATION_MESSAGE);
					}
				}
			}
		});
		
		JButton btnAlbum = new JButton("");
		btnAlbum.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				VentanaAlbumes ventanaAlbumes = new VentanaAlbumes(new LinkedList<Foto>(), Controlador.getUnicaInstancia().getUsuarioActual());
				ventanaAlbumes.getfrmAlbumes().setVisible(true);
				frmVentanaPrincipal.dispose();
			}
		});
		btnAlbum.setIcon(new ImageIcon(VentanaPrincipal.class.getResource("/imagenes/album.png")));
		GridBagConstraints gbc_btnAlbum = new GridBagConstraints();
		gbc_btnAlbum.insets = new Insets(0, 0, 0, 5);
		gbc_btnAlbum.gridx = 3;
		gbc_btnAlbum.gridy = 0;
		panelSuperior.add(btnAlbum, gbc_btnAlbum);

		btnFotos.setIcon(new ImageIcon(VentanaPrincipal.class.getResource("/imagenes/upload.png")));
		GridBagConstraints gbc_btnFotos = new GridBagConstraints();
		gbc_btnFotos.insets = new Insets(0, 0, 0, 5);
		gbc_btnFotos.gridx = 4;
		gbc_btnFotos.gridy = 0;
		panelSuperior.add(btnFotos, gbc_btnFotos);

		searchText = new JTextField();
		GridBagConstraints gbc_searchText = new GridBagConstraints();
		gbc_searchText.fill = GridBagConstraints.HORIZONTAL;
		gbc_searchText.insets = new Insets(0, 0, 0, 5);
		gbc_searchText.gridx = 5;
		gbc_searchText.gridy = 0;
		panelSuperior.add(searchText, gbc_searchText);
		searchText.setColumns(10);

		JButton btnNewButton_1 = new JButton("");
		btnNewButton_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String busqueda = searchText.getText();
				realizarBusqueda(busqueda);
			}
		});
		btnNewButton_1.setIcon(new ImageIcon(VentanaPrincipal.class.getResource("/imagenes/search.png")));
		GridBagConstraints gbc_btnNewButton_1 = new GridBagConstraints();
		gbc_btnNewButton_1.insets = new Insets(0, 0, 0, 5);
		gbc_btnNewButton_1.gridx = 6;
		gbc_btnNewButton_1.gridy = 0;
		panelSuperior.add(btnNewButton_1, gbc_btnNewButton_1);

		Luz luz = new Luz();
		luz.addEncendidoListener(new IEncendidoListener() {
			public void enteradoCambioEncendido(EventObject arg0) {
				JFileChooser fileChooser = new JFileChooser();
				if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					String ruta = fileChooser.getSelectedFile().getAbsolutePath();
					if (Controlador.getUnicaInstancia().cargarFotos(ruta)) {
						VentanaPrincipal ventanaRecargada = new VentanaPrincipal();
						ventanaRecargada.mostrarVentana();
						JOptionPane.showMessageDialog(ventanaRecargada.getFrmVentanaPrincipal(),
								"Fotos subidas correctamente por el cargador.",
								"Fotos subidas", JOptionPane.INFORMATION_MESSAGE);
						frmVentanaPrincipal.dispose();
					} else  {
						JOptionPane.showMessageDialog(frmVentanaPrincipal, "Formato incorrecto.",
								"Formato incorrecto", JOptionPane.INFORMATION_MESSAGE);
					}
					
				}
			}
		});
		GridBagConstraints gbc_luz = new GridBagConstraints();
		gbc_luz.insets = new Insets(0, 0, 0, 5);
		gbc_luz.gridx = 7;
		gbc_luz.gridy = 0;
		panelSuperior.add(luz, gbc_luz);


		JLabel lblNewLabel_1 = new JLabel(); //	
		ImageIcon imagen = new ImageIcon(Controlador.getUnicaInstancia().getPhotoUsr(Controlador.getUnicaInstancia().getUsuarioActual()));
		Image imagenACambiar = imagen.getImage();
		ImageIcon icono= new ImageIcon(imagenACambiar.getScaledInstance(70, 70,  java.awt.Image.SCALE_SMOOTH)); 
		lblNewLabel_1.setIcon(icono);
		JPopupMenu popupMenu = new JPopupMenu();
		addPopup(lblNewLabel_1, popupMenu);

		JButton btnNewButton_2 = new JButton("Perfil");
		btnNewButton_2.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		btnNewButton_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				VentanaPerfil profileWindow = new VentanaPerfil(Controlador.getUnicaInstancia().getUsuarioActual());
				profileWindow.mostrarVentana();
				frmVentanaPrincipal.dispose();
			}
		});
		popupMenu.add(btnNewButton_2);

		JButton btnNewButton_3 = new JButton("Cerrar sesion");
		btnNewButton_3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				VentanaLoginRegister ventanaLogin = new VentanaLoginRegister();
				ventanaLogin.mostrarVentana();
				frmVentanaPrincipal.dispose();
			}
		});
		
		JLabel lblNewLabel_notificacion = new JLabel();
		lblNewLabel_notificacion.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				VentanaNotificaciones ventanaNotif = new VentanaNotificaciones(frmVentanaPrincipal);
				ventanaNotif.mostrarVentana();
				frmVentanaPrincipal.setEnabled(false);
			}
		});
		if (Controlador.getUnicaInstancia().comprobarNotificaciones()) {
			lblNewLabel_notificacion.setIcon(new ImageIcon(VentanaPrincipal.class.getResource("/imagenes/notificacion_activa.png")));
		} else {
			lblNewLabel_notificacion.setIcon(new ImageIcon(VentanaPrincipal.class.getResource("/imagenes/notificacion.png")));
		}
			
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel_2.gridx = 9;
		gbc_lblNewLabel_2.gridy = 0;
		panelSuperior.add(lblNewLabel_notificacion, gbc_lblNewLabel_2);
		btnNewButton_3.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		popupMenu.add(btnNewButton_3);
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel_1.gridx = 10;
		gbc_lblNewLabel_1.gridy = 0;
		panelSuperior.add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		JPanel panelCentral = new JPanel();
		panel.add(panelCentral, BorderLayout.CENTER);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setPreferredSize(new Dimension(650, 360));
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setToolTipText("");
		
		panelCentral.add(scrollPane);
		
		JPanel panelFotos = new JPanel();
		scrollPane.setViewportView(panelFotos);
		panelFotos.setLayout(new GridLayout(0, 1, 0, 0));
		
		
		List<Foto> listaFotos = Controlador.getUnicaInstancia().ultimasFotos();
		for(Foto f: listaFotos) {
			PanelFoto pf = new PanelFoto(f, f.getAutor() , frmVentanaPrincipal);
			panelFotos.add(pf);
		}
		
		frmVentanaPrincipal.pack();

	}

	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					mouseClicked(e);
				}
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}

	private void realizarBusqueda(String busqueda) {
		if (!busqueda.isBlank()) {
			if (busqueda.startsWith("#")) {
				List<String> listaHashtag = parsearHashtag(busqueda);
				List<Publicacion> publicaciones = Controlador.getUnicaInstancia().obtenerPublicacionesPorHashtag(listaHashtag);
				if(publicaciones != null){
					DefaultListModel<Publicacion> listModel = new DefaultListModel<>();
					for (Publicacion p : publicaciones) {
						listModel.addElement(p);
					}
					JList<Publicacion> publicacionList = new JList<>(listModel);
					publicacionList.setCellRenderer(new PublicacionRenderer());
					JScrollPane pane = new JScrollPane(publicacionList);
					JFrame frameBusqueda = createFrame(publicaciones.size());
					publicacionList.addListSelectionListener(new ListSelectionListener(){
						@Override
						public void valueChanged(ListSelectionEvent e) {
							if(!e.getValueIsAdjusting()) {
								if(Controlador.getUnicaInstancia().isAlbum(publicacionList.getSelectedValue())) {
									VentanaAlbumPublicada ventanaPublic = new VentanaAlbumPublicada((Album)publicacionList.getSelectedValue());
									ventanaPublic.mostrarVentana();
									frameBusqueda.dispose();
									frmVentanaPrincipal.dispose();
								} else {
									VentanaFotoPublicada ventanaPublic = new VentanaFotoPublicada((Foto)publicacionList.getSelectedValue());
									ventanaPublic.mostrarVentana();
									frameBusqueda.dispose();
									frmVentanaPrincipal.dispose();
								}
							}
						}
					});
					frameBusqueda.getContentPane().add(pane);
					frameBusqueda.setLocationRelativeTo(null);
					frameBusqueda.setVisible(true);
				}
			} else {
				List<Usuario> userSearchList = new LinkedList<>();
				try {
					userSearchList = Controlador.getUnicaInstancia().buscarUsuarios(busqueda);
				} catch (DAOException e1) {
					e1.printStackTrace();
				}
				DefaultListModel<Usuario> listModel = new DefaultListModel<>();
				for (Usuario u : userSearchList) {
					listModel.addElement(u);
				}
				JList<Usuario> usuarioList = new JList<>(listModel);
				usuarioList.setCellRenderer(new UsuarioRenderer());
				JScrollPane pane = new JScrollPane(usuarioList);
				JFrame frameBusqueda = createFrame(userSearchList.size());
				usuarioList.addListSelectionListener(new ListSelectionListener() {
					@Override
					public void valueChanged(ListSelectionEvent e) {
						if (!e.getValueIsAdjusting()) {
								VentanaPerfil profileWindow = new VentanaPerfil(usuarioList.getSelectedValue());
								profileWindow.mostrarVentana();
								frameBusqueda.dispose();
								frmVentanaPrincipal.dispose();
						}
					}
				});
				frameBusqueda.getContentPane().add(pane);
				frameBusqueda.setLocationRelativeTo(null);
				frameBusqueda.setVisible(true);
			}

		}
	}
	
	private List<String> parsearHashtag(String busqueda){
		List<String> listaHashtag = new LinkedList<>();
		Pattern patron = Pattern.compile("\\#\\w+");
		Matcher matcher = patron.matcher(busqueda);
		while(matcher.find()) {
			String hashtag = matcher.group().substring(1);
			String[] subwords = hashtag.split("#");
			List<String> words = Arrays.asList(subwords);
			listaHashtag.addAll(words);
		}
		return listaHashtag;
	}

	private static JFrame createFrame(int resultados) {
		JFrame searchframe = new JFrame("Búsqueda");
		searchframe.setTitle(resultados + " resultados");
		if(resultados == 1) {
			searchframe.setTitle(resultados + " resultado");
		}
		searchframe.setIconImage(
				Toolkit.getDefaultToolkit().getImage(VentanaPrincipal.class.getResource("/imagenes/camarax32.png")));
		searchframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		searchframe.setSize(new Dimension(300, 300));
		return searchframe;
	}
	
	public void reconstruir(JFrame frame) {
		this.frmVentanaPrincipal = frame;
		this.frmVentanaPrincipal.pack();
	}
	
}