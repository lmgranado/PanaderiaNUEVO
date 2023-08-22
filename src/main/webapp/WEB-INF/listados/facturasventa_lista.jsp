<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script>

	function doBuscarPropio(elemento) {
		var form = document.getElementById('frmListado');
		if(document.getElementById('fvFechaHasta').value=='' || document.getElementById('fvFechaDesde').value==''){
			alert('Debe indicar un filtro de fechas para realizar la búsqueda');
			return;
		}
		if(!compare_dates(document.getElementById('fvFechaHasta').value, document.getElementById('fvFechaDesde').value)){
			alert('La fecha Hasta no puede ser inferior a la fecha Desde');
			return;
		}
		if(diferencia(document.getElementById('fvFechaDesde').value, document.getElementById('fvFechaHasta').value)>370){
			alert('La diferencia entre las fechas no puede ser superior a un año');
			return;
		}
		document.getElementById('filtro').value = '1';
		form.target = "";
		form.submit();
	}

	function doNuevo(){
		document.getElementById('frmListado').action='doFacturasVentaFormulario.do?nuevo=1';
		document.getElementById('frmListado').target="";
		document.getElementById('frmListado').submit();
	}
	
	function doReferencia(fvId){
		if(confirm('Va a deshabilitar la factura actual y crear una de referencia, ¿desea continuar?')){
			document.getElementById('frmListado').action='doFacturasVentaReferenciaCrear.do?fvRef=' + fvId;
			document.getElementById('frmListado').target="";
			document.getElementById('frmListado').submit();
		}
	}
	
	function doBorrar(){
		if(hayMasDeUnChecked('frmListado')){
			if(confirm('Va a eliminar las facturas seleccionadas, ¿Desea continuar?')){
				document.getElementById('frmListado').action='doFacturasVentaBorrar.do';
				document.getElementById('frmListado').target="";
				document.getElementById('frmListado').submit();
			}
		}
		
	}
	
	function doActualizaFactura(){
		if(hayChecked('frmListado')){
			if(confirm('Va a actualizar las facturas seleccionadas, ¿Desea continuar?')){
				document.getElementById('frmListado').action='doFacturasVentaActualizarEstado.do';
				document.getElementById('frmListado').target="";
				document.getElementById('frmListado').submit();
			}
		}else{
			alert('Debe marcar algúna factura para actualizar su estado');
		}
	}
	
		
	function doCerrarFactura(){
		if(hayChecked('frmListado')){
			if(confirm('Va a cerrar las facturas seleccionadas, ¿Desea continuar?')){
				document.getElementById('frmListado').action='doFacturasVentaCerrar.do';
				document.getElementById('frmListado').target="";
				document.getElementById('frmListado').submit();
			}
		}else{
			alert('Debe marcar algúna factura para cerrarla');
		}
	}
	
	function doImprimirFactura(){
		if(hayChecked('frmListado')){
			if(confirm('Va a imprimir las facturas seleccionadas, ¿Desea continuar?')){
				document.getElementById('frmListado').action='doGenerarRtfFacturasVenta.do';
				document.getElementById('frmListado').target="_new";
				document.getElementById('frmListado').submit();
				document.getElementById('frmListado').action='doListadoFacturasVenta.do';
				document.getElementById('frmListado').target="";
			}
		}else{
			alert('Debe marcar alguna de las facturas para realizar la operación solicitada');
		}
	}
	
	function doVerFactura(){
		if(hayChecked('frmListado')){
			if(confirm('Va a visualizar las facturas seleccionadas, ¿Desea continuar?')){
				document.getElementById('frmListado').action='doGenerarFacturasVenta.do';
				document.getElementById('frmListado').target="_new";
				document.getElementById('frmListado').submit();
				document.getElementById('frmListado').action='doListadoFacturasVenta.do';
				document.getElementById('frmListado').target="";
			}
		}else{
			alert('Debe marcar alguna de las facturas para realizar la operación solicitada');
		}
	}
	
	function doImprimirAgrupadas(){
		if(hayChecked('frmListado')){
			if(confirm('Va a imprimir las facturas agrupadas según su seleccion, ¿Desea continuar?')){
				document.getElementById('frmListado').action='doGenerarFacturasVenta.do';
				document.getElementById('frmListado').target="_new";
				document.getElementById('frmListado').agrupados.value="agrupados";
				document.getElementById('frmListado').submit();
				document.getElementById('frmListado').action='doListadoFacturasVenta.do';
				document.getElementById('frmListado').agrupados.value="";
				document.getElementById('frmListado').target="";
			}
		}else{
			alert('Debe marcar alguna de las facturas para realizar la operación solicitada');
		}
	}
	
	
</script>
<html:form styleId="frmListado" method="post"
				action="/doListadoFacturasVenta.do">
<input type="hidden" id="agrupados" name="agrupados" value=""/>
<table style="background-color: white; border: 1px solid #E5E5E5; width: 100%">
	<tr>
		<td colspan="9" style="font-family: Verdana; font-size: 11px; color: #2DACE4;padding: 5px 0px 0px 5px">
			<img src="img/play.gif"/>&nbsp;Búsqueda<br>
			<hr style="color: #DFDEDE">
		</td>
	</tr>
	<tr>
		<td width="2%"></td>
		<td width="15%" style="font-family: Verdana; font-size: 11px; color: #666666; font-weight: bold;">
			<b>NUMERO FACTURA</b><br>
			<input id="fvNumeroFactura" size="18" name="fvNumeroFactura" type="text" value="${FFACTURAS_VENTA.fvNumeroFactura}">
		</td>
		
		<td width="20%" style="font-family: Verdana; font-size: 11px; color: #666666; font-weight: bold;">
			<b>NOMBRE CLIENTE</b><br>
			<input id="fvObservaciones" size="25" name="fvObservaciones" type="text" value="${FFACTURAS_VENTA.fvObservaciones}">
		</td>
		
		<!-- <td  width="5%" style="font-family: Verdana; font-size: 11px; color: #666666">
			<b>Tipo</b><br>
			<html:select style="width: 100px" styleId="fvIva" property="fvIva" value="${FFACTURAS_VENTA.fvIva}">
			  <html:option value="">[Seleccionar]</html:option>
			  <html:option value="A">A</html:option>
			  <html:option value="B">B</html:option>
		    </html:select>
		</td>
		
		<td  width="12%" style="font-family: Verdana; font-size: 11px; color: #666666">
			<b>Clientes</b><br>
			<html:select style="width: 200px" styleId="fvClId" property="fvClId" value="${FFACTURAS_VENTA.fvClId}">
			  <html:option value="">[Seleccionar]</html:option>
			  <html:options property="clId" labelProperty="clNombreComercial" collection="LISTACLIENTES"/>
		    </html:select>
		</td>
		
		<td  width="12%" style="font-family: Verdana; font-size: 11px; color: #666666">
			<b>Cobradores</b><br>
			<html:select style="width: 200px" styleId="clCobId" property="clCobId" value="${FFACTURAS_VENTA.clCobId}">
			  <html:option value="">[Seleccionar]</html:option>
			  <html:options property="cobId" labelProperty="cobNombre" collection="LISTACOBRADORES"/>
		    </html:select>
		</td>
		
		<td  width="10%" style="font-family: Verdana; font-size: 11px; color: #666666">
			<b>Estado</b><br>
			<html:select style="width: 100px" styleId="fvPagada" property="fvPagada" value="${FFACTURAS_VENTA.fvPagada}">
			  <html:option value="">[Seleccionar]</html:option>
			  <html:option value="1">Pagada</html:option>
			  <html:option value="0">No Pagada</html:option>
		    </html:select>
		</td>
		
		<td  width="5%" style="font-family: Verdana; font-size: 11px; color: #666666">
			<b>Tipo Facturación</b><br>
			<html:select  style="width: 110px" styleId="clPfId" property="clPfId" value="${FFACTURAS_VENTA.clPfId}">
				  <html:option value="">[Seleccionar]</html:option>
				  <html:options property="pfId" labelProperty="pfDescripcion" collection="LISTAPERIODOS"/>
			</html:select>
		</td>
		--> 
		<!--
		<td colspan="2" align="right" width="15%">
			<img src="img/leyendaFacturas.png">
		</td>
     	
       </tr>
       <tr>  
       		<td width="2%"></td>
       		
       		<td  width="5%" style="font-family: Verdana; font-size: 11px; color: #666666">
			<b>Tipo de Factura</b><br>
			<html:select  style="width: 110px" styleId="fvTipo" property="fvTipo" value="${FFACTURAS_VENTA.fvTipo}">
				<html:option value="">[Seleccionar]</html:option>
				<html:option value="0">Facturas</html:option>
			  	<html:option value="1">Facturas Directas</html:option>
			  	<html:option value="2">Facturas Abono</html:option>
			  	<html:option value="3">F. Rectificativa</html:option>
			</html:select>
			</td>
		-->
			<c:set var="today" value="<%=new java.util.Date()%>" />
       		<td width="15%" style="font-family: Verdana; font-size: 11px; color: #666666">
	           	<b>FECHA DESDE</b><br>
	           		<c:choose>
						<c:when test="${FFACTURAS_VENTA.fvFechaDesde == null || FFACTURAS_VENTA.fvFechaDesde == '' }">
							<fmt:formatDate value="${today}" pattern="dd/MM/yyyy" var="fvFechaDesde" />
						</c:when>	
						<c:otherwise>
							<fmt:formatDate value="${FFACTURAS_VENTA.fvFechaDesde}" pattern="dd/MM/yyyy" var="fvFechaDesde" />
						</c:otherwise>
					</c:choose>
					
					<%-- <fmt:formatDate value="${FFACTURAS_VENTA.fvFechaDesde}" pattern="dd/MM/yyyy" var="fvFechaDesde" /> --%>
	        	<html:text style="background-color: #CCCCCC;" size="11" readonly="true" styleId="fvFechaDesde" property="fvFechaDesde" name="fvFechaDesde" value="${fvFechaDesde}"/>
	        	<img style="cursor: pointer;" class="vtop" id="calendario1" title="Seleccione fecha"  alt="Seleccione fecha" src="img/calendario.gif"/>
	        	<script type="text/javascript">
	                  Calendar.setup({
	                      inputField     :    "fvFechaDesde"   // id of the input field
	                  ,
	                      button         :    "calendario1"  // trigger for the calendar (button ID)
	                  });
	        	</script>
        	</td>
         
         <td width="15%" style="font-family: Verdana; font-size: 11px; color: #666666";>
            	<b>FECHA HASTA</b><br>
            	
            	<c:choose>
						<c:when test="${FFACTURAS_VENTA.fvFechaHasta == null || FFACTURAS_VENTA.fvFechaHasta == '' }">
							<fmt:formatDate value="${today}" pattern="dd/MM/yyyy" var="fvFechaHasta" />
						</c:when>	
						<c:otherwise>
							<fmt:formatDate value="${FFACTURAS_VENTA.fvFechaHasta}" pattern="dd/MM/yyyy" var="fvFechaHasta" />
						</c:otherwise>
					</c:choose>
			<%-- <fmt:formatDate value="${FFACTURAS_VENTA.fvFechaHasta}" pattern="dd/MM/yyyy" var="fvFechaHasta" />  --%>  	 	
         	<html:text style="background-color: #CCCCCC;" size="11" readonly="true" styleId="fvFechaHasta" property="fvFechaHasta" name="fvFechaHasta" value="${fvFechaHasta}"/>
         	<img style="cursor: pointer;" class="vtop" id="calendario2" title="Seleccione fecha"  alt="Seleccione fecha" src="img/calendario.gif"/>
         	<script type="text/javascript">
                   Calendar.setup({
                       inputField     :    "fvFechaHasta"   // id of the input field
                   ,
                       button         :    "calendario2"  // trigger for the calendar (button ID)
                   });
         	</script>
         </td>     
		<td width="7%" style="font-family: Verdana; font-size: 11px; color: #666666">
			<input id="filtro" name="filtro"  type="hidden" value="">
				<br><a onclick="javascript: doBuscarPropio(this)">
					<img src="img/buscar.gif" title="Aplicar filtro"/>
					</a>
					&nbsp;&nbsp;&nbsp;
					<a onclick="javascript: doLimpiar(this)">
					<img src="img/borrar.gif" title="Borrar filtro"/>
					</a>
		</td>
		
		<td colspan="5"></td>
	</tr>
	<tr>
		<td colspan="9" style="">
			&nbsp;
		</td>
	</tr>
	<tr>
		<td colspan="9" style="font-family: Verdana; font-size: 11px; color: #2DACE4;padding: 5px 0px 0px 5px">
			<img src="img/play.gif"/>&nbsp;Resultado<br>
			<hr style="color: #DFDEDE">
		</td>
	</tr>
	<tr>
		<td colspan="9">
			
				<display:table name="LISTAFACTURASVENTA" id="facturasv" export="false"
					cellpadding="0" cellspacing="0" summary="Tabla Listado"
					sort="external" class="" requestURI="/doListadoFacturasVenta.do"
					defaultsort="1" defaultorder="ascending" pagesize="10">
			
				<c:choose>
					<c:when test="${facturasv.fvTipo=='1'}">
						<c:set var="estilo" value=";background-color: "/>
					</c:when>	
					<c:when test="${facturasv.fvTipo=='2'}">
						<c:set var="estilo" value=";background-color: #FEFCB8"/>
					</c:when>
					<c:when test="${!empty facturasv.fvFvRef && facturasv.fvFvRef!='0'}">
						<c:set var="estilo" value=";background-color: #EEFEB8"/>
					</c:when>
					<c:otherwise>
						<c:set var="estilo" value=""/>
					</c:otherwise>
				</c:choose>
			
					<display:column class="columna porcen2" headerClass="columna porcen2"
						style="${estilo2009} ${estilo}" alt="selecc/deselec" function="checkUncheck()" src="img/marca_desmarca.gif" input="img">
							<input type="checkbox" id="checkList" name="checkList" class="check" value="${facturasv.fvId}" />
					</display:column>
			
					<display:column class="columna porcen8"
						headerClass="columna porcen8" sortProperty="fvNumeroFactura"
						style="color: #F76D2D ${estilo}" title="Nº Factura" sortable="true">
							&#67;${facturasv.fvNumeroFactura}
					</display:column>
					
					
					<display:column class="columna porcen15"
						headerClass="columna porcen15" sortProperty="clNombre" 
						style="color: #F76D2D ${estilo}" title="Nombre del Cliente">
							${facturasv.fvObservaciones}
					</display:column>
					
					<display:column class="columna porcen5"
						headerClass="columna porcen5" sortProperty="fvFecha"
						style="color: #F76D2D ${estilo}" title="Fecha" sortable="true">
							<fmt:formatDate value="${facturasv.fvFecha}"  pattern="dd/MM/yyyy" /> 
					</display:column>
								
					<display:column  class="columna porcen8"
						headerClass="columna porcen8" sortProperty="fvTotal"
						style="color: #F76D2D ${estilo}" title="Importe (&euro;)" sortable="true">
							<fmt:formatNumber pattern="###,###.###" var="total">${facturasv.fvTotal}</fmt:formatNumber>
							${total} &euro;
					</display:column>
					
					<c:if test="${usuario.usAdministrador == 1}">		
						<display:column class="columna porcen2" headerClass="columna porcen2" style="${estilo2009} ${estilo}" title="Ver">					
								<a href="doFacturasVentaFormulario.do?fvId=${facturasv.fvId}&fvTipo=${facturasv.fvTipo}"><img src="img/editar.gif"></a>&nbsp;
								<!-- <c:if test="${facturasv.clDatosRelacionados[7] == 1 && (empty facturasv.fvFvRef || facturasv.fvFvRef=='0')}">
									<a  href="#" onclick="doReferencia('${facturasv.fvId}')"><img src="img/ref.png"></a>	
								</c:if>-->
						</display:column>
					</c:if>	
				</display:table>
			</td>
		</tr>
	</table>
</html:form>