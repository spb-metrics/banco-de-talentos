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
import br.gov.camara.negocio.bancotalentos.pojo.AtributoTalentoOpcao;
import br.gov.camara.negocio.exception.DAOException;
import br.gov.camara.negocio.util.Consulta;
import br.gov.camara.negocio.util.ConsultaComum;

/**
 * Classe DAO para a tabela AtributoTalentoOpcao
 * 
 */
public class AtributoTalentoOpcaoDAO extends DAO implements ConsultaComum
{

    // Variáveis de instância
    private static Log log = LogFactory.getLog(AtributoTalentoDAO.class);

    /**
     * Construtor sem argumentos
     */
    public AtributoTalentoOpcaoDAO()
    {
        super("Opções de atributo de talento");
    }

    /**
     * Obtém todos os registros
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws DAOException se ocorrer algum erro relacionado com o acesso a banco de dados
     * 
     */
    public List obterTodos() throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Monta query
        String strConsulta = " FROM" + " AtributoTalentoOpcao atributoTalentoOpcao" +

        " ORDER BY" + " atributoTalentoOpcao.descricao ASC";

        return obter(strConsulta);
    }

    /**
     * Obtém todos os registros relacionados a determinado atributo de talento
     * 
     * @param objAtributoTalento Atributo de talento desejado
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws DAOException se ocorrer algum erro relacionado com o acesso a banco de dados
     * 
     */
    public List obterPorAtributoTalento(AtributoTalento objAtributoTalento) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Monta query
        String strConsulta = " FROM" + " AtributoTalentoOpcao atributoTalentoOpcao" +

        " WHERE" + " atributoTalentoOpcao.atributoTalento.identificador = " + objAtributoTalento.getIdentificador() +

        " ORDER BY" + " atributoTalentoOpcao.descricao ASC";

        return obter(strConsulta);
    }

    /**
     * Verifica se existem registros relacionados a determinado atributo de talento
     * 
     * @param objAtributoTalento Atributo de talento desejado
     * 
     * @return boolean Contendo a verificação
     *
     * @throws DAOException se ocorrer algum erro relacionado com o acesso a banco de dados
     * 
     */
    public boolean verificarExistenciaPorAtributoTalento(AtributoTalento objAtributoTalento) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Monta query
        String strConsulta = " SELECT" + " COUNT(*)" +

        " FROM" + " AtributoTalentoOpcao atributoTalentoOpcao" +

        " WHERE" + " atributoTalentoOpcao.atributoTalento.identificador = " + objAtributoTalento.getIdentificador() +

        " ORDER BY" + " atributoTalentoOpcao.descricao ASC";

        // Conta registros
        int intTotalRegistros = obterTotalRegistros(strConsulta);
        if (intTotalRegistros > 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Obtém um registro a partir da chave
     *
     * @param strChave Chave do registro a ser obtido
     * 
     * @return Object POJO representando o registro obtido
     *
     * @throws DAOException se ocorrer algum erro relacionado com o acesso a banco de dados
     * 
     */
    public Object obterPelaChave(String strChave) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        String strConsulta = " FROM" + " AtributoTalentoOpcao atributoTalentoOpcao" +

        " WHERE" + " atributoTalentoOpcao.identificador = " + strChave;

        List lstConsulta = obter(strConsulta);
        if (!lstConsulta.isEmpty())
        {
            return (AtributoTalentoOpcao) lstConsulta.get(0);
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

        " FROM" + " AtributoTalentoOpcao atributoTalentoOpcao";

        // Recupera os dados
        return obterTotalRegistros(strConsulta);
    }

    /**
     * Obtém o total de registros relacionados a determinado atributo de talento
     *
     * @param objAtributoTalento Atributo de talento desejado
     *
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     * 
     */
    public int obterTotalRegistrosPorAtributoTalento(AtributoTalento objAtributoTalento) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Monta query
        String strConsulta = " SELECT" + " COUNT(*)" +

        " FROM" + " AtributoTalentoOpcao atributoTalentoOpcao" +

        " WHERE" + " atributoTalentoOpcao.atributoTalento.identificador = " + objAtributoTalento.getIdentificador();

        // Recupera os dados
        return obterTotalRegistros(strConsulta);
    }

    /**
     * Obtém o total de registros relacionados a determinado atributo de talento
     *
     * @param objAtributoTalento Atributo de talento desejado
     *
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     * 
     */
    public int obterTotalRegistrosPorDescricaoAtributosTalentoPorPagina(String strDescricao, List lstAtributosTalento) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Obtém identificadores dos atributos
        String strAtributosTalento = "";
        Iterator itrAtributosTalento = lstAtributosTalento.iterator();
        while (itrAtributosTalento.hasNext())
        {
            AtributoTalento objAtributoTalento = (AtributoTalento) itrAtributosTalento.next();
            strAtributosTalento += objAtributoTalento.getIdentificador();
            if (itrAtributosTalento.hasNext())
            {
                strAtributosTalento += ",";
            }
        }

        // Monta consulta
        String strConsulta = " SELECT" + " COUNT(*)" +

        " FROM" + " AtributoTalentoOpcao atributoTalentoOpcao" +

        " WHERE" + " atributoTalentoOpcao.atributoTalento.identificador IN(" + strAtributosTalento + ")"
            + " AND upper(atributoTalentoOpcao.descricao) LIKE upper('%" + strDescricao + "%')";

        // Recupera os dados
        return obterTotalRegistros(strConsulta);
    }

    /**
     * Obtém os registros de determinada página
     *
     * @param int Número da página a ser mostrada
     * @param int Quantidade de registros
     *  
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     * 
     */
    public List obterPorPagina(int intNumeroPagina, int intMaximoPagina) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Monta consulta
        String strConsulta = " FROM" + " AtributoTalentoOpcao atributoTalentoOpcao" +

        " ORDER BY" + " atributoTalento.descricao ASC";

        // Recupera os dados
        return obterPorPagina(intNumeroPagina, intMaximoPagina, strConsulta);
    }

    /**
     * Obtém os registros por descrição, apenas os que estiverem associados aos atributos de talento especificados, 
     * de determinada página
     *
     * @param String Descrição da opção
     * @param List Atributos de talento que as opções devem estar relacionadas
     * @param int Número da página a ser mostrada
     * @param int Quantidade de registros
     *  
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     * 
     */
    public List obterPorDescricaoAtributosTalentoPorPagina(String strDescricao, List lstAtributosTalento, int intNumeroPagina, int intMaximoPagina) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Obtém identificadores dos atributos
        String strAtributosTalento = "";
        Iterator itrAtributosTalento = lstAtributosTalento.iterator();
        while (itrAtributosTalento.hasNext())
        {
            AtributoTalento objAtributoTalento = (AtributoTalento) itrAtributosTalento.next();
            strAtributosTalento += objAtributoTalento.getIdentificador();
            if (itrAtributosTalento.hasNext())
            {
                strAtributosTalento += ",";
            }
        }

        // Monta consulta
        String strConsulta = " FROM" + " AtributoTalentoOpcao atributoTalentoOpcao" +

        " WHERE" + " atributoTalentoOpcao.atributoTalento.identificador IN(" + strAtributosTalento + ")"
            + " AND upper(atributoTalentoOpcao.descricao) LIKE upper('%" + strDescricao + "%')" +

            " ORDER BY" + " atributoTalentoOpcao.descricao ASC";

        // Recupera os dados
        return obterPorPagina(intNumeroPagina, intMaximoPagina, strConsulta);
    }

    /**
     * Obtém os registros de determinada página relacionados a determinado atributo de talento
     *
     * @param objAtributoTalento Atributo de talento desejado
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     * 
     */
    public List obterPorPaginaPorAtributoTalento(AtributoTalento objAtributoTalento, int intNumeroPagina, int intMaximoPagina) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Monta consulta
        String strConsulta = " FROM" + " AtributoTalentoOpcao atributoTalentoOpcao" +

        " WHERE" + " atributoTalentoOpcao.atributoTalento.identificador = " + objAtributoTalento.getIdentificador() +

        " ORDER BY" + " atributoTalentoOpcao.descricao ASC";

        // Recupera os dados
        return obterPorPagina(intNumeroPagina, intMaximoPagina, strConsulta);
    }

    /**
     * Obtém candidatos a pai, ou seja, todas as opções do atributos requerido, exceto a que está sendo passado
     * 
     * @param objAtributoTalento Atributo de talento para buscar as opções
     * @param objAtributoTalentoOpcao Opção de atributo de talento que se deseja excluir
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     * 
     */
    public List obterCandidatosAPai(AtributoTalento objAtributoTalento, AtributoTalentoOpcao objAtributoTalentoOpcao) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Monta query
        String strConsulta = " FROM" + " AtributoTalentoOpcao atributoTalentoOpcao" +

        " WHERE" + " atributoTalentoOpcao.atributoTalento.identificador = " + objAtributoTalento.getAtributoTalentoPai().getIdentificador();

        if (objAtributoTalentoOpcao != null)
        {
            strConsulta += " AND" + " atributoTalentoOpcao.identificador != " + objAtributoTalentoOpcao.getIdentificador();
        }

        strConsulta += " ORDER BY" + " atributoTalentoOpcao.descricao";

        // Recupera os dados 
        return obter(strConsulta);
    }

    /**
     * Obtém as opções de determinado atributo de talento
     * 
     * @param objAtributoTalento Atributo de talento desejado
     * 
     * @return List contendo as opções de atributo de talento
     * 
     * @throws DAOException se ocorrer algum erro relacionado com o acesso a banco de dados
     */
    public List obterPeloAtributo(AtributoTalento objAtributoTalento) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Monta query
        String strConsulta = " FROM" + " AtributoTalentoOpcao atributoTalentoOpcao" +

        " WHERE " + " atributoTalentoOpcao.atributoTalento.identificador = " + objAtributoTalento.getIdentificador() +

        " ORDER BY" + " atributoTalentoOpcao.descricao ASC";

        return obter(strConsulta);

    }

    /**
     * Obtém opções de determinado atributo de talento, e que não tenham pai
     *  
     * @param objAtributoTalento Atributo de talento desejado
     * 
     * @return List contendo as opções de atributo de talento
     * 
     * @throws DAOException se ocorrer algum erro relacionado com o acesso a banco de dados
     */
    public List obterPeloAtributoSemPai(AtributoTalento objAtributoTalento) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Monta query
        String strConsulta = " FROM" + " AtributoTalentoOpcao atributoTalentoOpcao" +

        " WHERE " +

        " atributoTalentoOpcao.atributo.identificador = " + objAtributoTalento.getIdentificador() + " AND atributoTalentoOpcao.atributoPai IS NULL " +

        " ORDER BY" + " atributoTalentoOpcao.descricao ASC";

        return obter(strConsulta);
    }

    /**
     * Obtém opções de atributo pelo pai
     * 
     * @param objAtributoTalentoOpcao Opção de atributo de talento a ser consultada
     * 
     * @return List contendo as opções de atributo de talento
     * 
     * @throws DAOException se ocorrer algum erro relacionado com o acesso a banco de dados
     */
    public List obterPeloPaiEAtributoTalento(AtributoTalentoOpcao objAtributoTalentoOpcaoPai, AtributoTalento objAtributoTalento) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Monta query
        String strConsulta = " FROM" + " AtributoTalentoOpcao atributoTalentoOpcao" +

        " WHERE atributoTalentoOpcao.atributoTalentoOpcaoPai.identificador = " + objAtributoTalentoOpcaoPai.getIdentificador()
            + " AND atributoTalentoOpcao.atributoTalento.identificador = " + objAtributoTalento.getIdentificador() + " ORDER BY "
            + " atributoTalentoOpcao.descricao ASC";

        // Retorna
        return obter(strConsulta);

    }

    /**
     * Obtém opções de atributo pelo pai
     * 
     * @param objAtributoTalentoOpcao Opção de atributo de talento a ser consultada
     * 
     * @return List contendo as opções de atributo de talento
     * 
     * @throws DAOException se ocorrer algum erro relacionado com o acesso a banco de dados
     */
    public List obterPeloPai(AtributoTalentoOpcao objAtributoTalentoOpcaoPai) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Monta query
        String strConsulta = " FROM" + " AtributoTalentoOpcao atributoTalentoOpcao" +

        " WHERE " + " atributoTalentoOpcao.atributoTalentoOpcaoPai.identificador = " + objAtributoTalentoOpcaoPai.getIdentificador() +

        " ORDER BY " + " atributoTalentoOpcao.descricao ASC";

        // Retorna
        return obter(strConsulta);

    }

    /**
     * Exclui um registro
     *
     * @param Object POJO representando o objeto a ser excluído
     * 
     * @return String Contendo a consulta de exclusão 
     * 
     * @throws DAOException se ocorrer algum erro relacionado com o acesso a banco de dados
     * 
     */
    public String excluirImpl(Object objAtributoTalentoOpcao) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Retorna consulta de exclusão
        return " FROM" + " AtributoTalentoOpcao atributoTalentoOpcao" +

        " WHERE" + " atributoTalentoOpcao.identificador = " + ((AtributoTalentoOpcao) objAtributoTalentoOpcao).getIdentificador();
    }

    /**
     * Método utilizado para efetuar consultas genéricas, por página
     * 
     * @param objConsulta Objeto de consulta contendo os parâmetros necessários para montar a query
     * @param intNumeroPagina Número da página a ser exibida
     * @param intMaximoPagina Número total de registros da página
     * 
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     */
    public List consultar(Consulta objConsulta) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Declarações
        List lstRetorno = null;

        try
        {
            // Monta query
            String strConsulta = " FROM" + " AtributoTalentoOpcao atributoTalentoOpcao";

            // Verifica filtro
            if (!"".equals(objConsulta.montarFiltro()))
            {
                strConsulta += " WHERE " + objConsulta.montarFiltro();

            }

            // Ordenação            
            strConsulta += " ORDER BY" + " atributoTalentoOpcao.descricao ASC";

            // Recupera os dados 
            lstRetorno = obter(strConsulta);

            // Retorna
            return lstRetorno;
        }
        catch (CDException cde)
        {
            throw new DAOException("Ocorreu um erro ao consultar registros na classe " + strNomeClasse);
        }
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
            String strConsulta = " SELECT COUNT(*)" + " FROM" + " AtributoTalentoOpcao atributoTalentoOpcao";
            if (!strFiltro.trim().equals(""))
            {
                strConsulta += " WHERE " + strFiltro;
            }

            // Recupera os dados
            intRetorno = super.obterTotalRegistros(strConsulta);

            // Retorna
            return intRetorno;
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
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
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
            String strConsulta = " FROM" + " AtributoTalentoOpcao atributoTalentoOpcao";
            if (!strFiltro.trim().equals(""))
            {
                strConsulta += " WHERE " + strFiltro;
            }
            strConsulta += " ORDER BY" + " atributoTalentoOpcao.descricao ASC";

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
     *  Método que pede a inicialização da propriedade atributoTalentoOpcaoPai, que é lazy
     * 
     * @param objAtributoTalentoOpcao Objeto que tem o atributo que vai ser recuperado
     * 
     * @see br.gov.camara.negocio.DAO#inicializarRelacionamento(java.lang.Object)
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     */
    public void inicializarAtributoTalentoOpcaoPai(AtributoTalentoOpcao objAtributoTalentoOpcao) throws DAOException
    {
        inicializarRelacionamento(objAtributoTalentoOpcao.getAtributoTalentoOpcaoPai());
    }

    /**
     *  Método que pede a inicialização da propriedade atributoTalentoOpcaoPai, que é lazy
     * 
     * @param lstAtributoTalentoOpcoes Lista de objetos que têm o atributo que vai ser recuperado
     * 
     * @see br.gov.camara.negocio.DAO#inicializarRelacionamento(java.lang.Object)
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     */
    public void inicializarAtributoTalentoOpcaoPai(List lstAtributoTalentoOpcoes) throws DAOException
    {
        Iterator itrAtributoTalentoOpcoes = lstAtributoTalentoOpcoes.iterator();
        while (itrAtributoTalentoOpcoes.hasNext())
        {
            inicializarAtributoTalentoOpcaoPai((AtributoTalentoOpcao) itrAtributoTalentoOpcoes.next());
        }
    }

    /**
     * Método que pede a inicialização da propriedade atributoTalento, que é lazy
     * 
     * @param objAtributoTalentoOpcao Objeto que tem o atributo que vai ser recuperado
     * 
     * @see br.gov.camara.negocio.DAO#inicializarRelacionamento(java.lang.Object)
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     */
    public void inicializarAtributoTalento(AtributoTalentoOpcao objAtributoTalentoOpcao) throws DAOException
    {
        inicializarRelacionamento(objAtributoTalentoOpcao.getAtributoTalento());
    }

    /**
     *  Método que pede a inicialização da propriedade atributoTalento, que é lazy
     * 
     * @param lstAtributoTalentoOpcoes Lista de objetos que têm o atributo que vai ser recuperado
     * 
     * @see br.gov.camara.negocio.DAO#inicializarRelacionamento(java.lang.Object)
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     */
    public void inicializarAtributoTalento(List lstAtributoTalentoOpcoes) throws DAOException
    {
        Iterator itrAtributoTalentoOpcoes = lstAtributoTalentoOpcoes.iterator();
        while (itrAtributoTalentoOpcoes.hasNext())
        {
            inicializarAtributoTalento((AtributoTalentoOpcao) itrAtributoTalentoOpcoes.next());
        }
    }
}
