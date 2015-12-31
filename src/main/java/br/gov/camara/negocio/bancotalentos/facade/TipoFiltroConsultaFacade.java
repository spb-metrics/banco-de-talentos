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
    // Vari�veis de inst�ncia
    private static Log log = LogFactory.getLog(TipoFiltroConsultaFacade.class);

    /**
     * Obt�m um registro a partir da chave
     *
     * @param strChave Chave do registro a ser obtido
     * 
     * @return Talento POJO representando o registro obtido
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
     * 
     */
    public TipoFiltroConsulta obterPelaChave(String strChave) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obt�m o registro pela chave
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
     * Obt�m todos os registros 
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

        // Instancia DAO e obt�m todos os registros
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
     * @param objTalento POJO representando o objeto a ser inclu�do
     * @param lstAtributosTalentoValoros List contendo as valora��es do talento a serem inclu�das
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
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
            // Inicia transa��o
            DAO.iniciarTransacao();

            // Inclui TipoFiltroConsulta
            objTipoFiltroConsultaDAO.incluir(objTipoFiltroConsulta);

            // Realiza transa��o
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
     * @param objTalento POJO representando o objeto a ser inclu�do
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
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
            // Inicia transa��o
            DAO.iniciarTransacao();

            // Altera
            objTipoFiltroConsultaDAO.alterar(objTipoFiltroConsulta);

            // Realiza transa��o
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
     * @param objTalento POJO representando o objeto a ser inclu�do
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
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
            // Inicia transa��o
            DAO.iniciarTransacao();

            // Exclui
            objTipoFiltroConsultaDAO.excluir(objTipoFiltroConsulta);

            // Realiza transa��o
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
