package modelo;

import controlador.Controlador;

public class DescuentoPopularidad extends Descuento {

	@Override
	public double aplicarDescuento(Usuario usuario) {
		int numLikes = Controlador.getUnicaInstancia().obtenerTotalLikesUsuario(usuario);
		int descuento = numLikes/10;
		return descuento;
	}

}
