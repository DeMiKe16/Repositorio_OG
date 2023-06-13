package vista;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import com.itextpdf.text.DocumentException;

import controlador.Controlador;
import modelo.Foto;
import modelo.GeneradorExcel;
import modelo.GeneradorPDF;
import modelo.Publicacion;

import java.awt.Toolkit;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

public class VentanaPremium extends JFrame{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JFrame frmventanaPremium;
	private JPanel contentPane;
	private int precio;
	private JLabel lblTopLikes;
	private JFrame frmPerfil;
	
	/**
	 * Create the frame.
	 */
	public VentanaPremium(JFrame frmPerfil) {
		this.precio = 10;
		this.frmPerfil = frmPerfil;
		initialize();
	}
	
	public void mostrarVentana() {
		frmventanaPremium.setLocationRelativeTo(null);
		frmventanaPremium.setVisible(true);
	}
	
	private void initialize() {
		GeneradorExcel genE = new GeneradorExcel();
		GeneradorPDF genPdf = new GeneradorPDF();
		frmventanaPremium = new JFrame();
		frmventanaPremium.setIconImage(Toolkit.getDefaultToolkit().getImage(VentanaPremium.class.getResource("/imagenes/camarax32.png")));
		frmventanaPremium.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmventanaPremium.setBounds(100, 100, 367, 250);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		frmventanaPremium.setContentPane(contentPane);
		contentPane.setLayout(new CardLayout(0, 0));
				
		JPanel panelCompra = new JPanel();
		contentPane.add(panelCompra, "panelCompra");
		GridBagLayout gbl_panelCompra = new GridBagLayout();
		gbl_panelCompra.columnWidths = new int[]{15, 0, 70, 0, 15, 0};
		gbl_panelCompra.rowHeights = new int[]{15, 0, 5, 0, 0, 0, 0, 0, 0};
		gbl_panelCompra.columnWeights = new double[]{1.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_panelCompra.rowWeights = new double[]{0.0, 0.0, 1.0, 0.0, 0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE};
		panelCompra.setLayout(gbl_panelCompra);
		
		JLabel lblMensaje = new JLabel("Usted no es aún usuario premium");
		GridBagConstraints gbc_lblMensaje = new GridBagConstraints();
		gbc_lblMensaje.gridwidth = 3;
		gbc_lblMensaje.insets = new Insets(0, 0, 5, 5);
		gbc_lblMensaje.gridx = 1;
		gbc_lblMensaje.gridy = 1;
		panelCompra.add(lblMensaje, gbc_lblMensaje);
		
		JLabel lblOferta = new JLabel("¿Quiere pasar al plan premium por "+ precio + " € ?");
		GridBagConstraints gbc_lblOferta = new GridBagConstraints();
		gbc_lblOferta.gridwidth = 3;
		gbc_lblOferta.insets = new Insets(0, 0, 5, 5);
		gbc_lblOferta.gridx = 1;
		gbc_lblOferta.gridy = 3;
		panelCompra.add(lblOferta, gbc_lblOferta);
		
		JLabel lblDescuento = new JLabel("Descuento aplicado:");
		GridBagConstraints gbc_lblDescuento = new GridBagConstraints();
		gbc_lblDescuento.gridwidth = 3;
		gbc_lblDescuento.insets = new Insets(0, 0, 5, 5);
		gbc_lblDescuento.gridx = 1;
		gbc_lblDescuento.gridy = 4;
		panelCompra.add(lblDescuento, gbc_lblDescuento);
		
		JButton btnSI = new JButton("SI");
		btnSI.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Controlador.getUnicaInstancia().setPremium(Controlador.getUnicaInstancia().getUsuarioActual());
				CardLayout cl = (CardLayout) (contentPane.getLayout());
				cl.show(contentPane, "panelPriv");
				JOptionPane.showMessageDialog(frmventanaPremium,
						"Operación realizada con éxito.\nPrecio final " + precio + " €\n(Descuento edad: )\n(Descuento likes: ) ", "Registro",
						JOptionPane.INFORMATION_MESSAGE);
			}
		});
		GridBagConstraints gbc_btnSI = new GridBagConstraints();
		gbc_btnSI.anchor = GridBagConstraints.WEST;
		gbc_btnSI.insets = new Insets(0, 0, 5, 5);
		gbc_btnSI.gridx = 1;
		gbc_btnSI.gridy = 6;
		panelCompra.add(btnSI, gbc_btnSI);
		
		JButton btnNO = new JButton("NO");
		btnNO.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JOptionPane.showMessageDialog(frmventanaPremium,
						"La operación para ser usuario premium ha sido cancelada ", "Cancelación",
						JOptionPane.CLOSED_OPTION);
				frmventanaPremium.dispose();
				frmPerfil.setEnabled(true);				
			}
		});
		GridBagConstraints gbc_btnNO = new GridBagConstraints();
		gbc_btnNO.anchor = GridBagConstraints.EAST;
		gbc_btnNO.insets = new Insets(0, 0, 5, 5);
		gbc_btnNO.gridx = 3;
		gbc_btnNO.gridy = 6;
		panelCompra.add(btnNO, gbc_btnNO);
		
		JPanel panelPrivilegios = new JPanel();
		contentPane.add(panelPrivilegios, "panelPriv");
		GridBagLayout gbl_panelPrivilegios = new GridBagLayout();
		gbl_panelPrivilegios.columnWidths = new int[]{0, 0, 0, 0, 0, 0};
		gbl_panelPrivilegios.rowHeights = new int[]{10, 0, 0, 0, 0, 0, 10, 0};
		gbl_panelPrivilegios.columnWeights = new double[]{1.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_panelPrivilegios.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panelPrivilegios.setLayout(gbl_panelPrivilegios);
		
		JLabel labelPDF = new JLabel("Generar PDF de tus seguidores");
		GridBagConstraints gbc_labelPDF = new GridBagConstraints();
		gbc_labelPDF.anchor = GridBagConstraints.WEST;
		gbc_labelPDF.insets = new Insets(0, 0, 5, 5);
		gbc_labelPDF.gridx = 1;
		gbc_labelPDF.gridy = 1;
		panelPrivilegios.add(labelPDF, gbc_labelPDF);
		
		JButton btnPDF = new JButton("");
		btnPDF.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					genPdf.generar(Controlador.getUnicaInstancia().getSeguidoresUsuario(Controlador.getUnicaInstancia().getUsuarioActual()));
				} catch (FileNotFoundException | DocumentException e1) {
					e1.printStackTrace();
				} catch (MalformedURLException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnPDF.setIcon(new ImageIcon(VentanaPremium.class.getResource("/imagenes/pdf.png")));
		GridBagConstraints gbc_btnPDF = new GridBagConstraints();
		gbc_btnPDF.fill = GridBagConstraints.BOTH;
		gbc_btnPDF.insets = new Insets(0, 0, 5, 5);
		gbc_btnPDF.gridx = 3;
		gbc_btnPDF.gridy = 1;
		panelPrivilegios.add(btnPDF, gbc_btnPDF);
		
		JLabel lblExcel = new JLabel("Generar Excel de tus seguidores");
		GridBagConstraints gbc_lblExcel = new GridBagConstraints();
		gbc_lblExcel.anchor = GridBagConstraints.WEST;
		gbc_lblExcel.insets = new Insets(0, 0, 5, 5);
		gbc_lblExcel.gridx = 1;
		gbc_lblExcel.gridy = 3;
		panelPrivilegios.add(lblExcel, gbc_lblExcel);
		
		JButton btnExcel = new JButton("");
		btnExcel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					genE.generar(Controlador.getUnicaInstancia().getSeguidoresUsuario(Controlador.getUnicaInstancia().getUsuarioActual()));
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		btnExcel.setIcon(new ImageIcon(VentanaPremium.class.getResource("/imagenes/xlsx.png")));
		GridBagConstraints gbc_btnExcel = new GridBagConstraints();
		gbc_btnExcel.fill = GridBagConstraints.BOTH;
		gbc_btnExcel.insets = new Insets(0, 0, 5, 5);
		gbc_btnExcel.gridx = 3;
		gbc_btnExcel.gridy = 3;
		panelPrivilegios.add(btnExcel, gbc_btnExcel);
		
		lblTopLikes = new JLabel("Ver 10 fotos con más likes");
		GridBagConstraints gbc_lblTopLikes = new GridBagConstraints();
		gbc_lblTopLikes.anchor = GridBagConstraints.WEST;
		gbc_lblTopLikes.insets = new Insets(0, 0, 5, 5);
		gbc_lblTopLikes.gridx = 1;
		gbc_lblTopLikes.gridy = 5;
		panelPrivilegios.add(lblTopLikes, gbc_lblTopLikes);
		
		JButton btnTopLikes = new JButton("");
		btnTopLikes.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				List<Foto> fotos = Controlador.getUnicaInstancia().obtenerFotosMasLikes();
				if(fotos != null){
					DefaultListModel<Publicacion> listModel = new DefaultListModel<>();
					for (Foto f : fotos) {
						listModel.addElement(f);
					}
					JList<Publicacion> fotosList = new JList<>(listModel);
					fotosList.setCellRenderer(new PublicacionRenderer());
					JScrollPane pane = new JScrollPane(fotosList);
					JFrame frameBusqueda = createFrame(fotos.size());
					frameBusqueda.getContentPane().add(pane);
					frameBusqueda.setLocationRelativeTo(null);
					frameBusqueda.setVisible(true);
				}
			}
		});
		btnTopLikes.setIcon(new ImageIcon(VentanaPremium.class.getResource("/imagenes/like_dado.png")));
		GridBagConstraints gbc_btnTopLikes = new GridBagConstraints();
		gbc_btnTopLikes.fill = GridBagConstraints.BOTH;
		gbc_btnTopLikes.insets = new Insets(0, 0, 5, 5);
		gbc_btnTopLikes.gridx = 3;
		gbc_btnTopLikes.gridy = 5;
		panelPrivilegios.add(btnTopLikes, gbc_btnTopLikes);
				
		if (Controlador.getUnicaInstancia().esPremium(Controlador.getUnicaInstancia().getUsuarioActual())) {
			Controlador.getUnicaInstancia().setPremium(Controlador.getUnicaInstancia().getUsuarioActual());
			CardLayout cl = (CardLayout) (contentPane.getLayout());
			cl.show(contentPane, "panelPriv");
		}
		
		frmventanaPremium.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				frmPerfil.setEnabled(true);
			}
		});
	}

	private static JFrame createFrame(int resultados) {
		JFrame searchframe = new JFrame("Fotos con más likes");
		searchframe.setIconImage(
				Toolkit.getDefaultToolkit().getImage(VentanaPrincipal.class.getResource("/imagenes/camarax32.png")));
		searchframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		searchframe.setSize(new Dimension(300, 300));
		return searchframe;
	}
}
