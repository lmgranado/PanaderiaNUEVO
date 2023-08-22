<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script>

	function doEditar(cuId,cuClId){
		ventana('doCuadrantesFormulario.do?cuId=' + cuId + '&cuClId=' + cuClId, '300', '350','');
	}

	//Vamos a intentar poner el miniformulario en el mismo jsp para facilitarle las cosas	

	function doBorrar(){
		if(hayChecked('frmListado')){
			if(confirm('Va a eliminar los registros seleccionados, ¿Desea continuar?')){
				document.getElementById('frmListado').action='doCuadrantesGrabar.do?delete=1';
				document.getElementById('frmListado').submit();
			}
		}else{
			alert('Debe marcar algún registro para eliminar');
		}
	}
	
	function tomaCuadrante(form){
		hayCheck = false;
		valor = "";
		i=0;
		vForm = document.getElementById("frmListado");
		if(vForm.checkList!= undefined){
			size = vForm.checkList.length;
			value = vForm.checkList.value;
			if (value != undefined)
				hayCheck = vForm.checkList.checked;
			for(j=0; j<size; j++){
					if(vForm.checkList[j].checked){
						valor = vForm.checkList[j].value;
						i++;
					}	
			}
		}
		if(i!=1){
			alert('Debe seleccionar 1 solo cuadrante. Ha seleccionado ' + i);
			return '';
		}
		return valor;
	}
	
	function doInsertarCuadrante(){
		if(hayChecked('frmListado')){
			if(confirm('Va a insertar el cuadrante seleccionado, ¿Desea continuar?')){
				if(window.opener.document.getElementById('notViaje').value==''){
					alert('Debe indicar en la nota, el viaje a insertar.');
					return;
				}
				if(tomaCuadrante(document.getElementById('frmListado'))!=''){
					window.opener.document.getElementById('cuadran').value=tomaCuadrante(document.getElementById('frmListado'));
					window.opener.doEditar();
				}
			}
		}else{
			alert('Debe seleccionar algún cuadrante');
		}
	}
	
	function doAñadir(){
		if(document.getElementById('cuNombre').value==''){
			alert('Debe indicar el nombre del Cuadrante');
			return;
		}else{ 
			document.getElementById('frmListado').action='doCuadrantesGrabar.do';
			document.getElementById('frmListado').submit();
		}
	}
	
	
	function doCopiar(){
		if(!hayMasDeUnChecked('frmListado')){
			return;
		}
		
		if(document.getElementById('cuCopia').value==''){
			alert('Debe indicar un nombre para copiarle el cuadrante');
			return;
		}
		
		document.getElementById('frmListado').action='doCuadrantesCopiar.do';
		document.getElementById('frmListado').submit();
	}
	
</script>
<html:form styleId="frmListado" method="post"
				action="/doListadoCuadrantes.do">
<table style="background-color: white; border: 1px solid #E5E5E5; width: 100%">
	<html:hidden property="cuClId" value="${cuClId}"/>
    <tr>          
	  <td colspan="6" align="right">
	  	<span style='color: #FA7D3D; font-size: 12px; font-weight: bold;'>
			<c:if test="${!empty Cliente.clId}">
				Cliente: ${Cliente.clNombre} ${Cliente.clApellidos}
			</c:if>	
		</span>
	  </td>
    </tr>
 
<c:if test="${empty sinMenu || sinMenu==null}">    
	<tr>
		<td colspan="6" style="font-family: Verdana; font-size: 11px; color: #2DACE4;padding: 5px 0px 0px 5px">
			<img src="img/play.gif"/>&nbsp;Insertar Nuevo Cuadrante<br>
			<hr style="color: #DFDEDE">
		</td>
	</tr>
	<tr>	
		<td width="2%"></td>
		<td style="font-family: Verdana; font-size: 11px; color: #666666; width: 12%">
			Nombre<br>
			<input size="25" id="cuNombre" name="cuNombre" maxlength="64"  type="text" value="">
		</td>
		<td style="font-family: Verdana; font-size: 11px; color: #666666; width: 12%">
			Observaciones<br>
			<input size="45" id="cuObservaciones" name="cuObservaciones" maxlength="500"  type="text" value="">
		</td>
		
		<td style="font-family: Verdana; font-size: 11px; color: #666666; width: 10%">
			<br><a onclick="javascript: doAñadir(this)">
				<img src="img/add.png" title="Añadir Cuadrante"/>
				</a>
		</td>
		<td style="width: 66%">
			&nbsp;
		</td>
	</tr>
	<tr>
		<td colspan="6" style="font-family: Verdana; font-size: 11px; color: #2DACE4;padding: 5px 0px 0px 5px">
			<img src="img/play.gif"/>&nbsp;Copiar Cuadrante<br>
			<hr style="color: #DFDEDE">
		</td>
	</tr>
	<tr>
		<td colspan="5">
			<table>
			  <tr>
				<td style="font-family: Verdana; font-size: 11px; color: #666666; width: 12%">
					Nombre copia<br>
					<input size="45" id="cuCopia" name="cuCopia" maxlength="64"  type="text" value="">
				</td>
				
				<td style="font-family: Verdana; font-size: 11px; color: #666666; width: 12%">
					Observaciones copia<br>
					<input size="45" id="obsCopiar" name="obsCopiar" maxlength="1999"  type="text" value="">
				</td>
				
				<td style="font-family: Verdana; font-size: 11px; color: #666666; width: 10%">
					<br><a onclick="javascript: doCopiar(this)">
						<img src="img/add.png" title="Copiar cuadrante"/>
						</a>
				</td>
			  </tr>
			</table>
		</td>
	<tr>
		<td colspan="5" style="">
			&nbsp;
		</td>
	</tr>
</c:if>
	<tr>
		<td colspan="5" style="font-family: Verdana; font-size: 11px; color: #2DACE4;padding: 5px 0px 0px 5px">
			<img src="img/play.gif"/>&nbsp;Resultado<br>
			<hr style="color: #DFDEDE">
		</td>
	</tr>
	<tr>
		<td colspan="6">
			<input type="checkbox" id="checkList" name="checkList"
								class="check" value="-1" style="visibility: hidden"/>
				<display:table name="LISTACUADRANTES" id="cuadrante" export="false"
					cellpadding="0" cellspacing="0" summary="Tabla Listado"
					sort="external" class="" requestURI="/doListadoCuadrantes.do"
					defaultsort="1" defaultorder="ascending" pagesize="10">
			
					<display:column class="columna porcen2" headerClass="columna porcen2"
						style="${estilo2009}" alt="selecc/deselec" function="checkUncheck()" src="img/marca_desmarca.gif" input="img">
							<input type="checkbox" id="checkList" name="checkList"
								class="check"
								value="${cuadrante.cuId}" />
					</display:column>
			
					<display:column class="columna porcen10"
						headerClass="columna porcen10" property="cuNombre"
						style="color: #F76D2D" title="Nombre" sortable="true"/>

					<display:column class="columna porcen40"
						headerClass="columna porcen10"
						style="color: #F76D2D" title="Observaciones" sortable="false">
							${cuadrante.cuObservaciones}&nbsp;
					</display:column>
		
					<display:column class="columna porcen2" headerClass="columna porcen2"
						style="${estilo2009}" title="Editar">
							<a href="#" onclick="javascript: doEditar('${cuadrante.cuId}','${cuadrante.cuClId}');">
								<img src="img/editar.gif">
							</a>
							<a href="doListadoCuadrantesDetalle.do?cudCuId=${cuadrante.cuId}&cuClId=${cuadrante.cuClId}&sinMenu=${sinMenu}">
								<img src="img/producto.gif">
							</a>
					</display:column>
				</display:table>
			</td>
		</tr>
	</table>
<input type="hidden" id="sinMenu" name="sinMenu" value="${sinMenu}">
</html:form>