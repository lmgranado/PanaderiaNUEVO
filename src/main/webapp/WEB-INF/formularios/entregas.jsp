<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<script type="text/javascript">
	vFormName = "frmDetalle";
	function doSubmit(){
		vForm = document.getElementById(vFormName);		
		if(validateFields(vForm.name)){	
			_enviarDetalle('frmDetalle');
			window.opener.document.getElementById('frmListado').action = 'doListadoEntregas.do';
			window.opener.document.getElementById('frmListado').submit();
		}
	}
	

</script>
<html:form action="/doEntregasFormulario" styleId="frmDetalle" method="post">	
    <table class="displaytag_tabla" style="margin: 0px 20px; width: 95%;" cellpadding="2" cellspacing="2">
    	<tr>
			<td align="left" class="texto_negrita" style="font-weight: bold; text-decoration: underline;">
				Modifique el nombre de la ruta:
			</td>
		</tr>
    	<tr>
    		<td style="font-family: Verdana; font-size: 11px; color: #666666; width: 12%">
				Nombre<br>
				<input size="25" class="js_requerido" id="entNombre" name="entNombre" maxlength="64"  type="text" value="${entregas.entNombre}">
			</td>
		</tr>
		<tr>
			<td align="center" colspan="2">
				<a onclick="javascript: doSubmit();"><img src="img/ok.png"></a>&nbsp;
				<a onclick="javascript: doCerrar();"><img src="img/ko.png"></a>
			</td>
		</tr>
	</table>
	<html:hidden property="entId" value="${entregas.entId}"/>
	<input type='hidden' value="true" name="editar" />
	
</html:form>