<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script>

	//Vamos a intentar poner el miniformulario en el mismo jsp para facilitarle las cosas	
	function doAñadir(){
		 if(document.getElementById('hacFechaEntrega').value==''){
			alert('Debe seleccionar la fecha de entrega del dinero a cuenta');
			return;
		}else if( document.getElementById('hacCantidad').value==''){
			alert('Debe indicar una cantidad de dinero a cuenta');
			return;
		}else if( formato_numero(document.getElementById('hacCantidad').value) <= formato_numero(0)){
			alert('La cantidad debe ser mayor a 0');
			return;	
		}else{ 
			if(confirm('¿Desea insertar la cantidad a cuenta?')){
				if( document.getElementById('compruebaImportePendiente').value > 0 ){
						alert( 'Atención, la cantidad a cuenta es mayor que los importes pendiente de todas las facturas.\nDebe insertar una cantidad a cuenta menor.' );
						/*if( x ){
							document.getElementById('frmListado').action='doHistoricoAcGrabar.do';
							document.getElementById('frmListado').submit();
						}else{
							return false;
						}*/	
				}
				else{
					document.getElementById('frmListado').action='doHistoricoAcGrabar.do';
					document.getElementById('frmListado').submit();
				}
			}
		}
	}
	
	function compruebaImportePendienteFacturas(){
		var hacCantidad = document.getElementById('hacCantidad').value;
		var hacClId = document.getElementById('hacClId').value;
		var url = 'doCompruebaImportePendienteFacturas.do?hacCantidad='+hacCantidad+'&hacClId='+hacClId;
		setTimeout("loadTextDoc('"+url+"','compruebaImportePendiente')", 0); 
		
	}
	
	function doImprimir(){
		if(confirm('Va a imprimir el listado de historico a cuenta, ¿Desea continuar?')){
			document.getElementById('frmListado').action='doGenerarHistoricoAc.do';
			document.getElementById('frmListado').target="_new";
			document.getElementById('frmListado').submit();
			document.getElementById('frmListado').action='doListadoHistoricoAc.do';
			document.getElementById('frmListado').target="";
		}
	}
	
</script>
<html:form styleId="frmListado" method="post" action="/doListadoHistoricoAc.do">
<table style="background-color: white; border: 1px solid #E5E5E5; width: 100%">
    <tr>          
	  <td colspan="6" align="right" width="100%">
	  	<span style='color: #FA7D3D; font-size: 12px; font-weight: bold;'>
	  		<c:if test="${!empty Cliente.clId}">
				Cliente: ${Cliente.clNombre} ${Cliente.clApellidos}
			</c:if>	
	  		<html:hidden property="hacClId" styleId="hacClId" name="hacClId" value="${hacClId}"/>
	  		<input type="hidden" id="compruebaImportePendiente" name="compruebaImportePendiente" value="0"/>
		</span>
	  </td>
    </tr>
	<tr>
		<td colspan="6" style="font-family: Verdana; font-size: 11px; color: #2DACE4;padding: 5px 0px 0px 5px">
			<img src="img/play.gif"/>&nbsp;Insertar Cantidad a Cuenta<br>
			<hr style="color: #DFDEDE">
		</td>
	</tr>
	<tr>	
		<td width="2%"></td>
		
		<td width="15%" style="font-family: Verdana; font-size: 11px; color: #666666">
            	<b>Fecha De Entrega</b><br>
			<fmt:formatDate value="${FHISTORICOAC.hacFechaEntrega}" pattern="dd/MM/yyyy" var="hacFechaEntrega" />    	 	
         	<html:text style="background-color: #CCCCCC;" size="20" readonly="true" styleId="hacFechaEntrega" property="hacFechaEntrega" name="hacFechaEntrega" value="${hacFechaEntrega}"/>
         	<img style="cursor: pointer;" class="vtop" id="calendario1" title="Seleccione fecha"  alt="Seleccione fecha" src="img/calendario.gif"/>
         	<script type="text/javascript">
                   Calendar.setup({
                       inputField     :    "hacFechaEntrega"   // id of the input field
                   ,
                       button         :    "calendario1"  // trigger for the calendar (button ID)
                   });
         	</script>
         </td>
		
		<td style="font-family: Verdana; font-size: 11px; color: #666666; width: 12%">
			Cantidad a Cuenta<br>
			<input size="18" style="text-align: right;" onblur="javascript:this.value=formato_numero(this.value,1);compruebaImportePendienteFacturas();" id="hacCantidad" name="hacCantidad" maxlength="11"  type="text" value="">
		</td>
		<td style="font-family: Verdana; font-size: 11px; color: #666666; width: 10%">
			<br><a onclick="javascript: doAñadir(this)">
				<img src="img/add.png" title="Añadir Cantidad"/>
				</a>
		</td>
		
		<td style="width: 30%" style="font-family: Verdana; font-size: 11px; color: #666666; width: 10%">
		</td>
		
		<td align="right" style="width: 40%">
			<fmt:formatNumber pattern="###,###.###" var="sumaImportesFacturas">${sumaImportesFacturas}</fmt:formatNumber>
			<table cellpadding="1" class="displaytag_tabla" cellspacing="1">
				<thead>
					<tr >
						<td colspan="1" class="columna sortable" style="text-align: center;color: orange">	
						<img style="cursor: pointer;" class="vtop"  title="importe Pendiente"  alt="importe Pendiente" src="img/pendiente.png"/>
							<span style="color: #2DACE4;font-weight: bold;">Total Pendiente Facturas</span>
						</td>
					</tr>
				</thead>
				<tr>
					<td colspan="1" class="columna" style="text-align: right;font-weight: bold;">
						<c:out value="${sumaImportesFacturas}"/> &euro;
					</td>
				</tr>		
			</table>
		</td>
	</tr>
	
	<tr>
		<td colspan="6" style="font-family: Verdana; font-size: 11px; color: #2DACE4;padding: 5px 0px 0px 5px">
			<img src="img/play.gif"/>&nbsp;Opciones de impresión<br>
			<hr style="color: #DFDEDE">
		</td>
	</tr>
	
	<tr>	
		<td width="2%"></td>
		
		<td width="15%" style="font-family: Verdana; font-size: 11px; color: #666666">
            	<b>Fecha Desde</b><br>
			<fmt:formatDate value="${FHISTORICOAC.hacFechaDesde}" pattern="dd/MM/yyyy" var="hacFechaDesde" />    	 	
         	<html:text style="background-color: #CCCCCC;" size="20" readonly="true" styleId="hacFechaDesde" property="hacFechaDesde" name="hacFechaDesde" value="${hacFechaDesde}"/>
         	<img style="cursor: pointer;" class="vtop" id="calendario2" title="Seleccione fecha"  alt="Seleccione fecha" src="img/calendario.gif"/>
         	<script type="text/javascript">
                   Calendar.setup({
                       inputField     :    "hacFechaDesde"   // id of the input field
                   ,
                       button         :    "calendario2"  // trigger for the calendar (button ID)
                   });
         	</script>
         </td>
         
         <td width="15%" style="font-family: Verdana; font-size: 11px; color: #666666">
            	<b>Fecha Hasta</b><br>
			<fmt:formatDate value="${FHISTORICOAC.hacFechaHasta}" pattern="dd/MM/yyyy" var="hacFechaHasta" />    	 	
         	<html:text style="background-color: #CCCCCC;" size="20" readonly="true" styleId="hacFechaHasta" property="hacFechaHasta" name="hacFechaHasta" value="${hacFechaHasta}"/>
         	<img style="cursor: pointer;" class="vtop" id="calendario3" title="Seleccione fecha"  alt="Seleccione fecha" src="img/calendario.gif"/>
         	<script type="text/javascript">
                   Calendar.setup({
                       inputField     :    "hacFechaHasta"   // id of the input field
                   ,
                       button         :    "calendario3"  // trigger for the calendar (button ID)
                   });
         	</script>
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
			
				<display:table name="LISTAHISTORICOAC" id="historicoac" export="false"
					cellpadding="0" cellspacing="0" summary="Tabla Listado"
					sort="external" class="" requestURI="/doListadoHistoricoAc.do"
					defaultsort="1" defaultorder="ascending" pagesize="10">
					
					<display:column class="columna porcen0"
						headerClass="columna porcen0"
						style="color: #F76D2D" title="" sortable="false">
						<c:out value=""></c:out>
					</display:column>
					
					<display:column class="columna porcen50"
						headerClass="columna porcen50"
						style="color: #F76D2D" title="Fecha de Entrega" sortable="false">
						<fmt:formatDate value="${historicoac.hacFechaEntrega}" pattern="dd/MM/yyyy" var="fechaEntrega" />  
						<c:out value="${fechaEntrega}"></c:out>
					</display:column>
					
					<display:column class="columna porcen50"
						headerClass="columna porcen50"
						style="color: #F76D2D" title="Cantidad" sortable="false">
						<fmt:formatNumber pattern="###,###.###" var="hacCantidad">${historicoac.hacCantidad}</fmt:formatNumber>
						<c:out value="${hacCantidad}"></c:out>
					</display:column>
				</display:table>
			</td>
		</tr>
	</table>
</html:form>