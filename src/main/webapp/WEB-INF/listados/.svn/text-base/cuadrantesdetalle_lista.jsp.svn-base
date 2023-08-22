<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script>

	//Vamos a intentar poner el miniformulario en el mismo jsp para facilitarle las cosas	

	function doBorrar(){
		if(hayChecked('frmListado')){
			if(confirm('Va a eliminar los registros seleccionados, ¿Desea continuar?')){
				document.getElementById('frmListado').action='doCuadrantesDetalleGrabar.do?delete=1';
				document.getElementById('frmListado').submit();
			}
		}else{
			alert('Debe marcar algún registro para eliminar');
		}
	}
	
	function doVolver(){
		document.forms[0].action = 'doListadoCuadrantes.do?cuClId=${cuClId}';
		document.forms[0].submit();
	}
	
	function doGuardar(){
		document.forms[0].action = 'doCuadrantesDetalleEditar.do';
		document.forms[0].submit();
	}
	
	function doAñadir(){
		if(document.getElementById('cudPclId').value==''){
			alert('Debe indicar un producto para insertar');
			return;
		}else if(!((document.getElementById('cudCantidad1').value!='' || 
				 document.getElementById('cudCantidad2').value!='' ||
				 document.getElementById('cudCantidad3').value!='' ||
				 document.getElementById('cudCantidad4').value!='' ||
				 document.getElementById('cudCantidad5').value!='') &&
				 (document.getElementById('cudCantidad1').value > 0 || 
				 document.getElementById('cudCantidad2').value > 0 ||
				 document.getElementById('cudCantidad3').value > 0 ||
				 document.getElementById('cudCantidad4').value > 0 ||
				 document.getElementById('cudCantidad5').value > 0))){
			alert('Debe indicar al menos la cantidad para un viaje');
			return;
		}else{ 
			document.getElementById('frmListado').action='doCuadrantesDetalleGrabar.do';
			document.getElementById('frmListado').submit();
		}
	}
</script>

<html:form styleId="frmListado" method="post"
				action="/doListadoCuadrantesDetalle.do">
<table style="background-color: white; border: 1px solid #E5E5E5; width: 100%">
	<html:hidden property="cudCuId" value="${cudCuId}"/>
	<tr>
		<td colspan="8" style="font-family: Verdana; font-size: 11px; color: #2DACE4;padding: 5px 0px 0px 5px">
			<img src="img/play.gif"/>&nbsp;Insertar Nuevo Detalle al Cuadrante<br>
			<hr style="color: #DFDEDE">
		</td>
	</tr>
	<tr>	
		<td width="2%"></td>
		<td style="font-family: Verdana; font-size: 11px; color: #666666; width: 12%">
			Producto<br>
			<html:select style="width: 100px" styleId="cudPclId" property="cudPclId" value="">
			  <html:option value="">[Seleccionar]</html:option>
			  <html:options property="pclId" labelProperty="clDatosRelacionados[0]" collection="LISTAPRODUCTOSCLIENTES"/>
		    </html:select>
		</td>
		
		<td style="font-family: Verdana; font-size: 11px; color: #666666; width: 12%">
			Viaje_1<br>
			<input size="11" onblur="javascript:this.value=formato_entero(this.value)" id="cudCantidad1" name="cudCantidad1" maxlength="16"  type="text" value="0">
		</td>
		<td style="font-family: Verdana; font-size: 11px; color: #666666; width: 12%">
			Viaje_2<br>
			<input size="11" onblur="javascript:this.value=formato_entero(this.value)" id="cudCantidad2" name="cudCantidad2" maxlength="16"  type="text" value="0">
		</td>
		<td style="font-family: Verdana; font-size: 11px; color: #666666; width: 12%">
			Viaje_3<br>
			<input size="11" onblur="javascript:this.value=formato_entero(this.value)" id="cudCantidad3" name="cudCantidad3" maxlength="16"  type="text" value="0">
		</td>
		<td style="font-family: Verdana; font-size: 11px; color: #666666; width: 12%">
			Viaje_4<br>
			<input size="11" onblur="javascript:this.value=formato_entero(this.value)" id="cudCantidad4" name="cudCantidad4" maxlength="16"  type="text" value="0">
		</td>
		<td style="font-family: Verdana; font-size: 11px; color: #666666; width: 12%">
			Viaje_5<br>
			<input size="11" onblur="javascript:this.value=formato_entero(this.value)" id="cudCantidad5" name="cudCantidad5" maxlength="16"  type="text" value="0">
		</td>
		<td style="font-family: Verdana; font-size: 11px; color: #666666; width: 10%">
			<br><a onclick="javascript: doAñadir(this)">
				<img src="img/add.png" title="Añadir Detalle"/>
				</a>
		</td>
		<td style="width: 66%">
			&nbsp;
		</td>
	</tr>
	<tr>
		<td colspan="8" style="">
			&nbsp;
		</td>
	</tr>
	<tr>
		<td colspan="8" style="font-family: Verdana; font-size: 11px; color: #2DACE4;padding: 5px 0px 0px 5px">
			<img src="img/play.gif"/>&nbsp;Resultado<br>
			<hr style="color: #DFDEDE">
		</td>
	</tr>
	<tr>
		<td colspan="8">
			
				<display:table name="LISTACUADRANTESDETALLE" id="cuadrantesDetalle" export="false"
					cellpadding="0" cellspacing="0" summary="Tabla Listado"
					sort="external" class="" requestURI="/doListadoCuadrantesDetalle.do"
					defaultsort="1" defaultorder="ascending" pagesize="10">
			
					<display:column class="columna porcen2" headerClass="columna porcen2"
						style="${estilo2009}" alt="selecc/deselec" function="checkUncheck()" src="img/marca_desmarca.gif" input="img">
							<input type="checkbox" id="checkList" name="checkList" class="check" value="${cuadrantesDetalle.cudId}" />
					</display:column>
			
					<display:column class="columna porcen10"
						headerClass="columna porcen10"
						style="color: #F76D2D" title="Producto" sortable="false">
							${cuadrantesDetalle.clDatosRelacionados[0]}
					</display:column>
					
					<display:column class="columna porcen10"
						headerClass="columna porcen10"
						style="color: #F76D2D" title="Viaje 1">
							<input size="11" 
							onblur="javascript:this.value=formato_entero(this.value)" 
							id="cantidad_${cuadrantesDetalle.cudId}_1"  style="text-align: right;"
							name="cantidad_${cuadrantesDetalle.cudId}_1" maxlength="16"  type="text" value="${cuadrantesDetalle.cudCantidad1}">
					</display:column>
						
					<display:column class="columna porcen10"
						headerClass="columna porcen10"
						style="color: #F76D2D" title="Viaje 2">
							<input size="11" 
							onblur="javascript:this.value=formato_entero(this.value)" 
							id="cantidad_${cuadrantesDetalle.cudId}_2"  style="text-align: right;"
							name="cantidad_${cuadrantesDetalle.cudId}_2" maxlength="16"  type="text" value="${cuadrantesDetalle.cudCantidad2}">
					</display:column>
						
					<display:column class="columna porcen10"
						headerClass="columna porcen10"
						style="color: #F76D2D" title="Viaje 3">
							<input size="11" 
							onblur="javascript:this.value=formato_entero(this.value)" 
							id="cantidad_${cuadrantesDetalle.cudId}_3"  style="text-align: right;"
							name="cantidad_${cuadrantesDetalle.cudId}_3" maxlength="16"  type="text" value="${cuadrantesDetalle.cudCantidad3}">
					</display:column>
						
					<display:column class="columna porcen10"
						headerClass="columna porcen10"
						style="color: #F76D2D" title="Viaje 4">
							<input size="11" style="text-align: right;"
							onblur="javascript:this.value=formato_entero(this.value)"
							id="cantidad_${cuadrantesDetalle.cudId}_4" 
							name="cantidad_${cuadrantesDetalle.cudId}_4" maxlength="16"  type="text" value="${cuadrantesDetalle.cudCantidad4}">
					</display:column>
						
					<display:column class="columna porcen10"
						headerClass="columna porcen10"
						style="color: #F76D2D" title="Viaje 5">
						<input size="11" 
							onblur="javascript:this.value=formato_entero(this.value)" 
							id="cantidad_${cuadrantesDetalle.cudId}_5"  style="text-align: right;"
							name="cantidad_${cuadrantesDetalle.cudId}_5" maxlength="16"  type="text" value="${cuadrantesDetalle.cudCantidad5}">
					</display:column>
				</display:table>
			</td>
		</tr>
	</table>
<html:hidden property="sinMenu" value="${sinMenu}"/>
</html:form>