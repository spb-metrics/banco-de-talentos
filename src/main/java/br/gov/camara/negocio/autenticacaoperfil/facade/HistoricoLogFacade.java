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

package br.gov.camara.negocio.autenticacaoperfil.facade;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import br.gov.camara.exception.CDException;
import br.gov.camara.negocio.DAO;
import br.gov.camara.negocio.Facade;
import br.gov.camara.negocio.autenticacaoperfil.dao.HistoricoLogDAO;
import br.gov.camara.negocio.autenticacaoperfil.pojo.HistoricoLog;

/**
 * Facade para atributo de talento
 */
public class HistoricoLogFacade extends Facade
{
    // Variáveis de instância
    private static Log log = LogFactory.getLog(HistoricoLogFacade.class);

    /**
     * Construtor Default
     */
    public HistoricoLogFacade()
    {
        super();
    }

    /**
     * @param nomeConexao
     *
     */
    public HistoricoLogFacade(String nomeConexao)
    {
        super(nomeConexao);
    }

    /**
     * Obtém um registro a partir da chave
     *
     * @param strChave Chave do registro a ser obtido
     * 
     * @return Grupo POJO representando o registro obtido
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     * 
     */
    public HistoricoLog obterPelaChave(String strChave) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obtém o registro pela chave
        HistoricoLogDAO objHistoricoLogDAO = new HistoricoLogDAO(obterNomeConexao());
        HistoricoLog objHistoricoAuditoria= null;
        try
        {
            objHistoricoAuditoria = (HistoricoLog) objHistoricoLogDAO.obterPelaChave(strChave);
        }
        catch (Exception daoe)
        {
            CDException.dispararExcecao(daoe);
        }
        finally
        {
            DAO.desconectar(obterNomeConexao());
        }
        return objHistoricoAuditoria;
    }

    /**
     * Inclui um registro
     *
     * @param objGrupo POJO representando o objeto a ser incluído
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     *
     * @return retorna a chave do objeto que foi incluido
     *
     */
    public String incluir(HistoricoLog objHistoricoLog) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        String strChave = null;

        // Instancia DAO e inclui registro
        HistoricoLogDAO objHistoricoLogDAO = new HistoricoLogDAO(obterNomeConexao());
        try
        {
            strChave = objHistoricoLogDAO.incluir(objHistoricoLog);
        }
        catch (Exception daoe)
        {
            CDException.dispararExcecao(daoe);
        }
        finally
        {
            DAO.desconectar(obterNomeConexao());
        }

        return strChave;
    }

    /**
     * Altera um registro
     *
     * @param objGrupo POJO representando o objeto a ser incluído
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     *
     */
    public void alterar(HistoricoLog objHistoricoAuditoria) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e altera registro
        HistoricoLogDAO objHistoricoLogDAO = new HistoricoLogDAO(obterNomeConexao());
        try
        {
            objHistoricoLogDAO.alterar(objHistoricoAuditoria);
        }
        catch (Exception daoe)
        {
            CDException.dispararExcecao(daoe);
        }
        finally
        {
            DAO.desconectar(obterNomeConexao());
        }
    }

    /**
     * Exclui um registro
     *
     * @param objGrupo POJO representando o objeto a ser incluído
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     *
     */
    public void excluir(HistoricoLog objHistoricoAuditoria) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e exclui registro
        HistoricoLogDAO objHistoricoLogDAO = new HistoricoLogDAO(obterNomeConexao());
        try
        {
            objHistoricoLogDAO.excluir(objHistoricoAuditoria);
        }
        catch (Exception daoe)
        {
            CDException.dispararExcecao(daoe);
        }
        finally
        {
            DAO.desconectar(obterNomeConexao());
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
    public List obterTodos() throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obtém os registros da página
        HistoricoLogDAO objHistoricoLogDAO = new HistoricoLogDAO(obterNomeConexao());
        List lstRetorno = null;
        try
        {
            lstRetorno = objHistoricoLogDAO.obterTodos();
        }
        catch (Exception daoe)
        {
            CDException.dispararExcecao(daoe);
        }
        finally
        {
            DAO.desconectar(obterNomeConexao());
        }
        return lstRetorno;
    }

}
