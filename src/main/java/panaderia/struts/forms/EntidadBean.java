package panaderia.struts.forms;

import java.lang.reflect.*;
import java.math.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import javax.servlet.http.*;

import org.apache.struts.action.ActionForm;

import utils.Log4j;
import utils.Utils;

/**La clase EntidadBean contiene algunas funcionalidades y propiedades imprescindibles para la
 * ejecución de la aplicación.
 *
 * <p>Es la superclase de todas las que pertenecen al paquete comun.*, y así todas ellas
 * heredan sus métodos y atributos.</p>
 *
 * @version 1.0
 * @author (c)Guadal·TEL, s.a.  */
public class EntidadBean extends ActionForm implements java.io.Serializable
{
  private static final long serialVersionUID = 1L;

  public String pk_identificador_="";
  public boolean nuevo = false;
  private boolean checked = false;
  static private Locale locale = new Locale("es", "ES" );
  static private NumberFormat numberFormatter = null;

  static
  {
    numberFormatter = NumberFormat.getInstance( locale );
    numberFormatter.setMinimumFractionDigits(2);
    numberFormatter.setMaximumFractionDigits(2);
  }

  /**
   * Compara dos objetos que hereden de la clase EntidadBean. Ambos objetos
   * deben pertenecer a la misma clase, en cuyo caso, se considerará que son
   * iguales si el valor de sus claves primarias lo es (no se tiene en cuenta
   * el resto de campos de la clase)
   *
   * @param obj Objeto con el que se quiere comparar
   * @return true o false en funcion si ambos objetos son de la misma clase,
   * y en ese caso, si sus claves primarias son iguales tambien.
   */
  public boolean equals(Object obj)
  {
    if( this.getClass().isInstance(obj) )
    {
      EntidadBean objEntidad = (EntidadBean) obj;
      return this.pk_identificador_.equals(objEntidad.pk_identificador_);
    }
    else
    {
      return false;
    }
  }

	public boolean isChecked() {
		return checked;
	}



	public void setChecked(boolean checked) {
		this.checked = checked;
	}

  public static NumberFormat getNumberFormat()
  {
    return numberFormatter;
  }

  public static int indice (ArrayList lista, String pk)
  {
    int nElementos = lista.size();
    for( int i = 0; i < nElementos; i++ )
    {
      EntidadBean elemento = (EntidadBean) lista.get(i);
      if( elemento.pk_identificador_.equals(pk) )
      {
        return i;
      }
    }

    return -1;
  }
  /**Realiza una consulta a la base de datos, y se obtiene un vector de objetos inicializados
   * a los valores devueltos por la consulta.
   * <p> La clase de los objetos será la misma que la del que hizo la llamada a este método.
   * <br> Y la inicialización es posible gracias a la propiedad de Java de la <i>reflexión de código</i>.
   * Esto es que, por ejemplo, si se realiza una consulta que devuelva el campo 'nombre' de una
   * tabla, este método actualizará al atributo llamado 'nombre' a través del método público
   * 'setNombre' de la clase que llamó a consultaAVectorReflexiva.
   * <p> La reflexión de código de Java precisamente lo que nos permite es tener un mecanismo de
   * ejecución de métodos de una clase especificando su nombre, en lugar de hacer la llamada
   * explicita (como es más normal) del método.
   * @param sql es la consulta a hacer a la base de datos, y deberá consultar tantos
   * campos como propiedades de nuestro bean queramos inicializar.
   * @return Devuelve un java.util.Vector de objetos inicilizados a los valores devueltos por la consulta
   * */
  
  public ArrayList consultaAVectorReflexiva(String sql)
  {
    ArrayList listaRegistros = new ArrayList();
    Connection conexion = utils.PoolConexiones.conexion();
    Statement sentencia = null;
    ResultSet resultado = null;
    try
    {

      sentencia         = conexion.createStatement();
      resultado         = sentencia.executeQuery( sql );
      ResultSetMetaData metaInfoColumnas  = resultado.getMetaData();

      int               nColumnas         = metaInfoColumnas.getColumnCount();

      Method            listaMetodos[]    = new Method[nColumnas];

      Class claseString = null;
      Class claseDate   = null;
      Class claseTimeStamp   = null;
      try
      {
        claseString = Class.forName("java.lang.String");
        claseDate   = Class.forName("java.util.Date");
        claseTimeStamp   = Class.forName("java.sql.Timestamp");
      }
      catch (ClassNotFoundException e){ Log4j.error("Error:"+e); }

      // Se recuperan los metodos para cada columna
      for( int i = 1; i <= nColumnas; i++ )
      {
        String  nombreColumna = metaInfoColumnas.getColumnName(i);
        String  tipoColumna   = metaInfoColumnas.getColumnTypeName(i);
        try
        {
          if( tipoColumna.equals("DATE") )
          {
            listaMetodos[i-1] = obtenerMetodo( "set"+conversionCampoAMetodo(nombreColumna), claseDate );
          }
          else
          {
            listaMetodos[i-1] = obtenerMetodo( "set"+conversionCampoAMetodo(nombreColumna), claseString );
          }
        }
        catch( NoSuchMethodException e )
        {
        	if(tipoColumna.equals("DATE")){
	          try{
	        	  listaMetodos[i-1] = obtenerMetodo( "set"+conversionCampoAMetodo(nombreColumna), claseTimeStamp );
	          }catch (NoSuchMethodException ec) {
	        	  Log4j.error("[consultaAVectorReflexiva] Error [nombreColumna:"+nombreColumna+", tipoColumna:"+tipoColumna+"]:"+e);
	              listaMetodos[i-1] = null;
	          }
        	}else{
        		Log4j.error("[consultaAVectorReflexiva] Error [nombreColumna:"+nombreColumna+", tipoColumna:"+tipoColumna+"]:"+e);
	            listaMetodos[i-1] = null;
        	}
        }
      }

      // Ahora se recorren cada uno de los registros
      Object  [] valoresParametros = new Object[1];
      while( resultado.next() )
      {
        try
        {
          Object registro = this.getClass().newInstance();
          for( int i = 1; i <= nColumnas; i++ )
          {
            String nombreColumna  = metaInfoColumnas.getColumnName(i);
            String tipoColumna    = metaInfoColumnas.getColumnTypeName(i);
            valoresParametros[0] = resultado.getString(i);
            if( tipoColumna.equals("DATE") )
            {
              valoresParametros[0] = resultado.getTimestamp(i);
            }
            else
            {
              if( valoresParametros[0]==null )
              {
                valoresParametros[0] = "";
              }
              else if( tipoColumna.equals("NUMBER") && metaInfoColumnas.getScale(i) > 0 )
              {
                valoresParametros[0]  = numberFormatter.format(resultado.getDouble(i));
              }
            }

            if( listaMetodos[i-1] != null )
            {
              try
              {
                listaMetodos[i-1].invoke( registro, valoresParametros );
              }
              catch (Exception e){ Log4j.error("[consultaAVectorReflexiva] Error [nombreColumna:"+nombreColumna+", tipoColumna:"+tipoColumna+"]:"+e); }
            }
          }
          listaRegistros.add(registro);
        }
        catch (InstantiationException e){ Log4j.error("Error " + e); }
        catch (IllegalAccessException e){ Log4j.error("Error " + e); }
      }

      resultado.close();
      sentencia.close();
    }
    catch( SQLException e ){ Log4j.error("Error " + e); }
    finally{ utils.PoolConexiones.cerrarConexion(conexion, sentencia, resultado); }

    return listaRegistros;
  }


  /**Realiza una consulta a la base de datos, e inicializa las propiedades del bean a
   * los valores devueltos por la consulta.
   * <p> La inicialización es posible gracias a la propiedad de Java de la <i>reflexión de código</i>.
   * Esto es que, por ejemplo, si se realiza una consulta que devuelva el campo 'nombre' de una
   * tabla, este método actualizará al atributo llamado 'nombre' a través del método público
   * 'setNombre' de la clase que llamó a consultaAVectorReflexiva.
   * <p> La reflexión de código de Java precisamente lo que nos permite es tener un mecanismo de
   * ejecución de métodos de una clase especificando su nombre, en lugar de hacer la llamada
   * explicita (como es más normal) del método.
   * <p>Si la consulta devolviera varios registros, se tomará el primero para la inicialización.
   * @param sql es la consulta a hacer a la base de datos, y deberá consultar tantos
   * campos como propiedades de nuestro bean queramos inicializar.
   * */
  public void consultaReflexiva(String sql)
  {
	  Connection conexion = utils.PoolConexiones.conexion();
	  Statement sentencia = null;
	  ResultSet resultado = null; 
    try
    {
      sentencia     = conexion.createStatement();
      resultado     = sentencia.executeQuery( sql );
      ResultSetMetaData metaInfoData  = resultado.getMetaData();

      // Si se ha encontrado al menos un registros, entonces se lee el primero y se retorna
      if( resultado.next() )
      {
        Object  []valoresParametros = new Object[1]; //parametros para el metodo a invocar

        Class claseString = null;
        Class claseDate   = null;
        Class claseTimeStamp = null;
        try
        {
          claseString = Class.forName("java.lang.String");
          claseDate   = Class.forName("java.util.Date");
          claseTimeStamp   = Class.forName("java.sql.Timestamp");
        }
        catch (ClassNotFoundException e){ Log4j.error("Error:"+e); }

        int nColumnas = metaInfoData.getColumnCount();
        for( int i = 1; i <= nColumnas; i++ )
        {
          // Primero se lee el valor de la columna
          String tipoColumna = metaInfoData.getColumnTypeName(i);
          valoresParametros[0] = resultado.getString(i);
          if( tipoColumna.equals("DATE") )
          {
            valoresParametros[0] = resultado.getTimestamp(i);
          }
          else
          {
            if( valoresParametros[0]==null )
            {
              valoresParametros[0] = "";
            }
            else if( tipoColumna.equals("NUMBER") && metaInfoData.getScale(i) > 0 )
            {
              valoresParametros[0]  = numberFormatter.format(resultado.getDouble(i));
            }
          }

          // Ahora se llama al metodo correspondiente que establece el valor del atributo correspondiente
          String nombreColumna  = metaInfoData.getColumnName(i);
          Method metodo = null;
          try
          {
            if( tipoColumna.equals("DATE") )
            {
              metodo = obtenerMetodo( "set"+conversionCampoAMetodo(nombreColumna), claseDate );
            }
            else
            {
              metodo = obtenerMetodo( "set"+conversionCampoAMetodo(nombreColumna), claseString );
            }
            metodo.invoke( this, valoresParametros );
          }
          catch (Exception e){ 
        	  if( tipoColumna.equals("DATE") ){
	        	  try{
	        		  metodo = obtenerMetodo( "set"+conversionCampoAMetodo(nombreColumna), claseTimeStamp );
	        		  metodo.invoke( this, valoresParametros );
	        	  }catch (Exception ec) {
	        		  Log4j.error("[consultaReflexiva] Error [nombreColumna:"+nombreColumna+", tipoColumna:"+tipoColumna+"]:"+e);
	        	  }
        	  }else{
        		  Log4j.error("[consultaReflexiva] Error [nombreColumna:"+nombreColumna+", tipoColumna:"+tipoColumna+"]:"+e);
        	  }
          }
        }
      }
      resultado.close();
      sentencia.close();
    }
    catch (SQLException e){ Log4j.error("Error " + e); }
    finally{ utils.PoolConexiones.cerrarConexion(conexion, sentencia, resultado); }
  }
  
  protected Object rsToBean(ResultSet rs) throws Exception {
	  return new Object();
  }
    
  public Object[] consultaAVectorReflexiva(String sql, int maxResult, int firstResult)
  {
    ArrayList listaRegistros = new ArrayList();
    Object[] arrayResultado = new Object[2];
    Connection conexion = utils.PoolConexiones.conexion();
    Statement sentencia = null;
    ResultSet resultado = null;
    try
    {

      sentencia         = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

      resultado         = sentencia.executeQuery( sql );
      
      //Primero obtenemos el numero de registros totales.
      resultado.last();
      int numTotal = resultado.getRow();
      resultado.beforeFirst();
      arrayResultado[0] = Integer.toString(numTotal);
      
      if(firstResult!=0) resultado.absolute(firstResult);

      //en el caso en que queramos todos los registros ponemos el maxresult como -1
      if(maxResult==-1) maxResult = numTotal;
      
      int k=1;
      while( resultado.next() )
      {
    	if(k>maxResult) break;
        try
        {
          k++;
          listaRegistros.add(this.rsToBean(resultado));
        }
        catch (Exception e){ Log4j.error("Error " + e); }
      }
                    
      resultado.close();
      sentencia.close();
      
      arrayResultado[1] = listaRegistros;
    }
    catch( Exception e ){ Log4j.error("Error " + e); }
    finally{ utils.PoolConexiones.cerrarConexion(conexion, sentencia, resultado); }

    return arrayResultado;
  }
  
  public Object[] consultaAVectorReflexivaEspPaginacion(String sql, int maxResult, int firstResult)
  {
    ArrayList listaRegistros = new ArrayList();
    Object[] arrayResultado = new Object[2];
    Connection conexion = utils.PoolConexiones.conexion();
    Statement sentencia = null;
    Statement sentenciaCount = null;
    ResultSet resultado = null;
    ResultSet resultadoCount = null;
    try
    {
      sentencia         = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
	  sql = sql.toLowerCase().replaceFirst("select", "select SQL_CALC_FOUND_ROWS ");
	  if(maxResult!=-1){
		  sql += " limit " + firstResult + "," +maxResult;
	  }
      resultado         = sentencia.executeQuery( sql );

      sentenciaCount         = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
      resultadoCount         = sentenciaCount.executeQuery( "SELECT FOUND_ROWS() as cnt" );
      while( resultadoCount.next() )
      {
    	  arrayResultado[0] = Integer.toString(resultadoCount.getInt("cnt"));
      }
      
      while( resultado.next() )
      {
        try
        {
          listaRegistros.add(this.rsToBean(resultado));
        }
        catch (Exception e){ Log4j.error("Error " + e); }
      }
                    
      resultado.close();
      sentencia.close();
      
      arrayResultado[1] = listaRegistros;
    }
    catch( Exception e ){ Log4j.error("Error " + e); }
    finally{ utils.PoolConexiones.cerrarConexion(conexion, sentencia, resultado); }

    return arrayResultado;
  }
  
  public ArrayList consultaAVectorReflexivaRsToBean(String sql)
  {
    ArrayList listaRegistros = new ArrayList();
    Connection conexion = utils.PoolConexiones.conexion();
    Statement sentencia = null;
    ResultSet resultado = null;
    try
    {

      sentencia         = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

      resultado         = sentencia.executeQuery( sql );
            
      while( resultado.next() )
      {
        try
        {
          listaRegistros.add(this.rsToBean(resultado));
        }
        catch (Exception e){ Log4j.error("Error " + e); }
      }
                    
      resultado.close();
      sentencia.close();
      
    }
    catch( Exception e ){ Log4j.error("Error " + e); }
    finally{ utils.PoolConexiones.cerrarConexion(conexion, sentencia, resultado); }

    return listaRegistros;
  }
  
 

  
  public Object consultaObjeto(String sql)
  {
	Connection conexion = utils.PoolConexiones.conexion();
	Statement sentencia = null;
	ResultSet resultado = null;
    Object objeto = new Object();
    int valor = 0;
    try
    {
        sentencia         = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        resultado         = sentencia.executeQuery( sql );
	    try
	    {
	      while(resultado.next()){
	    	  objeto = this.rsToBean(resultado);
	    	  valor = 1;
	      }
	    }
	    catch (Exception e){ Log4j.error("Error " + e); }
              
      resultado.close();
      sentencia.close();
      
    }
    catch( Exception e ){ Log4j.error("Error " + e); }
    finally{ utils.PoolConexiones.cerrarConexion(conexion, sentencia, resultado); }
    if(valor == 0) objeto = null;
    return objeto;
  }

  /**
   * Realiza una consulta a la base de datos y devuelve el valor de la primera
   * columna del primer registro obtenido.
   *
   * @param sql Consulta SQL a realizar
   *
   * @return Valor de la primera columna del primer registro devuelto o null en
   * caso de no encontrar ninguna.
   */
   public static String consultarValor( String sql )
   {
      String valor = null;

      Connection conexion = utils.PoolConexiones.conexion();
  	  Statement sentencia = null;
  	  ResultSet resultado = null;
	  	try
	    {
	      sentencia = conexion.createStatement();
	      resultado = sentencia.executeQuery( sql );
	
	        if( resultado.next() )
	        {
	          valor = resultado.getString(1);
	        }
	    }
      catch( SQLException e ){ Log4j.error("Error " + e); }
      finally{ utils.PoolConexiones.cerrarConexion(conexion, sentencia, resultado); }

      return valor;
   }


   /**
    * Realiza una consulta a la base de datos y devuelve el valor de la primera
    * columna del primer registro obtenido.
    *
    * @param sql Consulta SQL a realizar
    *
    * @return Valor de la primera columna del primer registro devuelto o null en
    * caso de no encontrar ninguna.
    */
   public static BigDecimal consultarValorBigDecimal( String sql )
   {
      BigDecimal valor = null;

      Connection conexion = utils.PoolConexiones.conexion();
      Statement sentencia = null;
      ResultSet resultado = null;
      try
      {
        sentencia = conexion.createStatement();
        resultado = sentencia.executeQuery( sql );

          if( resultado.next() )
          {
            valor = resultado.getBigDecimal(1);
          }
      }
      catch( SQLException e ){ Log4j.error("Error " + e); }
      finally{ utils.PoolConexiones.cerrarConexion(conexion, sentencia, resultado); }

      return valor;
   }


  /**
   * Realiza una consulta a la base de datos y devuelve los valores de las
   * columnas del primer registro obtenido.
   *
   * @param sql Consulta SQL a realizar
   *
   * @return Valores de las columnas del primer registro devuelto o null en
   * caso de no encontrar ninguna.
   */
   public static String[] consultarValores( String sql )
   {
      String[] valores = null;

      Connection conexion = utils.PoolConexiones.conexion();
      Statement sentencia = null;
      ResultSet resultado = null;
      try
      {
        sentencia = conexion.createStatement();
        resultado = sentencia.executeQuery( sql );

          if( resultado.next() )
          {
            ResultSetMetaData metaDataColumnas  = resultado.getMetaData();
            int               nColumnas         = metaDataColumnas.getColumnCount();

            valores = new String[nColumnas];
            for( int i = 1; i <= nColumnas; i++ )
            {
              valores[i-1] = resultado.getString(i);
            }
          }

      }
      catch( SQLException e ){ Log4j.error("Error " + e); }
      finally{ utils.PoolConexiones.cerrarConexion(conexion, sentencia, resultado); }

      return valores;
   }


   /**Realiza una consulta a la base de datos, y los introduce dentro de un objeto ArrayList.
    * Cada uno de los elementos de dicho array seran del tipo String [] y contendrá un registro
    * de los datos devueltos por la consulta.
    * @param sql es la consulta a hacer a la base de datos
    */
  static public ArrayList consulta( String sql )
  {
    ArrayList listaRegistros = new ArrayList();

    Connection conexion = utils.PoolConexiones.conexion();
    Statement sentencia = null;
    ResultSet resultado = null;
    try
    {
      sentencia         = conexion.createStatement();
      resultado         = sentencia.executeQuery( sql );
      ResultSetMetaData metaInfoColumnas  = resultado.getMetaData();
      int               nColumnas         = metaInfoColumnas.getColumnCount();

      while( resultado.next() )
      {
        String []valores = new String[nColumnas];

        for( int i = 1; i <= nColumnas; i++ )
        {
          valores[i-1] = resultado.getString(i);
        }

        listaRegistros.add(valores);
      }

      resultado.close();
      sentencia.close();
    }
    catch( SQLException e ){ Log4j.error("Error " + e); }
    finally{ utils.PoolConexiones.cerrarConexion(conexion, sentencia, resultado); }

    return listaRegistros;
  }

  private String conversionCampoAMetodo(String cadena)
  {
    String  resultado   = "";
    boolean mayuscula   = true;

    for( int i = 0; i < cadena.length(); i++ )
    {
      if( cadena.charAt(i) != '_' )
      {
        if( mayuscula )
        {
          resultado += Character.toUpperCase(cadena.charAt(i));
          mayuscula = false;
        }
        else
        {
          resultado += Character.toLowerCase(cadena.charAt(i));
        }
      }
      else
      {
        mayuscula = true;
      }
    }

    return resultado;
  }

  /**Obtiene un número de secuencia de la secuencia dada.
   * <p> Dichos valores serán usados como identificadores de objetos, cuando
   * se cree uno nuevo. Así se garantiza que no habrá dos objetos con el mismo identificador.
   * Cada clase de objetos tendrá su secuencia propia, definida en la base de datos.
   * @param secuencia es el identificador de la secuencia.
   * @return Devuelve el nuevo valor de la secuencia.*/
  public static String numeroSecuencia(String secuencia)
  {
	Connection conexion     = utils.PoolConexiones.conexion();
	Statement sentencia = null;
	ResultSet resultado = null;
    String numeroSecuencia  = "";
    try
    {
      sentencia = conexion.createStatement();
      resultado = sentencia.executeQuery("select "+secuencia+".nextval from dual");
      if( resultado.next() )
      {
        numeroSecuencia = resultado.getString(1);
      }
      resultado.close();
      sentencia.close();
    }
    catch( SQLException e ){ Log4j.error("Error " + e); }
    finally{ utils.PoolConexiones.cerrarConexion(conexion, sentencia, resultado); }

    return numeroSecuencia;
  }
  /**
   * Reset all properties to their default values.
   * @param mapping The ActionMapping used to select this instance.
   * @param request The HTTP Request we are processing.
   */


  public void compruebaValores ()
  {
    compruebaValores(false);
  }

  public void compruebaValores (boolean todos)
  {
    Method []listaMetodos = this.getClass().getMethods();
    for( int i = 0; i < listaMetodos.length; i++ )
    {
      Method metodo = listaMetodos[i];
      if( metodo.getName().substring(0, 3).equals("get") )
      {
        try
        {
          Object objeto = metodo.invoke( this, null );
          if( todos )
          {
            Log4j.debug(metodo.getName()+ "\t->  "+objeto);
          }
          else if (objeto != null)
          {
            Log4j.debug(metodo.getName()+ "\t->  "+objeto);
          }
        }
        catch (InvocationTargetException e){ Log4j.error("Error " + e); }
        catch (IllegalAccessException e){    Log4j.error("Error " + e); }
      }
    }
    Log4j.debug("RESULTADOS: "+listaMetodos.length+" metodos en total" );
  }

  public String conversion (String s)
  {
    StringBuffer sb = new StringBuffer();
    sb.append((char)(s.charAt(0)-32));
    sb.append(s.substring(1));
    return sb.toString();
  }

  public void cargaDatosFormulario (HttpServletRequest request)
  {
    Class claseString = null;
    try 
    {
      claseString = Class.forName("java.lang.String");
    }
    catch( ClassNotFoundException e ){}

    for( java.util.Enumeration parametros = request.getParameterNames(); parametros.hasMoreElements();)
    {
      String parametro = (String) parametros.nextElement();
      try
      {
        try
        {
          Class []tiposArgumentos = {claseString};
          Method metodo = this.getClass().getMethod("set"+conversion(parametro), tiposArgumentos);
          String valor  = request.getParameter(parametro);

          Object []valoresArgumentos = {valor};
          metodo.invoke(this, valoresArgumentos);
        }
        catch( NoSuchMethodException e1 )
        {
          // Se prueba a ver si se trata de un campo de fecha
          try
          {
            Class []tiposArgumentos = {claseString, claseString};
            Method metodo = this.getClass().getMethod("set"+conversion(parametro), tiposArgumentos);
            String valor  =request.getParameter(parametro);

            String formatoFecha = "";
            if( valor!=null && valor.length()==10 )
            {
              formatoFecha = Utils.DATE_SHORT;
            }
            else if( valor!=null && valor.length()==19 )
            {
              formatoFecha = Utils.DATE_HOUR_SHORT;
            }
            else
            {
              Log4j.error("[cargaDatosFormulario] Error: No se ha podido determinar el formato de la fecha [parametro:"+parametro+", valor:"+valor+"]");
            }
            Object []valoresArgumentos = {valor, formatoFecha};
            metodo.invoke(this, valoresArgumentos);
          }
          catch( NoSuchMethodException e2 ) {}
        }
      }
      catch( Exception e ){}
    }
  }
  
  public void cargaDatosFormularioStruts (HttpServletRequest request)
  {
    Class claseString = null;
    try 
    {
      claseString = Class.forName("java.lang.String");
    }
    catch( ClassNotFoundException e ){}

    for( java.util.Enumeration parametros = request.getParameterNames(); parametros.hasMoreElements();)
    {
      String parametro = (String) parametros.nextElement();
      try
      {
        try
        {
          Class []tiposArgumentos = {claseString};
          Method metodo = this.getClass().getMethod("set"+ capitalizeFisrt(parametro), tiposArgumentos);
          String valor  = request.getParameter(parametro);

          Object []valoresArgumentos = {valor};
          metodo.invoke(this, valoresArgumentos);
        }
        catch( NoSuchMethodException e1 )
        {
          // Se prueba a ver si se trata de un campo de fecha
          try
          {
            Class []tiposArgumentos = {claseString, claseString};
            Method metodo = this.getClass().getMethod("set"+capitalizeFisrt(parametro), tiposArgumentos);
            String valor  =request.getParameter(parametro);

            String formatoFecha = "";
            if( valor!=null && valor.length()==10 )
            {
              formatoFecha = Utils.DATE_SHORT;
            }
            else if( valor!=null && valor.length()==19 )
            {
              formatoFecha = Utils.DATE_HOUR_SHORT;
            }
            else
            {
              Log4j.error("[cargaDatosFormulario] Error: No se ha podido determinar el formato de la fecha [parametro:"+parametro+", valor:"+valor+"]");
            }
            Object []valoresArgumentos = {valor, formatoFecha};
            metodo.invoke(this, valoresArgumentos);
          }
          catch( NoSuchMethodException e2 ) {}
        }
      }
      catch( Exception e ){}
    }
  }

  protected static String formatoNumero(String x)
  {
    if( x==null || x.equals("")){ return null; }

    String valido="";
    for(int i=0;i<x.length();i++)
    {
      if(!x.substring(i,i+1).equals("."))
        //if( x.substring(i,i+1).equals(",") )
          //valido+=".";
        //else
          valido+=x.substring(i,i+1);
    }
    return valido;
  }

  public String formatoNumero ( BigDecimal bd )
  {
    return formatoNumero ( bd.toString() );
  }
  /**Formatea los valores numéricos con decimales.
   * @param cantidad es el valor numérico a formatear.
   * @return Devuelve el nuevo valor formateado.*/
  public String validar_numeros(String importe)
  {
       String valor="";
     int orden=importe.indexOf(",");
     String entera="";
     String decimal="";
     if(orden==-1){
      entera=importe;
      decimal=",00";
     }
     else{
      entera=importe.substring(0,orden);
      decimal=importe.substring(orden,importe.length());
     }
     int contador=1;
     for(int i=entera.length()-1;i>=0;i--){
      if(contador==3 && i>0){
        String aux1=entera.substring(0,i);
        String aux2=entera.substring(i,entera.length());
        entera=aux1 + "." + aux2;
        contador=1;
        i--;
      }
      contador++;
     }
     valor=entera + decimal;
     return valor;
  }


  public void cargaDatosFormulario (HttpServletRequest request,int orden)
  {
    try
    {
      Class [] tiposParametros = new Class[1];
      tiposParametros[0] = Class.forName("java.lang.String");
      for (java.util.Enumeration e = request.getParameterNames(); e.hasMoreElements();)
      {
        try
        {
          String elem=(String)e.nextElement();
          Method m = this.getClass().getMethod("set"+conversion(elem),tiposParametros);
          String [] parametros = new String[1];
          parametros [0] = (request.getParameterValues(elem))[orden];
          //parametros [0] = request.getParameter((String)e.nextElement());
          m.invoke(this,parametros);
        }catch (Exception e_){}
      }
    }
    catch (ClassNotFoundException e){}
  }

  public static String quitar_comas(String numero)
  {
	if(numero==null || numero.equals("")) numero = "0";
    //numero = numero.replaceAll("[.]",""); //numero=numero.toString().replace(/\ |\./g,'');
    //Log4j.info(numero);
    numero = numero.replace(',','.');
    //numero=numero.toString().replace(/\ |\,/g,'.');
    return numero;
  }
  

  public static String quitar_formato(String numero)
  {
	if(numero==null || numero.equals("")) numero = "0";
    numero = numero.replaceAll("[.]",""); //numero=numero.toString().replace(/\ |\./g,'');
    //Log4j.info(numero);
    numero = numero.replace(',','.');
    //numero=numero.toString().replace(/\ |\,/g,'.');
    return numero;
  }
  

  public static String quitar_comas_invertido(String numero)
  {
	if(numero==null) numero = "";
    numero = numero.replaceAll("[,]",""); //numero=numero.toString().replace(/\ |\./g,'');
    //Log4j.info(numero);
    numero = numero.replace('.',',');
    //numero=numero.toString().replace(/\ |\,/g,'.');
    return numero;
  }


  private Method obtenerMetodo( String nombreMetodo, Class claseParametro ) throws NoSuchMethodException
  {
    Method metodo = null;
    Class[] clasesParametros = new Class[1];
    clasesParametros[0] = claseParametro;

    if( claseParametro == null )
    {
      metodo = this.getClass().getMethod( nombreMetodo, null );
    }
    else
    {
      metodo = this.getClass().getMethod( nombreMetodo, clasesParametros );
    }

    return metodo;
  }
  
  public static String capitalizeFisrt(String s) {
      if (s.length() == 0) return s;
      return s.substring(0, 1).toUpperCase() + s.substring(1);
  }
}