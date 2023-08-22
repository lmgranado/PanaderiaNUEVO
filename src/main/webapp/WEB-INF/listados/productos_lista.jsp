<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://jakarta.apache.org/taglibs/string-1.1" prefix="str" %>
<script>

	function doNuevo(){
		document.getElementById('frmListado').action='doProductosFormulario.do';
		document.getElementById('frmListado').submit();
	}
	

	function doBorrar(){
		if(hayChecked('frmListado')){
			if(confirm('Va a eliminar los registros seleccionados, ¿Desea continuar?')){
				document.getElementById('frmListado').action='doProductosBorrar.do';
				document.getElementById('frmListado').submit();
			}
		}else{
			alert('Debe marcar algún registro para eliminar');
		}
	}
	
	function doImprimirFichas(){
		if(hayChecked('frmListado')){
			if(confirm('Va a generar las fichas técnicas de los productos seleccionados, ¿Desea continuar?')){
				document.getElementById('frmListado').action='doGenerarFichasProductos.do';
				document.getElementById('frmListado').target="_new";
				document.getElementById('frmListado').submit();
				document.getElementById('frmListado').action='doListadoProdutos.do';
				document.getElementById('frmListado').target="";
			}
		}else{
			alert('Debe marca al menos un productos para generar la ficha técnica');
		}
	}
	
</script>
<html:form styleId="frmListado" method="post"
				action="/doListadoProductos.do">
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
			Referencia<br>
			<input id="tdNombre" size="30" name="prodReferencia" type="text" value="${FPRODUCTOS.prodReferencia}">
		</td>
		
		<td style="font-family: Verdana; font-size: 11px; color: #666666">
			Nombre<br>
			<input id="prodNombre" size="30" name="prodNombre" type="text" value="${FPRODUCTOS.prodNombre}">
		</td>
		
		<!--  <td style="font-family: Verdana; font-size: 11px; color: #666666">
			Peso Total<br>
			<SELECT id="peso" name="peso">
				<option value="menor">&le;</option>
				<option value="mayor">&gt;</option>
			</SELECT>
			&nbsp;<input id="prodPesoTotal" size="16" name="prodPesoTotal" type="text" value="${FPRODUCTOS.prodPesoTotal}">
		</td>
		
		<td style="font-family: Verdana; font-size: 11px; color: #666666">
			Precio<br>
			<SELECT id="precio" name="precio">
				<option value="menor">&le;</option>
				<option value="mayor">&gt;</option>
			</SELECT>
			&nbsp;<input id="prodPrecioGeneral" size="16" name="prodPrecioGeneral" type="text" value="${FPRODUCTOS.prodPrecioGeneral}">
		</td>-->
		
		
		<td style="font-family: Verdana; font-size: 11px; color: #666666">
            Familia<br>
			<html:select  style="width: 160px" styleId="prodFamId" property="prodFamId" value="${FPRODUCTOS.prodFamId}">
				  <html:option value="">[ TODAS ]</html:option>
				  <html:options collection="LISTAFAMILIAS" property="famId" labelProperty="famNombre"/>
			</html:select>&nbsp;
		</td>
				
		<td style="font-family: Verdana; font-size: 11px; color: #666666">
		<input id="filtro" name="filtro"  type="hidden" value="">
			<br><a onclick="javascript: doBuscar(this)">
				<img  src="img/buscar.gif" title="Aplicar filtro"/>
				</a>
				<a onclick="javascript: doLimpiar(this)">
				<img src="img/borrar.gif" title="Borrar filtro"/>
				</a>
		</td>
		<td width="40%"></td>
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
			
				<display:table name="LISTAPRODUCTOS" id="producto" export="false"
					cellpadding="0" cellspacing="0" summary="Tabla Listado"
					sort="external" class="" requestURI="/doListadoProductos.do"
					defaultsort="1" defaultorder="ascending" pagesize="10">
			
					<display:column class="columna porcen2" headerClass="columna porcen2"
						style="${estilo2009}" alt="selecc/deselec" function="checkUncheck()" src="img/marca_desmarca.gif" input="img">
							<input type="checkbox" id="checkList"
								class="check" name="checkList"
								value="${producto.prodId}" />
					</display:column>
			
					<display:column class="columna porcen10"
						headerClass="columna porcen10" property="prodReferencia"
						style="color: #F76D2D" title="Referencia" sortable="true"/>
			
					<display:column class="columna porcen10"
						headerClass="columna porcen10" property="prodNombre"
						style="color: #F76D2D" title="Nombre" sortable="true"/>
			
				
					<display:column class="columna porcen10" sortProperty="prodPrecioGeneral"
						headerClass="columna porcen10" style="color: #F76D2D" title="Precio (&euro;)" sortable="true">
						<fmt:formatNumber pattern="###,###.###" var="precioGeneral">${producto.prodPrecioGeneral}</fmt:formatNumber>
						${precioGeneral}
					</display:column>
						
					<display:column class="columna porcen10"
						headerClass="columna porcen10"
						style="color: #F76D2D" title="IVA">
						<fmt:formatNumber pattern="###,###.###" var="iva">${producto.prodIva*100}</fmt:formatNumber>
						${iva} %
					</display:column>
								
					<display:column class="columna porcen2" headerClass="columna porcen2"
						style="${estilo2009}" title="Editar">
							<a href="doProductosFormulario.do?prodId=${producto.prodId}">
								<img src="img/editar.gif">
							</a>
					</display:column>
				</display:table>
				
			</td>
		</tr>
	</table>
</html:form>