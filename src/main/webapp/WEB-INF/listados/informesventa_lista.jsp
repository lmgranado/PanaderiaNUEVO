<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script>
	
	function doInformesVenta(){
		if(document.getElementById('FechaInicio').value=='' || document.getElementById('FechaFin').value==''){
			alert('Debe indicar la fecha de inicio y fin. En caso de ser un solo dia, estas fechas deben ser las mismas');
			return;
		}
		document.forms[0].submit();
	}
	
</script>
<html:form styleId="frmListado" method="post" target="_blank" action="/doGraficasVentasCrear.do">
<table style="background-color: white; border: 1px solid #E5E5E5; width: 100%; height: 250px">
	<tr>
		<td colspan="3" style="font-family: Verdana; font-size: 11px; color: #2DACE4;padding: 5px 0px 0px 5px">
			<img src="img/play.gif"/>&nbsp;Establezca el filtro para la generación de las gráficas<br>
			<hr style="color: #DFDEDE">
		</td>
	</tr>
	<tr>
		<td width="2%">
		</td>
		<td style="font-family: Verdana; font-size: 11px; color: #666666">
			Fecha de Inicio<br>  	 	
           	<input type="text" style="background-color: #CCCCCC;" readonly="readonly" id="FechaInicio" name="FechaInicio" value=""/>
           	<img style="cursor: pointer;" class="vtop" id="calendario1" title="Seleccione fecha"  alt="Seleccione fecha" src="img/calendario.gif"/>
           	<script type="text/javascript">
                     Calendar.setup({
                         inputField     :    "FechaInicio"   // id of the input field
                     ,
                         button         :    "calendario1"  // trigger for the calendar (button ID)
                     });
           	</script>
		</td>
		
		<td style="font-family: Verdana; font-size: 11px; color: #666666">
			Fecha de Fin<br> 	 	
           	<input type="text" style="background-color: #CCCCCC;" readonly="readonly" id="FechaFin" name="FechaFin" value=""/>
           	<img style="cursor: pointer;" class="vtop" id="calendario2" title="Seleccione fecha"  alt="Seleccione fecha" src="img/calendario.gif"/>
           	<script type="text/javascript">
                     Calendar.setup({
                         inputField     :    "FechaFin"   // id of the input field
                     ,
                         button         :    "calendario2"  // trigger for the calendar (button ID)
                     });
           	</script>
		</td>
	</tr>
	<tr>
		<td width="2%">
		</td>
		<td style="font-family: Verdana; font-size: 11px; color: #666666">
			Agrupar por<br>
			<SELECT name="agrupacion" id="agrupacion">
				<option value="0">Clientes</option>
				<option value="1">Productos</option>
			</SELECT>
		</td>
		<td style="font-family: Verdana; font-size: 11px; color: #666666">
			Filtro empresa (En el caso de seleccionar una empresa, la parte Nº Fac no se usa para el calculo)<br>
			<select name="empresa" id="empresa" style="width: 160px"><option value="">[Ambas]</option>
				  <option value="1">Pinepan S.L.</option>
				  <option value="2">Obrador</option>
			</select>
		</td>
		<!-- Ocultamos el estado, ya que con los albaranes siempre vamos a mostrar los pagados y no pagados -->
		<td style="font-family: Verdana; font-size: 11px; color: #666666; visibility: hidden;" >
			Estado<br>
			<SELECT name="pagada" id="pagada">
				<option value="2">Pagadas y no pagadas</option>
				<option value="1">Pagadas</option>
				<option value="0">No Pagadas</option>
			</SELECT>
		</td>
		<!-- <td style="font-family: Verdana; font-size: 11px; color: #666666">
			Ver gráficas de<br>
			<SELECT name="agTipo" id="agTipo">
				<option value="0">Precios</option>
				<option value="1">Cantidades</option>
			</SELECT>
		</td>  -->
		
		<td style="font-family: Verdana; font-size: 11px; color: #666666">
			&nbsp;
		</td>
		
		<td style="font-family: Verdana; font-size: 11px; color: #666666">
			&nbsp;
		</td>
	</tr>
	<tr>
		<td colspan="4" style="">
			&nbsp;
		</td>
	</tr>
	</table>
</html:form>