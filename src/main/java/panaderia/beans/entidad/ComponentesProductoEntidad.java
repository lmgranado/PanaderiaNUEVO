package panaderia.beans.entidad;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import panaderia.struts.forms.EntidadBean;
import utils.SQLManager;

public class ComponentesProductoEntidad extends EntidadBean
{
  private static final long serialVersionUID = 1L;
  
  public static final String TABLE="componentes_producto";
  public static final String ALL_COLUMNS="componentes_producto.cp_cantidad,componentes_producto.cp_id,componentes_producto.cp_prod_id_fabricado,componentes_producto.cp_prod_id_usado";
  public static final String CPCANTIDAD="cp_cantidad";
  public static final String CPID="cp_id";
  public static final String CPPRODIDFABRICADO="cp_prod_id_fabricado";
  public static final String CPPRODIDUSADO="cp_prod_id_usado";
  public static final String PRIMARY_KEY="cp_id";

  protected ComponentesProductoEntidad(){}

  public int actualiza()
  {
	  Connection conexion  = utils.PoolConexiones.conexion(); 
	    try
	    {
	      return actualiza(conexion);
	    }
	    catch( SQLException e ){ utils.Log4j.error("Error", e); return -1; }
	    finally{ utils.PoolConexiones.cerrarConexion(conexion);  }
  }

  public int actualiza(Connection conexion) throws SQLException
  {
    PreparedStatement sentencia = conexion.prepareStatement("UPDATE componentes_producto SET cp_cantidad=?,cp_prod_id_fabricado=?,cp_prod_id_usado=? WHERE cp_id=?");
    sentencia.setString(1, quitar_comas(cpCantidad));    
    sentencia.setString(2, cpProdIdFabricado);
    sentencia.setString(3, cpProdIdUsado);
    sentencia.setString(4, cpId);
    int n = sentencia.executeUpdate();
    sentencia.close();
    return n;
  }

  public int inserta()
  {
	  Connection conexion  = utils.PoolConexiones.conexion(); 
	    try
	    {
	      return inserta(conexion);
	    }
	    catch( SQLException e ){ utils.Log4j.error("Error", e); return -1; }
	    finally{ utils.PoolConexiones.cerrarConexion(conexion);  }
  }

  public int inserta(Connection conexion) throws SQLException
  {
    PreparedStatement sentencia = conexion.prepareStatement("INSERT INTO componentes_producto (cp_cantidad,cp_prod_id_fabricado,cp_prod_id_usado) VALUES (?,?,?)");
    sentencia.setString(1, quitar_comas(cpCantidad));
    sentencia.setString(2, cpProdIdFabricado);
    sentencia.setString(3, cpProdIdUsado);
    int n = sentencia.executeUpdate();
    sentencia.close();
    return n;
  }

  public int elimina()
  {
	  Connection conexion  = utils.PoolConexiones.conexion(); 
	    try
	    {
	      return elimina(conexion);
	    }
	    catch( SQLException e ){ utils.Log4j.error("Error", e); return -1; }
	    finally{ utils.PoolConexiones.cerrarConexion(conexion); }
  }

  public int elimina(Connection conexion) throws SQLException
  {
    PreparedStatement sentencia = conexion.prepareStatement("DELETE FROM componentes_producto WHERE cp_id=?");
    sentencia.setString(1, cpId);
    int n = sentencia.executeUpdate();
    sentencia.close();
    return n;
  }

  public String toString()
  {
    return "componentes_producto["+
      "cpCantidad:"+cpCantidad+", "+
      "cpId:"+cpId+", "+
      "cpProdIdFabricado:"+cpProdIdFabricado+", "+
      "cpProdIdUsado:"+cpProdIdUsado+
    "]";
  }

  private String cpCantidad = "";
  public String getCpCantidad()
  {
    return cpCantidad;
  }
  public void setCpCantidad(String cpCantidad)
  {
    this.cpCantidad = cpCantidad;
  }

  private String cpId = "";
  public String getCpId()
  {
    return cpId;
  }
  public void setCpId(String cpId)
  {
    this.cpId = cpId;
    this.pk_identificador_ = cpId;
  }

  private String cpProdIdFabricado = "";
  public String getCpProdIdFabricado()
  {
    return cpProdIdFabricado;
  }
  public void setCpProdIdFabricado(String cpProdIdFabricado)
  {
    this.cpProdIdFabricado = cpProdIdFabricado;
  }

  private String cpProdIdUsado = "";
  public String getCpProdIdUsado()
  {
    return cpProdIdUsado;
  }
  public void setCpProdIdUsado(String cpProdIdUsado)
  {
    this.cpProdIdUsado = cpProdIdUsado;
  }
  
  private String[] clDatosRelacionados;
  public String[] getClDatosRelacionados()
  {
	  return clDatosRelacionados;
  }
  public void setClDatosRelacionados(String[] clDatosRelacionados)
  {
    this.clDatosRelacionados = clDatosRelacionados;
  }
}