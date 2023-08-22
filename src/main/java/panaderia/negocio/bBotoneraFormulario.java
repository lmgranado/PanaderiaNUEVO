package panaderia.negocio;

import java.util.Hashtable;
import java.util.Map;
import java.util.Vector;

import panaderia.beans.Boton;


public class bBotoneraFormulario {
	private Vector botones;
	private static Map tiposBotones;
		
	/* Inicialización del contenido static */
	static {
		tiposBotones = new Hashtable();
		
		// Botones
		Boton guardar = new Boton("Grabar","Grabar formulario","img/aceptar.gif","boton", "javascript:doSubmit();");
		Boton volver = new Boton("Volver","Volver al listado","img/volver.gif","boton", "");
		Boton grabar = new Boton("Grabar","Grabar formulario","img/aceptar.gif","boton", "javascript:doGrabar();");
		Boton imprimir = new Boton("Imprimir","Imprimir en pdf", "img/btn_imprimir.gif","boton","javascript:doImprimir();");
		Boton cerrar = new Boton("Cerrar","Cerrar","img/btn_cerrar.gif","boton", "javascript:doCerrar();");
		Boton editar = new Boton("Editar","Editar","img/editar_b.gif","boton", "javascript:doEditar();");
		Boton posterior = new Boton("Posterior","Posterior","img/posterior.png","boton", "javascript:doPosterior();");
		Boton anterior = new Boton("Anterior","Anterior","img/anterior.png","boton", "javascript:doAnterior();");
		Boton notasCuadrante = new Boton("notaCuadrante","notaCuadrante","img/notascuadrante.png","boton", "javascript:doNotaCuadrante();");
		Boton pasarCuadrante = new Boton("pasarCuadrante","Pasar Cuadrante","img/pasarcuadrante.png","boton", "javascript:doInsertarCuadrante();");
		Boton etiquetas = new Boton("imprimirEtiquetas","Imprimir Etiquetas","img/imprimiretiquetas.png","boton", "javascript:doImprimirEtiquetas();");
		
		tiposBotones.put("GUARDAR", guardar);
		tiposBotones.put("VOLVER", volver);
		tiposBotones.put("GRABAR", grabar);
		tiposBotones.put("IMPRIMIR", imprimir);
		tiposBotones.put("CERRAR", cerrar);
		tiposBotones.put("EDITAR", editar);
		tiposBotones.put("ANTERIOR", anterior);
		tiposBotones.put("POSTERIOR", posterior);
		tiposBotones.put("NOTASCUADRANTE", notasCuadrante);
		tiposBotones.put("PASARCUADRANTE", pasarCuadrante);
		tiposBotones.put("ETIQUETAS", etiquetas);

	}
	/* Fin Inicialización del contenido static */	

	/* Constantes */
	public static Boton GUARDAR = (Boton) tiposBotones.get("GUARDAR");
	public static Boton VOLVER = (Boton) tiposBotones.get("VOLVER");
	public static Boton GRABAR = (Boton) tiposBotones.get("GRABAR");
	public static Boton IMPRIMIR = (Boton) tiposBotones.get("IMPRIMIR");
	public static Boton SUBSANAR = (Boton) tiposBotones.get("SUBSANAR");
	public static Boton CERRAR = (Boton) tiposBotones.get("CERRAR");
	public static Boton EDITAR = (Boton) tiposBotones.get("EDITAR");
	public static Boton PASARCUADRANTE = (Boton) tiposBotones.get("PASARCUADRANTE");
	public static Boton ANTERIOR = (Boton) tiposBotones.get("ANTERIOR");
	public static Boton POSTERIOR= (Boton) tiposBotones.get("POSTERIOR");
	public static Boton NOTASCUADRANTE= (Boton) tiposBotones.get("NOTASCUADRANTE");
	public static Boton IMPRIMIRETIQUETAS= (Boton) tiposBotones.get("ETIQUETAS");
	
	/** Constructor
	 * 
	 */
	public bBotoneraFormulario(){
		this.botones = new Vector();
	}
	
	/** Añade una nuevo boton
	 * 
	 * @param orden
	 * @param boton
	 */
	public void addBoton(int orden, Boton boton){
		this.botones.add(orden, boton);
	}
	
	public void addBoton(int orden, Boton boton, String direccion){
		this.botones.add(orden, boton);
	}
	
	/** Devuelve la lista de botones básica
	 * 
	 * @return Vector
	 */
	public Vector getBotones(){
		return this.botones;
	}
}