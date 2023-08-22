package utils;

import java.util.Enumeration;
import java.util.Properties;
import java.util.ResourceBundle;

import org.apache.log4j.*;

public class Log4j implements java.io.Serializable
{
  private static final long serialVersionUID = 1L;

  static private Logger log = null;
  static private String logName = "Panaderia";

  static
  {
    Properties propiedades = new Properties();
    ResourceBundle bun = ResourceBundle.getBundle("log4java");
    Enumeration claves = bun.getKeys();

    while( claves.hasMoreElements() )
    {
      String clave = (String)claves.nextElement();
      String valor = bun.getString(clave);

      propiedades.setProperty(clave, valor);
    }

    PropertyConfigurator.configure(propiedades);
    log = Logger.getLogger(logName);
  }

  static public void debug(Object cadena)
  {
    log.debug(cadena);
  }

  static public void debug( Object cadena, Throwable excepcion )
  {
    log.debug(cadena, excepcion);
  }

  static public void info(Object cadena)
  {
    log.info(cadena);
  }

  static public void info( Object cadena, Throwable excepcion )
  {
    log.info(cadena, excepcion);
  }

  static public void warn(Object cadena)
  {
    log.warn(cadena);
  }

  static public void warn( Object cadena, Throwable excepcion )
  {
    log.warn(cadena, excepcion);
  }

  static public void error(Object cadena)
  {
    log.error(cadena);
  }

  static public void error( Object cadena, Throwable excepcion )
  {
    log.error(cadena, excepcion);
  }

  static public void fatal(Object cadena)
  {
    log.fatal(cadena);
  }

  static public void fatal( Object cadena, Throwable excepcion )
  {
    log.fatal(cadena, excepcion);
  }
}