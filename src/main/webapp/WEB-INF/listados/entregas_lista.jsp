<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script>

	function doObservaciones(entId){
		ventana('doNotasEntregaObs.do?entId=' + entId, '600', '400','');
	}
	
	function doEditar(entId){
		ventana('doEntregasFormulario.do?entId=' + entId, '','300', '350','');
	}
	
	function doBorrar(){
		if(hayChecked('frmListado')){
			if(confirm('Va a eliminar los registros seleccionados, ¿Desea continuar?')){
				document.getElementById('frmListado').action='doEntregasGrabar.do?delete=1';
				document.getElementById('frmListado').submit();
			}
		}else{
			alert('Debe marcar algún registro para eliminar');
		}
	}
	
	function doAñadir(){
		if(document.getElementById('entNombre').value==''){
			alert('Debe indicar el nombre de la entrega');
			return;
		}else{ 
			document.getElementById('frmListado').action='doEntregasGrabar.do';
			document.getElementById('frmListado').submit();
		}
	}
</script>
<html:form styleId="frmListado" method="post"
				action="/doListadoEntregas.do">
<table style="background-color: white; border: 1px solid #E5E5E5; width: 100%">
	<tr>
		<td colspan="4" style="font-family: Verdana; font-size: 11px; color: #2DACE4;padding: 5px 0px 0px 5px">
			<img src="img/play.gif"/>&nbsp;Insertar Nueva Ruta<br>
			<hr style="color: #DFDEDE">
		</td>
	</tr>
	<tr>	
		<td width="2%"></td>
		<td style="font-family: Verdana; font-size: 11px; color: #666666; width: 12%">
			Nombre<br>
			<input size="30" id="entNombre" name="entNombre" maxlength="64"  type="text" value="">
		</td>
		
		<td style="font-family: Verdana; font-size: 11px; color: #666666; width: 70%">
			<br><a onclick="javascript: doAñadir(this)">
				<img src="img/add.png" title="Añadir Cuadrante"/>
				</a>
		</td>
	</tr>
	<tr>
		<td colspan="3" style="">
			&nbsp;
		</td>
	</tr>
	<tr>
		<td colspan="3" style="font-family: Verdana; font-size: 11px; color: #2DACE4;padding: 5px 0px 0px 5px">
			<img src="img/play.gif"/>&nbsp;Búsqueda<br>
			<hr style="color: #DFDEDE">
		</td>
	</tr>
	<tr>
		<td width="2%"></td>
		<td style="font-family: Verdana; font-size: 11px; color: #666666" width="20%">
			Nombre<br>
			<input id="entNombreFiltro" size="30" name="entNombreFiltro" type="text" value="${FENTREGAS.entNombre}">
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
	</tr>
	<tr>
		<td colspan="3" style="">
			&nbsp;
		</td>
	</tr>
	<tr>
		<td colspan="3" style="font-family: Verdana; font-size: 11px; color: #2DACE4;padding: 5px 0px 0px 5px">
			<img src="img/play.gif"/>&nbsp;Listado de Rutas<br>
			<hr style="color: #DFDEDE">
		</td>
	</tr>
	<tr>
		<td colspan="3">
				<display:table name="LISTAENTREGAS" id="entrega" export="false"
					cellpadding="0" cellspacing="0" summary="Tabla Listado"
					sort="external" class="" requestURI="/doListadoEntregas.do"
					defaultsort="1" defaultorder="ascending" pagesize="10">
			
					<display:column class="columna porcen2" headerClass="columna porcen2"
						style="${estilo2009}" alt="selecc/deselec" function="checkUncheck()" src="img/marca_desmarca.gif" input="img">
							<input type="checkbox" id="checkList"
								class="check" name="checkList"
								value="${entrega.entId}" />
					</display:column>
			
					<display:column class="columna porcen90"
						headerClass="columna porcen10"  sortProperty="entNombre" property="entNombre"
						style="color: #F76D2D;" title="Nombre" sortable="entNombre"/>
									
					<display:column class="columna porcen10" headerClass="columna porcen10"
						style="${estilo2009}" title="Editar">
							<a href="#" onclick="javascript: doEditar('${entrega.entId}');">
								<img src="img/editar.gif">
							</a>
							<a href="doListadoEntregasDetalle.do?endEntId=${entrega.entId}&endEntNombre=${entrega.entNombre}">
								<img src="img/cuadrante.gif">
							</a>
							<c:if test="${entrega.clDatosRelacionados[0]!=0}">
								<a href="#" onclick="javascript: doObservaciones('${entrega.entId}');">
									<img src="img/mininota.gif">
								</a>
							</c:if>
					</display:column>
				</display:table>
			</td>
		</tr>
	</table>
</html:form>