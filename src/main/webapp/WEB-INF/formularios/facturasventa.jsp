<%-- /WEB-INF/modulos/seguridad/perfiles.ficha.jsp --%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/c-1_0-rt.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="/WEB-INF/taglibs-string.tld" prefix="str" %>

<script type="text/javascript">
	vFormName = "frmDetalle";
	
	function doSubmit(){
		vForm = document.getElementById(vFormName);		
		if( document.getElementById('fvClId').value=='' ){
			alert('Debe elegir un cliente válido');
			return;
		}else if(document.getElementById('fvFecha').value=='' ){
			alert('Debe indicar una fecha válida');
			return;
		/*}else if(document.getElementById('fvIva').value=='' ){
			alert('Debe indicar un tipo de Factura');
			return;
		}*/
	}else{
			 document.getElementById('frmDetalle').action = 'doFacturasVentaformularioGrabar.do';
        	_enviarDetalle('frmDetalle');
		}
	}
	
	function doEditar(){
		
		document.forms[0].action = 'doFacturasVentaFormularioEditar.do';
		document.forms[0].submit();
	}

	function doRefresh(){
		vForm = document.getElementById(vFormName);		
		document.getElementById('frmDetalle').action = 'doFacturasVentaFormulario.do';
        _enviarDetalle('frmDetalle');
	
	}
	
	function doEliminarDetalle(){
		/*vForm = document.getElementById(vFormName);				
		var idDetalles = CantidadDeChecked(vForm);
		if( idDetalles == '-1' ){
			alert('Debe elegir al menos un producto para eliminar');
		}
		else if( idDetalles > '1' ){
			alert('No se pueden elegir más de un producto para eliminar.');
		}
		else{
			document.getElementById('frmDetalle').action = 'doFacturasVentaDetalleBorrar.do?fvClId=${fvClId}';
			_enviarDetalle('frmDetalle');
		}*/
		if( hayMasDeUnChecked(vFormName) ){
			document.getElementById('frmDetalle').action = 'doFacturasVentaDetalleBorrar.do?fvClId=${fvClId}';
			_enviarDetalle('frmDetalle');
		}
	}
		
	function doAñadirDetalle(){
		vForm = document.getElementById(vFormName); 

		if(document.getElementById('prodId').value==''){
			alert('Debe indicar un producto para insertar');
			return;
		}else if(document.getElementById('prodPrecio').value==''){
			alert('Debe indicar el precio del producto');
			return;
		}else if( formato_numero(document.getElementById('prodPrecio').value) == '' ){
			return;
		}else if( formato_numero(document.getElementById('prodPrecio').value) <= formato_numero(0)){
			alert('El precio del producto debe ser mayor a 0');
			return;
		}else if( formato_numero(document.getElementById('prodCantidad').value) ==''){
			alert('Debe indicar una cantidad para el producto');
			return;
		}else if( formato_numero(document.getElementById('prodCantidad').value) == '' ){
			return;
		}else if( document.getElementById('fvTipo').value==1 && formato_numero(document.getElementById('prodCantidad').value)<= formato_numero(0)){
			alert('La cantidad del producto debe ser mayor a 0');
			return;
		}else if( document.getElementById('fvTipo').value==2 && formato_numero(document.getElementById('prodCantidad').value)>= formato_numero(0)){
			alert('La cantidad del producto debe ser menor a 0\n al ser una Factura de Abono');
			return;
		}else{ 
			
			//alert( "Cantidad a insertar " + formato_numero(document.getElementById('prodCantidad').value) );
			//alert( "Stock " + formato_numero(document.getElementById('stCantidad').value) );
			
			if( parseFloat(formato_numero(document.getElementById('prodCantidad').value)) > parseFloat(formato_numero(document.getElementById('stCantidad').value)) ){
				alert( 'No se puede insertar dicho stock' );
				return;
			}
			else{
				document.getElementById('frmDetalle').action='doFacturasVentaDetalleGrabarManual.do?manual=1';
				document.getElementById('frmDetalle').submit();
			}
		}
	}
	
	
	/* Comprueba se hay algún paroducto seleccionado, devuelve -1 si no hay ninguno */
	function hayChecked(form){
		hayCheck = false;
		vForm = document.getElementById("frmDetalle");
		value = -1;
		if(vForm.checkList!= undefined){
			size = vForm.checkList.length;
			value = vForm.checkList.value;
			if (value != undefined)
				hayCheck = vForm.checkList.checked;
			if (size != undefined){
				i=0; 		
				while (i<size && !hayCheck){
					hayCheck = vForm.checkList[i].checked;
					value = vForm.checkList[i].value;
					i++;
				}		
			}
		}
		if( !hayCheck ){
			value=-1;
		}
		return value;
	}
	
	/* Comprueba se hay algún paroducto seleccionado, devuelve -1 si no hay ninguno */
	function CantidadDeChecked(form){
		hayCheck = false;
		vForm = document.getElementById("frmDetalle");
		value = -1;
		seleccionados = 0;
		if(vForm.checkList!= undefined){
			size = vForm.checkList.length;
			value = vForm.checkList.value;
			if (value != undefined)
				hayCheck = vForm.checkList.checked;
			if (size != undefined){
				i=0; 		
				while (i<size ){
					hayCheck = vForm.checkList[i].checked;
					if(hayCheck)
						seleccionados++;
					value = vForm.checkList[i].value;
					i++;
				}		
			}
		}
		if( !hayCheck ){
			seleccionados=-1;
		}
		return seleccionados;
	}
	
	function devolverPrecioProducto(precio, Producto)
	{
		
		 var campoProducto = document.getElementById(Producto);
		 var idProducto = campoProducto[campoProducto.selectedIndex].value;
		 var url = 'doCargaPrecioProducto.do?producto='+idProducto;
		 // Ponemos en blanco
		 document.getElementById(precio).value = '';
		 setTimeout("loadXMLDocInput('"+url+"','"+precio+"')", 0);
		 
		
	}
	
	function devolverDatosLotes(lote, Producto)
	{
		
		 var campoProducto = document.getElementById(Producto);
		 
		 var idProducto = campoProducto[campoProducto.selectedIndex].value;
	
		 var url = 'doCargaLotes.do?prodId='+idProducto;
		 
		// Ponemos en blanco
		 document.getElementById(lote).length = 0;
		 
		 setTimeout("loadXMLDoc('"+url+"','"+lote+"')", 0); 
	}
	
	function devolverStockLote(stock, lote)
	{
		 var campoLote = document.getElementById(lote);
		 var stId = campoLote[campoLote.selectedIndex].value;
	
		 var url = 'doCargaStockLote.do?stId='+stId;
		 
		 // Ponemos en blanco
		 document.getElementById(stock).value = "";
		 
		 
		 setTimeout("loadXMLDocInput('"+url+"','"+stock+"')", 0); 
	}
	
	
	function devolverStockyPrecioProducto(precio, Producto, stock, lote)
	{
		 //campos para el precio
		 var campoProducto = document.getElementById(Producto);
		 var idProducto = campoProducto[campoProducto.selectedIndex].value;
		 //campos del stock
		 var campoLote = document.getElementById(lote);
		 var stId = campoLote[campoLote.selectedIndex].value;
		 //var url = 'doCargaPrecioProducto.do?producto='+idProducto;
		 
		 var url = 'doCargaPrecioProducto.do?producto='+idProducto + '&stId='+stId;
		 
		 // Ponemos en blanco
		 document.getElementById(precio).value = "";		
		 // Ponemos en blanco
		 document.getElementById(stock).value = "";
		 
		 //setTimeout("loadXMLDoc2Input('"+url+"','"+precio+"','"+stock+"')", 0);
		 setTimeout("loadXMLDocInput('"+url+"','"+precio+"','"+stock+"')", 0);
		
	}
	
	function doImprimirFactura(){
				document.getElementById('frmDetalle').action='doGenerarFacturasVenta.do';
				document.getElementById('frmDetalle').target="_new";
				document.getElementById('frmDetalle').submit();
				document.getElementById('frmDetalle').action='doFacturasVenta.do';
				document.getElementById('frmDetalle').target="";
	}
	
	function doImprimirEtiquetas(){
		document.getElementById('frmDetalle').action='doVentanaImprimirEtiquetasVentas.do';
		ventanaNombre('', '600', '400','','CallMePopUp');
		document.getElementById('frmDetalle').target='CallMePopUp';
		document.getElementById('frmDetalle').submit();
		document.getElementById('frmDetalle').target='';		
	}
	
</script>

<html:form styleId="frmDetalle" action="/doFacturasVentaformularioGrabar" method="post">
<table style="background-color: white; border: 1px solid #E5E5E5; width: 100%">
	<c:if test="${facturaventa.fvTipo==0 || fvTipo==0 }">							
		&nbsp;&nbsp;&nbsp;<span style="color: #2DACE4; font-size: 16px; font-weight: bold;"><img src="img/factura.png"/> Factura Venta</span>
		<br><br>
	</c:if>
	<c:if test="${facturaventa.fvTipo==1 || fvTipo==1 }">							
		&nbsp;&nbsp;&nbsp;<span style="color: #2DACE4; font-size: 16px; font-weight: bold;"><img src="img/factura.png"/> Factura De Venta</span>
		<br><br>
	</c:if>
	<c:if test="${facturaventa.fvTipo==2 || fvTipo==2 }">							
		&nbsp;&nbsp;&nbsp;<span style="color: #2DACE4; font-size: 16px; font-weight: bold;"><img src="img/factura.png"/> Factura De Abono</span>
		<br><br>
	</c:if>
	
	<tr>
		<td colspan="7" style="font-family: Verdana; font-size: 12px; color: #2DACE4;padding: 5px 0px 0px 5px; font-weight: bold;">
			<img src="img/play.gif"/>&nbsp;Datos Básicos de Factura <p style="color: red;"> 
			<hr style="color: #DFDEDE">
		</td>
	</tr>
	    <tr> 
          <td align="left" colspan='6' style="padding-left: 20px;"> 
			<table width="70%" border="0" cellpadding="0" cellspacing="0" class="texto">
              <tr> 
                <td style='width: 10%' align="left" class="texto_negrita">Cliente</td>
                <td style='width: 25%' align="left"> 
					<html:text size='30' maxlength="75"  disabled="true" property="fvId" name="fvId" value="${facturaventa.clDatosRelacionados[0]} ${facturaventa.clDatosRelacionados[1]}"/>
				</td>
							
				<c:set var="today" value="<%=new java.util.Date()%>" />				
				<td style='width: 7%' align="left" class="texto_negrita">Fecha</td>
                <td style='width: 25%' align="left">
                
               		<c:choose>
						<c:when test="${facturaventa.fvFecha == null || facturaventa.fvFecha == '' }">
							<fmt:formatDate value="${today}" pattern="dd/MM/yyyy" var="fecha" />
						</c:when>	
						<c:otherwise>
							<fmt:formatDate value="${facturaventa.fvFecha}" pattern="dd/MM/yyyy" var="fecha" />
						</c:otherwise>
					</c:choose>
					
					<%-- <fmt:formatDate value="${facturaventa.fvFecha}" pattern="dd/MM/yyyy" var="fecha" />  --%> 	
	            	<html:text size='30' style="background-color: #CCCCCC;text-align:right;" readonly="true" disabled="${DISABLED}" styleId="fvFecha" property="fvFecha" name="fvFecha" value="${fecha}"/>
	            	<c:if test="${empty facturaventa.fvId}">
		            	<img style="cursor: pointer;" class="vtop" id="calendario1" title="Seleccione fecha" alt="Seleccione fecha" src="img/calendario.gif"/>
		            	<script type="text/javascript">
		                      Calendar.setup({
		                          inputField     :    "fvFecha"   // id of the input field
		                      ,
		                          button         :    "calendario1"  // trigger for the calendar (button ID)
		                      });
		            	</script>
		            </c:if>
	            </td>
	            
	           <td style='width: 4%'>&nbsp;</td>
	            <c:if test="${!empty facturaventa.fvId}"> 
	            <td style='width: 7%' align="left" class="texto_negrita">Número Factura</td>
                <td style='width: 10%' align="left"> 
				<html:text size='30' maxlength="75"  disabled="true" property="" style="text-align:right;" name="" value="C${facturaventa.fvNumeroFactura}"/>
				</td>
	            </c:if>  
             
              	<td style="width: 30%">	&nbsp; 	</td>
			  	
			 </tr>
			 <c:if test="${!empty facturaventa.fvId}">
			 <tr>
			 	<td style='width: 7%' align="left" class="texto_negrita">Nombre Cliente</td>
			 	<td colspan='1'> 
						<html:text size='30' maxlength="75"  property="fvObservaciones" name="fvObservaciones" value="${facturaventa.fvObservaciones}"/>
				</td>
				<td style='width: 7%' align="left" class="texto_negrita">Dto (%)</td>
			 	<td colspan='1'> 
						<html:text size='10' maxlength="75" style="text-align:right;" property="fvDescuento" name="fvDescuento" value="${facturaventa.fvDescuento}"/>
				</td>
			 </tr>
			 </c:if> 
			   
			 </table>
			 
			 </td>
			 </tr>
		
        <tr>
     <!--  <tr> 
          <td align="center" colspan='4' style="padding-left: 20px;"> 
			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="texto">
              <tr> 
                <td style='width: 15%' align="left" class="texto_negrita">Nombre Comercial</td>
                <td style='width: 40%' align="left"> 
					<html:text size='60' maxlength="75" disabled="true" property="clNombreComercial" name="clNombreComercial" value="${facturaventa.clDatosRelacionados[6]}"/>&nbsp;<img src="img/asterisco.gif" alt="<bean:message key='campo.obligatorio'/>" title="<bean:message key='campo.obligatorio'/>">
				</td>		
				<td style='width: 4%'>&nbsp;</td>
				<td style='width: 5%' align="left" class="texto_negrita"></td>
                <td style='width: 40%' align="left">
				<select id="fvIva" name="fvIva" <c:if test="${!empty facturaventa.fvIva}"> disabled='disabled' </c:if> >
						<option value="">[Seleccionar]</option>
						<option <c:if test="${facturaventa.fvIva=='A'}"> selected='selected' </c:if> value="A">A</option>
						<option <c:if test="${facturaventa.fvIva=='B'}"> selected='selected' </c:if> value="B">B</option>
					</select>
	            </td>               
              </tr>
            </table>
		  </td>		  
        </tr>
        <tr> 
          <td align="center" colspan='4' style="padding-left: 20px;"> 
			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="texto">
              <tr> 
                <td style='width: 15%' align="left" class="texto_negrita">Dirección</td>
                <td style='width: 40%' align="left"> 
					<html:text size='60' maxlength="75" disabled="true" property="clDireccion" name="clDireccion" value="${facturaventa.clDatosRelacionados[2]}"/>&nbsp;<img src="img/asterisco.gif" alt="<bean:message key='campo.obligatorio'/>" title="<bean:message key='campo.obligatorio'/>">
				</td>		
				<td style='width: 45%'>&nbsp;</td>               
              </tr>
            </table>
		  </td>		  
        </tr>
        <tr> 
          <td align="center" colspan='4' style="padding-left: 20px;"> 
			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="texto">
              <tr> 
                <td style='width: 15%' align="left" class="texto_negrita">Provincia</td>
                 <td style='width: 40%' align="left"> 
					<html:text size='40' maxlength="75" disabled="true" property="provId" name="provId" value="${Provincia.provNombre}"/>&nbsp;<img src="img/asterisco.gif" alt="<bean:message key='campo.obligatorio'/>" title="<bean:message key='campo.obligatorio'/>">
				</td>
				<td style='width: 45%'>&nbsp;</td>               
              </tr>
            </table>
		  </td>		  
        </tr>
        <tr> 
          <td align="center" colspan='4' style="padding-left: 20px;"> 
			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="texto">
              <tr> 
                <td style='width: 15%' align="left" class="texto_negrita">Localidad</td>
                  <td style='width: 40%' align="left"> 
					<html:text size='40' maxlength="75" disabled="true" property="locId" name="locId" value="${Localidad.locNombre}"/>&nbsp;<img src="img/asterisco.gif" alt="<bean:message key='campo.obligatorio'/>" title="<bean:message key='campo.obligatorio'/>">
				</td>	
				<td style='width: 45%'>&nbsp;</td>               
              </tr>
            </table>
		  </td>		  
        </tr>    
        <tr> 
          <td align="center" colspan='4' style="padding-left: 20px;"> 
			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="texto">
              <tr> 
                <td style='width: 15%' align="left" class="texto_negrita">Observaciones</td>
                  <td style='width: 40%' align="left">        
                  <c:choose>
					<c:when test="${!empty facturaventa.fvId}">
						<html:textarea rows="4"  cols="37" readonly="true" property="fvObservaciones" name="fvObservaciones" value="${facturaventa.fvObservaciones}"/>
					</c:when>
					<c:otherwise>
						<html:textarea rows="4"  cols="37" property="fvObservaciones" name="fvObservaciones" value="${facturaventa.fvObservaciones}"/>
					</c:otherwise>
				  </c:choose>          
				</td>	
				<td style='width: 45%'>&nbsp;</td>               
              </tr>
            </table>
		  </td>		  
        </tr>    
        <tr ><td height="20px"></td></tr>   
	  -->
		
		
<!-- DETALLES DE LA FACTURAS -->		
<c:if test="${!empty facturaventa.fvId}">
	<tr>
		<td colspan="7" style="font-family: Verdana; font-size: 12px; color: #2DACE4;padding: 5px 0px 0px 5px; font-weight: bold;">
			<img src="img/play.gif"/>&nbsp;Detalles de facturación<br>
			<hr style="color: #DFDEDE">
			
				<table width="50%">
				<tr>
					<td style="font-family: Verdana; font-size: 12px; color: #666666; width: 10%; font-weight: bold;" align="center">
							Producto<br>
							<html:select style="width: 250px" styleId="prodId" property="prodId" value="" onchange="javascript: devolverDatosLotes('hinvdLote','prodId'); ">
							  <html:option value="">[Seleccionar]</html:option>
							  <html:options property="prodId" labelProperty="prodNombre" collection="LISTAPRODUCTOS"/>
						    </html:select>
					</td>
						
						<td style="font-family: Verdana; font-size: 12px; color: #666666; width: 10%; font-weight: bold;" align="center"font-weight: bold;" align="center">
							Lote<br>
							<html:select style="width: 250px" styleId="hinvdLote" property="hinvdLote" value="" onchange="javascript: devolverStockyPrecioProducto('prodPrecio','prodId','stCantidad','hinvdLote');">
							  <html:option value="">[Seleccionar]</html:option>
							  <html:options property="hinvdLote" labelProperty="hinvdLote" collection="LISTALOTES"/>
						    </html:select>
						</td>
					<td style="font-family: Verdana; font-size: 11px; color: #666666; width: 10%">
							Stock Máximo<br>
							<input size="16" readonly="readonly" id="stCantidad" name="stCantidad" maxlength="11" style="text-align: right;" type="text" value="">
						</td>
					
						 <td style="font-family: Verdana; font-size: 12px; color: #666666; width: 10%; font-weight: bold;" align="center">
							Cantidad<br>
							<input onblur="this.value=formato_numero(this.value, 1)" size="16" id="prodCantidad" name="prodCantidad" maxlength="11" style="text-align: right;" type="text" value="">
						</td>
						<td style="font-family: Verdana; font-size: 12px; color: #666666; width: 10%; font-weight: bold;" align="center">
							Precio<br>
							<input onblur="this.value=formato_numero(this.value,1)" size="16" id="prodPrecio" name="prodPrecio" maxlength="11" style="text-align: right;" type="text" value="">
						</td>
						
						<c:if test="${facturaventa.fvCierre!='1'}">
							<td style="font-family: Verdana; font-size: 11px; color: #666666; width: 5%">
								<a onclick="javascript: doAñadirDetalle();">
									<img src="img/add.png" title="Añadir Producto Manual"/>
									</a>
							</td>
							<td style="font-family: Verdana; font-size: 11px; color: #666666; width: 5%">
								<a onclick="javascript: doEliminarDetalle();"> 
									<img  src="img/remove.png" title="eliminar detalle"/></a>
							</td>
						<!-- 
							<td style="font-family: Verdana; font-size: 11px; color: #666666; width: 5%">
								<a><img src="img/producto.gif" title="Añaadir producto cliente" onclick="javascript:ventana('doVentanaSearchProductosClienteFactura.do?fvClId=${facturaventa.fvClId}&fvId=${facturaventa.fvId}&fvTipo=${facturaventa.fvTipo}', '750', '500','scrollbars=yes');"></a>
							</td>
						 -->
						</c:if>
					</tr>
				</table>	
		</td>
	</tr>
	<td colspan="7" style="font-family: Verdana; font-size: 12px; color: #2DACE4;padding: 5px 0px 0px 5px; font-weight: bold;">
			
		</td>
	<tr>
		<td  style="width: 75%">
				<table cellpadding="1" class="displaytag_tabla" cellspacing="1" style="margin-bottom: 20px">
				<c:set var="contador" value="0"/>
				<c:set var="indice" value="-1"/>
				<c:set var="totalBruto" value="0"/>
				<c:set var="totalNeto" value="0"/>
					<thead>
						<tr class="${estilo}">
							<th class="columna sortable">
								&nbsp;
							</th>
							<th class="columna sortable">
								Producto
							</th>							
							<th class="columna sortable">
								Cantidad
							</th>
							<th class="columna sortable">
								Lote
							</th>
							<th class="columna sortable">
								Precio (&euro;)
							</th>
						<!-- 	<th class="columna sortable">
								Iva (%)
							</th>
							<th class="columna sortable">
								Descuento (%)
							</th>
							 -->
							<th class="columna sortable">
								Importe (&euro;)
							</th>
							
						</tr>
					</thead>
					<c:forEach items="${LISTADETALLES}" var="detalle">
						<c:choose>
							<c:when test="${contador%2==0}">
								<c:set value="odd" var="estilo"/>
								<c:set var="contador" value="3"/>
							</c:when>
							<c:otherwise>
								<c:set value="even" var="estilo"/>
								<c:set var="contador" value="2"/>
							</c:otherwise>
						</c:choose>
						<c:set var="indice" value="${indice+1}"/>
						<tr class="${estilo}">
							<td class="columna" style="text-align: center;">
								<input type="checkbox" id="checkList" name="checkList" class="check" value="${detalle.fvdId}"/>
							</td>
							<td class="columna" style="text-align: left;">
								<c:if test="${empty detalle.fvdProducto}">&nbsp;</c:if>
								<c:if test="${!empty detalle.fvdProducto}">${detalle.fvdProducto}</c:if>
							</td>
							<!--  <td class="columna" style="text-align: right;">
								<fmt:formatNumber pattern="###,###.###" var="cantidad">${detalle.fvdCantidad}</fmt:formatNumber>
								<input  size="11" 
										onblur="javascript:this.value=formato_numero(this.value,1)" 
										id="cantidad_${detalle.fvdId}"  style="text-align: right; font-weight: bold;"
										name="cantidad_${detalle.fvdId}" maxlength="16"  type="text" value="${cantidad}">
							</td>
							-->
							<td class="columna" style="text-align: right;">
								<fmt:formatNumber pattern="###,###.###" var="cantidad">${detalle.fvdCantidad}</fmt:formatNumber>
								<c:out value="${cantidad}"/>
							</td>
							<td class="columna" style="text-align: right;">
								<c:out value="${detalle.lote}"/>
							</td>
							<td class="columna" style="text-align: right;">
								<fmt:formatNumber pattern="###,###.###" var="precioventa">${detalle.fvdPrecioVenta}</fmt:formatNumber>
								<c:out value="${precioventa}"/>
							</td>										
							<!-- <td class="columna" style="text-align: right;">
								<fmt:formatNumber pattern="###,###.###" var="iva">${detalle.fvdIva * 100 }</fmt:formatNumber>
								<c:out value="${iva}"/>
							</td>		
							<td class="columna" style="text-align: right;">
								<fmt:formatNumber pattern="###,###.###" var="descuento"><c:out value="${detalle.fvdDescuento * 100 }"/></fmt:formatNumber>
								<c:out value="${descuento}"/>
							</td>
							 -->
							<td class="columna" style="text-align: right;">
								<fmt:formatNumber pattern="###,###.###" var="importe">${detalle.fvdImporte}</fmt:formatNumber>
								<c:out value="${importe}"/>
							</td>
							
						</tr>
						<c:set var="totalBruto" value="${totalBruto + (detalle.fvdPrecioVenta * detalle.fvdCantidad)}"/>
						<c:set var="totalNeto" value="${totalNeto + detalle.fvdImporte }"/>
					</c:forEach>
				</table>
			</td>
			<c:if test="${ (facturaventa.fvTipo!=2 && totalNeto>0) or (facturaventa.fvTipo==2 && totalNeto < 0 )}">
				<td width="25%" style="vertical-align: top;">
					<table cellpadding="1" class="displaytag_tabla" cellspacing="1">
						<thead>
							<tr class="even">
								<td colspan="2" class="columna sortable" style="text-align: center; font-weight: bold;">	EUROS</td>
							</tr>
						</thead>
						<!--
						<tr>
							<th class="columna">Importe Bruto:</th>
							<td class="columna" style="text-align: right;">
								<fmt:formatNumber pattern="###,###.###" var="tb">${totalBruto}</fmt:formatNumber>
								<c:out value="${tb}"/> &euro;
							</td>
						</tr>
						-->	
						
							<tr>	
								<td class="columna" style="text-align: left; font-size: 14px; font-weight: bold;">
									Base Imponible
								</td>						
								<td class="columna" style="text-align: right; font-size: 14px; font-weight: bold;">
									<fmt:formatNumber pattern="###,###.###" var="tn">${totalNeto}</fmt:formatNumber>
									<c:out value="${tn}"/> &euro;
									<c:set var="totalNeto" value="${totalNeto}"/>
								</td>
								
							</tr>	
					
					<fmt:formatNumber pattern="###,###.###" var="descuento">${totalNeto * (facturaventa.fvDescuento*0.01)}</fmt:formatNumber>
					<c:if test="${ (facturaventa.fvTipo!=2 && descuento > '0') or (facturaventa.fvTipo==2 && descuento < '0' ) }">
							 <tr>	
							 	<td class="columna" style="text-align: left; font-size: 14px; font-weight: bold;">
									Descuento
								</td>						
								<td class="columna" style="text-align: right; font-size: 14px; font-weight: bold;">
									
									-<c:out value="${descuento}"/> &euro;
								</td>
							</tr>
					</c:if>	
							<tr>
								<td class="columna" style="text-align: left; font-size: 18px; font-weight: bold;">
									Total
								</td>
							  								
								<td class="columna" style="text-align: center; font-size: 18px; font-weight: bold;">
									<fmt:formatNumber pattern="###,###.##" var="totalConDescuento">${totalNeto - (totalNeto * (facturaventa.fvDescuento*0.01))}</fmt:formatNumber>
									<c:out value="${totalConDescuento}"/> &euro;
								</td>
							</tr> 
					</table>
				</td>
			</c:if>
		</tr>
</c:if>
</table>

<script>
		setTimeout("devolverPrecioProducto('prodPrecio','prodId')",400);
		setTimeout("devolverDatosLotes('hinvdLote','prodId')",400);
		setTimeout("devolverStockLote('stCantidad','hinvdLote')",400);
		setTimeout("devolverStockyPrecioProducto('prodPrecio','prodId','stCantidad','hinvdLote')",400);
		
</script>

<html:hidden property="fvId" styleId="fvId" value="${facturaventa.fvId}"/>
<html:hidden property="fvInvalida" styleId="fvInvalida" value="0"/>
<html:hidden property="fvIdEmpresa" styleId="fvIdEmpresa" value="0"/>
<c:choose>
	<c:when test="${!empty fvClId}">
		<html:hidden property="fvClId" styleId="fvClId" value="${fvClId}"/>
	</c:when>
	<c:otherwise>
		<html:hidden property="fvClId" styleId="fvClId" value="${facturaventa.fvClId}"/>
	</c:otherwise>
</c:choose>
<html:hidden property="fvIva" value="B"/>
<html:hidden property="fvTotal" value="${totalNeto}"/>
<html:hidden property="fvPagada" value="0"/>

<c:choose>
	<c:when test="${!empty facturaventa.fvTipo}">
		<html:hidden property="fvTipo" styleId="fvTipo" value="${facturaventa.fvTipo}"/>
	</c:when>
	<c:otherwise>
		<html:hidden property="fvTipo" styleId="fvTipo" value="${fvTipo}"/>
	</c:otherwise>
</c:choose>
</html:form> 