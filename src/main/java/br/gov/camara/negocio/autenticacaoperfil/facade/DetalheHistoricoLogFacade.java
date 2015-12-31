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
import br.gov.camara.negocio.autenticacaoperfil.dao.DetalheHistoricoLogDAO;
import br.gov.camara.negocio.autenticacaoperfil.pojo.DetalheHistoricoLog;

/**
 * Facade para atributo de talento
 */
public class DetalheHistoricoLogFacade extends Facade
{
    // Variáveis de instância
    private static Log log = LogFactory.getLog(DetalheHistoricoLogFacade.class);

    /**
     * Construtor Default
     */
    public DetalheHistoricoLogFacade()
    {
        super();
    }

    /**
     * @param nomeConexao
     *
     */
    public DetalheHistoricoLogFacade(String nomeConexao)
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
    public DetalheHistoricoLog obterPelaChave(String strChave) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obtém o registro pela chave
        DetalheHistoricoLogDAO objDetalheHistoricoLogDAO = new DetalheHistoricoLogDAO(obterNomeConexao());
        DetalheHistoricoLog objHistoricoAuditoria= null;
        try
        {
            objHistoricoAuditoria = (DetalheHistoricoLog) objDetalheHistoricoLogDAO.obterPelaChave(strChave);
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
    public String incluir(DetalheHistoricoLog objDetalheHistoricoLog) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        String strChave = null;

        // Instancia DAO e inclui registro
        DetalheHistoricoLogDAO objDetalheHistoricoLogDAO = new DetalheHistoricoLogDAO(obterNomeConexao());
        try
        {
            strChave = objDetalheHistoricoLogDAO.incluir(objDetalheHistoricoLog);
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
    public void alterar(DetalheHistoricoLog objHistoricoAuditoria) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e altera registro
        DetalheHistoricoLogDAO objDetalheHistoricoLogDAO = new DetalheHistoricoLogDAO(obterNomeConexao());
        try
        {
            objDetalheHistoricoLogDAO.alterar(objHistoricoAuditoria);
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
    public void excluir(DetalheHistoricoLog objHistoricoAuditoria) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e exclui registro
        DetalheHistoricoLogDAO objDetalheHistoricoLogDAO = new DetalheHistoricoLogDAO(obterNomeConexao());
        try
        {
            objDetalheHistoricoLogDAO.excluir(objHistoricoAuditoria);
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
        DetalheHistoricoLogDAO objDetalheHistoricoLogDAO = new DetalheHistoricoLogDAO(obterNomeConexao());
        List lstRetorno = null;
        try
        {
            lstRetorno = objDetalheHistoricoLogDAO.obterTodos();
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
