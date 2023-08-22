<%-- /WEB-INF/modulos/seguridad/perfiles.ficha.jsp --%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/c-1_0-rt.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<script type="text/javascript">
	vFormName = "frmDetalle";
	
	
	function doNotaCuadrante(){
		//ventana('doListadoCuadrantesDetalle.do?cudCuId=${notaentrega.notCuId}&cuClId=${notaentrega.notClId}&sinMenu=yes', '750', '500','');
		ventana('doListadoCuadrantes.do?cuClId=${notaentrega.notClId}&sinMenu=yes', '750', '500','');
	}
	
	
	function doSubmit(){
		vForm = document.getElementById(vFormName);		
		if( document.getElementById('notClId').value=='' ){
			alert('Debe elegir un cliente válido');
			return;
		}else if(document.getElementById('notNombre').value=='' ){
			alert('Debe indicar un nombre');
			return;
		}else if(document.getElementById('notRutId').value=='' ){
			alert('Debe indicar una ruta');
			return;
		}else if(document.getElementById('notOrden').value=='' ){
			alert('Debe indicar un orden');
			return;
		}else if(document.getElementById('notFecha').value=='' ){
			alert('Debe indicar una fecha válida');
			return;
		}else if(document.getElementById('notViaje').value=='' ){
			alert('Debe indicar un viaje');
			return;
		}else if(document.getElementById('notViaje').value>5 || document.getElementById('notViaje').value<1){
			alert('Debe indicar un viaje entre 1 y 5');
			return;
		}else{
			 document.getElementById('frmDetalle').action = 'doNotasEntregaformularioGrabar.do';
        	_enviarDetalle('frmDetalle');
		}
	}

	function doRefresh(){
		vForm = document.getElementById(vFormName);		
		document.getElementById('frmDetalle').action = 'doNotasEntregaFormulario.do';
        _enviarDetalle('frmDetalle');
	
	}
	
	function doEliminarDetalle(){
		vForm = document.getElementById(vFormName);				
		var idDetalles = hayChecked(vForm);
		if( idDetalles == '-1' ){
			alert('Debe elegir al menos un producto para eliminar');
		}
		else{
			document.getElementById('frmDetalle').action = 'doNotasEntregaDetalleBorrar.do?notClId=${notClId}';
			_enviarDetalle('frmDetalle');
		}
	}
	
		
	function doEditar(){
		document.forms[0].action = 'doNotasEntregaFormularioEditar.do';
		document.forms[0].submit();
	}
	
	function doPosterior(){
		vForm = document.getElementById(vFormName);				
		var ident = ${keyMapaActual} + 1;
		document.getElementById('frmDetalle').action = 'doNotasEntregaFormulario.do?notId=' + ident;
		_enviarDetalle('frmDetalle');
	}
	
	function doAnterior(){
		vForm = document.getElementById(vFormName);		
		var ident = ${keyMapaActual} - 1;		
		document.getElementById('frmDetalle').action = 'doNotasEntregaFormulario.do?notId=' + ident;
		_enviarDetalle('frmDetalle');
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
		}else if(document.getElementById('prodCantidad').value==''){
			alert('Debe indicar una cantidad para el producto');
			return;
		}else if( formato_entero(document.getElementById('prodCantidad').value) == '' ){
			return;
		}else if(formato_entero(document.getElementById('prodCantidad').value)<=0){
			alert('La cantidad del producto debe ser mayor a 0');
			return;
		}else{ 
			document.getElementById('frmDetalle').action='doNotasEntregaDetalleGrabarManual.do?manual=1';
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
	
	

</script>

<c:if test="${!empty notaentrega.notId}">
	<c:set value="true" var="readonly"/>
	<c:set value="true" var="disabled"/>
</c:if>

<html:form styleId="frmDetalle" action="/doNotasEntregaformularioGrabar" method="post">

<input type="hidden" value="${keyMapaActual}" id="keyMapa" name="keyMapa"/>
<input type="hidden" value="" id="cuadran" name="cuadran"/>

<table style="background-color: white; border: 1px solid #E5E5E5; width: 100%">
	<tr>          
		  <td colspan="4" align="right">
		  	<span style='color: #FA7D3D; font-size: 12px; font-weight: bold;'>
				<c:if test="${!empty notaentrega.notId}">
					Número Nota: ${notaentrega.notId}
				</c:if>	
			</span>
		  </td>
	    </tr>
	<tr>
		<td colspan="7" style="font-family: Verdana; font-size: 11px; color: #2DACE4;padding: 5px 0px 0px 5px">
			<img src="img/play.gif"/>&nbsp;Datos Básicos de la Nota<br>
			<hr style="color: #DFDEDE">
		</td>
	</tr>
	    <tr> 
          <td align="center" colspan='4' style="padding-left: 20px;"> 
			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="texto">
              <tr> 
                <td style='width: 15%' align="left" class="texto_negrita">Cliente</td>
                <td style='width: 40%' align="left"> 
				<html:text size='60' maxlength="75"  disabled="true" property="notId" name="notId" value="${notaentrega.clDatosRelacionados[0]} ${notaentrega.clDatosRelacionados[1]}"/>
				<c:if test="${empty notaentrega.notId}">
					&nbsp;<img src="img/buscar.gif" onclick="javascript:ventana('doVentanaSearchClienteNotasEntrega.do?notId=${notaentrega.notId}', '750', '500','scrollbars=yes');">
				</c:if>	
				</td>
				<td style='width: 4%'>&nbsp;</td>
				<td style='width: 5%' align="left" class="texto_negrita">Nombre</td>
                <td style='width: 40%' align="left">
                	<html:select disabled="${disabled}" style="width: 150px" styleId="notNombre" property="notNombre" value="${notaentrega.notNombre}">
					  <html:option value="">[Seleccionar]</html:option>
					  <html:options property="entNombre" labelProperty="entNombre" collection="LISTAENTREGAS"/>
				    </html:select>&nbsp;<img src="img/asterisco.gif" alt="<bean:message key='campo.obligatorio'/>" title="<bean:message key='campo.obligatorio'/>">
	            </td>
              </tr>
            </table>
		  </td>
        </tr>
        <tr> 
          <td align="center" colspan='4' style="padding-left: 20px;"> 
			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="texto">
              <tr> 
              	<td style='width: 15%' align="left" class="texto_negrita">Nombre Comercial</td>
                <td style='width: 40%' align="left"> 
					<html:text size='60' maxlength="75" disabled="true" property="clNombreComercial" name="clNombreComercial" value="${notaentrega.clDatosRelacionados[7]}"/>&nbsp;<img src="img/asterisco.gif" alt="<bean:message key='campo.obligatorio'/>" title="<bean:message key='campo.obligatorio'/>">
				</td>	
				<td style='width: 4%'>&nbsp;</td>
				<td style='width: 5%' align="left" class="texto_negrita">Ruta</td>
                <td style='width: 40%' align="left">
					<html:select disabled="${disabled}" style="width: 150px" styleId="notRutId" property="notRutId" value="${notaentrega.notRutId}">
					  <html:option value="">[Seleccionar]</html:option>
					  <html:options property="rutId" labelProperty="rutNombre" collection="RUTASLISTA"/>
				    </html:select>
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
					<html:text size='60' maxlength="75" disabled="true" property="clDireccion" name="clDireccion" value="${notaentrega.clDatosRelacionados[2]}"/>&nbsp;<img src="img/asterisco.gif" alt="<bean:message key='campo.obligatorio'/>" title="<bean:message key='campo.obligatorio'/>">
				</td>	                
				<td style='width: 4%'>&nbsp;</td>
				<td style='width: 5%' align="left" class="texto_negrita">Orden</td>
                <td style='width: 40%' align="left">
					<html:text readonly="${readonly}" size='20' maxlength="2" onblur="javascript:this.value=formato_entero(this.value)" styleId="notOrden" property="notOrden" name="notOrden" value="${notaentrega.notOrden}"/>&nbsp;<img src="img/asterisco.gif" alt="<bean:message key='campo.obligatorio'/>" title="<bean:message key='campo.obligatorio'/>">
	            </td>             
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
				<td style='width: 4%'>&nbsp;</td>
				<td style='width: 5%' align="left" class="texto_negrita">Fecha</td>
                <td style='width: 40%' align="left">
					<fmt:formatDate value="${notaentrega.notFecha}" pattern="dd/MM/yyyy" var="fecha" />   	
	            	<html:text style="background-color: #CCCCCC;" readonly="true" disabled="${DISABLED}" styleId="notFecha" property="notFecha" name="notFecha" value="${fecha}"/>
	            	<c:if test="${empty notaentrega.notId}">
		            	<img style="cursor: pointer;" class="vtop" id="calendario1" title="Seleccione fecha"  alt="Seleccione fecha" src="img/calendario.gif"/>
		            	<script type="text/javascript">
		                      Calendar.setup({
		                          inputField     :    "notFecha"   // id of the input field
		                      ,
		                          button         :    "calendario1"  // trigger for the calendar (button ID)
		                      });
		            	</script>
		            </c:if>
	            </td>             
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
				<td style='width: 4%'>&nbsp;</td>
				<td style='width: 5%; vertical-align: top' align="left" class="texto_negrita">Viaje</td>
                <td style='width: 40%; vertical-align: top' align="left">
					<html:text readonly="${readonly}" size='20' maxlength="1" onblur="javascript:this.value=formato_entero(this.value)" styleId="notViaje" property="notViaje" name="notViaje" value="${notaentrega.notViaje}"/>&nbsp;<img src="img/asterisco.gif" alt="<bean:message key='campo.obligatorio'/>" title="<bean:message key='campo.obligatorio'/>">
	            </td>               
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
					<html:textarea rows="4"  cols="37" property="notObservaciones" name="notObservaciones" value="${notaentrega.notObservaciones}"/>
				</td>	
				<td style='width: 4%'>&nbsp;</td>
				<td style='width: 5%; vertical-align: top' align="left" class="texto_negrita"></td>
                <td style='width: 40%; vertical-align: top' align="left">					
	            </td>               
              </tr>
            </table>
		  </td>		  
        </tr>    
        <tr ><td height="20px"></td></tr>   
		<!-- Fin Linea 21 -->
	<c:if test="${!empty notaentrega.notId}">
	<tr>
		<td colspan="7" style="font-family: Verdana; font-size: 11px; color: #2DACE4;padding: 5px 0px 0px 5px">
			<img src="img/play.gif"/>&nbsp;Detalles de notas de entrega<br>
			<hr style="color: #DFDEDE">
				<table width="40%">
				<tr>
					<td style="font-family: Verdana; font-size: 11px; color: #666666; width: 10%">
							Producto<br>
							<html:select style="width: 250px" styleId="prodId" property="prodId" value="">
							  <html:option value="">[Seleccionar]</html:option>
							  <html:options property="prodId" labelProperty="prodNombre" collection="LISTAPRODUCTOS"/>
						    </html:select>
						</td>
						
						<td style="font-family: Verdana; font-size: 11px; color: #666666; width: 10%">
							Precio<br>
							<input onblur="this.value=formato_numero(this.value,1)"  size="16" id="prodPrecio" style="text-align: right;" name="prodPrecio" maxlength="11"  type="text" value="">
						</td>
						<td style="font-family: Verdana; font-size: 11px; color: #666666; width: 10%">
							Cantidad<br>
							<input onblur="this.value=formato_entero(this.value)"  size="16" id="prodCantidad" style="text-align: right;" name="prodCantidad" maxlength="11"  type="text" value="">
						</td>
						<c:if test="${notaentrega.notCierre!='1'}">
							<td style="font-family: Verdana; font-size: 11px; color: #666666; width: 5%">
								<a onclick="javascript: doAñadirDetalle();">
									<img src="img/add.png" title="Añadir Producto Manual"/>
								</a>
							</td>
							<td style="font-family: Verdana; font-size: 11px; color: #666666; width: 5%">
								<a onclick="javascript: doEliminarDetalle();"> 
									<img  src="img/remove.png" title="eliminar detalle"/>
								</a>
							</td>
							<td style="font-family: Verdana; font-size: 11px; color: #666666; width: 5%">
								<a>
									<img src="img/producto.gif" title="Añadir producto cliente" onclick="javascript:ventana('doVentanaSearchProductosClienteNotasEntrega.do?notClId=${notaentrega.notClId}&notId=${notaentrega.notId}', '750', '500','');">
								</a>
							</td>
						</c:if>
					</tr>
				</table>	
		</td>
	</tr>
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
								Precio (&euro;)
							</th>
							<!-- 
							<th class="columna sortable">
								I.v.a. (%)
							</th>
							 -->
							<th class="columna sortable">
								Descuento (%)
							</th>
							<th class="columna sortable">
								Importe (&euro;)
							</th>
							<th class="columna sortable">
								Lote
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
								<input type="checkbox" name="checkList" id="checkList" class="check" value="${detalle.notdetId}"/>
							</td>
							<td class="columna" style="text-align: left;">
								<c:if test="${empty detalle.notdetProducto}">&nbsp;</c:if>
								<c:if test="${!empty detalle.notdetProducto}">${detalle.notdetProducto}</c:if>
							</td>
							<td class="columna" style="text-align: right;">
								
								<fmt:formatNumber pattern="###,###.###" var="cantidad">${detalle.notdetCantidad}</fmt:formatNumber>
								<fmt:formatNumber pattern="#########" var="cantidad2">${detalle.notdetCantidad}</fmt:formatNumber>
								<input  size="11" 
										onblur="javascript:this.value=formato_entero(this.value)" 
										id="cantidad_${detalle.notdetId}"  style="text-align: right;"
										name="cantidad_${detalle.notdetId}" maxlength="16"  type="text" value="${cantidad2}">
							</td>
							<td class="columna" style="text-align: right;">
								<fmt:formatNumber pattern="###,###.###" var="precio">${detalle.notdetPrecio}</fmt:formatNumber>
								<c:out value="${precio}"/>
							</td>	
							<!-- 
							<td class="columna" style="text-align: right;">
								<fmt:formatNumber pattern="###,###.###" var="iva">${detalle.notdetIva * 100}</fmt:formatNumber>
								<c:out value="${iva}"/>
							</td>	
							 -->
							<td class="columna" style="text-align: right;">
								<fmt:formatNumber pattern="###,###.###" var="descuento">${detalle.notdetDescuento * 100}</fmt:formatNumber>
								<c:out value="${descuento}"/>
							</td>							
							<td class="columna" style="text-align: right;">
								<fmt:formatNumber pattern="###,###.###" var="importe">${detalle.notdetImporte}</fmt:formatNumber>
								<c:out value="${importe}"/>
							</td>
							<td class="columna" style="text-align: right;">
								<c:out value="${detalle.lote}"/>
							</td>
						</tr>
						<c:set var="totalNeto" value="${totalNeto + detalle.notdetImporte}"/>
					</c:forEach>
				</table>
			</td>
			<c:if test="${totalNeto>0}">
				<td width="25%" style="vertical-align: top;">
					<table cellpadding="1" class="displaytag_tabla" cellspacing="1">
						<thead>
							<tr class="even">
								<td colspan="2" class="columna sortable" style="text-align: center;color: orange">	Totales Generales</td>
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
							<th class="columna">Importe Neto:</th>
							<td class="columna" style="text-align: right;">
								<fmt:formatNumber pattern="###,###.###" var="tn">${totalNeto}</fmt:formatNumber>
								<c:out value="${tn}"/> &euro;
							</td>
						</tr>	
					</table>
				</td>
			</c:if>
		</tr>
		</c:if>
</table>

<html:hidden property="notId" styleId="notId" value="${notaentrega.notId}"/>
<c:choose>
	<c:when test="${!empty notClId}">
		<html:hidden property="notClId" styleId="notClId" value="${notClId}"/>
	</c:when>
	<c:otherwise>
		<html:hidden property="notClId" styleId="notClId" value="${notaentrega.notClId}"/>
	</c:otherwise>
</c:choose>
<html:hidden property="notTotal" value="${totalNeto}"/>
<html:hidden property="notPagada" value="0"/>
<!-- Fin Ficha Perfiles -->
</html:form> 
