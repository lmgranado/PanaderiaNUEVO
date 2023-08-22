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
			document.getElementById('frmDetalle').action = 'doFacturasVentaDetalleGrabar.do?prodId=' + idProducto + '&cantidad='+ cantidad+'&fvClId=${fvClId}&fvTipo=${fvTipo}';
			_enviarDetalle('frmDetalle');
			//window.opener.doSubmit();
		}	
	}
	
	function validaCampos( idProducto, cantidad ){
		var correcto = true;
		var fvTipo = document.getElementById('fvTipo').value;
		if( idProducto == '-1' ){
			alert('Debe elegir algun producto para añadir a la factura')
			correcto = false;
		}
		else if( fvTipo!=2 && cantidad < 1 ){
			alert('Debe introducir un valor válido para la cantidad');
			correcto = false;
		}
		else if( fvTipo==2 && cantidad > 0 ){
			alert('Debe introducir un valor válido para la cantidad, en este caso\n un valor negativo al ser una Factura de Abono');
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
<html:form action="/doVentanaSearchProductosClienteFactura" styleId="frmDetalle"  method="post">
<table class="displaytag_tabla" style="margin: 0px 20px; width: 100%;" cellpadding="2" cellspacing="2">
	<html:hidden property="fvClId" value="${fvClId}"/>
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
		<td width="10%" style="font-family: Verdana; font-size: 11px; color: #666666">
			<input id="cantidad" size="16"  onblur="javascript:this.value=formato_entero(this.value)" name="cantidad" type="text" value="${cantidad}">
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
					sort="external" class="" requestURI="/doVentanaSearchProductosClienteFactura.do"
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
						headerClass="columna porcen10"
						style="color: #F76D2D" title="Precio" sortable="true">
						<fmt:formatNumber pattern="###,###.###" var="precio">${productosCliente.pclPrecio}</fmt:formatNumber>
						<c:out value="${precio}"/>
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
	<input type='hidden' value="${fvId}" id="fvId" name="fvId" />
	<input type='hidden' value="${fvClId}" name="fvClId" id="fvClId" />
	<input type='hidden' value="${fvTipo}" name="fvTipo" id="fvTipo" />
	<input type='hidden' value="true" name="accion" />
</html:form>
<!-- Validacion -->
<script type="text/javascript">
window.opener.doRefresh();
</script>