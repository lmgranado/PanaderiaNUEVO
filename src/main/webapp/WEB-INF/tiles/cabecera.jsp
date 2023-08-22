<%-- /WEB-INF/tiles/cabecera.jsp --%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/fmt-1_0-rt.tld" prefix="fmt" %>
<%@taglib uri="/WEB-INF/taglibs-string.tld" prefix="str"%>
<table cellpadding="0" cellspacing="0" style="width: 100%">
	<tr>
		<td width="80%" class='fondo_sup' style="padding-top: 10px; text-align: left; vertical-align: top; font-family: Verdana; font-size: 24px; color: white; padding: 10px 0px 0px 10px">
			Programa De Gestión<br><br><script type="text/javascript">var dmWorkPath="/js/";</script>
			<script type="text/javascript" src="js/dmenu.js"></script>
			<c:if test="${empty sinMenu || sinMenu==null}">
				<c:choose>
					 <c:when test="${usuario.usAdministrador == 1}">
		                	<script type="text/javascript" src="js/data-deluxe-menu_administrador.js"></script>
		            </c:when>
		            <c:otherwise>
							<script type="text/javascript" src="js/data-deluxe-menu.js"></script>
					</c:otherwise>
				</c:choose>
			</c:if>
		</td>
		<!--  <td width="20%" class='fondo_sup' style="text-align: right;vertical-align: top">
				<img style="vertical-align: baseline;" src="img/log.jpg" border="0"/>
		</td>
		-->
	</tr>
</table>
