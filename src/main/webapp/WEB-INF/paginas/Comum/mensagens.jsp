<%@ taglib prefix="html"      uri="/tlds/struts-html"  %>
<%@ taglib prefix="bean"      uri="/tlds/struts-bean"  %>
<%@ taglib prefix="logic"      uri="/tlds/struts-logic"  %>
<%@ taglib prefix="aa"        uri="/ajax/ajaxanywhere" %>

<aa:zone name="aaMensagens">
    <span id="mensagens">
        <!-- NAO REMOVER !!!! INICIOERRO-->
        <html:messages id="erro" header="errors.header" footer="errors.footer" bundle="comum">
            <bean:write name="erro" filter="false"/>
        </html:messages>
        <html:messages id="mensagem" message="true" header="messages.header" footer="messages.footer" bundle="comum">
            <bean:write name="mensagem" filter="false"/>
        </html:messages>
        <!--FIMERRO NAO REMOVER !!!-->
    </span>
    <logic:present name="erro">
        <script>
        	// Verifica se está dentro de uma popup
        	if (window.top!=null &&  window.top.document != window.document) {
        		window.top.avisarProcessamento(false);
        		// Tem alguma mensagem de erro ?
        		var msgsErro = getElementsByClassName('errors');
        		if (msgsErro!=null && msgsErro.length>0)
        		{
        			if (typeof window.top.closeOnError == "function") 
        			{
        				if (window.top.closeOnError())
        				{
			        		var texto = msgsErro[0].innerHTML;
			        		// remove <li>
			        		texto = texto.replace(/\<li\>/gi, '');
			        		// remove </li>
			        		texto = texto.replace(/<\/li>/gi, '');
				        	window.top.hidePopWin(false);
			        		window.top.alert(texto);
		        		}
        			}
        		}
        	}
        </script>
    </logic:present>
</aa:zone>