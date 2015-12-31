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
    // Vari�veis de inst�ncia
    private static Log log = LogFactory.getLog(CriterioAcessoFacade.class);

    /**
     * Obt�m o total de registros
     * 
     * @return int Contendo o total
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
     * 
     */
    public int obterTotalRegistros() throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obt�m os registros da p�gina
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
    public List obterPorPagina(int intNumeroPagina, int intMaximoPagina) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obt�m os registros da p�gina
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
     * Obt�m um registro a partir da chave
     *
     * @param strChave Chave do registro a ser obtido
     * 
     * @return CriterioAcesso POJO representando o registro obtido
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
     * 
     */
    public CriterioAcesso obterPelaChave(String strChave) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obt�m o registro pela chave
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
     * Obt�m um registro a partir da chave
     *
     * @param Object Chave do registro a ser obtido
     * 
     * @return CriterioAcesso POJO representando o registro obtido
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
     * 
     */
    public CriterioAcesso obterPelaChave(CriterioAcesso objCriterioAcesso) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obt�m o registro pela chave
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
     * @param objCriterioAcesso POJO representando o objeto a ser inclu�do
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
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
        // Obt�m classe implementadora do crit�rio de acesso
        ClasseDinamica clsDinamica = new ClasseDinamica();

        Object objCriterio = clsDinamica.obterInstancia(classeImplementadora);
        if (! (objCriterio instanceof br.gov.camara.seguranca.criterioacesso.CriterioAcesso))
        {
            throw new CDException("Classe implementadora '" + classeImplementadora + "' n�o implementa a classe ICriterioAcesso");
        }
    }

    /**
     * Altera um registro
     *
     * @param objCriterioAcesso POJO representando o objeto a ser inclu�do
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
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
     * @param objCriterioAcesso POJO representando o objeto a ser inclu�do
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
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
