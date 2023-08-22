package panaderia.beans.entidad;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import panaderia.struts.forms.EntidadBean;


public class FichasProductosEntidad extends EntidadBean
{
  private static final long serialVersionUID = 1L;
  
  public static final String TABLE="fichas_productos";
  public static final String ALL_COLUMNS="fichas_productos.fprod_caracteristica_lote,fichas_productos.fprod_caracteristica_producto,fichas_productos.fprod_clasificacion_industria,fichas_productos.fprod_clasificacion_legislacion,fichas_productos.fprod_condicion_almacenamiento,fichas_productos.fprod_condicion_transporte,fichas_productos.fprod_consumo_preferente,fichas_productos.fprod_denominacion_comercial,fichas_productos.fprod_destino_producto,fichas_productos.fprod_envasado,fichas_productos.fprod_etiquetado,fichas_productos.fprod_formato,fichas_productos.fprod_id,fichas_productos.fprod_marca_comercial,fichas_productos.fprod_nombre_ficha,fichas_productos.fprod_procesado";
  public static final String FPRODCARACTERISTICALOTE="fprod_caracteristica_lote";
  public static final String FPRODCARACTERISTICAPRODUCTO="fprod_caracteristica_producto";
  public static final String FPRODCLASIFICACIONINDUSTRIA="fprod_clasificacion_industria";
  public static final String FPRODCLASIFICACIONLEGISLACION="fprod_clasificacion_legislacion";
  public static final String FPRODCONDICIONALMACENAMIENTO="fprod_condicion_almacenamiento";
  public static final String FPRODCONDICIONTRANSPORTE="fprod_condicion_transporte";
  public static final String FPRODCONSUMOPREFERENTE="fprod_consumo_preferente";
  public static final String FPRODDENOMINACIONCOMERCIAL="fprod_denominacion_comercial";
  public static final String FPRODDESTINOPRODUCTO="fprod_destino_producto";
  public static final String FPRODENVASADO="fprod_envasado";
  public static final String FPRODETIQUETADO="fprod_etiquetado";
  public static final String FPRODFORMATO="fprod_formato";
  public static final String FPRODID="fprod_id";
  public static final String FPRODMARCACOMERCIAL="fprod_marca_comercial";
  public static final String FPRODNOMBREFICHA="fprod_nombre_ficha";
  public static final String FPRODPROCESADO="fprod_procesado";
  public static final String PRIMARY_KEY="fprod_id";

  protected FichasProductosEntidad(){}

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
    PreparedStatement sentencia = conexion.prepareStatement("UPDATE fichas_productos SET fprod_caracteristica_lote=?,fprod_caracteristica_producto=?,fprod_clasificacion_industria=?,fprod_clasificacion_legislacion=?,fprod_condicion_almacenamiento=?,fprod_condicion_transporte=?,fprod_consumo_preferente=?,fprod_denominacion_comercial=?,fprod_destino_producto=?,fprod_envasado=?,fprod_etiquetado=?,fprod_formato=?,fprod_id=?,fprod_marca_comercial=?,fprod_nombre_ficha=?,fprod_procesado=? WHERE fprod_id=?");
    sentencia.setString(1, fprodCaracteristicaLote);
    sentencia.setString(2, fprodCaracteristicaProducto);
    sentencia.setString(3, fprodClasificacionIndustria);
    sentencia.setString(4, fprodClasificacionLegislacion);
    sentencia.setString(5, fprodCondicionAlmacenamiento);
    sentencia.setString(6, fprodCondicionTransporte);
    sentencia.setString(7, fprodConsumoPreferente);
    sentencia.setString(8, fprodDenominacionComercial);
    sentencia.setString(9, fprodDestinoProducto);
    sentencia.setString(10, fprodEnvasado);
    sentencia.setString(11, fprodEtiquetado);
    sentencia.setString(12, fprodFormato);
    sentencia.setString(13, fprodId);
    sentencia.setString(14, fprodMarcaComercial);
    sentencia.setString(15, fprodNombreFicha);
    sentencia.setString(16, fprodProcesado);
    sentencia.setString(17, fprodId);
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
    PreparedStatement sentencia = conexion.prepareStatement("INSERT INTO fichas_productos (fprod_caracteristica_lote,fprod_caracteristica_producto,fprod_clasificacion_industria,fprod_clasificacion_legislacion,fprod_condicion_almacenamiento,fprod_condicion_transporte,fprod_consumo_preferente,fprod_denominacion_comercial,fprod_destino_producto,fprod_envasado,fprod_etiquetado,fprod_formato,fprod_marca_comercial,fprod_nombre_ficha,fprod_procesado) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
    sentencia.setString(1, fprodCaracteristicaLote);
    sentencia.setString(2, fprodCaracteristicaProducto);
    sentencia.setString(3, fprodClasificacionIndustria);
    sentencia.setString(4, fprodClasificacionLegislacion);
    sentencia.setString(5, fprodCondicionAlmacenamiento);
    sentencia.setString(6, fprodCondicionTransporte);
    sentencia.setString(7, fprodConsumoPreferente);
    sentencia.setString(8, fprodDenominacionComercial);
    sentencia.setString(9, fprodDestinoProducto);
    sentencia.setString(10, fprodEnvasado);
    sentencia.setString(11, fprodEtiquetado);
    sentencia.setString(12, fprodFormato);
    //sentencia.setString(13, fprodId);
    sentencia.setString(13, fprodMarcaComercial);
    sentencia.setString(14, fprodNombreFicha);
    sentencia.setString(15, fprodProcesado);
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
    PreparedStatement sentencia = conexion.prepareStatement("DELETE FROM fichas_productos WHERE fprod_id=?");
    sentencia.setString(1, fprodId);
    int n = sentencia.executeUpdate();
    sentencia.close();
    return n;
  }

  public String toString()
  {
    return "fichas_productos["+
      "fprodCaracteristicaLote:"+fprodCaracteristicaLote+", "+
      "fprodCaracteristicaProducto:"+fprodCaracteristicaProducto+", "+
      "fprodClasificacionIndustria:"+fprodClasificacionIndustria+", "+
      "fprodClasificacionLegislacion:"+fprodClasificacionLegislacion+", "+
      "fprodCondicionAlmacenamiento:"+fprodCondicionAlmacenamiento+", "+
      "fprodCondicionTransporte:"+fprodCondicionTransporte+", "+
      "fprodConsumoPreferente:"+fprodConsumoPreferente+", "+
      "fprodDenominacionComercial:"+fprodDenominacionComercial+", "+
      "fprodDestinoProducto:"+fprodDestinoProducto+", "+
      "fprodEnvasado:"+fprodEnvasado+", "+
      "fprodEtiquetado:"+fprodEtiquetado+", "+
      "fprodFormato:"+fprodFormato+", "+
      "fprodId:"+fprodId+", "+
      "fprodMarcaComercial:"+fprodMarcaComercial+", "+
      "fprodNombreFicha:"+fprodNombreFicha+", "+
      "fprodProcesado:"+fprodProcesado+
    "]";
  }

  private String fprodCaracteristicaLote = "";
  public String getFprodCaracteristicaLote()
  {
    return fprodCaracteristicaLote;
  }
  public void setFprodCaracteristicaLote(String fprodCaracteristicaLote)
  {
    this.fprodCaracteristicaLote = fprodCaracteristicaLote;
  }

  private String fprodCaracteristicaProducto = "";
  public String getFprodCaracteristicaProducto()
  {
    return fprodCaracteristicaProducto;
  }
  public void setFprodCaracteristicaProducto(String fprodCaracteristicaProducto)
  {
    this.fprodCaracteristicaProducto = fprodCaracteristicaProducto;
  }

  private String fprodClasificacionIndustria = "";
  public String getFprodClasificacionIndustria()
  {
    return fprodClasificacionIndustria;
  }
  public void setFprodClasificacionIndustria(String fprodClasificacionIndustria)
  {
    this.fprodClasificacionIndustria = fprodClasificacionIndustria;
  }

  private String fprodClasificacionLegislacion = "";
  public String getFprodClasificacionLegislacion()
  {
    return fprodClasificacionLegislacion;
  }
  public void setFprodClasificacionLegislacion(String fprodClasificacionLegislacion)
  {
    this.fprodClasificacionLegislacion = fprodClasificacionLegislacion;
  }

  private String fprodCondicionAlmacenamiento = "";
  public String getFprodCondicionAlmacenamiento()
  {
    return fprodCondicionAlmacenamiento;
  }
  public void setFprodCondicionAlmacenamiento(String fprodCondicionAlmacenamiento)
  {
    this.fprodCondicionAlmacenamiento = fprodCondicionAlmacenamiento;
  }

  private String fprodCondicionTransporte = "";
  public String getFprodCondicionTransporte()
  {
    return fprodCondicionTransporte;
  }
  public void setFprodCondicionTransporte(String fprodCondicionTransporte)
  {
    this.fprodCondicionTransporte = fprodCondicionTransporte;
  }

  private String fprodConsumoPreferente = "";
  public String getFprodConsumoPreferente()
  {
    return fprodConsumoPreferente;
  }
  public void setFprodConsumoPreferente(String fprodConsumoPreferente)
  {
    this.fprodConsumoPreferente = fprodConsumoPreferente;
  }

  private String fprodDenominacionComercial = "";
  public String getFprodDenominacionComercial()
  {
    return fprodDenominacionComercial;
  }
  public void setFprodDenominacionComercial(String fprodDenominacionComercial)
  {
    this.fprodDenominacionComercial = fprodDenominacionComercial;
  }

  private String fprodDestinoProducto = "";
  public String getFprodDestinoProducto()
  {
    return fprodDestinoProducto;
  }
  public void setFprodDestinoProducto(String fprodDestinoProducto)
  {
    this.fprodDestinoProducto = fprodDestinoProducto;
  }

  private String fprodEnvasado = "";
  public String getFprodEnvasado()
  {
    return fprodEnvasado;
  }
  public void setFprodEnvasado(String fprodEnvasado)
  {
    this.fprodEnvasado = fprodEnvasado;
  }

  private String fprodEtiquetado = "";
  public String getFprodEtiquetado()
  {
    return fprodEtiquetado;
  }
  public void setFprodEtiquetado(String fprodEtiquetado)
  {
    this.fprodEtiquetado = fprodEtiquetado;
  }

  private String fprodFormato = "";
  public String getFprodFormato()
  {
    return fprodFormato;
  }
  public void setFprodFormato(String fprodFormato)
  {
    this.fprodFormato = fprodFormato;
  }

  private String fprodId = "";
  public String getFprodId()
  {
    return fprodId;
  }
  public void setFprodId(String fprodId)
  {
    this.fprodId = fprodId;
    this.pk_identificador_ = fprodId;
  }

  private String fprodMarcaComercial = "";
  public String getFprodMarcaComercial()
  {
    return fprodMarcaComercial;
  }
  public void setFprodMarcaComercial(String fprodMarcaComercial)
  {
    this.fprodMarcaComercial = fprodMarcaComercial;
  }

  private String fprodNombreFicha = "";
  public String getFprodNombreFicha()
  {
    return fprodNombreFicha;
  }
  public void setFprodNombreFicha(String fprodNombreFicha)
  {
    this.fprodNombreFicha = fprodNombreFicha;
  }

  private String fprodProcesado = "";
  public String getFprodProcesado()
  {
    return fprodProcesado;
  }
  public void setFprodProcesado(String fprodProcesado)
  {
    this.fprodProcesado = fprodProcesado;
  }
}
