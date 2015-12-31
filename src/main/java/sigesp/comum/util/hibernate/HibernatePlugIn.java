/****
 * Copyright 2000, 2001, 2002, 2003, 2004, 2005, 2006, 2007, 2008 Câmara dos Deputados, Brasil
 * 
 * Este arquivo é parte do programa TALENTOS - Banco de Talentos
 *
 * O TALENTOS é um software livre; você pode redistribuí-lo e/ou modificá-lo dentro dos termos da Licença Pública Geral GNU como publicada pela Fundação do Software Livre (FSF); na versão 2 da Licença.
 *
 * Este programa é distribuído na esperança que possa ser útil, mas SEM NENHUMA GARANTIA; sem uma garantia implícita de ADEQUAÇÃO a qualquer MERCADO ou APLICAÇÃO EM PARTICULAR. Veja a Licença Pública Geral GNU para maiores detalhes.
 * 
 * Você deve ter recebido uma cópia da Licença Pública Geral GNU, sob o título "LICENCA.txt", junto com este programa, se não, escreva para a Fundação do Software Livre(FSF) Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 ***/

package sigesp.comum.util.hibernate;

import javax.servlet.ServletException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.action.PlugIn;
import org.apache.struts.config.ModuleConfig;

import br.gov.camara.hibernate.HibernateUtilCD;
import br.gov.camara.hibernate.exception.HibernateExceptionCD;

// import sigesp.comum.util.hibernate.audit.AuditLogInterceptor;

/**
 * Initialize the Hibernate SessionFactory for this project as an application scope object.
 * 
 * @author Ted Husted
 * @version $Revision: 1.3 $ $Date: 2003/03/14 21:59:41 $
 */
public class HibernatePlugIn extends HibernateUtilCD implements PlugIn
{
    // Inicializa o log
    private static Log log = LogFactory.getLog(HibernatePlugIn.class);

    /**
     * DOCUMENTAR!
     * 
     * @param servlet DOCUMENTAR!
     * @param config DOCUMENTAR!
     * @throws ServletException DOCUMENTAR!
     */
    public void init(ActionServlet servlet, ModuleConfig config) throws ServletException
    {
        try
        {
            this.inicializar();
        }
        catch (HibernateExceptionCD he)
        {
            log.error(he);
            throw new ServletException(he);
        }
    }

    /**
     * DOCUMENTAR!
     */
    public void destroy()
    {
        if (log.isInfoEnabled())
        {
            log.info("Finalizando o HibernatePlugIn...");
        }

        this.finalizar();

        if (log.isDebugEnabled())
        {
            log.debug("O HibernatePlugIn foi finalizado");
        }
    }

}
