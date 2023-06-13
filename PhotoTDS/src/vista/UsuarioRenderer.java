package vista;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import controlador.Controlador;
import modelo.Usuario;

public class UsuarioRenderer extends JPanel implements ListCellRenderer<Usuario> {
    /**
	 */
	private static final long serialVersionUID = 1L;
	private JLabel labelNombre = new JLabel();
    private JLabel labelImagen = new JLabel();
    
    public UsuarioRenderer() {
        setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5)); // establece el layout de la celda
        add(labelImagen); // agrega el label de la imagen
        add(labelNombre); // agrega el label del nombre
    }
           
    @Override
    public Component getListCellRendererComponent(JList<? extends Usuario> list, Usuario usuario, int index,
            boolean isSelected, boolean cellHasFocus) {
    	ImageIcon imagen = new ImageIcon(Controlador.getUnicaInstancia().getPhotoUsr(usuario));
		Image imagenACambiar = imagen.getImage();
		ImageIcon icono= new ImageIcon(imagenACambiar.getScaledInstance(40, 40,  java.awt.Image.SCALE_SMOOTH)); 
		labelImagen.setIcon(icono);
    	//labelImagen.setText("<html><img src=\"file:\\" + usuario.getPhotoLogin() + "\" width=40 height=40></img>");
    	labelNombre.setText(usuario.getLogin()); // muestra el nombre del usuario
    	
    	if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }
        setEnabled(list.isEnabled());
        setFont(list.getFont());
        setOpaque(true); // asegura que el fondo sea visible
        
        return this;
    }
}