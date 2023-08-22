<%-- /WEB-INF/tiles/mensajes.jsp --%>
<%@page language="java" pageEncoding="UTF-8" session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script>
	function cambiaVisibilidad(){
		document.getElementById('mensaje').style.visibility = 'hidden';
	}
</script>
	<tr>
		<td style="color: #990000; font-size: 11px; font-weight: bold;">
			<logic:messagesPresent property="info" message="true">
				<html:messages id="msg" property="info" header="messages.header" footer="messages.footer" message="true">
					<bean:message key="messages.prefix"/><bean:write  name="msg"/><bean:message key="messages.sufix"/>
				</html:messages>
				<html:errors property="info"/>
			</logic:messagesPresent>
		</td>
	</tr>
