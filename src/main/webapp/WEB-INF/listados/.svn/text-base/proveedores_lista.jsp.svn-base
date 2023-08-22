<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script>

	function doNuevo(){
		document.getElementById('frmListado').action='doProveedoresFormulario.do';
		document.getElementById('frmListado').submit();
	}
	

	function doBorrar(){
		if(hayChecked('frmListado')){
			if(confirm('Va a eliminar los registros seleccionados, ¿Desea continuar?')){
				document.getElementById('frmListado').action='doProveedoresBorrar.do';
				document.getElementById('frmListado').submit();
			}
		}else{
			alert('Debe marcar algún registro para eliminar');
		}
	}
</script>
<html:form styleId="frmListado" method="post"
				action="/doListadoProveedores.do">
<table style="background-color: white; border: 1px solid #E5E5E5; width: 100%">
	<tr>
		<td colspan="7" style="font-family: Verdana; font-size: 11px; color: #2DACE4;padding: 5px 0px 0px 5px">
			<img src="img/play.gif"/>&nbsp;Búsqueda<br>
			<hr style="color: #DFDEDE">
		</td>
	</tr>
	<tr>
		<td width="2%"></td>
		<td style="font-family: Verdana; font-size: 11px; color: #666666">
			Nombre Proveedor<br>
			<input id="prNombre" size="24" name="prNombre" type="text" value="${FPROVEEDORES.prNombre}">
		</td>
		
		<td style="font-family: Verdana; font-size: 11px; color: #666666">
			Nombre Comercial<br>
			<input id="prNombreComercial" size="24" name="prNombreComercial" type="text" value="${FPROVEEDORES.prNombreComercial}">
		</td>

		<td style="font-family: Verdana; font-size: 11px; color: #666666">
			Direccion<br>
			<input size="16" id="prDireccion" name="prDireccion"  type="text" value="${FPROVEEDORES.prDireccion}">
		</td>
		
		
		<td style="font-family: Verdana; font-size: 11px; color: #666666">
			NIF<br>
			<input size="16" id="prNif" name="prNif"  type="text" value="${FPROVEEDORES.prNif}">
		</td>
		
		<td style="font-family: Verdana; font-size: 11px; color: #666666">
			Localidad<br>
			<html:select style="width: 200px" styleId="prLocId" property="prLocId" value="${FPROVEEDORES.prLocId}">
			  <html:option value="">[Seleccionar]</html:option>
			  <html:options property="locId" labelProperty="locNombre" collection="LISTALOCALIDADES"/>
		    </html:select>
		</td>
		
		<td style="font-family: Verdana; font-size: 11px; color: #666666">
		<input id="filtro" name="filtro"  type="hidden" value="">
			<br><a onclick="javascript: doBuscar(this)">
				<img src="img/buscar.gif" title="Aplicar filtro"/>
				</a>
				<a onclick="javascript: doLimpiar(this)">
				<img src="img/borrar.gif" title="Borrar filtro"/>
				</a>
		</td>
	</tr>
	<tr>
		<td colspan="7" style="">
			&nbsp;
		</td>
	</tr>
	<tr>
		<td colspan="7" style="font-family: Verdana; font-size: 11px; color: #2DACE4;padding: 5px 0px 0px 5px">
			<img src="img/play.gif"/>&nbsp;Resultado<br>
			<hr style="color: #DFDEDE">
		</td>
	</tr>
	<tr>
		<td colspan="7">
			
				<display:table name="LISTAPROVEEDORES" id="Proveedores" export="false"
					cellpadding="0" cellspacing="0" summary="Tabla Listado"
					sort="external" class="" requestURI="/doListadoProveedores.do"
					defaultsort="1" defaultorder="ascending" pagesize="10">
			
					<display:column class="columna porcen2" headerClass="columna porcen2"
						style="${estilo2009}" alt="selecc/deselec" function="checkUncheck()" src="img/marca_desmarca.gif" input="img">
							<input type="checkbox" id="checkList"
								class="check" name="checkList"
								value="${Proveedores.prId}" />
					</display:column>
			
					<display:column class="columna porcen10"
						headerClass="columna porcen10" sortProperty="prNombre"
						style="color: #F76D2D" title="Nombre del Proveedor" sortable="true">
							${Proveedores.prNombre}&nbsp;${Proveedores.prApellidos}
					</display:column>
					
					<display:column class="columna porcen10"
						headerClass="columna porcen10" property="prNombreComercial"
						style="color: #F76D2D" title="Nombre Comercial" sortable="true"/>
						
					<display:column class="columna porcen10"
						headerClass="columna porcen10" sortProperty="prNif"
						style="color: #F76D2D" title="NIF" sortable="true">
							${Proveedores.prNif}
					</display:column>
					
					<display:column class="columna porcen10"
						headerClass="columna porcen10" sortProperty="prDireccion"
						style="color: #F76D2D" title="Direccion" sortable="true">
							${Proveedores.prDireccion}, ${Proveedores.prDatosRelacionados[4]}
					</display:column>
					
					<display:column class="columna porcen10"
						headerClass="columna porcen10"
						style="color: #F76D2D" title="Telefono" sortable="true">
							<c:if test="${empty Proveedores.prTelefono}">&nbsp;</c:if>
							<c:if test="${!empty Proveedores.prTelefono}">${Proveedores.prTelefono}</c:if>
					</display:column>
					
					<display:column class="columna porcen2" headerClass="columna porcen2"
						style="${estilo2009}" title="Editar">
							<a href="doProveedoresFormulario.do?prId=${Proveedores.prId}">
								<img src="img/editar.gif">
							</a>
					</display:column>
			
				</display:table>
			</td>
		</tr>
	</table>
</html:form>