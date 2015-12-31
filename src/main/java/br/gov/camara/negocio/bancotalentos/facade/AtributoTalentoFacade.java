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
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import br.gov.camara.biblioteca.util.ExpressaoRegular;
import br.gov.camara.exception.CDException;
import br.gov.camara.negocio.DAO;
import br.gov.camara.negocio.Facade;
import br.gov.camara.negocio.bancotalentos.dao.AtributoTalentoDAO;
import br.gov.camara.negocio.bancotalentos.dao.AtributoTalentoOpcaoDAO;
import br.gov.camara.negocio.bancotalentos.dao.TabelaApoioMMDAO;
import br.gov.camara.negocio.bancotalentos.pojo.AtributoTalento;
import br.gov.camara.negocio.comum.dao.TipoHTMLDAO;
import br.gov.camara.negocio.comum.pojo.TipoHTML;

/**
 * Facade para atributo de talento
 */
public class AtributoTalentoFacade extends Facade
{
    // Variáveis de instância
    private static Log log = LogFactory.getLog(AtributoTalentoFacade.class);

    // Para trabalhar com objetos Mock
    private AtributoTalentoDAO objAtributoTalentoDAO;

    public AtributoTalentoFacade()
    {
        setObjAtributoTalentoDAO(new AtributoTalentoDAO());
    }

    public void setObjAtributoTalentoDAO(AtributoTalentoDAO objAtributoTalentoDAO)
    {
        this.objAtributoTalentoDAO = objAtributoTalentoDAO;
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

        int intTotalRegistros = 0;
        try
        {
            intTotalRegistros = objAtributoTalentoDAO.obterTotalRegistros();
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

        List lstRetorno = null;
        try
        {
            lstRetorno = objAtributoTalentoDAO.obterPorPagina(intNumeroPagina, intMaximoPagina);
            objAtributoTalentoDAO.inicializarTipoHTML(lstRetorno);
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
     * @return AtributoTalento POJO representando o registro obtido
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     * 
     */
    public AtributoTalento obterPelaChave(String strChave) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obtém o registro pela chave

        AtributoTalento objAtributoTalento = null;
        try
        {
            objAtributoTalento = (AtributoTalento) objAtributoTalentoDAO.obterPelaChave(strChave);
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
     * Obtém candidatos a pai, ou seja, todos os atributos exceto o que está sendo passado
     * 
     * @param objAtributoTalento Atributo de talento que se deseja excluir
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     * 
     */
    public List obterCandidatosAPai(AtributoTalento objAtributoTalento) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obtém o registro pela chave

        List lstCandidatosAPai = null;
        try
        {
            lstCandidatosAPai = objAtributoTalentoDAO.obterCandidatosAPai(objAtributoTalento);
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
     * Obtém os tipos de HTML
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     * 
     */
    public List obterTiposHTML() throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obtém os registros
        TipoHTMLDAO objTipoHTMLDAO = new TipoHTMLDAO();
        List lstRetorno = null;
        try
        {
            lstRetorno = objTipoHTMLDAO.obterTodos();
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
     * Obtém tabelas de apoio de meta dados
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     * 
     */
    public List obterTabelasApoioMM() throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obtém
        TabelaApoioMMDAO objTabelaApoioMMDAO = new TabelaApoioMMDAO();
        List lstRetorno = null;
        try
        {
            lstRetorno = objTabelaApoioMMDAO.obterTodos();
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
     * @param objAtributoTalento POJO representando o objeto a ser incluído
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     *
     * @return retorna a chave do objeto que foi incluido
     *
     */
    public String incluir(AtributoTalento objAtributoTalento) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        TipoHTMLDAO objTipoHTMLDAO = new TipoHTMLDAO();
        String strChave = null;
        try
        {
            //  Verifica se o atributo tipo HTML do atributo pai indica
            // que o atributo aceita multivaloração, o que não é permitido
            if (objAtributoTalento.getAtributoTalentoPai() != null)
            {
                AtributoTalento objAtributoTalentoPai = (AtributoTalento) objAtributoTalentoDAO.obterPelaChave(String.valueOf(objAtributoTalento.getAtributoTalentoPai().getIdentificador()));
                if (!"U".equals(objAtributoTalentoPai.getTipoHTML().getMultiplicidade()))
                {
                    throw new CDException("Não é permitido que um atributo que possui filhos possa ser de um tipo HTML "
                            + "que permita múltiplos valores ou que não possua opções");
                }
            }

            // Se a descrição de pesquisa estiver preenchida, o indicativo de pesquisa também deverá estar
            if (objAtributoTalento.getDescricaoPesquisa() != null && !"".equals(objAtributoTalento.getDescricaoPesquisa()))
            {
                if ("N".equals(objAtributoTalento.getIndicativoPesquisa()))
                {
                    throw new CDException("Não é permitido informar uma dica de pesquisa" + " se o atributo não for de pesquisa");
                }
            }

            // Se o atributo for de pesquisa, o tipo HTML deve admitir opções, ou seja, ser dos tipos
            // U (aceita uma única opção) ou M (aceita múltiplas opções)
            TipoHTML objTipoHTML = (TipoHTML) objTipoHTMLDAO.obterPelaChave(String.valueOf(objAtributoTalento.getTipoHTML().getIdentificador()));
            if ("S".equals(objAtributoTalento.getIndicativoPesquisa()))
            {
                if (!"U".equals(objTipoHTML.getMultiplicidade()) && !"M".equals(objTipoHTML.getMultiplicidade()))
                {
                    throw new CDException("Não é permitido que um atributo que possa ser pesquisado não seja " + "de um tipo HTML que não permita opções");
                }
            }

            // Se houver máscara cadastrada, o tipo de HTML não pode aceitar opções
            if (objAtributoTalento.getMascara() != null && !"".equals(objAtributoTalento.getMascara()))
            {
                if ("U".equals(objTipoHTML.getMultiplicidade()) || "M".equals(objTipoHTML.getMultiplicidade()))
                {
                    throw new CDException("Não é permitido informar máscara para um atributo que seja " + "de um tipo HTML que permita opções");
                }
            }

            // Valida máscara
            if (objAtributoTalento.getMascara() != null && !"".equals(objAtributoTalento.getMascara()))
            {
                if (!ExpressaoRegular.compilar(objAtributoTalento.getMascara()))
                {
                    throw new CDException("Máscara inválida");
                }
            }

            // Inclui
            strChave = objAtributoTalentoDAO.incluir(objAtributoTalento);
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
     * @param objAtributoTalento POJO representando o objeto a ser incluído
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     *
     */
    public void alterar(AtributoTalento objAtributoTalento) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e altera registro

        AtributoTalentoOpcaoDAO objAtributoTalentoOpcaoDAO = new AtributoTalentoOpcaoDAO();
        TipoHTMLDAO objTipoHTMLDAO = new TipoHTMLDAO();
        try
        {
            //  Verifica se o atributo tipo HTML do atributo pai indica
            // que o atributo aceita multivaloração, o que não é permitido
            if (objAtributoTalento.getAtributoTalentoPai() != null)
            {
                AtributoTalento objAtributoTalentoPai = (AtributoTalento) objAtributoTalentoDAO.obterPelaChave(String.valueOf(objAtributoTalento.getAtributoTalentoPai().getIdentificador()));
                if ("M".equals(objAtributoTalentoPai.getTipoHTML().getMultiplicidade()))
                {
                    throw new CDException("Não é permitido que um atributo que possui filhos possa ser de um tipo HTML " + "que permita múltiplos valores");
                }
            }

            // Valida se há referência circular
            if (objAtributoTalento.getAtributoTalentoPai() != null)
            {
                if (objAtributoTalentoDAO.verificarReferenciaCircular(objAtributoTalento))
                {
                    throw new CDException("Não pode existir referência circular entre os atributos, isto é,"
                            + " um atributo não pode referenciar outro que já o referencia");
                }
            }

            // Valida se o tipo HTML tem multiplicidade igual a L (Livre preenchimento) e tem opções, o que não
            // é permitido
            TipoHTML objTipoHTML = (TipoHTML) objTipoHTMLDAO.obterPelaChave(String.valueOf(objAtributoTalento.getTipoHTML().getIdentificador()));
            if ("L".equals(objTipoHTML.getMultiplicidade()) && objAtributoTalentoOpcaoDAO.verificarExistenciaPorAtributoTalento(objAtributoTalento))
            {
                throw new CDException("Não é permitido que esse atributo seja de livre preenchimento, " + "pois ele já possui opções cadastradas");
            }

            // Se a descrição de pesquisa estiver preenchida, o indicativo de pesquisa também deverá estar
            if (objAtributoTalento.getDescricaoPesquisa() != null && !"".equals(objAtributoTalento.getDescricaoPesquisa()))
            {
                if ("N".equals(objAtributoTalento.getIndicativoPesquisa()))
                {
                    throw new CDException("Não é permitido informar uma dica de pesquisa" + " se o atributo não for de pesquisa");
                }
            }

            // Se o atributo for de pesquisa, o tipo HTML deve admitir opções, ou seja, ser dos tipos
            // U (aceita uma única opção) ou M (aceita múltiplas opções)
            if ("S".equals(objAtributoTalento.getIndicativoPesquisa()))
            {
                if (!"U".equals(objTipoHTML.getMultiplicidade()) && !"M".equals(objTipoHTML.getMultiplicidade()))
                {
                    throw new CDException("Não é permitido que um atributo que possa ser pesquisado não seja " + "de um tipo HTML que não permita opções");
                }
            }

            // Se o atributo tiver uma máscara definida, o mesmo não pode ter opções
            if (objAtributoTalento.getMascara() != null && !"".equals(objAtributoTalento.getMascara()))
            {
                if (objAtributoTalentoOpcaoDAO.obterPeloAtributo(objAtributoTalento) != null)
                {
                    throw new CDException("Não é permitido que um atributo com máscara de " + "preenchimento definida possua opções");
                }
            }

            // Se houver máscara cadastrada, o tipo de HTML não pode aceitar opções
            if (objAtributoTalento.getMascara() != null && !"".equals(objAtributoTalento.getMascara()))
            {
                if ("U".equals(objTipoHTML.getMultiplicidade()) || "M".equals(objAtributoTalento.getTipoHTML().getMultiplicidade()))
                {
                    throw new CDException("Não é permitido informar máscara para um atributo que seja " + "de um tipo HTML que permita opções");
                }
            }

            // Valida máscara
            if (objAtributoTalento.getMascara() != null && !"".equals(objAtributoTalento.getMascara()))
            {
                Pattern.compile(objAtributoTalento.getMascara());
            }

            // Altera
            objAtributoTalentoDAO.alterar(objAtributoTalento);
        }
        catch (PatternSyntaxException pse)
        {
            throw new CDException("Máscara inválida");
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
     * @param objAtributoTalento POJO representando o objeto a ser incluído
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     *
     */
    public void excluir(AtributoTalento objAtributoTalento) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        try
        {
            objAtributoTalentoDAO.excluir(objAtributoTalento);
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
