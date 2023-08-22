package panaderia.negocio;

import java.util.Hashtable;
import java.util.Map;
import java.util.Vector;

import panaderia.beans.Boton;


public class bBotoneraListado {
	private Vector botones;
	private static Map tiposBotones;

	static {
		tiposBotones = new Hashtable();
		
		Boton nuevo = new Boton("Nuevo","Crear nuevo registro","img/nuevo.gif","boton", "javascript:doNuevo();");
		Boton borrar = new Boton("Borrar","Borrar registros seleccionados","img/eliminar.gif", "boton", "javascript:doBorrar();");
		Boton generar = new Boton("Generar","Generar Documentación","img/btn_generar.gif","boton", "javascript:doNuevo();");
		Boton volver = new Boton("Volver","Volver al listado","img/volver.gif","boton", "javascript:doVolver();");
		Boton buscar = new Boton("BUSCAR","Buscar propuestas","img/btn_buscar2.gif","boton", "javascript:doBuscar();");
		Boton limpiar = new Boton("LIMPIAR","Limpiar filtros","img/btn_limpiar2.gif","boton", "javascript:doLimpiar();");
		Boton editar = new Boton("EDITAR","Editar","img/editar_b.gif","boton", "javascript:doEditar();");
		Boton guardar = new Boton("GUARDAR","Guardar","img/guardar.gif","boton", "javascript:doGuardar();");
		
		
		tiposBotones.put("NUEVO", nuevo);
		tiposBotones.put("VOLVER", volver);
		tiposBotones.put("BORRAR", borrar);
		tiposBotones.put("GENERAR", generar);
		tiposBotones.put("BUSCAR", buscar);
		tiposBotones.put("LIMPIAR", limpiar);
		tiposBotones.put("EDITAR", editar);
		tiposBotones.put("GUARDAR", guardar);
		
	}

	public static Boton NUEVO = (Boton) tiposBotones.get("NUEVO");
	public static Boton BORRAR = (Boton) tiposBotones.get("BORRAR");
	public static Boton VOLVER = (Boton) tiposBotones.get("VOLVER");
	public static Boton GENERAR = (Boton) tiposBotones.get("GENERAR");
	public static Boton BUSCAR = (Boton) tiposBotones.get("BUSCAR");
	public static Boton LIMPIAR = (Boton) tiposBotones.get("LIMPIAR");
	public static Boton EDITAR = (Boton) tiposBotones.get("EDITAR");
	public static Boton GUARDAR = (Boton) tiposBotones.get("GUARDAR");

	public bBotoneraListado(){
		this.botones = new Vector();
	}

	public void addBoton(int orden, Boton boton){
		this.botones.add(orden, boton);
	}

	public Vector getBotones(){
		return this.botones;
	}
}
