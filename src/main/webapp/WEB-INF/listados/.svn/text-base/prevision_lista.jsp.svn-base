<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script>
	
	function doPrevision(){
		entIds = document.getElementById('entIds').value;
		rutIds = document.getElementById('rutIds').value;
		if(entIds!='' && (document.getElementById('notFechaInicio').value!='' || 
						  document.getElementById('notFechaFin').value!='' ||
						  document.getElementById('notClId').value!='' ||
						  document.getElementById('rutIds').value!='')){
			alert('En el caso de indicar Entregas no puede filtrar por el resto de campos');
			return;				  
		}
		
		if(rutIds!='' && (document.getElementById('notClId').value!='' ||
						  document.getElementById('entIds').value!='')){
			alert('En el caso de indicar Rutas no puede filtrar por el resto de campos');
			return;				  
		}
		
		if(rutIds!='' && (document.getElementById('notFechaInicio').value=='' || 
						  document.getElementById('notFechaFin').value=='')){
			alert('En el caso de indicar Rutas debe indicar la fecha de inicio y fin. En caso de ser un solo dia, estas fechas deben ser las mismas');
			return;				  
		}
	
		if(entIds=='' && rutIds=='' && (document.getElementById('notFechaInicio').value=='' || document.getElementById('notFechaFin').value=='')){
			alert('Debe indicar la fecha de inicio y fin o alguna entrega o Ruta. En caso de ser un solo dia, estas fechas deben ser las mismas');
			return;
		}
		
		if(entIds=='' && document.getElementById('agEntrega').value=='1'){
			alert('No puede agrupar por entregas si ha indicado cualquier otro filtro que no sea el de entregas');
			return;
		}
		
		document.forms[0].submit();
	}
	
	function añadirEntrega(){
		var contEntregas = parseInt( document.getElementById('contEntregas').value ) + 1;
		document.getElementById('contEntregas').value  = contEntregas; 
		if( contEntregas == 1 ){
			//document.getElementById('pintaEntregas').innerHTML = '<b>Entregas Seleccionadas:</b><br>';
			document.getElementById('pintaEntregas').innerHTML = '<table cellpadding="1" class="displaytag_tabla" cellspacing="1" width="5%">'+
				'<thead>'+
					'<tr>'+
						'<td colspan="1" class="columna sortable" style="text-align: center;color: orange">'+						
							'<span style="color: #2DACE4;font-weight: bold;">Entregas Seleccionadas</span>'+
						'</td>'+
					'</tr>'+
				'</thead>'+
			'</table>';
		}
		entId = document.getElementById('entId').value;
		document.getElementById('entIds').value = document.getElementById('entIds').value + ',' + entId;
		var contEntregas = parseInt( document.getElementById('contEntregas').value ) + 1;
		document.getElementById('contEntregas').value  = contEntregas; 
		if( contEntregas == 1 ){
			document.getElementById('pintaEntregas').innerHTML = document.getElementById('pintaEntregas').innerHTML + '<br><span style="color: #2DACE4;font-weight: bold;">Entregas Seleccionadas</span>';
		}
		var indice = document.getElementById('entId').selectedIndex;
    	var valor = document.getElementById('entId').options[indice].text; 
		//document.getElementById('pintaEntregas').innerHTML = document.getElementById('pintaEntregas').innerHTML + '<br>' + valor;
		document.getElementById('pintaEntregas').innerHTML = document.getElementById('pintaEntregas').innerHTML + '<tr><td colspan="1" class="columna" style="text-align: left;font-weight: bold;text-align: left;"><b>'+valor+'</b></td></tr>';		
		
	}
	
	function añadirRuta(){
		entId = document.getElementById('notRutId').value;
		document.getElementById('rutIds').value = document.getElementById('rutIds').value + ',' + entId;
		var contRutas = parseInt( document.getElementById('contRutas').value ) + 1;
		document.getElementById('contRutas').value  = contRutas; 
		if( contRutas == 1 ){
			document.getElementById('pintaRutas').innerHTML = document.getElementById('pintaRutas').innerHTML + '<br><span style="color: #2DACE4;font-weight: bold;">Rutas Seleccionadas</span>';
		}
		var indice = document.getElementById('notRutId').selectedIndex;
    	var valor = document.getElementById('notRutId').options[indice].text; 
		document.getElementById('pintaRutas').innerHTML = document.getElementById('pintaRutas').innerHTML + '<br>' + valor;
		
	}
	
	function limpiarEntrega(){
		document.getElementById('entIds').value = '';
		document.getElementById('contEntregas').value = '0';
		document.getElementById('pintaEntregas').innerHTML = '';
	}

	

	function limpiarRutas(){
		document.getElementById('rutIds').value = '';
		document.getElementById('contRutas').value = '0';
		document.getElementById('pintaRutas').innerHTML = '';
	}
	
</script>
<html:form styleId="frmListado" method="post" target="_blank"
				action="/doPrevisionCrear.do">
<input type="hidden" id="entIds" name="entIds"/>
<input type="hidden" id="contEntregas" name="contEntregas" value="0"/>
<input type="hidden" id="rutIds" name="rutIds"/>
<input type="hidden" id="contRutas" name="contRutas" value="0"/>
<table style="background-color: white; border: 1px solid #E5E5E5; width: 100%; height: 280px">
	<tr>
		<td colspan="5" style="font-family: Verdana; font-size: 11px; color: #2DACE4;padding: 5px 0px 0px 5px">
			<img src="img/play.gif"/>&nbsp;Establezca el filtro para la previsión de fabricación<br>
			<hr style="color: #DFDEDE">
		</td>
	</tr>
	<tr>
		<td width="2%"></td>	
		<td style="font-family: Verdana; font-size: 11px; color: #666666">
			Cliente<br>
			<html:select style="width: 250px" styleId="notClId" property="notClId" value="${FNOTASENTREGA.notClId}">
			  <html:option value="">[Seleccionar]</html:option>
			  <html:options property="clId" labelProperty="clNombreComercial" collection="CLIENTESLISTA"/>
		    </html:select>
		</td>
		
		<td style="font-family: Verdana; font-size: 11px; color: #666666">
			Ruta<br>
			<html:select style="width: 200px" styleId="notRutId" property="notRutId" value="${FNOTASENTREGA.notRutId}">
			  <html:option value="">[Seleccionar]</html:option>
			  <html:options  property="rutId" labelProperty="rutNombre" collection="RUTASLISTA"/>
		    </html:select>
		    <img src="img/add.png" alt="Añadir ruta" title="Añadir ruta" onclick="añadirRuta();">
			<img src="img/borrar.gif" alt="Limpiar rutas" title="Limpiar ruta" onclick="limpiarRutas();">
		</td>
		
		<td style="font-family: Verdana; font-size: 11px; color: #666666">
			Fecha de Inicio<br>  	 	
           	<input type="text" style="background-color: #CCCCCC;" readonly="readonly" id="notFechaInicio" name="notFechaInicio" value=""/>
           	<img style="cursor: pointer;" class="vtop" id="calendario1" title="Seleccione fecha"  alt="Seleccione fecha" src="img/calendario.gif"/>
           	<script type="text/javascript">
                     Calendar.setup({
                         inputField     :    "notFechaInicio"   // id of the input field
                     ,
                         button         :    "calendario1"  // trigger for the calendar (button ID)
                     });
           	</script>
		</td>
		
		<td style="font-family: Verdana; font-size: 11px; color: #666666">
			Fecha de Fin<br> 	 	
           	<input type="text" style="background-color: #CCCCCC;" readonly="readonly" id="notFechaFin" name="notFechaFin" value=""/>
           	<img style="cursor: pointer;" class="vtop" id="calendario2" title="Seleccione fecha"  alt="Seleccione fecha" src="img/calendario.gif"/>
           	<script type="text/javascript">
                     Calendar.setup({
                         inputField     :    "notFechaFin"   // id of the input field
                     ,
                         button         :    "calendario2"  // trigger for the calendar (button ID)
                     });
           	</script>
		</td>
	</tr>
	
		<tr>
		<td width="2%"></td>	
		<td colspan="1" style="font-family: Verdana; font-size: 11px; color: #666666">
			Entregas<br>
			<SELECT name="entId" id="entId">
				<option value="">[Seleccionar]</option>
				<c:forEach items="${ENTREGASLISTA}" var="entrega">
					<option value="${entrega.entId}">${entrega.entNombre}</option>
				</c:forEach>
			</SELECT>
			<img src="img/add.png" alt="Añadir entrega" title="Añadir entrega" onclick="añadirEntrega();">
			<img src="img/borrar.gif" alt="Limpiar entregas" title="Limpiar entregas" onclick="limpiarEntrega();">
		</td>
		
		<%-- <td colspan="2" style="font-family: Verdana; font-size: 11px; color: #666666">
			<img src="img/add.png" alt="Añadir entrega" title="Añadir entrega" onclick="añadirEntrega();">
			<img src="img/borrar.gif" alt="Limpiar entregas" title="Limpiar entregas" onclick="limpiarEntrega();">
		</td> --%>
		
		<td style="font-family: Verdana; font-size: 11px; color: #666666">
			Directas <sub>(En caso de seleccionar, no se tendrá en cuenta lo indicado en rutas y entregas)</sub><br>
			<select id="directo" name="directo">
			  <option value="">[Seleccionar]</option>
			  <option value="mas">Añadir Facturas y Albaranes Directos</option>
			  <option value="solo">Solo Facturas y Albaranes Directos</option>
		    </select>
		</td>
		
		<td style="font-family: Verdana; font-size: 11px; color: #666666">
			&nbsp;
		</td>
	</tr>
	
	<tr>
		<td width="2%"></td>	
		<td colspan="1" style="font-family: Verdana; font-size: 11px; color: #666666">
			Agrupar por Cliente<br>
			<SELECT name="agCliente" id="agCliente">
				<option value="0">No</option>
				<option value="1">Sí</option>
			</SELECT>
		</td>
		
		<td colspan="1" style="font-family: Verdana; font-size: 11px; color: #666666">
			Agrupar por Ruta<br>
			<SELECT name="agRuta" id="agRuta">
				<option value="0">No</option>
				<option value="1">Sí</option>
			</SELECT>
		</td>
		
		<td colspan="1" style="font-family: Verdana; font-size: 11px; color: #666666; text-align: left;">
			Agrupar por Entregas<br>
			<SELECT name="agEntrega" id="agEntrega">
				<option value="0">No</option>
				<option value="1">Sí</option>
			</SELECT>
		</td>
		<td colspan="1" style="font-family: Verdana; font-size: 11px; color: #666666; text-align: left;">
			Agrupar por Familias<br>
			<SELECT name="agFamilia" id="agFamilia">
				<option value="0">No</option>
				<option value="1">Sí</option>
			</SELECT>
		</td>
	</tr>
	<tr>
	    <td width="2%"></td>	
		<td colspan="1" id="pintaEntregas" style="font-family: Verdana; font-size: 11px; color: #666666; text-align: left;">
		</td>
	</tr>
	<tr>
	    <td width="2%"></td>	
		<td colspan="1" id="pintaRutas" style="font-family: Verdana; font-size: 11px; color: #666666; text-align: left;">
		</td>
	</tr>
	</table>
</html:form>