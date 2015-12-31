/****
 * Copyright 2000, 2001, 2002, 2003, 2004, 2005, 2006, 2007, 2008 C�mara dos Deputados, Brasil
 * 
 * Este arquivo � parte do programa TALENTOS - Banco de Talentos
 *
 * O TALENTOS � um software livre; voc� pode redistribu�-lo e/ou modific�-lo dentro dos termos da Licen�a P�blica Geral GNU como publicada pela Funda��o do Software Livre (FSF); na vers�o 2 da Licen�a.
 *
 * Este programa � distribu�do na esperan�a que possa ser �til, mas SEM NENHUMA GARANTIA; sem uma garantia impl�cita de ADEQUA��O a qualquer MERCADO ou APLICA��O EM PARTICULAR. Veja a Licen�a P�blica Geral GNU para maiores detalhes.
 * 
 * Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU, sob o t�tulo "LICENCA.txt", junto com este programa, se n�o, escreva para a Funda��o do Software Livre(FSF) Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
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
    // Vari�veis de inst�ncia
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
     * Obt�m um registro a partir da chave
     *
     * @param strChave Chave do registro a ser obtido
     * 
     * @return Grupo POJO representando o registro obtido
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
     * 
     */
    public DetalheHistoricoLog obterPelaChave(String strChave) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obt�m o registro pela chave
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
     * @param objGrupo POJO representando o objeto a ser inclu�do
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
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
     * @param objGrupo POJO representando o objeto a ser inclu�do
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
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
     * @param objGrupo POJO representando o objeto a ser inclu�do
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
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
     * Obt�m os registros de determinada p�gina
     * 
     * @param int N�mero da p�gina a ser mostrada
     * @param int Quantidade de registros
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
     * 
     */
    public List obterTodos() throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obt�m os registros da p�gina
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
