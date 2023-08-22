<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script>

	function doNuevo(){
		document.getElementById('frmListado').action='doCobradoresFormulario.do';
		document.getElementById('frmListado').submit();
	}
	

	function doBorrar(){
		if(hayChecked('frmListado')){
			if(confirm('Va a eliminar los registros seleccionados, ¿Desea continuar?')){
				document.getElementById('frmListado').action='doCobradoresBorrar.do';
				document.getElementById('frmListado').submit();
			}
		}else{
			alert('Debe marcar algún registro para eliminar');
		}
	}
</script>
<html:form styleId="frmListado" method="post"
				action="/doListadoCobradores.do">
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
			<input id="cobNombre" size="16" name="cobNombre" type="text" value="${FCOBRADORES.cobNombre}">
		</td>

		<td style="font-family: Verdana; font-size: 11px; color: #666666">
			Direccion<br>
			<input size="16" id="cobDireccion" name="cobDireccion"  type="text" value="${FCOBRADORES.cobDireccion}">
		</td>
				
		<td style="font-family: Verdana; font-size: 11px; color: #666666">
			NIF<br>
			<input size="16" id="cobNif" name="cobNif"  type="text" value="${FCOBRADORES.cobNif}">
		</td>
		
		<td style="font-family: Verdana; font-size: 11px; color: #666666">
			Localidad<br>
			<html:select style="width: 200px" styleId="cobLocId" property="cobLocId" value="${FCOBRADORES.cobLocId}">
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
			
				<display:table name="LISTACOBRADORES" id="cobradores" export="false"
					cellpadding="0" cellspacing="0" summary="Tabla Listado"
					sort="external" class="" requestURI="/doListadoCobradores.do"
					defaultsort="1" defaultorder="ascending" pagesize="10">
			
					<display:column class="columna porcen2" headerClass="columna porcen2"
						style="${estilo2009}" alt="selecc/deselec" function="checkUncheck()" src="img/marca_desmarca.gif" input="img">
							<input type="checkbox" id="checkList" name="checkList" class="check"
								value="${cobradores.cobId}" />
					</display:column>
			
					<display:column class="columna porcen10"
						headerClass="columna porcen10" sortProperty="cobNombre"
						style="color: #F76D2D" title="Nombre" sortable="true">
							${cobradores.cobNombre}&nbsp;${cobradores.cobApellidos}
					</display:column>
								
					<display:column class="columna porcen10"
						headerClass="columna porcen10" sortProperty="cobDireccion"
						style="color: #F76D2D" title="Direccion" sortable="true">
							<c:if test="${empty cobradores.cobDireccion}">&nbsp;</c:if>
							<c:if test="${!empty cobradores.cobDireccion}">${cobradores.cobDireccion}</c:if>
					</display:column>	
					
					<display:column class="columna porcen10"
						headerClass="columna porcen10"
						style="color: #F76D2D" title="NIF" sortable="false">
							<c:if test="${empty cobradores.cobNif}">&nbsp;</c:if>
							<c:if test="${!empty cobradores.cobNif}">${cobradores.cobNif}</c:if>
					</display:column>
									
					<display:column class="columna porcen10"
						headerClass="columna porcen10"
						style="color: #F76D2D" title="Telefono" sortable="false">
							<c:if test="${empty cobradores.cobTelefono}">&nbsp;</c:if>
							<c:if test="${!empty cobradores.cobTelefono}">${cobradores.cobTelefono}</c:if>
					</display:column>
					
					
			
					<display:column class="columna porcen2" headerClass="columna porcen2"
						style="${estilo2009}" title="Editar">
							<a href="doCobradoresFormulario.do?cobId=${cobradores.cobId}">
								<img src="img/editar.gif">
							</a>
					</display:column>
			
				</display:table>
			</td>
		</tr>
	</table>
</html:form>