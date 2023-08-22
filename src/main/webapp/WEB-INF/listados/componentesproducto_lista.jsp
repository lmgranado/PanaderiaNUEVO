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
				document.getElementById('frmListado').action='doComponentesProductoGrabar.do?delete=1';
				document.getElementById('frmListado').submit();
			}
		}else{
			alert('Debe marcar algún registro para eliminar');
		}
	}
	
	function doAñadir(){
		if(document.getElementById('cpProdIdUsado').value==''){
			alert('Debe indicar un componente a añadir');
			return;
		}else if( formato_numero(document.getElementById('cpCantidad').value) <= formato_numero(0)){
			alert('La cantidad debe ser mayor a 0');
			return;	
		}else{ 
			document.getElementById('frmListado').action='doComponentesProductoGrabar.do';
			document.getElementById('frmListado').submit();
		}
	}
</script>
<html:form styleId="frmListado" method="post" action="/doListadoComponentesProducto.do">
<table style="background-color: white; border: 1px solid #E5E5E5; width: 100%">
	<html:hidden property="cpProdIdFabricado" value="${cpProdIdFabricado}"/>
	 <tr>          
		  <td colspan="4" align="right">
		  	<span style='color: #FA7D3D; font-size: 12px; font-weight: bold;'>
				<c:if test="${!empty Producto.prodId}">
					Producto: ${Producto.prodNombre}
				</c:if>	
			</span>
		  </td>
	    </tr>
	<tr>
		<td colspan="7" style="font-family: Verdana; font-size: 11px; color: #2DACE4;padding: 5px 0px 0px 5px">
				<img src="img/play.gif"/>&nbsp;Insertar Componente<br>
				<hr style="color: #DFDEDE">
		</td>
	</tr>
		
	<tr>
		<td style="font-family: Verdana; font-size: 11px; color: #666666; width: 10%">
							Producto<br>
							<html:select style="width: 250px" styleId="cpProdIdUsado" property="cpProdIdUsado" value="">
							  <html:option value="">[Seleccionar]</html:option>
							  <html:options property="prodId" labelProperty="prodNombre" collection="LISTAPRODUCTOS"/>
						    </html:select>
						</td>
						
						<td style="font-family: Verdana; font-size: 11px; color: #666666; width: 5%">
							Cantidad (Kgs)<br>
							<input onblur="this.value=formato_numero(this.value,1)" size="16" id="cpCantidad" name="cpCantidad" maxlength="11" style="text-align: right;" type="text" value="">
						</td>
						
						<td style="font-family: Verdana; font-size: 11px; color: #666666; width: 5%">
							<br><a onclick="javascript: doAñadir(this)">
								<img src="img/add.png" title="Añadir Componente"/>
							</a>
						</td>
						<td colspan="1" style="font-family: Verdana; font-size: 11px; color: #666666; width: 40%">
								&nbsp;
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
		<td colspan="7" width="10%">
			
				<display:table name="LISTACOMPONENTESPRODUCTO" id="componente" export="false"
					cellpadding="0" cellspacing="0" summary="Tabla Listado"
					sort="external" class="" requestURI="/doListadoComponentesProducto.do"
					defaultsort="1" defaultorder="ascending" pagesize="10">
			
					<display:column class="columna porcen2" headerClass="columna porcen2"
						style="${estilo2009}" alt="selecc/deselec" function="checkUncheck()" src="img/marca_desmarca.gif" input="img">
							<input type="checkbox" id="checkList" name="checkList"
								class="check"
								value="${componente.cpId}" />
					</display:column>
					
					<display:column class="columna porcen10" headerClass="columna porcen10" 
						style="color: #F76D2D" title="Componente" sortable="false"> <c:out value="${componente.clDatosRelacionados[0]}"></c:out> </display:column>
						
					<fmt:formatNumber pattern="###,###.###" var="cantidad">${componente.cpCantidad}</fmt:formatNumber>
					<display:column class="columna porcen10" headerClass="columna porcen10"
						style="color: #F76D2D" title="Cantidad" sortable="false"> <c:out value="${cantidad}"></c:out> </display:column>
					
	    		</display:table>
			</td>
					</tr>
	</table>
</html:form>