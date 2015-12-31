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

package br.gov.camara.negocio.bancotalentos.dao;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import br.gov.camara.exception.CDException;
import br.gov.camara.negocio.DAO;
import br.gov.camara.negocio.bancotalentos.pojo.AtributoTalento;
import br.gov.camara.negocio.exception.DAOException;
import br.gov.camara.negocio.util.Consulta;
import br.gov.camara.negocio.util.ConsultaComum;

/**
 * 
 * Classe DAO para a tabela AtributoTalento
 *
 */
public class AtributoTalentoDAO extends DAO implements ConsultaComum
{
    // Variáveis de instância
    private static Log log = LogFactory.getLog(AtributoTalentoDAO.class);

    /**
     * Construtor sem argumentos 
     */
    public AtributoTalentoDAO()
    {
        super("Atributo Talento");
    }

    /**
     * Obtém todos os registros
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     * 
     */
    public List obterTodos() throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Monta query
        String strConsulta = " FROM" + " AtributoTalento atributoTalento" +

        " ORDER BY" + " atributoTalento.nome";

        // Recupera os dados 
        return obter(strConsulta);
    }

    /**
     * Obtém um registro a partir da chave
     *
     * @param strChave Chave do registro a ser obtido
     * 
     * @return Object POJO representando o registro obtido
     *
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     * 
     */
    public Object obterPelaChave(String strChave) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        String strConsulta = " FROM" + " AtributoTalento atributoTalento" +

        " WHERE" + " atributoTalento.identificador = " + strChave;

        List lstConsulta = obter(strConsulta);
        if (!lstConsulta.isEmpty())
        {
            return (AtributoTalento) lstConsulta.get(0);
        }
        else
        {
            return null;
        }
    }

    /**
     * Obtém o total de registros
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     * 
     */
    public int obterTotalRegistros() throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Monta query
        String strConsulta = " SELECT COUNT(*)" +

        " FROM" + " AtributoTalento atributoTalento";

        // Recupera os dados
        return obterTotalRegistros(strConsulta);
    }

    /**
     * Obtém os registros de determinada página
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     * 
     */
    public List obterPorPagina(int intNumeroPagina, int intMaximoPagina) throws DAOException
    {
        // Monta consulta
        String strConsulta = " FROM" + " AtributoTalento atributoTalento" +

        " ORDER BY" + " atributoTalento.nome ASC";

        // Recupera os dados
        return obterPorPagina(intNumeroPagina, intMaximoPagina, strConsulta);
    }

    /**
     * Obtém candidatos a pai, ou seja, todos os atributos exceto o que está sendo passado
     * 
     * @param objAtributoTalento Atributo de talento que se deseja excluir
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     * 
     */
    public List obterCandidatosAPai(AtributoTalento objAtributoTalento) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Monta query
        String strConsulta = " FROM" + " AtributoTalento atributoTalento";

        if (objAtributoTalento != null)
        {
            strConsulta += " WHERE" + " atributoTalento.identificador != " + objAtributoTalento.getIdentificador();
        }

        strConsulta += " ORDER BY" + " atributoTalento.nome";

        // Recupera os dados 
        return obter(strConsulta);
    }

    /**
     * Exclui um registro
     *
     * @param Object POJO representando o objeto a ser excluído
     * 
     * @return String Contendo a consulta de exclusão 
     * 
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     * 
     */
    public String excluirImpl(Object objAtributoTalento) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Retorna consulta de exclusão
        return " FROM" + " AtributoTalento atributoTalento" +

        " WHERE" + " atributoTalento.identificador = " + ((AtributoTalento) objAtributoTalento).getIdentificador();
    }

    /**
     * Obtém total de registros da consulta
     *
     * @param Consulta objeto contendo os filtros para consulta
     *  
     * @return List contendo o resultado da pesquisa
     * 
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     * 
     */
    public int obterTotalRegistros(Consulta objConsulta) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }
        // Declarações
        int intRetorno = 0;

        try
        {
            String strFiltro = objConsulta.montarFiltro();

            // Monta query
            String strConsulta = " SELECT " + " COUNT(*)" +

            " FROM" + " AtributoTalento atributoTalento";
            if (!strFiltro.trim().equals(""))
            {
                strConsulta += " WHERE " + strFiltro;
            }

            // Recupera os dados
            return obterTotalRegistros(strConsulta);

        }
        catch (CDException cde)
        {
            throw new DAOException("Ocorreu um erro ao consultar registros por página na classe " + strNomeClasse, cde);
        }
    }

    /**
     * Método utilizado para efetuar consultas genéricas, por página
     * 
     * @param objConsulta Objeto de consulta contendo os parâmetros necessários para montar a query
     * @param intNumeroPagina Número da página a ser exibida
     * @param intMaximoPagina Número total de registros da página
     * 
     * @throws DAOException se ocorrer algum erro relacionado com o acesso a banco de dados
     */
    public List consultar(Consulta objConsulta, int intNumeroPagina, int intMaximoPagina) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Declarações
        List lstRetorno = null;

        try
        {
            String strFiltro = objConsulta.montarFiltro();

            // Monta query
            String strConsulta = " FROM" + " AtributoTalento atributoTalento";

            if (!strFiltro.trim().equals(""))
            {
                strConsulta += " WHERE " + strFiltro;
            }

            strConsulta += " ORDER BY" + " atributoTalento.nome ASC";

            // Recupera os dados 
            lstRetorno = obterPorPagina(intNumeroPagina, intMaximoPagina, strConsulta);

            // Retorna
            return lstRetorno;
        }
        catch (CDException cde)
        {
            throw new DAOException("Ocorreu um erro ao consultar registros por página na classe " + strNomeClasse, cde);
        }
    }

    /**
     * Verifica se existem filhos para determinado atributo de talento
     * 
     * @param objAtributoTalento Atributo de talento a ser verificado
     * 
     * @return boolean Para verificar a existência de filhos
     * 
     * @throws DAOException se ocorrer algum erro relacionado com o acesso a banco de dados
     */
    public boolean verificarExistenciaFilhos(AtributoTalento objAtributoTalento) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Monta query
        String strConsulta = " FROM"
                + " AtributoTalento atributoTalento"
                +

                " WHERE"
                + " EXISTS"
                + " (FROM AtributoTalento atributoTalentoFilho "
                + " WHERE atributoTalentoFilho.atributoTalentoPai.identificador = "
                + objAtributoTalento.getIdentificador()
                + ")";

        // Recupera os dados 
        List lstResultado = obter(strConsulta);
        if (lstResultado.size() > 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Verifica se o atributo que está sendo cadastrado como pai refencia o mesmo atributo passado
     * 
     * @param objAtributoTalento Atributo de talento a ser verificado
     * 
     * @return boolean Para verificar a existência da referência circular
     * 
     * @throws DAOException se ocorrer algum erro relacionado com o acesso a banco de dados
     */
    public boolean verificarReferenciaCircular(AtributoTalento objAtributoTalento) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Monta query
        String strConsulta = " FROM"
                + " AtributoTalento atributoTalento"
                +

                " WHERE"
                + " atributoTalento.atributoTalentoPai.identificador = "
                + objAtributoTalento.getIdentificador()
                + " AND atributoTalento.identificador = "
                + objAtributoTalento.getAtributoTalentoPai().getIdentificador();

        // Recupera os dados 
        List lstResultado = obter(strConsulta);
        if (lstResultado.size() > 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /* Caso de Uso 002-ManterAtributoTalento
    Não pode existir referência circular entre os Atributos de Talento, isto é, um Atributo não pode referenciar
    um outro que já o referencia */

    /**
     *  Método que pede a inicialização da propriedade atributoTalentoPai, que é lazy
     * 
     * @param Lotacao objAtributoTalento Classe que tem o atributo que vai ser recuperado
     * 
     * @see br.gov.camara.negocio.DAO#inicializarRelacionamento(java.lang.Object)
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     */
    public void inicializarAtributoTalentoPai(AtributoTalento objAtributoTalento) throws DAOException
    {
        inicializarRelacionamento(objAtributoTalento.getAtributoTalentoPai());
    }

    /**
     * Método que pede a inicialização da propriedade tipoHTML, que é lazy
     * 
     * @param Lotacao objAtributoTalento Classe que tem o atributo que vai ser recuperado
     * 
     * @see br.gov.camara.negocio.DAO#inicializarRelacionamento(java.lang.Object)
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     */
    public void inicializarTipoHTML(AtributoTalento objAtributoTalento) throws DAOException
    {
        inicializarRelacionamento(objAtributoTalento.getTipoHTML());
    }

    /**
     *  Método que pede a inicialização da propriedade atributoTalentoOpcao, que é lazy
     * 
     * @param lstTalentos Lista com os objetos que têm o atributo que vai ser recuperado
     * 
     * @see br.gov.camara.negocio.DAO#inicializarRelacionamento(java.lang.Object)
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     */
    public void inicializarTipoHTML(List lstAtributoTalento) throws DAOException
    {
        Iterator itrAtributoTalento = lstAtributoTalento.iterator();
        while (itrAtributoTalento.hasNext())
        {
            inicializarTipoHTML((AtributoTalento) itrAtributoTalento.next());
        }
    }

    /**
     *  Método que pede a inicialização da propriedade tabelaApoioMM, que é lazy
     * 
     * @param AtributoTalento objAtributoTalento Classe que tem o atributo que vai ser recuperado
     * 
     * @see br.gov.camara.negocio.DAO#inicializarRelacionamento(java.lang.Object)
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     */
    public void inicializarTabelaApoioMM(AtributoTalento objAtributoTalento) throws DAOException
    {
        inicializarRelacionamento(objAtributoTalento.getTabelaApoioMM());
    }

    /**
     *  Método que pede a inicialização da propriedade atributoTalentoOpcoes, que é lazy
     * 
     * @param AtributoTalento objAtributoTalento Classe que tem o atributo que vai ser recuperado
     * 
     * @see br.gov.camara.negocio.DAO#inicializarRelacionamento(java.lang.Object)
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     */
    public void inicializarAtributoTalentoOpcoes(AtributoTalento objAtributoTalento) throws DAOException
    {
        inicializarRelacionamento(objAtributoTalento.getAtributoTalentoOpcoes());
    }
}
