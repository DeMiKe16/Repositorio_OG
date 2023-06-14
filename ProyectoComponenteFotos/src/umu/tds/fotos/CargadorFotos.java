package umu.tds.fotos;

import java.util.Vector;

public class CargadorFotos {
	
	private Fotos archivoFotos;
	private Vector<FotosListener> oyentes = new Vector<FotosListener>();
	
	

	public CargadorFotos() {
		this.archivoFotos = new Fotos();
	}

	public synchronized boolean addOyente(FotosListener oyente) {
		if (oyentes.add(oyente)) {
			return true;
		}
		return false;
	}

	public synchronized boolean removeOyente(FotosListener oyente) {
		if (oyentes.remove(oyente)) {
			return true;
		}
		return false;
	}

	public Fotos getArchivoFotos() {
		return archivoFotos;
	}

	public void setArchivoFotos(String fichero) {
		Fotos newArchivoFotos = MapperFotosXMLtoJava.cargarFotos(fichero);
		Fotos oldArchivoFotos = archivoFotos;
		this.archivoFotos = newArchivoFotos;
		if (!oldArchivoFotos.equals(newArchivoFotos)) {

			FotosEvent evento = new FotosEvent(this, oldArchivoFotos, newArchivoFotos);
			notificarCambio(evento);
		}
	}
	
	@SuppressWarnings("unchecked")
	private void notificarCambio(FotosEvent event) {
		Vector<FotosListener> lista;
		synchronized (this) {
			lista = (Vector<FotosListener>) oyentes.clone();
		}
		for (FotosListener cl : lista) {
			cl.enteradoCambioFotos(event);
		}

	}
}
