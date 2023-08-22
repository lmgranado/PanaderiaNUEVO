<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script>

	function doNuevo(){
		document.getElementById('frmListado').action='doNotasEntregaFormulario.do?nuevo=1';
		document.getElementById('frmListado').submit();
	}
	

	function doBorrar(){
		if(hayChecked('frmListado')){
			if(confirm('Va a eliminar los registros seleccionados, ¿Desea continuar?')){
				document.getElementById('frmListado').action='doNotasEntregaBorrar.do';
				document.getElementById('frmListado').submit();
			}
		}else{
			alert('Debe marcar algún registro para eliminar');
		}
	}
	
	function doIncidencias(){
		//Primero verificamos si hay algun checkbox, si no hay no se lo permito
		if(existeCheckbox('frmListado')){
			document.getElementById('frmListado').action='doNotasEntregaFormulario.do?notId=1&banderaIncidenciasNotas=1';
			document.getElementById('frmListado').submit();
		}else{
			alert('El listado no contiene ninguna nota a la que poner incidencias');
		}
	}
	
	function doNotasMasivo(){
		if(hayCheckedImpresion('frmListado')){
			document.getElementById('frmListado').action='doGenerarNotasEntrega.do';
			document.getElementById('frmListado').target='_blank';
			document.getElementById('frmListado').submit();
			document.getElementById('frmListado').action='doListadoNotasEntrega.do';
			document.getElementById('frmListado').target='';
		}else{
			alert('Debe selecionar alguna nota de entrega');
		}
	}
	
	function doAlbaran(){
		if(hayChecked('frmListado')){
			var condicion = confirm( '¿Desea pasar a Albarán las notas seleccionadas?' );
			if( condicion ){
				document.getElementById('frmListado').action='doAlbaranesVentaCrear.do';
				document.getElementById('frmListado').submit();
			}
		}else{
			alert('Debe selecionar alguna nota de entrega');
		}
	}
	
		function doCerrarDia(){
			if(document.getElementById('notFecha').value==''){
				alert('Debe indicar el día que desea cerrar desde el filtro');
				return false;
			}
			if(confirm('Va a cerrar el día, todos las notas de entrega de hoy pasarán a Albaranes, ¿Desea continuar?')){
				document.getElementById('frmListado').action='doAlbaranesVentaCerrarDia.do';
				document.getElementById('frmListado').submit();
			}
		}
	
</script>
<html:form styleId="frmListado" method="post"
				action="/doListadoNotasEntrega.do">
<table style="background-color: white; border: 1px solid #E5E5E5; width: 100%">
	<tr>
		<td colspan="7" style="font-family: Verdana; font-size: 11px; color: #2DACE4;padding: 5px 0px 0px 5px">
			<img src="img/play.gif"/>&nbsp;Búsqueda<br>
			<hr style="color: #DFDEDE">
		</td>
	</tr>
	<tr>
		<td width="2%"></td>
		<td style="font-family: Verdana; font-size: 11px; color: #666666">
			Nombre<br>
			<!--  <input id="usNombre" size="30" name="usNombre" type="text" value="${FNOTASENTREGA.notNombre}"> --> 
			<html:select style="width: 200px" styleId="notNombre" property="notNombre" value="${FNOTASENTREGA.notNombre}">
			  <html:option value="">[Seleccionar]</html:option>
			  <html:options property="entNombre" labelProperty="entNombre" collection="LISTAENTREGAS"/>
		    </html:select>
		</td>
		
		
		
		
		<td style="font-family: Verdana; font-size: 11px; color: #666666">
			Fecha<br>
			<fmt:formatDate value="${FNOTASENTREGA.notFecha}" pattern="dd/MM/yyyy" var="fecha" />    	 	
           	<input type="text" style="background-color: #CCCCCC;" readonly="readonly" id="notFecha" name="notFecha" value="${fecha}"/>
           	<img style="cursor: pointer;" class="vtop" id="calendario1" title="Seleccione fecha"  alt="Seleccione fecha" src="img/calendario.gif"/>
           	<script type="text/javascript">
                     Calendar.setup({
                         inputField     :    "notFecha"   // id of the input field
                     ,
                         button         :    "calendario1"  // trigger for the calendar (button ID)
                     });
           	</script>
		</td>
		
		<td style="font-family: Verdana; font-size: 11px; color: #666666">
			Cliente<br>
			<html:select style="width: 200px" styleId="notClId" property="notClId" value="${FNOTASENTREGA.notClId}">
			  <html:option value="">[Seleccionar]</html:option>
			  <html:options property="clId" labelProperty="clNombreComercial" collection="CLIENTESLISTA"/>
		    </html:select>
		</td>
		
		<td style="font-family: Verdana; font-size: 11px; color: #666666">
			Ruta<br>
			<html:select style="width: 200px" styleId="notRutId" property="notRutId" value="${FNOTASENTREGA.notRutId}">
			  <html:option value="">[Seleccionar]</html:option>
			  <html:options property="rutId" labelProperty="rutNombre" collection="RUTASLISTA"/>
		    </html:select>
		</td>
		
		<td style="font-family: Verdana; font-size: 11px; color: #666666">
			Estado<br>
			<html:select style="width: 100px" styleId="notCierre" property="notCierre" value="${FNOTASENTREGA.notCierre}">
			  <html:option value="">[Seleccionar]</html:option>
			  <html:option value="0">ABIERTA</html:option>
			  <html:option value="1">CERRADA</html:option>
		    </html:select>
		</td>
		
		<td style="font-family: Verdana; font-size: 11px; color: #666666">
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
			
				<display:table name="LISTANOTASENTREGA" id="notas" export="false"
					cellpadding="0" cellspacing="0" summary="Tabla Listado"
					sort="external" class="" requestURI="/doListadoNotasEntrega.do"
					defaultsort="1" defaultorder="ascending" pagesize="10">
			
					<display:column class="columna porcen2" headerClass="columna porcen2"
						style="${estilo2009}" alt="selecc/deselec" function="checkUncheck()" src="img/marca_desmarca.gif" input="img">
							<c:if test="${notas.notCierre!='1'}">
								<input type="checkbox" id="checkList"
									class="check" name="checkList"
									value="${notas.notId}" />
							</c:if>
							<c:if test="${notas.notCierre=='1'}">
								&nbsp;
							</c:if>
					</display:column>
			
					<display:column class="columna porcen10"
						headerClass="columna porcen10" sortProperty="notNombre" property="notNombre"
						style="color: #F76D2D" title="Nombre" sortable="true"/>
							
					<display:column class="columna porcen10"
						headerClass="columna porcen10" sortProperty="notFecha"
						style="color: #F76D2D" title="Fecha" sortable="true">
						<fmt:formatDate value="${notas.notFecha}" pattern="dd/MM/yyyy" var="fechaDisplay" />
							${fechaDisplay}
					</display:column>
								
					<display:column class="columna porcen10"
						headerClass="columna porcen10" 
						style="color: #F76D2D" title="Cliente" sortable="false">
							${notas.clDatosRelacionados[0]}
					</display:column>
					
					<display:column class="columna porcen10"
						headerClass="columna porcen10"
						style="color: #F76D2D" title="Ruta" sortable="false">
							${notas.clDatosRelacionados[1]}
					</display:column>
					
					<display:column class="columna porcen10"
						headerClass="columna porcen10"
						style="color: #F76D2D" title="Orden" sortable="false">
							${notas.notOrden}
					</display:column>
					
					<display:column class="columna porcen10"
						headerClass="columna porcen10"
						style="color: #F76D2D" title="Estado" sortable="false">
							<c:if test="${notas.notCierre=='1'}">
								CERRADA
							</c:if>
							<c:if test="${notas.notCierre!='1'}">
								ABIERTA
							</c:if>
					</display:column>
			
					<display:column class="columna porcen2" headerClass="columna porcen2"
						style="${estilo2009}" title="Editar">
							<c:if test="${notas.notCierre!='1'}">
								<a href="doNotasEntregaFormulario.do?notId=${notas.notId}">
									<img src="img/editar.gif">
								</a>&nbsp;
							</c:if>
								<a target="_blank" href="doGenerarNotasEntrega.do?notId=${notas.notId}">
									<img src="img/cuadrante.gif">
								</a>
					</display:column>
					
					<display:column class="columna porcen2" headerClass="columna porcen2"
						style="${estilo2009}" alt="selecc/deselec" function="checkUncheckImpresion()" src="img/marca_desmarca.gif" input="img">
						<input type="checkbox" id="checkListImpresion"
							class="check" name="checkListImpresion"
							value="${notas.notId}" />
					</display:column>
			
				</display:table>
			</td>
		</tr>
	</table>
</html:form>