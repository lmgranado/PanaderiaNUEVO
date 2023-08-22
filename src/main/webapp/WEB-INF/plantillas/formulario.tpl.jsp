<%-- /WEB-INF/plantillas/lista.tpl.jsp --%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="/WEB-INF/taglibs-string.tld" prefix="str"%>
<%-- Inicializaciones de atributos --%>
<%try{ %>
<tiles:useAttribute name="cabecera"/>
<tiles:useAttribute name="menu_izdo"/>
<tiles:useAttribute name="img_opcion"/>
<tiles:useAttribute name="botonera"/>
<tiles:useAttribute name="body"/>
<tiles:useAttribute name="pestanas"/>
<tiles:useAttribute name="mensajes"/>
<tiles:useAttribute name="foot"/>
<tiles:useAttribute name="action"/>
<tiles:useAttribute name="enctype"/>
<tiles:useAttribute name="fondo"/>
<tiles:useAttribute name="titulo_opcion" id="titulo_opcion"/>
<c:set scope="session" var="titulo_opcion">${titulo_opcion}</c:set>
<tiles:useAttribute name="texto_ayuda" id="texto_ayuda"/>
<c:set scope="session" var="texto_ayuda">${texto_ayuda}</c:set>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<head>
	<!-- Head de plantilla -->
	<tiles:insert attribute="head" flush="true"/>
	<!-- Fin Head de plantilla -->
</head>
<body>
	<c:if test="${(cabecera!='') || (menu_izdo!='') || (miga_pan!='') || (titulo_opcion!='') || (botonera!='') || (action!='') || (body!='') || (mensajes!='') || (foot!='') || (escritorio!='')}">
	<!-- Body de Plantilla -->
	<table style="width: 100%; background-image: url('img/pixel_cuerpo.jpg'); background-repeat: x; background-position: top; cellpadding="0" cellspacing="0">
		<tr>
			<td>
				<!-- Fila Cabecera -->
				<c:if test="${cabecera!=''}">
					<tiles:insert attribute="cabecera" flush="true"/>
				</c:if>
				<!-- Fila Cabecera -->
			</td>
		</tr>
	
		<!-- Fila Cuerpo -->
		<c:if test="${(menu_izdo!='') || (miga_pan!='') || (titulo_opcion!='') || (botonera!='') || (action!='') || (body!='') || (mensajes!='')}">
		<tr>
			<td>
				<table style="width: 100%; background-color: #E4E4E4" cellpadding="0" cellspacing="0">
					<tr>
						<td class="${fondo}" style="width: 85%; vertical-align: top">
							<!-- Centro -->
							<c:if test="${(miga_pan!='') || (titulo_opcion!='') || (botonera!='') || (seleccionar!='') || (body!='') || (mensajes!='')}">
								<!-- Titulo -->
								
								<!-- Fin Titulo -->
								<!-- Fin Texto ayuda -->
								<!-- Listado -->
								<c:if test="${body!=''}">	
									<html:form enctype="${enctype}" action="${action}" styleId="frmDetalle" method="post">							
										<!-- Body -->		
										<table cellpadding="0" cellspacing="0" style="width: 100%; background-image: url('img/pixel_cuerpo.jpg'); background-repeat: x; background-position: top;">
											<tr>
												<td style="width: 100%">
													<c:if test="${titulo_opcion!=''}">							
														&nbsp;&nbsp;&nbsp;<span style="color: #2DACE4; font-size: 16px; font-weight: bold;"><img src="${img_opcion}"/> ${titulo_opcion}</span>
														<br><br>
													</c:if>
													<c:if test="${pestanas!=''}">
														<tiles:insert attribute="pestanas" flush="true"/>
													</c:if>
													<c:if test="${body!=''}">
														<tiles:insert attribute="body" flush="true"/>
													</c:if>
												</td>
											</tr>
										</table>
										<!-- Fin Body -->
										<!-- Botonera y mensajes-->
										<c:if test="${botonera!='' || mensajes!=''}">
											<tiles:insert attribute="botonera" flush="true"/>
										</c:if>  
										<!-- Fin Botonera -->
									</html:form>				
								</c:if>
								<!-- Fin Listado -->
							</c:if>		
							<!-- Fin Centro -->
						</td>
					</tr>
				</table>
			</td>
		</tr>
			<!-- Fin Tabla Cuerpo -->
		</c:if>
		<!-- Fin Fila Cuerpo -->
		
		<!-- Pie -->
		<!-- <tr>
			<td>
				<c:if test="${foot!=''}">
						<tiles:insert attribute="foot" flush="true"/>						
				</c:if>
			</td>
		</tr> <!--
		<!-- Fin Pie -->
	</table>
	<!-- Fin Tabla Body -->
	</c:if>
	<!-- Fin Body de Plantilla -->    
</body>
</html>
<%}catch(Exception e){
	System.out.println(e);
} %>