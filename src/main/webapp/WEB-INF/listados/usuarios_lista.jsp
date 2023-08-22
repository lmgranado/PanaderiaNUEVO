<%@ page language="java" pageEncoding="UTF-8" %>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script>

	function doNuevo(){
		document.getElementById('frmListado').action='doUsuariosFormulario.do';
		document.getElementById('frmListado').submit();
	}
	

	function doBorrar(){
		if(hayChecked('frmListado')){
			if(confirm('Va a eliminar los registros seleccionados, ¿Desea continuar?')){
				document.getElementById('frmListado').action='doUsuariosBorrar.do';
				document.getElementById('frmListado').submit();
			}
		}else{
			alert('Debe marcar algún registro para eliminar');
		}
	}
</script>
<html:form styleId="frmListado" method="post" action="/doListadoUsuarios.do">
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
			Nombre<br>
			<input id="usNombre" size="30" name="usNombre" type="text" value="${FUSUARIOS.usNombre}">
		</td>
		
		<td style="font-family: Verdana; font-size: 11px; color: #666666">
			Apellidos<br>
			<input id="usApellidos" size="30" name="usApellidos" type="text" value="${FUSUARIOS.usApellidos}">
		</td>
		
		<td style="font-family: Verdana; font-size: 11px; color: #666666">
			NIF<br>
			<input size="16" id="usNif" name="usNif"  type="text" value="${FUSUARIOS.usNif}">
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
			
				<display:table name="LISTAUSUARIOS" id="usuarios" export="true"
					cellpadding="0" cellspacing="0" summary="Tabla Listado"
					sort="external" class="" requestURI="/doListadoUsuarios.do"
					defaultsort="1" defaultorder="ascending" pagesize="10">
			
					<display:column class="columna porcen2" headerClass="columna porcen2"
						style="${estilo2009}" media="html" alt="selecc/deselec" function="checkUncheck()" src="img/marca_desmarca.gif" input="img">
							<input type="checkbox" id="checkList"
								class="check" name="checkList"
								value="${usuarios.usId}" />
					</display:column>
			
					<display:column class="columna porcen10" 
						headerClass="columna porcen10" sortProperty="usNombre" media="all"
						style="color: #F76D2D" title="Nombre" sortable="true">
							${usuarios.usNombre} ${usuarios.usApellidos}
					</display:column>
							
					<display:column class="columna porcen10"
						headerClass="columna porcen10" sortProperty="usDireccion"  media="all"
						style="color: #F76D2D" title="Direccion" sortable="true">
							<c:if test="${empty usuarios.usDireccion}">-</c:if>
							<c:if test="${!empty usuarios.usDireccion}">${usuarios.usDireccion}</c:if>
					</display:column>
								
					<display:column class="columna porcen10"
						headerClass="columna porcen10" sortProperty="usNif"  media="all"
						style="color: #F76D2D" title="NIF" sortable="true">
							<c:if test="${empty usuarios.usNif}">-</c:if>
							<c:if test="${!empty usuarios.usNif}">${usuarios.usNif}</c:if>
					</display:column>
					
					<display:column class="columna porcen10"
						headerClass="columna porcen10"
						style="color: #F76D2D" title="Telefono" sortable="false"  media="all">
							<c:if test="${empty usuarios.usTelefono}">-</c:if>
							<c:if test="${!empty usuarios.usTelefono}">${usuarios.usTelefono}</c:if>
					</display:column>
					
					
			
					<display:column class="columna porcen2" headerClass="columna porcen2"
						style="${estilo2009}" title="Editar" media="html">
							<a href="doUsuariosFormulario.do?usId=${usuarios.usId}">
								<img src="img/editar.gif">
							</a>
					</display:column>
			
				</display:table>
			</td>
		</tr>
	</table>
</html:form>