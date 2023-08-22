<%@ page language="java" pageEncoding="ISO-8859-1"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/c-1_0-rt.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt-1_0-rt.tld" prefix="fmt" %>
<html>
  <head>
    
    <title>Acceso denegado</title>
    
	<meta http-equiv="pragma" content="no-cache"/>
	<meta http-equiv="cache-control" content="no-cache"/>
	<meta http-equiv="expires" content="0"/>    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3"/>
	<meta http-equiv="description" content="This is my page"/>
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
 <body>
 
 
 
 <table style="width: 100%; height: 360px">
	<tr>
		<td colspan="1" style="width: 45%">
			<table>
				<tr>
					<td colspan='2' class="letra_negra">
						<br/>Puede que:<br/>
						- No tenga permiso para acceder a esta zona.<br/>
						- La sesión no esté iniciada o haya expirado.<br/><br/>
						<img alt="" src='img/play_naranja.gif'/> Vuelva a introducir su usuario y clave.<br/><br/>
						<c:choose>
							<c:when test="${empty is_popup}">
								<img alt="" src='img/play_naranja.gif'/> Volver a la <a href='.'>página principal.</a><br/><br/>
							</c:when>
							<c:otherwise>
								<img alt="" src='img/play_naranja.gif'/> Vuelva a la página principal.<br/><br/>
							</c:otherwise>
						</c:choose>
						- Si el problema persiste contacte con el administrador del sistema
					</td>
				</tr>
			</table>
		</td>
		<td colspan="2" style="width: 30%">
			&nbsp;
		</td>
		<td colspan="1"  style="vertical-align: middle; ">
			<img src="img/error.png">
		</td>
	</tr>
	<tr>
		<td colspan='4'>
			${ServletException}
		</td>
	</tr>
	
</table> 
 </body>
</html>
