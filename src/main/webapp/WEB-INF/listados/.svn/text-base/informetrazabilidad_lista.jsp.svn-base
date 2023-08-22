<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script>
	
	function anadirProductosC(){
		var contProductos = parseInt( document.getElementById('contProductos').value ) + 1;
		document.getElementById('contProductos').value  = contProductos; 
		if( contProductos == 1 ){
			//document.getElementById('pintaProductos').innerHTML = '<b>Entregas Seleccionadas:</b><br>';
			document.getElementById('pintaProductos').innerHTML = '<table cellpadding="1" class="displaytag_tabla" cellspacing="1" width="5%">'+
				'<thead>'+
					'<tr>'+
						'<td colspan="1" class="columna sortable" style="text-align: center;color: orange">'+						
							'<span style="color: #2DACE4;font-weight: bold;">Productos Seleccionados</span>'+
						'</td>'+
					'</tr>'+
				'</thead>'+
			'</table>';
		}
		prodId = document.getElementById('prodIdC').value;
		document.getElementById('prodIds').value = document.getElementById('prodIds').value + ',' + prodId;
		var contProductos = parseInt( document.getElementById('contProductos').value ) + 1;
		document.getElementById('contProductos').value  = contProductos; 
		if( contProductos == 1 ){
			document.getElementById('pintaProductos').innerHTML = document.getElementById('pintaProductos').innerHTML + '<br><span style="color: #2DACE4;font-weight: bold;">Productos Seleccionadas</span>';
		}
		var indice = document.getElementById('prodIdC').selectedIndex;
    	var valor = document.getElementById('prodIdC').options[indice].text; 
		//document.getElementById('pintaProductos').innerHTML = document.getElementById('pintaProductos').innerHTML + '<br>' + valor;
		document.getElementById('pintaProductos').innerHTML = document.getElementById('pintaProductos').innerHTML + '<tr><td colspan="1" class="columna" style="text-align: left;font-weight: bold;text-align: left;"><b>'+valor+'</b><br></td></tr>';		
		
	}
	
	function limpiarProductos(){
		document.getElementById('prodIds').value = '';
		document.getElementById('contProductos').value = '0';
		document.getElementById('pintaProductos').innerHTML = '';
	}
	
	function anadirProductosV(){
		var contProductos = parseInt( document.getElementById('contProductos').value ) + 1;
		document.getElementById('contProductos').value  = contProductos; 
		if( contProductos == 1 ){
			//document.getElementById('pintaProductos').innerHTML = '<b>Entregas Seleccionadas:</b><br>';
			document.getElementById('pintaProductos').innerHTML = '<table cellpadding="1" class="displaytag_tabla" cellspacing="1" width="5%">'+
				'<thead>'+
					'<tr>'+
						'<td colspan="1" class="columna sortable" style="text-align: center;color: orange">'+						
							'<span style="color: #2DACE4;font-weight: bold;">Productos Seleccionados</span>'+
						'</td>'+
					'</tr>'+
				'</thead>'+
			'</table>';
		}
		prodId = document.getElementById('prodIdV').value;
		document.getElementById('prodIds').value = document.getElementById('prodIds').value + ',' + prodId;
		var contProductos = parseInt( document.getElementById('contProductos').value ) + 1;
		document.getElementById('contProductos').value  = contProductos; 
		if( contProductos == 1 ){
			document.getElementById('pintaProductos').innerHTML = document.getElementById('pintaProductos').innerHTML + '<br><span style="color: #2DACE4;font-weight: bold;">Productos Seleccionadas</span>';
		}
		var indice = document.getElementById('prodIdV').selectedIndex;
    	var valor = document.getElementById('prodIdV').options[indice].text; 
		//document.getElementById('pintaProductos').innerHTML = document.getElementById('pintaProductos').innerHTML + '<br>' + valor;
		document.getElementById('pintaProductos').innerHTML = document.getElementById('pintaProductos').innerHTML + '<tr><td colspan="1" class="columna" style="text-align: left;font-weight: bold;text-align: left;"><b>'+valor+'</b><br></td></tr>';		
		
	}
	
		vFormName = "frmListado";
	
	function doGenerarTrazabilidad(){
		vForm = document.getElementById(vFormName);		
		if( document.getElementById('prodIds').value=='' && document.getElementById('FechaDesde').value=='' && document.getElementById('FechaHasta').value=='' ){
			alert('Debe filtrar por algun producto o por alguna fecha');
			return;
		}else{
			document.getElementById('frmListado').action = 'doGenerarTrazabilidad.do';
        	_enviarDetalle('frmListado');}
	}
	
</script>
<html:form styleId="frmListado" method="post" target="_blank"
				action="/doPrevisionCrear.do">
<input type="hidden" id="prodIds" name="prodIds"/>
<input type="hidden" id="contProductos" name="contProductos" value="0"/>
<table style="background-color: white; border: 1px solid #E5E5E5; width: 100%; height: 250px">
	<tr>
		<td colspan="7" style="font-family: Verdana; font-size: 11px; color: #2DACE4;padding: 5px 0px 0px 5px">
			<img src="img/play.gif"/>&nbsp;Establezca el filtro para el Plan de Trazabilidad<br>
			<hr style="color: #DFDEDE">
		</td>
		
		<td style="font-family: Verdana; font-size: 11px; color: #666666">
		<input id="filtro" name="filtro"  type="hidden" value="">
				<a onclick="javascript: doLimpiar(this)">
				<img src="img/borrar.gif" title="Borrar filtro"/>
				</a>
		</td>
	</tr>
	<tr>
		<td width="2%"></td>	
		
		<td width="5%" style="font-family: Verdana; font-size: 11px; color: #666666">
			Fecha Desde	 
		</td>
		<td width="15%" style="font-family: Verdana; font-size: 11px; color: #666666">	
           	<input type="text" style="background-color: #CCCCCC;" readonly="readonly" id="FechaDesde" name="FechaDesde" value=""/>
           	<img style="cursor: pointer;" class="vtop" id="calendario1" title="Seleccione fecha"  alt="Seleccione fecha" src="img/calendario.gif"/>
           	<script type="text/javascript">
                     Calendar.setup({
                         inputField     :    "FechaDesde"   // id of the input field
                     ,
                         button         :    "calendario1"  // trigger for the calendar (button ID)
                     });
           	</script>
		</td>
		
		<td width="8%"></td>	
		<td width="8%" style="font-family: Verdana; font-size: 11px; color: #666666">
			Productos Venta
		</td>
		<td width="20%" style="font-family: Verdana; font-size: 11px; color: #666666">
			<html:select style="width: 250px" styleId="prodIdV" property="prodIdV" value="">
			  <html:option value="">[Seleccionar]</html:option>
			  <html:options property="prodId" labelProperty="prodNombre" collection="PRODUCTOSLISTAVENTA"/>
		    </html:select>
		</td>
		<td width="20%">
		    <img src="img/add.png" alt="Anadir productos" title="Anadir productos" onclick="anadirProductosV();">
			<img src="img/borrar.gif" alt="Limpiar entregas" title="Limpiar entregas" onclick="limpiarProductos();">
		    
		</td>
		
	</tr>
	
		<tr>
		<td width="2%"></td>	
		
		<td width="5%" style="font-family: Verdana; font-size: 11px; color: #666666">
			Fecha Hasta	 
		</td>
		<td width="15%" style="font-family: Verdana; font-size: 11px; color: #666666">	
           	<input type="text" style="background-color: #CCCCCC;" readonly="readonly" id="FechaHasta" name="FechaHasta" value=""/>
           	<img style="cursor: pointer;" class="vtop" id="calendario2" title="Seleccione fecha"  alt="Seleccione fecha" src="img/calendario.gif"/>
           	<script type="text/javascript">
                     Calendar.setup({
                         inputField     :    "FechaHasta"   // id of the input field
                     ,
                         button         :    "calendario2"  // trigger for the calendar (button ID)
                     });
           	</script>
		</td>
		
		<td width="8%"></td>	
		<td width="8%" style="font-family: Verdana; font-size: 11px; color: #666666">
			Productos Compra
		</td>
		<td width="20%" style="font-family: Verdana; font-size: 11px; color: #666666">
			<html:select style="width: 250px" styleId="prodIdC" property="prodIdC" value="">
			  <html:option value="">[Seleccionar]</html:option>
			  <html:options property="prodId" labelProperty="prodNombre" collection="PRODUCTOSLISTACOMPRA"/>
		    </html:select>
		</td>
		<td width="20%">
		    <img src="img/add.png" alt="Anadir productos" title="Anadir productos" onclick="anadirProductosC();">
			<img src="img/borrar.gif" alt="Limpiar entregas" title="Limpiar entregas" onclick="limpiarProductos();">
		    
		</td>
		
	</tr>
		
	<tr>
		<td width="2%" colspan="7">	&nbsp;</td>
	</tr>
	
	<tr>
		<td colspan="4" width="1%">
		<table>
		<tr>
				<td width="1%">	&nbsp;</td>
				<td width="12%" style="font-family: Verdana; font-size: 11px; color: #666666">
						<input type="checkbox" id="stStockCero" class="check" name="stStockCero"/> 
						Incluir lotes con stock a cero
				</td>
			</tr>
			<tr>
				<td width="1%">	&nbsp;</td>
				<td width="12%" style="font-family: Verdana; font-size: 11px; color: #666666">
						<input type="checkbox" id="desglosado" class="check" name="desglosado"/> 
						Desglosar componentes / Visualizar fabricados
				</td>
			</tr>
			<tr>
				<td width="1%">	&nbsp;</td>
				<td width="10%" style="font-family: Verdana; font-size: 11px; color: #666666">
						<input type="checkbox" id="stReg" class="check" name="stReg"/> 
						Incluir regularizaciones
				</td>
			</tr>
		</table>
		</td>
		
		<td width="80%" colspan="3">
		<table style="background-color: white; border: 0px solid #E5E5E5; width: 100%; height: 100px">
		<tr>	
		    <td colspan="4" width="2%"></td>	
			<td colspan="2" width="25%" colspan="1" id="pintaProductos" style="font-family: Verdana; font-size: 11px; color: #666666; text-align: left;">
			</td>
		</tr>
		</table>
	</table>
	<!-- <tr>	
		
		<td width="2%"></td>	
		<td width="15%" style="font-family: Verdana; font-size: 11px; color: #666666">
			Cliente Desde
		</td>
		<td>
			<html:select style="width: 250px" styleId="clIdD" property="clIdD" value="">
			  <html:option value="">[Seleccionar]</html:option>
			  <html:options property="clId" labelProperty="clNombreComercial" collection="CLIENTESLISTA"/>
		    </html:select>
		</td>
		
		<td width="2%"></td>	
		
		<td style="font-family: Verdana; font-size: 11px; color: #666666">
			Familia Desde
		</td>
		<td width="15%" style="font-family: Verdana; font-size: 11px; color: #666666">
			<select style="width: 200px" id="famIdD" name="famIdD">
			  <option value="">[Todas]</option>
			  <c:forEach var="fam" items="${FAMILIAS}">
			  	<option value="${fam.famId}">${fam.famNombre}</option>
			  </c:forEach>
		    </select>
		</td>
		<td width="50%"></td>
	
	</tr>
	
	<tr>
		<td width="2%"></td>	
		<td width="15%" style="font-family: Verdana; font-size: 11px; color: #666666">
			Cliente Hasta
		</td>
		<td width="15%" style="font-family: Verdana; font-size: 11px; color: #666666">
			<html:select style="width: 250px" styleId="clIdH" property="clIdH" value="">
			  <html:option value="">[Seleccionar]</html:option>
			  <html:options property="clId" labelProperty="clNombreComercial" collection="CLIENTESLISTA"/>
		    </html:select>
		</td>
		
		<td width="2%"></td>	
		
		<td  width="15%" style="font-family: Verdana; font-size: 11px; color: #666666">
			Familia Hasta
		</td>
		<td width="15%" style="font-family: Verdana; font-size: 11px; color: #666666">
			<select style="width: 200px" id="famIdH" name="famIdH">
			  <option value="">[Todas]</option>
			  <c:forEach var="fam" items="${FAMILIAS}">
			  	<option value="${fam.famId}">${fam.famNombre}</option>
			  </c:forEach>
		    </select>
		</td>
		<td width="50%"></td>
	</tr>
	
	<tr>
		<td width="2%"></td>	
		<td width="15%" style="font-family: Verdana; font-size: 11px; color: #666666">
			Proveedor Desde
		</td>
		<td width="15%" style="font-family: Verdana; font-size: 11px; color: #666666">
			<select style="width: 200px" id="prIdD" name="prIdD">
			  <option value="">[Todas]</option>
			  <c:forEach var="prov" items="${PROVEEDORES}">
			  	<option value="${prov.prId}">${prov.prNombre}</option>
			  </c:forEach>
		    </select>
		</td>
		<td colspan="3" width="50%"></td>
	</tr>
	<tr>	
		<td width="2%"></td>	
		
		<td width="15%" style="font-family: Verdana; font-size: 11px; color: #666666">
			Proveedor Hasta
		</td>
		<td width="15%" style="font-family: Verdana; font-size: 11px; color: #666666">
			<select style="width: 200px" id="prIdH" name="prIdH">
			  <option value="">[Todas]</option>
			  <c:forEach var="prov" items="${PROVEEDORES}">
			  	<option value="${prov.prId}">${prov.prNombre}</option>
			  </c:forEach>
		    </select>
		</td>
		<td colspan="3" width="50%"></td>
	</tr>
	 --> 
	
</html:form>