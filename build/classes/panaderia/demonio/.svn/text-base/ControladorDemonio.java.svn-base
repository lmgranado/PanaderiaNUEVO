package panaderia.demonio;


import javax.servlet.*;
import javax.servlet.http.*;

public class ControladorDemonio extends HttpServlet
{
  private static final long serialVersionUID = 1L;

  public void init(ServletConfig config) throws ServletException
  {
    super.init(config);

    //Se inicializa y ejecuta el demonio
    DemonioFacturacion.getInstance().start();
    DemonioVacacionesClientes.getInstance().start();
  }

  public void destroy()
  {
    super.destroy();

    DemonioFacturacion.getInstance().stop();
    DemonioVacacionesClientes.getInstance().stop();
  }
}