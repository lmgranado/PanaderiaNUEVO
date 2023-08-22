<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/c-1_0-rt.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
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
	
	
	<tr>
		<td class='fondo_sup' style="text-align: left; vertical-align: top; font-family: Verdana; font-size: 24px; color: white; padding: 0px 0px 0px 10px">
			Gestión Panadería<br><br>
		</td>
		<td class='fondo_sup' style="text-align: right;">
				<img style="vertical-align: baseline;" src="img/logo.jpg" border="0"/>
		</td>
	</tr>
		<!-- Fin Titulo -->
		<tr>
          <td align="center" colspan='4' style="padding-left: 20px;"> 
			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="texto">
              <tr> 
                <td style='width: 15%' align="left" class="texto_negrita">Nombre</td>
                <td style='width: 40%' align="left"> 
				<html:text size='28' maxlength="75" property="usuario" name="usuario" value=""/>&nbsp;<img src="img/asterisco.gif" alt="<bean:message key='campo.obligatorio'/>" title="<bean:message key='campo.obligatorio'/>">
				</td>
				<td style='width: 4%'>&nbsp;</td>
                <td style='width: 11%' align="left" class="texto_negrita">Contraseña</td>
                <td style='width: 30%' align="left"> 
				<html:text size='28' maxlength="75" property="passw" name="passw" value=""/>&nbsp;<img src="img/asterisco.gif" alt="<bean:message key='campo.obligatorio'/>" title="<bean:message key='campo.obligatorio'/>">
                </td>
              </tr>
            </table>
		  </td>
        </tr>
		<c:if test="${mensajes!=''}">		
			<tiles:insert attribute="mensajes"  flush="true"/>
		</c:if>
	</table>
	<!-- Fin Cuerpo -->							
</body>
</html>