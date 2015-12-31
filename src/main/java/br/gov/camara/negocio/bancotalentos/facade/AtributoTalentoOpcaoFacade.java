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
    // Variáveis de instância
    private static Log log = LogFactory.getLog(AtributoTalentoOpcaoFacade.class);

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
     * Obtém o total de registros relacionados a determinado atributo de talento
     *
     * @param objAtributoTalento Atributo de talento desejado
     * 
     * @return int Contendo o total
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     * 
     */
    public int obterTotalRegistrosPorAtributoTalento(AtributoTalento objAtributoTalento) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obtém os registros da página
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
     * Obtém o total de registros, pela descrição relacionados a determinado atributo de talento 
     *
     * @param strDescricao Descrição a ser consultada
     * @param objAtributoTalento Atributo de talento desejado
     * 
     * @return int Contendo o total
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     * 
     */
    public int obterTotalRegistrosPorDescricaoCategoriaAtributoTalentoHierarquiaPorPagina(String strDescricao, CategoriaAtributoTalento objCategoriaAtributoTalento) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obtém os registros da página
        AtributoTalentoOpcaoDAO objAtributoTalentoOpcaoDAO = new AtributoTalentoOpcaoDAO();
        CategoriaAtributoTalentoFacade objCategoriaAtributoTalentoFacade = new CategoriaAtributoTalentoFacade();
        int intTotalRegistros = 0;
        try
        {
            // Obtém hierarquia da categoria/atributo passada
            List lstCategoriaAtributoTalentoHierarquia = objCategoriaAtributoTalentoFacade.obterHierarquia(objCategoriaAtributoTalento);

            // Obtém lista de atributos de talento hierarquia
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
     * Obtém os registros por descrição, de determinada página
     * 
     * @param String Descrição da opção
     * @param CategoriaAtributoTalento Categoria/atributo a buscar a hierarquia de atributos para a consulta
     * @param int Número da página a ser mostrada
     * @param int Quantidade de registros
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     * 
     */
    public List obterPorDescricaoCategoriaAtributoTalentoHierarquiaPorPagina(String strDescricao, CategoriaAtributoTalento objCategoriaAtributoTalento, int intNumeroPagina, int intMaximoPagina) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obtém os registros da página
        AtributoTalentoOpcaoDAO objAtributoTalentoOpcaoDAO = new AtributoTalentoOpcaoDAO();
        CategoriaAtributoTalentoFacade objCategoriaAtributoTalentoFacade = new CategoriaAtributoTalentoFacade();
        List lstRetorno = null;
        try
        {
            // Obtém hierarquia da categoria/atributo passada
            List lstCategoriaAtributoTalentoHierarquia = objCategoriaAtributoTalentoFacade.obterHierarquia(objCategoriaAtributoTalento);

            // Obtém lista de atributos de talento hierarquia
            Iterator itrCategoriaAtributoTalentoHierarquia = lstCategoriaAtributoTalentoHierarquia.iterator();
            List lstAtributosTalentoHierarquia = new ArrayList();
            while (itrCategoriaAtributoTalentoHierarquia.hasNext())
            {
                CategoriaAtributoTalento objCategoriaAtributoTalentoHierarquia = (CategoriaAtributoTalento) itrCategoriaAtributoTalentoHierarquia.next();
                lstAtributosTalentoHierarquia.add(objCategoriaAtributoTalentoHierarquia.getAtributoTalento());
            }

            // Retorna consulta de descrição na hierarquia
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
     * Obtém os registros de determinada página relacionados a determinado atributo de talento
     *
     * @param objAtributoTalento Atributo de talento desejado
     * @param int Número da página a ser mostrada
     * @param int Quantidade de registros
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     * 
     */
    public List obterPorAtributoTalentoPorPagina(AtributoTalento objAtributoTalento, int intNumeroPagina, int intMaximoPagina) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obtém os registros da página
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
     * Obtém um registro a partir da chave
     *
     * @param strChave Chave do registro a ser obtido
     * 
     * @return AtributoTalentoOpcao POJO representando o registro obtido
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     * 
     */
    public AtributoTalentoOpcao obterPelaChave(String strChave) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obtém o registro pela chave
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
     * Obtém candidatos a pai, ou seja, todos os atributos exceto o que está sendo passado
     * 
     * @param objAtributoTalento Atributo de talento para buscar as opções
     * @param objAtributoTalentoOpcao Opção de atributo de talento que se deseja excluir
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     * 
     */
    public List obterCandidatosAPai(AtributoTalento objAtributoTalento, AtributoTalentoOpcao objAtributoTalentoOpcao) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obtém o registro pela chave
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
     * Obtém um atributo de talento a partir da chave
     *
     * @param strChave Chave do registro a ser obtido
     * 
     * @return AtributoTalento POJO representando o registro obtido
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     * 
     */
    public AtributoTalento obterAtributoTalentoPelaChave(String strChave) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obtém o registro pela chave
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
     * Obtém uma categoria/atributo de talento a partir da chave
     *
     * @param strChave Chave do registro a ser obtido
     * 
     * @return CategoriaAtributoTalento POJO representando o registro obtido
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     * 
     */
    public CategoriaAtributoTalento obterCategoriaAtributoTalentoPelaChave(String strChave) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obtém o registro pela chave
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
     * Obtém a hierarquia superior de determinada opção
     * 
     * @param objAtributoTalentoOpcao Opção de atributo de talento a ser verificada
     * 
     * @return List Contendo os POJOs representando os registros obtidos
     * 
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     */
    public List obterHierarquiaSuperior(AtributoTalentoOpcao objAtributoTalentoOpcao) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obtém o registro pela chave
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
     * @param objAtributoTalentoOpcao POJO representando o objeto a ser incluído
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
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
            // Se a opção tem um pai, o atributo relacionado também deve ter
            AtributoTalento objAtributoTalento = (AtributoTalento) objAtributoTalentoDAO.obterPelaChave(String.valueOf(objAtributoTalentoOpcao.getAtributoTalento().getIdentificador()));
            if (objAtributoTalentoOpcao.getAtributoTalentoOpcaoPai() != null)
            {
                if (objAtributoTalento.getAtributoTalentoPai() == null)
                {
                    throw new CDException("Não é permitido associar uma opção pai a uma opção" + " se o atributo associado não tiver pai também");
                }
            }

            // Se o atributo relacionado tiver um pai, as opções dele também deverão ter 
            if (objAtributoTalento.getAtributoTalentoPai() != null)
            {
                if (objAtributoTalentoOpcao.getAtributoTalentoOpcaoPai() == null)
                {
                    throw new CDException("Não é permitido não associar uma opção pai a uma opção" + " se o atributo associado tiver um pai ");
                }
            }

            // Se o atributo associado for de livre preenchimento (tipo HTML igual a L), não é possível inserir opções
            TipoHTML objTipoHTML = (TipoHTML) objTipoHTMLDAO.obterPelaChave(String.valueOf(objAtributoTalento.getTipoHTML().getIdentificador()));
            if ("L".equals(objTipoHTML.getMultiplicidade()))
            {
                throw new CDException("Não é possível inserir opções para atributos com tipo HTML " + "de livre preenchimento");
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
     * @param objAtributoTalentoOpcao POJO representando o objeto a ser incluído
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
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
            // Se a opção tem um pai, o atributo relacionado também deve ter
            if (objAtributoTalentoOpcao.getAtributoTalentoOpcaoPai() != null)
            {
                if (objAtributoTalentoOpcao.getAtributoTalento().getAtributoTalentoPai() == null)
                {
                    throw new CDException("Não é permitido associar uma opção pai a uma opção" + " se o atributo associado não tiver pai também");
                }
            }

            // Se o atributo relacionado tiver um pai, as opções dele também deverão ter 
            if (objAtributoTalentoOpcao.getAtributoTalento().getAtributoTalentoPai() != null)
            {
                if (objAtributoTalentoOpcao.getAtributoTalentoOpcaoPai() == null)
                {
                    throw new CDException("Não é permitido não associar uma opção pai a uma opção" + " se o atributo associado tiver um pai ");
                }
            }

            // Inicia transação
            DAO.iniciarTransacao();

            // Altera opção
            objAtributoTalentoOpcaoDAO.alterar(objAtributoTalentoOpcao);

            // Altera descrição das valorações de talentos que estão relacionadas a esta opção
            List lstAtributosTalentoValorados = objAtributoTalentoValoradoDAO.obterPorCategoriaAtributoTalentoOpcao(objAtributoTalentoOpcao);
            Iterator itrAtributosTalentoValorados = lstAtributosTalentoValorados.iterator();
            while (itrAtributosTalentoValorados.hasNext())
            {
                AtributoTalentoValorado objAtributoTalentoValorado = (AtributoTalentoValorado) itrAtributosTalentoValorados.next();
                objAtributoTalentoValorado.setValoracao(objAtributoTalentoOpcao.getDescricao());
                objAtributoTalentoValoradoDAO.alterar(objAtributoTalentoValorado);
            }

            // Finaliza transação
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
     * @param objAtributoTalentoOpcao POJO representando o objeto a ser incluído
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
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
