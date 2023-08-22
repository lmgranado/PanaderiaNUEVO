<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script>
	
	function doActualizar(){
		if(validateFields(document.getElementById('frmListado').name)){
			if(confirm('Se van ha actualizar los precios para el filtro indicado. ¿Desea continuar?')){
				document.forms[0].action = 'doActualizadorPreciosGrabar.do';
				document.forms[0].submit();
			}
		}
	}
	
</script>
<html:form styleId="frmListado" method="post"
				action="/doActualizadorPrecios.do">
<table style="background-color: white; border: 1px solid #E5E5E5; width: 100%; height: 250px">
	<tr>
		<td colspan="5" style="font-family: Verdana; font-size: 11px; color: #2DACE4;padding: 5px 0px 0px 5px">
			<img src="img/play.gif"/>&nbsp;Establezca el filtro para la actualización de precios<br>
			<hr style="color: #DFDEDE">
		</td>
	</tr>
	<tr>
		<td width="2%"></td>	
		<td style="font-family: Verdana; font-size: 11px; color: #666666">
			Cliente<br>
			<html:select style="width: 200px" styleId="clId" property="clId" value="${clId}">
			  <html:option value="">[Seleccionar]</html:option>
			  <html:options property="clId" labelProperty="clNombreComercial" collection="CLIENTESLISTA"/>
		    </html:select>
		</td>
		
		<td style="font-family: Verdana; font-size: 11px; color: #666666">
			Ruta<br>
			<html:select styleClass="js_requerido" style="width: 200px" styleId="rutId" property="rutId" value="${rutId}">
			  <html:option value="">[Seleccionar]</html:option>
			  <html:options property="rutId" labelProperty="rutNombre" collection="RUTASLISTA"/>
		    </html:select>
		</td>
		
		<td style="font-family: Verdana; font-size: 11px; color: #666666">
			Localidad<br>
			<html:select style="width: 200px" styleId="locId" property="locId" value="${locId}">
			  <html:option value="">[Seleccionar]</html:option>
			  <html:options property="locId" labelProperty="locNombre" collection="LOCALIDADESLISTA"/>
		    </html:select>
		</td>
				
		<td style="font-family: Verdana; font-size: 11px; color: #666666">
			&nbsp;
		</td>
	</tr>
	
	<tr>
		<td width="2%"></td>	
		<td style="font-family: Verdana; font-size: 11px; color: #666666">
			Actualizar por:<br>
			<html:select style="width: 100px" styleClass="js_requerido" styleId="actTipo" property="actTipo" value="${actTipo}">
			  <html:option value="1">Porcentaje</html:option>
			  <html:option value="2">Euros</html:option>
		    </html:select>
		</td>
		
		<td colspan="1" style="font-family: Verdana; font-size: 11px; color: #666666">
			Indique la cantidad que quiere actualizar<br>
			<input type="text" id="actCantidad" class="js_requerido" value="${actCantidad}" onblur="javascript:this.value=formato_numero(this.value,1)" name="actCantidad">
		</td>
				
		<td style="font-family: Verdana; font-size: 11px; color: #666666">
		<input id="filtro" name="filtro"  type="hidden" value="">
			<br><a onclick="javascript: if(validateFields(document.getElementById('frmListado').name)){doBuscar(this)};">
				<img src="img/buscar.gif" title="Aplicar filtro"/>
				</a>
				<a onclick="javascript: doLimpiar(this)">
				<img src="img/borrar.gif" title="Borrar filtro"/>
				</a>
		</td>
	</tr>
	<tr>
		<td colspan="4" style="">
			&nbsp;
		</td>
	</tr>
    <tr>
		<td colspan="5" style="font-family: Verdana; font-size: 11px; color: #2DACE4;padding: 5px 0px 0px 5px">
			<img src="img/play.gif"/>&nbsp;Resultado<br>
			<hr style="color: #DFDEDE">
		</td>
	</tr>
	<tr>
		<td colspan="4">
			
				<display:table name="LISTAACTUALIZADORPRECIOS" id="actualizador" export="false"
					cellpadding="0" cellspacing="0" summary="Tabla Listado"
					sort="external" class="" requestURI="/doActualizadorPrecios.do"
					defaultsort="1" defaultorder="ascending" pagesize="20">
			
					<display:column class="columna porcen15"
						headerClass="columna porcen15" sortProperty="producto"
						style="color: #F76D2D" title="Producto" sortable="true">
							${actualizador.producto}
					</display:column>
					
					<display:column class="columna porcen15"
						headerClass="columna porcen15"
						style="color: #F76D2D" title="Nombre del Cliente">
							${actualizador.cliente}
					</display:column>
					
					<display:column class="columna porcen10"
						headerClass="columna porcen15" 
						style="color: #F76D2D" title="Precio actual (&euro;)">
							<fmt:formatNumber pattern="###,###.###" var="precio">${actualizador.precio}</fmt:formatNumber>
							${precio}
					</display:column>
					
					<display:column class="columna porcen5"
						headerClass="columna porcen15"
						style="color: #F76D2D" title="Precio actualizado (&euro;)">
							<fmt:formatNumber pattern="###,###.###" var="actualizacion">${actualizador.actualizacion}</fmt:formatNumber>
							${actualizacion}
					</display:column>

				</display:table>
			</td>
		</tr>
	</table>
</html:form>