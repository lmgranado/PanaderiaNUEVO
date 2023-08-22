package panaderia.negocio;

import java.util.Vector;

import panaderia.beans.Pestana;
import panaderia.utilidades.RecuperaProperties;


public class bPestanaFormulario {
	private Vector pestanas;
	
	RecuperaProperties rProperties = new RecuperaProperties("panaderia.properties.Recursos");
	
	
	public bPestanaFormulario(){
		this.pestanas = new Vector();
	}
	
		
	public void addPestana(int orden, Pestana pestana){
		this.pestanas.add(orden, pestana);
	}

	
	
	public Vector getPestanas(){
		return this.pestanas;
	}
	
}