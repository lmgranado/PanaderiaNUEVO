package panaderia.beans.entidad;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import panaderia.struts.forms.EntidadBean;


public class ActualizadorPreciosEntidad extends EntidadBean
{
  private static final long serialVersionUID = 1L;

  public static final String PRODUCTO="producto";
  public static final String CLIENTE="cliente";
  public static final String PRECIO="precio";
  public static final String ACTUALIZACION="actualizacion";
  //CHAPÚ, PARA QUE NO FALLE LA BÚSQUEDA, YA QUE SE MODIFICO PARA QUE BUSCASE POR CUALQUIER CAMPO + PRIMARY_KEY --> EL MOTIVO NO ME ACUERDO
  public static final String PRIMARY_KEY = "1";
  
  
  protected ActualizadorPreciosEntidad(){}

  public String toString()
  {
    return "actualizador["+
      "producto:"+producto+", "+
      "cliente:"+cliente+", "+
      "precio:"+precio+", "+
      "actualizacion:"+actualizacion+""+
    "]";
  }

  private String producto = "";
  public String getProducto()
  {
    return producto;
  }
  public void setProducto(String producto)
  {
    this.producto = producto;
  }

  private String cliente = "";
  public String getCliente()
  {
    return cliente;
  }
  public void setCliente(String cliente)
  {
    this.cliente = cliente;
  }
  

  private String precio = "";
  public String getPrecio()
  {
    return precio;
  }
  public void setPrecio(String precio)
  {
    this.precio = precio;
  }
  

  private String actualizacion = "";
  public String getActualizacion()
  {
    return actualizacion;
  }
  public void setActualizacion(String actualizacion)
  {
    this.actualizacion = actualizacion;
  }
}
