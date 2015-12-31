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

    // Vari�veis de inst�ncia
    private static Log log = LogFactory.getLog(AtributoTalentoDAO.class);

    /**
     * Construtor sem argumentos
     */
    public AtributoTalentoOpcaoDAO()
    {
        super("Op��es de atributo de talento");
    }

    /**
     * Obt�m todos os registros
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
     * Obt�m todos os registros relacionados a determinado atributo de talento
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
     * @return boolean Contendo a verifica��o
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
     * Obt�m um registro a partir da chave
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
     * Obt�m o total de registros
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
     * Obt�m o total de registros relacionados a determinado atributo de talento
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
     * Obt�m o total de registros relacionados a determinado atributo de talento
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

        // Obt�m identificadores dos atributos
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
     * Obt�m os registros de determinada p�gina
     *
     * @param int N�mero da p�gina a ser mostrada
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
     * Obt�m os registros por descri��o, apenas os que estiverem associados aos atributos de talento especificados, 
     * de determinada p�gina
     *
     * @param String Descri��o da op��o
     * @param List Atributos de talento que as op��es devem estar relacionadas
     * @param int N�mero da p�gina a ser mostrada
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

        // Obt�m identificadores dos atributos
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
     * Obt�m os registros de determinada p�gina relacionados a determinado atributo de talento
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
     * Obt�m candidatos a pai, ou seja, todas as op��es do atributos requerido, exceto a que est� sendo passado
     * 
     * @param objAtributoTalento Atributo de talento para buscar as op��es
     * @param objAtributoTalentoOpcao Op��o de atributo de talento que se deseja excluir
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
     * Obt�m as op��es de determinado atributo de talento
     * 
     * @param objAtributoTalento Atributo de talento desejado
     * 
     * @return List contendo as op��es de atributo de talento
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
     * Obt�m op��es de determinado atributo de talento, e que n�o tenham pai
     *  
     * @param objAtributoTalento Atributo de talento desejado
     * 
     * @return List contendo as op��es de atributo de talento
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
     * Obt�m op��es de atributo pelo pai
     * 
     * @param objAtributoTalentoOpcao Op��o de atributo de talento a ser consultada
     * 
     * @return List contendo as op��es de atributo de talento
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
     * Obt�m op��es de atributo pelo pai
     * 
     * @param objAtributoTalentoOpcao Op��o de atributo de talento a ser consultada
     * 
     * @return List contendo as op��es de atributo de talento
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
     * @param Object POJO representando o objeto a ser exclu�do
     * 
     * @return String Contendo a consulta de exclus�o 
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

        // Retorna consulta de exclus�o
        return " FROM" + " AtributoTalentoOpcao atributoTalentoOpcao" +

        " WHERE" + " atributoTalentoOpcao.identificador = " + ((AtributoTalentoOpcao) objAtributoTalentoOpcao).getIdentificador();
    }

    /**
     * M�todo utilizado para efetuar consultas gen�ricas, por p�gina
     * 
     * @param objConsulta Objeto de consulta contendo os par�metros necess�rios para montar a query
     * @param intNumeroPagina N�mero da p�gina a ser exibida
     * @param intMaximoPagina N�mero total de registros da p�gina
     * 
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     */
    public List consultar(Consulta objConsulta) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Declara��es
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

            // Ordena��o            
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
     * Obt�m total de registros da consulta
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
        // Declara��es
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
            throw new DAOException("Ocorreu um erro ao consultar registros por p�gina na classe " + strNomeClasse, cde);
        }
    }

    /**
     * M�todo utilizado para efetuar consultas gen�ricas, por p�gina
     * 
     * @param objConsulta Objeto de consulta contendo os par�metros necess�rios para montar a query
     * @param intNumeroPagina N�mero da p�gina a ser exibida
     * @param intMaximoPagina N�mero total de registros da p�gina
     * 
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     */
    public List consultar(Consulta objConsulta, int intNumeroPagina, int intMaximoPagina) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }
        // Declara��es
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
            throw new DAOException("Ocorreu um erro ao consultar registros por p�gina na classe " + strNomeClasse, cde);
        }
    }

    /**
     *  M�todo que pede a inicializa��o da propriedade atributoTalentoOpcaoPai, que � lazy
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
     *  M�todo que pede a inicializa��o da propriedade atributoTalentoOpcaoPai, que � lazy
     * 
     * @param lstAtributoTalentoOpcoes Lista de objetos que t�m o atributo que vai ser recuperado
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
     * M�todo que pede a inicializa��o da propriedade atributoTalento, que � lazy
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
     *  M�todo que pede a inicializa��o da propriedade atributoTalento, que � lazy
     * 
     * @param lstAtributoTalentoOpcoes Lista de objetos que t�m o atributo que vai ser recuperado
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
