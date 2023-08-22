<%-- /WEB-INF/plantillas/modelo.tpl.jsp --%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/c-1_0-rt.tld" prefix="c"%>
<%-- Inicializaciones de atributos --%>
<tiles:useAttribute name="titulo_opcion"/>
<tiles:useAttribute name="texto_ayuda"/>
<tiles:useAttribute name="mensajes"/>
<%-- Fin Inicializaciones de atributos --%>
<!DOCTYPE HTML PUBLIC "-//W3C//Ddiv HTML 4.01 Transitional//EN">
<html>
<head>
	<!-- Head de plantilla -->
	<tiles:insert attribute="head" flush="true"/>
	<!-- Fin Head de plantilla -->
</head>
<body style="background-color: #FDF8D7">
	<!-- Cuerpo -->
	<table style="width: 100%" cellspacing="0" cellpadding="0">
	
	
	
	
		<!-- Titulo -->
		<c:if test="${titulo_opcion!=''}">
		<tr>
			<td colspan="2">
				<u style="color: #8888A0"><span style='font-size: 12px; font-weight: bold; color: #000033;'><bean:message key="${titulo_opcion}"/></span></u> <br>
			</td>
		</tr>
		</c:if>
		<!-- Titulo -->
		<c:if test="${texto_ayuda!=''}">
			<table style="width: 98%; margin-top: 10px">
				<tr>
					<td class='tabla_avisos letra_politica' style='text-align: left'>
						<bean:message key="${texto_ayuda}"/>
					</td>
				</tr>
			</table>
		</c:if>
		<!-- Fin Titulo -->
		<tr>
			<td colspan="2">
				<tiles:insert attribute="body" flush="true"/>
			</td>
		</tr>
		<c:if test="${mensajes!=''}">		
			<tiles:insert attribute="mensajes"  flush="true"/>
		</c:if>
	</table>
	<!-- Fin Cuerpo -->							
</body>
</html>