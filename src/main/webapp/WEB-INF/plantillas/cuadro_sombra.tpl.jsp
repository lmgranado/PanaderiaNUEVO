<%-- /WEB-INF/modulos/administracion_sistema/perfiles.ficha.jsp --%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>

<%-- Inicializaciones de atributos --%>
<tiles:useAttribute id="formulario_titulo" name="formulario.titulo"/>
<tiles:useAttribute id="action_todos" name="action_todos"/>
<%-- Fin Inicializaciones de atributos --%>

<!-- Plantilla Cuadro Sombra -->
<table cellpadding="0" cellspacing="0" border="0" class="porcen100 vporcen100" summary="Ficha Cuadro Sombra">
<tbody>
	<tr>				
		<td class="porcen100 vporcen100">
			<table cellpadding="0" cellspacing="0" border="0" class="formulario_tabla"  summary="">
				<tbody>
					<!-- Formulario Titulo -->
					<tr>
						<td class="formulario_titulo">
							<bean:message key="${formulario_titulo}" />										
						</td>
					</tr>
					<!-- Fin Formulario Titulo -->
					
					<!-- Formulario Cuerpo -->				
					<tr>
						<td>
							<tiles:insert attribute="formulario.body" flush="true"/>		
						</td>
					</tr>
					<!-- Fin Formulario Cuerpo -->								
				</tbody>
			</table>
		</td>

</tbody>
</table>
<!-- Fin Plantilla Cuadro Sombra -->
