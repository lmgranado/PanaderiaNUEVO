package panaderia.beans.entidad;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import panaderia.struts.forms.EntidadBean;

import utils.Utils;

public class AlbaranesVentaDetalleEntidad extends EntidadBean implements
		Serializable {
	private static final long serialVersionUID = 1L;

	public static final String TABLE = "albaranes_venta_detalle";

	public static final String ALL_COLUMNS = "albaranes_venta_detalle.avd_av_id,albaranes_venta_detalle.avd_cantidad,albaranes_venta_detalle.avd_id,albaranes_venta_detalle.avd_importe,albaranes_venta_detalle.avd_iva,albaranes_venta_detalle.avd_linea,albaranes_venta_detalle.avd_precio_venta,albaranes_venta_detalle.avd_producto,albaranes_venta_detalle.avd_referencia,albaranes_venta_detalle.avd_descuento,albaranes_venta_detalle.avd_viaje,albaranes_venta_detalle.avd_st_id";

	public static final String AVDAVID = "avd_av_id";

	public static final String AVDCANTIDAD = "avd_cantidad";

	public static final String AVDID = "avd_id";

	public static final String AVDIMPORTE = "avd_importe";

	public static final String AVDIVA = "avd_iva";

	public static final String AVDLINEA = "avd_linea";

	public static final String AVDPRECIOVENTA = "avd_precio_venta";

	public static final String AVDPRODUCTO = "avd_producto";

	public static final String AVDREFERENCIA = "avd_referencia";

	public static final String AVDDESCUENTO = "avd_descuento";

	public static final String AVDVIAJE = "avd_viaje";

	public static final String PRIMARY_KEY = "avd_id";

	public static final String PRODNOMBRE = "prod_nombre";

	public static final String PRODREFERENCIA = "prod_referencia";

	public static final String PRODPRECIOGENERAL = "prod_precio_general";

	public static final String AVDSTID = "avd_st_id";

	protected AlbaranesVentaDetalleEntidad() {
	}

	public int actualiza() {
		
		Connection conexion  = utils.PoolConexiones.conexion();
		try {
			return actualiza(conexion);
		} catch (SQLException e) {
			utils.Log4j.error("Error", e);
			return -1;
		} finally {
			utils.PoolConexiones.cerrarConexion(conexion);
		}
	}

	public int actualiza(Connection conexion) throws SQLException {
		PreparedStatement sentencia = conexion
				.prepareStatement("UPDATE albaranes_venta_detalle SET avd_av_id=?,avd_cantidad=?,avd_importe=?,avd_iva=?,avd_linea=?,avd_precio_venta=?,avd_producto=?,avd_referencia=?,avd_descuento=?,avd_viaje=?,avd_st_id=? WHERE avd_id=?");
		sentencia.setString(1, quitar_comas(avdAvId));
		sentencia.setString(2, quitar_comas(avdCantidad));
		// sentencia.setString(3, avdId);
		sentencia.setString(3, avdImporte);
		sentencia.setString(4, quitar_comas(avdIva));
		sentencia.setString(5, avdLinea);
		sentencia.setString(6, quitar_comas(avdPrecioVenta));
		sentencia.setString(7, avdProducto);
		sentencia.setString(8, avdReferencia);
		sentencia.setString(9, quitar_comas(avdDescuento));
		sentencia.setString(10, avdViaje);
		sentencia.setString(11, avdStId);
		sentencia.setString(12, avdId);
		int n = sentencia.executeUpdate();
		sentencia.close();
		return n;
	}

	public int inserta() {
		
		Connection conexion  = utils.PoolConexiones.conexion();
		try {
			return inserta(conexion);
		} catch (SQLException e) {
			utils.Log4j.error("Error", e);
			return -1;
		} finally {
			utils.PoolConexiones.cerrarConexion(conexion);
		}
	}

	public int inserta(Connection conexion) throws SQLException {
		PreparedStatement sentencia = conexion
				.prepareStatement("INSERT INTO albaranes_venta_detalle (avd_av_id,avd_cantidad,avd_importe,avd_iva,avd_linea,avd_precio_venta,avd_producto,avd_descuento,avd_viaje,avd_st_id) VALUES (?,?,?,?,?,?,?,?,?,?)");
		sentencia.setString(1, avdAvId);
		sentencia.setString(2, quitar_comas(avdCantidad));
		// sentencia.setString(3, avdId);
		sentencia.setString(3, quitar_comas(avdImporte));
		sentencia.setString(4, quitar_comas(avdIva));
		sentencia.setString(5, avdLinea);
		sentencia.setString(6, quitar_comas(avdPrecioVenta));
		sentencia.setString(7, avdProducto);
		sentencia.setString(8, quitar_comas(avdDescuento));
		sentencia.setString(9, avdViaje);
		sentencia.setString(10, avdStId);
		int n = sentencia.executeUpdate();
		sentencia.close();
		return n;
	}

	public int elimina() {
		
		Connection conexion  = utils.PoolConexiones.conexion();
		try {
			return elimina(conexion);
		} catch (SQLException e) {
			utils.Log4j.error("Error", e);
			return -1;
		} finally {
			utils.PoolConexiones.cerrarConexion(conexion);
		}
	}

	public int elimina(Connection conexion) throws SQLException {
		PreparedStatement sentencia = conexion
				.prepareStatement("DELETE FROM albaranes_venta_detalle WHERE avd_id=?");
		sentencia.setString(1, avdId);
		int n = sentencia.executeUpdate();
		sentencia.close();
		return n;
	}

	public String toString() {
		return "albaranes_venta_detalle[" + "avdAvId:" + avdAvId + ", "
				+ "avdCantidad:" + avdCantidad + ", " + "avdId:" + avdId + ", "
				+ "avdImporte:" + avdImporte + ", " + "avdIva:" + avdIva + ", "
				+ "avdLinea:" + avdLinea + ", " + "avdPrecioVenta:"
				+ avdPrecioVenta + ", " + "avdProducto:" + avdProducto + ", "
				+ "avdReferencia:" + avdReferencia + ", " + "avdViaje:"
				+ avdViaje + ", " + "HashCode;" + this.hashCode() + "]";
	}

	private String avdAvId = "";

	public String getAvdAvId() {
		return avdAvId;
	}

	public void setAvdAvId(String avdAvId) {
		this.avdAvId = avdAvId;
	}

	private String avdCantidad = "";

	public String getAvdCantidad() {
		return avdCantidad;
	}

	public void setAvdCantidad(String avdCantidad) {
		this.avdCantidad = avdCantidad;
	}

	private String avdId = "";

	public String getAvdId() {
		return avdId;
	}

	public void setAvdId(String avdId) {
		this.avdId = avdId;
		this.pk_identificador_ = avdId;
	}

	private String avdImporte = "";

	public String getAvdImporte() {
		return avdImporte;
	}

	public void setAvdImporte(String avdImporte) {
		this.avdImporte = avdImporte;
	}

	private String avdIva = "";

	public String getAvdIva() {
		return avdIva;
	}

	public void setAvdIva(String avdIva) {
		this.avdIva = avdIva;
	}

	private String avdLinea = "";

	public String getAvdLinea() {
		return avdLinea;
	}

	public void setAvdLinea(String avdLinea) {
		this.avdLinea = avdLinea;
	}

	private String avdPrecioVenta = "";

	public String getAvdPrecioVenta() {
		return avdPrecioVenta;
	}

	public void setAvdPrecioVenta(String avdPrecioVenta) {
		this.avdPrecioVenta = avdPrecioVenta;
	}

	private String avdProducto = "";

	public String getAvdProducto() {
		return avdProducto;
	}

	public void setAvdProducto(String avdProducto) {
		this.avdProducto = avdProducto;
	}

	private String avdReferencia = "";

	public String getAvdReferencia() {
		return avdReferencia;
	}

	public void setAvdReferencia(String avdReferencia) {
		this.avdReferencia = avdReferencia;
	}

	private String avdDescuento = "";

	public String getAvdDescuento() {
		return avdDescuento;
	}

	public void setAvdDescuento(String avdDescuento) {
		this.avdDescuento = avdDescuento;
	}

	private String avdViaje = "";

	public String getAvdViaje() {
		return avdViaje;
	}

	public void setAvdViaje(String avdViaje) {
		this.avdViaje = avdViaje;
	}

	private String avdStId = "";

	public String getAvdStId() {
		return avdStId;
	}

	public void setAvdStId(String avdStId) {
		this.avdStId = avdStId;
	}
	
	  private String lote;
	  public String getLote()
	  {
	    return lote;
	  }
	  public void setLote(String lote)
	  {
	    this.lote = lote;
	  }
}
