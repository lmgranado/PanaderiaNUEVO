<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script>

	//Vamos a intentar poner el miniformulario en el mismo jsp para facilitarle las cosas	

	function doBorrar(){
		if(hayChecked('frmListado')){
			if(confirm('Va a eliminar los registros seleccionados, ¿Desea continuar?')){
				document.getElementById('frmListado').action='doProductosClienteGrabar.do?delete=1';
				document.getElementById('frmListado').submit();
			}
		}else{
			alert('Debe marcar algún registro para eliminar');
		}
	}
	
		
	function doGuardar(){
		document.forms[0].action = 'doProductosClienteEditar.do';
		document.forms[0].submit();
	}
	
	function doAñadir(){
		if(document.getElementById('pclProdId').value==''){
			alert('Debe indicar un producto para insertar');
			return;
		}else if(document.getElementById('pclPrecio').value==''){
			alert('Debe indicar el precio del producto');
			return;
		}else{ 
			document.getElementById('frmListado').action='doProductosClienteGrabar.do';
			document.getElementById('frmListado').submit();
		}
	}
</script>
<html:form styleId="frmListado" method="post"
				action="/doListadoProductosCliente.do">
<table style="background-color: white; border: 1px solid #E5E5E5; width: 100%">
	<html:hidden property="pclClId" value="${pclClId}"/>
    <tr>          
	  <td colspan="5" align="right" width="100%">
	  	<span style='color: #FA7D3D; font-size: 12px; font-weight: bold;'>
			<c:if test="${!empty Cliente.clId}">
				Cliente: ${Cliente.clNombre} ${Cliente.clApellidos}
			</c:if>	
		</span>
	  </td>
    </tr>
	<tr>
		<td colspan="5" style="font-family: Verdana; font-size: 11px; color: #2DACE4;padding: 5px 0px 0px 5px">
			<img src="img/play.gif"/>&nbsp;Insertar Nuevo Producto<br>
			<hr style="color: #DFDEDE">
		</td>
	</tr>
	<tr>	
		<td width="2%"></td>
		<td style="font-family: Verdana; font-size: 11px; color: #666666; width: 24%">
			Producto<br>
			<html:select style="width: 220px" styleId="pclProdId" property="pclProdId" value="">
			  <html:option value="">[Seleccionar]</html:option>
			  <html:options property="prodId" labelProperty="prodNombre" collection="LISTAPRODUCTOS"/>
		    </html:select>
		</td>
		
		<td style="font-family: Verdana; font-size: 11px; color: #666666; width: 12%">
			Precio<br>
			<input size="16" onblur="javascript:this.value=formato_numero(this.value,1)" id="pclPrecio" name="pclPrecio" maxlength="11"  type="text" value="">
		</td>
		<td style="font-family: Verdana; font-size: 11px; color: #666666; width: 10%">
			<br><a onclick="javascript: doAñadir(this)">
				<img src="img/add.png" title="Añadir Producto"/>
				</a>
		</td>
		<td style="width: 54%">
			&nbsp;
		</td>
	</tr>
	<tr>
		<td colspan="5" style="">
			&nbsp;
		</td>
	</tr>
	<tr>
		<td colspan="5" style="font-family: Verdana; font-size: 11px; color: #2DACE4;padding: 5px 0px 0px 5px">
			<img src="img/play.gif"/>&nbsp;Resultado<br>
			<hr style="color: #DFDEDE">
		</td>
	</tr>
	<tr>
		<td colspan="5">
			
				<display:table name="LISTAPRODUCTOSCLIENTE" id="productosCliente" export="false"
					cellpadding="0" cellspacing="0" summary="Tabla Listado"
					sort="external" class="" requestURI="/doListadoProductosCliente.do"
					defaultsort="1" defaultorder="ascending" pagesize="10">
			
					<display:column class="columna porcen2" headerClass="columna porcen2"
						style="${estilo2009}" alt="selecc/deselec" function="checkUncheck()" src="img/marca_desmarca.gif" input="img">
							<input type="checkbox" id="checkList" name="checkList"
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
						<fmt:formatNumber pattern="###,###.###" var="pclPrecio">${productosCliente.pclPrecio}</fmt:formatNumber>
						<input size="11" 
							onblur="javascript:this.value=formato_numero(this.value,1)" 
							id="precio_${productosCliente.pclId}"  style="text-align: right;"
							name="precio_${productosCliente.pclId}" maxlength="16"  type="text" value="${pclPrecio}">
					</display:column>
				</display:table>
			</td>
		</tr>
	</table>
</html:form>