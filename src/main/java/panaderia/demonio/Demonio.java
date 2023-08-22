package panaderia.demonio;

import java.util.Date;

import utils.Log4j;

public abstract class Demonio implements Runnable
{
  private boolean run;
  private Thread  hilo;

  protected Demonio()
  {
    run = false;
    hilo = null;
  }

  protected abstract void   task() throws Exception;
  protected abstract void   starting() throws Exception;
  protected abstract void   stopping();
  protected abstract long   getSleepTime() throws Exception;

  public final void run()
  {
    try
    {
      starting();
      Log4j.info("***** Iniciado el demonio *****");
      Log4j.info("***** Hora de inicio "+new Date(System.currentTimeMillis())+" *****");
      while(run)
      {
        Log4j.info("***** Iteracion del demonio *****");
        task();
        sleep();
      }
    }
    catch(Exception e)
    {
      Log4j.error("Error en el demonio --> Deteniendo.", e);
    }
    finally
    {
      Log4j.info("***** Deteniendo el demonio *****");
      stopping();
      Log4j.info("***** Detenido el demonio *****");
      hilo = null;
      run = false;
    }
  }

  /**
   * Inicia la ejecucion del demonio si este no se encuentra ejecutandose en el
   * momento de la llamada
   */
  public final void start()
  {
    if( hilo!=null && hilo.isAlive() )
    {
      run = true;
    }
    else
    {
      run = true;
      hilo = new Thread(this);
      hilo.setDaemon(false);
      hilo.start();
    }
  }

  /**
   * Detiene la ejecucion del demonio. Esta parada no es inmediata pero se espera
   * que ocurra en un tiempo finito.
   */
  public final void stop()
  {
    run = false;
    try{
    	Log4j.info("***** Hacemos wake up del hilo por parada *****");
    	hilo.interrupt();
    }catch (Exception e) {
    	Log4j.info("***** Excepcion por interrupcion ya que el hilo no estaba dormido *****");
	}
  }

  /**
   * Indica si el demonio se encuentra actualmente ejecutandose.
   * @return true en el caso que se este ejecutando o false en cualquier otro caso.
   */
  public final boolean isAlive()
  {
    return run;
  }

  /**
   * Detiene la ejecucion del hilo el tiempo indicado por el metodo getSleepTime
   * implementado por las clases hijas.
   */
  public final void sleep()
  {
    try{ Thread.sleep(getSleepTime()); }
    catch (Exception e){ Log4j.error("Error", e); }
  }
}