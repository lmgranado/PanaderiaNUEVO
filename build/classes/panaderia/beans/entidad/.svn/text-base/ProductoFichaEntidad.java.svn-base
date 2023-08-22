package panaderia.beans.entidad;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import panaderia.struts.forms.EntidadBean;


public class ProductoFichaEntidad extends EntidadBean
{
  private static final long serialVersionUID = 1L;
  
  public static final String TABLE="producto_ficha";
  public static final String ALL_COLUMNS="producto_ficha.prodf_caracteristica_lote,producto_ficha.prodf_caracteristica_producto,producto_ficha.prodf_clasificacion_industria,producto_ficha.prodf_clasificacion_legislacion,producto_ficha.prodf_condicion_almacenamiento,producto_ficha.prodf_condicion_transporte,producto_ficha.prodf_consumo_preferente,producto_ficha.prodf_denominacion_comercial,producto_ficha.prodf_destino_producto,producto_ficha.prodf_envasado,producto_ficha.prodf_etiquetado,producto_ficha.prodf_formato,producto_ficha.prodf_id,producto_ficha.prodf_marca_comercial,producto_ficha.prodf_nombre_ficha,producto_ficha.prodf_procesado, producto_ficha.prodf_prod_id";
  public static final String ALL_COLUMNS_CONVERSOR ="fichas_productos.fprod_caracteristica_lote as prodf_caracteristica_lote,fichas_productos.fprod_caracteristica_producto as prodf_caracteristica_producto,fichas_productos.fprod_clasificacion_industria as prodf_clasificacion_industria,fichas_productos.fprod_clasificacion_legislacion as prodf_clasificacion_legislacion,fichas_productos.fprod_condicion_almacenamiento as prodf_condicion_almacenamiento,fichas_productos.fprod_condicion_transporte as prodf_condicion_transporte,fichas_productos.fprod_consumo_preferente as prodf_consumo_preferente,fichas_productos.fprod_denominacion_comercial as prodf_denominacion_comercial,fichas_productos.fprod_destino_producto as prodf_destino_producto,fichas_productos.fprod_envasado as prodf_envasado,fichas_productos.fprod_etiquetado as prodf_etiquetado,fichas_productos.fprod_formato as prodf_formato,fichas_productos.fprod_id as prodf_id,fichas_productos.fprod_marca_comercial as prodf_marca_comercial,fichas_productos.fprod_nombre_ficha as prodf_nombre_ficha,fichas_productos.fprod_procesado as prodf_procesado";
  public static final String PRODFCARACTERISTICALOTE="prodf_caracteristica_lote";
  public static final String PRODFCARACTERISTICAPRODUCTO="prodf_caracteristica_producto";
  public static final String PRODFCLASIFICACIONINDUSTRIA="prodf_clasificacion_industria";
  public static final String PRODFCLASIFICACIONLEGISLACION="prodf_clasificacion_legislacion";
  public static final String PRODFCONDICIONALMACENAMIENTO="prodf_condicion_almacenamiento";
  public static final String PRODFCONDICIONTRANSPORTE="prodf_condicion_transporte";
  public static final String PRODFCONSUMOPREFERENTE="prodf_consumo_preferente";
  public static final String PRODFDENOMINACIONCOMERCIAL="prodf_denominacion_comercial";
  public static final String PRODFDESTINOPRODUCTO="prodf_destino_producto";
  public static final String PRODFENVASADO="prodf_envasado";
  public static final String PRODFETIQUETADO="prodf_etiquetado";
  public static final String PRODFFORMATO="prodf_formato";
  public static final String PRODFID="prodf_id";
  public static final String PRODFMARCACOMERCIAL="prodf_marca_comercial";
  public static final String PRODFNOMBREFICHA="prodf_nombre_ficha";
  public static final String PRODFPROCESADO="prodf_procesado";
  public static final String PRODFPRODID="prodf_prod_id";
  public static final String PRIMARY_KEY="prodf_id";

  protected ProductoFichaEntidad(){}

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
    PreparedStatement sentencia = conexion.prepareStatement("UPDATE producto_ficha SET prodf_caracteristica_lote=?,prodf_caracteristica_producto=?,prodf_clasificacion_industria=?,prodf_clasificacion_legislacion=?,prodf_condicion_almacenamiento=?,prodf_condicion_transporte=?,prodf_consumo_preferente=?,prodf_denominacion_comercial=?,prodf_destino_producto=?,prodf_envasado=?,prodf_etiquetado=?,prodf_formato=?,prodf_id=?,prodf_marca_comercial=?,prodf_nombre_ficha=?,prodf_procesado=?,prodf_prod_id=? WHERE prodf_id=?");
    sentencia.setString(1, prodfCaracteristicaLote);
    sentencia.setString(2, prodfCaracteristicaProducto);
    sentencia.setString(3, prodfClasificacionIndustria);
    sentencia.setString(4, prodfClasificacionLegislacion);
    sentencia.setString(5, prodfCondicionAlmacenamiento);
    sentencia.setString(6, prodfCondicionTransporte);
    sentencia.setString(7, prodfConsumoPreferente);
    sentencia.setString(8, prodfDenominacionComercial);
    sentencia.setString(9, prodfDestinoProducto);
    sentencia.setString(10, prodfEnvasado);
    sentencia.setString(11, prodfEtiquetado);
    sentencia.setString(12, prodfFormato);
    sentencia.setString(13, prodfId);
    sentencia.setString(14, prodfMarcaComercial);
    sentencia.setString(15, prodfNombreFicha);
    sentencia.setString(16, prodfProcesado);
    sentencia.setString(17, prodfProdId);
    sentencia.setString(18, prodfId);
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
    PreparedStatement sentencia = conexion.prepareStatement("INSERT INTO producto_ficha (prodf_caracteristica_lote,prodf_caracteristica_producto,prodf_clasificacion_industria,prodf_clasificacion_legislacion,prodf_condicion_almacenamiento,prodf_condicion_transporte,prodf_consumo_preferente,prodf_denominacion_comercial,prodf_destino_producto,prodf_envasado,prodf_etiquetado,prodf_formato,prodf_marca_comercial,prodf_nombre_ficha,prodf_procesado,prodf_prod_id) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
    sentencia.setString(1, prodfCaracteristicaLote);
    sentencia.setString(2, prodfCaracteristicaProducto);
    sentencia.setString(3, prodfClasificacionIndustria);
    sentencia.setString(4, prodfClasificacionLegislacion);
    sentencia.setString(5, prodfCondicionAlmacenamiento);
    sentencia.setString(6, prodfCondicionTransporte);
    sentencia.setString(7, prodfConsumoPreferente);
    sentencia.setString(8, prodfDenominacionComercial);
    sentencia.setString(9, prodfDestinoProducto);
    sentencia.setString(10, prodfEnvasado);
    sentencia.setString(11, prodfEtiquetado);
    sentencia.setString(12, prodfFormato);
    //sentencia.setString(13, prodfId);
    sentencia.setString(13, prodfMarcaComercial);
    sentencia.setString(14, prodfNombreFicha);
    sentencia.setString(15, prodfProcesado);
    sentencia.setString(16, prodfProdId);
    
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
    PreparedStatement sentencia = conexion.prepareStatement("DELETE FROM producto_ficha WHERE prodf_id=?");
    sentencia.setString(1, prodfId);
    int n = sentencia.executeUpdate();
    sentencia.close();
    return n;
  }

  public String toString()
  {
    return "producto_ficha["+
      "prodfCaracteristicaLote:"+prodfCaracteristicaLote+", "+
      "prodfCaracteristicaProducto:"+prodfCaracteristicaProducto+", "+
      "prodfClasificacionIndustria:"+prodfClasificacionIndustria+", "+
      "prodfClasificacionLegislacion:"+prodfClasificacionLegislacion+", "+
      "prodfCondicionAlmacenamiento:"+prodfCondicionAlmacenamiento+", "+
      "prodfCondicionTransporte:"+prodfCondicionTransporte+", "+
      "prodfConsumoPreferente:"+prodfConsumoPreferente+", "+
      "prodfDenominacionComercial:"+prodfDenominacionComercial+", "+
      "prodfDestinoProducto:"+prodfDestinoProducto+", "+
      "prodfEnvasado:"+prodfEnvasado+", "+
      "prodfEtiquetado:"+prodfEtiquetado+", "+
      "prodfFormato:"+prodfFormato+", "+
      "prodfId:"+prodfId+", "+
      "prodfMarcaComercial:"+prodfMarcaComercial+", "+
      "prodfNombreFicha:"+prodfNombreFicha+", "+
      "prodfProcesado:"+prodfProcesado+
    "]";
  }

  private String prodfCaracteristicaLote = "";
  public String getProdfCaracteristicaLote()
  {
    return prodfCaracteristicaLote;
  }
  public void setProdfCaracteristicaLote(String prodfCaracteristicaLote)
  {
    this.prodfCaracteristicaLote = prodfCaracteristicaLote;
  }

  private String prodfCaracteristicaProducto = "";
  public String getProdfCaracteristicaProducto()
  {
    return prodfCaracteristicaProducto;
  }
  public void setProdfCaracteristicaProducto(String prodfCaracteristicaProducto)
  {
    this.prodfCaracteristicaProducto = prodfCaracteristicaProducto;
  }

  private String prodfClasificacionIndustria = "";
  public String getProdfClasificacionIndustria()
  {
    return prodfClasificacionIndustria;
  }
  public void setProdfClasificacionIndustria(String prodfClasificacionIndustria)
  {
    this.prodfClasificacionIndustria = prodfClasificacionIndustria;
  }

  private String prodfClasificacionLegislacion = "";
  public String getProdfClasificacionLegislacion()
  {
    return prodfClasificacionLegislacion;
  }
  public void setProdfClasificacionLegislacion(String prodfClasificacionLegislacion)
  {
    this.prodfClasificacionLegislacion = prodfClasificacionLegislacion;
  }

  private String prodfCondicionAlmacenamiento = "";
  public String getProdfCondicionAlmacenamiento()
  {
    return prodfCondicionAlmacenamiento;
  }
  public void setProdfCondicionAlmacenamiento(String prodfCondicionAlmacenamiento)
  {
    this.prodfCondicionAlmacenamiento = prodfCondicionAlmacenamiento;
  }

  private String prodfCondicionTransporte = "";
  public String getProdfCondicionTransporte()
  {
    return prodfCondicionTransporte;
  }
  public void setProdfCondicionTransporte(String prodfCondicionTransporte)
  {
    this.prodfCondicionTransporte = prodfCondicionTransporte;
  }

  private String prodfConsumoPreferente = "";
  public String getProdfConsumoPreferente()
  {
    return prodfConsumoPreferente;
  }
  public void setProdfConsumoPreferente(String prodfConsumoPreferente)
  {
    this.prodfConsumoPreferente = prodfConsumoPreferente;
  }

  private String prodfDenominacionComercial = "";
  public String getProdfDenominacionComercial()
  {
    return prodfDenominacionComercial;
  }
  public void setProdfDenominacionComercial(String prodfDenominacionComercial)
  {
    this.prodfDenominacionComercial = prodfDenominacionComercial;
  }

  private String prodfDestinoProducto = "";
  public String getProdfDestinoProducto()
  {
    return prodfDestinoProducto;
  }
  public void setProdfDestinoProducto(String prodfDestinoProducto)
  {
    this.prodfDestinoProducto = prodfDestinoProducto;
  }

  private String prodfEnvasado = "";
  public String getProdfEnvasado()
  {
    return prodfEnvasado;
  }
  public void setProdfEnvasado(String prodfEnvasado)
  {
    this.prodfEnvasado = prodfEnvasado;
  }

  private String prodfEtiquetado = "";
  public String getProdfEtiquetado()
  {
    return prodfEtiquetado;
  }
  public void setProdfEtiquetado(String prodfEtiquetado)
  {
    this.prodfEtiquetado = prodfEtiquetado;
  }

  private String prodfFormato = "";
  public String getProdfFormato()
  {
    return prodfFormato;
  }
  public void setProdfFormato(String prodfFormato)
  {
    this.prodfFormato = prodfFormato;
  }

  private String prodfId = "";
  public String getProdfId()
  {
    return prodfId;
  }
  public void setProdfId(String prodfId)
  {
    this.prodfId = prodfId;
    this.pk_identificador_ = prodfId;
  }

  private String prodfMarcaComercial = "";
  public String getProdfMarcaComercial()
  {
    return prodfMarcaComercial;
  }
  public void setProdfMarcaComercial(String prodfMarcaComercial)
  {
    this.prodfMarcaComercial = prodfMarcaComercial;
  }

  private String prodfNombreFicha = "";
  public String getProdfNombreFicha()
  {
    return prodfNombreFicha;
  }
  public void setProdfNombreFicha(String prodfNombreFicha)
  {
    this.prodfNombreFicha = prodfNombreFicha;
  }

  private String prodfProcesado = "";
  public String getProdfProcesado()
  {
    return prodfProcesado;
  }
  public void setProdfProcesado(String prodfProcesado)
  {
    this.prodfProcesado = prodfProcesado;
  }
  
  private String prodfProdId = "";
  public String getProdfProdId()
  {
    return prodfProdId;
  }
  public void setProdfProdId(String prodfProdId)
  {
    this.prodfProdId = prodfProdId;
  }
}
