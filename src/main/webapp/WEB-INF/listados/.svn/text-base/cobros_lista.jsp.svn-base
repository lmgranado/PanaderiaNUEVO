<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script>
	
	function doActualizaFactura(){
		if(hayChecked('frmListado')){
			if(confirm('Va a actualizar las facturas seleccionadas, ¿Desea continuar?')){
				document.getElementById('frmListado').action='doFacturasVentaActualizarEstado.do';
				document.getElementById('frmListado').target = "";
				document.getElementById('frmListado').submit();
			}
		}else{
			alert('Debe marcar algúna factura para actualizar su estado');
		}
	}
	
	function doImprimirFactura(){
		if(hayChecked('frmListado')){
			if(confirm('Va a imprimir las facturas seleccionadas, ¿Desea continuar?')){
				document.getElementById('frmListado').action='doGenerarFacturasVenta.do';
				document.getElementById('frmListado').target="_new";
				document.getElementById('frmListado').submit();
				document.getElementById('frmListado').action='doListadoCobros.do';
				document.getElementById('frmListado').target="";
			}
		}else{
			alert('Debe marcar alguna de las facturas para realizar la operación solicitada');
		}
	}
	
	function doGenerarParteDiario(){
		if(( document.getElementById('fvFechaDesde').value != "" && document.getElementById('fvFechaHasta').value != "" )
			 || document.getElementById('fvFechaPago').value != ""){
			if(confirm('Va a generar el informe de cobros, ¿Desea continuar?')){
				document.getElementById('frmListado').action='doGenerarParteDiario.do';
				document.getElementById('frmListado').target="_new";
				document.getElementById('frmListado').submit();
				document.getElementById('frmListado').action='doListadoCobros.do';
				document.getElementById('frmListado').target="";
			}
		}	
		else{
			alert( 'Debe elegir fecha desde y fecha hasta, o fecha de pago, para generar el informe' );			
		}
	
	}
</script>
<html:form styleId="frmListado" method="post"
				action="/doListadoCobros.do">
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
		<td width="10%" style="font-family: Verdana; font-size: 11px; color: #666666; font-weight: bold;">
			<b>Número Factura</b><br>
			<input id="fvNumeroFactura" size="18" name="fvNumeroFactura" type="text" value="${FFACTURAS_VENTA.fvNumeroFactura}">
		</td>
		
		<td  width="5%" style="font-family: Verdana; font-size: 11px; color: #666666">
			<b>Tipo</b><br>
			<html:select style="width: 100px" styleId="fvIva" property="fvIva" value="${FFACTURAS_VENTA.fvIva}">
			  <html:option value="">[Selec]</html:option>
			  <html:option value="A">A</html:option>
			  <html:option value="B">B</html:option>
		    </html:select>
		</td>
		
		<td  width="20%" style="font-family: Verdana; font-size: 11px; color: #666666;">
			<b>Clientes</b><br>
			<html:select style="width: 200px" styleId="fvClId" property="fvClId" value="${FFACTURAS_VENTA.fvClId}">
			  <html:option value="">[Seleccionar]</html:option>
			  <html:options property="clId" labelProperty="clNombreComercial" collection="LISTACLIENTES"/>
		    </html:select>
		</td>
		
		<td  width="20%" style="font-family: Verdana; font-size: 11px; color: #666666">
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
		
		<td width="5%" style="font-family: Verdana; font-size: 11px; color: #666666">
			<b>Tipo Facturación</b><br>
			<html:select  style="width: 110px" styleId="clPfId" property="clPfId" value="${FFACTURAS_VENTA.clPfId}">
				  <html:option value="">[Seleccionar]</html:option>
				  <html:options property="pfId" labelProperty="pfDescripcion" collection="LISTAPERIODOS"/>
			</html:select>
		</td>
	</tr>
	<tr>	
		<td width="2%"></td>

         <td width="12%" style="font-family: Verdana; font-size: 11px; color: #666666">
            	<b>Fecha Desde</b><br>
			<fmt:formatDate value="${FFACTURAS_VENTA.fvFechaDesde}" pattern="dd/MM/yyyy" var="fvFechaDesde" />    	 	
         	<html:text style="background-color: #CCCCCC;" size="12" readonly="true" styleId="fvFechaDesde" property="fvFechaDesde" name="fvFechaDesde" value="${fvFechaDesde}"/>
         	<img style="cursor: pointer;" class="vtop" id="calendario1" title="Seleccione fecha"  alt="Seleccione fecha" src="img/calendario.gif"/>
         	<script type="text/javascript">
                   Calendar.setup({
                       inputField     :    "fvFechaDesde"   // id of the input field
                   ,
                       button         :    "calendario1"  // trigger for the calendar (button ID)
                   });
         	</script>
         </td>
         
         <td width="12%" style="font-family: Verdana; font-size: 11px; color: #666666">
            	<b>Fecha Hasta</b><br>
			<fmt:formatDate value="${FFACTURAS_VENTA.fvFechaHasta}" pattern="dd/MM/yyyy" var="fvFechaHasta" />    	 	
         	<html:text style="background-color: #CCCCCC;" size="12" readonly="true" styleId="fvFechaHasta" property="fvFechaHasta" name="fvFechaHasta" value="${fvFechaHasta}"/>
         	<img style="cursor: pointer;" class="vtop" id="calendario2" title="Seleccione fecha"  alt="Seleccione fecha" src="img/calendario.gif"/>
         	<script type="text/javascript">
                   Calendar.setup({
                       inputField     :    "fvFechaHasta"   // id of the input field
                   ,
                       button         :    "calendario2"  // trigger for the calendar (button ID)
                   });
         	</script>
         </td>
	            
	    <td width="12%" style="font-family: Verdana; font-size: 11px; color: #666666">
            	<b>Fecha de Pago</b><br>
			<fmt:formatDate value="${FFACTURAS_VENTA.fvFechaPago}" pattern="dd/MM/yyyy" var="fvFechaPago" />    	 	
         	<html:text style="background-color: #CCCCCC;" size="12" readonly="true" styleId="fvFechaPago" property="fvFechaPago" name="fvFechaPago" value="${fvFechaPago}"/>
         	<img style="cursor: pointer;" class="vtop" id="calendario3" title="Seleccione fecha"  alt="Seleccione fecha" src="img/calendario.gif"/>
         	<script type="text/javascript">
                   Calendar.setup({
                       inputField     :    "fvFechaPago"   // id of the input field
                   ,
                       button         :    "calendario3"  // trigger for the calendar (button ID)
                   });
         	</script>
         </td>
                 
		<td width="15%" style="font-family: Verdana; font-size: 11px; color: #666666">
		<input id="filtro" name="filtro"  type="hidden" value="">
			<br><a onclick="javascript: doBuscar(this)">
				<img src="img/buscar.gif" title="Aplicar filtro"/>
				</a>
				<a onclick="javascript: doLimpiar(this)">
				<img src="img/borrar.gif" title="Borrar filtro"/>
				</a>
		</td>
	</tr>
	<tr>
		<td style="width: 50%;" colspan="5"></td>
		<td colspan="4" style="width: 50%;">
			<fmt:formatNumber pattern="###,###.###" var="totalFacturasLista">${totalFacturasLista}</fmt:formatNumber>
			<table cellpadding="1" class="displaytag_tabla" cellspacing="1">
				<thead>
					<tr>
						<td colspan="1" class="columna sortable" style="text-align: center;color: orange">	
						<img style="cursor: pointer;" class="vtop"  title="importe Pendiente"  alt="importe Pendiente" src="img/pendiente.png"/>
							<span style="color: #2DACE4;font-weight: bold;">Total&nbsp;Facturas&nbsp;Pendientes(&euro;)</span>
						</td>
					</tr>
				</thead>
				<tr>
					<td colspan="1" class="columna" style="text-align: right;font-weight: bold;text-align: center;">
						<c:out value="${totalFacturasLista}"/> &euro;
					</td>
				</tr>		
			</table>
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
					sort="external" class="" requestURI="/doListadoCobros.do"
					defaultsort="1" defaultorder="ascending" pagesize="10">
			
					<display:column class="columna porcen2" headerClass="columna porcen2"
						style="${estilo2009}" alt="selecc/deselec" function="checkUncheck()" src="img/marca_desmarca.gif" input="img">
							<input type="checkbox" id="checkList" name="checkList" class="check"
								value="${facturasv.fvId}" />
					</display:column>
			
					<display:column class="columna porcen5"
						headerClass="columna porcen5" sortProperty="fvNumeroFactura"
						style="color: #F76D2D" title="NºFact." sortable="true">
							${facturasv.fvNumeroFactura}
					</display:column>
					
					<display:column class="columna porcen5"
						headerClass="columna porcen5" sortProperty="fvIva"
						style="color: #F76D2D" title="Tipo" sortable="true">
							${facturasv.fvIva}
					</display:column>
					
					<display:column class="columna porcen15"
						headerClass="columna porcen15" sortProperty="clNombre" 
						style="color: #F76D2D" title="Nombre del Cliente">
							${facturasv.clDatosRelacionados[0]}&nbsp;${facturasv.clDatosRelacionados[1]}
					</display:column>
					
					<display:column class="columna porcen10"
						headerClass="columna porcen10" sortProperty="clNombre" 
						style="color: #F76D2D" title="Nombre Comercial">
							${facturasv.clDatosRelacionados[6]}
					</display:column>
					
					<display:column class="columna porcen5"
						headerClass="columna porcen5" sortProperty="fvFecha"
						style="color: #F76D2D" title="Fecha" sortable="true">
							<fmt:formatDate value="${facturasv.fvFecha}"  pattern="dd/MM/yyyy" /> 
					</display:column>
					
					<%-- <display:column class="columna porcen5"
						headerClass="columna porcen5" sortProperty="fvtotal"
						style="color: #F76D2D" title="Iva">
							${facturasv.fvIva}%
					</display:column> --%>
								
					<display:column  class="columna porcen8"
						headerClass="columna porcen8" sortProperty="fvTotal"
						style="color: #F76D2D" title="Importe (&euro;)" sortable="true">
							<fmt:formatNumber pattern="###,###.###" var="total">${facturasv.fvTotal}</fmt:formatNumber>
							${total} &euro;
					</display:column>
					
					<display:column  class="columna porcen8"
						headerClass="columna porcen8" sortProperty="fvImportePendiente"
						style="color: #F76D2D" title="Pendiente (&euro;)" sortable="true">
							<fmt:formatNumber pattern="###,###.###" var="pendiente">${facturasv.fvImportePendiente}</fmt:formatNumber>
							${pendiente} &euro;
					</display:column>
					
					<display:column class="columna porcen5"
						headerClass="columna porcen5" sortProperty="fvFechaPago"
						style="color: #F76D2D" title="Fecha Pago" sortable="true">
						<c:if test="${!empty(facturasv.fvFechaPago)}">
							<fmt:formatDate value="${facturasv.fvFechaPago}"  pattern="dd/MM/yyyy" var="fechaPago"/>
							${fechaPago}
						</c:if>
						<c:if test="${empty(facturasv.fvFechaPago)}">
								&nbsp;
						</c:if>
							 
					</display:column>
													
					<display:column class="columna porcen5"
						headerClass="columna porcen5" sortProperty="fvPagada"
						style="color: #F76D2D" title="Estado" sortable="true">
						<c:choose>	
							<c:when test="${facturasv.fvPagada == 1}">
								<c:out value="PAGADA"/>
							</c:when>								
							<c:otherwise>
								<c:out value="NO PAGADA"/>
							</c:otherwise>
						</c:choose>
					</display:column>			
				</display:table>
			</td>
		</tr>
	</table>
<input id="listadoCobros" name="listadoCobros"  type="hidden" value="listadoCobros">
</html:form>