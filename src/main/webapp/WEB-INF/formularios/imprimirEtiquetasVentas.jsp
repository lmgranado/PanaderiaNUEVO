<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<script type="text/javascript">
	vFormName = "frmDetalle";
	function doSubmit(){
		/*var elementos = document.getElementsByTagName("SELECT");
		for(var i = 0; i < elementos.length; i++){ 
			alert( 'entra en bucle' );
			var elto = elementos[i];
			alert(elto[elto.selectedIndex].title);
			//alert(document.getElementsByName("prodCantidad").value);
			if(elto[elto.selectedIndex].title < document.getElementsByName("prodCantidad").value){
				alert('No puede fabricar la cantidad seleccionada con el stock marcado. Cambie de stock o bien cierre esta pantalla y seleccione otra cantidad');
				return;
			}
		}*/
		
		_enviarDetalle('frmDetalle');
	}
	
	function validaCampos( idProducto, cantidad ){
		var correcto = true;
		if( idProducto == '-1' ){
			alert('Debe elegir algun producto para añadir a la factura')
			correcto = false;
		}
		else if( cantidad < 1 ){
			alert('Debe introducir un valor válido para la cantidad');
			correcto = false;
		}
		return correcto;
	}
	
	/* Comprueba se hay algún paroducto seleccionado, devuelve -1 si no hay ninguno */
	function hayChecked(form){
		hayCheck = false;
		vForm = document.getElementById("frmDetalle");
		value = -1;
		if(vForm.checkList!= undefined){
			size = vForm.checkList.length;
			value = vForm.checkList.value;
			if (value != undefined)
				hayCheck = vForm.checkList.checked;
			if (size != undefined){
				i=0; 		
				while (i<size && !hayCheck){
					hayCheck = vForm.checkList[i].checked;
					value = vForm.checkList[i].value;
					i++;
				}		
			}
		}
		if( !hayCheck ){
			value=-1;
		}
		return value;
	}
	
	function copiasCero(){
		for (i=0;i<frmDetalle.elements.length;i++) {
			if (frmDetalle.elements[i].type=="text" ){
				frmDetalle.elements[i].value = "0";
			}
		}
	}
	
	
	function doImprimirEtiquetas(){
		if(hayChecked('frmListado')){
			if(confirm('Va a imprimir las etiquetas seleccionadas, ¿Desea continuar?')){
				document.getElementById('frmDetalle').action='doGenerarEtiquetasVentas.do';
				document.getElementById('frmDetalle').target="_new";
				document.getElementById('frmDetalle').submit();
				document.getElementById('frmDetalle').action='doVentanaImprimirEtiquetasVentas.do';
				document.getElementById('frmDetalle').target="";
			}
		}else{
			alert('Debe marcar alguna de las fabricaciones para realizar la operación solicitada');
		}
	}
	
	
	
	
	
</script>
<html:form action="/doVentanaImprimirEtiquetasVentas" styleId="frmDetalle"  method="post">	
	
	<input type="hidden" id="fvId" name="fvId" value="${fvId}"/>
		
    <table cellpadding="1" class="displaytag_tabla" cellspacing="1" style="margin-top: 20px">
					<thead>
						<tr class="${estilo}">
							<th class="columna sortable">
								Producto
							</th>							
							<th class="columna sortable">
								Lote
							</th>
							<th class="columna sortable">
								Cantidad
							</th>
						</tr>
					</thead>
					<c:forEach items="${LISTADETALLES}" var="detalle">
						<c:choose>
							<c:when test="${contador%2==0}">
								<c:set value="odd" var="estilo"/>
								<c:set var="contador" value="3"/>
							</c:when>
							<c:otherwise>
								<c:set value="even" var="estilo"/>
								<c:set var="contador" value="2"/>
							</c:otherwise>
						</c:choose>
						<c:set var="indice" value="${indice+1}"/>
						<tr class="${estilo}">							
							<td class="columna" style="text-align: left;">
								<c:out value="${detalle.fvdProducto}"/>
							</td>
							
							<td class="columna" style="text-align: right;">
								<c:out value="${detalle.fvdLote}"/>
							</td>
							
							<td class="columna" style="text-align: right;">
								<fmt:formatNumber  type="number" pattern="#" var="cantidad">${detalle.fvdCantidad}</fmt:formatNumber>
								<c:choose>
									<c:when test="${cantidad < '0'}">
										<input onblur="this.value=formato_entero(this.value)" style="text-align: right;font-size: 11px;" id="cantidad" name="fvdId_${detalle.fvdId}"  value="0">
									</c:when>
									<c:otherwise>
										<input onblur="this.value=formato_entero(this.value)" style="text-align: right;font-size: 11px;" id="cantidad" name="fvdId_${detalle.fvdId}" value="${cantidad}">
									</c:otherwise>
								</c:choose>
							</td>
						</tr>
					</c:forEach>
				</table>
		
		<c:if test="${!empty excepcion}">
			<tr>
				<td colspan="3" style="font-family: Verdana; font-size: 14px; color: red">
					${excepcion}
				</td>
			</tr>
		</c:if>	
		
	</table>
	<!-- Botonera Inferior -->
	<table align="center">
		<tr>
			<td style="font-family: Verdana; font-size: 11px; color: #666666">
			<br><a onclick="javascript: doImprimirEtiquetas()"><img src="img/imprimir.png" title="imprimir"/></a>
				<a onclick="javascript: doCerrar();"><img src="img/volver.gif" title="Volver"/></a>
				<a onclick="javascript: copiasCero();"><img src="img/ko.png" title="Volver"/></a>
		</td>
		</tr>	
	</table>
</html:form>
<!-- Validacion -->
<script type="text/javascript">
window.opener.doRefresh();
</script>
