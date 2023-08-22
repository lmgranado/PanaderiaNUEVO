<%-- /WEB-INF/plantillas/modelo.tpl.jsp --%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/taglibs-string.tld" prefix="str"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%-- Inicializaciones de atributos --%>
<tiles:useAttribute name="titulo_opcion"/>
<tiles:useAttribute name="menu_izdo"/>		
<tiles:useAttribute name="fondo"/>	
<tiles:useAttribute name="mensajesM"/>	
<%-- Fin Inicializaciones de atributos --%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<!-- Head de plantilla -->
	<tiles:insert attribute="head" flush="true"/>
	<!-- Fin Head de plantilla -->
</head>
<body>
  <table style="width: 100%; background-color: #FFFFFF" cellpadding="0" cellspacing="0">
	<tr>
		<td>
			<tiles:insert attribute="cabecera" flush="true"/>
		</td>
	</tr>
	<tr>
		<td>
			<table style="width: 100%;">
				<tr>
					<!-- menu -->
					<c:if test="${menu_izdo!=''}">
						<td>
							<tiles:insert attribute="menu_izdo" flush="true"/>
						</td>
					</c:if>		
					<!-- Fin menu -->
					<td class='${fondo}'>
						<!-- titulo -->
						<c:if test="${titulo_opcion!=''}">
							&nbsp;<bean:message key="${titulo_opcion}"/> 
						</c:if>
						<!-- Fin titulo -->
						<!-- cuerpo -->
						<tiles:insert attribute="body" flush="true"/>
						<!-- Fin cuerpo -->	
						<!-- Mensajes y Errores -->
						<c:if test="${mensajesM!=''}">														
							<tiles:insert attribute="mensajesM" flush="true"/>
						</c:if>
						<!-- Fin Mensajes y Errores -->
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td>
			<tiles:insert attribute="foot" flush="true"/>
		</td>
	</tr>
  </table>
 
</body>
</html>