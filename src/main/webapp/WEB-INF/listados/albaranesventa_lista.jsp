<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script>

	function doBuscarPropio(elemento) {
		var form = document.getElementById('frmListado');
		if(document.getElementById('avFechaHasta').value=='' || document.getElementById('avFechaDesde').value==''){
			alert('Debe indicar un filtro de fechas para realizar la búsqueda');
			return;
		}
		if(!compare_dates(document.getElementById('avFechaHasta').value, document.getElementById('avFechaDesde').value)){
			alert('La fecha Hasta no puede ser inferior a la fecha Desde');
			return;
		}
		if(diferencia(document.getElementById('avFechaDesde').value, document.getElementById('avFechaHasta').value)>370){
			alert('La diferencia entre las fechas no puede ser superior a un año');
			return;
		}
		document.getElementById('filtro').value = '1';
		form.target = "";
		form.submit();
	}

	function doNuevo(){
		document.getElementById('frmListado').action='doAlbaranesVentaFormulario.do?nuevo=1';
		document.getElementById('frmListado').target="";
		document.getElementById('frmListado').submit();
	}
	
	function doBorrar(){
		if(hayChecked('frmListado')){
			if(confirm('Va a eliminar los Albaranes seleccionadas, ¿Desea continuar?')){
				document.getElementById('frmListado').action='doAlbaranesVentaBorrar.do';
				document.getElementById('frmListado').target="";
				document.getElementById('frmListado').submit();
			}
		}else{
			alert('Debe marcar algún Albarán para eliminar');
		}
	}
	
	function doIncidencias(){
		//Primero verificamos si hay algun checkbox, si no hay no se lo permito
		if(existeCheckbox('frmListado')){
			document.getElementById('frmListado').action='doAlbaranesVentaFormulario.do?avId=1&banderaIncidenciasAlbaranes=1';
			document.getElementById('frmListado').submit();
		}else{
			alert('El listado no contiene ninguna nota a la que poner incidencias');
		}
	}
	
	function doActualizaAlbaran(){
		if(hayChecked('frmListado')){
			if(confirm('Va a actualizar los Albaranes seleccionados, ¿Desea continuar?')){
				document.getElementById('frmListado').action='doAlbaranesVentaActualizarEstado.do';
				document.getElementById('frmListado').target="";
				document.getElementById('frmListado').submit();
				document.getElementById('frmListado').action='doListadoAlbaranesVenta.do';
				document.getElementById('frmListado').target="";
			}
		}else{
			alert('Debe marcar algún Albarán para actualizar su estado');
		}
	}
	
	function doImprimirAlbaran(){
		if(hayCheckedImpresion('frmListado')){
			if(confirm('Va a imprimir los Albaranes seleccionados, ¿Desea continuar?')){
				document.getElementById('frmListado').action='doGenerarAlbaranesVenta.do';
				document.getElementById('frmListado').target="_new";
				document.getElementById('frmListado').submit();
				document.getElementById('frmListado').action='doListadoAlbaranesVenta.do';
				document.getElementById('frmListado').target="";
			}
		}else{
			alert('Debe marcar alguno de los Albaranes para realizar la operación solicitada');
		}
	}
			
	function doFactura(){
		if(hayChecked('frmListado')){
			var condicion = confirm( '¿Desea pasar a Factura los albaranes seleccionados?. Le recordamos que solo podrá pasar a Factura los Albaranes de clientes que tengan Periodo de Facturación diario.' );
			if( condicion ){
				document.getElementById('frmListado').action='doFacturasVentaCrear.do';
				document.getElementById('frmListado').submit();
			}	
		}else{
			alert('Debe selecionar alguna factura de entrega');
		}
	}
	
</script>
<html:form styleId="frmListado" method="post"
				action="/doListadoAlbaranesVenta.do">
<table style="background-color: white; border: 1px solid #E5E5E5; width: 100%">
	<tr>
		<td colspan="8" style="font-family: Verdana; font-size: 11px; color: #2DACE4;padding: 5px 0px 0px 5px">
			<img src="img/play.gif"/>&nbsp;Búsqueda<br>
			<hr style="color: #DFDEDE">
		</td>
	</tr>
	<tr>
		<td width="2%"></td>
		<td width="10%" style="font-family: Verdana; font-size: 11px; color: #666666">
			<b>Nº Albarán</b><br>
			<input id="avNumero" size="10" name="avNumero" type="text" value="${FALBARANES_VENTA.avNumero}">
		</td>
		
		<!-- <td style="font-family: Verdana; font-size: 11px; color: #666666">
			Nombre del Cliente<br>
			<input id="clNombre" size="40" name="clNombre" type="text" value="${FALBARANES_VENTA.clDatosRelacionados[0]}">
		</td> -->
		
		<td  width="20%" style="font-family: Verdana; font-size: 11px; color: #666666">
			<b>Clientes</b><br>
			<html:select style="width: 200px" styleId="avClId" property="avClId" value="${FALBARANES_VENTA.avClId}">
			  <html:option value="">[Seleccionar]</html:option>
			  <html:options property="clId" labelProperty="clNombreComercial" collection="LISTACLIENTES"/>
		    </html:select>
		</td>
		
		<!-- <td  width="15%" style="font-family: Verdana; font-size: 11px; color: #666666">
			<b>Estado</b><br>
			<html:select style="width: 100px" styleId="avPagado" property="avPagado" value="${FALBARANES_VENTA.avPagado}">
			  <html:option value="">[Seleccionar]</html:option>
			  <html:option value="1">Pagado</html:option>
			  <html:option value="0">No Pagado</html:option>
		    </html:select>
		</td> -->
		
		<td  width="15%" style="font-family: Verdana; font-size: 11px; color: #666666">
			<b>Tipo Facturación</b><br>
			<html:select  style="width: 110px" styleId="clPfId" property="clPfId" value="${FALBARANES_VENTA.clPfId}">
				  <html:option value="">[Seleccionar]</html:option>
				  <html:options property="pfId" labelProperty="pfDescripcion" collection="LISTAPERIODOS"/>
			</html:select>
		</td>
		
		<td  width="15%" style="font-family: Verdana; font-size: 11px; color: #666666">
			<b>Tipo Albarán</b><br>
			<html:select style="width: 110px" styleId="avTipo" property="avTipo" value="${FALBARANES_VENTA.avTipo}">
			  <html:option value="">[Seleccionar]</html:option>
			  <html:option value="0">Normal</html:option>
			  <html:option value="1">Directo</html:option>
		    </html:select>
		</td>
     
         <td width="15%" style="font-family: Verdana; font-size: 11px; color: #666666">
            	<b>Fecha Desde</b><br>
			<fmt:formatDate value="${FALBARANES_VENTA.avFechaDesde}" pattern="dd/MM/yyyy" var="avFechaDesde" />    	 	
         	<html:text style="background-color: #CCCCCC;" size="12" readonly="true" styleId="avFechaDesde" property="avFechaDesde" name="avFechaDesde" value="${avFechaDesde}"/>
         	<img style="cursor: pointer;" class="vtop" id="calendario1" title="Seleccione fecha"  alt="Seleccione fecha" src="img/calendario.gif"/>
         	<script type="text/javascript">
                   Calendar.setup({
                       inputField     :    "avFechaDesde"   // id of the input field
                   ,
                       button         :    "calendario1"  // trigger for the calendar (button ID)
                   });
         	</script>
         </td>
         
         <td width="15%" style="font-family: Verdana; font-size: 11px; color: #666666">
            	<b>Fecha Hasta</b><br>
			<fmt:formatDate value="${FALBARANES_VENTA.avFechaHasta}" pattern="dd/MM/yyyy" var="avFechaHasta" />    	 	
         	<html:text style="background-color: #CCCCCC;" size="12" readonly="true" styleId="avFechaHasta" property="avFechaHasta" name="avFechaHasta" value="${avFechaHasta}"/>
         	<img style="cursor: pointer;" class="vtop" id="calendario2" title="Seleccione fecha"  alt="Seleccione fecha" src="img/calendario.gif"/>
         	<script type="text/javascript">
                   Calendar.setup({
                       inputField     :    "avFechaHasta"   // id of the input field
                   ,
                       button         :    "calendario2"  // trigger for the calendar (button ID)
                   });
         	</script>
         </td>
	            
		<td width="40%" style="font-family: Verdana; font-size: 11px; color: #666666">
		<input id="filtro" name="filtro"  type="hidden" value="">
			<br><a onclick="javascript: doBuscarPropio(this)">
				<img src="img/buscar.gif" title="Aplicar filtro"/>
				</a>
				<a onclick="javascript: doLimpiar(this)">
				<img src="img/borrar.gif" title="Borrar filtro"/>
				</a>
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
			
				<display:table name="LISTAALBARANESVENTA" id="albaranesv" export="false"
					cellpadding="0" cellspacing="0" summary="Tabla Listado"
					sort="external" class="" requestURI="/doListadoAlbaranesVenta.do"
					defaultsort="1" defaultorder="ascending" pagesize="10">
					
					<c:choose>
						<c:when test="${albaranesv.avTipo=='1'}">
							<c:set var="estilo" value=";background-color: #FEFCB8"/>
						</c:when>
						<c:otherwise>
							<c:set var="estilo" value=""/>
						</c:otherwise>
					</c:choose>
			
					<display:column class="columna porcen2" headerClass="columna porcen2"
						style=" ${estilo2009} ${estilo}" alt="selecc/deselec" function="checkUncheck()" src="img/marca_desmarca.gif" input="img">
							<c:if test="${albaranesv.avCierre!='1'}">
								<input type="checkbox" id="checkList" name="checkList" class="check"
								value="${albaranesv.avId}" />
							</c:if>
							<c:if test="${albaranesv.avCierre=='1'}">
								&nbsp;
							</c:if>
							
					</display:column>
			
					<display:column class="columna porcen5"
						headerClass="columna porcen5" sortProperty="avNumero"
						style="color: #F76D2D ${estilo}" title="Nº Albarán" sortable="true">
							${albaranesv.avNumero}
					</display:column>
					
					<display:column class="columna porcen10"
						headerClass="columna porcen10" sortProperty="clNombre" 
						style="color: #F76D2D ${estilo}" title="Cliente">
							${albaranesv.clDatosRelacionados[0]}&nbsp;${albaranesv.clDatosRelacionados[1]}
					</display:column>
					
					<display:column class="columna porcen10"
						headerClass="columna porcen10" sortProperty="clNombre" 
						style="color: #F76D2D ${estilo}" title="Nombre Comercial">
							${albaranesv.clDatosRelacionados[6]}
					</display:column>
					
					<display:column class="columna porcen5"
						headerClass="columna porcen5" sortProperty="avFecha"
						style="color: #F76D2D ${estilo}" title="Fecha" sortable="true">
							<fmt:formatDate value="${albaranesv.avFecha}"  pattern="dd/MM/yyyy" /> 
					</display:column>
					
					<%-- <display:column class="columna porcen5"
						headerClass="columna porcen5" sortProperty="avtotal"
						style="color: #F76D2D" title="Iva">
							${albaranesv.avIva}%
					</display:column> --%>
								
					<display:column  class="columna porcen5"
						headerClass="columna porcen5" sortProperty="avTotal"
						style="color: #F76D2D ${estilo}" title="Importe total (&euro;)" sortable="true">
							<fmt:formatNumber pattern="###,###.###" var="total">${albaranesv.avTotal}</fmt:formatNumber>
							${total} &euro;
					</display:column>
					
					<%-- <display:column class="columna porcen5"
						headerClass="columna porcen5" sortProperty="avPagado"
						style="color: #F76D2D" title="Pagado" sortable="true">
						<c:choose>	
							<c:when test="${albaranesv.avPagado == 1}">
								<c:out value="PAGADO"/>
							</c:when>								
							<c:otherwise>
								<c:out value="NO PAGADO"/>
							</c:otherwise>
						</c:choose>
					</display:column>
					--%> 
					<display:column class="columna porcen10"
						headerClass="columna porcen10" sortProperty="avCierre"
						style="color: #F76D2D ${estilo}" title="Estado" sortable="true">
							<c:if test="${albaranesv.avCierre=='1'}">
								CERRADA
							</c:if>
							<c:if test="${albaranesv.avCierre!='1'}">
								ABIERTA
							</c:if>
					</display:column>
					
					<display:column class="columna porcen5" headerClass="columna porcen2"
						style=" ${estilo2009} ${estilo}" title="Ver">
							<a href="doAlbaranesVentaFormulario.do?avId=${albaranesv.avId}">
								<img src="img/editar.gif">
							</a>
							<a target="_blank" href="doGenerarAlbaranesVenta.do?avId=${albaranesv.avId}">
								<img src="img/cuadrante.gif">
							</a>
					</display:column>
					
					<display:column class="columna porcen2" headerClass="columna porcen2"
						style=" ${estilo2009} ${estilo}" alt="selecc/deselec" function="checkUncheckImpresion()" src="img/marca_desmarca.gif" input="img">
								<input type="checkbox" id="checkListImpresion" name="checkListImpresion" class="check"
								value="${albaranesv.avId}" />
							
					</display:column>
				</display:table>
			</td>
		</tr>
	</table>
</html:form>