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
		else if(document.getElementById('fcNumeroFactura').value == '' ){
			alert('Debe introducir un número para la fabricación');
			return;
		}else if( validateFields(vForm.name) ){
			document.getElementById('frmDetalle').action = 'doFacturasFabricacionformularioGrabar.do';
        	_enviarDetalle('frmDetalle');
        }       
	}
	
	
	function doImprimirEtiquetas( detalle ){
		vForm = document.getElementById(vFormName);		
		document.getElementById('frmDetalle').action = 'doGenerarEtiquetas.do?idDetalle='+detalle;
        _enviarDetalle('frmDetalle');
        
               
	}
		
	
	function doEliminarDetalle(){
		if(hayMasDeUnChecked('frmDetalle')){
			if(confirm('Va a eliminar el producto seleccionado, ¿Desea continuar?')){
				document.getElementById('frmDetalle').action = 'doFacturasFabricacionDetalleBorrar.do?fcPrId=${fcPrId}';
				_enviarDetalle('frmDetalle');
			}
		}
		
	}
	
	
	
	function doAñadirFabricacion(){
		vForm = document.getElementById(vFormName); 
		if(document.getElementById('prodId').value==''){
			alert('Debe indicar un producto para insertar');
			return;
		}
		/*else if( formato_numero(document.getElementById('prodCantidad').value) ==''){
			alert('Debe indicar una cantidad para el producto');
			return;
		}else if( formato_numero(document.getElementById('prodCantidad').value) == '' ){
			return;
		}else if(formato_numero(document.getElementById('prodCantidad').value)<= formato_numero(0)){
			alert('La cantidad del producto debe ser mayor a 0');
			return;
		}else if(document.getElementById('stFCaducidad').value==''){
			alert('Debe indicar la fecha de caducidad del producto');
			return;
		}else if( !compararFecha(fechaActualFormato(), document.getElementById("stFCaducidad").value) ){
			alert('La fecha de caducidad no puede ser inferior a la fecha de hoy');
			return;
		}*/
		//else if(document.getElementById('fcdLote').value==''){
			//alert('Debe indicar el lote del producto');
			//return;
		else{
			document.getElementById('frmDetalle').action='doVentanaSearchProductosFabricacion.do';
			ventanaNombre('', '500', '400','','CallMePopUp');
			document.getElementById('frmDetalle').target='CallMePopUp';
			document.getElementById('frmDetalle').submit();
			document.getElementById('frmDetalle').target='';
		}
	}
		
		
		function doImprimirEtiquetas(){
			document.getElementById('frmDetalle').action='doVentanaImprimirEtiquetasCompras.do';
			ventanaNombre('', '600', '400','','CallMePopUp');
			document.getElementById('frmDetalle').target='CallMePopUp';
			document.getElementById('frmDetalle').submit();
			document.getElementById('frmDetalle').target='';		
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
	
	function doRefresh(){
		vForm = document.getElementById(vFormName);		
		document.getElementById('frmDetalle').action = 'doFacturasFabricacionFormulario.do';
        _enviarDetalle('frmDetalle');
	
	}
	
	function muestraDatos(id){
		mostrado=0;
		elem = document.getElementById(id);
		if(elem.style.display=='block')
			mostrado=1;
		elem.style.display='none';
		if(mostrado!=1)
			elem.style.display='block';
	}
	
</script>

<html:form styleId="frmDetalle" action="/doFacturasFabricacionformularioGrabar" method="post">
<table style="background-color: white; border: 1px solid #E5E5E5; width: 100%">
	<tr>          
		  <td colspan="4" align="right">
		  	<span style='color: #FA7D3D; font-size: 12px; font-weight: bold;'>
				<%-- <c:if test="${!empty facturacompra.fcId}">
					Número Factura Fabricación: ${facturacompra.fcId}
				</c:if>--%>	
			</span>
		  </td>
	    </tr>
	<tr>
		<td colspan="7" style="font-family: Verdana; font-size: 11px; color: #2DACE4;padding: 5px 0px 0px 5px">
			<img src="img/play.gif"/>&nbsp;Datos Básicos de la Fabricación<br>
			<hr style="color: #DFDEDE">
		</td>
	</tr>
		<tr ><td height="10px"></td></tr> 
	    <tr> 
          <td align="center" colspan='4' style="padding-left: 20px;"> 
			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="texto">
              <tr> 
                <td style='width: 10%' align="left" class="texto_negrita">Proveedor</td>
                <td style='width: 20%' align="left"> 
					<html:text size='40' maxlength="75"  disabled="true" property="fcId" name="fcId" value="${facturacompra.prDatosRelacionados[0]}"/>
					<%-- <c:if test="${empty facturacompra.fcId}">
						&nbsp;<img src="img/buscar.gif" onclick="javascript:ventana('doVentanaSearchProveedoresFactura.do?fcId=${facturacompra.fcId}&tipo=F', '750', '500','');">
						</c:if>
					--%>	
				</td>
				<td style='width: 4%'>&nbsp;</td>
				<td style='width: 5%' align="left" class="texto_negrita">Fecha</td>
                <td style='width: 15%' align="left">
					<fmt:formatDate value="${facturacompra.fcFecha}" pattern="dd/MM/yyyy" var="fecha" />    	 	
	            	<html:text style="background-color: #CCCCCC;" readonly="true" disabled="${DISABLED}" styleId="fcFecha" property="fcFecha" name="fcFecha" value="${fecha}"/>
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
	            <td style='width: 5%' align="left" class="texto_negrita">Número</td>
	            <c:choose>
		            <c:when test="${empty facturacompra.fcId}">
		                <td style='width: 10%' align="left"> 
							<html:text size='20' maxlength="75" styleId="fcNumeroFactura" property="fcNumeroFactura" name="fcNumeroFactura" value="${facturacompra.fcNumeroFactura}"/>
						</td>
					</c:when>
					<c:otherwise><td style='width: 10%' align="left"> 
							<html:text size='20' maxlength="75" styleId="fcNumeroFactura" readonly="true" property="fcNumeroFactura" name="fcNumeroFactura" value="${facturacompra.fcNumeroFactura}"/>
						</td> 
					</c:otherwise>
				</c:choose>
				
				<td style='width: 20%'>&nbsp;</td>
              </tr>
            </table>
		  </td>
        </tr>
    <%-- <tr> 
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
    --%>    
    <tr ><td height="15px"></td></tr> 
		<!-- Fin Linea 21 -->
	<c:if test="${!empty facturacompra.fcId}">
	<tr>
		<td colspan="7" style="font-family: Verdana; font-size: 11px; color: #2DACE4;padding: 5px 0px 0px 5px">
			<img src="img/play.gif"/>&nbsp;Detalles de facturación<br>
			<hr style="color: #DFDEDE">
				<table width="30%">
				<tr>	
					<td style="font-family: Verdana; font-size: 12px; color: #666666; width: 15%;font-weight: bold;" align="center"">
						Producto a Fabricar<br>
						<select style="width: 250px" id="prodId" name="prodId">
						  <option value="">[Seleccionar]</option>
						  <c:forEach items="${LISTAPRODUCTOS}" var="prod">
						  	<option <c:if test="${prodId==prod.prodId}"> selected="selected" </c:if> value="${prod.prodId}">${prod.prodNombre}</option>
						  </c:forEach>
					    </select>
					</td>
					
					<%--<td style="font-family: Verdana; font-size: 11px; color: #666666; width: 15%">
						Cantidad<br>
							<input onblur="this.value=formato_entero(this.value)" size="16" id="prodCantidad" name="prodCantidad" style="text-align: right;" maxlength="11"  type="text" value="${prodCantidad}">
					</td>
					<td style="font-family: Verdana; font-size: 11px; color: #666666; width: 15%">
						Lote<br>
						<input size="16" id="fcdLote" name="fcdLote" style="text-align: right;" maxlength="11"  type="text" value="${fcdLote}">
					</td>
					<td style="font-family: Verdana; font-size: 11px; color: #666666; width: 25%">
		            	F.caducidad<br>
		            	<input size="12" style="background-color: #CCCCCC;" readonly="readonly" id="stFCaducidad" name="stFCaducidad" value="${stFCaducidad}"/>
			            	<img style="cursor: pointer;" class="vtop" id="calendario2" title="Seleccione fecha"  alt="Seleccione fecha" src="img/calendario.gif"/>
			            	<script type="text/javascript">
			                      Calendar.setup({
			                          inputField     :    "stFCaducidad"   // id of the input field
			                      ,
			                          button         :    "calendario2"  // trigger for the calendar (button ID)
			                      });
			            	</script>
					</td>  --%>
					<c:if test="${facturacompra.fcCierre!='1'}">
						<td style="font-family: Verdana; font-size: 11px; color: #666666; width: 5%">
							<br>
							<a onclick="javascript: doAñadirFabricacion();">
								<img src="img/add.png" title="Añadir Fabricación"/>
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
	 	<tr>
			<td style="width: 65%">
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
							<c:if test="${facturacompra.fcPrId!='999' && facturacompra.fcPrId!='998'}">
								<th class="columna sortable">
									Precio (&euro;)
								</th>
								<th class="columna sortable">
									Iva (%)
								</th>
								<th class="columna sortable">
									Descuento (%)
								</th>
								<th class="columna sortable">
									Importe (&euro;)
								</th>
							</c:if>
							<th class="columna sortable">
								Fecha caducidad
							</th>
							<th class="columna sortable">
								Lote
							</th>
							<!-- <th>etiqueta</th>
							<th>Comp</th> -->
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
							<td width="5%" class="columna" style="text-align: center;">
								<input type="checkbox" id="checkList" name="checkList" class="check" value="${detalle.fcdId}"/>
							</td>
							<td width="45%" class="columna" style="text-align: left;">
								<c:if test="${empty detalle.fcdProducto}">&nbsp;</c:if>
								<c:if test="${!empty detalle.fcdProducto}">${detalle.fcdProducto}</c:if>
							</td>
							<td width="10%" class="columna" style="text-align: right;">
								<fmt:formatNumber pattern="###,###.###" var="cantidad">${detalle.fcdCantidad}</fmt:formatNumber>
								<c:out value="${cantidad}"/>
							</td>
							<td width="20%" class="columna" style="text-align: right;">
								<fmt:formatDate value="${detalle.stock.stFCaducidad}" pattern="dd/MM/yyyy" var="fecha" />
								<c:out value="${fecha}"/>
							</td>
							<td width="25%" class="columna" style="text-align: right;">
								<c:out value="${detalle.fcdLote}"/>
							</td>
							<!-- <td align="center" style="font-family: Verdana; font-size: 11px; color: #666666; width: 5%">
							<br><a onclick="javascript: doImprimirEtiquetas(${detalle.fcdId})">
								<img src="img/etiqueta.png" title="Eliminar Producto"/>
								</a>
							</td>
							<td align="center" style="font-family: Verdana; font-size: 11px; color: #666666; width: 5%">
							<br><a onclick="javascript: muestraDatos(${indice});">
								<img src="img/etiqueta.png" title="Eliminar Producto"/>
								</a>
							</td> -->
						</tr>
						<tr id="${indice}" style="display:none;">
							<td>
								<table border="1" class="displaytag_tabla" style="">
									<thead>
										<tr class="${estilo}">
											<th class="columna sortable">
												Producto Usado
											</th>
											<%--<th class="columna sortable">
												Cantidad
											</th>--%>	
											<th class="columna sortable">
												Lote
											</th>	
										</tr>
									</thead>
									<c:forEach items="${detalle.componentesUsados}" var="cou">
										<tr>
											<td>${cou.nombreProdUsado}</td>
											<%--<td align="right">${cou.cfCantidad} Kg</td>--%>
											<td align="right">${cou.lote}</td>
										</tr>
									</c:forEach>
								</table>
							</td>
						</tr>
					</c:forEach>
				</table>
			</td>
			<td width="35%" style="vertical-align: top;">
				&nbsp;
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
