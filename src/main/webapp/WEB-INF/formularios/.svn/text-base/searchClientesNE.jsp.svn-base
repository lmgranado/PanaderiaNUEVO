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
			window.opener.document.getElementById('frmDetalle').action = 'doNotasEntregaFormulario.do?notClId=' + IdUsuario;
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
<html:form action="/doVentanaSearchClienteNotasEntrega" styleId="frmDetalle"  method="post">	
    <table class="displaytag_tabla" style="margin: 0px 20px; width: 100%;" cellpadding="2" cellspacing="2">
    	<tr>
		<td colspan="7" style="font-family: Verdana; font-size: 11px; color: #2DACE4;padding: 5px 0px 0px 5px">
			<img src="img/play.gif"/>&nbsp;Búsqueda Clientes<br>
			<hr style="color: #DFDEDE">
		</td>
	</tr>
	<tr>
	
		<td style="font-family: Verdana; font-size: 11px; color: #666666">
			Código<br>
			<input size="16" id="clNif" name="clNif"  type="text" value="${FCLIENTES.clId}">
		</td>
		<td style="font-family: Verdana; font-size: 11px; color: #666666">
			N. Comercial<br>
			<input id="clNombreComercial" size="16" name="clNombreComercial" type="text" value="${FCLIENTES.clNombreComercial}">
		</td>

		<td style="font-family: Verdana; font-size: 11px; color: #666666">
			Nombre<br>
			<input id="clNombre" size="16" name="clNombre" type="text" value="${FCLIENTES.clNombre}">
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
			
				<display:table name="LISTACLIENTES" id="clientes" export="false"
					cellpadding="0" cellspacing="0" summary="Tabla Listado"
					sort="external" class="" requestURI="/doVentanaSearchClienteNotasEntrega.do"
					defaultsort="1" defaultorder="ascending">
			
					<display:column class="columna porcen2" headerClass="columna porcen2"
						style="${estilo2009}">
							<input name="checkList" type="radio" id="checkList"
								class="check"
								value="${clientes.clId}" />
					</display:column>
			
					<display:column class="columna porcen10"
						headerClass="columna porcen10" sortProperty="clNombre"
						style="color: #F76D2D" title="Nombre" sortable="true">
							${clientes.clNombre}&nbsp;${clientes.clApellidos}
					</display:column>
					
					<display:column class="columna porcen10"
						headerClass="columna porcen10" property="clNombreComercial"
						style="color: #F76D2D" title="Comercial" sortable="true"/>
						
					<display:column class="columna porcen10"
						headerClass="columna porcen10"
						style="color: #F76D2D" title="Telefono" sortable="true">
							<c:if test="${empty clientes.clTelefono}">&nbsp;</c:if>
							<c:if test="${!empty clientes.clTelefono}">${clientes.clTelefono}</c:if>
					</display:column>
					
					<display:column class="columna porcen10"
						headerClass="columna porcen10" sortProperty="clDireccion"
						style="color: #F76D2D" title="Direccion" sortable="true">
							${clientes.clDireccion}, ${clientes.clDatosRelacionados[4]}
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
	<input type='hidden' value="${notId}" name="notId" />
	<input type='hidden' value="true" name="accion" />
</html:form>
