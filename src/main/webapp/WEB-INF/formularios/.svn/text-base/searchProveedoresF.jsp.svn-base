<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<script type="text/javascript">
	vFormName = "frmDetalle";
	function doSubmit(){
		vForm = document.getElementById(vFormName);					
		var IdUsuario = hayChecked(vForm);
		if( IdUsuario == -1 ){
			alert( 'Debe de elegir algun usuario' );
		}
		else{
			_enviarDetalle('frmDetalle');	
			<c:if test="${FPROVEEDORES.tipo=='C'}">
				window.opener.document.getElementById('frmDetalle').action = 'doFacturasCompraFormulario.do?tipo=C&fcPrId=' + IdUsuario;
			</c:if>
			<c:if test="${FPROVEEDORES.tipo=='F'}">
				window.opener.document.getElementById('frmDetalle').action = 'doFacturasFabricacionFormulario.do?tipo=F&fcPrId=' + IdUsuario;
			</c:if>
			window.opener.document.getElementById('frmDetalle').submit();
			window.close();
		}
	}
	
	
	/* Comprueba se hay algún usuario seleccionado, devuelve -1 si no hay ninguno */
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
<html:form action="/doVentanaSearchProveedoresFactura" styleId="frmDetalle"  method="post">	
<input type="hidden" value="${FPROVEEDORES.tipo}" name="tipo"/>
    <table class="displaytag_tabla" style="margin: 0px 20px; width: 100%;" cellpadding="2" cellspacing="2">
    	<html:hidden property="fcPrId" value="${fcPrId}"/>
    	<tr>
		<td colspan="7" style="font-family: Verdana; font-size: 11px; color: #2DACE4;padding: 5px 0px 0px 5px">
			<img src="img/play.gif"/>&nbsp;Búsqueda Proveedores<br>
			<hr style="color: #DFDEDE">
		</td>
	</tr>
	<tr>
	
		<td style="font-family: Verdana; font-size: 11px; color: #666666">
			Código<br>
			<input size="16" id="prNif" name="prNif"  type="text" value="${FPROVEEDORES.prId}">
		</td>
		<td style="font-family: Verdana; font-size: 11px; color: #666666">
			N. Comercial<br>
			<input id="prNombreComercial" size="16" name="prNombreComercial" type="text" value="${FPROVEEDORES.prNombreComercial}">
		</td>

		<td style="font-family: Verdana; font-size: 11px; color: #666666">
			Nombre<br>
			<input id="prNombre" size="16" name="prNombre" type="text" value="${FPROVEEDORES.prNombre}">
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
		<td colspan="8" style="">
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
			
				<display:table name="LISTAPROVEEDORES" id="proveedores" export="false"
					cellpadding="0" cellspacing="0" summary="Tabla Listado"
					sort="external" class="" requestURI="/doVentanaSearchProveedoresFactura.do"
					defaultsort="1" defaultorder="ascending">
			
					<display:column class="columna porcen2" headerClass="columna porcen2"
						style="${estilo2009}">
							<input name="checkList" type="radio" id="checkList"
								class="check"
								value="${proveedores.prId}" />
					</display:column>
			
					<display:column class="columna porcen10"
						headerClass="columna porcen10" sortProperty="clNombre"
						style="color: #F76D2D" title="Nombre" sortable="true">
							${proveedores.prNombre}&nbsp;${proveedores.prApellidos}
					</display:column>
					
					<display:column class="columna porcen10"
						headerClass="columna porcen10" property="prNombreComercial"
						style="color: #F76D2D" title="Comercial" sortable="true"/>
						
					<display:column class="columna porcen10"
						headerClass="columna porcen10"
						style="color: #F76D2D" title="Telefono" sortable="true">
							<c:if test="${empty proveedores.prTelefono}">&nbsp;</c:if>
							<c:if test="${!empty proveedores.prTelefono}">${proveedores.prTelefono}</c:if>
					</display:column>
					
					<display:column class="columna porcen10"
						headerClass="columna porcen10" sortProperty="clDireccion"
						style="color: #F76D2D" title="Direccion" sortable="true">
							${proveedores.prDireccion}, ${proveedores.prDatosRelacionados[4]}
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
	<input type='hidden' value="${fcId}" name="fcId" />
	<input type='hidden' value="true" name="accion" />
</html:form>
