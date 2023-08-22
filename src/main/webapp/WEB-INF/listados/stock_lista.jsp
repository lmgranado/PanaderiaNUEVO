<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script>
	
	function doImprimirStock(){
			if( document.getElementById('stFechaDesde').value == '' && document.getElementById('stFechaHasta').value == '' 
				&& document.getElementById('stProdId').value == '' ){
				alert( 'Debe indicar alguna fecha o producto antes de imprimir el informe' );
			}	 
			else if(confirm('Va a imprimir el informe de Stock por Lote, ¿Desea continuar?')){
				document.getElementById('frmListado').action='doInformeStockPorLote.do';
				document.getElementById('frmListado').target="_new";
				document.getElementById('frmListado').submit();
				document.getElementById('frmListado').action='doListadoStock.do';
				document.getElementById('frmListado').target="";
		}
	}
</script>
<html:form styleId="frmListado" method="post"
				action="/doListadoStock.do">
<table style="background-color: white; border: 1px solid #E5E5E5; width: 100%" >
	<tr>
		<td colspan="7" style="font-family: Verdana; font-size: 11px; color: #2DACE4;padding: 5px 0px 0px 5px">
			<img src="img/play.gif"/>&nbsp;Búsqueda<br>
			<hr style="color: #DFDEDE">
		</td>
	</tr>
	<tr>
		<td width="2%"></td>
		
         <td width="15%" style="font-family: Verdana; font-size: 11px; color: #666666">
            	<b>Fecha Desde</b><br>
			<fmt:formatDate value="${FSTOCK.stFechaDesde}" pattern="dd/MM/yyyy" var="stFechaDesde" />    	 	
         	<html:text size="15" style="background-color: #CCCCCC;" readonly="true" styleId="stFechaDesde" property="stFechaDesde" name="stFechaDesde" value="${stFechaDesde}"/>
         	<img style="cursor: pointer;" class="vtop" id="calendario1" title="Seleccione fecha"  alt="Seleccione fecha" src="img/calendario.gif"/>
         	<script type="text/javascript">
                   Calendar.setup({
                       inputField     :    "stFechaDesde"   // id of the input field
                   ,
                       button         :    "calendario1"  // trigger for the calendar (button ID)
                   });
         	</script>
         </td>
         
         <td width="15%" style="font-family: Verdana; font-size: 11px; color: #666666">
            	<b>Fecha Hasta</b><br>
			<fmt:formatDate value="${FSTOCK.stFechaHasta}" pattern="dd/MM/yyyy" var="stFechaHasta" />    	 	
         	<html:text size="15" style="background-color: #CCCCCC;" readonly="true" styleId="stFechaHasta" property="stFechaHasta" name="stFechaHasta" value="${stFechaHasta}"/>
         	<img style="cursor: pointer;" class="vtop" id="calendario2" title="Seleccione fecha"  alt="Seleccione fecha" src="img/calendario.gif"/>
         	<script type="text/javascript">
                   Calendar.setup({
                       inputField     :    "stFechaHasta"   // id of the input field
                   ,
                       button         :    "calendario2"  // trigger for the calendar (button ID)
                   });
         	</script>
         </td>
         
         <td width=15%" style="font-family: Verdana; font-size: 11px; color: #666666">
            <b>Productos</b><br>
			<html:select  style="width: 180px" styleId="stProdId" property="stProdId" value="${FSTOCK.stProdId}">
				  <html:option value="">[Seleccionar]</html:option>
				  <html:options collection="LISTAPRODUCTOS" property="prodId" labelProperty="prodNombre"/>
			</html:select>&nbsp;
		</td>
	            
		<td width="60%" style="font-family: Verdana; font-size: 11px; color: #666666">
		<input id="filtro" name="filtro"  type="hidden" value="">
			<br><a onclick="javascript: doBuscar(this)">
				<img src="img/buscar.gif" title="Aplicar filtro"/>
				</a>
				<a onclick="javascript: doLimpiar(this)">
				<img src="img/borrar.gif" title="Borrar filtro"/>
				</a>
		</td>
	<tr>
		<td width="2%">	&nbsp;</td>
		<td colspan="6" width="10%" style="font-family: Verdana; font-size: 11px; color: #666666">
			<c:if test="${FSTOCK.stStockCero == 'off' or FSTOCK.stStockCero == null}">
				<input type="checkbox" id="stStockCero" class="check" name="stStockCero"/> 
			</c:if>
			<c:if test="${FSTOCK.stStockCero == 'on'}">
				<input type="checkbox" id="stStockCero" class="check" checked="checked" name="stStockCero"/> 
			</c:if>
				Incluir lotes con stock a cero
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
			
				<display:table name="LISTASTOCK" id="stock" export="false"
					cellpadding="0" cellspacing="0" summary="Tabla Listado"
					sort="external" class="" requestURI="/doListadoStock.do"
					defaultsort="1" defaultorder="ascending" pagesize="10">
			
					<display:column class="columna porcen8"
						headerClass="columna porcen8" sortProperty="stProdId"
						style="color: #F76D2D" title="Cod. Articulo" sortable="true">
							<c:out value="${stock.stProdId}"/> 
					</display:column>
					
					<display:column class="columna porcen20"
						headerClass="columna porcen15" sortProperty="prNombre" 
						style="color: #F76D2D" title="Articulo">
							<c:out value="${stock.fcdDatosRelacionados[1]}"/> 
					</display:column>
					
					<display:column class="columna porcen8"
						headerClass="columna porcen15" sortProperty="clNombre" 
						style="color: #F76D2D" title="Lote">
							<c:out value="${stock.fcdDatosRelacionados[2]}"/> 
					</display:column>
					
					<display:column class="columna porcen10"
						headerClass="columna porcen10" sortProperty="stFCaducidad"
						style="color: #F76D2D" title="Fecha Entrada" sortable="true">
							<fmt:parseDate value="${stock.fcDatosRelacionados[2]}" type="DATE" pattern="yyyy-MM-dd" var="formatedDate"/>  
							<fmt:formatDate value="${formatedDate}"  pattern="dd/MM/yyyy" type="DATE"/> 
					</display:column>
					
					<display:column class="columna porcen10"
						headerClass="columna porcen10" sortProperty="stFCaducidad"
						style="color: #F76D2D" title="Fecha Caducidad" sortable="true">
							<fmt:formatDate value="${stock.stFCaducidad}"  pattern="dd/MM/yyyy" /> 
					</display:column>
					
								
					<display:column  class="columna porcen10"
						headerClass="columna porcen10" sortProperty="stEntrada"
						style="color: #F76D2D" title="Entradas" sortable="true">
							<fmt:formatNumber pattern="###,###.###" var="entradas">${stock.stCantidadInicial}</fmt:formatNumber>
							<c:out value="${entradas}"/> 
					</display:column>
					
					<display:column  class="columna porcen10"
						headerClass="columna porcen10" sortProperty="stEntrada"
						style="color: #F76D2D" title="Salidas" sortable="true">
							<fmt:formatNumber pattern="###,###.###" var="salidas">${stock.stSalidas}</fmt:formatNumber>
							<c:out value="${salidas}"/>
					</display:column>
					
					<display:column  class="columna porcen10"
						headerClass="columna porcen10" sortProperty="stRegularizacion"
						style="color: #F76D2D" title="Regulariz." sortable="true">
							<fmt:formatNumber pattern="###,###.###" var="regularizacion">${stock.stRegularizacion}</fmt:formatNumber>
							<c:out value="${regularizacion}"/>
					</display:column>
					
					<display:column  class="columna porcen10"
						headerClass="columna porcen10" sortProperty="stRegularizacion"
						style="color: #F76D2D" title="Stock" sortable="true">
							<fmt:formatNumber pattern="###,###.###" var="stock">${stock.stCantidadFinal}</fmt:formatNumber>
							<c:out value="${stock}"/>
					</display:column>
					
					<td style="font-family: Verdana; font-size: 11px; color: #666666">
						<input id="filtro" name="filtro"  type="hidden" value="">
						<br><a onclick="javascript: doBuscar(this)">
						<img src="img/buscar.gif" title="Aplicar filtro"/>
						</a>
						<a onclick="javascript: doLimpiar(this)">
						<img src="img/borrar.gif" title="Borrar filtro"/>
						</a>
					</td>
		
				</display:table>
			</td>
			
		</tr>
	</table>
</html:form>