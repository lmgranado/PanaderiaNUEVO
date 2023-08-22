<%-- /WEB-INF/modulos/seguridad/perfiles.ficha.jsp --%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/c-1_0-rt.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<script type="text/javascript">
	vFormName = "frmDetalle";
	
	function doSubmit(){
		vForm = document.getElementById(vFormName);		
		document.getElementById('frmDetalle').action = 'doProductoFichaFormularioGrabar.do';
        _enviarDetalle('frmDetalle');		
	}
	
	function doAnadirDetalle(){
		vForm = document.getElementById(vFormName); 

		if(document.getElementById('prodcompNombre').value==''){
			alert('Debe indicar un nombre');
			return;
		}/*else if(document.getElementById('prodcompDescripcion').value==''){
			alert('Debe indicar una descripcion');
			return;
		}*/else if( formato_numero(document.getElementById('prodcompPeso').value) == '' ){
			return;
		}else if( formato_numero(document.getElementById('prodcompPeso').value) <= formato_numero(0)){
			alert('El peso debe ser mayor que 0');
			return;
		}else if(document.getElementById('prodcompMedida').value==''){
			alert('Debe indicar una medida');
			return;
		}else{ 
			document.getElementById('frmDetalle').action='doProductoComposicionFormularioGrabar.do';
			document.getElementById('frmDetalle').submit();
		}
	}
	
	function doEliminarDetalle(){
		vForm = document.getElementById(vFormName);				
		var idDetalles = hayChecked(vForm);
		if( idDetalles == '-1' ){
			alert('Debe elegir al menos un compuesto para eliminar');
		}
		else{
			document.getElementById('frmDetalle').action = 'doProductoComposicionBorrar.do';
			_enviarDetalle('frmDetalle');
		}
	}
	
	/* Comprueba se hay algún paroducto seleccionado, devuelve -1 si no hay ninguno */
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

<html:form styleId="frmDetalle" action="/doProductoFichaFormularioGrabar" method="post">
<table cellpadding="5px" style="background-color: white; border: 1px solid #E5E5E5; width: 100%">
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
			<img src="img/play.gif"/>&nbsp;Datos Básicos de la Ficha<br>
			<hr style="color: #DFDEDE">
		</td>
	</tr>
		<tr> 
          <td align="center" colspan='4' style="padding-left: 20px;"> 
			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="texto">
              <tr> 
                <td style='width: 2%' align="left"></td>
                <td style='width: 40%' align="left"  class="texto_negrita">
                	Nombre de la Ficha<br> 
					<html:text size='91' maxlength="75"  property="prodfNombreFicha" name="prodfNombreFicha" value="${FichaProducto.prodfNombreFicha}"/>
				</td>
				<td style='width: 5%' align="left" ></td>
                <td style='width: 40%' align="left" class="texto_negrita">
	            </td>
              </tr>
            </table>
		  </td>
        </tr>	
		<tr> 
          <td align="center" colspan='4' style="padding-left: 20px;"> 
			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="texto">
              <tr> 
                <td style='width: 2%' align="left"></td>
                <td style='width: 40%' align="left"  class="texto_negrita">
                	Denominación Comercial<br> 
					<html:text size='91' maxlength="75"  property="prodfDenominacionComercial" name="prodfDenominacionComercial" value="${FichaProducto.prodfDenominacionComercial}"/>
				</td>
				<td style='width: 5%' align="left" ></td>
                <td style='width: 40%' align="left" class="texto_negrita">
                	Clasificación del producto según legislación vigente<br> 
					<html:text size='91' maxlength="75"  property="prodfClasificacionLegislacion" name="prodfClasificacionLegislacion" value="${FichaProducto.prodfClasificacionLegislacion}"/>
	            </td>
              </tr>
            </table>
		  </td>
        </tr>	
        
        <tr> 
          <td align="center" colspan='4' style="padding-left: 20px;"> 
			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="texto">
              <tr> 
                <td style='width: 2%' align="left"></td>
                <td style='width: 40%' align="left"  class="texto_negrita">
                	Clasificación del producto según industria<br> 
					<html:text size='91' maxlength="75"  property="prodfClasificacionIndustria" name="prodfClasificacionIndustria" value="${FichaProducto.prodfClasificacionIndustria}"/>
				</td>
				<td style='width: 5%' align="left" ></td>
                <td style='width: 40%' align="left" class="texto_negrita">
                	Marca comercial sobre la que se elabora y envasa<br> 
					<html:text size='91' maxlength="75"  property="prodfMarcaComercial" name="prodfMarcaComercial" value="${FichaProducto.prodfMarcaComercial}"/>
	            </td>
              </tr>
            </table>
		  </td>
        </tr>	
        
        <tr> 
          <td align="center" colspan='4' style="padding-left: 20px;"> 
			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="texto">
              <tr> 
                <td style='width: 2%' align="left"></td>
                <td style='width: 40%' align="left"  class="texto_negrita">
                	Procesado<br> 
					<html:textarea styleClass="textarea" rows="3"  cols="65" property="prodfProcesado" name="prodfProcesado" value="${FichaProducto.prodfProcesado}"/>
				</td>
				<td style='width: 5%' align="left" ></td>
                <td style='width: 40%' align="left" class="texto_negrita">
                	Formato de Presentación<br>
					<html:textarea styleClass="textarea" rows="3"  cols="65" property="prodfFormato" name="prodfFormato" value="${FichaProducto.prodfFormato}"/>
	            </td>
              </tr>
            </table>
		  </td>
        </tr>	
        
        <tr> 
          <td align="center" colspan='4' style="padding-left: 20px;"> 
			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="texto">
              <tr> 
                <td style='width: 2%' align="left"></td>
                <td style='width: 40%' align="left"  class="texto_negrita">
                	Envasado (cuando el cliente es una colectividad)<br>
					<html:textarea styleClass="textarea" rows="3"  cols="65" property="prodfEnvasado" name="prodfEnvasado" value="${FichaProducto.prodfEnvasado}"/>
				</td>
				<td style='width: 5%' align="left" ></td>
                <td style='width: 40%' align="left" class="texto_negrita">
                	Etiquetado<br> 
					<html:textarea styleClass="textarea" rows="3"  cols="65" property="prodfEtiquetado" name="prodfEtiquetado" value="${FichaProducto.prodfEtiquetado}"/>
	            </td>
              </tr>
            </table>
		  </td>
        </tr>	
        
        <tr> 
          <td align="center" colspan='4' style="padding-left: 20px;"> 
			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="texto">
              <tr> 
                <td style='width: 2%' align="left"></td>
                <td style='width: 40%' align="left"  class="texto_negrita">
                	Condiciones de almacenamiento<br>
					<html:textarea styleClass="textarea" rows="3"  cols="65" property="prodfCondicionAlmacenamiento" name="prodfCondicionAlmacenamiento" value="${FichaProducto.prodfCondicionAlmacenamiento}"/>
				</td>
				<td style='width: 5%' align="left" ></td>
                <td style='width: 40%' align="left" class="texto_negrita">
                	Condiciones de transporte<br>
					<html:textarea styleClass="textarea" rows="3"  cols="65" property="prodfCondicionTransporte" name="prodfCondicionTransporte" value="${FichaProducto.prodfCondicionTransporte}"/>
	            </td>
              </tr>
            </table>
		  </td>
        </tr>	
        
        <tr> 
          <td align="center" colspan='4' style="padding-left: 20px;"> 
			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="texto">
              <tr> 
                <td style='width: 2%' align="left"></td>
                <td style='width: 40%' align="left"  class="texto_negrita">
                	Destino final previsto para el producto<br>
					<html:textarea styleClass="textarea" rows="3"  cols="65" property="prodfDestinoProducto" name="prodfDestinoProducto" value="${FichaProducto.prodfDestinoProducto}"/>
				</td>
				<td style='width: 5%' align="left" ></td>
                <td style='width: 40%' align="left" class="texto_negrita">
                	Caracteristicas del número de lote (coincide con la fecha de fabricación F/F)<br>
					<html:textarea styleClass="textarea" rows="3"  cols="65" property="prodfCaracteristicaLote" name="prodfCaracteristicaLote" value="${FichaProducto.prodfCaracteristicaLote}"/>
	            </td>
              </tr>
            </table>
		  </td>
        </tr>	
    	
    	<tr> 
          <td align="center" colspan='4' style="padding-left: 20px;"> 
			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="texto">
              <tr> 
                <td style='width: 2%' align="left"></td>
                <td style='width: 40%' align="left"  class="texto_negrita">
                	Consumo preferente (C/P)<br>
					<html:textarea styleClass="textarea" rows="3"  cols="65" property="prodfConsumoPreferente" name="prodfConsumoPreferente" value="${FichaProducto.prodfConsumoPreferente}"/>
				</td>
				<td style='width: 5%' align="left" ></td>
                <td style='width: 40%' align="left" class="texto_negrita">
                	Características del producto terminado (R.D. 1137/1984, y posteriores modificaciones)<br>
					<html:textarea styleClass="textarea" rows="3"  cols="65" property="prodfCaracteristicaProducto" name="prodfCaracteristicaProducto" value="${FichaProducto.prodfCaracteristicaProducto}"/>
	            </td>
              </tr>
            </table>
		  </td>
        </tr>		
        
        <tr ><td height="20px"></td></tr>  
        <c:if test="${!empty FichaProducto.prodfId}">
	        <tr>
			<td colspan="1" align=left style="font-family: Verdana; font-size: 11px; color: #2DACE4;padding: 5px 0px 0px 5px">
				<img src="img/play.gif"/>&nbsp;Composición cualitativa y cuantitativa<br>
				<hr style="color: #DFDEDE">
				<table width="40%">
					<tr>
						<td style="font-family: Verdana; font-size: 11px; color: #666666; width: 10%;padding-left: 20px;">
								Nombre<br>
								<input size="30" id="prodcompNombre" style="text-align: left;" name="prodcompNombre" maxlength="11"  type="text" value="">
							</td>
							<td style="font-family: Verdana; font-size: 11px; color: #666666; width: 10%">
								Descripción<br>
								<input size="40" id="prodcompDescripcion" style="text-align: left;" name="prodcompDescripcion" maxlength="11"  type="text" value="">
							</td>
							<td style="font-family: Verdana; font-size: 11px; color: #666666; width: 10%">
								Peso<br>
								<input  size="16" id="prodcompPeso" style="text-align: right;" name="prodcompPeso" maxlength="11"  type="text" value="">
							</td>
							<td style="font-family: Verdana; font-size: 11px; color: #666666; width: 10%">
								Medida<br>
								<input size="16" id="prodcompMedida" style="text-align: right;" name="prodcompMedida" maxlength="11"  type="text" value="">
							</td>
								<td style="font-family: Verdana; font-size: 11px; color: #666666; width: 5%">
									<a onclick="javascript: doAnadirDetalle();">
										<img src="img/add.png" title="anadir composicion"/>
									</a>
								</td>
								<td style="font-family: Verdana; font-size: 11px; color: #666666; width: 5%">
									<a onclick="javascript: doEliminarDetalle();"> 
										<img  src="img/remove.png" title="eliminar composicion"/>
									</a>
								</td>
						</tr>
					</table>	
			</td>
			</tr>
	        <tr>
				<td style="width: 60%;padding-left: 20px;" colspan="1">
					<table cellpadding="1" class="displaytag_tabla" cellspacing="1" style="margin-bottom: 20px">
					<thead>
							<tr class="${estilo}">		
								<th class="columna sortable">
									&nbsp;
								</th>					
								<th class="columna sortable">
									Nombre
								</th>							
								<th class="columna sortable">
									Descripción
								</th>
								<th class="columna sortable">
									Peso
								</th>
							</tr>
						</thead>
						<c:forEach items="${LISTACOMPOSICIONFICHA}" var="composicion">
							<tr class="${estilo}">
								<td class="columna" style="text-align: center;">
									<input type="checkbox" name="checkList" id="checkList" class="check" value="${composicion.prodcompId}"/>
								</td>
								<td class="columna" style="text-align: left;">
									<c:if test="${empty composicion.prodcompNombre}">&nbsp;</c:if>
									<c:if test="${!empty composicion.prodcompNombre}">
										<c:out value="${composicion.prodcompNombre}"/>
									</c:if>
								</td>
								<td class="columna" style="text-align: left;">
									<c:if test="${empty composicion.prodcompDescripcion}">&nbsp;</c:if>
									<c:if test="${!empty composicion.prodcompDescripcion}">
										<c:out value="${composicion.prodcompDescripcion}"/>
									</c:if>
								</td>
								<td class="columna" style="text-align: right;">
									<c:if test="${empty composicion.prodcompPeso}">&nbsp;</c:if>
									<c:if test="${!empty composicion.prodcompPeso}">
										<fmt:formatNumber pattern="###,###.###" var="peso">${composicion.prodcompPeso}</fmt:formatNumber>
										<c:out value="${peso}  ${composicion.prodcompMedida}"/>
									</c:if>
								</td>
							</tr>						
						</c:forEach>
					</table>
				</td>
				<td width="40%" style="vertical-align: top;"></td>
			</tr>
		</c:if>
</table>

<html:hidden property="prodfId" styleId="prodfId" value="${FichaProducto.prodfId}"/>
<html:hidden property="prodfProdId" styleId="prodfProdId" value="${prodfProdId}"/>
</html:form> 
