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

import br.gov.camara.biblioteca.util.ClasseDinamica;
import br.gov.camara.exception.CDException;
import br.gov.camara.negocio.DAO;
import br.gov.camara.negocio.Facade;
import br.gov.camara.negocio.autenticacaoperfil.dao.CriterioAcessoDAO;
import br.gov.camara.negocio.autenticacaoperfil.pojo.CriterioAcesso;

/**
 * Facade para atributo de talento
 */
public class CriterioAcessoFacade extends Facade
{
    // Variáveis de instância
    private static Log log = LogFactory.getLog(CriterioAcessoFacade.class);

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
        CriterioAcessoDAO objCriterioAcessoDAO = new CriterioAcessoDAO(obterNomeConexao());
        int intTotalRegistros = 0;
        try
        {
            intTotalRegistros = objCriterioAcessoDAO.obterTotalRegistros();
        }
        catch (Exception daoe)
        {
            CDException.dispararExcecao(daoe);
        }
        finally
        {
            DAO.desconectar(obterNomeConexao());
        }
        return intTotalRegistros;
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
        CriterioAcessoDAO objCriterioAcessoDAO = new CriterioAcessoDAO(obterNomeConexao());
        List lstRetorno = null;
        try
        {
            lstRetorno = objCriterioAcessoDAO.obterPorPagina(intNumeroPagina, intMaximoPagina);
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

    /**
     * Obtém um registro a partir da chave
     *
     * @param strChave Chave do registro a ser obtido
     * 
     * @return CriterioAcesso POJO representando o registro obtido
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     * 
     */
    public CriterioAcesso obterPelaChave(String strChave) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obtém o registro pela chave
        CriterioAcessoDAO objCriterioAcessoDAO = new CriterioAcessoDAO(obterNomeConexao());
        CriterioAcesso objCriterioAcesso = null;
        try
        {
            objCriterioAcesso = (CriterioAcesso) objCriterioAcessoDAO.obterPelaChave(strChave);

        }
        catch (Exception daoe)
        {
            CDException.dispararExcecao(daoe);
        }
        finally
        {
            DAO.desconectar(obterNomeConexao());
        }
        return objCriterioAcesso;
    }

    /**
     * Obtém um registro a partir da chave
     *
     * @param Object Chave do registro a ser obtido
     * 
     * @return CriterioAcesso POJO representando o registro obtido
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     * 
     */
    public CriterioAcesso obterPelaChave(CriterioAcesso objCriterioAcesso) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obtém o registro pela chave
        CriterioAcessoDAO objCriterioAcessoDAO = new CriterioAcessoDAO(obterNomeConexao());
        try
        {
            objCriterioAcesso = (CriterioAcesso) objCriterioAcessoDAO.obterPelaChave(objCriterioAcesso);
        }
        catch (Exception daoe)
        {
            CDException.dispararExcecao(daoe);
        }
        finally
        {
            DAO.desconectar(obterNomeConexao());
        }
        return objCriterioAcesso;
    }

    /**
     * Inclui um registro
     *
     * @param objCriterioAcesso POJO representando o objeto a ser incluído
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     *
     * @return retorna a chave do objeto que foi incluido
     *
     */
    public String incluir(CriterioAcesso objCriterioAcesso) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        String strChave = null;

        // Instancia DAO e inclui registro
        CriterioAcessoDAO objCriterioAcessoDAO = new CriterioAcessoDAO(obterNomeConexao());
        try
        {
            validaClasseImplementadora(objCriterioAcesso.getClasseImplementadora());
            
            strChave = objCriterioAcessoDAO.incluir(objCriterioAcesso);
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

    private void validaClasseImplementadora(String classeImplementadora) throws CDException
    {
        // Obtém classe implementadora do critério de acesso
        ClasseDinamica clsDinamica = new ClasseDinamica();

        Object objCriterio = clsDinamica.obterInstancia(classeImplementadora);
        if (! (objCriterio instanceof br.gov.camara.seguranca.criterioacesso.CriterioAcesso))
        {
            throw new CDException("Classe implementadora '" + classeImplementadora + "' não implementa a classe ICriterioAcesso");
        }
    }

    /**
     * Altera um registro
     *
     * @param objCriterioAcesso POJO representando o objeto a ser incluído
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     *
     */
    public void alterar(CriterioAcesso objCriterioAcesso) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e altera registro
        CriterioAcessoDAO objCriterioAcessoDAO = new CriterioAcessoDAO(obterNomeConexao());
        try
        {
            validaClasseImplementadora(objCriterioAcesso.getClasseImplementadora());
            
            objCriterioAcessoDAO.alterar(objCriterioAcesso);
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
     * @param objCriterioAcesso POJO representando o objeto a ser incluído
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     *
     */
    public void excluir(CriterioAcesso objCriterioAcesso) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e exclui registro
        CriterioAcessoDAO objCriterioAcessoDAO = new CriterioAcessoDAO(obterNomeConexao());
        try
        {
            objCriterioAcessoDAO.excluir(objCriterioAcesso);
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
        CriterioAcessoDAO objCriterioAcessoDAO = new CriterioAcessoDAO(obterNomeConexao());
        List lstRetorno = null;
        try
        {
            lstRetorno = objCriterioAcessoDAO.obterTodos();
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
