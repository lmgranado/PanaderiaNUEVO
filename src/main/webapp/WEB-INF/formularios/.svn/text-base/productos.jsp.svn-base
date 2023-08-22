<%-- /WEB-INF/modulos/seguridad/perfiles.ficha.jsp --%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/c-1_0-rt.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<script type="text/javascript">
	vFormName = "frmDetalle";
	function doSubmit(){
		vForm = document.getElementById(vFormName);		
		if( validateFields(vForm.name) ){
        	_enviarDetalle('frmDetalle');
        }
	}
	
</script>


<table style="background-color: white; border: 1px solid #E5E5E5; width: 100%">

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
			<img src="img/play.gif"/>&nbsp;Datos del Producto<br>
			<hr style="color: #DFDEDE">
		</td>
	</tr>
	    <tr> 
          <td align="center" colspan='4' style="padding-left: 20px;"> 
			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="texto">
              <tr> 
                <td style='width: 15%' align="left" class="texto_negrita">Referencia</td>
                <td style='width: 40%' align="left"> 
					<html:text styleClass="js_requerido" size='28' maxlength="30" property="prodReferencia" name="prodReferencia" value="${Producto.prodReferencia}"/>&nbsp;
				</td>
				<td style='width: 4%'>&nbsp;</td>
                <td style='width: 11%' align="left" class="texto_negrita">Nombre</td>
                <td style='width: 30%' align="left"> 
					<html:text styleClass="js_requerido" size='28' maxlength="30" property="prodNombre" name="prodNombre" value="${Producto.prodNombre}"/>&nbsp;
                </td>
              </tr>
            </table>
		  </td>
        </tr>
        <tr> 
          <td align="center" colspan='4' style="padding-left: 20px;"> 
			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="texto">
              <tr> 
                <td style='width: 15%' align="left" class="texto_negrita">Familia</td>
                <td style='width: 40%' align="left"> 
					<html:select  styleClass="js_requerido" style="width: 160px" styleId="prodFamId" property="prodFamId" value="${Producto.prodFamId}">
						  <html:option value="">[Seleccionar]</html:option>
						  <html:options collection="LISTAFAMILIAS" property="famId" labelProperty="famNombre"/>
					</html:select>&nbsp;
				</td>
				<td style='width: 4%'>&nbsp;</td>
                <td style='width: 11%' align="left" class="texto_negrita">Precio</td>
                <td style='width: 30%' align="left"> 
                	<fmt:formatNumber pattern="###,###.###" var="prodPrecioGeneral">${Producto.prodPrecioGeneral}</fmt:formatNumber>
					<html:text styleClass="js_requerido" size='28' maxlength="30" property="prodPrecioGeneral" name="prodPrecioGeneral"  onblur="this.value=formato_numero(this.value,1)" value="${prodPrecioGeneral}"/>&nbsp;
                </td>
              </tr>
            </table>
		  </td>
        </tr>
        <tr> 
          <td align="center" colspan='4' style="padding-left: 20px;"> 
			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="texto">
              <tr> 
                <td style='width: 15%' align="left" class="texto_negrita">Peso Total</td>
                <td style='width: 40%' align="left"> 
                	<fmt:formatNumber pattern="###,###.###" var="prodPesoTotal">${Producto.prodPesoTotal}</fmt:formatNumber>
					<html:text styleClass="js_requerido" size='28' maxlength="30" property="prodPesoTotal" name="prodPesoTotal"  onblur="this.value=formato_numero(this.value,1)" value="${prodPesoTotal}"/>&nbsp;
				</td>
				<td style='width: 4%'>&nbsp;</td>
                <td style='width: 11%' align="left" class="texto_negrita">Peso Masa</td>
                <td style='width: 30%' align="left"> 
                	<fmt:formatNumber pattern="###,###.###" var="prodPesoMasa">${Producto.prodPesoMasa}</fmt:formatNumber>
					<html:text styleClass="js_requerido" size='28' maxlength="30" property="prodPesoMasa" name="prodPesoMasa"  onblur="this.value=formato_numero(this.value,1)" value="${prodPesoMasa}"/>
                </td>
              </tr>
            </table>
		  </td>
        </tr>
        <tr> 
          <td align="center" colspan='4' style="padding-left: 20px;"> 
			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="texto">
              <tr> 
                <td style='width: 15%' align="left" class="texto_negrita">IVA</td>
                <td style='width: 40%' align="left"> 
                	<fmt:formatNumber pattern="###,###.###" var="prodIva">${Producto.prodIva}</fmt:formatNumber>
                	
					<html:select  styleClass="js_requerido"  style="width: 160px" styleId="prodIva" property="prodIva" value="${prodIva}">
						  <html:option value="">[Seleccionar]</html:option>
						  <html:option value="0">0%</html:option>
						  <html:option value="0,04">4%</html:option>
						  <html:option value="0,07">7%</html:option>
						  <html:option value="0,1">10%</html:option>
						  <html:option value="0,16">16%</html:option>
						  <html:option value="0,18">18%</html:option>
						  <html:option value="0,21">21%</html:option>
					</html:select>
				</td>
				<td style='width: 4%'>&nbsp;</td>
                <td style='width: 11%' align="left" class="texto_negrita">Cantidad por caja</td>
                <td style='width: 30%' align="left"> 
					<html:text size='28' maxlength="30" property="prodCantidadPorCaja" name="prodCantidadPorCaja"  onblur="this.value=formato_entero(this.value)" value="${Producto.prodCantidadPorCaja}"/>
                </td>
              </tr>
            </table>
		  </td>
        </tr>
</table>

<html:hidden property="prodId" value="${Producto.prodId}"/>
<html:hidden property="prodStock" value="0"/>
<html:hidden property="prodStockMinimo" value="0"/>
<!-- Fin Ficha Perfiles -->
