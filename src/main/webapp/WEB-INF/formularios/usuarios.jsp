<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/c-1_0-rt.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>

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
				<c:if test="${!empty Usuario.usId}">
					Usuario: ${Usuario.usNombre} ${Usuario.usApellidos}
				</c:if>	
			</span>
		  </td>
	    </tr>
	<tr>
		<td colspan="7" style="font-family: Verdana; font-size: 11px; color: #2DACE4;padding: 5px 0px 0px 5px">
			<img src="img/play.gif"/>&nbsp;Datos Usuarios<br>
			<hr style="color: #DFDEDE">
		</td>
	</tr>
	    <tr> 
          <td align="center" colspan='4' style="padding-left: 20px;"> 
			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="texto">
              <tr> 
                <td style='width: 15%' align="left" class="texto_negrita">Nombre</td>
                <td style='width: 40%' align="left"> 
				<html:text styleClass="js_requerido" size='28' maxlength="75" property="usNombre" name="usNombre" value="${Usuario.usNombre}"/>
				</td>
				<td style='width: 4%'>&nbsp;</td>
                <td style='width: 11%' align="left" class="texto_negrita">Apellidos</td>
                <td style='width: 30%' align="left"> 
				<html:text size='28' maxlength="75" property="usApellidos" name="usApellidos" value="${Usuario.usApellidos}"/>
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
                <td style='width: 15%' align="left" class="texto_negrita">Usuario</td>
                <td style='width: 40%' align="left">
					<html:text styleClass="js_requerido" size='28' maxlength="75" property="usLogin" name="usLogin" value="${Usuario.usLogin}"/>
				</td>
				<td style='width: 4%'>&nbsp;</td>
                <td style='width: 11%' align="left" class="texto_negrita">Contraseña</td>
                <td style='width: 30%' align="left"> 
				<html:password styleClass="js_requerido" size='28' maxlength="10" property="usPassw" name="usPassw" value="${Usuario.usPassw}"/>

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
					<html:text size='28' maxlength="75" property="usDireccion" name="usDireccion" value="${Usuario.usDireccion}"/>
				</td>
				<td style='width: 4%'>&nbsp;</td>
                <td style='width: 11%' align="left" class="texto_negrita">C.P.</td>
                <td style='width: 30%' align="left"> 
				<html:text size='28' maxlength="10" property="usCodigoPostal" name="usCodigoPostal" value="${Usuario.usCodigoPostal}"/>

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
					<html:text size='28' maxlength="10" property="usTelefono" name="usTelefono" value="${Usuario.usTelefono}"/>
				</td>
				<td style='width: 4%'>&nbsp;</td>
                <td style='width: 11%' align="left" class="texto_negrita">Movil</td>
                <td style='width: 30%' align="left"> 
				<html:text size='28' maxlength="10" property="usMovil" name="usMovil" value="${Usuario.usMovil}"/>

                </td>
              </tr>
            </table>
		  </td>
        </tr>
        <tr> 
          <td align="center" colspan='4' style="padding-left: 20px;"> 
			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="texto">
              <tr> 
                <td style='width: 15%' align="left" class="texto_negrita">NIF</td>
                <td style='width: 40%' align="left">
					<html:text size='28' maxlength="75" property="usNif" name="usNif" value="${Usuario.usNif}"/>
				</td>
				<td style='width: 4%'>&nbsp;</td>
                <td style='width: 11%' align="left" class="texto_negrita">Email</td>
                <td style='width: 30%' align="left"> 
				<html:text size='28' maxlength="75" property="usEmail" name="usEmail" value="${Usuario.usEmail}"/>
                </td>
              </tr>
            </table>
		  </td>
        </tr>
        <tr> 
          <td align="center" colspan='4' style="padding-left: 20px;"> 
			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="texto">
              <tr> 
                <td style='width: 15%' align="left" class="texto_negrita">Administrador</td>
                <td style='width: 40%' align="left">
					<html:select styleClass="js_requerido" style="width: 160px" styleId="usAdministrador" property="usAdministrador" value="${Usuario.usAdministrador}">
						  <html:option value="">[Seleccionar]</html:option>
						  <html:option value="1">Sí</html:option>
						  <html:option value="0">No</html:option>
					</html:select>
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
<html:hidden property="usId" value="${Usuario.usId}"/>
<!-- Fin Ficha Perfiles -->
