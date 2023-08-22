package panaderia.beans.entidad;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import panaderia.beans.NotasEntregaDetalle;
import panaderia.beans.Stock;
import panaderia.struts.forms.EntidadBean;
import utils.Utils;


public class NotasEntregaEntidad extends EntidadBean
{
  private static final long serialVersionUID = 1L;
  
  public static final String TABLE="notas_entrega";
  public static final String ALL_COLUMNS="notas_entrega.not_cl_id,notas_entrega.not_fecha,notas_entrega.not_id,notas_entrega.not_nombre,notas_entrega.not_observaciones,notas_entrega.not_orden,notas_entrega.not_rut_id,notas_entrega.not_cierre,notas_entrega.not_viaje,notas_entrega.not_cu_id";
  public static final String NOTCLID="not_cl_id";
  public static final String NOTFECHA="not_fecha";
  public static final String NOTID="not_id";
  public static final String NOTNOMBRE="not_nombre";
  public static final String NOTOBSERVACIONES="not_observaciones";
  public static final String NOTORDEN="not_orden";
  public static final String NOTRUTID="not_rut_id";
  public static final String NOTCIERRE="not_cierre";
  public static final String NOTVIAJE="not_viaje";
  public static final String NOTCUID="not_cu_id";
  public static final String PRIMARY_KEY="not_id";

  protected NotasEntregaEntidad(){}

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
    PreparedStatement sentencia = conexion.prepareStatement("UPDATE notas_entrega SET not_cl_id=?,not_fecha=?,not_id=?,not_nombre=?,not_observaciones=?,not_orden=?,not_rut_id=?,not_cierre=?,not_viaje=? WHERE not_id=?");
    sentencia.setString(1, notClId);
    sentencia.setTimestamp(2, notFecha);
    sentencia.setString(3, notId);
    sentencia.setString(4, notNombre);
    sentencia.setString(5, notObservaciones);
    sentencia.setString(6, notOrden);
    sentencia.setString(7, notRutId);
    sentencia.setString(8, notCierre);
    sentencia.setString(9, notViaje);
    sentencia.setString(10, notId);
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
    PreparedStatement sentencia = conexion.prepareStatement("INSERT INTO notas_entrega (not_cl_id,not_fecha,not_nombre,not_observaciones,not_orden,not_rut_id,not_viaje,not_cu_id) VALUES (?,?,?,?,?,?,?,?)");
    sentencia.setString(1, notClId);
    sentencia.setTimestamp(2, notFecha);
    sentencia.setString(3, notNombre);
    sentencia.setString(4, notObservaciones);
    sentencia.setString(5, notOrden);
    sentencia.setString(6, notRutId);
    sentencia.setString(7, notViaje);
    sentencia.setString(8, notCuId);
    
    sentencia.executeUpdate();
    int autoIncKeyFromApi = -1;

    ResultSet rs = sentencia.getGeneratedKeys();

    if (rs.next()) {
        autoIncKeyFromApi = rs.getInt(1);
    } else {

        // throw an exception from here
    }
    
    sentencia.close();
    return autoIncKeyFromApi;
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
	ArrayList listaDetalles = NotasEntregaDetalle.getNotasEntregaDetalleByNotdetNotId(notId);
	int resultado = 0;
    for(int i=0; i<listaDetalles.size(); i++){
    	NotasEntregaDetalle notaDetalle = (NotasEntregaDetalle)listaDetalles.get(i);
    	//Primero el stock 
    	if(!Utils.skipNull(notaDetalle.getNotdetStId()).equals("")){
			Stock stock = Stock.getStockByStId(notaDetalle.getNotdetStId());
			stock.setStCantidadFinal(Double.toString(Double.parseDouble(stock.getStCantidadFinal()) + Double.parseDouble(notaDetalle.getNotdetCantidad())));
			stock.actualiza(conexion);
    	}
    	resultado = notaDetalle.elimina(conexion);
    }
    int n = 0;
    if(resultado>=0){
	PreparedStatement sentencia = conexion.prepareStatement("DELETE FROM notas_entrega WHERE not_id=?");
    sentencia.setString(1, notId);
    n = sentencia.executeUpdate();
    sentencia.close();
    }
    return n;
  }

  public String toString()
  {
    return "notas_entrega["+
      "notClId:"+notClId+", "+
      "notFecha:"+utils.Utils.date2String(notFecha, "dd/MM/yyyy HH:mm:ss")+", "+
      "notId:"+notId+", "+
      "notNombre:"+notNombre+", "+
      "notObservaciones:"+notObservaciones+", "+
      "notOrden:"+notOrden+", "+
      "notViaje:"+notViaje+", "+
      "notRutId:"+notRutId+
    "]";
  }

  private String notClId = "";
  public String getNotClId()
  {
    return notClId;
  }
  public void setNotClId(String notClId)
  {
    this.notClId = notClId;
  }

  private java.sql.Timestamp notFecha = null;
  public java.util.Date getNotFecha()
  {
    if(notFecha==null){ return null; }
    else{ return notFecha; }
  }
  public void setNotFecha(java.sql.Timestamp notFecha)
  {
    if(notFecha==null){ this.notFecha = null; }
    else{ this.notFecha = new java.sql.Timestamp(notFecha.getTime()); }
  }
  public String getNotFecha(String formato)
  {
    return utils.Utils.date2String(notFecha, formato);
  }
  public void setNotFecha(String notFecha, String formato)
  {
    if( notFecha==null || notFecha.equals("") )
    { this.notFecha = null; }
    else
    {
      try
      {
        this.notFecha = new java.sql.Timestamp(utils.Utils.string2Date(notFecha, formato).getTime());
      }
      catch(java.text.ParseException e){ utils.Log4j.error("Error", e); }
    }
  }

  private String notId = "";
  public String getNotId()
  {
    return notId;
  }
  public void setNotId(String notId)
  {
    this.notId = notId;
    this.pk_identificador_ = notId;
  }

  private String notNombre = "";
  public String getNotNombre()
  {
    return notNombre;
  }
  public void setNotNombre(String notNombre)
  {
    this.notNombre = notNombre;
  }

  private String notObservaciones = "";
  public String getNotObservaciones()
  {
    return notObservaciones;
  }
  public void setNotObservaciones(String notObservaciones)
  {
    this.notObservaciones = notObservaciones;
  }

  private String notOrden = "";
  public String getNotOrden()
  {
    return notOrden;
  }
  public void setNotOrden(String notOrden)
  {
    this.notOrden = notOrden;
  }

  private String notRutId = "";
  public String getNotRutId()
  {
    return notRutId;
  }
  public void setNotRutId(String notRutId)
  {
    this.notRutId = notRutId;
  }
 
  private String notCierre = "";
  public String getNotCierre()
  {
    return notCierre;
  }
  public void setNotCierre(String notCierre)
  {
    this.notCierre = notCierre;
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
  
  private String notViaje = "";
  public String getNotViaje()
  {
    return notViaje;
  }
  public void setNotViaje(String notViaje)
  {
    this.notViaje = notViaje;
  }
  
  private String notCuId = "";
  public String getNotCuId()
  {
    return notCuId;
  }
  public void setNotCuId(String notCuId)
  {
    this.notCuId = notCuId;
  }
}
