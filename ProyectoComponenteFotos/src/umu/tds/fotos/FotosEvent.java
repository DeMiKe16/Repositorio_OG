package umu.tds.fotos;

import java.util.EventObject;

@SuppressWarnings("serial")
public class FotosEvent extends EventObject {
	
	protected Fotos OldFotos;
	protected Fotos NewFotos;

	public FotosEvent(Object fuente, Fotos OldFotos, Fotos  NewFotos) {
		super(fuente);
		this.OldFotos = OldFotos;
		this.NewFotos = NewFotos;
	}

	public Fotos getOldFotos() {

		return OldFotos;

	}

	public Fotos getNewFotos() {

		return NewFotos;

	}

}
