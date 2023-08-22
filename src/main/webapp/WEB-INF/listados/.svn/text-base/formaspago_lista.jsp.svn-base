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
				document.getElementById('frmListado').action='doFormasPagoGrabar.do?delete=1';
				document.getElementById('frmListado').submit();
			}
		}else{
			alert('Debe marcar algún registro para eliminar');
		}
	}
	
	function doAñadir(){
		if(document.getElementById('fpDescripcion').value==''){
			alert('Debe indicar la descripción de la forma de pago');
			return;
		}else{ 
			document.getElementById('frmListado').action='doFormasPagoGrabar.do';
			document.getElementById('frmListado').submit();
		}
	}
</script>
<html:form styleId="frmListado" method="post" action="/doListadoFormasPago.do">
<table style="background-color: white; border: 1px solid #E5E5E5; width: 100%">
	<html:hidden property="fpId" value="${fpId}"/>
	<tr>
		<td colspan="4" style="font-family: Verdana; font-size: 11px; color: #2DACE4;padding: 5px 0px 0px 5px">
			<img src="img/play.gif"/>&nbsp;Insertar Nueva Forma de Pago<br>
			<hr style="color: #DFDEDE">
		</td>
	</tr>
	<tr>	
		<td style="width: 2%">
			&nbsp;
		</td>
		<td style="font-family: Verdana; font-size: 11px; color: #666666; width: 12%">
			Descripción Forma de Pago<br>
			<input size="30" id="fpDescripcion" name="fpDescripcion" maxlength="50"  type="text" value="">
		</td>
		
		<td style="font-family: Verdana; font-size: 11px; color: #666666; width: 10%">
			<br><a onclick="javascript: doAñadir(this)">
				<img src="img/add.png" title="Añadir Ruta"/>
				</a>
		</td>
		<td style="width: 66%">
			&nbsp;
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
		<td colspan="7" width="50%">
			
				<display:table name="LISTAFORMASPAGO" id="formas" export="false"
					cellpadding="0" cellspacing="0" summary="Tabla Listado"
					sort="external" class="" requestURI="/doListadoFormasPago.do"
					defaultsort="1" defaultorder="ascending" pagesize="10">
			
					<display:column class="columna porcen2" headerClass="columna porcen2"
						style="${estilo2009}" alt="selecc/deselec" function="checkUncheck()" src="img/marca_desmarca.gif" input="img">
							<input type="checkbox" id="checkList" name="checkList"
								class="check"
								value="${formas.fpId}" />
					</display:column>
					
					<%--<display:column class="columna porcen10"
						headerClass="columna porcen10" property="fpId"
						style="color: #F76D2D" title="Código" sortable="true"/>--%>
						
					<display:column class="columna porcen30"
						headerClass="columna porcen10" property="fpDescripcion"
						style="color: #F76D2D" title="Descripción" sortable="true"/>
					
	    		</display:table>
			</td>
		</tr>
	</table>
</html:form>