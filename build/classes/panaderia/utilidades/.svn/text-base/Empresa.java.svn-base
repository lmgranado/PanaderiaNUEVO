package panaderia.utilidades;

public class Empresa {

	private String nombreComercial = "PINEPAN S.L.";
	private String direccion1 = "C/ Manuel Jimenez León N.55";
	private String direccion2 = "";
	private String codigoPostal = "41520";
	private String localidad = "El Viso del Alcor";
	private String provincia = "Sevilla";
	private String telefonoFabrica = "955.94.63.53";
	private String telefonoOficina = "954.67.76.90";
	private String cif = "B-91434068";
	
	public static final String EmpresaPinepan = "1";
	private static final String EmpresaObrador = "2";
	
	public Empresa( ) {
		
	}
	public Empresa( String clPertenencia ) {
		//Si el cliente pertenece a la empresa Obrador
		if( EmpresaObrador.equals(clPertenencia) ){
			nombreComercial = "OBRADOR PINEDA JIMENEZ S.L.";
			direccion1 = "C/Madre de la Iglesia N.62";
			direccion2 = "";
			codigoPostal = "41016";
			localidad = "Sevilla";
			provincia = "Sevilla";
			telefonoFabrica = "954.67.76.90";
			telefonoOficina = "";
			cif = "41363060";
		}
		
	}
	public  String getCif() {
		return cif;
	}
	public  String getCodigoPostal() {
		return codigoPostal;
	}
	public  String getDireccion1() {
		return direccion1;
	}
	public  String getDireccion2() {
		return direccion2;
	}
	public  String getLocalidad() {
		return localidad;
	}
	public  String getNombreComercial() {
		return nombreComercial;
	}
	public  String getProvincia() {
		return provincia;
	}
	public  String getTelefonoFabrica() {
		return telefonoFabrica;
	}
	public  String getTelefonoOficina() {
		return telefonoOficina;
	}
}
