package vista;

import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controlador.Controlador;
import modelo.Notificacion;
import modelo.Usuario;

import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

public class VentanaNotificaciones extends JFrame {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JFrame frmNotificacion;
	private JFrame ventanaPrincipal;
	private JPanel contentPane;
	
	public void mostrarVentana() {
		frmNotificacion.setLocationRelativeTo(null);
		frmNotificacion.setVisible(true);
	}
	
	public VentanaNotificaciones(JFrame ventanaPrincipal) {
		this.ventanaPrincipal = ventanaPrincipal;
		initialize();
	}

	public void initialize() {
		frmNotificacion = new JFrame();
		frmNotificacion.setTitle("Notificaciones");
		frmNotificacion.setIconImage(Toolkit.getDefaultToolkit()
				.getImage(VentanaNotificaciones.class.getResource("/imagenes/camarax32.png")));
		frmNotificacion.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmNotificacion.setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		frmNotificacion.setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		contentPane.add(scrollPane, BorderLayout.CENTER);

		JPanel panel = new JPanel();
		scrollPane.setViewportView(panel);
		panel.setLayout(new GridLayout(0, 1, 0, 0));

		mostrarNotificaciones(panel);
		
		frmNotificacion.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				ventanaPrincipal.setEnabled(true);
			}
		});
		
	}

	public void mostrarNotificaciones(JPanel panel) {
		Usuario usuario = Controlador.getUnicaInstancia().getUsuarioActual();
		List<Notificacion> listaNotificaciones = Controlador.getUnicaInstancia().getNotificacionesUsuario(usuario);
		for(Notificacion n: listaNotificaciones) {
			System.out.println(n.toString());
			PanelNotificacion pn = new PanelNotificacion(n, ventanaPrincipal, frmNotificacion);
			panel.add(pn);
		}
	}
}
