<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script>
	
	function doInformes(){
		document.forms[0].action = 'doInformeListadoPrecios.do';
		document.forms[0].target = '_blank';
		document.forms[0].submit();
	}
	
	function cargaProductos(){
		document.forms[0].action = 'doInformeListadoPreciosF.do';
		document.forms[0].target = '_self';
		document.forms[0].submit();
	}
	
</script>
<html:form styleId="frmListado" method="post" target="_blank" action="/doInformeListadoPrecios.do">
<table style="background-color: white; border: 1px solid #E5E5E5; width: 100%; height: 250px">
	<tr>
		<td colspan="5" style="font-family: Verdana; font-size: 11px; color: #2DACE4;padding: 5px 0px 0px 5px">
			<img src="img/play.gif"/>&nbsp;Establezca el filtro<br>
			<hr style="color: #DFDEDE">
		</td>
	</tr>
	<tr>
		<td width="2%">
		</td>
		<td style="font-family: Verdana; font-size: 11px; color: #666666">
			Filtro tipo<br>
			<select style="width: 200px" id="tipo" name="tipo">
			  <option value="">[Generico]</option>
			  <option value="cliente" <c:if test="${!empty tipo}">selected="selected"</c:if> >[Clientes]</option>
		    </select>
		</td>
		<td style="font-family: Verdana; font-size: 11px; color: #666666">
			Filtro clientes<br>
			<select style="width: 200px" id="cliente" name="cliente" >
			  <option value="">[Todas]</option>
			  <c:forEach var="client" items="${CLIENTES}">
			  	<option  <c:if test="${client.clId==cliente}">selected="selected"</c:if>  value="${client.clId}">${client.clNombreComercial}</option>
			  </c:forEach>
		    </select>
		</td>
		<td style="font-family: Verdana; font-size: 11px; color: #666666">
			Filtro Familia<br>
			<select style="width: 200px" id="familia" name="familia" onchange="cargaProductos();" >
			  <option value="">[Todas]</option>
			  <c:forEach var="famili" items="${FAMILIAS}">
			  	<option  <c:if test="${famili.famId==familia}">selected="selected"</c:if>  value="${famili.famId}">${famili.famNombre}</option>
			  </c:forEach>
		    </select>
		</td>
		<td style="font-family: Verdana; font-size: 11px; color: #666666">
			Filtro Productos<br>
			<select style="width: 200px" id="producto" name="producto">
			  <option value="">[Todas]</option>
			  <c:forEach var="product" items="${PRODUCTOS}">
			  	<option  <c:if test="${product[5]==producto}">selected="selected"</c:if>  value="${product[5]}">${product[1]}</option>
			  </c:forEach>
		    </select>
		</td>
		
		<td style="font-family: Verdana; font-size: 11px; color: #666666">
			&nbsp;
		</td>
	</tr>
	<tr>
		<td colspan="6" style="">
			&nbsp;
		</td>
	</tr>
	</table>
</html:form>