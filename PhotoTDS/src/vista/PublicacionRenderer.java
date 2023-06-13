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
import modelo.Album;
import modelo.Foto;
import modelo.Publicacion;
import modelo.Usuario;

public class PublicacionRenderer extends JPanel implements ListCellRenderer<Publicacion>{
	private static final long serialVersionUID = 1L;
	private JLabel labelPreview = new JLabel();
	private JLabel numeroLikes = new JLabel();
	private JLabel fotoAutor = new JLabel();
	private JLabel nombreUsuario = new JLabel(); 
	
    
    public PublicacionRenderer() {
        setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5)); // establece el layout de la celda
        add(labelPreview); 
        add(numeroLikes); 
        add(fotoAutor);
        add(nombreUsuario);
    }
           

	@Override
	public Component getListCellRendererComponent(JList<? extends Publicacion> list, Publicacion publicacion, int index,
			boolean isSelected, boolean cellHasFocus) {
		Usuario autor = Controlador.getUnicaInstancia().getAutorPublicacion(publicacion);
		if(publicacion instanceof Foto) {
			ImageIcon imagen = new ImageIcon(Controlador.getUnicaInstancia().obtenerPathFoto((Foto) publicacion));
			Image imagenACambiar = imagen.getImage();
			ImageIcon icono= new ImageIcon(imagenACambiar.getScaledInstance(40, 40,  java.awt.Image.SCALE_SMOOTH));
			labelPreview.setIcon(icono);
		} 
		else if(publicacion instanceof Album) {
			String foto = Controlador.getUnicaInstancia().getPortadaAlbum((Album)publicacion);
			ImageIcon imagen = new ImageIcon(foto);
			Image imagenACambiar = imagen.getImage();
			ImageIcon icono= new ImageIcon(imagenACambiar.getScaledInstance(40, 40,  java.awt.Image.SCALE_SMOOTH));
			labelPreview.setIcon(icono);
		}
		numeroLikes.setText(String.valueOf(publicacion.getNumeroMegusta())+ " Likes");
		ImageIcon imagen = new ImageIcon(Controlador.getUnicaInstancia().getPhotoUsr(autor));
		Image imagenACambiar = imagen.getImage();
		ImageIcon icono= new ImageIcon(imagenACambiar.getScaledInstance(40, 40,  java.awt.Image.SCALE_SMOOTH));
		fotoAutor.setIcon(icono);
		nombreUsuario.setText(autor.getLogin());
    	
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
