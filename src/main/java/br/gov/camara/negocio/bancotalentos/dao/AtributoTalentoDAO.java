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
    // Vari�veis de inst�ncia
    private static Log log = LogFactory.getLog(AtributoTalentoDAO.class);

    /**
     * Construtor sem argumentos 
     */
    public AtributoTalentoDAO()
    {
        super("Atributo Talento");
    }

    /**
     * Obt�m todos os registros
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
     * Obt�m um registro a partir da chave
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

        " FROM" + " AtributoTalento atributoTalento";

        // Recupera os dados
        return obterTotalRegistros(strConsulta);
    }

    /**
     * Obt�m os registros de determinada p�gina
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
     * Obt�m candidatos a pai, ou seja, todos os atributos exceto o que est� sendo passado
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
     * @param Object POJO representando o objeto a ser exclu�do
     * 
     * @return String Contendo a consulta de exclus�o 
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

        // Retorna consulta de exclus�o
        return " FROM" + " AtributoTalento atributoTalento" +

        " WHERE" + " atributoTalento.identificador = " + ((AtributoTalento) objAtributoTalento).getIdentificador();
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
     * @throws DAOException se ocorrer algum erro relacionado com o acesso a banco de dados
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
            throw new DAOException("Ocorreu um erro ao consultar registros por p�gina na classe " + strNomeClasse, cde);
        }
    }

    /**
     * Verifica se existem filhos para determinado atributo de talento
     * 
     * @param objAtributoTalento Atributo de talento a ser verificado
     * 
     * @return boolean Para verificar a exist�ncia de filhos
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
     * Verifica se o atributo que est� sendo cadastrado como pai refencia o mesmo atributo passado
     * 
     * @param objAtributoTalento Atributo de talento a ser verificado
     * 
     * @return boolean Para verificar a exist�ncia da refer�ncia circular
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
    N�o pode existir refer�ncia circular entre os Atributos de Talento, isto �, um Atributo n�o pode referenciar
    um outro que j� o referencia */

    /**
     *  M�todo que pede a inicializa��o da propriedade atributoTalentoPai, que � lazy
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
     * M�todo que pede a inicializa��o da propriedade tipoHTML, que � lazy
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
     *  M�todo que pede a inicializa��o da propriedade atributoTalentoOpcao, que � lazy
     * 
     * @param lstTalentos Lista com os objetos que t�m o atributo que vai ser recuperado
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
     *  M�todo que pede a inicializa��o da propriedade tabelaApoioMM, que � lazy
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
     *  M�todo que pede a inicializa��o da propriedade atributoTalentoOpcoes, que � lazy
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
