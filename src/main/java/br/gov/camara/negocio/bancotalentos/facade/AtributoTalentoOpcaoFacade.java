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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import br.gov.camara.exception.CDException;
import br.gov.camara.negocio.DAO;
import br.gov.camara.negocio.Facade;
import br.gov.camara.negocio.bancotalentos.dao.AtributoTalentoDAO;
import br.gov.camara.negocio.bancotalentos.dao.AtributoTalentoOpcaoDAO;
import br.gov.camara.negocio.bancotalentos.dao.AtributoTalentoValoradoDAO;
import br.gov.camara.negocio.bancotalentos.dao.CategoriaAtributoTalentoDAO;
import br.gov.camara.negocio.bancotalentos.pojo.AtributoTalento;
import br.gov.camara.negocio.bancotalentos.pojo.AtributoTalentoOpcao;
import br.gov.camara.negocio.bancotalentos.pojo.AtributoTalentoValorado;
import br.gov.camara.negocio.bancotalentos.pojo.CategoriaAtributoTalento;
import br.gov.camara.negocio.comum.dao.TipoHTMLDAO;
import br.gov.camara.negocio.comum.pojo.TipoHTML;

/**
 * Facade para atributo de talento
 */
public class AtributoTalentoOpcaoFacade extends Facade
{
    // Vari�veis de inst�ncia
    private static Log log = LogFactory.getLog(AtributoTalentoOpcaoFacade.class);

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
        AtributoTalentoOpcaoDAO objAtributoTalentoOpcaoDAO = new AtributoTalentoOpcaoDAO();
        int intTotalRegistros = 0;
        try
        {
            intTotalRegistros = objAtributoTalentoOpcaoDAO.obterTotalRegistros();
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

    /**
     * Obt�m o total de registros relacionados a determinado atributo de talento
     *
     * @param objAtributoTalento Atributo de talento desejado
     * 
     * @return int Contendo o total
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
     * 
     */
    public int obterTotalRegistrosPorAtributoTalento(AtributoTalento objAtributoTalento) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obt�m os registros da p�gina
        AtributoTalentoOpcaoDAO objAtributoTalentoOpcaoDAO = new AtributoTalentoOpcaoDAO();
        int intTotalRegistros = 0;
        try
        {
            intTotalRegistros = objAtributoTalentoOpcaoDAO.obterTotalRegistrosPorAtributoTalento(objAtributoTalento);
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

    /**
     * Obt�m o total de registros, pela descri��o relacionados a determinado atributo de talento 
     *
     * @param strDescricao Descri��o a ser consultada
     * @param objAtributoTalento Atributo de talento desejado
     * 
     * @return int Contendo o total
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
     * 
     */
    public int obterTotalRegistrosPorDescricaoCategoriaAtributoTalentoHierarquiaPorPagina(String strDescricao, CategoriaAtributoTalento objCategoriaAtributoTalento) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obt�m os registros da p�gina
        AtributoTalentoOpcaoDAO objAtributoTalentoOpcaoDAO = new AtributoTalentoOpcaoDAO();
        CategoriaAtributoTalentoFacade objCategoriaAtributoTalentoFacade = new CategoriaAtributoTalentoFacade();
        int intTotalRegistros = 0;
        try
        {
            // Obt�m hierarquia da categoria/atributo passada
            List lstCategoriaAtributoTalentoHierarquia = objCategoriaAtributoTalentoFacade.obterHierarquia(objCategoriaAtributoTalento);

            // Obt�m lista de atributos de talento hierarquia
            Iterator itrCategoriaAtributoTalentoHierarquia = lstCategoriaAtributoTalentoHierarquia.iterator();
            List lstAtributosTalentoHierarquia = new ArrayList();
            while (itrCategoriaAtributoTalentoHierarquia.hasNext())
            {
                CategoriaAtributoTalento objCategoriaAtributoTalentoHierarquia = (CategoriaAtributoTalento) itrCategoriaAtributoTalentoHierarquia.next();
                lstAtributosTalentoHierarquia.add(objCategoriaAtributoTalentoHierarquia.getAtributoTalento());
            }

            intTotalRegistros = objAtributoTalentoOpcaoDAO.obterTotalRegistrosPorDescricaoAtributosTalentoPorPagina(strDescricao, lstAtributosTalentoHierarquia);
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
        AtributoTalentoOpcaoDAO objAtributoTalentoOpcaoDAO = new AtributoTalentoOpcaoDAO();
        List lstRetorno = null;
        try
        {
            lstRetorno = objAtributoTalentoOpcaoDAO.obterPorPagina(intNumeroPagina, intMaximoPagina);
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
     * Obt�m os registros por descri��o, de determinada p�gina
     * 
     * @param String Descri��o da op��o
     * @param CategoriaAtributoTalento Categoria/atributo a buscar a hierarquia de atributos para a consulta
     * @param int N�mero da p�gina a ser mostrada
     * @param int Quantidade de registros
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
     * 
     */
    public List obterPorDescricaoCategoriaAtributoTalentoHierarquiaPorPagina(String strDescricao, CategoriaAtributoTalento objCategoriaAtributoTalento, int intNumeroPagina, int intMaximoPagina) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obt�m os registros da p�gina
        AtributoTalentoOpcaoDAO objAtributoTalentoOpcaoDAO = new AtributoTalentoOpcaoDAO();
        CategoriaAtributoTalentoFacade objCategoriaAtributoTalentoFacade = new CategoriaAtributoTalentoFacade();
        List lstRetorno = null;
        try
        {
            // Obt�m hierarquia da categoria/atributo passada
            List lstCategoriaAtributoTalentoHierarquia = objCategoriaAtributoTalentoFacade.obterHierarquia(objCategoriaAtributoTalento);

            // Obt�m lista de atributos de talento hierarquia
            Iterator itrCategoriaAtributoTalentoHierarquia = lstCategoriaAtributoTalentoHierarquia.iterator();
            List lstAtributosTalentoHierarquia = new ArrayList();
            while (itrCategoriaAtributoTalentoHierarquia.hasNext())
            {
                CategoriaAtributoTalento objCategoriaAtributoTalentoHierarquia = (CategoriaAtributoTalento) itrCategoriaAtributoTalentoHierarquia.next();
                lstAtributosTalentoHierarquia.add(objCategoriaAtributoTalentoHierarquia.getAtributoTalento());
            }

            // Retorna consulta de descri��o na hierarquia
            lstRetorno = objAtributoTalentoOpcaoDAO.obterPorDescricaoAtributosTalentoPorPagina(strDescricao, lstAtributosTalentoHierarquia, intNumeroPagina, intMaximoPagina);
            objAtributoTalentoOpcaoDAO.inicializarAtributoTalento(lstRetorno);
            objAtributoTalentoOpcaoDAO.inicializarAtributoTalentoOpcaoPai(lstRetorno);
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
     * Obt�m os registros de determinada p�gina relacionados a determinado atributo de talento
     *
     * @param objAtributoTalento Atributo de talento desejado
     * @param int N�mero da p�gina a ser mostrada
     * @param int Quantidade de registros
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
     * 
     */
    public List obterPorAtributoTalentoPorPagina(AtributoTalento objAtributoTalento, int intNumeroPagina, int intMaximoPagina) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obt�m os registros da p�gina
        AtributoTalentoOpcaoDAO objAtributoTalentoOpcaoDAO = new AtributoTalentoOpcaoDAO();
        List lstRetorno = null;
        try
        {
            lstRetorno = objAtributoTalentoOpcaoDAO.obterPorPaginaPorAtributoTalento(objAtributoTalento, intNumeroPagina, intMaximoPagina);
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
     * Obt�m um registro a partir da chave
     *
     * @param strChave Chave do registro a ser obtido
     * 
     * @return AtributoTalentoOpcao POJO representando o registro obtido
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
     * 
     */
    public AtributoTalentoOpcao obterPelaChave(String strChave) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obt�m o registro pela chave
        AtributoTalentoOpcaoDAO objAtributoTalentoOpcaoDAO = new AtributoTalentoOpcaoDAO();
        AtributoTalentoOpcao objAtributoTalentoOpcao = null;
        try
        {
            objAtributoTalentoOpcao = (AtributoTalentoOpcao) objAtributoTalentoOpcaoDAO.obterPelaChave(strChave);
        }
        catch (Exception daoe)
        {
            CDException.dispararExcecao(daoe);
        }
        finally
        {
            DAO.desconectar();
        }
        return objAtributoTalentoOpcao;
    }

    /**
     * Obt�m candidatos a pai, ou seja, todos os atributos exceto o que est� sendo passado
     * 
     * @param objAtributoTalento Atributo de talento para buscar as op��es
     * @param objAtributoTalentoOpcao Op��o de atributo de talento que se deseja excluir
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
     * 
     */
    public List obterCandidatosAPai(AtributoTalento objAtributoTalento, AtributoTalentoOpcao objAtributoTalentoOpcao) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obt�m o registro pela chave
        AtributoTalentoOpcaoDAO objAtributoTalentoOpcaoDAO = new AtributoTalentoOpcaoDAO();
        List lstCandidatosAPai = null;
        try
        {
            if (objAtributoTalento.getAtributoTalentoPai() != null)
            {
                lstCandidatosAPai = objAtributoTalentoOpcaoDAO.obterCandidatosAPai(objAtributoTalento, objAtributoTalentoOpcao);
            }
        }
        catch (Exception daoe)
        {
            CDException.dispararExcecao(daoe);
        }
        finally
        {
            DAO.desconectar();
        }
        return lstCandidatosAPai;
    }

    /**
     * Obt�m um atributo de talento a partir da chave
     *
     * @param strChave Chave do registro a ser obtido
     * 
     * @return AtributoTalento POJO representando o registro obtido
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
     * 
     */
    public AtributoTalento obterAtributoTalentoPelaChave(String strChave) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obt�m o registro pela chave
        AtributoTalentoDAO objAtributoTalentoDAO = new AtributoTalentoDAO();
        AtributoTalento objAtributoTalento = null;
        try
        {
            objAtributoTalento = (AtributoTalento) objAtributoTalentoDAO.obterPelaChave(strChave);
            objAtributoTalentoDAO.inicializarAtributoTalentoPai(objAtributoTalento);
        }
        catch (Exception daoe)
        {
            CDException.dispararExcecao(daoe);
        }
        finally
        {
            DAO.desconectar();
        }
        return objAtributoTalento;
    }

    /**
     * Obt�m uma categoria/atributo de talento a partir da chave
     *
     * @param strChave Chave do registro a ser obtido
     * 
     * @return CategoriaAtributoTalento POJO representando o registro obtido
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
     * 
     */
    public CategoriaAtributoTalento obterCategoriaAtributoTalentoPelaChave(String strChave) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obt�m o registro pela chave
        CategoriaAtributoTalentoDAO objCategoriaAtributoTalentoDAO = new CategoriaAtributoTalentoDAO();
        CategoriaAtributoTalento objCategoriaAtributoTalento = null;
        try
        {
            objCategoriaAtributoTalento = (CategoriaAtributoTalento) objCategoriaAtributoTalentoDAO.obterPelaChave(strChave);
            objCategoriaAtributoTalentoDAO.inicializarAtributoTalento(objCategoriaAtributoTalento);
        }
        catch (Exception daoe)
        {
            CDException.dispararExcecao(daoe);
        }
        finally
        {
            DAO.desconectar();
        }
        return objCategoriaAtributoTalento;
    }

    /**
     * Obt�m a hierarquia superior de determinada op��o
     * 
     * @param objAtributoTalentoOpcao Op��o de atributo de talento a ser verificada
     * 
     * @return List Contendo os POJOs representando os registros obtidos
     * 
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
     */
    public List obterHierarquiaSuperior(AtributoTalentoOpcao objAtributoTalentoOpcao) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obt�m o registro pela chave
        AtributoTalentoOpcaoDAO objAtributoTalentoOpcaoDAO = new AtributoTalentoOpcaoDAO();
        List lstAtributoTalentoOpcoesHierarquia = new ArrayList();
        try
        {
            if (objAtributoTalentoOpcao.getAtributoTalentoOpcaoPai() != null)
            {
                objAtributoTalentoOpcaoDAO.inicializarAtributoTalentoOpcaoPai(objAtributoTalentoOpcao.getAtributoTalentoOpcaoPai());
                lstAtributoTalentoOpcoesHierarquia = obterHierarquiaSuperior(objAtributoTalentoOpcao.getAtributoTalentoOpcaoPai());
                lstAtributoTalentoOpcoesHierarquia.add(objAtributoTalentoOpcao.getAtributoTalentoOpcaoPai());
            }
        }
        catch (Exception daoe)
        {
            CDException.dispararExcecao(daoe);
        }
        finally
        {
            DAO.desconectar();
        }
        return lstAtributoTalentoOpcoesHierarquia;
    }

    /**
     * Inclui um registro
     *
     * @param objAtributoTalentoOpcao POJO representando o objeto a ser inclu�do
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
     *
     * @return retorna a chave do objeto que foi incluido
     *
     */
    public String incluir(AtributoTalentoOpcao objAtributoTalentoOpcao) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        String strChave = null;

        // Instancia DAO e inclui registro
        AtributoTalentoOpcaoDAO objAtributoTalentoOpcaoDAO = new AtributoTalentoOpcaoDAO();
        AtributoTalentoDAO objAtributoTalentoDAO = new AtributoTalentoDAO();
        TipoHTMLDAO objTipoHTMLDAO = new TipoHTMLDAO();
        try
        {
            // Se a op��o tem um pai, o atributo relacionado tamb�m deve ter
            AtributoTalento objAtributoTalento = (AtributoTalento) objAtributoTalentoDAO.obterPelaChave(String.valueOf(objAtributoTalentoOpcao.getAtributoTalento().getIdentificador()));
            if (objAtributoTalentoOpcao.getAtributoTalentoOpcaoPai() != null)
            {
                if (objAtributoTalento.getAtributoTalentoPai() == null)
                {
                    throw new CDException("N�o � permitido associar uma op��o pai a uma op��o" + " se o atributo associado n�o tiver pai tamb�m");
                }
            }

            // Se o atributo relacionado tiver um pai, as op��es dele tamb�m dever�o ter 
            if (objAtributoTalento.getAtributoTalentoPai() != null)
            {
                if (objAtributoTalentoOpcao.getAtributoTalentoOpcaoPai() == null)
                {
                    throw new CDException("N�o � permitido n�o associar uma op��o pai a uma op��o" + " se o atributo associado tiver um pai ");
                }
            }

            // Se o atributo associado for de livre preenchimento (tipo HTML igual a L), n�o � poss�vel inserir op��es
            TipoHTML objTipoHTML = (TipoHTML) objTipoHTMLDAO.obterPelaChave(String.valueOf(objAtributoTalento.getTipoHTML().getIdentificador()));
            if ("L".equals(objTipoHTML.getMultiplicidade()))
            {
                throw new CDException("N�o � poss�vel inserir op��es para atributos com tipo HTML " + "de livre preenchimento");
            }

            // Inclui
            strChave = objAtributoTalentoOpcaoDAO.incluir(objAtributoTalentoOpcao);
        }
        catch (Exception daoe)
        {
            CDException.dispararExcecao(daoe);
        }
        finally
        {
            DAO.desconectar();
        }
        return strChave;
    }

    /**
     * Altera um registro
     *
     * @param objAtributoTalentoOpcao POJO representando o objeto a ser inclu�do
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
     *
     */
    public void alterar(AtributoTalentoOpcao objAtributoTalentoOpcao) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e inclui registro
        AtributoTalentoOpcaoDAO objAtributoTalentoOpcaoDAO = new AtributoTalentoOpcaoDAO();
        //AtributoTalentoDAO objAtributoTalentoDAO = new AtributoTalentoDAO();
        AtributoTalentoValoradoDAO objAtributoTalentoValoradoDAO = new AtributoTalentoValoradoDAO();
        try
        {
            // Se a op��o tem um pai, o atributo relacionado tamb�m deve ter
            if (objAtributoTalentoOpcao.getAtributoTalentoOpcaoPai() != null)
            {
                if (objAtributoTalentoOpcao.getAtributoTalento().getAtributoTalentoPai() == null)
                {
                    throw new CDException("N�o � permitido associar uma op��o pai a uma op��o" + " se o atributo associado n�o tiver pai tamb�m");
                }
            }

            // Se o atributo relacionado tiver um pai, as op��es dele tamb�m dever�o ter 
            if (objAtributoTalentoOpcao.getAtributoTalento().getAtributoTalentoPai() != null)
            {
                if (objAtributoTalentoOpcao.getAtributoTalentoOpcaoPai() == null)
                {
                    throw new CDException("N�o � permitido n�o associar uma op��o pai a uma op��o" + " se o atributo associado tiver um pai ");
                }
            }

            // Inicia transa��o
            DAO.iniciarTransacao();

            // Altera op��o
            objAtributoTalentoOpcaoDAO.alterar(objAtributoTalentoOpcao);

            // Altera descri��o das valora��es de talentos que est�o relacionadas a esta op��o
            List lstAtributosTalentoValorados = objAtributoTalentoValoradoDAO.obterPorCategoriaAtributoTalentoOpcao(objAtributoTalentoOpcao);
            Iterator itrAtributosTalentoValorados = lstAtributosTalentoValorados.iterator();
            while (itrAtributosTalentoValorados.hasNext())
            {
                AtributoTalentoValorado objAtributoTalentoValorado = (AtributoTalentoValorado) itrAtributosTalentoValorados.next();
                objAtributoTalentoValorado.setValoracao(objAtributoTalentoOpcao.getDescricao());
                objAtributoTalentoValoradoDAO.alterar(objAtributoTalentoValorado);
            }

            // Finaliza transa��o
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
     * Exclui um registro
     *
     * @param objAtributoTalentoOpcao POJO representando o objeto a ser inclu�do
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
     *
     */
    public void excluir(AtributoTalentoOpcao objAtributoTalentoOpcao) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e inclui registro
        AtributoTalentoOpcaoDAO objAtributoTalentoOpcaoDAO = new AtributoTalentoOpcaoDAO();
        try
        {
            objAtributoTalentoOpcaoDAO.excluir(objAtributoTalentoOpcao);
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
}
