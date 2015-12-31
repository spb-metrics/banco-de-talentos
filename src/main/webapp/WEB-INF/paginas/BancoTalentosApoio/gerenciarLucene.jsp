<%@ page language="java" %>

<%@ taglib prefix="bean"      uri="/tlds/struts-bean"  %>
<%@ taglib prefix="html"      uri="/tlds/struts-html"  %>
<%@ taglib prefix="logic"     uri="/tlds/struts-logic" %>

<!-- replaced -->

<html:form action="reindexarLucene.do">

<table class="listagem" width="100%">
            <tr class="tituloTabelaListagem">
                <td class="tituloTabelaListagem" width="100%" colspan="2">Gerenciamento do Apache Lucene</td>
            </tr>
            
<tr>
            <td align="left"> 
    
            <strong> Atenção! Essa operação demanda tempo e processamento. Executá-la preferencialmente fora do expediente.   </strong>
            <html:submit styleClass="botaoPrincipal" >  Reindexação total  </html:submit>
       
            </td></tr>
 </table>


    <table>

        <tr> <td>
    
     </td></tr>
            </table>
</html:form>

