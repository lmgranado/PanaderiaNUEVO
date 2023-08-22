<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script>

	function doNuevo(){
		document.getElementById('frmListado').action='doHistoricoInventariosFormulario.do?nuevo=1';
		document.getElementById('frmListado').target="";
		document.getElementById('frmListado').submit();
	}
				
			
	
	function doImprimir(){
		if(hayChecked('frmListado')){
			if(confirm('Va a imprimir los inventarios/regularizaciones seleccionadas,\n\t\t\t\t ¿Desea continuar?')){
				document.getElementById('frmListado').action='doGenerarHistoricoInventarios.do';
				document.getElementById('frmListado').target="_new";
				document.getElementById('frmListado').submit();
				document.getElementById('frmListado').action='doListadoHistoricoInventarios.do';
				document.getElementById('frmListado').target="";
			}
		}else{
			alert('Debe marcar alguna de los registros para realizar la operación solicitada');
		}
	}
</script>
<html:form styleId="frmListado" method="post"
				action="/doListadoHistoricoInventarios.do">
<table style="background-color: white; border: 1px solid #E5E5E5; width: 100%">
	<tr>
		<td colspan="9" style="font-family: Verdana; font-size: 11px; color: #2DACE4;padding: 5px 0px 0px 5px">
			<img src="img/play.gif"/>&nbsp;Búsqueda<br>
			<hr style="color: #DFDEDE">
		</td>
	</tr>
	<tr>
		<td width="2%"></td>
					
       		<td width="12%" style="font-family: Verdana; font-size: 11px; color: #666666">
	           	<b>Fecha Desde</b><br>
					<fmt:formatDate value="${FHISTORICO_INVENTARIOS.hinvFechaDesde}" pattern="dd/MM/yyyy" var="hinvFechaDesde" />    	 	
	        	<html:text style="background-color: #CCCCCC;" size="11" readonly="true" styleId="hinvFechaDesde" property="hinvFechaDesde" name="hinvFechaDesde" value="${hinvFechaDesde}"/>
	        	<img style="cursor: pointer;" class="vtop" id="calendario1" title="Seleccione fecha"  alt="Seleccione fecha" src="img/calendario.gif"/>
	        	<script type="text/javascript">
	                  Calendar.setup({
	                      inputField     :    "hinvFechaDesde"   // id of the input field
	                  ,
	                      button         :    "calendario1"  // trigger for the calendar (button ID)
	                  });
	        	</script>
        	</td>
         
         <td width="12%" style="font-family: Verdana; font-size: 11px; color: #666666";>
            	<b>Fecha Hasta</b><br>
			<fmt:formatDate value="${FHISTORICO_INVENTARIOS.hinvFechaHasta}" pattern="dd/MM/yyyy" var="hinvFechaHasta" />    	 	
         	<html:text style="background-color: #CCCCCC;" size="11" readonly="true" styleId="hinvFechaHasta" property="hinvFechaHasta" name="hinvFechaHasta" value="${hinvFechaHasta}"/>
         	<img style="cursor: pointer;" class="vtop" id="calendario2" title="Seleccione fecha"  alt="Seleccione fecha" src="img/calendario.gif"/>
         	<script type="text/javascript">
                   Calendar.setup({
                       inputField     :    "hinvFechaHasta"   // id of the input field
                   ,
                       button         :    "calendario2"  // trigger for the calendar (button ID)
                   });
         	</script>
         </td>     
         
         <td  width="20%" style="font-family: Verdana; font-size: 11px; color: #666666">
			<b>Usuarios</b><br>
			<html:select style="width: 200px" styleId="hinvUsId" property="hinvUsId" value="${FHISTORICO_INVENTARIOS.hinvUsId}">
			  <html:option value="">[Seleccionar]</html:option>
			  <html:options property="usId" labelProperty="usNombre" collection="LISTAUSUARIOS"/>
		    </html:select>
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
			
			<display:table name="LISTAHISTORICOINVENTARIOS" id="inventario" export="false"
					cellpadding="0" cellspacing="0" summary="Tabla Listado"
					sort="external" class="" requestURI="/doListadoHistoricoInventarios.do"
					defaultsort="1" defaultorder="ascending" pagesize="10">
					
				<display:column class="columna porcen2" headerClass="columna porcen2"
						style="${estilo2009}" alt="selecc/deselec" function="checkUncheck()" src="img/marca_desmarca.gif" input="img">
								<input type="checkbox" id="checkList"
									class="check" name="checkList"
									value="${inventario.hinvId}" />
					</display:column>
			
					<display:column class="columna porcen10"
						headerClass="columna porcen10" sortProperty="hinvFecha"
						style="color: #F76D2D" title="Fecha" sortable="true">
							<fmt:formatDate value="${inventario.hinvFecha}" pattern="dd/MM/yyyy" var="fechaInventario" />
								${fechaInventario}
					</display:column>
					
					<display:column class="columna porcen15"
						headerClass="columna porcen15" sortProperty="clNombre" 
						style="color: #F76D2D ${estilo}" title="Nombre del Usuario">
							${inventario.hinvDatosRelacionados[0]}
					</display:column>
			
					
				</display:table>
			</td>
		</tr>
	</table>
</html:form>