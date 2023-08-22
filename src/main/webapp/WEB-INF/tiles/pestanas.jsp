<%-- /WEB-INF/tiles/botonera.jsp --%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/c-1_0-rt.tld" prefix="c"%>

<!--  Botonera -->
<c:if test="${!empty PESTANAS}">
	<table cellpadding="0" cellspacing="0" class="" summary="">
		<tbody>
			<tr>
				<c:forEach var="pestana" items="${PESTANAS}">
					<c:if test="${pestana.hijo==''}">
						<c:if test="${pestana.marcado!=''}">
							<c:set value="pestana_fondo_color" var="estilo1"/>
							<c:set value="pestana_letra" var="estilo2"/>
						</c:if>
						<c:if test="${pestana.marcado==''}">
							<c:set value="pestana_fondo_color_padre" var="estilo1"/>
							<c:set value="pestana_letra_des" var="estilo2"/>
						</c:if>
						
						<td class="${estilo1}">
							<c:choose>
								<c:when test="${pestana.action==''}">
									${pestana.txt}
								</c:when>
								<c:otherwise>
									<a class="${estilo2}" style=' ${pestana.javascript}' href="${pestana.action}">${pestana.txt}</a>
								</c:otherwise>
							</c:choose>
						</td><td>&nbsp;</td>
					</c:if>
				</c:forEach>
			</tr>
		</tbody>
	</table>
</c:if>
<!--  Fin Botonera -->