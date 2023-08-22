<%@ page language="java" pageEncoding="ISO-8859-1"%>
<jsp:directive.page import="panaderia.demonio.DemonioFacturacion"/>
<%
String inicio = request.getParameter("inicio");
String fin = request.getParameter("fin");
String tipo = request.getParameter("tipo");
if(inicio!=null){
	DemonioFacturacion demonio = new DemonioFacturacion();
	demonio.tratarFacturas(inicio, fin, tipo);
}
if(request.getParameter("reinicio")!=null){
	DemonioFacturacion.getInstance().stop();
	DemonioFacturacion.getInstance().start();
}
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>My JSP 'lanzaDemonio.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body> 
  <form action="lanzaDemonio.jsp">
    Fecha Inicio:<input name="inicio">
    Fecha Fin:<input name="fin">
    Tipo: <select name="tipo">
    	  	<option value="SEMANAL">SEMANAL</option>
    	  	<option value="QUINCENAL">QUINCENAL</option>
    	  	<option value="MENSUAL">MENSUAL</option>
    	  </select>
   <input type="submit">
   <a href="lanzaDemonio.jsp?reinicio=true">Reiniciar Demonio</a>
   </form>
  </body>
</html>
