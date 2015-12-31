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
    // Vari�veis de inst�ncia
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
     * Obt�m um registro a partir da chave
     *
     * @param strChave Chave do registro a ser obtido
     * 
     * @return AtributoTalento POJO representando o registro obtido
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
     * 
     */
    public AtributoTalento obterPelaChave(String strChave) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obt�m o registro pela chave

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
     * Obt�m candidatos a pai, ou seja, todos os atributos exceto o que est� sendo passado
     * 
     * @param objAtributoTalento Atributo de talento que se deseja excluir
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
     * 
     */
    public List obterCandidatosAPai(AtributoTalento objAtributoTalento) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obt�m o registro pela chave

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
     * Obt�m os tipos de HTML
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
     * 
     */
    public List obterTiposHTML() throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obt�m os registros
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
     * Obt�m tabelas de apoio de meta dados
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
     * 
     */
    public List obterTabelasApoioMM() throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obt�m
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
     * @param objAtributoTalento POJO representando o objeto a ser inclu�do
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
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
            // que o atributo aceita multivalora��o, o que n�o � permitido
            if (objAtributoTalento.getAtributoTalentoPai() != null)
            {
                AtributoTalento objAtributoTalentoPai = (AtributoTalento) objAtributoTalentoDAO.obterPelaChave(String.valueOf(objAtributoTalento.getAtributoTalentoPai().getIdentificador()));
                if (!"U".equals(objAtributoTalentoPai.getTipoHTML().getMultiplicidade()))
                {
                    throw new CDException("N�o � permitido que um atributo que possui filhos possa ser de um tipo HTML "
                            + "que permita m�ltiplos valores ou que n�o possua op��es");
                }
            }

            // Se a descri��o de pesquisa estiver preenchida, o indicativo de pesquisa tamb�m dever� estar
            if (objAtributoTalento.getDescricaoPesquisa() != null && !"".equals(objAtributoTalento.getDescricaoPesquisa()))
            {
                if ("N".equals(objAtributoTalento.getIndicativoPesquisa()))
                {
                    throw new CDException("N�o � permitido informar uma dica de pesquisa" + " se o atributo n�o for de pesquisa");
                }
            }

            // Se o atributo for de pesquisa, o tipo HTML deve admitir op��es, ou seja, ser dos tipos
            // U (aceita uma �nica op��o) ou M (aceita m�ltiplas op��es)
            TipoHTML objTipoHTML = (TipoHTML) objTipoHTMLDAO.obterPelaChave(String.valueOf(objAtributoTalento.getTipoHTML().getIdentificador()));
            if ("S".equals(objAtributoTalento.getIndicativoPesquisa()))
            {
                if (!"U".equals(objTipoHTML.getMultiplicidade()) && !"M".equals(objTipoHTML.getMultiplicidade()))
                {
                    throw new CDException("N�o � permitido que um atributo que possa ser pesquisado n�o seja " + "de um tipo HTML que n�o permita op��es");
                }
            }

            // Se houver m�scara cadastrada, o tipo de HTML n�o pode aceitar op��es
            if (objAtributoTalento.getMascara() != null && !"".equals(objAtributoTalento.getMascara()))
            {
                if ("U".equals(objTipoHTML.getMultiplicidade()) || "M".equals(objTipoHTML.getMultiplicidade()))
                {
                    throw new CDException("N�o � permitido informar m�scara para um atributo que seja " + "de um tipo HTML que permita op��es");
                }
            }

            // Valida m�scara
            if (objAtributoTalento.getMascara() != null && !"".equals(objAtributoTalento.getMascara()))
            {
                if (!ExpressaoRegular.compilar(objAtributoTalento.getMascara()))
                {
                    throw new CDException("M�scara inv�lida");
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
     * @param objAtributoTalento POJO representando o objeto a ser inclu�do
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
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
            // que o atributo aceita multivalora��o, o que n�o � permitido
            if (objAtributoTalento.getAtributoTalentoPai() != null)
            {
                AtributoTalento objAtributoTalentoPai = (AtributoTalento) objAtributoTalentoDAO.obterPelaChave(String.valueOf(objAtributoTalento.getAtributoTalentoPai().getIdentificador()));
                if ("M".equals(objAtributoTalentoPai.getTipoHTML().getMultiplicidade()))
                {
                    throw new CDException("N�o � permitido que um atributo que possui filhos possa ser de um tipo HTML " + "que permita m�ltiplos valores");
                }
            }

            // Valida se h� refer�ncia circular
            if (objAtributoTalento.getAtributoTalentoPai() != null)
            {
                if (objAtributoTalentoDAO.verificarReferenciaCircular(objAtributoTalento))
                {
                    throw new CDException("N�o pode existir refer�ncia circular entre os atributos, isto �,"
                            + " um atributo n�o pode referenciar outro que j� o referencia");
                }
            }

            // Valida se o tipo HTML tem multiplicidade igual a L (Livre preenchimento) e tem op��es, o que n�o
            // � permitido
            TipoHTML objTipoHTML = (TipoHTML) objTipoHTMLDAO.obterPelaChave(String.valueOf(objAtributoTalento.getTipoHTML().getIdentificador()));
            if ("L".equals(objTipoHTML.getMultiplicidade()) && objAtributoTalentoOpcaoDAO.verificarExistenciaPorAtributoTalento(objAtributoTalento))
            {
                throw new CDException("N�o � permitido que esse atributo seja de livre preenchimento, " + "pois ele j� possui op��es cadastradas");
            }

            // Se a descri��o de pesquisa estiver preenchida, o indicativo de pesquisa tamb�m dever� estar
            if (objAtributoTalento.getDescricaoPesquisa() != null && !"".equals(objAtributoTalento.getDescricaoPesquisa()))
            {
                if ("N".equals(objAtributoTalento.getIndicativoPesquisa()))
                {
                    throw new CDException("N�o � permitido informar uma dica de pesquisa" + " se o atributo n�o for de pesquisa");
                }
            }

            // Se o atributo for de pesquisa, o tipo HTML deve admitir op��es, ou seja, ser dos tipos
            // U (aceita uma �nica op��o) ou M (aceita m�ltiplas op��es)
            if ("S".equals(objAtributoTalento.getIndicativoPesquisa()))
            {
                if (!"U".equals(objTipoHTML.getMultiplicidade()) && !"M".equals(objTipoHTML.getMultiplicidade()))
                {
                    throw new CDException("N�o � permitido que um atributo que possa ser pesquisado n�o seja " + "de um tipo HTML que n�o permita op��es");
                }
            }

            // Se o atributo tiver uma m�scara definida, o mesmo n�o pode ter op��es
            if (objAtributoTalento.getMascara() != null && !"".equals(objAtributoTalento.getMascara()))
            {
                if (objAtributoTalentoOpcaoDAO.obterPeloAtributo(objAtributoTalento) != null)
                {
                    throw new CDException("N�o � permitido que um atributo com m�scara de " + "preenchimento definida possua op��es");
                }
            }

            // Se houver m�scara cadastrada, o tipo de HTML n�o pode aceitar op��es
            if (objAtributoTalento.getMascara() != null && !"".equals(objAtributoTalento.getMascara()))
            {
                if ("U".equals(objTipoHTML.getMultiplicidade()) || "M".equals(objAtributoTalento.getTipoHTML().getMultiplicidade()))
                {
                    throw new CDException("N�o � permitido informar m�scara para um atributo que seja " + "de um tipo HTML que permita op��es");
                }
            }

            // Valida m�scara
            if (objAtributoTalento.getMascara() != null && !"".equals(objAtributoTalento.getMascara()))
            {
                Pattern.compile(objAtributoTalento.getMascara());
            }

            // Altera
            objAtributoTalentoDAO.alterar(objAtributoTalento);
        }
        catch (PatternSyntaxException pse)
        {
            throw new CDException("M�scara inv�lida");
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
     * @param objAtributoTalento POJO representando o objeto a ser inclu�do
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
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
