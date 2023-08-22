package panaderia.beans.entidad;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import panaderia.struts.forms.EntidadBean;

import utils.Utils;

public class ProductosClienteEntidad extends EntidadBean
{
  private static final long serialVersionUID = 1L;
  
  public static final String TABLE="productos_cliente";
  public static final String ALL_COLUMNS="productos_cliente.pcl_cl_id,productos_cliente.pcl_id,productos_cliente.pcl_precio,productos_cliente.pcl_prod_id";
  public static final String PCLCLID="pcl_cl_id";
  public static final String PCLID="pcl_id";
  public static final String PCLPRECIO="pcl_precio";
  public static final String PRODNOMBRE="PROD_NOMBRE";
  public static final String PCLPRODID="pcl_prod_id";
  public static final String PRIMARY_KEY="pcl_id";

  protected ProductosClienteEntidad(){}

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
    PreparedStatement sentencia = conexion.prepareStatement("UPDATE productos_cliente SET pcl_cl_id=?,pcl_id=?,pcl_precio=?,pcl_prod_id=? WHERE pcl_id=?");
    sentencia.setString(1, pclClId);
    sentencia.setString(2, pclId);
    sentencia.setString(3, quitar_comas(pclPrecio));
    sentencia.setString(4, pclProdId);
    sentencia.setString(5, pclId);
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
    PreparedStatement sentencia = conexion.prepareStatement("INSERT INTO productos_cliente (pcl_cl_id,pcl_precio,pcl_prod_id) VALUES (?,?,?)");
    sentencia.setString(1, pclClId);
    sentencia.setString(2, quitar_comas(pclPrecio));
    sentencia.setString(3, pclProdId);
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
    PreparedStatement sentencia = conexion.prepareStatement("DELETE FROM productos_cliente WHERE pcl_id=?");
    sentencia.setString(1, pclId);
    int n = sentencia.executeUpdate();
    sentencia.close();
    return n;
  }

  public String toString()
  {
    return "productos_cliente["+
      "pclClId:"+pclClId+", "+
      "pclId:"+pclId+", "+
      "pclPrecio:"+pclPrecio+", "+
      "pclProdId:"+pclProdId+
    "]";
  }

  private String pclClId = "";
  public String getPclClId()
  {
    return pclClId;
  }
  public void setPclClId(String pclClId)
  {
    this.pclClId = pclClId;
  }

  private String pclId = "";
  public String getPclId()
  {
    return pclId;
  }
  public void setPclId(String pclId)
  {
    this.pclId = pclId;
    this.pk_identificador_ = pclId;
  }

  private String pclPrecio = "";
  public String getPclPrecio()
  {
    return pclPrecio;
  }
  public void setPclPrecio(String pclPrecio)
  {
    this.pclPrecio = pclPrecio;
  }

  private String pclProdId = "";
  public String getPclProdId()
  {
    return pclProdId;
  }
  public void setPclProdId(String pclProdId)
  {
    this.pclProdId = pclProdId;
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
