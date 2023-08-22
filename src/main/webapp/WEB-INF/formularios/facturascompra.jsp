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
		if(document.getElementById('fcPrId').value==''){
			alert('Debe seleccionar un proveedor para la factura');
			return;
		}
		else if( validateFields(vForm.name) ){
			document.getElementById('frmDetalle').action = 'doFacturasCompraformularioGrabar.do';
        	_enviarDetalle('frmDetalle');
        }       
	}
		
	function doEliminarDetalle(){
		/*vForm = document.getElementById(vFormName);				
		var idDetalles = hayChecked(vForm);
		if( idDetalles == '-1' ){
			alert('Debe elegir al menos un producto para eliminar');
		}
		else{
			document.getElementById('frmDetalle').action = 'doFacturasCompraDetalleBorrar.do?fcPrId=${fcPrId}';
			_enviarDetalle('frmDetalle');
		}*/
		
		if( hayMasDeUnChecked(vFormName) ){
			document.getElementById('frmDetalle').action = 'doFacturasCompraDetalleBorrar.do?fcPrId=${fcPrId}';
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
		}else if( formato_numero(document.getElementById('prodPrecio').value) <= formato_numero(0)){
			alert('El precio del producto debe ser mayor a 0');
			return;	
		}else if(document.getElementById('prodCantidad').value==''){
			alert('Debe indicar una cantidad para el producto');
			return;
		}else if( formato_numero(document.getElementById('prodCantidad').value)<= formato_numero(0)){
			alert('La cantidad del producto debe ser mayor a 0');
			return;	
		}else if(document.getElementById('fcdLote').value==''){
			alert('Debe indicar el lote del producto');
			return;
		}else if(document.getElementById('stFCaducidad').value==''){
			alert('Debe indicar la fecha de caducidad del producto');
			return;
		}else if( !compararFecha(fechaActualFormato(), document.getElementById("stFCaducidad").value) ){
			alert('La fecha de caducidad no puede ser inferior a la fecha de hoy');
			return;
		}else if( formato_numero(document.getElementById('prodCantidad').value) == '' ){
			return;
		}else if( formato_numero(document.getElementById('prodPrecio').value) == '' ){
			return;
		}else{ 
			document.getElementById('frmDetalle').action='doFacturasCompraDetalleGrabar.do';
			document.getElementById('frmDetalle').submit();
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
	
	
	function devolverUltimoPrecioDeCompra (precio, Producto)
	{
		
		 var campoProducto = document.getElementById(Producto);
		 var idProducto = campoProducto[campoProducto.selectedIndex].value;
		 var url = 'doCargaPrecioProductoUltimaCompra.do?producto='+idProducto;
		 
		 // Ponemos en blanco
		 document.getElementById(precio).value = '';
		 
		 setTimeout("loadXMLDocInput('"+url+"','"+precio+"')", 0); 
	}
	
	function doImprimirEtiquetas(){
		document.getElementById('frmDetalle').action='doVentanaImprimirEtiquetasCompras.do';
		ventanaNombre('', '600', '400','','CallMePopUp');
		document.getElementById('frmDetalle').target='CallMePopUp';
		document.getElementById('frmDetalle').submit();
		document.getElementById('frmDetalle').target='';		
	}
	
	function doImprimirFactura(){
		document.getElementById('frmDetalle').action='doGenerarFacturasCompra.do';
		document.getElementById('frmDetalle').target="_new";
		document.getElementById('frmDetalle').submit();
		document.getElementById('frmDetalle').action='doFacturasCompra.do';
		document.getElementById('frmDetalle').target="";
	}
	
</script>

<html:form styleId="frmDetalle" action="/doFacturasCompraformularioGrabar" method="post">
<table style="background-color: white; border: 1px solid #E5E5E5; width: 100%">
	<!-- <tr>          
		  <td colspan="4" align="right">
		  	<span style='color: #FA7D3D; font-size: 12px; font-weight: bold;'>
				<c:if test="${!empty facturacompra.fcId}">
					Número Factura: ${facturacompra.fcId}
				</c:if>	
			</span>
		  </td>
	    </tr>
	     -->
	<tr>
		<td colspan="7" style="font-family: Verdana; font-size: 12px; color: #2DACE4;padding: 5px 0px 0px 5px; font-weight: bold;">
			<img src="img/play.gif"/>&nbsp;Datos Básicos de Factura<br>
			<hr style="color: #DFDEDE">
		</td>
	</tr>
	    <tr> 
          <td align="left" colspan='6' style="padding-left: 20px;"> 
			<table width="75%" border="0" cellpadding="0" cellspacing="0" class="texto">
              <tr> 
                <td style='width: 10%' align="left" class="texto_negrita">Proveedor</td>
                <td style='width: 20%' align="left"> 
					<html:text size='30' maxlength="75"  disabled="true" property="fcId" name="fcId" value="${facturacompra.prDatosRelacionados[0]} ${facturacompra.prDatosRelacionados[1]}"/>
					<c:if test="${empty facturacompra.fcId}">
						&nbsp;<img src="img/buscar.gif" onclick="javascript:ventana('doVentanaSearchProveedoresFactura.do?fcId=${facturacompra.fcId}&tipo=C', '750', '500','');">
					</c:if>
				</td>
				
				<c:set var="today" value="<%=new java.util.Date()%>" />
				<td style='width: 5%' align="left" class="texto_negrita">Fecha</td>
                <td style='width: 20%' align="left">
                
                	<c:choose>
						<c:when test="${facturacompra.fcFecha == null || facturacompra.fcFecha == '' }">
							<fmt:formatDate value="${today}" pattern="dd/MM/yyyy" var="fecha" />
						</c:when>	
						<c:otherwise>
							<fmt:formatDate value="${facturacompra.fcFecha}" pattern="dd/MM/yyyy" var="fecha" />
						</c:otherwise>
					</c:choose>
					
					<%--<fmt:formatDate value="${facturacompra.fcFecha}" pattern="dd/MM/yyyy" var="fecha" />  --%>   	 	
	            	<html:text size='30' style="background-color: #CCCCCC; text-align:right;" readonly="true" disabled="${DISABLED}" styleId="fcFecha" property="fcFecha" name="fcFecha" value="${fecha}"/>
	            	<c:if test="${empty facturacompra.fcId}">
		            	<img style="cursor: pointer;" class="vtop" id="calendario1" title="Seleccione fecha"  alt="Seleccione fecha" src="img/calendario.gif"/>
		            	<script type="text/javascript">
		                      Calendar.setup({
		                          inputField     :    "fcFecha"   // id of the input field
		                      ,
		                          button         :    "calendario1"  // trigger for the calendar (button ID)
		                      });
		            	</script>
		            </c:if>
	            </td>
	            
	            <td style='width: 4%'>&nbsp;</td>
	            
	            <c:if test="${!empty facturacompra.fcId}">
	             <td style='width: 7%' align="left" class="texto_negrita"> Número Factura </td>
	             <td style='width: 10%' align="left"> 
					<html:text size='30' maxlength="75" style="text-align:right;" disabled="true" property="" name="" value="C${facturacompra.fcId}"/>
				</td>
				</c:if>	
				<td style='width: 25%'>&nbsp;</td> 
			
		     </tr>
            </table>
		  </td>
        </tr>
        
        <tr>
		
	</tr>
       <!-- <tr> 
          <td align="center" colspan='4' style="padding-left: 20px;"> 
			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="texto">
              <tr> 
                <td style='width: 15%' align="left" class="texto_negrita">Dirección</td>
                <td style='width: 40%' align="left"> 
					<html:text size='60' maxlength="75" disabled="true" property="clDireccion" name="clDireccion" value="${facturacompra.prDatosRelacionados[2]}"/>&nbsp;<img src="img/asterisco.gif" alt="<bean:message key='campo.obligatorio'/>" title="<bean:message key='campo.obligatorio'/>">
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
        <tr ><td height="20px"></td></tr>   
		-->
	<c:if test="${!empty facturacompra.fcId}">
	<tr>
		<td colspan="7" style="font-family: Verdana; font-size: 12px; color: #2DACE4;padding: 5px 0px 0px 5px; font-weight: bold;">
			<img src="img/play.gif"/>&nbsp;Detalles de facturación<br>
			<hr style="color: #DFDEDE">
				<table width="80%">
				<tr>	
					<td style="font-family: Verdana; font-size: 12px; color: #666666; width: 15%; font-weight: bold;" align="center">
						Producto<br>
						<select style="width: 250px" id="prodId" name="prodId" onchange="javascript: devolverUltimoPrecioDeCompra( 'prodPrecio', 'prodId' );">
						  <option value="">[Seleccionar]</option>
						  <c:forEach items="${LISTAPRODUCTOS}" var="prod">
						  	<option <c:if test="${prodId==prod.prodId}"> selected="selected" </c:if> value="${prod.prodId}">${prod.prodNombre}</option>
						  </c:forEach>
					    </select>
					</td>
					<td style="font-family: Verdana; font-size: 12px; color: #666666; width: 15%; font-weight: bold;" align="center">
						Cantidad<br>
						<input onblur="this.value=formato_numero(this.value,1)" size="16" id="prodCantidad" name="prodCantidad" style="text-align: right;" maxlength="11"  type="text" value="${prodCantidad}">
					</td>
					<td style="font-family: Verdana; font-size: 12px; color: #666666; width: 15%; font-weight: bold;" align="center">
						Precio<br>
						<input onblur="this.value=formato_numero(this.value,1)" size="16"  id="prodPrecio" name="prodPrecio" maxlength="11" style="text-align: right;" type="text" value="${prodPrecio}">
					</td>					
					<td style="font-family: Verdana; font-size: 12px; color: #666666; width: 15%; font-weight: bold;" align="center">
						Lote<br>
						<input size="16" id="fcdLote" name="fcdLote" style="text-align: right;" maxlength="20"  type="text" value="${fcdLote}">
					</td>
					<td style="font-family: Verdana; font-size: 11px; color: #666666; width: 25%; font-weight: bold;" align="center">
		            	Fecha caducidad<br>
		            	<input size="12" style="background-color: #CCCCCC;" readonly="readonly" id="stFCaducidad" name="stFCaducidad" value="${stFCaducidad}"/>
			            	<img style="cursor: pointer;" class="vtop" id="calendario2" title="Seleccione fecha"  alt="Seleccione fecha" src="img/calendario.gif"/>
			            	<script type="text/javascript">
			                      Calendar.setup({
			                          inputField     :    "stFCaducidad"   // id of the input field
			                      ,
			                          button         :    "calendario2"  // trigger for the calendar (button ID)
			                      });
			            	</script>
					</td>
					<td style="font-family: Verdana; font-size: 12px; color: #666666; width: 15%; font-weight: bold;" align="center">
						Origen<br>
						<input size="16" id="fcdOrigen" name="fcdOrigen" style="text-align: right;" maxlength="20"  type="text" value="${fcdOrigen}">
					</td>
					<c:if test="${facturacompra.fcCierre!='1'}">
						<td style="font-family: Verdana; font-size: 11px; color: #666666; width: 5%">
							<br>
							<a onclick="javascript: doAñadirDetalle();">
								<img src="img/add.png" title="Añadir Producto"/>
							</a>
						</td>
						<td style="font-family: Verdana; font-size: 11px; color: #666666; width: 5%">
							<br><a onclick="javascript: doEliminarDetalle()">
								<img src="img/remove.png" title="Eliminar Producto"/>
								</a>
						</td>
					</c:if>
					<td style="width: 10%">
						&nbsp;
					</td>
				</tr>
				</table>
			</td>
		</tr>
		<td colspan="7" style="font-family: Verdana; font-size: 12px; color: #2DACE4;padding: 5px 0px 0px 5px; font-weight: bold;">
			
		</td>
	 	<tr>
			<td style="width: 75%">
				<table cellpadding="1" class="displaytag_tabla" cellspacing="1" style="margin-bottom: 20px">
				<c:set var="contador" value="0"/>
				<c:set var="indice" value="-1"/>
				<c:set var="totalNeto" value="0"/>
					<thead>
						<tr class="${estilo}">
							<c:if test="${!empty facturacompra.fcId}">
								<th class="columna sortable">
									&nbsp;
								</th>
							</c:if>
							<th class="columna sortable">
								Producto
							</th>							
							<th class="columna sortable">
								Cantidad
							</th>
							<th class="columna sortable">
								Precio
							</th>
							<!--  <th class="columna sortable">
								Iva (%)
							</th>
							<th class="columna sortable">
								Descuento (%)
							</th> -->
							<th class="columna sortable">
								Importe
							</th>
							<th class="columna sortable">
								Lote
							</th>
							<th class="columna sortable">
								Fecha caducidad
							</th>
							<th class="columna sortable">
								Origen
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
								<input type="checkbox" id="checkList" name="checkList" class="check" value="${detalle.fcdId}"/>
							</td>
							<td class="columna" style="text-align: left;">
								<c:if test="${empty detalle.fcdProducto}">&nbsp;</c:if>
								<c:if test="${!empty detalle.fcdProducto}">${detalle.fcdProducto}</c:if>
							</td>
							<td class="columna" style="text-align: right;">
								<fmt:formatNumber pattern="###,###.###" var="cantidad">${detalle.fcdCantidad}</fmt:formatNumber>
								<c:out value="${cantidad}"/>
							</td>
							<td class="columna" style="text-align: right;">
								<fmt:formatNumber pattern="###,###.###" var="precio">${detalle.fcdPrecioCompra}</fmt:formatNumber>
								<c:out value="${precio}"/>
							</td>		
							<!-- <td class="columna" style="text-align: right;">
								<fmt:formatNumber pattern="###,###.###" var="iva">${detalle.fcdIva * 100}</fmt:formatNumber>
								<c:out value="${iva}"/>%
							</td>		
							<td class="columna" style="text-align: right;">
								<fmt:formatNumber pattern="###,###.###" var="descuento">${detalle.fcdDescuento * 100 }</fmt:formatNumber>
								<c:out value="${descuento}"/>
							</td>  -->					
							<td class="columna" style="text-align: right;">
								<fmt:formatNumber pattern="###,###.###" var="importe">${detalle.fcdImporte}</fmt:formatNumber>
								<c:out value="${importe}"/>
							</td>
							<td class="columna" style="text-align: right;">
								<c:out value="${detalle.fcdLote}"/>
							</td>
							<td class="columna" style="text-align: right;">
								<fmt:formatDate value="${detalle.stock.stFCaducidad}" pattern="dd/MM/yyyy" var="fecha" />
								<c:out value="${fecha}"/>
							</td>
							<td class="columna" style="text-align: right;">
								<c:if test="${empty detalle.fcdProdOrigen}">&nbsp;</c:if>
								<c:if test="${!empty detalle.fcdProdOrigen}">${detalle.fcdProdOrigen}</c:if>
							</td>
						</tr>
						<c:set var="totalNeto" value="${totalNeto + detalle.fcdImporte }"/>
					</c:forEach>
				</table>
			</td>
				<td width="25%" style="vertical-align: top;">
					<table cellpadding="1" class="displaytag_tabla" cellspacing="1">
						<thead>
						<tr class="even">
							<td colspan="2" class="columna sortable" style="text-align: center">EUROS</td>
						</tr>
						</thead>
						
						<tr>
							
							<td class="columna" style="text-align: center; font-size: 20px; font-weight: bold;">
								<fmt:formatNumber pattern="###,###.###" var="tn">${totalNeto}</fmt:formatNumber>
								<c:out value="${tn}"/> &euro;
								<c:set var="totalNeto" value="${totalNeto}"/>
							</td>
						</tr>		
						
						<!-- <tr>
							<th class="columna">Importe Neto:</th>
							<td class="columna" style="text-align: right;">
								<fmt:formatNumber pattern="###,###.###" var="tn">${totalNeto}</fmt:formatNumber>
								<c:out value="${tn}"/> &euro;
								<c:set var="totalNeto" value="${totalNeto}"/>
							</td>
						</tr>	 -->
					</table>
				</td>
		</tr>
		</c:if>
</table>

<html:hidden property="fcId" styleId="fcId" value="${facturacompra.fcId}"/>
<html:hidden property="fcPrId" styleId="fcPrId" value="${facturacompra.fcPrId}"/>
<html:hidden property="fcIva" styleId="fcIva" value="${Iva}"/>
<html:hidden property="fcTotal" styleId="fcTotal" value="${totalNeto}"/>
<html:hidden property="fcPagada" styleId="fcPagada" value="0"/>
<!-- Fin Ficha Perfiles -->
</html:form> 
