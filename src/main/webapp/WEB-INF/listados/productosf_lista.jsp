<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script>
	
	function doInformes(){
		document.forms[0].submit();
	}
	
</script>
<html:form styleId="frmListado" method="post" target="_blank" action="/doInformeListadoProductos.do">
<table style="background-color: white; border: 1px solid #E5E5E5; width: 100%; height: 250px">
	<tr>
		<td colspan="3" style="font-family: Verdana; font-size: 11px; color: #2DACE4;padding: 5px 0px 0px 5px">
			<img src="img/play.gif"/>&nbsp;Establezca el filtro<br>
			<hr style="color: #DFDEDE">
		</td>
	</tr>
	<tr>
		<td width="2%">
		</td>
		<td style="font-family: Verdana; font-size: 11px; color: #666666">
			Filtro familia<br>
			<select style="width: 200px" id="prodFamId" name="prodFamId">
			  <option value="">[Todas]</option>
			  <c:forEach var="fam" items="${FAMILIAS}">
			  	<option value="${fam.famId}">${fam.famNombre}</option>
			  </c:forEach>
		    </select>
		</td>
		<td style="font-family: Verdana; font-size: 11px; color: #666666">
			incluir importe con media<br>
			<select style="width: 100px" id="media" name="media">
			  <option value="">[NO]</option>
			  <option value="1">[SI]</option>
		    </select>
		</td>
		
		<td style="font-family: Verdana; font-size: 11px; color: #666666">
			&nbsp;
		</td>
	</tr>
	<tr>
		<td colspan="4" style="">
			&nbsp;
		</td>
	</tr>
	</table>
</html:form>