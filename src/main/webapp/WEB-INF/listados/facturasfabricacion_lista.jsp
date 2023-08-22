<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script>

	function doNuevo(){
		document.getElementById('frmListado').action='doFacturasFabricacionFormulario.do?nuevo=1';
		document.getElementById('frmListado').submit();
	}
	

	function doBorrar(){
		if(hayMasDeUnChecked('frmListado')){
			if(confirm('Va a eliminar las fabricaciones seleccionadas, ¿Desea continuar?')){
				document.getElementById('frmListado').action='doFacturasFabricacionBorrar.do';
				document.getElementById('frmListado').submit();
			}
		}
	}
	
	
	function doImprimirFabricacion(){
		if(hayChecked('frmListado')){
			if(confirm('Va a imprimir las fabricaciones seleccionadas, ¿Desea continuar?')){
				document.getElementById('frmListado').action='doGenerarFabricacion.do';
				document.getElementById('frmListado').target="_new";
				document.getElementById('frmListado').submit();
				document.getElementById('frmListado').action='doListadoFacturasCompra.do';
				document.getElementById('frmListado').target="";
			}
		}else{
			alert('Debe marcar alguna de las facturas para realizar la operación solicitada');
		}
	}
	
	function doImprimirEtiquetas(){
		if(hayChecked('frmListado')){
			if(confirm('Va a imprimir las etiquetas de las fabricaciones seleccionadas, ¿Desea continuar?')){
				document.getElementById('frmListado').action='doGenerarEtiquetas.do';
				document.getElementById('frmListado').target="_new";
				document.getElementById('frmListado').submit();
				document.getElementById('frmListado').action='doListadoFacturasCompra.do';
				document.getElementById('frmListado').target="";
			}
		}else{
			alert('Debe marcar alguna de las fabricaciones para realizar la operación solicitada');
		}
	}
	
</script>
<html:form styleId="frmListado" method="post"
				action="/doListadoFacturasFabricacion.do">
<table style="background-color: white; border: 1px solid #E5E5E5; width: 100%">
	<tr>
		<td colspan="7" style="font-family: Verdana; font-size: 11px; color: #2DACE4;padding: 5px 0px 0px 5px">
			<img src="img/play.gif"/>&nbsp;Búsqueda<br>
			<hr style="color: #DFDEDE">
		</td>
	</tr>
	<tr>
		<td width="2%"></td>
		<!-- <td width="15%" style="font-family: Verdana; font-size: 11px; color: #666666">
			<b>Número Fabricación</b><br>
			<input id="fcId" size="20" name="fcId" type="text" value="${FFACTURAS_COMPRA.fcId}">
		</td>
		
		<td style="font-family: Verdana; font-size: 11px; color: #666666">
			Nombre del Cliente<br>
			<input id="clNombre" size="40" name="clNombre" type="text" value="${FFACTURAS_COMPRA.prDatosRelacionados[0]}">
		</td> 
		<td  width="15%" style="font-family: Verdana; font-size: 11px; color: #666666">
			<b>Proveedores</b><br>
			<html:select style="width: 200px" styleId="fcPrId" property="fcPrId" value="${FFACTURAS_COMPRA.fcPrId}">
			  <html:option value="">[Seleccionar]</html:option>
			  <html:options property="prId" labelProperty="prNombreComercial" collection="LISTAPROVEEDORES"/>
		    </html:select>
		</td> -->
         
         <td width="15%" style="font-family: Verdana; font-size: 11px; color: #666666">
            	<b>Fecha Desde</b><br>
			<fmt:formatDate value="${FFACTURAS_COMPRA.fcFechaDesde}" pattern="dd/MM/yyyy" var="fcFechaDesde" />    	 	
         	<html:text size="15" style="background-color: #CCCCCC;" readonly="true" styleId="fcFechaDesde" property="fcFechaDesde" name="fcFechaDesde" value="${fcFechaDesde}"/>
         	<img style="cursor: pointer;" class="vtop" id="calendario1" title="Seleccione fecha"  alt="Seleccione fecha" src="img/calendario.gif"/>
         	<script type="text/javascript">
                   Calendar.setup({
                       inputField     :    "fcFechaDesde"   // id of the input field
                   ,
                       button         :    "calendario1"  // trigger for the calendar (button ID)
                   });
         	</script>
         </td>
         
         <td width="15%" style="font-family: Verdana; font-size: 11px; color: #666666">
            	<b>Fecha Hasta</b><br>
			<fmt:formatDate value="${FFACTURAS_COMPRA.fcFechaHasta}" pattern="dd/MM/yyyy" var="fcFechaHasta" />    	 	
         	<html:text size="15" style="background-color: #CCCCCC;" readonly="true" styleId="fcFechaHasta" property="fcFechaHasta" name="fcFechaHasta" value="${fcFechaHasta}"/>
         	<img style="cursor: pointer;" class="vtop" id="calendario2" title="Seleccione fecha"  alt="Seleccione fecha" src="img/calendario.gif"/>
         	<script type="text/javascript">
                   Calendar.setup({
                       inputField     :    "fcFechaHasta"   // id of the input field
                   ,
                       button         :    "calendario2"  // trigger for the calendar (button ID)
                   });
         	</script>
         </td>
	            
		<td width="70%" style="font-family: Verdana; font-size: 11px; color: #666666">
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
		<td colspan="7" style="">
			&nbsp;
		</td>
	</tr>
	<tr>
		<td colspan="7" style="font-family: Verdana; font-size: 11px; color: #2DACE4;padding: 5px 0px 0px 5px">
			<img src="img/play.gif"/>&nbsp;Resultado<br>
			<hr style="color: #DFDEDE">
		</td>
	</tr>
	<tr>
		<td colspan="7">
			
				<display:table name="LISTAFACTURASCOMPRA" id="facturasc" export="false"
					cellpadding="0" cellspacing="0" summary="Tabla Listado"
					sort="external" class="" requestURI="/doListadoFacturasFabricacion.do"
					defaultsort="1" defaultorder="ascending" pagesize="10">
			
					<display:column class="columna porcen2" headerClass="columna porcen2"
						style="${estilo2009}" alt="selecc/deselec" function="checkUncheck()" src="img/marca_desmarca.gif" input="img">
							<input type="checkbox" id="checkList" name="checkList" class="check"
								value="${facturasc.fcId}" />
					</display:column>
			
					<display:column class="columna porcen8"
						headerClass="columna porcen8" sortProperty="fcId"
						style="color: #F76D2D" title="Nº Fabricación" sortable="true">
							${facturasc.fcNumeroFactura}
					</display:column>
					
					<display:column class="columna porcen15"
						headerClass="columna porcen15" sortProperty="prNombre" 
						style="color: #F76D2D" title="Proveedor">
							${facturasc.prDatosRelacionados[0]}
					</display:column>
					
					<%-- <display:column class="columna porcen15"
						headerClass="columna porcen15" sortProperty="clNombre" 
						style="color: #F76D2D" title="Nombre Comercial">
							${facturasc.prDatosRelacionados[6]}
					</display:column>
					--%>
					<display:column class="columna porcen10"
						headerClass="columna porcen10" sortProperty="fcFecha"
						style="color: #F76D2D" title="Fecha" sortable="true">
							<fmt:formatDate value="${facturasc.fcFecha}"  pattern="dd/MM/yyyy" /> 
					</display:column>

					<display:column class="columna porcen2" headerClass="columna porcen2"
						style="${estilo2009}" title="Ver">
					<a href="doFacturasFabricacionFormulario.do?fcId=${facturasc.fcId}">
								<img src="img/editar.gif">
							</a>
					</display:column>
				</display:table>
			</td>
		</tr>
	</table>
</html:form>