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

package br.gov.camara.negocio.bancotalentos.facade;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import br.gov.camara.exception.CDException;
import br.gov.camara.negocio.DAO;
import br.gov.camara.negocio.Facade;
import br.gov.camara.negocio.bancotalentos.dao.TipoFiltroConsultaDAO;
import br.gov.camara.negocio.bancotalentos.pojo.TipoFiltroConsulta;

/**
 * Facade para atributo de talento
 */
public class TipoFiltroConsultaFacade extends Facade
{
    // Variáveis de instância
    private static Log log = LogFactory.getLog(TipoFiltroConsultaFacade.class);

    /**
     * Obtém um registro a partir da chave
     *
     * @param strChave Chave do registro a ser obtido
     * 
     * @return Talento POJO representando o registro obtido
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     * 
     */
    public TipoFiltroConsulta obterPelaChave(String strChave) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obtém o registro pela chave
        TipoFiltroConsultaDAO objTipoFiltroConsultaDAO = new TipoFiltroConsultaDAO();
        TipoFiltroConsulta objTipoFiltroConsulta = null;
        try
        {
            objTipoFiltroConsulta = (TipoFiltroConsulta) objTipoFiltroConsultaDAO.obterPelaChave(strChave);
        }
        catch (Exception daoe)
        {
            CDException.dispararExcecao(daoe);
        }
        finally
        {
            DAO.desconectar();
        }
        return objTipoFiltroConsulta;
    }

    /**
     * Obtém todos os registros 
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     * 
     */
    public List obterTodos() throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obtém todos os registros
        TipoFiltroConsultaDAO objTipoFiltroConsultaDAO = new TipoFiltroConsultaDAO();
        List lstRetorno = null;
        try
        {
            lstRetorno = objTipoFiltroConsultaDAO.obterTodos();
        }
        catch (Exception daoe)
        {
            CDException.dispararExcecao(daoe);
        }
        finally
        {
            DAO.desconectar();
        }
        return lstRetorno;
    }

    /**
     * Inclui um registro
     *
     * @param objTalento POJO representando o objeto a ser incluído
     * @param lstAtributosTalentoValoros List contendo as valorações do talento a serem incluídas
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     *
     * @return retorna a chave do objeto que foi incluido
     *
     */
    public void incluir(TipoFiltroConsulta objTipoFiltroConsulta) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e inclui registro
        TipoFiltroConsultaDAO objTipoFiltroConsultaDAO = new TipoFiltroConsultaDAO();
        try
        {
            // Inicia transação
            DAO.iniciarTransacao();

            // Inclui TipoFiltroConsulta
            objTipoFiltroConsultaDAO.incluir(objTipoFiltroConsulta);

            // Realiza transação
            DAO.realizarTransacao();

        }
        catch (Exception daoe)
        {
            CDException.dispararExcecao(daoe);
        }
        finally
        {
            DAO.desconectar();
        }
    }

    /**
     * Altera um registro
     *
     * @param objTalento POJO representando o objeto a ser incluído
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     *
     */
    public void alterar(TipoFiltroConsulta objTipoFiltroConsulta) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e altera registro
        TipoFiltroConsultaDAO objTipoFiltroConsultaDAO = new TipoFiltroConsultaDAO();
        try
        {
            // Inicia transação
            DAO.iniciarTransacao();

            // Altera
            objTipoFiltroConsultaDAO.alterar(objTipoFiltroConsulta);

            // Realiza transação
            DAO.realizarTransacao();
        }
        catch (Exception daoe)
        {
            DAO.desfazerTransacao();
            CDException.dispararExcecao(daoe);
        }
        finally
        {
            DAO.desconectar();
        }
    }

    /**
     * Exclui um registro
     *
     * @param objTalento POJO representando o objeto a ser incluído
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     *
     */
    public void excluir(TipoFiltroConsulta objTipoFiltroConsulta) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e inclui registro
        TipoFiltroConsultaDAO objTipoFiltroConsultaDAO = new TipoFiltroConsultaDAO();
        try
        {
            // Inicia transação
            DAO.iniciarTransacao();

            // Exclui
            objTipoFiltroConsultaDAO.excluir(objTipoFiltroConsulta);

            // Realiza transação
            DAO.realizarTransacao();
        }
        catch (Exception daoe)
        {
            DAO.desfazerTransacao();
            CDException.dispararExcecao(daoe);
        }
        finally
        {
            DAO.desconectar();
        }
    }

    /**
     * Obtém os registros de determinada página
     *
     * @param int Número da página a ser mostrada
     * @param int Quantidade de registros
     *  
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     * 
     */
    public List obterPorPagina(int intNumeroPagina, int intMaximoPagina) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obtém os registros da página
        TipoFiltroConsultaDAO objTipoFiltroConsultaDAO = new TipoFiltroConsultaDAO();
        List lstRetorno = null;
        try
        {
            lstRetorno = objTipoFiltroConsultaDAO.obterPorPagina(intNumeroPagina, intMaximoPagina);
        }
        catch (Exception daoe)
        {
            CDException.dispararExcecao(daoe);
        }
        finally
        {
            DAO.desconectar();
        }
        return lstRetorno;
    }

    /**
     * Obtém o total de registros
     * 
     * @return int Contendo o total
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     * 
     */
    public int obterTotalRegistros() throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obtém os registros da página
        TipoFiltroConsultaDAO objTipoFiltroConsultaDAO = new TipoFiltroConsultaDAO();
        int intTotalRegistros = 0;
        try
        {
            intTotalRegistros = objTipoFiltroConsultaDAO.obterTotalRegistros();
        }
        catch (Exception daoe)
        {
            CDException.dispararExcecao(daoe);
        }
        finally
        {
            DAO.desconectar();
        }
        return intTotalRegistros;
    }

}
