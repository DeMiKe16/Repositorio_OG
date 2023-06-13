package modelo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

import controlador.Controlador;

public class DescuentoEdad extends Descuento{

	@Override
	public double aplicarDescuento(Usuario usuario) {
		SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
		Date ahora = new Date();
		try {
			Date fecha = formatter.parse(Controlador.getUnicaInstancia().getFechaUsuario(usuario));
			long diferenciaMs = ahora.getTime() - fecha.getTime();
			Duration diferencia = Duration.ofMillis(diferenciaMs);
			long edad = diferencia.toDays() / 365;
			if(edad >= 40) {
				return 0.5;
			} else {
				return 1;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 1;
	}

}
