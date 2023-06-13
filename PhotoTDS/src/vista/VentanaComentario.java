package vista;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controlador.Controlador;
import modelo.Comentario;
import modelo.Foto;

import java.awt.Toolkit;
import java.util.List;
import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.GridLayout;
import javax.swing.ScrollPaneConstants;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class VentanaComentario extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JFrame frmComment;
	private JPanel contentPane;
	private JTextField textField;
	private PanelFoto panelFoto;
	private Foto foto;

	public VentanaComentario(Foto f, PanelFoto panelFoto) {
		this.foto = f;
		this.panelFoto =  panelFoto;
		initialize();
	}

	public void mostrarVentana() {
		frmComment.setLocationRelativeTo(null);
		frmComment.setVisible(true);
	}

	public void initialize() {
		frmComment = new JFrame();
		frmComment.setTitle("Comentarios");
		frmComment.setIconImage(
				Toolkit.getDefaultToolkit().getImage(VentanaComentario.class.getResource("/IMAGENES/camarax32.png")));
		frmComment.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmComment.setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		frmComment.setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JPanel panel_comentarios = new JPanel();
		contentPane.add(panel_comentarios, BorderLayout.CENTER);
		panel_comentarios.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		panel_comentarios.add(scrollPane, BorderLayout.CENTER);

		JPanel panel = new JPanel();
		scrollPane.setViewportView(panel);
		panel.setLayout(new GridLayout(0, 1, 0, 0));
		
		mostrarComentarios(panel);
		

		JPanel panel_escribir = new JPanel();
		contentPane.add(panel_escribir, BorderLayout.SOUTH);
		panel_escribir.setLayout(new BorderLayout(0, 0));

		JButton btnNewButton = new JButton("");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(!textField.getText().isBlank()) {
					if (textField.getText().trim().length() <= 120) {
						textField.setForeground(new JTextField().getForeground());
						Comentario nuevoComentario = Controlador.getUnicaInstancia().agregarComentarioAPublicacion(foto, textField.getText());
						textField.setText(null);
						agregarComentario(nuevoComentario, panel);
						revalidate();
						panel.scrollRectToVisible(getBounds());
						panelFoto.actualizarComentarios();
					} else {
						textField.setForeground(Color.RED);
						JOptionPane.showMessageDialog(frmComment, "Máximo de 120 caracteres para un comentario",
								"Error comentario", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		btnNewButton.setIcon(new ImageIcon(VentanaComentario.class.getResource("/IMAGENES/enviar.png")));
		panel_escribir.add(btnNewButton, BorderLayout.EAST);

		textField = new JTextField();
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					if(!textField.getText().isBlank()) {
						Comentario nuevoComentario = Controlador.getUnicaInstancia().agregarComentarioAPublicacion(foto, textField.getText());
						textField.setText(null);
						agregarComentario(nuevoComentario, panel);
						revalidate();
						panel.scrollRectToVisible(getBounds());
					}
				}
			}
		});
		panel_escribir.add(textField, BorderLayout.CENTER);
		textField.setColumns(10);
	}
	
	public void mostrarComentarios(JPanel panel) {
		List<Comentario> listaComentarios = Controlador.getUnicaInstancia().obtenerComentarios(foto);
		for(Comentario c: listaComentarios) {
			PanelComentario pc = new PanelComentario(c, foto);
			panel.add(pc);
		}
	}
	
	public void agregarComentario(Comentario comentario, JPanel panel) {
		PanelComentario pc = new PanelComentario(comentario, foto);
		panel.add(pc);
	}

}
