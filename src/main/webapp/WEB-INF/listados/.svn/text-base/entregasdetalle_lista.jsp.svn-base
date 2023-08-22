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
				document.getElementById('frmListado').action='doEntregasDetalleGrabar.do?delete=1';
				document.getElementById('frmListado').submit();
			}
		}else{
			alert('Debe marcar algún registro para eliminar');
		}
	}
	
	function doAñadir(){
		if(document.getElementById('endClId').value==''){
			alert('Debe indicar el cliente');
			return;
		}else if(document.getElementById('endRutId').value==''){
			alert('Debe indicar la ruta');
			return;
		}else if(document.getElementById('endCuId').value==''){
			alert('Debe indicar el cuadrante');
			return;
		}else if(document.getElementById('endViaje').value==''){
			alert('Debe indicar el viaje');
			return;
		}else if(document.getElementById('endViaje').value<1){
			alert('El numero del viaje debe ser mayor que 0');
			return;
		}else if(document.getElementById('endViaje').value>5){
			alert('Debe indicar no existen mas de 5 viajes');
			return;
		}else if(document.getElementById('endOrden').value==''){
			alert('Debe indicar el orden');
			return;
		}else if(document.getElementById('endOrden').value<1){
			alert('El numero del orden debe ser mayor que 0');
			return;
		}else{ 
			document.getElementById('frmListado').action='doEntregasDetalleGrabar.do';
			document.getElementById('frmListado').submit();
		}
	}
	
	
	function devolverDatosRuta()
	{
		 var campoCliente = document.getElementById('endClId');
		 var idCliente = campoCliente[campoCliente.selectedIndex].value;
	
		 var url = 'doCargaRutas.do?clId='+idCliente;
		 
		 // Ponemos en blanco
		 document.getElementById('endRutId').length = 0;
		 
		 setTimeout("loadXMLDoc('"+url+"','endRutId')", 1000); 
	}
	
	function devolverDatosCuadrantes()
	{
		 var campoCliente = document.getElementById('endClId');
		 var campoRuta = document.getElementById('endRutId');
		 var idCliente = campoCliente[campoCliente.selectedIndex].value;
		 var idRuta = campoRuta[campoRuta.selectedIndex].value;
	
		 var url = 'doCargaCuadrantes.do?clId='+idCliente+'&rutId='+idRuta;
		 
		 // Ponemos en blanco
		 document.getElementById('endCuId').length = 0;
		 
		 setTimeout("loadXMLDoc('"+url+"','endCuId')", 1000); 
	}
	
</script>
<html:form styleId="frmListado" method="post" action="/doListadoEntregasDetalle.do">
<table style="background-color: white; border: 1px solid #E5E5E5; width: 100%">
	<html:hidden property="endEntId" value="${endEntId}"/>
	<tr>          
		  <td colspan="9" align="right">
		  	<span style='color: #FA7D3D; font-size: 12px; font-weight: bold;'>
					${endEntNombre}
			</span>
		  </td>
	</tr>
	<tr>
		<td colspan="8" style="font-family: Verdana; font-size: 11px; color: #2DACE4;padding: 5px 0px 0px 5px">
			<img src="img/play.gif"/>&nbsp;Insertar Nuevo Detalle a la Ruta<br>
			<hr style="color: #DFDEDE">
		</td>
	</tr>
	<tr>	
		<td width="2%"></td>
		<td style="font-family: Verdana; font-size: 11px; color: #666666; width: 12%">
			Cliente<br>
			<html:select style="width: 200px" onchange="javascript: devolverDatosRuta();" styleId="endClId" property="endClId" value="">
			  <html:option value="">[Seleccionar]</html:option>
			  <html:options property="clId" labelProperty="clNombreComercial" collection="LISTACLIENTES"/>
		    </html:select>
		</td>
		
		<td style="font-family: Verdana; font-size: 11px; color: #666666; width: 12%">
			Entrega<br>
			<html:select style="width: 200px" styleId="endRutId" onchange="javascript: devolverDatosCuadrantes()" property="endRutId" value="">
			  <html:option value="">[Seleccionar]</html:option>
			  <html:options property="rutId" labelProperty="rutNombre" collection="LISTARUTAS"/>
		    </html:select>
		</td>
		
		<td style="font-family: Verdana; font-size: 11px; color: #666666; width: 12%">
			Cuadrante<br>
			<html:select style="width: 200px" styleId="endCuId" property="endCuId" value="">
			  <html:option value="">[Seleccionar]</html:option>
			  <html:options property="cuId" labelProperty="cuNombre" collection="LISTACUADRANTES"/>
		    </html:select>
		</td>
		
		<td style="font-family: Verdana; font-size: 11px; color: #666666; width: 12%">
			Viaje<br>
			<input onblur="javascript:this.value=formato_entero(this.value)" id="endViaje" size="16" name="endViaje" maxlength="1" type="text" value="${FENTREGASDETALLE.endViaje}"/>
		</td>
		
		<td style="font-family: Verdana; font-size: 11px; color: #666666; width: 12%">
			Orden<br>
			<input onblur="javascript:this.value=formato_entero(this.value)" id="endOrden" size="16" name="endOrden" maxlength="2" type="text" value="${FENTREGASDETALLE.endOrden}"/>
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
			
				<display:table name="LISTAENTREGASDETALLE" id="entrega" export="false"
					cellpadding="0" cellspacing="0" summary="Tabla Listado"
					sort="external"  class="" requestURI="/doListadoEntregasDetalle.do"
					defaultsort="1" defaultorder="ascending" pagesize="20">
			
					<display:column class="columna porcen2" headerClass="columna porcen2"
						style="${estilo2009}" alt="selecc/deselec" function="checkUncheck()" src="img/marca_desmarca.gif" input="img">
							<input type="checkbox" id="checkList" name="checkList"
								class="check"
								value="${entrega.endId}" />
					</display:column>
			
					<display:column class="columna porcen10"
						headerClass="columna porcen10"
						style="color: #F76D2D" title="Cliente" sortable="false">
						${entrega.clDatosRelacionados[1]}
					</display:column>
					
					<display:column class="columna porcen10"
						headerClass="columna porcen10"
						style="color: #F76D2D" title="Entrega" sortable="false">
						${entrega.clDatosRelacionados[0]}
					</display:column>
					
					<display:column class="columna porcen10"
						headerClass="columna porcen10"
						style="color: #F76D2D" title="Cuadrante" sortable="false">
						${entrega.clDatosRelacionados[2]}
					</display:column>
			
					<display:column class="columna porcen10"
						headerClass="columna porcen10" property="endViaje"
						style="color: #F76D2D" title="Viaje" sortable="true"/>
					
					
					<display:column class="columna porcen10"
						headerClass="columna porcen10" property="endOrden"
						style="color: #F76D2D" title="Orden" sortable="false"/>
					
					</display:table>
			</td>
		</tr>
	</table>
<html:hidden property="endEntNombre" value="${endEntNombre}"/>	
</html:form>