<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script>

	function doAñadir(){
		if( compararFecha(document.getElementById("vacFechaDesde").value, document.getElementById("vacFechaHasta").value) ){
			document.getElementById('frmListado').action='doVacacionesClientesGrabar.do';
			document.getElementById('frmListado').target="";
			document.getElementById('frmListado').submit();
		}
		else{
			alert("La fecha inicial tiene que ser menor que la fecha final")
		}
	}
	
	function doBorrar(){
		if(hayChecked('frmListado')){
			if(confirm('Va a eliminar los periodos vacacionales seleccionadas, ¿Desea continuar?')){
				document.getElementById('frmListado').action='doVacacionesClientesGrabar.do?delete=1';
				document.getElementById('frmListado').target="";
				document.getElementById('frmListado').submit();
			}
		}else{
			alert('Debe marcar algú periodo vacacional para eliminar');
		}
	}
	
</script>
<html:form styleId="frmListado" method="post" action="/doListadoVacacionesClientes.do">
<table style="background-color: white; border: 1px solid #E5E5E5; width: 100%">
    <tr>          
	  <td colspan="6" align="right" width="100%">
	  	<span style='color: #FA7D3D; font-size: 12px; font-weight: bold;'>
	  		<c:if test="${!empty Cliente.clId}">
				Cliente: ${Cliente.clNombre} ${Cliente.clApellidos}
			</c:if>	
	  		<html:hidden property="vacClId" styleId="vacClId" name="vacClId" value="${vacClId}"/>
		</span>
	  </td>
    </tr>
	<tr>
		<td colspan="6" style="font-family: Verdana; font-size: 11px; color: #2DACE4;padding: 5px 0px 0px 5px">
			<img src="img/play.gif"/>&nbsp;Insertar rango de vacaciones<br>
			<hr style="color: #DFDEDE">
		</td>
	</tr>
	
	<tr>	
		<td width="2%"></td>
		
		<td width="15%" style="font-family: Verdana; font-size: 11px; color: #666666">
            	<b>Fecha Desde</b><br>
			<fmt:formatDate value="${FVACACIONESCLIENTES.vacFechaDesde}" pattern="dd/MM/yyyy" var="vacFechaDesde" />    	 	
         	<html:text style="background-color: #CCCCCC;" size="20" readonly="true" styleId="vacFechaDesde" property="vacFechaDesde" name="vacFechaDesde" value="${vacFechaDesde}"/>
         	<img style="cursor: pointer;" class="vtop" id="calendario2" title="Seleccione fecha"  alt="Seleccione fecha" src="img/calendario.gif"/>
         	<script type="text/javascript">
                   Calendar.setup({
                       inputField     :    "vacFechaDesde"   // id of the input field
                   ,
                       button         :    "calendario2"  // trigger for the calendar (button ID)
                   });
         	</script>
         </td>
         
         <td width="15%" style="font-family: Verdana; font-size: 11px; color: #666666">
            	<b>Fecha Hasta</b><br>
			<fmt:formatDate value="${FVACACIONESCLIENTES.vacFechaHasta}" pattern="dd/MM/yyyy" var="vacFechaHasta" />    	 	
         	<html:text style="background-color: #CCCCCC;" size="20" readonly="true" styleId="vacFechaHasta" property="vacFechaHasta" name="vacFechaHasta" value="${vacFechaHasta}"/>
         	<img style="cursor: pointer;" class="vtop" id="calendario3" title="Seleccione fecha"  alt="Seleccione fecha" src="img/calendario.gif"/>
         	<script type="text/javascript">
                   Calendar.setup({
                       inputField     :    "vacFechaHasta"   // id of the input field
                   ,
                       button         :    "calendario3"  // trigger for the calendar (button ID)
                   });
         	</script>
         </td>
         
         <td style="font-family: Verdana; font-size: 11px; color: #666666; width: 10%">
			<br><a onclick="javascript: doAñadir(this)">
				<img src="img/add.png" title="Añadir Cantidad"/>
				</a>
		</td>
    </tr>
	<tr>
		<td colspan="6" style="">
			&nbsp;
		</td>
	</tr>
	<tr>
		<td colspan="6" style="font-family: Verdana; font-size: 11px; color: #2DACE4;padding: 5px 0px 0px 5px">
			<img src="img/play.gif"/>&nbsp;Resultado<br>
			<hr style="color: #DFDEDE">
		</td>
	</tr>
	<tr>
		<td colspan="6">
			
				<display:table name="LISTAVACACIONESCLIENTES" id="vacaciones" export="false"
					cellpadding="0" cellspacing="0" summary="Tabla Listado"
					sort="external" class="" requestURI="/doListadoVacacionesClientes.do"
					defaultsort="1" defaultorder="ascending" pagesize="10">
					
					<c:choose>
						<c:when test="${vacaciones.vacDisfrutadas=='1'}">
							<c:set var="estilo" value=";background-color: #FEE2FC"/>
						</c:when>	
						<c:otherwise>
							<c:set var="estilo" value=""/>
						</c:otherwise>
					</c:choose>
			
					<display:column class="columna porcen2" headerClass="columna porcen2"
						style="color: #F76D2D ${estilo}" alt="selecc/deselec" function="checkUncheck()" src="img/marca_desmarca.gif" input="img">
							<input type="checkbox" id="checkList" name="checkList" class="check" value="${vacaciones.vacId}" />
					</display:column>
					
					<display:column class="columna porcen50"
						headerClass="columna porcen50"
						style="color: #F76D2D ${estilo}" title="Fecha Desde" sortable="false">
						<fmt:formatDate value="${vacaciones.vacFechaDesde}" pattern="dd/MM/yyyy" var="fechaDesde" />  
						<c:out value="${fechaDesde}"></c:out>
					</display:column>
					
					<display:column class="columna porcen50"
						headerClass="columna porcen50"
						style="color: #F76D2D ${estilo}" title="Fecha Desde" sortable="false">
						<fmt:formatDate value="${vacaciones.vacFechaHasta}" pattern="dd/MM/yyyy" var="fechaHasta" />  
						<c:out value="${fechaHasta}"></c:out>
					</display:column>
					
				</display:table>
			</td>
		</tr>
	</table>
</html:form>