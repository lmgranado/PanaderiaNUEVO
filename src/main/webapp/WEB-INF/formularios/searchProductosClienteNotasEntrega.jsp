<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script type="text/javascript">
	vFormName = "frmDetalle";
	function doSubmit(){
		vForm = document.getElementById(vFormName);				
		var idProducto = hayChecked(vForm);
		var cantidad = document.getElementById('cantidad').value;
		if( validaCampos( idProducto, cantidad) ){
			document.getElementById('frmDetalle').action = 'doNotasEntregaDetalleGrabar.do?prodId=' + idProducto + '&cantidad='+ cantidad+'&notClId=${notClId}';
			_enviarDetalle('frmDetalle');
			//window.opener.doSubmit();
		}	
	}
	
	function validaCampos( idProducto, cantidad ){
		var correcto = true;
		if( idProducto == '-1' ){
			alert('Debe elegir algun producto para añadir a la nota de entrega')
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
</script>
<html:form action="/doVentanaSearchProductosClienteNotasEntrega" styleId="frmDetalle"  method="post">
<table class="displaytag_tabla" style="margin: 0px 20px; width: 100%;" cellpadding="2" cellspacing="2">
	<html:hidden property="notClId" value="${notClId}"/>
	<tr>
		<td colspan="7" style="font-family: Verdana; font-size: 11px; color: #2DACE4;padding: 5px 0px 0px 5px">
			<img src="img/play.gif"/>&nbsp;Productos Asociados al Cliente<br>
			<hr style="color: #DFDEDE">
		</td>
	</tr>
	<tr>
		<td width="6%" style="font-family: Verdana; font-size: 11px; color: #666666">
			<b>Introduzca la cantidad del producto: </b>
		</td>
		<td width="10%" style="font-family: Verdana; font-size: 11px; color: #666666";>
			<input id="cantidad" style="text-align: right;" onblur="javascript:this.value=formato_entero(this.value)" size="16" name="cantidad" type="text" value="${cantidad}">
		</td>
	</tr>
	<tr>
		<td colspan="4" style="">
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
				<display:table name="LISTAPRODUCTOSCLIENTE" id="productosCliente" export="false"
					cellpadding="0" cellspacing="0" summary="Tabla Listado"
					sort="external" class="" requestURI="/doVentanaSearchProductosClienteNotasEntrega.do"
					defaultsort="1" defaultorder="ascending">
			
					<display:column class="columna porcen2" headerClass="columna porcen2"
						style="${estilo2009}">
							<input type="radio" id="checkList" name="checkList"
								class="check"
								value="${productosCliente.pclId}" />
					</display:column>
			
					<display:column class="columna porcen10"
						headerClass="columna porcen10"
						style="color: #F76D2D" title="Producto" sortable="false">
							${productosCliente.clDatosRelacionados[0]}
					</display:column>
					
					<display:column class="columna porcen10"
						headerClass="columna porcen10" style="color: #F76D2D" title="Precio" sortable="true">
						<fmt:formatNumber pattern="###,###.###" var="pclPrecio">${productosCliente.pclPrecio}</fmt:formatNumber>
							${pclPrecio}
					</display:column>
				</display:table>
			</td>
		</tr>
	</table>
	<!-- Botonera Inferior -->
	<table align="center">
		<tr>
			<td style="font-family: Verdana; font-size: 11px; color: #666666">
			<input id="filtro" name="filtro"  type="hidden" value="">
			<br><a onclick="javascript: doSubmit()">

				<img src="img/ok.png" title="anadir usuario"/>
				</a>
				<a onclick="javascript: doCerrar();">
				<img src="img/ko.png" title="Volver"/>
				</a>
		</td>
		</tr>	
	</table>
	<input type='hidden' value="${notId}" id="notId" name="notId" />
	<input type='hidden' value="" name="notClId" id="notClId" />
	<input type='hidden' value="true" name="accion" />
</html:form>
<!-- Validacion -->
<script type="text/javascript">
	window.opener.location = 'doNotasEntregaFormulario.do?notId=${notId}&keyMapaActual=' + window.opener.document.getElementById('keyMapa').value;
</script>