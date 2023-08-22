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
		
		if(document.getElementById('prodCantidad').value==''){
			alert('Debe introducir una CANTIDAD para la fabricacion');
			return;
		}
		else if(document.getElementById('fcdLote').value == '' ){
			alert('Debe introducir un LOTE para la fabricación');
			return;
		}
		else if(document.getElementById('stFCaducidad').value == '' ){
			alert('Debe introducir una FECHA DE CADUCIDAD para la fabricación');
			return;
		}
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
	
	function muestraDatos(lote, producto){
		 var cells = document.getElementsByTagName("tr");   
		 for (var i = 0; i < cells.length; i++) {   
		     var miId = cells[i].getAttribute("id"); 
		     var miTitle = cells[i].getAttribute("title");   
		     //alert(miId + " - " + lote + " + " + miTitle + " - " + producto);
		     if ( miId == lote && miTitle == producto ) {   
		         document.getElementById(lote).style.display = 'block';
		     }else if(miTitle == producto ) {   
		         document.getElementById(miId).style.display = 'none';
		     }  
		 }  
	}
	
	
	function rellenaDatos(lote, fechaCaducidad){
		   
		document.getElementById('fcdLote').value = lote;
		document.getElementById('stFCaducidad').value = fechaCaducidad;
		  
	}
	
</script>
<html:form action="/doVentanaSearchProductosFabricacion" styleId="frmDetalle"  method="post">	
	<input type="hidden" name="prodId" value="${prodId}"/>
	<input type="hidden" name="fcId" value="${fcId}"/>
	<%-- <input type="hidden" name="prodCantidad" value="${prodCantidad}"/>
	<input type="hidden" name="fcdLote" value="${fcdLote}"/>
	<input type="hidden" name="stFCaducidad" value="${stFCaducidad}"/>--%>
	<input type="hidden" name="accion" value="fabricar"/>
	
	
    <table class="displaytag_tabla" style="margin: 0px 20px; width: 100%;" cellpadding="2" cellspacing="2">
    	<tr>
			<td colspan="3" style="font-family: Verdana; font-size: 14px; color: #2DACE4;padding: 5px 0px 0px 5px; font-weight: bold;">
				<img src="img/play.gif"/>&nbsp;Selección de productos<br>
				<hr style="color: #DFDEDE">
			</td>
		</tr>
		
		<tr>
			<td  style="font-family: Verdana; font-size: 11px; color: #666666; width: 15%">
				Cantidad<br>
					<input onblur="this.value=formato_entero(this.value)" size="16" id="prodCantidad" name="prodCantidad" style="text-align: right;" maxlength="11"  type="text" value="${prodCantidad}">
			</td>
			<td style="font-family: Verdana; font-size: 11px; color: #666666; width: 15%">
				Lote<br>
				<input size="16" id="fcdLote" name="fcdLote" style="text-align: right;" maxlength="11"  type="text" value="${fcdLote}">
			</td>
			<td style="font-family: Verdana; font-size: 11px; color: #666666; width: 25%">
            	F.caducidad<br>
            	<input size="12" style="background-color: #CCCCCC;" readonly="readonly" id="stFCaducidad" name="stFCaducidad" value="${stFCaducidad}"/>
	            	<img style="cursor: pointer;" class="vtop" id="calendario2" title="Seleccione fecha"  alt="Seleccione fecha" src="img/calendario.gif"/>
	            	<script type="text/javascript">
	                      Calendar.setup({
	                          inputField     :    "stFCaducidad"   // id of the input field
	                      ,
	                          button         :    "calendario2"  // trigger for the calendar (button ID)
	                      });
	            	</script>
			</td>
			
		</tr>
		
		
		<tr>
			<td colspan="3" style="font-family: Verdana; font-size: 14px; color: #2DACE4;padding: 5px 0px 0px 5px; font-weight: bold;">
				<img src="img/play.gif"/>&nbsp;Componentes del productos<br>
				<hr style="color: #DFDEDE">
			</td>
		</tr>
		
		
		<c:forEach items="${listaParaFabricar}" var="item">
			<tr>
				<td colspan="3" style="font-family: Verdana; font-size: 11px; color: #666666">
					<b>${item[0]}</b><br>
					<SELECT style="font-size: 14px"  name="producto_${item[1]}" id="producto_${item[1]}">
						<c:forEach items="${item[2]}" var="lotes">
							<fmt:formatNumber pattern="###,###.###" var="tn">${lotes.stCantidadFinal}</fmt:formatNumber>
							<fmt:formatNumber pattern="###,###.###" var="cantidadFinal">${lotes.stCantidadFinal}</fmt:formatNumber>
							<fmt:formatNumber pattern="###,###.###" var="salidas">${lotes.stSalidas}</fmt:formatNumber>
							<fmt:formatNumber pattern="###,###.###" var="cantidadInicial">${lotes.stCantidadInicial}</fmt:formatNumber>
							<fmt:formatDate value="${lotes.stFCaducidad}" pattern="dd/MM/yyyy" var="stFCaducidad" />
							<%-- <option onclick="muestraDatos('${lotes.stProdLote}','${item[0]}');" title="${lotes.stCantidadFinal}" value="${lotes.stId}">Lote:   ${lotes.stProdLote} | Entradas:   ${cantidadInicial} | Salidas:  ${salidas} | Stock:   ${cantidadFinal}</option>--%>
							<option onclick="rellenaDatos('${lotes.stProdLote}','${stFCaducidad}');" value="${lotes.stId}"> Lote:   ${lotes.stProdLote}   *******       Stock: ${cantidadFinal}</option>
						</c:forEach>
					</SELECT>
				</td>
			</tr>
		</c:forEach>	
		
		<c:forEach items="${listaParaFabricar}" var="item">
			<tr>
				<td style="font-family: Verdana; font-size: 11px; color: #666666">
					<c:forEach items="${item[2]}" var="lotes">
						<fmt:formatNumber pattern="###,###.###" var="tn">${lotes.stCantidadFinal}</fmt:formatNumber>
						<tr id="${lotes.stProdLote}" title="${item[0]}"  style="display: none">
							<td>
								<table border="1">
									<tr>
										<td style="background-color: white">${item[0]}</td>
									</tr>
									<tr>
										<td>Lote: <span style="font-weight: bold">'${lotes.stProdLote}'</span></td>
									</tr>
									<tr>
										<td>Caducidad: <span style="font-weight: bold">${lotes.stFCaducidad}</span></td>
									</tr>
									<tr>
										<td>Cantidad de Stock: <span style="font-weight: bold">${tn} Kg</span></td>
									</tr>
									<tr>
										<td>Cantidad máxima a producir: <span style="font-weight: bold">${lotes.stCantidadFinal} unidades</span></td>
									</tr>
								</table>
							</td>
						</tr>
					</c:forEach>
				</td>
			</tr>
		</c:forEach>
		
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
			<br><a onclick="javascript: doSubmit()">
				<img src="img/ok.png" title="Guardar"/>
				</a>
				<a onclick="javascript: doCerrar();">
				<img src="img/ko.png" title="Volver"/>
				</a>
		</td>
		</tr>	
	</table>
</html:form>
<!-- Validacion -->
<script type="text/javascript">
window.opener.doRefresh();
</script>
