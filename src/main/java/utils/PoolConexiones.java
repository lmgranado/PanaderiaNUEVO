package utils;

import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.commons.dbcp.*;
import org.apache.commons.pool.*;

public class PoolConexiones implements java.io.Serializable
{
  private static final long serialVersionUID = 1L;

  static private DataSource ds = null;

  static
  {

    try
    {
    	Context initContext = new InitialContext();
    	Context envContext  = (Context)initContext.lookup("java:/comp/env");
    	ds = (DataSource)envContext.lookup("jdbc/trMysql");
    }
    catch( Exception e )
    {
      ds = null;
      Log4j.error("Error al buscar la clase para la conexion a la base de datos",e);
    }
  }

  
  static public Connection conexion()
  {
    if( ds == null )
    {
      Log4j.error("El pool no esta inicializado correctamente");
    }

    Connection conexion = null;
    try
    {
    	conexion = ds.getConnection();
    	Log4j.debug("Toma Conexion del pool (pool)");
    }
    catch( SQLException e ){ e.printStackTrace(); }

    if( conexion == null )
    {
      Log4j.error("El pool devuelve una conexion nula.");
    }

    return conexion;
  }

  
  static public void cerrarConexion( Connection c )
  {
    try
    {
      c.close();
    }
    catch( SQLException e ){ Log4j.error("Error al cerrar una conexion",e); }
    finally{ c = null; }
  }
  
  static public void cerrarConexion( Connection c , PreparedStatement s)
  {
    try
    {
      if(s!=null) s.close();
      c.close();
    }
    catch( SQLException e ){ Log4j.error("Error al cerrar una conexion",e); }
    finally{ 
    	try{
    		c.close();
    	}catch (Exception e) {}
    	c = null; 
    }
  }
  
  static public void cerrarConexion( Connection c , Statement s)
  {
    try
    {
      if(s!=null) s.close();
      c.close();
    }
    catch( SQLException e ){ Log4j.error("Error al cerrar una conexion",e); }
    finally{ 
    	try{
    		c.close();
    	}catch (Exception e) {}
    	c = null; 
    }
  }
  
  static public void cerrarConexion( Connection c , PreparedStatement s, ResultSet r)
  {
    try
    {
      if(r!=null) r.close();
      if(s!=null) s.close();
      c.close();
    }
    catch( SQLException e ){ Log4j.error("Error al cerrar una conexion",e); }
    finally{ 
    	try{
    		c.close();
    	}catch (Exception e) {}
    	c = null; 
    }
  }
  
  static public void cerrarConexion( Connection c , Statement s, ResultSet r)
  {
    try
    {
      if(r!=null) r.close();
      if(s!=null) s.close();
      c.close();
    }
    catch( SQLException e ){ Log4j.error("Error al cerrar una conexion",e); }
    finally{ 
    	try{
    		c.close();
    	}catch (Exception e) {}
    	c = null; 
    }
  }

  static public int getActive()
  {
    int i = 0;
    try{
      PoolingDriver driver = (PoolingDriver) DriverManager.getDriver("jdbc:apache:commons:dbcp:");
      ObjectPool connectionPool = driver.getConnectionPool("example");

      i = connectionPool.getNumActive();
    }
    catch (Exception e ) {e.printStackTrace();}

    return i;
  }

  static public int getNumIdle()
  {
    int i = 0;
    try{
      PoolingDriver driver = (PoolingDriver) DriverManager.getDriver("jdbc:apache:commons:dbcp:");
      ObjectPool connectionPool = driver.getConnectionPool("example");

      i = connectionPool.getNumIdle();
    }
    catch (Exception e ) {e.printStackTrace();}

    return i;
  }
}

