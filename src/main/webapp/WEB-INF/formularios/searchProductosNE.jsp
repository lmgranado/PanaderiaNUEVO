<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
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
	
</script>
<html:form action="/doVentanaSearchProductosNotasEntrega" styleId="frmDetalle"  method="post">	
    <table class="displaytag_tabla" style="margin: 0px 20px; width: 100%;" cellpadding="2" cellspacing="2">
    	<tr>
		<td colspan="7" style="font-family: Verdana; font-size: 11px; color: #2DACE4;padding: 5px 0px 0px 5px">
			<img src="img/play.gif"/>&nbsp;Búsqueda Productos<br>
			<hr style="color: #DFDEDE">
		</td>
	</tr>
	<tr>
	
		<td style="font-family: Verdana; font-size: 11px; color: #666666">
			Código<br>
			<input size="16" id="prodId" name="prodId"  type="text" value="${FPRODUCTOS.prodId}">
		</td>
		<td style="font-family: Verdana; font-size: 11px; color: #666666">
			Descripción<br>
			<input id="prodNombre" size="16" name="prodNombre" type="text" value="${FPRODUCTOS.prodNombre}">
		</td>
		
		<td style="font-family: Verdana; font-size: 11px; color: #666666">
			Cantidad<br>
			<input id="cantidad" size="16" name="cantidad" type="text" value="${cantidad}">
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
			
				<display:table name="LISTAPRODUCTOS" id="productos" export="false"
					cellpadding="0" cellspacing="0" summary="Tabla Listado"
					sort="external" class="" requestURI="/doVentanaSearchProductosNotasEntrega.do"
					defaultsort="1" defaultorder="ascending">
			
					<display:column class="columna porcen2" headerClass="columna porcen2"
						style="${estilo2009}">
							<input name="checkList" type="radio" id="checkList"
								class="check"
								value="${productos.prodId}" />
					</display:column>
			
					<display:column class="columna porcen10"
						headerClass="columna porcen10" sortProperty="prodReferencia"
						style="color: #F76D2D" title="Código" sortable="true">
							${productos.prodReferencia}
					</display:column>
					
					<display:column class="columna porcen10"
						headerClass="columna porcen10" sortProperty="clNombre"
						style="color: #F76D2D" title="Descripción" sortable="true">
							${productos.prodNombre}
					</display:column>
					
					<display:column class="columna porcen10"
						headerClass="columna porcen10" property="prodPrecioGeneral"
						style="color: #F76D2D" title="Precio" sortable="true"/>
							${productos.prodPrecioGeneral}
							
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
	<input type='hidden' value="${notClId}" name="notClId" id="notClId" />
	<input type='hidden' value="true" name="accion" />
</html:form>
<!-- Validacion -->
<script type="text/javascript">
window.opener.doRefresh();
</script>
