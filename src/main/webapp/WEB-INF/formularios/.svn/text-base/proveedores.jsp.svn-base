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
		if(validateFields(vForm.name)){	
        	_enviarDetalle('frmDetalle');
        }
	}

	
	function devolverDatosMunicipio(municipio, provincia)
	{
	 var campoProvincia = document.getElementById(provincia);
	 var idProvincia = campoProvincia[campoProvincia.selectedIndex].value;

	 var url = 'doCargaProvincias.do?provincia='+idProvincia;
	 
	 // Ponemos en blanco
	 document.getElementById(municipio).length = 0;
	 
	 setTimeout("loadXMLDoc('"+url+"','"+municipio+"')", 1000); 
	}
	
</script>


<table style="background-color: white; border: 1px solid #E5E5E5; width: 100%">
	    <tr>          
		  <td colspan="4" align="right">
		  	<span style='color: #FA7D3D; font-size: 12px; font-weight: bold;'>
				<c:if test="${!empty Proveedor.prId}">
					Proveedor: ${Proveedor.prNombre} ${Proveedor.prApellidos}
				</c:if>	
			</span>
		  </td>
	    </tr>
	<tr>
		<td colspan="7" style="font-family: Verdana; font-size: 11px; color: #2DACE4;padding: 5px 0px 0px 5px">
			<img src="img/play.gif"/>&nbsp;Datos Proveedors<br>
			<hr style="color: #DFDEDE">
		</td>
	</tr>
	    <tr> 
          <td align="center" colspan='4' style="padding-left: 20px;"> 
			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="texto">
              <tr> 
                <td style='width: 15%' align="left" class="texto_negrita">Nombre</td>
                <td style='width: 40%' align="left"> 
				<html:text size='28' styleClass="js_requerido" maxlength="75" property="prNombre" name="prNombre" value="${Proveedor.prNombre}"/>
				</td>
				<td style='width: 4%'>&nbsp;</td>
                <td style='width: 11%' align="left" class="texto_negrita">Apellidos</td>
                <td style='width: 30%' align="left"> 
				<html:text size='28' styleClass="js_requerido" maxlength="75" property="prApellidos" name="prApellidos" value="${Proveedor.prApellidos}"/>
                </td>
              </tr>
            </table>
		  </td>
        </tr>
		<!-- Fin Linea 21 -->
		<!-- Linea 22 -->
        <tr> 
          <td align="center" colspan='4' style="padding-left: 20px;"> 
			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="texto">
              <tr> 
                <td style='width: 15%' align="left" class="texto_negrita">Nombre Co.</td>
                <td style='width: 40%' align="left">
					<html:text size='28' styleClass="js_requerido   " maxlength="75" property="prNombreComercial" name="prNombreComercial" value="${Proveedor.prNombreComercial}"/>
				</td>
				<td style='width: 4%'>&nbsp;</td>
                <td style='width: 11%' align="left" class="texto_negrita">NIF</td>
                <td style='width: 30%' align="left"> 
				<html:text size='28' styleClass="js_requerido   " maxlength="10" property="prNif" name="prNif" value="${Proveedor.prNif}"/>

                </td>
              </tr>
            </table>
		  </td>
        </tr>
        <tr> 
          <td align="center" colspan='4' style="padding-left: 20px;"> 
			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="texto">
              <tr> 
                <td style='width: 15%' align="left" class="texto_negrita">Dirección</td>
                <td style='width: 40%' align="left">
					<html:text size='28' maxlength="75" styleClass="js_requerido   " property="prDireccion" name="prDireccion" value="${Proveedor.prDireccion}"/>
				</td>
				<td style='width: 4%'>&nbsp;</td>
                <td style='width: 11%' align="left" class="texto_negrita">C.P.</td>
                <td style='width: 30%' align="left"> 
				<html:text size='28' maxlength="10" styleClass="js_codigopostal" property="prCodigoPostal" name="prCodigoPostal" value="${Proveedor.prCodigoPostal}"/>

                </td>
              </tr>
            </table>
		  </td>
        </tr>
        <tr> 
          <td align="center" colspan='4' style="padding-left: 20px;"> 
			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="texto">
              <tr> 
                <td style='width: 15%' align="left" class="texto_negrita">Provincia</td>
                <td style='width: 40%' align="left">
					<html:select  styleClass="js_requerido   " style="width: 160px" styleId="prProvId" onchange="javascript: devolverDatosMunicipio('prLocId','prProvId');" property="prProvId" value="${Proveedor.prProvId}">
						  <html:option value="">[Seleccionar]</html:option>
						  <html:options property="provId" labelProperty="provNombre" collection="LISTAPROVINCIAS"/>
					</html:select>
				</td>
				<td style='width: 4%'>&nbsp;</td>
                <td style='width: 11%' align="left" class="texto_negrita">Localidad</td>
                <td style='width: 30%' align="left"> 
					<html:select  styleClass="js_requerido   " style="width: 160px" styleId="prLocId" property="prLocId" value="${Proveedor.prLocId}">
						  <html:option value="">[Seleccionar]</html:option>
						  <html:options property="locId" labelProperty="locNombre" collection="LISTALOCALIDADES"/>
					</html:select>

                </td>
              </tr>
            </table>
		  </td>
        </tr>
        <tr> 
          <td align="center" colspan='4' style="padding-left: 20px;"> 
			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="texto">
              <tr> 
                <td style='width: 15%' align="left" class="texto_negrita">Telefono</td>
                <td style='width: 40%' align="left">
					<html:text size='28' styleClass="js_requerido js_telefono " maxlength="10" property="prTelefono" name="prTelefono" value="${Proveedor.prTelefono}"/>
				</td>
				<td style='width: 4%'>&nbsp;</td>
                <td style='width: 11%' align="left" class="texto_negrita">Movil</td>
                <td style='width: 30%' align="left"> 
				<html:text size='28' maxlength="10" property="prMovil" name="prMovil" value="${Proveedor.prMovil}"/>

                </td>
              </tr>
            </table>
		  </td>
        </tr>
        <tr> 
          <td align="center" colspan='4' style="padding-left: 20px;"> 
			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="texto">
              <tr> 
                <td style='width: 15%' align="left" class="texto_negrita">Persona Contacto</td>
                <td style='width: 40%' align="left">
					<html:text size='28' maxlength="75" styleClass="js_requerido   " property="prPersonaContacto" name="prPersonaContacto" value="${Proveedor.prPersonaContacto}"/>
				</td>
				<td style='width: 4%'>&nbsp;</td>
                <td style='width: 11%' align="left" class="texto_negrita">Fax</td>
                <td style='width: 30%' align="left"> 
				<html:text size='28' maxlength="10" property="prFax" name="prFax" value="${Proveedor.prFax}"/>
                </td>
              </tr>
            </table>
		  </td>
        </tr>
        <tr> 
          <td align="center" colspan='4' style="padding-left: 20px;"> 
			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="texto">
              <tr> 
                <td style='width: 15%' align="left" class="texto_negrita">Email</td>
                <td style='width: 40%' align="left">
					<html:text size='28' maxlength="75" property="prEmail" styleClass="jsvalidate_email" name="prEmail" value="${Proveedor.prEmail}"/>
				</td>
				<td style='width: 4%'>&nbsp;</td>
                <td style='width: 11%' align="left" class="texto_negrita">Web</td>
                <td style='width: 30%' align="left"> 
				<html:text size='28' maxlength="75" property="prWeb" name="prWeb" value="${Proveedor.prWeb}"/>
                </td>
              </tr>
            </table>
		  </td>
        </tr>
	<tr>
		<td colspan="7" style="font-family: Verdana; font-size: 11px; color: #2DACE4;padding: 5px 0px 0px 5px">
			<img src="img/play.gif"/>&nbsp;Condiciones de facturación<br>
			<hr style="color: #DFDEDE">
		</td>
	</tr>
	
	
        <tr> 
          <td align="center" colspan='4' style="padding-left: 20px;"> 
			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="texto">
              <tr> 
                <td style='width: 15%' align="left" class="texto_negrita">Cuenta Bancaria</td>
                <td style='width: 40%' align="left">
					<html:text size='28' maxlength="75" property="prCcc" name="prCcc" value="${Proveedor.prCcc}"/>
				</td>
				<td style='width: 4%'>&nbsp;</td>
                <td style='width: 11%' align="left" class="texto_negrita">Banco</td>
                <td style='width: 30%' align="left"> 
					<html:text size='28' maxlength="75" property="prNombreBanco" name="prNombreBanco" value="${Proveedor.prNombreBanco}"/>
                </td>
              </tr>
            </table>
		  </td>
        </tr>
        <tr> 
          <td align="center" colspan='4' style="padding-left: 20px;"> 
			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="texto">
              <tr> 
                <td style='width: 15%' align="left" class="texto_negrita">Forma Pago</td>
                <td style='width: 40%' align="left">
					<html:select  style="width: 160px" styleClass="js_requerido    " styleId="prFpId" property="prFpId" value="${Proveedor.prFpId}">
						  <html:option value="">[Seleccionar]</html:option>
						  <html:options property="fpId" labelProperty="fpDescripcion" collection="LISTAFORMASPAGO"/>
					</html:select>
				</td>
				<td style='width: 4%'>&nbsp;</td>
                <td style='width: 11%' align="left" class="texto_negrita">Periodo Facturación</td>
                <td style='width: 30%' align="left"> 
					<html:select  style="width: 160px" styleId="prPfId" styleClass="js_requerido" property="prPfId" value="${Proveedor.prPfId}">
						  <html:option value="">[Seleccionar]</html:option>
						  <html:options property="pfId" labelProperty="pfDescripcion" collection="LISTAPERIODOS"/>
					</html:select>
                </td>
              </tr>
            </table>
		  </td>
        </tr>
	
        <tr> 
          <td align="center" colspan='4' style="padding-left: 20px;"> 
			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="texto">
              <tr> 
                <td style='width: 15%;' align="left" class="texto_negrita">Descuento</td>
                <td style='width: 40%;' align="left" class="texto_negrita">
                	<fmt:formatNumber pattern="###,###.###" var="prDescuento">${Proveedor.prDescuento*100}</fmt:formatNumber>
					<html:text size='28' maxlength="5" property="prDescuento" onblur="javascript:this.value=formato_numero(this.value,1)" name="prDescuento" value="${prDescuento}"/>%
				</td>
				<td style='width: 4%'>&nbsp;</td>
                <td style='width: 11%' align="left" class="texto_negrita">&nbsp;</td>
                <td style='width: 30%' align="left"> 
				&nbsp;
                </td>
              </tr>
            </table>
		  </td>
        </tr>
</table>

<script>
		setTimeout("devolverDatosMunicipio('prLocId','prProvId')",0);
		setTimeout("aplicarValor('${Proveedor.prLocId}','prLocId')",300);
</script>
<html:hidden property="prId" value="${Proveedor.prId}"/>
<!-- Fin Ficha Perfiles -->
