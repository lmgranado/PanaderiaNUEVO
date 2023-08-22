// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   EntidadBean.java

package panaderia.struts.forms;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionForm;
import utils.Log4j;
import utils.PoolConexiones;
import utils.Utils;

public class EntidadBean_old extends org.apache.struts.action.ActionForm
    implements java.io.Serializable
{

    public EntidadBean_old()
    {
        pk_identificador_ = "";
        nuevo = false;
        checked = false;
    }

    public boolean equals(java.lang.Object obj)
    {
        if(getClass().isInstance(obj))
        {
            panaderia.struts.forms.EntidadBean objEntidad = (panaderia.struts.forms.EntidadBean)obj;
            return pk_identificador_.equals(objEntidad.pk_identificador_);
        } else
        {
            return false;
        }
    }

    public boolean isChecked()
    {
        return checked;
    }

    public void setChecked(boolean checked)
    {
        this.checked = checked;
    }

    public static java.text.NumberFormat getNumberFormat()
    {
        return numberFormatter;
    }

    public static int indice(java.util.ArrayList lista, java.lang.String pk)
    {
        int nElementos = lista.size();
        for(int i = 0; i < nElementos; i++)
        {
            panaderia.struts.forms.EntidadBean elemento = (panaderia.struts.forms.EntidadBean)lista.get(i);
            if(elemento.pk_identificador_.equals(pk))
                return i;
        }

        return -1;
    }

    public java.util.ArrayList consultaAVectorReflexiva(java.lang.String sql)
    {
        java.util.ArrayList listaRegistros;
        java.sql.Connection conexion;
        java.sql.Statement sentencia;
        java.sql.ResultSet resultado;
        listaRegistros = new ArrayList();
        conexion = utils.PoolConexiones.conexion();
        sentencia = null;
        resultado = null;
        try
        {
            sentencia = conexion.createStatement();
            resultado = sentencia.executeQuery(sql);
            java.sql.ResultSetMetaData metaInfoColumnas = resultado.getMetaData();
            int nColumnas = metaInfoColumnas.getColumnCount();
            java.lang.reflect.Method listaMetodos[] = new java.lang.reflect.Method[nColumnas];
            java.lang.Class claseString = null;
            java.lang.Class claseDate = null;
            java.lang.Class claseTimeStamp = null;
            try
            {
                claseString = java.lang.Class.forName("java.lang.String");
                claseDate = java.lang.Class.forName("java.util.Date");
                claseTimeStamp = java.lang.Class.forName("java.sql.Timestamp");
            }
            catch(java.lang.ClassNotFoundException e)
            {
                utils.Log4j.error((new StringBuilder("Error:")).append(e).toString());
            }
            for(int i = 1; i <= nColumnas; i++)
            {
                java.lang.String nombreColumna = metaInfoColumnas.getColumnName(i);
                java.lang.String tipoColumna = metaInfoColumnas.getColumnTypeName(i);
                try
                {
                    if(tipoColumna.equals("DATE"))
                        listaMetodos[i - 1] = obtenerMetodo((new StringBuilder("set")).append(conversionCampoAMetodo(nombreColumna)).toString(), claseDate);
                    else
                        listaMetodos[i - 1] = obtenerMetodo((new StringBuilder("set")).append(conversionCampoAMetodo(nombreColumna)).toString(), claseString);
                }
                catch(java.lang.NoSuchMethodException e)
                {
                    if(tipoColumna.equals("DATE"))
                    {
                        try
                        {
                            listaMetodos[i - 1] = obtenerMetodo((new StringBuilder("set")).append(conversionCampoAMetodo(nombreColumna)).toString(), claseTimeStamp);
                        }
                        catch(java.lang.NoSuchMethodException ec)
                        {
                            utils.Log4j.error((new StringBuilder("[consultaAVectorReflexiva] Error [nombreColumna:")).append(nombreColumna).append(", tipoColumna:").append(tipoColumna).append("]:").append(e).toString());
                            listaMetodos[i - 1] = null;
                        }
                    } else
                    {
                        utils.Log4j.error((new StringBuilder("[consultaAVectorReflexiva] Error [nombreColumna:")).append(nombreColumna).append(", tipoColumna:").append(tipoColumna).append("]:").append(e).toString());
                        listaMetodos[i - 1] = null;
                    }
                }
            }

            java.lang.Object valoresParametros[] = new java.lang.Object[1];
            while(resultado.next()) 
                try
                {
                    java.lang.Object registro = getClass().newInstance();
                    for(int i = 1; i <= nColumnas; i++)
                    {
                        java.lang.String nombreColumna = metaInfoColumnas.getColumnName(i);
                        java.lang.String tipoColumna = metaInfoColumnas.getColumnTypeName(i);
                        valoresParametros[0] = resultado.getString(i);
                        if(tipoColumna.equals("DATE"))
                            valoresParametros[0] = resultado.getTimestamp(i);
                        else
                        if(valoresParametros[0] == null)
                            valoresParametros[0] = "";
                        else
                        if(tipoColumna.equals("NUMBER") && metaInfoColumnas.getScale(i) > 0)
                            valoresParametros[0] = numberFormatter.format(resultado.getDouble(i));
                        if(listaMetodos[i - 1] != null)
                            try
                            {
                                listaMetodos[i - 1].invoke(registro, valoresParametros);
                            }
                            catch(java.lang.Exception e)
                            {
                                utils.Log4j.error((new StringBuilder("[consultaAVectorReflexiva] Error [nombreColumna:")).append(nombreColumna).append(", tipoColumna:").append(tipoColumna).append("]:").append(e).toString());
                            }
                    }

                    listaRegistros.add(registro);
                }
                catch(java.lang.InstantiationException e)
                {
                    utils.Log4j.error((new StringBuilder("Error ")).append(e).toString());
                }
                catch(java.lang.IllegalAccessException e)
                {
                    utils.Log4j.error((new StringBuilder("Error ")).append(e).toString());
                }
            resultado.close();
            sentencia.close();
            break MISSING_BLOCK_LABEL_740;
        }
        catch(java.sql.SQLException e)
        {
            utils.Log4j.error((new StringBuilder("Error ")).append(e).toString());
        }
        utils.PoolConexiones.cerrarConexion(conexion, sentencia, resultado);
        break MISSING_BLOCK_LABEL_748;
        java.lang.Exception exception;
        exception;
        utils.PoolConexiones.cerrarConexion(conexion, sentencia, resultado);
        throw exception;
        utils.PoolConexiones.cerrarConexion(conexion, sentencia, resultado);
        return listaRegistros;
    }

    public void consultaReflexiva(java.lang.String sql)
    {
        java.sql.Connection conexion;
        java.sql.Statement sentencia;
        java.sql.ResultSet resultado;
        conexion = utils.PoolConexiones.conexion();
        sentencia = null;
        resultado = null;
        try
        {
            sentencia = conexion.createStatement();
            resultado = sentencia.executeQuery(sql);
            java.sql.ResultSetMetaData metaInfoData = resultado.getMetaData();
            if(resultado.next())
            {
                java.lang.Object valoresParametros[] = new java.lang.Object[1];
                java.lang.Class claseString = null;
                java.lang.Class claseDate = null;
                java.lang.Class claseTimeStamp = null;
                try
                {
                    claseString = java.lang.Class.forName("java.lang.String");
                    claseDate = java.lang.Class.forName("java.util.Date");
                    claseTimeStamp = java.lang.Class.forName("java.sql.Timestamp");
                }
                catch(java.lang.ClassNotFoundException e)
                {
                    utils.Log4j.error((new StringBuilder("Error:")).append(e).toString());
                }
                int nColumnas = metaInfoData.getColumnCount();
                for(int i = 1; i <= nColumnas; i++)
                {
                    java.lang.String tipoColumna = metaInfoData.getColumnTypeName(i);
                    valoresParametros[0] = resultado.getString(i);
                    if(tipoColumna.equals("DATE"))
                        valoresParametros[0] = resultado.getTimestamp(i);
                    else
                    if(valoresParametros[0] == null)
                        valoresParametros[0] = "";
                    else
                    if(tipoColumna.equals("NUMBER") && metaInfoData.getScale(i) > 0)
                        valoresParametros[0] = numberFormatter.format(resultado.getDouble(i));
                    java.lang.String nombreColumna = metaInfoData.getColumnName(i);
                    java.lang.reflect.Method metodo = null;
                    try
                    {
                        if(tipoColumna.equals("DATE"))
                            metodo = obtenerMetodo((new StringBuilder("set")).append(conversionCampoAMetodo(nombreColumna)).toString(), claseDate);
                        else
                            metodo = obtenerMetodo((new StringBuilder("set")).append(conversionCampoAMetodo(nombreColumna)).toString(), claseString);
                        metodo.invoke(this, valoresParametros);
                    }
                    catch(java.lang.Exception e)
                    {
                        if(tipoColumna.equals("DATE"))
                            try
                            {
                                metodo = obtenerMetodo((new StringBuilder("set")).append(conversionCampoAMetodo(nombreColumna)).toString(), claseTimeStamp);
                                metodo.invoke(this, valoresParametros);
                            }
                            catch(java.lang.Exception ec)
                            {
                                utils.Log4j.error((new StringBuilder("[consultaReflexiva] Error [nombreColumna:")).append(nombreColumna).append(", tipoColumna:").append(tipoColumna).append("]:").append(e).toString());
                            }
                        else
                            utils.Log4j.error((new StringBuilder("[consultaReflexiva] Error [nombreColumna:")).append(nombreColumna).append(", tipoColumna:").append(tipoColumna).append("]:").append(e).toString());
                    }
                }

            }
            resultado.close();
            sentencia.close();
            break MISSING_BLOCK_LABEL_534;
        }
        catch(java.sql.SQLException e)
        {
            utils.Log4j.error((new StringBuilder("Error ")).append(e).toString());
        }
        utils.PoolConexiones.cerrarConexion(conexion, sentencia, resultado);
        break MISSING_BLOCK_LABEL_541;
        java.lang.Exception exception;
        exception;
        utils.PoolConexiones.cerrarConexion(conexion, sentencia, resultado);
        throw exception;
        utils.PoolConexiones.cerrarConexion(conexion, sentencia, resultado);
    }

    protected java.lang.Object rsToBean(java.sql.ResultSet rs)
        throws java.lang.Exception
    {
        return new Object();
    }

    public java.lang.Object[] consultaAVectorReflexiva(java.lang.String sql, int maxResult, int firstResult)
    {
        java.util.ArrayList listaRegistros;
        java.lang.Object arrayResultado[];
        java.sql.Connection conexion;
        java.sql.Statement sentencia;
        java.sql.ResultSet resultado;
        listaRegistros = new ArrayList();
        arrayResultado = new java.lang.Object[2];
        conexion = utils.PoolConexiones.conexion();
        sentencia = null;
        resultado = null;
        try
        {
            sentencia = conexion.createStatement(1004, 1007);
            resultado = sentencia.executeQuery(sql);
            resultado.last();
            int numTotal = resultado.getRow();
            resultado.beforeFirst();
            arrayResultado[0] = java.lang.Integer.toString(numTotal);
            if(firstResult != 0)
                resultado.absolute(firstResult);
            if(maxResult == -1)
                maxResult = numTotal;
            int k = 1;
            while(resultado.next()) 
            {
                if(k > maxResult)
                    break;
                try
                {
                    k++;
                    listaRegistros.add(rsToBean(resultado));
                }
                catch(java.lang.Exception e)
                {
                    utils.Log4j.error((new StringBuilder("Error ")).append(e).toString());
                }
            }
            resultado.close();
            sentencia.close();
            arrayResultado[1] = listaRegistros;
            break MISSING_BLOCK_LABEL_241;
        }
        catch(java.lang.Exception e)
        {
            utils.Log4j.error((new StringBuilder("Error ")).append(e).toString());
        }
        utils.PoolConexiones.cerrarConexion(conexion, sentencia, resultado);
        break MISSING_BLOCK_LABEL_250;
        java.lang.Exception exception;
        exception;
        utils.PoolConexiones.cerrarConexion(conexion, sentencia, resultado);
        throw exception;
        utils.PoolConexiones.cerrarConexion(conexion, sentencia, resultado);
        return arrayResultado;
    }

    public java.util.ArrayList consultaAVectorReflexivaRsToBean(java.lang.String sql)
    {
        java.util.ArrayList listaRegistros;
        java.sql.Connection conexion;
        java.sql.Statement sentencia;
        java.sql.ResultSet resultado;
        listaRegistros = new ArrayList();
        conexion = utils.PoolConexiones.conexion();
        sentencia = null;
        resultado = null;
        try
        {
            sentencia = conexion.createStatement(1004, 1007);
            for(resultado = sentencia.executeQuery(sql); resultado.next();)
                try
                {
                    listaRegistros.add(rsToBean(resultado));
                }
                catch(java.lang.Exception e)
                {
                    utils.Log4j.error((new StringBuilder("Error ")).append(e).toString());
                }

            resultado.close();
            sentencia.close();
            break MISSING_BLOCK_LABEL_154;
        }
        catch(java.lang.Exception e)
        {
            utils.Log4j.error((new StringBuilder("Error ")).append(e).toString());
        }
        utils.PoolConexiones.cerrarConexion(conexion, sentencia, resultado);
        break MISSING_BLOCK_LABEL_162;
        java.lang.Exception exception;
        exception;
        utils.PoolConexiones.cerrarConexion(conexion, sentencia, resultado);
        throw exception;
        utils.PoolConexiones.cerrarConexion(conexion, sentencia, resultado);
        return listaRegistros;
    }

    public java.lang.Object consultaObjeto(java.lang.String sql)
    {
        java.sql.Connection conexion;
        java.sql.Statement sentencia;
        java.sql.ResultSet resultado;
        java.lang.Object objeto;
        int valor;
        conexion = utils.PoolConexiones.conexion();
        sentencia = null;
        resultado = null;
        objeto = new Object();
        valor = 0;
        try
        {
            sentencia = conexion.createStatement(1004, 1007);
            resultado = sentencia.executeQuery(sql);
            try
            {
                while(resultado.next()) 
                {
                    objeto = rsToBean(resultado);
                    valor = 1;
                }
            }
            catch(java.lang.Exception e)
            {
                utils.Log4j.error((new StringBuilder("Error ")).append(e).toString());
            }
            resultado.close();
            sentencia.close();
            break MISSING_BLOCK_LABEL_152;
        }
        catch(java.lang.Exception e)
        {
            utils.Log4j.error((new StringBuilder("Error ")).append(e).toString());
        }
        utils.PoolConexiones.cerrarConexion(conexion, sentencia, resultado);
        break MISSING_BLOCK_LABEL_159;
        java.lang.Exception exception;
        exception;
        utils.PoolConexiones.cerrarConexion(conexion, sentencia, resultado);
        throw exception;
        utils.PoolConexiones.cerrarConexion(conexion, sentencia, resultado);
        if(valor == 0)
            objeto = null;
        return objeto;
    }

    public static java.lang.String consultarValor(java.lang.String sql)
    {
        java.lang.String valor;
        java.sql.Connection conexion;
        java.sql.Statement sentencia;
        java.sql.ResultSet resultado;
        valor = null;
        conexion = utils.PoolConexiones.conexion();
        sentencia = null;
        resultado = null;
        try
        {
            sentencia = conexion.createStatement();
            resultado = sentencia.executeQuery(sql);
            if(resultado.next())
                valor = resultado.getString(1);
            break MISSING_BLOCK_LABEL_93;
        }
        catch(java.sql.SQLException e)
        {
            utils.Log4j.error((new StringBuilder("Error ")).append(e).toString());
        }
        utils.PoolConexiones.cerrarConexion(conexion, sentencia, resultado);
        break MISSING_BLOCK_LABEL_100;
        java.lang.Exception exception;
        exception;
        utils.PoolConexiones.cerrarConexion(conexion, sentencia, resultado);
        throw exception;
        utils.PoolConexiones.cerrarConexion(conexion, sentencia, resultado);
        return valor;
    }

    public static java.math.BigDecimal consultarValorBigDecimal(java.lang.String sql)
    {
        java.math.BigDecimal valor;
        java.sql.Connection conexion;
        java.sql.Statement sentencia;
        java.sql.ResultSet resultado;
        valor = null;
        conexion = utils.PoolConexiones.conexion();
        sentencia = null;
        resultado = null;
        try
        {
            sentencia = conexion.createStatement();
            resultado = sentencia.executeQuery(sql);
            if(resultado.next())
                valor = resultado.getBigDecimal(1);
            break MISSING_BLOCK_LABEL_93;
        }
        catch(java.sql.SQLException e)
        {
            utils.Log4j.error((new StringBuilder("Error ")).append(e).toString());
        }
        utils.PoolConexiones.cerrarConexion(conexion, sentencia, resultado);
        break MISSING_BLOCK_LABEL_100;
        java.lang.Exception exception;
        exception;
        utils.PoolConexiones.cerrarConexion(conexion, sentencia, resultado);
        throw exception;
        utils.PoolConexiones.cerrarConexion(conexion, sentencia, resultado);
        return valor;
    }

    public static java.lang.String[] consultarValores(java.lang.String sql)
    {
        java.lang.String valores[];
        java.sql.Connection conexion;
        java.sql.Statement sentencia;
        java.sql.ResultSet resultado;
        valores = (java.lang.String[])null;
        conexion = utils.PoolConexiones.conexion();
        sentencia = null;
        resultado = null;
        try
        {
            sentencia = conexion.createStatement();
            resultado = sentencia.executeQuery(sql);
            if(resultado.next())
            {
                java.sql.ResultSetMetaData metaDataColumnas = resultado.getMetaData();
                int nColumnas = metaDataColumnas.getColumnCount();
                valores = new java.lang.String[nColumnas];
                for(int i = 1; i <= nColumnas; i++)
                    valores[i - 1] = resultado.getString(i);

            }
            break MISSING_BLOCK_LABEL_142;
        }
        catch(java.sql.SQLException e)
        {
            utils.Log4j.error((new StringBuilder("Error ")).append(e).toString());
        }
        utils.PoolConexiones.cerrarConexion(conexion, sentencia, resultado);
        break MISSING_BLOCK_LABEL_149;
        java.lang.Exception exception;
        exception;
        utils.PoolConexiones.cerrarConexion(conexion, sentencia, resultado);
        throw exception;
        utils.PoolConexiones.cerrarConexion(conexion, sentencia, resultado);
        return valores;
    }

    public static java.util.ArrayList consulta(java.lang.String sql)
    {
        java.util.ArrayList listaRegistros;
        java.sql.Connection conexion;
        java.sql.Statement sentencia;
        java.sql.ResultSet resultado;
        listaRegistros = new ArrayList();
        conexion = utils.PoolConexiones.conexion();
        sentencia = null;
        resultado = null;
        try
        {
            sentencia = conexion.createStatement();
            resultado = sentencia.executeQuery(sql);
            java.sql.ResultSetMetaData metaInfoColumnas = resultado.getMetaData();
            int nColumnas = metaInfoColumnas.getColumnCount();
            java.lang.String valores[];
            for(; resultado.next(); listaRegistros.add(valores))
            {
                valores = new java.lang.String[nColumnas];
                for(int i = 1; i <= nColumnas; i++)
                    valores[i - 1] = resultado.getString(i);

            }

            resultado.close();
            sentencia.close();
            break MISSING_BLOCK_LABEL_170;
        }
        catch(java.sql.SQLException e)
        {
            utils.Log4j.error((new StringBuilder("Error ")).append(e).toString());
        }
        utils.PoolConexiones.cerrarConexion(conexion, sentencia, resultado);
        break MISSING_BLOCK_LABEL_177;
        java.lang.Exception exception;
        exception;
        utils.PoolConexiones.cerrarConexion(conexion, sentencia, resultado);
        throw exception;
        utils.PoolConexiones.cerrarConexion(conexion, sentencia, resultado);
        return listaRegistros;
    }

    private java.lang.String conversionCampoAMetodo(java.lang.String cadena)
    {
        java.lang.String resultado = "";
        boolean mayuscula = true;
        for(int i = 0; i < cadena.length(); i++)
            if(cadena.charAt(i) != '_')
            {
                if(mayuscula)
                {
                    resultado = (new StringBuilder(java.lang.String.valueOf(resultado))).append(java.lang.Character.toUpperCase(cadena.charAt(i))).toString();
                    mayuscula = false;
                } else
                {
                    resultado = (new StringBuilder(java.lang.String.valueOf(resultado))).append(java.lang.Character.toLowerCase(cadena.charAt(i))).toString();
                }
            } else
            {
                mayuscula = true;
            }

        return resultado;
    }

    public static java.lang.String numeroSecuencia(java.lang.String secuencia)
    {
        java.sql.Connection conexion;
        java.sql.Statement sentencia;
        java.sql.ResultSet resultado;
        java.lang.String numeroSecuencia;
        conexion = utils.PoolConexiones.conexion();
        sentencia = null;
        resultado = null;
        numeroSecuencia = "";
        try
        {
            sentencia = conexion.createStatement();
            resultado = sentencia.executeQuery((new StringBuilder("select ")).append(secuencia).append(".nextval from dual").toString());
            if(resultado.next())
                numeroSecuencia = resultado.getString(1);
            resultado.close();
            sentencia.close();
            break MISSING_BLOCK_LABEL_124;
        }
        catch(java.sql.SQLException e)
        {
            utils.Log4j.error((new StringBuilder("Error ")).append(e).toString());
        }
        utils.PoolConexiones.cerrarConexion(conexion, sentencia, resultado);
        break MISSING_BLOCK_LABEL_130;
        java.lang.Exception exception;
        exception;
        utils.PoolConexiones.cerrarConexion(conexion, sentencia, resultado);
        throw exception;
        utils.PoolConexiones.cerrarConexion(conexion, sentencia, resultado);
        return numeroSecuencia;
    }

    public void compruebaValores()
    {
        compruebaValores(false);
    }

    public void compruebaValores(boolean todos)
    {
        java.lang.reflect.Method listaMetodos[] = getClass().getMethods();
        for(int i = 0; i < listaMetodos.length; i++)
        {
            java.lang.reflect.Method metodo = listaMetodos[i];
            if(metodo.getName().substring(0, 3).equals("get"))
                try
                {
                    java.lang.Object objeto = metodo.invoke(this, null);
                    if(todos)
                        utils.Log4j.debug((new StringBuilder(java.lang.String.valueOf(metodo.getName()))).append("\t->  ").append(objeto).toString());
                    else
                    if(objeto != null)
                        utils.Log4j.debug((new StringBuilder(java.lang.String.valueOf(metodo.getName()))).append("\t->  ").append(objeto).toString());
                }
                catch(java.lang.reflect.InvocationTargetException e)
                {
                    utils.Log4j.error((new StringBuilder("Error ")).append(e).toString());
                }
                catch(java.lang.IllegalAccessException e)
                {
                    utils.Log4j.error((new StringBuilder("Error ")).append(e).toString());
                }
        }

        utils.Log4j.debug((new StringBuilder("RESULTADOS: ")).append(listaMetodos.length).append(" metodos en total").toString());
    }

    public java.lang.String conversion(java.lang.String s)
    {
        java.lang.StringBuffer sb = new StringBuffer();
        sb.append((char)(s.charAt(0) - 32));
        sb.append(s.substring(1));
        return sb.toString();
    }

    public void cargaDatosFormulario(javax.servlet.http.HttpServletRequest request)
    {
        java.lang.Class claseString = null;
        try
        {
            claseString = java.lang.Class.forName("java.lang.String");
        }
        catch(java.lang.ClassNotFoundException classnotfoundexception) { }
        for(java.util.Enumeration parametros = request.getParameterNames(); parametros.hasMoreElements();)
        {
            java.lang.String parametro = (java.lang.String)parametros.nextElement();
            try
            {
                try
                {
                    java.lang.Class tiposArgumentos[] = {
                        claseString
                    };
                    java.lang.reflect.Method metodo = getClass().getMethod((new StringBuilder("set")).append(conversion(parametro)).toString(), tiposArgumentos);
                    java.lang.String valor = request.getParameter(parametro);
                    java.lang.Object valoresArgumentos[] = {
                        valor
                    };
                    metodo.invoke(this, valoresArgumentos);
                }
                catch(java.lang.NoSuchMethodException e1)
                {
                    try
                    {
                        java.lang.Class tiposArgumentos[] = {
                            claseString, claseString
                        };
                        java.lang.reflect.Method metodo = getClass().getMethod((new StringBuilder("set")).append(conversion(parametro)).toString(), tiposArgumentos);
                        java.lang.String valor = request.getParameter(parametro);
                        java.lang.String formatoFecha = "";
                        if(valor != null && valor.length() == 10)
                            formatoFecha = utils.Utils.DATE_SHORT;
                        else
                        if(valor != null && valor.length() == 19)
                            formatoFecha = utils.Utils.DATE_HOUR_SHORT;
                        else
                            utils.Log4j.error((new StringBuilder("[cargaDatosFormulario] Error: No se ha podido determinar el formato de la fecha [parametro:")).append(parametro).append(", valor:").append(valor).append("]").toString());
                        java.lang.Object valoresArgumentos[] = {
                            valor, formatoFecha
                        };
                        metodo.invoke(this, valoresArgumentos);
                    }
                    catch(java.lang.NoSuchMethodException nosuchmethodexception) { }
                }
            }
            catch(java.lang.Exception exception) { }
        }

    }

    public void cargaDatosFormularioStruts(javax.servlet.http.HttpServletRequest request)
    {
        java.lang.Class claseString = null;
        try
        {
            claseString = java.lang.Class.forName("java.lang.String");
        }
        catch(java.lang.ClassNotFoundException classnotfoundexception) { }
        for(java.util.Enumeration parametros = request.getParameterNames(); parametros.hasMoreElements();)
        {
            java.lang.String parametro = (java.lang.String)parametros.nextElement();
            try
            {
                try
                {
                    java.lang.Class tiposArgumentos[] = {
                        claseString
                    };
                    java.lang.reflect.Method metodo = getClass().getMethod((new StringBuilder("set")).append(panaderia.struts.forms.EntidadBean.capitalizeFisrt(parametro)).toString(), tiposArgumentos);
                    java.lang.String valor = request.getParameter(parametro);
                    java.lang.Object valoresArgumentos[] = {
                        valor
                    };
                    metodo.invoke(this, valoresArgumentos);
                }
                catch(java.lang.NoSuchMethodException e1)
                {
                    try
                    {
                        java.lang.Class tiposArgumentos[] = {
                            claseString, claseString
                        };
                        java.lang.reflect.Method metodo = getClass().getMethod((new StringBuilder("set")).append(panaderia.struts.forms.EntidadBean.capitalizeFisrt(parametro)).toString(), tiposArgumentos);
                        java.lang.String valor = request.getParameter(parametro);
                        java.lang.String formatoFecha = "";
                        if(valor != null && valor.length() == 10)
                            formatoFecha = utils.Utils.DATE_SHORT;
                        else
                        if(valor != null && valor.length() == 19)
                            formatoFecha = utils.Utils.DATE_HOUR_SHORT;
                        else
                            utils.Log4j.error((new StringBuilder("[cargaDatosFormulario] Error: No se ha podido determinar el formato de la fecha [parametro:")).append(parametro).append(", valor:").append(valor).append("]").toString());
                        java.lang.Object valoresArgumentos[] = {
                            valor, formatoFecha
                        };
                        metodo.invoke(this, valoresArgumentos);
                    }
                    catch(java.lang.NoSuchMethodException nosuchmethodexception) { }
                }
            }
            catch(java.lang.Exception exception) { }
        }

    }

    protected static java.lang.String formatoNumero(java.lang.String x)
    {
        if(x == null || x.equals(""))
            return null;
        java.lang.String valido = "";
        for(int i = 0; i < x.length(); i++)
            if(!x.substring(i, i + 1).equals("."))
                valido = (new StringBuilder(java.lang.String.valueOf(valido))).append(x.substring(i, i + 1)).toString();

        return valido;
    }

    public java.lang.String formatoNumero(java.math.BigDecimal bd)
    {
        return panaderia.struts.forms.EntidadBean.formatoNumero(bd.toString());
    }

    public java.lang.String validar_numeros(java.lang.String importe)
    {
        java.lang.String valor = "";
        int orden = importe.indexOf(",");
        java.lang.String entera = "";
        java.lang.String decimal = "";
        if(orden == -1)
        {
            entera = importe;
            decimal = ",00";
        } else
        {
            entera = importe.substring(0, orden);
            decimal = importe.substring(orden, importe.length());
        }
        int contador = 1;
        for(int i = entera.length() - 1; i >= 0; i--)
        {
            if(contador == 3 && i > 0)
            {
                java.lang.String aux1 = entera.substring(0, i);
                java.lang.String aux2 = entera.substring(i, entera.length());
                entera = (new StringBuilder(java.lang.String.valueOf(aux1))).append(".").append(aux2).toString();
                contador = 1;
                i--;
            }
            contador++;
        }

        valor = (new StringBuilder(java.lang.String.valueOf(entera))).append(decimal).toString();
        return valor;
    }

    public void cargaDatosFormulario(javax.servlet.http.HttpServletRequest request, int orden)
    {
        try
        {
            java.lang.Class tiposParametros[] = new java.lang.Class[1];
            tiposParametros[0] = java.lang.Class.forName("java.lang.String");
            for(java.util.Enumeration e = request.getParameterNames(); e.hasMoreElements();)
                try
                {
                    java.lang.String elem = (java.lang.String)e.nextElement();
                    java.lang.reflect.Method m = getClass().getMethod((new StringBuilder("set")).append(conversion(elem)).toString(), tiposParametros);
                    java.lang.String parametros[] = new java.lang.String[1];
                    parametros[0] = request.getParameterValues(elem)[orden];
                    m.invoke(this, parametros);
                }
                catch(java.lang.Exception exception) { }

        }
        catch(java.lang.ClassNotFoundException classnotfoundexception) { }
    }

    public static java.lang.String quitar_comas(java.lang.String numero)
    {
        if(numero == null || numero.equals(""))
            numero = "0";
        numero = numero.replace(',', '.');
        return numero;
    }

    public static java.lang.String quitar_formato(java.lang.String numero)
    {
        if(numero == null || numero.equals(""))
            numero = "0";
        numero = numero.replaceAll("[.]", "");
        numero = numero.replace(',', '.');
        return numero;
    }

    public static java.lang.String quitar_comas_invertido(java.lang.String numero)
    {
        if(numero == null)
            numero = "";
        numero = numero.replaceAll("[,]", "");
        numero = numero.replace('.', ',');
        return numero;
    }

    private java.lang.reflect.Method obtenerMetodo(java.lang.String nombreMetodo, java.lang.Class claseParametro)
        throws java.lang.NoSuchMethodException
    {
        java.lang.reflect.Method metodo = null;
        java.lang.Class clasesParametros[] = new java.lang.Class[1];
        clasesParametros[0] = claseParametro;
        if(claseParametro == null)
            metodo = getClass().getMethod(nombreMetodo, null);
        else
            metodo = getClass().getMethod(nombreMetodo, clasesParametros);
        return metodo;
    }

    public static java.lang.String capitalizeFisrt(java.lang.String s)
    {
        if(s.length() == 0)
            return s;
        else
            return (new StringBuilder(java.lang.String.valueOf(s.substring(0, 1).toUpperCase()))).append(s.substring(1)).toString();
    }

    private static final long serialVersionUID = 1L;
    public java.lang.String pk_identificador_;
    public boolean nuevo;
    private boolean checked;
    private static java.util.Locale locale;
    private static java.text.NumberFormat numberFormatter;

    static 
    {
        locale = new Locale("es", "ES");
        numberFormatter = null;
        numberFormatter = java.text.NumberFormat.getInstance(locale);
        numberFormatter.setMinimumFractionDigits(2);
        numberFormatter.setMaximumFractionDigits(2);
    }
}
