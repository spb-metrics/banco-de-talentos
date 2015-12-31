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

package br.gov.camara.negocio.comum.dao;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import br.gov.camara.biblioteca.exception.DataException;
import br.gov.camara.biblioteca.util.DataNova;
import br.gov.camara.negocio.DAO;
import br.gov.camara.negocio.exception.DAOException;

/**
 * Classe DAO para a tabela TipoHTML
 */
public class DataDAO extends DAO
{
    // Variáveis de instância de configuração
    private static Log log = LogFactory.getLog(DataDAO.class);

    private static final String MSG_ERRO = "Este método não pode ser utilizado";

    private static final String CHAVE_QUERY_TIMESTAMP_BD = "queryConsultaTimeStampBD";
    private static final String CHAVE_NOME_COLUNA_TIMESTAMP = "timestamp";

    /**
     * Construtor sem argumentos
     */
    public DataDAO()
    {
        super("DataDAO");
    }

    /**
     * Construtor sem argumentos
     */
    public DataDAO(String nomeConexao)
    {
        super("DataDAO", nomeConexao);
    }

    /**
     * Obtém todos os registros
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     * @throws SigespDAOException
     *             Se ocorrer algum erro relacionado com o acesso a banco de
     *             dados
     */
    public List obterTodos() throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        throw new DAOException(MSG_ERRO);
    }

    /**
     * Obtém um registro a partir da chave
     * 
     * @param strChave
     *            Chave do registro a ser obtido
     * @return Object POJO representando o registro obtido
     * @throws DAOException
     *             Se ocorrer algum erro relacionado com o acesso a banco de
     *             dados
     */
    public Object obterPelaChave(String strChave) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        throw new DAOException(MSG_ERRO);
    }

    /**
     * Exclui um registro
     * 
     * @param TipoHTML
     *            POJO representando o objeto a ser excluído
     * @return String Contendo a consulta
     * @throws DAOException
     *             Se ocorrer algum erro relacionado com o acesso a banco de
     *             dados
     */
    public String excluirImpl(Object objTipoHTML) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        throw new DAOException(MSG_ERRO);
    }

    /*
     * (non-Javadoc)
     * 
     * @see br.gov.camara.negocio.DAO#alterarImpl(java.lang.Object)
     */
    protected void alterarImpl(Object objPOJO) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        throw new DAOException(MSG_ERRO);
    }

    /*
     * (non-Javadoc)
     * 
     * @see br.gov.camara.negocio.DAO#existeTransacaoAtiva()
     */
    public boolean existeTransacaoAtiva() throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        throw new DAOException(MSG_ERRO);
    }

    /*
     * (non-Javadoc)
     * 
     * @see br.gov.camara.negocio.DAO#incluirImpl(java.lang.Object)
     */
    protected Serializable incluirImpl(Object objPOJO) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        throw new DAOException(MSG_ERRO);
    }

    /*
     * (non-Javadoc)
     * 
     * @see br.gov.camara.negocio.DAO#obterPelaChave(java.lang.Object)
     */
    public Object obterPelaChave(Object objPOJO) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        throw new DAOException(MSG_ERRO);
    }

    public Date obterTimeStamp() throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Lê SQL para obtenção da data e hora
        String strConsulta = super.obterPropriedadeSubstituicao(DataDAO.CHAVE_QUERY_TIMESTAMP_BD);

        if (log.isDebugEnabled())
        {
            log.debug("Consulta a ser executada para a obtenção da data e hora do banco de dados: " + strConsulta);
        }

        // Executa SQL
        List resultado = super.obterPorSQL(strConsulta);

        Date datDataHora = null;

        try
        {
            if (resultado == null || resultado.size() == 0)
            {
                log.debug("Nenhum resultado (data e hora) foi obtido do banco de dados.");
                throw new DAOException("Ocorreu um erro recuperando a hora do banco de dados");
            }

            // Pega primeiro registro
            Object resultadoDataHora = ((Map) resultado.get(0)).get(DataDAO.CHAVE_NOME_COLUNA_TIMESTAMP);
            if (resultadoDataHora instanceof Date)
            {
                datDataHora = (Date) resultadoDataHora;
            }
            else if (resultadoDataHora instanceof String)
            {
                try
                {
                    datDataHora = DataNova.formatarDate((String) resultadoDataHora);
                }
                catch (DataException de)
                {
                    throw new DAOException("Não foi possível formatar a data retornada pelo banco de dados." + resultadoDataHora);
                }

            }
            else
            {
                throw new DAOException("A query informada no hibernate-config não retorna um dado do tipo tratado (Date ou String).");
            }

        }
        catch (DAOException e)
        {
            throw new DAOException(e.getMessage(), e);
        }

        return datDataHora;

    }

}
