<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script>

	function doNuevo(){
		document.getElementById('frmListado').action='doClientesFormulario.do';
		document.getElementById('frmListado').submit();
	}
	

	function doBorrar(){
		if(hayChecked('frmListado')){
			if(confirm('Va a eliminar los registros seleccionados, ¿Desea continuar?')){
				document.getElementById('frmListado').action='doClientesBorrar.do';
				document.getElementById('frmListado').submit();
			}
		}else{
			alert('Debe marcar algún registro para eliminar');
		}
	}
</script>
<html:form styleId="frmListado" method="post"
				action="/doListadoClientes.do">
<table style="background-color: white; border: 1px solid #E5E5E5; width: 100%">
	<tr>
		<td colspan="9" style="font-family: Verdana; font-size: 11px; color: #2DACE4;padding: 5px 0px 0px 5px">
			<img src="img/play.gif"/>&nbsp;Búsqueda<br>
			<hr style="color: #DFDEDE">
		</td>
	</tr>
	<tr>
		<td width="2%"></td>
		
		<td style="font-family: Verdana; font-size: 11px; color: #666666">
			Nombre<br>
			<input id="clNombre" size="16" name="clNombre" type="text" value="${FCLIENTES.clNombre}">
		</td>
		
		<td style="font-family: Verdana; font-size: 11px; color: #666666">
			Apellidos<br>
			<input id="clApellidos" size="16" name="clApellidos" type="text" value="${FCLIENTES.clApellidos}">
		</td>
		
		<td style="font-family: Verdana; font-size: 11px; color: #666666">
			N. Comercial<br>
			<input id="clNombreComercial" size="16" name="clNombreComercial" type="text" value="${FCLIENTES.clNombreComercial}">
		</td>

		<td style="font-family: Verdana; font-size: 11px; color: #666666">
			Direccion<br>
			<input size="16" id="clDireccion" name="clDireccion"  type="text" value="${FCLIENTES.clDireccion}">
		</td>
		
		<td style="font-family: Verdana; font-size: 11px; color: #666666">
			Localidad<br>
			<html:select style="width: 200px" styleId="clLocId" property="clLocId" value="${FCLIENTES.clLocId}">
			  <html:option value="">[Seleccionar]</html:option>
			  <html:options property="locId" labelProperty="locNombre" collection="LISTALOCALIDADES"/>
		    </html:select>
		</td>
		
		<td style="font-family: Verdana; font-size: 11px; color: #666666">
			NIF<br>
			<input size="16" id="clNif" name="clNif"  type="text" value="${FCLIENTES.clNif}">
		</td>
		
		<td style="font-family: Verdana; font-size: 11px; color: #666666">
			Estado<br>
			<html:select  styleClass="js_requerido"  style="width: 80px" styleId="clActivo" property="clActivo" value="${FCLIENTES.clActivo}">
						  <html:option value="1">Activo</html:option>
						  <html:option value="0">Inactivo</html:option>
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
		<td colspan="9" style="">
			&nbsp;
		</td>
	</tr>
	<tr>
		<td colspan="9" style="font-family: Verdana; font-size: 11px; color: #2DACE4;padding: 5px 0px 0px 5px">
			<img src="img/play.gif"/>&nbsp;Resultado<br>
			<hr style="color: #DFDEDE">
		</td>
	</tr>
	<tr>
		<td colspan="9">
			
				<display:table name="LISTACLIENTES" id="clientes" export="false"
					cellpadding="0" cellspacing="0" summary="Tabla Listado"
					sort="external" class="" requestURI="/doListadoClientes.do"
					defaultsort="1" defaultorder="ascending" pagesize="10">
			
					<display:column class="columna porcen2" headerClass="columna porcen2"
						style="${estilo2009}" alt="selecc/deselec" function="checkUncheck()" src="img/marca_desmarca.gif" input="img">
							<input type="checkbox" id="checkList"
								class="check" name="checkList"
								value="${clientes.clId}" />
					</display:column>
			
					<display:column class="columna porcen10"
						headerClass="columna porcen10" sortProperty="clNombre"
						style="color: #F76D2D" title="Nombre" sortable="true">
							${clientes.clNombre}&nbsp;${clientes.clApellidos}
					</display:column>
					
					<display:column class="columna porcen10"
						headerClass="columna porcen10" property="clNombreComercial"
						style="color: #F76D2D" title="Comercial" sortable="true"/>
						
					<display:column class="columna porcen10"
						headerClass="columna porcen10" sortProperty="clTelefono"
						style="color: #F76D2D" title="Telefono" sortable="true">
							<c:if test="${empty clientes.clTelefono}">&nbsp;</c:if>
							<c:if test="${!empty clientes.clTelefono}">${clientes.clTelefono}</c:if>
					</display:column>
					
					<display:column class="columna porcen10"
						headerClass="columna porcen10" sortProperty="clDireccion"
						style="color: #F76D2D" title="Direccion" sortable="true">
							${clientes.clDireccion}, ${clientes.clDatosRelacionados[4]}
					</display:column>
			
					<display:column class="columna porcen2" headerClass="columna porcen2"
						style="${estilo2009}" title="Editar">
							<a href="doClientesFormulario.do?clId=${clientes.clId}">
								<img src="img/editar.gif">
							</a>
						<%-- 	<a href="doListadoProductosCliente.do?pclClId=${clientes.clId}">
								<img src="img/producto.gif">
							</a>
							<a href="doListadoCuadrantes.do?cuClId=${clientes.clId}">
								<img src="img/cuadrante.gif">
							</a> --%>
					</display:column>
			
				</display:table>
			</td>
		</tr>
	</table>
</html:form>