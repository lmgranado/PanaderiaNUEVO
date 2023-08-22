<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script>
	
	function doBorrar(){
		if(hayChecked('frmListado')){
			if(confirm('Va a eliminar los registros seleccionados, ¿Desea continuar?')){
				document.getElementById('frmListado').action='doFichasProductosBorrar.do';
				document.getElementById('frmListado').submit();
			}
		}else{
			alert('Debe marcar algún registro para eliminar');
		}
	}
	
	function doNuevo(){
		document.getElementById('frmListado').action='doFichasProductosFormulario.do';
		document.getElementById('frmListado').submit();
	}
</script>
<html:form styleId="frmListado" method="post"
				action="/doListadoFichasProductos.do">
<table style="background-color: white; border: 1px solid #E5E5E5; width: 100%">		
	<tr>
		<td colspan="7" style="font-family: Verdana; font-size: 11px; color: #2DACE4;padding: 5px 0px 0px 5px">
			<img src="img/play.gif"/>&nbsp;Búsqueda<br>
			<hr style="color: #DFDEDE">
		</td>
	</tr>
	<tr>
		<td width="2%"></td>
		<td style="font-family: Verdana; font-size: 11px; color: #666666" width="20%">
			Nombre Ficha<br>
			<input id="fprodNombreFicha" size="30" name="fprodNombreFicha" type="text" value="${FFICHAS_PRODUCTOS.fprodNombreFicha}">
		</td>

		<td style="font-family: Verdana; font-size: 11px; color: #666666" width="20%">
			Denominación Comerical<br>
			<input id="fprodDenominacionComercial" size="30" name="fprodDenominacionComercial" type="text" value="${FFICHAS_PRODUCTOS.fprodDenominacionComercial}">
		</td>
		<td>
		<input id="filtro" name="filtro"  type="hidden" value="">
			<br><a onclick="javascript: doBuscar(this)">
				<img src="img/buscar.gif" title="Aplicar filtro"/>
				</a>
				<a onclick="javascript: doLimpiar(this)">
				<img src="img/borrar.gif" title="Borrar filtro"/>
				</a>
		</td>
		<td colspan="3" width="30%"></td>
	</tr>
	<tr>
		<td colspan="7" style="">
			&nbsp;
		</td>
	</tr>
	<tr>
		<td colspan="7" style="font-family: Verdana; font-size: 11px; color: #2DACE4;padding: 5px 0px 0px 5px">
			<img src="img/play.gif"/>&nbsp;Listado de Fichas<br>
			<hr style="color: #DFDEDE">
		</td>
	</tr>
	<tr>
		<td colspan="7" width="50%">
				<display:table name="LISTAFICHASPRODUCTOS" id="ficha" export="false"
					cellpadding="0" cellspacing="0" summary="Tabla Listado"
					sort="external" class="" requestURI="/doListadoFichasProductos.do"
					defaultsort="1" defaultorder="ascending" pagesize="10">
			
					<display:column class="columna porcen2" headerClass="columna porcen2"
						style="${estilo2009}" alt="selecc/deselec" function="checkUncheck()" src="img/marca_desmarca.gif" input="img">
							<input type="checkbox" id="checkList"
								class="check" name="checkList"
								value="${ficha.fprodId}" />
					</display:column>
			
					<display:column class="columna porcen10"
						headerClass="columna porcen10"  sortProperty="fprodNombreFicha" property="fprodNombreFicha"
						style="color: #F76D2D;" title="Nombre Ficha" sortable="fprodNombreFicha"/>
					
					<display:column class="columna porcen10"
						headerClass="columna porcen10"  sortProperty="fprodDenominacionComercial" property="fprodDenominacionComercial"
						style="color: #F76D2D;" title="Denominacion Comercial" sortable="fprodDenominacionComercial"/>
									
					<display:column class="columna porcen2" headerClass="columna porcen2"
						style="${estilo2009}" title="Editar">
							<a href="doFichasProductosFormulario.do?fprodId=${ficha.fprodId}">
								<img src="img/editar.gif">
							</a>						
					</display:column>
				</display:table>
			</td>
		</tr>
	</table>
</html:form>